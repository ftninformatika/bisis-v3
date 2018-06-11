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

public class MandLevelDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JScrollBar jScrollBar1 = new JScrollBar();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbPreview = new JButton();
  JButton jbChange = new JButton();
  JButton jbImput = new JButton();
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
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextArea jTextArea = new JTextArea();
  JScrollPane spane = new JScrollPane();
  Border border1;
  Border border2;
  String oldTP = "";
  Connection conn;
  DBMMandLevel dbmml;
  int row;
  int mod;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  boolean first;
  boolean trans;
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  public MandLevelDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    jbPreview.setText(Messages.get("MANDLEVELDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("MANDLEVELDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbImput.setText(Messages.get("MANDLEVELDLG_BUTTON_INPUT"));
    jbImput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbImput_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("MANDLEVELDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("MANDLEVELDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("MANDLEVELDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("MANDLEVELDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("MANDLEVELDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("MANDLEVELDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("MANDLEVELDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("MANDLEVELDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("MANDLEVELDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    jLabel1.setText(Messages.get("MANDLEVELDLG_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("MANDLEVELDLG_LABEL_PROCESS_LEVEL"));
    jLabel3.setText(Messages.get("MANDLEVELDLG_LABEL_MANDATORY_LEVEL"));
    jLabel4.setText(Messages.get("MANDLEVELDLG_LABEL_MANDATORY_LEVEL_NAME"));
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
    getContentPane().add(panel1);
    panel1.add(jTextField1, new XYConstraints(150, 120, 70, 20));
    panel1.add(jbPreview, new XYConstraints(410, 255, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 255, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 255, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 302, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 255, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 255, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 255, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 297, 300, -1));
    jPanel1.add(jbImput, new XYConstraints(2, 2, 90, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    jPanel1.add(jbRemove, new XYConstraints(202, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 297, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(150, 20, 70, 22));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jComboBox2, new XYConstraints(150, 70, 70, 22));
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(jLabel4, new XYConstraints(15, 170, -1, -1));
    panel1.add(spane, new XYConstraints(160, 170, 170, 70));
    panel1.add(jLabel5, new XYConstraints(255, 20, 250, -1));
    panel1.add(jLabel6, new XYConstraints(255, 70, 250, -1));
    spane.getViewport().add(jTextArea, null);
    spane.getViewport().setView(jTextArea);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    dbmml = new DBMMandLevel(conn);
    row=0;
    first = true;
    trans=true;

    dbcm1 = new DBComboModel(conn, "select distinct nobr.TP_TIPID,tp.TIP_NAZIV from NIVO_OBRADE nobr,TIP_PUBLIKACIJE tp"+
                              " where tp.TIPID=nobr.TP_TIPID order by nobr.TP_TIPID", "",true);
    jComboBox1.setModel(dbcm1);
    //trans = false;

    if (dbmml.getRowCount() == 0) {
      jTextField1.setText("");
      jTextField1.requestFocus();
      jTextArea.setText("");
      jComboBox1.setSelectedIndex(0);
      oldTP = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2 = new DBComboModel(conn, "select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE", " where TP_TIPID="+"'"+jComboBox1.getSelectedItem()+"' order by NOBRID",true);
      jComboBox2.setModel(dbcm2);
      jComboBox2.setSelectedIndex(0);
      mod=INSERT;
      setButtonEnabled();
    }
    else {
      dbcm2 = new DBComboModel(conn, "select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE", " where TP_TIPID="+"'"+dbmml.getValueAt(row,0)+"' order by NOBRID",true);
      jComboBox2.setModel(dbcm2);
      oldTP=((String)dbmml.getValueAt(row,0)).trim();
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    first=false;
    trans=false;
    setNameTP();
    setNameNO();
  }
  void setNameTP() {
    jLabel5.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel6.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setValues(int row) {
    jComboBox1.setSelectedItem(dbmml.getValueAt(row,0));
    jComboBox2.setSelectedItem(dbmml.getValueAt(row,1));
    jTextField1.setText((String)dbmml.getValueAt(row,2));
    jTextArea.setText((String)dbmml.getValueAt(row,3));
  }

  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jbFirst.setEnabled(true);
                      jbPrev.setEnabled(true);
                      jbNext.setEnabled(true);
                      jbLast.setEnabled(true);
                      jbPreview.setEnabled(true);
                      jbImput.setEnabled(true);
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
                      jbImput.setEnabled(false);
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
                      jTextField1.setEnabled(false);
                      jTextArea.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jTextField1.setEnabled(true);
                    jTextArea.setEnabled(true);
                    jTextField1.requestFocus();
                    jTextField1.setText("");
                    jTextArea.setText("");
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea.setEnabled(true);
                    jTextArea.requestFocus();
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    break;
    }
  }

  boolean checkValues() {
    String nobavID = jTextField1.getText();
    String nobavDesc = jTextArea.getText();
    if(nobavID.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL"),Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(nobavID.length() > 2) {
      JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_CHAR_LIMIT"),Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(!(Character.isDigit(nobavID.charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_FIRST_CHAR"),Messages.get("MANDLEVELDLG_OPTION_PANE_FIRST_CHAR_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if (nobavID.length() == 2)
      if(!(Character.isDigit(nobavID.charAt(1)))) {
        JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_SECOND_CHAR"),Messages.get("MANDLEVELDLG_OPTION_PANE_SECOND_CHAR_WARNING"),JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
    if(nobavDesc.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_NAME"),Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_NAME_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(nobavDesc.length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_NAME_CHAR_LIMIT"),Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_NAME_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void setValuesDB(int row,int mod) {
    if(mod == INSERT) {
      dbmml.setValues(0, ((String)jComboBox1.getSelectedItem()).trim(), row, mod);
      dbmml.setValues(1, ((String)jComboBox2.getSelectedItem()).trim(), row, mod);
      dbmml.setValues(2, jTextField1.getText().trim(), row, mod);
      dbmml.setValues(4, ((String)jComboBox1.getSelectedItem()).trim()+"|"+((String)jComboBox2.getSelectedItem()).trim()+"|"+jTextField1.getText().trim(), row, mod);
    }
    dbmml.setValues(3, jTextArea.getText().trim(), row, mod);
  }

  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmml.getRowCount()-1;
    setValues(row);
    trans = false;
  }

  void next() {
    trans = true;
    if(row != dbmml.getRowCount()-1)
      row = row+1;
    else
      row = dbmml.getRowCount()-1;
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
    FMandLevel fml = new FMandLevel(null,Messages.get("MANDLEVELDLG_FMANDLEVEL_FIND"),true,conn,this);
    fml.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fml.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fml.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fml.setVisible(true);
  }
  void jbChange_actionPerformed(ActionEvent e) {
    update();
  }
  void update() {
    mod = UPDATE;
    setComponentEnabled();
    setButtonEnabled();
  }
  void jbImput_actionPerformed(ActionEvent e) {
    insert();
  }
  void insert() {
    mod = INSERT;
    setButtonEnabled();
    setComponentEnabled();
  }

  void jbLast_actionPerformed(ActionEvent e) {
    last();
  }
  void jbRemove_actionPerformed(ActionEvent e) {
    delete();
  }
  void delete() {
    if(!DBDefValLibr.existNObav(conn,(String)dbmml.getValueAt(row,0),(String)dbmml.getValueAt(row,1),(String)dbmml.getValueAt(row,2))) {
      if(dbmml.getRowCount() == 1)
        JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_REMOVE_LAST_APPEERANCE"),Messages.get("MANDLEVELDLG_OPTION_PANE_REMOVE_LAST_APPEERANCE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_CANT_DELETE"),Messages.get("MANDLEVELDLG_OPTION_PANE_MANDATORY_LEVEL_CANT_DELETE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmml.getRowCount() == 0) {
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
    String mandLevel = jTextField1.getText().trim();
    String procLevel = ((String)jComboBox2.getSelectedItem()).trim();
    String typePubl = ((String)jComboBox1.getSelectedItem()).trim();
    Vector keys = new Vector();
    keys.addElement(typePubl);
    keys.addElement(procLevel);
    keys.addElement(mandLevel);
    int[] lengths = {typePubl.length(),procLevel.length(),mandLevel.length()};
    if(mod == INSERT) {
      if(checkValues()) {
        int i = dbmml.findPos(typePubl,procLevel,mandLevel);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_PUBLICATION_PROCESS_MANDATORY_EXIST"),Messages.get("MANDLEVELDLG_OPTION_PANE_PUBLICATION_PROCESS_MANDATORY_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else {
          row = i;
        }
        setValuesDB(row,mod);
        dbmml.saveToDB(mod,keys,keys.size(),row);
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
        if(checkValues()) {
          setValuesDB(row,mod);
          dbmml.saveToDB(mod,keys,keys.size(),row);
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
          dbmml.saveToDB(mod,keys,keys.size(),row);
          dbmml.removeRow(row);
          if(dbmml.getRowCount()==0) {
            dispose();
          }
          else {
            if (row == dbmml.getRowCount()) {
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

  void jComboBox1_actionPerformed(ActionEvent e) {
    setNameTP();
    String tp = ((String)jComboBox1.getSelectedItem()).trim();
    if(!trans) {
      trans=true;
      dbcm2.reloadItems("select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE where TP_TIPID="+"'"+tp+"' order by NOBRID",true);
      int[] lengths = {((String)jComboBox1.getSelectedItem()).trim().length(),0,0};
      int i = dbmml.find(((String)jComboBox1.getSelectedItem()).trim(),lengths,1);
      if(i != -1) {
        row = i;
        setValues(row);
        jbNext.requestFocus();
        setNameNO();
      }
      else {
        oldTP=((String)jComboBox1.getSelectedItem()).trim();
        mod = INSERT;
        jComboBox2.setSelectedIndex(0);
        setNameNO();
        setComponentEnabled();
        jComboBox2.setEnabled(true);
        setButtonEnabled();
      }
      trans=false;
    }
    else {
      if(!first) {
        //if(!oldTP.equals(((String)jComboBox1.getSelectedItem()).trim())) {
        dbcm2.reloadItems("select distinct NOBRID,NOBR_NAZIV from NIVO_OBRADE where TP_TIPID="+"'"+tp+"' order by NOBRID",true);
        //}
      }
    }
    //oldTP = ((String)jComboBox1.getSelectedItem()).trim();
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameNO();
    if(!trans) {
      trans=true;
      int[] lengths = {((String)jComboBox1.getSelectedItem()).trim().length(),((String)jComboBox2.getSelectedItem()).trim().length(),0};
      int i = dbmml.find(((String)jComboBox1.getSelectedItem()).trim()+"|"+((String)jComboBox2.getSelectedItem()).trim(),lengths,2);
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
                                    if(dbmml.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmml.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmml.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmml.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
                            if(dbmml.getRowCount() !=0) {
                              insert();
                            }
                          }
                          break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmml.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmml.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("MANDLEVELDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("MANDLEVELDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
