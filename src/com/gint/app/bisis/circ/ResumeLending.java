
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
import javax.swing.event.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import com.gint.app.bisis.circ.reports.*;

public class ResumeLending extends EscapeDialog {
  boolean closed = true;
  int selected_row = -1;
  DBConnection db_conn;
  DataSet dsResume, dsSingle, dsUsers, dsLendType, dsPlace;
  JDBTableModel tmResume;
  EditableTableModel tmDefault;
  JDBComboBoxModel cmPlace, cmLendType;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lMmbrID = new JLabel();
  JLabel lRDate = new JLabel();
  JTable tblResume = new JTable();
  JScrollPane sp1;
  JLabel tfName = new JLabel("1");
  JLabel tfRDate = new JLabel("1");
  JLabel tfSingleID = new JLabel("1");
//  JLabel lRealAuthor = new JLabel();
//  JLabel lRealTitle = new JLabel();
  JLabel lEndDate = new JLabel();
  JLabel lName = new JLabel();
  JLabel lWarning = new JLabel();
  JComboBox cboLendType = new JComboBox();
  JComboBox cboLocation = new JComboBox();
  JButton btnSave = new JButton();
  JButton btnExit = new JButton();
  JButton btnWarn = new JButton();
  JLabel lTitle = new JLabel();
  JLabel lAuthor = new JLabel();
  JLabel tfTitle = new JLabel();
  JLabel tfAuthor = new JLabel();
  ListSelectionModel lsmResume;
  JLabel lDeadLine = new JLabel();
  ListSelectionListener lsmListLisener;
  JLabel lUntil = new JLabel();
  JLabel tfUntil = new JLabel();
  JLabel lBlockCard = new JLabel();
  JLabel lBlockCardDesc = new JLabel();
  JLabel lSingleDup = new JLabel();
  //JLabel lSingleDupDate = new JLabel();

  public ResumeLending(Frame frame, String title, boolean modal,DBConnection db) {
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

  public ResumeLending() {
    this(CircApplet.parent, "", false,null);
  }

  public ResumeLending(String title,DBConnection db_conn, boolean modal) {
    this(CircApplet.parent, title, modal,db_conn);
  }

  void jbInit() throws Exception {
    dsUsers = db_conn.getUsersModel();
    dsSingle = db_conn.getSingleModel();
    dsPlace = db_conn.getPlaces();
    dsLendType = db_conn.getLendType();
    dsResume = new DataSet(db_conn.getConnection(),"LENDING",
                            new String[]{"LEND_DATE", "SINGLE_ID","CTLG_NO","COUNTER"});
    cmLendType = new JDBComboBoxModel(dsLendType,"NAME");
    cmPlace = new JDBComboBoxModel(dsPlace,"NAME");
    cboLendType.setModel(cmLendType);
    cboLocation.setModel(cmPlace);
    lsmResume = tblResume.getSelectionModel();
    lsmResume.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblResume.setSelectionModel(lsmResume);
    sp1 = new JScrollPane(tblResume);
    lUntil.setText(Messages.get("RESUMELENDING_LUNTIL_TEXT"));
    lDeadLine.setText(Messages.get("RESUMELENDING_LDEADLINE_TEXT"));
    lTitle.setText(Messages.get("RESUMELENDING_LTITLE_TEXT"));
    lAuthor.setText(Messages.get("RESUMELENDING_LAUTHOR_TEXT"));
    tfTitle.setText("1");
    tfTitle.setForeground(Color.blue);
    tfAuthor.setForeground(Color.blue);
    tfAuthor.setText("1");
    tfName.setForeground(Color.blue);
    tfRDate.setForeground(Color.blue);
    tfSingleID.setForeground(Color.blue);
    tfUntil.setForeground(Color.blue);
    lEndDate.setForeground(Color.blue);
    lBlockCard.setForeground(Color.RED);
    lBlockCard.setText(Messages.get("LENDING_BLOCKCARD_TEXT"));
    lBlockCard.setEnabled(false);
    lBlockCardDesc.setForeground(Color.red);
    lSingleDup.setForeground(Color.blue);
    //lSingleDupDate.setForeground(Color.blue);
    lEndDate.setText("?");
    xYLayout1.setWidth(562);
    xYLayout1.setHeight(400);
    panel1.setLayout(xYLayout1);
    btnWarn.setEnabled(false);
    btnWarn.setText(Messages.get("RESUMELENDING_BTNWARN_TEXT"));
    btnWarn.setForeground(Color.red);
    btnWarn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnWarn_actionPerformed(e);
      }
    });

    lName.setText(Messages.get("RESUMELENDING_LNAME_TEXT"));
    lWarning.setText(Messages.get("RESUMELENDING_LWARNING_TEXT"));
    lWarning.setEnabled(false);
    lWarning.setForeground(Color.red);
    btnSave.setText(Messages.get("RESUMELENDING_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });
    btnExit.setText(Messages.get("RESUMELENDING_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    lMmbrID.setText(Messages.get("RESUMELENDING_LMMBRID_TEXT"));
    lRDate.setText(Messages.get("RESUMELENDING_LRDATE_TEXT"));

/*    tblResume.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
        tblResume_mouseClicked(e);
      }
    });*/

    panel1.add(lMmbrID, new XYConstraints(27, 17, -1, -1));
    panel1.add(lRDate, new XYConstraints(27, 88, -1, -1));
    panel1.add(lEndDate, new XYConstraints(332, 88, 63, -1));
    panel1.add(sp1, new XYConstraints(27, 161, 505, 122));
    panel1.add(tfRDate, new XYConstraints(146, 88, -1, -1));
    panel1.add(tfSingleID, new XYConstraints(146, 17, -1, -1));
    panel1.add(lUntil, new XYConstraints(27, 64, -1, -1));
    panel1.add(tfUntil, new XYConstraints(146, 64, -1, -1));
    panel1.add(btnWarn, new XYConstraints(20, 358, -1, -1));
    panel1.add(lName, new XYConstraints(27, 41, -1, -1));
    panel1.add(lWarning, new XYConstraints(20, 330, -1, -1));
    panel1.add(tfName, new XYConstraints(146, 41, -1, -1));
    panel1.add(btnSave, new XYConstraints(245, 358, 80, -1));
    panel1.add(btnExit, new XYConstraints(450, 358, 80, -1));
    panel1.add(lAuthor, new XYConstraints(27, 135, -1, -1));
    panel1.add(lTitle, new XYConstraints(27, 112, -1, -1));
    panel1.add(tfTitle, new XYConstraints(78, 112, 451, -1));
    panel1.add(tfAuthor, new XYConstraints(78, 135, 451, -1));
    panel1.add(lDeadLine, new XYConstraints(252, 88, -1, -1));
    panel1.add(lBlockCard, new XYConstraints(340, 45, 111, -1));
    panel1.add(lBlockCardDesc, new XYConstraints(340, 65, 150, -1));
    panel1.add(lSingleDup, new XYConstraints(340, 20, -1, -1));
    //panel1.add(lSingleDupDate, new XYConstraints(330, 64, -1, -1));

    lsmListLisener = new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmResume_valueChanged(e);
      }
    };

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed){
          lsmResume.addListSelectionListener(lsmListLisener);
        }
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        lsmResume.removeListSelectionListener(lsmListLisener);
        closed = true;
        setVisible(false);
      }
    });


  }

  public void lsmResume_valueChanged(ListSelectionEvent e){
    int days = 0;
    if(!lsmResume.getValueIsAdjusting()){
      int row = tblResume.getSelectedRow();
      if(row != selected_row) selected_row = row;
      if(selected_row >=0){
        Extract.getPrefContents((String)tmResume.getValueAt(selected_row,0),db_conn.getConnection(),true);
        days = Extract.getPeriod(db_conn.getConnection(),tfSingleID.getText(),(String)tmResume.getValueAt(selected_row,6));
        tfAuthor.setText(Extract.getAuthor());
//        tfTitle.setText(Extract.getTitle());

        String ser =(String) tmResume.getValueAt(selected_row,8);
        if (ser.equals("") || ser.equals(null) || ser.equals("null"))
          tfTitle.setText(Extract.getTitle());
        else
          tfTitle.setText(Extract.getTitle()+"/"+ser);
  // uzimam lend_date kao datum za racunanje roka vracanja, a trebao bih da uzmem resume_date ako ga ima!!!!
  // resume_date je 4 kolona
        String s = (String)tmResume.getValueAt(selected_row,4);
        if(s.equals("")) s = (String)tmResume.getValueAt(selected_row,2);
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
    lEndDate.setText("?");
    tmResume = new JDBTableModel(dsResume,"select * from LENDING where SINGLE_ID ='"+id+"' AND RETURN_DATE is null ORDER BY CTLG_NO");
    tmDefault = new EditableTableModel();
    tmDefault.addColumn(Messages.get("RESUMELENDING_TMFDEF_COL1"));
    tmDefault.addColumn(Messages.get("RESUMELENDING_TMFDEF_COL2"));
    tmDefault.addColumn(Messages.get("RESUMELENDING_TMFDEF_COL3"));
    tmDefault.addColumn(Messages.get("RESUMELENDING_TMFDEF_COL4"));
    tmDefault.addColumn(Messages.get("RESUMELENDING_TMFDEF_COL5"));

    lWarning.setEnabled(false);
    btnWarn.setEnabled(false);

    if (tmResume.getRowCount()>0){
//      tmResume.setEditable(false);
      for(int k = 0; k < tmResume.getRowCount(); k++){
        Object [] dataRow = new Object[5];
        dsPlace.setCurrentRow(new String[]{"ID"},new String[]{(String)tmResume.getValueAt(k,7)});
        dsLendType.setCurrentRow(new String[]{"ID"},new String[]{(String)tmResume.getValueAt(k,6)});
        dataRow[0] = tmResume.getValueAt(k,0);
        dataRow[1] = tmResume.getValueAt(k,2);
        dataRow[2] = dsPlace.getRow().getData("NAME");
        dataRow[3] = dsLendType.getRow().getData("NAME");
        dataRow[4] = tmResume.getValueAt(k,4);
        tmDefault.addRow(dataRow);
        tmDefault.setRowEditable(tmDefault.getRowCount()-1,false);
      }
      tblResume.setModel(tmDefault);
      tblResume.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblResume.sizeColumnsToFit(1);
      dsSingle.setCurrentRow(new String[]{"USER_ID"}, new String[]{id});
      tfSingleID.setText(dsSingle.getRow().getData("USER_ID"));
      tfName.setText(dsSingle.getRow().getData("LAST_NAME")+","+dsSingle.getRow().getData("FIRST_NAME"));
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
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        setDup();
        setWarning();
        
      super.setVisible(vis);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("RESUMELENDING_OPTIONPANE_MSG1")+" "+id+" "+Messages.get("RESUMELENDING_OPTIONPANE_MSG2"),Messages.get("RESUMELENDING_OPTIONPANE_MSG3"),JOptionPane.INFORMATION_MESSAGE);
      this.setVisible(false);
    }
  }


/*  public void tblResume_mouseClicked(java.awt.event.MouseEvent e){
    int days = 0;
    Extract.getPrefContents((String)tmResume.getValueAt(tblResume.getSelectedRow(),0),db_conn.getConnection(),true);
    tfAuthor.setText(Extract.getAuthor());
    tfTitle.setText(Extract.getTitle());
    dsLendType.setCurrentRow(new String[]{"ID"},new String[]{(String)tmResume.getValueAt(tblResume.getSelectedRow(),6)});
    String s = (String)tmResume.getValueAt(tblResume.getSelectedRow(),2);
    CoolDate cd = new CoolDate(DBConnection.StringToTime(s));
    try{
      days = Integer.parseInt(dsLendType.getRow().getData("PERIOD"));
    } catch(NumberFormatException ex){
      ex.printStackTrace();
    }
    cd = cd.plus(days);
    lEndDate.setText(cd.toString());
  }*/

  void btnExit_actionPerformed(ActionEvent e) {
    lsmResume.removeListSelectionListener(lsmListLisener);
    this.setVisible(false);
  }

  void btnSave_actionPerformed(ActionEvent e) {
    try{
      String query = "update LENDING set RESUME_DATE =\'"+DBConnection.toDatabaseDate(tfRDate.getText())+"\'" ;
      query += (" where CTLG_NO =\'"+(String)tmResume.getValueAt(selected_row,0)+"\'"+" AND SINGLE_ID = \'"+tfSingleID.getText());
      query += ("\' AND LEND_DATE = \'"+DBConnection.toDatabaseDate((String)tmResume.getValueAt(selected_row,2))+"\'");
      System.out.println("<ResumeLending> Query:"+query);
      Statement stmt = db_conn.getConnection().createStatement();
      int i  = stmt.executeUpdate(query);
      stmt.close();
      db_conn.getConnection().commit();
      tmResume.reloadSet();
      JOptionPane.showMessageDialog(null,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG1"),Messages.get("SINGLEMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
    }catch(SQLException ex){
      ex.printStackTrace();
    }
  }

  void btnWarn_actionPerformed(ActionEvent e) {
    Opomena opomena = new Opomena(db_conn);
    opomena.makeOne(tfSingleID.getText());
  }
  
  
  public void setWarning() {
	  	
	  try{
	  		String qry = "select until_date from signing where single_id ='" + tfSingleID.getText() + "' order by until_date desc";  		
	  		Statement stmt = db_conn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        if (rset.next()){
	        	Date until = rset.getDate(1);
	        	if (until != null){
		        	Date now = new java.sql.Date(System.currentTimeMillis());
		        	if(now.after(until)){
		        		stmt.executeUpdate("insert into blocked_cards (user_id, reason_id) values ('" + tfSingleID.getText() + "', 1)");
		        		db_conn.getConnection().commit();
		            }
	        	}
	        }
	        rset.close();
	        stmt.close();
	  	}catch(SQLException e){
	  	}
	  	
	  	
	  	lBlockCard.setEnabled(false);
	  	lBlockCardDesc.setText("");
	  	try{
	  		String qry = "select br.id, br.name, bc.note from blocked_cards bc, block_reasons br where bc.reason_id = br.id and bc.user_id ='" + tfSingleID.getText() + "'";  		
	  		Statement stmt = db_conn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        if (rset.next()){
	        	lBlockCard.setEnabled(true);
	        	lBlockCardDesc.setText(rset.getString(2));
	      		if (rset.getString(3) != null){
	      			lBlockCardDesc.setText(lBlockCardDesc.getText() + ": "+rset.getString(3));
	      		}
	        }
	        while (rset.next()){	
	        	lBlockCardDesc.setText(lBlockCardDesc.getText() + ", " + rset.getString(2));
	        	if (rset.getString(3) != null){
	        		lBlockCardDesc.setText(lBlockCardDesc.getText() + ": "+rset.getString(3));
	      		} 
	        }
	        rset.close();
	        stmt.close();
	        db_conn.getConnection().commit();
	  	}catch(SQLException e){
	  	}  
	  }
  
	  public void setDup(){
		  try{
		  		String qry = "select dup_no, dup_date from duplicate where user_id ='" + tfSingleID.getText() + "' order by dup_no desc";  		
		  		Statement stmt = db_conn.getConnection().createStatement();
		        ResultSet rset = stmt.executeQuery(qry);
		        if (rset.next()){
		        	int dup_no = rset.getInt(1);
		        	Timestamp date = rset.getTimestamp(2);
		        	lSingleDup.setText(Messages.get("SINGLEMEMBER_LSINGLEDUP_TEXT") + " " + dup_no);
//		        	if (date != null){
//		        		lSingleDupDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDUPDATE_TEXT") + " " + DBConnection.fromDatabaseDate(date.toString()));
//		        	} else {
//		        		lSingleDupDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDUPDATE_TEXT"));
//		        	}
		        }else{
		        	lSingleDup.setText("");
		        }
		        rset.close();
		        stmt.close();
		        db_conn.getConnection().commit();
		  	}catch(SQLException e){
		  		e.printStackTrace();
		  	}
	  }

}

