package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMESifarnik extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMESifarnik(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct TES_TESID,ESID,ES_NAZIV from eksterni_sifarnik order by TES_TESID,ESID");
      while(rset.next()) {
        tesId.addElement(rset.getString("TES_TESID"));
        esfId.addElement(rset.getString("ESID"));
        esfNaziv.addElement(rset.getString("ES_NAZIV"));
        keys.addElement(rset.getString("TES_TESID")+"|"+rset.getString("ESID"));
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
      case 1: return esfId.elementAt(row);
      case 2: return esfNaziv.elementAt(row);
      default: return esfId.elementAt(row);
    }
  }

  public int getRowCount() {
    return esfId.size();
  }

  public int getColumnCount() {
    return 3;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      tesId.insertElementAt(tes,row);
      esfId.insertElementAt(esf,row);
      esfNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(tes+"|"+esf,row);
    }
    else if (mod == UPDATE) {
      esfNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    esfId.removeElementAt(row);
    esfNaziv.removeElementAt(row);
    tesId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from eksterni_sifarnik where TES_TESID='"+tes+
                          "' and ESID='"+esf+"'";
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
      String updateRow = "update eksterni_sifarnik set ES_NAZIV ='"+naziv+"' where TES_TESID='"+tes+
                          "' and ESID='"+esf+"'";
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
      String insertRow = "insert into eksterni_sifarnik (TES_TESID,ESID,ES_NAZIV) values ('"+tes+"','"+esf+ "'"+",'"+naziv+ "')";
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
      if(((String)keys.get(i)).length() >= lengths)
        if (((String)keys.get(i)).substring(0,lengths).equals(value)) {
          return i;
  	}
      }
    return -1;
  }
  public int find (String value) {
    int i=0;
    for (i=0; i<keys.size(); i++) {
      int j = ((String)keys.get(i)).indexOf("|",0);
      if(j != -1)
      	if (((String)keys.get(i)).substring(0,j).equals(value))
          return i;
    }
    if (i == keys.size()-1) {
    	return -1;
    }
    return -1;
  }

  public int findPos(Object tes,Object esfr) {
    if(tesId.contains(tes)) {
      int firstTes = tesId.indexOf(tes);
      int lastTes = tesId.lastIndexOf(tes);
      int lastEsfr = Position.myLastIndexOf(esfr,firstTes,lastTes,esfId);
      if(lastEsfr != -1) {
      	int firstEsfr = esfId.indexOf(esfr,firstTes);
       	return Position.IndexToInsert(esfr,firstEsfr,lastEsfr+1,esfId);
      }
      else {
      	return Position.IndexToInsert(esfr,firstTes,lastTes+1,esfId);
      }
    }
    else {
      return Position.IndexToInsert1(tes,0,tesId.size(),tesId);
    }
  }

  public void setESifarnik(String esf) {
    this.esf = esf;
  }
  public void setNazivESifarnik(String naziv) {
    this.naziv = naziv;
  }
  public void setTESifarnik(String tes) {
    this.tes = tes;
  }

  Vector tesId = new Vector();
  Vector esfId = new Vector();
  Vector esfNaziv = new Vector();
  Vector keys = new Vector();
  String esf = "";
  String naziv = "";
  String tes = "";
}