
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

public class Duplicate extends EscapeDialog {
  DBConnection dbConn;
  DataSet dsDup;
  JDBTableModel dmDup;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTable tblDup;// = new JTable();
  JScrollPane sp1;
  JButton btnDelete = new JButton();
  JButton btnInsert = new JButton();
  JButton btnSave = new JButton();
  JButton btnExit = new JButton();
  SingleMember sm;

  public Duplicate(Frame frame, String title, boolean modal,DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Duplicate() {
    this(CircApplet.parent, "", false,null);
  }

  public Duplicate(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    java.util.Vector col = new java.util.Vector();
    col.addElement(Messages.get("RENEWMEMBERSHIP_COL_EL1"));
    col.addElement(Messages.get("RENEWMEMBERSHIP_COL_EL2"));
    col.addElement(Messages.get("RENEWMEMBERSHIP_COL_EL3"));
    dsDup = new DataSet(dbConn.getConnection(),"DUPLICATE",new String[] {"USER_ID","DUP_NO"});
    tblDup = new JTable();
    sp1 = new JScrollPane(tblDup);

    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(300);
    btnDelete.setText(Messages.get("RENEWMEMBERSHIP_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });
    btnInsert.setText(Messages.get("PLACES_BTNINSERT1_TEXT"));
    btnInsert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnInsert_actionPerformed(e);
      }
    });
    btnSave.setText(Messages.get("RENEWMEMBERSHIP_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });
    btnExit.setText(Messages.get("RENEWMEMBERSHIP_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    panel1.setPreferredSize(new Dimension(370, 160));
    panel1.setMinimumSize(new Dimension(370, 160));
    xYLayout1.setWidth(370);
    getContentPane().add(panel1);
    panel1.add(sp1, new XYConstraints(20, 20, 330, 100));
    panel1.add(btnDelete, new XYConstraints(190, 125, 80, 25));
    panel1.add(btnInsert, new XYConstraints(100, 125, 80, 25));
    panel1.add(btnSave, new XYConstraints(10, 125, 80, 25));
    panel1.add(btnExit, new XYConstraints(280, 125, 80, 25));
  }

  void btnSave_actionPerformed(ActionEvent e) {
    dmDup.saveData();
    JOptionPane.showMessageDialog(null,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG1"),Messages.get("SINGLEMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
  }

  void btnInsert_actionPerformed(ActionEvent e) {
    dmDup.expand();
    dmDup.setValueAt(DBConnection.getTodaysDate(),dmDup.getLastRow(),2);
    if (dmDup.getLastRow() > 0){
    	dmDup.setValueAt(String.valueOf(Integer.parseInt((String)dmDup.getValueAt(dmDup.getLastRow()-1,1))+1),dmDup.getLastRow(),1);
    } else {
    	dmDup.setValueAt("1",dmDup.getLastRow(),1);
    }
  }

  

  void btnDelete_actionPerformed(ActionEvent e) {
    dmDup.deleteRow(tblDup.getSelectedRow());
  }

  void btnExit_actionPerformed(ActionEvent e) {
	 sm.setDup();
     this.setVisible(false);
  }

  public void setVisible(boolean vis){
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id, SingleMember sm){
//    super.setVisible(vis); 15.02.00.
	  
	this.sm = sm;
    setPosition(id);
    super.setVisible(vis);
  }

  private void setPosition(String id){
    dmDup = new JDBTableModel(dsDup,"select * from DUPLICATE where USER_ID ='"+id+"'");
    tblDup.setModel(dmDup);
    TableColumnModel tcm = tblDup.getColumnModel();
    int i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);

      if(tc.getHeaderValue().toString().equals("DUP_DATE"))
        tc.setHeaderValue("DATUM");
      else if(tc.getHeaderValue().toString().equals("DUP_NO"))
        tc.setHeaderValue("BROJ");
      
      if(tc.getHeaderValue().toString().equals("USER_ID"))
          tcm.removeColumn(tc);
      else
         i++;
    }
    tblDup.setColumnModel(tcm);
    tblDup.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblDup.sizeColumnsToFit(1);
    dmDup.setDefaultCol(0,id);
   }
}

