/*
 * Created on Aug 16, 2006
 */
package com.gint.app.bisis.circ.reports;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.Messages;


public class MembersByGroup extends EscapeDialog{

	  DBConnection dbConn;
	  JPanel panel1 = new JPanel();
	  JPanel panel2 = new JPanel();
	  XYLayout xYLayout1 = new XYLayout();
	  XYLayout xYLayout2 = new XYLayout();
	  JLabel lDatOd = new JLabel();
	  JButton buttonOK = new JButton();
	  JButton buttonCancel = new JButton();
	  JLabel jLabel1 = new JLabel();
	  JLabel lBroj = new JLabel();
	  JTextField tfBroj = new JTextField();
	  JTextField tfDatOd = new JTextField();
	  JLabel lDatDo = new JLabel();
	  JTextField tfDatDo = new JTextField();

	  public MembersByGroup(Frame frame, String title, boolean modal, DBConnection db) {
	    super(frame, title, modal);
	    dbConn = db;
	    try {
	      jbInit();
	      pack();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public MembersByGroup() {
	    this(CircApplet.parent, "", false, null);
	  }

	  public MembersByGroup(String title, DBConnection dbConn, boolean modal) {
	    this(CircApplet.parent, title, modal, dbConn);
	  }

	  void jbInit() throws Exception {
	    xYLayout1.setWidth(500);
	    xYLayout1.setHeight(312);
	    panel1.setLayout(xYLayout1);
	    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
	    jLabel1.setForeground(Color.blue);
	    lBroj.setText(Messages.get("MEMBERSBYGROUP_BROJ_TEXT") + " ");
	    lDatDo.setText(Messages.get("MEMBERSBYGROUP_LDATDO_TEXT"));
	    getContentPane().add(panel1);

	    lDatOd.setText(Messages.get("MEMBERSBYGROUP_LDATOD_TEXT"));
	    jLabel1.setText(Messages.get("MEMBERSBYGROUP_JLABEL1_TEXT"));

	    buttonOK.setText(Messages.get("MEMBERSBYGROUP_BUTTONOK_TEXT"));
	    buttonCancel.setText(Messages.get("MEMBERSBYGROUP_BUTTONCANCEL_TEXT"));
	    panel1.add(jLabel1, new XYConstraints(100, 30, -1, -1));
	    panel1.add(lDatOd, new XYConstraints(40, 121, -1, -1));
	    panel1.add(lBroj, new XYConstraints(40, 78, -1, 15));
	    panel1.add(lDatDo, new XYConstraints(40, 160, -1, 19));
	    panel1.add(tfBroj, new XYConstraints(164, 74, 108, 20));
	    panel1.add(tfDatOd, new XYConstraints(164, 117, 85, 20));
	    panel1.add(tfDatDo, new XYConstraints(164, 158, 85, 20));
	    panel1.add(buttonOK, new XYConstraints(106, 219, 85, 25));
	    panel1.add(buttonCancel, new XYConstraints(273, 217, -1, 25));

	    tfBroj.addKeyListener(new KeyAdapter() {
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
	    String userid = DBConnection.toDBMember(tfBroj.getText());
	    if (doDatuma.equals("")){
	    	doDatuma = odDatuma;
	    }else if (odDatuma.equals("")){
	    	odDatuma = doDatuma;
	    }
	    if (DBConnection.isValidDate(odDatuma)
	        && DBConnection.isValidDate(doDatuma)) {
	    try {
	   
	    	String qry = "select inst_name from groups where user_id = '"+userid+"'";
	        Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        
	        if (rset.next()){
	        	String groupname = rset.getString(1);    
	          
//	          File dir = new File("lib");
//	          File[] files = dir.listFiles(new FilenameFilter() {
//	             public boolean accept(File dir, String name) {
//	               return name.startsWith("jasper") && name.endsWith("jar");
//	             }
//	          });
//	          String classPath = "lib/"+files[0].getName() +
//	             System.getProperty("path.separator") + "circ.jar";
//	          System.setProperty("jasper.reports.compile.class.path", classPath);
	   
	          
	          Map params = new HashMap(4);
	          params.put("begdate", java.sql.Timestamp.valueOf(DBConnection
	              .toDatabaseDate(odDatuma)));
	          params.put("enddate", java.sql.Timestamp.valueOf(DBConnection
	              .toDatabaseDate(doDatuma)));
	          params.put("group", userid);
	          params.put("groupname", groupname);

	          JasperPrint jp = JasperFillManager.fillReport(
	            Kartica.class.getResource(
	              "/com/gint/app/bisis/circ/reports/membersbygroup.jasper").openStream(), 
	            params, dbConn.getConnection());
//	          JasperReport jr = JasperCompileManager.compileReport(Kartica.class.getResource(
//	          			"/com/gint/app/bisis/circ/reports/membersbygroup.jrxml").openStream());
//	          JasperPrint jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
	          JasperViewer.viewReport(jp, false);
	        } else {
	          JOptionPane.showMessageDialog(null, "Ne postoji kolektivni clan",
	              "Kolektivni clan", JOptionPane.ERROR_MESSAGE);
	        }

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
	    tfBroj.setText("");
	  }
}
