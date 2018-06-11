package com.gint.app.bisis.export.libenv;

import java.io.*;
import java.sql.*;
import com.gint.app.bisis.export.*;
import com.gint.app.bisis.export.dbutil.*;

public class Exporter {
  public Exporter(String filename, Connection conn, ProgressDlg p) {
    BufferedWriter out = null;
    try {
      StringBuffer report = new StringBuffer(204800); // 200 KB
      TableData tableData = new TableData();
      for (int i = 0; i < Config.okruzTableNames.length; i++) {
        report.append(tableData.getData(conn, Config.okruzTableNames[i], Config.okruzTableCols[i]));
      }
      out = new BufferedWriter(new OutputStreamWriter(
      	new FileOutputStream(filename), "UTF-8"));
      String str = report.toString();
      out.write(str, 0, str.length());
      out.close();
      p.setFinished(true);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
