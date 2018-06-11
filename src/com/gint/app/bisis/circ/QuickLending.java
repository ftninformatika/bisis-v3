/*
 * Created on Mar 18, 2005
 *
 */
package com.gint.app.bisis.circ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.app.bisis.circ.reports.Istorija;
import com.gint.app.bisis.circ.reports.Revers;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;

/**
 * @author Administrator
 *
 */
public class QuickLending extends EscapeDialog{

	boolean closed = true;
	  JPanel panel1 = new JPanel();
	  BorderLayout borderLayout1 = new BorderLayout();
	  JPanel jPanel1 = new JPanel();
	  JPanel jPanel2 = new JPanel();
	  JButton btnOK = new JButton();
	  JButton btnCancel = new JButton();
	  JLabel jLabel1 = new JLabel();
	  JTextField jTextField1 = new JTextField();
	  FlowLayout flowLayout1 = new FlowLayout();
	  FlowLayout flowLayout2 = new FlowLayout();
	  JButton btnInsert = new JButton();
	  String id = "";
	  String datum = null;
	  String bibid = "";
	  DBConnection dbConn;
	  JLabel lSingleID = new JLabel();
	  JLabel lName = new JLabel();
	  JLabel lDate = new JLabel();
	  JLabel tfSingleID = new JLabel();
	  JLabel tfUntil = new JLabel();
	  JLabel tfName = new JLabel();
	  JLabel lTitle = new JLabel();
	  JLabel lAuthor = new JLabel();
	  JLabel tfTitle = new JLabel("");
	  JLabel tfAuthor = new JLabel("");
	  JLabel lBlockCard = new JLabel();
	  JLabel lBlockCardDesc = new JLabel();
	  JTextField tfLDate = new JTextField();
	  JLabel lUntil = new JLabel();
	  XYLayout xYLayout1 = new XYLayout();
	  Vector lista = new Vector();
	  JScrollPane jsp = new JScrollPane();
	  JList jl = new JList(lista);
	  JButton btnOldLend = new JButton();
	  Returning returning;
	  JButton btnHistory = new JButton();
	  Istorija istorija;
	  SingleMember smember;
	  ResumeLending resume;
	  JButton btnRevers = new JButton();
	  JButton btnData = new JButton();
	  JButton btnResume = new JButton();
	  String location;
	  int neobradjene = 0;
	  JLabel lSingleNapomena = new JLabel();
	  JLabel tfSingleNote = new JLabel();
	  JLabel lSingleDup = new JLabel();
	  //JLabel lSingleDupDate = new JLabel();
	  int lended = 0, titleno = 0;
	  

	  public QuickLending(JFrame frame, String title, boolean modal,DBConnection db, Returning retur, Istorija ist, SingleMember sm, ResumeLending res) {
	    super(frame, title, modal);
	    dbConn = db;
	    bibid = dbConn.getOfficial();
	    returning =retur;
	    istorija = ist;
	    smember = sm;
	    resume = res;
	    try  {
	      jbInit();
	      pack();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public QuickLending() {
	    this(CircApplet.parent, "", false,null,null,null,null,null);
	  }

	  public QuickLending(String title, boolean modal,DBConnection db,Returning retur,Istorija ist,SingleMember sm, ResumeLending res){
	    this(CircApplet.parent,title,modal,db,retur,ist,sm,res);
	  }

	  void jbInit() throws Exception {	  
	  	
	  	INIFile iniFile = new INIFile(
	            FileUtils.getClassDir(NewMember.class) + "/config.ini"); 
	  	location = iniFile.getString("circ", "homeLocation");
	  	neobradjene = Integer.parseInt(iniFile.getString("circ", "neobradjene"));
	  	
	    lSingleNapomena.setText(Messages.get("SINGLEMEMBER_LSINGLENAPOMENA_TEXT"));
	    panel1.setMaximumSize(new Dimension(400, 370));
	    panel1.setPreferredSize(new Dimension(400, 370));
	    panel1.setMinimumSize(new Dimension(400, 370));
	    panel1.setSize(new Dimension(400, 370));
	    jPanel1.setLayout(flowLayout2);
	    jPanel2.setLayout(xYLayout1);
	    btnOK.setPreferredSize(new Dimension(80, 25));
	    btnOK.setText(Messages.get("QUICKLENDING_BTNOK_TEXT"));
	    btnOK.addActionListener(new java.awt.event.ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        btnOK_actionPerformed(e);
		      }
		    });
	    btnCancel.setPreferredSize(new Dimension(85, 25));
	    btnCancel.setText(Messages.get("QUICKLENDING_BTNCANCEL_TEXT"));
	    btnCancel.addActionListener(new java.awt.event.ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        btnCancel_actionPerformed(e);
		      }
		    });
	    btnOldLend.setText(Messages.get("LENDING_BTNOLDLEND_TEXT"));
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
	    btnData.setText(Messages.get("CIRCAPPLET_JMENUITEM11_TEXT"));
	    btnData.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          btnData_actionPerformed(e);
	        }
	      });
	    btnResume.setText(Messages.get("QUICKLENDING_BTNRESUME_TEXT"));
	    btnResume.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          btnResume_actionPerformed(e);
	        }
	      });
	    
	    flowLayout2.setHgap(20);
	    xYLayout1.setWidth(400);
	    xYLayout1.setHeight(320);
	    btnInsert.setPreferredSize(new Dimension(80, 25));
	    btnInsert.setText(Messages.get("QUICKLENDING_BTNINSERT_TEXT"));
	    btnInsert.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        btnInsert_actionPerformed(e);
	      }
	    });
	    btnRevers.setText(Messages.get("LENDING_BTNREVERS_TEXT"));
//	    btnRevers.addActionListener(new java.awt.event.ActionListener() {
//	        public void actionPerformed(ActionEvent e) {
//	          btnRevers_actionPerformed(e);
//	        }
//	    });
	    lBlockCard.setForeground(Color.red);
	    lBlockCard.setText(Messages.get("LENDING_BLOCKCARD_TEXT"));
	    lBlockCard.setEnabled(false);
	    jLabel1.setText(Messages.get("QUICKLENDING_LABEL1_TEXT"));
	    jTextField1.setPreferredSize(new Dimension(80, 20));
	    jTextField1.setAlignmentY((float) 20.0);
	    jTextField1.setMinimumSize(new Dimension(80, 20));
	    panel1.setLayout(borderLayout1);
	    getContentPane().add(panel1);
	    lName.setText(Messages.get("LENDING_NAME_TEXT"));
	    tfLDate.setForeground(Color.blue);
	    tfLDate.setBackground(Color.lightGray);
	    tfSingleID.setForeground(Color.blue);
	    tfSingleID.setText("1");
	    tfSingleNote.setForeground(Color.blue);
	    lSingleDup.setForeground(Color.blue);
	    //lSingleDupDate.setForeground(Color.blue);
	    lUntil.setText(Messages.get("LENDING_VALIDUNTIL_DATE"));
	    tfUntil.setForeground(Color.blue);
	    tfName.setForeground(Color.blue);
	    lTitle.setText(Messages.get("LENDING_LTITLE_TEXT"));
	    lAuthor.setText(Messages.get("LENDING_LAUTHOR_TEXT"));
	    tfTitle.setForeground(Color.blue);
	    tfAuthor.setForeground(Color.blue);
	    lBlockCardDesc.setForeground(Color.red);
	    lSingleID.setText(Messages.get("LENDING_SINGLEID_TEXT"));
	    lDate.setText(Messages.get("LENDING_DATE_TEXT"));
	    jsp.getViewport().setView(jl);
	    jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    jl.addListSelectionListener(new ListSelectionListener(){
	    	public void valueChanged(ListSelectionEvent e){
	    		String ctlg = (String)jl.getSelectedValue();
	    		if (ctlg != null){
	    	    Extract.getPrefContents(ctlg,dbConn.getConnection(), true);
	    	    tfAuthor.setText(Extract.getAuthor());
	    	    tfTitle.setText(Extract.getTitle());
	    		}
	        }
	    });

	    jPanel2.add(jLabel1, new XYConstraints(30, 180, -1, -1));
	    jPanel2.add(jTextField1, new XYConstraints(130, 180, -1, -1));
//	    jPanel1.add(btnRevers, null);
	    jPanel1.add(btnCancel, null);
	    jPanel1.add(btnInsert, null);
	    jPanel1.add(btnOK, null);
	    
	    jPanel2.add(lSingleID, new XYConstraints(16, 10, -1, -1));
	    jPanel2.add(lDate, new XYConstraints(16, 75, -1, 15));
	    jPanel2.add(tfLDate, new XYConstraints(136, 75, 67, -1));
	    jPanel2.add(tfSingleID, new XYConstraints(136, 10, -1, -1));
	    jPanel2.add(lUntil, new XYConstraints(16, 52, -1, -1));
	    jPanel2.add(tfUntil, new XYConstraints(136, 52, -1, -1));
	    jPanel2.add(lName, new XYConstraints(16, 31, -1, 15));
	    jPanel2.add(tfName, new XYConstraints(136, 31, -1, -1));
	    jPanel2.add(lTitle, new XYConstraints(16, 130, -1, -1));
	    jPanel2.add(tfTitle, new XYConstraints(71, 130, 330, -1));
	    jPanel2.add(lAuthor, new XYConstraints(16, 150, -1, -1));
	    jPanel2.add(tfAuthor, new XYConstraints(71, 150, 330, -1));
	    jPanel2.add(jsp, new XYConstraints(30, 220, 150, 100));
	    jPanel2.add(btnOldLend, new XYConstraints(240, 200, 145, 24));
	    jPanel2.add(btnResume, new XYConstraints(240, 235, 145, 24));
	    jPanel2.add(btnHistory, new XYConstraints(240, 270, 145, 24));
	    jPanel2.add(btnData, new XYConstraints(240, 305, 145, 24));
	    jPanel2.add(lBlockCard, new XYConstraints(250, 50, 111, -1));
	    jPanel2.add(lBlockCardDesc, new XYConstraints(250, 75, 150, -1));
	    jPanel2.add(lSingleNapomena, new XYConstraints(16, 100, -1, -1));
	    jPanel2.add(tfSingleNote, new XYConstraints(136, 100, 330, -1));
	    jPanel2.add(lSingleDup, new XYConstraints(280, 10, -1, -1));
	    //jPanel2.add(lSingleDupDate, new XYConstraints(235, 75, -1, -1));
	    
	    panel1.add(jPanel2, BorderLayout.CENTER);
	    panel1.add(jPanel1, BorderLayout.SOUTH);

	    jTextField1.addKeyListener(new KeyAdapter(){
	      public void keyPressed(KeyEvent e){
	        if(e.getKeyCode()==KeyEvent.VK_ENTER){
	          btnOK.doClick();
	        }else if (e.getKeyCode()==KeyEvent.VK_ADD){
	          btnInsert.doClick();
	        }
	      }
	    });
	    
	    jTextField1.addKeyListener(new KeyAdapter(){
		      public void keyReleased(KeyEvent e){
		        if(e.getKeyCode()==KeyEvent.VK_ADD)
		        	jTextField1.setText("");
		        }
		      }
		    );
	    


	    this.addWindowListener(new WindowAdapter(){
	      public void windowActivated(WindowEvent e){
	        jTextField1.requestFocus(); //Tanja
	        closed = false;
	      }

	      public void windowClosing(WindowEvent e){
	        closed = true;
	        clear();
	        setVisible(false);
	      }
	    });
	  }
	  
	

	  public void clear(){
	    jTextField1.setText("");
	    id = "";
	    lista.clear();
	    jl.setListData(lista);
	    tfName.setText("");
	    tfUntil.setText("");
	    tfTitle.setText("");
	    tfAuthor.setText("");
	    tfLDate.setText("");
	    tfSingleNote.setText("");
	    
	    
	  }

	  void btnOK_actionPerformed(ActionEvent e) {
	   		boolean saved = saveRow();
	   		if (saved){
	   		this.setVisible(false);
	   		closed=true;
	   		clear();
	   		}
	  }
	  
	  void btnCancel_actionPerformed(ActionEvent e) {
		  	this.setVisible(false);
		  	closed=true;
		  	clear();
		   
	  }

	  void btnInsert_actionPerformed(ActionEvent e) {
	  		boolean saved = saveRow();
	  		if (saved){
	  			jTextField1.setText("");
	  		}
	  }
	  
	  void btnData_actionPerformed(ActionEvent e) {
	  	smember.setVisible(true,id);
	  }
	  
//	  void btnRevers_actionPerformed(ActionEvent e) {
//	  	int count = lista.size();
//	  	if(count > 0){
//	  		String[] invs = new String[count];
//	  		for(int i = 0; i < count; i++) {
//	  			invs[i] = (String)lista.get(i);
//	  		}
//	      Revers revers = new Revers(dbConn);
//	      revers.makeOne(tfSingleID.getText(),invs,tfLDate.getText());
//	    }
//	    else
//	      JOptionPane.showMessageDialog(null,Messages.get("LENDING_OPTIONPANE_MSG16"),Messages.get("LENDING_OPTIONPANE_MSG17"),JOptionPane.INFORMATION_MESSAGE);
//	  }
	  
	  void btnOldLend_actionPerformed(ActionEvent e) {
	    returning.setVisible(true,id);
	  }
	  
	  void btnHistory_actionPerformed(ActionEvent e) {
	    istorija.setVisible(true,id);
	  }
	  
	  void btnResume_actionPerformed(ActionEvent e) {
	    resume.setVisible(true,id);
	  }
	  
	  private boolean saveRow(){
	  	String counter;
	  	String ctlg_no =DBConnection.toDBInventory(jTextField1.getText());
	  	datum = DBConnection.toDatabaseDate(tfLDate.getText());
	  	boolean saved=false;
	  	
	  	if (proveriTitleNo()){
	  	
		  	if (proveriIspravnostCtlgNo(ctlg_no)){
		  	
		  	try{
	    	      Statement stmt = dbConn.getConnection().createStatement();
	    	      ResultSet rSet = stmt.executeQuery("select lendidseq.nextval from dual");
	    	      rSet.next();
	    	      int ret = rSet.getInt(1);
	    	      rSet.close();
	    	      stmt.close();
	    	      dbConn.getConnection().commit();
	    	      counter = (new Integer(ret)).toString();
	    	    }
	    	    catch (SQLException ex) {
	    	      System.err.println("Error in creating statement or fetching data.");
	    	      System.err.println(ex);
	    	      counter = "-1";
	    	    }
	    	    if (!counter.equals("-1")){
	    	    	try{
	    	    		String qry = "insert into lending (ctlg_no, single_id, lend_date, bib_id,lend_type, location, counter) " +
	    	    				"values ('"+ctlg_no+"', '"+id+"', '"+datum+"', '"+bibid+"', "+2+", "+location+", "+counter+")";
	    	    		Statement stmt = dbConn.getConnection().createStatement();
	    	    	    stmt.executeUpdate(qry);
	    	    	    stmt.executeUpdate("update circ_docs set status = 1 where ctlg_no =\'"+ctlg_no+"\'");
	    	    	    stmt.close();
	    	    	    dbConn.getConnection().commit();
	    	    	    saved = true;
	    	    	    lista.add(ctlg_no);
	    	    	    jl.setListData(lista);
	    	    	    jl.setSelectedIndex(lista.size()-1);
	    	    	}catch (SQLException ex) {
	    	    		 System.err.println(ex);
	    	    	}
	    	    }else{
	    	    	JOptionPane.showMessageDialog(null,Messages.get("LENDING_OPTIONSPANE_MSG19"), Messages.get("LENDING_OPTIONSPANE_MSG19"),JOptionPane.ERROR_MESSAGE);
	    	    }
		  	}
	  	}
	  	return saved;
    	    
	  }
	  
	  private boolean proveriTitleNo(){
		  boolean ok = setLended() < titleno;
		  if (!ok){
			  JOptionPane.showMessageDialog(null,Messages.get("LENDING_OPTIONPANE_MSG10") + titleno,Messages.get("LENDING_OPTIONPANE_MSG8"),JOptionPane.INFORMATION_MESSAGE);
		  }
		  return ok;
	  }
	  
	  private boolean proveriIspravnostCtlgNo(String ctlg_no) {
	    boolean vrati=false;
	    String query = "select status from CIRC_DOCS where CTLG_NO =\'"+ctlg_no+"\'";
	    int status = 0;       
	    try {
	      Statement stmt = dbConn.getConnection().createStatement();
	      ResultSet rset = stmt.executeQuery(query);
	      if (rset.next()){
	         status = rset.getInt(1);
	         rset.close();
	         if (status == 0){
	         	vrati = true;
	         }else{
	         	String[] options = {Messages.get("LENDING_OPTIONSPANE_MSG22"),Messages.get("LENDING_OPTIONSPANE_MSG23")}; 
	         	int op = JOptionPane.showOptionDialog(this,Messages.get("LENDING_OPTIONSPANE_MSG18"), Messages.get("LENDING_OPTIONSPANE_MSG19"),JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
	         	if (op == JOptionPane.YES_OPTION){
	         	    stmt.executeUpdate("update lending set return_date ='"+datum+"' where ctlg_no ='"+ctlg_no+"' and return_date is null");
	         	    stmt.executeUpdate("update circ_docs set status = 0 where ctlg_no =\'"+ctlg_no+"\'");
	         	    vrati = true;
	         	}else{
	         		vrati = false;
	         	}
	         }
	      }else{
	      	if(neobradjene == 1){
	      		String[] options = {Messages.get("LENDING_OPTIONSPANE_MSG22"),Messages.get("LENDING_OPTIONSPANE_MSG23")}; 
	         	int op = JOptionPane.showOptionDialog(this,Messages.get("LENDING_OPTIONSPANE_MSG24"), Messages.get("LENDING_OPTIONSPANE_MSG25"),JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
	         	if (op == JOptionPane.YES_OPTION){
	         		rset = stmt.executeQuery("select * from lending where ctlg_no = '"+ctlg_no+"' and return_date is null");
	         		if (rset.next()){
	    	         	op = JOptionPane.showOptionDialog(this,Messages.get("LENDING_OPTIONSPANE_MSG18"), Messages.get("LENDING_OPTIONSPANE_MSG19"),JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
	    	         	if (op == JOptionPane.YES_OPTION){
	    	         	    stmt.executeUpdate("update lending set return_date ='"+datum+"' where ctlg_no ='"+ctlg_no+"' and return_date is null");
	    	         	    vrati = true;
	    	         	}else{
	    	         		vrati = false;
	    	         	}
	         		}else{
	         			 vrati = true;
	         		}
	         		rset.close();
	         	}else{
	         		vrati = false;
	         	}
	      	}else{
		      	JOptionPane.showMessageDialog(null,Messages.get("LENDING_OPTIONSPANE_MSG20"), Messages.get("LENDING_OPTIONSPANE_MSG21"),JOptionPane.ERROR_MESSAGE);
		      	vrati = false;
	      	}
	      }
	      dbConn.getConnection().commit();
	      stmt.close();
	    }
	    catch (SQLException e){
	      System.out.println("Greska kod proveriIspravnostCtlgNo()");
	      e.printStackTrace();
	    }
	    return vrati;
	  }
	
	  public void setVisible(boolean vis, String id){
	  	this.id = id;
	    closed = false;
	    tfLDate.setText(DBConnection.getTodaysDate());
	    tfSingleID.setText(id);
	    try {
	    	String qry = "select last_name, first_name, note from single where user_id ='" + id + "'";
	    	Statement stmt = dbConn.getConnection().createStatement();
		    ResultSet rset = stmt.executeQuery(qry);
		    if (rset.next()){
		    	tfName.setText(rset.getString(1) + ", " + rset.getString(2));
		    	if (rset.getString(3) != null){
		    		tfSingleNote.setText(rset.getString(3));
		    	}
		    }
		    rset.close();
		    qry = "select until_date from signing where single_id ='" + id + "' order by until_date desc";
		    rset = stmt.executeQuery(qry);
		    if (rset.next()){
		    	tfUntil.setText(DBConnection.formatDate(rset.getTimestamp(1)).toString());
		    }else {
		    	tfUntil.setText(Messages.get("SINGLEMEMBER_TFSINGLEUNTIL_TEXT2"));
		    }
		    rset.close();
		    stmt.close();
		    dbConn.getConnection().commit();
	    }catch(SQLException ex){
	        ex.printStackTrace();
	    }
	    setDup();
	    setWarning();
	    //lended = setLended();
	    titleno = Extract.getTitleNo(dbConn.getConnection(),tfSingleID.getText(),"2");
	    super.setVisible(vis);
	  }
	  
	  public void setWarning() {
	  	
		  try{
		  		String qry = "select until_date from signing where single_id ='" + tfSingleID.getText() + "' order by until_date desc";  		
		  		Statement stmt = dbConn.getConnection().createStatement();
		        ResultSet rset = stmt.executeQuery(qry);
		        if (rset.next()){
		        	Date until = rset.getDate(1);
		        	rset.close();
		        	if (until != null){
			        	Date now = new java.sql.Date(System.currentTimeMillis());
			        	if(now.after(until)){
			        		stmt.executeUpdate("insert into blocked_cards (user_id, reason_id) values ('" + tfSingleID.getText() + "', 1)");
			        		dbConn.getConnection().commit();
			        		stmt.close();
			            }
		        	}
		        }
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
		        dbConn.getConnection().commit();
		  	}catch(SQLException e){
		  		e.printStackTrace();
		  	}
	  }
}
