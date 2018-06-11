package com.gint.app.bisis.editor.report;

import java.util.*;
import java.sql.*;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.Charset;

public class InventarnaKnjiga {

  public InventarnaKnjiga(Connection conn) {
    this.conn = conn;
    conv = new UnimarcConverter();

    //upunjavaju se HashMap-e internih sifarnika
    co996v = getIntCodes("996", "v");
    co996s = getIntCodes("996", "s");
    co996q = getIntCodes("996", "q");
  }

  /** getIntCodes - vraca HashMap-u internog sifarnika sa parovima (sifra, znacenje sifre)
      @param po - sifra polja
      @param pp sifra potpolja
      @return HashMap-a sa siframa i njihovim znacenjima
  */
  private static boolean exist996q(Field f){
	   Vector v=f.getSubfieldsByName("q");
	   if (v.size()==0){
	     return false;
	   }else{
		   return true;
	   }
  }
  private HashMap getIntCodes(String po, String pp) {
    Statement stmt;
    ResultSet rset;
    HashMap intCodes = new HashMap();

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
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DBERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    }
    return intCodes;
  }


/// proveriti da li vrednost sifre iz getCodeValue treba prevesti u Unicode

  /** getCodeValue - vraca znacenje internog sifarnika na osnovu zadate sifre
      @param ic HashMap-a sa parovima (sifra, znacenje sifre)
      @param co sifra internog sifarnika
      @return String - vrednost na osnovu zadate sifre
  */
  private String getCodeValue(HashMap ic, String co) {

    if (ic.isEmpty() || co.equals(""))
      return "";
    if (ic.containsKey(co))  {
      return (String)ic.get(co);
    }
    else
      return com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_UNKNOWNCODE");
  }

  /** getInvBook - Vraca HTML dokument sa inventarnom knjigom
      @param invNumbers vektor inventarnih brojeva
      @param docIDs vektor odgovarajucih ID brojeva zapisa
      @param docs   vektor odgovarajucih zapisa
      @return HTML dokument
  */
  public String getInvBook(int type, Vector invNumbers, Vector docIDs, Vector docs) {
    String retVal = "";

    //NAPOMENA: eventualno ubaciti proveru redosleda inventarnih brojeva....
    retVal = generateHeader(type);
    for (int i = 0; i < invNumbers.size(); i++) {
       retVal += generateOne(makeRowContent(type, invNumbers.elementAt(i).toString(), docIDs.elementAt(i).toString(), docs.elementAt(i).toString()));
    }
    retVal += generateFooter();
    return retVal;
  }

  /** generateHeader - generise zaglavlje HTML dokumenta
  */

  // NAPOMENA Treba precizno odrediti sirinu tabele da ne izlazi iz margina

  public String generateHeader(int type) {

    String retVal = "";
    retVal += "<HTML><HEAD></HEAD><BODY BGCOLOR=white TEXT=black>";
    retVal += "<TABLE BORDER=\"1\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n";
    switch (type) {
      case 0:   // monografska publikacija
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVMON") + "<BR></B></FONT></CAPTION>"; // treba izvuci iz tipa publikacije
          retVal += "<TR><TD><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM") + "</B></FONT></TD>\n"; // WIDTH=\"35\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n"; // WIDTH=\"45\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTI") + "</B></FONT></TD>\n"; // WIDTH=\"160\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TYPEPOV") + "</B></FONT></TD>\n"; // WIDTH=\"40\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";  // WIDTH=\"40\"
          break;
      case 1:    // serijska publikacija
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVSER") + "<BR></B></FONT></CAPTION>"; // treba izvuci iz tipa publikacije
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TISUBTI") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_SERPY") + "<BR>godina, sveska (tom)</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NOOFWRKBK") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_POVEZ") + "</B></FONT></TD>\n";
          break;
      case 2: //kartografska gradja
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVCART") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIPP") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NOOFLEAFS") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TECH") + "</B></FONT></TD>\n";
          break;
      case 3: //stara i retka knjiga
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_OLDRARE") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIPPOLD") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PUBTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_VRSTPOV") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";
          break;
      case 4: //muzikalije
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVMUS") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIPP") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";
          break;
      case 5: //vizuelna projekcija
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVVISPROJ") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIVIS") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCNUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PROPGR") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TECHDESC") + "</B></FONT></TD>\n";
          break;
      case 6: //filmovi i videosnimke
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVVID") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIVIS") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCNUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PROPGR") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TECHDESC") + "</B></FONT></TD>\n";
          break;
      case 7: //mikrooblik
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVMIC") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIVIS") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCNUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PROPGR") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TECHDESC") + "</B></FONT></TD>\n";
          break;
      case 8: //vizuelna gradja
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVVISGR") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIVIS") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCTYPE") + " </B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCNUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TECHDESC") + "</B></FONT></TD>\n";
          break;
      case 9: //zvucna gradja
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVZVGR") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-2\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTIVIS") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DOCNUM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PROPGR") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_SNDRECTECH")+ "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";
          break;
      case 10: //rukopisna gradja
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_INVMAN") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD WIDTH=\"35\"><FONT FACE=\"Arial\" SIZE=\"-2\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM2") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"45\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"160\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_MANAUTI") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_MANTYPE") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";
          retVal += "<TD VALIGN=\"top\" WIDTH=\"40\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PUBLISHED") + "</B></FONT></TD>\n";
          break;
      case 11: //osnovna sredstva
          retVal += "<CAPTION><FONT FACE=\"Arial\" SIZE=\"-1\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_BASIC_MEANS") + "<BR></B></FONT></CAPTION>";
          retVal += "<TR><TD><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_NUM") + "</B></FONT></TD>\n"; // WIDTH=\"35\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DATE") + "</B></FONT></TD>\n"; // WIDTH=\"45\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_AUTI") + "</B></FONT></TD>\n"; // WIDTH=\"160\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TYPEPOV") + "</B></FONT></TD>\n"; // WIDTH=\"40\"
          retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DIM") + "</B></FONT></TD>\n";  // WIDTH=\"40\"
          break;
      default:
          break;
    }
    retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_TYPEOFAQUIS") + "</B></FONT></TD>\n"; //  WIDTH=\"40\"
    retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_PRICE") + "</B></FONT></TD>\n"; // WIDTH=\"30\"
    retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_CALLNO") + "</B></FONT></TD>\n"; // WIDTH=\"40\"
    retVal += "<TD VALIGN=\"top\"><FONT FACE=\"Arial\" SIZE=\"-3\"><B>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_REM") + "</B></FONT></TD>\n"; // WIDTH=\"45\"
    retVal += "</TD></TR>\n";
    return retVal;
  }


  /** Generise vektor sadrzaja celija jedne vrste tabele
       @param inv inventarni broj
       @param docID identifikacioni broj dokumenta
       @param doc rezanac
       @return vektor sadrzaja celija jedne vrste tabele
   */
  public Vector makeRowContent(int type, String inv, String docID, String doc) {
    Vector v = new Vector();
    ReportFields rf = new ReportFields();
    Vector retVal = new Vector();
    Concept c = new Concept();
    Catalog cat = new Catalog(Integer.parseInt(docID),doc,"");
    String val, sign, datum, povez, nabavka, odrednica, dimenzije, cena, godiste, broj, napomena, glOpis, brListova, tehnika, vrPubl, vrDok, brDok, proprGradja, tehOpis, publ,status,nazivStatusa;
    val = ""; sign = ""; datum = ""; povez = ""; nabavka = ""; dimenzije = ""; cena = ""; odrednica = ""; napomena = ""; glOpis = "";
    godiste = ""; broj = ""; brListova=""; tehnika = ""; vrPubl = ""; vrDok=""; brDok = ""; proprGradja = ""; tehOpis = ""; publ = ""; status="";nazivStatusa="";
    int i = 0;
    boolean exist;

    RezanceUtilities ru = new RezanceUtilities();
    fields = RezanceUtilities.unpackRezance(doc);

    int typePubl = cat.getType();

    // zajednicko za sve inv. liste osim serijskih
    if (type != 1)  {
      v = c.getField(fields, "996", " ", " "); //vraca samo polja 996
      while (i < v.size()) {
        if (!(val = rf.getFieldContent996((Field)v.elementAt(i),"d")).equals("")) {
          sign = val;
        }
        //uzimanje sifre statusa
        exist=exist996q((Field)v.elementAt(i));
		if (!exist){ //ako polje 996q ne postoji
			nazivStatusa="Aktivno";
			
		}else{
			status=rf.getFieldContent996((Field)v.elementAt(i),"q");
			HashMap co996q = getIntCodes("996", "q");
			nazivStatusa= getCodeValue(co996q, status);
		}

        if (rf.getFieldContent996((Field)v.elementAt(i),"f").equals(inv)) {
           datum = rf.getFieldContent996II((Field)v.elementAt(i),"o");
           povez = getCodeValue(co996s, rf.getFieldContent996II((Field)v.elementAt(i),"s"));
           nabavka = getCodeValue(co996v, rf.getFieldContent996II((Field)v.elementAt(i),"v"))
                     + " " + rf.getFieldContent996II((Field)v.elementAt(i),"2") //uslov!!!!
                     + " " + rf.getFieldContent996II((Field)v.elementAt(i),"y");
           cena = rf.getFieldContent996II((Field)v.elementAt(i),"3");
         //  napomena = "ID=" + docID + " " + rf.getFieldContent996II((Field)v.elementAt(i),"r");
           napomena = "ID=" + docID + " " + rf.getFieldContent996II((Field)v.elementAt(i),"r")+" \n "+ nazivStatusa;
           break;
        }
        i++;
      }
    }


    switch (type) {
      case 0: // monografske publikacije
              if (typePubl != 1)
                break;
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

              odrednica = c.odrednica(fields);
//              glOpis=formatiraj(glOpis,21);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
                  dimenzije = val;
                }
              }

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);

              retVal.addElement(povez);
              retVal.addElement(dimenzije);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 1: //serijske publikacije

              if (typePubl != 4)  // OVO NIJE DOBRO, TREBA PROVERITI !!!!!!!!!!
                break;

              HashMap co997v = getIntCodes("997", "v");
              HashMap co997s = getIntCodes("997", "s");

              v = c.getField(fields, "997", " ", " ");
              while (i < v.size()) {
                if (!(val = rf.getFieldContent996((Field)v.elementAt(i),"d")).equals("")) {
                   sign = val;
                }
                if (rf.getFieldContent996((Field)v.elementAt(i),"f").equals(inv)) {
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

              HashMap co102a = getIntCodes("102", "a");
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
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(godiste);
              retVal.addElement(broj);
              retVal.addElement(dimenzije);
              retVal.addElement(povez);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 2: //kartografska gradja

              if (typePubl != 17)
                break;

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

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
                  dimenzije = val;
                }
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
                  brListova = val;
                }
              }

              //tehnika - treba desifrovati 120 ili 121 ili 123 ili 124 - nedefinisano

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(brListova);
              retVal.addElement(dimenzije);
              retVal.addElement(tehnika);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 3: //stara i retka knjiga

              //if (typePubl != 10) nije sigurno
                //break;


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

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
                  dimenzije = val;
                }
              }

              // vrPubl neodredjeno

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrPubl);
              retVal.addElement(povez);
              retVal.addElement(dimenzije);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 4: //muzikalije

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

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
                  dimenzije = val;
                }
              }

              // vrDok nije potpuno odredjeno
              HashMap co001b = getIntCodes("001", "b");
              if ((v = c.getField(fields, "001", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent001((Field)v.elementAt(0),"b")).equals(""))   {
                   vrDok = getCodeValue(co001b, val);
                }
              }

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrDok);
              retVal.addElement(dimenzije);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 5: //vizuelna projekcija
      case 6: //filmovi i videosnimke
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

              // fali standardni broj

              if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
                glOpis += ".";

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
                  brDok = val;
                }
              }

              HashMap co115a = getIntCodes("115", "a");
              HashMap co115c = getIntCodes("115", "c");
              HashMap co115d = getIntCodes("115", "d");
              HashMap co115f = getIntCodes("115", "f");
              HashMap co115j = getIntCodes("115", "j");
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
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrDok);
              retVal.addElement(brDok);
              retVal.addElement(proprGradja);
              retVal.addElement(tehOpis);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 7: //mikrooblik

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

              // fali standardni broj

              if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
                glOpis += ".";

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
                  brDok = val;
                }
              }

              HashMap co130a = getIntCodes("130", "a");
              HashMap co130c = getIntCodes("130", "c");
              HashMap co130d = getIntCodes("130", "d");

              if ((v = c.getField(fields, "130", " ", " ")).size() > 0) {
                if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
                   vrDok = getCodeValue(co130a, val);
                if (!(val = c.getSFContent((Field)v.elementAt(0),"d")).equals(""))
                   tehOpis = getCodeValue(co130d, val);
                if (!(val = c.getSFContent((Field)v.elementAt(0),"c")).equals(""))
                   tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co130c, val);
              }

              // fali propratna gradja
              // mozda HashMap co115j = getIntCodes("115", "j");

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrDok);
              retVal.addElement(brDok);
              retVal.addElement(proprGradja);
              retVal.addElement(tehOpis);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 8: //vizuelna gradja

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

              // fali standardni broj

              if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
                glOpis += ".";

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
                  brDok = val;
                }
              }

              HashMap co116a = getIntCodes("116", "a");
              HashMap co116c = getIntCodes("116", "c");
              if ((v = c.getField(fields, "116", " ", " ")).size() > 0) {
                if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
                   vrDok = getCodeValue(co116a, val);
                if (!(val = c.getSFContent((Field)v.elementAt(0),"c")).equals(""))
                   tehOpis = getCodeValue(co116c, val);
              }

              HashMap co115d1 = getIntCodes("115", "d");
              HashMap co115f1 = getIntCodes("115", "f");
              if ((v = c.getField(fields, "115", " ", " ")).size() > 0) {
                if (!(val = c.getSFContent((Field)v.elementAt(0),"d")).equals(""))
                   tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co115d1, val);
                if (!(val = c.getSFContent((Field)v.elementAt(0),"f")).equals(""))
                   tehOpis += (tehOpis.equals("")? "" : " ") + getCodeValue(co115f1, val);
              }

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrDok);
              retVal.addElement(brDok);
              retVal.addElement(tehOpis);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 9: //zvucnu gradju

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

              // fali standardni broj

              if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
                glOpis += ".";

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"a")).equals(""))   {
                  brDok = val;
                }
              }

              HashMap co126a = getIntCodes("126", "a");
              HashMap co126h = getIntCodes("126", "h");
              HashMap co126e = getIntCodes("126", "e");
              if ((v = c.getField(fields, "126", " ", " ")).size() > 0) {
                if (!(val = c.getSFContent((Field)v.elementAt(0),"a")).equals(""))
                   vrDok = getCodeValue(co126a, val);
                if (!(val = c.getSFContent((Field)v.elementAt(0),"h")).equals(""))
                   proprGradja = getCodeValue(co126h, val);
                if (!(val = c.getSFContent((Field)v.elementAt(0),"e")).equals(""))
                   dimenzije = getCodeValue(co126e, val);
              }

              //fali tehnika zvucnog opisa

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrDok);
              retVal.addElement(brDok);
              retVal.addElement(proprGradja);
              retVal.addElement(tehOpis);
              retVal.addElement(dimenzije);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 10: //rukopisna gradja

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

              // fali opis dela

              if (!glOpis.equals("") && !glOpis.endsWith(".") && !glOpis.endsWith(") "))
                glOpis += ".";

              odrednica = c.odrednica(fields);
              glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


              if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
                if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
                  dimenzije = val;
                }
              }

              //fali publikovano

              retVal.addElement(inv);
              retVal.addElement(datum);
              retVal.addElement(glOpis);
              retVal.addElement(vrDok);
              retVal.addElement(dimenzije);
              retVal.addElement(publ);
              retVal.addElement(nabavka);
              retVal.addElement(cena);
              retVal.addElement(sign);
              retVal.addElement(napomena);
              break;

      case 11: //Osnovna sredstva
    	 
    		 
    	 
    	  if (typePubl == 1 && (nazivStatusa.compareToIgnoreCase("Aktivno")==0)){
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

            odrednica = c.odrednica(fields);
//            glOpis=formatiraj(glOpis,21);
            glOpis = (odrednica.equals("") ? "" : odrednica + "<BR>\n") + glOpis;


            if ((v = c.getField(fields, "215", " ", " ")).size() > 0) {
              if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"d")).equals(""))   {
                dimenzije = val;
              }
            }

            retVal.addElement(inv);
            retVal.addElement(datum);
            retVal.addElement(glOpis);

            retVal.addElement(povez);
            retVal.addElement(dimenzije);
            retVal.addElement(nabavka);
            retVal.addElement(cena);
            retVal.addElement(sign);
            retVal.addElement(napomena);
    	  }
            break;
      default:
              break;

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
      retVal += "<TR>";

      while (e.hasMoreElements()) {
        String t = (String)e.nextElement();
        if (t.equals("") || t.substring(0,1).equals(" "))
            t="&nbsp;"+t;
        retVal += "<TD VALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"-3\">" + t + "</FONT></TD>";
      }
      retVal+="</TR>";
    }
    return retVal;
  }

/*
  private int nadjiPrelom(String str,int duzina) {
    int lsp=str.lastIndexOf(' ');
    if (lsp > duzina)
      return (nadjiPrelom(str.substring(0,lsp),duzina));
    else
      return lsp;
  }

  private String formatiraj(String tekst,int duzina) {
    if (tekst.length() > duzina) {
      String vrati="";
      String rez="";
      String pom = new String(tekst);
      int prelom;

      prelom = nadjiPrelom(pom,duzina);
      rez=pom.substring(0,prelom+1);
      vrati+=rez;
      pom=pom.substring(prelom+1);

      while (pom.length() > duzina) {
        prelom = nadjiPrelom(pom,duzina);
        rez=pom.substring(0,prelom+1);
        vrati+="\n<BR>"+rez;
        pom=pom.substring(prelom+1);
      }
      if (pom.length() != 0)
        vrati+="\n<BR>"+pom;

      return vrati;
    }
    else
      return tekst;
  }
*/

  private Connection conn;
  private UnimarcConverter conv;
  public HashMap co996v;
  public HashMap co996s;
  public HashMap co996q;
  public Vector fields = new Vector();
}

/*  public String getInvBook(int[] docIDs, String[] rezances) {
    String retVal = "";
    for (int i = 0; i < docIDs.length; i++) {
       retVal += generateHeader() + generateOne(makeRowContent(rezances[i], "")) + generateFooter();
    }
    return retVal;
  }*/



