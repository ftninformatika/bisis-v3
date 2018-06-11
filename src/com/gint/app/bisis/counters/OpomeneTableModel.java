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
public class OpomeneTableModel extends AbstractTableModel {

  public OpomeneTableModel(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT warn_type, warn_year, last_no FROM warn_counters order by warn_type, warn_year");
      while (rset.next()) {
        brojaci.add(new OpomeneCounter(new Integer(rset.getInt(1)), new Integer(rset.getInt(2)), new Integer(rset.getInt(3))));
      }
      rset.close();
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public Object getValueAt(int row, int col) {
    OpomeneCounter c = (OpomeneCounter)brojaci.get(row);
    switch (col) {
      case 0:
        return c.getWarnType();
      case 1:
        return c.getWarnYear();
      case 2:
        return c.getLastNo();
    }
    return null;
  }
  
  public void setValueAt(Object value, int row, int col) {
  	OpomeneCounter c;
    switch (col) {
      case 0:
      	c = (OpomeneCounter)brojaci.get(row);
        c.setWarnType((Integer)value);
      case 1:
      	c = (OpomeneCounter)brojaci.get(row);
        c.setWarnYear((Integer)value);	
      case 2:
        c = (OpomeneCounter)brojaci.get(row);
        c.setLastNo((Integer)value);
    }
  }
  
  public Class getColumnClass(int col) {
     return Integer.class;
  }
  
  public int getRowCount() {
    return brojaci.size();
  }
  
  public int getColumnCount() {
    return 3;
  }
  
  public boolean isCellEditable(int row, int col) {
    return true;
  }
  
  public void addRow() {
    OpomeneCounter newRow = new OpomeneCounter(new Integer(0), new Integer(0), new Integer(0));
    brojaci.add(newRow);
    fireTableDataChanged();
  }
  
  public void delRow(int row) {
    brojaci.remove(row);
    fireTableDataChanged();
  }
  
  public void save(Connection conn) {
    try {
    	Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM warn_counters");
        stmt.close();
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO warn_counters (warn_type, warn_year, last_no) VALUES (?, ?, ?)");
        
      Iterator it = brojaci.iterator();
      while (it.hasNext()) {
        OpomeneCounter c = (OpomeneCounter)it.next();
        pstmt.clearParameters();
        pstmt.setInt(1, c.getWarnType().intValue());
        pstmt.setInt(2, c.getWarnYear().intValue());
        pstmt.setInt(3, c.getLastNo().intValue());
        pstmt.executeUpdate();
      }
      pstmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  List brojaci = new ArrayList();
}
