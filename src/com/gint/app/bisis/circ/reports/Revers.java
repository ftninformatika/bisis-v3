//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ.reports;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.CoolDate;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.Extract;
import com.gint.app.bisis.circ.Messages;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;

// Stampanje reversa

public class Revers {
  DBConnection db_conn;

  public Revers(DBConnection db) {
    db_conn = db;
  }


  public void makeOne(String singleID, Vector invs, String datum, boolean two){
    String ispisHTML = new String();

    try {
    	
      Statement stmt = db_conn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery("select a.FIRST_NAME, a.LAST_NAME, b.PHONE, b.ADDRESS, b.CITY from SINGLE a, USERS b where b.ID ='"+singleID+"' and a.user_id=b.id");

      if (rset.next()) {
        ispisHTML+="<CENTER><FONT FACE=\"Arial, Helvetica\" SIZE=\"4\"><B>"+Messages.get("REVERS_ISPISHTML_2")+"\n<BR>";
        ispisHTML+=institution+"</B></FONT></CENTER>\n<BR><BR>\n";
        ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Messages.get("REVERS_ISPISHTML_3")+" ";
        ispisHTML+=datum+" "+Messages.get("REVERS_ISPISHTML_4_3")+" "+"\n<BR></FONT>\n";
        ispisHTML+="<HR width=\"100%\">";
      	for (int i=0; i<invs.size();i++){
      		Vector temp = (Vector)invs.get(i);
      		String inv = (String)temp.get(0);
            Extract.getPrefContents(inv, db_conn.getConnection(), true);
//            String sig = Extract.getSigNo();
            String title = Extract.getTitle();
            String author = Extract.getAuthor();
            ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>";
            ispisHTML+=Messages.get("REVERS_ISPISHTML_6")+" "+"</B>"+author+"<BR>\n";
            ispisHTML+="<B>"+Messages.get("REVERS_ISPISHTML_7")+" </B>"+title+"<BR>\n";
            ispisHTML+="<B>"+Messages.get("REVERS_ISPISHTML_9")+" </B>"+inv+"<BR>\n";
//            ispisHTML+="<B>"+Messages.get("REVERS_ISPISHTML_1")+" "+"</B>";
//            if (sig != null)
//              ispisHTML+=sig+"<BR>\n";
            
            int days = Extract.getPeriod(db_conn.getConnection(),singleID,(String)temp.get(3));
            String resume = (String)temp.get(2);
            String lend = (String)temp.get(1);
            String s = resume;
            if(s.equals("")) 
              s = lend;
            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            CoolDate cd = new CoolDate(DBConnection.StringToTime(s));
            CoolDate endDate = new CoolDate((cd.plus(days)).getTime());
            ispisHTML+="<B>"+Messages.get("REVERS_ISPISHTML_10")+" </B>"+lend+"\n";
            if (!resume.equals(""))
            	ispisHTML+="<B>"+Messages.get("REVERS_ISPISHTML_11")+" </B>"+resume+"\n";
            ispisHTML+="<B>"+Messages.get("REVERS_ISPISHTML_12")+" </B>"+DBConnection.formatDate(endDate)+"\n";
            
            ispisHTML+="<BR>\n</FONT>\n";
            ispisHTML+="<HR width=\"100%\">\n";
      	}   

        ispisHTML+="<BR><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("REVERS_ISPISHTML_8")+"</B><BR>\n";
        String prezime=rset.getString("LAST_NAME");
        String ime=rset.getString("FIRST_NAME");
        //String telefon=rset.getString("PHONE");
        String adresa=rset.getString("ADDRESS");
        String mesto=rset.getString("CITY");

        ispisHTML+=singleID+", ";
        if (prezime != null)
          ispisHTML+=prezime+" ";
        if (ime != null)
          ispisHTML+=ime+", ";
        //if (zanimanje != null)
          //ispisHTML+=zanimanje;
        //ispisHTML+="<BR>\n";
        //if (telefon != null)
          //ispisHTML+=telefon+", ";
        if (adresa != null)
          ispisHTML+=adresa+", ";
        if (mesto != null)
          ispisHTML+=mesto;

        ispisHTML+="<BR><BR>";
        ispisHTML+="<TABLE width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">__________________________</FONT></TD><TD align=\"right\"><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">bibliotekar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT></TD></TR>";
        ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Potpis korisnika</FONT></TD><TD align=\"right\"><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+db_conn.getOfficialName()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT></TD></TR></TABLE>";
      }
      rset.close();
      stmt.close();

//      ReportDlg rDlg = new ReportDlg(null,"Revers",false);
      PrintingWindow rDlg = new PrintingWindow(CircApplet.parent,Messages.get("REVERS_PRINTDLG_TITLE"), true);
      if (two){
      	rDlg.setHTML(ispisHTML + "<BR>" + ispisHTML);
      } else {
      	rDlg.setHTML(ispisHTML);
      }
      rDlg.setVisible(true);
    }
    catch (SQLException ex) {
      System.err.println("GRESKA");
      System.err.println(ex);
      JOptionPane.showMessageDialog(null,Messages.get("REVERS_OPTIONPANE_MSG1")+" "+ex.getMessage(),Messages.get("REVERS_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private static String institution = "";
  
  static {
    INIFile iniFile = new INIFile(
        FileUtils.getClassDir(Revers.class) + "/config.ini"); 
    institution = iniFile.getString("circ", "libraryName");
    if (institution == null)
      institution = "Biblioteka";
  }

}

