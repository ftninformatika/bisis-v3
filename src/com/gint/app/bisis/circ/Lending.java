
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
import javax.swing.table.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import com.gint.app.bisis.circ.reports.Istorija;
import com.gint.app.bisis.circ.reports.Revers;

public class Lending extends EscapeDialog {
  String currId="";
  boolean closed = true;
  int selected_row = -1;
  DBConnection dbConn;
  DataSet dsUsers, dsSingle, dsLending, dsPlace, dsLendType;
  JDBComboBoxModel cmPlace, cmLendType;
  JDBComboBox cbPlace = new JDBComboBox();
  JDBComboBox cbLendType = new JDBComboBox();
  EditableTableModel tmLending;
  ListSelectionModel lsmLending;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lSingleID = new JLabel();
  JLabel lName = new JLabel();
  JLabel lDate = new JLabel();
  JTable tblLending;// = new JTable();
  JScrollPane sp1;
  JLabel tfSingleID = new JLabel();
//  JLabel tfLDate = new JLabel();
  JTextField tfLDate = new JTextField();
  JLabel lUntil = new JLabel();
  JLabel tfUntil = new JLabel();
  JLabel tfName = new JLabel();
  JButton btnDelete = new JButton();
  JButton btnInsert = new JButton();
  JButton btnSave = new JButton();
  JButton btnExit = new JButton();
//  JButton btnInvNum = new JButton();
  CircDocs circ;
  Returning returning;
  String value = "";
  ActionListener aCircAdd, aCircExit;
  ListSelectionListener lsmListLisener;  // dodat lisener 31.10.00
  JLabel lTitle = new JLabel();
  JLabel lAuthor = new JLabel();
  JLabel tfTitle = new JLabel("1");
  JLabel tfAuthor = new JLabel("1");
  JButton btnOldLend = new JButton();
  JLabel lBlockCard = new JLabel();
  JLabel lBlockCardDesc = new JLabel();
  JButton btnRevers = new JButton();
  JButton btnHistory = new JButton();
  JLabel lSingleDup = new JLabel();
  //JLabel lSingleDupDate = new JLabel();
  Istorija istorija;
  int lended = 0, titleno = 0;

  public Lending(Frame frame, String title, boolean modal,DBConnection db, CircDocs sig, Returning retur, Istorija ist) {
    super(frame, title, modal);
    dbConn=db;
    circ = sig;
    returning =retur;
    istorija = ist;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Lending() {
    this(CircApplet.parent, "", false,null,null,null,null);
  }

  public Lending(String title,DBConnection dbConn, boolean modal, CircDocs sig,Returning retur, Istorija ist) {
    this(CircApplet.parent, title, modal,dbConn,sig,retur,ist);
  }

  void jbInit() throws Exception {
    dsUsers = dbConn.getUsersModel();
    dsSingle = dbConn.getSingleModel();
    dsPlace = dbConn.getPlaces();
    dsLendType = dbConn.getLendType();
    dsLending = new DataSet(dbConn.getConnection(),"LENDING",
                            new String[]{"LEND_DATE", "SINGLE_ID","CTLG_NO","COUNTER"});
    cmLendType = new JDBComboBoxModel(dsLendType,"NAME");
    cbLendType.setModel(cmLendType);
    cmPlace = new JDBComboBoxModel(dsPlace,"NAME");
    cbPlace.setModel(cmPlace);
    tblLending = new JTable();
    lsmLending = tblLending.getSelectionModel();
    lsmLending.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblLending.setSelectionModel(lsmLending);
    sp1= new JScrollPane(tblLending);
    xYLayout1.setWidth(430);
    xYLayout1.setHeight(340);
    lName.setText(Messages.get("LENDING_NAME_TEXT"));
    tfLDate.setForeground(Color.blue);
    tfLDate.setBackground(Color.lightGray);
    tfSingleID.setForeground(Color.blue);
    tfSingleID.setText("1");
    lUntil.setText(Messages.get("LENDING_VALIDUNTIL_DATE"));
    tfUntil.setForeground(Color.blue);
    tfName.setForeground(Color.blue);
    btnDelete.setText(Messages.get("LENDING_BTNDELETE_TEXT"));
    btnInsert.setText(Messages.get("LENDING_BTNINSERT_TEXT"));
//    btnInvNum.setText(Messages.get("LENDING_BTNINVNUM_TEXT"));
    btnInsert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnInsert_actionPerformed(e);
      }
    });
    btnSave.setText(Messages.get("LENDING_BTNSAVE_TEXT"));
    btnExit.setText(Messages.get("LENDING_BTNEXIT_TEXT"));

    aCircAdd = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(circ.isAvailable()){
          value = circ.getValue();
          tmLending.setValueAt(value,tmLending.getRowCount()-1,0);
          tblLending.setRowSelectionInterval(tmLending.getRowCount()-1,tmLending.getRowCount()-1); // 21.11.00
          circ.setVisible(false);
        }
        else{
          JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG1"), Messages.get("LENDING_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
//          circ.setVisible(true);
        }
      }
    };

    aCircExit = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if (tmLending.getRowCount() != 0) {
          tmLending.removeRow(tmLending.getRowCount()-1);
        }
        circ.setVisible(false);
      }
    };

    lsmListLisener = new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmLending_valueChanged(e);
      }
    };

    lTitle.setText(Messages.get("LENDING_LTITLE_TEXT"));
    lAuthor.setText(Messages.get("LENDING_LAUTHOR_TEXT"));
    tfTitle.setForeground(Color.blue);
    tfAuthor.setForeground(Color.blue);
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });

    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });

    btnOldLend.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOldLend_actionPerformed(e);
      }
    });
    
    btnHistory.setText(Messages.get("QUICKLENDING_BTNHISTORY_TEXT"));
    btnHistory.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          btnHistory_actionPerformed(e);
        }
      });

//    btnRevers.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        btnRevers_actionPerformed(e);
//      }
//    });
    
/*    btnInvNum.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          btnInvNum_actionPerformed(e);
        }
    });*/


    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed){
          circ.addAddListener(aCircAdd);
          circ.addExitListener(aCircExit);
          lsmLending.addListSelectionListener(lsmListLisener);

        }
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        circ.removeAddListener();
        circ.removeExitListener();
        lsmLending.removeListSelectionListener(lsmListLisener);
        closed = true;
        setVisible(false);
      }
    });

    panel1.setLayout(xYLayout1);
    btnOldLend.setText(Messages.get("LENDING_BTNOLDLEND_TEXT"));
    lBlockCard.setForeground(Color.red);
    lBlockCard.setText(Messages.get("LENDING_BLOCKCARD_TEXT"));
    lBlockCard.setEnabled(false);
    lBlockCardDesc.setForeground(Color.red);
    lSingleDup.setForeground(Color.blue);
    //lSingleDupDate.setForeground(Color.blue);
    btnRevers.setText(Messages.get("LENDING_BTNREVERS_TEXT"));
    getContentPane().add(panel1);
    lSingleID.setText(Messages.get("LENDING_SINGLEID_TEXT"));
    lDate.setText(Messages.get("LENDING_DATE_TEXT"));
    panel1.add(lSingleID, new XYConstraints(16, 10, -1, -1));
    panel1.add(lDate, new XYConstraints(16, 75, -1, 15));
    panel1.add(tfLDate, new XYConstraints(136, 75, 67, -1));
    panel1.add(tfSingleID, new XYConstraints(136, 10, -1, -1));
    panel1.add(lUntil, new XYConstraints(16, 52, -1, -1));
    panel1.add(tfUntil, new XYConstraints(136, 52, -1, -1));
    panel1.add(lName, new XYConstraints(16, 31, -1, 15));
    panel1.add(tfName, new XYConstraints(136, 31, -1, -1));
    panel1.add(lTitle, new XYConstraints(16, 100, -1, -1));
    panel1.add(tfTitle, new XYConstraints(71, 100, 330, -1));
    panel1.add(sp1, new XYConstraints(16, 143, 388, 115));
    panel1.add(lAuthor, new XYConstraints(16, 119, -1, -1));
    panel1.add(tfAuthor, new XYConstraints(71, 119, 330, -1));
    panel1.add(btnInsert, new XYConstraints(170, 270, 80, -1));
//    panel1.add(btnInvNum, new XYConstraints(225, 270, 80, -1));
    panel1.add(btnExit, new XYConstraints(329, 270, 80, -1));
    panel1.add(btnSave, new XYConstraints(16, 270, 80, -1));
    panel1.add(btnOldLend, new XYConstraints(16, 308, 131, 24));
    panel1.add(btnHistory, new XYConstraints(329, 308, 80, -1));
    panel1.add(lBlockCard, new XYConstraints(260, 50, 111, -1));
    panel1.add(lBlockCardDesc, new XYConstraints(260, 75, 150, -1));
    panel1.add(lSingleDup, new XYConstraints(300, 10, -1, -1));
    //panel1.add(lSingleDupDate, new XYConstraints(250, 80, -1, -1));
//    panel1.add(btnRevers, new XYConstraints(16, 307, 80, -1));

    btnDelete.setEnabled(false); //21.11.00
    btnSave.setEnabled(false); //21.11.00
  }

  public void lsmLending_valueChanged(ListSelectionEvent e){
     if(!lsmLending.getValueIsAdjusting()){
       int row = tblLending.getSelectedRow();
       if(row != selected_row) selected_row = row;
System.out.println("<Lending> Selected row:"+selected_row);
	   if (selected_row != -1){
	       Extract.getPrefContents((String)tmLending.getValueAt(selected_row,0),dbConn.getConnection(), true);
	       tfAuthor.setText(Extract.getAuthor());
	       tfTitle.setText(Extract.getTitle());
	       String s = (String)tmLending.getValueAt(selected_row,1);
	       int status = (int)Double.parseDouble(s);
	       if(status == 0)
	         btnSave.setEnabled(true);
	       else
	         btnSave.setEnabled(false);
	       btnDelete.setEnabled(true); //21.11.00
	       btnRevers.setEnabled(true);
	   }
     }
  }

  public void setVisible(boolean vis){
    closed = true;
    selected_row=-1;
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
    closed = true;
    btnDelete.setEnabled(false); //21.11.00
    btnSave.setEnabled(false); //21.11.00
    btnRevers.setEnabled(false);
    currId=id;
    setPosition(id);
    super.setVisible(vis);
  }

  private void setPosition(String id){
//    dmLending = new JDBTableModel(dsLending,"select * from LENDING where SINGLE_ID ="+id);
    tfAuthor.setText("");
    tfTitle.setText("");
    tmLending = new EditableTableModel();
    tmLending.addColumn(Messages.get("LENDING_TMLENDING_COL1"));
    tmLending.addColumn(Messages.get("LENDING_TMLENDING_COL2"));
    tmLending.addColumn(Messages.get("LENDING_TMLENDING_COL3"));
    tmLending.addColumn(Messages.get("LENDING_TMLENDING_COL4"));
    tmLending.addColumn(Messages.get("LENDING_TMLENDING_COL5"));

    tblLending.setModel(tmLending);
    TableColumnModel tcm = tblLending.getColumnModel();
    int i = 0;
    int j = tcm.getColumnCount();
    while(i < j){
      TableColumn tc = (TableColumn)tcm.getColumn(i);
/*  ako ovaj if ubacimo, onda aplikacija puca kad se startuje iz MS-DOS propta. Iz JBuilder-a radi
      if(tc.getHeaderValue().toString().equals("Saved"))
          tcm.removeColumn(tc);
      else */
       if(tc.getHeaderValue().toString().equals(Messages.get("LENDING_TCHDR_STRCOMP287"))){
          tc.setCellEditor(new DefaultCellEditor(cbPlace));
          i++;
       }
      else if(tc.getHeaderValue().toString().equals(Messages.get("LENDING_TCHDR_STRCOMP291"))){
          tc.setCellEditor(new DefaultCellEditor(cbLendType));
          i++;
      }
      else i++;
      j = tcm.getColumnCount();
    }
    tblLending.setColumnModel(tcm);
    tblLending.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblLending.sizeColumnsToFit(1);

    dsSingle.setCurrentRow(new String[]{"USER_ID"}, new String[]{id});
    tfSingleID.setText(dsSingle.getRow().getData("USER_ID"));
    tfName.setText(dsSingle.getRow().getData("LAST_NAME")+", "+dsSingle.getRow().getData("FIRST_NAME"));
    tfLDate.setText(DBConnection.getTodaysDate());
    
    try{
    String qry = "select until_date from signing where single_id ='" + id + "' order by until_date desc";
    Statement stmt = dbConn.getConnection().createStatement();
    ResultSet rset = stmt.executeQuery(qry);
    if (rset.next()){
    	tfUntil.setText(DBConnection.formatDate(rset.getTimestamp(1)).toString());
    }else {
    	tfUntil.setText(Messages.get("SINGLEMEMBER_TFSINGLEUNTIL_TEXT2"));
    }
    rset.close();
    dbConn.getConnection().commit();
    stmt.close();
    }catch(SQLException ex){
        ex.printStackTrace();
    }


/*    if (dsSingle.getRow().getData("NOTE").equals("")){
      lBlockCard.setEnabled(false);
    }
    else{
      lBlockCard.setEnabled(true);
    }*/
    setDup();
    setWarning();
    lended = setLended();
    titleno = Extract.getTitleNo(dbConn.getConnection(),tfSingleID.getText(),"2");

  }

  void btnInsert_actionPerformed(ActionEvent e) {
    int last = tmLending.getRowCount()-1;
    String s = "f";
    if(last >=0) s = (String)tmLending.getValueAt(last,0);
    boolean b =s.equals("");
    boolean dalje = true;
    if (b){
    	dalje = lended + last < titleno;
    }else {
    	dalje = lended + last + 1 < titleno;
    }
    if (dalje){
    	if((last < 0)||(!b)){
	      tmLending.addRow(new Object[]{"","0"});
	    }
	    circ.displaySignNo();
    }else{
    	JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG10") + titleno,Messages.get("LENDING_OPTIONPANE_MSG8"),JOptionPane.INFORMATION_MESSAGE);
    }
    
  }
  
/*  void btnInvNum_actionPerformed(ActionEvent e) {
    int last = tmLending.getRowCount()-1;
    String s = "f";
    if(last >=0) s = (String)tmLending.getValueAt(last,0);
    boolean b =s.equals("");
    if((last < 0)||(!b)){
      tmLending.addRow(new Object[]{"","0"});
    }
  }*/

  void btnDelete_actionPerformed(ActionEvent e) {
System.out.println("<Lending> btnDelete selected_row="+selected_row);
    if (selected_row != -1) {
      tfAuthor.setText("");
      tfTitle.setText("");
      String s =  (String)tmLending.getValueAt(selected_row,1);
      int status = (int)Double.parseDouble(s);
      if(status == 0){
        tmLending.removeRow(selected_row);
      }
      else{
        try{
          String datum=DBConnection.toDatabaseDate(tfLDate.getText().trim());
          String query = "delete from LENDING where CTLG_NO =\'"+(String)tmLending.getValueAt(selected_row,0)+"\'";
          query += " AND SINGLE_ID = "+tfSingleID.getText();
          query += (" AND LEND_DATE = \'"+datum+"\'");

          Statement stmt = dbConn.getConnection().createStatement();
          int i  = stmt.executeUpdate(query);
          String ser = (String)tmLending.getValueAt(selected_row,2);
//          if (ser.equals("") || ser.equals(null) || ser.equals("null"))
          if (!jeSerijska((String)tmLending.getValueAt(selected_row,0)))
             i = stmt.executeUpdate("update circ_docs set status = 0 where ctlg_no =\'"+tmLending.getValueAt(selected_row,0)+"\'");
          stmt.close();
          dbConn.getConnection().commit();
          tmLending.removeRow(selected_row);
        }catch(SQLException ex){
          ex.printStackTrace();
        }
      }
     // if dodat 21.11.00
      if (tblLending.getRowCount() != 0) {
         tblLending.setRowSelectionInterval(0,0);
         selected_row=0;
      }
      else
         btnDelete.setEnabled(false);
    }
  }

  void btnSave_actionPerformed(ActionEvent e) {
    int count = tmLending.getRowCount();
    int hasError=0;
    String counter;
    
    if(count > 0){
      if (DBConnection.isValidDate(tfLDate.getText())) {
        for(int i = 0; i < count; i++) {
          if(tmLending.getValueAt(i,1).equals("0") &&(!(((String)tmLending.getValueAt(i,0)).equals(""))) ){
          	if(proveriIspravnostCtlgNo(i)){
// sledeci red ne radi iz MS-dos prompta, a iz JBuilder-a radi, ako je izbacena kolona Saved iz tabele !!!
//            if (proveriJedinstvenostKljuca(i)) {
          		
          		try{
          	      Statement stmt = dbConn.getConnection().createStatement();
          	      ResultSet rSet = stmt.executeQuery("select lendidseq.nextval from dual");
          	      rSet.next();
          	      int ret = rSet.getInt(1);
          	      rSet.close();
          	      dbConn.getConnection().commit();
          	      stmt.close();
          	      counter = (new Integer(ret)).toString();
          	    }
          	    catch (SQLException ex) {
          	      System.err.println("Error in creating statement or fetching data.");
          	      System.err.println(ex);
          	      counter = "-1";
          	    }
              dsLending.emptyRow();
              dsLending.getRow().setData(dbConn.getOfficial(),"BIB_ID");
              dsLending.getRow().setData(dsPlace.getRow().getData("ID"),"LOCATION");
              dsLending.getRow().setData(dsLendType.getRow().getData("ID"),"LEND_TYPE");
              dsLending.getRow().setData((String)tmLending.getValueAt(i,0),"CTLG_NO");
              dsLending.getRow().setData(tfSingleID.getText(),"SINGLE_ID");
              dsLending.getRow().setData(tfLDate.getText(),"LEND_DATE");
              dsLending.getRow().setData((String)tmLending.getValueAt(i,2),"DESCRIPTION");
              dsLending.getRow().setData(counter,"COUNTER");

              dsLending.saveRow();
              if (dsLending.hasError()) {
                tmLending.setValueAt("1",i,1);
                try{
//            dsLending.saveRow();

                  Statement stmt = dbConn.getConnection().createStatement();
                  int k = stmt.executeUpdate("update circ_docs set status = 1 where ctlg_no =\'"+tmLending.getValueAt(i,0)+"\'");
                  stmt.close();
                  dbConn.getConnection().commit();

                }catch(SQLException ex){
                  if(ex.getMessage().indexOf("NULL")!= -1)
                    JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG3"), Messages.get("LENDING_OPTIONPANE_MSG4"),JOptionPane.ERROR_MESSAGE);
                  else
                    ex.printStackTrace();
                }
                tmLending.setRowEditable(i, false);
                btnSave.setEnabled(false);
              }
              else
                hasError++;
           /* }
            else {
              hasError++;
              JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG5")+" "+(String)tmLending.getValueAt(i,0),Messages.get("LENDING_OPTIONPANE_MSG6"),JOptionPane.ERROR_MESSAGE);
            }*/
          	}
            else{
            	hasError++;
            }
          }
        }

        if (hasError==0)
          JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG7"),Messages.get("LENDING_OPTIONPANE_MSG8"),JOptionPane.INFORMATION_MESSAGE);
        else
          JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG9")+" "+hasError+Messages.get("LENDING_OPTIONPANE_MSG10"),Messages.get("LENDING_OPTIONPANE_MSG11"),JOptionPane.ERROR_MESSAGE);
      }
      else
        JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG12"),Messages.get("LENDING_OPTIONPANE_MSG13"),JOptionPane.ERROR_MESSAGE);
    }
    else
      JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONSPANE_MSG14"), Messages.get("LENDING_OPTIONSPANE_MSG15"),JOptionPane.ERROR_MESSAGE);
  }

  void btnExit_actionPerformed(ActionEvent e) {
    circ.removeAddListener();
    circ.removeExitListener();
    lsmLending.removeListSelectionListener(lsmListLisener);
    setVisible(false);
  }
  
  void btnOldLend_actionPerformed(ActionEvent e) {
    returning.setVisible(true,currId);
  }
  
  void btnHistory_actionPerformed(ActionEvent e) {
    istorija.setVisible(true,currId);
  }
  
  public void reloadCMB() { // dodato 28.02.00.
    cmLendType = new JDBComboBoxModel(dsLendType,"NAME");
    cbLendType.setModel(cmLendType);
    cmPlace = new JDBComboBoxModel(dsPlace,"NAME");
    cbPlace.setModel(cmPlace);
  }

  private boolean proveriJedinstvenostKljuca(int vrsta) {
    boolean vrati=false;
    String datum=DBConnection.toDatabaseDate(tfLDate.getText());

    String query = "select * from LENDING where CTLG_NO =\'"+(String)tmLending.getValueAt(vrsta,0)+"\'";
           query += " AND SINGLE_ID = '"+tfSingleID.getText();
           query += ("' AND LEND_DATE = \'"+datum+"\'");

    try {
      Statement stmt = dbConn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(query);
      if (rset.next())
        vrati = false;
      else
        vrati = true;
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();
    }
    catch (SQLException e){
      System.out.println("Greska kod proveriJedinstvenostKljuca()");
      e.printStackTrace();
    }
    return vrati;
  }
  
  private boolean proveriIspravnostCtlgNo(int vrsta) {
    boolean vrati=false;
    String query = "select status from CIRC_DOCS where CTLG_NO =\'"+(String)tmLending.getValueAt(vrsta,0)+"\'";
    int status = 0;       
    try {
      Statement stmt = dbConn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(query);
      if (rset.next()){
         status = rset.getInt(1);
    
         if (status == 0){
         	vrati = true;
         }else{
         	JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONSPANE_MSG18"), Messages.get("LENDING_OPTIONSPANE_MSG19"),JOptionPane.ERROR_MESSAGE);
         	vrati =false;
         }
      }else{
      	JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONSPANE_MSG20"), Messages.get("LENDING_OPTIONSPANE_MSG21"),JOptionPane.ERROR_MESSAGE);
      	vrati = false;
      }
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();
    }
    catch (SQLException e){
      System.out.println("Greska kod proveriIspravnostCtlgNo()");
      e.printStackTrace();
    }
    return vrati;
  }

//  void btnRevers_actionPerformed(ActionEvent e) {
//  	int count = tmLending.getRowCount();
//  	if(count > 0){
//  		String[] invs = new String[count];
//  		for(int i = 0; i < count; i++) {
//  			if(proveriIspravnostCtlgNo(i) && !((String)tmLending.getValueAt(i,0)).equals("") ){
//  				invs[i] = (String)tmLending.getValueAt(i,0);
//  			}
//  		}
//      Revers revers = new Revers(dbConn);
//      revers.makeOne(tfSingleID.getText(),invs,tfLDate.getText());
//    }
//    else
//      JOptionPane.showMessageDialog(circ,Messages.get("LENDING_OPTIONPANE_MSG16"),Messages.get("LENDING_OPTIONPANE_MSG17"),JOptionPane.INFORMATION_MESSAGE);
//  }

  public boolean jeSerijska(String ctlg_no) {
    boolean serijska=false;
    String query = "select type from CIRC_DOCS where CTLG_NO =\'"+ctlg_no+"\'";

    try {
      Statement stmt = dbConn.getConnection().createStatement();
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
      dbConn.getConnection().commit();
    }
    catch (SQLException e){
      System.out.println("Greska kod jeSerijska()");
      e.printStackTrace();
    }
    return serijska;
  }
  
  public void setWarning() {
  	
	  try{
	  		String qry = "select until_date from signing where single_id ='" + tfSingleID.getText() + "' order by until_date desc";  		
	  		Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        if (rset.next()){
	        	Date until = rset.getDate(1);
	        	if (until != null){
		        	Date now = new java.sql.Date(System.currentTimeMillis());
		        	if(now.after(until)){
		        		stmt.executeUpdate("insert into blocked_cards (user_id, reason_id) values ('" + tfSingleID.getText() + "', 1)");
		            }
	        	}
	        }
	        rset.close();
	        dbConn.getConnection().commit();
    		stmt.close();
	  	}catch(SQLException e){
	  	}
	  	
	  	
	  	lBlockCard.setEnabled(false);
	  	lBlockCardDesc.setText("");
	  	try{
	  		String qry = "select br.id, br.name, bc.note from blocked_cards bc, block_reasons br where bc.reason_id = br.id and bc.user_id ='" + tfSingleID.getText() + "'";  		
	  		Statement stmt = dbConn.getConnection().createStatement();
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
	        dbConn.getConnection().commit();
	        
	  	}catch(SQLException e){
	  	}  
  }
  
  public int setLended(){
	  try{
	  		String qry = "select count(*) from lending where single_id ='" + tfSingleID.getText() + "' and return_date is null";
	  		Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        int res = 0;
	        if (rset.next()){
	        	res = rset.getInt(1);
	        }
	        rset.close();
	        stmt.close();
	        dbConn.getConnection().commit();
	        return res;
	  	}catch(SQLException e){
	  		return 0;
	  	}
  }
  
  public void setDup(){
	  try{
	  		String qry = "select dup_no, dup_date from duplicate where user_id ='" + tfSingleID.getText() + "' order by dup_no desc";  		
	  		Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        if (rset.next()){
	        	int dup_no = rset.getInt(1);
	        	Timestamp date = rset.getTimestamp(2);
	        	lSingleDup.setText(Messages.get("SINGLEMEMBER_LSINGLEDUP_TEXT") + " " + dup_no);
//	        	if (date != null){
//	        		lSingleDupDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDUPDATE_TEXT") + " " + DBConnection.fromDatabaseDate(date.toString()));
//	        	} else {
//	        		lSingleDupDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDUPDATE_TEXT"));
//	        	}
	        }else{
	        	lSingleDup.setText("");
	        }
	        rset.close();
	        stmt.close();
	        dbConn.getConnection().commit();
	  	}catch(SQLException e){
	  		e.printStackTrace();
	  	}
  }
}

