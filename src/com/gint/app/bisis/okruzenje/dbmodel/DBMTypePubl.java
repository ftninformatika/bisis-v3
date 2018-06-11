package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMTypePubl extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMTypePubl(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select TIPID, TIP_NAZIV from TIP_PUBLIKACIJE order by TIPID ASC");
      while(rset.next()) {
        typePubl.addElement(rset.getString(1));
        naTypePubl.addElement(rset.getString(2));
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
      case 0: return typePubl.elementAt(row);
      case 1: return naTypePubl.elementAt(row);
      default: return typePubl.elementAt(row);
    }
  }

  public int getRowCount() {
    return typePubl.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public int findKey(String value) {
    if (typePubl.contains(value))
      return typePubl.indexOf(value);
    else
      return -1;
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: typePubl.insertElementAt((String)value, row);
                return;
        case 1: naTypePubl.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: typePubl.setElementAt((String)value, row);
                  return;
          case 1: naTypePubl.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    typePubl.removeElementAt(row);
    naTypePubl.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)naTypePubl.elementAt(row));
      query.setString(2,(String)typePubl.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, String typePub, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from tip_publikacije where tipid =" + "'"+typePub+"'";
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
      String updateRow = "update TIP_PUBLIKACIJE set TIP_NAZIV =? where TIPID = ?";
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
      String insertRow = "insert into TIP_PUBLIKACIJE (TIP_NAZIV, TIPID) values (" + "?, ?" + " )";
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


  public int findPos(Object tpPub) {
    return ValuePos.IndexToInsert(tpPub,0,typePubl.size(),typePubl);
  }

  Vector typePubl = new Vector();
  Vector naTypePubl = new Vector();
}