package com.gint.app.bisis.editor.report;

import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

import com.gint.app.bisis.editor.*;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.StringUtils;


public class BiltenPrinovljeneFrame extends JFrame {
  XYLayout xYLayout1 = new XYLayout();
  JLabel lIntType = new JLabel();
  JComboBox cbIntType = new JComboBox();
  JLabel lOd = new JLabel();
  JTextField tfOd = new JTextField();
  JLabel lDo = new JLabel();
  JTextField tfDo = new JTextField();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
//  public ReportDlg reportDlg = new ReportDlg(this, "Inventarska knjiga", true);
  public PrintingWindow reportDlg = new PrintingWindow(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_TITLE"), true);

  public BiltenPrinovljeneFrame(Connection conn) {
    this.conn = conn;
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    lIntType.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_LABELTYPE"));
    this.getContentPane().setLayout(xYLayout1);
    lOd.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_LABELFROM"));
    lDo.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_LABELTO"));
    bOK.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_BUTTONPRINT"));
    bOK.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(300);
    xYLayout1.setWidth(450);
    this.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_TITLE"));
    this.getContentPane().add(lOd, new XYConstraints(35, 125, 119, 30));
    this.getContentPane().add(lIntType, new XYConstraints(35, 57, 136, 54));
    this.getContentPane().add(cbIntType, new XYConstraints(180, 69, 245, -1));
    this.getContentPane().add(tfDo, new XYConstraints(338, 125, 85, 23));
    this.getContentPane().add(lDo, new XYConstraints(288, 125, 32, 23));
    this.getContentPane().add(tfOd, new XYConstraints(180, 125, 85, 23));
    this.getContentPane().add(bOK, new XYConstraints(100, 200, 100, 35));
    this.getContentPane().add(bCancel, new XYConstraints(250, 200, 100, 35));
    setSize(450, 300);

    // Izbaciti ovo - ucitavanje sa spiska monografsek, serijske, kartografska...
    Vector pubTypes = Environment.getLib().getPubTypeList();
    if (cbIntType.getItemCount() > 0)
      cbIntType.removeAllItems();


//    for (int i = 0; i < pubTypes.size(); i++)
//    cbIntType.addItem(pubTypes.elementAt(i));
//    cbIntType.setSelectedItem(currIntType);

       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_MONPUB"));
/*       cbIntType.addItem("Za serijske publikacije");
       cbIntType.addItem("Za kartografsku gra\u0111u");
       cbIntType.addItem("Za staru i retku knjigu");
       cbIntType.addItem("Za muzikalije");
       cbIntType.addItem("Za vizuelnu projekciju");
       cbIntType.addItem("Za filmove i video snimke");
       cbIntType.addItem("Za mikrooblik");
       cbIntType.addItem("Za vizuelnu gra\u0111u");
       cbIntType.addItem("Za zvu\u010dnu gra\u0111u");
       cbIntType.addItem("Za rukopisnu gra\u0111u");*/
  }




  void bOK_actionPerformed(ActionEvent e) {
    if (isValidDate(tfOd.getText().trim()) && isValidDate(tfDo.getText().trim())) {

      Statement stmt;
      ResultSet rset;

      Vector docIDs = new Vector();
      Vector docs = new Vector();
      BiltenPrinovljene bp = new BiltenPrinovljene(conn,tfOd.getText().trim(),tfDo.getText().trim());

      String datumOd= new String("");
      String datumDo= new String("");
      String datOd = tfOd.getText().trim();
      String datDo = tfDo.getText().trim();

      datumOd=datumZaUpit(datOd);
      datumDo=datumZaUpit(datDo);

      try {
        stmt = conn.createStatement();
      // citanje odgovarajucih dokumenata iz baze na osnovu datuma inventarisanja
        rset = stmt.executeQuery("SELECT c.content, c.doc_id, d.document FROM pref_DA c, Documents d"
            + " WHERE c.doc_id=d.doc_id AND c.content>='" + datumOd+ "' AND c.content<='"+ datumDo
            + "' ORDER BY c.content");

        while (rset.next()) {
          if (Environment.getDBType().equals("oracle")) {
            docIDs.addElement(new String(rset.getString(2)));
            docs.addElement(StringUtils.getStringLower(rset.getBytes(3)).trim());
          } else if (Environment.getDBType().equals("sapdb")) {
            docIDs.addElement(new String(rset.getString(2)));
            docs.addElement(rset.getString(3));
          } else {
          }
        }
        rset.close();
        stmt.close();
      }
      catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_DBERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        System.err.println(ex);
      }

      String HTML = bp.getBilten(cbIntType.getSelectedIndex(),docIDs, docs);

      this.reportDlg.setHTML(HTML);
      this.reportDlg.setVisible(true);
    }
    else
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_WRONGDATEFORMAT"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
  }

  void bCancel_actionPerformed(ActionEvent e) {
     this.dispose();
  }


  public boolean isValidDate(String stringDate){
    String dan = new String(stringDate.substring(0,2));
    String mesec = new String(stringDate.substring(3,5));
    String godina = new String(stringDate.substring(6,stringDate.length()));
    int xdrez;
    int idan=0, imesec=0, igodina=0;

    if (stringDate.length() != 10)
      return false;

    try {
      idan = (int)Integer.parseInt(dan);
      imesec = (int)Integer.parseInt(mesec);
      igodina = (int)Integer.parseInt(godina);
    }
    catch (NumberFormatException e) {
      System.out.println("<DBConnection>: isValidDate - " + com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_WRONGDATEFORMAT"));
      return false;
    }
    if (imesec==1||imesec==3||imesec==5||imesec==7||imesec==8||imesec==10||imesec==12) {
      if (idan>=1 && idan<=31);
      else
        return false;
    }
    else if(imesec==4||imesec==6||imesec==9||imesec==11) {
      if (idan>=1 && idan<=30);
      else
        return false;
    }
    else if (igodina%4==0 && imesec==2) {
      if (idan>=1 && idan <=29);
      else
        return false;
    }
    else if (imesec==2) {
      if (idan>=1 && idan<=28);
      else
        return false;
    }

    if ((igodina>=1970) && (igodina<=2100)) xdrez=0;
    else xdrez=1;

    if ((imesec>=1) && (imesec<=12));
    else xdrez=xdrez+1;

    if ((idan>=1) && (idan<=31));
    else xdrez=xdrez+1;

    if (xdrez==0)
      return true;
    else
      return false;
   }

  static String datumZaUpit(String datum) {
    String res="";
    String rez="";
    int tacka = 0;

    res=datum;
    while (res != null) {
      tacka = res.lastIndexOf('.');
      rez+=res.substring(tacka+1);
      if (tacka > 0)
        res=res.substring(0,tacka);
      else
        res=null;
    }
    return rez;
  }

  String currIntType = com.gint.app.bisis.editor.Messages.get("BISISAPP_BILTPRIN_MONPUB");
  private UnimarcConverter conv = new UnimarcConverter();
  private Connection conn;


}