package com.gint.app.bisis.utils;

/**
 *
 * @author branko
 */
public class ImportRecords {

  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: ImportRecords <address> <dbname> <username> <password> <input-file>");
      return;
    }
    String address    = args[0];
    String dbname     = args[1];
    String username   = args[2];
    String password   = args[3];
    String inputFile  = args[4];
    
  }
}
