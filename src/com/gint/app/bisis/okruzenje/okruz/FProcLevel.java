//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.okruzenje.okruz;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class FProcLevel extends JDialog {
  JPanel panel1 = new JPanel();
  Connection conn;
  ProcLevelDlg pld;
  XYLayout xYLayout1 = new XYLayout();
  JComboBox jComboBox1 = new JComboBox();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox2 = new JComboBox();
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  boolean trans = true;
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public FProcLevel(Frame frame, String title, boolean modal, Connection conn, ProcLevelDlg pld) {
    super(frame, title, modal);
    this.conn = conn;
    this.pld = pld;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(210);
    xYLayout1.setWidth(350);
    jbCancel.setText(Messages.get("FPROCLEVEL_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FPROCLEVEL_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FPROCLEVEL_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("FPROCLEVEL_LABEL_PROCESS_LEVEL"));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jComboBox1, new XYConstraints(120, 20, 70, 22));
    panel1.add(jbCancel, new XYConstraints(190, 160, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 160, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jComboBox2, new XYConstraints(120, 70, 70, 22));
    panel1.add(jLabel3, new XYConstraints(205, 20, 146, -1));
    panel1.add(jLabel4, new XYConstraints(205, 70, 145, -1));
    setResizable(false);

    trans = true;

    dbcm1 = new DBComboModel(conn,"select distinct nobr.TP_TIPID,tp.TIP_NAZIV from NIVO_OBRADE nobr, TIP_PUBLIKACIJE tp"+
    													" where tp.TIPID=nobr.TP_TIPID order by nobr.TP_TIPID ASC","",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(pld.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn,"select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE"," where TP_TIPID="+"'"+(String)pld.jComboBox1.getSelectedItem()+"' order by NOBRID ASC",true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(pld.jTextField1.getText());

    trans = false;
    setNameTP();
    setNameNO();
  }
  void setNameTP() {
    jLabel3.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel4.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int i = pld.dbmpl.find(((String)dbcm1.getSelectedItem()).trim()+((String)dbcm2.getSelectedItem()).trim(),2);
    if (i != -1) {
      pld.trans = true;
      pld.setValues(i);
      pld.row = i;
      pld.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FPROCLEVEL_OPTION_PANE_PREFIX_NOT_EXIST"),Messages.get("FPROCLEVEL_OPTION_PANE_PREFIX_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if (!trans) {
      setNameTP();
      trans=true;
      dbcm2.reloadItems("select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE where TP_TIPID="+"'"+(String)jComboBox1.getSelectedItem()+"' order by NOBRID ASC",true);
      jComboBox2.setSelectedIndex(0);
      trans=false;
      setNameNO();
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans)
      setNameNO();
  }
}

