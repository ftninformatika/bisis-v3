//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.okruzenje.okruz;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class FSFTypePubl extends JDialog {
  JPanel panel1 = new JPanel();
  Connection conn;
  SFTypePublDlg sftpd;
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox3 = new JComboBox();
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  boolean trans=true;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  public FSFTypePubl(Frame frame, String title, boolean modal, Connection conn, SFTypePublDlg sftpd) {
    super(frame, title, modal);
    this.conn = conn;
    this.sftpd = sftpd;
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
    xYLayout1.setHeight(230);
    xYLayout1.setWidth(350);
    jbCancel.setText(Messages.get("FSFTYPEPUBL_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FSFTYPEPUBL_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FSFTYPEPUBL_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("FSFTYPEPUBL_LABEL_FIELD_CODE"));
    jLabel3.setText(Messages.get("FSFTYPEPUBL_LABEL_SUBFIELD_CODE"));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jbCancel, new XYConstraints(190, 180, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 180, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(20, 120, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(120, 20, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(120, 70, 80, 22));
    panel1.add(jComboBox3, new XYConstraints(120, 120, 60, 22));
    panel1.add(jLabel4, new XYConstraints(215, 70, 137, -1));
    panel1.add(jLabel5, new XYConstraints(215, 20, 136, -1));
    panel1.add(jLabel6, new XYConstraints(215, 120, 137, -1));
    setResizable(false);
    trans = true;

    dbcm1 = new DBComboModel(conn,"select distinct dt.TP_TIPID,tp.TIP_NAZIV from DEF_TIPA dt, TIP_PUBLIKACIJE tp"+
    	                      " where tp.TIPID=dt.TP_TIPID order by dt.TP_TIPID ASC","",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(sftpd.jcbTypePubl.getSelectedItem());

    dbcm2 = new DBComboModel(conn,"select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p ",
                              " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID="+"'"+jComboBox1.getSelectedItem()+"' order by dt.PPO_PO_POLJEID ASC",true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sftpd.jcbField.getSelectedItem());

    dbcm3 = new DBComboModel(conn,"select distinct dt.PPO_POTPOLJAID,pp.PP_NAZIV from DEF_TIPA dt, POTPOLJA pp",
                              " where pp.PO_POLJEID=dt.PPO_PO_POLJEID and pp.POTPOLJAID=dt.PPO_POTPOLJAID and dt.TP_TIPID="+"'"+jComboBox1.getSelectedItem()+"'"+
                              " and dt.PPO_PO_POLJEID="+"'"+jComboBox2.getSelectedItem()+"' order by dt.PPO_POTPOLJAID ASC",true);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(sftpd.jcbSubField.getSelectedItem());
    trans=false;

    setNameTP();
    setNameFI();
    setNameSF();
  }

  void setNameTP() {
    jLabel5.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel4.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameSF() {
    jLabel6.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int[] lengths={((String)dbcm1.getSelectedItem()).trim().length(),((String)dbcm2.getSelectedItem()).trim().length(),
                 ((String)dbcm3.getSelectedItem()).trim().length()};
    int i = sftpd.dbmsftp.find(((String)dbcm1.getSelectedItem()).trim()+"|"+((String)dbcm2.getSelectedItem()).trim()+"|"+((String)dbcm3.getSelectedItem()).trim(),lengths,2);
    if (i != -1) {
      sftpd.trans = true;
      sftpd.setValues(i);
      sftpd.row = i;
      sftpd.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FSFTYPEPUBL_OPTION_PANE_SUBFIELD_NOT_EXIST"),Messages.get("FSFTYPEPUBL_OPTION_PANE_SUBFIELD_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameTP();
      trans=true;
      dbcm2.reloadItems("select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID="
                        +"'"+jComboBox1.getSelectedItem()+"' order by dt.PPO_PO_POLJEID ASC",true);
      jComboBox2.setSelectedIndex(0);
      setNameFI();
      dbcm3.reloadItems("select distinct dt.PPO_POTPOLJAID, pp.PP_NAZIV from DEF_TIPA dt, POTPOLJA pp where pp.PO_POLJEID=dt.PPO_PO_POLJEID and pp.POTPOLJAID=dt.PPO_POTPOLJAID"+
      			" and dt.TP_TIPID="+"'"+jComboBox1.getSelectedItem()+"'"+" and dt.PPO_PO_POLJEID="+"'"+jComboBox2.getSelectedItem()+"' order by dt.PPO_POTPOLJAID ASC",true);
      jComboBox3.setSelectedIndex(0);
      trans=false;
      setNameSF();
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameFI();
      trans=true;
      dbcm3.reloadItems("select distinct dt.PPO_POTPOLJAID, pp.PP_NAZIV from DEF_TIPA dt, POTPOLJA pp where pp.PO_POLJEID=dt.PPO_PO_POLJEID and pp.POTPOLJAID=dt.PPO_POTPOLJAID"+
                        " and dt.TP_TIPID="+"'"+jComboBox1.getSelectedItem()+"'"+" and dt.PPO_PO_POLJEID="+"'"+jComboBox2.getSelectedItem()+"' order by dt.PPO_POTPOLJAID ASC",true);
      jComboBox3.setSelectedIndex(0);
      trans=false;
      setNameSF();
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e) {
    if(!trans) {
      setNameSF();
    }
  }
}
