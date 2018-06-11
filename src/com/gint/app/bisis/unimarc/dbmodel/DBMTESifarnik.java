package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMTESifarnik extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMTESifarnik(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select TESID,TES_NAZIV from tipekst_sifarnika order by TESID");
      while(rset.next()) {
        tesId.addElement(rset.getString("TESID"));
        tesNaziv.addElement(rset.getString("TES_NAZIV"));
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
      case 0: return tesId.elementAt(row);
      case 1: return tesNaziv.elementAt(row);
      default: return tesId.elementAt(row);
    }
  }

  public int getRowCount() {
    return tesId.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public int find(String value) {
    if (tesId.contains(value))
      return tesId.indexOf(value);
    else
      return -1;
  }

  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      tesId.insertElementAt(tes,row);
      tesNaziv.insertElementAt(naziv,row);
    }
    else if (mod == UPDATE) {
      tesNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    tesId.removeElementAt(row);
    tesNaziv.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from tipekst_sifarnika where TESID="+tes;
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
      String updateRow = "update tipekst_sifarnika set TES_NAZIV ='"+naziv+"' where TESID ="+tes;
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
      String insertRow = "insert into tipekst_sifarnika (TESID,TES_NAZIV) values ('" + tes+"','"+naziv+ "' )";
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


  public int findPos(Object tes) {
    return Position.IndexToInsert1(tes,0,tesId.size(),tesId);
  }

  public void setTESifarnik(String tes) {
    this.tes = tes;
  }

  public void setNazivTESifarnik(String naziv) {
    this.naziv = naziv;
  }

  Vector tesId = new Vector();
  Vector tesNaziv = new Vector();
  String tes = "";
  String naziv = "";
}