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

public class TypePublDlg extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel panel1 = new JPanel();
  Connection conn;
  XYLayout xYLayout1 = new XYLayout();
  JLabel jlNaTypePubl = new JLabel();
  JLabel jlTypePubl = new JLabel();
  JTextArea jtaNaTypePubl = new JTextArea();
  JScrollPane spane = new JScrollPane();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbPreview = new JButton();
  JButton jbChange = new JButton();
  JButton jbImput = new JButton();
  JButton jbLast = new JButton();
  JButton jbRemove = new JButton();
  JButton jbCancel = new JButton();
  JTextField jtfTypePubl = new JTextField();
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
  int row;
  DBMTypePubl dbmtp;
  int mod;

  public TypePublDlg(Frame frame, String title, boolean modal, Connection conn) {
    super(frame, title, modal);
    this.conn=conn;
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
    jlNaTypePubl.setText(Messages.get("TYPEPUBLDLG_LABEL_PUBLICATION_TYPE_NAME"));
    jlTypePubl.setText(Messages.get("TYPEPUBLDLG_LABEL_PUBLICATION_TYPE"));
    jbPreview.setText(Messages.get("TYPEPUBLDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("TYPEPUBLDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbImput.setText(Messages.get("TYPEPUBLDLG_BUTTON_INPUT"));
    jbImput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbImput_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("TYPEPUBLDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("TYPEPUBLDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("TYPEPUBLDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("TYPEPUBLDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("TYPEPUBLDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("TYPEPUBLDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("TYPEPUBLDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("TYPEPUBLDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("TYPEPUBLDLG_BUTTON_PREVIOUS"));
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
    getContentPane().add(panel1);
    panel1.add(jlNaTypePubl, new XYConstraints(15, 70, -1, -1));
    panel1.add(jlTypePubl, new XYConstraints(15, 20, -1, -1));
    panel1.add(jbPreview, new XYConstraints(410, 255, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 255, 90, 25));
    panel1.add(jtfTypePubl, new XYConstraints(150, 20, 70, 20));
    panel1.add(spane, new XYConstraints(150, 70, 170, 70));
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
    spane.getViewport().add(jtaNaTypePubl, null);
    spane.getViewport().setView(jtaNaTypePubl);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    setResizable(false);

    dbmtp = new DBMTypePubl(conn);
    row=0;

    if (dbmtp.getRowCount() == 0) {
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
    jtfTypePubl.setText((String)dbmtp.getValueAt(row,0));
    jtaNaTypePubl.setText((String)dbmtp.getValueAt(row,1));
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
      case UNDEFINED :jtfTypePubl.setEnabled(false);
                      jtaNaTypePubl.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jtfTypePubl.setEnabled(true);
                    jtaNaTypePubl.setEnabled(true);
                    jtfTypePubl.requestFocus();
                    jtfTypePubl.setText("");
                    jtaNaTypePubl.setText("");
                    break;
      case UPDATE : jtfTypePubl.setEnabled(false);
                    jtaNaTypePubl.setEnabled(true);
                    jtaNaTypePubl.requestFocus();
                    break;
      case DELETE : jtfTypePubl.setEnabled(false);
                    jtaNaTypePubl.setEnabled(false);
                    break;
    }
  }

  boolean checkValues() {
    String typePubl=jtfTypePubl.getText();
    if(typePubl.length() != 2) {
      JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_LENGTH_LIMIT"),Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_LENGTH_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(!(Character.isLetter(typePubl.charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_FIRST_CHAR_LETTER"),Messages.get("TYPEPUBLDLG_OPTION_PANE_FIRST_CHAR_LETTER_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if((Character.isLowerCase(typePubl.charAt(0)))) {
      JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_FIRST_CHAR_CAPITAL_LETTER"),Messages.get("TYPEPUBLDLG_OPTION_PANE_FIRST_CHAR_CAPITAL_LETTER_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jtaNaTypePubl.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_NAME"),Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_NAME_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jtaNaTypePubl.getText().length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_NAME_CHAR_LIMIT"),Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_NAME_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void setValuesDB(int row,int mod) {
    if(mod == INSERT)
      dbmtp.setValues(0, jtfTypePubl.getText().trim(),row, mod);
    dbmtp.setValues(1, jtaNaTypePubl.getText().trim(),row, mod);
  }

  void first() {
    row=0;
    setValues(row);
  }

  void last() {
    row=dbmtp.getRowCount()-1;
    setValues(row);
  }

  void next() {
    if(row == dbmtp.getRowCount()-1)
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
    if(!DBDefValLibr.existTP(conn,(String)dbmtp.getValueAt(row,0))) {
      if(dbmtp.getRowCount() == 1)
        JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_REMOVE_LAST"),Messages.get("TYPEPUBLDLG_OPTION_PANE_REMOVE_LAST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_CANT_DELETE"),Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_CANT_DELETE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmtp.getRowCount() == 0) {
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
    if(mod == INSERT) {
      if(checkValues()) {
        String typePubl = jtfTypePubl.getText();
        int i = dbmtp.findPos(typePubl);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_EXIST"),Messages.get("TYPEPUBLDLG_OPTION_PANE_PUBLICATION_TYPE_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else
          row = i;
        setValuesDB(row,mod);
        dbmtp.saveToDB(mod,typePubl,row);
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
          dbmtp.saveToDB(mod,jtfTypePubl.getText(),row);
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
          dbmtp.saveToDB(mod,(String)dbmtp.getValueAt(row,0),row);
          dbmtp.removeRow(row);
          if(dbmtp.getRowCount()==0) {
            dispose();
          }
          else if (row == dbmtp.getRowCount()) {
            row=row-1;
            setValues(row);
          }
          else {
            setValues(row);
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

  void jbPreview_actionPerformed(ActionEvent e) {
    FTypePubl ftp = new FTypePubl(null,Messages.get("TYPEPUBLDLG_FTYPEPUBL_FIND"),true,conn,this);
    ftp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = ftp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    ftp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    ftp.setVisible(true);
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmtp.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmtp.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmtp.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmtp.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
                            if(dbmtp.getRowCount() !=0) {
                              insert();
                            }
                          }
                          break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmtp.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmtp.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("TYPEPUBLDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("TYPEPUBLDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
