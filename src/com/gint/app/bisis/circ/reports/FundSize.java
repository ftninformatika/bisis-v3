package com.gint.app.bisis.circ.reports;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import javax.swing.*;

import java.awt.Frame;
import java.sql.*;
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.event.*;
//import com.borland.jbcl.layout.*;
//import circ.EscapeDialog;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.Messages;

public class FundSize {
  DBConnection dbConn;

  public FundSize(DBConnection db) {
    this.dbConn=db;
    getReport();
  }


  private void getReport() {
    String ispisHTML = new String();
    int broj=0, suma=0;

    try {
      Statement stmt = dbConn.getConnection().createStatement();

// Format za datum: ... sign_date between '01.02.2000' and '29.02.2000'";

      ispisHTML = "<CENTER><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("FUNDSIZE_ISPISHTML_1")+"\n<BR>";
      ispisHTML+=Messages.get("FUNDSIZE_ISPISHTML_2")+"\n</B><BR>";


      ispisHTML+="<TABLE BORDER=\"1\" CELLPADDING=\"1\" CELLSPACING=\"0\">";
      ispisHTML+="<TR><TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("FUNDSIZE_ISPISHTML_3")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\"><B>"+Messages.get("FUNDSIZE_ISPISHTML_4")+"</B></TH></TR>";

      ResultSet rset = stmt.executeQuery("select count(*) from circ_docs where type='001'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_5")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='002'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_6")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='003'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_7")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='004'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_8")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='005'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_9")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='006'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_10")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='007'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_11")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='008'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_12")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='009'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_13")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='010'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_14")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='017'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_15")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='027'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_16")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs where type='037'");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (broj>0) {
          suma+=broj;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_17")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
        }
      }
      rset.close();

      rset = stmt.executeQuery("select count(*) from circ_docs");
      if (rset.next()) {
        broj=rset.getInt(1);

        if (suma != broj) {
          suma=broj-suma;

          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_18")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+suma+"</TD></TR>";
        }
        ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+Messages.get("FUNDSIZE_ISPISHTML_19")+"</TD>";
        ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"-1\">"+broj+"</TD></TR>";
      }
      rset.close();

      stmt.close();

      PrintingWindow rDlg = new PrintingWindow((Frame)null, Messages.get("FUNDSIZE_PRINTDLG_TITLE"), true);
      rDlg.setHTML(ispisHTML);
      rDlg.setVisible(true);
    }
    catch (SQLException ex) {
      System.err.println("GRESKA");
      System.err.println(ex);
      JOptionPane.showMessageDialog(null,Messages.get("FUNDSIZE_OPTIONPANE_MSG1")+" "+ex.getMessage(),Messages.get("FUNDSIZE_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
    }
  }
}