/*
 * Created on Mar 24, 2005
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
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
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

/**
 * @author Administrator
 *
 */
public class Strposetilaca extends EscapeDialog{

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

	  public Strposetilaca(Frame frame, String title, boolean modal, DBConnection db) {
	    super(frame, title, modal);
	    dbConn = db;
	    try {
	      jbInit();
	      pack();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public Strposetilaca() {
	    this(CircApplet.parent, "", false, null);
	  }

	  public Strposetilaca(String title, DBConnection dbConn, boolean modal) {
	    this(CircApplet.parent, title, modal, dbConn);
	  }

	  void jbInit() throws Exception {
	    xYLayout1.setWidth(500);
	    xYLayout1.setHeight(312);
	    panel1.setLayout(xYLayout1);
	    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
	    jLabel1.setForeground(Color.blue);
	    lDatDo.setText(Messages.get("STRUKTURA_LDATDO_TEXT"));
	    getContentPane().add(panel1);

	    lDatOd.setText(Messages.get("STRUKTURA_LDATOD_TEXT"));
	    jLabel1.setText(Messages.get("STRPOS_JLABEL1_TEXT"));

	    buttonOK.setText(Messages.get("STRUKTURA_BUTTONOK_TEXT"));
	    buttonCancel.setText(Messages.get("STRUKTURA_BUTTONCANCEL_TEXT"));
	    dsBranch = dbConn.getBranch();
	    dsUsers = dbConn.getUsersModel();
	    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
	    cboBranch = new JDBComboBox(cmBranch);
	    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));
	    panel1.add(jLabel1, new XYConstraints(150, 40, -1, -1));
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
	    JasperReport kategorija;
	    JasperReport uclanjenje;
	    JasperReport pol;
	    JasperReport kategorijapasivni;
	    if (doDatuma.equals("")){
	    	doDatuma = odDatuma;
	    }else if (odDatuma.equals("")){
	    	odDatuma = doDatuma;
	    }
	    if (DBConnection.isValidDate(odDatuma)
	        && DBConnection.isValidDate(doDatuma)) {
	      try{
	      	
	      	String nazivogr = (String)cboBranch.getSelectedItem();
	      	cmBranch.setSelectedItem(cboBranch.getSelectedItem());
	      	Integer ogranak = Integer.valueOf(cmBranch.getData());
	      	
	      	File dir = new File("lib");
	        File[] files = dir.listFiles(new FilenameFilter() {
	           public boolean accept(File dir, String name) {
	             return name.startsWith("jasper") && name.endsWith("jar");
	           }
	        });
	        String classPath = "lib/"+files[0].getName() +
	           System.getProperty("path.separator") + "circ.jar";
	        System.setProperty("jasper.reports.compile.class.path", classPath);
	        
/*	        if (ogranak.intValue() != 0){
	      		nazivogr = "ogranak " + nazivogr;
	      		kategorija = JasperCompileManager.compileReport(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocik.jrxml").openStream());
		      	uclanjenje = JasperCompileManager.compileReport(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiociu.jrxml").openStream());
		      	pol = JasperCompileManager.compileReport(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocip.jrxml").openStream());
	      	}else{
		      	kategorija = JasperCompileManager.compileReport(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocik0.jrxml").openStream());
		      	uclanjenje = JasperCompileManager.compileReport(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiociu0.jrxml").openStream());
		      	pol = JasperCompileManager.compileReport(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocip0.jrxml").openStream());
	      	}*/
	        
	        if (ogranak.intValue() != 0){
	      		nazivogr = "ogranak " + nazivogr;
	      		kategorija = (JasperReport)JRLoader.loadObject(
	      	            Strposetilaca.class.getResource("/com/gint/app/bisis/circ/reports/posetiocik.jasper").openStream()); 
	      		kategorijapasivni = (JasperReport)JRLoader.loadObject(
	      	            Strposetilaca.class.getResource("/com/gint/app/bisis/circ/reports/posetiocikpasiv.jasper").openStream());
		      	uclanjenje = (JasperReport)JRLoader.loadObject(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiociu.jasper").openStream());
		      	pol = (JasperReport)JRLoader.loadObject(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocip.jasper").openStream());
	      	}else{
		      	kategorija = (JasperReport)JRLoader.loadObject(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocik0.jasper").openStream());
		      	kategorijapasivni = (JasperReport)JRLoader.loadObject(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocik0pasiv.jasper").openStream());
		      	uclanjenje = (JasperReport)JRLoader.loadObject(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiociu0.jasper").openStream());
		      	pol = (JasperReport)JRLoader.loadObject(Strposetilaca.class
			            .getResource("/com/gint/app/bisis/circ/reports/posetiocip0.jasper").openStream());
	      	}
	        Map params = new HashMap(8);
	        params.put("begdate", java.sql.Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma)));
	        params.put("enddate", java.sql.Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma)));
	        params.put("kategorija",kategorija);
	        params.put("kategorijapasivni",kategorijapasivni);
	        params.put("uclanjenje",uclanjenje);
	        params.put("pol",pol);
	        params.put("ogranak",ogranak);
	        params.put("nazivogr",nazivogr);
	        
          JasperPrint jp = JasperFillManager.fillReport(
              Strposetilaca.class.getResource(
                  "/com/gint/app/bisis/circ/reports/strposetilaca.jasper").openStream(), 
                  params, dbConn.getConnection());
	        
//	        JasperReport jr = JasperCompileManager.compileReport(Struktura.class
//		            .getResource("/com/gint/app/bisis/circ/reports/strposetilaca.jrxml").openStream());
//	        JasperPrint jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
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
