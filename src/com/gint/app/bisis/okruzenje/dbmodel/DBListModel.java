package com.gint.app.bisis.okruzenje.dbmodel;

import java.sql.*;
import javax.swing.*;
import java.util.*;

public class DBListModel  extends AbstractListModel implements ListModel{

  Connection conn;

  public DBListModel(Connection conn, String query, String where) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query+where);
      int i = 0;
      while(rset.next()) {
        values.addElement((rset.getString(1) == null)? "" : rset.getString(1));
        desc.addElement((rset.getString(2) == null)? "" : rset.getString(2));
        items.addElement((String)values.get(i)+"-"+(String)desc.get(i));
        i++;
      }
      items.insertElementAt("---bez selekcije---",0);
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public DBListModel(Connection conn, String query) {
    this.conn = conn;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      int i = 0;
      while(rset.next()) {
        values.addElement((rset.getString(1) == null)? "" : rset.getString(1));
        desc.addElement((rset.getString(2) == null)? "" : rset.getString(2));
        items.addElement((String)values.get(i)+"-"+(String)desc.get(i));
        i++;
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public int getSize() {
    return items.size();
  }

  public Object getElementAt(int row) {
    return items.elementAt(row);
  }

  public void reloadItems(String select){
    items.removeAllElements();
    values.removeAllElements();
    desc.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select);
      int i = 0;
      while(rset.next()) {
        values.addElement((rset.getString(1) == null)? "" : rset.getString(1));
        desc.addElement((rset.getString(2) == null)? "" : rset.getString(2));
        items.addElement((String)values.get(i)+"-"+(String)desc.get(i));
        i++;
      }
      rset.close();
      stmt.close();
      items.insertElementAt("---bez selekcije---",0);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    fireContentsChanged(this,0,items.size()-1);
  }

  public void reloadItems(String select, String where){
    items.removeAllElements();
    values.removeAllElements();
    desc.removeAllElements();
    try{
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(select+where);
      int i = 0;
      while(rset.next()) {
        values.addElement((rset.getString(1) == null)? "" : rset.getString(1));
        desc.addElement((rset.getString(2) == null)? "" : rset.getString(2));
        items.addElement((String)values.get(i)+"-"+(String)desc.get(i));
        i++;
      }
      rset.close();
      stmt.close();
      items.insertElementAt("---bez selekcije---",0);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    fireContentsChanged(this,0,items.size()-1);
  }


  Vector items = new Vector();
  Vector values = new Vector();
  Vector desc = new Vector();
}