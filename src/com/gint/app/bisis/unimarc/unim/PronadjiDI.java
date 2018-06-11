
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

public class PronadjiDI extends JDialog {
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();

  Connection conn;

  DBComboModel dbcm1;
  DBComboModel dbcm2;

  Dindikator dind;
  String fld="";
  String drind="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public PronadjiDI(Connection conn,Dindikator dind) {
    try  {
      this.conn=conn;
      this.dind=dind;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel2.setText(Messages.get("PRONADJIDI_LABEL_SECONDINDICATOR"));
    jLabel1.setText(Messages.get("PRONADJIDI_LABEL_FIELD"));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIDI_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel2);
    jPanel2.setLayout(xYLayout1);
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.setMinimumSize(new Dimension(80, 21));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("PRONADJIDI_BUTTON_CANCEL"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.setMinimumSize(new Dimension(80, 21));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("PRONADJIDI_BUTTON_OK"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel1.setMinimumSize(new Dimension(370, 130));
    jPanel1.setPreferredSize(new Dimension(370, 130));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(380);
    jLabel3.setForeground(new java.awt.Color(50, 54, 120));
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jPanel2.add(jPanel1, new XYConstraints(10, 10, 360, -1));
    jPanel1.add(jLabel1, new XYConstraints(10, 25, -1, -1));
    jPanel1.add(jLabel2, new XYConstraints(10, 65, -1, -1));
    jPanel1.add(jComboBox1, new XYConstraints(115, 25, -1, -1));
    jPanel1.add(jComboBox2, new XYConstraints(115, 65, -1, -1));
    jPanel1.add(jLabel3, new XYConstraints(185, 25, -1, -1));
    jPanel1.add(jLabel4, new XYConstraints(185, 65, -1, -1));
    jPanel2.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel2.add(jButton1, new XYConstraints(180, 150, -1, -1));
    this.setModal(true);

    fld=dind.getField().trim();
    drind=dind.getDrind().trim();
    trans=true;

    dbcm1 = new DBComboModel(conn,"select distinct di.PO_POLJEID,p.PO_NAZIV from drugi_indikator di, polje p"+
                              " where di.PO_POLJEID=p.POLJEID order by di.PO_POLJEID",true);
    dbcm2 = new DBComboModel(conn,"select distinct DRINDID,DI_NAZIV from drugi_indikator"+
                            " where PO_POLJEID='"+fld+"' order by DRINDID",true);

    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(fld);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(drind);
    setNameField();
    setNameDInd();
    trans=false;
  }
  void setNameField() {
    jLabel3.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameDInd() {
    jLabel4.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String drind = ((String)jComboBox2.getSelectedItem()).trim();
    dind.find(fld,drind);
    dispose();
  }
  void jComboBox1_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct DRINDID,DI_NAZIV from  drugi_indikator where PO_POLJEID='"+
  	                fld+"' order by DRINDID",true);
      jComboBox2.setSelectedIndex(0);
      setNameDInd();
      trans=false;
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameDInd();
    }
  }
}
