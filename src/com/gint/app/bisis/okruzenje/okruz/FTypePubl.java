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

public class FTypePubl extends JDialog {
  JPanel panel1 = new JPanel();
  Connection conn;
  TypePublDlg tpd;
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jlTypePubl = new JLabel();
  JComboBox jcbTypePubl = new JComboBox();
  DBComboModel dbcm;
  JLabel jLabel1 = new JLabel();

  public FTypePubl(Frame frame, String title, boolean modal,Connection conn,TypePublDlg tpd) {
    super(frame, title, modal);
    this.conn=conn;
    this.tpd=tpd;
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
    jbCancel.setText(Messages.get("FTYPEPUBL_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FTYPEPUBL_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jcbTypePubl.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jcbTypePubl_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(230);
    xYLayout1.setWidth(350);
    jlTypePubl.setText(Messages.get("FTYPEPUBL_LABEL_PUBLICATION_TYPE"));
    getContentPane().add(panel1);
    panel1.add(jbCancel, new XYConstraints(190, 180, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 180, 90, 25));
    panel1.add(jlTypePubl, new XYConstraints(20, 40, -1, -1));
    panel1.add(jcbTypePubl, new XYConstraints(120, 40, 70, 22));
    panel1.add(jLabel1, new XYConstraints(205, 40, 146, -1));
    setResizable(false);

    dbcm = new DBComboModel(conn,"select TIPID,TIP_NAZIV from TIP_PUBLIKACIJE order by TIPID ASC","",true);
    jcbTypePubl.setModel(dbcm);
    jcbTypePubl.setSelectedItem(tpd.jtfTypePubl.getText());
    setName();
  }
  void setName() {
    jLabel1.setText((String)dbcm.getNameAt(jcbTypePubl.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int i = tpd.dbmtp.findKey(((String)dbcm.getSelectedItem()).trim());
    if (i != -1) {
      tpd.setValues(i);
      tpd.row = i;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FTYPEPUBL_OPTION_PANE_PUBLICATION_TYPE_NOT_EXIST"),Messages.get("FTYPEPUBL_OPTION_PANE_PUBLICATION_TYPE_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jcbTypePubl_actionPerformed(ActionEvent e) {
    setName();
  }
}
