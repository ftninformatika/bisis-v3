
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

public class ExchangeOut extends EscapeDialog {
  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  JLabel label5 = new JLabel();
  JLabel label6 = new JLabel();
  JLabel label7 = new JLabel();
  JLabel label8 = new JLabel();
  JLabel label9 = new JLabel();
  JLabel label10 = new JLabel();
  JButton buttonControl1 = new JButton();
  JButton buttonControl2 = new JButton();
  JTextField textFieldControl1 = new JTextField();
  JTextField textFieldControl2 = new JTextField();
  JTextField textFieldControl3 = new JTextField();
  JTextField textFieldControl4 = new JTextField();
  JTextField textFieldControl5 = new JTextField();
  JTextField textFieldControl7 = new JTextField();
  JComboBox choiceControl2 = new JComboBox();
  JComboBox choiceControl3 = new JComboBox();
  JTextField jTextField1 = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField2 = new JTextField();

  public ExchangeOut(Frame frame, String title, boolean modal,DBConnection db) {
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


  public ExchangeOut() {
    this(CircApplet.parent, "", false,null);
  }

  public ExchangeOut(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    jLabel1.setText(Messages.get("EXCHANGEOUT_JLABEL1_TEXT"));
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    label1.setText(Messages.get("EXCHANGEOUT_LABEL1_TEXT"));
    label2.setText(Messages.get("EXCHANGEOUT_LABEL2_TEXT"));
    label3.setText(Messages.get("EXCHANGEOUT_LABEL3_TEXT"));
    label4.setText(Messages.get("EXCHANGEOUT_LABEL4_TEXT"));
    label5.setText(Messages.get("EXCHANGEOUT_LABEL5_TEXT"));
    label6.setText(Messages.get("EXCHANGEOUT_LABEL6_TEXT"));
    label7.setText(Messages.get("EXCHANGEOUT_LABEL7_TEXT"));
    label8.setText(Messages.get("EXCHANGEOUT_LABEL8_TEXT"));
    label9.setText(Messages.get("EXCHANGEOUT_LABEL9_TEXT"));
    label10.setText(Messages.get("EXCHANGEOUT_LABEL10_TEXT"));
    buttonControl1.setText(Messages.get("EXCHANGEOUT_BUTTONCONTROL1_TEXT"));
    buttonControl2.setText(Messages.get("EXCHANGEOUT_BUTTONCONTROL2_TEXT"));
/*    panel1.add(label1, new XYConstraints(30, 54, 69, -1));
    panel1.add(label2, new XYConstraints(40, 21, 59, -1));
    panel1.add(label3, new XYConstraints(56, 88, 43, -1));
    panel1.add(label4, new XYConstraints(67, 121, 32, -1));
    panel1.add(label5, new XYConstraints(293, 88, 71, -1));
    panel1.add(label6, new XYConstraints(334, 121, 30, -1));
    panel1.add(label7, new XYConstraints(14, 154, 85, -1));
    panel1.add(label8, new XYConstraints(313, 154, 51, -1));
    panel1.add(label9, new XYConstraints(33, 187, 66, -1));
    panel1.add(label10, new XYConstraints(219, 54, 145, -1));
    panel1.add(buttonControl1, new XYConstraints(131, 273, 75, 25));
    panel1.add(buttonControl2, new XYConstraints(298, 271, 75, 25));
    panel1.add(textFieldControl1, new XYConstraints(369, 85, 85, 20));
    panel1.add(textFieldControl2, new XYConstraints(98, 118, 155, 20));
    panel1.add(textFieldControl3, new XYConstraints(98, 85, 155, 20));
    panel1.add(textFieldControl4, new XYConstraints(98, 51, 85, 20));
    panel1.add(textFieldControl5, new XYConstraints(370, 151, 85, 20));
    panel1.add(textFieldControl7, new XYConstraints(369, 51, 85, 20));
    panel1.add(choiceControl2, new XYConstraints(369, 118, 100, 20));
    panel1.add(choiceControl3, new XYConstraints(98, 151, 100, 20));
    panel1.add(jTextField1, new XYConstraints(98, 18, 85, 20));
    panel1.add(jScrollPane1, new XYConstraints(32, 211, 456, 55));
    jScrollPane1.getViewport().add(jTextArea1, null);
    panel1.add(jLabel1, new XYConstraints(287, 21, -1, -1));
    panel1.add(jTextField2, new XYConstraints(354, 19, 100, -1));*/
    panel1.add(label1, new XYConstraints(21, 54, -1, -1));
    panel1.add(label2, new XYConstraints(21, 22, -1, -1));
    panel1.add(label3, new XYConstraints(21, 88, -1, -1));
    panel1.add(label4, new XYConstraints(21, 121, -1, -1));
    panel1.add(label5, new XYConstraints(295, 88, -1, -1));
    panel1.add(label6, new XYConstraints(295, 121, -1, -1));
    panel1.add(label7, new XYConstraints(21, 154, -1, -1));
    panel1.add(label8, new XYConstraints(295, 153, -1, -1));
    panel1.add(label9, new XYConstraints(21, 187, -1, -1));
    panel1.add(label10, new XYConstraints(295, 21, -1, -1));
    panel1.add(buttonControl1, new XYConstraints(131, 273, 75, 25));
    panel1.add(buttonControl2, new XYConstraints(298, 271, 75, 25));
    panel1.add(textFieldControl1, new XYConstraints(369, 85, 85, 20));
    panel1.add(textFieldControl2, new XYConstraints(120, 118, 155, 20));
    panel1.add(textFieldControl3, new XYConstraints(120, 85, 155, 20));
    panel1.add(textFieldControl4, new XYConstraints(120, 51, 85, 20));
    panel1.add(textFieldControl5, new XYConstraints(370, 151, 85, 20));
    panel1.add(textFieldControl7, new XYConstraints(369, 51, 85, 20));
    panel1.add(choiceControl2, new XYConstraints(369, 118, 100, 20));
    panel1.add(choiceControl3, new XYConstraints(120, 151, 100, 20));
    panel1.add(jTextField1, new XYConstraints(120, 18, 85, 20));
    panel1.add(jLabel1, new XYConstraints(295, 53, -1, -1));
    panel1.add(jTextField2, new XYConstraints(368, 19, 100, -1));
    panel1.add(jScrollPane1, new XYConstraints(28, 207, 455, 58));
    jScrollPane1.getViewport().add(jTextArea1, null);
  }
}

