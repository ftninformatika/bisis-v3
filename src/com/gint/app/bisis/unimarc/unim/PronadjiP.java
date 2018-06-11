
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class PronadjiP extends JDialog {
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();

  Connection conn;

  DBComboModel dbcm1;

  Polje polje;
  String fld="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel2 = new JLabel();

  public PronadjiP(Connection conn,Polje polje) {
    try  {
      this.conn=conn;
      this.polje=polje;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setText(Messages.get("PRONADJIP_LABEL_FIELD"));
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIP_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(430, 80));
    jPanel4.setPreferredSize(new Dimension(430, 80));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("PRONADJIP_BUTTON_CANCEL"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("PRONADJIP_BUTTON_OK"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(333);
    jLabel2.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(180, 150, -1, -1));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 317, 131));
    jPanel4.add(jLabel1, new XYConstraints(25, 25, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(75, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(165, 25, 150, -1));
    this.setModal(true);

    fld=polje.getField().trim();
    trans=true;

    dbcm1 = new DBComboModel(conn,"select distinct POLJEID,PO_NAZIV from polje order by POLJEID",true);

    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(fld);
    setNameField();
    trans=false;
  }
  void setNameField() {
    jLabel2.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    polje.find(fld);
    dispose();
  }
  void jComboBox1_actionPerformed(ActionEvent e)  {
    if(!trans)
      setNameField();
  }
}
