package com.gint.app.bisis.circ;

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

//package C;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

public class ExchangeAdmins extends EscapeDialog {
  DBConnection dbConn;
//  DBTableModel dbtm1, dbtm2;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTabbedPane tabsetPanel1 = new JTabbedPane();
  JPanel bevelPanel1 = new JPanel();
  JPanel bevelPanel2 = new JPanel();
  JTable gridControl1;// = new JTable();
  JTable gridControl2 ;//= new JTable();
  JButton buttonCSacuvaj = new JButton();
  JButton buttonCKraj = new JButton();
  JButton buttonCIzbrisi = new JButton();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();
  JTextField jTextField5 = new JTextField();
  JTextField jTextField6 = new JTextField();
  JTextField jTextField7 = new JTextField();
  JTextField jTextField8 = new JTextField();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JLabel jLabel15 = new JLabel();
  JLabel jLabel16 = new JLabel();
  JTextField jTextField9 = new JTextField();
  JTextField jTextField10 = new JTextField();
  JTextField jTextField11 = new JTextField();
  JTextField jTextField12 = new JTextField();
  JTextField jTextField13 = new JTextField();
  JTextField jTextField14 = new JTextField();
  JTextField jTextField15 = new JTextField();
  JTextField jTextField16 = new JTextField();
 JButton jButton4 = new JButton();
 JButton jButton5 = new JButton();
 JButton jButton6 = new JButton();
 JButton jButton7 = new JButton();
 JButton buttonControl1 = new JButton();

  
  public ExchangeAdmins(Frame frame, String title, boolean modal,DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ExchangeAdmins() {
    this(CircApplet.parent, "", false,null);
  }

  public ExchangeAdmins(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
//    dbConn.executeQuery("select * from primljeni_zahtevi");
//    dbtm1 = new DBTableModel(dbConn.getConnection(),"primljeni_zahtevi",new String[] {"sifra"});
    gridControl1 = new JTable();

//    dbConn.executeQuery("select * from izdati_zahtevi");
//    dbtm2 = new DBTableModel(dbConn.getConnection(),"izdati_zahtevi",new String[] {"sifra"});
    gridControl2 = new JTable();

    bevelPanel1.setLayout(xYLayout2);
    bevelPanel2.setLayout(xYLayout3);
    jLabel1.setText(Messages.get("EXCHANGEADMINS_LABEL1_TEXT"));
    jLabel2.setText(Messages.get("EXCHANGEADMINS_LABEL2_TEXT"));
    jLabel3.setText(Messages.get("EXCHANGEADMINS_LABEL3_TEXT"));
    jLabel4.setText(Messages.get("EXCHANGEADMINS_LABEL4_TEXT"));
    jLabel5.setText(Messages.get("EXCHANGEADMINS_LABEL5_TEXT"));
    jLabel6.setText(Messages.get("EXCHANGEADMINS_LABEL6_TEXT"));
    jLabel7.setText(Messages.get("EXCHANGEADMINS_LABEL7_TEXT"));
    jLabel8.setText(Messages.get("EXCHANGEADMINS_LABEL8_TEXT"));
    jLabel9.setText(Messages.get("EXCHANGEADMINS_LABEL9_TEXT"));
    jLabel10.setText(Messages.get("EXCHANGEADMINS_LABEL10_TEXT"));
    jLabel11.setText(Messages.get("EXCHANGEADMINS_LABEL11_TEXT"));
    jLabel12.setText(Messages.get("EXCHANGEADMINS_LABEL12_TEXT"));
    jLabel13.setText(Messages.get("EXCHANGEADMINS_LABEL13_TEXT"));
    jLabel14.setText(Messages.get("EXCHANGEADMINS_LABEL14_TEXT"));
    jLabel15.setText(Messages.get("EXCHANGEADMINS_LABEL15_TEXT"));
    jLabel16.setText(Messages.get("EXCHANGEADMINS_LABEL16_TEXT"));
    jButton4.setText(">>");
    jButton4.setText(">>");
    jButton4.setToolTipText(Messages.get("EXCHANGEADMINS_BUTTON4_TOOLTIP"));
    jButton5.setToolTipText(Messages.get("EXCHANGEADMINS_BUTTON5_TOOLTIP"));
    jButton6.setToolTipText(Messages.get("EXCHANGEADMINS_BUTTON6_TOOLTIP"));
    jButton7.setToolTipText(Messages.get("EXCHANGEADMINS_BUTTON7_TOOLTIP"));
    buttonControl1.setText(Messages.get("EXCHANGEADMINS_BUTTONCONTROL1_TEXT"));
    jButton7.setText(">>|");
    jButton6.setText("|<<");
    jButton6.setText("|<<");
    jButton5.setText("<<");
    getContentPane().add(panel1);
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(400);
    buttonCSacuvaj.setText(Messages.get("EXCHANGEADMINS_BUTTONCSACUVAJ_TEXT"));
    buttonCKraj.setText(Messages.get("EXCHANGEADMINS_BUTTONCKRAJ_TEXT"));
    buttonCIzbrisi.setText(Messages.get("EXCHANGEADMINS_BUTTONCIZBRISI_TEXT"));
    panel1.setLayout(xYLayout1);
    tabsetPanel1.addTab(Messages.get("EXCHANGEADMINS_TABSETPANEL1_TAB1"),bevelPanel1);
    bevelPanel1.add(jLabel1, new XYConstraints(32, 30, -1, -1));
    bevelPanel1.add(jLabel2, new XYConstraints(32, 74, -1, -1));
    bevelPanel1.add(jLabel3, new XYConstraints(258, 74, -1, -1));
    bevelPanel1.add(jLabel4, new XYConstraints(32, 118, -1, -1));
    bevelPanel1.add(jLabel5, new XYConstraints(32, 161, -1, -1));
    bevelPanel1.add(jLabel6, new XYConstraints(32, 205, -1, -1));
    bevelPanel1.add(jLabel7, new XYConstraints(258, 205, -1, -1));
    bevelPanel1.add(jLabel8, new XYConstraints(258, 118, -1, -1));
    bevelPanel1.add(jTextField1, new XYConstraints(130, 28, 80, -1));
    bevelPanel1.add(jTextField2, new XYConstraints(130, 72, 80, -1));
    bevelPanel1.add(jTextField3, new XYConstraints(130, 116, 100, -1));
    bevelPanel1.add(jTextField4, new XYConstraints(130, 159, 100, -1));
    bevelPanel1.add(jTextField5, new XYConstraints(130, 203, 80, -1));
    bevelPanel1.add(jTextField6, new XYConstraints(347, 116, 100, -1));
    bevelPanel1.add(jTextField7, new XYConstraints(347, 72, 100, -1));
    bevelPanel1.add(jTextField8, new XYConstraints(347, 203, 80, -1));
    tabsetPanel1.addTab(Messages.get("EXCHANGEADMINS_TABSETPANEL1_TAB2"), bevelPanel2);
    bevelPanel2.add(jLabel9,  new XYConstraints(32, 30, -1, -1));
    bevelPanel2.add(jLabel10, new XYConstraints(32, 74, -1, -1));
    bevelPanel2.add(jLabel11, new XYConstraints(258, 74, -1, -1));
    bevelPanel2.add(jLabel12, new XYConstraints(32, 118, -1, -1));
    bevelPanel2.add(jLabel13, new XYConstraints(32, 161, -1, -1));
    bevelPanel2.add(jLabel14, new XYConstraints(32, 205, -1, -1));
    bevelPanel2.add(jLabel15, new XYConstraints(258, 205, -1, -1));
    bevelPanel2.add(jLabel16, new XYConstraints(258, 118, -1, -1));
    bevelPanel2.add(jTextField9, new XYConstraints  (130, 28, 80, -1));
    bevelPanel2.add(jTextField10, new XYConstraints(130, 72, 80, -1));
    bevelPanel2.add(jTextField11, new XYConstraints(130, 116, 100, -1));
    bevelPanel2.add(jTextField12, new XYConstraints(130, 159, 100, -1));
    bevelPanel2.add(jTextField13, new XYConstraints(130, 203, 80, -1));
    bevelPanel2.add(jTextField14, new XYConstraints(347, 116, 100, -1));
    bevelPanel2.add(jTextField15, new XYConstraints(347, 72, 100, -1));
    bevelPanel2.add(jTextField16, new XYConstraints(347, 203, 80, -1));
  panel1.add(buttonCKraj, new XYConstraints(406, 356, 80, -1));
    panel1.add(buttonCIzbrisi, new XYConstraints(237, 356, 80, -1));
  panel1.add(jButton4, new XYConstraints(67, 367, 51, 25));
    panel1.add(buttonCSacuvaj, new XYConstraints(322, 356, 80, -1));
//    tabsetPanel1.setSelectedIndex(0);
  panel1.add(tabsetPanel1, new XYConstraints(10, 10, 480, 324));
  panel1.add(jButton5, new XYConstraints(15, 367, 51, 25));
  panel1.add(jButton6, new XYConstraints(15, 338, 51, 25));
  panel1.add(jButton7, new XYConstraints(67, 338, 51, 25));
    panel1.add(buttonControl1, new XYConstraints(153, 356, 80, -1));
  }
}

