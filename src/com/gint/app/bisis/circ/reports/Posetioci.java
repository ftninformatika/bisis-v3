/*
 * Created on Dec 1, 2004
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
//import java.io.File;
//import java.io.FilenameFilter;
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
public class Posetioci extends EscapeDialog {

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

  public Posetioci(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Posetioci() {
    this(CircApplet.parent, "", false, null);
  }

  public Posetioci(String title, DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal, dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);

    label1.setText(Messages.get("BIBLIOTEKAR_LABEL1_TEXT"));
    jLabel2.setText(Messages.get("POSETIOCI_JLABEL2_TEXT"));
    jLabel2.setForeground(Color.blue);

    buttonOK.setText(Messages.get("BIBLIOTEKAR_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("BIBLIOTEKAR_BUTTONCANCEL_TEXT"));
    
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(cmBranch);
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));

    panel1.add(label1, new XYConstraints(37, 106, -1, -1));
    panel1.add(jLabel2, new XYConstraints(150, 43, -1, -1));
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
    String Datum = textFieldControl1.getText();
    if (DBConnection.isValidDate(Datum)) {
      try {
      	String nazivogr = (String)cboBranch.getSelectedItem();
      	cmBranch.setSelectedItem(cboBranch.getSelectedItem());
      	Integer ogranak = Integer.valueOf(cmBranch.getData());
      	if (ogranak.intValue() != 0){
      		nazivogr = "ogranak " + nazivogr;
      	}
        Map params = new HashMap(3);
        params.put("datum", java.sql.Timestamp.valueOf(DBConnection
            .toDatabaseDate(Datum)));
        params.put("ogranak", ogranak);
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

        JasperPrint jp;
        if (ogranak.intValue() != 0){
	        jp = JasperFillManager.fillReport(
	            Posetioci.class.getResource(
	                "/com/gint/app/bisis/circ/reports/posetioci.jasper").openStream(), 
	                params, dbConn.getConnection());
        }else{
        	jp = JasperFillManager.fillReport(
    	            Posetioci.class.getResource(
    	                "/com/gint/app/bisis/circ/reports/posetioci0.jasper").openStream(), 
    	                params, dbConn.getConnection());
        }
        
//        if (ogranak.intValue() != 0){
//        JasperReport jr = JasperCompileManager.compileReport(Bibliotekar.class
//                .getResource("/com/gint/app/bisis/circ/reports/posetioci.jrxml")
//                .openStream());
//        jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
//      } else {
//    	  JasperReport jr = JasperCompileManager.compileReport(Bibliotekar.class
//                .getResource("/com/gint/app/bisis/circ/reports/posetioci0.jrxml")
//                .openStream());
//        jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
//      }
        
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
    textFieldControl1.setText("");
  }

  public static void main(String[] args) {
  }
}