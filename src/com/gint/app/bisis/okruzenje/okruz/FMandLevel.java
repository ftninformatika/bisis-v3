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

public class FMandLevel extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox3 = new JComboBox();
  Connection conn;
  MandLevelDlg mld;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  boolean trans;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  public FMandLevel(Frame frame, String title, boolean modal, Connection conn, MandLevelDlg mld) {
    super(frame, title, modal);
    this.conn = conn;
    this.mld = mld;
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
    xYLayout1.setHeight(210);
    xYLayout1.setWidth(350);
    jbCancel.setText(Messages.get("FMANDLEVEL_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FMANDLEVEL_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("FMANDLEVEL_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("FMANDLEVEL_LABEL_PROCESS_LEVEL"));
    jLabel3.setText(Messages.get("FMANDLEVEL_LABEL_MANDATORY_LEVEL"));
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
    getContentPane().add(panel1);
    panel1.add(jbCancel, new XYConstraints(190, 160, 90, 25));
    panel1.add(jbOk, new XYConstraints(70, 160, 90, 25));
    panel1.add(jLabel1, new XYConstraints(20, 20, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(130, 20, 70, 22));
    panel1.add(jLabel2, new XYConstraints(20, 70, -1, -1));
    panel1.add(jComboBox2, new XYConstraints(130, 70, 70, 22));
    panel1.add(jLabel3, new XYConstraints(20, 120, -1, -1));
    panel1.add(jComboBox3, new XYConstraints(130, 120, 70, 22));
    panel1.add(jLabel4, new XYConstraints(215, 20, 136, -1));
    panel1.add(jLabel5, new XYConstraints(215, 70, 136, -1));
    panel1.add(jLabel6, new XYConstraints(215, 120, 136, -1));
    setResizable(false);

    trans = true;

    dbcm1 = new DBComboModel(conn,"select distinct nobav.NOB_TP_TIPID, tp.TIP_NAZIV from NIVO_OBAVEZNOSTI nobav, TIP_PUBLIKACIJE tp"+
    													" where  tp.TIPID=nobav.NOB_TP_TIPID order by nobav.NOB_TP_TIPID ASC","",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(mld.jComboBox1.getSelectedItem());

    dbcm2 = new DBComboModel(conn,"select distinct nobav.NOB_NOBRID, nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nobav, NIVO_OBRADE nobr",
    												" where nobav.NOB_NOBRID=nobr.NOBRID and nobr.TP_TIPID=nobav.NOB_TP_TIPID and nobav.NOB_TP_TIPID="+"'"+(String)mld.jComboBox1.getSelectedItem()+
    												"' order by nobav.NOB_NOBRID ASC", true);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(mld.jComboBox2.getSelectedItem());

    dbcm3 = new DBComboModel(conn,"select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"," where NOB_TP_TIPID="+"'"+(String)mld.jComboBox1.getSelectedItem()+"' and NOB_NOBRID ="+
                              "'"+(String)mld.jComboBox2.getSelectedItem()+"' "+"order by NOBAVID ASC",true);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(mld.jTextField1.getText());
    trans = false;
    setNameTP();
    setNameNO();
    setNameNOBAV();

  }
  void setNameTP() {
    jLabel4.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel5.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameNOBAV() {
    jLabel6.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }

  void jbCancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbOk_actionPerformed(ActionEvent e) {
    int[] lengths={((String)dbcm1.getSelectedItem()).trim().length(),((String)dbcm2.getSelectedItem()).trim().length(),
                 ((String)dbcm3.getSelectedItem()).trim().length()};
    int i = mld.dbmml.find(((String)dbcm1.getSelectedItem()).trim()+"|"+((String)dbcm2.getSelectedItem()).trim()+"|"+((String)dbcm3.getSelectedItem()).trim(),lengths,3);
    if (i != -1) {
      mld.trans = true;
      mld.setValues(i);
      mld.row = i;
      mld.trans = false;
      dispose();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FMANDLEVEL_OPTION_PANE_NOT_EXISTS"),Messages.get("FMANDLEVEL_OPTION_PANE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if (!trans) {
      setNameTP();
      trans=true;
      dbcm2.reloadItems("select distinct nobav.NOB_NOBRID, nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nobav, NIVO_OBRADE nobr"+
                        " where nobav.NOB_NOBRID=nobr.NOBRID and nobr.TP_TIPID=nobav.NOB_TP_TIPID and nobav.NOB_TP_TIPID="+"'"+(String)jComboBox1.getSelectedItem()+
      			"' order by nobav.NOB_NOBRID ASC",true);
      jComboBox2.setSelectedIndex(0);
      setNameNO();
      dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI where NOB_TP_TIPID="+"'"+(String)jComboBox1.getSelectedItem()+"' and NOB_NOBRID ="+
                        "'"+(String)jComboBox2.getSelectedItem()+"' "+"order by NOBAVID ASC",true);
      jComboBox2.setSelectedIndex(0);
      setNameNOBAV();
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    if (!trans) {
      setNameNO();
      trans=true;
      dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI where NOB_TP_TIPID="+"'"+(String)jComboBox1.getSelectedItem()+"' and NOB_NOBRID ="+
                         "'"+(String)jComboBox2.getSelectedItem()+"' "+"order by NOBAVID ASC",true);
      jComboBox3.setSelectedIndex(0);
      trans=false;
      setNameNOBAV();
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e) {
    if(!trans)
      setNameNOBAV();
  }
}
