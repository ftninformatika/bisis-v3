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
public class Najcitanije extends EscapeDialog {

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

  public Najcitanije(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Najcitanije() {
    this(CircApplet.parent, "", false, null);
  }

  public Najcitanije(String title, DBConnection dbConn, boolean modal) {
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
    jLabel1.setText(Messages.get("NAJCITANIJE_JLABEL1_TEXT"));

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
      	
        String qry = "select  ctlg_no, count(ctlg_no)  from lending "
            + "where lend_date >= '" + DBConnection.toDatabaseDate(odDatuma)
            + "' and lend_date <= '" + DBConnection.toDatabaseDate(doDatuma) + "'"
            + qryogr
			+ " group by ctlg_no order by count(ctlg_no) desc"; 

        Statement stmt = dbConn.getConnection().createStatement();
        Statement stmt1 = dbConn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        ResultSet rset1;
        
        //pravi listu inventarnih brojeva
        List ls = new ArrayList();
        List doc = new ArrayList(); // docid za odgovarajuci inventarni broj u ls
        List cit = new ArrayList(); // broj iznajmljivanja za odgovarajuci
                                    // inventarni broj u ls
        							
        while (rset.next()) {
          ls.add(rset.getString(1));
          cit.add(new Integer(rset.getInt(2)));
          qry = "select docid from circ_docs where ctlg_no = '" + rset.getString(1) + "'";
          rset1 = stmt1.executeQuery(qry);
          if (rset1.next()){;
          	doc.add(new Integer(rset1.getInt(1)));
          }else{
          	doc.add(null);
          }
          rset1.close();
        }
        stmt1.close();
        rset.close();
        stmt.close();       

        //iz liste izbacuje duple zapise i sabira broj iznajmljivanja za
        // inventarne brojeve koji predstavljaju jedan naslov
        int i = 0;
        Integer docc;
        while (i < ls.size()) {
          docc = (Integer) doc.get(i);
          if (docc != null) {
            int docid = docc.intValue();
            int j = i + 1;
            Integer tmp;
            while (j < doc.size()) {
              tmp = (Integer) doc.get(j);
              if (tmp != null) {
                if (tmp.intValue() == docid) {
                  cit.set(i, new Integer(((Integer) cit.get(i)).intValue() + ((Integer) cit.get(j)).intValue()));
                  cit.remove(j);
                  doc.remove(j);
                  ls.remove(j);
                } else {
                  j++;
                }
              } else {
                doc.remove(j);
                cit.remove(j);
                ls.remove(j);
              }
            }
            i++;
          } else {
            cit.remove(i);
            doc.remove(i);
            ls.remove(i);
          }
        }
       
        //sortira listu zapisa po citanosti
        int kraj = 1;
        Integer tmp1;
        String tmp2;
        while (kraj < cit.size()) {
          if (((Integer) cit.get(kraj)).compareTo((Integer) cit.get(kraj - 1)) <= 0) {
            kraj++;
          } else {
            int mesto = 0;
            boolean nasao = false;
            while (mesto <= kraj - 1 && !nasao) {
              if (((Integer) cit.get(kraj))
                  .compareTo(((Integer) cit.get(mesto))) > 0) {
                nasao = true;
              } else {
                mesto++;
              }
            }
            tmp1 = (Integer) cit.remove(kraj);
            cit.add(mesto, tmp1);
            tmp2 = (String) ls.remove(kraj);
            ls.add(mesto, tmp2);
          }
        }       
        
        
//      dobija zapise za listu inventarnih brojeva
        if (ls.size() > 50)
        	ls = ls.subList(0,50);
        List recs = InvNumUtils.getRecords(ls, dbConn.getConnection());

        

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
        
        JRXmlDataSource ds = new JRXmlDataSource(recordsToXML(recs, ls, cit),
          "/Bisis/Zapis");

        JasperPrint jp = JasperFillManager.fillReport(
            Najcitanije.class.getResource(
                "/com/gint/app/bisis/circ/reports/najcitanije.jasper").openStream(), 
                params, ds);

//        JasperReport jr = JasperCompileManager.compileReport(Najcitanije.class
//            .getResource("/com/gint/app/bisis/circ/reports/najcitanije.jrxml")
//            .openStream());
//        JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
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

  public static Document recordsToXML(List recs, List ls, List cit) {

    StringWriter sw = new StringWriter();
    try {
      BufferedWriter out = new BufferedWriter(sw);
      out.write("<?xml version=\"1.0\"?>");
      out.newLine();
      out.write("<Bisis>");
      out.newLine();

      Iterator it = recs.iterator();
      int i = 1;
      while (it.hasNext() && i <= 20) {
        Record rec = (Record) it.next();
        if (rec != null){
          i++;
          int indx = recs.indexOf(rec);
          String ctlg = (String) ls.get(indx);
          out.write("<Zapis>");
          out.newLine();

          out.write("<Ctlg>" + ctlg + "</Ctlg>");
          out.newLine();
          
          out.write("<Iznajmljivanje>" + (Integer)cit.get(indx) + "</Iznajmljivanje>");
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

//          try {
//            autor = rec.getField("700").getSubfield('a').getContent() + " "
//                + rec.getField("700").getSubfield('b').getContent();
//            empty = autor.substring(0, 2);
//          } catch (Exception e1) {
//            try {
//              autor = rec.getField("701").getSubfield('a').getContent() + " "
//                  + rec.getField("701").getSubfield('b').getContent();
//              empty = autor.substring(0, 2);
//            } catch (Exception e2) {
//              try {
//                autor = rec.getField("710").getSubfield('a').getContent();
//                empty = autor.substring(0, 1);
//              } catch (Exception e3) {
//                try {
//                  autor = rec.getField("711").getSubfield('a').getContent();
//                } catch (Exception e4) {
//
//                }
//              }
//            }
//          }
          try {
            autor = rec.getField("200").getSubfield('f').getContent();
          } catch (Exception e1) {
          	
          }
          
          out.write("<Autor>" + autor + "</Autor>");
          out.newLine();

          out.write("</Zapis>");
          out.newLine();
        } 
      }
      out.write("</Bisis>");
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return XMLUtils.getDocumentFromString(sw.toString());
  }

  public static void main(String[] args) {
  }
}