
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import com.gint.app.bisis.circ.reports.*;

public class FindLendedPubl extends EscapeDialog {
  boolean closed = true;
  int selected_row = -1;
  DBConnection dbConn;
  DataSet dsLending;

  JDBTableModel tmLending;
  ListSelectionModel lsmLending;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lCtlgNo = new JLabel();
  JLabel lSigNo = new JLabel();
  JTable tblLending;// = new JTable();
  JScrollPane sp1;
  JLabel tfCtlgNo = new JLabel();
//  JLabel tfLDate = new JLabel();
  JLabel tfSigNo = new JLabel();
  JButton btnFind = new JButton();
  JButton btnExit = new JButton();
  JButton btnFindCtlgno = new JButton();
  CircDocs circ;
  CtlgNo ctlgno;
  String value = "";
  ActionListener aCircAdd, aCircExit, ctlgnoAdd;
  JLabel lTitle = new JLabel();
  JLabel lAuthor = new JLabel();
  JLabel tfTitle = new JLabel("1");
  JLabel tfAuthor = new JLabel("1");
  JLabel lOpis = new JLabel();
  EditableTableModel tmDefault;
  JButton btnWarn = new JButton();
  JButton btnSame = new JButton();

  public FindLendedPubl(Frame frame, String title, boolean modal,DBConnection db, CircDocs sig, CtlgNo ctlgno) {
    super(frame, title, modal);
    dbConn=db;
    circ = sig;
    this.ctlgno = ctlgno;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public FindLendedPubl() {
    this(CircApplet.parent, "", false,null,null,null);
  }

  public FindLendedPubl(String title,DBConnection dbConn, boolean modal, CircDocs sig, CtlgNo ctlgno) {
    this(CircApplet.parent, title, modal,dbConn,sig,ctlgno);
  }

  void jbInit() throws Exception {
    dsLending = new DataSet(dbConn.getConnection(),"LENDING",
                            new String[]{"LEND_DATE", "SINGLE_ID","CTLG_NO","COUNTER"});

    tblLending = new JTable();
    lsmLending = tblLending.getSelectionModel();
    lsmLending.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblLending.setSelectionModel(lsmLending);
    sp1= new JScrollPane(tblLending);
    xYLayout1.setWidth(545);
    xYLayout1.setHeight(393);
    panel1.setLayout(xYLayout1);

    btnFind.setText(Messages.get("FINDLENDEDPUBL_BTNFIND_TEXT"));
    btnFind.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFind_actionPerformed(e);
      }
    });
    this.getRootPane().setDefaultButton(btnFind);
    
    btnFindCtlgno.setText(Messages.get("FINDLENDEDPUBL_BTNFINDCTLGNO_TEXT"));
    btnFindCtlgno.addActionListener(new java.awt.event.ActionListener() {
    	public void actionPerformed(ActionEvent e) {
            btnFindCtlgno_actionPerformed(e);
          }
    });
   
    btnExit.setText(Messages.get("FINDLENDEDPUBL_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });

    btnWarn.setText(Messages.get("FINDLENDEDPUBL_BTNWARN_TEXT"));
    btnWarn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnWarn_actionPerformed(e);
      }
    });
    btnWarn.setEnabled(false);

    aCircAdd = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        value = circ.getValue();
        circ.setVisible(false);
        setPosition(value);
/* 28.01.2003.
       if(!circ.isAvailable()){
          value = circ.getValue();
          circ.setVisible(false);
          setPosition(value);
        }
        else{
          JOptionPane.showMessageDialog(circ,"Primerak je dostupan!", "Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
//          circ.setVisible(true);
        }*/
      }
    };

    aCircExit = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        circ.setVisible(false);
      }
    };
    
    ctlgnoAdd = new ActionListener(){
        public void actionPerformed(ActionEvent e){
          value = ctlgno.getValue();
          ctlgno.clear();
          ctlgno.setVisible(false);
          setPositionCtlgno(value);
        }
    };

    lCtlgNo.setText(Messages.get("FINDLENDEDPUBL_LCTLGNO_TEXT"));
    tfCtlgNo.setForeground(Color.blue);
    tfCtlgNo.setText("");

    lSigNo.setText(Messages.get("FINDLENDEDPUBL_SIGNO_TEXT"));
    tfSigNo.setForeground(Color.blue);
    tfSigNo.setText("");

    lTitle.setText(Messages.get("FINDLENDEDPUBL_LTITLE_TEXT"));
    tfTitle.setForeground(Color.blue);
    tfTitle.setText("");

    lAuthor.setText(Messages.get("FINDLENDEDPUBL_LAUTHOR_TEXT"));
    tfAuthor.setForeground(Color.blue);
    tfAuthor.setText("");

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed){
          circ.addAddListener(aCircAdd);
          circ.addExitListener(aCircExit);
          ctlgno.addOKListener(ctlgnoAdd);

        }
        closed = false;
        panel1.requestFocus();
      }

      public void windowClosing(WindowEvent e){
        circ.removeAddListener();
        circ.removeExitListener();
        ctlgno.removeOKListener();
        closed = true;
        setVisible(false);
      }
      
    });
    
/*    panel1.addKeyListener(new KeyAdapter(){
	      public void keyPressed(KeyEvent e){
	        if(e.getKeyCode()==KeyEvent.VK_ENTER){
	          btnFind.doClick();
	        }
	      }
	    });*/
    

    
    lOpis.setText(Messages.get("FINDLENDEDPUBL_LOPIS_TEXT"));

    btnSame.setText(Messages.get("FINDLENDEDPUBL_BTNSAME_TEXT"));
    btnSame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSame_actionPerformed(e);
      }
    });
    btnSame.setEnabled(false);

    getContentPane().add(panel1);

    panel1.add(lCtlgNo, new XYConstraints(18, 24, -1, -1));
    panel1.add(sp1, new XYConstraints(18, 161, 502, 115));
    panel1.add(lSigNo, new XYConstraints(18, 52, -1, 15));
    panel1.add(lOpis, new XYConstraints(19, 138, 386, 23));
    panel1.add(lTitle, new XYConstraints(19, 83, -1, -1));
    panel1.add(lAuthor, new XYConstraints(19, 111, -1, -1));
    panel1.add(tfAuthor, new XYConstraints(74, 111, 437, 16));
    panel1.add(tfTitle, new XYConstraints(74, 83, 438, 16));
    panel1.add(tfSigNo, new XYConstraints(105, 51, 78, 16));
    panel1.add(tfCtlgNo, new XYConstraints(105, 24, 78, 18));
    panel1.add(btnWarn, new XYConstraints(300, 302, 95, -1));
    panel1.add(btnFind, new XYConstraints(25, 302, 95, -1));
    panel1.add(btnSame, new XYConstraints(142, 302, 95, -1));
    panel1.add(btnExit, new XYConstraints(420, 302, 95, -1));
    panel1.add(btnFindCtlgno, new XYConstraints(25, 350, 220, -1));
    
  }


  public void setVisible(boolean vis){
    closed = true;
    selected_row=-1;
    setPosition("");
    btnSame.setEnabled(false);
    super.setVisible(vis); 
  }

  private void setPosition(String ctlg_no){
    tfAuthor.setText("");
    tfTitle.setText("");
    tfSigNo.setText("");

    tmDefault = new EditableTableModel();
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL1"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL2"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL3"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL4"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL5"));

    if (!ctlg_no.equals("")) {
      tmLending = new JDBTableModel(dsLending,"select * from lending where ctlg_no='"+ctlg_no+"' and return_date is null");

/*      tmDefault = new EditableTableModel();
      tmDefault.addColumn("Korisnik");
      tmDefault.addColumn("Ime");
      tmDefault.addColumn("Prezime");
      tmDefault.addColumn("Datum zadu\u017eenja");
*/
      if (tmLending.getRowCount()>0){

        for(int k = 0; k < tmLending.getRowCount(); k++){
          Object [] dataRow = new Object[5];
          dataRow[0] = tmLending.getValueAt(k,1);

          try {
            Statement stmt = dbConn.getConnection().createStatement();
            ResultSet rset = stmt.executeQuery("select first_name, last_name from single where user_id='"+tmLending.getValueAt(k,1)+"'");
            while(rset.next()){
              dataRow[1] = rset.getString("FIRST_NAME");
              dataRow[2] = rset.getString("LAST_NAME");
            }
            rset.close();
            dbConn.getConnection().commit();
            stmt.close();
          } catch (Exception ex) {
            System.out.println("Greska kod nalazenja imena i prezimena");
            System.out.println(ex);
          }

          dataRow[3] = tmLending.getValueAt(k,2);
          dataRow[4] = tmLending.getValueAt(k,4);
          tmDefault.addRow(dataRow);
          tmDefault.setRowEditable(tmDefault.getRowCount()-1,false);
        }
        tblLending.setModel(tmDefault);
        tblLending.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblLending.sizeColumnsToFit(1);

        tblLending.setRowSelectionInterval(0,0);

        tfCtlgNo.setText(ctlg_no);
        tfSigNo.setText(circ.getSignatura());
        tfAuthor.setText(circ.getAutor());
        tfTitle.setText(circ.getNaslov());
        btnWarn.setEnabled(true);
      }
      btnSame.setEnabled(true);
    }
    else {
      btnWarn.setEnabled(false);
      tblLending.setModel(tmDefault);
      tblLending.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblLending.sizeColumnsToFit(1);
     }
  }
  
  private void setPositionCtlgno(String ctlg_no){
    tfAuthor.setText("");
    tfTitle.setText("");
    tfSigNo.setText("");

    tmDefault = new EditableTableModel();
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL1"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL2"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL3"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL4"));
    tmDefault.addColumn(Messages.get("FINDLENDEDPUBL_TMDEFAULT_COL5"));
    
    if (!ctlg_no.equals("")) {
      tmLending = new JDBTableModel(dsLending,"select * from lending where ctlg_no='"+ctlg_no+"' and return_date is null");

/*      tmDefault = new EditableTableModel();
      tmDefault.addColumn("Korisnik");
      tmDefault.addColumn("Ime");
      tmDefault.addColumn("Prezime");
      tmDefault.addColumn("Datum zadu\u017eenja");
*/
      if (tmLending.getRowCount()>0){

        for(int k = 0; k < tmLending.getRowCount(); k++){
          Object [] dataRow = new Object[5];
          dataRow[0] = tmLending.getValueAt(k,1);

          try {
            Statement stmt = dbConn.getConnection().createStatement();
            ResultSet rset = stmt.executeQuery("select first_name, last_name from single where user_id='"+tmLending.getValueAt(k,1)+"'");
            while(rset.next()){
              dataRow[1] = rset.getString("FIRST_NAME");
              dataRow[2] = rset.getString("LAST_NAME");
            }
            rset.close();
            dbConn.getConnection().commit();
            stmt.close();
          } catch (Exception ex) {
            System.out.println("Greska kod nalazenja imena i prezimena");
            System.out.println(ex);
          }

          dataRow[3] = tmLending.getValueAt(k,2);
          dataRow[4] = tmLending.getValueAt(k,4);
          tmDefault.addRow(dataRow);
          tmDefault.setRowEditable(tmDefault.getRowCount()-1,false);
        }
        tblLending.setModel(tmDefault);
        tblLending.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblLending.sizeColumnsToFit(1);

        tblLending.setRowSelectionInterval(0,0);

        tfCtlgNo.setText(ctlg_no);
        Extract.getPrefContents(value, dbConn.getConnection(), true);
        tfSigNo.setText(Extract.getSigNo());
        tfAuthor.setText(Extract.getAuthor());
        tfTitle.setText(Extract.getTitle());
        btnWarn.setEnabled(true);
      }
    }
    else {
      btnWarn.setEnabled(false);
      tblLending.setModel(tmDefault);
      tblLending.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblLending.sizeColumnsToFit(1);
     }
  }

  void btnFind_actionPerformed(ActionEvent e) {
    circ.displaySignNo();
  }
  
  void btnFindCtlgno_actionPerformed(ActionEvent e) {
  	ctlgno.setVisible(true);
  }


  void btnExit_actionPerformed(ActionEvent e) {
    circ.removeAddListener();
    circ.removeExitListener();
    ctlgno.removeOKListener();
    setVisible(false);
  }

  void btnWarn_actionPerformed(ActionEvent e) {
    Opomena opomena = new Opomena(dbConn);
    opomena.makeOne((String)tmLending.getValueAt(tblLending.getSelectedRow(),1));
  }

  void btnSame_actionPerformed(ActionEvent e) {
    circ.setVisible(true);
  }
}

