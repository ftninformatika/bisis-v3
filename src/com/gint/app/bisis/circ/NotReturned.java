
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.borland.jbcl.layout.*;

public class NotReturned extends EscapeDialog {
  private boolean closed = true;
  private int selected_row = -1;

  private DBConnection db_conn;
  private ListSelectionModel lsmResults;

  JPanel pMain = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton btnGoTo = new JButton();
  JButton btnCancel = new JButton();
  JScrollPane sp1 ;
  JTable tblResults;
  EditableTableModel tmResults = new EditableTableModel();
  JButton btnPrint = new JButton();
  ActionListener alOK;

  public NotReturned(Frame frame, String title, boolean modal,DBConnection db) {
    super(frame, title, modal);
    db_conn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public NotReturned() {
    this(null, "", false,null);
  }

  public NotReturned(String title,DBConnection db, boolean modal){
    this(null,title,modal,db);
  }

  void jbInit() throws Exception {
    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
      }
    });

    xYLayout1.setWidth(535);
    xYLayout1.setHeight(300);
    pMain.setMaximumSize(new Dimension(535, 300));
    pMain.setSize(new Dimension(500, 300));
    pMain.setLayout(xYLayout1);
    getContentPane().add(pMain);

    tblResults = new JTable(tmResults);
    ListSelectionModel tmp = tblResults.getSelectionModel();
    tmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblResults.setSelectionModel(tmp);
    tblResults.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblResults.sizeColumnsToFit(1);
    sp1=new JScrollPane(tblResults);
    btnPrint.setText(Messages.get("NEWMEMBER_BTNPRINT_TEXT"));
    btnPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrint_actionPerformed(e);
      }
    });
    pMain.add(btnGoTo, new XYConstraints(114, 249, 75, -1));
    pMain.add(btnCancel, new XYConstraints(227, 249, 75, -1));
    pMain.add(sp1, new XYConstraints(23, 35, 490, 206));
    pMain.add(btnPrint, new XYConstraints(339, 249, 85, -1));

    btnGoTo.setText(Messages.get("NEWMEMBER_BTNGOTO_TEXT"));

    btnCancel.setText(Messages.get("NEWMEMBER_BTNCANCEL_TEXT"));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    lsmResults = tblResults.getSelectionModel();
    btnGoTo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnGoTo_actionPerformed(e);
      }
    });
    btnPrint.setPreferredSize(new Dimension(90, 25));
    btnPrint.setMinimumSize(new Dimension(85, 25));
    lsmResults.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmResults_valueChanged(e);
      }
    });

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed) populate();
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        closed = true;
        setVisible(false);
      }
    });
  }

  public void lsmResults_valueChanged(ListSelectionEvent e){
    if(!lsmResults.getValueIsAdjusting()){
      int row = tblResults.getSelectedRow();
      if (row != selected_row) 
        selected_row = row;
    }
  }

  void btnCancel_actionPerformed(ActionEvent e){
    this.setVisible(false);
  }

  void btnPrint_actionPerformed(ActionEvent e) {

  }

  public void populate(){
    String id = "", name = "",surname ="", lend_date = "", author = "", title = "", ctlg_no ="";
    boolean flag = false;
    tmResults = new EditableTableModel();
    tblResults.setModel(tmResults);
    tmResults.addColumn(Messages.get("NOTRETURNED_TMRESULTS_COL1"));
    tmResults.addColumn(Messages.get("NOTRETURNED_TMRESULTS_COL1"));
    tmResults.addColumn(Messages.get("NOTRETURNED_TMRESULTS_COL1"));
    tmResults.addColumn(Messages.get("NOTRETURNED_TMRESULTS_COL1"));
    tmResults.addColumn(Messages.get("NOTRETURNED_TMRESULTS_COL1"));
    tmResults.addColumn(Messages.get("NOTRETURNED_TMRESULTS_COL1"));
    try{
      Statement stmt = db_conn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery("select single_id, ctlg_no, lend_date from lending where return_date is null order by single_id");

      while(rset.next()){
        Object [] data;
        id = rset.getString(1);
        ctlg_no = rset.getString(2);
        lend_date = DBConnection.TSToObject(rset.getTimestamp(3)).toString();
        Statement sm = db_conn.getConnection().createStatement();
        ResultSet rs = sm.executeQuery("select last_name, first_name from single where user_id ='"+id+"'");
        if(rs.next()){
          surname = rs.getString(1);
          name = rs.getString(2);
        }
        rs.close();
        sm.close();
        Extract.getPrefContents(ctlg_no,db_conn.getConnection(),true);
        author = Extract.getAuthor();
        title = Extract.getTitle();
        data = new Object[] { id , surname, name, lend_date, author, title} ;
        tmResults.addRow(data);
        tmResults.setRowEditable(tmResults.getRowCount()-1,false);
      }
      tblResults.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblResults.sizeColumnsToFit(1);
      rset.close();
      stmt.close();
      db_conn.getConnection().commit();
    }catch(SQLException ex){
      ex.printStackTrace();
    }
  }

  public void addOKListener(ActionListener act){
    alOK = act;
    btnGoTo.addActionListener(alOK);
  }

  public void removeOKListener(){
    btnGoTo.removeActionListener(alOK);
  }

  public String getValue(){
    return (String)tmResults.getValueAt(selected_row,0);
  }

  void btnGoTo_actionPerformed(ActionEvent e) {
    populate();
  }
}

