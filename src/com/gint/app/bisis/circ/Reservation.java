//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

public class Reservation extends EscapeDialog {
  DBConnection dbConn;
//  DBTableModel dbtm1;
//  DBTableModel dbtm2;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel label1 = new JLabel();
  JLabel labelControl1 = new JLabel();
  JCheckBox checkbox1 = new JCheckBox();
  JTable gridControl1;// = new JTable();
  JScrollPane sp1;
  JTextField jTextField1 = new JTextField();
  JScrollPane sp2;
  JTable jTable1;
  JButton buttonCIzbrisi = new JButton();
  JButton buttonCSledeci = new JButton();
  JButton buttonCSacuvaj = new JButton();
  JButton buttonCKraj = new JButton();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField2 = new JTextField();


  public Reservation(Frame frame, String title, boolean modal,DBConnection db) {
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

  public Reservation() {
    this(CircApplet.parent, "", false,null);
  }

  public Reservation(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
//    dbConn.executeQuery("select pub_inventbr from rezervisanja");
//    dbtm1 = new DBTableModel(dbConn.getConnection(),"inventbr, autor, naslov","fake_publ",new String[] {"sifra"});
//    dbtm2 = new DBTableModel(dbConn.getConnection(),"inventbr, autor, naslov,period","fake_publ",new String[] {"sifra"});
    gridControl1 = new JTable();
    jTable1 = new JTable();
    sp1 = new JScrollPane(gridControl1);
    sp2 =new JScrollPane(jTable1);

    xYLayout1.setWidth(430);
    xYLayout1.setHeight(366);
    buttonCIzbrisi.setText(Messages.get("RESERVATION_BUTTONCIZBRISI_TEXT"));
    buttonCSledeci.setText(Messages.get("RESERVATION_BUTTONCSLEDECI_TEXT"));
    buttonCSacuvaj.setText(Messages.get("RESERVATION_BUTTONCSACUVAJ_TEXT"));
    buttonCKraj.setText(Messages.get("RESERVATION_BUTTONCKRAJ_TEXT"));
    jLabel1.setText(Messages.get("RESERVATION_JLABEL1_TEXT"));
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    label1.setText(Messages.get("RESERVATION_LABEL1_TEXT"));
    checkbox1.setText(Messages.get("RESERATION_CHECKBOX1_TEXT"));
    labelControl1.setText(Messages.get("RESERATION_LABELCONTROL1_TEXT"));

    panel1.add(label1, new XYConstraints(22, 11, -1, -1));
    panel1.add(checkbox1, new XYConstraints(23, 327, -1, -1));
    panel1.add(sp1, new XYConstraints(23, 217, 385, 105));
    panel1.add(labelControl1, new XYConstraints(24, 194, -1, -1));
    panel1.add(jTextField1, new XYConstraints(94, 9, 70, -1));
    panel1.add(sp2, new XYConstraints(23, 39, 385, 105));
    panel1.add(buttonCIzbrisi, new XYConstraints(226, 161, 80, 25));
    panel1.add(buttonCSledeci, new XYConstraints(124, 161, 80, 25));
    panel1.add(buttonCSacuvaj, new XYConstraints(23, 161, 80, -1));
    panel1.add(buttonCKraj, new XYConstraints(327, 161, 80, 25));
    panel1.add(jLabel1, new XYConstraints(205, 11, -1, -1));
    panel1.add(jTextField2, new XYConstraints(299, 9, 100, -1));

  }
}

