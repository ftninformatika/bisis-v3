package com.gint.app.bisis.okruzenje.dbmodel;

import javax.swing.table.*;
import java.sql.*;
import java.util.Vector;

public class TableModelField extends AbstractTableModel {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  private ResultSetMetaData metaData;
  private int colCount;

  public TableModelField(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select poljeid, po_naziv, obaveznost, ponovljivost, sekundarnost, opis_pi, opis_di, def_pi, def_di from polje");
      while (rset.next()) {
        fieldCodes.addElement(rset.getString(1));
        fieldNames.addElement(rset.getString(2));
        mand.addElement(rset.getString(3));
        repeat.addElement(rset.getString(4));
        sec.addElement(rset.getString(5));
        descFI.addElement((rset.getString(6) == null)? "" : rset.getString(6));
        descSI.addElement((rset.getString(7) == null)? "" : rset.getString(7));
        defValFI.addElement((rset.getString(8) == null)? "" : rset.getString(8));
        defValSI.addElement((rset.getString(9) == null)? "" : rset.getString(9));
      }

      metaData = rset.getMetaData();
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
  public int getRowCount() { return fieldCodes.size(); }
  public int getColumnCount() { return 9; }

  public Object getValueAt(int row, int col) {
    switch (col) {
      case 0: return fieldCodes.elementAt(row);
      case 1: return fieldNames.elementAt(row);
      case 2: return mand.elementAt(row);
      case 3: return repeat.elementAt(row);
      case 4: return sec.elementAt(row);
      case 5: return descFI.elementAt(row);
      case 6: return descSI.elementAt(row);
      case 7: return defValFI.elementAt(row);
      case 8: return defValSI.elementAt(row);
      default: return fieldNames.elementAt(row);
    }
  }

  public void removeRow(int row) {
    fieldCodes.removeElementAt(row);
    fieldNames.removeElementAt(row);
    mand.removeElementAt(row);
    repeat.removeElementAt(row);
    sec.removeElementAt(row);
    descFI.removeElementAt(row);
    descSI.removeElementAt(row);
    defValFI.removeElementAt(row);
    defValSI.removeElementAt(row);
  }

  public int find(String value) {
    if (fieldCodes.contains(value))
      return fieldCodes.indexOf(value);
    else
      return -1;
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setInt(1, Integer.parseInt((String)mand.elementAt(row)));
      query.setInt(2, Integer.parseInt((String)repeat.elementAt(row)));
      query.setString(3, (String)fieldNames.elementAt(row));
      query.setInt(4, Integer.parseInt((String)sec.elementAt(row)));
      query.setString(5, (String)descSI.elementAt(row));
      query.setString(6, (String)descFI.elementAt(row));
      query.setString(7, (String)defValFI.elementAt(row));
      query.setString(8, (String)defValSI.elementAt(row));
      query.setString(9, (String)fieldCodes.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void setValues(int col, Object obj, int row, int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: fieldCodes.insertElementAt((String)obj, row);
                return;
        case 1: fieldNames.insertElementAt((String)obj, row);
                return;
        case 2: mand.insertElementAt((String)obj, row);
                return;
        case 3: repeat.insertElementAt((String)obj, row);
                return;
        case 4: sec.insertElementAt((String)obj, row);
                return;
        case 5: descFI.insertElementAt((String)obj, row);
                return;
        case 6: descSI.insertElementAt((String)obj, row);
                return;
        case 7: defValFI.insertElementAt((String)obj, row);
                return;
        case 8: defValSI.insertElementAt((String)obj, row);
                return;
        //default: return fieldNames.setElementAt(row);
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: fieldCodes.setElementAt((String)obj, row);
                  return;
          case 1: fieldNames.setElementAt((String)obj, row);
                  return;
          case 2: mand.setElementAt((String)obj, row);
                  return;
          case 3: repeat.setElementAt((String)obj, row);
                  return;
          case 4: sec.setElementAt((String)obj, row);
                  return;
          case 5: descFI.setElementAt((String)obj, row);
                  return;
          case 6: descSI.setElementAt((String)obj, row);
                  return;
          case 7: defValFI.setElementAt((String)obj, row);
                  return;
          case 8: defValSI.setElementAt((String)obj, row);
                  return;
          //default: return fieldNames.setElementAt(row);
        }
      }
    }
  }

  public boolean contains(Object obj) {
    return fieldCodes.contains(obj);
  }

  public void saveToDB(int mode, String field, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from polje where poljeid =" + field;
      try{
        System.out.println("Brisem u bazu");
        PreparedStatement deleteQuery = conn.prepareStatement(deleteRow);
        deleteQuery.executeUpdate();
        conn.commit();
        deleteQuery.close();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }

    if (mode == UPDATE) {
      String updateRow = "update polje set OBAVEZNOST = ?, PONOVLJIVOST = ?, PO_NAZIV = ?, SEKUNDARNOST = ?, OPIS_DI = ?, OPIS_PI = ?, DEF_PI = ?, DEF_DI =? where POLJEID = ?";
      try {
        PreparedStatement updateQuery = conn.prepareStatement(updateRow);
        setValuesDB(updateQuery, row);
        updateQuery.executeUpdate();
        conn.commit();
        updateQuery.close();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }
    if (mode == INSERT) {
      String insertRow = "insert into polje (OBAVEZNOST, PONOVLJIVOST, PO_NAZIV, SEKUNDARNOST, OPIS_DI, OPIS_PI, DEF_PI, DEF_DI, POLJEID) values (" + "?, ?, ?, ?, ?, ?, ?, ?, ?" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        setValuesDB(insertQuery, row);
        insertQuery.executeUpdate();
        conn.commit();
        insertQuery.close();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  private Connection conn;
  private Vector fieldCodes = new Vector();
  private Vector fieldNames = new Vector();
  private Vector mand = new Vector();
  private Vector repeat = new Vector();
  private Vector sec = new Vector();
  private Vector descFI = new Vector();
  private Vector descSI = new Vector();
  private Vector defValFI = new Vector();
  private Vector defValSI = new Vector();
}