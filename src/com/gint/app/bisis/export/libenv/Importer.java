package com.gint.app.bisis.export.libenv;

import java.io.*;
import java.sql.*;
import com.gint.app.bisis.export.*;
import com.gint.app.bisis.export.dbutil.*;

public class Importer {

  public Importer(String filename, Connection conn, ProgressDlg p) {
    //RandomAccessFile in = null;
    BufferedReader in = null;
    TableData tableData = new TableData();

    // brisanje tabela
    for (int i = Config.okruzTableNames.length-1; i >= 0; i--)
      tableData.deleteData(conn, Config.okruzTableNames[i]);

    try {
      //in = new RandomAccessFile(filename, "r");
      in = new BufferedReader(new InputStreamReader(
      	new FileInputStream(filename), "UTF-8"));
      String line = "";
      StringBuffer buffer = new StringBuffer();
      while ((line = in.readLine()) != null) {
      	buffer.append(line);
      	buffer.append("\n");
      }
      //long length = in.length();
      //byte[] buffer = new byte[(int)length];
      //in.readFully(buffer);
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
System.out.println("Importing table: " + Config.okruzTableNames[tableCounter]);
        tableData.setData(conn, Config.okruzTableNames[tableCounter], Config.okruzTableCols[tableCounter], Config.okruzColTypes[tableCounter], tableText);
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
