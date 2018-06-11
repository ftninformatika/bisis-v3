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

public class FSFMandLev extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JComboBox jComboBox3 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel4 = new JLabel();
  Connection conn;
  SFMandLevDlg sfmld;
  boolean trans;
  DBComboModel  dbcm1;
  DBComboModel  dbcm2;
  DBComboModel  dbcm3;
  DBComboModel  dbcm4;
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();

  public FSFMandLev(Frame frame, String title, boolean modal, Connection conn, SFMandLevDlg sfmld) {
    super(frame, title, modal);
    this.conn = conn;
    this.sfmld = sfmld;
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
    xYLayout1.setWidth(350);
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
    jLabel1.setText(Messages.get("FSFMANDLEV_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("FSFMANDLEV_LABEL_PROCESS_LEVEL"));
    jbCancel.setText(Messages.get("FSFMANDLEV_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FSFMANDLEV_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
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
    jLabel3.setText(Messages.get("FSFMANDLEV_LABEL_MANDATORY_LEVEL"));
    jLabel4.setText(Messages.get("FSFMANDLEV_LABEL_FIELD_CODE"));
    jLabel5.setText(Messages.get("FSFMANDLEV_LABEL_15"));
    jLabel6.setText(Messages.get("FSFMANDLEV_LABEL_16"));
    jLabel7.setText(Messages.get("FSFMANDLEV_LABEL_17"));
    jLabel8.setText(Messages.get("FSFMANDLEV_LABEL_18"));
    getContentPane().add(panel1);
    panel1.add(jComboBox1, new XYConstraints(130, 20, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(130, 70, 70, 22));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jbCancel, new XYConstraints(190, 220, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 220, 90, 25));
    panel1.add(jComboBox3, new XYConstraints(130, 120, 70, 22));
    panel1.add(jLabel3, new XYConstraints(20, 120, -1, -1));
    panel1.add(jComboBox4, new XYConstraints(130, 170, 80, 22));
    panel1.add(jLabel4, new XYConstraints(20, 170, -1, -1));
    panel1.add(jLabel5, new XYConstraints(225, 170, 126, -1));
    panel1.add(jLabel6, new XYConstraints(225, 120, 127, -1));
    panel1.add(jLabel7, new XYConstraints(225, 70, 127, -1));
    panel1.add(jLabel8, new XYConstraints(225, 20, 126, -1));
    setResizable(false);

    trans = true;

    dbcm1 = new DBComboModel(conn, "select distinct nobpp.NOV_NOB_TP_TIPID,tp.TIP_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, TIP_PUBLIKACIJE tp"+
                              " where tp.TIPID=nobpp.NOV_NOB_TP_TIPID order by nobpp.NOV_NOB_TP_TIPID", "",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(sfmld.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn, "select distinct nobpp.NOV_NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, NIVO_OBRADE nobr",
    			      " where nobr.TP_TIPID=nobpp.NOV_NOB_TP_TIPID and nobr.NOBRID=nobpp.NOV_NOB_NOBRID and nobpp.NOV_NOB_TP_TIPID = "+"'"+sfmld.jComboBox1.getSelectedItem()+"'"+
    			      " order by nobpp.NOV_NOB_NOBRID",true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sfmld.jComboBox2.getSelectedItem());

    dbcm3 = new DBComboModel(conn, "select distinct nobpp.NOV_NOBAVID, nob.NOBAV_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, NIVO_OBAVEZNOSTI nob",
    			      " where nob.NOBAVID=nobpp.NOV_NOBAVID and nob.NOB_NOBRID=nobpp.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=nobpp.NOV_NOB_TP_TIPID and nobpp.NOV_NOB_TP_TIPID = "+"'"+sfmld.jComboBox1.getSelectedItem()+"'"+
                              " and nobpp.NOV_NOB_NOBRID="+"'"+sfmld.jComboBox2.getSelectedItem()+"'"+" order by nobpp.NOV_NOBAVID",true);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(sfmld.jComboBox3.getSelectedItem());

    dbcm4 = new DBComboModel(conn, "select distinct nobpp.PPO_PO_POLJEID, p.PO_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, POLJE p",
    			      " where p.POLJEID=nobpp.PPO_PO_POLJEID	and nobpp.NOV_NOB_TP_TIPID = "+"'"+sfmld.jComboBox1.getSelectedItem()+"'"+
                              " and nobpp.NOV_NOB_NOBRID="+"'"+sfmld.jComboBox2.getSelectedItem()+"'"+" and nobpp.NOV_NOBAVID="+"'"+sfmld.jComboBox3.getSelectedItem()+"'"+" order by nobpp.PPO_PO_POLJEID", true);
    jComboBox4.setModel(dbcm4);
    jComboBox4.setSelectedItem(sfmld.jComboBox4.getSelectedItem());

    trans=false;
    setNameTP();
    setNameNO();
    setNameNOBAV();
    setNameFI();
  }
  void setNameTP() {
    jLabel8.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel7.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameNOBAV() {
    jLabel6.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel5.setText((String)dbcm4.getNameAt(jComboBox4.getSelectedIndex()));
  }

  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    String typePubl = ((String)dbcm1.getSelectedItem()).trim();
    String noBr = ((String)dbcm2.getSelectedItem()).trim();
    String noBav = ((String)dbcm3.getSelectedItem()).trim();
    String field = ((String)dbcm4.getSelectedItem()).trim();
    int lengths[] = {typePubl.length(),noBr.length(),noBav.length(),field.length(),0};
    int i = sfmld.dbmsfml.find(typePubl+"|"+noBr+"|"+noBav+"|"+field,lengths,3);
    if (i != -1) {
      sfmld.trans = true;
      sfmld.setValues(i);
      sfmld.row = i;
      sfmld.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FSFMANDLEV_OPTION_PANE_SUBFIELD_NOT_EXIST"),Messages.get("FSFMANDLEV_OPTION_PANE_SUBFIELD_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameTP();
      trans=true;
      dbcm2.reloadItems("select distinct nobpp.NOV_NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, NIVO_OBRADE nobr"+
                        " where nobr.TP_TIPID=nobpp.NOV_NOB_TP_TIPID and nobr.NOBRID=nobpp.NOV_NOB_NOBRID and nobpp.NOV_NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
      			" order by nobpp.NOV_NOB_NOBRID", true);
      jComboBox2.setSelectedIndex(0);
      setNameNO();
      dbcm3.reloadItems("select distinct nobpp.NOV_NOBAVID, nob.NOBAV_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, NIVO_OBAVEZNOSTI nob"+
      			" where nob.NOBAVID=nobpp.NOV_NOBAVID and nob.NOB_NOBRID=nobpp.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=nobpp.NOV_NOB_TP_TIPID and nobpp.NOV_NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nobpp.NOV_NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by nobpp.NOV_NOBAVID", true);
      jComboBox3.setSelectedIndex(0);
      setNameNOBAV();
      dbcm4.reloadItems("select distinct nobpp.PPO_PO_POLJEID, p.PO_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, POLJE p"+
                        " where p.POLJEID=nobpp.PPO_PO_POLJEID and nobpp.NOV_NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nobpp.NOV_NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" and nobpp.NOV_NOBAVID="+"'"+jComboBox3.getSelectedItem()+"'"+" order by nobpp.PPO_PO_POLJEID",true);
      jComboBox4.setSelectedIndex(0);
      setNameFI();
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameNO();
      trans=true;
      dbcm3.reloadItems("select distinct nobpp.NOV_NOBAVID, nob.NOBAV_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, NIVO_OBAVEZNOSTI nob"+
      			" where nob.NOBAVID=nobpp.NOV_NOBAVID and nob.NOB_NOBRID=nobpp.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=nobpp.NOV_NOB_TP_TIPID and nobpp.NOV_NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nobpp.NOV_NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by nobpp.NOV_NOBAVID", true);
      jComboBox3.setSelectedIndex(0);
      setNameNOBAV();
      dbcm4.reloadItems("select distinct nobpp.PPO_PO_POLJEID, p.PO_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, POLJE p"+
                        " where p.POLJEID=nobpp.PPO_PO_POLJEID and nobpp.NOV_NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nobpp.NOV_NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" and nobpp.NOV_NOBAVID="+"'"+jComboBox3.getSelectedItem()+"'"+" order by nobpp.PPO_PO_POLJEID",true);
      jComboBox4.setSelectedIndex(0);
      setNameFI();
      trans=false;
    }
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameNOBAV();
      trans=true;
      dbcm4.reloadItems("select distinct nobpp.PPO_PO_POLJEID, p.PO_NAZIV from NIVO_OBAVEZNOSTI_POTPOLJA nobpp, POLJE p"+
                        " where p.POLJEID=nobpp.PPO_PO_POLJEID and nobpp.NOV_NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nobpp.NOV_NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" and nobpp.NOV_NOBAVID="+"'"+jComboBox3.getSelectedItem()+"'"+" order by nobpp.PPO_PO_POLJEID",true);
      jComboBox4.setSelectedIndex(0);
      trans=false;
      setNameFI();
    }
  }
  void jComboBox4_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameFI();
    }
  }
}
