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
import java.awt.event.*;
import java.sql.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;

public class SFMandLevDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jbPreview = new JButton();
  JButton jbLast = new JButton();
  JButton jbFirst = new JButton();
  JButton jbExit = new JButton();
  JButton jbNext = new JButton();
  JButton jbHelp = new JButton();
  JButton jbPrev = new JButton();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox3 = new JComboBox();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JScrollPane spane = new JScrollPane();
  JList jList1 = new JList();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbChange = new JButton();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  Border border1;
  Border border2;
  Connection conn;
  int row;
  boolean first;
  boolean trans;
  int mod;
  DBMSFMandLev dbmsfml;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBComboModel dbcm4;
  DBListModel dblm;
  int countSubFields;
  String oldField="";
  String oldNO="";
  String oldTP="";
  Object[] subField = new Object[35];
  int[] indexes;
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();

  public SFMandLevDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    xYLayout1.setHeight(450);
    xYLayout1.setWidth(610);
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jbPreview.setText(Messages.get("SFMANDLEVDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("SFMANDLEVDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("SFMANDLEVDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("SFMANDLEVDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("SFMANDLEVDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("SFMANDLEVDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("SFMANDLEVDLG_BUTTON_PREVIOUS"));
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
    jLabel1.setText(Messages.get("SFMANDLEVDLG_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("SFMANDLEVDLG_LABEL_PROCESS_LEVEL"));
    jLabel3.setText(Messages.get("SFMANDLEVDLG_LABEL_MANDATORY_LEVEL"));
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    jComboBox4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox4_actionPerformed(e);
      }
    });
    jLabel5.setText(Messages.get("SFMANDLEVDLG_LABEL_FIELD_CODE"));
    jLabel4.setText(Messages.get("SFMANDLEVDLG_LABEL_SUBFIELD_CODE"));
    jbChange.setText(Messages.get("SFMANDLEVDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("SFMANDLEVDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("SFMANDLEVDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
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
    panel1.add(jbLast, new XYConstraints(310, 340, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 340, 90, 25));
    panel1.add(jbPreview, new XYConstraints(410, 340, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 387, 90, 25));
    panel1.add(jbNext, new XYConstraints(210, 340, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 340, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 340, 90, 25));
    panel1.add(jComboBox1, new XYConstraints(150, 20, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(150, 70, 70, 22));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(jComboBox3, new XYConstraints(150, 120, 70, 22));
    panel1.add(jComboBox4, new XYConstraints(150, 170, 80, 22));
    panel1.add(jLabel5, new XYConstraints(15, 170, -1, -1));
    panel1.add(jLabel4, new XYConstraints(15, 220, -1, -1));
    panel1.add(spane, new XYConstraints(150, 220, 210, 100));
    panel1.add(jPanel1, new XYConstraints(5, 382, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, 25));
    panel1.add(jPanel2, new XYConstraints(310, 382, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, 25));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, 25));
    panel1.add(jLabel6, new XYConstraints(255, 20, 250, -1));
    panel1.add(jLabel7, new XYConstraints(255, 70, 250, -1));
    panel1.add(jLabel8, new XYConstraints(255, 120, 250, -1));
    panel1.add(jLabel9, new XYConstraints(255, 170, 250, -1));
    spane.getViewport().add(jList1, null);
    spane.getViewport().setView(jList1);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    jList1.clearSelection();
    setResizable(false);

    row = 0;
    first = true;
    trans = true;

    dbmsfml = new DBMSFMandLev(conn);

    dbcm1 = new DBComboModel(conn, "select distinct b.NOB_TP_TIPID,tp.TIP_NAZIV from NIVO_OBRADE_POTPOLJA a, NIVO_OBAVEZNOSTI b, TIP_PUBLIKACIJE tp",
                              " where tp.TIPID=b.NOB_TP_TIPID and a.NOB_TP_TIPID=b.NOB_TP_TIPID and a.NOB_NOBRID=b.NOB_NOBRID"+
                              " order by b.NOB_TP_TIPID",true);
    jComboBox1.setModel(dbcm1);

    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jList1.clearSelection();

    if (dbmsfml.getRowCount() == 0) {
      jComboBox1.setSelectedIndex(0);
      dbcm2 = new DBComboModel(conn, "select distinct a.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBRADE_POTPOLJA a, NIVO_OBAVEZNOSTI b, NIVO_OBRADE nobr",
                                " where nobr.NOBRID=b.NOB_NOBRID and nobr.TP_TIPID=b.NOB_TP_TIPID and a.NOB_NOBRID=b.NOB_NOBRID and a.NOB_TP_TIPID=b.NOB_TP_TIPID and"+
                                " a.NOB_TP_TIPID='"+((String)jComboBox1.getSelectedItem()).trim()+
                                "' order by a.NOB_NOBRID",true);
      jComboBox2.setModel(dbcm2);
      jComboBox2.setSelectedIndex(0);
      dbcm3 = new DBComboModel(conn, "select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI",
                                " where NOB_TP_TIPID = '"+((String)jComboBox1.getSelectedItem()).trim()+"'"+
                                "and NOB_NOBRID = '"+((String)jComboBox2.getSelectedItem()).trim()+
                                "' order by NOBAVID",true);
      jComboBox3.setModel(dbcm3);
      jComboBox3.setSelectedIndex(0);
      dbcm4 = new DBComboModel(conn, "select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p",
                                " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = '"+((String)jComboBox1.getSelectedItem()).trim()+
                                "' and nopp.NOB_NOBRID= '"+((String)jComboBox2.getSelectedItem()).trim()+
                                "'  order by nopp.PPO_PO_POLJEID",true);
      jComboBox4.setModel(dbcm4);
      jComboBox4.setSelectedIndex(0);
      oldTP=((String)jComboBox1.getSelectedItem()).trim();
      oldNO=((String)jComboBox2.getSelectedItem()).trim();
      oldField=((String)jComboBox4.getSelectedItem()).trim();
      String select = "select distinct POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from NIVO_OBRADE_POTPOLJA where NOB_TP_TIPID= '"+((String)jComboBox1.getSelectedItem()).trim()+"' and NOB_NOBRID= '"+((String)jComboBox2.getSelectedItem()).trim()+"' and PPO_PO_POLJEID= '"+((String)jComboBox4.getSelectedItem()).trim()+"')"+" and PO_POLJEID='"+((String)jComboBox4.getSelectedItem()).trim()+"' order by POTPOLJAID";
      dblm = new DBListModel(conn, select, where);
      jList1.setModel(dblm);
      mod=INSERT;
      setButtonEnabled();
    }
    else {
      dbcm2 = new DBComboModel(conn, "select distinct a.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBRADE_POTPOLJA a, NIVO_OBAVEZNOSTI b,NIVO_OBRADE nobr",
                                " where nobr.NOBRID=b.NOB_NOBRID and nobr.TP_TIPID=b.NOB_TP_TIPID and a.NOB_NOBRID=b.NOB_NOBRID and a.NOB_TP_TIPID=b.NOB_TP_TIPID and"+
                                " a.NOB_TP_TIPID='"+(String)dbmsfml.getValueAt(row,0)+
                                "' order by a.NOB_NOBRID",true);
      jComboBox2.setModel(dbcm2);
      dbcm3 = new DBComboModel(conn, "select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI",
                                " where NOB_TP_TIPID = '"+(String)dbmsfml.getValueAt(row,0)+"'"+
                                "and NOB_NOBRID = '"+(String)dbmsfml.getValueAt(row,1)+
                                "' order by NOBAVID",true);
      jComboBox3.setModel(dbcm3);
      dbcm4 = new DBComboModel(conn, "select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p",
                                " where nopp.PPO_PO_POLJEID=p.POLJEID and nopp.NOB_TP_TIPID = '"+(String)dbmsfml.getValueAt(row,0)+
      				"' and nopp.NOB_NOBRID= '"+(String)dbmsfml.getValueAt(row,1)+
      				"'  order by nopp.PPO_PO_POLJEID",true);
      jComboBox4.setModel(dbcm4);
      oldTP=((String)dbmsfml.getValueAt(row,0)).trim();
      oldNO=((String)dbmsfml.getValueAt(row,1)).trim();
      oldField=((String)dbmsfml.getValueAt(row,3)).trim();
      String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where= " where POTPOLJAID in (select distinct PPO_POTPOLJAID from NIVO_OBRADE_POTPOLJA"+
                    " where NOB_TP_TIPID= '"+(String)dbmsfml.getValueAt(row,0)+
                    "' and NOB_NOBRID= '"+(String)dbmsfml.getValueAt(row,1)+
                    "' and PPO_PO_POLJEID= '"+(String)dbmsfml.getValueAt(row,3)+"')"+
                    " and PO_POLJEID='"+(String)dbmsfml.getValueAt(row,3)+
                    "' order by POTPOLJAID";
      dblm = new DBListModel(conn, select, where);
      jList1.setModel(dblm);
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    first=false;
    trans=false;
    setNameTP();
    setNameNO();
    setNameNObav();
    setNameFI();
  }
  void setNameTP() {
    jLabel6.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel7.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameNObav() {
    jLabel8.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel9.setText((String)dbcm4.getNameAt(jComboBox4.getSelectedIndex()));
  }

  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jbFirst.setEnabled(true);
                      jbPrev.setEnabled(true);
                      jbNext.setEnabled(true);
                      jbLast.setEnabled(true);
                      jbPreview.setEnabled(true);
                      jbChange.setEnabled(true);
                      jbOk.setEnabled(false);
                      jbCancel.setEnabled(false);
                      jbExit.setEnabled(true);
                      break;
      case INSERT :
      case UPDATE :
      case DELETE : jbFirst.setEnabled(false);
                    jbPrev.setEnabled(false);
                    jbNext.setEnabled(false);
                    jbLast.setEnabled(false);
                    jbPreview.setEnabled(false);
                    jbChange.setEnabled(false);
                    jbOk.setEnabled(true);
                    jbCancel.setEnabled(true);
                    jbExit.setEnabled(false);
                    break;
    }
  }

  void setComponentEnabled() {
    switch(mod) {
      case UNDEFINED :jComboBox1.setEnabled(true);
                      jComboBox2.setEnabled(true);
                      jComboBox3.setEnabled(true);
                      jComboBox4.setEnabled(true);
                      jList1.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jList1.setEnabled(true);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
      case UPDATE : jList1.setEnabled(true);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
      case DELETE : jList1.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
    }
  }

  void setValues(int row) {
    String typePubl = ((String)dbmsfml.getValueAt(row,0));
    String noBr = ((String)dbmsfml.getValueAt(row,1));
    String noBav = ((String)dbmsfml.getValueAt(row,2));
    String field = ((String)dbmsfml.getValueAt(row,3));
    jComboBox1.setSelectedItem(typePubl);
    jComboBox2.setSelectedItem(noBr);
    jComboBox3.setSelectedItem(noBav);
    jComboBox4.setSelectedItem(field);
    first=false;
    jList1.clearSelection();
    countSubFields = 0;
    int k;
    String subField = "";
    for (int j=0; j < dblm.getSize(); j++) {
      subField = (String)dblm.getElementAt(j);
      k = dbmsfml.findKey(typePubl.trim()+"|"+noBr.trim()+"|"+noBav.trim()+"|"+field.trim()+"|"+subField.substring(0,subField.indexOf("-")));
      if(k != -1) {
        countSubFields++;
        jList1.addSelectionInterval(j,j);
      }
    }
  }

  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmsfml.getRowCount()-1;
    setValues(row);
    row = (row-countSubFields)+1;
    trans = false;
  }

  void next() {
    trans = true;
    if((row+countSubFields) > dbmsfml.getRowCount()-1) {
      setValues(row);
    }
    else {
      row = row+countSubFields;
      setValues(row);
    }
    trans = false;
  }

  void prev() {
    trans = true;
    if(row == 0)
      setValues(row);
    else {
      row = row-1;
      setValues(row);
      row = (row-countSubFields)+1;
    }
    trans = false;
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FSFMandLev fsfml = new FSFMandLev(null,Messages.get("SFMANDLEVDLG_FSFMANDLEV_FIND"),true,conn,this);
    fsfml.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fsfml.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fsfml.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fsfml.setVisible(true);
  }
  void jbChange_actionPerformed(ActionEvent e) {
    update();
  }
  void update() {
    subField = jList1.getSelectedValues();
    indexes = jList1.getSelectedIndices();
    mod = UPDATE;
    setComponentEnabled();
    setButtonEnabled();
  }

  void jbLast_actionPerformed(ActionEvent e) {
    last();
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmsfml.getRowCount() == 0) {
      mod = UNDEFINED;
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

  void jbFirst_actionPerformed(ActionEvent e) {
    first();
  }
  void jbOk_actionPerformed(ActionEvent e) {
    doAction();
  }
  void doAction() {
    boolean isInsert = false;
    if(jList1.getSelectedValues().length == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("SFMANDLEVDLG_OPTION_PANE_ONE_ITEM_SELECTED"),Messages.get("SFMANDLEVDLG_OPTION_PANE_ONE_ITEM_SELECTED_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
      String noBr = ((String)jComboBox2.getSelectedItem()).trim();
      String noBav = ((String)jComboBox3.getSelectedItem()).trim();
      String field = ((String)jComboBox4.getSelectedItem()).trim();
      int[] lengths ={typePubl.length(),noBr.length(),noBav.length(),field.length(),0};
      int j = dbmsfml.findPos(typePubl, noBr, noBav, field);
      if (j != -1) {
        mod = INSERT;
        row = j;
        isInsert = true;
      }
      else {
        mod = UPDATE;
        for(int k = 0; k < subField.length; k++) {
          dbmsfml.removeRow(row);
        }
      }
      if(((String)jList1.getSelectedValues()[0]).equals("---bez selekcije---")) {
        mod = DELETE;
      }
      else {
        subField = jList1.getSelectedValues();
      }
      dbmsfml.saveToDB(mod,typePubl,noBr,noBav,field,subField,row);
      countSubFields = subField.length;
      if(mod != DELETE) {
        row = dbmsfml.find(typePubl+"|"+noBr+"|"+noBav+"|"+field,lengths,3);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
      else {
        if(dbmsfml.getRowCount() != 0) {
          if(!isInsert) {
            if(row-1 < 0) {
              row = 0;
            }
          }
          if(row != 0)
            row = row-1;
          trans = true;
          setValues(row);
          trans = false;
          int[] leng = {((String)jComboBox1.getSelectedItem()).trim().length(),((String)jComboBox2.getSelectedItem()).trim().length(),((String)jComboBox3.getSelectedItem()).trim().length(),((String)jComboBox4.getSelectedItem()).trim().length(),0};
          row = dbmsfml.find(((String)jComboBox1.getSelectedItem()).trim()+"|"+((String)jComboBox2.getSelectedItem()).trim()+"|"+((String)jComboBox3.getSelectedItem()).trim()+"|"+((String)jComboBox4.getSelectedItem()).trim(),leng,3);
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
        }
        else {
          if(dbmsfml.getRowCount()==0)
            dispose();
          mod = INSERT;
          setButtonEnabled();
          setComponentEnabled();
        }
      }
    }
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
    setNameTP();
    String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm2.reloadItems("select distinct a.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBRADE_POTPOLJA a, NIVO_OBAVEZNOSTI b, NIVO_OBRADE nobr"+
                        " where nobr.NOBRID=b.NOB_NOBRID and nobr.TP_TIPID=b.NOB_TP_TIPID and a.NOB_NOBRID=b.NOB_NOBRID and"+
                        " a.NOB_TP_TIPID=b.NOB_TP_TIPID and a.NOB_TP_TIPID='"+typePubl+
                        "' order by a.NOB_NOBRID",true);
      String noBr = ((String)jComboBox2.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI where NOB_TP_TIPID="+"'"+typePubl+"'"+
                        " and NOB_NOBRID= '"+noBr+"' order by NOBAVID",true);
      dbcm4.reloadItems("select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p"+
                        " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = '"+typePubl+"' and nopp.NOB_NOBRID= '"+noBr+
                        "' order by nopp.PPO_PO_POLJEID",true);
      int[] lenghts = {typePubl.length(),0,0,0,0};
      int i = dbmsfml.find(typePubl,lenghts,0);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameNO();
        setNameNObav();
        setNameFI();
      }
      else {
        mod = INSERT;
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        setNameNO();
        setNameNObav();
        setNameFI();
        setButtonEnabled();
        setComponentEnabled();
        jComboBox2.setEnabled(true);
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
        jList1.clearSelection();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldTP.equals(((String)jComboBox1.getSelectedItem()).trim())) {
          dbcm2.reloadItems("select distinct a.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBRADE_POTPOLJA a, NIVO_OBAVEZNOSTI b, NIVO_OBRADE nobr"+
                            " where nobr.NOBRID=b.NOB_NOBRID and nobr.TP_TIPID=b.NOB_TP_TIPID and a.NOB_NOBRID=b.NOB_NOBRID and"+
                            " a.NOB_TP_TIPID=b.NOB_TP_TIPID and a.NOB_TP_TIPID='"+typePubl+
                            "' order by a.NOB_NOBRID",true);
          String noBr = ((String)jComboBox2.getSelectedItem()).trim();
          dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI where NOB_TP_TIPID="+"'"+typePubl+"'"+
                            " and NOB_NOBRID= '"+noBr+"' order by NOBAVID",true);
          dbcm4.reloadItems("select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p"+
                            " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = '"+typePubl+"' and nopp.NOB_NOBRID= '"+noBr+
                            "'  order by nopp.PPO_PO_POLJEID",true);
        //}
      }
    }
//  oldTP = ((String)jComboBox1.getSelectedItem()).trim();
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameNO();
    String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
    String noBr = ((String)jComboBox2.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI where NOB_TP_TIPID="+"'"+typePubl+"'"+
                        " and NOB_NOBRID= '"+noBr+"' order by NOBAVID",true);
      dbcm4.reloadItems("select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p"+
                        " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = '"+typePubl+"' and nopp.NOB_NOBRID= '"+noBr+
                        "'  order by nopp.PPO_PO_POLJEID",true);
      int[] lengths = {typePubl.length(),noBr.length(),0,0,0};
      int i = dbmsfml.find(typePubl+"|"+noBr,lengths,1);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameNObav();
        setNameFI();
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        jComboBox3.setSelectedIndex(0);
        jComboBox3.setEnabled(true);
        jComboBox4.setSelectedIndex(0);
        jComboBox4.setEnabled(true);
        setNameNObav();
        setNameFI();
        setButtonEnabled();
        jList1.clearSelection();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldNO.equals(((String)jComboBox2.getSelectedItem()).trim())) {
          dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI where NOB_TP_TIPID="+"'"+typePubl+"'"+
                            " and NOB_NOBRID= '"+noBr+"' order by NOBAVID",true);
          dbcm4.reloadItems("select distinct nopp.PPO_PO_POLJEID,p.PO_NAZIV from NIVO_OBRADE_POTPOLJA nopp, POLJE p"+
                            " where p.POLJEID=nopp.PPO_PO_POLJEID and nopp.NOB_TP_TIPID = '"+typePubl+"' and nopp.NOB_NOBRID= '"+noBr+
                            "'  order by nopp.PPO_PO_POLJEID",true);
        //}
      }
    }
//  oldNO = ((String)jComboBox2.getSelectedItem()).trim();
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    setNameNObav();
    String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
    String noBr = ((String)jComboBox2.getSelectedItem()).trim();
    String noBav = ((String)jComboBox3.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      int[] lengths = {typePubl.length(),noBr.length(),noBav.length(),0,0};
      int i = dbmsfml.find(typePubl+"|"+noBr+"|"+noBav,lengths,2);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameFI();
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        jComboBox4.setSelectedIndex(0);
        setNameFI();
        jComboBox4.setEnabled(true);
        setButtonEnabled();
        jList1.clearSelection();
      }
      trans=false;
    }
  }

  void jComboBox4_actionPerformed(ActionEvent e) {
    setNameFI();
    String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
    String noBr = ((String)jComboBox2.getSelectedItem()).trim();
    String noBav = ((String)jComboBox3.getSelectedItem()).trim();
    String field = ((String)jComboBox4.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from NIVO_OBRADE_POTPOLJA where NOB_TP_TIPID= '"+typePubl+"' and NOB_NOBRID= '"+noBr+"' and PPO_PO_POLJEID= '"+field+"')"+" and PO_POLJEID='"+field+"' order by POTPOLJAID";
      dblm.reloadItems(select,where);
      int[] lengths = {typePubl.length(),noBr.length(),noBav.length(),field.length(),0};
      int i = dbmsfml.find(typePubl+"|"+noBr+"|"+noBav+"|"+field,lengths,3);
      if(i != -1) {
        row = i;
        setValues(row);
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        setButtonEnabled();
        jList1.clearSelection();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldField.equals(((String)jComboBox4.getSelectedItem()).trim())) {
          String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
          String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from NIVO_OBRADE_POTPOLJA where NOB_TP_TIPID= '"+typePubl+"' and NOB_NOBRID= '"+noBr+"' and PPO_PO_POLJEID= '"+field+"')"+" and PO_POLJEID='"+field+"' order by POTPOLJAID";
          dblm.reloadItems(select,where);
        //}
      }
    }
    //oldField = ((String)jComboBox4.getSelectedItem()).trim();
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmsfml.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmsfml.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmsfml.getRowCount() !=0) {
				first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmsfml.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmsfml.getRowCount() !=0) {
                              update();
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
  protected void processWindowEvent(WindowEvent e){
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      if(mod != 0) {
        JOptionPane.showMessageDialog(null,Messages.get("SFMANDLEVDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("SFMANDLEVDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
