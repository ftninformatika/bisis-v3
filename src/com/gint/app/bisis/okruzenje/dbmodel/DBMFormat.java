package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMFormat extends AbstractTableModel {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMFormat(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select FORMATID, FOR_OPIS from FORMATF order by FORMATID");
      while(rset.next()) {
        formID.addElement(rset.getString(1).trim());
        formDesc.addElement((rset.getString(2) == null)? "" : rset.getString(2));
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
      case 0: return formID.elementAt(row);
      case 1: return formDesc.elementAt(row);
      default: return formID.elementAt(row);
    }
  }

  public int getRowCount() {
    return formID.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public int find(String value) {
    if (formID.contains(value))
      return formID.indexOf(value);
    else
      return -1;
  }

  public int findPos(Object form) {
    return ValuePos.IndexToInsert(form,0,formID.size(),formID);
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: formID.insertElementAt((String)value, row);
                return;
        case 1: formDesc.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: formID.setElementAt((String)value, row);
                  return;
          case 1: formDesc.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }


  public void removeRow(int row) {
    formID.removeElementAt(row);
    formDesc.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)formDesc.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, String key, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from FORMATF where FORMATID =" + "'"+key+"'";
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
      String updateRow = "update FORMATF set FOR_OPIS = ? where FORMATID ="+ "'"+key+"'";
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
    }
    if (mode == INSERT) {
      String insertRow = "insert into FORMATF (FOR_OPIS, FORMATID) values (" + "?,"+"'"+key+"'"+ " )";
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
    }
  }


  Vector formID = new Vector();
  Vector formDesc = new Vector();
}