package com.gint.app.bisis.counters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class KorisTableModel extends AbstractTableModel {

  public KorisTableModel(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT id, name, last_user_id FROM branch");
      while (rset.next()) {
        ogranci.add(new KorisCounter(new Integer(rset.getInt(1)), rset.getString(2), new Integer(rset.getInt(3))));
      }
      rset.close();
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public Object getValueAt(int row, int col) {
    KorisCounter c = (KorisCounter)ogranci.get(row);
    switch (col) {
      case 0:
        return c.getBranchID();
      case 1:
        return c.getDescription();
      case 2:
        return c.getLastValue();
    }
    return null;
  }
  
  public void setValueAt(Object value, int row, int col) {
    switch (col) {
      case 2:
        KorisCounter c = (KorisCounter)ogranci.get(row);
        c.setLastValue((Integer)value);
    }
  }
  
  public Class getColumnClass(int col) {
    switch (col) {
      case 0:
      case 2:
        return Integer.class;
      case 1:
        return String.class;
    }
    return null;
  }
  
  public int getRowCount() {
    return ogranci.size();
  }
  
  public int getColumnCount() {
    return 3;
  }
  
  public boolean isCellEditable(int row, int col) {
    return col == 2;
  }
  
  public void save(Connection conn) {
    try {
      PreparedStatement pstmt = conn.prepareStatement(
          "UPDATE branch SET last_user_id=? WHERE id=?");
      Iterator it = ogranci.iterator();
      while (it.hasNext()) {
        KorisCounter c = (KorisCounter)it.next();
        pstmt.clearParameters();
        pstmt.setInt(1, c.getLastValue().intValue());
        pstmt.setInt(2, c.getBranchID().intValue());
        pstmt.executeUpdate();
      }
      pstmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  List ogranci = new ArrayList();
}
