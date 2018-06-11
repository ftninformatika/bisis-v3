package com.gint.app.bisis.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gint.util.string.StringUtils;

/**
 *
 * @author branko
 */
public class InvNumHoles {

  public static void main(String[] args) {
    if (args.length < 5) {
      System.out.println("Usage: InvNumHoles <address> <dbname> <username> <password> <output-file> [-compress]");
      return;
    }
    String address    = args[0];
    String dbname     = args[1];
    String username   = args[2];
    String password   = args[3];
    String outputFile = args[4];
    boolean compressed = false;
    if (args.length == 6)
      if (args[5].equals("-compressed"))
        compressed = true;
    try {
      String url = "jdbc:sapdb://" + address + "/" + dbname 
          + "?unicode=yes&timeout=0";
      Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
      PrintWriter out = new PrintWriter(new BufferedWriter(new 
          OutputStreamWriter(new FileOutputStream(outputFile), "UTF8")));
      out.println("<html><head><title>Rupe u inventarnim brojevima</title>");
      out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"utf-8\">");
      out.println("</head><body><h1>Rupe u inventarnim brojevima</h1>");
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. hh:mm");
      out.println("<h3>" + sdf.format(new Date()) + "</h3>");
      out.println("<p>");
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT ctlg_no FROM circ_docs ORDER BY ctlg_no ASC");
      int lastOgr = 1;
      int lastPubType = 0;
      int lastInvNo = 0;
      while (rset.next()) {
        String sInvNo = rset.getString(1);
        if (sInvNo.length() != 11)
          continue;
        try {
          int ogr = Integer.parseInt(sInvNo.substring(0, 2));
          int pubType = Integer.parseInt(sInvNo.substring(2, 4));
          int invNo = Integer.parseInt(sInvNo.substring(4));
          if (ogr == lastOgr && lastPubType == pubType && invNo != (lastInvNo+1)) {
            int start = lastInvNo + 1;
            int end = invNo - 1;
            display(start, end, ogr, pubType, out, compressed);
          } else if ((lastOgr != ogr || lastPubType != pubType) && invNo != 1) {
            int start = 1;
            int end = invNo - 1;
            display(start, end, ogr, pubType, out, compressed);
          }
          lastOgr = ogr;
          lastPubType = pubType;
          lastInvNo = invNo;
        } catch (NumberFormatException ex) {
          out.println("Neispravan inv. broj: " + sInvNo + "<br/>"); 
          continue;
        }
      }
      rset.close();
      stmt.close();
      conn.close();
      out.println("</p>");
      out.println("</body></html>");
      out.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static void display(int start, int end, int ogr, int pubType, 
      PrintWriter out, boolean compressed) {
    try {
	    if (start == end) {
	      String missing = StringUtils.padZeros(ogr, 2) + 
	      StringUtils.padZeros(pubType, 2) + StringUtils.padZeros(start, 7);
	      		out.println(missing + "<br/>");
	      return;
	    }
	    if (compressed) {
	      String missing1 = StringUtils.padZeros(ogr, 2) + 
	        StringUtils.padZeros(pubType, 2) + StringUtils.padZeros(start, 7);
	      String missing2 = StringUtils.padZeros(ogr, 2) + 
	        StringUtils.padZeros(pubType, 2) + StringUtils.padZeros(end, 7);
	      out.println(missing1 + " - " + missing2 + "<br/>");
	    } else {
	      for (int i = start; i <= end; i++) {
	        String missing = StringUtils.padZeros(ogr, 2) + 
	            StringUtils.padZeros(pubType, 2) + StringUtils.padZeros(i, 7);
	        out.println(missing + "<br/>");
	      }
	    }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
