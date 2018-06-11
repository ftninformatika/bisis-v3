
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
import java.sql.*;
import java.text.SimpleDateFormat;

public class RenewMembership extends EscapeDialog {
  DBConnection dbConn;
  DataSet dsUsers, dsSingle, dsRenew;
  JDBTableModel dmRenew;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTable tblRenew;// = new JTable();
  JScrollPane sp1;
  JLabel lName = new JLabel();
  JLabel lMmbrId = new JLabel();
  JLabel tfName = new JLabel();
  JLabel tfMmbrId = new JLabel();
  JButton btnDelete = new JButton();
  JButton btnInsert = new JButton();
  JButton btnSave = new JButton();
  JButton btnExit = new JButton();
  String mmbrId = "-1";
  JLabel tfDate = new JLabel();
  JLabel jLabel1 = new JLabel();

  public RenewMembership(Frame frame, String title, boolean modal,DBConnection db) {
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

  public RenewMembership() {
    this(CircApplet.parent, "", false,null);
  }

  public RenewMembership(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    dsUsers = dbConn.getUsersModel();
    dsSingle = dbConn.getSingleModel();
    java.util.Vector col = new java.util.Vector();
    col.addElement(Messages.get("RENEWMEMBERSHIP_COL_EL1"));
    col.addElement(Messages.get("RENEWMEMBERSHIP_COL_EL2"));
    col.addElement(Messages.get("RENEWMEMBERSHIP_COL_EL3"));
    dsRenew = new DataSet(dbConn.getConnection(),"SIGNING",new String[] {"SINGLE_ID","BIB_ID","SDATE"});
    tblRenew = new JTable();
    sp1 = new JScrollPane(tblRenew);

    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(300);
    tfName.setForeground(Color.blue);
    tfMmbrId.setForeground(Color.blue);
    btnDelete.setText(Messages.get("RENEWMEMBERSHIP_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });
    btnInsert.setText(Messages.get("RENEWMEMBERSHIP_BTNINSERT_TEXT"));
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
    tfDate.setForeground(Color.blue);
    jLabel1.setText(Messages.get("RENEWMEMBERSHIP_JLABEL1_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    panel1.setPreferredSize(new Dimension(420, 300));
    panel1.setMinimumSize(new Dimension(420, 300));
    xYLayout1.setWidth(420);
    getContentPane().add(panel1);
    lName.setText(Messages.get("RENEWMEMBERSHIP_LNAME_TEXT"));
    lMmbrId.setText(Messages.get("RENEWMEMBERSHIP_LMMBRID_TEXT"));
    panel1.add(sp1, new XYConstraints(23, 96, 364, 132));
    panel1.add(lMmbrId, new XYConstraints(14, 27, 63, -1));
    panel1.add(lName, new XYConstraints(171, 27, 91, -1));
    panel1.add(tfName, new XYConstraints(266, 27, -1, -1));
    panel1.add(tfMmbrId, new XYConstraints(85, 27, -1, -1));
    panel1.add(btnDelete, new XYConstraints(221, 245, 80, 25));
    panel1.add(btnInsert, new XYConstraints(116, 245, 80, 25));
    panel1.add(btnSave, new XYConstraints(12, 245, 80, 25));
    panel1.add(btnExit, new XYConstraints(325, 245, 80, 25));
    panel1.add(tfDate, new XYConstraints(192, 56, -1, -1));
    panel1.add(jLabel1, new XYConstraints(86, 56, -1, -1));
  }

  void btnSave_actionPerformed(ActionEvent e) {
    dmRenew.saveData();
    try{
  		Statement stmt = dbConn.getConnection().createStatement();
  		stmt.executeUpdate("delete from blocked_cards where user_id ='"+tfMmbrId.getText()+"' and reason_id = 1");
  		dbConn.getConnection().commit();
  		stmt.close();
	}catch(SQLException e1){
  	}
    JOptionPane.showMessageDialog(null,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG1"),Messages.get("SINGLEMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
  }

  void btnInsert_actionPerformed(ActionEvent e) {
  	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
  	java.util.Date until = null;
  	try{
  		 until = sdf.parse(tblRenew.getValueAt(tblRenew.getRowCount()-1,3).toString());
  	}catch (Exception e1){
	}
  	if (until != null){
    	java.util.Date now = new java.util.Date(System.currentTimeMillis());
    	if(now.after(until)){
    		dmRenew.expand();
    	    dmRenew.setValueAt(DBConnection.getTodaysDate(),dmRenew.getLastRow(),2);
    	    findCost();
    	}else {
    		String[] options = {Messages.get("RENEWMEMBERSHIP_OPTIONSPANE_MSG1"),Messages.get("RENEWMEMBERSHIP_OPTIONSPANE_MSG2")}; 
    	 	int op = JOptionPane.showOptionDialog(this,Messages.get("RENEWMEMBERSHIP_OPTIONSPANE_MSG3"), Messages.get("RENEWMEMBERSHIP_OPTIONSPANE_MSG4"),JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
    	 	if (op == JOptionPane.YES_OPTION){
    		    dmRenew.expand();
    		    dmRenew.setValueAt(DBConnection.getTodaysDate(),dmRenew.getLastRow(),2);
    		    findCost();
    	 	}
    	}
  	}else {
  		dmRenew.expand();
	    dmRenew.setValueAt(DBConnection.getTodaysDate(),dmRenew.getLastRow(),2);
	    findCost();
  	}
  	
  }

  void findCost () {
    String qry = "select cost from membership where user_ctgr=(select user_ctgr from single where user_id='"+tfMmbrId.getText()+"') and mmbr_type=(select mmbr_type from single where user_id='"+tfMmbrId.getText()+"') order by mdate desc";
    try{
      Statement stmt = dbConn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(qry);
      if(rset.next()){
        dmRenew.setValueAt(rset.getObject(1).toString(),dmRenew.getLastRow(),3);
      }
/*      else {
        dmRenew.setValueAt("",dmRenew.getLastRow(),3);
      } */
      rset.close();
      CoolDate cd = new CoolDate(System.currentTimeMillis());
      qry = "select period from mmbr_types where id = (select mmbr_type from single where user_id='"+tfMmbrId.getText()+"')";
      rset = stmt.executeQuery(qry);
      if(rset.next()){
        int days = rset.getInt(1);
        CoolDate endDate = new CoolDate((cd.plus(days)).getTime());
        dmRenew.setValueAt(DBConnection.formatDate(endDate),dmRenew.getLastRow(),6);
      }
      
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();
    }catch(SQLException e){
//      dmRenew.setValueAt("",dmRenew.getLastRow(),3);
      e.printStackTrace();
    }
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    dmRenew.deleteRow(tblRenew.getSelectedRow());
  }

  void btnExit_actionPerformed(ActionEvent e) {
    setVisible(false);
    this.setVisible(false);
  }

  void setTextField(){
    tfMmbrId.setText(""+mmbrId);
  }

  public void setVisible(boolean vis){
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
//    super.setVisible(vis);             15.02.00.
    setPosition(id);
    super.setVisible(vis);
  }

  private void setPosition(String id){
    dmRenew = new JDBTableModel(dsRenew,"select * from SIGNING where SINGLE_ID ='"+id+"'");
    tblRenew.setModel(dmRenew);
    TableColumnModel tcm = tblRenew.getColumnModel();
    int i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);
      System.out.println("<RenewMemvership> "+tc.getHeaderValue().toString()+" "+tc.getWidth());

      if(tc.getHeaderValue().toString().equals("SDATE"))
        tc.setHeaderValue("DATUM");
      else if(tc.getHeaderValue().toString().equals("COST"))
        tc.setHeaderValue("CENA");
      else if(tc.getHeaderValue().toString().equals("RECEIPT_ID"))
      	tc.setHeaderValue("BR. PRIZNANICE");
      else if(tc.getHeaderValue().toString().equals("UNTIL_DATE"))
      	tc.setHeaderValue("VAZI DO");

      if(tc.getHeaderValue().toString().equals("SINGLE_ID"))
          tcm.removeColumn(tc);
      else if (tc.getHeaderValue().toString().equals("BIB_ID"))
          tcm.removeColumn(tc);
      else if(tc.getHeaderValue().toString().equals("HARD_CURR"))
          tcm.removeColumn(tc);
      else
         i++;
    }
    tblRenew.setColumnModel(tcm);
    tblRenew.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblRenew.sizeColumnsToFit(1);
    dmRenew.setDefaultCol(0,id);
    dmRenew.setDefaultCol(1,dbConn.getOfficial()); 
    dsUsers.setCurrentRow(new String[]{"ID"}, new String[]{id});
    dsSingle.setCurrentRow(new String[]{"USER_ID"}, new String[]{id});
    tfMmbrId.setText(dsUsers.getRow().getData("ID"));
    tfName.setText(dsSingle.getRow().getData("LAST_NAME")+", "+dsSingle.getRow().getData("FIRST_NAME"));
    tfDate.setText(dsUsers.getRow().getData("SIGN_DATE"));
  }
}

