package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMSearchPref extends AbstractTableModel {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMSearchPref(Connection conn)  {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select POLJEBPRID, PBPR_NAZIV from POLJEBPR order by POLJEBPRID ASC");
      while(rset.next()) {
        pobprID.addElement(rset.getString(1));
        pbprDesc.addElement(rset.getString(2));
      }
      rset.close();
      stmt.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Object getValueAt(int row, int col) {
    switch(col) {
      case 0: return pobprID.elementAt(row);
      case 1: return pbprDesc.elementAt(row);
      default: return pobprID.elementAt(row);
    }
  }

  public int getRowCount() {
    return pobprID.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public int find(String value) {
    if (pobprID.contains(value))
      return pobprID.indexOf(value);
    else
      return -1;
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: pobprID.insertElementAt((String)value, row);
                return;
        case 1: pbprDesc.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: pobprID.setElementAt((String)value, row);
                  return;
          case 1: pbprDesc.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    pobprID.removeElementAt(row);
    pbprDesc.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)pbprDesc.elementAt(row));
      query.setString(2,(String)pobprID.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, String searchPref, int row){
    /*if (mode == DELETE) {
      String deleteRow = "delete from POLJEBPR where POLJEBPRID =" + "'"+searchPref+"'";
      try{
        PreparedStatement deleteQuery = conn.prepareStatement(deleteRow);
        deleteQuery.executeUpdate();
        //conn.commit();
        deleteQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }  */

    if (mode == UPDATE) {
      String updateRow = "update POLJEBPR set  PBPR_NAZIV=? where POLJEBPRID = ?";
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
   /* if (mode == INSERT) {
      String insertRow = "insert into POLJEBPR (PBPR_NAZIV, POLJEBPRID) values (" + "?, ?" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        setValuesDB(insertQuery, row);
        insertQuery.executeUpdate();
        //conn.commit();
        insertQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }*/
  }

  public int findPos(Object pref) {
    return ValuePos.IndexToInsert(pref,0,pobprID.size(),pobprID);
  }



  Vector pobprID = new Vector();
  Vector pbprDesc = new Vector();
}