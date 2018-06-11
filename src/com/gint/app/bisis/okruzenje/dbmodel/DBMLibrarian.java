package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMLibrarian extends AbstractTableModel {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMLibrarian(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select BIBID, BIBTIP, BIBNOBR, BIBNOBAV, BIBINTOZN, BIBTAJOZN, OPIS_BIBL from BIBLIOTEKAR order by BIBID ASC");
      while(rset.next()) {
        bibID.addElement(rset.getString(1).trim());
        bibType.addElement(rset.getString(2));
        bibNoBr.addElement(rset.getString(3));
        bibNoBav.addElement(rset.getString(4));
        bibIntOzn.addElement(rset.getString(5));
        bibTajOzn.addElement(rset.getString(6));
        bibDesc.addElement((rset.getString(7) == null)? "" : rset.getString(7));
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public DBMLibrarian(Connection conn, String where) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select BIBID, BIBTIP, BIBNOBR, BIBNOBAV from BIBLIOTEKAR "+where+" order by BIBID ASC");
      while(rset.next()) {
        currVal.addElement(rset.getString(1).trim());
        currVal.addElement(rset.getString(2));
        currVal.addElement(rset.getString(3));
        currVal.addElement(rset.getString(4));
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public Object getValueAt(int row, int col) {
    switch(col) {
      case 0: return bibID.elementAt(row);
      case 1: return bibType.elementAt(row);
      case 2: return bibNoBr.elementAt(row);
      case 3: return bibNoBav.elementAt(row);
      case 4: return bibIntOzn.elementAt(row);
      case 5: return bibTajOzn.elementAt(row);
      case 6: return bibDesc.elementAt(row);
      default: return bibID.elementAt(row);
    }
  }

  public int getRowCount() {
    return bibID.size();
  }

  public int getColumnCount() {
    return 7;
  }

  public Vector getCurrValues() {
    return currVal;
  }

  public void reloadValues(String where) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select BIBID, BIBTIP, BIBNOBR, BIBNOBAV from BIBLIOTEKAR "+where+" order by BIBID ASC");
      currVal.removeAllElements();
      while(rset.next()) {
        currVal.addElement(rset.getString(1).trim());
        currVal.addElement(rset.getString(2));
        currVal.addElement(rset.getString(3));
        currVal.addElement(rset.getString(4));
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void setValues(int col, Object value, int row, int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: bibID.insertElementAt((String)value, row);
                return;
        case 1: bibType.insertElementAt((String)value, row);
                return;
        case 2: bibNoBr.insertElementAt((String)value, row);
                return;
        case 3: bibNoBav.insertElementAt((String)value, row);
                return;
        case 4: bibIntOzn.insertElementAt((String)value, row);
                return;
        case 5: bibTajOzn.insertElementAt((String)value, row);
                return;
        case 6: bibDesc.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
        case 0: bibID.setElementAt((String)value, row);
                return;
        case 1: bibType.setElementAt((String)value, row);
                return;
        case 2: bibNoBr.setElementAt((String)value, row);
                return;
        case 3: bibNoBav.setElementAt((String)value, row);
                return;
        case 4: bibIntOzn.setElementAt((String)value, row);
                return;
        case 5: bibTajOzn.setElementAt((String)value, row);
                return;
        case 6: bibDesc.setElementAt((String)value, row);
                return;
        }
      }
    }
  }

  public void removeRow(int row) {
     bibID.removeElementAt(row);
     bibType.removeElementAt(row);
     bibNoBr.removeElementAt(row);
     bibNoBav.removeElementAt(row);
     bibIntOzn.removeElementAt(row);
     bibTajOzn.removeElementAt(row);
     bibDesc.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)bibType.elementAt(row));
      query.setInt(2,Integer.parseInt((String)bibNoBr.elementAt(row)));
      query.setInt(3,Integer.parseInt((String)bibNoBav.elementAt(row)));
      query.setInt(4,Integer.parseInt((String)bibIntOzn.elementAt(row)));
      query.setString(5,(String)bibTajOzn.elementAt(row));
      query.setString(6,(String)bibDesc.elementAt(row));
      //query.setString(7,((String)bibID.elementAt(row)));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  private void insertProcType(Connection conn, int row) {
     Vector pref = new Vector();
     pref.addElement("AU");
     pref.addElement("TI");
     pref.addElement("PY");
     pref.addElement("LA");
     pref.addElement("KW");
     DBDefValLibr.insertIntoProcType(conn, ((String)bibID.elementAt(row)),(String)bibType.elementAt(row),Integer.parseInt((String)bibNoBr.elementAt(row)),
                                     Integer.parseInt((String)bibNoBav.elementAt(row)));
     DBDefValLibr.insertIntoPrefProcType(conn, ((String)bibID.elementAt(row)),(String)bibType.elementAt(row),Integer.parseInt((String)bibNoBr.elementAt(row)),
                                     Integer.parseInt((String)bibNoBav.elementAt(row)), pref);

  }

  public void saveToDB(int mode, String key, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from BIBLIOTEKAR where BIBID =" + "'"+key+"'";
      try{
        PreparedStatement deleteQuery = conn.prepareStatement(deleteRow);
        deleteQuery.executeUpdate();
        deleteQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }

    if (mode == UPDATE) {
			String updateRow = "update BIBLIOTEKAR set BIBTIP = ?, BIBNOBR = ?, BIBNOBAV = ?, BIBINTOZN = ?, BIBTAJOZN = ?, OPIS_BIBL =? where BIBID =" + "'"+key+"'";
      try {
        PreparedStatement updateQuery = conn.prepareStatement(updateRow);
        setValuesDB(updateQuery, row);
        updateQuery.executeUpdate();
        updateQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
      insertProcType(conn,row);
    }
    if (mode == INSERT) {
			String insertRow = "insert into BIBLIOTEKAR (BIBTIP, BIBNOBR, BIBNOBAV, BIBINTOZN, BIBTAJOZN, OPIS_BIBL, BIBID) values (" + "?, ?, ?, ?, ?, ?,"+"'"+key+"'" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        setValuesDB(insertQuery, row);
        insertQuery.executeUpdate();
        insertQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
      insertProcType(conn,row);
    }
  }

  public int find(String value) {
    if (bibID.contains(value))
      return bibID.indexOf(value);
    else
      return -1;
  }

  public int findPos(Object liID) {
   return ValuePos.IndexToInsert(liID,0,bibID.size(),bibID);
  }

  private Vector currVal = new Vector();
  private Vector bibID = new Vector();
  private Vector bibType = new Vector();
  private Vector bibNoBr = new Vector();
  private Vector bibNoBav = new Vector();
  private Vector bibIntOzn = new Vector();
  private Vector bibTajOzn = new Vector();
  private Vector bibDesc = new Vector();
}