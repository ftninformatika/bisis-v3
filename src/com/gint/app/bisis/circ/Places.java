
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;

public class Places extends EscapeDialog {

  boolean chSelected ;
  boolean chPlaces = false;
  boolean chLendType = false;
  boolean chBranch = false;
  DBConnection dbConn;
  DataSet dsPlaces,dsLendType, dsBranch;  
  JDBTableModel dmPlaces,dmLendType, dmBranch, dmSelected = null;
  JTable tblPlaces, tblLendType, tblBranch, tblSelected = null;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTabbedPane tabsetPanel1 = new JTabbedPane();
  JPanel bevelPanel1 = new JPanel();
  JPanel bevelPanel2 = new JPanel();
  JPanel bevelPanel3 = new JPanel();
  JScrollPane sp1, sp2, sp3;

  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  
  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();
  JButton btnInsert = new JButton();
  JButton btnExit = new JButton();
  Lending lending; // dodato 28.02.00.
  NewMember newmember;
  SingleMember singlemember;

  public Places(Frame frame, String title, boolean modal,DBConnection db,Lending lending, NewMember nm, SingleMember sm) {
    super(frame, title, modal);
    this.lending = lending;
    newmember = nm;
    singlemember = sm;
    dbConn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Places() {
    this(CircApplet.parent, "", false,null,null,null,null);
  }

  public Places(String title,DBConnection dbConn, boolean modal, Lending lending, NewMember nm, SingleMember sm) {
    this(CircApplet.parent, title, modal,dbConn,lending,nm,sm);
  }

  void jbInit() throws Exception {
    dsPlaces = dbConn.getPlaces();
    dmPlaces = new JDBTableModel(dsPlaces);
    tblPlaces = new JTable(dmPlaces);
/**/
    tblPlaces.setModel(dmPlaces);
    TableColumnModel tcm = tblPlaces.getColumnModel();
    int i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);

      if(tc.getHeaderValue().toString().equals("ID"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL1"));
      else if(tc.getHeaderValue().toString().equals("NAME"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL2"));

      i++;
    }
    tblPlaces.setColumnModel(tcm);
/**/

    sp1 = new JScrollPane(tblPlaces);

// dodata inicijalizacija 16.02.00.
    dmSelected = dmPlaces;
    tblSelected = tblPlaces;

    dsLendType = dbConn.getLendType();
    dmLendType = new JDBTableModel(dsLendType);
    tblLendType = new JTable(dmLendType);
/**/
    tblLendType.setModel(dmLendType);
    tcm = tblLendType.getColumnModel();
    i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);

      if(tc.getHeaderValue().toString().equals("ID"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL3"));
      else if(tc.getHeaderValue().toString().equals("NAME"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL4"));
      else if(tc.getHeaderValue().toString().equals("TITLES_NO"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL5"));
      else if(tc.getHeaderValue().toString().equals("PERIOD"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL6"));

      i++;
    }
    tblLendType.setColumnModel(tcm);
/**/
    sp2 = new JScrollPane(tblLendType);
    
    
    dsBranch = dbConn.getBranch();
    dmBranch = new JDBTableModel(dsBranch);
    tblBranch = new JTable(dmBranch);
/**/
    tblBranch.setModel(dmBranch);
    tcm = tblBranch.getColumnModel();
    i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);

      if(tc.getHeaderValue().toString().equals("ID"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL7"));
      else if(tc.getHeaderValue().toString().equals("NAME"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL8"));
      else if(tc.getHeaderValue().toString().equals("LAST_USER_ID"))
        tc.setHeaderValue(Messages.get("PLACES_TCHEADER_VAL9"));
      
      i++;
    }
    tblBranch.setColumnModel(tcm);
/**/



    sp3 = new JScrollPane(tblBranch);

    bevelPanel1.setLayout(xYLayout2);
    bevelPanel2.setLayout(xYLayout3);
    bevelPanel3.setLayout(xYLayout4);
    getContentPane().add(panel1);
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(300);
    btnSave.setText(Messages.get("PLACES_BTNSAVE_TEXT"));
    btnExit.setText(Messages.get("PLACES_BTNEXIT_TEXT"));
    btnInsert.setText(Messages.get("PLACES_BTNINSERT_TEXT"));
    btnDelete.setText(Messages.get("PLACES_BTNDELETE_TEXT"));
    panel1.setLayout(xYLayout1);
    tabsetPanel1.addTab(Messages.get("PLACES_TABSETPANEL1_TAB1"),bevelPanel1);
    bevelPanel1.add(sp1, new XYConstraints(31, 19, 401, 164));
    tabsetPanel1.addTab(Messages.get("PLACES_TABSETPANEL1_TAB2"),bevelPanel2);
    bevelPanel2.add(sp2, new XYConstraints(38, 19, 384, 166));
    tabsetPanel1.addTab(Messages.get("PLACES_TABSETPANEL1_TAB3"),bevelPanel3);
    bevelPanel3.add(sp3, new XYConstraints(38, 19, 384, 166));
//    tabsetPanel1.setSelectedIndex(0);
    panel1.add(tabsetPanel1, new XYConstraints(10, 10, 480, 239));
    panel1.add(btnSave, new XYConstraints(60, 260, 80, 25));
    panel1.add(btnExit, new XYConstraints(361, 260, 80, 25));
    panel1.add(btnInsert, new XYConstraints(161, 260, 80, 25));
    panel1.add(btnDelete, new XYConstraints(262, 260, 80, 24));

    tabsetPanel1.addChangeListener(new javax.swing.event.ChangeListener() {
     public void stateChanged(javax.swing.event.ChangeEvent e) {
      tabsetPanel1_stateChanged(e);
     }
    });

    btnSave.setText(Messages.get("PLACES_BTNSAVE1_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnSave_actionPerformed(e);
     }
    });

    btnDelete.setText(Messages.get("PLACES_BTNDELETE1_TEXT"));
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

    btnExit.setText(Messages.get("PLACES_BTNEXIT1_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnExit_actionPerformed(e);
     }
    });

  }

  void btnInsert_actionPerformed(ActionEvent e) {
    dmSelected.expand();
  }

  void btnSave_actionPerformed(ActionEvent e) {
    dmSelected.saveData();
    lending.reloadCMB(); // dodato 28.02.00.
    newmember.reloadCMB();
    singlemember.reloadCMB();
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    dmSelected.deleteRow(tblSelected.getSelectedRow());
    lending.reloadCMB(); // dodato 28.02.00.
    newmember.reloadCMB();
    singlemember.reloadCMB();
  }

  void btnExit_actionPerformed(ActionEvent e) {
    Object[] options = {Messages.get("PLACES_EXITOPTIONS_STR1"),
                        Messages.get("PLACES_EXITOPTIONS_STR2")};
    if(chPlaces || chLendType || chBranch) {
      int opt = JOptionPane.showOptionDialog(null,Messages.get("PLACES_OPTIONPANE_MSG1"),Messages.get("PLACES_OPTIONPANE_MSG2"),
                                   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                   null, options, options[0]);
      if(opt == 0){
        if(chPlaces){
//          tmPlaces.saveToDB();
          chPlaces = true;
        }
        if(chLendType){
//          tmLendType.saveToDB();
          chLendType = true;
        }
        if(chBranch){
//          tmBranch.saveToDB();
          chBranch = true;
        }
      }
    }
    this.setVisible(false);
  }

 void tabsetPanel1_stateChanged(javax.swing.event.ChangeEvent e) {
   switch(tabsetPanel1.getSelectedIndex()){
     case 0:
       dmSelected = dmPlaces;
       tblSelected = tblPlaces;
     break;
     case 1:
       dmSelected = dmLendType;
       tblSelected = tblLendType;
     break;
     case 2:
        dmSelected = dmBranch;
        tblSelected = tblBranch;
      break;
   }
 }

}

