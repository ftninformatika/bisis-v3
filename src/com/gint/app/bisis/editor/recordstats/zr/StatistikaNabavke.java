package com.gint.app.bisis.editor.recordstats.zr;

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
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.RecordID;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;
import com.gint.util.xml.XMLUtils;

public class StatistikaNabavke extends RecordReport {

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
//      totalRecords = 130158;
      if (rp.isCancelled())
        return;
      rp.setMaximum(totalRecords);
      rp.setValue(0);
      int count = 0;
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT doc_id, document FROM documents");
      while (rset.next()) {
        String rezance = rset.getString(2);
        if (rezance == null)
          continue;
        Record rec = RecordFactory.fromUNIMARC(rezance);
        if (rec == null)
          continue;
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

//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/BISIS-sites/gbzr/records.dat", "r");
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

      List itemList = new ArrayList();
      itemList.addAll(items.values());
      Collections.sort(itemList);
      addTotal(itemList);

      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = itemList.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      buff.append("\n</report>\n");
      
      if (noInvDate > 0)
        problems.append("Ukupno " + noInvDate + " zapisa bez datuma ra\u010duna (9961q)\n");

      params.put("today", sdf.format(new Date()));

      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          StatistikaNabavke.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/zr/StatistikaNabavke.jasper")
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
      Subfield sf9961 = f.getSubfield('1');
      if (sf9961 == null) {
        noInvDate++;
        continue;
      }
      Subsubfield ssf9961q = sf9961.getSubsubfield('q');
      if (ssf9961q == null) {
        noInvDate++;
        continue;
      }
      Date invDate = null;
      try {
        invDate = intern.parse(ssf9961q.getContent().trim());
      } catch (Exception ex) {
        noInvDate++;
        System.out.print(".");
        continue;
      }
      if (invDate.compareTo(startDate) < 0 || invDate.compareTo(endDate) > 0)
        continue;
      

      Subfield sfw = f.getSubfield('w');
      if (sfw == null || sfw.getContent().equals("")) {
        problems.append("Nedostaje 996w (odeljenje) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noBranch++;
        continue;
      }
      Subfield sfv = f.getSubfield('v');
      if (sfv == null || sfv.getContent().equals("")) {
        problems.append("Nedostaje 996v (na\u010din nabavke) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noCode++;
        continue;
      }
      Subfield sff = f.getSubfield('f');
      if (sff == null || sff.getContent().equals("") || sff.getContent().length() < 4) {
        problems.append("Nedostaje ili neispravan 996f (inventarni broj) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noInvNum++;
        continue;
      }
      
      // check price
      Subfield sf3 = f.getSubfield('3');
      if (sf3 == null || sf3.getContent().equals("")) {
        problems.append("Nedostaje 9963 (cena) u zapisu " +   rec.getRecordID().getLocalRecordID() + "\n");
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

      //String odeljenje = sfw.getContent().trim();
      String odeljenje = sff.getContent().substring(0, 2);
      Item i = getItem(odeljenje);
      
      // nacin nabavke i vrednost
      String nacin = sfv.getContent().trim();
      if (nacin.equals("a") || nacin.equals("k")) {
        i.kupovina++;
        i.vredKupovina += price;
      } else if (nacin.equals("c") || nacin.equals("p")) {
        Subfield sf1 = f.getSubfield('1');
        if (sf1 == null) {
          i.poklon++;
          i.vredPoklon += price;
        } else {
          Subsubfield ssfm = sf1.getSubsubfield('m');
          if (ssfm == null) {
            i.poklon++;
            i.vredPoklon += price;
          } else {
            if (ssfm.getContent().toUpperCase().indexOf("PM") != -1) {
              i.poklonM++;
              i.vredPoklonM += price;
            } else {
              i.poklon++;
              i.vredPoklon += price;
            }
          }
        }
      }
      
      // po jezicima
      String od = sff.getContent().substring(0, 2);
      String jez = sff.getContent().substring(2, 4);

      // slikovnice racunamo sa ostalima
      if (jez.equals("04"))
        jez = "01";
      if (jez.equals("05"))
        jez = "02";

      
      if (jez.equals("01"))
        i.srpski++;
      else if (jez.equals("02"))
        i.madjarski++;
      else if (jez.equals("03"))
        i.strani++;
    }
    
  }
  
  public class Item implements Comparable {
    public String code;
    public int srpski;
    public int madjarski;
    public int strani;
    public int kupovina;
    public int poklon;
    public int poklonM;
    public double vredKupovina;
    public double vredPoklon;
    public double vredPoklonM;
    public boolean isTotal = false;

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item)o;
        return code.compareTo(b.code);
      }
      return 0;
    }
    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item code=\"");
      buf.append(code);
      buf.append("\" isTotal=\"");
      buf.append(isTotal);
      buf.append("\">\n    <srpski>");
      buf.append(srpski);
      buf.append("</srpski>\n    <madjarski>");
      buf.append(madjarski);
      buf.append("</madjarski>\n    <strani>");
      buf.append(strani);
      buf.append("</strani>\n    <jezukup>");
      buf.append(srpski + madjarski + strani);
      buf.append("</jezukup>\n    <kupovina>");
      buf.append(kupovina);
      buf.append("</kupovina>\n    <poklon>");
      buf.append(poklon);
      buf.append("</poklon>\n    <poklonM>");
      buf.append(poklonM);
      buf.append("</poklonM>\n    <vredKupovina>");
      buf.append(nf.format(vredKupovina));
      buf.append("</vredKupovina>\n    <vredPoklon>");
      buf.append(nf.format(vredPoklon));
      buf.append("</vredPoklon>\n    <vredPoklonM>");
      buf.append(nf.format(vredPoklonM));
      buf.append("</vredPoklonM>\n    <vredUkup>");
      buf.append(nf.format(vredKupovina + vredPoklon + vredPoklonM));
      buf.append("</vredUkup>\n  </item>");
      return buf.toString();
    }
  }
  
  private Item getItem(String code) {
    Item i = (Item)items.get(code);
    if (i != null)
      return i;
    i = new Item();
    i.code = code;
    items.put(code, i);
    return i;
  }
  
  private void addTotal(List itemList) {
    Item total = new Item();
    total.isTotal = true;
    total.code = "UK";
    Iterator it = itemList.iterator();
    while (it.hasNext()) {
      Item i = (Item)it.next();
      total.srpski += i.srpski;
      total.madjarski += i.madjarski;
      total.strani += i.strani;
      total.kupovina += i.kupovina;
      total.poklon += i.poklon;
      total.poklonM += i.poklonM;
      total.vredKupovina += i.vredKupovina;
      total.vredPoklon += i.vredPoklon;
      total.vredPoklonM += i.vredPoklonM;
    }
    itemList.add(total);
  }
  
  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noCode = 0;
  int noPrice = 0;
  int noInvDate = 0;
  int noBranch = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  NumberFormat nf;
  HashMap items = new HashMap();
}
