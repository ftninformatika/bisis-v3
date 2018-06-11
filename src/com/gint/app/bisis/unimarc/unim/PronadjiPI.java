
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class PronadjiPI extends JDialog {
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();

  Connection conn;

  DBComboModel dbcm1;
  DBComboModel dbcm2;

  Pindikator pind;
  String fld="";
  String prind="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public PronadjiPI(Connection conn,Pindikator pind) {
    try  {
      this.conn=conn;
      this.pind=pind;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jButton2.setText(Messages.get("PRONADJIPI_BUTTON_OK"));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton1.setText(Messages.get("PRONADJIPI_BUTTON_CANCEL"));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIPI_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("PRONADJIPI_LABEL_FIELD"));
    jLabel2.setText(Messages.get("PRONADJIPI_LABEL_FIRSTINDICATOR"));
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(300, 130));
    jPanel4.setPreferredSize(new Dimension(300, 130));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(330);
    jLabel3.setForeground(new java.awt.Color(50, 54, 120));
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 317, -1));
    jPanel4.add(jLabel1, new XYConstraints(15, 25, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(105, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(15, 65, -1, -1));
    jPanel4.add(jComboBox2, new XYConstraints(105, 65, -1, -1));
    jPanel4.add(jLabel3, new XYConstraints(175, 25, 140, -1));
    jPanel4.add(jLabel4, new XYConstraints(175, 65, 140, -1));
    jPanel1.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(180, 150, -1, -1));
    this.setModal(true);

    fld=pind.getField().trim();
    prind=pind.getPrind().trim();
    trans=true;

    dbcm1 = new DBComboModel(conn,"select distinct pid.PO_POLJEID,p.PO_NAZIV from prvi_indikator pid, polje p"+
                              " where pid.PO_POLJEID=p.POLJEID order by pid.PO_POLJEID",true);
    dbcm2 = new DBComboModel(conn,"select distinct PRINDID,PI_NAZIV from prvi_indikator"+
                              " where PO_POLJEID='"+fld+"' order by PRINDID",true);

    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(fld);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(prind);
    setNameField();
    setNamePInd();
    trans=false;
  }
  void setNameField() {
    jLabel3.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNamePInd() {
    jLabel4.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String prind = ((String)jComboBox2.getSelectedItem()).trim();
    pind.find(fld,prind);
    dispose();
  }
  void jComboBox1_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct PRINDID,PI_NAZIV from  prvi_indikator where PO_POLJEID='"+
  	                fld+"' order by PRINDID",true);
      jComboBox2.setSelectedIndex(0);
      setNamePInd();
      trans=false;
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNamePInd();
    }
  }
}
