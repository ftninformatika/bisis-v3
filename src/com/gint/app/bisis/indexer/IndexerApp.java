package com.gint.app.bisis.indexer;

import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.StopWatch;

import com.gint.app.bisis.export.ExportEscapes;
import com.gint.app.bisis.textsrv.TextServer;
import com.gint.util.string.StringUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class IndexerApp {

  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: indexer <address> <dbname> <username> <password> <record-file> [<failed-file>]");
      return;
    }
    String address    = args[0];
    String dbname     = args[1];
    String username   = args[2];
    String password   = args[3];
    String inputFile  = args[4];
    String outputFile = "";
    if (args.length > 5)
      outputFile = args[5];
    
    try {
      System.out.println("Connecting to database...");
      String url = "jdbc:sapdb://" + address + "/" + dbname + "?sqlmode=ORACLE&unicode=yes&timeout=0";
      Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
      TextServer textsrv = new TextServer("sapdb", conn, url, username, password);

      System.out.println("Opening file(s)...");
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      RandomAccessFile out = null;
      if (!outputFile.equals("")) {
        File f = new File(outputFile);
        if (f.isFile())
          f.delete();
        out = new RandomAccessFile(outputFile, "rw");
      }
      
      System.out.println("Counting records...");
      int totalCount = 0;
      while (in.readLine() != null) {
        totalCount++;
        if (totalCount % 1000 == 0)
          System.out.print(" " + totalCount);
      }
      in.close();
      System.out.println("\nTotal records to import: " + totalCount);
      
      System.out.println("Importing data...");
      in = new RandomAccessFile(inputFile, "r");
      int lineCount = 0;
      int badCount = 0;
      String line;
      StopWatch clock = new StopWatch();
      clock.start();
      while ((line = in.readLine()) != null) {
        lineCount++;
        line = line.trim();
        if (line.equals(""))
          continue;
        line = ExportEscapes.unescapeText(line);
        try {
          int retCode = textsrv.insert(line);
          if (retCode == -1) {
            System.out.println("Error inserting record: " + lineCount);
            if (out != null)
              out.writeBytes(line+"\r\n");
            badCount++;
          }
        } catch (Exception ex) {
          if (out != null)
            out.writeBytes(line+"\r\n");
          badCount++;
        }
        if (lineCount == 10)
          displayTime(clock, lineCount, totalCount, badCount);
        else if (lineCount == 100)
          displayTime(clock, lineCount, totalCount, badCount);
        else if (lineCount == 500)
          displayTime(clock, lineCount, totalCount, badCount);
        else if (lineCount % 1000 == 0)
          displayTime(clock, lineCount, totalCount, badCount);
      }
      
      System.out.println("Closing stuff...");
      conn.close();
      in.close();
      if (out != null)
        out.close();
      
      System.out.println();
      System.out.println("--- FINAL STATS ---");
      displayTime(clock, lineCount, totalCount, badCount);
      clock.stop();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  private static void displayTime(StopWatch clock, int lineCount, int totalCount, int badCount) {
    long timeElapsed = clock.getTime();
    long totalTime = (long)((float)timeElapsed * ((float)totalCount / (float)lineCount));
    long timeRemaining = totalTime - timeElapsed;
    System.out.println();
    System.out.println("[" + sdf.format(new Date()) + "] ------------------------");
    System.out.println("Record count:     " + lineCount);
    System.out.println("  progress:       " + percent(lineCount, totalCount));
    System.out.println("Bad record count: " + badCount);
    System.out.println("Time elapsed:     " + timeToString(timeElapsed));
    System.out.println("Time remaining:   " + timeToString(timeRemaining));
  }

  private static String timeToString(long time) {
    int hours = (int)time / 3600000;
    long remainder = time % 3600000;
    int minutes = (int)remainder / 60000;
    remainder = remainder % 60000;
    int seconds = (int)remainder / 1000;
    return StringUtils.padZeros(hours, 2) + ":" + 
      StringUtils.padZeros(minutes, 2) + ":" + StringUtils.padZeros(seconds, 2);
  }
  
  private static String percent(int value, int max) {
    int percent = (int)((float)value / (float)max * 100.0f);
    return ""+percent+"%";
  }
  
  private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
}
