package com.gint.app.bisis.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.RecordID;

/**
 *
 * @author branko
 */
public class ExportRecords {
  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: ExportRecords <address> <dbname> <username> <password> <output-file>");
      return;
    }
    String address    = args[0];
    String dbname     = args[1];
    String username   = args[2];
    String password   = args[3];
    String outputFile = args[4];
    try {
      String url = "jdbc:sapdb://" + address + "/" + dbname + "?sqlmode=ORACLE&unicode=yes&timeout=0";
      Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF8")));
      out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
      out.println("<unimarc>\n");
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT doc_id, document FROM documents");
      while (rset.next()) {
        int docid = rset.getInt(1);
        String doc = rset.getString(2);
        Record rec = RecordFactory.fromUNIMARC(doc);
        rec.setRecordID(new RecordID(0, docid));
        String recXml = RecordFactory.toLooseXML(rec);
        out.println(recXml);
      }
      rset.close();
      stmt.close();
      conn.close();
      out.println("</unimarc>\n");
      out.close();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
    
  }

}
