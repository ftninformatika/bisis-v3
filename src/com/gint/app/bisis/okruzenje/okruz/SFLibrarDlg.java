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
import java.util.*;
import com.gint.app.bisis.editor.edit.validation.Validator;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;

public class SFLibrarDlg extends JDialog {
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox3 = new JComboBox();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbPreview = new JButton();
  JButton jbChange = new JButton();
  JButton jbLast = new JButton();
  JButton jbRemove = new JButton();
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
  int row;
  boolean trans;
  int mod;
  boolean first;
  String oldTP="";
  String oldField="";
  DBMSFLibrar dbmsfli;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBComboModel dbcm4;
  private Validator myValidator;
 // private UFieldSet ufs;
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();

  //public SFLibrarDlg(Frame frame, String title, boolean modal, Connection conn, Validator myValidator, UFieldSet ufs) {
  public SFLibrarDlg(Frame frame, String title, boolean modal, Connection conn, Validator myValidator) {
    super(frame, title, modal);
    this.conn = conn;
    this.myValidator = myValidator;
    //this.ufs = ufs;
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
    jLabel3.setText(Messages.get("SFLIBRARDLG_LABEL_FIELD_CODE"));
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
    jLabel4.setText(Messages.get("SFLIBRARDLG_LABEL_SUBFIELD_CODE"));
    jComboBox4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox4_actionPerformed(e);
      }
    });
    jLabel2.setText(Messages.get("SFLIBRARDLG_LABEL_PUBLICATION_TYPE"));
    jLabel1.setText(Messages.get("SFLIBRARDLG_LABEL_LIBRARIAN_CODE"));
    jbPreview.setText(Messages.get("SFLIBRARDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("SFLIBRARDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("SFLIBRARDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("SFLIBRARDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("SFLIBRARDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("SFLIBRARDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("SFLIBRARDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("SFLIBRARDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("SFLIBRARDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("SFLIBRARDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("SFLIBRARDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    jLabel5.setText(Messages.get("SFLIBRARDLG_LABEL_DEFAULT"));
    jLabel6.setText(Messages.get("SFLIBRARDLG_LABEL_VALUE"));
    getContentPane().add(panel1);
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(jComboBox2, new XYConstraints(150, 70, 70, 22));
    panel1.add(jComboBox3, new XYConstraints(150, 120, 80, 22));
    panel1.add(jLabel4, new XYConstraints(15, 170, -1, -1));
    panel1.add(jTextField1, new XYConstraints(150, 220, 120, 20));
    panel1.add(jComboBox4, new XYConstraints(150, 170, 60, 22));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(150, 20, 220, 22));
    panel1.add(jbPreview, new XYConstraints(410, 340, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 340, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 340, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 387, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 340, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 340, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 340, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 382, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    jPanel1.add(jbRemove, new XYConstraints(202, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 382, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel5, new XYConstraints(15, 210, -1, -1));
    panel1.add(jLabel6, new XYConstraints(15, 230, -1, -1));
    panel1.add(jLabel7, new XYConstraints(255, 70, 250, -1));
    panel1.add(jLabel8, new XYConstraints(255, 120, 250, -1));
    panel1.add(jLabel9, new XYConstraints(255, 170, 250, -1));
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    row = 0;

    first = true;
    trans=true;

    dbmsfli = new DBMSFLibrar(conn);


    dbcm1 = new DBComboModel(conn,"select distinct BIBID from BIBLIOTEKAR order by BIBID ASC","");
    jComboBox1.setModel(dbcm1);

    dbcm2 = new DBComboModel(conn,"select distinct dt.TP_TIPID,tp.TIP_NAZIV from DEF_TIPA dt, TIP_PUBLIKACIJE tp"+
                              " where tp.TIPID=dt.TP_TIPID order by dt.TP_TIPID ASC","",true);
    jComboBox2.setModel(dbcm2);

    if (dbmsfli.getRowCount() == 0) {
      jTextField1.setText("");
      jComboBox1.setSelectedIndex(0);
      jComboBox2.setSelectedIndex(0);
      dbcm3 = new DBComboModel(conn,"select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p",
                                " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+((String)jComboBox2.getSelectedItem()).trim()+
      				"'  order by dt.PPO_PO_POLJEID",true);
      jComboBox3.setModel(dbcm3);
      jComboBox3.setSelectedIndex(0);
      String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+((String)jComboBox2.getSelectedItem()).trim()+"' and PPO_PO_POLJEID= '"+((String)jComboBox3.getSelectedItem()).trim()+"')"+" and PO_POLJEID='"+((String)jComboBox3.getSelectedItem()).trim()+"' order by POTPOLJAID";
      dbcm4 = new DBComboModel(conn,select,where,true);
      jComboBox4.setModel(dbcm4);
      //dodato da bi imali selektovan prvi u listi
      jComboBox4.setSelectedIndex(0);
      oldTP=((String)jComboBox2.getSelectedItem()).trim();
      oldField=((String)jComboBox3.getSelectedItem()).trim();
      mod=INSERT;
      setButtonEnabled();
    }
    else {
      dbcm3 = new DBComboModel(conn,"select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p",
      				" where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+(String)dbmsfli.getValueAt(row,1)+
      				"'  order by dt.PPO_PO_POLJEID",true);
      jComboBox3.setModel(dbcm3);
      String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+(String)dbmsfli.getValueAt(row,1)+"' and PPO_PO_POLJEID= '"+(String)dbmsfli.getValueAt(row,2)+"')"+" and PO_POLJEID='"+(String)dbmsfli.getValueAt(row,2)+"' order by POTPOLJAID";
      dbcm4 = new DBComboModel(conn,select,where,true);
      jComboBox4.setModel(dbcm4);
      //dodato da bi imali selektovan prvi u listi
      jComboBox4.setSelectedIndex(0);
      oldTP=((String)dbmsfli.getValueAt(row,1)).trim();
      oldField=((String)dbmsfli.getValueAt(row,2)).trim();
      mod=UNDEFINED;
      setValues(row);
      setButtonEnabled();
      setComponentEnabled();
    }
    trans=false;
    first=false;
    setNameTP();
    setNameFI();
    setNameSF();
  }
  void setNameTP() {
    jLabel7.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel8.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameSF() {
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
                      jTextField1.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jTextField1.setEnabled(true);
                    jTextField1.requestFocus();
                    jTextField1.setText("");
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
      case UPDATE : jTextField1.setEnabled(true);
                    jTextField1.requestFocus();
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                    break;
    }
  }

  void setValues(int row) {
    jComboBox1.setSelectedItem(((String)dbmsfli.getValueAt(row,0)).trim());
    jComboBox2.setSelectedItem(dbmsfli.getValueAt(row,1));
    jComboBox3.setSelectedItem(dbmsfli.getValueAt(row,2));
    jComboBox4.setSelectedItem(dbmsfli.getValueAt(row,3));
    jTextField1.setText((String)dbmsfli.getValueAt(row,4));
  }

  boolean checkValues() {
    if(jTextField1.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("SFLIBRARDLG_OPTION_PANE_DEFAULT_VALUE"),Messages.get("SFLIBRARDLG_OPTION_PANE_DEFAULT_VALUE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextField1.getText().length() > 15) {
      JOptionPane.showMessageDialog(null,Messages.get("SFLIBRARDLG_OPTION_PANE_DEFAULT_VALUE_CHAR_LIMIT"),Messages.get("SFLIBRARDLG_OPTION_PANE_DEFAULT_VALUE_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

//  boolean checkValid(String f, String sf, String content) {
  boolean checkValid(Field f, Subfield sf, String content) {
    try {
      //myValidator.isValid(f, sf, content, ufs);
      myValidator.isValid(f, sf, content);
       return true;
    } catch (Exception ex) {
      String errorMessage = ex.toString().substring(ex.toString().indexOf(':')+1);
      JOptionPane.showMessageDialog(null, errorMessage, Messages.get("SFLIBRARDLG_OPTION_PANE_INVALID_VALUE"), JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  void setValuesDB(int row,int mod) {
   if(mod == INSERT) {
      dbmsfli.setValues(0, ((String)jComboBox1.getSelectedItem()).trim(), row, mod);
      dbmsfli.setValues(1, ((String)jComboBox2.getSelectedItem()).trim(), row, mod);
      dbmsfli.setValues(2, ((String)jComboBox3.getSelectedItem()).trim(), row, mod);
      dbmsfli.setValues(3, ((String)jComboBox4.getSelectedItem()).trim(), row, mod);
      dbmsfli.setValues(5, ((String)jComboBox1.getSelectedItem()).trim()+"|"+((String)jComboBox2.getSelectedItem()).trim()+"|"+((String)jComboBox3.getSelectedItem()).trim()+"|"+((String)jComboBox4.getSelectedItem()).trim(), row, mod);
    }
    dbmsfli.setValues(4, jTextField1.getText().trim(), row, mod);
  }


  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmsfli.getRowCount()-1;
    setValues(row);
    trans = false;
  }

  void next() {
    trans = true;
    if(row != dbmsfli.getRowCount()-1)
      row = row+1;
    else
      row = dbmsfli.getRowCount()-1;
    setValues(row);
    trans = false;
  }

  void prev() {
    trans = true;
    if(row != 0)
      row = row-1;
    else
      row = 0;
    setValues(row);
    trans = false;
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FSFLibrar fsfli = new FSFLibrar(null,Messages.get("SFLIBRARDLG_FSFLIBRAR_FIND"),true,conn,this);
    fsfli.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fsfli.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fsfli.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fsfli.setVisible(true);
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
  void jbRemove_actionPerformed(ActionEvent e) {
    delete();
  }
  void delete() {
    if(dbmsfli.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("SFLIBRARDLG_OPTION_PANE_REMOVE_LAST"),Messages.get("SFLIBRARDLG_OPTION_PANE_REMOVE_LAST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    mod = DELETE;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmsfli.getRowCount() == 0) {
      mod = UNDEFINED;
      dispose();
    }
    else {
      trans = true;
      setValues(row);
      trans = false;
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
    String subField = ((String)jComboBox4.getSelectedItem()).trim();
    String field = ((String)jComboBox3.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
    String librar = ((String)jComboBox1.getSelectedItem());
    Vector keys = new Vector();
    keys.addElement(librar);
    keys.addElement(typePubl);
    keys.addElement(field);
    keys.addElement(subField);
    int[] lengths = {librar.length(),typePubl.length(),field.length(),subField.length()};
    Subfield sf = new Subfield(subField,jTextField1.getText().trim());
    Vector vsf = new Vector();
    vsf.add(sf);
    Field fi = new Field(field,"","",vsf);
    if(mod == INSERT) {
//      if(checkValues() && checkValid(field,subField,jTextField1.getText().trim())) {
      if(checkValues() && checkValid(fi,sf,jTextField1.getText().trim())) {
        int i = dbmsfli.findPos(librar,typePubl,field,subField);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("SFLIBRARDLG_OPTION_PANE_LIBRARIAN_PUBLICATION_FIELD_SUBFIELD_EXIST"),Messages.get("SFLIBRARDLG_OPTION_PANE_LIBRARIAN_PUBLICATION_FIELD_SUBFIELD_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else {
          row = i;
        }
        setValuesDB(row,mod);
        dbmsfli.saveToDB(mod,keys,keys.size(),row);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
      else {
        setButtonEnabled();
        setComponentEnabled();
      }
    }
    else {
      if(mod == UPDATE) {
        //if(checkValues() && checkValid(field,subField,jTextField1.getText().trim())) {
        if(checkValues() && checkValid(fi,sf,jTextField1.getText().trim())) {
          setValuesDB(row,mod);
          dbmsfli.saveToDB(mod,keys,keys.size(),row);
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
        }
        else {
          setButtonEnabled();
          setComponentEnabled();
        }
      }
      else {
        if(mod == DELETE) {
          dbmsfli.saveToDB(mod,keys,keys.size(),row);
          dbmsfli.removeRow(row);
          if(dbmsfli.getRowCount()==0) {
            dispose();
          }
          else {
            if (row == dbmsfli.getRowCount()) {
              row=row-1;
              trans = true;
              setValues(row);
              trans = false;
            }
            else {
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
    if(!trans) {
      trans=true;
      String librar = ((String)jComboBox1.getSelectedItem()).trim();
      int[] lengths = {librar.length(),0,0,0};
      int i = dbmsfli.find(librar,lengths,0);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameTP();
        setNameFI();
        setNameSF();
      }
      else {
        mod = INSERT;
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        setNameTP();
        setNameFI();
        setNameSF();
        setButtonEnabled();
        setComponentEnabled();
        jComboBox2.setEnabled(true);
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
      }
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameTP();
    String librar = ((String)jComboBox1.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm3.reloadItems("select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p"+
                        " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+typePubl+
                        "'  order by dt.PPO_PO_POLJEID",true);
      String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+typePubl+"' and PPO_PO_POLJEID= '"+((String)jComboBox3.getSelectedItem()).trim()+"')"+" and PO_POLJEID='"+((String)jComboBox3.getSelectedItem()).trim()+"' order by POTPOLJAID";
      dbcm4.reloadItems(select+where,true);
      int[] lengths = {librar.length(),typePubl.length(),0,0};
      int i = dbmsfli.find(librar+"|"+typePubl,lengths,1);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameFI();
        setNameSF();
      }
      else {
        mod = INSERT;
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        setNameFI();
        setNameSF();
        setButtonEnabled();
        setComponentEnabled();
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldTP.equals(((String)jComboBox2.getSelectedItem()).trim())) {
          dbcm3.reloadItems("select distinct dt.PPO_PO_POLJEID,p.PO_NAZIV from DEF_TIPA dt, POLJE p"+
                              " where p.POLJEID=dt.PPO_PO_POLJEID and dt.TP_TIPID = '"+typePubl+
                              "'  order by dt.PPO_PO_POLJEID",true);
          String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
          String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+typePubl+"' and PPO_PO_POLJEID= '"+((String)jComboBox3.getSelectedItem()).trim()+"')"+" and PO_POLJEID='"+((String)jComboBox3.getSelectedItem()).trim()+"' order by POTPOLJAID";
          dbcm4.reloadItems(select+where,true);
     	  //}
      }
    }
//  oldTP = ((String)jComboBox2.getSelectedItem()).trim();
  }

  void jComboBox3_actionPerformed(ActionEvent e) {
    setNameFI();
    String librar = ((String)jComboBox1.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
    String field = ((String)jComboBox3.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
      String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+typePubl+"' and PPO_PO_POLJEID= '"+field+"')"+" and PO_POLJEID='"+field+"' order by POTPOLJAID";
      dbcm4.reloadItems(select+where,true);
      int[] lengths = {librar.length(),typePubl.length(),field.length(),0};
      int i = dbmsfli.find(librar+"|"+typePubl+"|"+field,lengths,2);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameSF();
      }
      else {
        mod = INSERT;
        jComboBox4.setSelectedIndex(0);
        setNameSF();
        setButtonEnabled();
        setComponentEnabled();
        jComboBox4.setEnabled(true);
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldField.equals(((String)jComboBox3.getSelectedItem()).trim())) {
          String select = "select POTPOLJAID, PP_NAZIV from POTPOLJA";
          String where=  " where POTPOLJAID in (select PPO_POTPOLJAID from DEF_TIPA where TP_TIPID= '"+typePubl+"' and PPO_PO_POLJEID= '"+field+"')"+" and PO_POLJEID='"+field+"' order by POTPOLJAID";
          dbcm4.reloadItems(select+where,true);
        //}
      }
    }
    //oldField = ((String)jComboBox3.getSelectedItem()).trim();
  }

  void jComboBox4_actionPerformed(ActionEvent e) {
    if(!trans) {
      trans=true;
      setNameSF();
      String librar = ((String)jComboBox1.getSelectedItem()).trim();
      String typePubl = ((String)jComboBox2.getSelectedItem()).trim();
      String field = ((String)jComboBox3.getSelectedItem()).trim();
      String subField = ((String)jComboBox4.getSelectedItem()).trim();
      int[] lengths = {librar.length(),typePubl.length(),field.length(),subField.length()};
      int i = dbmsfli.find(librar+"|"+typePubl+"|"+field+"|"+subField,lengths,3);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
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
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmsfli.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmsfli.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmsfli.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmsfli.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmsfli.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmsfli.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("SFLIBRARDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("SFLIBRARDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
