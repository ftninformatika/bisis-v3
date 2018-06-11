package com.gint.app.bisis.okruzenje.dbmodel;

import javax.swing.table.*;
import java.sql.*;
import java.util.Vector;

public class TableModelTExCode extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public TableModelTExCode(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select TESID, TES_NAZIV from TIPEKST_SIFARNIKA");
      while(rset.next()){
        typeExtCode.addElement(rset.getString(1));
        naTypeExtCode.addElement(rset.getString(2));
      }
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public Object getValueAt(int row, int col) {
    switch (col) {
      case 0: return typeExtCode.elementAt(row);
      case 1: return naTypeExtCode.elementAt(row);
      default: return naTypeExtCode.elementAt(row);
    }
  }

  public int getRowCount() {
    return typeExtCode.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public void setValues(int col, Object obj, int row, int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: typeExtCode.insertElementAt((String)obj, row);
                return;
        case 1: naTypeExtCode.insertElementAt((String)obj, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: typeExtCode.setElementAt((String)obj, row);
                  return;
          case 1: naTypeExtCode.setElementAt((String)obj, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    typeExtCode.removeElementAt(row);
    naTypeExtCode.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1, (String)naTypeExtCode.elementAt(row));
      query.setInt(2, Integer.parseInt((String)typeExtCode.elementAt(row)));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }


  public void saveToDB(int mode, String field, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from polje where tipekst_sifarnika =" + field;
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
      String updateRow = "update polje set TES_NAZIV = ? where TESID = ?";
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
      String insertRow = "insert into polje (TES_NAZIV, TESID) values (" + "?, ?" + " )";
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

  Vector typeExtCode = new Vector();
  Vector naTypeExtCode = new Vector();
}