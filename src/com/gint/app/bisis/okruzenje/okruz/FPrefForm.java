
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
import java.sql.*;
import java.awt.event.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class FPrefForm extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  Connection conn;
  PrefFormDlg pfd;
  DBComboModel dbcm;
  JLabel jLabel2 = new JLabel();

  public FPrefForm(Frame frame, String title, boolean modal, Connection conn, PrefFormDlg pfd) {
    super(frame, title, modal);
    this.conn = conn;
    this.pfd = pfd;
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
    jbCancel.setText(Messages.get("FPREFFORM_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FPREFFORM_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FPREFFORM_LABEL_FORMAT_CODE"));
    getContentPane().add(panel1);
    panel1.add(jbCancel, new XYConstraints(190, 160, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 160, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 40, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(115, 40, 100, 22));
    panel1.add(jLabel2, new XYConstraints(230, 40, 117, -1));
    setResizable(false);

    dbcm = new DBComboModel(conn, "select distinct fpbpr.FOR_FORMATID, f.FOR_OPIS from FORMATF_POLJEBPR fpbpr, FORMATF f where f.FORMATID=fpbpr.FOR_FORMATID order by fpbpr.FOR_FORMATID", "",true);
    jComboBox1.setModel(dbcm);
    jComboBox1.setSelectedItem(pfd.jComboBox1.getSelectedItem());
    setNameForm();
  }

  void setNameForm() {
    jLabel2.setText((String)dbcm.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int i = pfd.dbmpf.find(((String)dbcm.getSelectedItem()),0);
    if (i != -1) {
      pfd.trans = true;
      pfd.setValues(i);
      pfd.row = i;
      pfd.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FPREFFORM_OPTION_PANE_PREFIX_NOT_EXIST"),Messages.get("FPREFFORM_OPTION_PANE_PREFIX_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void jComboBox1_actionPerformed(ActionEvent e) {
    setNameForm();
  }
}