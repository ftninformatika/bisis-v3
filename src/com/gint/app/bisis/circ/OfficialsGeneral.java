
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
import com.borland.jbcl.layout.*;

public class OfficialsGeneral extends EscapeDialog {
  boolean changed = false;

  DBConnection dbConn;
  DataSet dsOfficials;
  JDBTableModel dmOfficials;
  JTable tblOfficials;// = new JTable();
  JScrollPane sp1;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();
  JButton btnInsert = new JButton();
  JButton btnExit = new JButton();
  JPasswordField pfPass = new JPasswordField();


  public OfficialsGeneral(Frame frame, String title, boolean modal ,DBConnection db) {
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

  public OfficialsGeneral() {
    this(null, "", false,null);
  }

  public OfficialsGeneral(String title,DBConnection dbConn, boolean modal) {
    this(null, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    dsOfficials = new DataSet(dbConn.getConnection(),"OFFICIALS",new String[] {"ID"});
    dmOfficials = new JDBTableModel(dsOfficials);
    xYLayout1.setHeight(300);
    panel1.setPreferredSize(new Dimension(550, 300));
    panel1.setMinimumSize(new Dimension(550, 300));
    xYLayout1.setWidth(550);
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    tblOfficials = new JTable(dmOfficials);
/*    TableColumnModel tcm = tblOfficials.getColumnModel();
    int i = 0;
    while(i < tcm.getColumnCount()){
      TableColumn tc = (TableColumn)tcm.getColumn(i);
      if(tc.getHeaderValue().toString().equals("PASS"))
        tc.setCellEditor(new DefaultCellEditor(pfPass));
         i++;
    }
    tblOfficials.setColumnModel(tcm);
    tblOfficials.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblOfficials.sizeColumnsToFit(1);*/
    sp1 = new JScrollPane(tblOfficials);
    panel1.add(sp1, new XYConstraints(24, 39, 499, 192));
    panel1.add(btnSave, new XYConstraints(66, 264, 80, -1));
    panel1.add(btnInsert, new XYConstraints(176, 264, 80, -1));
    panel1.add(btnDelete, new XYConstraints(287, 264, 80, -1));
    panel1.add(btnExit, new XYConstraints(397, 264, 80, -1));

    btnSave.setText(Messages.get("OFFICIALSGENERAL_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnSave_actionPerformed(e);
     }
    });

    btnDelete.setText(Messages.get("OFFICIALSGENERAL_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnDelete_actionPerformed(e);
     }
    });

    btnInsert.setText(Messages.get("OFFICIALSGENERAL_BTNINSERT_TEXT"));
    btnInsert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnInsert_actionPerformed(e);
      }
    });

    btnExit.setText(Messages.get("OFFICIALSGENERAL_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(ActionEvent e) {
      btnExit_actionPerformed(e);
     }
    });

  }

  void btnInsert_actionPerformed(ActionEvent e) {
    dmOfficials.expand();
  }

  void btnSave_actionPerformed(ActionEvent e) {
    dmOfficials.saveData();
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    dmOfficials.deleteRow(tblOfficials.getSelectedRow());
  }

  void btnExit_actionPerformed(ActionEvent e) {
    Object[] options = {Messages.get("OFFICIALSGENERAL_EXITOPTIONS_STR1"),
                        Messages.get("OFFICIALSGENERAL_EXITOPTIONS_STR2")};
    if(changed) {
      int opt = JOptionPane.showOptionDialog(null, Messages.get("OFFICIALSGENERAL_OPTIONPANE_MSG1"),Messages.get("OFFICIALSGENERAL_OPTIONPANE_MSG2"),
                                   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                   null, options, options[0]);
      System.out.println("<OfficialsGeneral> Option:"+opt);
      if(opt == 0){
//        tmOfficials.saveToDB();
        changed = true;
      }
    }
    this.setVisible(false);
  }

}

