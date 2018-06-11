package com.gint.app.bisis.export.dbutil;

import java.sql.*;
import java.util.StringTokenizer;

import com.gint.util.string.StringUtils;

/** 
 * Provides import/export operations on a single database table. 
 * 
 * @author Branko Milosavljevic mbranko@uns.ns.ac.yu
 */
public class TableData {

  public TableData() {
  }

  /** 
   * Retrieves a table's rows and encodes them in a string.
   * @param conn Connection to the database
   * @param tableName The name of the table to retrieve
   * @param tableCols Comma-separated list of table's column names
   * @return Encoded string.
   */
  public String getData(Connection conn, String tableName, String tableCols) {
    int colsCount = 0;
    StringTokenizer st = new StringTokenizer(tableCols, ",");
    while (st.hasMoreTokens()) {
      colsCount++;
      st.nextToken();
    }
    StringBuffer retVal = new StringBuffer(204800); // 200 KB buffer
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
        "SELECT " + tableCols + " FROM " + tableName);
      retVal.append("$SEPARATOR$");
      retVal.append(tableName);
      retVal.append("\n");

      while (rset.next()) {
        retVal.append("#");
        for (int i = 0; i < colsCount; i++) {
          String temp = rset.getString(i+1);
          if (temp == null)
            temp = "";
          else
            temp = temp.trim();
          temp = StringUtils.replace(temp, "\r\n", "\t");
          temp = temp.replace('#', ' ').replace('\n', '\t').replace('\r', '\t');
          retVal.append(temp);
          retVal.append("#");
        }
        retVal.append("\n");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return retVal.toString();
  }
  
  
  /** 
   * budzotina za izdvajanje clanova jednog ogranka. napravljeno za gbbg.
   */
  public String getData2(Connection conn, String tableName, String tableCols) {
    int colsCount = 0;
    StringTokenizer st = new StringTokenizer(tableCols, ",");
    while (st.hasMoreTokens()) {
      colsCount++;
      st.nextToken();
    }
    StringBuffer retVal = new StringBuffer(204800); // 200 KB buffer
    try {
    	String query = "SELECT " + tableCols + " FROM " + tableName;
    	if (tableName.equals("USERS")){
    		query = query + " WHERE ID LIKE '12%' OR ID = '0'";
    	} else if (tableName.equals("SINGLE")){
    		query = query + " WHERE USER_ID LIKE '12%'";
    	} else if (tableName.equals("LENDING")){
    		query = query + " WHERE SINGLE_ID LIKE '12%'";
    	} else if (tableName.equals("SIGNING")){
    		query = query + " WHERE SINGLE_ID LIKE '12%'";
    	} else if (tableName.equals("DUPLICATE")){
     		query = query + " WHERE USER_ID LIKE '12%'";
     	} else if (tableName.equals("BLOCKED_CARDS")){
     		query = query + " WHERE USER_ID LIKE '12%'";
     	};
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      retVal.append("$SEPARATOR$");
      retVal.append(tableName);
      retVal.append("\n");

      while (rset.next()) {
        retVal.append("#");
        for (int i = 0; i < colsCount; i++) {
          String temp = rset.getString(i+1);
          if (temp == null)
            temp = "";
          else
            temp = temp.trim();
          temp = StringUtils.replace(temp, "\r\n", "\t");
          temp = temp.replace('#', ' ').replace('\n', '\t').replace('\r', '\t');
          retVal.append(temp);
          retVal.append("#");
        }
        retVal.append("\n");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return retVal.toString();
  }

  /**
   * Deletes all of the table's data.
   * @param conn Connection to the database
   * @param tableName The name of the table to truncate.
   */
  public void deleteData(Connection conn, String tableName) {
    try {
      Statement stmt = conn.createStatement();
      while (stmt.executeUpdate("DELETE FROM "+tableName+" CASCADE") > 0)
        ;
      stmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Populates a table with the given data.
   * @param conn Connection to the database
   * @param tableName The name of the table to populate
   * @param tableCols Comma-separated list of table column names
   * @param colTypes An array of table column type identificators
   * @param data The encoded string with the table data
   */
  public void setData(Connection conn, String tableName, String tableCols, 
      int[] colTypes, String data) {
    String insert = "INSERT INTO "+tableName+" ("+tableCols+") VALUES (?";
    for (int i = 1; i < colTypes.length; i++)
      insert += ",?";
    insert += ")";
    String temp = "";
    String cumul = "";
    try {
      PreparedStatement pstmt = conn.prepareStatement(insert);
      int pos1 = data.indexOf('#', 0);
      int pos2 = data.indexOf('#', pos1+1);
      while (pos1 != -1) {
        cumul = "";
        pstmt.clearParameters();
        if (pos2 != -1)
          temp = data.substring(pos1+1, pos2).trim();
        else
          temp = data.substring(pos1+1).trim();

        if (temp.equals("")) {
          pos1 = pos2;
          pos2 = data.indexOf('#', pos2+1);
          if (pos1 != -1)
            if (pos2 != -1)
              temp = data.substring(pos1+1, pos2).trim();
            else
              temp = data.substring(pos1+1).trim();
          continue;
        }

        for (int i = 0; i < colTypes.length; i++) {
          cumul += temp + "#";
          switch (colTypes[i]) {
            case Types.INTEGER:
              int val = 0;
              try {
                val = Integer.parseInt(temp);
                pstmt.setInt(i+1, val);
              } catch (Exception ex) {
                pstmt.setNull(i+1, Types.INTEGER);
              }
              break;
            case Types.CHAR:
            case Types.VARCHAR:
              pstmt.setString(i+1, temp.replace('\t', '\n'));
              break;
            case Types.DOUBLE:
              double dval = 0;
              try {
                dval = Double.parseDouble(temp);
                pstmt.setDouble(i+1, dval);
              } catch (Exception ex) {
                pstmt.setNull(i+1, Types.DOUBLE);
              }
              break;
            case Types.DATE:
              try {
                String temp1=temp.substring(0,10);
                pstmt.setDate(i+1, java.sql.Date.valueOf(temp1));
              } catch (Exception ex) {
                pstmt.setNull(i+1, Types.DATE);
              }
              break;
          }
          pos1 = pos2;
          pos2 = data.indexOf('#', pos2+1);
          if (pos1 != -1)
            if (pos2 != -1)
              temp = data.substring(pos1+1, pos2).trim();
            else
              temp = data.substring(pos1+1).trim();
        
        }
        pstmt.executeUpdate();
      }
      pstmt.close();
      conn.commit();
    } catch (Exception ex) {
      System.out.println("Exception: " + ex.getMessage());
      System.out.println(insert);
      System.out.println(cumul);
    }
  }

  /**
   * Updates the table with the given set of values. Currently works only with 
   * the CIRC_DOCS table.
   * 
   * THIS METHOD DOES NOT WORK AT ALL. DO NOT INVOKE IT.
   * 
   * @param conn Connection to the database
   * @param data The encoded string with the table data
   */
  public void updateData(Connection conn, String data) {
    String update = "UPDATE CIRC_DOCS set STATUS=? where CTLG_NO=?";

    String temp = "";
    String ctlg_no="";
    try {
      PreparedStatement pstmt = conn.prepareStatement(update);
      String s=new String();
      int pos1 = data.indexOf('#', 0);
      int pos2 = data.indexOf('#', pos1+1);

      while (pos1 != -1) {
        pstmt.clearParameters();
        if (pos2 != -1)
          temp = data.substring(pos1+1, pos2).trim();
        else
          temp = data.substring(pos1+1).trim();

        if (temp.equals("")) {
          pos1 = pos2;
          pos2 = data.indexOf('#', pos2+1);
          if (pos1 != -1)
            if (pos2 != -1)
              temp = data.substring(pos1+1, pos2).trim();
            else
              temp = data.substring(pos1+1).trim();
          continue;
        }

        for (int i = 0; i < 7; i++) {
          if (i==1) {
            ctlg_no=temp;
          }
          if (i==3) {
            double dval = 0;
            try {
              dval = Double.parseDouble(temp);
              pstmt.setDouble(1, dval);
            } catch (Exception ex) {
              pstmt.setNull(1, Types.DOUBLE);
            }
            pstmt.setString(2, ctlg_no);
          }
          pos1 = pos2;
          pos2 = data.indexOf('#', pos2+1);
          if (pos1 != -1)
            if (pos2 != -1)
              temp = data.substring(pos1+1, pos2).trim();
            else
              temp = data.substring(pos1+1).trim();
        }
        pstmt.executeUpdate();
      }
      pstmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
