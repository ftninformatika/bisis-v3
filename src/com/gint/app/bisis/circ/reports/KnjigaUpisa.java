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
import com.gint.app.bisis.circ.EscapeDialog;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.*;
// Knjiga upisa - dnevni izvestaj upisanih clanova sa bilansom

public class KnjigaUpisa extends EscapeDialog {
  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel label1 = new JLabel();
  JButton buttonOK = new JButton();
  JButton buttonCancel = new JButton();
  JTextField textFieldControl1 = new JTextField();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  private DataSet dsBranch = null, dsUsers = null;
  private JDBLookupModel cmBranch = null;
  JDBComboBox cboBranch;
  JLabel lSingleBranchID = new JLabel();

  public KnjigaUpisa(Frame frame, String title, boolean modal,DBConnection db) {
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


  public KnjigaUpisa() {
    this(CircApplet.parent, "", false,null);
  }

  public KnjigaUpisa(String title,DBConnection dbConn, boolean modal) {
    this(CircApplet.parent, title, modal,dbConn);
  }

  void jbInit() throws Exception {
    xYLayout1.setWidth(500);
    xYLayout1.setHeight(312);
    panel1.setLayout(xYLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setForeground(Color.red);
    getContentPane().add(panel1);

    label1.setText(Messages.get("KNJIGAUPISA_LABEL1_TEXT"));
    jLabel1.setText(Messages.get("KNJIGAUPISA_JLABEL1_TEXT"));
    jLabel2.setText(Messages.get("KNJIGAUPISA_JLABEL2_TEXT"));

    buttonOK.setText(Messages.get("KNJIGAUPISA_BUTTONOK_TEXT"));
    buttonCancel.setText(Messages.get("KNJIGAUPISA_BUTTONCANCEL_TEXT"));
    dsBranch = dbConn.getBranch();
    dsUsers = dbConn.getUsersModel();
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cboBranch = new JDBComboBox(cmBranch);
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));

    panel1.add(label1, new XYConstraints(37, 106, -1, -1));
    panel1.add(jLabel2, new XYConstraints(82, 43, -1, -1));
    panel1.add(jLabel1, new XYConstraints(168, 16, -1, -1));
    panel1.add(textFieldControl1, new XYConstraints(169, 103, 85, 20));
    panel1.add(lSingleBranchID, new XYConstraints(37, 150, 85, 20));
    panel1.add(cboBranch, new XYConstraints(169, 150, -1, -1));
    panel1.add(buttonOK, new XYConstraints(106, 200, 85, 25));
    panel1.add(buttonCancel, new XYConstraints(273, 200, -1, 25));

    textFieldControl1.addKeyListener(new KeyAdapter(){
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
    String ispisHTML = new String();
    int ogranak = Integer.parseInt(cmBranch.getData());
    double iznos=0, sIznos=0;
    int broj=0, sBroj=0, mmbr=0, redBr=0;

    try {
      Statement stmt = dbConn.getConnection().createStatement();
      Statement stmt1 = dbConn.getConnection().createStatement();
      Statement stmt2 = dbConn.getConnection().createStatement();
      Statement stmt3 = dbConn.getConnection().createStatement();

// Format za datum: ... sign_date between '01.02.2000' and '29.02.2000'";

      ispisHTML = "<CENTER><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_1")+"\n<BR>";
      ispisHTML+=Messages.get("KNJIGAUPISA_ISPISHTML_2")+" \n</B><BR>";
      ispisHTML+=Messages.get("KNJIGAUPISA_ISPISHTML_3")+odDatuma+"\n</FONT></CENTER><BR>";

      ispisHTML+="<TABLE BORDER=\"1\" CELLPADDING=\"1\" CELLSPACING=\"0\">";
      ispisHTML+="<TR><TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>R.br.</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_4")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_5")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_6")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_7")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_8")+"\n</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_9")+"\n</B></TH></TR>";

      ResultSet rset = stmt.executeQuery("select * from mmbr_types");

      while (rset.next()) {
        mmbr = rset.getInt("ID");
        String upit="select a.user_id, a.user_ctgr, a.mmbr_type, a.first_name, a.last_name, a.doc_no, a.jmbg, ";
        upit+=" b.id, b.address, b.zipcode, b.city from single a, ";
        upit+="(select id, address, zipcode, city from users where user_type=1 and branch_id = " + ogranak + " and ";
        upit+="sign_date='"+DBConnection.toDatabaseDate(odDatuma)+"' and id<>'0') b where a.mmbr_type="+mmbr;
        upit+=" and a.user_id=b.id";

        ResultSet rset1 = stmt1.executeQuery(upit);
        while (rset1.next()) {
          redBr++;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+redBr+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset1.getString("USER_ID")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset1.getString("FIRST_NAME")+" "+rset1.getString("LAST_NAME")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset1.getString("ADDRESS")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset1.getString("ZIPCODE")+" "+rset1.getString("CITY")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset1.getString("DOC_NO")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+rset1.getString("JMBG")+"</TD></TR>";
        }
        rset1.close();
      }
      rset.close();
//      stmt = dbConn.getConnection().createStatement();
      rset = stmt.executeQuery("select * from mmbr_types");

      ispisHTML+="</TABLE><BR><BR>";
      ispisHTML+="<TABLE BORDER=\"1\" CELLPADDING=\"1\" CELLSPACING=\"0\">";
      ispisHTML+="<TR><TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_10")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_11")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_12")+"\n</B></TH></TR>";
      ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><I>"+Messages.get("KNJIGAUPISA_ISPISHTML_13")+"\n</I></TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>";

      while (rset.next()) {
        mmbr = rset.getInt("ID");
        iznos=0;
        broj=0;
        String upit="select user_id, user_ctgr, mmbr_type from single where mmbr_type="+mmbr;
        upit+=" and user_id in (select id from users where user_type=1 and id<>'0' and branch_id = " + ogranak + " and sign_date='"+DBConnection.toDatabaseDate(odDatuma)+"')";
//        stmt1 = dbConn.getConnection().createStatement();
        ResultSet rset1 = stmt1.executeQuery(upit);
        while (rset1.next()) {
          String upit2="select sign_date from users where id='"+rset1.getString("USER_ID")+"'";
          ResultSet rset2 = stmt2.executeQuery(upit2);
          if (rset2.next()) {
//            String datum=DBConnection.fromOracleDate(rset2.getDate("SIGN_DATE").toString());
/*            String datum=new String();
            datum=sDatum.substring(8,10);
            datum+=".";
            datum+=sDatum.substring(5,7);
            datum+='.';
            datum+=sDatum.substring(0,4);*/

            String datum=rset2.getString("SIGN_DATE");
            String upit3="select cost from membership where user_ctgr="+rset1.getInt("USER_CTGR")+" and mmbr_type="+rset1.getInt("MMBR_TYPE")+" and mdate<='"+datum+"' order by mdate desc";

            ResultSet rset3 = stmt3.executeQuery(upit3);
            if (rset3.next()) {
               iznos+=rset3.getDouble("COST");
               broj+=1;
            }
            rset3.close();
          }
          else
            System.out.println("<KnjigaUpisa> Greska kod drugog ResultSet-a");
          rset2.close();
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
      ResultSet rset1=stmt1.executeQuery("select id from users where user_type=2 and sign_date='"+DBConnection.toDatabaseDate(odDatuma)+"' and branch_id =" + ogranak);
      broj=0;
      while (rset1.next())
        broj++;
      rset1.close();
      stmt.close();
      stmt1.close();
      stmt2.close();
      stmt3.close();
      sBroj+=broj;

      if (broj != 0) {
        ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><I>"+Messages.get("KNJIGAUPISA_ISPISHTML_1")+"</I></TD>";
        ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"\n</TD><TD>&nbsp;</TD></TR>";
      }
      ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("KNJIGAUPISA_ISPISHTML_2")+"</B></TD>";
      ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+sBroj+"</B></TD>";
      ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+sIznos+"\n</B></TD></TR></TABLE><BR>";

//      ReportDlg rDlg = new ReportDlg(null,"Pregled podataka",false);
      PrintingWindow rDlg = new PrintingWindow((Frame)null,Messages.get("KNJIGAUPISA_PRINTDLG_TITLE"), true);
      rDlg.setHTML(ispisHTML);
      rDlg.setVisible(true);
    }
    catch (SQLException ex) {
      System.err.println("GRESKA");
      System.err.println(ex);
      JOptionPane.showMessageDialog(null,Messages.get("KNJIGAUPISA_OPTIONPANE_MSG1")+" "+ex.getMessage(), Messages.get("KNJIGAUPISA_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
    }
  }

  public void clear() {
    textFieldControl1.setText("");
  }
}

