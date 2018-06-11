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
import java.io.File;
import java.io.FilenameFilter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.Document;

//import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.Messages;
import com.gint.app.bisis.circ.NewMember;
import com.gint.app.bisis4.common.records.Record;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;
import com.gint.util.xml.XMLUtils;

/**
 * @author Administrator
 *  
 */
public class Kartica extends EscapeDialog {

  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel lDatOd = new JLabel();
  JButton buttonOK = new JButton();
  JButton buttonCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel lInvBroj = new JLabel();
  JTextField tfInvBroj = new JTextField();
  JTextField tfDatOd = new JTextField();
  JLabel lDatDo = new JLabel();
  JTextField tfDatDo = new JTextField();
  int neobradjene = 0;

  public Kartica(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Kartica() {
    this(CircApplet.parent, "", false, null);
  }

  public Kartica(String title, DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal, dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.blue);
    lInvBroj.setText(Messages.get("KARTICA_LUDKBROJ_TEXT") + " ");
    lDatDo.setText(Messages.get("KARTICA_LDATDO_TEXT"));
    getContentPane().add(panel1);

    lDatOd.setText(Messages.get("KARTICA_LDATOD_TEXT"));
    jLabel1.setText(Messages.get("KARTICA_JLABEL1_TEXT"));

    buttonOK.setText(Messages.get("KARTICA_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("KARTICA_BUTTONCANCEL_TEXT"));
    panel1.add(jLabel1, new XYConstraints(150, 30, -1, -1));
    panel1.add(lDatOd, new XYConstraints(40, 121, -1, -1));
    panel1.add(lInvBroj, new XYConstraints(40, 78, -1, 15));
    panel1.add(lDatDo, new XYConstraints(40, 160, -1, 19));
    panel1.add(tfInvBroj, new XYConstraints(164, 74, 108, 20));
    panel1.add(tfDatOd, new XYConstraints(164, 117, 85, 20));
    panel1.add(tfDatDo, new XYConstraints(164, 158, 85, 20));
    panel1.add(buttonOK, new XYConstraints(106, 219, 85, 25));
    panel1.add(buttonCancel, new XYConstraints(273, 217, -1, 25));
    
    INIFile iniFile = new INIFile(
            FileUtils.getClassDir(NewMember.class) + "/config.ini"); 
  	neobradjene = Integer.parseInt(iniFile.getString("circ", "neobradjene"));

    tfInvBroj.addKeyListener(new KeyAdapter() {
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
    String ctlg = DBConnection.toDBInventory(tfInvBroj.getText());
    if (doDatuma.equals("")){
    	doDatuma = odDatuma;
    }else if (odDatuma.equals("")){
    	odDatuma = doDatuma;
    }
    if (DBConnection.isValidDate(odDatuma)
        && DBConnection.isValidDate(doDatuma)) {
      try {
        List ls = new ArrayList();
        ls.add(ctlg);
        List recs = InvNumUtils.getRecords(ls, dbConn.getConnection());
        Record rec = (Record) recs.get(0);

        if (rec != null || neobradjene == 1) {
          StringWriter sw = new StringWriter();
          BufferedWriter out = new BufferedWriter(sw);
          out.write("<?xml version=\"1.0\"?>");
          out.newLine();
          out.write("<Bisis>");
          out.newLine();

          out.write("<Zapis>");
          out.newLine();

          out.write("<Ctlg>" + ctlg + "</Ctlg>");
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

          String izdavac = "";
          try {
            izdavac = rec.getField("210").getSubfield('c').getContent();
          } catch (Exception e1) {

          }
          out.write("<Izdavac>" + izdavac + "</Izdavac>");
          out.newLine();

          String mesto = "";
          try {
            mesto = rec.getField("210").getSubfield('a').getContent();
          } catch (Exception e1) {

          }
          out.write("<Mesto>" + mesto + "</Mesto>");
          out.newLine();

          String godina = "";
          try {
            godina = rec.getField("100").getSubfield('c').getContent();
            empty = godina.substring(0, 1);
          } catch (Exception e1) {
            try {
              godina = rec.getField("210").getSubfield('d').getContent();
            } catch (Exception e2) {

            }
          }
          out.write("<Godina>" + godina + "</Godina>");
          out.newLine();

          String signatura = "";
          try {
            signatura = rec.getField("996").getSubfield('d')
                .getSubsubfield('l').getContent();
            try {
              signatura += rec.getField("996").getSubfield('d').getSubsubfield(
                  'n').getContent();
            } catch (Exception e1) {

            }
          } catch (Exception e2) {

          }
          out.write("<Signatura>" + signatura + "</Signatura>");
          out.newLine();

          out.write("</Zapis>");
          out.newLine();

          out.write("</Bisis>");
          out.close();
          
          File dir = new File("lib");
          File[] files = dir.listFiles(new FilenameFilter() {
             public boolean accept(File dir, String name) {
               return name.startsWith("jasper") && name.endsWith("jar");
             }
          });
          String classPath = "lib/"+files[0].getName() +
             System.getProperty("path.separator") + "circ.jar";
          System.setProperty("jasper.reports.compile.class.path", classPath);
          
          JasperReport subreport = (JasperReport)JRLoader.loadObject(
            Kartica.class.getResource(
              "/com/gint/app/bisis/circ/reports/kartica_sub.jasper").openStream());  	
//          JasperReport subreport = JasperCompileManager.compileReport(Kartica.class.getResource(
//            "/com/gint/app/bisis/circ/reports/kartica_sub.jrxml").openStream());
          Document doc = XMLUtils.getDocumentFromString(sw.toString());
          Map params = new HashMap(5);
          params.put("begdate", java.sql.Timestamp.valueOf(DBConnection
              .toDatabaseDate(odDatuma)));
          params.put("enddate", java.sql.Timestamp.valueOf(DBConnection
              .toDatabaseDate(doDatuma)));
          params.put("ctlg", ctlg);
          params.put("subreport", subreport);
          params.put("doc", doc);   

          JasperPrint jp = JasperFillManager.fillReport(
            Kartica.class.getResource(
              "/com/gint/app/bisis/circ/reports/kartica.jasper").openStream(), 
            params, dbConn.getConnection());
//          JasperReport jr = JasperCompileManager.compileReport(Kartica.class.getResource(
//          			"/com/gint/app/bisis/circ/reports/kartica.jrxml").openStream());
//          JasperPrint jp = JasperFillManager.fillReport(jr, params, dbConn.getConnection());
          JasperViewer.viewReport(jp, false);
        } else {
          JOptionPane.showMessageDialog(null, "Ne postoji inventarni broj",
              "Inventarni broj", JOptionPane.ERROR_MESSAGE);
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
    tfInvBroj.setText("");
  }

}