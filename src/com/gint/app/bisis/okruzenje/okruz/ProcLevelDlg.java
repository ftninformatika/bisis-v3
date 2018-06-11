
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

public class ProcLevelDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  Connection conn;
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
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JTextField jTextField1 = new JTextField();
  JTextArea jTextArea = new JTextArea();
  JScrollPane spane = new JScrollPane();
  Border border1;
  Border border2;
  DBMProcLevel dbmpl;
  int row;
  int mod;
  DBComboModel dbcm;
  boolean trans;
  JLabel jLabel4 = new JLabel();

  public ProcLevelDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    jbPreview.setText(Messages.get("PROCLEVELDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("PROCLEVELDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbImput.setText(Messages.get("PROCLEVELDLG_BUTTON_INPUT"));
    jbImput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbImput_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("PROCLEVELDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("PROCLEVELDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("PROCLEVELDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("PROCLEVELDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("PROCLEVELDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("PROCLEVELDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("PROCLEVELDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("PROCLEVELDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("PROCLEVELDLG_BUTTON_PREVIOUS"));
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
    jLabel1.setText(Messages.get("PROCLEVELDLG_LABEL_PUBLICATION_TYPE"));
    jLabel2.setText(Messages.get("PROCLEVELDLG_LABEL_PROCESS_LEVEL"));
    jLabel3.setText(Messages.get("PROCLEVELDLG_LABEL_PROCESS_LEVEL_NAME"));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jTextField1, new XYConstraints(150, 70, 70, 20));
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
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(150, 20, 70, 22));
    panel1.add(spane, new XYConstraints(150, 120, 170, 70));
    panel1.add(jLabel4, new XYConstraints(255, 20, 250, -1));
    spane.getViewport().add(jTextArea, null);
    spane.getViewport().setView(jTextArea);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    dbmpl = new DBMProcLevel(conn);
    row=0;

    dbcm = new DBComboModel(conn, "select TIPID,TIP_NAZIV from TIP_PUBLIKACIJE order by TIPID", "",true);
    jComboBox1.setModel(dbcm);

    trans = true;

    if (dbmpl.getRowCount() == 0) {
      jTextField1.setText("");
      jTextField1.requestFocus();
      jTextArea.setText("");
      jComboBox1.setSelectedIndex(0);
      mod=INSERT;
      setButtonEnabled();
      jComboBox1.setEnabled(true);
    }
    else {
      setValues(row);
      setButtonEnabled();
      setComponentEnabled();
      mod=UNDEFINED;
    }
    trans=false;
    setNameTP();
  }
  void setNameTP() {
    jLabel4.setText((String)dbcm.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setValues(int row) {
    jComboBox1.setSelectedItem(dbmpl.getValueAt(row,0));
    jTextField1.setText((String)dbmpl.getValueAt(row,1));
    jTextArea.setText((String)dbmpl.getValueAt(row,2));
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
                    break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea.setEnabled(true);
                    jTextArea.requestFocus();
                    jComboBox1.setEnabled(false);
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    break;
    }
  }

  boolean checkValues() {
    String nobrID = jTextField1.getText();
    String nobrDesc = jTextArea.getText();
    if(nobrID.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL"),Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(nobrID.length() > 2) {
      JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_CHAR_LIMIT"),Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(!(Character.isDigit(nobrID.charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_FIRST_CHAR"),Messages.get("PROCLEVELDLG_OPTION_PANE_FIRST_CHAR_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if (nobrID.length() == 2)
      if(!(Character.isDigit(nobrID.charAt(1)))) {
        JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_SECOND_CHAR"),Messages.get("PROCLEVELDLG_OPTION_PANE_SECOND_CHAR_WARNING"),JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
    if(nobrDesc.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_NAME"),Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_NAME_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(nobrDesc.length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_NAME_CHAR_LIMIT"),Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_NAME_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void setValuesDB(int row,int mod) {
    if(mod == INSERT) {
      dbmpl.setValues(0, ((String)jComboBox1.getSelectedItem()).trim(), row, mod);
      dbmpl.setValues(1, jTextField1.getText().trim(), row, mod);
      dbmpl.setValues(3, ((String)jComboBox1.getSelectedItem()).trim()+jTextField1.getText().trim(), row, mod);
    }
    dbmpl.setValues(2, jTextArea.getText().trim(), row, mod);
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FProcLevel fpl = new FProcLevel(null,Messages.get("PROCLEVELDLG_FPROCLEVEL_FIND"),true,conn,this);
    fpl.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fpl.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fpl.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fpl.setVisible(true);
  }

  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmpl.getRowCount()-1;
    setValues(row);
    trans = false;
  }

  void next() {
    trans = true;
    if(row != dbmpl.getRowCount()-1)
      row = row+1;
    else
      row = dbmpl.getRowCount()-1;
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
    if(!DBDefValLibr.existNO(conn,(String)dbmpl.getValueAt(row,0),(String)dbmpl.getValueAt(row,1))) {
      if(dbmpl.getRowCount() == 1)
        JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_REMOVE_LAST_APPEERANCE"),Messages.get("PROCLEVELDLG_OPTION_PANE_REMOVE_LAST_APPEERANCE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_CANT_DELETE"),Messages.get("PROCLEVELDLG_OPTION_PANE_PROCESS_LEVEL_CANT_DELETE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmpl.getRowCount() == 0) {
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
    String procLevel = jTextField1.getText();
    String typePubl = (String)jComboBox1.getSelectedItem();
    Vector keys = new Vector();
    keys.addElement(typePubl);
    keys.addElement(procLevel);
    if(mod == INSERT) {
      if(checkValues()) {
        int i = dbmpl.findPos(typePubl,procLevel);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_PUBLICATION_PROCESS_EXIST"),Messages.get("PROCLEVELDLG_OPTION_PANE_PUBLICATION_PROCESS_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else {
          row = i;
        }
        setValuesDB(row,mod);
        dbmpl.saveToDB(mod,keys,keys.size(),row);
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
          dbmpl.saveToDB(mod,keys,keys.size(),row);
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
          dbmpl.saveToDB(mod,keys,keys.size(),row);
          dbmpl.removeRow(row);
          if(dbmpl.getRowCount()==0) {
            dispose();
          }
          else {
            if (row == dbmpl.getRowCount()) {
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
    if(!trans) {
      int i = dbmpl.find(((String)jComboBox1.getSelectedItem()).trim(),1);
      if(i != -1) {
        row = i;
        trans=true;
        setValues(row);
        trans=false;
        jbNext.requestFocus();
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        setButtonEnabled();
      }
    }
  }
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmpl.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmpl.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmpl.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmpl.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
                            if(dbmpl.getRowCount() !=0) {
                              insert();
                            }
                          }
                          break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmpl.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmpl.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("PROCLEVELDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("PROCLEVELDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
