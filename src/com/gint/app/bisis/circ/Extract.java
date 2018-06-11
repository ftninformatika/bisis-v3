package com.gint.app.bisis.circ;

import java.sql.*;
import com.gint.util.string.StringUtils;
import com.gint.util.string.UnimarcConverter;

public class Extract{

  private static UnimarcConverter conv = new UnimarcConverter();
  private static String author = "";
  private static String title = "";
  private static String signo = "";
  private String ctlg_no;

  public static void getPrefContents(String doc_id, Connection conn, boolean ctlg){
    String pref = "";
    boolean a = true, t = true, sig = true;
    author = ""; title = ""; signo="";
    if(!doc_id.equals("")){
      try{
        Statement stmt = conn.createStatement();
        String query_str = "select content, pref_name from prefix_contents where pref_name in (\'AU\',\'TI\') and doc_id = ";
        if(ctlg)
          query_str += "(select docid from circ_docs where ctlg_no = \'"+doc_id+"\')";
        else
          query_str += doc_id;
        ResultSet rset = stmt.executeQuery(query_str);
        String temp = "";
        while(rset.next()){// && (t||a)){
          temp = "";
          if (DBConnection.getDbType().equals("oracle")) {
            byte[] buf = rset.getBytes(1);
            temp = StringUtils.getStringLower(buf);
          } else
            temp = rset.getString(1);
          
          pref = (rset.getString(2)).trim();

          if(pref.equals("AU")) {
            author += (temp + "; ");
            a = false;
          }
          else if (pref.equals("TI")){
            title +=( temp + "; ");
            t = false;
          }
/*          else {
            signo +=( temp + "; ");
            sig = false;
          } */
        }
        if(!a)author = author.substring(0,author.length()-2);
        author = conv.Unimarc2Unicode(author);
        if(!t) title = title.substring(0,title.length()-2);
        title = conv.Unimarc2Unicode(title);

        rset.close();
        stmt.close();

        stmt = conn.createStatement();
        if (ctlg)
          query_str = "select sig from circ_docs where ctlg_no = \'"+doc_id+"\'";
        else
          query_str = "select sig from circ_docs where docid = \'"+doc_id+"\'";
        rset = stmt.executeQuery(query_str);
        while (rset.next()) {
            temp = rset.getString(1);
            if (temp == null) temp = "";
            signo +=( temp + "; ");
            sig = false;
        }

        if(!sig) signo = signo.substring(0,signo.length()-2);
        //signo = conv.Unimarc2Unicode(signo);
        //signo = Signature.userDisplay(signo);
        rset.close();
        conn.commit();
        stmt.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

  public static String getAuthor(){
    return author;
  }

  public static String getTitle(){
    return title;
  }

  public static String getSigNo(){
    return signo;
  }

  private static int getOne(Connection conn, String user_id, String lend_type, String what){
    String query_str = "";
    int user_type, mmbr_type, user_ctgr = -1;
    int users_period = 0, ctgr_period = 0, lend_period = 0, type_period = 0;
    long l1 = System.currentTimeMillis();

    try{
      Statement stmt = conn.createStatement();
//odredjivanje tipa korisnika
      query_str = "select user_type from users where id = '" + user_id + "'";
      ResultSet rset = stmt.executeQuery(query_str);
      rset.next();
      user_type = rset.getInt(1);
      rset.close();
//dozvoljen period zadrzavanja publikacija za dati tip
      query_str = "select "+ what +" from user_types where id = " + user_type;
      rset = stmt.executeQuery(query_str);
      rset.next();
      users_period = rset.getInt(1);
      rset.close();
      query_str = "select "+ what+ " from lend_types where id = " + lend_type;
      rset = stmt.executeQuery(query_str);
      rset.next();
      lend_period = rset.getInt(1);
      rset.close();

      users_period = criteria(users_period, lend_period);
      if (user_type == 1){
        //query_str = "select mmbr_type, user_ctgr from single where user_id = "+user_id;
        query_str = "select user_ctgr from single where user_id = '"+user_id+"'";
        rset = stmt.executeQuery(query_str);
        rset.next();
        //mmbr_type = rset.getInt(1);
        //user_ctgr = rset.getInt(2);
        user_ctgr = rset.getInt(1);
        rset.close();
        /*query_str = "select "+what+" from mmbr_types where id = "+mmbr_type;
        rset = stmt.executeQuery(query_str);
        rset.next();
        type_period = rset.getInt(1);
        rset.close();*/
        
        query_str = "select "+what+" from user_categs where id = "+user_ctgr;
        rset = stmt.executeQuery(query_str);
        rset.next();
        ctgr_period = rset.getInt(1);

        rset.close();
        //ctgr_period = criteria(ctgr_period, type_period);
        users_period = criteria(ctgr_period,users_period);
      }
      conn.commit();
      stmt.close();
    }
    catch(Exception e){
      users_period = -1;
      e.printStackTrace();
    }
    long l2 = System.currentTimeMillis();

    return users_period;
  }

  public static int getTitleNo(Connection conn, String user_id, String lend_type){
    return getOne(conn, user_id, lend_type, "titles_no");
  }

  public static int getPeriod(Connection conn, String user_id, String lend_type){
    return getOne(conn, user_id, lend_type, "period");
  }

  private static int criteria(int first, int second){
    return criteria(first, second, Extract.MAX);
  }

  private static int criteria(int first, int second, int fnct){
    int ret_val = -1;
    switch(fnct){
      case Extract.MAX: ret_val = Math.max(first, second); break;
      case Extract.MIN: ret_val = Math.min(first, second); break;
    }
    return ret_val;
  }

  public static int expires(Connection conn, String user_id, String lend_type, String what){
    String query_str = "";
    int mmbr_type = -1;
    int member_period = 0;
    long l1 = System.currentTimeMillis();

    try{
      Statement stmt = conn.createStatement();
//odredjivanje tipa korisnika
      query_str = "select mmbr_type from users where id = '" + user_id + "'";
      ResultSet rset = stmt.executeQuery(query_str);
      rset.next();
      mmbr_type = rset.getInt(1);
      rset.close();
//dozvoljen period zadrzavanja publikacija za dati tip
      query_str = "select expires from mmbr_types where id = " + (new Integer(mmbr_type).toString());
      rset = stmt.executeQuery(query_str);
      rset.next();
      member_period = rset.getInt(1);
      rset.close();
      conn.commit();
      stmt.close();
    }
    catch(Exception e){
      member_period = -1;
      e.printStackTrace();
    }
    long l2 = System.currentTimeMillis();
    return member_period;
  }


  private static final int MAX = 1;
  private static final int MIN = 2;
}


