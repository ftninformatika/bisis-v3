package com.gint.app.bisis.editor.recordstats.ns;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.comlin.BranchesCommand;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis.textsrv.TextServerPackedDoc;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.util.xml.XMLUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class PogociPoOgrancima extends RecordReport {
  
  public void execute() {
    MainFrame mf = (MainFrame)params.get("MainFrame");
    if (mf == null) {
      System.err.println("MainFrame not set");
      rp.setFinished(true);
      return;
    }
    int hitCount = Environment.getReadTs().getHitCount();
    rp.setMaximum(hitCount);
    rp.setValue(0);
    Vector v = Environment.getReadTs().getDocsRangePacked(1, hitCount);
    if (v.size() != 0) {
      HashMap branchMap = new HashMap();
      for (int i = 0; i < v.size(); i++) {
        if (rp.isCancelled())
          return;
        TextServerPackedDoc p = (TextServerPackedDoc)v.elementAt(i);
        Record rec = RecordFactory.fromUNIMARC(p.doc);
        process(rec, branchMap);
        rp.setValue(i + 1);
      }
      
      StringBuffer buff = new StringBuffer();
      buff.append("<report>\n");
      List keys = new ArrayList();
      keys.addAll(branchMap.keySet());
      Collections.sort(keys);
      Iterator branchIDs = keys.iterator();
      int sum = 0;
      while (branchIDs.hasNext()) {
        String key = (String)branchIDs.next();
        Integer value = (Integer)branchMap.get(key);
        buff.append("  <row><branch>");
        buff.append(key);
        buff.append("</branch><count>");
        buff.append(value);
        buff.append("</count></row>\n");
        sum += value.intValue();
      }
      buff.append("  <row><branch>ukupno</branch><count>");
      buff.append(sum);
      buff.append("</count></row>\n");
      buff.append("</report>\n");
//      File dir = new File("lib");
//      File[] files = dir.listFiles(new FilenameFilter() {
//        public boolean accept(File dir, String name) {
//          return name.startsWith("jasper") && name.endsWith("jar");
//        }
//      });
//      String classPath = "lib/" + files[0].getName();
//      System.setProperty("jasper.reports.compile.class.path", classPath);
      try {
	      JRXmlDataSource dataSource = new JRXmlDataSource(
	         XMLUtils.getDocumentFromString(buff.toString()), "/report/row");
	
	      Map params = new HashMap();
	      params.put("date", sdf.format(new Date()));
	      String query1 = "";
	      String query2 = "";
	      String query3 = "";
	      String query4 = "";
	      String query5 = "";
	      Iterator it = mf.getQueryContent().iterator();
	      if (it.hasNext())
	        query1 = (String)it.next();
	      if (it.hasNext())
	        query2 = (String)it.next();
	      if (it.hasNext())
	        query3 = (String)it.next();
	      if (it.hasNext())
	        query4 = (String)it.next();
	      if (it.hasNext())
	        query5 = (String)it.next();
	      params.put("query1", query1);
	      params.put("query2", query2);
	      params.put("query3", query3);
	      params.put("query4", query4);
	      params.put("query5", query5);

        JasperPrint jp = JasperFillManager.fillReport(
            PogociPoOgrancima.class.getResource(
              "/com/gint/app/bisis/editor/recordstats/ns/PogociPoOgrancima.jasper")
            .openStream(), params, dataSource);

//        JasperReport jr = JasperCompileManager.compileReport(
//            BranchesCommand.class.getResource(
//                "/com/gint/app/bisis/editor/recordstats/PogociPoOgrancima.jrxml")
//            .openStream());
//	      JasperPrint jp = JasperFillManager.fillReport(jr, params, dataSource);
	      rp.setReport(jp);
	      rp.setFinished(true);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void process(Record rec, HashMap branchMap) {
    Iterator it996 = rec.getFields("996").iterator();
    while (it996.hasNext()) {
      Field f = (Field)it996.next();
      Subfield sff = f.getSubfield('f');
      if (sff == null)
        continue;
      String invNo = sff.getContent();
      Subfield sfw = f.getSubfield('w');
      if (sfw == null)
        continue;
      String branchID = sfw.getContent();
      Subfield sfq = f.getSubfield('q');
      if (sfq != null) {
        if (!sfq.getContent().equals("A") && !sfq.getContent().equals("+")) 
          continue; 
      }
        
      Integer count = (Integer)branchMap.get(branchID);
      if (count == null) {
        count = new Integer(1);
        branchMap.put(branchID, count);
      } else {
        count = new Integer(count.intValue() + 1);
        branchMap.put(branchID, count);
      }
    }
  }

  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
}
