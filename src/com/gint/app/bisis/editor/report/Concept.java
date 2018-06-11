package com.gint.app.bisis.editor.report;

import java.util.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.util.string.StringUtils;
import com.gint.util.string.UnimarcConverter;


/** Concept -- klasa za generisanje koncepata kataloskih listica.
    @author Tatjana Zubic tanja@uns.ns.ac.yu
    @version 1.0
  */
public class Concept {

  public Concept() {
    conv = new UnimarcConverter();
  }



  public String addIntField(String ulaz) {
    // ako je prvi sadrzaj ne stavljamo nista
    // ako je zadnji znak u stringu bio . ne stavljam . -  nego -

    if (!ulaz.equals("") /*&& !ulaz.equals("\n")*/)  {
      if (ulaz.endsWith("."))
        ulaz += " - ";
      else  {
        ulaz += ". - ";
      }
    }
    return ulaz;
  }



  public Vector getField(Vector fields, String fName, String ind1, String ind2) {

    Vector retVal = new Vector();

    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field) fields.elementAt(i);
      
      if (f.getName().equals(fName) && (ind1.equals(" ") || f.getInd1().equals(ind1))
          && (ind2.equals(" ") || f.getInd2().equals(ind2))) {
            retVal.addElement(f);
      }
    }
    return retVal;
  }

  public Vector getFieldMore(Vector fields, String fName, String ind1, String ind2) {

    Vector retVal = new Vector();

    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field) fields.elementAt(i);
      if (f.getName().equals(fName) && (ind1.equals(" ") || ind1.indexOf(f.getInd1()) != -1)
          && (ind2.equals(" ") || ind2.indexOf(f.getInd2()) != -1)) {
            retVal.addElement(f);
      }
    }
    return retVal;
  }


  public String po(Vector fields){
  	ReportFields rf = new ReportFields();
  	String po="";
  	Vector v = new Vector();
  	boolean prvi=true;
  	if ((v = getField(fields, "610", " ", " ")).size() > 0) {
  		for (int i=0; i < v.size(); i++) {	
  		if (!(po = rf.getFieldContent610((Field)v.elementAt(0),"a")).equals("")) {
  		
  		if (prvi)
  			prvi=false;
  		else
  			po+="," + po;
  		}
  			
  		}
  	}
  	
  	return po;
  	
  }

  public String odrednica(Vector fields) {

    ReportFields rf = new ReportFields();
    String odrednica = new String("");
    String pom = new String("");
    Vector v = new Vector();


   if  ((v = getField(fields, "700", " ", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent700((Field)v.elementAt(0),"adbcf")).equals("")) {
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "700";
        return "\n" + odrednica;
      }
   }
   else if ((v = getField(fields, "710", " ", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent710((Field)v.elementAt(0),"abghcdfe")).equals("")) {   // postoji uslovna interpunkcija
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "710";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "720", " ", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent720((Field)v.elementAt(0),"af")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "720";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "532", "1", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent532((Field)v.elementAt(0),"a")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "532";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "500", "1", "1")).size() > 0) {
      if (!(odrednica = rf.getFieldContent500((Field)v.elementAt(0),"abhiklm")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "500";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "503", "1", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent503((Field)v.elementAt(0),"ajb")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "503";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "200", "1", " ")).size() > 0 && !rf.getFieldContent200((Field)v.elementAt(0),"a").equals("") ){
      odr = "200";
    }
    else if ((v = getField(fields, "532", /*"1"*/" ", " ")).size() > 0) {
        if (!(odrednica = rf.getFieldContent532((Field)v.elementAt(0),"a")).equals("")){
          odrednica = ReportUtilities.toUpper(odrednica, -1);
          odrednica = ReportUtilities.toBold(odrednica, -1);
          odr = "532";
          return "\n" + odrednica;
        }
      }
    else return com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOHEADWORD");

   return odrednica;
  }


  public String odrednicaBIB(Vector fields) {
    ReportFields rf = new ReportFields();
    String odrednica = new String("");
    String pom = new String("");
    Vector v = new Vector();


   if  ((v = getField(fields, "700", " ", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent700((Field)v.elementAt(0),"adbcf")).equals("")) {
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "700";
        return "\n" + odrednica;
      }
      else if  ((v = getField(fields, "701", " ", " ")).size() > 0) {
        if (!(odrednica = rf.getFieldContent700((Field)v.elementAt(0),"adbcf")).equals("")) {
          odrednica = ReportUtilities.toUpper(odrednica, -1);
          odrednica = ReportUtilities.toBold(odrednica, -1);
          odr = "701";
          return "\n" + odrednica;
        }
        else if  ((v = getField(fields, "702", " ", " ")).size() > 0) {
          if (!(odrednica = rf.getFieldContent700((Field)v.elementAt(0),"adbcf")).equals("")) {
            odrednica = ReportUtilities.toUpper(odrednica, -1);
            odrednica = ReportUtilities.toBold(odrednica, -1);
            odr = "702";
            return "\n" + odrednica;
          }
        }
      }
      else if  ((v = getField(fields, "702", " ", " ")).size() > 0) {
        if (!(odrednica = rf.getFieldContent700((Field)v.elementAt(0),"adbcf")).equals("")) {
          odrednica = ReportUtilities.toUpper(odrednica, -1);
          odrednica = ReportUtilities.toBold(odrednica, -1);
          odr = "702";
          return "\n" + odrednica;
        }
      }
   }
   else if ((v = getField(fields, "710", " ", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent710((Field)v.elementAt(0),"abghcdfe")).equals("")) {   // postoji uslovna interpunkcija
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "710";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "720", " ", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent720((Field)v.elementAt(0),"af")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "720";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "532", "1", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent532((Field)v.elementAt(0),"a")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "532";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "500", "1", "1")).size() > 0) {
      if (!(odrednica = rf.getFieldContent500((Field)v.elementAt(0),"abhiklm")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "500";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "503", "1", " ")).size() > 0) {
      if (!(odrednica = rf.getFieldContent503((Field)v.elementAt(0),"ajb")).equals("")){
        odrednica = ReportUtilities.toUpper(odrednica, -1);
        odrednica = ReportUtilities.toBold(odrednica, -1);
        odr = "503";
        return "\n" + odrednica;
      }
    }
    else if ((v = getField(fields, "200", "1", " ")).size() > 0 && !rf.getFieldContent200((Field)v.elementAt(0),"a").equals("") ){
      odr = "200";
    }
    else return com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOHEADWORD");

   return odrednica;
  }



  public String zaglavlje(Vector fields) {

    ReportFields rf = new ReportFields();
    String zag = new String("");
    Vector v = new Vector();
    if (odr.equals("200")) {
      if ((v = getField(fields, "200", "1", " ")).size() > 0) {
        if (!(zag = rf.getFieldContent200((Field)v.elementAt(0),"a")).equals("")){
          zag = ReportUtilities.cutWords(zag, 3); // skracujemo na 3 reci
          zag = ReportUtilities.toUpper(zag, 1);
          zag = ReportUtilities.toBold(zag, -1);
          zag = "\n" + zag;
        }
      }
    }
    return zag;
  }

  public String alone532(Vector fields) {

    ReportFields rf = new ReportFields();
    String alone532 = new String("");

    if  (getField(fields, "700", " ", " ").size() > 0 || getField(fields, "710", " ", " ").size() > 0  || getField(fields, "720", " ", " ").size() > 0 || getField(fields, "503", " ", " ").size() > 0) {
      Vector v = getField(fields, "532", " ", " ");
      for (int i = 0; i < v.size(); i++) {
        String val = rf.getFieldContent532((Field)v.elementAt(0),"a");
        if (!val.equals(""))
          alone532 = "\n[" + val + "]";
      }
    }
    return alone532;
  }

  // glavni opis
  public String glavniOpis(Vector fields) {

    ReportFields rf = new ReportFields();
    String glOpis = new String("");
    String val = new String("");
    Vector v = new Vector();


    if ((v = getField(fields, "200", "1", " ")).size() > 0) {
      if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"@")).equals("")){
        if (odr.equals("200")) {
          glOpis = ReportUtilities.toUpper(glOpis, 1);
          //glOpis = ReportUtilities.toBold(glOpis, -1);
        }
        glOpis = "\n&nbsp;&nbsp;&nbsp;" + glOpis;
         // else if odr == "" -> greska = nema odrednice
      }
    }
    else if ((v = getField(fields, "200", " ", " ")).size() > 0) {
      if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"@")).equals("")){
        glOpis = "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"abcdefghiv");
      }
    }
    if ((v = getField(fields, "205", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent205((Field)v.elementAt(0),"adfgb")).equals(""))
        glOpis = addIntField(glOpis) + val;
    }
    if ((v = getField(fields, "207", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent207((Field)v.elementAt(0),"a")).equals(""))
        glOpis = addIntField(glOpis) + val;
    }
    if ((v = getField(fields, "210", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
        glOpis = addIntField(glOpis) + val;
    }
    if ((v = getField(fields, "215", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"acde")).equals(""))
        glOpis = addIntField(glOpis) + val;
    }
    v = getField(fields, "225", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent225((Field)v.elementAt(i),"adefhivx")).equals("")) {
        glOpis = (i==0) ? addIntField(glOpis) : glOpis;
        glOpis += " (" + val + ") ";
      }
    }
    if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
      glOpis += ".";
    return glOpis;
  }

  /** Opis nadredjene serijske publikacije za clanak
      veza preko 011
  */
  public String opisSerijske(Vector fields) {
    ReportFields rf = new ReportFields();
    String opis = new String("");
    String val = new String("");
    Vector v = new Vector();
    Vector v1 = new Vector();


    if ((v = getField(fields, "200", "0", " ")).size() > 0) {

      // postoji 200 sa indikatorom 0 -> ispitati postojanje 532
      if ((v1 = getField(fields, "532", " ", " ")).size() > 0)  {
      	// ispisati sadrzaj 532a. - 011a
        if (!(val = rf.getFieldContent532((Field)v1.elementAt(0),"a")).equals("")) {
          opis += val;
        }

      }
      else  {
        if (!(val = rf.getFieldContent200((Field)v.elementAt(0),"aefhi")).equals("")) {
          opis += val;
        }
      }
    }
    else if ((v = getField(fields, "200", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent200((Field)v.elementAt(0),"aefhi")).equals("")){
        opis += val;
      }
    }

        if ((v1 = getField(fields, "011", " ", " ")).size() > 0)  {
          if (!(val = rf.getFieldContent011((Field)v1.elementAt(0),"e")).equals("")) {
            opis += (opis.equals("") ? "" : ". - ISSN ") + val;
          }
        }

/*    String godina = "";
    if ((v = getField(fields, "210", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"d")).equals("")){
        godina = val;
      }
    }

    if ((v = getField(fields, "215", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"i")).equals("")){
        opis += val;
      }
      opis += godina;
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"h")).equals("")){
        opis += val;
      }
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"ac")).equals("")){
        opis = addIntField(opis) + val;
      }
    }
    else
      opis += godina;
*/
    return opis;
  }

  /** Opis nadredjene monografske (ili serijske koja nema 011) publikacije za clanak
      veza preko 464
  */
  public String opisMonografske(Vector fields) {
    ReportFields rf = new ReportFields();
    String opis = new String("");
    String val = new String("");
    Vector v = new Vector();
    Vector v1 = new Vector();
    boolean tip = true;

    // posto ne znamo da li je monografska ili serijska bez 011 ispitujemo 000a
    // !!!!! a ako  nije uradjen save ?????
    if ((v = getField(fields, "000", " ", " ")).size() > 0) {
      val = rf.getFieldContent010((Field)v.elementAt(0),"a");
      val = val.substring(0,3);
      if (val.equals("001") || val.equals("002") || val.equals("003")) {
         tip = true; // monografske publikacije
      }
      else if (val.equals("004") || val.equals("005")) {
         tip = false; // serijske publikacije
      }
      else {
         tip = true; // inace mada se ovo mozda moze tretirati kao greska
      }
    }


    if ((v = getField(fields, "200", "0", " ")).size() > 0) {
      // postoji 200 sa indikatorom 0 -> ispitati postojanje 532 i 500
      if ((v1 = getField(fields, "532", " ", " ")).size() > 0)  {
        if (!(val = rf.getFieldContent532((Field)v1.elementAt(0),"a")).equals("")){
          opis += val;
        }
        if ((v1 = getField(fields, "500", " ", " ")).size() > 0)  {
          if (!(val = rf.getFieldContent532((Field)v1.elementAt(0),"a")).equals("")){
            opis += (opis.equals("") ? "" : " ") + val;
          }
        }
      }
      else if (!(val = rf.getFieldContent200((Field)v.elementAt(0),"a")).equals("")){
          opis += val;
      }
      if (!(val = rf.getFieldContent200((Field)v.elementAt(0),"f")).equals("")){
          opis += val;
      }

    }
    else if ((v = getField(fields, "200", " ", " ")).size() > 0) {
      if (!(val = rf.getFieldContent200((Field)v.elementAt(0),"acefhi")).equals("")){
        opis += val;
      }
    }

    if (tip) {
      if ((v = getField(fields, "205", " ", " ")).size() > 0)  {
        if (!(val = rf.getFieldContent205((Field)v.elementAt(0),"a")).equals("")){
          opis = addIntField(opis) + val;
        }
      }
      if ((v = getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acd")).equals("")){
          opis = addIntField(opis) + val;
        }
      }
    }

/*    if ((v = getField(fields, "215", " ", " ")).size() > 0) {
      if (!tip) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"i")).equals("")){
          opis += val;
        }
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"h")).equals("")){
          opis += val;
        }
        opis += godiste;
      }
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"g")).equals("")){
        opis += val;
      }
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"ac")).equals("")){
        opis = addIntField(opis) + val;
      }
    }
*/
    return opis;
  }




  public String napomene(Vector fields) {
    ReportFields rf = new ReportFields();
    String napomene = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "300", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "314", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "320", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent200((Field)v.elementAt(i),"a")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "321", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent321((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "322", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "323", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "324", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "326", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "327", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent200((Field)v.elementAt(i),"a")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "328", " ", " ");   //samo a
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"a")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "330", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    if (!napomene.equals("") && !napomene.endsWith("."))
      napomene += ".";
    return napomene;

  }

  public String napomeneSer(Vector fields) {
    ReportFields rf = new ReportFields();
    String napomene = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "326", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    v = getField(fields, "300", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent300((Field)v.elementAt(i),"@")).equals(""))
          napomene = (napomene.equals("") ? "\n\n" : addIntField(napomene)) + val;
    }

    if (!napomene.equals("") && !napomene.endsWith("."))
      napomene += ".";
    return napomene;
  }

  public String dsTitula(Vector fields) {
    ReportFields rf = new ReportFields();
    String titula = new String("");
    String val = new String("");

    Vector v = getField(fields, "328", " ", " ");
    for (int i=0; i < v.size(); i++) {
       if (!(val = rf.getFieldContent328((Field)v.elementAt(i),"f")).equals("")) {
         titula = ", " + val;
         return titula;
       }
    }
    return titula;
  }


  public String dsOdbrana(Vector fields) {
    ReportFields rf = new ReportFields();
    String odbrana = new String("");
    String val = new String("");

    Vector v = getField(fields, "328", " ", " ");
    for (int i=0; i < v.size(); i++) {
       if (!(val = rf.getFieldContent328((Field)v.elementAt(i),"d")).equals("")) {
         odbrana = "\n\n" + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_DEFEND") + " " + ReportUtilities.convertDate(val);
         return odbrana;
       }
    }
    return odbrana;
  }

  public String dsPromocija(Vector fields) {
    ReportFields rf = new ReportFields();
    String promocija = new String("");
    String val = new String("");

    Vector v = getField(fields, "328", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent328((Field)v.elementAt(i),"e")).equals("")) {
        promocija = com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_PROMOTION") + " " + ReportUtilities.convertDate(val);
        return promocija;
      }
    }
    return promocija;
  }

  // UBACITI PREPOZNAVANJE MENTORA PREKO SIFRE
  public String dsKomisija(Vector fields) {
    ReportFields rf = new ReportFields();
    String komisija = new String("");
    String val = new String("");

    Vector v = getField(fields, "702", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"ab")).equals("")) { //dodati i 4 sa desifrovanjem
        if (i == 0)
          komisija += "\n" + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_MENTOR") + " ";
        else if (i == 1)
          komisija += "\n" + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_DEFENDBOARD") + " ";
        else
          komisija += "\n          ";
        komisija += val;
      }
    }
    return komisija;
  }



  public String brojISBN(Vector fields) {
    ReportFields rf = new ReportFields();
    String brISBN = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "010", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        brISBN += (brISBN.equals("") ? "\n\n" : ". - ") + "ISBN " + val + " ";
      }
    }
    return brISBN;
  }
  public String brojISBNStampa(Vector fields) {
    ReportFields rf = new ReportFields();
    String brISBN = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "010", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        brISBN += (brISBN.equals("") ? "\n" : ". - ") + "ISBN " + val + " ";
      }
    }
    return brISBN;
  }
  public String brojISSN(Vector fields) {
    ReportFields rf = new ReportFields();
    String brISSN = new String("");
    Vector v = new Vector();

    if ((v = getField(fields, "011", " ", " ")).size() > 0) {
      if (!(brISSN = rf.getFieldContent011((Field)v.elementAt(0),"e")).equals(""))
        brISSN = "\n\nISSN " + brISSN;
    }
    return brISSN;
  }

  public String specGodista(Vector fields) {
    ReportFields rf = new ReportFields();
    String specGod = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "997", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent997((Field)v.elementAt(i),"jkm")).equals(""))
        specGod += (specGod.equals("") ? "\n\n" : "\n") + val;
    }
    return specGod;
  }


  public String prilozi(Vector fields) {
    ReportFields rf = new ReportFields();
    String prilozi = new String("");
    String val = new String("");
    Vector v = new Vector();
    boolean prvi = true;

    v = getField(fields, "421", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent421((Field)v.elementAt(i),"@")).equals(""))
        prilozi += (prilozi.equals("") ? "\n-- " : "\n-- ") + val;
    }
    v = getField(fields, "469", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      Field f = (Field)v.elementAt(i); //polje 469
      if (!(val = rf.getFieldContent469(f,"@")).equals(""))  {
        if (prvi) {
           prilozi += "\n-- " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_CONTENT") + " ";
           prvi = false;
        }
        else  {
          if (f.getInd2().equals("0"))
             prilozi += " ; ";
          else if (f.getInd2().equals("1"))
             prilozi += ". ";
          else if (f.getInd2().equals("2"))
             prilozi += ". - ";
          else if (f.getInd2().equals("3"))
             prilozi += " : ";
        }
        prilozi += val;
      }
    }
    return prilozi;
  }


  public String brojUDC(Vector fields) {
    ReportFields rf = new ReportFields();
    String brUDC = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "675", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        brUDC += (brUDC.equals("") ? "" : "\n") + val;
      }
    }
    //if (!brUDC.equals("")) {
    // udc = true;
    // if (rim || arap || predm)
    //   brUDC = "\n" + brUDC;
    // else
    //  brUDC = "\n\n" + brUDC;
   // }
    return brUDC;
  }
  public String brojUDCOsnovniListic(Vector fields) {
    ReportFields rf = new ReportFields();
    String brUDC = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "675", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        brUDC += (brUDC.equals("") ? "" : "\n"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;") + val;
      }
    }
    
    return brUDC;
  }
  public String brojUDC1(Vector fields) {
    ReportFields rf = new ReportFields();
    String brUDC = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "675", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        brUDC += (brUDC.equals("") ? "" : "<BR>\n"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;") + val;
      }
    }
        
        
    
    return brUDC;
  }
  public String toNS(Vector fields) {
    ReportFields rf = new ReportFields();
    String to = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "999", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        to += (to.equals("") ? "" : "\n") + val;
      }
    }
   // if (!brUDC.equals("")) {
     // udc = true;
      //if (rim || arap || predm)
       // brUDC = "\n" + brUDC;
     // else
      //  brUDC = "\n\n" + brUDC;
    //}
    return to;
  }
  
  public String datumNS(Vector fields) {
    ReportFields rf = new ReportFields();
    String dat = new String("");
    String val = new String("");
    Vector v = new Vector();

    v = getField(fields, "998", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent010((Field)v.elementAt(i),"a")).equals(""))  {
        dat += (dat.equals("") ? "" : "\n") + val;
      }
    }
   // if (!brUDC.equals("")) {
     // udc = true;
      //if (rim || arap || predm)
       // brUDC = "\n" + brUDC;
     // else
      //  brUDC = "\n\n" + brUDC;
    //}
    return dat;
  }

  public String arapTrejsing(Vector fields) {
    ReportFields rf = new ReportFields();
    String arTrejs = new String("");
    String val = new String("");
    Vector v = new Vector();
    int br = 1;

    if (getField(fields, "200", "1", " ").size() > 0 && !odr.equals("")) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_MRTITLE");
      br++;
    }

    if (getField(fields, "510", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_CRTITLE");
      br++;
    }

    if (getField(fields, "512", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_ORTITLE");
      br++;
    }

    if (getField(fields, "513", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_RTITLESHEAD");
      br++;
    }

    if (getField(fields, "514", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_RTITLEABOVETEXT");
      br++;
    }

    if (getField(fields, "515", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_CURRTITLE");
      br++;
    }

    if (getField(fields, "516", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_HRBTTITLE");
      br++;
    }

    if (getField(fields, "517", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_SSLTTITLE");
      br++;
    }

    if (getField(fields, "540", "1", " ").size() > 0) {
      arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_ADDTITLE");
      br++;
    }

    v = getField(fields, "701", "1", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"abcdf")).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + val;
        br++;
      }
    }

    v = getField(fields, "702", "1", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"abcdf")).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + val;
        br++;
      }
    }

    v = getField(fields, "711", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"abcdefgh")).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + val;
        br++;
      }
    }

    v = getField(fields, "712", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"abcdefgh")).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + val;
        br++;
      }
    }

    v = getField(fields, "721", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent720((Field)v.elementAt(i),"af")).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + val;
        br++;
      }
    }

    v = getField(fields, "722", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent720((Field)v.elementAt(i),"af")).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + br + ". " + val;
        br++;
      }
    }

    v = getField(fields, "423", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent423((Field)v.elementAt(i),"@", br)).equals("")) {
        arTrejs += (arTrejs.equals("") ? "\n\n" : " ") + val;
        br += rf.getBr200();
      }
    }


    if (!arTrejs.equals(""))
      arap = true;
    return arTrejs;
  }

  public String getSFContent(Field currField, String pp) {

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (sf.getName().equals(pp))
        return conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
    }
    return "";
  }


  public String rimTrejsing(Vector fields) {         // treba brojeve pretvoriti u rimske
    ReportFields rf = new ReportFields();
    String rimTrejs = new String("");
    Vector v = new Vector();
    Vector v1 = new Vector();
    String val = new String("");
    String val1 = new String("");
    boolean first = true;
    int br = 1;

    v = getField(fields, "900", " ", " ");
    v1 = getField(fields, "700", " ", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent700((Field)v1.elementAt(i),"@")).equals("")) {
          if (first) {
            rimTrejs += (arap) ? "\n" : "\n\n";
            first = false;
          }
          rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
          br++;
        }
      }
    }

    v = getField(fields, "901", "1", " ");
    v1 = getField(fields, "701", "1", " ");
    if (v.size() > 0 && v1.size() > 0)  {
     for (int i = 0; i < v.size(); i++) {
       for (int j = 0;j < v1.size(); j++)  {
         if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent700((Field)v1.elementAt(j),"@")).equals("")) {
            String val9016 = getSFContent((Field)v.elementAt(i),"6");
            String val7016 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9016.equals("") && val9016.equals(val7016)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }


    v = getField(fields, "902", "1", " ");
    v1 = getField(fields, "702", "1", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent700((Field)v1.elementAt(j),"@")).equals("")) {
            String val9026 = getSFContent((Field)v.elementAt(i),"6");
            String val7026 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9026.equals("") && val9026.equals(val7026)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }


    // po savetu FF samo a
    v = getField(fields, "910", " ", " ");
    v1 = getField(fields, "710", " ", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent710((Field)v1.elementAt(i),"a")).equals("")) {
          if (first) {
            rimTrejs += (arap) ? "\n" : "\n\n";
            first = false;
          }
          rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
          br++;
        }
      }
    }

    // po savetu FF samo a
    v = getField(fields, "911", "1", " ");
    v1 = getField(fields, "711", "1", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent710((Field)v1.elementAt(j),"a")).equals("")) {
            String val9116 = getSFContent((Field)v.elementAt(i),"6");
            String val7116 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9116.equals("") && val9116.equals(val7116)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getField(fields, "912", "1", " ");
    v1 = getField(fields, "712", "1", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent710((Field)v1.elementAt(j),"a")).equals("")) {
            String val9126 = getSFContent((Field)v.elementAt(i),"6");
            String val7126 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9126.equals("") && val9126.equals(val7126)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    v = getFieldMore(fields, "960", "13", " ");
    v1 = getFieldMore(fields, "600", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent600((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent600((Field)v1.elementAt(j),"@")).equals("")) {
            String val9606 = getSFContent((Field)v.elementAt(i),"6");
            String val6006 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9606.equals("") && val9606.equals(val6006)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getField(fields, "961", " ", " ");
    v1 = getField(fields, "601", " ", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent601((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent601((Field)v1.elementAt(j),"a")).equals("")) {
            String val9616 = getSFContent((Field)v.elementAt(i),"6");
            String val6016 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9616.equals("") && val9616.equals(val6016)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getFieldMore(fields, "962", "13", " ");
    v1 = getFieldMore(fields, "602", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent602((Field)v.elementAt(i),"af")).equals("") && !(val1 = rf.getFieldContent602((Field)v1.elementAt(j),"af")).equals("")) {
            String val9626 = getSFContent((Field)v.elementAt(i),"6");
            String val6026 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9626.equals("") && val9626.equals(val6026)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    //??
    v = getFieldMore(fields, "965", "13", " ");
    v1 = getFieldMore(fields, "605", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent605((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent605((Field)v1.elementAt(j),"@")).equals("")) {
            String val9656 = getSFContent((Field)v.elementAt(i),"6");
            String val6056 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9656.equals("") && val9656.equals(val6056)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getFieldMore(fields, "966", "13", " ");
    v1 = getFieldMore(fields, "606", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
            String val9666 = getSFContent((Field)v.elementAt(i),"6");
            String val6066 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9666.equals("") && val9666.equals(val6066)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getFieldMore(fields, "967", "13", " ");
    v1 = getFieldMore(fields, "607", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
            String val9676 = getSFContent((Field)v.elementAt(i),"6");
            String val6076 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9676.equals("") && val9676.equals(val6076)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getFieldMore(fields, "968", "13", " ");
    v1 = getFieldMore(fields, "608", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
            String val9686 = getSFContent((Field)v.elementAt(i),"6");
            String val6086 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9686.equals("") && val9686.equals(val6086)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    // po savetu FF samo a
    v = getFieldMore(fields, "969", "13", " ");
    v1 = getFieldMore(fields, "609", "13", " ");
    if (v.size() > 0 && v1.size() > 0)  {
      for (int i = 0; i < v.size(); i++) {
        for (int j = 0;j < v1.size(); j++)  {
          if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
            String val9696 = getSFContent((Field)v.elementAt(i),"6");
            String val6096 = getSFContent((Field)v1.elementAt(j),"6");
            if ((!val9696.equals("") && val9696.equals(val6096)) || (v.size() == 1 && v1.size() == 1)) {
              if (first) {
                rimTrejs += (arap) ? "\n" : "\n\n";
                first = false;
              }
              rimTrejs += "" + StringUtils.arabicToRoman(br) + ". " + val + " V. " + val1;
              br++;
            }
          }
        }
      }
    }

    if (!rimTrejs.equals(""))
      rim = true;
    return rimTrejs;
  }


  public String predmTrejsing(Vector fields) {
    ReportFields rf = new ReportFields();
    String prTrejs = new String("");
    String val = new String("");
    Vector v = new Vector();
    String slova = new String("abcdefghijklm");
    boolean first = true;
    int br = 0;

    v = getFieldMore(fields, "600", "13", " ");      // indikator ne sme biti 0
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent600((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getField(fields, "601", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent601((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getFieldMore(fields, "602", "13", " ");      // indikator ne sme biti 0, moze biti 1,2,3!!!!!
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent602((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getFieldMore(fields, "605", "13", " ");      // indikator ne sme biti 0, moze biti 1,2,3!!!!!
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent605((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getFieldMore(fields, "606", "13", " ");      // indikator ne sme biti 0, moze biti 1,2,3!!!!!
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getFieldMore(fields, "607", "13", " ");      // indikator ne sme biti 0, moze biti 1,2,3!!!!!
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getFieldMore(fields, "608", "13", " ");      // indikator ne sme biti 0, moze biti 1,2,3!!!!!
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }

    v = getFieldMore(fields, "609", "13", " ");      // indikator ne sme biti 0, moze biti 1,2,3!!!!!
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals("")) {
        if (first) {
         prTrejs += (arap || rim) ? "\n" : "\n\n";
         first = false;
        }
        prTrejs += slova.substring(br,br+1) + ") " + val + " ";
        br++;
      }
    }
    if (!prTrejs.equals(""))
      predm = true;
    return prTrejs;
  }

  public boolean brojID() {
    return (rim || arap || predm || udc) ? true : false;
  }



  public String odr = new String("");
  public boolean arap = false;
  public boolean rim = false;
  public boolean predm = false;
  public boolean udc = false;
  private UnimarcConverter conv;
} // kraj klase



