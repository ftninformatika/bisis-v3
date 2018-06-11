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
import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.DataSet;
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.JDBComboBox;
import com.gint.app.bisis.circ.JDBLookupModel;
import com.gint.app.bisis.circ.Messages;

// Periodicni izvestaj o broju i strukturi upisanih korisnika sa finansijskim pokazateljima

public class Report1 extends EscapeDialog {
  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JButton buttonOK = new JButton();
  JButton buttonCancel = new JButton();
  JTextField textFieldControl2 = new JTextField();
  JTextField textFieldControl1 = new JTextField();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  private DataSet dsBranch = null, dsUsers = null;
  private JDBLookupModel cmBranch = null;
  JDBComboBox cboBranch;
  JLabel lSingleBranchID = new JLabel();

  public Report1(Frame frame, String title, boolean modal,DBConnection db) {
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


  public Report1() {
    this(CircApplet.parent, "", false,null);
  }

  public Report1(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.red);
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel2.setForeground(Color.red);
    getContentPane().add(panel1);

    label1.setText(Messages.get("REPORT1_LABEL1_TEXT"));
    label2.setText(Messages.get("REPORT1_LABEL2_TEXT"));
    jLabel1.setText(" "+Messages.get("REPORT1_JLABEL1_TEXT"));
    jLabel2.setText(" "+Messages.get("REPORT1_JLABEL2_TEXT"));

    buttonOK.setText(Messages.get("REPORT1_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("REPORT1_BUTTONCANCEL_TEXT"));
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(cmBranch);
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));

    panel1.add(jLabel1, new XYConstraints(59, 14, 399, -1));
    panel1.add(jLabel2, new XYConstraints(130, 43, 212, -1));
    panel1.add(label1, new XYConstraints(40, 110, -1, -1));
    panel1.add(label2, new XYConstraints(40, 145, -1, 19));
    panel1.add(textFieldControl1, new XYConstraints(164, 107, 85, 20));
    panel1.add(textFieldControl2, new XYConstraints(164, 143, 85, 20));
    panel1.add(lSingleBranchID, new XYConstraints(40, 180, 85, 20));
    panel1.add(cboBranch, new XYConstraints(164, 180, -1, -1));
    panel1.add(buttonOK, new XYConstraints(106, 230, 85, 25));
    panel1.add(buttonCancel, new XYConstraints(273, 230, -1, 25));

    textFieldControl1.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          buttonOK.doClick();
      }
    });

    textFieldControl2.addKeyListener(new KeyAdapter(){
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
    String odDatuma = textFieldControl1.getText();
    String doDatuma = textFieldControl2.getText();
    if (doDatuma.equals("")){
    	doDatuma = odDatuma;
    }else if (odDatuma.equals("")){
    	odDatuma = doDatuma;
    }
    int ogranak = Integer.parseInt(cmBranch.getData());
    String ispisHTML = new String();
    double iznos=0, sIznos=0;
    int broj=0, sBroj=0, user_ctg=0; //mmbr=0;

    try {
      Statement stmt = dbConn.getConnection().createStatement();
      Statement stmt1 = dbConn.getConnection().createStatement();
      Statement stmt2 = dbConn.getConnection().createStatement();
      Statement stmt3 = dbConn.getConnection().createStatement();

// Format za datum: ... sign_date between '01.02.2000' and '29.02.2000'";

      ispisHTML = "<CENTER><FONT FACE=\"Arial, Helvetica\" SIZE=\"+0\"><B>"+Messages.get("REPORT1_ISPISHTML_1")+"\n<BR>";
      ispisHTML+=Messages.get("REPORT1_ISPISHTML_2")+"\n</B><BR>";
      ispisHTML+=Messages.get("REPORT1_ISPISHTML_3")+odDatuma+" "+Messages.get("REPORT1_ISPISHTML_4")+doDatuma+"\n</CENTER></FONT><BR>";
      ispisHTML+="<TABLE align=center BORDER=\"1\" CELLPADDING=\"3\" CELLSPACING=\"1\"><TR>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("REPORT1_ISPISHTML_5")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("REPORT1_ISPISHTML_6")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("REPORT1_ISPISHTML_7")+"\n</B></TH></TR>";
      ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><I>"+Messages.get("REPORT1_ISPISHTML_8")+"\n</I></TD>";
      ispisHTML+="<TD>&nbsp;</TD><TD>&nbsp;</TD></TR>";

//      ResultSet rset = stmt.executeQuery("select * from mmbr_types");
      ResultSet rset = stmt.executeQuery("select id, name from user_categs");

      while (rset.next()) {
//        mmbr = rset.getInt("ID");
        user_ctg = rset.getInt("ID");
        iznos=0;
        broj=0;
        String upit="select user_id, mmbr_type from single where user_ctgr="+user_ctg+" and user_id in (select id from users where user_type=1 and branch_id = " +ogranak +" and sign_date between '"+DBConnection.toDatabaseDate(odDatuma)+"' and '"+DBConnection.toDatabaseDate(doDatuma)+"')";
        ResultSet rset1 = stmt1.executeQuery(upit);
        while (rset1.next()) {
          String upit2="select sign_date from users where id='"+rset1.getString("USER_ID")+"'";
          ResultSet rset2 = stmt2.executeQuery(upit2);
          if (rset2.next()) {
            String datum=rset2.getString("SIGN_DATE");//.toString();
/*            String datum=new String();
            datum=sDatum.substring(8,10);
            datum+=".";
            datum+=sDatum.substring(5,7);
            datum+='.';
            datum+=sDatum.substring(0,4);*/
            rset2.close();

            String upit3="select cost from membership where user_ctgr="+user_ctg+" and mmbr_type="+rset1.getInt("MMBR_TYPE")+" and mdate<='"+DBConnection.toDatabaseDate(datum)+"' order by mdate desc";

            ResultSet rset3 = stmt3.executeQuery(upit3);
            if (rset3.next()) {
               iznos+=rset3.getDouble("COST");
               broj+=1;
            }
            rset3.close();
          }
          else {
            System.out.println("<Report1> Greska kod drugog ResultSet-a");
            rset2.close();
          }
        }
        rset1.close();
        if (broj != 0) {
          ispisHTML+="<TR><TD>&nbsp;&nbsp;<FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset.getString("NAME");
          ispisHTML+="</TD><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj;
          ispisHTML+="</TD><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+iznos+"</TD></TR>";
          sIznos+=iznos;
          sBroj+=broj;
        }
      }
      rset.close();
      stmt.close();
      stmt2.close();
      stmt3.close();

      ResultSet rset1=stmt1.executeQuery("select id from users where user_type=2 and branch_id = "+ogranak+" and sign_date between '"+DBConnection.toDatabaseDate(odDatuma)+"' and '"+DBConnection.toDatabaseDate(doDatuma)+"'");
      broj=0;
      while (rset1.next()) {
        if (!rset1.getString("ID").equals("0"))
          broj++;
      }

      rset1.close();
      stmt1.close();
      sBroj+=broj;

      if (broj != 0) {
        ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><I>"+Messages.get("REPORT1_ISPISHTML_9")+"</I></TD>";
        ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"\n</TD><TD>&nbsp;</TD></TR>";
      }
      ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("REPORT1_ISPISHTML_10")+"</B></TD>";
      ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+sBroj;
      ispisHTML+="</B></TD><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+sIznos+"\n</B></TD></TR></TABLE>";

//      ReportDlg rDlg = new ReportDlg(null,"Pregled podataka",false);
      PrintingWindow rDlg = new PrintingWindow((Frame)null, Messages.get("REPORT1_PRINDLG_TITLE"), true);
      rDlg.setHTML(ispisHTML);
      rDlg.setVisible(true);
    }
    catch (SQLException ex) {
      System.err.println("GRESKA");
      System.err.println(ex);
      JOptionPane.showMessageDialog(null,Messages.get("REPORT1_OPTIONSPANE_MSG1")+" "+ex.getMessage(), Messages.get("REPORT1_OPTIONSPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
    }
  }

  public void clear() {
    textFieldControl1.setText("");
    textFieldControl2.setText("");
  }
}

