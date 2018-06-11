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

public class SearchPrefDlg extends JDialog {

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
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextArea jTextArea = new JTextArea();
  JScrollPane spane = new JScrollPane();
  Border border1;
  Border border2;
  DBMSearchPref dbmsp;
  int row;
  Connection conn;
  int mod;

  public SearchPrefDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    jbPreview.setText(Messages.get("SEARCHPREFDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("SEARCHPREFDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("SEARCHPREFDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("SEARCHPREFDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("SEARCHPREFDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("SEARCHPREFDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("SEARCHPREFDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("SEARCHPREFDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("SEARCHPREFDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("SEARCHPREFDLG_BUTTON_PREVIOUS"));
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
    jLabel1.setText(Messages.get("SEARCHPREFDLG_LABEL_PREFIX_CODE"));
    jLabel2.setText(Messages.get("SEARCHPREFDLG_LABEL_PREFIX_NAME"));
    getContentPane().add(panel1);
    panel1.add(jbPreview, new XYConstraints(410, 255, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 255, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 255, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 302, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 255, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 255, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 255, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 297, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 297, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jTextField1, new XYConstraints(120, 20, 60, 20));
    panel1.add(spane, new XYConstraints(120, 70, 170, 70));
    spane.getViewport().add(jTextArea, null);
    spane.getViewport().setView(jTextArea);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    dbmsp = new DBMSearchPref(conn);
    row=0;

    if (dbmsp.getRowCount() == 0) {
      mod=INSERT;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
  }

  void setValues(int row) {
    jTextField1.setText((String)dbmsp.getValueAt(row,0));
    jTextArea.setText((String)dbmsp.getValueAt(row,1));
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
      case UNDEFINED :jTextField1.setEnabled(false);
                      jTextArea.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jTextField1.setEnabled(true);
                    jTextArea.setEnabled(true);
                    jTextField1.requestFocus();
                    jTextField1.setText("");
                    jTextArea.setText("");
                    break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea.setEnabled(true);
                    jTextArea.requestFocus();
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea.setEnabled(false);
                    break;
    }
  }

  boolean checkValues() {
    String searchPref=jTextField1.getText();
    String prefDesc = jTextArea.getText();
    if(searchPref.length() != 2) {
      JOptionPane.showMessageDialog(null,Messages.get("SEARCHPREFDLG_OPTION_PANE_PREFIX_LENGTH"),Messages.get("SEARCHPREFDLG_OPTION_PANE_PREFIX_LENGTH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(!(Character.isLetter(searchPref.charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("SEARCHPREFDLG_OPTION_PANE_FIRST_CHAR_LETTER"),Messages.get("SEARCHPREFDLG_OPTION_PANE_FIRST_CHAR_LETTER_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if((Character.isLowerCase(searchPref.charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("SEARCHPREFDLG_OPTION_PANE_FIRST_CHAR_CAPITAL_LETTER"),Messages.get("SEARCHPREFDLG_OPTION_PANE_FIRST_CHAR_CAPITAL_LETTER_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(prefDesc.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("SEARCHPREFDLG_OPTION_PANE_PREFIX_NAME"),Messages.get("SEARCHPREFDLG_OPTION_PANE_PREFIX_NAME_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(prefDesc.length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("SEARCHPREFDLG_OPTION_PANE_PREFIX_NAME_CHAR_LIMIT"),Messages.get("SEARCHPREFDLG_OPTION_PANE_PREFIX_NAME_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void setValuesDB(int row,int mod) {
    if(mod == INSERT)
      dbmsp.setValues(0, jTextField1.getText().trim(),row, mod);
    dbmsp.setValues(1, jTextArea.getText().trim(),row, mod);
  }

  void first() {
    row=0;
    setValues(row);
  }

  void last() {
    row=dbmsp.getRowCount()-1;
    setValues(row);
  }

  void next() {
    if(row == dbmsp.getRowCount()-1)
      return;
    else {
      row = row+1;
      setValues(row);
    }
  }

  void prev() {
    if(row == 0)
      return;
    else {
      row = row-1;
      setValues(row);
    }
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FSearchPref fsp = new FSearchPref(null,Messages.get("SEARCHPREFDLG_FSEARCHPREF_FIND"),true,conn,this);
    fsp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fsp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fsp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fsp.setVisible(true);
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
    if (dbmsp.getRowCount() == 0) {
      mod = UNDEFINED;
      dispose();
    }
    else {
      setValues(row);
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
   /* if(mod == INSERT) {
      String searchPref = jTextField1.getText();
      //if(checkValues() && !keyFound(searchPref)) {
      if(checkValues()) {
        int i = dbmsp.findPos(searchPref);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,"Prefiks ve\u0107 postoji!","Upozorenje",JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else
          row = i;
        setValuesDB(row,mod);
        row = dbmsp.find(searchPref);
        dbmsp.saveToDB(mod,searchPref,row);
        setButtonEnabled(true);
        setComponentEnabled(false);
        mod = UNDEFINED;
      }
      else {
        //setValues(row);
        setButtonEnabled(false);
        setComponentEnabled(true);
      }
    }
    else*/
      if(mod == UPDATE) {
        if(checkValues()) {
          setValuesDB(row,mod);
          dbmsp.saveToDB(mod,jTextField1.getText(),row);
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
        }
        else {
          setButtonEnabled();
          setComponentEnabled();
        }
      }
      /*else
        if(mod == DELETE) {
          setButtonEnabled(true);
          setComponentEnabled(false);
          if (dbmsp.getRowCount() == 1) {
            row = 0;
            int ret = JOptionPane.showConfirmDialog(null,"Brisanje poslednje pojave","Upozorenje",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
              dbmsp.saveToDB(mod,(String)dbmsp.getValueAt(row,0),row);
              dbmsp.removeRow(row);
              dispose();
            }
            if (ret == JOptionPane.NO_OPTION) {
               setValues(row);
            }
            //mod = UNDEFINED;
          }
          else {
            if (row == dbmsp.getRowCount()-1) {
              dbmsp.saveToDB(mod,(String)dbmsp.getValueAt(row,0),row);
              dbmsp.removeRow(row);
              row=row-1;
              setValues(row);
              //mod = UNDEFINED;
            }
            else {
              dbmsp.saveToDB(mod,(String)dbmsp.getValueAt(row,0),row);
              dbmsp.removeRow(row);
              setValues(row);
              //mod = UNDEFINED;
            }
          }
          mod = UNDEFINED;
        }*/
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
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmsp.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmsp.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmsp.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmsp.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmsp.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("SEARCHPREFDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("SEARCHPREFDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
