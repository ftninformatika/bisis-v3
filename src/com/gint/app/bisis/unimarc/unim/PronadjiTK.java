
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
import java.sql.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class PronadjiTK extends JDialog {
  JLabel jLabel1 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JComboBox jComboBox1 = new JComboBox();
  JPanel jPanel2 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();

  Connection conn;
  DBComboModel dbcm;
  Tipkontrole tk=null;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JLabel jLabel2 = new JLabel();

  public PronadjiTK(Connection conn, Tipkontrole tk) {
    try  {
      this.conn=conn;
      this.tk=tk;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setText(Messages.get("PRONADJITK_LABEL_CONTROLTYPE"));
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJITK_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jPanel4.setLayout(xYLayout2);
    jPanel4.setPreferredSize(new Dimension(400, 63));
    jComboBox1.setMinimumSize(new Dimension(42, 21));
    jComboBox1.setPreferredSize(new Dimension(42, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("PRONADJITK_BUTTON_CANCEL"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("PRONADJITK_BUTTON_OK"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel2.setLayout(xYLayout3);
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(330);
    jLabel2.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 316, 97));
    jPanel4.add(jLabel1, new XYConstraints(25, 25, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(105, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(160, 25, 150, -1));
    jPanel1.add(jPanel2, new XYConstraints(48, 102, 203, 37));
    jPanel1.add(jButton1, new XYConstraints(180, 150, -1, -1));
    jPanel1.add(jButton2, new XYConstraints(75, 150, -1, -1));
    this.setModal(true);

    dbcm = new DBComboModel(conn,"select KONTRID,KO_NAZIV from tip_kontrole order by KONTRID",true);
    jComboBox1.setModel(dbcm);
    String tpk =tk.getCurrentTK().trim();
    jComboBox1.setSelectedItem(tpk);
    setName();
  }

  void setName() {
    jLabel2.setText((String)dbcm.getNameAt(jComboBox1.getSelectedIndex()));
  }

  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }

  void jButton2_actionPerformed(ActionEvent e)  {
    tk.find((String)jComboBox1.getSelectedItem());
    dispose();
  }

  void jComboBox1_actionPerformed(ActionEvent e)  {
    setName();
  }

}
