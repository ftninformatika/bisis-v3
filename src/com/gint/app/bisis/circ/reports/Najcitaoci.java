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
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import org.w3c.dom.Document;

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
public class Najcitaoci extends EscapeDialog {

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

  public Najcitaoci(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Najcitaoci() {
    this(CircApplet.parent, "", false, null);
  }

  public Najcitaoci(String title, DBConnection dbConn, boolean modal) {
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
    jLabel1.setText(Messages.get("NAJCITAOCI_JLABEL1_TEXT"));

    buttonOK.setText(Messages.get("NAJCITANIJE_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("NAJCITANIJE_BUTTONCANCEL_TEXT"));
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(dsBranch, "NAME");
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
    String qryogr = "", ogranak = "";
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
      	
        String qry = "select  single_id, count(distinct ctlg_no)  from lending "
            + "where lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma) + "'"
            + qryogr
			+ " group by single_id order by count(distinct ctlg_no) desc"; 

        Statement stmt = dbConn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        
        PreparedStatement userstmt = dbConn.getConnection().prepareStatement(
        		"select first_name, last_name from single where user_id = ?");
        ResultSet userrset;
        
        StringWriter sw = new StringWriter();
        BufferedWriter out = new BufferedWriter(sw);
        out.write("<?xml version=\"1.0\"?>");
        out.newLine();
        out.write("<Bisis>");
        out.newLine();
          
        int max = 0;
        while (rset.next() && max < 20) {
          max++;
          userstmt.setString(1, rset.getString(1));
          userrset = userstmt.executeQuery();
          
          if (userrset.next()){
          	out.write("<User>");
            out.newLine();
            
            out.write("<User_id>" + rset.getString(1) + "</User_id>");
            out.newLine();

            out.write("<First_name>" + userrset.getString(1) + "</First_name>");
            out.newLine();
            
            out.write("<Last_name>" + userrset.getString(2) + "</Last_name>");
  		    out.newLine();
  		    
	  		out.write("<Pub_no>" + rset.getInt(2) + "</Pub_no>");
	        out.newLine();
	        
	        out.write("</User>");
            out.newLine();
          }
        }
        
        out.write("</Bisis>");
        out.close();
        
        userstmt.close();
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
        
        JRXmlDataSource ds = new JRXmlDataSource(XMLUtils.getDocumentFromString(sw.toString()),
          "/Bisis/User");

        JasperPrint jp = JasperFillManager.fillReport(
            Najcitaoci.class.getResource(
                "/com/gint/app/bisis/circ/reports/najcitaoci.jasper").openStream(), 
                params, ds);
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