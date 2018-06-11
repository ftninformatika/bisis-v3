package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMPPPolje extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMPPPolje(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct PPO_PO_POLJEID,PPO_POTPOLJAID,POTPID,POTP_NAZIV,"+
      																	"KONTROLA_KONTRID,POTPDUZINA,POTPONOV,POTPOBAVEZ,STATUSPPP,POTPDEFVR from potpotpolja order by PPO_PO_POLJEID,PPO_POTPOLJAID,POTPID");
      while(rset.next()) {
        fieldId.addElement(rset.getString("PPO_PO_POLJEID"));
        sfieldId.addElement(rset.getString("PPO_POTPOLJAID"));
        ssfieldId.addElement(rset.getString("POTPID"));
        ssfieldNaziv.addElement(rset.getString("POTP_NAZIV"));
        kontrola.addElement(rset.getString("KONTROLA_KONTRID"));
        duzina.addElement(rset.getString("POTPDUZINA"));
        ponov.addElement(rset.getString("POTPONOV"));
        obavez.addElement(rset.getString("POTPOBAVEZ"));
        status.addElement(rset.getString("STATUSPPP"));
        defval.addElement(rset.getString("POTPDEFVR"));
        keys.addElement(rset.getString("PPO_PO_POLJEID")+"|"+rset.getString("PPO_POTPOLJAID")+"|"+rset.getString("POTPID"));
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
      case 2: return ssfieldId.elementAt(row);
      case 3: return ssfieldNaziv.elementAt(row);
      case 4: return kontrola.elementAt(row);
      case 5: return duzina.elementAt(row);
      case 6: return ponov.elementAt(row);
      case 7: return obavez.elementAt(row);
      case 8: return status.elementAt(row);
      case 9: return defval.elementAt(row);
      default: return ssfieldId.elementAt(row);
    }
  }

  public int getRowCount() {
    return ssfieldId.size();
  }

  public int getColumnCount() {
    return 10;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      fieldId.insertElementAt(fld,row);
      sfieldId.insertElementAt(sfld,row);
      ssfieldId.insertElementAt(ssfld,row);
      ssfieldNaziv.insertElementAt(naziv,row);
      kontrola.insertElementAt(kontr,row);
      duzina.insertElementAt(duz,row);
      ponov.insertElementAt(pon,row);
      obavez.insertElementAt(obav,row);
      status.insertElementAt(sts,row);
      defval.insertElementAt(dfval,row);
      keys.insertElementAt(fld+"|"+sfld+"|"+ssfld,row);
    }
    else if (mod == UPDATE) {
      ssfieldNaziv.setElementAt(naziv, row);
      kontrola.setElementAt(kontr,row);
      duzina.setElementAt(duz,row);
      ponov.setElementAt(pon,row);
      obavez.setElementAt(obav,row);
      status.setElementAt(sts,row);
      defval.setElementAt(dfval,row);
    }
  }

  public void removeRow(int row) {
    ssfieldId.removeElementAt(row);
    ssfieldNaziv.removeElementAt(row);
    fieldId.removeElementAt(row);
    sfieldId.removeElementAt(row);
    kontrola.removeElementAt(row);
    duzina.removeElementAt(row);
    ponov.removeElementAt(row);
    obavez.removeElementAt(row);
    status.removeElementAt(row);
    defval.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from potpotpolja where PPO_PO_POLJEID="+Integer.parseInt(fld)+
                          " and PPO_POTPOLJAID='"+sfld+"'and POTPID='"+ssfld+"'";
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
      String updateRow = "update potpotpolja set POTP_NAZIV ='"+naziv+"' ,KONTROLA_KONTRID="+Integer.parseInt(kontr)+
                          " ,POTPDUZINA="+Integer.parseInt(duz)+" , POTPONOV="+Integer.parseInt(pon)+" ,POTPOBAVEZ="
			+Integer.parseInt(obav)+" ,STATUSPPP='"+sts+"' ,POTPDEFVR='"+dfval+"'"+" where PPO_PO_POLJEID="
			+Integer.parseInt(fld)+" and PPO_POTPOLJAID='"+sfld+"' and POTPID='"+ssfld+"'";
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
      String insertRow = "insert into potpotpolja (PPO_PO_POLJEID,PPO_POTPOLJAID,POTPID,POTP_NAZIV,KONTROLA_KONTRID,POTPDUZINA"+
			",POTPONOV,POTPOBAVEZ,STATUSPPP,POTPDEFVR) values ('"+fld+"','"+sfld+ "','"+ssfld+ "'"
			+",'"+naziv+"',"+Integer.parseInt(kontr)+ ","+Integer.parseInt(duz)+ ","+
			Integer.parseInt(pon)+ ","+Integer.parseInt(obav)+ ",'"+sts+ "','"+dfval+ "')";
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

  public int findPos(Object fld,Object sfld,Object ssfr) {
    if(fieldId.contains(fld)) {
      int firstFld = fieldId.indexOf(fld);
      int lastFld = fieldId.lastIndexOf(fld);
      int lastSfld = Position.myLastIndexOf(sfld,firstFld,lastFld,sfieldId);
      if(lastSfld != -1) {
        int firstSfld = sfieldId.indexOf(sfld,firstFld);
        int lastSsfld = Position.myLastIndexOf(ssfld,firstSfld,lastSfld,ssfieldId);
       	if(lastSsfld != -1) {
          int firstSsfld = ssfieldId.indexOf(ssfld,firstSfld);
          return Position.IndexToInsert(ssfld,firstSsfld,lastSsfld+1,ssfieldId);
       	}
       	else {
          return Position.IndexToInsert(ssfld,firstSfld,lastSfld+1,ssfieldId);
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

  public void setSSField(String ssfld) {
    this.ssfld = ssfld;
  }
  public void setNazivSSField(String naziv) {
    this.naziv = naziv;
  }
  public void setField(String fld) {
    this.fld = fld;
  }
  public void setSField(String sfld) {
    this.sfld = sfld;
  }
  public void setKontrola(String kontr) {
    this.kontr = kontr;
  }
  public void setDuzina(String duz) {
    this.duz = duz;
  }
  public void setPonov(String pon) {
    this.pon = pon;
  }
  public void setObav(String obav) {
    this.obav = obav;
  }
  public void setStatus(String sts) {
    this.sts = sts;
  }
  public void setDefVal(String dfval) {
    this.dfval = dfval;
  }

  Vector fieldId = new Vector();
  Vector sfieldId = new Vector();
  Vector ssfieldId = new Vector();
  Vector ssfieldNaziv = new Vector();
  Vector kontrola = new Vector();
  Vector duzina = new Vector();
  Vector ponov = new Vector();
  Vector obavez = new Vector();
  Vector status = new Vector();
  Vector defval = new Vector();
  Vector keys = new Vector();

  String ssfld = "";
  String naziv = "";
  String fld = "";
  String sfld = "";
  String kontr = "";
  String duz = "";
  String pon = "";
  String obav = "";
  String sts = "";
  String dfval = "";
}