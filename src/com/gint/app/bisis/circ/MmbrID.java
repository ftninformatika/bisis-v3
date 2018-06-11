package com.gint.app.bisis.circ;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MmbrID extends EscapeDialog{
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
  ActionListener alOK, alCancel, alFind;
  FlowLayout flowLayout2 = new FlowLayout();
  JButton btnFind = new JButton();
  FindMember findMember;

  public MmbrID(JFrame frame, String title, boolean modal, FindMember find) {
    super(frame, title, modal);
    findMember = find;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public MmbrID() {
    this(CircApplet.parent, "", false, null);
  }

  public MmbrID(String title, FindMember f, boolean modal){
    this(CircApplet.parent,title,modal, f);
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
    flowLayout1.setHgap(10);
    flowLayout1.setVgap(30);
    flowLayout2.setHgap(15);
    btnFind.setPreferredSize(new Dimension(75, 25));
    btnFind.setText(Messages.get("MMBRID_BTNFIND_TEXT"));
    btnFind.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFind_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("MMBRID_LABEL1_TEXT"));
    jTextField1.setPreferredSize(new Dimension(80, 20));
    jTextField1.setAlignmentY((float) 20.0);
    jTextField1.setMinimumSize(new Dimension(80, 20));
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);

    jPanel2.add(jLabel1, null);
    jPanel2.add(jTextField1, null);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnFind, null);

    panel1.add(jPanel2, BorderLayout.CENTER);
    panel1.add(jPanel1, BorderLayout.SOUTH);

    jTextField1.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          btnOK.doClick();
      }
    });

    alFind = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        jTextField1.setText(DBConnection.fromDBMember(findMember.getValue()));
        findMember.clear();  // Tanja, 10.11.00
        findMember.setVisible(false);
      }
    };

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed){
          findMember.addOKListener(alFind);
        }
        jTextField1.requestFocus(); //Tanja
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        findMember.removeOKListener();
        closed = true;
        clear();
        setVisible(false);
      }
    });
//    jTextField1.requestFocus();
  }

  public void addOKListener(ActionListener act){
    alOK = act;
    btnOK.addActionListener(alOK);
  }

  public void removeOKListener(){
    btnOK.removeActionListener(alOK);
  }

  public void addCancelListener(ActionListener act){
    alCancel = act;
    btnCancel.addActionListener(alCancel);
  }

  public void removeCancelListener(){
    btnCancel.removeActionListener(alCancel);
  }

  public String getValue(){
    return DBConnection.toDBMember(jTextField1.getText());
  }

  public void clear(){
    jTextField1.setText("");
  }

  void btnFind_actionPerformed(ActionEvent e) {
    findMember.setVisible(true);
  }
/*
  public void setVisible(boolean vis) {
    if (vis)
      jTextField1.requestFocus();
    super.setVisible(vis);
  }*/
}
