package com.gint.app.bisis.export.unimarc;

import java.io.*;
import java.sql.*;

import com.gint.app.bisis.export.*;
import com.gint.app.bisis.export.dbutil.*;

/**
 * Provides export for UNIMARC subsystem.
 */
public class Exporter {
  /**
   * Constructor to be used within a <code>SwingWorker</code> thread.
   * @param filename The name of the target export file
   * @param conn Connection to the database
   * @param p Progress dialog
   */
  public Exporter(String filename, Connection conn, com.gint.app.bisis.export.libenv.ProgressDlg p) {
    BufferedWriter out = null;
    try {
      StringBuffer report = new StringBuffer(204800); // 200 KB
      TableData tableData = new TableData();
      for (int i = 0; i < Config.unimarcTableNames.length; i++) {
        report.append(tableData.getData(conn, Config.unimarcTableNames[i], Config.unimarcTableCols[i]));
      }
      out = new BufferedWriter(new OutputStreamWriter(
      	new FileOutputStream(filename), "UTF-8"));
      String str = report.toString();
      out.write(str, 0, str.length());
      out.close();
      p.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
