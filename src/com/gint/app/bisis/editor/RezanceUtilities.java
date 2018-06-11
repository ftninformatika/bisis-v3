package com.gint.app.bisis.editor;

import java.util.Vector;
import java.util.StringTokenizer;

import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.LELibrarian.*;

/** Konvertuje:<br>
  * 1. Iz vektora polja sa potpoljima itd. u string u UNIMARC formatu<br>
  * 2. String UNIMARC formata u vektor polja sa potpoljima itd.
  */
public class RezanceUtilities {
  /** Konstanta koja oznacava pocetak polja (decimalno 30). */
  public static final String newField = new String("\36");
  /** Konstanta koja oznacava pocetak potpolja (decimalno 31). */
  public static final String newSub = new String("\37");

  /** Pakuje rezanac u obliku stringa na osnovu vektora polja sa svojim potpoljima.
   @param fields Vektor polja sa potpoljima. Svaki objekat je klase <b>Field</b>.
   @return String u kojem se nalazi rezanac.
  */
  public static String packRezance(Vector fields) {
    String retval = new String("");
    int totalFields = fields.size();
    for (int i = 0; i < totalFields; i++) {
      Field f = (Field)fields.elementAt(i);
      retval += newField;
      retval += processField(f);
    }
    return retval;
  }

  /** Obradjuje tekuce polje na osnovu njegovog vektora potpolja.
   @param f Polje koje treba procesirati.
   @return String koji sadrzi deo rezanca vezan za prosledjeno polje.
  */
  private static String processField(Field f) {
    String retval = new String();
    retval += f.getName();
    retval += f.getInd1();
    retval += f.getInd2();
    Vector subs = f.getSubfields();
    int totalSubs = subs.size();
    for (int j = 0; j < totalSubs; j++) {
      Subfield sf = (Subfield)subs.elementAt(j);
      retval += newSub;
      retval += processSubfield(sf);
    }
    return new String(retval);
  }

  private static String processSubfield(Subfield sf) {
    String retval = new String();
    retval += sf.getName();
    String sfContent = sf.getContent();
    Vector subSubs = sf.getSubsubfields();
    int subSubTotal = subSubs.size();
    if (subSubTotal > 0) { // potpotpolje
      String newSubSub = new String();
      for (int k = 0; k < subSubTotal; k++) {
        Subfield subSF = (Subfield)subSubs.elementAt(k);
        if(!subSF.getContent().equals("")) {
          retval += newSubSub;
          retval += processSubfield(subSF);
          newSubSub = new String("\\");
        } // if
      } // for
    } else {
      // sekundarna polja ili obicna
      Vector secFields = sf.getSecFieldInVect();
      if (secFields.size() > 0) { // sekundarno polje
        int secFieldsTotal = secFields.size();
        String newSecField = new String();
        for (int k = 0; k < secFieldsTotal; k++) {
          Field secF = (Field)secFields.elementAt(k);
          retval += (newSecField);
          retval += processField(secF);
          newSecField = new String(newSub);
        } // for
      } else {
        // ovde se obradjuju potpolja
        retval += sfContent;
      }
    }
    return new String(retval);
  }


/** Raspakuje rezanac na Vektor polja sa potpoljima, gde svako potpolje ima vektor potpotpolja i
   sekundarnih polja, prema definiciji iz Unimarc formata.
   Koristi se kod editovanja bibliografske gradje.
   Format je sledeci:
   <pre>
   +------+------+---
   |polje0|polje1|...
   +------+------+---
    |
   +---------+  +------------+ +------------+
   |         |--|potpotpolje0|-|potpotpolje1| - ...
   |potpolje0|  +------------+ +------------+
   |         |
   |         |  +-----------------+ +-----------------+
   |         |--|sekundarno polje0|-|sekundarno polje1| - ...
   |         |  +-----------------+ +-----------------+
   +---------+
    |
   ...     ...

   </pre>
   @param rezance Rezanac koji se parsira.
   @return Vektor koji sadrzi polja sa svojim vektorima potpolja.
  */
	public static Vector unpackRezance(String rezance) {
		Vector fieldVect = new Vector();
//    rezance = new String(rezance.substring(rezance.indexOf("\36")));
		parseSubfieldLine(rezance, fieldVect);
    int len = fieldVect.size();
    for (int i = 0; i < len; i++)
    {
      Field f = (Field)fieldVect.elementAt(i);
      String fName = f.getName();
      if (fName.equals("996") || fName.equals("997") || fName.equals("998"))
      {
        Vector subs = f.getSubfields();
        int subsLen = subs.size();
        for (int j = 0; j < subsLen; j++)
        {
          Subfield s = (Subfield)subs.elementAt(j);
          String sName = s.getName();
          Vector subSubfieldsList = Environment.getFs().getSubSubfieldList(fName, sName);
          if (subSubfieldsList != null) // subSubfieldList ne bi trebalo da bude null
            if (subSubfieldsList.size() != 0)  // ako jeste, onda ne postoji polje fName->sName u okruzenju
            {
               String subSubfieldContent = s.getContent();
               s.setContent("");
               while (subSubfieldContent != null)
               {
                 int bsPos = -1;
                 if ((bsPos = subSubfieldContent.indexOf("\\")) == -1)
                 {
                   if (!subSubfieldContent.equals("")) { // ako nije prazan string
                     // ovaj if sam dodao ja (Minja)
                     String ssfName = new String(subSubfieldContent.substring(0, 1));
                     String ssfCont = new String(subSubfieldContent.substring(1));
                     Subfield subSubfield = new Subfield(ssfName, ssfCont);
                     s.addSubsubfield(subSubfield);
                   } // if
                   subSubfieldContent = null;
                 }
                 else
                 {
                   String ssfName = new String(subSubfieldContent.substring(0, 1));
                   String ssfCont = new String(subSubfieldContent.substring(1, bsPos));
                   Subfield subSubfield = new Subfield(ssfName, ssfCont);
                   subSubfieldContent = subSubfieldContent.substring(bsPos + 1);
                   s.addSubsubfield(subSubfield);
                 }
               }
            }
        }
      }
    }
		return fieldVect;
	}

  /** Parsira rezanac na vektor polja sa potpoljima, gde svako potpolje ima vektor potpotpolja i sekundarnih polja,
   prema definiciji iz Unimarc formata.
   @param line Rezanac koji se parsira.
   @param vect Vektor u koji se smestaju polja sa svojim vektorima potpolja.
  */
  private static void parseSubfieldLine(String line, Vector vect) {
    StringTokenizer lineTokenizer = new StringTokenizer(line, "\36", false);
    boolean isS1PubType = false;
    boolean isNOPubType = false;
    while (lineTokenizer.hasMoreTokens())  {
      String oneField = new String(lineTokenizer.nextToken());
      StringTokenizer fieldTokenizer = new StringTokenizer(oneField.substring(5, oneField.length()), "\37", false);
      String field = new String(oneField.substring(0, 3));
      String indicator = new String(oneField.substring(3, 5));
      // za polje 421... - obrada sekundarnog polja
      Field f = new Field(field, indicator.substring(0, 1), indicator.substring(1));
      if ((field.equals("421") || field.equals("423") || field.equals("464") || field.equals("469")) && !isS1PubType && !isNOPubType) {
          String tmp = oneField.substring(7, oneField.length());
          while (tmp != null)  {
            Subfield s = new Subfield("1", "");
            f.addSubfield(s);
            int sepPos = tmp.indexOf("\37" + "1");
            if (sepPos == -1)  {
                parseSubfieldLine(tmp, s.getSecFieldInVect());
                tmp = null;
            } else  {
                parseSubfieldLine(tmp.substring(0, sepPos), s.getSecFieldInVect());
                tmp = tmp.substring(sepPos + 2, tmp.length());
            }
          }
      } else  {
          while (fieldTokenizer.hasMoreTokens()) {
              String IDContent = fieldTokenizer.nextToken();
              String fieldID = IDContent.substring(0, 1);
              String subField = field + fieldID;
              String content = IDContent.substring(1, IDContent.length());
              if (subField.equals("000a")) {
                isS1PubType = content.substring(0, 3).equals("004");
                isNOPubType = content.substring(0, 3).equals("007");
              }
              Subfield sub = new Subfield(fieldID, content);
              f.addSubfield(sub);
          }// while
      }// else
      vect.addElement(f);
    }// while
  }

  /** Vraca Vektor stringova u kojima je spisak polja sa potpoljima koja nisu popunjena, a treba.
      Ako je vektor prazan, znaci da je zapis OK.
    */
  public static Vector checkManFields(Vector fields) {
    Vector v = new Vector();
    Vector subSubfield = new Vector();
    Vector errors = new Vector();
    int i = 0;
    while (i < fields.size()) {
      Field f = (Field)fields.elementAt(i);
      String fName = f.getName();
      Vector subfields = f.getSubfields();
      collectSubfields(fName, subfields, v);
      i++;
    }
    Vector v1 = Environment.getLib().getManLESubfields();
    int j = 0;
    while (j < v1.size()) {
      boolean nasao = false;
      String field = ((LESubfield)v1.elementAt(j)).getField();
      String fieldsubfi = field + ((LESubfield)v1.elementAt(j)).getSubfield(field);
      if (v.indexOf(fieldsubfi) == -1) {
        errors.addElement(fieldsubfi);
      }
      j++;
    }
    return compressSubfields(errors);
  }


  private static void collectSubfields(String fieldName, Vector subfields, Vector v) {
    int j = 0;
    while (j < subfields.size()) {
      Subfield sif = (Subfield)subfields.elementAt(j);
      String sfname = sif.getName();
      // potpolja
      if (Environment.getFs().containsSubSubfields(fieldName, sfname)) {
        // ako potpolje sadrzi potpotpolja onda sastavljamo u vektor v
        // polje i to potpolje
        Vector subSubfield = sif.getSubsubfields();
        Subfield ssf;
        int k = 0;
        while (k < subSubfield.size()) {
          ssf = (Subfield)subSubfield.elementAt(k);
          if (!ssf.getContent().equals(""))
            // samo ako ne postoji vec u vektoru
            if (v.indexOf(fieldName+sif.getName()) == -1)
              v.addElement(fieldName+sif.getName());
            k++;
        }
      } else if (Environment.getFs().containsSecondaryFields(fieldName)) {
        // sekundarna polja
        Vector secFields = sif.getSecFieldInVect();
        if (secFields.size() > 0) {
          Field secF = (Field)secFields.elementAt(0);
          Vector potpolja = secF.getSubfields();
          for (int i = 0; i < potpolja.size(); i++) {
            Subfield scsf = (Subfield)potpolja.elementAt(i);
            // proveravamo sadrzaj sekundarnih potpolja
            if (!scsf.getContent().equals(""))
              // samo ako ne postoji vec u vektoru
              if (v.indexOf(fieldName+sif.getName()) == -1)
                v.addElement(fieldName+sif.getName());
          } // for
        } // if
      } else {
        // obicna potpolja
        if (!sif.getContent().equals(""))
          v.addElement(fieldName+sif.getName());
      }
      j++;
    }
  }

   /** Kompresuje vektor tako da nemam npr. 100a, 100b i 100c, vec
     100abc.
   */
  public static Vector compressSubfields(Vector v) {
    Vector retVal = new Vector();
    String prevField = "";
    String subfield = "";
    if (v.size() > 0)
      prevField = ((String)v.elementAt(0)).substring(0,3);
    for (int i = 0; i < v.size(); i++) {
      String s = ((String)v.elementAt(i)).substring(0,3);
      if (!s.equals(prevField)) {
        retVal.addElement(prevField+subfield);
        prevField = s;
        subfield = ((String)v.elementAt(i)).substring(3);
      } else
        subfield = subfield + ((String)v.elementAt(i)).substring(3);
    }
    if (!prevField.equals(""))
      retVal.addElement(prevField+subfield);
    return retVal;
  }
  
  /**
   * Returns the content of the first given subfield, or <code>null</code> if
   * the subfield is not present.
   * 
   * @param fields The vector of fields
   * @param sfName The four-character name of the subfield
   * @return the content of the first given subfield, or <code>null</code> if
   * the subfield is not present
   */
  public static String getSubfieldContent(Vector fields, String sfName) {
    if (sfName.length() != 4)
      return null;
    String fn = sfName.substring(0, 3);
    String sfn = sfName.substring(3);
    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field)fields.elementAt(i);
      if (f.getName().equals(fn)) {
        for (int j = 0; j < f.getSubfields().size(); j++) {
          Subfield sf = (Subfield)f.getSubfields().elementAt(j);
          if (sf.getName().equals(sfn))
            return sf.getContent();
        }
      }
    }
    return null;
  }

}
