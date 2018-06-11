package com.gint.app.bisis.circ.reports;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

public class Groups extends EscapeDialog {

	  DBConnection dbConn;
	  JPanel panel1 = new JPanel();
	  JPanel panel2 = new JPanel();
	  XYLayout xYLayout1 = new XYLayout();
	  XYLayout xYLayout2 = new XYLayout();
	  JLabel label1 = new JLabel();
	  JButton buttonOK = new JButton();
	  JButton buttonCancel = new JButton();
	  JTextField textFieldControl1 = new JTextField();
	  JLabel jLabel2 = new JLabel();
	  private DataSet dsBranch = null, dsUsers = null;
	  private JDBLookupModel cmBranch = null;
	  JDBComboBox cboBranch;
	  JLabel lSingleBranchID = new JLabel();

	  public Groups(Frame frame, String title, boolean modal, DBConnection db) {
	    super(frame, title, modal);
	    dbConn = db;
	    try {
	      jbInit();
	      pack();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public Groups() {
	    this(CircApplet.parent, "", false, null);
	  }

	  public Groups(String title, DBConnection dbConn, boolean modal) {
	    this(CircApplet.parent, title, modal, dbConn);
	  }

	  void jbInit() throws Exception {
	    xYLayout1.setWidth(500);
	    xYLayout1.setHeight(312);
	    panel1.setLayout(xYLayout1);
	    getContentPane().add(panel1);

	    label1.setText(Messages.get("GROUPS_LABEL1_TEXT"));
	    jLabel2.setText(Messages.get("GROUPS_JLABEL2_TEXT"));
	    jLabel2.setForeground(Color.blue);
	    textFieldControl1.setEnabled(false);

	    buttonOK.setText(Messages.get("GROUPS_BUTTONOK_TEXT"));
	    buttonCancel.setText(Messages.get("GROUPS_BUTTONCANCEL_TEXT"));
	    
	    dsBranch = dbConn.getBranch();
	    dsUsers = dbConn.getUsersModel();
	    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
	    cboBranch = new JDBComboBox(cmBranch);
	    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));

	    panel1.add(label1, new XYConstraints(37, 106, -1, -1));
	    panel1.add(jLabel2, new XYConstraints(70, 43, -1, -1));
	    panel1.add(textFieldControl1, new XYConstraints(169, 103, 85, 20));
	    panel1.add(lSingleBranchID, new XYConstraints(37, 150, 85, 20));
	    panel1.add(cboBranch, new XYConstraints(169, 150, 250, -1));
	    panel1.add(buttonOK, new XYConstraints(106, 200, 85, 25));
	    panel1.add(buttonCancel, new XYConstraints(273, 200, -1, 25));

	    textFieldControl1.addKeyListener(new KeyAdapter() {
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
	      try {
	      	
	      	String nazivogr = (String)cboBranch.getSelectedItem();
	      	cmBranch.setSelectedItem(cboBranch.getSelectedItem());
	      	Integer ogranak = Integer.valueOf(cmBranch.getData());
	      	if (ogranak.intValue() != 0){
	      		nazivogr = "ogranak " + nazivogr;
	      	}
	      	
	        Map params = new HashMap(2);
	        params.put("ogranak", ogranak);
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
	            MemberBook.class.getResource(
	                "/com/gint/app/bisis/circ/reports/groups.jasper").openStream(), 
	                params, dbConn.getConnection());

//	        JasperReport jr = JasperCompileManager.compileReport(Kategorija.class.getResource(
//	        			"/com/gint/app/bisis/circ/reports/kategorija.jrxml").openStream());
//	        JasperPrint jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
	        JasperViewer.viewReport(jp, false);

	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }

	  }

	  public void clear() {
	    textFieldControl1.setText("");
	  }

}
