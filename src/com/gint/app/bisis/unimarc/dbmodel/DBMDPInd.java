package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMDPInd extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;
  String field="";

  public DBMDPInd(Connection conn,String field) {
    this.conn=conn;
    this.field=field;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct PRINDID,PI_NAZIV from prvi_indikator where PO_POLJEID='"+field+"' order by PRINDID");
      while(rset.next()) {
        prindId.addElement(rset.getString("PRINDID"));
        prindNaziv.addElement(rset.getString("PI_NAZIV"));
        keys.addElement(field+"|"+rset.getString("PRINDID"));
        flag.addElement("-1");
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
      case 0: return prindId.elementAt(row);
      case 1: return prindNaziv.elementAt(row);
      default: return prindId.elementAt(row);
    }
  }

  public int getRowCount() {
    return prindId.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      prindId.insertElementAt(prind,row);
      prindNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(field+"|"+prind,row);
      flag.insertElementAt("0",row);
    }
    else if (mod == UPDATE) {
      prindNaziv.setElementAt(naziv, row);
      if(!((String)flag.elementAt(row)).equals("0"))
        flag.setElementAt("1",row);
    }
  }

  public void removeRow(int row) {
    prindNaziv.removeElementAt(row);
    piForDel.add(prindId.elementAt(row));
    prindId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void removeAll() {
    prindNaziv.removeAllElements();
    piForDel.removeAllElements();
    prindId.removeAllElements();
    keys.removeAllElements();
    flag.removeAllElements();
  }

  public void removeForDelete() {
    piForDel.removeAllElements();
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

  public int findPos(Object pi) {
    return Position.IndexToInsert(pi,0,prindId.size(),prindId);
  }

  public void setNazivPrind(String naziv) {
    this.naziv = naziv;
  }

  public void setPrind(String prind) {
    this.prind = prind;
  }

  public Vector getFlag() {
    return flag;
  }

  public Vector getPrindId() {
    return prindId;
  }

  public Vector getPrindNaziv() {
    return prindNaziv;
  }
  public Vector getPiForDel() {
    return piForDel;
  }

  Vector prindId = new Vector();
  Vector prindNaziv = new Vector();
  Vector keys = new Vector();
  Vector flag = new Vector();
  Vector piForDel=new Vector();

  String naziv = "";
  String prind = "";
}