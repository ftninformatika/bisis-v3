package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import java.util.Vector;

public class TableModelFInd {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  public TableModelFInd(Connection conn, String where) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select po_poljeid, prindid, pi_naziv  from prvi_indikator"+where);
      if (where.equals("")) {
        rset.next();
        String s = rset.getString(1);
        while (s.equals(rset.getString(1))) {
          fieldCodes.addElement(rset.getString(1));
          fiCodes.addElement(rset.getString(2));
          fiName.addElement(rset.getString(3));
          rset.next();
        }
      }
      else {
        while (rset.next()) {
          fieldCodes.addElement(rset.getString(1));
          fiCodes.addElement(rset.getString(2));
          fiName.addElement(rset.getString(3));
        }
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public int getRowCount() { return fiCodes.size(); }
  public int getColumnCount() { return 3; }

  public Object getValueAt(int row, int col) {
    switch (col) {
      case 0: return fieldCodes.elementAt(row);
      case 1: return fiCodes.elementAt(row);
      case 2: return fiName.elementAt(row);
      default: return fiName.elementAt(row);
    }
  }

  public void setValues(int col, Object obj, int row, int mod) {
    if (mod == INSERT) {
      switch (col) {
        case 0: fieldCodes.insertElementAt((String)obj, row);
        case 1: fiCodes.insertElementAt((String)obj, row);
        case 2: fiName.insertElementAt((String)obj, row);
      }
    }
    else {
      if (mod == UPDATE) {
        switch (col) {
        case 0: fieldCodes.insertElementAt((String)obj, row);
        case 1: fiCodes.insertElementAt((String)obj, row);
        case 2: fiName.insertElementAt((String)obj, row);
        }
      }
    }
  }

  Connection conn;
  Vector fieldCodes = new Vector();
  Vector fiCodes = new Vector();
  Vector fiName = new Vector();
}
