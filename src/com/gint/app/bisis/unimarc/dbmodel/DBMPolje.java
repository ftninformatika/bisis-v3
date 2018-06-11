package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMPolje extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMPolje(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct POLJEID,PO_NAZIV,PONOVLJIVOST,OBAVEZNOST,"+
      																	"SEKUNDARNOST,OPIS_PI,OPIS_DI,DEF_PI,DEF_DI from polje order by POLJEID");
      while(rset.next()) {
        fieldId.addElement(rset.getString("POLJEID"));
        fieldNaziv.addElement(rset.getString("PO_NAZIV"));
        ponov.addElement(rset.getString("PONOVLJIVOST"));
        obavez.addElement(rset.getString("OBAVEZNOST"));
        sek.addElement(rset.getString("SEKUNDARNOST"));
        opisPi.addElement(rset.getString("OPIS_PI"));
        opisDi.addElement(rset.getString("OPIS_DI"));
        defPi.addElement(rset.getString("DEF_PI"));
	defDi.addElement(rset.getString("DEF_DI"));
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
      case 1: return fieldNaziv.elementAt(row);
      case 2: return ponov.elementAt(row);
      case 3: return obavez.elementAt(row);
      case 4: return sek.elementAt(row);
      case 5: return opisPi.elementAt(row);
      case 6: return opisDi.elementAt(row);
      case 7: return defPi.elementAt(row);
      case 8: return defDi.elementAt(row);
      default: return fieldId.elementAt(row);
    }
  }

  public int getRowCount() {
    return fieldId.size();
  }

  public int getColumnCount() {
    return 9;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      fieldId.insertElementAt(fld,row);
      fieldNaziv.insertElementAt(naziv,row);
      ponov.insertElementAt(pon,row);
      obavez.insertElementAt(obav,row);
      sek.insertElementAt(skd,row);
      opisPi.insertElementAt(descPi,row);
      opisDi.insertElementAt(descDi,row);
      defPi.insertElementAt(dfPi,row);
      defDi.insertElementAt(dfDi,row);
    }
    else if (mod == UPDATE) {
      fieldNaziv.setElementAt(naziv,row);
      ponov.setElementAt(pon, row);
      obavez.setElementAt(obav,row);
      sek.setElementAt(skd,row);
      opisPi.setElementAt(descPi,row);
      opisDi.setElementAt(descDi,row);
      defPi.setElementAt(dfPi,row);
      defDi.setElementAt(dfDi,row);
    }
  }

  public void removeRow(int row) {
    fieldId.removeElementAt(row);
    fieldNaziv.removeElementAt(row);
    ponov.removeElementAt(row);
    obavez.removeElementAt(row);
    sek.removeElementAt(row);
    opisPi.removeElementAt(row);
    opisDi.removeElementAt(row);
    defPi.removeElementAt(row);
    defDi.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from polje where POLJEID="+Integer.parseInt(fld);
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
      String updateRow = "update polje set PO_NAZIV ='"+naziv+"' ,OBAVEZNOST="+Integer.parseInt(obav)+
                        " ,PONOVLJIVOST="+Integer.parseInt(pon)+" , SEKUNDARNOST="+Integer.parseInt(skd)+
			" ,OPIS_PI='"+descPi+"' ,OPIS_DI='"+descDi+"' ,DEF_PI='"+dfPi+"' ,DEF_DI='"+dfDi+"'"+
			" where POLJEID="+Integer.parseInt(fld);
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
      String insertRow = "insert into polje (POLJEID,PO_NAZIV,OBAVEZNOST,PONOVLJIVOST,SEKUNDARNOST,OPIS_PI"+
			",OPIS_DI,DEF_PI,DEF_DI) values ('"+fld+"','"+naziv+"',"+Integer.parseInt(obav)+
			","+Integer.parseInt(pon)+ ","+Integer.parseInt(skd)+ ",'"+descPi+"','"+descDi+
			"','"+dfPi+ "','"+dfDi+ "')";
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
    for (i=0; i<fieldId.size(); i++) {
      if(((String)fieldId.get(i)).length() >= lengths) {
        if (((String)fieldId.get(i)).substring(0,lengths).equals(value)) {
          return i;
        }
      }
    }
    return -1;
  }

  public int findPos(Object fld) {
    return Position.IndexToInsert(fld,0,fieldId.size(),fieldId);
  }

  public void setField(String fld) {
    this.fld = fld;
  }
  public void setNazivField(String naziv) {
    this.naziv = naziv;
  }
  public void setDefPi(String dfPi) {
    this.dfPi = dfPi;
  }
  public void setDefDi(String dfDi) {
    this.dfDi = dfDi;
  }
  public void setOpisPi(String descPi) {
    this.descPi = descPi;
  }
  public void setOpisDi(String descDi) {
    this.descDi = descDi;
  }
  public void setPonov(String pon) {
    this.pon = pon;
  }
  public void setObav(String obav) {
    this.obav = obav;
  }
  public void setSek(String skd) {
    this.skd = skd;
  }

  Vector fieldId = new Vector();
  Vector fieldNaziv = new Vector();
  Vector opisPi = new Vector();
  Vector opisDi = new Vector();
  Vector defPi = new Vector();
  Vector defDi = new Vector();
  Vector ponov = new Vector();
  Vector obavez = new Vector();
  Vector sek = new Vector();

  String fld = "";
  String naziv = "";
  String descPi = "";
  String descDi = "";
  String dfPi = "";
  String dfDi = "";
  String pon = "";
  String obav = "";
  String skd = "";
}