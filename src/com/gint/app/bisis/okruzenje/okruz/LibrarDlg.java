
//Title:        BISIS
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

public class LibrarDlg extends JDialog {

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
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextArea jTextArea = new JTextArea();
  JScrollPane spane = new JScrollPane();
  Border border1;
  Border border2;
  DBMLibrarian dbmli;
  int row;
  int mod;
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox3 = new JComboBox();
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  String oldTypePubl;
  int oldProcLev;
  int oldMandLev;
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  boolean trans;

  public LibrarDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    jbPreview.setText(Messages.get("LIBRARDLG_BUTTON_PREVIEW"));
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("LIBRARDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbImput.setText(Messages.get("LIBRARDLG_BUTTON_INPUT"));
    jbImput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbImput_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("LIBRARDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("LIBRARDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("LIBRARDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("LIBRARDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("LIBRARDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("LIBRARDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("LIBRARDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("LIBRARDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("LIBRARDLG_BUTTON_PREVIOUS"));
    jbPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPrev_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(xYLayout3);
    jLabel1.setText(Messages.get("LIBRARDLG_LABEL_LIBRARIAN_CODE"));
    jLabel5.setText(Messages.get("LIBRARDLG_LABEL_INTERN_MARK"));
    jLabel6.setText(Messages.get("LIBRARDLG_LABEL_SECRET_MARK"));
    jLabel7.setText(Messages.get("LIBRARDLG_LABEL_LIBRARIAN_DESCRIPTION"));
    jLabel2.setText(Messages.get("LIBRARDLG_LABEL_PUBLICATION_TYPE"));
    jLabel3.setText(Messages.get("LIBRARDLG_LABEL_PROCESS_LEVEL"));
    jLabel4.setText(Messages.get("LIBRARDLG_LABEL_MANDATORY_LEVEL"));
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
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    getContentPane().add(panel1);
    xYLayout1.setHeight(510);
    xYLayout1.setWidth(610);
    panel1.add(jbPreview, new XYConstraints(410, 400, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 400, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 400, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 447, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 400, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 400, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 400, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 442, 300, -1));
    jPanel1.add(jbImput, new XYConstraints(2, 2, 90, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    jPanel1.add(jbRemove, new XYConstraints(202, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 442, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel5, new XYConstraints(15, 220, -1, -1));
    panel1.add(jLabel6, new XYConstraints(15, 270, -1, -1));
    panel1.add(jLabel7, new XYConstraints(15, 320, -1, -1));
    panel1.add(jTextField1, new XYConstraints(150, 20, 180, -1));
    panel1.add(jTextField2, new XYConstraints(150, 220, 110, -1));
    panel1.add(jTextField3, new XYConstraints(150, 270, 100, -1));
    panel1.add(spane, new XYConstraints(150, 320, 170, 70));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(jLabel3, new XYConstraints(15, 120, -1, -1));
    panel1.add(jLabel4, new XYConstraints(15, 170, -1, -1));
    panel1.add(jComboBox1, new XYConstraints(150, 70, 70, 22));
    panel1.add(jComboBox2, new XYConstraints(150, 120, 70, 22));
    panel1.add(jComboBox3, new XYConstraints(150, 170, 70, 22));
    panel1.add(jLabel8, new XYConstraints(255, 70, 250, -1));
    panel1.add(jLabel9, new XYConstraints(255, 120, 250, -1));
    panel1.add(jLabel10, new XYConstraints(255, 170, 250, -1));
    spane.getViewport().add(jTextArea, null);
    spane.getViewport().setView(jTextArea);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    trans=true;
    dbmli = new DBMLibrarian(conn);
    row=0;

    dbcm1 = new DBComboModel(conn, "select distinct nob.NOB_TP_TIPID,tp.TIP_NAZIV from NIVO_OBAVEZNOSTI nob, TIP_PUBLIKACIJE tp"+
    	                      " where nob.NOB_TP_TIPID=tp.TIPID order by nob.NOB_TP_TIPID", "",true);
    jComboBox1.setModel(dbcm1);

    if (dbmli.getRowCount() == 0) {
      jComboBox1.setSelectedIndex(0);
      dbcm2 = new DBComboModel(conn, "select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob, NIVO_OBRADE nobr",
      			        " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID = '"+(String)jComboBox1.getSelectedItem()+
      				"' order by nob.NOB_NOBRID",true);
      jComboBox2.setModel(dbcm2);
      jComboBox2.setSelectedIndex(0);
      dbcm3 = new DBComboModel(conn, "select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI",
      			        " where NOB_TP_TIPID = '"+(String)jComboBox1.getSelectedItem()+"'"+
                                "and NOB_NOBRID = '"+(String)jComboBox2.getSelectedItem()+"' order by NOBAVID",true);
      jComboBox3.setModel(dbcm3);
      jComboBox3.setSelectedIndex(0);
      mod=INSERT;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      dbcm2 = new DBComboModel(conn, "select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob, NIVO_OBRADE nobr",
                                " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID = '"+((String)dbmli.getValueAt(row,1))+
      				"' order by nob.NOB_NOBRID",true);
      jComboBox2.setModel(dbcm2);
      dbcm3 = new DBComboModel(conn, "select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI",
      			        " where NOB_TP_TIPID = '"+(String)dbmli.getValueAt(row,1)+"'"+
                                " and NOB_NOBRID = '"+(String)dbmli.getValueAt(row,2)+"' order by NOBAVID",true);
      jComboBox3.setModel(dbcm3);
      setValues(row);
      mod=UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    trans=false;
    setNameTP();
    setNameNO();
    setNameNObav();
  }
  void setNameTP() {
    jLabel8.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameNO() {
    jLabel9.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameNObav() {
    jLabel10.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
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
      case UNDEFINED :jComboBox1.setEnabled(false);
                      jComboBox2.setEnabled(false);
                      jComboBox3.setEnabled(false);
                      jTextField1.setEnabled(false);
                      jTextField2.setEnabled(false);
                      jTextField3.setEnabled(false);
                      jTextArea.setEnabled(false);
                      jbNext.requestFocus();
                      break;
      case INSERT : jTextField1.requestFocus();
                    jTextField1.setText("");
                    jTextField1.setEnabled(true);
                    jTextField2.setText("");
                    jTextField2.setEnabled(true);
                    jTextField3.setText("");
                    jTextField3.setEnabled(true);
                    jTextArea.setEnabled(true);
                    jTextArea.setText("");
                    jComboBox1.setEnabled(true);
                    jComboBox2.setEnabled(true);
                    jComboBox3.setEnabled(true);
                    break;
    	case UPDATE : jTextField1.setEnabled(false);
                      jTextField2.setEnabled(true);
                      jTextField2.requestFocus();
                      jTextField3.setEnabled(true);
                      jTextArea.setEnabled(true);
                      jComboBox1.setEnabled(true);
                      jComboBox2.setEnabled(true);
                      jComboBox3.setEnabled(true);
                      break;
    	case DELETE : jTextField1.setEnabled(false);
                      jTextField2.setEnabled(false);
                      jTextField3.setEnabled(false);
                      jTextArea.setEnabled(false);
                      jComboBox1.setEnabled(false);
                      jComboBox2.setEnabled(false);
                      jComboBox3.setEnabled(false);
                      break;
    }
  }

  void setValues(int row) {
    jTextField1.setText((String)dbmli.getValueAt(row,0));
    jTextField2.setText((String)dbmli.getValueAt(row,4));
    jTextField3.setText((String)dbmli.getValueAt(row,5));
    jTextArea.setText((String)dbmli.getValueAt(row,6));
    jComboBox1.setSelectedItem((String)dbmli.getValueAt(row,1));
    jComboBox2.setSelectedItem((String)dbmli.getValueAt(row,2));
    jComboBox3.setSelectedItem((String)dbmli.getValueAt(row,3));
  }

  boolean checkValues() {
    String libID = jTextField1.getText();
    String intOzn = jTextField2.getText();
    String tajnOzn = jTextField3.getText();
    String desc = jTextArea.getText();

    if(libID.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_CODE"),Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_CODE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(libID.length() > 50) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_CODE_CHAR_LIMIT"),Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_CODE_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(intOzn.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_INTERN_MARK"),Messages.get("LIBRARDLG_OPTION_PANE_INTERN_MARK_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(intOzn.length() > 10) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_INTERN_MARK_CHAR_LIMIT"),Messages.get("LIBRARDLG_OPTION_PANE_INTERN_MARK_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    for (int i = 0; i < intOzn.length(); i++) {
      if (! Character.isDigit(intOzn.charAt(i))) {
        JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_INTERN_MARK_COMPOSED_NUMBER"),Messages.get("LIBRARDLG_OPTION_PANE_INTERN_MARK_COMPOSED_NUMBER_WARNING"),JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
    }
    if(tajnOzn.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_SECRET_MARK"),Messages.get("LIBRARDLG_OPTION_PANE_SECRET_MARK_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(tajnOzn.length() > 6) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_SECRET_MARK_CHAR_LIMIT"),Messages.get("LIBRARDLG_OPTION_PANE_SECRET_MARK_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(desc.length() > 240) {
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_DESCRIPTION_CHAR_LIMIT"),Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_DESCRIPTION_CHAR_LIMIT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void setValuesDB(int row,int mod) {
    if(mod == INSERT) {
      dbmli.setValues(0, jTextField1.getText().trim(),row, mod);
    }
    dbmli.setValues(1, ((String)jComboBox1.getSelectedItem()).trim(), row, mod);
    dbmli.setValues(2, ((String)jComboBox2.getSelectedItem()).trim(), row, mod);
    dbmli.setValues(3, ((String)jComboBox3.getSelectedItem()).trim(), row, mod);
    dbmli.setValues(4, jTextField2.getText().trim(), row, mod);
    dbmli.setValues(5, jTextField3.getText().trim(), row, mod);
    dbmli.setValues(6, jTextArea.getText().trim(), row, mod);
  }

  void first() {
    row=0;
    setValues(row);
  }

  void last() {
    row=dbmli.getRowCount()-1;
    setValues(row);
  }

  void next() {
    if(row == dbmli.getRowCount()-1)
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
    FLibrarian fli = new FLibrarian(null,Messages.get("LIBRARDLG_FLIBRARIAN_FIND"),true,conn,this);
    fli.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fli.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fli.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fli.setVisible(true);
  }
  void jbChange_actionPerformed(ActionEvent e) {
    update();
  }
  void update() {
    oldTypePubl = ((String)jComboBox1.getSelectedItem()).trim();
    oldProcLev = Integer.parseInt(((String)jComboBox2.getSelectedItem()).trim());
    oldMandLev = Integer.parseInt(((String)jComboBox3.getSelectedItem()).trim());
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
    if(dbmli.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_DELET_LAST_APPERANCE"),Messages.get("LIBRARDLG_OPTION_PANE_DELET_LAST_APPERANCE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    mod = DELETE;
    setComponentEnabled();
    setButtonEnabled();
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmli.getRowCount() == 0) {
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
        String liID = jTextField1.getText();
        int i = dbmli.findPos(liID);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_CODE_NOT_EXIST"),Messages.get("LIBRARDLG_OPTION_PANE_LIBRARIAN_CODE_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else {
          row = i;
        }
        setValuesDB(row, mod);
        dbmli.saveToDB(mod,liID,row);
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
          dbmli.saveToDB(mod,jTextField1.getText(),row);
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
          dbmli.saveToDB(mod,(String)dbmli.getValueAt(row,0),row);
          dbmli.removeRow(row);
          if(dbmli.getRowCount()==0) {
            dispose();
          }
          else {
            if (row == dbmli.getRowCount()) {
              row=row-1;
              setValues(row);
            }
            else {
              setValues(row);
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
      trans=true;
      dbcm2.reloadItems("select distinct nob.NOB_NOBRID,nobr.NOBR_NAZIV from NIVO_OBAVEZNOSTI nob, NIVO_OBRADE nobr"+
                        " where nobr.NOBRID=nob.NOB_NOBRID and nobr.TP_TIPID=nob.NOB_TP_TIPID and nob.NOB_TP_TIPID="+"'"+((String)jComboBox1.getSelectedItem()).trim()+
      			"' order by nob.NOB_NOBRID",true);
      jComboBox2.setSelectedIndex(0);
      setNameNO();
      dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"+
      			" where NOB_TP_TIPID="+"'"+((String)jComboBox1.getSelectedItem()).trim()+"'"+
                        " and NOB_NOBRID= '"+((String)jComboBox2.getSelectedItem()).trim()+"' order by NOBAVID",true);
      jComboBox3.setSelectedIndex(0);
      setNameNObav();
      trans=false;
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
    setNameNO();
    if(!trans) {
      trans=true;
      dbcm3.reloadItems("select distinct NOBAVID,NOBAV_NAZIV from NIVO_OBAVEZNOSTI"+
                        " where NOB_TP_TIPID="+"'"+((String)jComboBox1.getSelectedItem()).trim()+"'"+
                        " and NOB_NOBRID= '"+((String)jComboBox2.getSelectedItem()).trim()+"' order by NOBAVID",true);
      jComboBox3.setSelectedIndex(0);
      setNameNObav();
      trans=false;
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e) {
    setNameNObav();
  }
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
	                            if(dbmli.getRowCount() !=0) {
				      next();
				    }
				  }
				  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
	                          if(dbmli.getRowCount() !=0) {
				    prev();
				  }
				}
				break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                              if(dbmli.getRowCount() !=0) {
				first();
			      }
			    }
			    break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
	                      if(dbmli.getRowCount() !=0) {
				last();
			      }
                            }
                            break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
                            if(dbmli.getRowCount() !=0) {
                              insert();
                            }
                          }
                          break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                            if(dbmli.getRowCount() !=0) {
                              update();
                            }
                          }
                          break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                            if(dbmli.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("LIBRARDLG_OPTION_PANE_ACTION_NOT_COMPLETED"),Messages.get("LIBRARDLG_OPTION_PANE_ACTION_NOT_COMPLETED_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
