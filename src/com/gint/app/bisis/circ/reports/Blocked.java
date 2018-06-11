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
public class Blocked extends EscapeDialog {

  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JButton buttonOK = new JButton();
  JButton buttonCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  private DataSet dsBranch = null, dsUsers = null;
  private JDBLookupModel cmBranch = null;
  JDBComboBox cboBranch;
  JLabel lSingleBranchID = new JLabel();

  public Blocked(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Blocked() {
    this(CircApplet.parent, "", false, null);
  }

  public Blocked(String title, DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal, dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.blue);
    getContentPane().add(panel1);
    jLabel1.setText(Messages.get("BLOCKED_JLABEL1_TEXT"));
    buttonOK.setText(Messages.get("NAJCITANIJE_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("NAJCITANIJE_BUTTONCANCEL_TEXT"));
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(dsBranch, "NAME");
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));
    panel1.add(jLabel1, new XYConstraints(140, 40, -1, -1));
    panel1.add(lSingleBranchID, new XYConstraints(40, 140, 85, 20));
    panel1.add(cboBranch, new XYConstraints(164, 140, 250, -1));
    panel1.add(buttonOK, new XYConstraints(106, 230, 85, 25));
    panel1.add(buttonCancel, new XYConstraints(273, 230, -1, 25));


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
      String qryogr = "", ogranak = "";
      try {
      	String nazivogr = (String)cboBranch.getSelectedItem();
      	cmBranch.setSelectedItem(cboBranch.getSelectedItem());
      	if (Integer.parseInt(cmBranch.getData()) != 0){
      		nazivogr = "ogranak " + nazivogr;
      		ogranak = StringUtils.padZeros(Integer.parseInt(cmBranch.getData()),2);
      		qryogr = " and u.branch_id = "+ ogranak;
      	}
      	
        String qry = "select s.user_id, s.first_name, s.last_name, u.branch_id from single s, users u "
            + "where s.user_id = u.id and s.user_id in "
            + "(select single_id from signing group by single_id "
			+ "having max(until_date) < '" + DBConnection.toDatabaseDate(DBConnection.getTodaysDate()) + "')"
			+ qryogr;
        
        
        Statement stmt = dbConn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        
        StringWriter sw = new StringWriter();
        BufferedWriter out = new BufferedWriter(sw);
        out.write("<?xml version=\"1.0\"?>");
        out.newLine();
        out.write("<Bisis>");
        out.newLine();
          
        
        while (rset.next()) {
          	out.write("<User>");
            out.newLine();
            
            out.write("<User_id>" + rset.getString(1) + "</User_id>");
            out.newLine();

            out.write("<First_name>" + rset.getString(2) + "</First_name>");
            out.newLine();
            
            out.write("<Last_name>" + rset.getString(3) + "</Last_name>");
  		    out.newLine();
  		    
  		    out.write("<Reason>Istekla clanarina</Reason>");
		    out.newLine();
		    
		    out.write("<Note/>");
		    out.newLine();
  		    
	        out.write("</User>");
            out.newLine();
          
        }
        
        rset.close();
        stmt.close();
        
        qry = "select s.user_id, s.first_name, s.last_name, u.branch_id, br.name, bc.note "
            + "from single s, users u, blocked_cards bc, block_reasons br "
            + "where s.user_id = u.id and s.user_id = bc.user_id and bc.reason_id = br.id and (bc.reason_id = 2 or bc.reason_id = 3) "
			+ qryogr
			+ "order by br.name";
		
        stmt = dbConn.getConnection().createStatement();
        rset = stmt.executeQuery(qry);
        
        while (rset.next()) {
          	out.write("<User>");
            out.newLine();
            
            out.write("<User_id>" + rset.getString(1) + "</User_id>");
            out.newLine();

            out.write("<First_name>" + rset.getString(2) + "</First_name>");
            out.newLine();
            
            out.write("<Last_name>" + rset.getString(3) + "</Last_name>");
  		    out.newLine();
  		    
  		    out.write("<Reason>" + rset.getString(5) + "</Reason>");
		    out.newLine();
		    
		    out.write("<Note>" + rset.getString(6) + "</Note>");
		    out.newLine();
  		    
	        out.write("</User>");
            out.newLine();
          
        }
        
        rset.close();
        stmt.close();
        
        out.write("</Bisis>");
        out.close();

        Map params = new HashMap(1);
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
                "/com/gint/app/bisis/circ/reports/blocked.jasper").openStream(), 
                params, ds);
        JasperViewer.viewReport(jp, false);
        
      } catch (Exception ex) {
        ex.printStackTrace();
      }

  }

  public void clear() {
  }

}