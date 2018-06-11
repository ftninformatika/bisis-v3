package com.gint.app.bisis.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author branko
 */
public class UpdateRecords {

  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: UpdateRecords <address> <dbname> <username> <password> <input-file>");
      return;
    }
    String address    = args[0];
    String dbname     = args[1];
    String username   = args[2];
    String password   = args[3];
    String inputFile  = args[4];
    try {
      String url = "jdbc:sapdb://" + address + "/" + dbname + "?sqlmode=ORACLE&unicode=yes&timeout=0";
      Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
      
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      LooseSAXHandler handler = new LooseSAXHandler(conn);
      parser.parse(inputFile, handler);
      conn.close();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}
