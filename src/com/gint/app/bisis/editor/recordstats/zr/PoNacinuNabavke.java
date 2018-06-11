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

public class PoNacinuNabavke extends RecordReport {

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
          PoNacinuNabavke.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/zr/PoNacinuNabavke.jasper")
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
        continue;
      }
      if (invDate.compareTo(startDate) < 0 || invDate.compareTo(endDate) > 0)
        continue;

      Subfield sfv = f.getSubfield('v');
      if (sfv == null || sfv.getContent().equals("")) {
        problems.append("Nedostaje 996v (na\u010din nabavke) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noCode++;
        continue;
      }
      Subfield sf3 = f.getSubfield('3');
      if (sf3 == null || sf3.getContent().equals("")) {
        problems.append("Nedostaje 9963 (cena) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noPrice++;
        continue;
      }
      
      Subsubfield ssf9961m = sf9961.getSubsubfield('m');
      if (ssf9961m == null) {
        problems.append("Nedostaje 9961m (broj ra\u010duna) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noInvDate++;
        continue;
      }

      String code = sfv.getContent().trim();
      // izuzetak -- poklon ministarstva
      if (ssf9961m.getContent().toUpperCase().indexOf("PM") != -1)
        code = "x";
      
      float value = 0f;
      try {
        value = Float.parseFloat(sf3.getContent().trim());
      } catch (Exception ex) {
        noPrice++;
        continue;
      }
      Item i = getItem(code);
      i.quantity++;
      i.value += value;
    }
  }
  
  public class Item implements Comparable {
    public String code;
    public int quantity = 0;
    public float value = 0f;

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item)o;
        String x1 = code;
        if (x1.equals("x"))
          x1 = "c";
        String x2 = b.code;
        if (x2.equals("x"))
          x2 = "c";
        return x1.compareTo(x2);
      }
      return 0;
    }
    public String toString() {
      String c = code;
      if (code.equals("x"))
        c = "c";
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item code=\"");
      buf.append(c);
      buf.append("\" name=\"");
      buf.append(getName(code));
      buf.append("\">\n    <quantity>");
      buf.append(quantity);
      buf.append("</quantity>\n    <value>");
      buf.append(nf.format(value));
      buf.append("</value>\n  </item>");
      return buf.toString();
    }
  }
  
  public String getName(String code) {
    if (code == null || code.equals(""))
      return "";
    switch (code.charAt(0)) {
      case 'a':
        return "Kupovina";
      case 'b':
        return "Razmena";
      case 'c':
        return "Poklon";
      case 'd':
        return "Obavezni primerak";
      case 'e':
        return "Zate\u010deni (stari) fond";
      case 'f':
        return "Sopstvena izdanja";
      case 'x':
        return "Poklon ministarstva";
      case ' ':
        return "UKUPNO";
    }
    return "";
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
  
  public void addTotal(List itemList) {
    Item total = new Item();
    total.code = " ";
    Iterator it = itemList.iterator();
    while (it.hasNext()) {
      Item i = (Item)it.next();
      total.quantity += i.quantity;
      total.value += i.value;
    }
    itemList.add(total);
  }
  
  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noCode = 0;
  int noPrice = 0;
  int noInvDate = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  NumberFormat nf;
  HashMap items = new HashMap();
}
