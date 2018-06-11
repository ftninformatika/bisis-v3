package com.gint.app.bisis.export.circ;

import java.io.*;
import java.sql.*;
import com.gint.app.bisis.export.*;
import com.gint.app.bisis.export.dbutil.*;

/**
 * Provides import for Circulation subsystem.
 */
public class Importer {

  /**
   * Constructor to be used within a <code>SwingWorker</code> thread.
   * @param filename The name of the file to import
   * @param conn Connection to the database
   * @param p Progress dialog
   * boolean eraseDocs Flag indicating whether current circulation data (stored
   *   in <code>CIRC_DOCS</code> table) is to be erased
   */
  public Importer(String filename, Connection conn, com.gint.app.bisis.export.libenv.ProgressDlg p, boolean eraseDocs) {
    BufferedReader in = null;
    TableData tableData = new TableData();

    // Truncate all tables' contents
    for (int i = Config.circTableNames.length-1; i >= 0; i--) {
      // CIRC_DOCS gets some special care
      if (Config.circTableNames[i].equals("CIRC_DOCS")) {
        if (eraseDocs)
          tableData.deleteData(conn, Config.circTableNames[i]);
      } else
        tableData.deleteData(conn, Config.circTableNames[i]);
    }
    try {
      in = new BufferedReader(new InputStreamReader(
      	new FileInputStream(filename), "UTF-8"));
      String line = "";
      StringBuffer buffer = new StringBuffer(8192);
      while ((line = in.readLine()) != null) {
      	buffer.append(line);
      	buffer.append("\n");
      }

      String text = buffer.toString();
      int dashPos1 = text.indexOf("$SEPARATOR$", 0);
      if (dashPos1 != 0)
        return;
      int dashPos2 = text.indexOf("$SEPARATOR$", 3);
      int tableCounter = 0;
      while (dashPos1 != -1) {
        String tableText;
        if (dashPos2 != -1)
          tableText = text.substring(dashPos1, dashPos2).trim();
        else
          tableText = text.substring(dashPos1).trim();
        // CIRC_DOCS can be updated instead of inserted
        if (Config.circTableNames[tableCounter].equals("CIRC_DOCS")) {
          if (eraseDocs)
            tableData.setData(conn, Config.circTableNames[tableCounter], Config.circTableCols[tableCounter], Config.circColTypes[tableCounter], tableText);
          else
            tableData.updateData(conn, tableText);
        }
        else
          tableData.setData(conn, Config.circTableNames[tableCounter], Config.circTableCols[tableCounter], Config.circColTypes[tableCounter], tableText);
        dashPos1 = dashPos2;
        dashPos2 = text.indexOf("$SEPARATOR$", dashPos2+3);
        tableCounter++;
      }
      in.close();
      
//      //
//      // SEQUENCE mmbridseq must be updated to (MAX(id) from USERS) + 1
//      //
//      Statement stmt = conn.createStatement();
//      ResultSet rset = stmt.executeQuery("SELECT MAX(id) FROM USERS");
//      if (rset.next()) {
//        int lastMemberID = rset.getInt(1);
//        stmt.executeUpdate("DROP SEQUENCE mmbridseq");
//        stmt.executeUpdate(
//          "CREATE SEQUENCE mmbridseq START WITH " + (lastMemberID + 1) + " NOCACHE");
//      }
//      rset.close();
//      stmt.close();
      
      p.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
