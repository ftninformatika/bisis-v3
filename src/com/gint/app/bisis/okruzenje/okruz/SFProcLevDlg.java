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

public class SFProcLevDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
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
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JScrollPane spane = new JScrollPane();
  JList jList1 = new JList();
  JComboBox jComboBox3 = new JComboBox();
  JLabel jLabel4 = new JLabel();
  Border border1;
  Border border2;
  Connection conn;
  int row;
  int mod;
  boolean first;
  boolean trans;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBListModel dblm;
  DBMSFProcLev dbmsfpl;
  int countSubFields;
  String oldField="";
  String oldTP="";
  Object[] subField = new Object[35];
  int[] indexes;
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();

  public SFProcLevDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    jbPreview.setText(Messages.get("SFPROCLEVDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("SFPROCLEVDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("SFPROCLEVDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("SFPROCLEVDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("SFPROCLEVDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("SFPROCLEVDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("SFPROCLEVDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("SFPROCLEVDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("SFPROCLEVDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("SFPROCLEVDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
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
    jLabel1.setText(Messages.get("SFPROCLEVDLG_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("SFPROCLEVDLG_LABEL_PROCESS_LEVEL"));
    jLabel3.setText(Messages.get("SFPROCLEVDLG_LABEL_FIELD_CODE"));
    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jLabel4.setText(Messages.get("SFPROCLEVDLG_LABEL_SUBFIELD_CODE"));
    getContentPane().add(panel1);
    panel1.add(jbPreview, new XYConstraints(410, 340, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 340, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 340, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 387, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 340, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 340, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 340, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 382, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, 25));
    panel1.add(jPanel2, new XYConstraints(310, 382, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, 25));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, 25));
    panel1.add(jComboBox1, new XYConstraints(120, 20, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(120, 70, 70, 22));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(spane, new XYConstraints(120, 170, 210, 100));
    panel1.add(jComboBox3, new XYConstraints(120, 120, 80, 22));
    panel1.add(jLabel4, new XYConstraints(15, 170, -1, -1));
    panel1.add(jLabel5, new XYConstraints(245, 20, 250, -1));
    panel1.add(jLabel6, new XYConstraints(245, 70, 250, -1));
    panel1.add(jLabel7, new XYConstraints(245, 120, 250, -1));
    spane.getViewport().add(jList1, null);
    spane.getViewport().setView(jList1);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    jList1.clearSelection();
    jList1.clearSelection();
    setResizable(false);

    row = 0;
    first = true;
    trans = true;

    dbmsfpl = new DBMSFProcLev(conn);

    dbcm1 = new DBComboModel(conn, "select distinct dt.TP_TIPID,tp.TIP_NAZIV from DEF_TIPA dt, TIP_PUBLIKACIJE tp",
                              " where tp.TIPID=dt.TP_TIPID and dt.TP_TIPID in (select TP_TIPID from NIVO_OBRADE) order by dt.TP_TIPID",true);
    jComboBox1.setModel(dbcm1);

    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jList1.clearSelection();

    if (dbmsfpl.getRowCount() == 0) {
      jComboBox1.setSelectedIndex(0);
      oldTP = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2 = new DBComboModel(conn, "select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE", " where TP_TIPID = '"+((String)jComboBox1.getSelectedItem()).trim()+"' order by NOBRID",true);
      jComboBox2.setModel(dbcm2);
      jComboBox2.setSelectedIndex(0);
      dbcm3 = new DBComboModel(conn, "select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p",
                                " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+((String)jComboBox1.getSelectedItem()).trim()+
                                "' order by dt.PPO_PO_POLJEID",true);
      jComboBox3.setModel(dbcm3);
      jComboBox3.setSelectedIndex(0);
      oldField=((String)jComboBox3.getSelectedItem()).trim();
      String select = "select distinct POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select distinct PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+((String)jComboBox1.getSelectedItem()).trim()+"' and PPO_PO_POLJEID= '"+((String)jComboBox3.getSelectedItem()).trim()+"') and PO_POLJEID='"+(String)jComboBox3.getSelectedItem()+"' order by POTPOLJAID";
      dblm = new DBListModel(conn, select, where);
      jList1.setModel(dblm);
      mod=INSERT;
      setButtonEnabled();
    }
    else {
      dbcm2 = new DBComboModel(conn, "select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE", " where TP_TIPID = '"+(String)dbmsfpl.getValueAt(row,0)+"' order by NOBRID",true);
      jComboBox2.setModel(dbcm2);
      dbcm3 = new DBComboModel(conn, "select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p",
                                " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+(String)dbmsfpl.getValueAt(row,0)+"'  order by dt.PPO_PO_POLJEID",true);
      jComboBox3.setModel(dbcm3);
      oldTP = ((String)dbmsfpl.getValueAt(row,0)).trim();
      oldField=((String)dbmsfpl.getValueAt(row,2)).trim();
      String select = "select distinct POTPOLJAID, PP_NAZIV from POTPOLJA";
      String select1 = "select distinct PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+(String)dbmsfpl.getValueAt(row,0)+"' and PPO_PO_POLJEID= '"+(String)dbmsfpl.getValueAt(row,2)+"'";
      String where=  " where POTPOLJAID in ("+select1+")"+"and PO_POLJEID='"+(String)dbmsfpl.getValueAt(row,2)+"' order by POTPOLJAID";
      dblm = new DBListModel(conn, select, where);
      jList1.setModel(dblm);
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    first = false;
    trans=false;
    setNameTP();
    setNameNO();
    setNameFI();
  }
  void setNameTP() {
    jLabel5.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel6.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel7.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setValues(int row) {
    String typePubl = (String)dbmsfpl.getValueAt(row,0);
    String noBr = ((String)dbmsfpl.getValueAt(row,1));
    String field = (String)dbmsfpl.getValueAt(row,2);
    jComboBox1.setSelectedItem(typePubl);
    jComboBox2.setSelectedItem(noBr);
    jComboBox3.setSelectedItem(field);
    jList1.clearSelection();
    countSubFields = 0;
    int k = 0;
    String subField = "";
    for (int j=0; j < dblm.getSize(); j++) {
      subField = (String)dblm.getElementAt(j);
      k = dbmsfpl.findKey(typePubl.trim()+"|"+noBr.trim()+"|"+field.trim()+"|"+subField.substring(0,subField.indexOf("-")));
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
                      jList1.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jList1.setEnabled(true);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    break;
      case UPDATE : jList1.setEnabled(true);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    break;
      case DELETE : jList1.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    break;
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
    row=dbmsfpl.getRowCount()-1;
    setValues(row);
    row = (row-countSubFields)+1;
    trans = false;
  }

  void next() {
    trans = true;
    if((row+countSubFields) > dbmsfpl.getRowCount()-1) {
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
    if(row == 0) {
      setValues(row);
    }
    else {
      row = row-1;
      setValues(row);
      row = (row-countSubFields)+1;
    }
    trans = false;
  }


  void jbPreview_actionPerformed(ActionEvent e) {
    FSFProcLev fsfpl = new FSFProcLev(null,Messages.get("SFPROCLEVDLG_FSFPROCLEV_FIND"),true,conn,this);
    fsfpl.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fsfpl.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fsfpl.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fsfpl.setVisible(true);
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
    if (dbmsfpl.getRowCount() == 0) {
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
      JOptionPane.showMessageDialog(null,Messages.get("SFPROCLEVDLG_OPTION_PANE_ONE_ITEM_SELECTED"),Messages.get("SFPROCLEVDLG_OPTION_PANE_ONE_ITEM_SELECTED_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
      String noBr = ((String)jComboBox2.getSelectedItem()).trim();
      String field = ((String)jComboBox3.getSelectedItem()).trim();
      int[] lengths ={typePubl.length(),noBr.length(),field.length(),0};
      int j = dbmsfpl.findPos(typePubl, noBr, field);
      if (j != -1) {
        mod = INSERT;
        row = j;
        isInsert = true;
      }
      else {
        mod = UPDATE;
        for(int k = 0; k < subField.length; k++) {
          dbmsfpl.removeRow(row);
        }
      }
      if(((String)jList1.getSelectedValues()[0]).equals("---bez selekcije---")) {
        mod = DELETE;
      }
      else {
        subField = jList1.getSelectedValues();
      }
      dbmsfpl.saveToDB(mod,typePubl,noBr,field,subField,row);
      countSubFields = subField.length;
      if(mod != DELETE) {
        row = dbmsfpl.find(typePubl+"|"+noBr+"|"+field,lengths,2);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
      else {
        if(dbmsfpl.getRowCount() != 0) {
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
          int[] leng = {((String)jComboBox1.getSelectedItem()).trim().length(),((String)jComboBox2.getSelectedItem()).trim().length(),((String)jComboBox3.getSelectedItem()).trim().length(),0};
          row = dbmsfpl.find(((String)jComboBox1.getSelectedItem()).trim()+"|"+((String)jComboBox2.getSelectedItem()).trim()+"|"+((String)jComboBox3.getSelectedItem()).trim(),leng,2);
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
        }
        else {
	  if(dbmsfpl.getRowCount()==0)
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
      dbcm2.reloadItems("select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE where TP_TIPID="+"'"+typePubl+"' order by NOBRID",true);
      dbcm3.reloadItems("select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p"+
                        " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+typePubl+"'  order by dt.PPO_PO_POLJEID",true);
      int[] lenghts = {typePubl.length(),0,0,0};
      int i = dbmsfpl.find(typePubl,lenghts,0);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameNO();
        setNameFI();
      }
      else {
        mod = INSERT;
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        setNameNO();
        setNameFI();
        setButtonEnabled();
        setComponentEnabled();
        jComboBox2.setEnabled(true);
        jComboBox3.setEnabled(true);
        jList1.clearSelection();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldTP.equals(((String)jComboBox1.getSelectedItem()).trim())) {
          dbcm2.reloadItems("select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE where TP_TIPID="+"'"+typePubl+"' order by NOBRID",true);
          dbcm3.reloadItems("select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p"+
                            " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+typePubl+"'  order by dt.PPO_PO_POLJEID",true);
        //}
      }
    }
    //oldTP = ((String)jComboBox1.getSelectedItem()).trim();
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameNO();
    if(!trans) {
      trans=true;
      String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
      String noBr = ((String)jComboBox2.getSelectedItem()).trim();
      int[] lengths = {typePubl.length(),noBr.length(),0,0};
      int i = dbmsfpl.find(typePubl+"|"+noBr,lengths,1);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameFI();
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        jComboBox3.setSelectedIndex(0);
        setNameFI();
        jComboBox3.setEnabled(true);
        setButtonEnabled();
        jList1.clearSelection();
      }
      trans=false;
    }
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    setNameFI();
    String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
    String noBr = ((String)jComboBox2.getSelectedItem()).trim();
    String field = ((String)jComboBox3.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      String select = "select distinct POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select distinct PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+typePubl+"' and PPO_PO_POLJEID= '"+(String)jComboBox3.getSelectedItem()+"') and PO_POLJEID='"+field+"' order by POTPOLJAID";
      dblm.reloadItems(select,where);
      int[] lengths = {typePubl.length(),noBr.length(),field.length(),0};
      int i = dbmsfpl.find(typePubl+"|"+noBr+"|"+field,lengths,2);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
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
        // if(!oldField.equals(((String)jComboBox3.getSelectedItem()).trim())) {
          String select = "select distinct POTPOLJAID, PP_NAZIV from POTPOLJA";
          String where=  " where POTPOLJAID in (select distinct PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+typePubl+"' and PPO_PO_POLJEID= '"+(String)jComboBox3.getSelectedItem()+"') and PO_POLJEID='"+field+"' order by POTPOLJAID";
          dblm.reloadItems(select,where);
        //}
      }
    }
    //oldField = ((String)jComboBox3.getSelectedItem()).trim();
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmsfpl.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmsfpl.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmsfpl.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmsfpl.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmsfpl.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("SFPROCLEVDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("SFPROCLEVDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
