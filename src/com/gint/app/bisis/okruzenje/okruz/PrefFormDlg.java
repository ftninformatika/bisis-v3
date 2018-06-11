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

public class PrefFormDlg extends JDialog {

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
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JList jList1 = new JList();
  JScrollPane spane = new JScrollPane();
  Connection conn;
  DBMPrefForm dbmpf;
  DBComboModel dbcm;
  DBListModel dblm;
  int row;
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JButton jbChange = new JButton();
  JButton jbCancel = new JButton();
  JButton jbOk = new JButton();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  Border border1;
  Border border2;
  int countPref;
  int mod;
  Object[] prefix = new Object[100];
  int[] indexes;
  boolean trans;
  JLabel jLabel3 = new JLabel();

  public PrefFormDlg(Frame frame, String title, boolean modal,Connection conn) {
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
    jbPreview.setText(Messages.get("PREFFORMDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("PREFFORMDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("PREFFORMDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("PREFFORMDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("PREFFORMDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("PREFFORMDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("PREFFORMDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("PREFFORMDLG_FORMAT_CODE"));
    jLabel2.setText(Messages.get("PREFFORMDLG_PREFIX_CODE"));
    jbChange.setText(Messages.get("PREFFORMDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("PREFFORMDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("PREFFORMDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jbPreview, new XYConstraints(410, 255, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 255, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 255, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 302, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 255, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 255, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 255, 90, 25));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(130, 20, 100, 22));
    panel1.add(spane, new XYConstraints(130, 70, 210, 150));
    panel1.add(jPanel1, new XYConstraints(5, 297, 300, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 297, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel3, new XYConstraints(255, 20, 250, -1));
    spane.getViewport().add(jList1, null);
    spane.getViewport().setView(jList1);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    row = 0;

    trans=true;
    dbmpf = new DBMPrefForm(conn);

    dbcm = new DBComboModel(conn, "select FORMATID,FOR_OPIS from FORMATF order by FORMATID", true, false);
    jComboBox1.setModel(dbcm);


    dblm = new DBListModel(conn, "select POLJEBPRID, PBPR_NAZIV from POLJEBPR order by POLJEBPRID", "");
    jList1.setModel(dblm);

    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jList1.clearSelection();

    if (dbmpf.getRowCount() == 0) {
      jComboBox1.setSelectedIndex(0);
      jComboBox1.setEnabled(true);
      mod=INSERT;
      setButtonEnabled();
    }
    else {
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    trans=false;
    setNameForm();
  }
  void setNameForm() {
    jLabel3.setText((String)dbcm.getNameAt(jComboBox1.getSelectedIndex()));
  }
//promenjiva countPref uvedena da bi se znalo koliko pozicija treba preskociti
//do sledeceg prefiksa jer su u bazi parovi form/pref1;form/pref2;form/pref3...
  void setValues(int row) {
    String item = ((String)dbmpf.getValueAt(row,0));
    jComboBox1.setSelectedItem(item);
    jList1.clearSelection();
    countPref = 0;
    int k;
    String list = "";
    for (int j=0; j < dblm.getSize(); j++) {
      list = (String)dblm.getElementAt(j);
      k = dbmpf.find(item.trim()+"|"+list.substring(0,list.indexOf("-")),1);
      if(k != -1) {
        countPref++;
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
                      jList1.setEnabled(false);
                      break;
      case INSERT : jComboBox1.setEnabled(false);
                    jList1.setEnabled(true);
                    break;
      case UPDATE : jComboBox1.setEnabled(false);
                    jList1.setEnabled(true);
                    break;
      case DELETE : jComboBox1.setEnabled(false);
                    jList1.setEnabled(false);
                    break;
    }
  }

  void jbPreview_actionPerformed(ActionEvent e) {
    FPrefForm fpf = new FPrefForm(null,Messages.get("PREFFORMDLG_FPREFFORM_FIND"),true,conn,this);
    fpf.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fpf.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fpf.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fpf.setVisible(true);
  }

  void first() {
    trans = true;
    row=0;
    setValues(row);
    trans = false;
  }

  void last() {
    trans = true;
    row=dbmpf.getRowCount()-1;
    setValues(row);
    row = (row-countPref)+1;
    trans = false;
  }

  void next() {
    trans = true;
    if((row+countPref) > dbmpf.getRowCount()-1)
      setValues(row);
    else {
      row = row+countPref;
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
      row = (row-countPref)+1;
    }
    trans = false;
  }

  void jbLast_actionPerformed(ActionEvent e) {
    last();
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmpf.getRowCount() == 0) {
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
      JOptionPane.showMessageDialog(null,Messages.get("PREFFORMDLG_OPTION_PANE_PREFIX_NOT_SELECT"),Messages.get("PREFFORMDLG_OPTION_PANE_PREFIX_NOT_SELECT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      String form = (String)jComboBox1.getSelectedItem();
      int j = dbmpf.findPos(form);
      if(j != -1) {
        mod = INSERT;
        row = j;
        isInsert = true;
      }
      else {
        mod = UPDATE;
        for(int k = 0; k < prefix.length; k++) {
          dbmpf.removeRow(row);
        }
      }
      if(((String)(jList1.getSelectedValues()[0])).equals("---bez selekcije---")) {
        mod = DELETE;
      }
      else {
        prefix = jList1.getSelectedValues();
      }
      dbmpf.saveToDB(mod,form,prefix,row);
      countPref = prefix.length;
      if (mod != DELETE) {
        row = dbmpf.find(form,0);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
      else {
        if(dbmpf.getRowCount() != 0) {
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
          row = dbmpf.find((String)jComboBox1.getSelectedItem(),0);
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
        }
        else {
          if(dbmpf.getRowCount()==0)
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
  void jbChange_actionPerformed(ActionEvent e) {
    update();
  }
  void update() {
    prefix = jList1.getSelectedValues();
    indexes = jList1.getSelectedIndices();
    mod = UPDATE;
    setComponentEnabled();
    setButtonEnabled();
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
    setNameForm();
    if(!trans) {
      String form = ((String)jComboBox1.getSelectedItem());
      int i = dbmpf.find(form,0);
      if(i != -1) {
        row = i;
        trans=true;
        setValues(row);
        jbNext.requestFocus();
        trans=false;
      }
      else {
        mod = INSERT;
        setComponentEnabled();
        setButtonEnabled();
        jList1.clearSelection();
      }
    }
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
                                    if(dbmpf.getRowCount() !=0) {
                                      next();
                                    }
                                  }
                                  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                                  if(dbmpf.getRowCount() !=0) {
                                    prev();
                                  }
                                }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmpf.getRowCount() !=0) {
                                first();
                              }
                            }
                            break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
                              if(dbmpf.getRowCount() !=0) {
                                last();
                              }
                            }
                            break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmpf.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("PREFFORMDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("PREFFORMDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
