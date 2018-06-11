package com.gint.app.bisis.editor.report;

import java.util.*;
import javax.swing.*;
import java.sql.*;

import com.gint.app.bisis.editor.*;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.StringUtils;

// FIZICKI FORMAT LISTICA: (15cm x 7,5cm) odnosno (5,91'' x 2,95'')
// OBAVEZNO !!!!!!!!!!!
// Potrebno je na sistemu defilisati format BibListic koji je 13,50cm x 7,70cm i TopMargin je 0,50 cm

/** Report -- pocetna klasa za generisanje listica.
    @author Tatjana Zubic tanja@uns.ns.ac.yu
    @version 1.0
  */
public class Report {

  /** Formira izvestaj za vise zapisa.
      @param docIDs niz ID zapisa
      @param rezances niz odgovarajucih zapisa
      @param typeCode tip listica
      @return HTML izvestaj
    */
  public static String makeMulti(int[] docIDs, String[] rezances, String typeCode) {
    String retVal = "";
    for (int i = 0; i < docIDs.length; i++) {
       retVal += makeOne(docIDs[i], rezances[i], typeCode);
    }
    return retVal;
  }


  /** Preuzima biblioteku iz validatora
      @return biblioteka npr. SMIP
  */
  private static String getLib()  {
    //String server = Environment.getServer();
    String server = Environment.getURL();
    return server;
  }



  public static String getDoc(String issn)  {
    Statement stmt;
    ResultSet rset;
    Vector doc = new Vector();
    Vector docs = new Vector();

    issn = issn.toUpperCase();
    try {
      String issn1=issn.substring(0,4);
      String issn2=issn.substring(5,9);

      String query="SELECT document FROM Documents where doc_id=";
      query+="(select doc_id from pref_SP WHERE content='" + issn1.trim() + "' and doc_id=(";
      query+="select doc_id from pref_SP WHERE content='" + issn2.trim() + "'))";
      stmt = conn.createStatement();
      ((oracle.jdbc.driver.OracleStatement)stmt).defineColumnType(1, Types.LONGVARBINARY);
      rset = stmt.executeQuery(query);

      while (rset.next()) {
        docs.addElement(StringUtils.getStringLower(rset.getBytes(1)).trim());
      }
      rset.close();
      stmt.close();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_DBERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
      System.err.println(ex);
    }
    if (docs.size() <= 0 || docs.size() > 1)
      return "";
    else {
      return (String)docs.elementAt(0);
    }
  }

  /** Formira izvestaj za jedan zapis.
      @param docID ID zapisa
      @param rezance Zapis
      @param typeCode tip listica
      @return HTML izvestaj
    */
  public static String makeOne(int docID, String rezance, String typeCode){
    String izlaz;
    Catalog catalog = new Catalog(docID, rezance, typeCode);

    int typeRep = getTypeRep(typeCode);
    if (!catalog.checkType(typeRep))
      return izlaz = "<BR><BR>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_WRONGPUBTYPE") + "<BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR>";

    switch (typeRep) {
      case 2:
             //if (Environment.getClient() == Environment.SMIP) //** SMIP
             //  catalog.setNoTes(true);
             izlaz = catalog.getInventarni();//getGjklm();
             //catalog.setNoTes(false);
             break;
      case 3:
             /*if (Environment.getClient() == Environment.SMIP) //** SMIP
               catalog.setNoTes(true);
             izlaz = catalog.getUDCListic();
             catalog.setNoTes(false);*/
      	     izlaz= catalog.zaStampu();
             break;
      case 4:
             /*if (Environment.getClient() == Environment.SMIP) //** SMIP
               catalog.setNoTes(true);
             izlaz = catalog.getPredmListic();
             catalog.setNoTes(false);*/
      	     izlaz=catalog.osnovniListic();
             break;
      default:
             return "Gre\u0161ka!!!";
    }
    return izlaz;
  }


  /** getTypeRep - Tip izvestaja (codeRep) pretvara u broj typeRep. */
  private static int getTypeRep(String codeRep) {
    if (codeRep.equals("LIN"))   // Inventarni listic
       return 2;
    if (codeRep.equals("LST"))   // Listic za stampanje
       return 3;
    if (codeRep.equals("LGL"))   // Listic za prikazivanje
       return 4;
    return 0;
  }

  public static boolean reportTypeExists(String type) {
    for (int i = 0; i < reportTypes.length; i++)
      if (type.equals(reportTypes[i]))
        return true;
    return false;
  }


  static int brmax = 13; //18 za font 8;
  static int bkmax = 60;//50;//50; // bilo 45
  
  static int maxKol = 110;
  static int ravnanje=25;
  private static UnimarcConverter conv = new UnimarcConverter();
  private static Connection conn;

  public static String[] reportTypeDesc;
  public static String[] reportTypes;
/*
  public final static String[] reportTypeDesc = {
    "L11 : Osnovni - monografije (Filozofski fakultet)",
    "L13 : UDC - monografije (Filozofski fakultet)",
    "L14 : Predmetni - monografije (Filozofski fakultet)",
    "L15 : Autorski - monografije (Filozofski fakultet)",
    "L16 : Uputni - monografije (Filozofski fakultet)",
    "L17 : Set za monografije (Filozofski fakultet)",
    "L21 : Osnovni - doktorske",
    "L22 : Abecedni - doktorske",
    "L23 : UDC - doktorske",
    "L24 : Predmetni - doktorske",
    "L25 : Autorski - doktorske",
    "L26 : Uputni - doktorske",
    "L27 : Set za doktorske",
    "L31 : Osnovni - doktorske (Filozofski fakultet)",
    "L32 : Abecedni - doktorske (Filozofski fakultet)",
    "L33 : UDC - doktorske (Filozofski fakultet)",
    "L34 : Predmetni - doktorske (Filozofski fakultet)",
    "L35 : Autorski - doktorske (Filozofski fakultet)",
    "L36 : Uputni - doktorske (Filozofski fakultet)",
    "L37 : Set za doktorske (Filozofski fakultet)",
    "L41 : Osnovni za clanke iz monografija",
    "L42 : Osnovni za clanke iz serijskih",
    "L71 : Osnovni - monografije",
    "L72 : Abecedni - monografije",
    "L73 : UDC - monografije",
    "L74 : Predmetni - monografije",
    "L75 : Autorski - monografije",
    "L76 : Uputni - monografije",
    "L77 : Set za monografije",
    "L81 : Osnovni - serijske",
    "L82 : Predmetni - serijske",
    "L83 : Osnovni - serijske (Filozofski fakultet)",
    "L99 : Probni izvestaj - inventarna knjiga"

    return niz1;
  };
*/

  public static void setConnection(Connection db_conn) {
    conn=db_conn;
  }

  public final static void loadReportTypes()
  {
    try {
      int i = 0;
      int duzina=0;
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select formatid, for_opis from FORMATF where formatid LIKE 'L%'");

      while (rset.next()) {
        duzina++;
      }
      rset.close();
reportTypeDesc = new String[duzina];
reportTypes = new String[duzina];


      rset = stmt.executeQuery("select formatid, for_opis from FORMATF where formatid LIKE 'L%'");
      while (rset.next()) {
        reportTypes[i] = rset.getString("FORMATID").trim();
        reportTypeDesc[i]= rset.getString("FORMATID").trim()+" : "+rset.getString("FOR_OPIS").trim();
        i++;
      }
      rset.close();
    }
    catch (SQLException ex) {
      System.err.println(ex);
    }
  }


/*   public final static String[] reportTypes = {

    "L11", "L13", "L14", "L15", "L16", "L17",
    "L21", "L22", "L23", "L24", "L25", "L26", "L27",
    "L31", "L32", "L33", "L34", "L35", "L36", "L37",
    "L41", "L42",
    "L71", "L72", "L73", "L74", "L75", "L76", "L77",
    "L81", "L82", "L83", "L99"
  };
*/
  public static String currentType = "LST";

}
