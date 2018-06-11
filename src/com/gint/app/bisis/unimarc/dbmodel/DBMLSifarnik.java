package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMLSifarnik extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;

  public DBMLSifarnik(Connection conn) {
    this.conn=conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct PPP_PPO_PO_POLJEID,PPP_PPO_POTPOLJAID,PPP_POTPID,SIFPOTPID,SIFPOTP_NAZIV from sifarnik_potpotpolja order by PPP_PPO_PO_POLJEID,PPP_PPO_POTPOLJAID,PPP_POTPID,SIFPOTPID");
      while(rset.next()) {
        fieldId.addElement(rset.getString("PPP_PPO_PO_POLJEID"));
        sfieldId.addElement(rset.getString("PPP_PPO_POTPOLJAID"));
        ssfieldId.addElement(rset.getString("PPP_POTPID"));
        lsfId.addElement(rset.getString("SIFPOTPID"));
        lsfNaziv.addElement(rset.getString("SIFPOTP_NAZIV"));
        keys.addElement(rset.getString("PPP_PPO_PO_POLJEID")+"|"+rset.getString("PPP_PPO_POTPOLJAID")+"|"+rset.getString("PPP_POTPID")+"|"+rset.getString("SIFPOTPID"));
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
      case 3: return lsfId.elementAt(row);
      case 4: return lsfNaziv.elementAt(row);
      default: return lsfId.elementAt(row);
    }
  }

  public int getRowCount() {
    return lsfId.size();
  }

  public int getColumnCount() {
    return 5;
  }

  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      fieldId.insertElementAt(fld,row);
      sfieldId.insertElementAt(sfld,row);
      ssfieldId.insertElementAt(ssfld,row);
      lsfId.insertElementAt(lsf,row);
      lsfNaziv.insertElementAt(naziv,row);
      keys.insertElementAt(fld+"|"+sfld+"|"+ssfld+"|"+lsf,row);
    }
    else if (mod == UPDATE) {
      lsfNaziv.setElementAt(naziv, row);
    }
  }

  public void removeRow(int row) {
    lsfId.removeElementAt(row);
    lsfNaziv.removeElementAt(row);
    fieldId.removeElementAt(row);
    sfieldId.removeElementAt(row);
    ssfieldId.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from sifarnik_potpotpolja where PPP_PPO_PO_POLJEID="+Integer.parseInt(fld)+
                        " and PPP_PPO_POTPOLJAID='"+sfld+"' and PPP_POTPID='"+ssfld+"' and SIFPOTPID='"+lsf+"'";
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
      String updateRow = "update sifarnik_potpotpolja set SIFPOTP_NAZIV ='"+naziv+"' where PPP_PPO_PO_POLJEID="+Integer.parseInt(fld)+
                        " and PPP_PPO_POTPOLJAID='"+sfld+"' and PPP_POTPID='"+ssfld+"' and SIFPOTPID='"+lsf+"'";
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
      String insertRow = "insert into sifarnik_potpotpolja (PPP_PPO_PO_POLJEID,PPP_PPO_POTPOLJAID,PPP_POTPID,SIFPOTPID,SIFPOTP_NAZIV) values ('" +fld+"','"+sfld+ "'"+
                          ",'"+ssfld+ "'"+",'"+lsf+ "'"+",'"+naziv+ "' )";
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

  public int findPos(Object fld, Object sfld, Object ssfld, Object lsfr) {
    if(fieldId.contains(fld)) {
      int firstFld = fieldId.indexOf(fld);
      int lastFld = fieldId.lastIndexOf(fld);
      int lastSfld = Position.myLastIndexOf(sfld,firstFld,lastFld,sfieldId);
      if(lastSfld != -1) {
        int firstSfld = sfieldId.indexOf(sfld,firstFld);
        int lastSsfld = Position.myLastIndexOf(ssfld,firstSfld,lastSfld,ssfieldId);
        if(lastSsfld != -1) {
          int firstSsfld = ssfieldId.indexOf(ssfld,firstSfld);
          int lastLsfr = Position.myLastIndexOf(lsfr,firstSsfld,lastSsfld,lsfId);
          if(lastLsfr != -1) {
            int firstLsfr = lsfId.indexOf(lsfr,firstSsfld);
            return Position.IndexToInsert(lsfr,firstLsfr,lastLsfr+1,lsfId);
          }
          else {
            return Position.IndexToInsert(lsfr,firstSsfld,lastSsfld+1,lsfId);
          }
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

  public void setLSifarnik(String lsf) {
    this.lsf = lsf;
  }
  public void setNazivLSifarnik(String naziv) {
    this.naziv = naziv;
  }
  public void setField(String fld) {
    this.fld = fld;
  }
  public void setSField(String sfld) {
    this.sfld = sfld;
  }
  public void setSSField(String ssfld) {
    this.ssfld = ssfld;
  }
  Vector fieldId = new Vector();
  Vector sfieldId = new Vector();
  Vector ssfieldId = new Vector();
  Vector lsfId = new Vector();
  Vector lsfNaziv = new Vector();
  Vector keys = new Vector();
  String lsf = "";
  String naziv = "";
  String fld = "";
  String sfld = "";
  String ssfld = "";
}