package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class DBMSFMandLev extends AbstractTableModel{
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  Connection conn;

  public DBMSFMandLev(Connection conn) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID, PPO_PO_POLJEID, PPO_POTPOLJAID from NIVO_OBAVEZNOSTI_POTPOLJA order by NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID, PPO_PO_POLJEID, PPO_POTPOLJAID ASC");
      while(rset.next()) {
        typePubl.addElement(rset.getString(1));
        noBr.addElement(rset.getString(2));
        noBav.addElement(rset.getString(3));
        field.addElement(rset.getString(4));
        subField.addElement(rset.getString(5));
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
      case 0: return typePubl.elementAt(row);
      case 1: return noBr.elementAt(row);
      case 2: return noBav.elementAt(row);
      case 3: return field.elementAt(row);
      case 4: return subField.elementAt(row);
      default: return typePubl.elementAt(row);
    }
  }

  public int getRowCount() {
    return noBav.size();
  }

  public int getColumnCount() {
    return 5;
  }

  public void removeRow(int row) {
    typePubl.removeElementAt(row);
    noBr.removeElementAt(row);
    noBav.removeElementAt(row);
    field.removeElementAt(row);
    subField.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void setValues(Object tpPub, Object noB, Object noBv, Object fi, Object sf, Object key, int row) {
    typePubl.insertElementAt((String)tpPub, row);
    noBr.insertElementAt((String)noB, row);
    noBav.insertElementAt((String)noBv, row);
    field.insertElementAt((String)fi, row);
    subField.insertElementAt((String)sf, row);
    keys.insertElementAt((String)key, row);
  }

  public void setValuesDB(PreparedStatement query, int row) {
    try {
      query.setString(1,(String)typePubl.elementAt(row));
      query.setInt(2,Integer.parseInt((String)noBr.elementAt(row)));
      query.setInt(3,Integer.parseInt((String)noBav.elementAt(row)));
      query.setString(4,(String)field.elementAt(row));
      query.setString(5,(String)subField.elementAt(row));
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveToDB(int mod, String typePubl, String noBr, String noBav, String field, Object[] subFields, int row){
    if (mod != INSERT) {
      String deleteRow = "delete from NIVO_OBAVEZNOSTI_POTPOLJA where NOV_NOB_TP_TIPID ="+"'"+typePubl+"'"+
                         " and NOV_NOB_NOBRID = "+"'"+noBr+"'"+" and NOV_NOBAVID = "+"'"+noBav+"'"+" and PPO_PO_POLJEID = "+"'"+field+"'";
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
      String insertRow = "insert into NIVO_OBAVEZNOSTI_POTPOLJA (NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID, PPO_PO_POLJEID, PPO_POTPOLJAID) values (" + "?, ?, ?, ?, ?" + " )";
      try {
        PreparedStatement insertQuery = conn.prepareStatement(insertRow);
        String sf;
        for(int i=0; i < subFields.length; i++) {
          sf = ((String)subFields[i]).substring(0,((String)subFields[i]).indexOf("-"));
          setValues(typePubl,noBr,noBav,field,sf,typePubl.trim()+"|"+noBr.trim()+"|"+noBav.trim()+"|"+field.trim()+"|"+sf,row+i);
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

  public int findPos(Object tpPub, Object nBr, Object nBv, Object fi) {
    if(typePubl.contains(tpPub)) {
      int firstTpPub = typePubl.indexOf(tpPub);
      int lastTpPub = typePubl.lastIndexOf(tpPub);
      int lastNoBr = ValuePos.myLastIndexOf(nBr,firstTpPub,lastTpPub,noBr);
      if(lastNoBr != -1) {
        int firstNoBr = noBr.indexOf(nBr,firstTpPub);
        int lastNoBav = ValuePos.myLastIndexOf(nBv,firstNoBr,lastNoBr,noBav);
        if(lastNoBav != -1) {
          int firstNoBav = noBav.indexOf(nBv, firstNoBr);
          return ValuePos.IndexToInsert(fi,firstNoBav,lastNoBav+1,field,0);
        }
        else {
          return ValuePos.IndexToInsert(nBv,firstNoBr,lastNoBr+1,noBav,0);
        }
      }
      else {
        return ValuePos.IndexToInsert(nBr,firstTpPub,lastTpPub+1,noBr,0);
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
                  if (((String)keys.get(i)).substring(0,j).equals(value))
                    return i;
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

  Vector typePubl = new Vector();
  Vector noBr = new Vector();
  Vector noBav = new Vector();
  Vector field = new Vector();
  Vector subField = new Vector();
  Vector keys =  new Vector();
}