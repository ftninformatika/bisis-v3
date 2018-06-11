package com.gint.app.bisis.editor.recordstats.ns;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.ReportUtils;
import com.gint.app.bisis.editor.recordstats.ServerReport;
import com.gint.util.xml.XMLUtils;

public class NabavkaPoNacinu2 extends ServerReport {

  public void execute() throws Exception {
    Date startDate = (Date)params.get("startDate");
    Date endDate = (Date)params.get("endDate");
    
    String url = Environment.getReportServlet() + 
        "/OnlineReport?onlineReport=NabavkaPoNacinuZaPeriod.xml" +
        "&startDate=" + intern.format(startDate) + 
        "&endDate=" + intern.format(endDate);
    String xml = ReportUtils.getXmlReport(new URL(url));
    JRXmlDataSource dataSource = new JRXmlDataSource(
        XMLUtils.getDocumentFromString(xml), "/report/branch");

    report = JasperFillManager.fillReport(
        BrojPrimeraka.class.getResource(
          "/com/gint/app/bisis/editor/recordstats/ns/NabavkaPoNacinu.jasper")
        .openStream(), params, dataSource);
  }

  private SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
}