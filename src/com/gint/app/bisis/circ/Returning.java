
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import com.borland.jbcl.layout.*;

import java.awt.event.*;

import com.gint.app.bisis.circ.reports.Opomena;
import com.gint.app.bisis.circ.reports.Revers;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;

public class Returning extends EscapeDialog {
  boolean closed = true;
  int selected_row = -1;
  DBConnection db_conn;
  DataSet dsSingle, dsReturning, dsLendType;
  JDBTableModel tmReturning;
  EditableTableModel tmDefault;
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel lSingleID = new JLabel();
  JLabel lRDate = new JLabel();
  JTable tblReturning;// = new JTable();
  JScrollPane sp1;
  //JDBComboBoxModel cmLendType;
  JLabel tfRDate = new JLabel("");
  JLabel tfSingleID = new JLabel("");
  JLabel lName = new JLabel();
  JLabel tfName = new JLabel("");
  JLabel lUntil = new JLabel("");
  JLabel tfUntil = new JLabel("");
  JLabel lWarning = new JLabel();
  JButton btnWarn = new JButton();
  JButton btnNote = new JButton();
  JButton btnDelete = new JButton();
  JButton btnExit = new JButton();
  JButton btnRevers = new JButton();
  JLabel tfAuthor = new JLabel();
  JLabel tfTitle = new JLabel();
  JLabel lAuthor = new JLabel();
  JLabel lTitle = new JLabel();
  JLabel lEndDate = new JLabel();
  JLabel lDeadLine = new JLabel();
  ListSelectionModel lsmReturning;
  //JComboBox cboLendType = new JComboBox();
  JCheckBox chkRevers = new JCheckBox();
  WarningNote wnote;

  public Returning(Frame frame, String title, boolean modal,DBConnection db) {
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

  public Returning() {
    this(CircApplet.parent, "", false,null);
  }

  public Returning(String title,DBConnection db_conn, boolean modal) {
    this(CircApplet.parent, title, modal,db_conn);
  }

  void jbInit() throws Exception {
    dsSingle = db_conn.getSingleModel();
    dsLendType = db_conn.getLendType();
    //cmLendType = new JDBComboBoxModel(dsLendType,"NAME");
    //cboLendType.setModel(cmLendType);

    dsReturning = new DataSet(db_conn.getConnection(),"LENDING",
                            new String[]{"LEND_DATE", "SINGLE_ID","CTLG_NO","COUNTER"});
    tblReturning = new JTable();
    lsmReturning = tblReturning.getSelectionModel();
    lsmReturning.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblReturning.setSelectionModel(lsmReturning);
    sp1 = new JScrollPane(tblReturning);
    lDeadLine.setText(Messages.get("RETURNING_LDEADLINE_TEXT"));
/*    tblReturning.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        tblReturning_mouseClicked(e);
      }
    });*/
    xYLayout1.setWidth(540);
    xYLayout1.setHeight(400);
    xYLayout2.setWidth(240);
    xYLayout2.setHeight(30);
    tfRDate.setText("");
    tfRDate.setForeground(Color.blue);
    tfSingleID.setText("1");
    tfSingleID.setForeground(Color.blue);
    lUntil.setText(Messages.get("RETURNING_VALIDUNTIL_DATE"));
    tfUntil.setForeground(Color.blue);
    lName.setText(Messages.get("RETURNING_LNAME_TEXT"));
    tfName.setText("1");
    tfName.setForeground(Color.blue);
    lWarning.setText(Messages.get("RETURNING_LWARNING_TEXT"));
    lWarning.setEnabled(false);
    lWarning.setForeground(Color.red);
    tfTitle.setText("?");
    tfTitle.setForeground(Color.blue);
    tfAuthor.setText("?");
    tfAuthor.setForeground(Color.blue);
    lAuthor.setText(Messages.get("RETURNING_LAUTHOR_TEXT"));
    lTitle.setText(Messages.get("RETURNING_LTITLE_TEXT"));
    lEndDate.setText("?");
    lEndDate.setForeground(Color.blue);
    chkRevers.setText("Dva primerka");
    INIFile iniFile = new INIFile(
            FileUtils.getClassDir(Returning.class) + "/config.ini"); 
        String tmp = iniFile.getString("circ", "revers");
        if (tmp != null && tmp.equals("1")){
        	chkRevers.setSelected(false);
        }else{
        	chkRevers.setSelected(true);
        }
    
    btnWarn.setEnabled(false);
    btnWarn.setText(Messages.get("RETURNING_BTNWARN_TEXT"));
    btnWarn.setForeground(Color.red);
    btnWarn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnWarn_actionPerformed(e);
      }
    });
    btnNote.setEnabled(false);
    btnNote.setForeground(Color.blue);
    btnNote.setText(Messages.get("RETURNING_BTNNOTE_TEXT"));
    btnNote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNote_actionPerformed(e);
      }
    });
    btnDelete.setText(Messages.get("RETURNING_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });
    btnExit.setText(Messages.get("RETURNING_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    btnRevers.setText(Messages.get("LENDING_BTNREVERS_TEXT"));
    btnRevers.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          btnRevers_actionPerformed(e);
        }
    });
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    lSingleID.setText(Messages.get("RETURNING_LSINGLEID_TEXT"));
    lRDate.setText(Messages.get("RETURNING_LRDATE_TEXT"));
    panel1.add(lSingleID, new XYConstraints(16, 10, -1, -1));
    panel1.add(lRDate, new XYConstraints(16, 75, -1, -1));
    panel1.add(sp1, new XYConstraints(16, 135, 485, 133));
    panel1.add(tfRDate, new XYConstraints(139, 75, -1, -1));
    panel1.add(tfSingleID, new XYConstraints(139, 10, -1, -1));
    panel1.add(lName, new XYConstraints(16, 31, -1, -1));
    panel1.add(tfName, new XYConstraints(139, 31, -1, -1));
    panel1.add(lUntil, new XYConstraints(16, 52, -1, -1));
    panel1.add(tfUntil, new XYConstraints(139, 52, -1, -1));
    panel1.add(lWarning, new XYConstraints(30, 272, -1, -1));
    panel1.add(btnWarn, new XYConstraints(27, 298, 90, -1));
    panel1.add(btnNote, new XYConstraints(403, 350, -1, -1));
    panel1.add(btnDelete, new XYConstraints(210, 298, 90, -1));
    panel1.add(tfAuthor, new XYConstraints(64, 115, 434, -1));
    panel1.add(tfTitle, new XYConstraints(64, 95, 435, -1));
    panel1.add(lAuthor, new XYConstraints(16, 115, -1, -1));
    panel1.add(lTitle, new XYConstraints(16, 95, -1, -1));
    panel1.add(lEndDate, new XYConstraints(335, 75, 75, -1));
    panel1.add(lDeadLine, new XYConstraints(250, 75, -1, -1));
    panel1.add(btnExit, new XYConstraints(403, 298, 90, -1));
    //panel1.add(btnRevers, new XYConstraints(150, 298, 90, -1));
    panel2.setLayout(xYLayout2);
    panel2.setBorder(new TitledBorder("Revers"));
    panel2.add(chkRevers, new XYConstraints(2, 2, -1, -1));
    panel2.add(btnRevers, new XYConstraints(140, 2, 90, -1));
    panel1.add(panel2, new XYConstraints(25, 330, -1, -1));
    lsmReturning.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmReturning_valueChanged(e);
      }
    });
 /*   tblReturning.addKeyListener(new KeyAdapter(){
	      public void keyPressed(KeyEvent e){
	        if(e.getKeyCode()==KeyEvent.VK_ENTER){
	          btnDelete.doClick();
	        }
	      }
	    });
    tblReturning.addKeyListener(new KeyAdapter(){
	      public void keyReleased(KeyEvent e){
	        if(e.getKeyCode()==KeyEvent.VK_ENTER){
	        	selected_row = -1;
	        }
	      }
    	});*/
  }

  public void lsmReturning_valueChanged(ListSelectionEvent e){
    int days = 0;
    String s = "";

    if(!lsmReturning.getValueIsAdjusting() && !lsmReturning.isSelectionEmpty()){
      int row = tblReturning.getSelectedRow();
      if(row != selected_row)
        selected_row = row;
   System.out.print("<Returning> Selected row:"+selected_row);
      Extract.getPrefContents((String)tmReturning.getValueAt(selected_row,0),db_conn.getConnection(),true);
      days = Extract.getPeriod(db_conn.getConnection(),tfSingleID.getText(),(String)tmReturning.getValueAt(selected_row,6));
      tfAuthor.setText(Extract.getAuthor());
//      tfTitle.setText(Extract.getTitle());

        String ser =(String) tmReturning.getValueAt(selected_row,8);
        if (ser.equals("") || ser==null || ser.equals("null"))
          tfTitle.setText(Extract.getTitle());
        else
          tfTitle.setText(Extract.getTitle()+"/"+ser);


      s = (String)tmReturning.getValueAt(selected_row,4);
      if(s.equals("")) 
        s = (String)tmReturning.getValueAt(selected_row,2);
      java.util.Date now = new java.util.Date(System.currentTimeMillis());
      CoolDate cd = new CoolDate(DBConnection.StringToTime(s));
      CoolDate endDate = new CoolDate((cd.plus(days)).getTime());
      lEndDate.setText(DBConnection.formatDate(endDate));
      if(now.after(endDate)){
        lWarning.setEnabled(true);
        btnWarn.setEnabled(true);
      }
      else{
        lWarning.setEnabled(false);
        btnWarn.setEnabled(false);
      }
      
      try{
          String qry = "select * from warnings where user_id ='" + tfSingleID.getText() + "' and ctlg_no ='"+(String)tmReturning.getValueAt(selected_row,0)+"' and return_date is null";
          Statement stmt = db_conn.getConnection().createStatement();
          ResultSet rset = stmt.executeQuery(qry);
          if (rset.next()){
          	btnNote.setEnabled(true);
          }else {
          	btnNote.setEnabled(false);
          }
          rset.close();
          stmt.close();
          db_conn.getConnection().commit();
      }catch(SQLException ex){
          ex.printStackTrace();
      }
      
    }
  }

  public void setVisible(boolean vis){
    closed = true;
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
    closed = true;
    setPosition(vis, id);
  }

  private void setPosition(boolean vis, String id){
    tfAuthor.setText("");
    tfTitle.setText("");
    tmReturning = new JDBTableModel(dsReturning,"select * from LENDING where SINGLE_ID ='"+id+"' AND RETURN_DATE is null ORDER BY CTLG_NO");
    tmDefault = new EditableTableModel();
    tmDefault.addColumn(Messages.get("RETURNING_TMFDEF_COL1"));
    tmDefault.addColumn(Messages.get("RETURNING_TMFDEF_COL2"));
    tmDefault.addColumn(Messages.get("RETURNING_TMFDEF_COL3"));
    tmDefault.addColumn(Messages.get("RETURNING_TMFDEF_COL4"));

    if (tmReturning.getRowCount()>0){
      lWarning.setEnabled(false);
      btnWarn.setEnabled(false);
//      tmReturning.setEditable(false);
//      tblReturning.setModel(tmReturning);
      btnNote.setEnabled(false);
    	
      for(int k = 0; k < tmReturning.getRowCount(); k++){
        Object [] dataRow = new Object[4];

        dsLendType.setCurrentRow(new String[]{"ID"},new String[]{(String)tmReturning.getValueAt(k,6)});
        dataRow[0] = tmReturning.getValueAt(k,0);
        dataRow[1] = tmReturning.getValueAt(k,2);
        dataRow[2] = dsLendType.getRow().getData("NAME");
        dataRow[3] = tmReturning.getValueAt(k,4);
        tmDefault.addRow(dataRow);
        tmDefault.setRowEditable(tmDefault.getRowCount()-1,false);
      }
/*
      TableColumnModel tcm = tblReturning.getColumnModel();
      int i = 0;
      while(i < tcm.getColumnCount()){
        TableColumn tc = (TableColumn)tcm.getColumn(i);
        if(tc.getHeaderValue().toString().equals("SINGLE_ID"))
            tcm.removeColumn(tc);
        else if (tc.getHeaderValue().toString().equals("OFFICIAL_ID"))
            tcm.removeColumn(tc);
        else if (tc.getHeaderValue().toString().equals("LEND_TYPE"))
            tcm.removeColumn(tc);
        else if (tc.getHeaderValue().toString().equals("LOCATION"))
            tcm.removeColumn(tc);
        else if (tc.getHeaderValue().toString().equals("RETURN_DATE"))
            tcm.removeColumn(tc);
        else if (tc.getHeaderValue().toString().equals("RESUME_DATE"))
            tcm.removeColumn(tc);
        else if (tc.getHeaderValue().toString().equals("CTLG_NO")) {
            tc.setHeaderValue("Inventarni broj");
            i++;
        }
        else if (tc.getHeaderValue().toString().equals("LEND_DATE")) {
            tc.setHeaderValue("Datum zadu\u017Eenja");
            i++;
        }
        else
           i++;
      }
      tblReturning.setColumnModel(tcm);
      tblReturning.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblReturning.sizeColumnsToFit(1);
*/
      tblReturning.setModel(tmDefault);
      tblReturning.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblReturning.sizeColumnsToFit(1);


      dsSingle.setCurrentRow(new String[]{"USER_ID"}, new String[]{id});
      tfSingleID.setText(dsSingle.getRow().getData("USER_ID"));
      tfName.setText(dsSingle.getRow().getData("LAST_NAME")+", "+dsSingle.getRow().getData("FIRST_NAME"));
      tfRDate.setText(DBConnection.getTodaysDate());
      
      try{
        String qry = "select until_date from signing where single_id ='" + id + "' order by until_date desc";
        Statement stmt = db_conn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        if (rset.next()){
        	tfUntil.setText(DBConnection.formatDate(rset.getTimestamp(1)).toString());
        }else {
        	tfUntil.setText(Messages.get("SINGLEMEMBER_TFSINGLEUNTIL_TEXT2"));
        }
        rset.close();
        stmt.close();
        db_conn.getConnection().commit();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
      super.setVisible(vis);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("RETURNING_OPTIONPANE_MSG1")+" "+id+" "+Messages.get("RETURNING_OPTIONPANE_MSG2"),Messages.get("RETURNING_OPTIONPANE_MSG3"),JOptionPane.INFORMATION_MESSAGE);
      this.setVisible(false);
    }
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    try{
      String query = "update LENDING set RETURN_DATE =\'"+DBConnection.toDatabaseDate(tfRDate.getText())+"\'" ;
      query += (" where CTLG_NO =\'"+(String)tmReturning.getValueAt(selected_row,0)+"\'"+" AND SINGLE_ID = '"+tfSingleID.getText());
      query += ("' AND LEND_DATE = \'"+DBConnection.toDatabaseDate((String)tmReturning.getValueAt(selected_row,2))+"\' AND COUNTER ="+(String)tmReturning.getValueAt(selected_row,9));
      Statement stmt = db_conn.getConnection().createStatement();
      int i  = stmt.executeUpdate(query);
      String ser = (String)tmReturning.getValueAt(selected_row,8);
      if (!jeSerijska((String)tmReturning.getValueAt(selected_row,0)) && i == 1)
         i = stmt.executeUpdate("update circ_docs set status = 0 where ctlg_no =\'"+tmReturning.getValueAt(selected_row,0)+"\'");
      
      query = "update warnings set return_date = '"+DBConnection.toDatabaseDate(tfRDate.getText())+"' where ctlg_no ='"+(String)tmReturning.getValueAt(selected_row,0)+"'"+" and user_id = '"+tfSingleID.getText()+"'";
      stmt.executeUpdate(query);
      
      db_conn.getConnection().commit();
      stmt.close();
tmDefault.removeRow(selected_row);  // TanjaT
      tfAuthor.setText("");
      tfTitle.setText("");
      lEndDate.setText("?");
      selected_row=-1;

      tmReturning.reloadSet();
      lWarning.setEnabled(false);
      btnWarn.setEnabled(false);
      btnNote.setEnabled(false);
      
      setBlocked();
      
    }catch(SQLException ex){
      ex.printStackTrace();
    }
// moram da brisem iz baze, i da vratim STATUS na 0;
  }
  
  void setBlocked(){
	  try{
		  String query = "select * from warnings where user_id = '"+tfSingleID.getText()+"' and return_date is null";
		  Statement stmt = db_conn.getConnection().createStatement();
		  ResultSet rset = stmt.executeQuery(query);
		  if (!rset.next()){
			  stmt.executeUpdate("delete from blocked_cards where user_id = '"+tfSingleID.getText()+"' and reason_id = 2");
			  db_conn.getConnection().commit();
		  }
		  rset.close();
		  stmt.close();
	  }catch(SQLException ex){
	      ex.printStackTrace();
	  }
  }

  void btnExit_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void tblReturning_mouseClicked(MouseEvent e) {
  }

  void btnWarn_actionPerformed(ActionEvent e) {
    Opomena opomena = new Opomena(db_conn);
    opomena.makeOne(tfSingleID.getText());
  }
  
  
  void btnNote_actionPerformed(ActionEvent e) {
	wnote = new WarningNote(this,true);
  	wnote.setLocationRelativeTo(null);
  	KeyAdapter ka = new KeyAdapter(){
  		public void keyReleased(java.awt.event.KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if (!wnote.getValue().equals("")){
						try{
				      		Statement stmt = db_conn.getConnection().createStatement();
				      		stmt.executeUpdate("update warnings set note = '" + wnote.getValue()+"' where user_id ='" + tfSingleID.getText() + "' and ctlg_no ='"+(String)tmReturning.getValueAt(selected_row,0)+"' and return_date is null");
				      		db_conn.getConnection().commit();
				      		stmt.close();
				      		wnote.dispose();
						}catch(SQLException e1){
				      	}
						
					}
				}
			}
  	};
  	wnote.addListener(ka);
  	wnote.setVisible(true);
  }
  
  void btnRevers_actionPerformed(ActionEvent e) {
  	int count = tmReturning.getRowCount();
  	Vector invs = new Vector();
  	if(count > 0){
  		for(int i = 0; i < count; i++) {	
  			Vector temp = new Vector();
  			temp.add((String)tmReturning.getValueAt(i,0));//ctlg_no
  			temp.add((String)tmReturning.getValueAt(i,2));//lend_date
  			temp.add((String)tmReturning.getValueAt(i,4));//resume_date
  			temp.add((String)tmReturning.getValueAt(i,6));//lend_type
  			invs.add(temp);
  		}
      Revers revers = new Revers(db_conn);
      revers.makeOne(tfSingleID.getText(),invs,tfRDate.getText(), chkRevers.isSelected());
    }
    else
      JOptionPane.showMessageDialog(null,Messages.get("LENDING_OPTIONPANE_MSG16"),Messages.get("LENDING_OPTIONPANE_MSG17"),JOptionPane.INFORMATION_MESSAGE);
  }

  public boolean jeSerijska(String ctlg_no) {
    boolean serijska=false;
    String query = "select type from CIRC_DOCS where CTLG_NO =\'"+ctlg_no+"\'";

    try {
      Statement stmt = db_conn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(query);
      if (rset.next()) {
        String tip = rset.getString("TYPE");
        if (tip==null)
          tip="";
        if (tip.equals("004") || tip.equals("005"))
          serijska=true;
        else
          serijska=false;
      }
      else
        serijska=false;
      rset.close();
      stmt.close();
      db_conn.getConnection().commit();
    }
    catch (SQLException e){
      System.out.println("Greska kod jeSerijska()");
      e.printStackTrace();
    }
    return serijska;
  }
}

