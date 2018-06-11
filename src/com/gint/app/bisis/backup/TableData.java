package com.gint.app.bisis.backup;

import java.io.PrintWriter;
import java.sql.*;
import java.util.StringTokenizer;

import com.gint.util.string.StringUtils;

public class TableData {

  public static void saveData(Connection conn, PrintWriter out, 
      String tableName, String tableCols) {
    StringTokenizer st = new StringTokenizer(tableCols, ",");
    int colsCount = st.countTokens();
    StringBuffer retVal = new StringBuffer(65536);
    try {
      int redbr = 0;
      int numberOfColumns = 0;
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
        "SELECT " + tableCols + " FROM " + tableName);
      
      out.println("$SEPARATOR$"+tableName);

      while (rset.next()) {
        redbr++;
        out.print("#");
        for (int i = 0; i < colsCount; i++) {
          String temp = rset.getString(i+1);
          if (temp == null)
            temp = "";
          else
            temp = temp.trim();
          temp = temp.replace('#', ' ');
          temp = StringUtils.replace(temp, "\r\n", "\t");
          temp = temp.replace('\n', '\t').replace('\r', '\t');
          out.print(temp);
          out.print("#");
        }
        out.println("");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }


}
