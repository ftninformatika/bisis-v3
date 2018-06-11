package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMSFSearchPref extends AbstractTableModel{
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMSFSearchPref(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select PREF_NAME, SUBFIELD_ID from PREFIX_MAP order by PREF_NAME, SUBFIELD_ID ASC");
      while(rset.next()) {
        pref.addElement(rset.getString(1));
        field.addElement(rset.getString(2).substring(0,3));
        subField.addElement(rset.getString(2).substring(3));
        keys.addElement(rset.getString(1).trim()+"|"+rset.getString(2).substring(0,3).trim()+"|"+rset.getString(2).substring(3).trim());
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
      case 0: return pref.elementAt(row);
      case 1: return field.elementAt(row);
      case 2: return subField.elementAt(row);
      default: return pref.elementAt(row);
    }
  }

  public int getRowCount() {
    return pref.size();
  }

  public int getColumnCount() {
    return 3;
  }

  public void removeRow(int row) {
    pref.removeElementAt(row);
    field.removeElementAt(row);
    subField.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValues(Object pre, Object fi, Object sf, Object key,int row) {
    pref.insertElementAt((String)pre, row);
    field.insertElementAt((String)fi, row);
    subField.insertElementAt((String)sf, row);
    keys.insertElementAt((String)key, row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)pref.elementAt(row));
      query.setString(2,(String)field.elementAt(row));
      query.setString(3,(String)subField.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mod, String pref, String field, Object[] subFields, int row){
    if (mod != INSERT) {
      String deleteRow = "delete from POLJEBPR_POTPOLJA where PPR_POLJEBPRID ="+"'"+pref+"'"+
                         " and PPO_PO_POLJEID = "+"'"+field+"'";
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
    if (mod != DELETE) {
      String insertRow = "insert into POLJEBPR_POTPOLJA (PPR_POLJEBPRID, PPO_PO_POLJEID, PPO_POTPOLJAID) values (" + "?, ?, ?" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        String sf;
        for(int i=0; i < subFields.length; i++) {
          sf = ((String)subFields[i]).substring(0,((String)subFields[i]).indexOf("-"));
          setValues(pref,field,sf,pref.trim()+"|"+field.trim()+"|"+sf,row+i);
          setValuesDB(insertQuery, row+i);
          insertQuery.executeUpdate();
        }
        insertQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public int findKey(String value) {
    if (keys.contains(value))
      return keys.indexOf(value);
    else
      return -1;
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

  Vector pref = new Vector();
  Vector field = new Vector();
  Vector subField = new Vector();
  Vector keys = new Vector();
}
