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

public class FProcType extends JDialog {
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
  Connection conn;
  ProcTypeDlg ptd;
  DBComboModel  dbcm1;
  DBComboModel  dbcm2;
  DBComboModel  dbcm3;
  DBComboModel  dbcm4;
  boolean trans;
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();

  public FProcType(Frame frame, String title, boolean modal, Connection conn, ProcTypeDlg ptd) {
    super(frame, title, modal);
    this.conn = conn;
    this.ptd = ptd;
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
    jLabel1.setText(Messages.get("FPROCTYPE_LABEL_LIBRARIAN_CODE"));
    jLabel2.setText(Messages.get("FPROCTYPE_LABEL_PUBLICATION_TYPE"));
    jLabel3.setText(Messages.get("FPROCTYPE_LABEL_PROCESS_LEVEL"));
    jLabel4.setText(Messages.get("FPROCTYPE_LABEL_MANDATORY_LEVEL"));
    jbCancel.setText(Messages.get("FPROCTYPE_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FPROCTYPE_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(270);
    xYLayout1.setWidth(470);
    getContentPane().add(panel1);
    panel1.add(jComboBox1, new XYConstraints(130, 20, 180, 22));
    panel1.add(jComboBox2, new XYConstraints(130, 70, 70, 22));
    panel1.add(jComboBox3, new XYConstraints(130, 120, 70, 22));
    panel1.add(jComboBox4, new XYConstraints(130, 170, 70, 22));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(20, 120, -1, -1));
    panel1.add(jLabel4, new XYConstraints(20, 170, -1, -1));
    panel1.add(jbCancel, new XYConstraints(230, 220, 90, 25));
    panel1.add(jbOk, new XYConstraints(110, 220, 90, 25));
    panel1.add(jLabel6, new XYConstraints(230, 70, 241, -1));
    panel1.add(jLabel7, new XYConstraints(230, 120, 241, -1));
    panel1.add(jLabel8, new XYConstraints(230, 170, 241, -1));
    setResizable(false);

    trans = true;

    dbcm1 = new DBComboModel(conn, "select distinct BIB_BIBID from TIP_OBRADE order by BIB_BIBID", "");
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(ptd.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn, "select distinct tob.NOV_NOB_TP_TIPID,tp.TIP_NAZIV from TIP_OBRADE tob, TIP_PUBLIKACIJE tp",
                              " where tp.TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = '"+ptd.jComboBox1.getSelectedItem()+"'"
    			      +" order by tob.NOV_NOB_TP_TIPID",true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(ptd.jComboBox2.getSelectedItem());

    dbcm3 = new DBComboModel(conn, "select distinct tob.NOV_NOB_NOBRID,nobr.NOBR_NAZIV from TIP_OBRADE tob, NIVO_OBRADE nobr",
    		              " where nobr.NOBRID=tob.NOV_NOB_NOBRID and nobr.TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+ptd.jComboBox1.getSelectedItem()+"'"+
                              " and tob.NOV_NOB_TP_TIPID="+"'"+ptd.jComboBox2.getSelectedItem()+"'"+" order by tob.NOV_NOB_NOBRID",true);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(ptd.jComboBox3.getSelectedItem());

    dbcm4 = new DBComboModel(conn, "select distinct tob.NOV_NOBAVID,nob.NOBAV_NAZIV from TIP_OBRADE tob, NIVO_OBAVEZNOSTI nob",
    			      " where nob.NOBAVID=tob.NOV_NOBAVID and nob.NOB_NOBRID=tob.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+ptd.jComboBox1.getSelectedItem()+"'"+
                              " and tob.NOV_NOB_TP_TIPID="+"'"+ptd.jComboBox2.getSelectedItem()+"'"+
                              " and tob.NOV_NOB_NOBRID="+"'"+ptd.jComboBox3.getSelectedItem()+"'"+" order by tob.NOV_NOBAVID",true);
    jComboBox4.setModel(dbcm4);
    jComboBox4.setSelectedItem(ptd.jComboBox4.getSelectedItem());

    trans=false;
    setNameTP();
    setNameNO();
    setNameNOBAV();
  }
  void setNameTP() {
    jLabel6.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel7.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameNOBAV() {
    jLabel8.setText((String)dbcm4.getNameAt(jComboBox4.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    String librar = ((String)dbcm1.getSelectedItem()).trim();
    String typePubl = ((String)dbcm2.getSelectedItem()).trim();
    String noBr = ((String)dbcm3.getSelectedItem()).trim();
    String noBav = ((String)dbcm4.getSelectedItem()).trim();
    int lengths[] = {librar.length(),typePubl.length(),noBr.length(),noBav.length(),0};
    int i = ptd.dbmpt.find(librar+"|"+typePubl+"|"+noBr+"|"+noBav,lengths,3);
    if (i != -1) {
      ptd.trans = true;
      ptd.setValues(i);
      ptd.row = i;
      ptd.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FPROCTYPE_OPTION_PANE_PROCESS_TYPE_NOT_EXIST"),Messages.get("FPROCTYPE_OPTION_PANE_PROCESS_TYPE_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if(!trans) {
      trans=true;
      dbcm2.reloadItems("select distinct tob.NOV_NOB_TP_TIPID,tp.TIP_NAZIV from TIP_OBRADE tob, TIP_PUBLIKACIJE tp"+
                        " where tp.TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"
      			+" order by tob.NOV_NOB_TP_TIPID",true);
      jComboBox2.setSelectedIndex(0);
      setNameTP();
      dbcm3.reloadItems("select distinct tob.NOV_NOB_NOBRID,nobr.NOBR_NAZIV from TIP_OBRADE tob, NIVO_OBRADE nobr"+
      			" where nobr.NOBRID=tob.NOV_NOB_NOBRID and nobr.TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by tob.NOV_NOB_NOBRID",true);
      jComboBox3.setSelectedIndex(0);
      setNameNO();
      dbcm4.reloadItems("select distinct tob.NOV_NOBAVID,nob.NOBAV_NAZIV from TIP_OBRADE tob, NIVO_OBAVEZNOSTI nob"
      			+" where nob.NOBAVID=tob.NOV_NOBAVID and nob.NOB_NOBRID=tob.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_NOBRID="+"'"+jComboBox3.getSelectedItem()+"'"
                        +" order by tob.NOV_NOBAVID",true);
      jComboBox4.setSelectedIndex(0);
      setNameNOBAV();
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans) {
      trans=true;
      dbcm3.reloadItems("select distinct tob.NOV_NOB_NOBRID,nobr.NOBR_NAZIV from TIP_OBRADE tob, NIVO_OBRADE nobr"+
                        " where nobr.NOBRID=tob.NOV_NOB_NOBRID and nobr.TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by tob.NOV_NOB_NOBRID",true);
      jComboBox3.setSelectedIndex(0);
      setNameNO();
      dbcm4.reloadItems("select distinct tob.NOV_NOBAVID,nob.NOBAV_NAZIV from TIP_OBRADE tob, NIVO_OBAVEZNOSTI nob"
                        +" where nob.NOBAVID=tob.NOV_NOBAVID and nob.NOB_NOBRID=tob.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_NOBRID="+"'"+jComboBox3.getSelectedItem()+"'"
                        +" order by tob.NOV_NOBAVID",true);
      jComboBox4.setSelectedIndex(0);
      setNameNOBAV();
      trans=false;
    }
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameNO();
      trans=true;
      dbcm4.reloadItems("select distinct tob.NOV_NOBAVID,nob.NOBAV_NAZIV from TIP_OBRADE tob, NIVO_OBAVEZNOSTI nob"
                        +" where nob.NOBAVID=tob.NOV_NOBAVID and nob.NOB_NOBRID=tob.NOV_NOB_NOBRID and nob.NOB_TP_TIPID=tob.NOV_NOB_TP_TIPID and tob.BIB_BIBID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_TP_TIPID="+"'"+jComboBox2.getSelectedItem()+"'"+
                        " and tob.NOV_NOB_NOBRID="+"'"+jComboBox3.getSelectedItem()+"'"
                        +" order by tob.NOV_NOBAVID",true);
      jComboBox4.setSelectedIndex(0);
      setNameNOBAV();
      trans=false;
    }
  }
  void jComboBox4_actionPerformed(ActionEvent e) {
    if(!trans)
      setNameNOBAV();
  }
}
