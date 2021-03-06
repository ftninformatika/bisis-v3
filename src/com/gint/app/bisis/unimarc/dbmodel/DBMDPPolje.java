package com.gint.app.bisis.unimarc.dbmodel;

import java.sql.*;
import javax.swing.table.*;
import java.util.*;

public class DBMDPPolje extends AbstractTableModel{

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  Connection conn;
  String field="";

  public DBMDPPolje(Connection conn,String field) {
    this.conn=conn;
    this.field=field;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select distinct POTPOLJAID,PP_NAZIV,TES_TESID,"+
      																	"KONTROLA_KONTRID,DUZINA,PONOVLJIVOSTPP,OBAVEZNOSTPP,STATUS,DEFVREDNOST from potpolja where PO_POLJEID='"+field+"' order by POTPOLJAID");
      while(rset.next()) {
        sfieldId.addElement(rset.getString("POTPOLJAID"));
        tesId.addElement(rset.getString("TES_TESID"));
        sfieldNaziv.addElement(rset.getString("PP_NAZIV"));
        kontrola.addElement(rset.getString("KONTROLA_KONTRID"));
        duzina.addElement(rset.getString("DUZINA"));
        ponov.addElement(rset.getString("PONOVLJIVOSTPP"));
        obavez.addElement(rset.getString("OBAVEZNOSTPP"));
        status.addElement(rset.getString("STATUS"));
        defval.addElement(rset.getString("DEFVREDNOST"));
        keys.addElement(field+"|"+rset.getString("POTPOLJAID"));
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
      case 0: return sfieldId.elementAt(row);
      case 1: return tesId.elementAt(row);
      case 2: return sfieldNaziv.elementAt(row);
      case 3: return kontrola.elementAt(row);
      case 4: return duzina.elementAt(row);
      case 5: return ponov.elementAt(row);
      case 6: return obavez.elementAt(row);
      case 7: return status.elementAt(row);
      case 8: return defval.elementAt(row);
      default: return sfieldId.elementAt(row);
    }
  }

  public int getRowCount() {
    return sfieldId.size();
  }

  public int getColumnCount() {
    return 9;
  }


  public void setTo(int row,int mod) {
    if (mod == INSERT) {
      //fieldId.insertElementAt(fld,row);
      sfieldId.insertElementAt(sfld,row);
      tesId.insertElementAt(tes,row);
      sfieldNaziv.insertElementAt(naziv,row);
      kontrola.insertElementAt(kontr,row);
      duzina.insertElementAt(duz,row);
      ponov.insertElementAt(pon,row);
      obavez.insertElementAt(obav,row);
      status.insertElementAt(sts,row);
      defval.insertElementAt(dfval,row);
      keys.insertElementAt(field+"|"+sfld,row);
      flag.insertElementAt("0",row);
    }
    else if (mod == UPDATE) {
      tesId.setElementAt(tes,row);
      sfieldNaziv.setElementAt(naziv, row);
      kontrola.setElementAt(kontr,row);
      duzina.setElementAt(duz,row);
      ponov.setElementAt(pon,row);
      obavez.setElementAt(obav,row);
      status.setElementAt(sts,row);
      defval.setElementAt(dfval,row);
      if(!((String)flag.elementAt(row)).equals("0"))
        flag.setElementAt("1",row);
    }
  }

  public void removeRow(int row) {
    tesId.removeElementAt(row);
    sfieldNaziv.removeElementAt(row);
    //fieldId.removeElementAt(row);
    sfForDel.add(sfieldId.elementAt(row));
    sfieldId.removeElementAt(row);
    kontrola.removeElementAt(row);
    duzina.removeElementAt(row);
    ponov.removeElementAt(row);
    obavez.removeElementAt(row);
    status.removeElementAt(row);
    defval.removeElementAt(row);
    keys.removeElementAt(row);
  }

  public void removeAll() {
    tesId.removeAllElements();
    sfieldNaziv.removeAllElements();
    sfForDel.removeAllElements();
    sfieldId.removeAllElements();
    kontrola.removeAllElements();
    duzina.removeAllElements();
    ponov.removeAllElements();
    obavez.removeAllElements();
    status.removeAllElements();
    defval.removeAllElements();
    keys.removeAllElements();
    flag.removeAllElements();
  }

  public void removeForDelete() {
    sfForDel.removeAllElements();
  }
  /*public void saveToDB(int mod, int row){
    if (mod == DELETE) {
      String deleteRow = "delete from potpolja where PO_POLJEID="+Integer.parseInt(fld)+
                          " and POTPOLJAID='"+sfld+"'";
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
      String updateRow;
      if(tes != null)
        updateRow = "update potpolja set PP_NAZIV ='"+naziv+"' ,KONTROLA_KONTRID="+Integer.parseInt(kontr)+
                     " ,DUZINA="+Integer.parseInt(duz)+" , PONOVLJIVOSTPP="+Integer.parseInt(pon)+" ,OBAVEZNOSTPP="
                     +Integer.parseInt(obav)+" ,STATUS='"+sts+"' ,DEFVREDNOST='"+dfval+"' ,TES_TESID="+Integer.parseInt(tes)+
                     " where PO_POLJEID="+Integer.parseInt(fld)+" and POTPOLJAID='"+sfld+"'";
      else
         updateRow = "update potpolja set PP_NAZIV ='"+naziv+"' ,KONTROLA_KONTRID="+Integer.parseInt(kontr)+
                     " ,DUZINA="+Integer.parseInt(duz)+" , PONOVLJIVOSTPP="+Integer.parseInt(pon)+" ,OBAVEZNOSTPP="
                     +Integer.parseInt(obav)+" ,STATUS='"+sts+"' ,DEFVREDNOST='"+dfval+"'" +
                     " where PO_POLJEID="+Integer.parseInt(fld)+" and POTPOLJAID='"+sfld+"'";
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
      String insertRow;
      if(tes != null)
      	insertRow = "insert into potpolja (PO_POLJEID,POTPOLJAID,TES_TESID,PP_NAZIV,KONTROLA_KONTRID,DUZINA"+
                    ",PONOVLJIVOSTPP,OBAVEZNOSTPP,STATUS,DEFVREDNOST) values ('"+fld+"','"+sfld+ "',"+Integer.parseInt(tes)+
                    ",'"+naziv+"',"+Integer.parseInt(kontr)+ ","+Integer.parseInt(duz)+ ","+
                    Integer.parseInt(pon)+ ","+Integer.parseInt(obav)+ ",'"+sts+ "','"+dfval+ "')";
      else
	insertRow = "insert into potpolja (PO_POLJEID,POTPOLJAID,PP_NAZIV,KONTROLA_KONTRID,DUZINA"+
                    ",PONOVLJIVOSTPP,OBAVEZNOSTPP,STATUS,DEFVREDNOST) values ('"+fld+"','"+sfld+ "',"+
                    "'"+naziv+"',"+Integer.parseInt(kontr)+ ","+Integer.parseInt(duz)+ ","+
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
  }*/

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

  public int findPos(Object sfld) {
    return Position.IndexToInsert(sfld,0,sfieldId.size(),sfieldId);
  }

  public void setTes(String tes) {
    this.tes = tes;
  }
  public void setNazivSField(String naziv) {
    this.naziv = naziv;
  }
 /* public void setField(String fld) {
  	this.fld = fld;
  }*/
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

  public Vector getFlag() {
    return flag;
  }

  public Vector getSfieldId() {
    return sfieldId;
  }

  public Vector getTesId() {
    return tesId;
  }

  public Vector getSfieldNaziv() {
    return sfieldNaziv;
  }
  public Vector getKontrola() {
    return kontrola;
  }
  public Vector getDuzina() {
    return duzina;
  }
  public Vector getPonov() {
    return ponov;
  }
  public Vector getObavez() {
    return obavez;
  }
  public Vector getStatus() {
    return status;
  }
  public Vector getDefval() {
    return defval;
  }
  public Vector getSfForDel() {
    return sfForDel;
  }

  Vector sfieldId = new Vector();
  Vector tesId = new Vector();
  Vector sfieldNaziv = new Vector();
  Vector kontrola = new Vector();
  Vector duzina = new Vector();
  Vector ponov = new Vector();
  Vector obavez = new Vector();
  Vector status = new Vector();
  Vector defval = new Vector();
  Vector keys = new Vector();
  Vector flag = new Vector();
  Vector sfForDel=new Vector();

  String tes = "";
  String naziv = "";
  String sfld = "";
  String kontr = "";
  String duz = "";
  String pon = "";
  String obav = "";
  String sts = "";
  String dfval = "";
}