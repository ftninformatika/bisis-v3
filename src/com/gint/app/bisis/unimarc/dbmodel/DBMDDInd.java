package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMDDInd extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;
  String field="";

  public DBMDDInd(Connection conn,String field) {
    this.conn=conn;
    this.field=field;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct DRINDID,DI_NAZIV from drugi_indikator where PO_POLJEID='"+field+"' order by DRINDID");
      while(rset.next()) {
        drindId.addElement(rset.getString("DRINDID"));
        drindNaziv.addElement(rset.getString("DI_NAZIV"));
        keys.addElement(field+"|"+rset.getString("DRINDID"));
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
      case 0: return drindId.elementAt(row);
      case 1: return drindNaziv.elementAt(row);
      default: return drindId.elementAt(row);
    }
  }

  public int getRowCount() {
    return drindId.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      drindId.insertElementAt(drind,row);
      drindNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(field+"|"+drind,row);
      flag.insertElementAt("0",row);
    }
    else if (mod == UPDATE) {
      drindNaziv.setElementAt(naziv, row);
      if(!((String)flag.elementAt(row)).equals("0"))
        flag.setElementAt("1",row);
    }
  }

  public void removeRow(int row) {
    drindNaziv.removeElementAt(row);
    diForDel.add(drindId.elementAt(row));
    drindId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void removeAll() {
    drindNaziv.removeAllElements();
    diForDel.removeAllElements();
    drindId.removeAllElements();
    keys.removeAllElements();
    flag.removeAllElements();
  }

  public void removeForDelete() {
    diForDel.removeAllElements();
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

  public int findPos(Object di) {
    return Position.IndexToInsert(di,0,drindId.size(),drindId);
  }

  public void setNazivDrind(String naziv) {
    this.naziv = naziv;
  }

  public void setDrind(String drind) {
    this.drind = drind;
  }

  public Vector getFlag() {
    return flag;
  }

  public Vector getDrindId() {
    return drindId;
  }

  public Vector getDrindNaziv() {
    return drindNaziv;
  }
  public Vector getDiForDel() {
    return diForDel;
  }

  Vector drindId = new Vector();
  Vector drindNaziv = new Vector();
  Vector keys = new Vector();
  Vector flag = new Vector();
  Vector diForDel=new Vector();

  String naziv = "";
  String drind = "";
}