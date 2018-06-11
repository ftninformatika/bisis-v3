package com.gint.app.bisis.editor.report;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

public class BrojZ extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel1 = new JLabel();
  JButton jButton1 = new JButton();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField2 = new JTextField();

  public BrojZ(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public BrojZ() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    jLabel1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BROJZ_LABELRECNO"));
    jButton1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BROJZ_BUTTONOK"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jLabel2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BROJZ_LABELCARD"));
    getContentPane().add(panel1);
    panel1.add(jButton1, new XYConstraints(37, 124, 102, 29));
    panel1.add(jTextField1, new XYConstraints(89, 84, 87, 23));
    panel1.add(jLabel1, new XYConstraints(16, 88, 96, 23));
    panel1.add(jLabel2, new XYConstraints(18, 41, 68, 20));
    panel1.add(jTextField2, new XYConstraints(87, 41, 89, 23));
  }

  public int returnNumber()  {
     Integer integer = new Integer(jTextField1.getText());
     return integer.intValue();
  }

  public String returnListic()  {
     return jTextField2.getText();
  }


  void jButton1_actionPerformed(ActionEvent e) {
      dispose();
  }
}

