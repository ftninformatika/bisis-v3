//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.okruzenje.okruz;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import java.sql.*;
import java.awt.event.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class SFSearchPrefDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JScrollPane spane = new JScrollPane();
  JList jList1 = new JList();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbPreview = new JButton();
  JButton jbChange = new JButton();
  JButton jbLast = new JButton();
  JButton jbCancel = new JButton();
  JButton jbFirst = new JButton();
  JButton jbOk = new JButton();
  JButton jbExit = new JButton();
  JButton jbNext = new JButton();
  JButton jbHelp = new JButton();
  JButton jbPrev = new JButton();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  Border border1;
  Border border2;
  Connection conn;
  DBMSFSearchPref dbmsfsp;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBListModel dblm;
  int mod;
  int row;
  int countSubFields;
  Object[] subField = new Object[35];
  boolean first;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();

  public SFSearchPrefDlg(Frame frame, String title, boolean modal, Connection conn) {
    super(frame, title, modal);
    this.conn = conn;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(365);
    xYLayout1.setWidth(610);
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jLabel1.setText(Messages.get("SFSEARCHPREFDLG_LABEL_PREFIX_CODE"));
    jLabel2.setText(Messages.get("SFSEARCHPREFDLG_LABEL_FIELD_CODE"));
    jLabel3.setText(Messages.get("SFSEARCHPREFDLG_LABEL_SUBFIELD_CODE"));
    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jbPreview.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_CHANGE"));
    jbLast.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_CANCEL"));
    jbFirst.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_OK"));
    jbExit.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("SFSEARCHPREFDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    getContentPane().add(panel1);
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(120, 20, 70, 22));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jComboBox2, new XYConstraints(120, 70, 80, 22));
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(spane, new XYConstraints(120, 120, 210, 110));
    panel1.add(jbPreview, new XYConstraints(405, 255, 90, 25));
    panel1.add(jbLast, new XYConstraints(305, 255, 90, 25));
    panel1.add(jbFirst, new XYConstraints(5, 255, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 302, 90, -1));
    panel1.add(jbNext, new XYConstraints(205, 255, 90, 25));
    panel1.add(jbHelp, new XYConstraints(505, 255, 90, 25));
    panel1.add(jbPrev, new XYConstraints(105, 255, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 297, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 297, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel4, new XYConstraints(225, 20, 250, -1));
    panel1.add(jLabel5, new XYConstraints(225, 70, 250, -1));
    spane.getViewport().add(jList1, null);
    spane.getViewport().setView(jList1);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    jList1.clearSelection();
    setResizable(false);

    row = 0;
    first = true;

    dbmsfsp = new DBMSFSearchPref(conn);

    dbcm1 = new DBComboModel(conn, "select distinct POLJEBPRID,PBPR_NAZIV from POLJEBPR order by POLJEBPRID", "",true);
    jComboBox1.setModel(dbcm1);

    dbcm2 = new DBComboModel(conn, "select distinct pp.PO_POLJEID,p.PO_NAZIV from POTPOLJA pp, POLJE p"+
                              " where p.POLJEID=pp.PO_POLJEID order by pp.PO_POLJEID", "",true);
    jComboBox2.setModel(dbcm2);

    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jList1.clearSelection();

    dblm = new DBListModel(conn, "select distinct POTPOLJAID, PP_NAZIV from POTPOLJA", " where PO_POLJEID="+"'"+(String)dbmsfsp.getValueAt(row,1)+"'  order by POTPOLJAID");
    jList1.setModel(dblm);
    setValues(row);
    mod=UNDEFINED;
    setButtonEnabled();
    setComponentEnabled();

    setNamePref();
    setNameFI();
  }
  void setNamePref() {
    jLabel4.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel5.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setValues(int row) {
    String pref = ((String)dbmsfsp.getValueAt(row,0));
    String field = ((String)dbmsfsp.getValueAt(row,1));
    jComboBox1.setSelectedItem(pref);
    jComboBox2.setSelectedItem(field);
    if(!first)
      dblm.reloadItems("select distinct POTPOLJAID, PP_NAZIV from POTPOLJA where PO_POLJEID="+"'"+(String)dbmsfsp.getValueAt(row,1)+"'"+" order by POTPOLJAID");
    else
      first = false;
    jList1.clearSelection();
    countSubFields = 0;
    int k;
    String subField = "";
    for (int j=0; j < dblm.getSize(); j++) {
      subField = (String)dblm.getElementAt(j);
      k = dbmsfsp.findKey(pref.trim()+"|"+field.trim()+"|"+subField.substring(0,subField.indexOf("-")));
      if(k != -1) {
        countSubFields++;
        jList1.addSelectionInterval(j,j);
      }
    }
  }

   void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jbFirst.setEnabled(true);
                      jbPrev.setEnabled(true);
                      jbNext.setEnabled(true);
                      jbLast.setEnabled(true);
                      jbPreview.setEnabled(true);
                      jbChange.setEnabled(false);
                      jbOk.setEnabled(false);
                      jbCancel.setEnabled(false);
                      jbExit.setEnabled(true);
                      break;
    }
  }

  void setComponentEnabled() {
    switch(mod) {
      case UNDEFINED :jComboBox1.setEnabled(false);
                      jComboBox2.setEnabled(false);
                      jList1.setEnabled(false);
                      jbNext.requestFocus();
                      break;
    }
  }

  void first() {
    row=0;
    setValues(row);
  }

  void last() {
    row=dbmsfsp.getRowCount()-1;
    setValues(row);
    row = (row-countSubFields)+1;
  }

  void next() {
    if((row+countSubFields) > dbmsfsp.getRowCount()-1)
      return;
    else {
      row = row+countSubFields;
      setValues(row);
    }
  }

  void prev() {
    if(row <= 0)
       return;
    else {
      row = row-1;
      setValues(row);
      row = (row-countSubFields)+1;
    }
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FSFSearchPref fsfsp = new FSFSearchPref(null,Messages.get("SFSEARCHPREFDLG_FSFSEARCHPREF_FIND"),true,conn,this);
    fsfsp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fsfsp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fsfsp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fsfsp.setVisible(true);
  }

  void jbLast_actionPerformed(ActionEvent e) {
    last();
  }

  void jbFirst_actionPerformed(ActionEvent e) {
    first();
  }

  void jbExit_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jbNext_actionPerformed(ActionEvent e) {
    next();
  }

  void jbPrev_actionPerformed(ActionEvent e) {
    prev();
  }
  void jComboBox1_actionPerformed(ActionEvent e) {
    setNamePref();
  }
  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameFI();
  }
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmsfsp.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmsfsp.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmsfsp.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmsfsp.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      default:
    }
  }
}
