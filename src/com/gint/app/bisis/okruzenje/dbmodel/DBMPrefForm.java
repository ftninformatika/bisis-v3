package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMPrefForm extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMPrefForm(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select FOR_FORMATID, PPR_POLJEBPRID from FORMATF_POLJEBPR order by FOR_FORMATID, PPR_POLJEBPRID ASC");
      while(rset.next()) {
        formID.addElement(rset.getString(1).trim());
        prefID.addElement(rset.getString(2));
        keys.addElement(rset.getString(1).trim()+"|"+rset.getString(2).trim());
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
      case 1: return prefID.elementAt(row);
      default: return formID.elementAt(row);
    }
  }

  public int getRowCount() {
    return formID.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public void setValues(Object form, Object pref,Object key,int row) {
    formID.insertElementAt((String)form, row);
    prefID.insertElementAt((String)pref, row);
    keys.insertElementAt((String)key, row);
  }

  public void removeRow(int row) {
    formID.removeElementAt(row);
    prefID.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)formID.elementAt(row));
      query.setString(2,(String)prefID.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mod, String form, Object[] prefix, int row){
    if (mod != INSERT) {
      String deleteRow = "delete from FORMATF_POLJEBPR where FOR_FORMATID ="+"'"+form+"'";
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
    if(mod != DELETE) {
      String insertRow = "insert into FORMATF_POLJEBPR (FOR_FORMATID, PPR_POLJEBPRID) values (" + "?, ?" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        String pref;
        for(int i=0; i < prefix.length; i++) {
          pref = ((String)prefix[i]).substring(0,((String)prefix[i]).indexOf("-"));
          setValues(form,pref,form.trim()+"|"+pref,row+i);
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

  public int find(String value, int num) {
    switch(num) {
      case 0: if (formID.contains(value))
                return formID.indexOf(value);
              else
                return -1;
      case 1: if (keys.contains(value))
                return keys.indexOf(value);
              else
                return -1;
      default: return -1;
    }
  }

  public int findPos(Object form) {
    return ValuePos.IndexToInsert(form,0,formID.size(),formID);
  }

  Vector formID = new Vector();
  Vector prefID = new Vector();
  Vector keys = new Vector();
}