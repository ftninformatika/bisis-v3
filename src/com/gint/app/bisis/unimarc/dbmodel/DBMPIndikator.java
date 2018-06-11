package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMPIndikator extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMPIndikator(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct PO_POLJEID,PRINDID,PI_NAZIV from prvi_indikator order by PO_POLJEID,PRINDID");
      while(rset.next()) {
        fldId.addElement(rset.getString("PO_POLJEID"));
        prindId.addElement(rset.getString("PRINDID"));
        prindNaziv.addElement(rset.getString("PI_NAZIV"));
        keys.addElement(rset.getString("PO_POLJEID")+"|"+rset.getString("PRINDID"));
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
      case 1: return prindId.elementAt(row);
      case 2: return prindNaziv.elementAt(row);
      default: return prindId.elementAt(row);
    }
  }

  public int getRowCount() {
    return prindId.size();
  }

  public int getColumnCount() {
    return 3;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      fldId.insertElementAt(fld,row);
      prindId.insertElementAt(prind,row);
      prindNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(fld+"|"+prind,row);
    }
    else if (mod == UPDATE) {
      prindNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    prindId.removeElementAt(row);
    prindNaziv.removeElementAt(row);
    fldId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from prvi_indikator where PO_POLJEID='"+fld+
                          "' and PRINDID='"+prind+"'";
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
      String updateRow = "update prvi_indikator set PI_NAZIV ='"+naziv+"' where PO_POLJEID='"+fld+
			"' and PRINDID='"+prind+"'";
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
      String insertRow = "insert into prvi_indikator (PO_POLJEID,PRINDID,PI_NAZIV) values ('"+fld+"','"+prind+ "'"+",'"+naziv+ "')";
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
      if(((String)keys.get(i)).length() >= lengths){
        if (((String)keys.get(i)).substring(0,lengths).equals(value)) {
  	  return i;
  	}
      }
    }
    return -1;
  }

  public int findPos(Object fld,Object prind) {
    if(fldId.contains(fld)) {
      int firstFld = fldId.indexOf(fld);
      int lastFld = fldId.lastIndexOf(fld);
      int lastPrind = Position.myLastIndexOf(prind,firstFld,lastFld,prindId);
      if(lastPrind != -1) {
      	int firstPrind = prindId.indexOf(prind,firstFld);
       	return Position.IndexToInsert(prind,firstPrind,lastPrind+1,prindId);
      }
      else {
      	return Position.IndexToInsert(prind,firstFld,lastFld+1,prindId);
      }
    }
    else {
      return Position.IndexToInsert(fld,0,fldId.size(),fldId,1);
    }
  }

  public void setField(String fld) {
    this.fld = fld;
  }
  public void setNazivPrind(String naziv) {
    this.naziv = naziv;
  }
  public void setPrind(String prind) {
    this.prind = prind;
  }

  Vector fldId = new Vector();
  Vector prindId = new Vector();
  Vector prindNaziv = new Vector();
  Vector keys = new Vector();
  String fld = "";
  String naziv = "";
  String prind = "";
}