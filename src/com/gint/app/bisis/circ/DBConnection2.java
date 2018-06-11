package com.gint.app.bisis.circ;


import java.sql.*;


public class DBConnection2 {
  private Connection  connection;
  
  
  public DBConnection2() {
/********/
   
    try {

        Class.forName("com.sap.dbtech.jdbc.DriverSapDB");

      } catch (ClassNotFoundException ex) {
        
        ex.printStackTrace();
        System.exit(0);
      }

    try {
      connection = DriverManager.getConnection("jdbc:sapdb://147.91.177.22/BISIS?sqlmode=ORACLE&unicode=yes&timeout=0","bisisbg","bisisbg");

      connection.setAutoCommit(false);

      
    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(0);
    }
  }
  
  public Connection getConnection(){
	    return connection;
  }
  
     
}
  