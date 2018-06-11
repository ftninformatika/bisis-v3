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

public class FLibrarian extends JDialog {
  JPanel panel1 = new JPanel();
  Connection conn;
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  DBComboModel dbcm;
  LibrarDlg lid;

  public FLibrarian(Frame frame, String title, boolean modal, Connection conn, LibrarDlg lid) {
    super(frame, title, modal);
    this.conn = conn;
    this.lid = lid;
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
    xYLayout1.setWidth(420);
    jbCancel.setText(Messages.get("FLIBRARIAN_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FLIBRARIAN_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FLIBRARIAN_LABEL_CODE"));
    getContentPane().add(panel1);
    panel1.add(jbCancel, new XYConstraints(230, 160, 90, 25));
    panel1.add(jbOk, new XYConstraints(110, 160, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 40, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(130, 40, 155, 22));
    setResizable(false);

    dbcm = new DBComboModel(conn,"select BIBID from BIBLIOTEKAR order by BIBID ASC","");
    jComboBox1.setModel(dbcm);
    jComboBox1.setSelectedItem(lid.jTextField1.getText());
  }

  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int i = lid.dbmli.find(((String)dbcm.getSelectedItem()).trim());
    if (i != -1) {
      lid.setValues(i);
      lid.row = i;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FLIBRARIAN_OPTION_PANE_NOT_EXISTS"),Messages.get("FLIBRARIAN_OPTION_PANE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
