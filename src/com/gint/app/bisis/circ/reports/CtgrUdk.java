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
import java.util.ArrayList;
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
public class CtgrUdk extends EscapeDialog {

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

  public CtgrUdk(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public CtgrUdk() {
    this(CircApplet.parent, "", false, null);
  }

  public CtgrUdk(String title, DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal, dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.blue);
    lDatDo.setText(Messages.get("CTGRUDK_LDATDO_TEXT"));
    getContentPane().add(panel1);

    lDatOd.setText(Messages.get("CTGRUDK_LDATOD_TEXT"));
    jLabel1.setText(Messages.get("CTGRUDK_JLABEL1_TEXT"));

    buttonOK.setText(Messages.get("CTGRUDK_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("CTGRUDK_BUTTONCANCEL_TEXT"));
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(cmBranch);
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));
    panel1.add(jLabel1, new XYConstraints(70, 40, -1, -1));
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
    	String nazivogr = (String)cboBranch.getSelectedItem();
      	cmBranch.setSelectedItem(cboBranch.getSelectedItem());
      	int ogr = Integer.parseInt(cmBranch.getData());

    	try {
	      	if (ogr != 0){
	      		nazivogr = "ogranak " + nazivogr;
	      		//ogranak = StringUtils.padZeros(ogr,2);
	      		qryogr = " and l.location = "+ ogr;
	      	}
	      	
	        String qry = "select  l.ctlg_no, l.single_id, s.user_ctgr, uc.name"
	            + " from lending l, single s, user_categs uc"
	            + " where l.single_id = s.user_id and s.user_ctgr = uc.id"
	            + " and ((l.lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and l.lend_date <= '" + DBConnection.toDatabaseDate(doDatuma) + "') or "
	            + " (l.resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and l.resume_date <= '" + DBConnection.toDatabaseDate(doDatuma) + "')) "
				+ qryogr
	            + " order by s.user_ctgr";
	        Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        
	        List ls = new ArrayList();
	        while (rset.next()) {
	                 ls.add(rset.getString(1));
	        }
	        rset.close();
	       
	        List recs = InvNumUtils.getRecords(ls, dbConn.getConnection());

	        rset = stmt.executeQuery(qry);

	        StringWriter sw = new StringWriter();
	        BufferedWriter out = new BufferedWriter(sw);
	        out.write("<?xml version=\"1.0\"?>");
	        out.newLine();
	        out.write("<Bisis>");
	        out.newLine();

	        Iterator it = recs.iterator();
	        while (rset.next() && it.hasNext()) {
	          Record rec = (Record) it.next();
	          try {
	            if (rec != null) {
	              String udk = "";
	              try {
	                udk = rec.getField("675").getSubfield('a').getContent()
	                    .substring(0, 1);
	              } catch (Exception e1) {
	                try {
	                  udk = rec.getField("675").getSubfield('u').getContent()
	                      .substring(0, 1);
	                } catch (Exception e2) {

	                }
	              }
	              if (!udk.equals("")) {
	                out.write("<Zapis>");
	                out.newLine();

	                out.write("<Ctlg>" + rset.getString(1) + "</Ctlg>");
	                out.newLine();

	                out.write("<UserID>" + rset.getString(2) + "</UserID>");
	                out.newLine();

	                out.write("<UserCtgr>" + rset.getInt(3) + "</UserCtgr>");
	                out.newLine();

	                out.write("<CtgrName>" + rset.getString(4) + "</CtgrName>");
	                out.newLine();

	                out.write("<Udk>" + udk + "</Udk>");
	                out.newLine();

	                out.write("</Zapis>");
	                out.newLine();
	              }
	            }
	          } catch (Exception e1) {

	          }
	        }

	        out.write("</Bisis>");
	        out.close();

	        rset.close();
	        stmt.close();
	        

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
	        
	        JRXmlDataSource ds = new JRXmlDataSource(XMLUtils
	            .getDocumentFromString(sw.toString()), "/Bisis/Zapis");

          JasperPrint jp = JasperFillManager.fillReport(
              CtgrUdk.class.getResource(
                "/com/gint/app/bisis/circ/reports/ctgrudk.jasper").openStream(), 
                params, ds);
//          JasperReport jr = JasperCompileManager.compileReport(CtgrUdk.class.getResource(
//	          "/com/gint/app/bisis/circ/reports/ctgrudk.jrxml").openStream());
//	        JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
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