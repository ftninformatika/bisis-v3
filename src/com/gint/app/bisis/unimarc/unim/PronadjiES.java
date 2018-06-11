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

public class PronadjiES extends JDialog {
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();

  Connection conn;

  DBComboModel dbcm1;
  DBComboModel dbcm2;

  Esifarnik esf;
  String tes="";
  String eSifr="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();

  public PronadjiES(Connection conn,Esifarnik esf) {
    try  {
      this.conn=conn;
      this.esf = esf;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel3.setText(Messages.get("PRONADJIES_LABEL_EXTERNCODER"));
    jLabel2.setText(Messages.get("PRONADJIES_LABEL_EXTERNTYPE"));
    jComboBox2.setMinimumSize(new Dimension(175, 21));
    jComboBox2.setPreferredSize(new Dimension(175, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox1.setMinimumSize(new Dimension(45, 21));
    jComboBox1.setPreferredSize(new Dimension(45, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIES_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jButton1.setText(Messages.get("PRONADJIES_BUTTON_CANCEL"));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText(Messages.get("PRONADJIES_BUTTON_OK"));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(650, 140));
    jPanel4.setPreferredSize(new Dimension(650, 140));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(400);
    jLabel1.setText(Messages.get("PRONADJIES_LABEL_CODER"));
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jLabel5.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jButton2, new XYConstraints(115, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(220, 150, -1, -1));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 388, -1));
    jPanel4.add(jLabel2, new XYConstraints(15, 25, -1, -1));
    jPanel4.add(jLabel3, new XYConstraints(15, 65, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(125, 25, -1, -1));
    jPanel4.add(jComboBox2, new XYConstraints(125, 65, 129, -1));
    jPanel4.add(jLabel1, new XYConstraints(15, 41, -1, -1));
    jPanel4.add(jLabel4, new XYConstraints(185, 25, 200, -1));
    jPanel4.add(jLabel5, new XYConstraints(259, 65, 120, -1));
    this.setModal(true);

    tes=esf.getTESifarnik().trim();
    eSifr=esf.getESifarnik().trim();
    trans=true;

    dbcm1 = new DBComboModel(conn,"select distinct es.TES_TESID,tes.TES_NAZIV from eksterni_sifarnik es,tipekst_sifarnika tes"+
                              " where es.TES_TESID=tes.TESID order by es.TES_TESID",true);
    dbcm2 = new DBComboModel(conn,"select distinct ESID,ES_NAZIV from eksterni_sifarnik"+
                              " where TES_TESID='"+tes+"' order by ESID",true);

    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(tes);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(eSifr);
    setNameTESif();
    setNameESif();
    trans=false;
  }
  void setNameTESif() {
    jLabel4.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameESif() {
    jLabel5.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String tes = ((String)jComboBox1.getSelectedItem()).trim();
    String eSifr = ((String)jComboBox2.getSelectedItem()).trim();
    esf.find(tes,eSifr);
    dispose();
  }
  void jComboBox1_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameTESif();
      trans=true;
      String tes = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct ESID,ES_NAZIV from  eksterni_sifarnik where TES_TESID='"+
  	                tes+"' order by ESID",true);
      jComboBox2.setSelectedIndex(0);
      setNameESif();
      trans=false;
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(!trans)
      setNameESif();
  }
}
