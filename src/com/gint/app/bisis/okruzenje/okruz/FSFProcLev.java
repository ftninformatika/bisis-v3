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

public class FSFProcLev extends JDialog {
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
  Connection conn;
  SFProcLevDlg sfpld;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  boolean trans;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  public FSFProcLev(Frame frame, String title, boolean modal, Connection conn, SFProcLevDlg sfpld) {
    super(frame, title, modal);
    this.conn = conn;
    this.sfpld = sfpld;
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
    jLabel1.setText(Messages.get("FSFPROCLEV_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("FSFPROCLEV_LABEL_PROCESS_LEVEL"));
    jbCancel.setText(Messages.get("FSFPROCLEV_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FSFPROCLEV_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jLabel3.setText(Messages.get("FSFPROCLEV_LABEL_FIELD_CODE"));
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
    getContentPane().add(panel1);
    panel1.add(jComboBox1, new XYConstraints(120, 20, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(120, 70, 70, 22));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jbCancel, new XYConstraints(190, 220, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 220, 90, 25));
    panel1.add(jComboBox3, new XYConstraints(120, 120, 80, 22));
    panel1.add(jLabel3, new XYConstraints(20, 120, -1, -1));
    panel1.add(jLabel4, new XYConstraints(215, 120, 136, -1));
    panel1.add(jLabel5, new XYConstraints(215, 70, 136, -1));
    panel1.add(jLabel6, new XYConstraints(215, 20, 136, -1));
    setResizable(false);

    trans = true;

    dbcm1 = new DBComboModel(conn, "select distinct nopp.NOB_TP_TIPID, tp.TIP_NAZIV from NIVO_OBRADE_POTPOLJA nopp, TIP_PUBLIKACIJE tp"+
    	                      " where tp.TIPID=nopp.NOB_TP_TIPID order by nopp.NOB_TP_TIPID", "",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(sfpld.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn, "select distinct nopp.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBRADE_POTPOLJA nopp, NIVO_OBRADE nobr",
    			      " where nobr.TP_TIPID=nopp.NOB_TP_TIPID and nobr.NOBRID=nopp.NOB_NOBRID and nopp.NOB_TP_TIPID = "+"'"+sfpld.jComboBox1.getSelectedItem()+
    			      "'"+" order by nopp.NOB_NOBRID",true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sfpld.jComboBox2.getSelectedItem());

    dbcm3 = new DBComboModel(conn, "select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p",
    			      " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = "+"'"+sfpld.jComboBox1.getSelectedItem()+"'"+
                              " and nopp.NOB_NOBRID="+"'"+sfpld.jComboBox2.getSelectedItem()+"'"+" order by nopp.PPO_PO_POLJEID",true);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(sfpld.jComboBox3.getSelectedItem());

    trans=false;
    setNameTP();
    setNameNO();
    setNameFI();
  }
  void setNameTP() {
    jLabel6.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel5.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel4.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    String typePubl = ((String)dbcm1.getSelectedItem()).trim();
    String noBr = ((String)dbcm2.getSelectedItem()).trim();
    String field = ((String)dbcm3.getSelectedItem()).trim();
    int lengths[] = {typePubl.length(),noBr.length(),field.length(),0};
    int i = sfpld.dbmsfpl.find(typePubl+"|"+noBr+"|"+field,lengths,2);
    if (i != -1) {
      sfpld.trans = true;
      sfpld.setValues(i);
      sfpld.row = i;
      sfpld.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FSFPROCLEV_OPTION_PANE_SUBFIELD_NOT_EXIST"),Messages.get("FSFPROCLEV_OPTION_PANE_SUBFIELD_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameTP();
      trans=true;
      dbcm2.reloadItems("select distinct nopp.NOB_NOBRID, nobr.NOBR_NAZIV from NIVO_OBRADE_POTPOLJA nopp, NIVO_OBRADE nobr"+
                        " where nobr.TP_TIPID=nopp.NOB_TP_TIPID and nobr.NOBRID=nopp.NOB_NOBRID and nopp.NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+" order by NOB_NOBRID");
      jComboBox2.setSelectedIndex(0);
      setNameNO();
      dbcm3.reloadItems("select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p"+
                        " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nopp.NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by nopp.PPO_PO_POLJEID",true);
      jComboBox3.setSelectedIndex(0);
      setNameFI();
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameNO();
      trans=true;
      dbcm3.reloadItems("select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p"+
                        " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = "+"'"+jComboBox1.getSelectedItem()+"'"+
                        " and nopp.NOB_NOBRID="+"'"+jComboBox2.getSelectedItem()+"'"+" order by nopp.PPO_PO_POLJEID",true);
      jComboBox3.setSelectedIndex(0);
      trans=false;
      setNameFI();
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e) {
    if(!trans)
      setNameFI();
  }
}
