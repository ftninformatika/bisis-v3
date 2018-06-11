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

public class FSearchPref extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  Connection conn;
  SearchPrefDlg spd;
  DBComboModel dbcm;
  JLabel jLabel2 = new JLabel();

  public FSearchPref(Frame frame, String title, boolean modal, Connection conn, SearchPrefDlg spd) {
    super(frame, title, modal);
    this.conn = conn;
    this.spd = spd;
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
    jbCancel.setText(Messages.get("FSEARCHPREF_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FSEARCHPREF_BUTTON_OK"));
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
    jLabel1.setText(Messages.get("FSEARCHPREF_LABEL_PREFIX_CODE"));
    getContentPane().add(panel1);
    panel1.add(jbCancel, new XYConstraints(190, 160, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 160, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 40, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(115, 40, 60, 22));
    panel1.add(jLabel2, new XYConstraints(190, 40, 160, -1));
    setResizable(false);

    dbcm = new DBComboModel(conn,"select POLJEBPRID,PBPR_NAZIV from POLJEBPR order by POLJEBPRID ASC","",true);
    jComboBox1.setModel(dbcm);
    jComboBox1.setSelectedItem(spd.jTextField1.getText());
    setNamePref();
  }

  void setNamePref() {
    jLabel2.setText((String)dbcm.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int i = spd.dbmsp.find(((String)dbcm.getSelectedItem()).trim());
    if (i != -1) {
      spd.setValues(i);
      spd.row = i;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FSEARCHPREF_OPTION_PANE_PREFIX_NOT_EXIST"),Messages.get("FSEARCHPREF_OPTION_PANE_PREFIX_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void jComboBox1_actionPerformed(ActionEvent e) {
    setNamePref();
  }
}
