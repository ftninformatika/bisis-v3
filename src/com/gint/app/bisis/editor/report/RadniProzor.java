package com.gint.app.bisis.editor.report;

import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

public class RadniProzor extends JFrame {
  JTextArea jTextArea1 = new JTextArea();
  XYLayout xYLayout1 = new XYLayout();
  JTextArea jTextArea2 = new JTextArea();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();

  public RadniProzor() {
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {

      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    this.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_RADNIPROZOR_TITLE"));
    this.getContentPane().setLayout(xYLayout1);
    this.setSize(2500,600);
    jTextArea1.setLineWrap(true);
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setAutoscrolls(true);
    xYLayout1.setHeight(752);
    xYLayout1.setWidth(523);
    jLabel1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_RADNIPROZOR_LABELRESULT"));
    jLabel2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_RADNIPROZOR_LABELRECORD"));
    jTextArea2.setLineWrap(true);
    jTextArea2.setWrapStyleWord(true);
    jTextArea2.setAutoscrolls(true);
    this.getContentPane().add(jLabel2, new XYConstraints(3, 2, 107, -1));
//    this.getContentPane().add(jTextArea2, new XYConstraints(25, 341, 434, 317));
    jScrollPane2.getViewport().add(jTextArea2, null);
    jScrollPane2.getViewport().setView(jTextArea2);
    this.getContentPane().add(jScrollPane2, new XYConstraints(10, 341, 434, 300));
   // this.getContentPane().add(jTextArea1, new XYConstraints(11, 20, 459, 279));
    jScrollPane1.getViewport().add(jTextArea1, null);
    jScrollPane1.getViewport().setView(jTextArea1);
    this.getContentPane().add(jScrollPane1, new XYConstraints(10, 20, 434, 250));
    this.getContentPane().add(jLabel1, new XYConstraints(10, 284, -1, -1));
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }


}

