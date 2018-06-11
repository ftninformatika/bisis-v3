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

public class FormatDlg extends JDialog {

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
  JTextField jTextField1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextArea jTextArea = new JTextArea();
  JScrollPane spane = new JScrollPane();
  Border border1;
  Border border2;
  Connection conn;
  DBMFormat dbmf;
  int row;
  int mod;

  public FormatDlg(Frame frame, String title, boolean modal, Connection conn) {
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
    jbPreview.setText(Messages.get("FORMATDLG_BUTTON_PREVIEW"));
    jbPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbPreview_actionPerformed(e);
      }
    });
    jbChange.setText(Messages.get("FORMATDLG_BUTTON_CHANGE"));
    jbChange.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbChange_actionPerformed(e);
      }
    });
    jbImput.setText(Messages.get("FORMATDLG_BUTTON_INPUT"));
    jbImput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbImput_actionPerformed(e);
      }
    });
    jbLast.setText(Messages.get("FORMATDLG_BUTTON_LAST"));
    jbLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbLast_actionPerformed(e);
      }
    });
    jbRemove.setText(Messages.get("FORMATDLG_BUTTON_REMOVE"));
    jbRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbRemove_actionPerformed(e);
      }
    });
    jbCancel.setText(Messages.get("FORMATDLG_BUTTON_CANCEL"));
    jbCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbCancel_actionPerformed(e);
      }
    });
    jbFirst.setText(Messages.get("FORMATDLG_BUTTON_FIRST"));
    jbFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbFirst_actionPerformed(e);
      }
    });
    jbOk.setText(Messages.get("FORMATDLG_BUTTON_OK"));
    jbOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbOk_actionPerformed(e);
      }
    });
    jbExit.setText(Messages.get("FORMATDLG_BUTTON_EXIT"));
    jbExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbExit_actionPerformed(e);
      }
    });
    jbNext.setText(Messages.get("FORMATDLG_BUTTON_NEXT"));
    jbNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbNext_actionPerformed(e);
      }
    });
    jbHelp.setText(Messages.get("FORMATDLG_BUTTON_HELP"));
    jbPrev.setText(Messages.get("FORMATDLG_BUTTON_PREVIOUS"));
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
    jLabel1.setText(Messages.get("FORMATDLG_LABEL_FORMAT_CODE"));
    jLabel2.setText(Messages.get("FORMATDLG_LABEL_FORMAT_NAME"));
    getContentPane().add(panel1);
    panel1.add(jTextField1, new XYConstraints(120, 20, 100, 20));
    panel1.add(jbPreview, new XYConstraints(410, 255, 90, 25));
    panel1.add(jbLast, new XYConstraints(310, 255, 90, 25));
    panel1.add(jbFirst, new XYConstraints(10, 255, 90, 25));
    panel1.add(jbExit, new XYConstraints(515, 303, 90, -1));
    panel1.add(jbNext, new XYConstraints(210, 255, 90, 25));
    panel1.add(jbHelp, new XYConstraints(510, 255, 90, 25));
    panel1.add(jbPrev, new XYConstraints(110, 255, 90, 25));
    panel1.add(jPanel1, new XYConstraints(5, 298, 300, -1));
    jPanel1.add(jbImput, new XYConstraints(2, 2, 90, -1));
    jPanel1.add(jbChange, new XYConstraints(102, 2, 90, -1));
    jPanel1.add(jbRemove, new XYConstraints(202, 2, 90, -1));
    panel1.add(jPanel2, new XYConstraints(310, 298, 200, -1));
    jPanel2.add(jbOk, new XYConstraints(2, 2, 90, -1));
    jPanel2.add(jbCancel, new XYConstraints(102, 2, 90, -1));
    panel1.add(jLabel1, new XYConstraints(15, 20, -1, -1));
    panel1.add(jLabel2, new XYConstraints(15, 70, -1, -1));
    panel1.add(spane, new XYConstraints(120, 70, 170, 70));
    spane.getViewport().add(jTextArea, null);
    spane.getViewport().setView(jTextArea);
    jPanel1.setBorder(border1);
    jPanel2.setBorder(border2);
    setResizable(false);

    dbmf = new DBMFormat(conn);
    row=0;

    if (dbmf.getRowCount() == 0) {
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

  void setValues(int row) {
    jTextField1.setText((String)dbmf.getValueAt(row,0));
    jTextArea.setText((String)dbmf.getValueAt(row,1));
  }

  boolean checkValues() {
    String formID = jTextField1.getText();
    String formDesc = jTextArea.getText();

    if(formID.length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_FORMAT_CODE"),Messages.get("FORMATDLG_OPTION_PANE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(formID.length() > 6) {
      JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_FORMAT_CODE_CHARS_RESTRICTION"),Messages.get("FORMATDLG_OPTION_PANE_FORMAT_CODE_CHARS_RESTRICTION_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(formDesc.length() > 240) {
      JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_FORMAT_DESCRIPTION_CHARS_RESTRICTION"),Messages.get("FORMATDLG_OPTION_PANE_FORMAT_CHARS_RESTRICTION_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void setValuesDB(int row,int mod) {
    if(mod == INSERT)
      dbmf.setValues(0, jTextField1.getText().trim(),row, mod);
    dbmf.setValues(1, jTextArea.getText().trim(),row, mod);
  }

  void first() {
    row=0;
    setValues(row);
  }

  void last() {
    row=dbmf.getRowCount()-1;
    setValues(row);
  }

  void next() {
    if(row == dbmf.getRowCount()-1)
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
    FFormat ff = new FFormat(null,Messages.get("FORMATDLG_FFORMAT_FIND"),true,conn,this);
    ff.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = ff.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    ff.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    ff.setVisible(true);
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
    if(!DBDefValLibr.existForm(conn,(String)dbmf.getValueAt(row,0))) {
      if(dbmf.getRowCount() == 1)
        JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_DELETE_LAST"),Messages.get("FORMATDLG_OPTION_PANE_DELETE_LAST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_FORMAT_RESTRICTION"),Messages.get("FORMATDLG_OPTION_PANE_FORMAT_RESTRICTION_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void jbCancel_actionPerformed(ActionEvent e) {
    doCancel();
  }
  void doCancel() {
    if (dbmf.getRowCount() == 0) {
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
      String formID = jTextField1.getText();
      if(checkValues()) {
        int i = dbmf.findPos(formID);
        if(i == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_FORMAT_EXIST"),Messages.get("FORMATDLG_OPTION_PANE_FORMAT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        else
          row = i;
        setValuesDB(row, mod);
        dbmf.saveToDB(mod,formID,row);
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
          dbmf.saveToDB(mod,jTextField1.getText(),row);
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
          dbmf.saveToDB(mod,(String)dbmf.getValueAt(row,0),row);
          dbmf.removeRow(row);
          if(dbmf.getRowCount()==0) {
            dispose();
          }
          else {
            if (row == dbmf.getRowCount()) {
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
  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN: if(mod == UNDEFINED) {
	                            if(dbmf.getRowCount() !=0) {
			              next();
				    }
				  }
				  break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
	                          if(dbmf.getRowCount() !=0) {
				    prev();
				  }
                                }
				break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
	                      if(dbmf.getRowCount() !=0) {
				first();
			      }
			    }
			    break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
	                      if(dbmf.getRowCount() !=0) {
			        last();
			      }
			    }
			    break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
	                    if(dbmf.getRowCount() !=0) {
			      insert();
			    }
			  }
			  break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
	                    if(dbmf.getRowCount() !=0) {
			      update();
			    }
			  }
			  break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
	                    if(dbmf.getRowCount() !=0) {
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
        JOptionPane.showMessageDialog(null,Messages.get("FORMATDLG_OPTION_PANE_ACTION_NOT_FINISH"),Messages.get("FORMATDLG_OPTION_PANE_ACTION_NOT_FINISH_WARNING"),JOptionPane.INFORMATION_MESSAGE);
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
