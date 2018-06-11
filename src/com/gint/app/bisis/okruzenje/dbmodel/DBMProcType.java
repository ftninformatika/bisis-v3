package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMProcType extends AbstractTableModel {
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMProcType(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select BIB_BIBID, NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID, FOR_FORMATID from TIP_OBRADE order by BIB_BIBID, NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID, FOR_FORMATID ASC");
      while(rset.next()) {
        librar.addElement(rset.getString(1).trim());
        typePubl.addElement(rset.getString(2));
        noBr.addElement(rset.getString(3));
        noBav.addElement(rset.getString(4));
        form.addElement(rset.getString(5).trim());
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
    switch(col) {
      case 0: return librar.elementAt(row);
      case 1: return typePubl.elementAt(row);
      case 2: return noBr.elementAt(row);
      case 3: return noBav.elementAt(row);
      case 4: return form.elementAt(row);
      default: return librar.elementAt(row);
    }
  }

  public int getRowCount() {
    return librar.size();
  }

  public int getColumnCount() {
    return 5;
  }

  public void removeRow(int row) {
    librar.removeElementAt(row);
    typePubl.removeElementAt(row);
    noBr.removeElementAt(row);
    noBav.removeElementAt(row);
    form.removeElementAt(row);
    keys.removeElementAt(row);
  }
  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)form.elementAt(row));
      query.setString(2,(String)typePubl.elementAt(row));
      query.setInt(3,Integer.parseInt((String)noBr.elementAt(row)));
      query.setInt(4,Integer.parseInt((String)noBav.elementAt(row)));

    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void setValues(int col,Object value,int row,int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: librar.insertElementAt((String)value, row);
                return;
        case 1: typePubl.insertElementAt((String)value, row);
                return;
        case 2: noBr.insertElementAt((String)value, row);
                return;
        case 3: noBav.insertElementAt((String)value, row);
                return;
        case 4: form.insertElementAt((String)value, row);
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
          case 2: noBr.setElementAt((String)value, row);
                  return;
          case 3: noBav.setElementAt((String)value, row);
                  return;
          case 4: form.setElementAt((String)value, row);
                  return;
        }
      }
    }
  }

  public void saveToDB(int mod, String librar, String typePubl, String noBr, String noBav, String format, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from TIP_OBRADE where BIB_BIBID ="+"'"+librar+"'"+
                         " and NOV_NOB_TP_TIPID = "+"'"+typePubl+"'"+" and NOV_NOB_NOBRID = "+"'"+noBr+"'"+" and NOV_NOBAVID = "+"'"+noBav+"'";
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
    if (mod == INSERT) {
      String insertRow = "insert into TIP_OBRADE (FOR_FORMATID, NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID, BIB_BIBID) values (" + "?, ?, ?, ?," +"'"+librar+"'" + " )";
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
    if (mod == UPDATE) {
      String insertRow = "update TIP_OBRADE set FOR_FORMATID =? where NOV_NOB_TP_TIPID = ? and NOV_NOB_NOBRID = ? and NOV_NOBAVID = ? and BIB_BIBID = "+"'"+librar+"'";
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

  public int findKey(String value) {
    if (keys.contains(value))
      return keys.indexOf(value);
    else
      return -1;
  }

  public int findPos(Object lib, Object tpPub, Object nBr, Object nBv, Object fo) {
    if(librar.contains(lib)) {
      int firstLibrar = librar.indexOf(lib);
      int lastLibrar = librar.lastIndexOf(lib);
      int lastTypePubl = ValuePos.myLastIndexOf(tpPub,firstLibrar,lastLibrar,typePubl);
      if(lastTypePubl != -1) {
        int firstTypePubl = typePubl.indexOf(tpPub,firstLibrar);
        int lastNoBr = ValuePos.myLastIndexOf(nBr,firstTypePubl,lastTypePubl,noBr);
        if(lastNoBr != -1) {
          int firstNoBr = noBr.indexOf(nBr, firstTypePubl);
          int lastNoBav = ValuePos.myLastIndexOf(nBv,firstNoBr,lastNoBr,noBav);
          if(lastNoBav != -1) {
            int firstNoBav = noBav.indexOf(nBv, firstNoBr);
            return ValuePos.IndexToInsert(fo,firstNoBr,lastNoBav+1,form,1);
          }
          else {
            return ValuePos.IndexToInsert(nBv,firstNoBr,lastNoBr+1,noBav,0);
          }
        }
        else {
          return ValuePos.IndexToInsert(nBr,firstTypePubl,lastTypePubl+1,noBr,0);
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
                if (j != -1)
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
  Vector noBr = new Vector();
  Vector noBav = new Vector();
  Vector form = new Vector();
  Vector keys = new Vector();
}