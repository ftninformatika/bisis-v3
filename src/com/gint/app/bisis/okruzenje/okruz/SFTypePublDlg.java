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
import java.sql.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import com.gint.app.bisis.okruzenje.dbmodel.*;
import java.util.*;
import com.gint.app.bisis.editor.edit.validation.Validator;
//import com.gint.app.bisis.editor.UFieldSet.UFieldSet;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;

public class SFTypePublDlg extends JDialog {
  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  Connection conn;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbChange = new JButton();
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
  JButton jbLast = new JButton();
  JButton jbPreview = new JButton();
  JLabel jlTypePubl = new JLabel();
  JComboBox jcbTypePubl = new JComboBox();
  JLabel jlFiled = new JLabel();
  JLabel jlSubField = new JLabel();
  JComboBox jcbField = new JComboBox();
  JComboBox jcbSubField = new JComboBox();
  JTextField jtfDefVal = new JTextField();
  Border border1;
  Border border2;
  String oldField="";
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBMSFTypePubl dbmsftp;
  int row;
  int mod = UNDEFINED;
  boolean first;
  boolean trans;
  private Validator myValidator;
//  private UFieldSet ufs;
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();


  //public SFTypePublDlg(Frame frame, String title, boolean modal, Connection conn, Validator myValidator, UFieldSet ufs) {
  public SFTypePublDlg(Frame frame, String title, boolean modal, Connection conn, Validator myValidator) {
    super(frame, title, modal);
    this.conn=conn;
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
    xYLayout1.setHeight(365);
    xYLayout1.setWidth(610);
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jbChange.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    jbLast.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbPreview.setText(Messages.get("SFTYPEPUBLDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jlTypePubl.setText(Messages.get("SFTYPEPUBLDLG_LABEL_PUBLICATION_TYPE"));
    jlFiled.setText(Messages.get("SFTYPEPUBLDLG_LABEL_FIELD_CODE"));
    jlSubField.setText(Messages.get("SFTYPEPUBLDLG_LABEL_SUBFIELD_CODE"));
    jcbField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jcbField_actionPerformed(e);
      }
    });
    jcbTypePubl.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jcbTypePubl_actionPerformed(e);
      }
    });

    jcbSubField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jcbSubField_actionPerformed(e);
      }
    });
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jLabel1.setText(Messages.get("SFTYPEPUBLDLG_LABEL_DEFAULT"));
    jLabel2.setText(Messages.get("SFTYPEPUBLDLG_LABEL_VALUE"));

    getContentPane().add(panel1);
    panel1.add(jtfDefVal, new XYConstraints(120, 180, 120, 20));
    panel1.add(jbExit, new XYConstraints(515, 302, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 255, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 255, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 255, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 297, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    jPanel1.add(jbRemove, new XYConstraints(202, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 297, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jbLast, new XYConstraints(310, 255, 90, 25));
    panel1.add(jbPreview, new XYConstraints(410, 255, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 255, 90, 25));
    panel1.add(jlTypePubl, new XYConstraints(15, 20, -1, -1));
    panel1.add(jcbTypePubl, new XYConstraints(120, 20, 70, 22));
    panel1.add(jlFiled, new XYConstraints(15, 70, -1, -1));
    panel1.add(jlSubField, new XYConstraints(15, 120, -1, -1));
    panel1.add(jcbField, new XYConstraints(120, 70, 80, 22));
    panel1.add(jcbSubField, new XYConstraints(120, 120, 60, 22));
    panel1.add(jLabel1, new XYConstraints(15, 170, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 190, -1, -1));
    panel1.add(jLabel3, new XYConstraints(245, 20, 250, -1));
    panel1.add(jLabel4, new XYConstraints(245, 70, 120, -1));
    panel1.add(jLabel5, new XYConstraints(245, 120, 250, -1));
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    row = 0;

    first = true;
    trans = true;
    dbmsftp = new DBMSFTypePubl(conn);


    dbcm1 = new DBComboModel(conn,"select TIPID,TIP_NAZIV from TIP_PUBLIKACIJE order by TIPID ASC","",true);
    jcbTypePubl.setModel(dbcm1);

    dbcm2 = new DBComboModel(conn,"select distinct pp.PO_POLJEID,p.PO_NAZIV from POTPOLJA pp, POLJE p"+
                              " where p.POLJEID=pp.PO_POLJEID order by pp.PO_POLJEID ASC","",true);
    jcbField.setModel(dbcm2);


    if (dbmsftp.getRowCount() == 0) {
      jtfDefVal.setText("");
      jcbTypePubl.setSelectedIndex(0);
      jcbField.setSelectedIndex(0);
      oldField=((String)jcbField.getSelectedItem()).trim();
      dbcm3 = new DBComboModel(conn,"select distinct POTPOLJAID,PP_NAZIV from POTPOLJA"," where PO_POLJEID="+"'"+(String)jcbField.getSelectedItem()+"' order by POTPOLJAID ASC",true);
      jcbSubField.setModel(dbcm3);
      jcbSubField.setSelectedIndex(0);
      jtfDefVal.requestFocus();
      mod=INSERT;
      setButtonEnabled();
    }
    else {
      dbcm3 = new DBComboModel(conn,"select distinct POTPOLJAID,PP_NAZIV from POTPOLJA"," where PO_POLJEID="+"'"+(String)dbmsftp.getValueAt(row,1)+"' order by POTPOLJAID ASC",true);
      jcbSubField.setModel(dbcm3);
      oldField=((String)dbmsftp.getValueAt(row,1)).trim();
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    first = false;
    trans=false;
    setNameTP();
    setNameFI();
    setNameSF();
  }
  void setNameTP() {
    jLabel3.setText((String)dbcm1.getNameAt(jcbTypePubl.getSelectedIndex()));
  }
  void setNameFI() {
    jLabel4.setText((String)dbcm2.getNameAt(jcbField.getSelectedIndex()));
  }
  void setNameSF() {
    jLabel5.setText((String)dbcm3.getNameAt(jcbSubField.getSelectedIndex()));
  }
  void setValues(int row) {
    jcbTypePubl.setSelectedItem(dbmsftp.getValueAt(row,0));
    jcbField.setSelectedItem(dbmsftp.getValueAt(row,1));
    jcbSubField.setSelectedItem(dbmsftp.getValueAt(row,2));
    jtfDefVal.setText((String)dbmsftp.getValueAt(row,3));
  }

  boolean checkValues() {
    if(jtfDefVal.getText().length() > 15) {
      JOptionPane.showMessageDialog(null,Messages.get("SFTYPEPUBLDLG_OPTION_PANE_DEFAULT_VALUE_CHAR_LIMIT"),Messages.get("SFTYPEPUBLDLG_OPTION_PANE_DEFAULT_VALUE_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  //boolean checkValid(String f, String sf, String content) {
  boolean checkValid(Field f, Subfield sf, String content) {
    try {
//      myValidator.isValid(f, sf, content, ufs);
      myValidator.isValid(f, sf, content);
      return true;
    } catch (Exception ex) {
      String errorMessage = ex.toString().substring(ex.toString().indexOf(':')+1);
      JOptionPane.showMessageDialog(null, errorMessage, Messages.get("SFTYPEPUBLDLG_OPTION_PANE_INVALID_VALUE"), JOptionPane.ERROR_MESSAGE);
      return false;
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
      case UNDEFINED :jcbTypePubl.setEnabled(true);
                      jcbField.setEnabled(true);
                      jcbSubField.setEnabled(true);
                      jtfDefVal.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jtfDefVal.setEnabled(true);
                    jtfDefVal.requestFocus();
                    jtfDefVal.setText("");
                    jcbTypePubl.setEnabled(false);
                    jcbField.setEnabled(false);
                    jcbSubField.setEnabled(false);
                    break;
      case UPDATE : jtfDefVal.setEnabled(true);
                    jtfDefVal.requestFocus();
                    jcbTypePubl.setEnabled(false);
                    jcbField.setEnabled(false);
                    jcbSubField.setEnabled(false);
                    break;
      case DELETE : jtfDefVal.setEnabled(false);
                    jcbTypePubl.setEnabled(false);
                    jcbField.setEnabled(false);
                    jcbSubField.setEnabled(false);
                    break;
    }
  }

  void setValuesDB(int row,int mod) {
   if(mod == INSERT) {
      dbmsftp.setValues(0, ((String)jcbTypePubl.getSelectedItem()).trim(), row, mod);
      dbmsftp.setValues(1, ((String)jcbField.getSelectedItem()).trim(), row, mod);
      dbmsftp.setValues(2, ((String)jcbSubField.getSelectedItem()).trim(), row, mod);
      dbmsftp.setValues(4, ((String)jcbTypePubl.getSelectedItem()).trim()+"|"+((String)jcbField.getSelectedItem()).trim()+"|"+((String)jcbSubField.getSelectedItem()).trim(), row, mod);
    }
    dbmsftp.setValues(3, jtfDefVal.getText().trim(), row, mod);
  }

  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmsftp.getRowCount()-1;
    setValues(row);
    trans = false;
  }

  void next() {
    trans = true;
    if(row != dbmsftp.getRowCount()-1)
      row = row+1;
    else
      row = dbmsftp.getRowCount()-1;
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
  void jbChange_actionPerformed(ActionEvent e) {
    update();
  }
  void update() {
    mod = UPDATE;
    setComponentEnabled();
    setButtonEnabled();
  }
  void jbRemove_actionPerformed(ActionEvent e) {
    delete();
  }
  void delete() {
    if(dbmsftp.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("SFTYPEPUBLDLG_OPTION_PANE_REMOVE_LAST"),Messages.get("SFTYPEPUBLDLG_OPTION_PANE_REMOVE_LAST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    mod = DELETE;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmsftp.getRowCount() == 0) {
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
    String subField = ((String)jcbSubField.getSelectedItem()).trim();
    String field = ((String)jcbField.getSelectedItem()).trim();
    String typePubl = ((String)jcbTypePubl.getSelectedItem()).trim();
    Vector keys = new Vector();
    keys.addElement(typePubl);
    keys.addElement(field);
    keys.addElement(subField);
    int[] lengths = {typePubl.length(),field.length(),subField.length()};
    Subfield sf = new Subfield(subField,jtfDefVal.getText().trim());
    Vector vsf = new Vector();
    vsf.add(sf);
    Field fi = new Field(field,"","",vsf);
    if(mod == INSERT) {
     // if(checkValues() && checkValid(field,subField,jtfDefVal.getText().trim())) {
      if(checkValues() && checkValid(fi,sf,jtfDefVal.getText().trim())) {
        int i = dbmsftp.findPos(typePubl,field,subField);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("SFTYPEPUBLDLG_OPTION_PANE_PUBLICATION_FIELD_SUBFIELD_EXIST"),Messages.get("SFTYPEPUBLDLG_OPTION_PANE_PUBLICATION_FIELD_SUBFIELD_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else {
          row = i;
        }
        setValuesDB(row,mod);
        dbmsftp.saveToDB(mod,keys,keys.size(),row);
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
        //if(checkValues() && checkValid(field,subField,jtfDefVal.getText().trim())) {
        if(checkValues() && checkValid(fi,sf,jtfDefVal.getText().trim())) {
          setValuesDB(row,mod);
          dbmsftp.saveToDB(mod,keys,keys.size(),row);
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
          dbmsftp.saveToDB(mod,keys,keys.size(),row);
          dbmsftp.removeRow(row);
          if(dbmsftp.getRowCount()==0) {
            dispose();
          }
          else {
            if (row == dbmsftp.getRowCount()) {
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

  void jbLast_actionPerformed(ActionEvent e) {
    last();
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FSFTypePubl fsftp = new FSFTypePubl(null,Messages.get("SFTYPEPUBLDLG_FSFTYPEPUBL_FIND"),true,conn,this);
    fsftp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fsftp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fsftp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fsftp.setVisible(true);
  }

  void jcbTypePubl_actionPerformed(ActionEvent e) {
    setNameTP();
    if(!trans) {
      trans=true;
      int[] lengths = {((String)jcbTypePubl.getSelectedItem()).trim().length(),0,0};
      int i = dbmsftp.find(((String)jcbTypePubl.getSelectedItem()).trim(),lengths,0);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameFI();
        setNameSF();
      }
      else {
        mod = INSERT;
        jcbField.setSelectedIndex(0);
        setNameFI();
        dbcm3.reloadItems("select distinct POTPOLJAID,PP_NAZIV from POTPOLJA where PO_POLJEID="+"'"+((String)jcbField.getSelectedItem()).trim()+"' order by POTPOLJAID",true);
        jcbSubField.setSelectedIndex(0);
        setNameSF();
        setComponentEnabled();
        jcbField.setEnabled(true);
        jcbSubField.setEnabled(true);
        setButtonEnabled();
      }
      trans=false;
    }
  }

  void jcbField_actionPerformed(ActionEvent e) {
    setNameFI();
    String fld = ((String)jcbField.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm3.reloadItems("select distinct POTPOLJAID,PP_NAZIV from POTPOLJA where PO_POLJEID="+"'"+fld+"' order by POTPOLJAID",true);
      int[] lengths = {((String)jcbTypePubl.getSelectedItem()).trim().length(),((String)jcbField.getSelectedItem()).trim().length(),0};
      int i = dbmsftp.find(((String)jcbTypePubl.getSelectedItem()).trim()+"|"+((String)jcbField.getSelectedItem()).trim(),lengths,1);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
      }
      else {
        mod = INSERT;
        jcbSubField.setSelectedIndex(0);
        setComponentEnabled();
        jcbSubField.setEnabled(true);
        setButtonEnabled();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldField.equals(((String)jcbField.getSelectedItem()).trim())) {
          dbcm3.reloadItems("select distinct POTPOLJAID,PP_NAZIV from POTPOLJA where PO_POLJEID="+"'"+fld+"' order by POTPOLJAID",true);
        //}
      }
    }
    //oldField = ((String)jcbField.getSelectedItem()).trim();
  }

  void jcbSubField_actionPerformed(ActionEvent e) {
    setNameSF();
    if(!trans) {
      trans=true;
      int l1=((String)jcbTypePubl.getSelectedItem()).trim().length();
      int l2=((String)jcbField.getSelectedItem()).trim().length();
      int l3=((String)jcbSubField.getSelectedItem()).trim().length();
      int[] lengths = {l1,l2,l3};
      int i = dbmsftp.find(((String)jcbTypePubl.getSelectedItem()).trim()+"|"+((String)jcbField.getSelectedItem()).trim()+"|"+((String)jcbSubField.getSelectedItem()).trim(),lengths,2);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
      }
      else {
        mod = INSERT;
        jtfDefVal.setText("");
        jtfDefVal.requestFocus();
        setComponentEnabled();
        setButtonEnabled();
      }
      trans=false;
    }
  }
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmsftp.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmsftp.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmsftp.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmsftp.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmsftp.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmsftp.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("SFTYPEPUBLDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("SFTYPEPUBLDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
