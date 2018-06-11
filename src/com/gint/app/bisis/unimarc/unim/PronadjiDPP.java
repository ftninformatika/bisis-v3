
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

public class PronadjiDPP extends JDialog {
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel1 = new JPanel();

  DBComboModel dbcm1;

  DPotpolja pp;
  String fld="";
  String sf="";
  Vector sfld=new Vector();
  Vector sfieldName=new Vector();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public PronadjiDPP(DPotpolja pp) {
    try  {
      this.pp=pp;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jButton2.setText(Messages.get("PRONADJIDPP_BUTTON_OK"));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton1.setText(Messages.get("PRONADJIDPP_BUTTON_OK"));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIDPP_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jLabel1.setText(Messages.get("PRONADJIDPP_LABEL_FIELD"));
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(500, 130));
    jPanel4.setPreferredSize(new Dimension(500, 130));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jLabel2.setText(Messages.get("PRONADJIDPP_LABEL_SUBFIELD"));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(330);
    jLabel3.setForeground(new java.awt.Color(50, 54, 120));
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(180, 150, -1, -1));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 317, -1));
    jPanel4.add(jLabel1, new XYConstraints(25, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(25, 65, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(85, 25, -1, -1));
    jPanel4.add(jComboBox2, new XYConstraints(85, 65, -1, -1));
    jPanel4.add(jLabel3, new XYConstraints(155, 25, 160, -1));
    jPanel4.add(jLabel4, new XYConstraints(155, 65, 160, -1));
    this.setModal(true);

    DBMDPPolje dbmpp=pp.getInstance();
    fld=pp.getField().trim();
    sfld=dbmpp.getSfieldId();
    sfieldName=dbmpp.getSfieldNaziv();
    sf=pp.getSField().trim();

    dbcm1 = new DBComboModel(sfld);

    jComboBox1.addItem(fld);
    jComboBox1.setEnabled(false);
    jComboBox2.setModel(dbcm1);
    jComboBox2.setSelectedItem(sf);
    setNameField();
    setNameSField();
  }
  void setNameField() {
    jLabel3.setText(pp.getFieldName());
  }
  void setNameSField() {
    jLabel4.setText((String)sfieldName.elementAt(jComboBox2.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String sfld = ((String)jComboBox2.getSelectedItem()).trim();
    pp.find(fld,sfld);
    dispose();
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    setNameSField();
  }
}
