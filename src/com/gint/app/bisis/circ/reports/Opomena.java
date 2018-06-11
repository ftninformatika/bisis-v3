//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ.reports;

import javax.swing.*;
import java.sql.*;

import com.gint.app.bisis.circ.CircApplet;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.Extract;
import com.gint.app.bisis.circ.CoolDate;
import com.gint.app.bisis.circ.Messages;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;

// Stampanje opomena

public class Opomena {
  DBConnection db_conn;

  public Opomena(DBConnection db) {
    db_conn = db;
  }


  public void makeOne(String singleID){
    String datum=new String();
    String ispisHTML = new String();
    String autor = new String();
    int days = 0, rbr=0;

    long prvi, drugi;

    java.util.Date now = new java.util.Date(System.currentTimeMillis());
//    CoolDate coolNow = new CoolDate(now.getTime());

    try {
      Statement stmt = db_conn.getConnection().createStatement();

// Format za datum: ... sign_date between '01.02.2000' and '29.02.2000'";

      ispisHTML = "<FONT FACE=\"Arial, Helvetica\" SIZE=\"4\"><B>"+Messages.get("OPOMENA_ISPISHTML_1")+"</FONT><BR>";
      ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+institution+"</B><BR>";
//      ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Messages.get("OPOMENA_ISPISHTML_2")+"</B><BR>";
//      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_3")+"<BR>";
//      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_4");
      ispisHTML+="\n</FONT><BR><BR>";

      ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Messages.get("OPOMENA_ISPISHTML_5")+"<BR><BR></FONT>";
      ispisHTML+="<TABLE BORDER=\"1\" CELLPADDING=\"1\" CELLSPACING=\"0\"><TR>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_6")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_7")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_8")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_9")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_10")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_11")+"</B></TH>";
      ispisHTML+="<TH><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\"><B>"+Messages.get("OPOMENA_ISPISHTML_12")+"</B></TH></TR>";

      ResultSet rset = stmt.executeQuery("select * from LENDING where SINGLE_ID ='"+singleID+"' AND RETURN_DATE is null");

      while (rset.next()) {
        Extract.getPrefContents((String)rset.getString("CTLG_NO"),db_conn.getConnection(),true);
        days = Extract.getPeriod(db_conn.getConnection(),singleID,rset.getString("LEND_TYPE"));
        if (rset.getDate("RESUME_DATE") == null)
            datum = DBConnection.fromDatabaseDate(rset.getDate("LEND_DATE").toString());
         else
          datum = DBConnection.fromDatabaseDate(rset.getDate("RESUME_DATE").toString());

/*        datum=chDatum.substring(8,10);
        datum+=".";
        datum+=chDatum.substring(5,7);
        datum+='.';
        datum+=chDatum.substring(0,4);*/


        CoolDate cd = new CoolDate(DBConnection.StringToTime(datum));
        CoolDate endDate = new CoolDate((cd.plus(days)).getTime());

        if(now.after(endDate)){
          int razlika = endDate.minus(new CoolDate(now.getTime()));

          int indexOfEnd = Extract.getAuthor().indexOf(";");
          if (indexOfEnd > 0)
            autor = Extract.getAuthor().substring(0,Extract.getAuthor().indexOf(";"));
          else
            autor = Extract.getAuthor();

          rbr++;
          ispisHTML+="<TR><TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+rbr+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Extract.getTitle()+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+autor+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+rset.getString("CTLG_NO")+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Extract.getSigNo()+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+datum+"</TD>";
          ispisHTML+="<TD><FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+razlika+"</TD></TR>";
        }

      }
      rset.close();
      ispisHTML+="</TABLE><BR>";
      ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Messages.get("OPOMENA_ISPISHTML_13")+" ";
      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_14")+" ";
      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_15")+"<BR><BR>";
      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_16")+"<BR>";
      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_17")+"<BR>";
      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_18")+"<BR>";
      ispisHTML+=Messages.get("OPOMENA_ISPISHTML_19")+" "+DBConnection.getTodaysDate()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_____________________<BR><BR><BR></FONT>";

//      stmt = db_conn.getConnection().createStatement();
      rset = stmt.executeQuery("select a.first_name, a.last_name, b.address, b.zipcode, b.city from single a, users b where a.user_id='"+singleID+"' and a.user_id=b.id");

      if (rset.next()) {
        ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"2\">"+Messages.get("OPOMENA_ISPISHTML_20")+" "+singleID+"<BR><B>";
        ispisHTML+=rset.getString("FIRST_NAME")+" "+rset.getString("LAST_NAME")+"<BR>";
        ispisHTML+=rset.getString("ADDRESS")+"<BR>";
        ispisHTML+=rset.getInt("ZIPCODE")+" "+rset.getString("CITY")+"</B><BR></FONT>";
      }
      rset.close();
      stmt.close();

//      ReportDlg rDlg = new ReportDlg(null,"Opomena",false);
      PrintingWindow rDlg = new PrintingWindow(CircApplet.parent, Messages.get("OPOMENA_PRINTINGDLG_TITLE"), true);
      rDlg.setHTML(ispisHTML);
      rDlg.setVisible(true);
    }
    catch (SQLException ex) {
      System.err.println("GRESKA");
      System.err.println(ex);
      JOptionPane.showMessageDialog(null, Messages.get("OPOMENA_OPTIONSPANE_MSG1")+ex.getMessage(),Messages.get("OPOMENA_OPTIONSPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
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

