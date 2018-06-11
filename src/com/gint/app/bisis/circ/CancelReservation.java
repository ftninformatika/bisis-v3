package com.gint.app.bisis.circ;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

public class CancelReservation extends EscapeDialog {
  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JTable gridControl1;// = new JTable();
  JScrollPane sp1;
  JTextField textFieldControl1 = new JTextField();
  JTextField jTextField1 = new JTextField();
  JButton buttonCIzbrisi = new JButton();
  JButton buttonCSledeci = new JButton();
  JButton buttonCSacuvaj = new JButton();
  JButton buttonCKraj = new JButton();


   public CancelReservation(Frame frame, String title, boolean modal,DBConnection db) {
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


  public CancelReservation() {
    this(CircApplet.parent, "", false,null);
  }

  public CancelReservation(String title,DBConnection dbConn) {
    this(CircApplet.parent, title, false,dbConn);
  }

  void jbInit() throws Exception {
    sp1 = new JScrollPane(gridControl1);
    xYLayout1.setWidth(395);
    xYLayout1.setHeight(306);
    buttonCIzbrisi.setText(Messages.get("CANCELRESERVATION_BUTTONC_CLEAR"));
    buttonCSledeci.setText(Messages.get("CANCELRESERVATION_BUTTONC_NEXT"));
    buttonCSacuvaj.setText(Messages.get("CANCELRESERVATION_BUTTONC_KEEP"));
    buttonCKraj.setText(Messages.get("CANCELRESERVATION_BUTTONC_END"));
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    label1.setText(Messages.get("CANCELRESERVATION_LABEL1_TEXT"));
    label2.setText(Messages.get("CANCELRESERVATION_LABEL2_TEXT"));
    panel1.add(label1, new XYConstraints(10, 24, -1, -1));
    panel1.add(label2, new XYConstraints(173, 24, -1, -1));
    panel1.add(sp1, new XYConstraints(47, 83, 300, 166));
    panel1.add(textFieldControl1, new XYConstraints(285, 21, 80, 20));
    panel1.add(jTextField1, new XYConstraints(75, 21, 80, 20));
    panel1.add(buttonCIzbrisi, new XYConstraints(205, 264, 75, 25));
    panel1.add(buttonCSledeci, new XYConstraints(115, 264, 75, 25));
    panel1.add(buttonCSacuvaj, new XYConstraints(25, 265, 75, 25));
    panel1.add(buttonCKraj, new XYConstraints(295, 264, 75, 25));
  }
}

