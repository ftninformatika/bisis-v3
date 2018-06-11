package com.gint.app.bisis.export.circ;

import java.io.*;
import java.sql.*;
import com.gint.app.bisis.export.*;
import com.gint.app.bisis.export.dbutil.*;

/**
 * Provides export for Circulation subsystem.
 */
public class Exporter {
  /**
   * Constructor to be used within a <code>SwingWorker</code> thread.
   * @param filename The name of the target file for export
   * @param conn Connection to the database
   * @param p Progress dialog
   */
  public Exporter(String filename, Connection conn, com.gint.app.bisis.export.libenv.ProgressDlg p) {
    BufferedWriter out = null;
    try {
      StringBuffer report = new StringBuffer(204800);
      TableData tableData = new TableData();
      out = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(filename), "UTF-8"));
      for (int i = 0; i < Config.circTableNames.length; i++) {
        out.write(tableData.getData(conn, Config.circTableNames[i], Config.circTableCols[i]));
      }
      out.close();
      p.setFinished(true);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
