package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class DBMPrefProcTp extends AbstractTableModel{
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMPrefProcTp(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select TO_BIB_BIBID, TO_NOV_NOB_TP_TIPID, TO_NOV_NOB_NOBRID, TO_NOV_NOBAVID, PPR_POLJEBPRID from POLJEBPR_TIP_OBRADE order by  TO_BIB_BIBID, TO_NOV_NOB_TP_TIPID, TO_NOV_NOB_NOBRID, TO_NOV_NOBAVID, PPR_POLJEBPRID ASC");
      while(rset.next()) {
        librar.addElement(rset.getString(1).trim());
        typePubl.addElement(rset.getString(2));
        noBr.addElement(rset.getString(3));
        noBav.addElement(rset.getString(4));
        pref.addElement(rset.getString(5));
        keys.addElement(rset.getString(1).trim()+"|"+rset.getString(2).trim()+"|"+rset.getString(3).trim()+"|"+rset.getString(4).trim()+"|"+rset.getString(5).trim());
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
      case 4: return pref.elementAt(row);
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
    pref.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValues(Object libr, Object tpPub, Object noB, Object noBv, Object prefx, Object key, int row) {
    librar.insertElementAt((String)libr, row);
    typePubl.insertElementAt((String)tpPub, row);
    noBr.insertElementAt((String)noB, row);
    noBav.insertElementAt((String)noBv, row);
    pref.insertElementAt((String)prefx, row);
    keys.insertElementAt((String)key, row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)librar.elementAt(row));
      query.setString(2,(String)typePubl.elementAt(row));
      query.setInt(3,Integer.parseInt((String)noBr.elementAt(row)));
      query.setInt(4,Integer.parseInt((String)noBav.elementAt(row)));
      query.setString(5,(String)pref.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }


  public void saveToDB(int mod, String librar, String typePubl, String noBr, String noBav, Object[] prefix, int row){
    if (mod != INSERT) {
      String deleteRow = "delete from POLJEBPR_TIP_OBRADE where TO_BIB_BIBID ="+"'"+librar+"'"+
                         " and TO_NOV_NOB_TP_TIPID = "+"'"+typePubl+"'"+" and TO_NOV_NOB_NOBRID = "+"'"+noBr+"'"+" and TO_NOV_NOBAVID = "+"'"+noBav+"'";
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
      String insertRow = "insert into POLJEBPR_TIP_OBRADE (TO_BIB_BIBID, TO_NOV_NOB_TP_TIPID, TO_NOV_NOB_NOBRID, TO_NOV_NOBAVID, PPR_POLJEBPRID) values (" + "?, ?, ?, ?, ?" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        String pref;
        for(int i=0; i < prefix.length; i++) {
          pref = ((String)prefix[i]).substring(0,((String)prefix[i]).indexOf("-"));
          setValues(librar,typePubl,noBr,noBav,pref,librar.trim()+"|"+typePubl.trim()+"|"+noBr.trim()+"|"+noBav.trim()+"|"+pref,row+i);
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
                int j = ((String)keys.get(i)).indexOf("|",lengths[0]+lengths[1]+lengths[2]+3);
                if(j != -1)
                  if (((String)keys.get(i)).substring(0,j).equals(value)) {
                    return i;
                   }
              }
              if (i == keys.size()) {
                return -1;
              }
      case 4: for (i=0; i<keys.size(); i++) {
                if (((String)keys.get(i)).substring(0).equals(value))
                  return i;
              }
              if (i == keys.size()) {
                return -1;
              }
      default : return -1;
    }
  }


  public int findKey(String value) {
    if (keys.contains(value))
      return keys.indexOf(value);
    else
      return -1;
  }

  public int findPos(Object lib, Object tpPub, Object nBr, Object nBv) {
    if(librar.contains(lib)) {
      int firstLibrar = librar.indexOf(lib);
      int lastLibrar = librar.lastIndexOf(lib);
      int lastTypePubl = ValuePos.myLastIndexOf(tpPub,firstLibrar,lastLibrar,typePubl);
      if(lastTypePubl != -1) {
        int firstTypePubl = typePubl.indexOf(tpPub,firstLibrar);
        int lastNoBr = ValuePos.myLastIndexOf(nBr,firstTypePubl,lastTypePubl,noBr);
        if(lastNoBr != -1) {
          int firstNoBr = noBr.indexOf(nBr, firstTypePubl);
          return ValuePos.IndexToInsert(nBv,firstNoBr,lastNoBr+1,noBav,0);
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


  Vector librar = new Vector();
  Vector typePubl = new Vector();
  Vector noBr = new Vector();
  Vector noBav = new Vector();
  Vector pref = new Vector();
  Vector keys = new Vector();
}
