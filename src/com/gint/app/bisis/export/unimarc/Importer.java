package com.gint.app.bisis.export.unimarc;

import java.io.*;
import java.sql.*;
import com.gint.app.bisis.export.*;
import com.gint.app.bisis.export.dbutil.*;

/**
 * Provides import for the UNIMARC subsystem.
 */
public class Importer {

  /**
   * Constructor to be used within a <code>SwingWorker</code> thread.
   * @param filename The name of the file to be imported
   * @param conn Connection to the database
   * @param p Progress dialog
   */
  public Importer(String filename, Connection conn, com.gint.app.bisis.export.libenv.ProgressDlg p) {
    BufferedReader in = null;
    TableData tableData = new TableData();

    // truncate all tables' contents
    for (int i = Config.unimarcTableNames.length-1; i >= 0; i--)
      tableData.deleteData(conn, Config.unimarcTableNames[i]);

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
        System.out.println(Config.unimarcTableNames[tableCounter]);
        tableData.setData(conn, Config.unimarcTableNames[tableCounter], Config.unimarcTableCols[tableCounter], Config.unimarcColTypes[tableCounter], tableText);
        dashPos1 = dashPos2;
        dashPos2 = text.indexOf("$SEPARATOR$", dashPos2+3);
        tableCounter++;
      }
      in.close();
      p.setFinished(true);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
