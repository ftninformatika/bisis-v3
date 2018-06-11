package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class DBMProcLevel extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMProcLevel(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select TP_TIPID, NOBRID, NOBR_NAZIV from NIVO_OBRADE order by TP_TIPID, NOBRID ASC");
      while(rset.next()) {
        tipID.addElement(rset.getString(1));
        nobrID.addElement(rset.getString(2));
        nobrDesc.addElement(rset.getString(3));
        keys.addElement(rset.getString(1).trim()+rset.getString(2).trim());
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
      case 0: return tipID.elementAt(row);
      case 1: return nobrID.elementAt(row);
      case 2: return nobrDesc.elementAt(row);
      default: return tipID.elementAt(row);
    }
  }

  public int getRowCount() {
    return nobrID.size();
  }

  public int getColumnCount() {
    return 3;
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: tipID.insertElementAt((String)value, row);
                return;
        case 1: nobrID.insertElementAt((String)value, row);
                return;
        case 2: nobrDesc.insertElementAt((String)value, row);
                return;
        case 3: keys.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: tipID.setElementAt((String)value, row);
                  return;
          case 1: nobrID.setElementAt((String)value, row);
                  return;
          case 2: nobrDesc.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    tipID.removeElementAt(row);
    nobrID.removeElementAt(row);
    nobrDesc.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)nobrDesc.elementAt(row));
      query.setString(2,(String)tipID.elementAt(row));
      query.setInt(3,Integer.parseInt((String)nobrID.elementAt(row)));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, Vector key, int numKeys, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from NIVO_OBRADE where TP_TIPID =" + "'"+(String)key.elementAt(0)+"'"+
                         "and NOBRID ="+Integer.parseInt((String)key.elementAt(1));
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
      String updateRow = "update NIVO_OBRADE set NOBR_NAZIV =? where TP_TIPID = ? and NOBRID = ?";
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
      String insertRow = "insert into NIVO_OBRADE (NOBR_NAZIV, TP_TIPID, NOBRID) values (" + "?, ?, ?" + " )";
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

  int myIndexOf(Object value, int dg, int gg,Vector v, int type) {
    int i = dg;
    int j;
    for (j = dg; j < gg; j++) {
      switch(type) {
        case 0: if(new Integer(((String)value).trim()).compareTo(new Integer(((String)v.elementAt(j)).trim())) == 0)
                  return -1;
                if(new Integer(((String)value).trim()).compareTo(new Integer(((String)v.elementAt(j)).trim())) < 0)
                  return j;
        case 1: if(((String)value).trim().compareTo(((String)v.elementAt(j)).trim()) == 0)
                  return -1;
                if(((String)value).trim().compareTo(((String)v.elementAt(j)).trim()) < 0)
                  return j;
      }
    }
    if(j == gg-1)
      return j;
    else
      return j++;
  }

  public int findPos(Object tpPub, Object noBr) {
    if(tipID.contains(tpPub)) {
      int first = tipID.indexOf(tpPub);
      int last = tipID.lastIndexOf(tpPub)+1;
      return ValuePos.IndexToInsert(noBr,first,last,nobrID,0);
    }
    else {
      return ValuePos.IndexToInsert(tpPub,0,tipID.size(),tipID,1);
    }
  }

  public int find (String value, int num) {
    int i;
    switch(num) {
      case 1: for (i=0; i<keys.size(); i++) {
                if (((String)keys.get(i)).substring(0,2).equals(value))
                  return i;
              }
              if (i == keys.size()) {
                return -1;
              }
      case 2: for (i=0; i<keys.size(); i++) {
                if (((String)keys.get(i)).substring(0).equals(value))
                  return i;
              }
              if (i == keys.size()) {
                return -1;
              }
      default : return -1;
    }
  }


  Vector tipID = new Vector();
  Vector nobrID = new Vector();
  Vector nobrDesc = new Vector();
  Vector keys = new Vector();
}