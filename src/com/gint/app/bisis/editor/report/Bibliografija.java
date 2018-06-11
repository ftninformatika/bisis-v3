package com.gint.app.bisis.editor.report;

// ako ide po prefiksima onda uvezi ovo
import java.util.Vector;
import java.util.Enumeration;
import com.objectspace.jgl.HashMap;

import com.gint.util.sort.*;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.Charset;

// ako ide po listicima onda uvezi ovo i odkomentarisi
// definiciju metoda getIntCodes() i getCodeValue() i co9996v i co996s= ...

import java.sql.*;
import javax.swing.*;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;

public class Bibliografija {

  public Bibliografija(Connection conn) {
    this.conn = conn;
    conv = new UnimarcConverter();

    //upunjavaju se HashMap-e internih sifarnika
    co996v = getIntCodes("996", "v");
    co996s = getIntCodes("996", "s");

  }

  /** getIntCodes - vraca HashMap-u internog sifarnika sa parovima (sifra, znacenje sifre)
      @param po - sifra polja
      @param pp sifra potpolja
      @return HashMap-a sa siframa i njihovim znacenjima
  */
  private java.util.HashMap getIntCodes(String po, String pp) {
    java.util.HashMap intCodes = new java.util.HashMap();
    Statement stmt;
    ResultSet rset;


    try {
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT isid, is_naziv FROM interni_sifarnik WHERE ppo_po_poljeid=" + po + " AND ppo_potpoljaid='" + pp + "' ORDER BY isid");
      while (rset.next()) {
        String key = Charset.YUSCII2Unicode(rset.getString(1).trim());
        String value = Charset.YUSCII2Unicode(rset.getString(2).trim());
        intCodes.put(key, value);
      }
      rset.close();
      stmt.close();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIO_DBERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    }

    return intCodes;
  }

  /** getCodeValue - vraca znacenje internog sifarnika na osnovu zadate sifre
      @param ic HashMap-a sa parovima (sifra, znacenje sifre)
      @param co sifra internog sifarnika
      @return String - vrednost na osnovu zadate sifre
  */
  private String getCodeValue(java.util.HashMap ic, String co) {
    if (ic.isEmpty() || co.equals(""))
      return "";
    if (ic.containsKey(co))  {
      return (String)ic.get(co);
    }
    else
      return com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIO_UNKNOWNCODE");
  }

  /** getBibliografija - Vraca HTML dokument sa bibliografijom
      @param type1 prvi parametar za sortiranje podataka - AU, TI ili PY
      @param type2 drugi parametar za sortiranje podataka - AU, TI ili PY
      @param type3 treci parametar za sortiranje podataka - AU, TI ili PY
      @param docIDs vektor odgovarajucih ID brojeva zapisa
      @param docs   vektor odgovarajucih zapisa
      @return HTML dokument
  */
  public String getBibliografija(String type1, String type2, String type3,Vector docIDs, Vector docs) {
    String retVal = "";
    int redbr=0;
    int i=0;
    Vector lines = new Vector();

    retVal = generateHeader();

    Format format = Environment.getLib().getFormat();

    Format formatTMP = new Format();
    Vector pref = new Vector();

    String fNaziv="", fNiz="";

    if (type1.equals("??") || type2.equals("??") || type3.equals("??")) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIO_UNKNOWNSORTPARAM"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE");
    }
    else {
      fNaziv=type1;

      fNiz=type1;
      fNiz+=type2;
      fNiz+=type3;

      pref.add(type1);
      pref.add(type2);
      pref.add(type3);

      formatTMP.postavi(fNaziv,fNiz);
    }

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

     /*
     // stampanje zbog kontrole
      System.out.println("DOCID="+docIDs.elementAt(i));
      for (int j=0;j<sortableVector.size();j++)
        System.out.println("element vektora "+j+"="+sortableVector.elementAt(j));
      */
    }

    Sorter.qsort(niz);


    /*
    System.out.println("*** sortirani niz ***");
    for (i = 0; i < docs.size(); i++) {
      System.out.println("DOCID="+niz[i].getID());
      for (int j=0;j<3;j++)
        System.out.println("element vektora "+j+"="+niz[i].getSortItem(j));
    }
    */

    for (i = 0; i < docs.size(); i++) {
      redbr=i+1;
      //try {
        retVal += generateOne(makeRowContent(String.valueOf(niz[i].getID()),Environment.getReadTs().getDoc(niz[i].getID())));
      //} catch (java.rmi.RemoteException ex) {
      //}
    }
    retVal += generateFooter();
    return retVal;
  }


  /** generateHeader - generise zaglavlje HTML dokumenta
  */

  // NAPOMENA Treba precizno odrediti sirinu tabele da ne izlazi iz margina

  public String generateHeader() {
    String retVal = "";
    retVal += "<HTML><HEAD></HEAD><BODY>";
    retVal += "<CENTER><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIO_TITLECAPS") + "</B></FONT></CENTER>";
    retVal += "<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n";
    return retVal;
  }


  /** Generise vektor sadrzaja celija jedne vrste tabele
       @param docID identifikacioni broj dokumenta
       @param doc rezanac
       @return vektor sadrzaja celija jedne vrste tabele
   */
  public Vector makeRowContent(String docID, String doc) {
    Vector v = new Vector();
    ReportFields rf = new ReportFields();
    Vector retVal = new Vector();
    Concept c = new Concept();
    Catalog cat = new Catalog(Integer.parseInt(docID),doc,"");
    String val, sign, datum, povez, nabavka, odrednica, dimenzije, cena, godiste, broj, napomena,
           glOpis, brListova, tehnika, vrPubl, vrDok, brDok, proprGradja, tehOpis, publ, inv;

    inv = ""; val = ""; sign = ""; datum = ""; povez = ""; nabavka = ""; dimenzije = ""; cena = "";
    odrednica = ""; napomena = ""; glOpis = ""; godiste = ""; broj = ""; brListova=""; tehnika = "";
    vrPubl = ""; vrDok=""; brDok = ""; proprGradja = ""; tehOpis = ""; publ = "";
    int i = 0;

    RezanceUtilities ru = new RezanceUtilities();
    fields = RezanceUtilities.unpackRezance(doc);

    int typePubl = cat.getType();
    // zajednicko za sve inv. liste osim serijskih
    v = c.getField(fields, "996", " ", " ");
    sign="";
    inv="";
    while (i < v.size()) {
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
//        datum = rf.getFieldContent996II((Field)v.elementAt(i),"o");
//        povez = getCodeValue(co996s, rf.getFieldContent996II((Field)v.elementAt(i),"s"));
//        nabavka = getCodeValue(co996v, rf.getFieldContent996II((Field)v.elementAt(i),"v"))
//                  + " " + rf.getFieldContent996II((Field)v.elementAt(i),"2") //uslov!!!!
//                  + " " + rf.getFieldContent996II((Field)v.elementAt(i),"y");
//        cena = rf.getFieldContent996II((Field)v.elementAt(i),"3");
//        napomena = "ID=" + docID + " " + rf.getFieldContent996II((Field)v.elementAt(i),"r");

//        break;
      }
      i++;
    }

    if (typePubl == 1 || typePubl == 9) {  // monografske publikacije i doktorske disertacije
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"adeh")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"adeh");
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
      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
          dimenzije = val;
        }
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
//      retVal.addElement(povez);
//      retVal.addElement(dimenzije);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
    }
    else if (typePubl == 4)  {//serijske publikacije
      java.util.HashMap co997v = getIntCodes("997", "v");
      java.util.HashMap co997s = getIntCodes("997", "s");

      v = c.getField(fields, "997", " ", " ");
      while (i < v.size()) {
        if (!(val = rf.getFieldContent996((Field)v.elementAt(i),"d")).equals("")) {
          if (sign.equals(""))
            sign = val;
          else
            sign+=", "+val;
        }
        if (!(val=rf.getFieldContent996((Field)v.elementAt(i),"f")).equals("")) {
          if (inv.equals(""))
            inv = val;
          else
            inv+=", "+inv;

          datum = rf.getFieldContent996II((Field)v.elementAt(i),"o");
          povez = getCodeValue(co997s, rf.getFieldContent996II((Field)v.elementAt(i),"s"));
          nabavka = getCodeValue(co997v, rf.getFieldContent996II((Field)v.elementAt(i),"v"))
                    + " " + rf.getFieldContent996II((Field)v.elementAt(i),"2") //uslov!!!!
                    + " " + rf.getFieldContent996II((Field)v.elementAt(i),"y");
          cena = rf.getFieldContent996II((Field)v.elementAt(i),"3");
          napomena = "ID=" + docID + " " + rf.getFieldContent996II((Field)v.elementAt(i),"r");
          godiste = rf.getFieldContent997((Field)v.elementAt(i),"jk");
          broj = rf.getFieldContent997((Field)v.elementAt(i),"m");
          break;
        }
        i++;
      }

      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"adeh")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"adeh");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acd")).equals(""))
          glOpis +=  ". - " + val;
      }

      java.util.HashMap co102a = getIntCodes("102", "a");
      if ((v = c.getField(fields, "102", " ", " ")).size() > 0) {
        if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))   {
          glOpis += ", " + getCodeValue(co102a, val);
        }
      }

      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
          dimenzije = val;
        }
      }
      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(godiste);
      retVal.addElement(broj);
//      retVal.addElement(dimenzije);
//      retVal.addElement(povez);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
    }
    else if (typePubl == 17) {//kartografska gradja
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"adeh")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"adeh");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }
      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); // bilo komentar
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; // bilo komentar

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
          dimenzije = val;
        }
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
          brListova = val;
        }
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(brListova);
//      retVal.addElement(dimenzije);
      retVal.addElement(tehnika);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
    }
    else if (typePubl == 10)  {//stara i retka knjiga - nije sigurno
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"adeh")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"adeh");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }
      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; // bilo komentar

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
          dimenzije = val;
        }
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrPubl);
      retVal.addElement(povez);
//      retVal.addElement(dimenzije);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
    }
    else if (typePubl == 99) { //muzikalije - ne znam koji broj
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"ah")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"ah");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acd")).equals(""))
          glOpis +=  ". - " + val;
      }
      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; //

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
          dimenzije = val;
        }
      }

      java.util.HashMap co001b = getIntCodes("001", "b");
      if ((v = c.getField(fields, "001", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent001((Field)v.elementAt(0),"b")).equals(""))   {
          vrDok = getCodeValue(co001b, val);
        }
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrDok);
//      retVal.addElement(dimenzije);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
    }
    else if (typePubl == 999) {  //vizuelna projekcija - ne znam koji je broj
                                //filmovi i videosnimke - ne znam koji je broj
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"a")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"a");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }

      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; //

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
          brDok = val;
        }
      }

      java.util.HashMap co115a = getIntCodes("115", "a");
      java.util.HashMap co115c = getIntCodes("115", "c");
      java.util.HashMap co115d = getIntCodes("115", "d");
      java.util.HashMap co115f = getIntCodes("115", "f");
      java.util.HashMap co115j = getIntCodes("115", "j");
      if ((v = c.getField(fields, "115", " ", " ")).size() > 0) {
        if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
          vrDok = getCodeValue(co115a, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"j")).equals(""))
          proprGradja = getCodeValue(co115j, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"c")).equals(""))
          tehOpis = getCodeValue(co115c, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"d")).equals(""))
          tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co115d, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"f")).equals(""))
          tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co115f, val);
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrDok);
      retVal.addElement(brDok);
      retVal.addElement(proprGradja);
      retVal.addElement(tehOpis);
      retVal.addElement(nabavka);
      retVal.addElement(cena);
//      retVal.addElement(sign);
      retVal.addElement(napomena);
    }
    else if (typePubl == 999) { //mikrooblik - ne znam koji je broj
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"a")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"a");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }

      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; //

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
          brDok = val;
        }
      }

      java.util.HashMap co130a = getIntCodes("130", "a");
      java.util.HashMap co130c = getIntCodes("130", "c");
      java.util.HashMap co130d = getIntCodes("130", "d");

      if ((v = c.getField(fields, "130", " ", " ")).size() > 0) {
        if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
          vrDok = getCodeValue(co130a, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"d")).equals(""))
          tehOpis = getCodeValue(co130d, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"c")).equals(""))
          tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co130c, val);
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrDok);
      retVal.addElement(brDok);
      retVal.addElement(proprGradja);
      retVal.addElement(tehOpis);
      retVal.addElement(nabavka);
      retVal.addElement(cena);
//      retVal.addElement(sign);
      retVal.addElement(napomena);
    }
    else if (typePubl == 99999) {//vizuelna gradja - ne znam koji je broj
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"ad")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"ad");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }

      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; //

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
          brDok = val;
        }
      }

      java.util.HashMap co116a = getIntCodes("116", "a");
      java.util.HashMap co116c = getIntCodes("116", "c");
      if ((v = c.getField(fields, "116", " ", " ")).size() > 0) {
        if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
          vrDok = getCodeValue(co116a, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"c")).equals(""))
          tehOpis = getCodeValue(co116c, val);
      }

      java.util.HashMap co115d1 = getIntCodes("115", "d");
      java.util.HashMap co115f1 = getIntCodes("115", "f");
      if ((v = c.getField(fields, "115", " ", " ")).size() > 0) {
        if (!(val = c.getSFContent((Field)v.elementAt(0),"d")).equals(""))
          tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co115d1, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"f")).equals(""))
          tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co115f1, val);
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrDok);
      retVal.addElement(brDok);
      retVal.addElement(tehOpis);
      retVal.addElement(nabavka);
      retVal.addElement(cena);
//      retVal.addElement(sign);
      retVal.addElement(napomena);
    }
    else if (typePubl == 999999) { //zvucnu gradju - ne znam koji je to broj
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"a")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"a");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }

      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; //

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
          brDok = val;
        }
      }

      java.util.HashMap co126a = getIntCodes("126", "a");
      java.util.HashMap co126h = getIntCodes("126", "h");
      java.util.HashMap co126e = getIntCodes("126", "e");
      if ((v = c.getField(fields, "126", " ", " ")).size() > 0) {
        if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
          vrDok = getCodeValue(co126a, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"h")).equals(""))
          proprGradja = getCodeValue(co126h, val);
        if (!(val = c.getSFContent((Field)v.elementAt(0),"e")).equals(""))
          dimenzije = getCodeValue(co126e, val);
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrDok);
      retVal.addElement(brDok);
      retVal.addElement(proprGradja);
      retVal.addElement(tehOpis);
//      retVal.addElement(dimenzije);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
    }
    else if (typePubl == 9999999) { //rukopisna gradja - ne znam koji broj
      if ((v = c.getField(fields, "200", "1", " ")).size() > 0) {
        if (!(glOpis = rf.getFieldContent200((Field)v.elementAt(0),"a")).equals(""))
          glOpis = "\n&nbsp;&nbsp;&nbsp;" + ReportUtilities.toUpper(glOpis, 1);
      }
      else if ((v = c.getField(fields, "200", " ", " ")).size() > 0) {
        glOpis += "\n&nbsp;&nbsp;&nbsp;" + rf.getFieldContent200((Field)v.elementAt(0),"a");
      }
      if ((v = c.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"acdegh")).equals(""))
          glOpis +=  ". - " + val;
      }

      if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
        glOpis += ".";

      odrednica = c.odrednica(fields); //
      glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis; //

      if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
          dimenzije = val;
        }
      }

      retVal.addElement(inv);
      retVal.addElement(sign);
      retVal.addElement(datum);
      retVal.addElement(glOpis);
      retVal.addElement(vrDok);
//      retVal.addElement(dimenzije);
      retVal.addElement(publ);
//      retVal.addElement(nabavka);
//      retVal.addElement(cena);
//      retVal.addElement(sign);
//      retVal.addElement(napomena);
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
      retVal += "<TR><TD VALIGN=\"top\"><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">";

      while (e.hasMoreElements()) {
        String t = (String)e.nextElement();
        retVal += t +"<BR>\n";
      }
      retVal+="\n<BR></FONT></TD></TR>";
    }
    return retVal;
  }

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

    // dodaj sadrzaj 996dl kao prefiks LX
    int pos1 = rezance.indexOf("\36"+"996  "+"\37"+"l");
    if (pos1 != -1) {
      int pos2 = rezance.indexOf("\\", pos1 + 1);
      if (pos2 == -1)
        pos2 = rezance.indexOf("\37", pos1 + 1);
      if (pos2 != -1) {
        String s = rezance.substring(pos1, pos2);
        hMap.add("LX", s);
      }
    }
    return hMap;
  }


  private Connection conn;
  private UnimarcConverter conv;
  public java.util.HashMap co996v;
  public java.util.HashMap co996s;
  public Vector fields = new Vector();
}




