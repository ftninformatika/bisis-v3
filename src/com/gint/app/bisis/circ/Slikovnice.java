/*
 * Created on Jan 28, 2005
 *
 */
package com.gint.app.bisis.circ;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.app.bisis.circ.reports.Bibliotekar;

/**
 * @author Administrator
 *
 */
public class Slikovnice extends EscapeDialog {
	
	DBConnection db_conn;
	JLabel lDatum = new JLabel();
	JLabel lZaduzio = new JLabel();
	JLabel lRazduzio = new JLabel();
	JLabel tfDatum = new JLabel();
	JLabel lStanje = new JLabel();
	JLabel tfStanje = new JLabel();
	JLabel lUntil = new JLabel();
	JLabel tfUntil = new JLabel();
	JTextField tfZaduzio = new JTextField();
	JTextField tfRazduzio = new JTextField();
	JPanel panel1 = new JPanel();
	XYLayout xYLayout1 = new XYLayout();
    JLabel lMmbrID = new JLabel();
    JLabel tfMmbrID = new JLabel();
    JLabel lName = new JLabel("bla");
    JLabel tfName = new JLabel();
    JButton btnSave = new JButton();
    JButton btnExit = new JButton();
    JButton btnHistory = new JButton();
    String id = "";
    int izdato = 0;
    int vraceno = 0;
    int stanje = 0;
    int tmpi = 0;
    int tmpv = 0;
    boolean update = false;
	
	
	public Slikovnice(Frame frame, String title, boolean modal,DBConnection db) {
	    super(frame, title, modal);
	    db_conn = db;
	    try  {
	      jbInit();
	      pack();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  public Slikovnice() {
	    this(CircApplet.parent, "", false,null);
	  }

	  public Slikovnice(String title,DBConnection db_conn, boolean modal) {
	    this(CircApplet.parent, title, modal,db_conn);
	  }
	  
	  void jbInit() throws Exception {
	  	
	  	tfName.setForeground(Color.blue);
	  	tfDatum.setForeground(Color.blue);
	  	tfStanje.setForeground(Color.blue);
	  	tfMmbrID.setForeground(Color.blue);
	  	tfUntil.setForeground(Color.blue);
	  	lName.setText(Messages.get("SLIKOVNICE_LNAME_TEXT"));
	  	lDatum.setText(Messages.get("SLIKOVNICE_LDATUM_TEXT"));
	  	lUntil.setText(Messages.get("SLIKOVNICE_LUNTIL_TEXT"));
	  	lZaduzio.setText(Messages.get("SLIKOVNICE_LZADUZIO_TEXT"));
	  	lRazduzio.setText(Messages.get("SLIKOVNICE_LRAZDUZIO_TEXT"));
	  	lMmbrID.setText(Messages.get("SLIKOVNICE_LMMBRID_TEXT"));
	  	lStanje.setText(Messages.get("SLIKOVNICE_LSTANJE_TEXT"));
	  	btnSave.setText(Messages.get("SLIKOVNICE_BTNSAVE_TEXT"));
	    btnSave.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        btnSave_actionPerformed(e);
	      }
	    });
	    btnExit.setText(Messages.get("SLIKOVNICE_BTNEXIT_TEXT"));
	    btnExit.addActionListener(new java.awt.event.ActionListener() {
    	public void actionPerformed(ActionEvent e) {
            btnExit_actionPerformed(e);
          }
        });
	    btnHistory.setText(Messages.get("SLIKOVNICE_BTNHISTORY_TEXT"));
	    btnHistory.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(ActionEvent e) {
            btnHistory_actionPerformed(e);
          }
        });
	    xYLayout1.setWidth(320);
	    xYLayout1.setHeight(270);
	    panel1.setLayout(xYLayout1);
	    getContentPane().add(panel1);
	    
	    panel1.add(lDatum, new XYConstraints(16, 90, -1, 15));
	    panel1.add(tfDatum, new XYConstraints(100, 90, 67, -1));
	    panel1.add(lName, new XYConstraints(16, 31, -1, 15));
	    panel1.add(tfName, new XYConstraints(136, 31, -1, -1));
	    panel1.add(lZaduzio, new XYConstraints(16, 150, -1, 15));
	    panel1.add(tfZaduzio, new XYConstraints(100, 150, 60, 20));
	    panel1.add(lRazduzio, new XYConstraints(16, 180, -1, 15));
	    panel1.add(tfRazduzio, new XYConstraints(100, 180, 60, 20));
	    panel1.add(lStanje, new XYConstraints(16, 120, -1, 15));
	    panel1.add(tfStanje, new XYConstraints(100, 120, -1, -1));
	    panel1.add(lMmbrID, new XYConstraints(16, 10, -1, 15));
	    panel1.add(tfMmbrID, new XYConstraints(136, 10, -1, -1));
	    panel1.add(btnExit, new XYConstraints(200, 220, 85, 25));
	    panel1.add(btnSave, new XYConstraints(16, 220, 85, 25));
	    panel1.add(btnHistory, new XYConstraints(200, 170, 85, 25));
	    panel1.add(lUntil, new XYConstraints(16, 52, -1, -1));
	    panel1.add(tfUntil, new XYConstraints(136, 52, -1, -1));
	   

	  }
	  
	  void btnHistory_actionPerformed(ActionEvent e){
	  	try {

	        Map params = new HashMap(2);
	        params.put("id", id);
	        params.put("dbconn", db_conn);
	        
//	        File dir = new File("lib");
//	        File[] files = dir.listFiles(new FilenameFilter() {
//	           public boolean accept(File dir, String name) {
//	             return name.startsWith("jasper") && name.endsWith("jar");
//	           }
//	        });
//	        String classPath = "lib/"+files[0].getName() +
//	           System.getProperty("path.separator") + "circ.jar";
//	        System.setProperty("jasper.reports.compile.class.path", classPath);
	        
//	        JasperReport jr = JasperCompileManager.compileReport(Slikovnice.class
//		            .getResource("/com/gint/app/bisis/circ/slikovnice.jrxml")
//	                .openStream());
//	        JasperPrint jp = JasperFillManager.fillReport(jr, params, db_conn.getConnection());
	        JasperPrint jp = JasperFillManager.fillReport(
	                Bibliotekar.class.getResource(
	                  "/com/gint/app/bisis/circ/slikovnice.jasper").openStream(), 
	                  params, db_conn.getConnection());
	        JasperViewer.viewReport(jp, false);

	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }
	  }
	  
	  void btnExit_actionPerformed(ActionEvent e) {
	  	izdato = 0;
	  	vraceno = 0;
	  	stanje = 0;
	  	update = false;
	    this.setVisible(false);
	  }
	  
	  void btnSave_actionPerformed(ActionEvent e) {
	  	
	  	try{
	  		tmpi = Integer.parseInt(tfZaduzio.getText());
	  		tmpv = Integer.parseInt(tfRazduzio.getText());
	    	if (stanje + (tmpi - izdato) - (tmpv - vraceno) >= 0){
	    		stanje = stanje + (tmpi - izdato) - (tmpv - vraceno);
		    	izdato = tmpi;
		    	vraceno = tmpv;
		    	String query = "" ;
		    	if (update){
		    		query = "update SLIKOVNICE set IZDATO = "+izdato+", VRACENO = "
					+vraceno+", STANJE = "+stanje+", BIB_ID = '"+db_conn.getOfficial()+"'"
					+ " where USER_ID = '"+id+"' and DATUM = '"+Timestamp.valueOf(DBConnection.toDatabaseDate(DBConnection.getTodaysDate()))+"'";
		  	        System.out.println("<Slikovnice> Update:"+query);
		  	        update = false;
		    	}else{
		    		query = "insert into SLIKOVNICE values ('"+id+"', '"+Timestamp.valueOf(DBConnection.toDatabaseDate(DBConnection.getTodaysDate()))
					+"', "+izdato+", "+vraceno+", "+stanje+", '"+db_conn.getOfficial()+"')";
		  	        System.out.println("<Slikovnice> Insert:"+query);
		    	}
		    	try{
			  	      Statement stmt = db_conn.getConnection().createStatement();
			  	      int i = stmt.executeUpdate(query);
			  	      System.out.println(i);
			  	      stmt.close();
			  	      db_conn.getConnection().commit();
			  	      JOptionPane.showMessageDialog(null,Messages.get("SLIKOVNICE_OPTIONPANE_MSG3"),Messages.get("SLIKOVNICE_OPTIONPANE_MSG4"),JOptionPane.INFORMATION_MESSAGE);
			  	      izdato = 0;
			  	      vraceno = 0;
			  	      stanje = 0;
			  	      this.setVisible(false);
			  	      
		    	}catch(SQLException ex){
				      ex.printStackTrace();
			    }
	    	}else{
	    		JOptionPane.showMessageDialog(null,Messages.get("SLIKOVNICE_OPTIONPANE_MSG5"),Messages.get("SLIKOVNICE_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
	    	}
	    	
		    
		    
	  	}catch(NumberFormatException ex){
	  		JOptionPane.showMessageDialog(null,Messages.get("SLIKOVNICE_OPTIONPANE_MSG1"),Messages.get("SLIKOVNICE_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
	  	}
	    
	  }
	  
	  public void setVisible(boolean vis, String id){
	    setPosition(vis, id);
	  }
	  
	  private void setPosition(boolean vis, String id){
	  	try{
	
	  	this.id = id;
	  	String qry = "select IZDATO, VRACENO, STANJE, DATUM from SLIKOVNICE " +
	  			"where USER_ID = '" + id +"'" + " ORDER BY DATUM desc";
	  	Statement stmt = db_conn.getConnection().createStatement();
	  	ResultSet rset = stmt.executeQuery(qry);
	  	if (rset.next()){
	  		Timestamp dat = rset.getTimestamp(4);
	  		if (dat.equals(Timestamp.valueOf(DBConnection.toDatabaseDate(DBConnection.getTodaysDate())))){
	  			izdato = rset.getInt(1);
	  			vraceno = rset.getInt(2);
	  			stanje = rset.getInt(3);
	  			update = true;
	  		}else{
	  			stanje = rset.getInt(3);
	  		}
	  	}
	  	rset.close();
	  	rset = stmt.executeQuery("select FIRST_NAME, LAST_NAME from SINGLE where USER_ID = '"+ id +"'");
	  	rset.next();
	  	tfName.setText(rset.getString(2)+", "+rset.getString(1));
	  	rset.close();
	  	rset = stmt.executeQuery("select until_date from signing where single_id ='" + id + "' order by until_date desc");
	  	if(rset.next()){
	  		tfUntil.setText(DBConnection.formatDate(rset.getTimestamp(1)).toString());
	  	}else{
	  		tfUntil.setText(Messages.get("SLIKOVNICE_TFUNTIL_TEXT1"));
	  	}
	  	rset.close();
	  	stmt.close();
	  	db_conn.getConnection().commit();
	  	tfStanje.setText(new Integer(stanje).toString());
	  	tfZaduzio.setText(new Integer(izdato).toString());
	  	tfRazduzio.setText(new Integer(vraceno).toString());
	  	tfDatum.setText(DBConnection.getTodaysDate());
	  	tfMmbrID.setText(id);
	  	super.setVisible(vis);
	  	
	  	
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  		
	  }

}
