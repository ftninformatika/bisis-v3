/*
 * Created on Mar 4, 2005
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.w3c.dom.Document;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.Messages;
import com.gint.app.bisis4.common.records.Record;
import com.gint.util.xml.XMLUtils;

/**
 * @author Administrator
 *
 */
public class Istorija extends EscapeDialog{
	
	  DBConnection dbConn;
	  JPanel panel1 = new JPanel();
	  JPanel panel2 = new JPanel();
	  XYLayout xYLayout1 = new XYLayout();
	  XYLayout xYLayout2 = new XYLayout();
	  JLabel lDatOd = new JLabel();
	  JButton buttonOK = new JButton();
	  JButton buttonCancel = new JButton();
	  JLabel jLabel1 = new JLabel();
	  JLabel lBrojCl = new JLabel();
	  JTextField tfBrojCl = new JTextField();
	  JTextField tfDatOd = new JTextField();
	  JLabel lDatDo = new JLabel();
	  JTextField tfDatDo = new JTextField();

	  public Istorija(Frame frame, String title, boolean modal, DBConnection db) {
	    super(frame, title, modal);
	    dbConn = db;
	    try {
	      jbInit();
	      pack();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public Istorija() {
	    this(CircApplet.parent, "", false, null);
	  }

	  public Istorija(String title, DBConnection dbConn, boolean modal) {
	    this(CircApplet.parent, title, modal, dbConn);
	  }

	  void jbInit() throws Exception {
	    xYLayout1.setWidth(500);
	    xYLayout1.setHeight(312);
	    panel1.setLayout(xYLayout1);
	    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
	    jLabel1.setForeground(Color.blue);
	    lBrojCl.setText(Messages.get("ISTORIJA_BROJCL_TEXT") + " ");
	    lDatDo.setText(Messages.get("ISTORIJA_LDATDO_TEXT"));
	    getContentPane().add(panel1);

	    lDatOd.setText(Messages.get("ISTORIJA_LDATOD_TEXT"));
	    jLabel1.setText(Messages.get("ISTORIJA_JLABEL1_TEXT"));

	    buttonOK.setText(Messages.get("ISTORIJA_BUTTONOK_TEXT"));
	    buttonCancel.setText(Messages.get("ISTORIJA_BUTTONCANCEL_TEXT"));
	    panel1.add(jLabel1, new XYConstraints(120, 30, -1, -1));
	    panel1.add(lDatOd, new XYConstraints(40, 121, -1, -1));
	    panel1.add(lBrojCl, new XYConstraints(40, 78, -1, 15));
	    panel1.add(lDatDo, new XYConstraints(40, 160, -1, 19));
	    panel1.add(tfBrojCl, new XYConstraints(164, 74, 85, 20));
	    panel1.add(tfDatOd, new XYConstraints(164, 117, 85, 20));
	    panel1.add(tfDatDo, new XYConstraints(164, 158, 85, 20));
	    panel1.add(buttonOK, new XYConstraints(106, 219, 85, 25));
	    panel1.add(buttonCancel, new XYConstraints(273, 217, -1, 25));

	    tfBrojCl.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_ENTER)
	          buttonOK.doClick();
	      }
	    });

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
	    String userid = DBConnection.toDBMember(tfBrojCl.getText().trim());
	    if (doDatuma.equals("")){
	    	doDatuma = odDatuma;
	    }else if (odDatuma.equals("")){
	    	odDatuma = doDatuma;
	    }
	    if (DBConnection.isValidDate(odDatuma)
	        && DBConnection.isValidDate(doDatuma)) {
	    if (proveri(userid)){
	      try {
	      	String qry = "select  ctlg_no, single_id, lend_date, return_date "
	            + " from lending "
	            + " where single_id = '" + userid
	            + "' and lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
	            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma)
	            + "'" + " order by lend_date";
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

		          out.write("<Zapis>");
		          out.newLine();
		          
		          Timestamp lend = rset.getTimestamp(3);
		          if (lend != null){
			          out.write("<Zaduzenje>" + DBConnection.fromDatabaseDate(lend.toString()) + "</Zaduzenje>");
		              out.newLine();
		          }else{
		          	  out.write("<Zaduzenje></Zaduzenje>");
		              out.newLine();
		          }
		          	
	              Timestamp returndate = rset.getTimestamp(4);
	              if (returndate != null){
	              	  out.write("<Razduzenje>" + DBConnection.fromDatabaseDate(returndate.toString()) + "</Razduzenje>");
		              out.newLine();
	              }else{
	              	  out.write("<Razduzenje></Razduzenje>");
		              out.newLine();
	              }
	              
	              out.write("<Ctlgno>" + rset.getString(1) + "</Ctlgno>");
	              out.newLine();
	              

		          String naslov = "";
		          String empty;
		          try {
		            naslov = rec.getField("200").getSubfield('a').getContent();
		            empty = naslov.substring(0, 1);
		          } catch (Exception e1) {
		            try {
		              naslov = rec.getField("200").getSubfield('c').getContent();
		              empty = naslov.substring(0, 1);
		            } catch (Exception e2) {
		              try {
		                naslov = rec.getField("200").getSubfield('d').getContent();
		                empty = naslov.substring(0, 1);
		              } catch (Exception e3) {
		                try {
		                  naslov = rec.getField("200").getSubfield('i').getContent();
		                } catch (Exception e4) {
	
		                }
		              }
		            }
		          }
		          out.write("<Naslov>" + naslov + "</Naslov>");
		          out.newLine();

		          String autor = "";
	
//		          try {
//		            autor = rec.getField("700").getSubfield('a').getContent() + " "
//		                + rec.getField("700").getSubfield('b').getContent();
//		            empty = autor.substring(0, 2);
//		          } catch (Exception e1) {
//		            try {
//		              autor = rec.getField("701").getSubfield('a').getContent() + " "
//		                  + rec.getField("701").getSubfield('b').getContent();
//		              empty = autor.substring(0, 2);
//		            } catch (Exception e2) {
//		              try {
//		                autor = rec.getField("710").getSubfield('a').getContent();
//		                empty = autor.substring(0, 1);
//		              } catch (Exception e3) {
//		                try {
//		                  autor = rec.getField("711").getSubfield('a').getContent();
//		                } catch (Exception e4) {
//	
//		                }
//		              }
//		            }
//		          }
		          try {
		            autor = rec.getField("200").getSubfield('f').getContent();
		          } catch (Exception e1) {
		          	
		          }
		          
		          out.write("<Autor>" + autor + "</Autor>");
		          out.newLine();    

		          out.write("</Zapis>");
		          out.newLine();
	
		          
	            }
	          }catch (Exception e1) {

	          }
	        }
	        
	        out.write("</Bisis>");
	        out.close();
	        
	        rset.close();
	        stmt.close();
	        
	  
	        
//	          File dir = new File("lib");
//	          File[] files = dir.listFiles(new FilenameFilter() {
//	             public boolean accept(File dir, String name) {
//	               return name.startsWith("jasper") && name.endsWith("jar");
//	             }
//	          });
//	          String classPath = "lib/"+files[0].getName() +
//	             System.getProperty("path.separator") + "circ.jar";
//	          System.setProperty("jasper.reports.compile.class.path", classPath);
	          
	          JasperReport subreport = (JasperReport)JRLoader.loadObject(
              Istorija.class.getResource(
                "/com/gint/app/bisis/circ/reports/istorija_sub.jasper").openStream());  	
//	          JasperReport subreport = JasperCompileManager.compileReport(Istorija.class.getResource(
//              		"/com/gint/app/bisis/circ/reports/istorija_sub.jrxml").openStream());
	          Document doc = XMLUtils.getDocumentFromString(sw.toString());
	          Map params = new HashMap(5);
	          params.put("begdate", odDatuma);
	          params.put("enddate", doDatuma);
	          params.put("brojclana", userid);
	          params.put("subreport", subreport);
	          params.put("doc", doc);
	          
	          
	          JasperPrint jp = JasperFillManager.fillReport(
              Istorija.class.getResource(
                "/com/gint/app/bisis/circ/reports/istorija.jasper").openStream(), 
                params, dbConn.getConnection());
//            JasperReport jr = JasperCompileManager.compileReport(Istorija.class.getResource(
//              		"/com/gint/app/bisis/circ/reports/istorija.jrxml").openStream());
//	          JasperPrint jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
	          JasperViewer.viewReport(jp, false);
	        

	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }
	    }else{
	    	JOptionPane.showMessageDialog(null, "Nepostojeci broj clana", "Broj clana",
	  	          JOptionPane.ERROR_MESSAGE);
	    }

	    } else {
	      JOptionPane.showMessageDialog(null, "Pogresan format datuma", "Datum",
	          JOptionPane.ERROR_MESSAGE);
	    }

	  }

	  public void clear() {
	    tfDatOd.setText("");
	    tfDatDo.setText("");
	    tfBrojCl.setText("");
	  }

	  private boolean proveri(String id){
	  	boolean vrati = false;
	  	try{
	  		String qry = "select * from users where id ='" + id + "'";
	  		Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        if (rset.next()){
	        	vrati = true;
	        }
	        
	  	}catch(Exception e){
	  		vrati = false;
	  	}
	  	return vrati;
	  }
	  
	  public void setVisible(boolean vis, String id){
	  	tfBrojCl.setText(DBConnection.fromDBMember(id));
	  	super.setVisible(vis);
	  }
}
