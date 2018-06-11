package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMDIndikator extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMDIndikator(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct PO_POLJEID,DRINDID,DI_NAZIV from drugi_indikator order by PO_POLJEID,DRINDID");
      while(rset.next()) {
        fldId.addElement(rset.getString("PO_POLJEID"));
        drindId.addElement(rset.getString("DRINDID"));
        drindNaziv.addElement(rset.getString("DI_NAZIV"));
        keys.addElement(rset.getString("PO_POLJEID")+"|"+rset.getString("DRINDID"));
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
      case 0: return fldId.elementAt(row);
      case 1: return drindId.elementAt(row);
      case 2: return drindNaziv.elementAt(row);
      default: return drindId.elementAt(row);
    }
  }

  public int getRowCount() {
    return drindId.size();
  }

  public int getColumnCount() {
    return 3;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      fldId.insertElementAt(fld,row);
      drindId.insertElementAt(drind,row);
      drindNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(fld+"|"+drind,row);
    }
    else if (mod == UPDATE) {
      drindNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    drindId.removeElementAt(row);
    drindNaziv.removeElementAt(row);
    fldId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from drugi_indikator where PO_POLJEID='"+fld+
                        "' and DRINDID='"+drind+"'";
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
      String updateRow = "update drugi_indikator set DI_NAZIV ='"+naziv+"' where PO_POLJEID='"+fld+
                          "' and DRINDID='"+drind+"'";
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
      String insertRow = "insert into drugi_indikator (PO_POLJEID,DRINDID,DI_NAZIV) values ('"+fld+"','"+drind+ "'"+",'"+naziv+ "')";
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

  public int findPos(Object fld,Object drind) {
    if(fldId.contains(fld)) {
      int firstFld = fldId.indexOf(fld);
      int lastFld = fldId.lastIndexOf(fld);
      int lastDrind = Position.myLastIndexOf(drind,firstFld,lastFld,drindId);
      if(lastDrind != -1) {
      	int firstDrind = drindId.indexOf(drind,firstFld);
       	return Position.IndexToInsert(drind,firstDrind,lastDrind+1,drindId);
      }
      else {
      	return Position.IndexToInsert(drind,firstFld,lastFld+1,drindId);
      }
    }
    else {
      return Position.IndexToInsert(fld,0,fldId.size(),fldId,1);
    }
  }

  public void setField(String fld) {
  	this.fld = fld;
  }
  public void setNazivDrind(String naziv) {
  	this.naziv = naziv;
  }
  public void setDrind(String drind) {
  	this.drind = drind;
  }

  Vector fldId = new Vector();
  Vector drindId = new Vector();
  Vector drindNaziv = new Vector();
  Vector keys = new Vector();
  String fld = "";
  String naziv = "";
  String drind = "";
}