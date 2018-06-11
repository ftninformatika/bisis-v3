package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;


public class DBMMandLevel extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMMandLevel(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select NOB_TP_TIPID, NOB_NOBRID, NOBAVID, NOBAV_NAZIV from NIVO_OBAVEZNOSTI order by NOB_TP_TIPID, NOB_NOBRID, NOBAVID ASC");
      while(rset.next()) {
        typPubID.addElement(rset.getString(1));
        nobrID.addElement(rset.getString(2));
        nobavID.addElement(rset.getString(3));
        nobavDesc.addElement(rset.getString(4));
        keys.addElement(rset.getString(1)+"|"+rset.getString(2)+"|"+rset.getString(3));
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
      case 0: return typPubID.elementAt(row);
      case 1: return nobrID.elementAt(row);
      case 2: return nobavID.elementAt(row);
      case 3: return nobavDesc.elementAt(row);
      default: return nobavID.elementAt(row);
    }
  }

  public int getRowCount() {
    return nobavID.size();
  }

  public int getColumnCount() {
    return 4;
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: typPubID.insertElementAt((String)value, row);
                return;
        case 1: nobrID.insertElementAt((String)value, row);
                return;
        case 2: nobavID.insertElementAt((String)value, row);
                return;
        case 3: nobavDesc.insertElementAt((String)value, row);
                return;
        case 4: keys.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: typPubID.setElementAt((String)value, row);
                  return;
          case 1: nobrID.setElementAt((String)value, row);
                  return;
          case 2: nobavDesc.setElementAt((String)value, row);
                  return;
          case 3: nobavDesc.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    typPubID.removeElementAt(row);
    nobrID.removeElementAt(row);
    nobavID.removeElementAt(row);
    nobavDesc.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)nobavDesc.elementAt(row));
      query.setString(2,(String)typPubID.elementAt(row));
      query.setInt(3,Integer.parseInt((String)nobrID.elementAt(row)));
      query.setInt(4,Integer.parseInt((String)nobavID.elementAt(row)));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, Vector key, int numKeys, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from NIVO_OBAVEZNOSTI where NOB_TP_TIPID =" + "'"+(String)key.elementAt(0)+"'"+
                         " and NOB_NOBRID ="+Integer.parseInt((String)key.elementAt(1))+
                         " and NOBAVID ="+Integer.parseInt((String)key.elementAt(2));
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
      String updateRow = "update NIVO_OBAVEZNOSTI set NOBAV_NAZIV =? where NOB_TP_TIPID = ? and NOB_NOBRID = ? and NOBAVID = ?";
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
      String insertRow = "insert into NIVO_OBAVEZNOSTI (NOBAV_NAZIV, NOB_TP_TIPID, NOB_NOBRID, NOBAVID) values (" + "?, ?, ?, ?" + " )";
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

  public int findPos(Object tpPub, Object noBr, Object noBav) {
    if(typPubID.contains(tpPub)) {
      int firstTpPub = typPubID.indexOf(tpPub);
      int lastTpPub = typPubID.lastIndexOf(tpPub);
      int lastNoBr = ValuePos.myLastIndexOf(noBr,firstTpPub,lastTpPub,nobrID);
      if(lastNoBr != -1) {
        int firstNoBr = nobrID.indexOf(noBr,firstTpPub);
        return ValuePos.IndexToInsert(noBav,firstNoBr,lastNoBr+1,nobavID,0);
      }
      else {
        return ValuePos.IndexToInsert(noBr,firstTpPub,lastTpPub+1,nobrID,0);
      }
    }
    else {
      return ValuePos.IndexToInsert(tpPub,0,typPubID.size(),typPubID,1);
    }
  }

  public int find (String value, int[] lengths, int num) {
    int i;
    switch(num) {
      case 1: for (i=0; i<keys.size(); i++) {
                int j = ((String)keys.get(i)).indexOf("|",0);
                if(j != -1)
                  if (((String)keys.get(i)).substring(0,j).equals(value))
                    return i;
              }
              if (i == keys.size()) {
                return -1;
              }
      case 2: for (i=0; i<keys.size(); i++) {
                int j = ((String)keys.get(i)).indexOf("|",lengths[0]+1);
                  if(j != -1)
                    if (((String)keys.get(i)).substring(0,j).equals(value))
                      return i;
                }
                if (i == keys.size()) {
                  return -1;
                }
      case 3: for (i=0; i<keys.size(); i++) {
                if (((String)keys.get(i)).substring(0).equals(value))
                  return i;
              }
              if (i == keys.size()) {
                return -1;
              }
      default : return -1;
    }
  }

  Vector typPubID = new Vector();
  Vector nobrID = new Vector();
  Vector nobavID = new Vector();
  Vector nobavDesc = new Vector();
  Vector keys = new Vector();
}