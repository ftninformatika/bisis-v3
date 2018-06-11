package com.gint.app.bisis.export;

import java.text.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Calendar;
import java.util.StringTokenizer;
import com.objectspace.jgl.HashMap;
import com.gint.util.string.UnimarcConverter;
import com.gint.app.bisis.textsrv.*;

public class NeuspeliTableModel extends AbstractTableModel {

  public NeuspeliTableModel() {
    try { Class.forName(Config.getDriver()); } 
    catch (ClassNotFoundException ex) {}
    conn = null;
    conv = new UnimarcConverter();
  }

  public int getRowCount() { return dates.size(); }
  public int getColumnCount() { return 4; }

  public Object getValueAt(int row, int col) {
    switch (col) {
      case 0: return (String)dates.elementAt(row);
      case 1: return (String)ids.elementAt(row);
      case 2: return (String)titles.elementAt(row);
      case 3: return (String)authors.elementAt(row);
      case 4: return (String)docs.elementAt(row);
      default: return (String)ids.elementAt(row);
    }
  }

  /** Lista sadrzaj LOG fajla na formi. */
  public void setData(String fileName) {
    RandomAccessFile in = null;
    String line;
    try {
      in = new RandomAccessFile(fileName, "r");
      while ((line = in.readLine()) != null) {
        parseLine(line);
      }
      in.close();
    } catch (Exception ex) {
    }
  }

  /** Kreira HashMap objekat sa preslikavanjem prefiks->vrednost za dati rezanac.
      @see getPrefixContent
    */
  public HashMap prepareMap(String rezance) {
    HashMap prefixMap = textServer.getPrefixMap();
    DocumentParser dp = new DocumentParser(prefixMap, "\36", "\37");
    Vector pv = dp.parseDocument(rezance);
    PrefixPair pp = null;
    HashMap hMap = new HashMap(true);
    for (int h = 0; h < pv.size(); h++) {
      pp = (PrefixPair)pv.elementAt(h);
      hMap.add(pp.prefName, pp.value);
    }
    return hMap;
  }

  /** Vrati sadrzaj prefiksa datog imenom (prefix). */
  public String getPrefixContent(String rezance, String prefix) {
    String retVal = "";
    HashMap prefs = prepareMap(rezance);
    Enumeration e = prefs.values(prefix);
    if (e != null) {
      while (e.hasMoreElements()) {
        String tmp = (String)e.nextElement();
        retVal += new String(conv.Unimarc2Unicode(tmp) + "; ");
      }
    }
    if (retVal.endsWith("; "))
      retVal = new String(retVal.substring(0, retVal.length() - 2));
    return retVal;
  }

  /** Parsira jednu liniju LOG fajla. */
  private void parseLine(String line) {
    String s;
    DateFormat df = DateFormat.getDateInstance();
    Date d;
    Calendar c = Calendar.getInstance();
    if (line.length() > 0) {
      StringTokenizer st = new StringTokenizer(line, "\t");
      // prvo ide tekstualni opis datuma
      s = st.nextToken().trim();
      // zatim datum i vreme u milisekundama
      s = st.nextToken().trim();
      try {
        // to isparsiramo da bismo izvukli dan, mesec i godinu.
        d = new Date(Long.parseLong(s));
        c.setTime(d);
        dates.addElement("" + c.get(Calendar.DAY_OF_MONTH) +"."+ c.get(Calendar.MONTH) +"."+ c.get(Calendar.YEAR)+".");
        // ID neuspelog rezanca
        ids.addElement(st.nextToken().trim());
        // rezanac
        s = st.nextToken().trim();
        // uzmemo naslov iz rezanca
        titles.addElement(getPrefixContent(s, "TI"));
        // uzmemo autore iz rezanca
        authors.addElement(getPrefixContent(s, "AU"));
        // sacuvamo rezanac
        docs.addElement(s);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public int insert(String doc) {
    return textServer.insert(doc);
  }

  public int update(int id, String doc) {
    return textServer.update(id, doc);
  }

  public boolean connect() {
    try {
       conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
       conn.setAutoCommit(false);
       textServer = new TextServer(conn, Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  public boolean disconnect() {
    try {
      if (conn != null)
        conn.close();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private UnimarcConverter conv;
  private Connection conn;
  private TextServer textServer;
  private Vector dates = new Vector();
  private Vector ids = new Vector();
  private Vector titles = new Vector();
  private Vector authors = new Vector();
  private Vector docs = new Vector();
}
