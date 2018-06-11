package com.gint.app.bisis.editor.report;

// ako ide po prefiksima onda uvezi ovo
import java.util.Vector;
import java.util.Enumeration;
//import java.util.StringTokenizer;
import com.objectspace.jgl.HashMap;

import java.sql.*;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;

import com.gint.util.sort.*;
import com.gint.util.string.UnimarcConverter;

public class BiltenPrinovljene {

  public BiltenPrinovljene(Connection conn, String datOd, String datDo) {
    this.conn = conn;
    conv = new UnimarcConverter();
    datumOd = datOd;
    datumDo = datDo;
  }

/** Ovo sortira, tako sto podeli niz na dva podniza, a onda ih rekurzivno sortira.
  private static void qsort(int d, int g, ExpandPair[] a) {
    int i, j;
    ExpandPair k;
    // sort jednoelementnog niza
    if (d >= g)
      return;
    // sort dvoelementnog niza
    if (d + 1 == g) {
      if (a[d].getContent().compareTo(a[g].getContent()) > 0) {
        //zameni(d, g, a);
        k    = a[d];
        a[d] = a[g];
        a[g] = k;
      }
      return;
    }
    // svi ostali slucajevi
    //j = podeli(d, g, a);
    j = d;
    for (i = d + 1; i <= g; i++)
      if (a[i].getContent().compareTo(a[d].getContent()) <= 0) {
        //zameni(++j, i, a);
        j++;
        k    = a[j];
        a[j] = a[i];
        a[i] = k;
      }
    //zameni(d, j, a);
    k    = a[d];
    a[d] = a[j];
    a[j] = k;

    qsort(d, j - 1, a);
    qsort(j + 1, g, a);
  }
*/
  /** getBilten - Vraca HTML dokument sa biltenom prinovljenih knjiga
      @param type tip publikacija (monografije, serijske ...)
      @param docIDs vektor odgovarajucih ID brojeva zapisa
      @param docs   vektor odgovarajucih zapisa
      @return HTML dokument
  */
/*  public String getBilten(int type, Vector docIDs, Vector docs) {
    String retVal = "";
    int i=0;
    Vector lines = new Vector();

    retVal = generateHeader(type);

// Ovaj deo vrsi upunjavanje niza za sortiranje pogodaka po izabanom prefiksu (AU,TI ili PY)
// U niz sortNiz stavlja podatke iz izabranog prefiksa za sve rezance
      Format formatTMP = new Format();
      String pref = new String("AU");
      formatTMP.postavi("Autori","AU");

      Vector sortNiz = new Vector();
      for (i=0; i < docs.size(); i++) {
        HashMap prefs = prepareMap(docs.elementAt(i).toString());

        Enumeration enum = prefs.values(pref);
        String str="";
        if (enum.hasMoreElements()) { //enum != null) {
          String tmp = (String)enum.nextElement();
          str += conv.Unimarc2Unicode(tmp);
          while (enum.hasMoreElements()) {
            tmp = (String)enum.nextElement();
            str += "; "+conv.Unimarc2Unicode(tmp);
          }
        }
        sortNiz.add(str);
      }

// U ovom delu puni ExpandPair sa parovima (sadrzaj prefiksa, id zapisa). Zatim se taj niz sortira, i tako
// sortirani niz se prosledjuje kao parametar za izradu bibliografije.

      ExpandPair[] niz = new ExpandPair[docs.size()];
      for (i=0; i < sortNiz.size(); i++) {
        niz[i] = new ExpandPair(sortNiz.elementAt(i).toString(),(int)Integer.parseInt(docIDs.elementAt(i).toString()));
      }

      qsort(0,docs.size()-1,niz);

      for (i = 0; i < docs.size(); i++) {
        try {
          retVal+=generateOne(makeRowContent(niz[i].getCount(),Environment.getReadTs().getDoc(niz[i].getCount())));
        } catch (java.rmi.RemoteException ex) {
        }
      }

    retVal += generateFooter();
    return retVal;
  }
*/

/***odavde****/
  public String getBilten(int type, Vector docIDs, Vector docs) {
    String retVal = "";
    int i=0;
    Vector lines = new Vector();

    retVal = generateHeader(type);
    Format format = Environment.getLib().getFormat();

    Format formatTMP = new Format();
    Vector pref = new Vector();

    String fNaziv="", fNiz="";

    fNaziv="AU";

    fNiz="AUTIPY";

    pref.add("AU");
    pref.add("TI");
    pref.add("PY");

    formatTMP.postavi(fNaziv,fNiz);

    SortableBiblio[] niz=new SortableBiblio[docs.size()];

    for (i=0; i < docs.size(); i++) {
      com.objectspace.jgl.HashMap prefs = prepareMap(docs.elementAt(i).toString());

      Vector sortableVector = new Vector();
      for (int j=0;j<pref.size();j++) {
        Enumeration e = prefs.values((String)pref.elementAt(j));
        String str="";
        if (e.hasMoreElements()) {
          String tmp = (String)e.nextElement();
          str += conv.Unimarc2Unicode(tmp);
          while (e.hasMoreElements()) {
            tmp = (String)e.nextElement();
            str += "; "+conv.Unimarc2Unicode(tmp);
          }
        }
        sortableVector.add(str);
      }
      niz[i]= new SortableBiblio((int)Integer.parseInt(docIDs.elementAt(i).toString()),sortableVector);


     /*// stampanje zbog kontrole
      System.out.println("DOCID="+docIDs.elementAt(i));
      for (int j=0;j<sortableVector.size();j++)
        System.out.println("element vektora "+j+"="+sortableVector.elementAt(j));
      */
    }

    Sorter.qsort(niz);

   /* System.out.println("*** sortirani niz ***");
    for (i = 0; i < docs.size(); i++) {
      System.out.println("DOCID="+niz[i].getID());
      for (int j=0;j<3;j++)
        System.out.println("element vektora "+j+"="+niz[i].getSortItem(j));
    }
  */

    for (i = 0; i < docs.size(); i++) {
      //try {
//        retVal+=generateOne(makeRowContent(niz[i].getCount(),Environment.getReadTs().getDoc(niz[i].getCount())));
        retVal+=generateOne(makeRowContent(niz[i].getID(),Environment.getReadTs().getDoc(niz[i].getID())));
      //} catch (java.rmi.RemoteException ex) {
      //}
    }
    retVal += generateFooter();
    return retVal;
  }

/****dovde****/
  /** generateHeader - generise zaglavlje HTML dokumenta
  */

  // NAPOMENA Treba precizno odrediti sirinu tabele da ne izlazi iz margina

  public String generateHeader(int type) {

    String retVal = "";
    retVal += "<HTML><HEAD></HEAD><BODY BGCOLOR=white TEXT=black>";
//    retVal+="<FONT FACE=\"Verdana, Arial\" SIZE=\"4\"><B>B I L T E N<BR>\n";
//    retVal +="PRINOVLJENIH PUBLIKACIJA</B><BR>\n";
//    retVal+= "<FONT FACE=\"Verdana, Arial\" SIZE=\"3\">"+datumOd+" "+datumDo+"<BR>\n<BR>\n";
    retVal += "<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\" WIDTH=\"100%\">\n";
//    retVal+="<FONT FACE=\"Verdana, Arial\" SIZE=\"2\">";
    return retVal;
  }


  /** Generise vektor sadrzaja celija jedne vrste tabele
       @param docID identifikacioni broj dokumenta
       @param doc rezanac
       @return vektor sadrzaja celija jedne vrste tabele
   */
  public Vector makeRowContent(int docID, String doc) {
    Vector retVal = new Vector();
    Vector v = new Vector();
    ReportFields rf = new ReportFields();
    Concept c = new Concept();
    Catalog cat = new Catalog(docID,doc,"");
    String val, sign, odrednica, glOpis, inv,datum;
    inv = ""; val = ""; sign = ""; odrednica = ""; glOpis = "";
    int i = 0;

    RezanceUtilities ru = new RezanceUtilities();
    fields = RezanceUtilities.unpackRezance(doc);

    int typePubl = cat.getType();

    if (typePubl == 1) {
    // samo za monografske - 001, 002 i 003
      redBroj++;

      v = c.getField(fields, "996", " ", " ");
      sign="";
      inv="";
      while (i < v.size()) {
        if (!(val = rf.getFieldContent996II((Field)v.elementAt(i),"o")).equals("")) {
          // ako sadrzaj ne zadovoljava upit - continue
          datum=BiltenPrinovljeneFrame.datumZaUpit(val.substring(0,val.length()-1));
          if (datum.compareTo(datumOd) >=0 && datum.compareTo(datumDo) <=0) {
            if (!(val = rf.getFieldContent996((Field)v.elementAt(i),"d")).equals("")) {
              if (sign.equals(""))
                sign = val;
              else
                sign += ", "+val;
            }
            if (!(val = rf.getFieldContent996((Field)v.elementAt(i),"f")).equals("")) {
              if (inv.equals(""))
                inv =val;
              else
                inv +=", "+val;
//          break;
            }
          }
        }
        i++;
      }

      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"adeh")).equals(""))
          glOpis =  ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += rf.getFieldContent200((Field)v.elementAt(0),"adeh");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acd")).equals(""))
          glOpis +=  ". - " + val;
      }
      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      if (Environment.getClient() == Environment.SMIP) //** SMIP
        odrednica = c.odrednicaBIB(fields);
      else
      	odrednica = c.odrednica(fields);


      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;

      retVal.addElement(""+redBroj);
      retVal.addElement(glOpis);
      retVal.addElement(sign);
    }
    return retVal;
  }


  /** generateFooter - generise futer HTML dokumenta
  */
  public String generateFooter() {
    String retVal = "";
    retVal += "</TABLE></BODY></HTML>";
    return retVal;
  }


  /** generateOne - Generise jednu vrstu HTML tabele
      @param v vektor koji sadrzi sadrzaje celija tabele
      @return jedna vrsta HTML tabele
  */

  public String generateOne(Vector v) {
    String retVal = "";

    Enumeration e = v.elements();
    if (e.hasMoreElements()) {
      retVal+="<TR><TD>&nbsp;</TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>";

      retVal += "<TR>";
      int i=0;
      while (e.hasMoreElements()) {
        i++;
        String t = (String)e.nextElement();
        if (t.equals("") || t.substring(0,1).equals(" ") || i==1)
            t=t+"&nbsp;";
        if (i!=3)
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Verdana, Arial\" SIZE=\"2\">" + t + "</FONT></TD>";
        else
          retVal += "<TD VALIGN=\"top\" WIDTH=\"10%\"><FONT FACE=\"Verdana, Arial\" SIZE=\"2\">" + t + "</FONT></TD>";
      }
      retVal+="</TR>";
    }
    return retVal;
  }


// Ovo je ako hocemo da pisemo u izabranom formatu

  /** Kreira HashMap objekat sa preslikavanjem prefiks->vrednost za dati rezanac.
      @see prepareList
    */

/*  public HashMap prepareMap(String rezance) {
    HashMap prefixMap = Environment.getWriteTs().getPrefixMap();
    DocumentParser dp = new DocumentParser(prefixMap, "\36", "\37");
    Vector pv = dp.parseDocument(rezance);
    PrefixPair pp = null;
    HashMap hMap = new HashMap(true);
    for (int h = 0; h < pv.size(); h++) {
      pp = (PrefixPair)pv.elementAt(h);
      hMap.add(pp.prefName, pp.value);
    }
    return hMap;
  }*/

  public com.objectspace.jgl.HashMap prepareMap(String rezance) {
    com.objectspace.jgl.HashMap prefixMap = Environment.getWriteTs().getPrefixMap();
    DocumentParser dp = new DocumentParser(prefixMap, "\36", "\37");
    Vector pv = dp.parseDocument(rezance);
    PrefixPair pp = null;
    com.objectspace.jgl.HashMap hMap = new HashMap(true);
    for (int h = 0; h < pv.size(); h++) {
      pp = (PrefixPair)pv.elementAt(h);
      hMap.add(pp.prefName, pp.value);
    }
    return hMap;
  }


  private void formatirajLiniju(String str, Vector lines, int spaces) {
    String blanko= new String("");
    for (int i=0;i<spaces;i++)
      blanko+="&nbsp;";

    if (str.length() > 65) {
      String rez="";
      String pom = new String(str);
      int prelom = 0;

      while (pom.length() > 65) {
        prelom = nadjiPrelom(pom,65);
        rez=pom.substring(0,prelom);
        lines.addElement(blanko+rez);
        pom=pom.substring(prelom+1);
      }
      if (pom.length() != 0)
        lines.addElement(blanko+pom);
    }
    else
      lines.addElement(blanko+str);
  }

  private int nadjiPrelom(String str,int duzina) {
    int lsp=str.lastIndexOf(' ');
    if (lsp > duzina)
      return (nadjiPrelom(str.substring(0,lsp),duzina));
    else
      return lsp;
  }

  private String rightAlign(String in) {
    if (!in.equals("")) {
     String blanko = new String("");
     for (int j=0; j < (80-in.trim().length()); j++)
        blanko += "&nbsp;";
     in = blanko + in;
    }
    return in;
  }


  private Connection conn;
  private String datumOd="";
  private String datumDo="";
  private int redBroj=0;
  private UnimarcConverter conv;
  public HashMap co996v;
  public HashMap co996s;
  public Vector fields = new Vector();
}




