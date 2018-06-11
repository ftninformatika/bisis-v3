package com.gint.app.bisis4.common.records;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.List;
/**
 * @author mbranko@uns.ns.ac.yu
 *
 */
public class RecordTest {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: RecordTest <inputfile> [<testid>]");
      System.out.println("  If <testid> is omitted, all tests are run.");
      return;
    }
    if (args.length > 1) {
      int testid = 0;
      try { testid = Integer.parseInt(args[1]); } catch (Exception ex) { }
      switch (testid) {
        case 1:
          test1(args[0]);
          break;
        case 2:
          test2(args[0]);
          break;
        case 3:
          test3(args[0]);
          break;
        case 4:
          test4(args[0]);
          break;
        case 5:
          test5(args[0]);
          break;
        case 6:
          test6(args[0]);
          break;
        case 7:
          test7(args[0]);
          break;
        case 8:
          test8(args[0]);
          break;
         // ... add tests here
        default:
          System.out.println("Illegal test ID.");
          break;
      }
    } else {
      test1(args[0]);
      test2(args[0]);
      test3(args[0]);
      test4(args[0]);
      test5(args[0]);
      test6(args[0]);
      test7(args[0]);
      test8(args[0]);
      // ... add tests here
    }
    
  }
  
  public static void test1(String inputFile) {
    System.out.println("Running test 1...");
    try {
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      String line;
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        Record rec = RecordFactory.fromUNIMARC(line);
        System.out.println(rec);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void test2(String inputFile) {
    System.out.println("Running test 2...");
    try {
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      String line;
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        Record rec = RecordFactory.fromUNIMARC(line);
        System.out.println(RecordFactory.toUNIMARC(rec));
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void test3(String inputFile) {
    System.out.println("Running test 3...");
    try {
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      String line;
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        Record rec = RecordFactory.fromUNIMARC(line);
        System.out.println(RecordFactory.toFullFormat(rec, false));
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void test4(String inputFile) {
    System.out.println("Running test 4...");
    try {
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      String line;
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        Record rec = RecordFactory.fromUNIMARC(line);
        System.out.println(RecordFactory.toLooseXML(rec));
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void test5(String inputFile) {
    System.out.println("Running test 5...");
    try {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
      String line;
      StringBuffer buff = new StringBuffer();
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        buff.append(line);
      }
      in.close();
      Record rec = RecordFactory.fromLooseXML(buff.toString());
      System.out.println(RecordFactory.toFullFormat(rec, false));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static void test6(String inputFile) {
    System.out.println("Running test 6...");
    try {
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tight.xml"), "UTF8")));
      out.println("<unimarc>");
      String line;
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        Record rec = RecordFactory.fromUNIMARC(line);
        rec.pack();
        out.println(RecordFactory.toTightXML(rec));
      }
      in.close();
      out.println("</unimarc>");
      out.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void test7(String inputFile) {
    System.out.println("Running test 7...");
    try {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
      String line;
      StringBuffer buff = new StringBuffer();
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        buff.append(line);
      }
      in.close();
      Record rec = RecordFactory.fromTightXML(buff.toString());
      System.out.println(RecordFactory.toFullFormat(rec, false));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void test8(String inputFile) {
    System.out.println("Running test 8...");
    try {
      RandomAccessFile in = new RandomAccessFile(inputFile, "r");
      String line;
      int count = 0;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals(""))
          continue;
        Record rec = RecordFactory.fromUNIMARC(line);
        List prefixValues = RecordFactory.toPrefixes(rec);
        System.out.println("--------");
        for (int i = 0; i < prefixValues.size(); i++) {
          PrefixValue pv = (PrefixValue)prefixValues.get(i);
          System.out.println(pv.prefName + ":" + pv.value);
        }
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

