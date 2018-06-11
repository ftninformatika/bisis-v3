package com.gint.app.bisis.editor.recordstats.ki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.util.xml.XMLUtils;

public class StanjePoNaslovima extends RecordReport {

  public void execute() {
    try {
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 28759;
      if (rp.isCancelled())
        return;
      rp.setMaximum(totalRecords);
      rp.setValue(0);
      int count = 0;
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT document FROM documents");
      while (rset.next()) {
        String rezance = rset.getString(1);
        Record rec = RecordFactory.fromUNIMARC(rezance);
        if (rp.isCancelled()) {
          rset.close();
          stmt.close();
          return;
        }
        rp.setValue(++count);
        handleRecord(rec);
      }
      rset.close();
      stmt.close();
//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("D:/BISIS/vojvodina/Kikinda/zapisi.dat", "r");
//      //java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/bisis/zr/records-2005-09-09-11-55.dat", "r");
//      String line = "";
//      while ((line = in.readLine()) != null) {
//        line = line.trim();
//        rp.setValue(++count);
//        if (line.equals(""))
//          continue;
//        Record rec = RecordFactory.fromUNIMARC(line);
//        handleRecord(rec);
//      }
//      in.close();

      List branchList = new ArrayList();
      branchList.addAll(branches.values());
      addTotals(branchList);
      Collections.sort(branchList);
      
      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = branchList.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      buff.append("\n</report>\n");
      
      System.out.println(buff);

      if (params == null)
        params = new HashMap();
      params.put("today", sdf.format(new Date()));

      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/branch");

      JasperPrint jp = JasperFillManager.fillReport(
          StanjeFondaPoUDK.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/ki/StanjePoNaslovima.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void handleRecord(Record rec) {
    String title = rec.getSubfieldContent("200a");
    if (title == null) {
      noTitle++;
      return;
    }
    title = title.toUpperCase();

    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      Subfield sfw = f.getSubfield('w');
      if (sfw == null || sfw.getContent().equals("")) {
        noBranch++;
        continue;
      }
      Subfield sff = f.getSubfield('f');
      if (sff == null || sff.getContent().equals("") || sff.getContent().length() < 4) {
        noInvNum++;
        continue;
      }

      //
      // da li se ogranak cita iz 996w ili 996f?
      //
      String sigla = sfw.getContent();
      Branch b = getBranch(sigla);
      
      if (b.titles.contains(title))
        continue;
      else
        b.titles.add(title);
    }
    
  }
  
  private Branch getBranch(String sigla) {
    Branch b = (Branch)branches.get(sigla);
    if (b != null)
      return b;
    b = new Branch();
    b.sigla = sigla;
    branches.put(sigla, b);
    return b;
  }
  
  public class Branch implements Comparable {
    public String sigla = "";
    public HashSet titles = new HashSet();
    public boolean isTotal = false;

    public int compareTo(Object o) {
      if (o instanceof Branch) {
        Branch b = (Branch)o;
        return sigla.compareTo(b.sigla);
      }
      return 0;
    }
    
    public void add(Branch b) {
      titles.addAll(b.titles);
    }
    
    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <branch id=\"");
      buf.append(sigla);
      buf.append("\" name=\"");
      buf.append(getName(sigla));
      buf.append("\" isTotal=\"");
      buf.append(isTotal);
      buf.append("\">\n    <titleCount>");
      buf.append(titles.size());
      buf.append("</titleCount>\n  </branch>");
      return buf.toString();
    }
  }
  
  public String getName(String sigla) {
    if (sigla.equals("01"))
      return "De\u010dje odeljenje";
    if (sigla.equals("02"))
      return "Odeljenje za odrasle";
    if (sigla.equals("03"))
      return "Stru\u010dne knjige";
    if (sigla.equals("04"))
      return "Banatska Topola";
    if (sigla.equals("05"))
      return "Banatsko Veliko Selo";
    if (sigla.equals("06"))
      return "Ba\u0161aid";
    if (sigla.equals("07"))
      return "I\u0111o\u0161";
    if (sigla.equals("08"))
      return "Mokrin";
    if (sigla.equals("09"))
      return "Nakovo";
    if (sigla.equals("10"))
      return "Novi Kozarci";
    if (sigla.equals("11"))
      return "Rusko Selo";
    if (sigla.equals("12"))
      return "Sajan";
    if (sigla.equals("13"))
      return "Zavi\u010dajno";
    if (sigla.equals("14"))
      return "Legat";
    if (sigla.equals("xx"))
      return "UKUPNO";
    return sigla;
  }
  
  public void addTotals(List branches) {
    Branch total = new Branch();
    total.sigla = "xx";
    total.isTotal = true;
    Iterator it = branches.iterator();
    while (it.hasNext()) {
      Branch b = (Branch)it.next();
      total.add(b);
    }
    branches.add(total);
  }
  
  int totalRecords = 0;
  int noTitle = 0;
  int noBranch = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  HashMap branches = new HashMap();
}
