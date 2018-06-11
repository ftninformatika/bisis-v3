//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;

public class MembersFee extends EscapeDialog {
  int selPanel=0;
  boolean inSave = false;
  DBConnection dbConn;
  DataSet dsMmbrType, dsUserCateg, dsMembership;
  DefaultListModel lmMmbrType, lmUserCateg;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lMmbrType = new JLabel();
  JLabel lUsrCateg = new JLabel();
  JLabel lDate = new JLabel();
  JLabel lAmount = new JLabel();
  JLabel lForeign = new JLabel();
  JDBTextField tfDate = new JDBTextField();
  JDBTextField tfAmount = new JDBTextField();
  JDBTextField tfForeign = new JDBTextField();
  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();  
  JButton btnExit = new JButton();
  JTabbedPane tabsetPanel1 = new JTabbedPane();
  JPanel bevelPanel1 = new JPanel();
  JPanel bevelPanel2 = new JPanel();
  JList lstMmbrType = new JList();
  JList lstUsrCateg = new JList();
  JTable gridControl1;
  BorderLayout borderLayout2 = new BorderLayout();
  JDBComboBoxModel cmUserCtg, cmMmbrType;
  HashMap userCtgId, userCtgName;
  HashMap mmbrTypeId, mmbrTypeName;
  JTable tblMembersFee;
  ListSelectionModel lsmMembersFee;
  JDBTableModel tmMembersFee;

  java.util.Date currentDate = new java.util.Date();
  String currentDateStr = new String();
  XYLayout xYLayout2 = new XYLayout();
  JScrollPane sp1;
  JScrollPane sp2;
  JScrollPane sp3;

  public MembersFee(Frame frame, String title, boolean modal, DBConnection db) {
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

  public MembersFee() {
    this(CircApplet.parent, "", false,null);
  }

  public MembersFee(String title, DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    dsMmbrType = dbConn.getMmbrType();
    dsUserCateg = dbConn.getCategs();
    dsMembership = dbConn.getMembership();

    lMmbrType.setText(Messages.get("MEMBERSFEE_LMMBRTYPE_TEXT"));
    lUsrCateg.setText(Messages.get("MEMBERSFEE_LUSERCATEG_TEXT"));
    lDate.setText(Messages.get("MEMBERSFEE_LDATE_TEXT"));
    lAmount.setText(Messages.get("MEMBERSFEE_LAMOUNT_TEXT"));
    lForeign.setText(Messages.get("MEMBERSFEE_LFOREIGN_TEXT"));
    tfDate.setDataSet(dsMembership);
    tfDate.setColumn("MDATE");
    tfDate.setChangeble(false);
    tfAmount.setDataSet(dsMembership);
    tfAmount.setColumn("COST");
    tfAmount.setChangeble(false);
    tfForeign.setDataSet(dsMembership);
    tfForeign.setColumn("HARD_CURR");
    tfForeign.setChangeble(false);

    btnSave.setText(Messages.get("MEMBERSFEE_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnSave_actionPerformed(e);
     }
    });

    btnDelete.setText(Messages.get("MEMBERSFEE_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnDelete_actionPerformed(e);
     }
    });


    btnExit.setText(Messages.get("MEMBERSFEE_BTNEXIT_TEXT"));

    reloadItems();

    sp1 = new JScrollPane(lstUsrCateg);
    sp2 = new JScrollPane(lstMmbrType);
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(300);
    getContentPane().add(panel1);
    panel1.setLayout(xYLayout1);

    panel1.add(tabsetPanel1, new XYConstraints(10, 8, 482, 254));

    bevelPanel1.setLayout(xYLayout2);
    bevelPanel1.add(tfDate, new XYConstraints(360, 42, 105, -1));
    bevelPanel1.add(tfAmount, new XYConstraints(360, 83, 105, -1));
    bevelPanel1.add(tfForeign, new XYConstraints(365, 123, 100, -1));
    bevelPanel1.add(lMmbrType, new XYConstraints(20, 15, -1, -1));

    bevelPanel1.add(lUsrCateg, new XYConstraints(20, 120, -1, -1));
    bevelPanel1.add(lDate, new XYConstraints(281, 46, -1, -1));
    bevelPanel1.add(lAmount, new XYConstraints(281, 85, -1, -1));
    bevelPanel1.add(lForeign, new XYConstraints(281, 125, -1, -1));
    bevelPanel1.add(sp1, new XYConstraints(20, 140, 200, 70));
    bevelPanel1.add(sp2, new XYConstraints(20, 35, 200, 70));

    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.GERMANY);
    df.setTimeZone(TimeZone.getDefault());
    currentDate.setTime(System.currentTimeMillis());
    currentDateStr = df.format(currentDate);
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    tfDate.setText(currentDateStr);


// odavde

    tblMembersFee = new JTable();
    lsmMembersFee = tblMembersFee.getSelectionModel();
    lsmMembersFee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblMembersFee.setSelectionModel(lsmMembersFee);
    sp3= new JScrollPane(tblMembersFee);

    bevelPanel2.setLayout(borderLayout2);
    bevelPanel2.add(sp3, BorderLayout.CENTER);
    panel1.add(btnSave, new XYConstraints(71, 268, 80, 25));
    panel1.add(btnExit, new XYConstraints(338, 268, 80, 25));
    panel1.add(btnDelete, new XYConstraints(195, 268, 80, 25));
// dovde

    tabsetPanel1.addTab(Messages.get("MEMBERSFEE_TABSETPANEL1_TAB1"),bevelPanel1);
    tabsetPanel1.addTab(Messages.get("MEMBERSFEE_TABSETPANEL1_TAB2"),bevelPanel2);

    tabsetPanel1.addChangeListener(new javax.swing.event.ChangeListener() {
     public void stateChanged(javax.swing.event.ChangeEvent e) {
      tabsetPanel1_stateChanged(e);
     }
    });
  }

  void btnSave_actionPerformed(ActionEvent e) {
    if (selPanel==0) {
    int usrCategSel [];
    int mmbrTypeSel [];
    inSave = true;
    usrCategSel = lstUsrCateg.getSelectedIndices();
    mmbrTypeSel = lstMmbrType.getSelectedIndices();
    if( mmbrTypeSel.length == 0){
      JOptionPane.showMessageDialog(null,Messages.get("MEMBERSFEE_OPTIONPANE_MSG1"),Messages.get("MEMBERSFEE_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
    }
    else if(usrCategSel.length == 0){
      JOptionPane.showMessageDialog(null,Messages.get("MEMBERSFEE_OPTIONPANE_MSG3"),Messages.get("MEMBERSFEE_OPTIONPANE_MSG4"),JOptionPane.INFORMATION_MESSAGE);
    }
    else{
      for( int j = 0; j<mmbrTypeSel.length; j++){
        for(int i = 0; i<usrCategSel.length; i++){
          dsMembership.emptyRow();
          dsUserCateg.setCurrentRow(new String[]{"NAME"},new String[] {(String)lmUserCateg.getElementAt(usrCategSel[i])});
          dsMmbrType.setCurrentRow(new String[]{"NAME"},new String[] {(String)lmMmbrType.getElementAt(mmbrTypeSel[j])});
          dsMembership.getRow().setData(dsUserCateg.getRow().getData("ID"),"USER_CTGR");
          dsMembership.getRow().setData(dsMmbrType.getRow().getData("ID"),"MMBR_TYPE");
          tfDate.post();
          tfAmount.post();
          tfForeign.post();
          dsMembership.saveRow();
        }

      }
   }
   emptyLists();
   System.out.println("<MembersFee> End");
   inSave = false;
   }
  }


  void emptyLists(){
    lstUsrCateg.clearSelection();
    lstMmbrType.clearSelection();
    tfAmount.setText("");
    tfForeign.setText("");
  }

//  void btnInsert_actionPerformed(ActionEvent e) {
//
//  }

  public void setVisible(boolean vis) { // 20.07.2000.
    if (vis == true) {
      if (selPanel == 0) {
        reloadItems();
        tfDate.setText(currentDateStr);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
      }
      else if (selPanel == 1) {
        setPosition();
        btnSave.setEnabled(false);
        btnDelete.setEnabled(true);
      }
    }
    super.setVisible(vis);
  }

  void reloadItems(){
    lmUserCateg = new DefaultListModel();
    lmMmbrType = new DefaultListModel();
    try{
      Statement stmt = dbConn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery("select name from MMBR_TYPES");
      while(rset.next()){
        lmMmbrType.addElement(rset.getString(1));
      }
      rset.close();
      rset = stmt.executeQuery("select name from USER_CATEGS");
      while(rset.next()){
        lmUserCateg.addElement(rset.getString(1));
      }
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    lstMmbrType.setModel(lmMmbrType);
    lstUsrCateg.setModel(lmUserCateg);
  }



  private void setPosition(){
    userCtgId = new HashMap();
    userCtgName = new HashMap();
    mmbrTypeId = new HashMap();
    mmbrTypeName = new HashMap();
    try{
      Statement stmt = dbConn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery("select id, name from MMBR_TYPES");
      while(rset.next()){
        mmbrTypeId.put(new Integer(rset.getInt("ID")),rset.getString("NAME"));
        mmbrTypeName.put(rset.getString("NAME"),new Integer(rset.getInt("ID")));
      }
      rset.close();
      rset = stmt.executeQuery("select id, name from USER_CATEGS");
      while(rset.next()){
        userCtgId.put(new Integer(rset.getInt("ID")),rset.getString("NAME"));
        userCtgName.put(rset.getString("NAME"),new Integer(rset.getInt("ID")));
      }
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();
    }catch(SQLException ex){
      ex.printStackTrace();
    }


    tmMembersFee = new JDBTableModel(dsMembership,"select * from membership order by user_ctgr, mmbr_type, mdate");

    if (tmMembersFee.getRowCount()>0){

      tblMembersFee.setModel(tmMembersFee);

      TableColumnModel tcm = tblMembersFee.getColumnModel();
      int i = 0;
      while(i < tcm.getColumnCount()){
        TableColumn tc = (TableColumn)tcm.getColumn(i);
        if(tc.getHeaderValue().toString().equals("USER_CTGR")) {
          tc.setHeaderValue(Messages.get("MEMBERSFEE_TCHDR_VAL1"));
          i++;
        }
        else if (tc.getHeaderValue().toString().equals("MMBR_TYPE")) {
          tc.setHeaderValue(Messages.get("MEMBERSFEE_TCHDR_VAL2"));
          i++;
        }
        else if (tc.getHeaderValue().toString().equals("MDATE")) {
          tc.setHeaderValue(Messages.get("MEMBERSFEE_TCHDR_VAL3"));
          i++;
        }
        else if (tc.getHeaderValue().toString().equals("COST")) {
          tc.setHeaderValue(Messages.get("MEMBERSFEE_TCHDR_VAL4"));
          i++;
        }
        else if (tc.getHeaderValue().toString().equals("HARD_CURR")) {
          tc.setHeaderValue(Messages.get("MEMBERSFEE_TCHDR_VAL5"));
          i++;
        }
      }

      tblMembersFee.setColumnModel(tcm);
      tblMembersFee.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblMembersFee.sizeColumnsToFit(1);

      tmMembersFee.setEditable(false);

      String sadrzaj=new String();
      int indeks;
      for (i=0;i<tblMembersFee.getRowCount();i++) {
        indeks = (int)Double.parseDouble((String)tblMembersFee.getValueAt(i,0));
        sadrzaj = (String) userCtgId.get(new Integer(indeks));
        tblMembersFee.setValueAt(sadrzaj,i,0);

        indeks = (int)Double.parseDouble((String)tblMembersFee.getValueAt(i,1));
        sadrzaj = (String) mmbrTypeId.get(new Integer(indeks));
        tblMembersFee.setValueAt(sadrzaj,i,1);

        sadrzaj = (String)tblMembersFee.getValueAt(i,4);
        if ((sadrzaj == null) || (sadrzaj.equals("null")))
          tblMembersFee.setValueAt("",i,4);
     }

     tblMembersFee.setRowSelectionInterval(0,0);
    }
  }


  void btnExit_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

 void btnDelete_actionPerformed(ActionEvent e) {
    if (tblMembersFee.getSelectedRow() != -1) {
      String sadrzaj = new String();
      String s = new String();
      Integer indeks;

      sadrzaj= (String)tblMembersFee.getValueAt((int)tblMembersFee.getSelectedRow(),0);
      indeks = (Integer)userCtgName.get(sadrzaj);
      tblMembersFee.setValueAt(indeks.toString(),tblMembersFee.getSelectedRow(),0);

      sadrzaj= (String) tblMembersFee.getValueAt((int)tblMembersFee.getSelectedRow(),1);
      indeks = (Integer)mmbrTypeName.get(sadrzaj);
      tblMembersFee.setValueAt(indeks.toString(),tblMembersFee.getSelectedRow(),1);

      tmMembersFee.deleteRow(tblMembersFee.getSelectedRow());
      setPosition();
    }
 }

 void tabsetPanel1_stateChanged(javax.swing.event.ChangeEvent e) {
   switch(tabsetPanel1.getSelectedIndex()){
     case 0:
       selPanel = 0;
       reloadItems();
       tfDate.setText(currentDateStr);
       btnSave.setEnabled(true);
       btnDelete.setEnabled(false);
     break;
     case 1:
       selPanel = 1;
       setPosition();
       btnSave.setEnabled(false);
       btnDelete.setEnabled(true);
     break;
   }
 }

}

