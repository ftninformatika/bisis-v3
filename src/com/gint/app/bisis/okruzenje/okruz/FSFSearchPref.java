
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.okruzenje.okruz;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.sql.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class FSFSearchPref extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  Connection conn;
  SFSearchPrefDlg sfspd;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  boolean trans;
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public FSFSearchPref(Frame frame, String title, boolean modal,Connection conn, SFSearchPrefDlg sfspd) {
    super(frame, title, modal);
    this.conn = conn;
    this.sfspd = sfspd;
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
    xYLayout1.setWidth(400);
    jbCancel.setText(Messages.get("FSFSEARCHPREF_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FSFSEARCHPREF_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FSFSEARCHPREF_LABEL_PREFIX_CODE"));
    jLabel2.setText(Messages.get("FSFSEARCHPREF_LABEL_FIELD_CODE"));
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
    panel1.add(jbCancel, new XYConstraints(210, 160, 90, 25));
    panel1.add(jbOk, new XYConstraints(90, 160, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(110, 20, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(110, 70, 80, 22));
    panel1.add(jLabel3, new XYConstraints(205, 70, 196, -1));
    panel1.add(jLabel4, new XYConstraints(205, 20, 196, -1));
    setResizable(false);
    trans = true;

    dbcm1 = new DBComboModel(conn, "select distinct pm.PREF_NAME,pbpr.PBPR_NAZIV from PREFIX_MAP pm, POLJEBPR pbpr"+
    		              " where pbpr.POLJEBPRID=pm.PREF_NAME order by pm.PREF_NAME", "",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(sfspd.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn, "select distinct pm.SUBFIELD_ID, p.PO_NAZIV from PREFIX_MAP pm, POLJE p"+
                              " where p.poljeid=substr(pm.SUBFIELD_ID,1,3) and pm.PREF_NAME like "+"'"+jComboBox1.getSelectedItem()+"%'"+" order by pm.SUBFIELD_ID");
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sfspd.jComboBox2.getSelectedItem());
    trans = false;
    setNamePref();
    setNameSF();
  }
  void setNamePref() {
    jLabel4.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameSF() {
    jLabel3.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    String pref = ((String)dbcm1.getSelectedItem()).trim();
    String field = ((String)dbcm2.getSelectedItem()).trim();
    int lengths[] = {pref.length(),field.length(),0};
    int i = sfspd.dbmsfsp.find(pref+"|"+field,lengths,1);
    if (i != -1) {
      sfspd.setValues(i);
      sfspd.row = i;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FSFSEARCHPREF_OPTION_PANE_PREFIX_NOT_EXIST"),Messages.get("FSFSEARCHPREF_OPTION_PANE_PREFIX_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNamePref();
      trans=true;
      int sub=3;
      dbcm2.reloadItems("select distinct pm.SUBFIELD_ID, p.PO_NAZIV from PREFIX_MAP pm, POLJE p",
     			" where p.POLJEID=substr(pm.SUBFIELD_ID,1,3) and pm.PREF_NAME like "+"'"+jComboBox1.getSelectedItem()+"%'"+" order by pm.SUBFIELD_ID",sub);
      jComboBox2.setSelectedIndex(0);
      setNameSF();
      trans=false;
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameSF();
    }
  }
}

