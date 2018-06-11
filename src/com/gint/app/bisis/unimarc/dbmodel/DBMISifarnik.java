package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMISifarnik extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMISifarnik(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct PPO_PO_POLJEID,PPO_POTPOLJAID,ISID,IS_NAZIV from interni_sifarnik order by PPO_PO_POLJEID,PPO_POTPOLJAID,ISID");
      while(rset.next()) {
        fieldId.addElement(rset.getString("PPO_PO_POLJEID"));
        sfieldId.addElement(rset.getString("PPO_POTPOLJAID"));
        isfId.addElement(rset.getString("ISID"));
        isfNaziv.addElement(rset.getString("IS_NAZIV"));
        keys.addElement(rset.getString("PPO_PO_POLJEID")+"|"+rset.getString("PPO_POTPOLJAID")+"|"+rset.getString("ISID"));
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
      case 0: return fieldId.elementAt(row);
      case 1: return sfieldId.elementAt(row);
      case 2: return isfId.elementAt(row);
      case 3: return isfNaziv.elementAt(row);
      default: return isfId.elementAt(row);
    }
  }

  public int getRowCount() {
    return isfId.size();
  }

  public int getColumnCount() {
    return 4;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      fieldId.insertElementAt(fld,row);
      sfieldId.insertElementAt(sfld,row);
      isfId.insertElementAt(isf,row);
      isfNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(fld+"|"+sfld+"|"+isf,row);
    }
    else if (mod == UPDATE) {
      isfNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    isfId.removeElementAt(row);
    isfNaziv.removeElementAt(row);
    fieldId.removeElementAt(row);
    sfieldId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from interni_sifarnik where PPO_PO_POLJEID="+Integer.parseInt(fld)+
                        " and PPO_POTPOLJAID='"+sfld+"'and ISID='"+isf+"'";
      try{
      	Statement stmt = conn.createStatement();
        int rez = stmt.executeUpdate(deleteRow);
        stmt.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }

    if (mod == UPDATE) {
      String updateRow = "update interni_sifarnik set IS_NAZIV ='"+naziv+"' where PPO_PO_POLJEID="+Integer.parseInt(fld)+
                        " and PPO_POTPOLJAID='"+sfld+"' and ISID='"+isf+"'";
      try {
        Statement stmt = conn.createStatement();
        int rez = stmt.executeUpdate(updateRow);
        stmt.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }
    if (mod == INSERT) {
      String insertRow = "insert into interni_sifarnik (PPO_PO_POLJEID,PPO_POTPOLJAID,ISID,IS_NAZIV) values ('"+fld+"','"+sfld+ "'"+
                        ",'"+isf+ "'"+",'"+naziv+ "')";
      try {
        Statement stmt = conn.createStatement();
        int rez = stmt.executeUpdate(insertRow);
        stmt.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public int find (String value, int lengths) {
    int i=0;
    for (i=0; i<keys.size(); i++) {
      if(((String)keys.get(i)).length() >= lengths) {
        if (((String)keys.get(i)).substring(0,lengths).equals(value)) {
  	  return i;
  	}
      }
    }
    return -1;
  }

  public int findPos(Object fld,Object sfld,Object isfr) {
    if(fieldId.contains(fld)) {
      int firstFld = fieldId.indexOf(fld);
      int lastFld = fieldId.lastIndexOf(fld);
      int lastSfld = Position.myLastIndexOf(sfld,firstFld,lastFld,sfieldId);
      if(lastSfld != -1) {
        int firstSfld = sfieldId.indexOf(sfld,firstFld);
        int lastIsfr = Position.myLastIndexOf(isfr,firstSfld,lastSfld,isfId);
       	if(lastIsfr != -1) {
       	  int firstIsfr = isfId.indexOf(isfr,firstSfld);
       	  return Position.IndexToInsert(isfr,firstIsfr,lastIsfr+1,isfId);
       	}
       	else {
       	  return Position.IndexToInsert(isfr,firstSfld,lastSfld+1,isfId);
       	}
      }
      else {
        return Position.IndexToInsert(sfld,firstFld,lastFld+1,sfieldId);
      }
    }
    else {
      return Position.IndexToInsert(fld,0,fieldId.size(),fieldId,1);
    }
  }

  public void setISifarnik(String isf) {
    this.isf = isf;
  }
  public void setNazivISifarnik(String naziv) {
    this.naziv = naziv;
  }
  public void setField(String fld) {
    this.fld = fld;
  }
  public void setSField(String sfld) {
    this.sfld = sfld;
  }
  Vector fieldId = new Vector();
  Vector sfieldId = new Vector();
  Vector isfId = new Vector();
  Vector isfNaziv = new Vector();
  Vector keys = new Vector();
  String isf = "";
  String naziv = "";
  String fld = "";
  String sfld = "";
}