package com.gint.app.bisis.editor.recordstats.ns;

import java.net.URL;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.ReportUtils;
import com.gint.app.bisis.editor.recordstats.ServerReport;
import com.gint.util.xml.XMLUtils;

/**
 *
 * @author branko
 */
public class BrojUradjenihZapisa extends ServerReport {
  
  public void execute() throws Exception {
    String url = Environment.getReportServlet() + 
        "/OfflineReport?reportFile=BrojUradjenihZapisa.xml";
    String xml = ReportUtils.getXmlReport(new URL(url));
    JRXmlDataSource dataSource = new JRXmlDataSource(
        XMLUtils.getDocumentFromString(xml), "/report/librarian");

    JasperReport subreport = (JasperReport)JRLoader.loadObject(
        BrojUradjenihZapisa.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/ns/BrojUradjenihZapisa_part.jasper")
        .openStream());
    params.put("subreport", subreport);

    report = JasperFillManager.fillReport(
        BrojPrimeraka.class.getResource(
          "/com/gint/app/bisis/editor/recordstats/ns/BrojUradjenihZapisa.jasper")
        .openStream(), params, dataSource);

  }
}
