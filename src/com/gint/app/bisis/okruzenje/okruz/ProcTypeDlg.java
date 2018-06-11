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
import java.util.*;

public class ProcTypeDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox3 = new JComboBox();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JComboBox jComboBox5 = new JComboBox();
  JScrollPane spane = new JScrollPane();
  JList jList1 = new JList();
  JLabel jLabel7 = new JLabel();
  Connection conn;
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
  JButton jbRemove = new JButton();
  Border border1;
  Border border2;
  int row;
  boolean first;
  boolean trans;
  int mod;
  String oldTP="";
  String oldNOBr="";
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBComboModel dbcm4;
  DBComboModel dbcm5;
  DBListModel dblm;
  DBMProcType dbmpt;
  DBMPrefProcTp dbmppt;
  int countPref;
  Object[] prefix = new Object[5];
  DBMLibrarian dbmli;
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();

  public ProcTypeDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    xYLayout1.setHeight(510);
    xYLayout1.setWidth(610);
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
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
    jLabel1.setText(Messages.get("PROCTYPEDLG_LABEL_LIBRARIAN_CODE"));
    jLabel2.setText(Messages.get("PROCTYPEDLG_LABEL_PUBLICATION_TYPE"));
    jLabel3.setText(Messages.get("PROCTYPEDLG_LABEL_PROCESS_LEVEL"));
    jLabel4.setText(Messages.get("PROCTYPEDLG_LABEL_MANDATORY_LEVEL"));
    jLabel5.setText(Messages.get("PROCTYPEDLG_LABEL_15"));
    jLabel6.setText(Messages.get("PROCTYPEDLG_LABEL_FORMAT_CODE"));
    jComboBox5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox5_actionPerformed(e);
      }
    });
    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jLabel7.setText(Messages.get("PROCTYPEDLG_LABEL_PREFIX_CODE"));
    jbPreview.setText(Messages.get("PROCTYPEDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("PROCTYPEDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("PROCTYPEDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("PROCTYPEDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("PROCTYPEDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("PROCTYPEDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("PROCTYPEDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("PROCTYPEDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("PROCTYPEDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("PROCTYPEDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    jbRemove.setText(Messages.get("PROCTYPEDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jLabel8.setText(Messages.get("PROCTYPEDLG_LABEL_18"));
    jLabel9.setText(Messages.get("PROCTYPEDLG_LABEL_19"));
    jLabel10.setText(Messages.get("PROCTYPEDLG_LABEL_10"));
    jLabel11.setText(Messages.get("PROCTYPEDLG_LABEL_11"));
    getContentPane().add(panel1);
    panel1.add(jComboBox1, new XYConstraints(130, 20, 220, 22));
    panel1.add(jComboBox2, new XYConstraints(130, 60, 70, 22));
    panel1.add(jComboBox3, new XYConstraints(130, 100, 70, 22));
    panel1.add(jComboBox4, new XYConstraints(130, 140, 70, 22));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 60, -1, -1));
    panel1.add(jLabel3, new XYConstraints(15, 100, -1, -1));
    panel1.add(jLabel4, new XYConstraints(15, 140, -1, -1));
    panel1.add(jLabel6, new XYConstraints(15, 220, -1, -1));
    panel1.add(jComboBox5, new XYConstraints(130, 220, 100, 22));
    panel1.add(spane, new XYConstraints(130, 260, 210, 120));
    panel1.add(jLabel7, new XYConstraints(15, 260, -1, -1));
    panel1.add(jLabel5, new XYConstraints(20, 180, 900, 20));
    panel1.add(jbPreview, new XYConstraints(410, 400, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 400, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 400, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 447, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 400, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 400, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 400, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 442, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    jPanel1.add(jbRemove, new XYConstraints(202, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 442, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel8, new XYConstraints(235, 70, 270, -1));
    panel1.add(jLabel9, new XYConstraints(235, 100, 270, -1));
    panel1.add(jLabel10, new XYConstraints(235, 140, 270, -1));
    panel1.add(jLabel11, new XYConstraints(260, 220, 270, -1));
    spane.getViewport().add(jList1, null);
    spane.getViewport().setView(jList1);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    jList1.clearSelection();
    setResizable(false);

    row = 0;
    first = true;
    trans=true;
    dbmpt = new DBMProcType(conn);

    dbmppt = new DBMPrefProcTp(conn);

    dbcm1 = new DBComboModel(conn, "select distinct BIBID from BIBLIOTEKAR order by BIBID", "");
    jComboBox1.setModel(dbcm1);
    //dodato da bi imali selektovan prvi u listi
    jComboBox1.setSelectedIndex(0);

    String where = " where BIBID= " +"'"+((String)jComboBox1.getSelectedItem()).trim()+"'";
    dbmli = new DBMLibrarian(conn, where);

    dbcm2 = new DBComboModel(conn, "select distinct nob.NOB_TP_TIPID,tp.TIP_NAZIV from NIVO_OBAVEZNOSTI nob, TIP_PUBLIKACIJE tp"+
                              " where tp.TIPID=nob.NOB_TP_TIPID order by nob.NOB_TP_TIPID", "",true);
    jComboBox2.setModel(dbcm2);

    dbcm5 = new DBComboModel(conn, "select distinct FORMATID,FOR_OPIS from FORMATF order by FORMATID",true,true);
    jComboBox5.setModel(dbcm5);

    dblm = new DBListModel(conn, "select distinct POLJEBPRID, PBPR_NAZIV from POLJEBPR order by POLJEBPRID");
    jList1.setModel(dblm);
    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jList1.clearSelection();

    if (dbmpt.getRowCount() == 0) {
      dbcm3 = new DBComboModel(conn, "select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob, NIVO_OBRADE nobr",
                                " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID = '"+(String)jComboBox2.getSelectedItem()+
      				"' order by nob.NOB_NOBRID",true);
      jComboBox3.setModel(dbcm3);
      jComboBox3.setSelectedIndex(0);
      dbcm4 = new DBComboModel(conn, "select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI",
      			        " where NOB_TP_TIPID = '"+(String)jComboBox2.getSelectedItem()+"'"+
                                "and NOB_NOBRID = '"+(String)jComboBox3.getSelectedItem()+
                                "' order by NOBAVID",true);
      jComboBox4.setModel(dbcm4);
      jComboBox4.setSelectedIndex(0);
      oldTP=((String)jComboBox2.getSelectedItem()).trim();
      oldNOBr=((String)jComboBox3.getSelectedItem()).trim();
      mod=INSERT;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      dbcm3 = new DBComboModel(conn, "select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob,NIVO_OBRADE nobr",
                                " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID = '"+(String)dbmpt.getValueAt(row,1)+
      				"' order by nob.NOB_NOBRID",true);
      jComboBox3.setModel(dbcm3);
      dbcm4 = new DBComboModel(conn, "select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI",
      			        " where NOB_TP_TIPID = '"+(String)dbmpt.getValueAt(row,1)+"'"+
                                " and NOB_NOBRID = '"+(String)dbmpt.getValueAt(row,2)+
                                "' order by NOBAVID",true);
      jComboBox4.setModel(dbcm4);
      jComboBox4.setSelectedIndex(0);
      oldTP=((String)dbmpt.getValueAt(row,1)).trim();
      oldNOBr=((String)dbmpt.getValueAt(row,2)).trim();
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    first=false;
    trans=false;
    setNameTP();
    setNameForm();
    setNameNO();
    setNameNObav();
  }
  void setNameTP() {
    jLabel8.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameForm() {
    jLabel11.setText((String)dbcm5.getNameAt(jComboBox5.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel9.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameNObav() {
    jLabel10.setText((String)dbcm4.getNameAt(jComboBox4.getSelectedIndex()));
  }

  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jbFirst.setEnabled(true);
                      jbPrev.setEnabled(true);
                      jbNext.setEnabled(true);
                      jbLast.setEnabled(true);
                      jbPreview.setEnabled(true);
                      jbChange.setEnabled(true);
                      jbRemove.setEnabled(true);
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
                    jbRemove.setEnabled(false);
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
                      jComboBox5.setEnabled(false);
                      jList1.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jList1.setEnabled(true);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    jComboBox5.setEnabled(true);
                    break;
      case UPDATE : jList1.setEnabled(true);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    jComboBox5.setEnabled(true);
                    break;
      case DELETE : jList1.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    jComboBox5.setEnabled(false);
                    break;
    }
  }

  void setValues(int row) {
    String librar = (((String)dbmpt.getValueAt(row,0)).trim());
    String typePubl = ((String)dbmpt.getValueAt(row,1));
    String noBr = ((String)dbmpt.getValueAt(row,2));
    String noBav = ((String)dbmpt.getValueAt(row,3));
    String form = (((String)dbmpt.getValueAt(row,4)).trim());
    jComboBox1.setSelectedItem(librar);
    jComboBox2.setSelectedItem(typePubl);
    jComboBox3.setSelectedItem(noBr);
    jComboBox4.setSelectedItem(noBav);
    jComboBox5.setSelectedItem(form);
    Vector defProcType = dbmli.getCurrValues();
    jLabel5.setText(Messages.get("PROCTYPEDLG_LABEL_15_DEFAULT_LIBRARIAN")+" "+defProcType.elementAt(0)+" "+Messages.get("PROCTYPEDLG_LABEL_15_DEFAULT_LIBRARIAN_ARE")+" "+
                     defProcType.elementAt(1)+"  "+defProcType.elementAt(2)+"  "+defProcType.elementAt(3));
    jList1.clearSelection();
    countPref = 0;
    int k;
    String pref = "";
    for (int j=0; j < dblm.getSize(); j++) {
      pref = (String)dblm.getElementAt(j);
      k = dbmppt.findKey(librar.trim()+"|"+typePubl.trim()+"|"+noBr.trim()+"|"+noBav.trim()+"|"+pref.substring(0,pref.indexOf("-")));
      if(k != -1) {
        countPref++;
        jList1.addSelectionInterval(j,j);
      }
    }
  }

  void setValuesDB(int row,int mod) {
   if(mod == INSERT) {
      dbmpt.setValues(0, ((String)jComboBox1.getSelectedItem()).trim(), row, mod);
      dbmpt.setValues(1, ((String)jComboBox2.getSelectedItem()).trim(), row, mod);
      dbmpt.setValues(2, ((String)jComboBox3.getSelectedItem()).trim(), row, mod);
      dbmpt.setValues(3, ((String)jComboBox4.getSelectedItem()).trim(), row, mod);
      dbmpt.setValues(5, ((String)jComboBox1.getSelectedItem()).trim()+"|"+((String)jComboBox2.getSelectedItem()).trim()+"|"+((String)jComboBox3.getSelectedItem()).trim()+"|"+((String)jComboBox4.getSelectedItem()).trim(), row, mod);
    }
    dbmpt.setValues(4, ((String)jComboBox5.getSelectedItem()).trim(), row, mod);
  }

  boolean isDefault() {
    Vector defProcType = dbmli.getCurrValues();
    if(!((String)defProcType.elementAt(1)).trim().equals(((String)jComboBox2.getSelectedItem()).trim()))
      return false;
    if(!((String)defProcType.elementAt(2)).trim().equals(((String)jComboBox3.getSelectedItem()).trim()))
      return false;
    if(!((String)defProcType.elementAt(3)).trim().equals(((String)jComboBox4.getSelectedItem()).trim()))
      return false;
    return true;
  }

  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmpt.getRowCount()-1;
    setValues(row);
    trans = false;
  }

  void next() {
    if(row == dbmpt.getRowCount()-1)
      return;
    else {
      trans = true;
      row = row+1;
      setValues(row);
      trans = false;
    }
  }

  void prev() {
    if(row == 0)
      return;
    else {
      trans = true;
      row = row-1;
      setValues(row);
      trans = false;
    }
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FProcType fpt = new FProcType(null,Messages.get("PROCTYPEDLG_FPROCTYPE_FIND"),true,conn,this);
    fpt.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fpt.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fpt.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fpt.setVisible(true);
  }
  void jbChange_actionPerformed(ActionEvent e) {
    update();
  }
  void update() {
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
    if (dbmpt.getRowCount() == 0) {
      mod = UNDEFINED;
      dispose();
    }
    else {
      mod = UNDEFINED;
      trans=true;
      setValues(row);
      trans=false;
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
    String form = ((String)jComboBox5.getSelectedItem()).trim();
    String noBav = ((String)jComboBox4.getSelectedItem()).trim();
    String noBr = ((String)jComboBox3.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
    String librar = ((String)jComboBox1.getSelectedItem());
    Vector keys = new Vector();
    keys.addElement(librar);
    keys.addElement(typePubl);
    keys.addElement(noBr);
    keys.addElement(noBav);
    keys.addElement(form);
    int[] lengths = {librar.length(),typePubl.length(),noBr.length(),noBav.length(),form.length()};
    int[] lengths1 = {librar.length(),typePubl.length(),noBr.length(),noBav.length()};
    if(mod == INSERT) {
      if(jList1.getSelectedValues().length == 5) {
        int i = dbmpt.findPos(librar,typePubl,noBr,noBav,form);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("PROCTYPEDLG_OPTION_PANE_PROCESS_TYPE_EXIST"),Messages.get("PROCTYPEDLG_OPTION_PANE_PROCESS_TYPE_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else {
          row = i;
        }
        setValuesDB(row,mod);
        dbmpt.saveToDB(mod,librar,typePubl,noBr,noBav,form,row);
        int j = dbmppt.findPos(librar,typePubl,noBr,noBav);
        dbmppt.saveToDB(mod,librar,typePubl,noBr,noBav,jList1.getSelectedValues(),j);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("PROCTYPEDLG_OPTION_PANE_SELECTED_PREFIX"),Messages.get("PROCTYPEDLG_OPTION_PANE_SELECTED_PREFIX_WARNING"),JOptionPane.INFORMATION_MESSAGE);
        setButtonEnabled();
        setComponentEnabled();
      }
    }
    else
      if(mod == UPDATE) {
        if(jList1.getSelectedValues().length == 5) {
          setValuesDB(row,mod);
          dbmpt.saveToDB(mod,librar,typePubl,noBr,noBav,form,row);
          int j = dbmppt.find(librar+"|"+typePubl+"|"+noBr+"|"+noBav,lengths1,3);
          for(int k = 0; k < jList1.getSelectedValues().length; k++) {
            dbmppt.removeRow(j);
          }
          dbmppt.saveToDB(mod,librar,typePubl,noBr,noBav,jList1.getSelectedValues(),j);
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("PROCTYPEDLG_OPTION_PANE_SELECTED_PREFIX_1"),Messages.get("PROCTYPEDLG_OPTION_PANE_SELECTED_PREFIX_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
          setButtonEnabled();
          setComponentEnabled();
        }
      }
    else {
      if(mod == DELETE) {
        if (dbmpt.getRowCount() == 1) {
          row = 0;
          int ret = JOptionPane.showConfirmDialog(null,Messages.get("PROCTYPEDLG_OPTION_PANE_REMOVE_LAST"),Messages.get("PROCTYPEDLG_OPTION_PANE_REMOVE_LAST_WARNING"),JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
              dbmpt.saveToDB(mod,librar,typePubl,noBr,noBav,form,row);
              dbmpt.removeRow(row);
              int j = dbmppt.find(librar+"|"+typePubl+"|"+noBr+"|"+noBav,lengths1,3);
              dbmppt.saveToDB(mod,librar,typePubl,noBr,noBav,jList1.getSelectedValues(),j);
              for(int k = 0; k < jList1.getSelectedValues().length; k++) {
                dbmppt.removeRow(j);
              }
              dispose();
            }
            if (ret == JOptionPane.NO_OPTION) {
              trans=true;
              setValues(row);
              trans=false;
            }
        }
        else {
          if (row == dbmpt.getRowCount()-1) {
            dbmpt.saveToDB(mod,librar,typePubl,noBr,noBav,form,row);
            dbmpt.removeRow(row);
            int j = dbmppt.find(librar+"|"+typePubl+"|"+noBr+"|"+noBav,lengths1,3);
            dbmppt.saveToDB(mod,librar,typePubl,noBr,noBav,jList1.getSelectedValues(),j);
            for(int k = 0; k < jList1.getSelectedValues().length; k++) {
              dbmppt.removeRow(j);
            }
            row=row-1;
            trans = true;
            setValues(row);
            trans = false;
          }
          else {
            dbmpt.saveToDB(mod,librar,typePubl,noBr,noBav,form,row);
            dbmpt.removeRow(row);
            int j = dbmppt.find(librar+"|"+typePubl+"|"+noBr+"|"+noBav,lengths1,3);
            dbmppt.saveToDB(mod,librar,typePubl,noBr,noBav,jList1.getSelectedValues(),j);
            for(int k = 0; k < jList1.getSelectedValues().length; k++) {
              dbmppt.removeRow(j);
            }
            trans = true;
            setValues(row);
            trans = false;
          }
          mod = UNDEFINED;
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
  void jbRemove_actionPerformed(ActionEvent e) {
    delete();
  }
  void delete() {
    if(!isDefault()) {
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PROCTYPEDLG_OPTION_PANE_DEFAULT_PROCESS_TYPE_CANT_REMOVE"),Messages.get("PROCTYPEDLG_OPTION_PANE_DEFAULT_PROCESS_TYPE_CANT_REMOVE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return;
    }
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    if (!first) {
      String where = " where BIBID= " +"'"+((String)jComboBox1.getSelectedItem()).trim()+"'";
      dbmli.reloadValues(where);
      if(!trans) {
      	trans=true;
        String librar = ((String)jComboBox1.getSelectedItem()).trim();
        int[] lengths = {librar.length(),0,0,0,0};
        int i = dbmpt.find(librar,lengths,0);
        if(i != -1) {
          row = i;
          setValues(row);
          jbNext.requestFocus();
          setNameTP();
          setNameForm();
          setNameNO();
          setNameNObav();
        }
        else {
          mod = INSERT;
          setButtonEnabled();
          setComponentEnabled();
          jComboBox2.setSelectedIndex(0);
          jComboBox3.setSelectedIndex(0);
          jComboBox4.setSelectedIndex(0);
          jComboBox5.setSelectedIndex(0);
          setNameTP();
          setNameForm();
          setNameNO();
          setNameNObav();
          Vector defProcType = dbmli.getCurrValues();
          jLabel5.setText(Messages.get("PROCTYPEDLG_LABEL_15_DEFAULT_LIBRARIAN_1")+" "+defProcType.elementAt(0)+" "+Messages.get("PROCTYPEDLG_LABEL_15_DEFAULT_LIBRARIAN_ARE_1")+" "+
                            defProcType.elementAt(1)+"  "+defProcType.elementAt(2)+"  "+defProcType.elementAt(3));
          jComboBox2.setEnabled(true);
          jComboBox3.setEnabled(true);
          jComboBox4.setEnabled(true);
          jList1.clearSelection();
      	}
      	trans=false;
      }
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameTP();
    String librar = ((String)jComboBox1.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm3.reloadItems("select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob,NIVO_OBRADE nobr"+
                        " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID="+"'"+typePubl+
      			"' order by nob.NOB_NOBRID",true);
      String noBr = ((String)jComboBox3.getSelectedItem()).trim();
      dbcm4.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"+
                        " where NOB_TP_TIPID="+"'"+typePubl+"'"+
                        " and NOB_NOBRID= '"+noBr+
                        "' order by NOBAVID",true);
      int[] lenghts = {librar.length(),typePubl.length(),0,0,0};
      int i = dbmpt.find(librar+"|"+typePubl,lenghts,1);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameNO();
        setNameNObav();
      }
      else {
        mod = INSERT;
        setButtonEnabled();
        setComponentEnabled();
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        setNameNO();
        setNameNObav();
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
        jList1.clearSelection();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldTP.equals(((String)jComboBox2.getSelectedItem()).trim())) {
          dbcm3.reloadItems("select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob,NIVO_OBRADE nobr"+
                            " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID="+"'"+typePubl+
                            "' order by nob.NOB_NOBRID",true);
          String noBr = ((String)jComboBox3.getSelectedItem()).trim();
          dbcm4.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"+
                            " where NOB_TP_TIPID="+"'"+typePubl+"'"+
                            " and NOB_NOBRID= '"+noBr+
                            "' order by NOBAVID",true);
        //}
      }
    }
    //oldTP = ((String)jComboBox2.getSelectedItem()).trim();
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    setNameNO();
    String librar = ((String)jComboBox1.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
    String noBr = ((String)jComboBox3.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm4.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"+
                        " where NOB_TP_TIPID="+"'"+typePubl+"'"+
                        " and NOB_NOBRID= '"+noBr+
                        "' order by NOBAVID",true);
      int[] lengths = {librar.length(),typePubl.length(),noBr.length(),0,0};
      int i = dbmpt.find(librar+"|"+typePubl+"|"+noBr,lengths,2);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameNObav();
      }
      else {
        mod = INSERT;
        setButtonEnabled();
        setComponentEnabled();
        jComboBox4.setSelectedIndex(0);
        setNameNObav();
        jComboBox4.setEnabled(true);
      }
      trans=false;
    }
    else {
      if(!first) {
        // if(!oldNOBr.equals(((String)jComboBox3.getSelectedItem()).trim())) {
            dbcm4.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"+
                              " where NOB_TP_TIPID="+"'"+typePubl+"'"+
                              " and NOB_NOBRID= '"+noBr+
                              "' order by NOBAVID",true);
        //}
      }
    }
    //oldNOBr=((String)jComboBox3.getSelectedItem()).trim();
  }

  void jComboBox4_actionPerformed(ActionEvent e) {
    setNameNObav();
    if(!trans) {
      String librar = ((String)jComboBox1.getSelectedItem()).trim();
      String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
      String noBr = ((String)jComboBox3.getSelectedItem()).trim();
      String noBav = ((String)jComboBox4.getSelectedItem()).trim();
      int[] lengths = {librar.length(),typePubl.length(),noBr.length(),noBav.length(),0};
      trans=true;
      int i = dbmpt.find(librar+"|"+typePubl+"|"+noBr+"|"+noBav,lengths,3);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
      }
      else {
        mod = INSERT;
        setButtonEnabled();
        setComponentEnabled();
        jList1.clearSelection();
      }
      trans=false;
    }
  }

  void jComboBox5_actionPerformed(ActionEvent e) {
    setNameForm();
  }
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmppt.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmppt.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmppt.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmppt.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmppt.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmppt.getRowCount() !=0) {
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

  protected void processWindowEvent(WindowEvent e){
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      if(mod != 0) {
        JOptionPane.showMessageDialog(null,Messages.get("PROCTYPEDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("PROCTYPEDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
