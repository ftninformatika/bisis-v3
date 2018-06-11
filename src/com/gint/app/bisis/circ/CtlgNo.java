package com.gint.app.bisis.circ;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class CtlgNo extends EscapeDialog{
  boolean closed = true;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  FlowLayout flowLayout1 = new FlowLayout();
  ActionListener alOK;
  FlowLayout flowLayout2 = new FlowLayout();

  public CtlgNo(JFrame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public CtlgNo() {
    this(CircApplet.parent, "", false);
  }

  public CtlgNo(String title,boolean modal){
    this(CircApplet.parent,title,modal);
  }

  void jbInit() throws Exception {
    panel1.setMaximumSize(new Dimension(285, 115));
    panel1.setPreferredSize(new Dimension(285, 115));
    panel1.setMinimumSize(new Dimension(285, 115));
    panel1.setSize(new Dimension(285, 115));
    jPanel1.setLayout(flowLayout2);
    jPanel2.setLayout(flowLayout1);
    btnOK.setPreferredSize(new Dimension(75, 25));
    btnOK.setText(Messages.get("MMBRID_BTNOK_TEXT"));
    this.getRootPane().setDefaultButton(btnOK);
    btnCancel.setText(Messages.get("MMBRID_BTNCANCEL_TEXT"));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          btnCancel_actionPerformed(e);
        }
      });
    flowLayout1.setHgap(10);
    flowLayout1.setVgap(30);
    flowLayout2.setHgap(15);
   
    jLabel1.setText("Inventarni broj");
    jTextField1.setPreferredSize(new Dimension(80, 20));
    jTextField1.setAlignmentY((float) 20.0);
    jTextField1.setMinimumSize(new Dimension(80, 20));
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);

    jPanel2.add(jLabel1, null);
    jPanel2.add(jTextField1, null);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnCancel, null);

    panel1.add(jPanel2, BorderLayout.CENTER);
    panel1.add(jPanel1, BorderLayout.SOUTH);

    jTextField1.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          btnOK.doClick();
      }
    });

 
    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        jTextField1.requestFocus(); //Tanja
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        closed = true;
        clear();
        setVisible(false);
      }
    });
//    jTextField1.requestFocus();
  }

  public String getValue(){
    return DBConnection.toDBInventory(jTextField1.getText());
  }

  public void clear(){
    jTextField1.setText("");
  }
  
  
  
  void btnCancel_actionPerformed(ActionEvent e) {
  	closed = true;
    clear();
    setVisible(false);
  }
  
  public void addOKListener(ActionListener act){
    alOK = act;
    btnOK.addActionListener(alOK);
  }
  
  public void removeOKListener(){
    btnOK.removeActionListener(alOK);
  }

}
