package com.gint.app.bisis.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import com.gint.app.bisis.textsrv.TextServer;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class PutRecord {

  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: PutRecord <address> <dbname> <username> <password> <record-id> [<input-file>]");
      return;
    }
    String address    = args[0];
    String dbname     = args[1];
    String username   = args[2];
    String password   = args[3];
    String sRecID     = args[4];
    int recID = 0;
    try {
      recID = Integer.parseInt(sRecID);
      if (recID < 1)
        throw new Exception("Invalid record ID (recID < 1)");
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      return;
    }

    try {
      String url = "jdbc:sapdb://" + address + "/" + dbname + "?sqlmode=ORACLE&unicode=yes&timeout=0";
      Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
      TextServer textsrv = new TextServer("sapdb", conn, url, username, password);
      
      BufferedReader in = null;
      if (args.length > 5)
        in = new BufferedReader(new InputStreamReader(new FileInputStream(args[5]), "UTF8"));
      else
        in = new BufferedReader(new InputStreamReader(System.in, "UTF8"));
      
      StringBuffer sb = new StringBuffer();
      String line = null;
      while ((line = in.readLine()) != null)
      	sb.append(line);
      
      String xml = sb.toString();
      Record rec = RecordFactory.fromLooseXML(xml);
      String rez = RecordFactory.toUNIMARC(rec);
      
      textsrv.update(recID, rez);

      conn.close();
      in.close();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}
