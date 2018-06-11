package com.gint.app.bisis.editor.recordstats.ns;

import java.net.URL;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.ReportUtils;
import com.gint.app.bisis.editor.recordstats.ServerReport;
import com.gint.util.xml.XMLUtils;

/**
 *
 * @author branko
 */
public class BrojZapisa extends ServerReport {
  
  public void execute() throws Exception {
    String url = Environment.getReportServlet() + 
        "/OfflineReport?reportFile=BrojZapisa.xml";
    String xml = ReportUtils.getXmlReport(new URL(url));
    JRXmlDataSource dataSource = new JRXmlDataSource(
        XMLUtils.getDocumentFromString(xml), "/report");


    report = JasperFillManager.fillReport(
        BrojPrimeraka.class.getResource(
          "/com/gint/app/bisis/editor/recordstats/ns/BrojZapisa.jasper")
        .openStream(), params, dataSource);
  }
}
