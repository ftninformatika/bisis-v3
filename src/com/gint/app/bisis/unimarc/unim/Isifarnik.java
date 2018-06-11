
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

public class Isifarnik extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel5 = new JPanel();
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
  JPanel jPanel1 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel10 = new JPanel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextArea jTextArea1 = new JTextArea();
  JScrollPane jScrollPane3 = new JScrollPane();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  Border border3;
  TitledBorder titledBorder2;
  Border border4;
  Border border5;
  TitledBorder titledBorder3;
  Border border6;
  Border border7;
  Border border8;

  Connection conn;
  DBMISifarnik dbmIS;
  DBComboModel dbcm1;
  DBComboModel dbcm2;

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
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  boolean isEmpty=false;
  boolean first=false;

  public Isifarnik(Connection conn) {
    try  {
      this.conn = conn;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,Messages.get("ISIFARNIK_TITLEDBORDER1_FIELDS"));
    border2 = BorderFactory.createCompoundBorder(titledBorder1,BorderFactory.createEmptyBorder(3,3,3,3));
    border3 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border3,Messages.get("ISIFARNIK_TITLEDBORDER2_SUBFIELDS"));
    border4 = BorderFactory.createCompoundBorder(titledBorder2,BorderFactory.createEmptyBorder(3,3,3,3));
    border5 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder3 = new TitledBorder(border5,Messages.get("ISIFARNIK_TITLEDBORDER3_INTERNCODER"));
    border6 = BorderFactory.createCompoundBorder(titledBorder3,BorderFactory.createEmptyBorder(3,3,3,3));
    border7 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border8 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jLabel1.setText(Messages.get("ISIFARNIK_LABEL1_CODE"));
    jComboBox1.setPreferredSize(new Dimension(53, 21));
    jComboBox1.setMinimumSize(new Dimension(53, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("ISIFARNIK_DIALOGTITLE_INTERNCODER"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jPanel4.setLayout(xYLayout2);
    jPanel4.setBorder(border2);
    jPanel4.setMinimumSize(new Dimension(666, 80));
    jPanel4.setPreferredSize(new Dimension(666, 80));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jLabel3.setText(Messages.get("ISIFARNIK_LABEL3_CODE"));
    jPanel5.setLayout(xYLayout3);
    jPanel5.setBorder(border4);
    jPanel5.setMinimumSize(new Dimension(666, 80));
    jPanel5.setPreferredSize(new Dimension(666, 80));
    jButton10.setText(Messages.get("ISIFARNIK_BUTTON_INPUT"));
    jButton10.setPreferredSize(new Dimension(80, 21));
    jButton10.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton11.setText(Messages.get("ISIFARNIK_BUTTON_CHANGE"));
    jButton11.setPreferredSize(new Dimension(80, 21));
    jButton11.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton11_actionPerformed(e);
      }
    });
    jButton12.setText(Messages.get("ISIFARNIK_BUTTON_CLEAR"));
    jButton12.setPreferredSize(new Dimension(80, 21));
    jButton12.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton12_actionPerformed(e);
      }
    });
    jButton13.setText(Messages.get("ISIFARNIK_BUTTON_OK"));
    jButton13.setPreferredSize(new Dimension(80, 21));
    jButton13.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton13_actionPerformed(e);
      }
    });
    jButton14.setText(Messages.get("ISIFARNIK_BUTTON_CANCEL"));
    jButton14.setPreferredSize(new Dimension(80, 21));
    jButton14.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton14_actionPerformed(e);
      }
    });
    jButton15.setText(Messages.get("ISIFARNIK_BUTTON_EXIT"));
    jButton15.setPreferredSize(new Dimension(80, 21));
    jButton15.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton15_actionPerformed(e);
      }
    });
    jButton4.setText(Messages.get("ISIFARNIK_BUTTON_FIRST"));
    jButton4.setPreferredSize(new Dimension(80, 21));
    jButton4.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setText(Messages.get("ISIFARNIK_BUTTON_PREVIOUS"));
    jButton5.setPreferredSize(new Dimension(80, 21));
    jButton5.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setText(Messages.get("ISIFARNIK_BUTTON_NEXT"));
    jButton6.setPreferredSize(new Dimension(80, 21));
    jButton6.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setText(Messages.get("ISIFARNIK_BUTTON_LAST"));
    jButton7.setPreferredSize(new Dimension(80, 21));
    jButton7.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setText(Messages.get("ISIFARNIK_BUTTON_VIEW"));
    jButton8.setPreferredSize(new Dimension(80, 21));
    jButton8.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jButton9.setText(Messages.get("ISIFARNIK_BUTTON_HELP"));
    jButton9.setPreferredSize(new Dimension(80, 21));
    jButton9.setFont(new java.awt.Font("Dialog", 0, 11));
  	this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel9.setLayout(xYLayout5);
    jPanel6.setLayout(xYLayout7);
    jPanel7.setLayout(xYLayout6);
    jPanel10.setLayout(xYLayout4);
    jLabel6.setText(Messages.get("ISIFARNIK_LABEL6_CODE"));
    jLabel5.setText(Messages.get("ISIFARNIK_LABEL_NAME"));
    jTextField1.setMinimumSize(new Dimension(175, 21));
    jTextField1.setPreferredSize(new Dimension(175, 21));
    jTextField1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setPreferredSize(new Dimension(250, 119));
    jTextArea1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setMinimumSize(new Dimension(250, 119));
    jScrollPane3.setPreferredSize(new Dimension(260, 70));
    jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane3.setMinimumSize(new Dimension(260, 70));
    jPanel10.setBorder(border6);
    jPanel6.setBorder(border7);
    jPanel7.setBorder(border8);
    xYLayout1.setHeight(400);
    xYLayout1.setWidth(650);
    jLabel7.setForeground(new java.awt.Color(50, 54, 120));
    jLabel8.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel9, new XYConstraints(5, 320, -1, -1));
    jPanel9.add(jButton6, new XYConstraints(229, 3, -1, -1));
    jPanel9.add(jButton9, new XYConstraints(565, 3, -1, -1));
    jPanel9.add(jPanel7, new XYConstraints(336, 30, -1, -1));
    jPanel7.add(jButton14, new XYConstraints(115, 1, 0, 0));
    jPanel7.add(jButton13, new XYConstraints(2, 1, 0, 0));
    jPanel9.add(jButton15, new XYConstraints(565, 36, -1, -1));
    jPanel9.add(jButton7, new XYConstraints(341, 3, -1, -1));
    jPanel9.add(jButton5, new XYConstraints(117, 3, -1, -1));
    jPanel9.add(jButton8, new XYConstraints(453, 3, -1, -1));
    jPanel9.add(jButton4, new XYConstraints(5, 3, -1, -1));
    jPanel9.add(jPanel6, new XYConstraints(0, 30, -1, -1));
    jPanel6.add(jButton10, new XYConstraints(3, 1, 0, 0));
    jPanel6.add(jButton12, new XYConstraints(228, 1, 0, 0));
    jPanel6.add(jButton11, new XYConstraints(115, 1, 0, 0));
    jPanel1.add(jPanel4, new XYConstraints(7, 10, 500, -1));
    jPanel4.add(jLabel1, new XYConstraints(5, 2, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(47, 0, -1, -1));
    jPanel4.add(jLabel7, new XYConstraints(150, 0, 320, -1));
    jPanel1.add(jPanel5, new XYConstraints(7, 90, 500, -1));
    jPanel5.add(jLabel3, new XYConstraints(5, 2, -1, -1));
    jPanel5.add(jComboBox2, new XYConstraints(47, 0, -1, -1));
    jPanel5.add(jLabel8, new XYConstraints(150, 0, 320, -1));
    jPanel1.add(jPanel10, new XYConstraints(7, 170, 340, -1));
    jPanel10.add(jLabel6, new XYConstraints(5, 2, -1, -1));
    jPanel10.add(jLabel5, new XYConstraints(5, 31, -1, -1));
    jPanel10.add(jTextField1, new XYConstraints(47, 0, -1, -1));
    jPanel10.add(jScrollPane3, new XYConstraints(47, 31, -1, -1));
    jScrollPane3.getViewport().add(jTextArea1, null);
    this.setModal(true);

    trans=true;
    dbmIS = new DBMISifarnik(conn);
    first=true;
    dbcm1 = new DBComboModel(conn,"select distinct pp.PO_POLJEID,p.PO_NAZIV from potpolja pp,polje p where pp.PO_POLJEID=p.POLJEID order by pp.PO_POLJEID",true);
    jComboBox1.setModel(dbcm1);
    if(dbmIS.getRowCount()==0) {
      jComboBox1.setSelectedIndex(0);
      dbcm2 = new DBComboModel(conn,"select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID="+Integer.parseInt(((String)jComboBox1.getSelectedItem()).trim())
                               +" order by POTPOLJAID", true);
      oldField = ((String)jComboBox1.getSelectedItem()).trim();
    }
    else{
      dbcm2 = new DBComboModel(conn,"select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID="+Integer.parseInt(((String)dbmIS.getValueAt(row,0)).trim())
                                +" order by POTPOLJAID", true);
      oldField = ((String)dbmIS.getValueAt(row,0)).trim();
      jComboBox1.setSelectedItem(((String)dbmIS.getValueAt(row,0)).trim());
    }
    jComboBox2.setModel(dbcm2);
    first=false;
    trans=false;
    if (dbmIS.getRowCount() != 0){
      trans=true;
      setValues(row);
      setComponentEnabled();
      jButton6.requestFocus();
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
                      jComboBox1.setEnabled(true);
                      jComboBox2.setEnabled(true);
                      break;
      case INSERT : jTextField1.setEnabled(true);
                    jTextArea1.setEnabled(true);
                    jTextField1.requestFocus();
                    jTextField1.setText("");
                    jTextArea1.setText("");
		    jComboBox1.setEnabled(false);
              	    jComboBox2.setEnabled(false);
                    break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(true);
                    jTextArea1.requestFocus();
		    jComboBox1.setEnabled(false);
              	    jComboBox2.setEnabled(false);
              	    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(false);
		    jComboBox1.setEnabled(false);
              	    jComboBox2.setEnabled(false);
              	    break;
    }
  }

  void setValues(int row){
    jComboBox1.setSelectedItem((String)dbmIS.getValueAt(row,0));
    jComboBox2.setSelectedItem((String)dbmIS.getValueAt(row,1));
    jTextField1.setText((String)dbmIS.getValueAt(row,2));
    jTextArea1.setText((String)dbmIS.getValueAt(row,3));
  }

  void setNameField(){
    jLabel7.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameSField(){
    jLabel8.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }

  boolean checkValues() {
    if (mod == INSERT) {
      if(jTextField1.getText().length() > 15) {
        JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_INTERNCODER_MORETHAN15CHAR_TEXT"),Messages.get("ISIFARNIK_INFDLG_INTERNCODER_MORETHAN15CHAR_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if(jTextField1.getText().length() == 0) {
        JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_INTERNCODER_MISSINGINTERNCODER_TEXT"),Messages.get("ISIFARNIK_INFDLG_INTERNCODER_MISSINGINTERNCODER_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
    }
    if(jTextArea1.getText().length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_INTERNCODERNAME_LENGTHEXCEEDED_TEXT"),Messages.get("ISIFARNIK_INFDLG_INTERNCODERNAME_LENGTHEXCEEDED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextArea1.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_INTERNCODERNAME_NONAME_TEXT"),Messages.get("ISIFARNIK_INFDLG_INTERNCODERNAME_NONAME_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void find(String fld,String sfld,String iSfr) {
    String value = fld+"|"+sfld+"|"+iSfr;
    int lengths = fld.length()+sfld.length()+iSfr.length()+2;
    int pos = dbmIS.find(value,lengths);
    if (pos!=-1){
      row=pos;
      trans=true;
      setValues(row);
      trans=false;
    }
  }

  public String getField() {
    return (String)dbmIS.getValueAt(row,0);
  }
  public String getSField() {
    return (String)dbmIS.getValueAt(row,1);
  }
  public String getISifarnik() {
    return (String)dbmIS.getValueAt(row,2);
  }
  void first() {
    trans=true;
    row=0;
    setValues(row);
    trans=false;
  }

  void last() {
    trans=true;
    row=dbmIS.getRowCount()-1;
    setValues(row);
    trans=false;
  }

  void next() {
    if(row == dbmIS.getRowCount()-1)
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
    PronadjiIS pis = new PronadjiIS(conn,this);
    pis.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = pis.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    pis.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    pis.setVisible(true);
  }
  void jButton10_actionPerformed(ActionEvent e)  {
    insert();
  }
  void insert() {
    mod = INSERT;
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
    if(dbmIS.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_RECORD_LASTRECDELETED_TEXT"),Messages.get("ISIFARNIK_INFDLG_RECORD_LASTRECDELETED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
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
    String iSfr = jTextField1.getText().trim();
    String naziv = jTextArea1.getText().trim();
    dbmIS.setField(fld);
    dbmIS.setSField(sfld);
    dbmIS.setISifarnik(iSfr);
    dbmIS.setNazivISifarnik(naziv);
    if (mod == INSERT) {
      int pos = dbmIS.findPos(fld,sfld,iSfr);
      if (checkValues()&& pos!=-1) {
        row = pos;
        dbmIS.setTo(row,mod);
        dbmIS.saveToDB(mod,row);
        if(isEmpty)
          isEmpty=false;
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
          jButton6.requestFocus();
      }
      else {
        if(pos == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_INTERNCODER_EXISTANCEINDATABASE_TEXT"),Messages.get("ISIFARNIK_INFDLG_INTERNCODER_EXISTANCEINDATABASE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	}
      }
    }
    else if (mod == UPDATE) {
      if (checkValues()) {
        dbmIS.setTo(row,mod);
        dbmIS.saveToDB(mod,row);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
    else if (mod == DELETE) {
      dbmIS.removeRow(row);
      dbmIS.saveToDB(mod,row);
      if (row == dbmIS.getRowCount()) {
        row=row-1;
      }
      if(dbmIS.getRowCount() == 0) {
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
      int fld = Integer.parseInt(fi);
      dbcm2.reloadItems("select distinct POTPOLJAID,PP_NAZIV from potpolja where PO_POLJEID="+fld+" order by POTPOLJAID",true);
      int i = dbmIS.find(fi,fi.length());
      if(i != -1) {
	row = i;
  	setValues(row);
  	jButton6.requestFocus();
      }
      else {
      	//oldField = fi;//!!!
      	mod = INSERT;
	jComboBox2.setSelectedIndex(0);
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
      int i = dbmIS.find(fld+"|"+sfld,fld.length()+sfld.length()+1);
      if(i != -1) {
        row = i;
        setValues(row);
        jButton6.requestFocus();
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        setButtonEnabled();
      }
      trans=false;
    }
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN:if(mod == UNDEFINED) {
	                          if(dbmIS.getRowCount() !=0) {
				    next();
				  }
				 }
				 break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
	                          if(dbmIS.getRowCount() !=0) {
                                    prev();
				  }
			        }
				break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
	                      if(dbmIS.getRowCount() !=0) {
				first();
			      }
			    }
			    break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
	                      if(dbmIS.getRowCount() !=0) {
			        last();
			      }
			    }
			    break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
	                    if(dbmIS.getRowCount() !=0) {
			      insert();
			    }
			  }
			  break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
	                    if(dbmIS.getRowCount() !=0) {
			      update();
			    }
			  }
			  break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
	                    if(dbmIS.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("ISIFARNIK_INFDLG_CLOSING_ACTIONNOTFINISHED_TEXT"),Messages.get("ISIFARNIK_INFDLG_CLOSING_ACTIONNOTFINISHED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
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
