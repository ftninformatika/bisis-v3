//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ.reports;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import com.borland.jbcl.layout.*;
import com.gint.app.bisis.circ.*;
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.Messages;
// Knjiga upisa - dnevni izvestaj upisanih clanova sa bilansom

public class LendedHistory extends EscapeDialog {
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

  public LendedHistory(Frame frame, String title, boolean modal,DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public LendedHistory() {
    this(CircApplet.parent, "", false,null);
  }

  public LendedHistory(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.blue);
    lInvBroj.setText(Messages.get("LENDEDHISTORY_LINVBROJ_TEXT")+" ");
    lDatDo.setText(Messages.get("LENDEDHISTORY_LDATDO_TEXT"));
    getContentPane().add(panel1);

    lDatOd.setText(Messages.get("LENDEDHISTORY_LDATOD_TEXT"));
    jLabel1.setText(Messages.get("LENDEDHISTORY_JLABEL1_TEXT"));

    buttonOK.setText(Messages.get("LENDEDHISTORY_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("LENDEDHISTORY_BUTTONCANCEL_TEXT"));
    panel1.add(jLabel1, new XYConstraints(82, 16, -1, -1));
    panel1.add(lDatOd, new XYConstraints(40, 121, -1, -1));
    panel1.add(lInvBroj, new XYConstraints(40, 78, -1, 15));
    panel1.add(lDatDo, new XYConstraints(40, 160, -1, 19));
    panel1.add(tfInvBroj, new XYConstraints(164, 74, 108, 20));
    panel1.add(tfDatOd, new XYConstraints(164, 117, 85, 20));
    panel1.add(tfDatDo, new XYConstraints(164, 158, 85, 20));
    panel1.add(buttonOK, new XYConstraints(106, 219, 85, 25));
    panel1.add(buttonCancel, new XYConstraints(273, 217, -1, 25));

    tfInvBroj.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          buttonOK.doClick();
      }
    });

    tfDatOd.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          buttonOK.doClick();
      }
    });

    tfDatDo.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
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

    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        clear();
        setVisible(false);
      }
    });
  }

  void buttonOK_actionPerformed(ActionEvent e){
    String odDatuma = tfDatOd.getText();
    String doDatuma = tfDatDo.getText();
    if (doDatuma.equals("")){
    	doDatuma = odDatuma;
    }else if (odDatuma.equals("")){
    	odDatuma = doDatuma;
    }
    String ctlg_no = DBConnection.toDBInventory(tfInvBroj.getText());
    String ispisHTML = new String();
//    String chDatum="";
    String datum="";
    String autor="";
    String naslov="";

    double iznos=0, sIznos=0;
    int broj=0, sBroj=0, redBr=0;
    String mmbr;

    try {
      Extract.getPrefContents(ctlg_no,dbConn.getConnection(),true);
      int indexOfEnd = Extract.getAuthor().indexOf(";");
      if (indexOfEnd > 0)
        autor = Extract.getAuthor().substring(0,Extract.getAuthor().indexOf(";"));
      else
        autor = Extract.getAuthor();
      naslov = Extract.getTitle();

      Statement stmt = dbConn.getConnection().createStatement();
      Statement stmt1 = dbConn.getConnection().createStatement();
// Format za datum: ... sign_date between '01.02.2000' and '29.02.2000'";

      ispisHTML = "<CENTER><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("LENDEDHISTORY_ISPISHTML_1")+"\n</B><BR>";
      ispisHTML+=Messages.get("LENDEDHISTORY_ISPISHTML_2")+" "+odDatuma+" - "+doDatuma+"\n\n</FONT></CENTER><BR><BR>";
      ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("LENDEDHISTORY_ISPISHTML_3")+ctlg_no+"\n<BR>";
      ispisHTML+=Messages.get("LENDEDHISTORY_ISPISHTML_4")+" "+autor+"\n<BR>";
      ispisHTML+=Messages.get("LENDEDHISTORY_ISPISHTML_5")+" "+naslov+"\n<BR></FONT>";


      ispisHTML+="<TABLE BORDER=\"1\" CELLPADDING=\"1\" CELLSPACING=\"0\">";
      ispisHTML+="<TR><TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>R.br.</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("LENDEDHISTORY_ISPISHTML_6")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("LENDEDHISTORY_ISPISHTML_7")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("LENDEDHISTORY_ISPISHTML_8")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("LENDEDHISTORY_ISPISHTML_9")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("LENDEDHISTORY_ISPISHTML_10")+"\n</B></TH></TR>";


      ResultSet rset = stmt.executeQuery("select * from lending where ctlg_no='"+ctlg_no+"' and lend_date >='"+DBConnection.toDatabaseDate(odDatuma)+"' and lend_date<='"+DBConnection.toDatabaseDate(doDatuma)+"' order by lend_date");

      String ime="";
      String prezime="";

      while (rset.next()) {

        mmbr = rset.getString("SINGLE_ID");

        String upit="select first_name, last_name from single where user_id='"+mmbr+"'";
        ResultSet rset1 = stmt1.executeQuery(upit);

        ime="";
        prezime="";
        if (rset1.next()) {
          ime = rset1.getString("FIRST_NAME");
          prezime = rset1.getString("LAST_NAME");
        }
        rset1.close();

        datum = DBConnection.fromOracleDate(rset.getDate("LEND_DATE").toString());
/*        datum=chDatum.substring(8,10);
        datum+=".";
        datum+=chDatum.substring(5,7);
        datum+='.';
        datum+=chDatum.substring(0,4);*/

        redBr++;
        ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+redBr+"</TD>";
        ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+mmbr+"</TD>";
        ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+ime+" "+prezime+"</TD>";
        ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+datum+"</TD>";

        if (rset.getDate("RESUME_DATE") != null) {
          datum = DBConnection.fromOracleDate(rset.getDate("RESUME_DATE").toString());
/*          datum=chDatum.substring(8,10);
          datum+=".";
          datum+=chDatum.substring(5,7);
          datum+='.';
          datum+=chDatum.substring(0,4);*/
          ispisHTML+="<TD>"+datum+"</TD>";
        }
        else
          ispisHTML+="<TD>&nbsp;</TD>";

        if (rset.getDate("RETURN_DATE") != null) {
          datum = DBConnection.fromOracleDate(rset.getDate("RETURN_DATE").toString());
/*          datum=chDatum.substring(8,10);
          datum+=".";
          datum+=chDatum.substring(5,7);
          datum+='.';
          datum+=chDatum.substring(0,4);*/
          ispisHTML+="<TD>"+datum+"</TD>";
        }
        else
          ispisHTML+="<TD>&nbsp;</TD>";

        ispisHTML+="</TR>";
      }
      stmt1.close();
      rset.close();
      stmt.close();

      PrintingWindow rDlg = new PrintingWindow((Frame)null,Messages.get("LENDEDHISTORY_PRINTDLG_TITLE"), true);
      rDlg.setHTML(ispisHTML);
      rDlg.setVisible(true);
    }
    catch (SQLException ex) {
      System.err.println("GRESKA");
      System.err.println(ex);
      JOptionPane.showMessageDialog(null,"Gre\u0161ka: "+ex.getMessage(), "Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
    }
  }

  public void clear() {
    tfDatOd.setText("");
    tfDatDo.setText("");
    tfInvBroj.setText("");
  }
}

