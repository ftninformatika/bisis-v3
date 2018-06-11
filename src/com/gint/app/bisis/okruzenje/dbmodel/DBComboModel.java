package com.gint.app.bisis.okruzenje.dbmodel;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class DBComboModel extends AbstractListModel implements ComboBoxModel {

 public DBComboModel(Connection conn, String query, String where) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query+where);
      while (rset.next()) {
        if (!rset.getString(1).trim().equals("000"))
          items.addElement((rset.getString(1).trim() == null)? "" : rset.getString(1).trim());
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

 public DBComboModel(Connection conn, String query, boolean name,boolean full) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      while (rset.next()) {
        if(!full) {
          if (!rset.getString(1).trim().equals("FULL") && !rset.getString(1).trim().substring(0,1).equals("L")) {
            items.addElement((rset.getString(1).trim() == null)? "" : rset.getString(1).trim());
            if(name)
              names.addElement((rset.getString(2).trim() == null)? "" : rset.getString(2).trim());
          }
      	}
      	else {
          if (!rset.getString(1).trim().substring(0,1).equals("L")) {
            items.addElement((rset.getString(1).trim() == null)? "" : rset.getString(1).trim());
            if(name)
              names.addElement((rset.getString(2).trim() == null)? "" : rset.getString(2).trim());
          }
      	}
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

 public DBComboModel(Connection conn, String query,String where, boolean name) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      query+=where;
      ResultSet rset = stmt.executeQuery(query);
      while (rset.next()) {
        items.addElement((rset.getString(1).trim() == null)? "" : rset.getString(1).trim());
        if(name)
          names.addElement((rset.getString(2).trim() == null)? "" : rset.getString(2).trim());
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  //dodato iskljucivo zbog tabele prefix_map koju Branko koristi
  public DBComboModel(Connection conn, String query) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      String temp = "";
      while (rset.next()) {
        if(!temp.equals(rset.getString(1).substring(0,3))) {
          temp = rset.getString(1).substring(0,3);
          items.addElement((rset.getString(1).substring(0,3) == null)? "" : rset.getString(1).substring(0,3));
          names.addElement((rset.getString(2).trim() == null)? "" : rset.getString(2).trim());
        }
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }


  public int getSize() {
    return items.size();
  }

  public Object getElementAt(int row) {
    return (Object)items.elementAt(row);
  }
  public Object getNameAt(int row) {
    return (Object)names.elementAt(row);
  }

  public void setSelectedItem(Object obj) {
    selectedObject = obj;
    fireContentsChanged(this, -1, -1);
  }

  public Object getSelectedItem() {
    return selectedObject;
  }

  public void reloadItems(String select){
    items.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select);
      while (rset.next()) {
        if(!rset.getString(1).trim().equals("000"))
          items.addElement((rset.getString(1) == null)? "" : rset.getString(1).trim());
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    fireContentsChanged(this,0,items.size()-1);
  }

  public void reloadItems(String select, String where) {
    items.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select+where);
      String temp = "";
      while (rset.next()) {
        if(!rset.getString(1).trim().equals("000"))
          if(!temp.equals(rset.getString(1).trim())) {
            temp = rset.getString(1).trim();
            items.addElement((rset.getString(1) == null)? "" : rset.getString(1).trim());
          }
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    fireContentsChanged(this,0,items.size()-1);
  }

  //dodato iskljucivo zbog tabele prefix_map koju Branko koristi
  public void reloadItems(String select, String where, int subPos) {
    items.removeAllElements();
    names.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select+where);
      String temp = "";
      while (rset.next()) {
        if(!temp.equals(rset.getString(1).substring(0,subPos))) {
          temp = rset.getString(1).substring(0,subPos);
          items.addElement((rset.getString(1).substring(0,subPos) == null)? "" : rset.getString(1).substring(0,subPos));
          names.addElement((rset.getString(2).trim() == null)? "" : rset.getString(2).trim());
        }
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    fireContentsChanged(this,0,items.size()-1);
  }

  public void reloadItems(String select,boolean name) {
    items.removeAllElements();
    names.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select);
      String temp = "";
      while (rset.next()) {
        if(!temp.equals(rset.getString(1))) {
          temp = rset.getString(1);
          items.addElement((rset.getString(1) == null)? "" : rset.getString(1));
          if(name)
            names.addElement((rset.getString(2).trim() == null)? "" : rset.getString(2).trim());
        }
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    fireContentsChanged(this,0,items.size()-1);
  }


  Connection conn;
  private Vector items = new Vector();
  private Vector names = new Vector();
  Object selectedObject;
}
