package com.gint.app.bisis.editor.report;

import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

import com.gint.app.bisis.editor.*;
import com.gint.util.string.StringUtils;
import com.gint.util.string.UnimarcConverter;


public class InventarFrame extends JFrame {
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
  public PrintingWindow reportDlg = new PrintingWindow(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_TITLE"), true);

  public InventarFrame(Connection conn) {
    this.conn = conn;
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    lIntType.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_LABELINVBOOKTYPE"));
    this.getContentPane().setLayout(xYLayout1);
    lOd.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_LABELFROM"));
    lDo.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_LABELTO"));
    bOK.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_BUTTONPRINT"));
    bOK.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(300);
    xYLayout1.setWidth(450);
    this.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_TITLE"));
    this.getContentPane().add(lOd, new XYConstraints(10, 125, -1, 30));
    this.getContentPane().add(lIntType, new XYConstraints(10, 57, -1, 54));
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

       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_MONPUB"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_SERPUB"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_CARTPUB"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_OLDRARE"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_MUS"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_VISPROJ"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_MOVIES"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_MICRO"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_VISGR"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_SNDGR"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_MAN"));
       cbIntType.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_BASIC_MEANS"));
  }




  void bOK_actionPerformed(ActionEvent e) {
    Statement stmt;
    ResultSet rset;
    Vector invNumbers = new Vector();
    Vector docIDs = new Vector();
    Vector docs = new Vector();
    InventarnaKnjiga ik = new InventarnaKnjiga(conn);


    try {
      stmt = conn.createStatement();
      // citanje odgovarajucih dokumenata iz baze na osnovu intervala inventarnih brojeva
      rset = stmt.executeQuery("SELECT c.content, c.doc_id, d.document FROM pref_IN c, Documents d"
          + " WHERE c.doc_id=d.doc_id AND c.content>='" + tfOd.getText().trim()+ "' AND c.content<='"+ tfDo.getText().trim()
          + "' ORDER BY c.content");
      while (rset.next()) {
        invNumbers.addElement(new String(rset.getString(1)));
        docIDs.addElement(new String(rset.getString(2)));
        if (Environment.getDBType().equals("oracle")) {
          docs.addElement(StringUtils.getStringLower(rset.getBytes(3)).trim());
        } else {
          docs.addElement(rset.getString(3));
        }
      }
      rset.close();
      stmt.close();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_DBERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
      System.err.println(ex);
    }

    String HTML = ik.getInvBook(cbIntType.getSelectedIndex(), invNumbers, docIDs, docs);
    this.reportDlg.setHTML(HTML);
    this.reportDlg.setVisible(true);

  }



/*  void bOK_actionPerformed(ActionEvent e) {
    Statement stmt;
    ResultSet rset;
    HashMap hmDocIDs = new HashMap();


    try {
      stmt = conn.createStatement();
      ((oracle.jdbc.driver.OracleStatement)stmt).defineColumnType(1, Types.INTEGER);
      ((oracle.jdbc.driver.OracleStatement)stmt).defineColumnType(2, Types.INTEGER);
      ((oracle.jdbc.driver.OracleStatement)stmt).defineColumnType(3, Types.LONGVARBINARY);
System.out.println(" poruka 3 ");
      rset = stmt.executeQuery("SELECT c.content, c.doc_id, d.document FROM pref_IN c, Documents d"
          + " WHERE c.doc_id=d.doc_id AND c.content>=" + tfOd.getText().trim()+ " AND c.content<="+ tfDo.getText().trim()
          + " ORDER BY c.content");
System.out.println(" poruka 4 ");
      while (rset.next()) {
        Integer key = new Integer(rset.getInt(1));   //content - inventarni broj je kljuc
        Vector value = new Vector();
        value.addElement(new Integer(rset.getInt(2)));
        value.addElement(Converter.getString(rset.getBytes(3)).trim());
        hmDocIDs.put(key,value);
System.out.println("" + key + "          " + value.elementAt(0));// + "   " + value.elementAt(1));
      }
      rset.close();
      stmt.close();
    }
    catch (SQLException ex) {
    }
  } */


/*  public String getCont000a(String currPubType) {
    String retVal = "";
System.out.println("Tip publikacije: "+currPubType);
    if (currPubType.equals("M1"))
       retVal = "00101";
    else if (currPubType.equals("M2"))
       retVal = "00201";
    else if (currPubType.equals("M3"))
       retVal = "00301";
    else if (currPubType.equals("S1"))
       retVal = "00401";
    else if (currPubType.equals("S2"))
       retVal = "00501";
    else if (currPubType.equals("A1"))
       retVal = "00601";
    else if (currPubType.equals("NO"))
       retVal = "00701";
    else if (currPubType.equals("C_"))
       retVal = "00801";
    else if (currPubType.equals("DS"))
       retVal = "00901";
    else if (currPubType.equals("R_"))
       retVal = "01001";
    else if (currPubType.equals("NK"))
       retVal = "01701";
    else if (currPubType.equals("NN"))
       retVal = "02701";
    else if (currPubType.equals("RD"))
       retVal = "03701";
    return retVal;
  }*/




  void bCancel_actionPerformed(ActionEvent e) {
     this.dispose();
  }

  String currIntType = com.gint.app.bisis.editor.Messages.get("BISISAPP_INVFRM_MONPUB");
  private UnimarcConverter conv = new UnimarcConverter();
  private Connection conn;


}