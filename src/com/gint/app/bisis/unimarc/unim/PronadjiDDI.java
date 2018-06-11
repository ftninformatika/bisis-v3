
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

public class PronadjiDDI extends JDialog {
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();

  DBComboModel dbcm1;

  DDindikatora di;
  String fld="";
  String drind="";
  Vector drindId=new Vector();
  Vector drindName=new Vector();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JButton jButton2 = new JButton();
  JButton jButton1 = new JButton();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public PronadjiDDI(DDindikatora di) {
    try  {
      this.di=di;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel2.setText(Messages.get("PRONADJIDDI_LABEL_SECONDINDICATOR"));
    jLabel1.setText(Messages.get("PRONADJIDDI_LABEL_FIELD"));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIDDI_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel2);
    jPanel2.setLayout(xYLayout1);
    jPanel1.setLayout(xYLayout2);
    jPanel1.setMinimumSize(new Dimension(520, 130));
    jPanel1.setPreferredSize(new Dimension(520, 130));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.setMinimumSize(new Dimension(80, 21));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("PRONADJIDDI_BUTTON_OK"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.setMinimumSize(new Dimension(80, 21));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("PRONADJIDDI_BUTTON_CANCEL"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(330);
    jLabel3.setForeground(new java.awt.Color(50, 54, 120));
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jPanel2.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel2.add(jButton1, new XYConstraints(180, 150, -1, -1));
    jPanel2.add(jPanel1, new XYConstraints(10, 10, 317, -1));
    jPanel1.add(jLabel2, new XYConstraints(25, 65, -1, -1));
    jPanel1.add(jComboBox1, new XYConstraints(115, 25, -1, -1));
    jPanel1.add(jLabel1, new XYConstraints(25, 25, -1, -1));
    jPanel1.add(jComboBox2, new XYConstraints(115, 65, -1, -1));
    jPanel1.add(jLabel3, new XYConstraints(185, 25, 130, -1));
    jPanel1.add(jLabel4, new XYConstraints(185, 65, 130, -1));
    this.setModal(true);

    DBMDDInd dbmdi=di.getInstance();
    fld=di.getField().trim();
    drindId=dbmdi.getDrindId();
    drindName=dbmdi.getDrindNaziv();
    drind=di.getDInd().trim();

    dbcm1 = new DBComboModel(drindId);

    jComboBox1.addItem(fld);
    jComboBox1.setEnabled(false);
    jComboBox2.setModel(dbcm1);
    jComboBox2.setSelectedItem(drind);
    setNameField();
    setNameDInd();
  }
  void setNameField() {
    jLabel3.setText(di.getFieldName());
  }
  void setNameDInd() {
    jLabel4.setText((String)drindName.elementAt(jComboBox2.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String drind = ((String)jComboBox2.getSelectedItem()).trim();
    di.find(fld,drind);
    dispose();
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    setNameDInd();
  }
}
