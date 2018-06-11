package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMSFTypePubl extends AbstractTableModel{
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMSFTypePubl(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select TP_TIPID, PPO_PO_POLJEID, PPO_POTPOLJAID, DEFVR_TIPA from DEF_TIPA order by TP_TIPID, PPO_PO_POLJEID, PPO_POTPOLJAID ASC");
      while(rset.next()) {
        typePubl.addElement(rset.getString(1));
        field.addElement(rset.getString(2));
        subField.addElement(rset.getString(3));
        defVal.addElement((rset.getString(4) == null)? "" : rset.getString(4));
        keys.addElement(rset.getString(1).trim()+"|"+rset.getString(2).trim()+"|"+rset.getString(3).trim());
      }
      rset.close();
      stmt.close();

    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public Object getValueAt(int row, int col) {
    switch (col) {
      case 0: return typePubl.elementAt(row);
      case 1: return field.elementAt(row);
      case 2: return subField.elementAt(row);
      case 3: return defVal.elementAt(row);
      default: return typePubl.elementAt(row);
    }
  }

  public int getRowCount() {
    return typePubl.size();
  }

  public int getColumnCount() {
    return 4;
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: typePubl.insertElementAt((String)value, row);
                return;
        case 1: field.insertElementAt((String)value, row);
                return;
        case 2: subField.insertElementAt((String)value, row);
                return;
        case 3: defVal.insertElementAt((String)value, row);
                return;
        case 4: keys.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: typePubl.setElementAt((String)value, row);
                  return;
          case 1: field.setElementAt((String)value, row);
                  return;
          case 2: subField.setElementAt((String)value, row);
                  return;
          case 3: defVal.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    typePubl.removeElementAt(row);
    field.removeElementAt(row);
    subField.removeElementAt(row);
    defVal.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)defVal.elementAt(row));
      query.setString(2,(String)typePubl.elementAt(row));
      query.setString(3,(String)field.elementAt(row));
      query.setString(4,(String)subField.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, Vector key, int numKeys, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from DEF_TIPA where TP_TIPID =" + "'"+(String)key.elementAt(0)+"'"+
                         " and PPO_PO_POLJEID="+"'"+(String)key.elementAt(1)+"'"+
                         " and PPO_POTPOLJAID="+"'"+(String)key.elementAt(2)+"'";
      try{
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
      String updateRow = "update DEF_TIPA set DEFVR_TIPA =? where TP_TIPID = ? and PPO_PO_POLJEID = ? and PPO_POTPOLJAID = ?";
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
      String insertRow = "insert into DEF_TIPA (DEFVR_TIPA, TP_TIPID, PPO_PO_POLJEID, PPO_POTPOLJAID) values (" + "?, ?, ?, ?" + " )";
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

  public int findPos(Object tpPub, Object fi, Object subFi) {
    if(typePubl.contains(tpPub)) {
      int firstTpPub = typePubl.indexOf(tpPub);
      int lastTpPub = typePubl.lastIndexOf(tpPub);
      int lastField = ValuePos.myLastIndexOf(fi,firstTpPub,lastTpPub,field);
      if(lastField != -1) {
        int firstField = field.indexOf(fi,firstTpPub);
        return ValuePos.IndexToInsert(subFi,firstField,lastField+1,subField,1);
      }
      else {
        return ValuePos.IndexToInsert(fi,firstTpPub,lastTpPub+1,field,0);
      }
    }
    else {
      return ValuePos.IndexToInsert(tpPub,0,typePubl.size(),typePubl,1);
    }
  }

 public int find (String value, int[] lengths, int num) {
    int i;
    switch(num) {
      case 0: for (i=0; i<keys.size(); i++) {
                int j = ((String)keys.get(i)).indexOf("|",0);
                if(j != -1)
                  if (((String)keys.get(i)).substring(0,j).equals(value))
                    return i;
              }
              if (i == keys.size()) {
                return -1;
              }
      case 1: for (i=0; i<keys.size(); i++) {
                int j = ((String)keys.get(i)).indexOf("|",lengths[0]+1);
                if(j != -1)
                  if (((String)keys.get(i)).substring(0,j).equals(value))
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

  Vector field = new Vector();
  Vector subField = new Vector();
  Vector typePubl = new Vector();
  Vector defVal = new Vector();
  Vector keys = new Vector();
}