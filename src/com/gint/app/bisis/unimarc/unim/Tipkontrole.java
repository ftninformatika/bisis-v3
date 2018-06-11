//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class Tipkontrole extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JPanel jPanel9 = new JPanel();
  JButton jButton10 = new JButton();
  JButton jButton11 = new JButton();
  JButton jButton12 = new JButton();
  JButton jButton13 = new JButton();
  JButton jButton14 = new JButton();
  JButton jButton15 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JButton jButton8 = new JButton();
  JButton jButton9 = new JButton();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel2= new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  Border border3;
  Border border4;
  JPanel jPanel1 = new JPanel();
  Border border5;
  TitledBorder titledBorder2;
  Border border6;
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  JTextField jTextField1 = new JTextField();

  Connection conn;

  DBMTipKontrole dbmTK;

  int mod = UNDEFINED;
  int row = 0;
  boolean isEmpty=false;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();

  public Tipkontrole(Connection conn) {
    try  {
      this.conn=conn;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().add(jPanel2);
    jPanel2.setLayout(xYLayout1);
    xYLayout1.setHeight(250);
    xYLayout1.setWidth(650);
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,Messages.get("TIPKONTROLE_TITLEBORDER1_CONTROLTYPE"));
    border2 = BorderFactory.createCompoundBorder(titledBorder1,BorderFactory.createEmptyBorder(3,3,3,3));
    border3 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border4 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border5 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border5,Messages.get("TIPKONTROLE_TITLEBORDER2_CONTROLTYPE"));
    border6 = BorderFactory.createCompoundBorder(titledBorder2,BorderFactory.createEmptyBorder(3,3,3,3));
    this.setResizable(false);
    this.setTitle(Messages.get("TIPKONTROLE_DIALOGTITLE_CONTROLTYPE"));
    jPanel9.setLayout(xYLayout3);
    jButton10.setText(Messages.get("TIPKONTROLE_BUTTON_INPUT"));
    jButton10.setPreferredSize(new Dimension(80, 21));
    jButton10.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton10.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton11.setText(Messages.get("TIPKONTROLE_BUTTON_CHANGE"));
    jButton11.setPreferredSize(new Dimension(80, 21));
    jButton11.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton11_actionPerformed(e);
      }
    });
    jButton12.setText(Messages.get("TIPKONTROLE_BUTTON_CLEAR"));
    jButton12.setPreferredSize(new Dimension(80, 21));
    jButton12.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton12_actionPerformed(e);
      }
    });
    jButton13.setText(Messages.get("TIPKONTROLE_BUTTON_OK"));
    jButton13.setPreferredSize(new Dimension(80, 21));
    jButton13.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton13_actionPerformed(e);
      }
    });
    jButton14.setText(Messages.get("TIPKONTROLE_BUTTON_CANCEL"));
    jButton14.setPreferredSize(new Dimension(80, 21));
    jButton14.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton14_actionPerformed(e);
      }
    });
    jButton15.setText(Messages.get("TIPKONTROLE_BUTTON_EXIT"));
    jButton15.setPreferredSize(new Dimension(80, 21));
    jButton15.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton15_actionPerformed(e);
      }
    });
    jButton4.setText(Messages.get("TIPKONTROLE_BUTTON_FIRST"));
    jButton4.setPreferredSize(new Dimension(80, 21));
    jButton4.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });

    jButton5.setText(Messages.get("TIPKONTROLE_BUTTON_PREVIOUS"));
    jButton5.setPreferredSize(new Dimension(80, 21));
    jButton5.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setText(Messages.get("TIPKONTROLE_BUTTON_NEXT"));
    jButton6.setPreferredSize(new Dimension(80, 21));
    jButton6.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setText(Messages.get("TIPKONTROLE_BUTTON_LAST"));
    jButton7.setPreferredSize(new Dimension(80, 21));
    jButton7.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setText(Messages.get("TIPKONTROLE_BUTTON_VIEW"));
    jButton8.setPreferredSize(new Dimension(80, 21));
    jButton8.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jButton9.setText(Messages.get("TIPKONTROLE_BUTTON_HELP"));
    jButton9.setPreferredSize(new Dimension(80, 21));
    jButton9.setFont(new java.awt.Font("Dialog", 0, 11));
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel7.setLayout(xYLayout4);
    jPanel11.setLayout(xYLayout5);
    jPanel11.setBorder(border3);
    jPanel7.setBorder(border4);
    jPanel1.setBorder(border6);
    jPanel1.setLayout(xYLayout2);
    jLabel1.setText(Messages.get("TIPKONTROLE_LABEL_CODE"));
    jLabel2.setText(Messages.get("TIPKONTROLE_LABEL_NAME"));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane1.setMinimumSize(new Dimension(260, 70));
    jScrollPane1.setPreferredSize(new Dimension(260, 70));
    jTextArea1.setPreferredSize(new Dimension(250, 119));
    jTextArea1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setMinimumSize(new Dimension(250, 119));
    jTextField1.setMinimumSize(new Dimension(25, 21));
    jTextField1.setPreferredSize(new Dimension(25, 21));
    jTextField1.setMargin(new Insets(0, 2, 0, 2));
    jPanel2.add(jPanel1, new XYConstraints(7, 10, 340, -1));
    jPanel1.add(jLabel1, new XYConstraints(5, 2, -1, -1));
    jPanel1.add(jLabel2, new XYConstraints(5, 31, -1, -1));
    jPanel1.add(jTextField1, new XYConstraints(47, 0, -1, -1));
    jPanel1.add(jScrollPane1, new XYConstraints(47, 31, -1, -1));
    jScrollPane1.getViewport().add(jTextArea1, null);
    jPanel2.add(jPanel9, new XYConstraints(5, 170, -1, -1));
    jPanel9.add(jButton6, new XYConstraints(229, 3, -1, -1));
    jPanel9.add(jButton9, new XYConstraints(565, 3, -1, -1));
    jPanel9.add(jPanel7, new XYConstraints(336, 30, 201, -1));
    jPanel7.add(jButton14, new XYConstraints(115, 1, -1, -1));
    jPanel7.add(jButton13, new XYConstraints(2, 1, -1, -1));
    jPanel9.add(jButton15, new XYConstraints(565, 36, -1, -1));
    jPanel9.add(jButton7, new XYConstraints(341, 3, -1, -1));
    jPanel9.add(jButton5, new XYConstraints(117, 3, -1, -1));
    jPanel9.add(jButton8, new XYConstraints(453, 3, -1, -1));
    jPanel9.add(jButton4, new XYConstraints(5, 3, -1, -1));
    jPanel9.add(jPanel11, new XYConstraints(0, 30, 320, -1));
    jPanel11.add(jButton10, new XYConstraints(3, 1, -1, -1));
    jPanel11.add(jButton12, new XYConstraints(228, 1, -1, -1));
    jPanel11.add(jButton11, new XYConstraints(115, 1, -1, -1));
    this.setModal(true);

    dbmTK = new DBMTipKontrole(conn);

    if (dbmTK.getRowCount() != 0){
      setValues(row);
    }
    else {
      mod = INSERT;
      isEmpty=true;
    }
    setButtonEnabled();
    setComponentEnabled();
  }

  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jButton4.setEnabled(true);
                      jButton5.setEnabled(true);
                      jButton6.setEnabled(true);
                      jButton7.setEnabled(true);
                      jButton8.setEnabled(true);
                      jButton9.setEnabled(true);
                      jButton10.setEnabled(true);
                      jButton11.setEnabled(true);
                      jButton12.setEnabled(true);
                      jButton13.setEnabled(false);
                      jButton14.setEnabled(false);
                      jButton15.setEnabled(true);
                      break;

      case INSERT : jButton4.setEnabled(false);
                    jButton5.setEnabled(false);
                    jButton6.setEnabled(false);
                    jButton7.setEnabled(false);
                    jButton8.setEnabled(false);
                    jButton9.setEnabled(true);
                    jButton10.setEnabled(false);
                    jButton11.setEnabled(false);
                    jButton12.setEnabled(false);
                    jButton13.setEnabled(true);
                    jButton14.setEnabled(true);
                    jButton15.setEnabled(false);
                    break;

      case UPDATE : jButton4.setEnabled(false);
      	            jButton5.setEnabled(false);
                    jButton6.setEnabled(false);
                    jButton7.setEnabled(false);
                    jButton8.setEnabled(false);
                    jButton9.setEnabled(true);
                    jButton10.setEnabled(false);
                    jButton11.setEnabled(false);
                    jButton12.setEnabled(false);
                    jButton13.setEnabled(true);
                    jButton14.setEnabled(true);
                    jButton15.setEnabled(false);
                    break;

      case DELETE : jButton4.setEnabled(false);
                    jButton5.setEnabled(false);
                    jButton6.setEnabled(false);
                    jButton7.setEnabled(false);
                    jButton8.setEnabled(false);
                    jButton9.setEnabled(true);
                    jButton10.setEnabled(false);
                    jButton11.setEnabled(false);
                    jButton12.setEnabled(false);
                    jButton13.setEnabled(true);
                    jButton14.setEnabled(true);
                    jButton15.setEnabled(false);
                    break;
    }
  }

  void setComponentEnabled() {
    switch(mod) {
      case UNDEFINED :jTextField1.setEnabled(false);
                      jTextArea1.setEnabled(false);
                      break;
      case INSERT : jTextField1.setEnabled(true);
                    jTextArea1.setEnabled(true);
                    jTextField1.requestFocus();
                    jTextField1.setText("");
                    jTextArea1.setText("");
                    break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(true);
                    jTextArea1.requestFocus();
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(false);
                    break;
    }
  }

  void setValues(int row){
    jTextField1.setText((String)dbmTK.getValueAt(row,0));
    jTextArea1.setText((String)dbmTK.getValueAt(row,1));
  }


  String getCurrentTK() {
    return (String)dbmTK.getValueAt(row,0);
  }

  void find(String tk) {
    int i = dbmTK.find(tk.trim());
    if (i != -1) {
      row = i;
      setValues(row);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLETYP_NOTEXIST_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLETYP_NOTEXIST_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  boolean checkValues() {
    if (mod == INSERT) {
      if(jTextField1.getText().length() > 2) {
        JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_MAX2CHARS_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_MAX2CHARS_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if(jTextField1.getText().length() == 0) {
        JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_MISSINGCONTROLTYPE_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_MISSINGCONTROLTYPE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if(!(Character.isDigit(jTextField1.getText().charAt(0)))) {
        JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_FIRSTLENCHARMUSTBENUMERIC_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_FIRSTLENCHARMUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if (jTextField1.getText().length() == 2)
      	if(!(Character.isDigit(jTextField1.getText().charAt(1)))) {
          JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_SECONDLENCHARMUSTBENUMERIC_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_SECONDLENCHARMUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
          return false;
      	}
    }
    if(jTextArea1.getText().length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPENAME_LENGTHEXCEEDED_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPENAME_LENGTHEXCEEDED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextArea1.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPENAME_NONAME_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPENAME_NONAME_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void first() {
    row=0;
    setValues(row);
  }

  void last() {
    row=dbmTK.getRowCount()-1;
    setValues(row);
  }

  void next() {
    if(row == dbmTK.getRowCount()-1)
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

  void jButton4_actionPerformed(ActionEvent e)  {
    first();
  }

  void jButton5_actionPerformed(ActionEvent e)  {
    prev();
  }
  void jButton6_actionPerformed(ActionEvent e)  {
    next();
  }
  void jButton7_actionPerformed(ActionEvent e)  {
    last();
  }

  void jButton8_actionPerformed(ActionEvent e)  {
    PronadjiTK ptk = new PronadjiTK(conn,this);
    ptk.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = ptk.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    ptk.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    ptk.setVisible(true);
  }

  void jButton10_actionPerformed(ActionEvent e)  {
    insert();
  }

  void insert() {
    mod = INSERT;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton11_actionPerformed(ActionEvent e)  {
    update();
  }

  void update() {
    mod = UPDATE;
    setButtonEnabled();
    setComponentEnabled();
  }

  void jButton12_actionPerformed(ActionEvent e)  {
    delete();
  }

  void delete() {
    if(dbmTK.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_RECORD_LASTRECDELETED_TEXT"),Messages.get("TIPKONTROLE_INFDLG_RECORD_LASTRECDELETED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
  }
  void jButton13_actionPerformed(ActionEvent e)  {
    doAction();
  }

  void doAction()  {
    String tk = jTextField1.getText().trim();
    String naziv = jTextArea1.getText().trim();
    dbmTK.setTipKontr(tk);
    dbmTK.setNazivKontr(naziv);
    boolean ok=false;
    if (mod == INSERT) {
      if(checkValues()) {
        int pos = dbmTK.findPos(tk);
        if (pos!=-1) {
          row = pos;
          ok=dbmTK.saveToDB(mod,row);
          dbmTK.setTo(row,mod);
          if(isEmpty)
            isEmpty=false;
          mod = UNDEFINED;
          setButtonEnabled();
          setComponentEnabled();
      	}
      	else {
          JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_EXISTANCEINDATABASE_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_EXISTANCEINDATABASE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	}
      }
    }
    else if (mod == UPDATE) {
      if (checkValues()) {
        ok=dbmTK.saveToDB(mod,row);
        dbmTK.setTo(row,mod);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
    else if (mod == DELETE) {
      ok=dbmTK.saveToDB(mod,row);
      if(ok) {
        dbmTK.removeRow(row);
        if (row == dbmTK.getRowCount()) {
          row--;
        }
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_RELATEDTOSUBFIELD_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CONTROLTYPE_RELATEDTOSUBFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      }
      if(dbmTK.getRowCount() == 0) {
        dispose();
      }
      else {
      	setValues(row);
      	mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
  }

  void jButton14_actionPerformed(ActionEvent e)  {
    doCancel();
  }

  void doCancel()  {
    if(!isEmpty) {
      if (mod==INSERT || mod==UPDATE)
        setValues(row);
      mod = UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      dispose();
    }
  }

  void jButton15_actionPerformed(ActionEvent e)  {
    dispose();
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN:if(mod == UNDEFINED) {
	                          if(dbmTK.getRowCount() !=0) {
				    next();
				  }
				 }
				 break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
	                          if(dbmTK.getRowCount() !=0) {
				    prev();
				  }
			        }
                                break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
	                      if(dbmTK.getRowCount() !=0) {
				first();
			      }
			    }
			    break;
      case KeyEvent.VK_END: if(mod == UNDEFINED) {
	                      if(dbmTK.getRowCount() !=0) {
			        last();
			      }
			    }
			    break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
	                    if(dbmTK.getRowCount() !=0) {
			      insert();
			    }
			  }
			  break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
	                    if(dbmTK.getRowCount() !=0) {
			      update();
			    }
			  }
			  break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
	                    if(dbmTK.getRowCount() !=0) {
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

  protected void processWindowEvent(WindowEvent e){   // ZASTITA OD X-ICA U TOKU NEKE AKCIJE
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      if(mod != UNDEFINED) {
        JOptionPane.showMessageDialog(null,Messages.get("TIPKONTROLE_INFDLG_CLOSING_ACTIONNOTFINISHED_TEXT"),Messages.get("TIPKONTROLE_INFDLG_CLOSING_ACTIONNOTFINISHED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
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
