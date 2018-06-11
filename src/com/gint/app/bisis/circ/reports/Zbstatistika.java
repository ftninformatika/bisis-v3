/*
 * Created on Apr 6, 2005
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
import java.util.HashMap;
import java.util.Map;

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
import com.gint.util.xml.XMLUtils;

/**
 * @author Administrator
 *
 */
public class Zbstatistika extends EscapeDialog{
	
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
	  String qry = "";

	  public Zbstatistika(Frame frame, String title, boolean modal, DBConnection db) {
	    super(frame, title, modal);
	    dbConn = db;
	    try {
	      jbInit();
	      pack();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public Zbstatistika() {
	    this(CircApplet.parent, "", false, null);
	  }

	  public Zbstatistika(String title, DBConnection dbConn, boolean modal) {
	    this(CircApplet.parent, title, modal, dbConn);
	  }

	  void jbInit() throws Exception {
	    xYLayout1.setWidth(500);
	    xYLayout1.setHeight(312);
	    panel1.setLayout(xYLayout1);
	    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
	    jLabel1.setForeground(Color.blue);
	    lDatDo.setText(Messages.get("NAJCITANIJE_LDATDO_TEXT"));
	    getContentPane().add(panel1);

	    lDatOd.setText(Messages.get("NAJCITANIJE_LDATOD_TEXT"));
	    jLabel1.setText(Messages.get("ZBSTAT_JLABEL1_TEXT"));

	    buttonOK.setText(Messages.get("NAJCITANIJE_BUTTONOK_TEXT"));
	    buttonCancel.setText(Messages.get("NAJCITANIJE_BUTTONCANCEL_TEXT"));
	    dsBranch = dbConn.getBranch();
	    dsUsers = dbConn.getUsersModel();
	    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
	    cboBranch = new JDBComboBox(cmBranch);
	    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));
	    panel1.add(jLabel1, new XYConstraints(120, 40, -1, -1));
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
	    if (doDatuma.equals("")){
	    	doDatuma = odDatuma;
	    }else if (odDatuma.equals("")){
	    	odDatuma = doDatuma;
	    }
	    if (DBConnection.isValidDate(odDatuma)
	        && DBConnection.isValidDate(doDatuma)) {
	    	try {
	    	  StringWriter sw = new StringWriter();
		      BufferedWriter out = new BufferedWriter(sw);
		      out.write("<?xml version=\"1.0\"?>");
		      out.newLine();
		      out.write("<Bisis>");
		      out.newLine();
	          out.write("<Zapis>");
	          out.newLine();
	        
	        cmBranch.setSelectedItem(cboBranch.getSelectedItem());  
	      	int ogranak = Integer.parseInt(cmBranch.getData());
	      	String nazivogr = (String)cboBranch.getSelectedItem();
	      	if (ogranak != 0){
	      		nazivogr = "ogranak " + nazivogr;
	      	}
	      	
	      	if (ogranak != 0){
		        qry = "select count(*)  from users u, signing s "
	        	+ "where u.id = s.single_id and u.branch_id = " + ogranak + " and "
	            + " s.sdate >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and s.sdate <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'";
	      	}else{
	      		qry = "select count(*) from signing where "
	            + " sdate >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and sdate <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'";
	      	}
	        
	        Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        
	        if (rset.next()) {
	        	out.write("<upiskor>" + rset.getInt(1) + "</upiskor>");
		        out.newLine();
	        }else{
	        	out.write("<upiskor></upiskor>");
		        out.newLine();
	        }
	        rset.close();
        
	       
	        if (ogranak != 0){
		        qry = "select lend_date, count(distinct single_id) from lending where"
	            + " ((lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "') or  " 
	            + "(resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'))"
	            + " and location = " + ogranak
	            + " group by lend_date";
	        }else{
	        	qry = "select lend_date, count(distinct single_id) from lending where"
	            + " ((lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "') or " 
	            + " (resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "')) " 
	            + " group by lend_date";
	        }
	        
	        rset = stmt.executeQuery(qry);
	        int count = 0;
	        while (rset.next()){
	        	count += rset.getInt(2);
	        }
        	out.write("<zaduzkor>" + count + "</zaduzkor>");
	        out.newLine();
	        rset.close();
	        
	        
	        if (ogranak != 0){
		        qry = "select return_date, count(distinct single_id) from lending where"
	            + " return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "' "
		        + "and location = " + ogranak
		        + " group by return_date";
	        }else{
	        	 qry = "select return_date, count(distinct single_id) from lending where"
	             + " return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	             + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "' "
	             + " group by return_date";
	        }
	        
	        rset = stmt.executeQuery(qry);
	        count = 0;
	        while (rset.next()){
	        	count += rset.getInt(2);
	        }
        	out.write("<razduzkor>" + count + "</razduzkor>");
	        out.newLine();
	        rset.close();
	        

	        if(ogranak != 0) {
	        	qry = "select distinct lend_date, single_id from lending where"
		            + " lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
		            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
		            + " and location = " + ogranak
		            + " and (return_date is null or return_date > lend_date)"
					+ " union "
					+ " select distinct resume_date, single_id from lending where"
		            + " resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
		            + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
		            + " and location = " + ogranak
		            + " and (return_date is null or return_date > resume_date)";
	        }else{
	        	qry = "select distinct lend_date, single_id from lending where"
		            + " lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
		            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
		            + " and (return_date is null or return_date > lend_date)"
					+ " union "
					+ " select distinct resume_date, single_id from lending where"
		            + " resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
		            + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
		            + " and (return_date is null or return_date > resume_date)";
	        }
	        
	        rset = stmt.executeQuery(qry);
	        int aktiv = 0;
	        while (rset.next()){
	        	aktiv++;
	        }
        	out.write("<aktkor>" + aktiv + "</aktkor>");
	        out.newLine();
	        rset.close();
	        
	        
	        
	        if(ogranak != 0) {
				qry = "select l1.return_date, count(distinct l1.single_id) from lending l1 where"
	            + " l1.return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and l1.return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "' " 
				+ " and l1.location = " + ogranak
	            + " and l1.single_id not in (select distinct(l2.single_id) from lending l2 where "
	            + " l2.location = " + ogranak +" and (l2.lend_date = l1.return_date or l2.resume_date = l1.return_date) and (l2.return_date is null or l2.return_date > l1.return_date))"
	            + " group by l1.return_date";
	        }else{
	        	qry = "select l1.return_date, count(distinct l1.single_id) from lending l1 where"
	            + " l1.return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and l1.return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "' " 
	            + " and l1.single_id not in (select distinct(l2.single_id) from lending l2 where "
	            + " (l2.lend_date = l1.return_date or l2.resume_date = l1.return_date) and (l2.return_date is null or l2.return_date > l1.return_date))"
	            + " group by l1.return_date";
	        }
    		
	        rset = stmt.executeQuery(qry);
	        int pasiv = 0;
	        while (rset.next()){
	        	pasiv += rset.getInt(2);
	        }
	        rset.close();
	        
	        if(ogranak != 0) {
				qry = "select count(s.single_id) from signing s, users u "
	        		+ "where u.id = s.single_id and u.branch_id = "+ ogranak +" and s.sdate >= '" + DBConnection.toDatabaseDate(odDatuma) + "' " 
	        		+ "and s.sdate <= '"+ DBConnection.toDatabaseDate(doDatuma) +"' "
	        		+ "and s.single_id not in (select l.single_id from lending l "
	        		+ "where l.location = "+ ogranak +" and (l.lend_date = s.sdate or l.return_date = s.sdate or l.resume_date = s.sdate))";
	        }else{
	        	qry = "select count(s.single_id) from signing s "
	        		+ "where s.sdate >= '" + DBConnection.toDatabaseDate(odDatuma) + "' and s.sdate <= '"+ DBConnection.toDatabaseDate(doDatuma) +"' "
	        		+ "and s.single_id not in (select l.single_id from lending l "
	        		+ "where l.lend_date = s.sdate or l.return_date = s.sdate or l.resume_date = s.sdate)";
	        }
	        rset = stmt.executeQuery(qry);
	        if (rset.next()) {
	        	pasiv += rset.getInt(1);
	        }	        
	        out.write("<paskor>" + pasiv + "</paskor>");
	        out.newLine();
	        rset.close();

	        
	        aktiv += pasiv;
	        out.write("<ukupnokor>" + aktiv + "</ukupnokor>");
  
	        
	        if (ogranak != 0){
	        	qry = "select lend_date, count(distinct ctlg_no)  from lending where "
        		+ " lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
	            + " and location = " + ogranak
	            + " group by lend_date"; 
	        }else{
	        	qry = "select lend_date, count(distinct ctlg_no)  from lending where "
	            + " lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
	            + " group by lend_date"; 
	        }
             
	        rset = stmt.executeQuery(qry);
	        count = 0;
	        while (rset.next()){
	        	count += rset.getInt(2);
	        }
	        out.write("<zaduzknj>" + count + "</zaduzknj>");
		    out.newLine();
	        rset.close();
	        
	        if (ogranak != 0){
	        	qry = "select lend_date, count(distinct ctlg_no)  from lending where "
	            + " resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
		        + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
	            + " and location = " + ogranak
	            + " group by lend_date"; 
	        }else{
	        	qry = "select lend_date, count(distinct ctlg_no)  from lending where "
	            + " resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
		        + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
	            + " group by lend_date"; 
	        }
             
	        rset = stmt.executeQuery(qry);
	        count = 0;
	        while (rset.next()){
	        	count += rset.getInt(2);
	        }
	        out.write("<produzknj>" + count + "</produzknj>");
		    out.newLine();
	        rset.close();
	
	        
	        if(ogranak != 0){
	        	qry = "select return_date, count(distinct ctlg_no)  from lending where "
                + " return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
                + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "' and location = "  + ogranak
                + " group by return_date";
	        }else{
	        	qry = "select return_date, count(distinct ctlg_no)  from lending where "
                + " return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
                + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
                + " group by return_date";
	        }
	        
            rset = stmt.executeQuery(qry);
            count = 0;
	        while (rset.next()){
	        	count += rset.getInt(2);
	        }
        	out.write("<razduzknj>" + count + "</razduzknj>");
	        out.newLine();
	        rset.close();
	
	        
	        if(ogranak != 0){
	        	qry = "select count(ctlg_no) from lending where "
	                + " lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	                + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"
	                + " and location = " + ogranak; 
	        }else{
		        qry = "select count(ctlg_no) from lending where "
		        	+ " lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	                + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'";
	        }

	        rset = stmt.executeQuery(qry);
	        if (rset.next()) {
	        	out.write("<zaduzusl>" + rset.getInt(1) + "</zaduzusl>");
		        out.newLine();
	        }else{
	        	out.write("<zaduzusl></zaduzusl>");
		        out.newLine();
	        }
	        rset.close();
	        
	        if(ogranak != 0){
	        	qry = "select count(ctlg_no) from lending where "
	                + " resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	                + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'"		
	                + " and location = " + ogranak; 
	        }else{
		        qry = "select count(ctlg_no) from lending where "
	                + " resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	                + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'";	
	        }

	        rset = stmt.executeQuery(qry);
	        if (rset.next()) {
	        	out.write("<produzusl>" + rset.getInt(1) + "</produzusl>");
		        out.newLine();
	        }else{
	        	out.write("<produzusl></produzusl>");
		        out.newLine();
	        }
	        rset.close();
		
	        
	        if (ogranak != 0){
		        qry = "select count(ctlg_no)  from lending where "
	            + " return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "' and location = "  + ogranak;
	        }else{
	        	qry = "select count(ctlg_no)  from lending where "
	            + " return_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "'";
	        }
	        
            rset = stmt.executeQuery(qry);
	        if (rset.next()) {
	        	out.write("<razduzusl>" + rset.getInt(1) + "</razduzusl>");
		        out.newLine();
	        }else{
	        	out.write("<razduzusl></razduzusl>");
		        out.newLine();
	        }
	        rset.close();
        
	        
	        stmt.close();
	        out.write("</Zapis>");
	        out.newLine();
	        out.write("</Bisis>");
		    out.close();
	        

	        JRXmlDataSource ds = new JRXmlDataSource(XMLUtils.getDocumentFromString(sw.toString()),
	            "/Bisis/Zapis");

	        Map params = new HashMap(2);
	        params.put("begdate", odDatuma);
	        params.put("enddate", doDatuma);
	        params.put("nazivogr", nazivogr);
	        
//	        File dir = new File("lib");
//	        File[] files = dir.listFiles(new FilenameFilter() {
//	           public boolean accept(File dir, String name) {
//	             return name.startsWith("jasper") && name.endsWith("jar");
//	           }
//	        });
//	        String classPath = "lib/"+files[0].getName() +
//	           System.getProperty("path.separator") + "circ.jar";
//	        System.setProperty("jasper.reports.compile.class.path", classPath);

	        JasperPrint jp = JasperFillManager.fillReport(
              Zbstatistika.class.getResource(
                  "/com/gint/app/bisis/circ/reports/zbstatistika.jasper")
              .openStream(), params, ds);
//          JasperReport jr = JasperCompileManager.compileReport(Zbstatistika.class
//		            .getResource("/com/gint/app/bisis/circ/reports/zbstatistika.jrxml")
//	                .openStream());
//	        JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
	        JasperViewer.viewReport(jp, false);

	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }

	    } else {
	      JOptionPane.showMessageDialog(null, "Pogre\u0161an format datuma",
	          "Datum", JOptionPane.ERROR_MESSAGE);
	    }

	  }

	  public void clear() {
	    tfDatOd.setText("");
	    tfDatDo.setText("");
	  }

	  


}
