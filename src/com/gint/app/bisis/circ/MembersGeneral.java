
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

public class MembersGeneral extends EscapeDialog {
  DBConnection dbConn;
  DataSet dsUserCategs, dsUserTypes, dsEduLvl, dsMmbrType, dsLanguage;
  JDBTableModel dmUserCateg, dmUserTypes, dmEduLvl, dmMmbrType,dmLanguage, selModel = null;
  JTable tblUserTypes, tblUserCateg, tblEduLvl, tblMmbrType,tblLanguage, selTable = null;

  JPanel pMain = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTabbedPane tabsetPanel1 = new JTabbedPane();
  JPanel bevelPanel1 = new JPanel();
  JPanel bevelPanel2 = new JPanel();
  JPanel bevelPanel3 = new JPanel();
  JPanel bevelPanel4 = new JPanel();
  JPanel bevelPanel5 = new JPanel();

  JScrollPane sp1;
  JScrollPane sp2;
  JScrollPane sp3;
  JScrollPane sp4;
  JScrollPane sp5;
  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();
  JButton btnInsert = new JButton();
  JButton btnExit = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  int selectedRow = -1, exSelectedRow = -1;
  NewMember newMember; // dodato 28.02.00.
  SingleMember singleMember; // dodato 28.02.00.
//  NoviClanDial ncDial;
//  ClanarDial cDial;


  public MembersGeneral(Frame frame, String title, boolean modal, DBConnection db,NewMember newMember,SingleMember singleMember) {
    super(frame, title, modal);
    this.newMember = newMember;
    this.singleMember = singleMember;
    dbConn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public MembersGeneral() {
    this(CircApplet.parent, "", false,null,null,null);
  }

  public MembersGeneral(String title, DBConnection dbConn, boolean modal,NewMember newMember, SingleMember singleMember) {
    this(CircApplet.parent, title, modal,dbConn,newMember,singleMember);
  }

  void jbInit() throws Exception {

    dsUserTypes = dbConn.getUserType();
    dsUserCategs = dbConn.getCategs();
    dsEduLvl = dbConn.getEduLvl();
    dsMmbrType = dbConn.getMmbrType();
    dsLanguage = dbConn.getLanguage();

    xYLayout1.setWidth(500);
    xYLayout1.setHeight(300);
    getContentPane().add(pMain);
    pMain.setLayout(xYLayout1);

    dmUserTypes = new JDBTableModel(dsUserTypes);
    tblUserTypes = new JTable(dmUserTypes);
/***/
    tblUserTypes.setModel(dmUserTypes);
    TableColumnModel tcm = tblUserTypes.getColumnModel();
    int i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);

      if(tc.getHeaderValue().toString().equals("ID"))
        tc.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR_VAL1"));
      else if(tc.getHeaderValue().toString().equals("NAME"))
        tc.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR_VAL2"));
      else if(tc.getHeaderValue().toString().equals("TITLES_NO"))
        tc.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR_VAL3"));
      else if(tc.getHeaderValue().toString().equals("PERIOD"))
        tc.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR_VAL4"));

      i++;
    }
    tblUserTypes.setColumnModel(tcm);

/***/
    tblUserTypes.setPreferredScrollableViewportSize(new Dimension(300, 70));
    sp1 = new JScrollPane(tblUserTypes);

// dodata inicijalizacija 16.02.00.
    selTable = tblUserTypes;
    selModel = dmUserTypes;

    dmUserCateg = new JDBTableModel(dsUserCategs);
    tblUserCateg = new JTable(dmUserCateg);
    ListSelectionModel tmp = tblUserCateg.getSelectionModel();
    tmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblUserCateg.setSelectionModel(tmp);
/**/
    tblUserCateg.setModel(dmUserCateg);
    TableColumnModel tcm1 = tblUserCateg.getColumnModel();
    i = 0;
    while(i < tcm1.getColumnCount()){
      TableColumn tc1 = (TableColumn)tcm1.getColumn(i);

      if(tc1.getHeaderValue().toString().equals("ID"))
        tc1.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR1_VAL1"));
      else if(tc1.getHeaderValue().toString().equals("NAME"))
        tc1.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR1_VAL2"));
      else if(tc1.getHeaderValue().toString().equals("TITLES_NO"))
        tc1.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR1_VAL3"));
      else if(tc1.getHeaderValue().toString().equals("PERIOD"))
        tc1.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR1_VAL4"));

      i++;
    }
    tblUserCateg.setColumnModel(tcm1);
/**/
    sp2 = new JScrollPane(tblUserCateg);

    dmEduLvl = new JDBTableModel(dsEduLvl);
    tblEduLvl = new JTable(dmEduLvl);
/**/
    tblEduLvl.setModel(dmEduLvl);
    TableColumnModel tcm2 = tblEduLvl.getColumnModel();
    i = 0;
    while(i < tcm2.getColumnCount()){
      TableColumn tc2 = (TableColumn)tcm2.getColumn(i);

      if(tc2.getHeaderValue().toString().equals("ID"))
        tc2.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR2_VAL1"));
      else if(tc2.getHeaderValue().toString().equals("NAME"))
        tc2.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR2_VAL2"));

      i++;
    }
    tblEduLvl.setColumnModel(tcm2);
/**/
    sp3 = new JScrollPane(tblEduLvl);

    dmMmbrType = new JDBTableModel(dsMmbrType);
    tblMmbrType = new JTable(dmMmbrType);
/**/
    tblMmbrType.setModel(dmMmbrType);
    TableColumnModel tcm3 = tblMmbrType.getColumnModel();
    i = 0;
    while(i < tcm3.getColumnCount()){
      TableColumn tc3 = (TableColumn)tcm3.getColumn(i);

      if(tc3.getHeaderValue().toString().equals("ID"))
        tc3.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR3_VAL1"));
      else if(tc3.getHeaderValue().toString().equals("NAME"))
        tc3.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR3_VAL2"));
      else if(tc3.getHeaderValue().toString().equals("TITLES_NO"))
        tc3.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR3_VAL3"));
      else if(tc3.getHeaderValue().toString().equals("PERIOD"))
        tc3.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR3_VAL4"));

      i++;
    }
    tblMmbrType.setColumnModel(tcm3);
/**/

    sp4 = new JScrollPane(tblMmbrType);
    
    dmLanguage = new JDBTableModel(dsLanguage);
    tblLanguage = new JTable(dmLanguage);
/**/
    tblLanguage.setModel(dmLanguage);
    TableColumnModel tcm4 = tblLanguage.getColumnModel();
    i = 0;
    while(i < tcm4.getColumnCount()){
      TableColumn tc4 = (TableColumn)tcm4.getColumn(i);

      if(tc4.getHeaderValue().toString().equals("ID"))
        tc4.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR4_VAL1"));
      else if(tc4.getHeaderValue().toString().equals("NAME"))
        tc4.setHeaderValue(Messages.get("MEMBERSGENERAL_TCHDR4_VAL2"));
      
      i++;
    }
    tblLanguage.setColumnModel(tcm4);
/**/

    sp5 = new JScrollPane(tblLanguage);

    bevelPanel1.setLayout(borderLayout1);
    bevelPanel2.setLayout(borderLayout2);
    bevelPanel3.setLayout(borderLayout3);
    bevelPanel4.setLayout(borderLayout4);
    bevelPanel5.setLayout(borderLayout5);

    bevelPanel1.add(sp1, BorderLayout.CENTER);
    bevelPanel2.add(sp2, BorderLayout.CENTER);
    bevelPanel3.add(sp3, BorderLayout.CENTER);
    bevelPanel4.add(sp4, BorderLayout.CENTER);
    bevelPanel5.add(sp5, BorderLayout.CENTER);

    tabsetPanel1.addTab(Messages.get("MEMBERSGENERAL_TABSETPANEL1_TAB1"),bevelPanel1);
    tabsetPanel1.addTab(Messages.get("MEMBERSGENERAL_TABSETPANEL1_TAB2"),bevelPanel2);
    tabsetPanel1.addTab(Messages.get("MEMBERSGENERAL_TABSETPANEL1_TAB3"),bevelPanel3);
    tabsetPanel1.addTab(Messages.get("MEMBERSGENERAL_TABSETPANEL1_TAB4"),bevelPanel4);
    tabsetPanel1.addTab(Messages.get("MEMBERSGENERAL_TABSETPANEL1_TAB5"),bevelPanel5);

    pMain.add(tabsetPanel1, new XYConstraints(20, 25, 451, 229));
    pMain.add(btnSave, new XYConstraints(38, 264, 80, 25));
    pMain.add(btnDelete, new XYConstraints(270, 264, 80, 25));
    pMain.add(btnInsert, new XYConstraints(154, 264, 80, 25));
    pMain.add(btnExit, new XYConstraints(386, 264, 80, 25));

    tabsetPanel1.addChangeListener(new javax.swing.event.ChangeListener() {
     public void stateChanged(javax.swing.event.ChangeEvent e) {
      tabsetPanel1_stateChanged(e);
     }
    });

    btnSave.setText(Messages.get("MEMBERSGENERAL_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnSave_actionPerformed(e);
     }
    });

    btnDelete.setText(Messages.get("MEMBERSGENERAL_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnDelete_actionPerformed(e);
     }
    });

    btnInsert.setText(Messages.get("MEMBERSGENERAL_BTNINSERT_TEXT"));
    btnInsert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnInsert_actionPerformed(e);
      }
    });

    btnExit.setText(Messages.get("MEMBERSGENERAL_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnExit_actionPerformed(e);
     }
    });

  }

  void btnInsert_actionPerformed(ActionEvent e) {
    selModel.expand();
  }

 void btnSave_actionPerformed(ActionEvent e) {
   selModel.saveData();
   newMember.reloadCMB(); // dodato 28.02.00.
   singleMember.reloadCMB(); // dodato 28.02.00.
 }

 void btnDelete_actionPerformed(ActionEvent e) {
    selModel.deleteRow(selTable.getSelectedRow());
    newMember.reloadCMB(); // dodato 28.02.00.
    singleMember.reloadCMB(); // dodato 28.02.00.
 }

 void btnExit_actionPerformed(ActionEvent e) {
  this.setVisible(false);
 }

 void tabsetPanel1_stateChanged(javax.swing.event.ChangeEvent e) {
   switch(tabsetPanel1.getSelectedIndex()){
     case 0:
       selTable = tblUserTypes;
       selModel = dmUserTypes;
     break;
     case 1:
       selTable = tblUserCateg;
       selModel = dmUserCateg;
     break;
     case 2:
       selTable = tblEduLvl;
       selModel = dmEduLvl;
     break;
     case 3:
       selTable = tblMmbrType;
       selModel = dmMmbrType;
     break;
     case 4:
        selTable = tblLanguage;
        selModel = dmLanguage;
      break;
   }
 }

}

