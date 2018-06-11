/*
 * Created on Jan 22, 2005
 *  
 */
package com.gint.app.bisis.circ.reports;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FilenameFilter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.view.JasperViewer;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.DataSet;
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.JDBComboBox;
import com.gint.app.bisis.circ.JDBLookupModel;
import com.gint.app.bisis.circ.Messages;
import com.gint.app.bisis4.common.records.Record;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

/**
 * @author Administrator
 *  
 */
public class IzdatoVraceno extends EscapeDialog {

  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel lDatOd = new JLabel();
  JButton buttonOK = new JButton();
  JButton buttonCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  JTextField tfDatOd = new JTextField();
  JLabel lDatDo = new JLabel();
  JTextField tfDatDo = new JTextField();
  private DataSet dsBranch = null, dsUsers = null;
  private JDBLookupModel cmBranch = null;
  JDBComboBox cboBranch;
  JLabel lSingleBranchID = new JLabel();

  public IzdatoVraceno(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public IzdatoVraceno() {
    this(CircApplet.parent, "", false, null);
  }

  public IzdatoVraceno(String title, DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal, dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.blue);
    lDatDo.setText(Messages.get("IZDATOVRACENO_LDATDO_TEXT"));
    getContentPane().add(panel1);

    lDatOd.setText(Messages.get("IZDATOVRACENO_LDATOD_TEXT"));
    jLabel1.setText(Messages.get("IZDATOVRACENO_JLABEL1_TEXT"));

    buttonOK.setText(Messages.get("IZDATOVRACENO_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("IZDATOVRACENO_BUTTONCANCEL_TEXT"));
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(cmBranch);
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));
    panel1.add(jLabel1, new XYConstraints(90, 40, -1, -1));
    panel1.add(lDatOd, new XYConstraints(40, 110, -1, -1));
    panel1.add(lDatDo, new XYConstraints(40, 145, -1, 19));
    panel1.add(tfDatOd, new XYConstraints(164, 107, 85, 20));
    panel1.add(tfDatDo, new XYConstraints(164, 143, 85, 20));
    panel1.add(lSingleBranchID, new XYConstraints(40, 180, 85, 20));
    panel1.add(cboBranch, new XYConstraints(164, 180, 250, -1));
    panel1.add(buttonOK, new XYConstraints(106, 230, 85, 25));
    panel1.add(buttonCancel, new XYConstraints(273, 230, -1, 25));

    tfDatOd.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
          buttonOK.doClick();
      }
    });

    tfDatDo.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
          buttonOK.doClick();
      }
    });

    buttonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonOK_actionPerformed(e);
      }
    });

    buttonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clear();
        setVisible(false);
      }
    });

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        clear();
        setVisible(false);
      }
    });

  }

  void buttonOK_actionPerformed(ActionEvent e) {
    String odDatuma = tfDatOd.getText();
    String doDatuma = tfDatDo.getText();
    String qryogr = "", ogranak = "";
    String udk;
    int izP0=0, izN0=0, izP0875=0, izN0875=0, izP1=0, izN1=0, izP2=0, izN2=0, izP3=0, izN3=0, izP4=0, izN4=0, izP5=0, izN5=0;
    int izP6=0, izN6=0, izP7=0, izN7=0, izP8=0, izN8=0, izP8289=0, izN8289=0, izP9=0, izN9=0, izunknown=0, izPu=0, izNu=0;
    int vrP0=0, vrN0=0, vrP0875=0, vrN0875=0, vrP1=0, vrN1=0, vrP2=0, vrN2=0, vrP3=0, vrN3=0, vrP4=0, vrN4=0, vrP5=0, vrN5=0;
    int vrP6=0, vrN6=0, vrP7=0, vrN7=0, vrP8=0, vrN8=0, vrP8289=0, vrN8289=0, vrP9=0, vrN9=0, vrunknown=0, vrPu=0, vrNu=0;
    Vector nasloviI = new Vector();
    Vector nasloviV = new Vector();
    Timestamp lenddate, returndate, resumedate;
    Integer docid = null;
    
    if (doDatuma.equals("")){
    	doDatuma = odDatuma;
    }else if (odDatuma.equals("")){
    	odDatuma = doDatuma;
    }
    if (DBConnection.isValidDate(odDatuma)
        && DBConnection.isValidDate(doDatuma)) {
      try {
      	String nazivogr = (String)cboBranch.getSelectedItem();
      	cmBranch.setSelectedItem(cboBranch.getSelectedItem());
       	if (Integer.parseInt(cmBranch.getData()) != 0){
       		nazivogr = "ogranak " + nazivogr;
       		//ogranak = StringUtils.padZeros(Integer.parseInt(cmBranch.getData()),2);
      		qryogr = " and location = "+ cmBranch.getData();
       	}
      
        String qry = "select  ctlg_no, lend_date, return_date, resume_date from lending "
            + " where ((lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "') or "
			+ "(return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	        + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "') or"
	        + "(resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	        + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'))"
            + qryogr;

        Statement stmt = dbConn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        

        List ls = new ArrayList();
        while (rset.next()) {
        	ls.add(rset.getString(1));
        }
        rset.close();
        List recs = InvNumUtils.getRecords(ls, dbConn.getConnection());
        rset = stmt.executeQuery(qry);

        Iterator it = recs.iterator();
        while (rset.next() && it.hasNext()) {
          Record rec = (Record) it.next();
          try {
        	udk = "";
            if (rec != null) {
              try {
                udk = rec.getField("675").getSubfield('a').getContent();
              } catch (Exception e1) {
                try {
                  udk = rec.getField("675").getSubfield('u').getContent();
                } catch (Exception e2) {

                }
              }
              docid = new Integer(rec.getRecordID().getLocalRecordID());
            }
              lenddate = rset.getTimestamp(2);
              returndate = rset.getTimestamp(3);
              resumedate = rset.getTimestamp(4);
                            
              if (udk.equals("")) {
            	  if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
            			  || (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                  	  izunknown++;
                  	  izPu++;
  	                }
                    if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                  	  vrunknown++;
                  	  vrPu++;
                    }        
              }else if (udk.startsWith("087.5")){
            		if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
            				|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                  	  izP0875++;
                  	  izPu++;
  	                  if(!nasloviI.contains(docid)){
  	                	  izN0875++;
  	                	  izNu++;
  	                	  nasloviI.add(docid);
  	                  }
                    }
                    if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                  	  vrP0875++;
                  	  vrPu++;
  	                  if(!nasloviV.contains(docid)){
  	                	  vrN0875++;
  	                	  vrNu++;
  	                	  nasloviV.add(docid);
  	                  }
                    } 
                    
                } else if (udk.startsWith("0")) {
                  if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                		  || (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                	  izP0++;
                	  izPu++;
	                  if(!nasloviI.contains(docid)){
	                	  izN0++;
	                	  izNu++;
	                	  nasloviI.add(docid);
	                  }
                  }
                  if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                	  vrP0++;
                	  vrPu++;
	                  if(!nasloviV.contains(docid)){
	                	  vrN0++;
	                	  vrNu++;
	                	  nasloviV.add(docid);
	                  }
                  }
                
                } else if (udk.startsWith("1")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                  	  izP1++;
                  	  izPu++;
  	                  if(!nasloviI.contains(docid)){
  	                	  izN1++;
  	                	  izNu++;
  	                	  nasloviI.add(docid);
  	                  }
                    }
                    if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                  	  vrP1++;
                  	  vrPu++;
  	                  if(!nasloviV.contains(docid)){
  	                	  vrN1++;
  	                	  vrNu++;
  	                	  nasloviV.add(docid);
  	                  }
                    }
                    
                } else if (udk.startsWith("2")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP2++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN2++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP2++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN2++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                      
                } else if (udk.startsWith("3")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP3++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN3++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP3++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN3++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("4")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP4++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN4++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP4++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN4++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("5")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP5++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN5++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP5++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN5++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("6")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP6++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN6++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP6++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN6++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("7")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP7++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN7++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP7++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN7++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("82") || udk.startsWith("89")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP8289++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN8289++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP8289++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN8289++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("8")){
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP8++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN8++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP8++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN8++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else if (udk.startsWith("9")) {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izP9++;
                    	  izPu++;
    	                  if(!nasloviI.contains(docid)){
    	                	  izN9++;
    	                	  izNu++;
    	                	  nasloviI.add(docid);
    	                  }
                      }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrP9++;
                    	  vrPu++;
    	                  if(!nasloviV.contains(docid)){
    	                	  vrN9++;
    	                	  vrNu++;
    	                	  nasloviV.add(docid);
    	                  }
                      }
                } else {
                	if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
                			|| (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
                    	  izunknown++;
                    	  izPu++;
    	                }
                      if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
                    	  vrunknown++;
                    	  vrPu++;
                      }  
                }
          }catch (Exception e1) {
        	  e1.printStackTrace();
          }
        }
            	
    	
    	StringWriter swout = new StringWriter();
        BufferedWriter out = new BufferedWriter(swout);
        
        out.write("<?xml version=\"1.0\"?>\n<Bisis>\n");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>087.5</Udk>");
        out.newLine();

        out.write("<IzP>" + izP0875 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN0875 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP0875+ "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN0875 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>0</Udk>");
        out.newLine();

        out.write("<IzP>" + izP0 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN0 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP0 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN0 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>1</Udk>");
        out.newLine();

        out.write("<IzP>" + izP1 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN1 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP1 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN1 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>2</Udk>");
        out.newLine();

        out.write("<IzP>" + izP2 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN2 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP2 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN2 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>3</Udk>");
        out.newLine();

        out.write("<IzP>" + izP3 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN3 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP3 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN3 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>4</Udk>");
        out.newLine();

        out.write("<IzP>" + izP4 + "</IzP>");
        out.newLine();
        
        out.write("<IzN>" + izN4 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP4 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN4 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>5</Udk>");
        out.newLine();

        out.write("<IzP>" + izP5 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN5 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP5 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN5 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>6</Udk>");
        out.newLine();

        out.write("<IzP>" + izP6 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN6 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP6 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN6 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>7</Udk>");
        out.newLine();

        out.write("<IzP>" + izP7 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN7 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP7 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN7 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>8</Udk>");
        out.newLine();

        out.write("<IzP>" + izP8 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN8 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP8 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN8 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>82/89</Udk>");
        out.newLine();

        out.write("<IzP>" + izP8289 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN8289 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP8289 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN8289 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>9</Udk>");
        out.newLine();

        out.write("<IzP>" + izP9 + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izN9 + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrP9 + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrN9 + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        if (izunknown != 0 || vrunknown !=0){
        	out.write("<Row>");
            out.newLine();
            
            out.write("<Udk>NN</Udk>");
            out.newLine();

            out.write("<IzP>" + izunknown + "</IzP>");
            out.newLine();

            out.write("<IzN>0</IzN>");
            out.newLine();

            out.write("<VrP>" + vrunknown + "</VrP>");
            out.newLine();

            out.write("<VrN>0</VrN>");
            out.newLine();

            out.write("</Row>");
            out.newLine();
        }
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Udk>Ukupno</Udk>");
        out.newLine();

        out.write("<IzP>" + izPu + "</IzP>");
        out.newLine();

        out.write("<IzN>" + izNu + "</IzN>");
        out.newLine();

        out.write("<VrP>" + vrPu + "</VrP>");
        out.newLine();

        out.write("<VrN>" + vrNu + "</VrN>");
        out.newLine();

        out.write("</Row>");
        out.newLine();
        
        out.write("</Bisis>");
        out.close();
                
        rset.close();
        stmt.close();        
       
        Map params = new HashMap(2);
        params.put("begdate", odDatuma);
        params.put("enddate", doDatuma);
        params.put("nazivogr", nazivogr);
        
//        File dir = new File("lib");
//        File[] files = dir.listFiles(new FilenameFilter() {
//           public boolean accept(File dir, String name) {
//             return name.startsWith("jasper") && name.endsWith("jar");
//           }
//        });
//        String classPath = "lib/"+files[0].getName() +
//           System.getProperty("path.separator") + "circ.jar";
//        System.setProperty("jasper.reports.compile.class.path", classPath);
        
        JRXmlDataSource ds = new JRXmlDataSource(
            XMLUtils.getDocumentFromString(swout.toString()), "/Bisis/Row");
        
        JasperPrint jp = JasperFillManager.fillReport(
            IzdatoVraceno.class.getResource(
              "/com/gint/app/bisis/circ/reports/izdatovraceno.jasper").openStream(), 
            params, ds);
        
//        JasperReport jr = JasperCompileManager.compileReport(IzdatoVraceno.class
//                .getResource("/com/gint/app/bisis/circ/reports/izdatovraceno.jrxml").openStream());
//        JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
        JasperViewer.viewReport(jp, false);

      } catch (Exception ex) {
        ex.printStackTrace();
      }

    } else {
      JOptionPane.showMessageDialog(null, "Pogresan format datuma", "Datum",
          JOptionPane.ERROR_MESSAGE);
    }

  }

  public void clear() {
    tfDatOd.setText("");
    tfDatDo.setText("");
  }

}