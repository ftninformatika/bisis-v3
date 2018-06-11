package com.gint.app.bisis.counters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.gint.util.string.StringUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class InvBrTableModel extends AbstractTableModel {

  public InvBrTableModel(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT counter_id, last_value FROM InvCounters");
      while (rset.next()) {
        invbrojevi.add(new InvBrCounter(rset.getString(1), new Integer(rset.getInt(2))));
      }
      rset.close();
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public Object getValueAt(int row, int col) {
    InvBrCounter c = (InvBrCounter)invbrojevi.get(row);
    if (col == 0)
      return c.getCounterID();
    else
      return c.getLastValue();
  }
  
  public void setValueAt(Object value, int row, int col) {
    if (col == 1) {
      InvBrCounter c = (InvBrCounter)invbrojevi.get(row);
      c.setLastValue((Integer)value);
    } else if (col == 0) {
      InvBrCounter c = (InvBrCounter)invbrojevi.get(row);
      c.setCounterID((String)value);
    }
  }
  
  public Class getColumnClass(int col) {
    if (col == 1)
      return Integer.class;
    else
      return String.class;
  }
  
  public int getRowCount() {
    return invbrojevi.size();
  }
  
  public int getColumnCount() {
    return 2;
  }
  
  public boolean isCellEditable(int row, int col) {
    return true;
  }
  
  public void addRow() {
    InvBrCounter newRow = new InvBrCounter(StringUtils.padZeros(invbrojevi.size(), 2), new Integer(0));
    invbrojevi.add(newRow);
    fireTableDataChanged();
  }
  
  public void delRow(int row) {
    invbrojevi.remove(row);
    fireTableDataChanged();
  }
  
  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("DELETE FROM InvCounters");
      stmt.close();
      PreparedStatement pstmt = conn.prepareStatement(
          "INSERT INTO InvCounters (counter_id, last_value) VALUES (?, ?)");
      Iterator it = invbrojevi.iterator();
      while (it.hasNext()) {
        InvBrCounter c = (InvBrCounter)it.next();
        pstmt.clearParameters();
        pstmt.setString(1, c.getCounterID());
        pstmt.setInt(2, c.getLastValue().intValue());
        pstmt.executeUpdate();
      }
      pstmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  List invbrojevi = new ArrayList();
}
