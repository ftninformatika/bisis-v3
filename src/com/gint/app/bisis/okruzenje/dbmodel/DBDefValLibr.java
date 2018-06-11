package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import java.util.*;

public class DBDefValLibr {

  public static boolean existTP(Connection conn, String valueTP) {
    boolean exist=false;
    try {
      Statement stmt=conn.createStatement();
      ResultSet rs=stmt.executeQuery("select * from bibliotekar where BIBTIP='"+valueTP+"'");
      if(rs.next()) {
        exist=true;
      }
      rs.close();
      stmt.close();
      return exist;
    }
    catch(SQLException e) {
      e.printStackTrace();
      return exist;
    }
  }
  public static boolean existNO(Connection conn, String valueTP, String valueNO) {
    boolean exist=false;
    try {
      Statement stmt=conn.createStatement();
      ResultSet rs=stmt.executeQuery("select * from bibliotekar where BIBTIP='"+valueTP+"' and BIBNOBR='"+
                                      valueNO+"'");
      if(rs.next())
        exist=true;
      rs.close();
      stmt.close();
      return exist;
    }
    catch(SQLException e) {
      e.printStackTrace();
      return exist;
    }
  }
  public static boolean existNObav(Connection conn, String valueTP, String valueNO, String valueNObav) {
    boolean exist=false;
    try {
      Statement stmt=conn.createStatement();
      ResultSet rs=stmt.executeQuery("select * from bibliotekar where BIBTIP='"+valueTP+"' and BIBNOBR='"+
  	                              valueNO+"' and BIBNOBAV='"+valueNObav+"'");
      if(rs.next())
        exist=true;
      rs.close();
      stmt.close();
      return exist;
    }
    catch(SQLException e) {
      e.printStackTrace();
      return exist;
    }
  }
  public static boolean existForm(Connection conn, String valueF) {
    boolean exist=false;
    try {
      Statement stmt=conn.createStatement();
      ResultSet rs=stmt.executeQuery("select * from tip_obrade where FOR_FORMATID='"+valueF+"'");
      if(rs.next()) {
        exist=true;
      }
      rs.close();
      stmt.close();
      return exist;
    }
    catch(SQLException e) {
      e.printStackTrace();
      return exist;
    }
  }

  public static void insertIntoProcType(Connection conn, String librar, String typePubl, int valueNObr, int valueNObv) {
    if(!egsistProcType(conn,librar,typePubl,valueNObr,valueNObv)) {
      String form=findForm(conn);
      String updateRow = "insert into TIP_OBRADE (BIB_BIBID, NOV_NOB_TP_TIPID, NOV_NOB_NOBRID, NOV_NOBAVID,FOR_FORMATID) values ("+"'"+librar+"'"+", '"+typePubl+"' ,"+valueNObr+","+valueNObv+","+"'"+form+"'"+")";
      try {
        PreparedStatement updateQuery = conn.prepareStatement(updateRow);
        updateQuery.executeUpdate();
        updateQuery.close();
        conn.commit();
      }
      catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public static void insertIntoPrefProcType(Connection conn, String librar, String typePubl, int valueNObr, int valueNObv, Vector pref) {
    if(!egsistPrefProcType(conn,librar,typePubl,valueNObr,valueNObv)) {
      try{
        for(int i=0; i<5; i++) {
          String updateRow = "insert into POLJEBPR_TIP_OBRADE (TO_BIB_BIBID, TO_NOV_NOB_TP_TIPID, TO_NOV_NOB_NOBRID, TO_NOV_NOBAVID,PPR_POLJEBPRID) values ("+"'"+librar+"'"+","+"'"+typePubl+"'"+","+valueNObr+","+valueNObv+","+ "'"+(String)pref.elementAt(i)+"'"+")";
          PreparedStatement updateQuery = conn.prepareStatement(updateRow);
          updateQuery.executeUpdate();
          updateQuery.close();
        }
        conn.commit();
      }
      catch(SQLException e){
       e.printStackTrace();
      }
    }
  }
  private static String findForm(Connection conn) {
    return "FULL";
//    String form="";
//    try {
//      Statement stmt = conn.createStatement();
//      ResultSet rset = stmt.executeQuery("select FORMATID from formatf");
//      if(rset.next()) {
//        form=rset.getString(1).trim();
//      }
//      rset.close();
//      stmt.close();
//      return form;
//    }
//    catch(SQLException e) {
//      e.printStackTrace();
//      return form;
//    }
  }
  private static boolean egsistProcType(Connection conn, String librar, String typePubl, int valueNObr, int valueNObv) {
    int num=0;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select * from TIP_OBRADE where BIB_BIBID="+"'"+librar+"'"+" and NOV_NOB_TP_TIPID="+"'"+typePubl+"'"+" and NOV_NOB_NOBRID="+valueNObr+" and NOV_NOBAVID="+valueNObv);
      while(rset.next()) {
        num++;
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }

    if(num == 0)
      return false;
    else
      return true;
  }

  private static boolean egsistPrefProcType(Connection conn, String librar, String typePubl, int valueNObr, int valueNObv) {
    int num=0;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select * from POLJEBPR_TIP_OBRADE where TO_BIB_BIBID="+"'"+librar+"'"+" and TO_NOV_NOB_TP_TIPID="+"'"+typePubl+"'"+" and TO_NOV_NOB_NOBRID="+valueNObr+" and TO_NOV_NOBAVID="+valueNObv);
      while(rset.next()) {
        num++;
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }

    if(num == 0)
      return false;
    else
      return true;
  }
}
