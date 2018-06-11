
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.sql.*;
import java.util.StringTokenizer;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.borland.jbcl.layout.*;

public class FindMember extends EscapeDialog {

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
  JTextField tfString = new JTextField();
  JLabel lSearch = new JLabel();
  JComboBox cboSearchField = new JComboBox(new Object[]{
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT1"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT2"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT3"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT4"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT5"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT6"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT7"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT8"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT9"),
                                                          Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT10"),
														  Messages.get("FINDMEMBER_CBOSEARCHFIELD_OPT11"),
                                                        });
  JLabel lText = new JLabel();
  JButton btnFind = new JButton();
  ActionListener alOK;

  public FindMember(Frame frame, String title, boolean modal,DBConnection db) {
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

  public FindMember() {
    this(CircApplet.parent, "", false,null);
  }

  public FindMember(String title,DBConnection db, boolean modal){
    this(CircApplet.parent,title,modal,db);
  }

  void jbInit() throws Exception {
    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        clear();
      }
    });

    xYLayout1.setWidth(500);
    xYLayout1.setHeight(300);
    pMain.setMaximumSize(new Dimension(500, 300));
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
    cboSearchField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cboSearchField_actionPerformed(e);
      }
    });
    btnFind.setText(Messages.get("FINDMEMBER_BTNFIND_TEXT"));
    btnFind.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFind_actionPerformed(e);
      }
    });

    lText.setText(Messages.get("FINDMEMBER_LTEXT_TEXT"));
    lSearch.setText(Messages.get("FINDMEMBER_LSEARCH_TEXT"));

    tfString.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          btnFind.doClick();
      }
    });

    pMain.add(btnGoTo, new XYConstraints(119, 250, 75, -1));
    pMain.add(btnCancel, new XYConstraints(293, 250, 75, -1));
    pMain.add(tfString, new XYConstraints(83, 42, 130, -1));
    pMain.add(sp1, new XYConstraints(23, 86, 452, 155));
    pMain.add(lSearch, new XYConstraints(23, 15, -1, -1));
    pMain.add(cboSearchField, new XYConstraints(84, 10, -1, -1));
    pMain.add(lText, new XYConstraints(23, 44, -1, -1));
    pMain.add(btnFind, new XYConstraints(293, 39, 75, -1));

    btnGoTo.setText(Messages.get("FINDMEMBER_BTNGOTO_TEXT"));
    btnGoTo.setEnabled(false);

    btnCancel.setText(Messages.get("FINDMEMBER_BTNCANCEL_TEXT"));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    lsmResults = tblResults.getSelectionModel();
    lsmResults.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmResults_valueChanged(e);
      }
    });
  }

  public void lsmResults_valueChanged(ListSelectionEvent e){
    if(!lsmResults.getValueIsAdjusting()){
      int row = tblResults.getSelectedRow();
      if(row != selected_row) selected_row = row;
      System.out.println("<FindMember> Selected row:"+selected_row);
      btnGoTo.setEnabled(true);
    }
  }

  void btnCancel_actionPerformed(ActionEvent e){
    clear();
    this.setVisible(false);
  }

  void btnFind_actionPerformed(ActionEvent e) {
    btnGoTo.setEnabled(false);
    if (tfString.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("FINDMEMBER_OPTIONPANE_MSG1"),Messages.get("FINDMEMBER_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
    }
    else {
    StringTokenizer st;
    String search = "", name = "",surname ="", query_str = "", parent="";
    query_str = "select user_id,";
    boolean flag = false;
    tmResults = new EditableTableModel();
    tblResults.setModel(tmResults);
    switch(cboSearchField.getSelectedIndex()){
// Prezime, Ime
      case 0: st = new StringTokenizer(tfString.getText().trim()," ,");
              surname = st.nextToken().toUpperCase();
              if (st.hasMoreTokens())
                name = st.nextToken().toUpperCase();
              else {
                JOptionPane.showMessageDialog(null,Messages.get("FINDMEMBER_OPTIONPANE_MSG3"),Messages.get("FINDMEMBER_OPTIONPANE_MSG4"),JOptionPane.ERROR_MESSAGE);
                return;
              }
              query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single"+
                " where upper(first_name) like \'"+name+"%\' and upper(last_name) like \'"+surname+"%\' order by last_name, first_name";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS1_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS1_COL2"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS1_COL3"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS1_COL4"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS1_COL5"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS1_COL6"));

              flag = true;
              break;
// Prezime, Ime roditelja, Ime
      case 1: st = new StringTokenizer(tfString.getText().trim()," ,");
              surname = st.nextToken().toUpperCase();
              if (st.hasMoreTokens())
                parent = st.nextToken().toUpperCase();
              else {
                JOptionPane.showMessageDialog(null,Messages.get("FINDMEMBER_OPTIONPANE_MSG5"),Messages.get("FINDMEMBER_OPTIONPANE_MSG6"),JOptionPane.ERROR_MESSAGE);
                return;
              }
              if (st.hasMoreTokens())
                name = st.nextToken().toUpperCase();
              else {
                JOptionPane.showMessageDialog(null,Messages.get("FINDMEMBER_OPTIONPANE_MSG7"),Messages.get("FINDMEMBER_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
//                btnGoTo.setEnabled(false);
                return;
              }

              query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single"+
                " where upper(first_name) like \'"+name+"%\' and upper(last_name) like \'"+surname+"%\' and upper(parent_name) like \'"+parent+"%\' order by last_name, first_name";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS2_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS2_COL2"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS2_COL3"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS2_COL4"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS2_COL5"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS2_COL6"));

              flag = true;
              break;


// Prezime
      case 2: query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single where upper(last_name) like \'"+(tfString.getText()).trim().toUpperCase()+"%\' order by last_name, first_name";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS3_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS3_COL2"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS3_COL3"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS3_COL4"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS3_COL5"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS3_COL6"));

              flag = true;
              break;
// Ime
      case 3: query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single where upper(first_name) like \'"+(tfString.getText()).trim().toUpperCase()+"%\' order by first_name, last_name ";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL2"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL3"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL4"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL5"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL6"));

              flag = true;
              break;
// JMBG
      case 4: query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single where jmbg like \'"+(tfString.getText()).trim()+"%\' order by jmbg";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS5_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS5_COL2"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS5_COL3"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS5_COL4"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS5_COL5"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS5_COL6"));

              flag = true;
              break;
// Boroj indeksa
      case 5: query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single where index_no like \'"+(tfString.getText()).trim()+"%\' order by index_no";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS6_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS6_COL2"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS6_COL3"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS6_COL4"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS6_COL5"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS6_COL6"));

              flag = true;
              break;
// Organiz
      case 6: query_str = "select user_id, inst_name from groups where upper(inst_name) like \'"+(tfString.getText()).trim().toUpperCase()+"%\'";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS7_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS7_COL2"));
              break;
// Inst Razmene
      case 7: query_str = "select user_id, inst_name from institutions where upper(inst_name) like \'"+(tfString.getText()).trim().toUpperCase()+"%\'";
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS8_COL1"));
              tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS8_COL2"));
              break;
// Inventarni broj
      case 8: query_str = "select s.user_id, s.last_name, s.first_name, s.parent_name, s.jmbg, s.index_no from single s, lending l where s.user_id = l.single_id and l.ctlg_no = \'"+DBConnection.toDBInventory((tfString.getText()).trim())+"\' and return_date is null order by last_name, first_name";
      		  tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL1"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL2"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL3"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL4"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL5"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL6"));

		      flag = true;
		      break;   
// Broj priznanice		      
      case 9: query_str = "select s.user_id, s.last_name, s.first_name, s.parent_name, s.jmbg, s.index_no from single s, signing sg where s.user_id = sg.single_id and sg.receipt_id = \'"+tfString.getText().trim()+"\' order by last_name, first_name";
			  tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL1"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL2"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL3"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL4"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL5"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL6"));
	
		      flag = true;
		      break;
//Broj kolektivnog clana		      
      case 10: query_str = "select user_id, last_name, first_name, parent_name, jmbg, index_no from single where grp_id = \'"+DBConnection.toDBMember(tfString.getText().trim())+"\' order by last_name, first_name";
			  tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL1"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL2"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL3"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL4"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL5"));
		      tmResults.addColumn(Messages.get("FINDMEMBER_TMRESULTS4_COL6"));
	
		      flag = true;
		      break;
      	
    }
    try{
      Statement stmt = db_conn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(query_str);
      String jmbg=new String();
      String indexNo=new String();
      while(rset.next()){
        Object [] data;
        if(flag) {
          jmbg = (String) ""+rset.getObject(5);
          indexNo = (String) ""+rset.getObject(6);
          if (jmbg == null || jmbg.equals("") || jmbg.equals("null"))
            jmbg="  ";
          if (indexNo == null || indexNo.equals("") || indexNo.equals("null"))
            indexNo="  ";
          data = new Object[] {rset.getString(1),rset.getString(2),rset.getString(3),rset.getString(4),jmbg,indexNo} ;
        }
        else
          data = new Object[] {rset.getString(1),rset.getString(2)} ;
        tmResults.addRow(data);
        tmResults.setRowEditable(tmResults.getRowCount()-1,false);
      }
      tblResults.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblResults.sizeColumnsToFit(1);
      rset.close();
      db_conn.getConnection().commit();
      stmt.close();
    }catch(SQLException ex){
      JOptionPane.showMessageDialog(null,Messages.get("FINDMEMBER_OPTIONPANE_MSG9")+ex.getMessage(), Messages.get("FINDMEMBER_OPTIONPANE_MSG10"),JOptionPane.ERROR_MESSAGE);
//      btnGoTo.setEnabled(false);
//      ex.printStackTrace();
    }
    }
  }

  void cboSearchField_actionPerformed(ActionEvent e) {
    clear();
//    tfString.setText("");
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
/*
  void jCheckBox1_actionPerformed(ActionEvent e) {

  }
*/
  void clear() {
    tfString.setText("");
    tmResults = new EditableTableModel();
    tblResults.setModel(tmResults);
    btnGoTo.setEnabled(false);
  }

}

