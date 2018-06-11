
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class Potpotpolja extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel2 = new JPanel();
  Border border3;
  TitledBorder titledBorder2;
  Border border4;
  JPanel jPanel8 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JComboBox jComboBox5 = new JComboBox();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel12 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JCheckBox jCheckBox1 = new JCheckBox();
  JCheckBox jCheckBox2 = new JCheckBox();
  JPanel jPanel10 = new JPanel();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextArea jTextArea1 = new JTextArea();
  JScrollPane jScrollPane3 = new JScrollPane();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  Border border5;
  TitledBorder titledBorder3;
  Border border6;
  JButton jButton10 = new JButton();
  JButton jButton11 = new JButton();
  JButton jButton12 = new JButton();
  JButton jButton13 = new JButton();
  JButton jButton14 = new JButton();
  JButton jButton15 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JButton jButton8 = new JButton();
  JButton jButton9 = new JButton();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  Border border7;
  Border border8;
  TitledBorder titledBorder4;
  Border border9;

  Connection conn;
  DBMPPPolje dbmPPP;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;

  int mod = UNDEFINED;
  int row = 0;
  boolean trans = false;
  String oldField = "";
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  XYLayout xYLayout8 = new XYLayout();
  XYLayout xYLayout9 = new XYLayout();
  XYLayout xYLayout10 = new XYLayout();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel7 = new JLabel();
  boolean isEmpty=false;
  boolean first=false;

  public Potpotpolja(Connection conn) {
    try  {
      this.conn=conn;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,Messages.get("POTPOTPOLJA_TITLEDBORDER1_FIELD"));
    border2 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),Messages.get("POTPOTPOLJA_LINEBORDER_FIELDS")),BorderFactory.createEmptyBorder(3,3,3,3));
    border3 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border3,Messages.get("POTPOTPOLJA_TITLEDBORDER2_SUBFIELD"));
    border4 = BorderFactory.createCompoundBorder(titledBorder2,BorderFactory.createEmptyBorder(3,3,3,3));
    border5 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    titledBorder3 = new TitledBorder(border5,Messages.get("POTPOTPOLJA_TITLEDBORDER3_SUBSUBFIELD"));
    border6 = BorderFactory.createCompoundBorder(titledBorder3,BorderFactory.createEmptyBorder(3,3,3,3));
    border7 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border8 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder4 = new TitledBorder(border8,Messages.get("POTPOTPOLJA_TITLEDBORDER4_SUBSUBFIELD"));
    border9 = BorderFactory.createCompoundBorder(titledBorder4,BorderFactory.createEmptyBorder(3,3,3,3));
    jLabel1.setText(Messages.get("POTPOTPOLJA_LABEL1_CODE"));
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("POTPOTPOLJA_DIALOGTITLE_SUBSUBFIELDS"));
    this.getContentPane().add(jPanel4);
    jPanel4.setLayout(xYLayout1);
    jPanel1.setLayout(xYLayout2);
    jPanel1.setBorder(border2);
    jPanel1.setMinimumSize(new Dimension(660, 80));
    jPanel1.setPreferredSize(new Dimension(660, 80));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jLabel3.setText(Messages.get("POTPOTPOLJA_LABEL3_CODE"));
    jPanel2.setLayout(xYLayout3);
    jPanel2.setBorder(border4);
    jPanel2.setMinimumSize(new Dimension(666, 80));
    jPanel2.setPreferredSize(new Dimension(666, 80));
    jPanel8.setPreferredSize(new Dimension(457, 65));
    jPanel8.setLayout(xYLayout6);
    jPanel9.setLayout(xYLayout5);
    jComboBox5.setMinimumSize(new Dimension(40, 21));
    jComboBox5.setPreferredSize(new Dimension(40, 21));
    jComboBox4.setMinimumSize(new Dimension(45, 21));
    jComboBox4.setPreferredSize(new Dimension(45, 21));
    jLabel12.setText(Messages.get("POTPOTPOLJA_LABEL12_CODE"));
    jLabel5.setText(Messages.get("POTPOTPOLJA_LABEL_NAME"));
    jLabel6.setText(Messages.get("POTPOTPOLJA_LABEL_STATUS"));
    jLabel8.setText(Messages.get("POTPOTPOLJA_LABEL_CONTROLTYPE"));
    jCheckBox1.setText(Messages.get("POTPOTPOLJA_CHECKBOX_REPEATED"));
    jCheckBox2.setText(Messages.get("POTPOTPOLJA_CHECKBOX_MANDATORY"));
    jPanel10.setLayout(xYLayout7);
    jTextField1.setMinimumSize(new Dimension(21, 21));
    jTextField1.setPreferredSize(new Dimension(21, 21));
    jTextField1.setMargin(new Insets(0, 2, 0, 2));
    jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
    jTextField2.setMinimumSize(new Dimension(180, 21));
    jTextField2.setPreferredSize(new Dimension(175, 21));
    jTextField2.setMargin(new Insets(0, 2, 0, 2));
    jTextField3.setMinimumSize(new Dimension(25, 21));
    jTextField3.setPreferredSize(new Dimension(25, 21));
    jTextField3.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setPreferredSize(new Dimension(250, 119));
    jTextArea1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setMinimumSize(new Dimension(250, 119));
    jScrollPane3.setPreferredSize(new Dimension(260, 70));
    jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane3.setMinimumSize(new Dimension(260, 70));
    jLabel10.setText(Messages.get("POTPOTPOLJA_LABEL_LENGTH"));
    jLabel11.setText(Messages.get("POTPOTPOLJA_LABEL_DEFAULTVALUE"));
    jPanel3.setBorder(border9);
    jPanel3.setMinimumSize(new Dimension(666, 210));
    jPanel3.setPreferredSize(new Dimension(666, 210));
    jPanel3.setLayout(xYLayout4);
    jButton10.setText(Messages.get("POTPOTPOLJA_BUTTON_INPUT"));
    jButton10.setPreferredSize(new Dimension(80, 21));
    jButton10.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton11.setText(Messages.get("POTPOTPOLJA_BUTTON_CHANGE"));
    jButton11.setPreferredSize(new Dimension(80, 21));
    jButton11.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton11_actionPerformed(e);
      }
    });
    jButton12.setText(Messages.get("POTPOTPOLJA_BUTTON_CLEAR"));
    jButton12.setPreferredSize(new Dimension(80, 21));
    jButton12.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton12_actionPerformed(e);
      }
    });
    jButton13.setText(Messages.get("POTPOTPOLJA_BUTTON_OK"));
    jButton13.setPreferredSize(new Dimension(80, 21));
    jButton13.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton13_actionPerformed(e);
      }
    });
    jButton14.setText(Messages.get("POTPOTPOLJA_BUTTON_CANCEL"));
    jButton14.setPreferredSize(new Dimension(80, 21));
    jButton14.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton14_actionPerformed(e);
      }
    });
    jButton15.setText(Messages.get("POTPOTPOLJA_BUTTON_EXIT"));
    jButton15.setPreferredSize(new Dimension(80, 21));
    jButton15.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton15_actionPerformed(e);
      }
    });
    jButton4.setText(Messages.get("POTPOTPOLJA_BUTTON_FIRST"));
    jButton4.setPreferredSize(new Dimension(80, 21));
    jButton4.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setText(Messages.get("POTPOTPOLJA_BUTTON_PREVIOUS"));
    jButton5.setPreferredSize(new Dimension(80, 21));
    jButton5.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setText(Messages.get("POTPOTPOLJA_BUTTON_NEXT"));
    jButton6.setPreferredSize(new Dimension(80, 21));
    jButton6.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setText(Messages.get("POTPOTPOLJA_BUTTON_LAST"));
    jButton7.setPreferredSize(new Dimension(80, 21));
    jButton7.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setText(Messages.get("POTPOTPOLJA_BUTTON_VIEW"));
    jButton8.setPreferredSize(new Dimension(80, 21));
    jButton8.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jButton9.setText(Messages.get("POTPOTPOLJA_BUTTON_HELP"));
    jButton9.setPreferredSize(new Dimension(80, 21));
    jButton9.setFont(new java.awt.Font("Dialog", 0, 11));
  	this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel5.setLayout(xYLayout9);
    jPanel6.setLayout(xYLayout10);
    jPanel6.setBorder(border5);
    jPanel7.setLayout(xYLayout8);
    jPanel7.setBorder(border7);
    xYLayout1.setHeight(480);
    xYLayout1.setWidth(650);
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jLabel7.setForeground(new java.awt.Color(50, 54, 120));
    jPanel4.add(jPanel5, new XYConstraints(5, 400, -1, -1));
    jPanel5.add(jButton6, new XYConstraints(229, 3, -1, -1));
    jPanel5.add(jButton9, new XYConstraints(565, 3, -1, -1));
    jPanel5.add(jPanel7, new XYConstraints(337, 31, -1, -1));
    jPanel7.add(jButton14, new XYConstraints(114, 3, -1, -1));
    jPanel7.add(jButton13, new XYConstraints(2, 3, -1, -1));
    jPanel5.add(jButton15, new XYConstraints(565, 36, -1, -1));
    jPanel5.add(jButton7, new XYConstraints(341, 3, -1, -1));
    jPanel5.add(jButton5, new XYConstraints(117, 3, -1, -1));
    jPanel5.add(jButton8, new XYConstraints(453, 3, -1, -1));
    jPanel5.add(jButton4, new XYConstraints(5, 3, -1, -1));
    jPanel5.add(jPanel6, new XYConstraints(0, 30, -1, -1));
    jPanel6.add(jButton10, new XYConstraints(2, 3, -1, -1));
    jPanel6.add(jButton12, new XYConstraints(226, 3, -1, -1));
    jPanel6.add(jButton11, new XYConstraints(114, 3, -1, -1));
    jPanel4.add(jPanel1, new XYConstraints(7, 10, 500, -1));
    jPanel1.add(jLabel1, new XYConstraints(5, 2, -1, -1));
    jPanel1.add(jComboBox1, new XYConstraints(47, 0, -1, -1));
    jPanel1.add(jLabel4, new XYConstraints(150, 0, 320, -1));
    jPanel4.add(jPanel2, new XYConstraints(7, 90, 500, -1));
    jPanel2.add(jLabel3, new XYConstraints(5, 2, -1, -1));
    jPanel2.add(jComboBox2, new XYConstraints(47, 0, -1, -1));
    jPanel2.add(jLabel7, new XYConstraints(150, 0, 320, -1));
    jPanel4.add(jPanel3, new XYConstraints(7, 170, 630, -1));
    jPanel3.add(jPanel9, new XYConstraints(11, 5, -1, -1));
    jPanel9.add(jLabel12, new XYConstraints(5, 2, -1, -1));
    jPanel9.add(jLabel5, new XYConstraints(5, 31, -1, -1));
    jPanel9.add(jTextField1, new XYConstraints(47, 0, -1, -1));
    jPanel9.add(jScrollPane3, new XYConstraints(47, 31, -1, -1));
    jScrollPane3.getViewport().add(jTextArea1, null);
    jPanel9.add(jCheckBox2, new XYConstraints(337, 31, -1, -1));
    jPanel9.add(jCheckBox1, new XYConstraints(337, 68, -1, -1));
    jPanel3.add(jPanel8, new XYConstraints(270, 111, 340, 62));
    jPanel8.add(jLabel11, new XYConstraints(6, 38, -1, -1));
    jPanel8.add(jTextField2, new XYConstraints(158, 36, -1, -1));
    jPanel8.add(jTextField3, new XYConstraints(158, 5, -1, -1));
    jPanel8.add(jLabel10, new XYConstraints(6, 7, -1, -1));
    jPanel3.add(jPanel10, new XYConstraints(11, 111, -1, -1));
    jPanel10.add(jLabel6, new XYConstraints(5, 7, -1, -1));
    jPanel10.add(jLabel8, new XYConstraints(5, 38, -1, -1));
    jPanel10.add(jComboBox4, new XYConstraints(82, 36, -1, -1));
    jPanel10.add(jComboBox5, new XYConstraints(82, 5, -1, -1));
    this.setModal(true);

    trans=true;
    dbmPPP = new DBMPPPolje(conn);
    first=true;

    dbcm1 = new DBComboModel(conn,"select distinct pp.PO_POLJEID,p.PO_NAZIV from potpolja pp,polje p where pp.PO_POLJEID=p.POLJEID order by pp.PO_POLJEID",true);
    jComboBox1.setModel(dbcm1);
    if(dbmPPP.getRowCount()==0) {
      jComboBox1.setSelectedIndex(0);
      dbcm2 = new DBComboModel(conn,"select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID="+Integer.parseInt(((String)jComboBox1.getSelectedItem()).trim())
                               +" order by POTPOLJAID",true);
      oldField = ((String)jComboBox1.getSelectedItem()).trim();
    }
    else {
      jComboBox1.setSelectedItem((String)dbmPPP.getValueAt(row,0));
      dbcm2 = new DBComboModel(conn,"select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID="+Integer.parseInt(((String)dbmPPP.getValueAt(row,0)).trim())
                              +" order by POTPOLJAID",true);
      oldField = ((String)dbmPPP.getValueAt(row,0)).trim();
    }
    jComboBox2.setModel(dbcm2);
    first=false;
    dbcm3 = new DBComboModel(conn,"select distinct KONTRID from tip_kontrole order by KONTRID",false);
    jComboBox4.setModel(dbcm3);

    jComboBox5.addItem("a");
    jComboBox5.addItem("p");
    trans=false;

    if (dbmPPP.getRowCount() != 0){
      trans=true;
      setValues(row);
      jButton6.requestFocus();
      setComponentEnabled();
      trans=false;
    }
    else {
      mod = INSERT;
      setComponentEnabled();
      jComboBox1.setEnabled(true);
      jComboBox2.setEnabled(true);
      isEmpty=true;
    }
    setNameField();
    setNameSField();
    setButtonEnabled();
    //setComponentEnabled();
  }
  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jButton4.setEnabled(true);
                      jButton5.setEnabled(true);
                      jButton6.setEnabled(true);
                      jButton7.setEnabled(true);
                      jButton8.setEnabled(true);
                      jButton9.setEnabled(true);
                      jButton10.setEnabled(true);
                      jButton11.setEnabled(true);
                      jButton12.setEnabled(true);
                      jButton13.setEnabled(false);
                      jButton14.setEnabled(false);
                      jButton15.setEnabled(true);
                      break;

        case INSERT : jButton4.setEnabled(false);
                      jButton5.setEnabled(false);
                      jButton6.setEnabled(false);
                      jButton7.setEnabled(false);
                      jButton8.setEnabled(false);
                      jButton9.setEnabled(true);
                      jButton10.setEnabled(false);
                      jButton11.setEnabled(false);
                      jButton12.setEnabled(false);
                      jButton13.setEnabled(true);
                      jButton14.setEnabled(true);
                      jButton15.setEnabled(false);
                      break;

        case UPDATE : jButton4.setEnabled(false);
                      jButton5.setEnabled(false);
                      jButton6.setEnabled(false);
                      jButton7.setEnabled(false);
                      jButton8.setEnabled(false);
                      jButton9.setEnabled(true);
                      jButton10.setEnabled(false);
                      jButton11.setEnabled(false);
                      jButton12.setEnabled(false);
                      jButton13.setEnabled(true);
                      jButton14.setEnabled(true);
                      jButton15.setEnabled(false);
                      break;

        case DELETE : jButton4.setEnabled(false);
                      jButton5.setEnabled(false);
                      jButton6.setEnabled(false);
                      jButton7.setEnabled(false);
                      jButton8.setEnabled(false);
                      jButton9.setEnabled(true);
                      jButton10.setEnabled(false);
                      jButton11.setEnabled(false);
                      jButton12.setEnabled(false);
                      jButton13.setEnabled(true);
                      jButton14.setEnabled(true);
                      jButton15.setEnabled(false);
                      break;
    }
  }

  void setComponentEnabled() {
    switch(mod) {
      case UNDEFINED :jTextField1.setEnabled(false);
                      jTextArea1.setEnabled(false);
                      jTextField2.setEnabled(false);
                      jTextField3.setEnabled(false);
                      jCheckBox1.setEnabled(false);
                      jCheckBox2.setEnabled(false);
                      jComboBox1.setEnabled(true);
                      jComboBox2.setEnabled(true);
                      jComboBox5.setEnabled(false);
                      jComboBox4.setEnabled(false);
                      break;
      case INSERT : jTextField1.setEnabled(true);
                    jTextField2.setEnabled(true);
    		    jTextField3.setEnabled(true);
              	    jTextArea1.setEnabled(true);
              	    jTextField1.requestFocus();
              	    jTextField1.setText("");
              	    jTextField2.setText("");
              	    jTextField3.setText("");
              	    jTextArea1.setText("");
              	    jCheckBox1.setEnabled(true);
              	    jCheckBox2.setEnabled(true);
		    jComboBox1.setEnabled(false);
              	    jComboBox2.setEnabled(false);
              	    jComboBox5.setEnabled(true);
              	    jComboBox4.setEnabled(true);
              	    break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(true);
                    jTextArea1.requestFocus();
                    jTextField2.setEnabled(true);
                    jTextField3.setEnabled(true);
                    jCheckBox1.setEnabled(true);
                    jCheckBox2.setEnabled(true);
		    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox5.setEnabled(true);
                    jComboBox4.setEnabled(true);
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(false);
                    jTextField2.setEnabled(false);
                    jTextField3.setEnabled(false);
		    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox5.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
    }
  }

  void setValues(int row){
    jComboBox1.setSelectedItem((String)dbmPPP.getValueAt(row,0));
    jComboBox2.setSelectedItem((String)dbmPPP.getValueAt(row,1));
    jTextField1.setText((String)dbmPPP.getValueAt(row,2));
    jTextArea1.setText((String)dbmPPP.getValueAt(row,3));
    jTextField3.setText((String)dbmPPP.getValueAt(row,5));
    jTextField2.setText((String)dbmPPP.getValueAt(row,9));
    String ponov = ((String)dbmPPP.getValueAt(row,6)).trim();
    String obav = ((String)dbmPPP.getValueAt(row,7)).trim();
    jComboBox5.setSelectedItem(((String)dbmPPP.getValueAt(row,8)).trim());
    jComboBox4.setSelectedItem(((String)dbmPPP.getValueAt(row,4)).trim());
    if(Integer.parseInt(ponov) == 1) {
      jCheckBox1.setSelected(true);
    }
    else {
      jCheckBox1.setSelected(false);
    }
    if(Integer.parseInt(obav) == 1) {
      jCheckBox2.setSelected(true);
    }
    else {
      jCheckBox2.setSelected(false);
    }
  }

  void setNameField() {
    jLabel4.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameSField() {
    jLabel7.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }

  boolean checkValues() {
    if (mod == INSERT) {
      if(jTextField1.getText().length() > 1) {
        JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_ONECHARMANDATORY_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_ONECHARMANDATORY_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if(jTextField1.getText().length() == 0) {
        JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_MISSINGSUBSUBFIELD_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_MISSINGSUBSUBFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
    }
    if(jTextField2.getText().length() > 15) {
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_DEFAULTTENGTHEXCEEDED_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_DEFAULTTENGTHEXCEEDED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }

    if(jTextField3.getText().length() > 2) {
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_LENGTHTOOSMALL_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_LENGTHTOOSMALL_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextField3.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_NOLENGTH_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_NOLENGTH_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(!(Character.isDigit(jTextField3.getText().charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_FIRSTLENCHARMUSTBENUMERIC_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_FIRSTLENCHARMUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if (jTextField3.getText().length() == 2)
      if(!(Character.isDigit(jTextField3.getText().charAt(1)))) {
        JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_SECONDLENCHARMUSTBENUMERIC_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_SECONDLENCHARMUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
       	return false;
      }
    if(jTextArea1.getText().length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELDNAME_LENGTHEXCEEDED_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELDNAME_LENGTHEXCEEDED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextArea1.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELDNAME_NONAME_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELDNAME_NONAME_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void find(String fld,String sfld,String ssfld) {
    String value = fld+"|"+sfld+"|"+ssfld;
    int lengths = fld.length()+sfld.length()+ssfld.length()+2;
    int pos = dbmPPP.find(value,lengths);
    if (pos!=-1){
      row=pos;
      trans=true;
      setValues(row);
      trans=false;
    }
  }

  public String getField() {
    return (String)dbmPPP.getValueAt(row,0);
  }
  public String getSField() {
    return (String)dbmPPP.getValueAt(row,1);
  }
  public String getSSField() {
    return (String)dbmPPP.getValueAt(row,2);
  }
  void first() {
    trans=true;
    row=0;
    setValues(row);
    trans=false;
  }

  void last() {
    trans=true;
    row=dbmPPP.getRowCount()-1;
    setValues(row);
    trans=false;
  }

  void next() {
    if(row == dbmPPP.getRowCount()-1)
      return;
    else {
      trans=true;
      row = row+1;
      setValues(row);
      trans=false;
    }
  }

  void prev() {
    if(row == 0)
      return;
    else {
      trans=true;
      row = row-1;
      setValues(row);
      trans=false;
    }
  }

  void jButton4_actionPerformed(ActionEvent e)  {
    first();
  }

  void jButton5_actionPerformed(ActionEvent e)  {
    prev();
  }
  void jButton6_actionPerformed(ActionEvent e)  {
    next();
  }
  void jButton7_actionPerformed(ActionEvent e)  {
    last();
  }
  void jButton8_actionPerformed(ActionEvent e)  {
    PronadjiPPP ppp = new PronadjiPPP(conn,this);
    ppp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = ppp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    ppp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    ppp.setVisible(true);
  }
  void jButton10_actionPerformed(ActionEvent e)  {
    insert();
  }
  void insert() {
    mod = INSERT;
    jCheckBox1.setSelected(false);
    jCheckBox2.setSelected(false);
    jComboBox4.setSelectedIndex(0);
    jComboBox5.setSelectedIndex(0);
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton11_actionPerformed(ActionEvent e)  {
    update();
  }
  void update() {
    mod = UPDATE;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton12_actionPerformed(ActionEvent e)  {
    delete();
  }
  void delete() {
    if(dbmPPP.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_RECORD_LASTRECDELETED_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_RECORD_LASTRECDELETED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
  }
  void jButton13_actionPerformed(ActionEvent e)  {
    doAction();
  }
  void doAction() {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String sfld = ((String)jComboBox2.getSelectedItem()).trim();
    String ssfld = jTextField1.getText().trim();
    String naziv = jTextArea1.getText().trim();
    String kontr = ((String)jComboBox4.getSelectedItem()).trim();
    String status = ((String)jComboBox5.getSelectedItem()).trim();
    String duzina = jTextField3.getText().trim();
    String defVal = jTextField2.getText().trim();
    dbmPPP.setField(fld);
    dbmPPP.setSField(sfld);
    dbmPPP.setSSField(ssfld);
    dbmPPP.setNazivSSField(naziv);
    dbmPPP.setKontrola(kontr);
    dbmPPP.setStatus(status);
    dbmPPP.setDuzina(duzina);
    dbmPPP.setDefVal(defVal);
    if(jCheckBox1.isSelected())
      dbmPPP.setPonov("1");
    else
      dbmPPP.setPonov("0");
    if(jCheckBox2.isSelected())
      dbmPPP.setObav("1");
    else
      dbmPPP.setObav("0");

    if (mod == INSERT) {
      int pos = dbmPPP.findPos(fld,sfld,ssfld);
      if (checkValues()&& pos!=-1) {
	row = pos;
	dbmPPP.setTo(row,mod);
        dbmPPP.saveToDB(mod,row);
        if(isEmpty)
          isEmpty=false;
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
        jButton6.requestFocus();
      }
      else {
      	if(pos == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_EXISTANCEINDATABASE_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_SUBSUBFIELD_EXISTANCEINDATABASE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	}
      }
    }
    else if (mod == UPDATE) {
      if (checkValues()) {
        dbmPPP.setTo(row,mod);
        dbmPPP.saveToDB(mod,row);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
    else if (mod == DELETE) {
      dbmPPP.removeRow(row);
      dbmPPP.saveToDB(mod,row);
      if (row == dbmPPP.getRowCount()) {
        row=row-1;
      }
      if(dbmPPP.getRowCount() == 0) {
        dispose();
      }
      else {
        trans=true;
      	setValues(row);
      	trans=false;
      	mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
  }
  void jButton14_actionPerformed(ActionEvent e)  {
    doCancel();
  }
  void doCancel() {
    if(!isEmpty) {
      if (mod==INSERT || mod==UPDATE) {
        trans=true;
        setValues(row);
        trans=false;
      }
      mod = UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      dispose();
    }
  }

  void jButton15_actionPerformed(ActionEvent e)  {
    dispose();
  }

  void jComboBox1_actionPerformed(ActionEvent e)  {
    setNameField();
    if(!trans) { //kada selektuje vrednost iz ComboBox-a
      trans=true;
      String fi = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID='"+fi+"' order by POTPOLJAID",true);
      int i = dbmPPP.find(fi,fi.length());
      if(i != -1) {
        row = i;
        setValues(row);
        jButton6.requestFocus();
      }
      else {
        mod = INSERT;
        jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);
        jComboBox2.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        setComponentEnabled();
        jComboBox2.setEnabled(true);
        setButtonEnabled();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldField.equals(((String)jComboBox1.getSelectedItem()).trim())) {
        int fld = Integer.parseInt(((String)jComboBox1.getSelectedItem()).trim());
        dbcm2.reloadItems("select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID="+fld+" order by POTPOLJAID",true);
        //}
      }
    }
    //oldField = ((String)jComboBox1.getSelectedItem()).trim();
  }

  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(jComboBox2.getSelectedIndex() == -1)
      jComboBox2.setSelectedIndex(0);
    setNameSField();
    if(!trans) {
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      String sfld = ((String)jComboBox2.getSelectedItem()).trim();
      int i = dbmPPP.find(fld+"|"+sfld,fld.length()+sfld.length()+1);
      if(i != -1) {
        row = i;
        setValues(row);
        jButton6.requestFocus();
      }
      else {
        mod = INSERT;
      	jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        setComponentEnabled();
       	setButtonEnabled();
      }
      trans=false;
    }
  }
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN:if(mod == UNDEFINED) {
	                          if(dbmPPP.getRowCount() !=0) {
				    next();
				  }
				 }
				 break;
      case KeyEvent.VK_PAGE_UP:if(mod == UNDEFINED) {
	                        if(dbmPPP.getRowCount() !=0) {
				  prev();
				}
			       }
			       break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
	                      if(dbmPPP.getRowCount() !=0) {
				first();
			      }
			    }
			    break;
      case KeyEvent.VK_END:if(mod == UNDEFINED) {
	                    if(dbmPPP.getRowCount() !=0) {
			      last();
			    }
			   }
			   break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
	                    if(dbmPPP.getRowCount() !=0) {
			      insert();
			    }
			  }
			  break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
	                    if(dbmPPP.getRowCount() !=0) {
			      update();
			    }
			  }
			  break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
	                    if(dbmPPP.getRowCount() !=0) {
			      delete();
			    }
			  }
			  break;
      case KeyEvent.VK_F5:if(mod==INSERT || mod==DELETE || mod==UPDATE) {
	                    doAction();
			  }
			  break;
      case KeyEvent.VK_F6:if(mod==INSERT || mod==DELETE || mod==UPDATE) {
	                    doCancel();
			  }
			  break;
      default:
    }
  }
  protected void processWindowEvent(WindowEvent e){   // ZASTITA OD X-ICA U TOKU NEKE AKCIJE
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      if(mod != UNDEFINED) {
        JOptionPane.showMessageDialog(null,Messages.get("POTPOTPOLJA_INFDLG_CLOSING_ACTIONNOTFINISHED_TEXT"),Messages.get("POTPOTPOLJA_INFDLG_CLOSING_ACTIONNOTFINISHED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      else {
        super.processWindowEvent(e);
      }
    }
    else {
      super.processWindowEvent(e);
    }
  }
}
