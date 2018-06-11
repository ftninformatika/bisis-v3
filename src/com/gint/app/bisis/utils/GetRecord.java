package com.gint.app.bisis.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import com.gint.app.bisis.textsrv.TextServer;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class GetRecord {

  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: GetRecord <address> <dbname> <username> <password> <record-id> [<output-file>]");
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
      
      String srec = textsrv.getDoc(recID);
      Record rec = RecordFactory.fromUNIMARC(srec);
      String xml = RecordFactory.toLooseXML(rec);

      BufferedWriter out = null;
      if (args.length > 5) {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[5]), "UTF8"));
      } else {
        out = new BufferedWriter(new OutputStreamWriter(System.out, "UTF8"));
      }
      out.write(xml);
      out.close();
      
      conn.close();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}
