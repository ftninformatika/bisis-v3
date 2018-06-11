
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class PronadjiDPI extends JDialog {
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();

  DBComboModel dbcm1;

  DPindikatora pi;
  String fld="";
  String prind="";
  Vector prindId=new Vector();
  Vector prindName=new Vector();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public PronadjiDPI(DPindikatora pi) {
    try  {
      this.pi=pi;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jButton2.setText(Messages.get("PRONADJIDPI_BUTTON_OK"));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton1.setText(Messages.get("PRONADJIDPI_BUTTON_CANCEL="));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIDPI_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("PRONADJIDPI_LABEL_FIELD"));
    jLabel2.setText(Messages.get("PRONADJIDPI_LABEL_FIRSTINDICATOR"));
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(490, 130));
    jPanel4.setPreferredSize(new Dimension(490, 130));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(330);
    jLabel3.setForeground(new java.awt.Color(50, 54, 120));
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(180, 150, -1, -1));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 317, -1));
    jPanel4.add(jLabel1, new XYConstraints(25, 25, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(105, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(25, 65, -1, -1));
    jPanel4.add(jComboBox2, new XYConstraints(105, 65, -1, -1));
    jPanel4.add(jLabel3, new XYConstraints(175, 25, 140, -1));
    jPanel4.add(jLabel4, new XYConstraints(175, 65, 140, -1));
    this.setModal(true);

    DBMDPInd dbmpi=pi.getInstance();
    fld=pi.getField().trim();
    prindId=dbmpi.getPrindId();
    prindName=dbmpi.getPrindNaziv();
    prind=pi.getPInd().trim();

    dbcm1 = new DBComboModel(prindId);

    jComboBox1.addItem(fld);
    jComboBox1.setEnabled(false);
    jComboBox2.setModel(dbcm1);
    jComboBox2.setSelectedItem(prind);
    setNameField();
    setNamePInd();
  }
  void setNameField() {
    jLabel3.setText(pi.getFieldName());
  }
  void setNamePInd() {
    jLabel4.setText((String)prindName.elementAt(jComboBox2.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String prind = ((String)jComboBox2.getSelectedItem()).trim();
    pi.find(fld,prind);
    dispose();
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    setNamePInd();
  }
}
