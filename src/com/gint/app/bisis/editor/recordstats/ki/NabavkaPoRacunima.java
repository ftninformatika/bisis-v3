package com.gint.app.bisis.editor.recordstats.ki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis.editor.recordstats.ns.BrojUradjenihZapisa;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.RecordID;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;
import com.gint.util.xml.XMLUtils;

public class NabavkaPoRacunima extends RecordReport {

  public void execute() {
    try {
      nf = NumberFormat.getInstance(Locale.GERMANY);
      nf.setMinimumFractionDigits(2);
      nf.setMaximumFractionDigits(2);
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate"); 
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 129487;
      if (rp.isCancelled())
        return;
      rp.setMaximum(totalRecords);
      rp.setValue(0);
      int count = 0;
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT doc_id, document FROM documents");
      while (rset.next()) {
        String rezance = rset.getString(2);
        Record rec = RecordFactory.fromUNIMARC(rezance);
        rec.setRecordID(new RecordID(0, rset.getInt(1)));
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

//      //java.io.RandomAccessFile in = new java.io.RandomAccessFile("D:/BISIS/vojvodina/Zrenjanin/BISIS-2005-09-09/records-2005-09-09-11-55.dat", "r");
//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/BISIS_backup/BISIS-2005-12-01/records-2005-12-01-01-49.dat", "r");
//      String line = "";
//      while ((line = in.readLine()) != null) {
//        line = line.trim();
//        rp.setValue(++count);
//        if (line.equals(""))
//          continue;
//        Record rec = RecordFactory.fromUNIMARC(line);
//        rec.setRecordID(new RecordID(0, count));
//        handleRecord(rec);
//      }
//      in.close();

      List branchList = new ArrayList();
      branchList.addAll(items.values());
      Collections.sort(branchList);
      
      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = branchList.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      
      buff.append("</report>\n");

      if (noInvDate > 0)
        problems.append("Ukupno " + noInvDate + " zapisa bez datuma inventarisanja (996o)\n");

      if (params == null)
        params = new HashMap();
      params.put("today", sdf.format(new Date()));

      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/racun");

      JasperReport subreport = (JasperReport)JRLoader.loadObject(
          BrojUradjenihZapisa.class.getResource(
              "/com/gint/app/bisis/editor/recordstats/ki/NabavkaPoRacunima_part.jasper")
          .openStream());
      params.put("subreport", subreport);

      JasperPrint jp = JasperFillManager.fillReport(
          StanjeFondaPoUDK.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/ki/NabavkaPoRacunima.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      rp.setProblems(problems.toString());
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void handleRecord(Record rec) {
    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      // check date
      Subfield sf996o = f.getSubfield('o');
      if (sf996o == null) {
        noInvDate++;
        continue;
      }
      Date invDate = null;
      try {
        invDate = intern.parse(sf996o.getContent().trim());
      } catch (Exception ex) {
        noInvDate++;
        System.out.print(".");
        continue;
      }
      if (invDate.compareTo(startDate) < 0 || invDate.compareTo(endDate) > 0)
        continue;
      // check inv num
      Subfield sff = f.getSubfield('f');
      if (sff == null || sff.getContent().equals("")) {
        problems.append("Nedostaje 996f (inventarni broj) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noInvNum++;
        continue;
      }
      // check price
      Subfield sf3 = f.getSubfield('3');
      if (sf3 == null || sf3.getContent().equals("")) {
        problems.append("Nedostaje 9963 (cena) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noPrice++;
        continue;
      }
      double price = 0d;
      try {
        price = Double.parseDouble(sf3.getContent().trim());
      } catch (Exception ex) {
        problems.append("Neispravan 9963 (cena) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noPrice++;
        continue;
      }
      Subfield sf9961 = f.getSubfield('1');
      if (sf9961 == null) {
        problems.append("Neispravan 9961m (broj ra\u010duna) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noRacun++;
        continue;
      }
      Subsubfield ssf9961m = sf9961.getSubsubfield('m');
      if (ssf9961m == null || ssf9961m.getContent().equals("")) {
        problems.append("Neispravan 9961m (broj ra\u010duna) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noRacun++;
        continue;
      }
      
      Item item = getItem(ssf9961m.getContent().trim());
      item.subitems.add(new SubItem(sff.getContent().trim(), price));
    }
  }
  
  public Item getItem(String racun) {
    Item i = (Item)items.get(racun);
    if (i == null) {
      i = new Item();
      i.racun = racun;
      items.put(racun, i);
    }
    return i;
  }
  
  public class Item implements Comparable {
    public String racun = "";
    public List subitems = new ArrayList();
    
    public int hashCode() {
      return racun.hashCode();
    }
    
    public boolean equals(Object o) {
      Item obj = (Item)o;
      return racun.equals(obj.racun);
    }
    
    public int compareTo(Object o) {
      Item obj = (Item)o;
      return racun.compareTo(obj.racun);
    }
    
    public String toString() {
      Collections.sort(subitems);
      StringBuffer buff = new StringBuffer();
      buff.append("  <racun id=\"");
      buff.append(racun);
      buff.append("\" totalBooks=\"");
      buff.append(subitems.size());
      buff.append("\">\n");
      double sum = 0d;
      for (int i = 0; i < subitems.size(); i++) {
        String ib = ((SubItem)subitems.get(i)).invbr;
        double c = ((SubItem)subitems.get(i)).cena;
        sum += c;
        buff.append("    <stavka invbr=\"");
        buff.append(ib);
        buff.append("\" cena=\"");
        buff.append(nf.format(c));
        buff.append("\" isTotal=\"false\"/>\n");
      }
      buff.append("    <stavka invbr=\"UKUPNO\" cena=\"");
      buff.append(nf.format(sum));
      buff.append("\" isTotal=\"true\"/>\n");
      buff.append("  </racun>\n");
      return buff.toString();
    }
  }
  
  public class SubItem implements Comparable {
    public SubItem() {
    }
    public SubItem(String invbr, double cena) {
      this.invbr = invbr;
      this.cena = cena;
    }
    
    public int compareTo(Object o) {
      SubItem si = (SubItem)o;
      return invbr.compareTo(si.invbr);
    }
    
    public String invbr;
    public double cena;
  }

  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noRacun = 0;
  int noPrice = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  NumberFormat nf;
  HashMap items = new HashMap();
}
