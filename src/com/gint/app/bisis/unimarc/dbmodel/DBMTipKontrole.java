package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMTipKontrole extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMTipKontrole(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select KONTRID,KO_NAZIV from tip_kontrole order by KONTRID");
      while(rset.next()) {
        kontrId.addElement(rset.getString("KONTRID"));
        koNaziv.addElement(rset.getString("KO_NAZIV"));
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
      case 0: return kontrId.elementAt(row);
      case 1: return koNaziv.elementAt(row);
      default: return kontrId.elementAt(row);
    }
  }

  public int getRowCount() {
    return kontrId.size();
  }

  public int getColumnCount() {
    return 2;
  }

  public int find(String value) {
    if (kontrId.contains(value))
      return kontrId.indexOf(value);
    else
      return -1;
  }

  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      kontrId.insertElementAt(tk,row);
      koNaziv.insertElementAt(naziv,row);
    }
    else if (mod == UPDATE) {
      koNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    kontrId.removeElementAt(row);
    koNaziv.removeElementAt(row);
  }

  public boolean saveToDB(int mod, int row){
    int rez=0;
    if (mod == DELETE) {
      if(!tkForeignKey(tk)) {
        String deleteRow = "delete from tip_kontrole where KONTRID="+tk;
        try{
          Statement stmt = conn.createStatement();
          rez = stmt.executeUpdate(deleteRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
        if(rez>0)
          return true;
        else
          return false;
        }
  	else {
          return false;
        }
    }

    if (mod == UPDATE) {
      String updateRow = "update tip_kontrole set KO_NAZIV ='"+naziv+"' where KONTRID ="+tk;
      try {
        Statement stmt = conn.createStatement();
        rez = stmt.executeUpdate(updateRow);
        //conn.commit();
        stmt.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
      if(rez>0)
      	return true;
      else
      	return false;
    }
    if (mod == INSERT) {
      String insertRow = "insert into tip_kontrole (KONTRID,KO_NAZIV) values (" + tk+",'"+naziv+ "' )";
      try {
        Statement stmt = conn.createStatement();
        rez = stmt.executeUpdate(insertRow);
        stmt.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
      if(rez>0)
      	return true;
      else
      	return false;
    }
    return false;
  }

  protected boolean tkForeignKey(String tk) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from potpolja where KONTROLA_KONTRID="+Integer.parseInt(tk));
      if(rs.next()) {
        return true;
      }
      rs.close();
      rs = stmt.executeQuery("select * from potpotpolja where KONTROLA_KONTRID="+Integer.parseInt(tk));
      if(rs.next()) {
        return true;
      }
      rs.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
    return false;
  }


  public int findPos(Object tk) {
    return Position.IndexToInsert1(tk,0,kontrId.size(),kontrId);
  }

  public void setTipKontr(String tk) {
    this.tk = tk;
  }

  public void setNazivKontr(String naziv) {
    this.naziv = naziv;
  }

  Vector kontrId = new Vector();
  Vector koNaziv = new Vector();
  String tk = "";
  String naziv = "";
}