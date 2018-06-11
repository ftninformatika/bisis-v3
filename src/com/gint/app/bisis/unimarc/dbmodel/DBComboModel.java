package com.gint.app.bisis.unimarc.dbmodel;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class DBComboModel extends AbstractListModel implements ComboBoxModel {


 public DBComboModel(Connection conn, String query, boolean name) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
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

  public DBComboModel(Connection conn, String query) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      items.addElement("");
      while (rset.next()) {
        items.addElement((rset.getString(1).trim() == null)? "" : rset.getString(1).trim());
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public DBComboModel(Vector vItems) {
    for(int i=0;i<vItems.size();i++)
      items.addElement(vItems.elementAt(i));
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

  public int getNamesSize() {
    return names.size();
  }

  public void setSelectedItem(Object obj) {
    selectedObject = obj;
    fireContentsChanged(this, -1, -1);
  }

  public Object getSelectedItem() {
    return selectedObject;
  }

  public void reloadItems(Vector vItems) {
    items.removeAllElements();
    items.addElement("");
    for(int i=0;i<vItems.size();i++)
      items.addElement(vItems.elementAt(i));
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

  public void reloadItemsWithBlank(String select) {
    items.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select);
      String temp = "";
      items.addElement("");
      while (rset.next()) {
        if(!temp.equals(rset.getString(1))) {
          temp = rset.getString(1);
          items.addElement((rset.getString(1) == null)? "" : rset.getString(1));
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
