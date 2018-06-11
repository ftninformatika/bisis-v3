package com.gint.app.bisis.okruzenje.dbmodel;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class DBMSFLibrar extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMSFLibrar(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select BIB_BIBID, TP_TIPID, PPO_PO_POLJEID, PPO_POTPOLJAID, DEFVR_TIPBIB from TIPBIBLIOTEKARA order by BIB_BIBID, TP_TIPID, PPO_PO_POLJEID, PPO_POTPOLJAID ASC");
      while(rset.next()) {
        librar.addElement(rset.getString(1).trim());
        typePubl.addElement(rset.getString(2));
        field.addElement(rset.getString(3));
        subField.addElement(rset.getString(4));
        defVal.addElement((rset.getString(5) == null)? "" : rset.getString(5));
        keys.addElement(rset.getString(1).trim()+"|"+rset.getString(2).trim()+"|"+rset.getString(3).trim()+"|"+rset.getString(4).trim());
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
       case 0: return librar.elementAt(row);
       case 1: return typePubl.elementAt(row);
       case 2: return field.elementAt(row);
       case 3: return subField.elementAt(row);
       case 4: return defVal.elementAt(row);
       default: return librar.elementAt(row);
    }
  }

  public int getRowCount() {
    return librar.size();
  }

  public int getColumnCount() {
    return 5;
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: librar.insertElementAt((String)value, row);
                return;
        case 1: typePubl.insertElementAt((String)value, row);
                return;
        case 2: field.insertElementAt((String)value, row);
                return;
        case 3: subField.insertElementAt((String)value, row);
                return;
        case 4: defVal.insertElementAt((String)value, row);
                return;
        case 5: keys.insertElementAt((String)value, row);
                return;
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
          case 0: librar.setElementAt((String)value, row);
                  return;
          case 1: typePubl.setElementAt((String)value, row);
                  return;
          case 2: field.setElementAt((String)value, row);
                  return;
          case 3: subField.setElementAt((String)value, row);
                  return;
          case 4: defVal.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void removeRow(int row) {
    librar.removeElementAt(row);
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
      //query.setString(5,(String)librar.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mode, Vector key, int numKeys, int row){
    if (mode == DELETE) {
      String deleteRow = "delete from TIPBIBLIOTEKARA where BIB_BIBID =" + "'"+(String)key.elementAt(0)+"'"+
                         " and TP_TIPID="+"'"+(String)key.elementAt(1)+"'"+
                         " and PPO_PO_POLJEID="+"'"+(String)key.elementAt(2)+"'"+
                         " and PPO_POTPOLJAID="+"'"+(String)key.elementAt(3)+"'";
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
      String updateRow = "update TIPBIBLIOTEKARA set DEFVR_TIPBIB =? where TP_TIPID = ? and PPO_PO_POLJEID = ? and PPO_POTPOLJAID = ? and BIB_BIBID =" + "'"+(String)key.elementAt(0)+"'";
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
      String insertRow = "insert into TIPBIBLIOTEKARA (DEFVR_TIPBIB, TP_TIPID, PPO_PO_POLJEID, PPO_POTPOLJAID, BIB_BIBID) values (" + "?, ?, ?, ?,"+"'"+(String)key.elementAt(0)+"'"+ " )";
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

  public int findPos(Object lib, Object tpPub, Object fi, Object subFi) {
    if(librar.contains(lib)) {
      int firstLibrar = librar.indexOf(lib);
      int lastLibrar = librar.lastIndexOf(lib);
      int lastTypPub = ValuePos.myLastIndexOf(tpPub,firstLibrar,lastLibrar,typePubl);
      if(lastTypPub != -1) {
        int firstTypPub = typePubl.indexOf(tpPub,firstLibrar);
        int lastField = ValuePos.myLastIndexOf(fi,firstTypPub,lastTypPub,field);
        if(lastField != -1) {
          int firstField = field.indexOf(fi, firstTypPub);
          return ValuePos.IndexToInsert(subFi,firstField,lastField+1,subField,1);
        }
        else {
          return ValuePos.IndexToInsert(fi,firstTypPub,lastTypPub+1,field,0);
        }
      }
      else {
        return ValuePos.IndexToInsert(tpPub,firstLibrar,lastLibrar+1,typePubl,1);
      }
    }
    else {
      return ValuePos.IndexToInsert(lib,0,librar.size(),librar,1);
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
                int j = ((String)keys.get(i)).indexOf("|",lengths[0]+lengths[1]+2);
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

  Vector librar = new Vector();
  Vector typePubl = new Vector();
  Vector field = new Vector();
  Vector subField = new Vector();
  Vector defVal = new Vector();
  Vector keys = new Vector();
}