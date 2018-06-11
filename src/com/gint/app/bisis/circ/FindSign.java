package com.gint.app.bisis.circ;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;


public class FindSign extends EscapeDialog{
  DBConnection db_conn;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jSign = new JLabel();
  ActionListener alCancel;

  public FindSign(JFrame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    db_conn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public FindSign() {
    this(CircApplet.parent, "", false, null);
  }

  public FindSign(String title, DBConnection db, boolean modal){
    this(CircApplet.parent,title,modal, db);
  }

  void jbInit() throws Exception {
    panel1.setMaximumSize(new Dimension(285, 115));
    panel1.setPreferredSize(new Dimension(285, 115));
    panel1.setMinimumSize(new Dimension(285, 115));
    panel1.setSize(new Dimension(285, 124));
    jPanel1.setLayout(xYLayout1);
    jPanel2.setLayout(xYLayout2);
    btnOK.setPreferredSize(new Dimension(85, 25));
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    this.getRootPane().setDefaultButton(btnOK);
    btnOK.setText(Messages.get("FINDSIGN_BTNOK_TEXT"));
    btnCancel.setText(Messages.get("FINDSIGN_BTNCANCEL_TEXT"));
    btnCancel.setPreferredSize(new Dimension(80, 25));
    jLabel2.setText(Messages.get("FINDSIGN_JLABEL2_TEXT"));
    jSign.setText("");
    jSign.setForeground(Color.blue);
    jLabel1.setText(Messages.get("FINDSIGN_JLABEL1_TEXT"));
    jTextField1.setPreferredSize(new Dimension(80, 20));
    jTextField1.setAlignmentY((float) 20.0);
    jTextField1.setMinimumSize(new Dimension(80, 20));
    jTextField1.requestDefaultFocus();
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnOK, new XYConstraints(50, 5, -1, -1));
    jPanel1.add(btnCancel, new XYConstraints(150, 5, -1, -1));
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1, new XYConstraints(85, 21, -1, -1));
    jPanel2.add(jTextField1, new XYConstraints(112, 20, -1, -1));
    jPanel2.add(jLabel2, new XYConstraints(48, 55, -1, -1));
    jPanel2.add(jSign, new XYConstraints(115, 53, 75, -1));
  }

  public String getValue(){
    return jSign.getText();
  }

  public void clear(){
    jTextField1.setText("");
  }


  boolean checkID () {
   for (int i=0;i<jTextField1.getText().length();i++) {
      if (!Character.isDigit(jTextField1.getText().charAt(i))) {
        JOptionPane.showMessageDialog(null,Messages.get("FINDSIGN_OPTIONPANE_MSG1"),Messages.get("FINDSIGN_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
        jSign.setText("                    ");
        return false;
      }
    }

    if (jTextField1.getText().equals("")) {
      JOptionPane.showMessageDialog(null,Messages.get("FINDSIGN_OPTIONPANE_MSG3"),Messages.get("FINDSIGN_OPTIONPANE_MSG4"),JOptionPane.ERROR_MESSAGE);
      jSign.setText("                    ");
      return false;
    }

    if (jTextField1.getText().length() > 20) {
      JOptionPane.showMessageDialog(null,Messages.get("FINDSIGN_OPTIONPANE_MSG5"),Messages.get("FINDSIGN_OPTIONPANE_MSG6"),JOptionPane.ERROR_MESSAGE);
      jSign.setText("                    ");
      return false;
    }
    return true;
  }

  void btnOK_actionPerformed(ActionEvent e) {
    if (checkID())  {
      try{
        Statement stmt = db_conn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery("select sig from circ_docs where docid = "+jTextField1.getText());
        if(rset.next()){
          jSign.setText(rset.getString(1));
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("FINDSIGN_OPTIONPANE_MSG7"),Messages.get("FINDSIGN_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
          jSign.setText("                    ");
        }
        rset.close();
        db_conn.getConnection().commit();
        stmt.close();
      }catch (SQLException ex){
        ex.printStackTrace();
      }
    }
  }


  public void addCancelListener(ActionListener act){
    alCancel = act;
    btnCancel.addActionListener(alCancel);
  }

  public void removeCancelListener(){
    btnCancel.removeActionListener(alCancel);
  }

}
