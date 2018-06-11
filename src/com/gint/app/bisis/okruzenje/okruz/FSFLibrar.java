//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.okruzenje.okruz;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.sql.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class FSFLibrar extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox3 = new JComboBox();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  Connection  conn;
  SFLibrarDlg sflid;
  boolean trans;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBComboModel dbcm4;
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();

  public FSFLibrar(Frame frame, String title, boolean modal, Connection conn, SFLibrarDlg sflid) {
    super(frame, title, modal);
    this.conn = conn;
    this.sflid = sflid;
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
    xYLayout1.setHeight(270);
    xYLayout1.setWidth(470);
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    jComboBox4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox4_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FSFLIBRAR_LABEL_LIBRARIAN_CODE"));
    jLabel2.setText(Messages.get("FSFLIBRAR_LABEL_PUBLICATION_TYPE"));
    jLabel3.setText(Messages.get("FSFLIBRAR_LABEL_FIELD_CODE"));
    jLabel4.setText(Messages.get("FSFLIBRAR_LABEL_SUBFIELD_CODE"));
    jbCancel.setText(Messages.get("FSFLIBRAR_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FSFLIBRAR_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jComboBox1, new XYConstraints(130, 20, 180, 22));
    panel1.add(jComboBox2, new XYConstraints(130, 70, 70, 22));
    panel1.add(jComboBox3, new XYConstraints(130, 120, 80, 22));
    panel1.add(jComboBox4, new XYConstraints(130, 170, 60, 22));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(20, 120, -1, -1));
    panel1.add(jLabel4, new XYConstraints(20, 170, -1, -1));
    panel1.add(jbCancel, new XYConstraints(230, 220, 90, 25));
    panel1.add(jbOk, new XYConstraints(110, 220, 90, 25));
    panel1.add(jLabel6, new XYConstraints(240, 70, 231, -1));
    panel1.add(jLabel7, new XYConstraints(240, 120, 231, -1));
    panel1.add(jLabel8, new XYConstraints(240, 170, 232, -1));
    setResizable(false);

    trans = true;

    dbcm1 = new DBComboModel(conn, "select distinct BIB_BIBID from TIPBIBLIOTEKARA order by BIB_BIBID", "");
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(sflid.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn, "select distinct tb.TP_TIPID,tp.TIP_NAZIV from TIPBIBLIOTEKARA tb, TIP_PUBLIKACIJE tp",
    	                      " where tp.TIPID=tb.TP_TIPID and tb.BIB_BIBID = "+"'"+sflid.jComboBox1.getSelectedItem()+"'"+" order by tb.TP_TIPID",true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sflid.jComboBox2.getSelectedItem());

    dbcm3 = new DBComboModel(conn, "select distinct tb.PPO_PO_POLJEID,p.PO_NAZIV from TIPBIBLIOTEKARA tb, POLJE p",
                              " where p.POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+sflid.jComboBox1.getSelectedItem()+"'"+
                              " and tb.TP_TIPID="+"'"+sflid.jComboBox2.getSelectedItem()+"'"+" order by tb.PPO_PO_POLJEID",true);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(sflid.jComboBox3.getSelectedItem());

    dbcm4 = new DBComboModel(conn, "select distinct tb.PPO_POTPOLJAID,pp.PP_NAZIV from TIPBIBLIOTEKARA tb, POTPOLJA pp",
    			      " where pp.POTPOLJAID=tb.PPO_POTPOLJAID and pp.PO_POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+sflid.jComboBox1.getSelectedItem()+"'"+
                              " and tb.TP_TIPID="+"'"+sflid.jComboBox2.getSelectedItem()+"'"+" and tb.PPO_PO_POLJEID="+"'"+sflid.jComboBox3.getSelectedItem()+"'"+" order by tb.PPO_POTPOLJAID",true);
    jComboBox4.setModel(dbcm4);
    jComboBox4.setSelectedItem(sflid.jComboBox4.getSelectedItem());

    trans=false;
    setNameTP();
    setNameFI();
    setNameSF();
  }

  void setNameTP() {
    jLabel6.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel7.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameSF() {
    jLabel8.setText((String)dbcm4.getNameAt(jComboBox4.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    String librar = ((String)dbcm1.getSelectedItem()).trim();
    String typePubl = ((String)dbcm2.getSelectedItem()).trim();
    String field = ((String)dbcm3.getSelectedItem()).trim();
    String subField = ((String)dbcm4.getSelectedItem()).trim();
    int lengths[] = {librar.length(),typePubl.length(),field.length(),subField.length(),0};
    int i = sflid.dbmsfli.find(librar+"|"+typePubl+"|"+field+"|"+subField,lengths,3);
    if (i != -1) {
      sflid.trans = true;
      sflid.setValues(i);
      sflid.row = i;
      sflid.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FSFLIBRAR_OPTION_PANE_SUBFIELD_NOT_EXIST"),Messages.get("FSFLIBRAR_OPTION_PANE_SUBFIELD_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if(!trans) {
      trans=true;
      dbcm2.reloadItems("select distinct tb.TP_TIPID, tp.TIP_NAZIV from TIPBIBLIOTEKARA tb, TIP_PUBLIKACIJE tp"+
                        " where tb.TP_TIPID=tp.TIPID and tb.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+" order by tb.TP_TIPID",true);
      jComboBox2.setSelectedIndex(0);
      setNameTP();
      dbcm3.reloadItems("select distinct tb.PPO_PO_POLJEID,p.PO_NAZIV from TIPBIBLIOTEKARA tb, POLJE p"+
                        " where p.POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tb.TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by tb.PPO_PO_POLJEID",true);
      jComboBox3.setSelectedIndex(0);
      setNameFI();
      dbcm4.reloadItems("select distinct tb.PPO_POTPOLJAID,pp.PP_NAZIV from TIPBIBLIOTEKARA tb, POTPOLJA pp"+
                        " where pp.POTPOLJAID=tb.PPO_POTPOLJAID and pp.PO_POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tb.TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" and tb.PPO_PO_POLJEID="+"'"+jComboBox3.getSelectedItem()+"'"+" order by tb.PPO_POTPOLJAID",true);
      jComboBox4.setSelectedIndex(0);
      setNameSF();
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameTP();
      trans=true;
      dbcm3.reloadItems("select distinct tb.PPO_PO_POLJEID,p.PO_NAZIV from TIPBIBLIOTEKARA tb, POLJE p"+
                        " where p.POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tb.TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by tb.PPO_PO_POLJEID",true);
      jComboBox3.setSelectedIndex(0);
      setNameFI();
      dbcm4.reloadItems("select distinct tb.PPO_POTPOLJAID,pp.PP_NAZIV from TIPBIBLIOTEKARA tb, POTPOLJA pp"+
                        " where pp.POTPOLJAID=tb.PPO_POTPOLJAID and pp.PO_POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tb.TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" and tb.PPO_PO_POLJEID="+"'"+jComboBox3.getSelectedItem()+"'"+" order by tb.PPO_POTPOLJAID",true);
      jComboBox4.setSelectedIndex(0);
      setNameSF();
      trans=false;
    }
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameFI();
      trans=true;
      dbcm4.reloadItems("select distinct tb.PPO_POTPOLJAID,pp.PP_NAZIV from TIPBIBLIOTEKARA tb, POTPOLJA pp"+
                        " where pp.POTPOLJAID=tb.PPO_POTPOLJAID and pp.PO_POLJEID=tb.PPO_PO_POLJEID and tb.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tb.TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" and tb.PPO_PO_POLJEID="+"'"+jComboBox3.getSelectedItem()+"'"+" order by tb.PPO_POTPOLJAID",true);
      jComboBox4.setSelectedIndex(0);
      setNameSF();
      trans=false;
    }
  }
  void jComboBox4_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameSF();
    }
  }
}
