package com.gint.app.bisis.editor.recordstats.ns;

import java.net.URL;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.ReportUtils;
import com.gint.app.bisis.editor.recordstats.ServerReport;
import com.gint.util.xml.XMLUtils;

public class NabavkaPoUDK1 extends ServerReport {

  public void execute() throws Exception {
    String url = Environment.getReportServlet() + 
        "/OfflineReport?reportFile=NabavkaPoUDKZaTekucuGodinu.xml";
    String xml = ReportUtils.getXmlReport(new URL(url));
    JRXmlDataSource dataSource = new JRXmlDataSource(
        XMLUtils.getDocumentFromString(xml), "/report/branch");

    report = JasperFillManager.fillReport(
        NabavkaPoUDK1.class.getResource(
          "/com/gint/app/bisis/editor/recordstats/ns/NabavkaPoUDK.jasper")
        .openStream(), params, dataSource);
  }
}
