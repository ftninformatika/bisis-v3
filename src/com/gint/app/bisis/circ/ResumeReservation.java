
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

public class ResumeReservation extends EscapeDialog {
  DBConnection dbConn;
//  DBTableModel dbtm1;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JTable gridControl1;// = new JTable();
  JScrollPane sp1;
  JTextField txtDatum = new JTextField();
  JTextField textFieldControl1 = new JTextField();
 JButton buttonCIzbrisi = new JButton();
 JButton buttonCSledeci = new JButton();
 JButton buttonCSacuvaj = new JButton();
 JButton buttonCKraj = new JButton();

  public ResumeReservation(Frame frame, String title, boolean modal,DBConnection db) {
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

  public ResumeReservation() {
    this(CircApplet.parent, "", false,null);
  }

  public ResumeReservation(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
//    dbConn.executeQuery("select pub_inventbr from rezervisanja");
//    dbtm1 = new DBTableModel(dbConn.getConnection(),"inventbr, autor, naslov, period","fake_publ",new String[] {"sifra"});
    gridControl1 = new JTable();
    sp1 = new JScrollPane(gridControl1);
    xYLayout1.setWidth(450);
    xYLayout1.setHeight(270);
  buttonCIzbrisi.setText(Messages.get("RESUMERESERVATION_BUTTONCIZBRISI_TEXT"));
  buttonCSledeci.setText(Messages.get("RESUMERESERVATION_BUTTONCSLEDECI_TEXT"));
  buttonCSacuvaj.setText(Messages.get("RESUMERESERVATION_BUTTONCSACUVAJ_TEXT"));
  buttonCKraj.setText(Messages.get("RESUMERESERVATION_BUTTONCKRAJ_TEXT"));
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    label1.setText(Messages.get("RESUMERESERVATION_LABEL1_TEXT"));
    label2.setText(Messages.get("RESUMERESERVATION_LABEL2_TEXT"));
    panel1.add(label1, new XYConstraints(30, 23, -1, -1));
    panel1.add(label2, new XYConstraints(222, 23, -1, -1));
    panel1.add(sp1, new XYConstraints(27, 56, 391, 155));
  panel1.add(buttonCIzbrisi, new XYConstraints(234, 225, 80, 25));
  panel1.add(buttonCSledeci, new XYConstraints(131, 225, 80, 25));
  panel1.add(buttonCSacuvaj, new XYConstraints(27, 226, 80, 25));
  panel1.add(buttonCKraj, new XYConstraints(338, 225, 80, 25));
    panel1.add(txtDatum, new XYConstraints(338, 21, 80, -1));
    panel1.add(textFieldControl1, new XYConstraints(103, 21, 80, -1));

  }
}
