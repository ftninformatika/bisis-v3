
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class Dindikator extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  JLabel jLabel1 = new JLabel();
  JPanel jPanel4 = new JPanel();
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
  JPanel jPanel1 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextArea jTextArea1 = new JTextArea();
  JScrollPane jScrollPane2 = new JScrollPane();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  Border border3;
  TitledBorder titledBorder2;
  Border border4;
  Border border5;
  Border border6;
  JComboBox jComboBox1 = new JComboBox();

  Connection conn;
  DBMDIndikator dbmDI;
  DBComboModel dbcm1;

  int mod = UNDEFINED;
  int row = 0;
  boolean trans = false;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  JLabel jLabel3 = new JLabel();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  boolean isEmpty=false;

  public Dindikator(Connection conn) {
    try  {
      this.conn=conn;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,Messages.get("DINDIKATOR_TITLEDBORDER1_FIELDS"));
    border2 = BorderFactory.createCompoundBorder(titledBorder1,BorderFactory.createEmptyBorder(3,3,3,3));
    border3 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border3,Messages.get("DINDIKATOR_TITLEDBORDER2_SECONDINDICATOR"));
    border4 = BorderFactory.createCompoundBorder(titledBorder2,BorderFactory.createEmptyBorder(3,3,3,3));
    border5 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border6 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jLabel1.setText(Messages.get("DINDIKATOR_LABEL_CODE"));
    this.setResizable(false);
    this.setTitle(Messages.get("DINDIKATOR_DIALOGTITLE_SECONDINDICATOR"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jPanel4.setLayout(xYLayout2);
    jPanel4.setBorder(border2);
    jPanel4.setMinimumSize(new Dimension(666, 80));
    jPanel4.setPreferredSize(new Dimension(666, 80));
    jButton10.setText(Messages.get("DINDIKATOR_BUTTON_INPUT"));
    jButton10.setPreferredSize(new Dimension(80, 21));
    jButton10.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton11.setText(Messages.get("DINDIKATOR_BUTTON_CHANGE"));
    jButton11.setPreferredSize(new Dimension(80, 21));
    jButton11.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton11_actionPerformed(e);
      }
    });
    jButton12.setText(Messages.get("DINDIKATOR_BUTTON_CLEAR"));
    jButton12.setPreferredSize(new Dimension(80, 21));
    jButton12.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton12_actionPerformed(e);
      }
    });
    jButton13.setText(Messages.get("DINDIKATOR_BUTTON_OK"));
    jButton13.setPreferredSize(new Dimension(80, 21));
    jButton13.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton13_actionPerformed(e);
      }
    });
    jButton14.setText(Messages.get("DINDIKATOR_BUTTON_CANCEL"));
    jButton14.setPreferredSize(new Dimension(80, 21));
    jButton14.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton14_actionPerformed(e);
      }
    });
    jButton15.setText(Messages.get("DINDIKATOR_BUTTON_EXIT"));
    jButton15.setPreferredSize(new Dimension(80, 21));
    jButton15.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton15_actionPerformed(e);
      }
    });
    jButton4.setText(Messages.get("DINDIKATOR_BUTTON_FIRST"));
    jButton4.setPreferredSize(new Dimension(80, 21));
    jButton4.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setText(Messages.get("DINDIKATOR_BUTTON_PREVIOUS"));
    jButton5.setPreferredSize(new Dimension(80, 21));
    jButton5.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setText(Messages.get("DINDIKATOR_BUTTON_NEXT"));
    jButton6.setPreferredSize(new Dimension(80, 21));
    jButton6.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setText(Messages.get("DINDIKATOR_BUTTON_LAST"));
    jButton7.setPreferredSize(new Dimension(80, 21));
    jButton7.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setText(Messages.get("DINDIKATOR_BUTTON_VIEW"));
    jButton8.setPreferredSize(new Dimension(80, 21));
    jButton8.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jButton9.setText(Messages.get("DINDIKATOR_BUTTON_HELP"));
    jButton9.setPreferredSize(new Dimension(80, 21));
    jButton9.setFont(new java.awt.Font("Dialog", 0, 11));
  	this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel5.setLayout(xYLayout3);
    jPanel6.setLayout(xYLayout7);
    jPanel7.setLayout(xYLayout6);
    jPanel9.setLayout(xYLayout4);
    jLabel4.setText(Messages.get("DINDIKATOR_LABEL_VALUE"));
    jLabel5.setText(Messages.get("DINDIKATOR_LABEL_NAME"));
    jTextField1.setMinimumSize(new Dimension(21, 21));
    jTextField1.setPreferredSize(new Dimension(21, 21));
    jTextField1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setPreferredSize(new Dimension(250, 119));
    jTextArea1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setMinimumSize(new Dimension(250, 119));
    jScrollPane2.setPreferredSize(new Dimension(260, 70));
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane2.setMinimumSize(new Dimension(260, 70));
    jPanel9.setBorder(border4);
    jPanel6.setBorder(border5);
    jPanel7.setBorder(border6);
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(320);
    xYLayout1.setWidth(650);
    jLabel3.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel5, new XYConstraints(5, 240, -1, -1));
    jPanel5.add(jButton6, new XYConstraints(229, 3, -1, -1));
    jPanel5.add(jButton9, new XYConstraints(565, 3, -1, -1));
    jPanel5.add(jPanel7, new XYConstraints(336, 30, -1, -1));
    jPanel7.add(jButton14, new XYConstraints(115, 1, -1, -1));
    jPanel7.add(jButton13, new XYConstraints(2, 1, -1, -1));
    jPanel5.add(jButton15, new XYConstraints(565, 36, -1, -1));
    jPanel5.add(jButton7, new XYConstraints(341, 3, -1, -1));
    jPanel5.add(jButton5, new XYConstraints(117, 3, -1, -1));
    jPanel5.add(jButton8, new XYConstraints(453, 3, -1, -1));
    jPanel5.add(jButton4, new XYConstraints(5, 3, -1, -1));
    jPanel5.add(jPanel6, new XYConstraints(0, 30, -1, -1));
    jPanel6.add(jButton10, new XYConstraints(3, 1, -1, -1));
    jPanel6.add(jButton12, new XYConstraints(228, 1, -1, -1));
    jPanel6.add(jButton11, new XYConstraints(115, 1, -1, -1));
    jPanel1.add(jPanel4, new XYConstraints(7, 10, 500, -1));
    jPanel4.add(jLabel1, new XYConstraints(5, 2, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(47, 0, -1, -1));
    jPanel4.add(jLabel3, new XYConstraints(150, 0, 320, -1));
    jPanel1.add(jPanel9, new XYConstraints(7, 90, 340, -1));
    jPanel9.add(jLabel4, new XYConstraints(5, 2, -1, -1));
    jPanel9.add(jLabel5, new XYConstraints(5, 31, -1, -1));
    jPanel9.add(jTextField1, new XYConstraints(67, 0, -1, -1));
    jPanel9.add(jScrollPane2, new XYConstraints(67, 31, -1, -1));
    jScrollPane2.getViewport().add(jTextArea1, null);
    this.setModal(true);

    trans=true;
    dbmDI = new DBMDIndikator(conn);

    dbcm1 = new DBComboModel(conn,"select distinct POLJEID,PO_NAZIV from polje order by POLJEID",true);
    jComboBox1.setModel(dbcm1);
    trans=false;

    if (dbmDI.getRowCount() != 0){
      trans=true;
      setValues(row);
      setComponentEnabled();
      jButton6.requestFocus();
      trans=false;
    }
    else {
      mod = INSERT;
      setComponentEnabled();
      jComboBox1.setEnabled(true);
      isEmpty=true;
    }
    setName();
    setButtonEnabled();
    //setComponentEnabled();
  }
  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :
                      jButton4.setEnabled(true);
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

      case INSERT :
                    jButton4.setEnabled(false);
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

      case UPDATE :
                    jButton4.setEnabled(false);
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

      case DELETE :
                    jButton4.setEnabled(false);
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
      case UNDEFINED :
                      jTextField1.setEnabled(false);
                      jTextArea1.setEnabled(false);
                      jComboBox1.setEnabled(true);
                      break;
      case INSERT :
                    jTextField1.setEnabled(true);
                    jTextArea1.setEnabled(true);
                    jTextField1.requestFocus();
                    jTextField1.setText("");
                    jTextArea1.setText("");
		    jComboBox1.setEnabled(false);
              	    break;
    	case UPDATE :
                      jTextField1.setEnabled(false);
                      jTextArea1.setEnabled(true);
                      jTextArea1.requestFocus();
                      jComboBox1.setEnabled(false);
                      break;
    	case DELETE :
                      jTextField1.setEnabled(false);
                      jTextArea1.setEnabled(false);
                      jComboBox1.setEnabled(false);
                      break;
    }
  }

  void setValues(int row){
    jComboBox1.setSelectedItem((String)dbmDI.getValueAt(row,0));
    jTextField1.setText((String)dbmDI.getValueAt(row,1));
    jTextArea1.setText((String)dbmDI.getValueAt(row,2));
  }

  void setName() {
    jLabel3.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }

  boolean checkValues() {
    if (mod == INSERT) {
      if(jTextField1.getText().length() > 1) {
      	JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_MORETHANONECHAR_TEXT"),Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_MORETHANONECHAR_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if(jTextField1.getText().length() == 0) {
        JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_MISSINGSECONDCHAR_TEXT"),Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_MISSINGSECONDCHAR_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
      if(!(Character.isDigit(jTextField1.getText().charAt(0)))) {
      	JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_MUSTBENUMERIC_TEXT"),Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_MUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	return false;
      }
    }
    if(jTextArea1.getText().length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_SECONDINDICATORNAME_LENGTHEXCEEDED_TEXT"),Messages.get("DINDIKATOR_INFDLG_SECONDINDICATORNAME_LENGTHEXCEEDED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextArea1.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_SECONDINDICATORNAME_NONAME_TEXT"),Messages.get("DINDIKATOR_INFDLG_SECONDINDICATORNAME_NONAME_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void find(String fld,String drind) {
    String value = fld+"|"+drind;
    int lengths = fld.length()+drind.length()+1;
    int pos = dbmDI.find(value,lengths);
    if (pos!=-1){
      row=pos;
      trans=true;
      setValues(row);
      trans=false;
    }
  }

  public String getField() {
    return (String)dbmDI.getValueAt(row,0);
  }
  public String getDrind() {
    return (String)dbmDI.getValueAt(row,1);
  }
  void first() {
    trans=true;
    row=0;
    setValues(row);
    trans=false;
  }

  void last() {
    trans=true;
    row=dbmDI.getRowCount()-1;
    setValues(row);
    trans=false;
  }

  void next() {
    if(row == dbmDI.getRowCount()-1)
      return;
    else {
      trans=true;
      row = row+1;
      setValues(row);
      trans=false;
    }
  }

  void prev() {
    if(row == 0)
      return;
    else {
      trans=true;
      row = row-1;
      setValues(row);
      trans=false;
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
    PronadjiDI pdi = new PronadjiDI(conn,this);
    pdi.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = pdi.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    pdi.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    pdi.setVisible(true);
  }
  void jButton10_actionPerformed(ActionEvent e)  {
    insert();
  }
  void insert()  {
    mod = INSERT;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton11_actionPerformed(ActionEvent e)  {
    update();
  }
  void update()  {
    mod = UPDATE;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton12_actionPerformed(ActionEvent e)  {
    delete();
  }
  void delete() {
    if(dbmDI.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_RECORD_LASTRECDELETED_TEXT"),Messages.get("DINDIKATOR_INFDLG_RECORD_LASTRECDELETED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      mod = DELETE;
      setButtonEnabled();
      setComponentEnabled();
  }
  void jButton13_actionPerformed(ActionEvent e)  {
    doAction();
  }
  void doAction()  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String drind = jTextField1.getText().trim();
    String naziv = jTextArea1.getText().trim();
    dbmDI.setField(fld);
    dbmDI.setDrind(drind);
    dbmDI.setNazivDrind(naziv);
    if (mod == INSERT) {
      int pos = dbmDI.findPos(fld,drind);
      if (checkValues()&& pos!=-1) {
        row = pos;
        dbmDI.setTo(row,mod);
        dbmDI.saveToDB(mod,row);
        if(isEmpty)
          isEmpty=false;
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
        jButton6.requestFocus();
      }
      else {
      	if(pos == -1) {
          JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_EXISTANCEINDATABASE_TEXT"),Messages.get("DINDIKATOR_INFDLG_SECONDINDICATOR_EXISTANCEINDATABASE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      	}
      }
    }
    else if (mod == UPDATE) {
      if (checkValues()) {
        dbmDI.setTo(row,mod);
        dbmDI.saveToDB(mod,row);
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
    else if (mod == DELETE) {
      dbmDI.removeRow(row);
      dbmDI.saveToDB(mod,row);
      if (row == dbmDI.getRowCount()) {
        row=row-1;
      }
      if(dbmDI.getRowCount() == 0) {
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
  }
  void jButton14_actionPerformed(ActionEvent e)  {
    doCancel();
  }
  void doCancel()  {
    if(!isEmpty) {
      if (mod==INSERT || mod==UPDATE) {
        trans=true;
        setValues(row);
        trans=false;
      }
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


  void jComboBox1_actionPerformed(ActionEvent e)  {
    setName();
    if(!trans) {
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      int i = dbmDI.find(fld,fld.length());
      if(i != -1) {
        row = i;
        setValues(row);
        jButton6.requestFocus();
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
      case KeyEvent.VK_PAGE_DOWN:
                                if(mod == UNDEFINED) {
	                          if(dbmDI.getRowCount() !=0) {
				    next();
				  }
				}
				break;
      case KeyEvent.VK_PAGE_UP:
                              if(mod == UNDEFINED) {
	                        if(dbmDI.getRowCount() !=0) {
				  prev();
				}
			      }
			      break;
      case KeyEvent.VK_HOME:
                            if(mod == UNDEFINED) {
	                      if(dbmDI.getRowCount() !=0) {
                                first();
			      }
			    }
			    break;
      case KeyEvent.VK_END:
                          if(mod == UNDEFINED) {
                            if(dbmDI.getRowCount() !=0) {
			      last();
			    }
			  }
                          break;
      case KeyEvent.VK_F1:
                          if(mod == UNDEFINED) {
	                    if(dbmDI.getRowCount() !=0) {
			      insert();
			    }
			  }
			  break;
      case KeyEvent.VK_F2:
                          if(mod == UNDEFINED) {
	                    if(dbmDI.getRowCount() !=0) {
			      update();
			    }
			  }
			  break;
      case KeyEvent.VK_F3:
                          if(mod == UNDEFINED) {
			    if(dbmDI.getRowCount() !=0) {
			      delete();
			    }
			  }
			  break;
      case KeyEvent.VK_F5:
                          if(mod==INSERT || mod==DELETE || mod==UPDATE) {
	                    doAction();
			  }
			  break;
      case KeyEvent.VK_F6:
                          if(mod==INSERT || mod==DELETE || mod==UPDATE) {
	                    doCancel();
			  }
			  break;
      default:
    }
  }
  protected void processWindowEvent(WindowEvent e){   // ZASTITA OD X-ICA U TOKU NEKE AKCIJE
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      if(mod != UNDEFINED) {
        JOptionPane.showMessageDialog(null,Messages.get("DINDIKATOR_INFDLG_CLOSING_ACTIONNOTFINISHED_TEXT"),Messages.get("DINDIKATOR_INFDLG_CLOSING_ACTIONNOTFINISHED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
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
