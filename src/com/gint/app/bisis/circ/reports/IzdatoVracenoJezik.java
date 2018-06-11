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
public class IzdatoVracenoJezik extends EscapeDialog {

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

  public IzdatoVracenoJezik(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public IzdatoVracenoJezik() {
    this(CircApplet.parent, "", false, null);
  }

  public IzdatoVracenoJezik(String title, DBConnection dbConn, boolean modal) {
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
    jLabel1.setText(Messages.get("IZDATOVRACENOJEZIK_JLABEL1_TEXT"));

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
    String lng = "";
    Vector lngsfr = new Vector();
    Vector lngnoI = new Vector();
    Vector lngnoV = new Vector();
    Timestamp lenddate, returndate, resumedate;
    int ind = 0;
    
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
	        + "' and return_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "') or "
	        + "(resume_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	        + "' and resume_date <= '" + DBConnection.toDatabaseDate(doDatuma)+ "')) "
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
        	lng = "";
            if (rec != null) {
              try {
                lng = rec.getField("101").getSubfield('a').getContent();
              } catch (Exception e1) {
                
              }
              
            }
            lenddate = rset.getTimestamp(2);
            returndate = rset.getTimestamp(3);
            resumedate = rset.getTimestamp(4);
              
          ind = lngsfr.indexOf(lng);
          if(ind == -1){
        	  lngsfr.add(lng);
        	  ind = lngsfr.indexOf(lng);
        	  lngnoV.add(ind, new Integer(0));
        	  lngnoI.add(ind, new Integer(0));
          }
          
          if ((lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && lenddate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)
        		  || (resumedate != null && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && resumedate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0)){
        	  int tmp = ((Integer)lngnoI.get(ind)).intValue()+1;
        	  lngnoI.remove(ind);
        	  lngnoI.add(ind,new Integer(tmp));
          }
          if (returndate != null && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(odDatuma))) >= 0 && returndate.compareTo(Timestamp.valueOf(DBConnection.toDatabaseDate(doDatuma))) <= 0){
        	  int tmp = ((Integer)lngnoV.get(ind)).intValue()+1;
        	  lngnoV.remove(ind);
        	  lngnoV.add(ind,new Integer(tmp));
          }  
          
              
          }catch (Exception e1) {
        	  e1.printStackTrace();
          }
        }
            	
    	
    	StringWriter swout = new StringWriter();
        BufferedWriter out = new BufferedWriter(swout);
        
        out.write("<?xml version=\"1.0\"?>\n<Bisis>\n");
        out.newLine();
        
        int i = 0;
        int ukupnoI = 0;
        int ukupnoV = 0;
        while (i < lngsfr.size()){
        	
	        out.write("<Row>");
	        out.newLine();
	        
	        
	        if (!lngsfr.get(i).equals("")){
	        	out.write("<Sfr>" + lngsfr.get(i) +"</Sfr>");
	        }else{
	        	out.write("<Sfr>nepoznato</Sfr>");
	        }
	        out.newLine();
	
	        out.write("<NoI>" + lngnoI.get(i) +"</NoI>");
	        out.newLine();
	        
	        out.write("<NoV>" + lngnoV.get(i) +"</NoV>");
	        out.newLine();
	        
	        ukupnoI += ((Integer)lngnoI.get(i)).intValue();
	        ukupnoV += ((Integer)lngnoV.get(i)).intValue();
	
	        out.write("</Row>");
	        out.newLine();
	        
	        i++;
        
        } 
        
        
        out.write("<Row>");
        out.newLine();
        
        out.write("<Sfr>Ukupno</Sfr>");
        out.newLine();

        out.write("<NoI>" + ukupnoI + "</NoI>");
        out.newLine();
        
        out.write("<NoV>" + ukupnoV + "</NoV>");
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
              "/com/gint/app/bisis/circ/reports/izdatovracenojezik.jasper").openStream(), 
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