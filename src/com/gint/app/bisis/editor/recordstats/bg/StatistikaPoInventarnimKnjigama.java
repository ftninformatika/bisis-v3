package com.gint.app.bisis.editor.recordstats.bg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class StatistikaPoInventarnimKnjigama extends RecordReport {
  
  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    dateRange = (String)params.get("dateRange");
    itemMap.clear();
    recordSum = 0;
    
    try {
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
      if (rp.isCancelled())
        return;
      rp.setMaximum(totalRecords);
      rp.setValue(0);
      int count = 0;
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT document FROM documents");
      while (rset.next()) {
        ++count;
        String rezance = rset.getString(1);
        if (rezance == null || "".equals(rezance))
          continue;
        try {
          Record rec = RecordFactory.fromUNIMARC(rezance);
          if (rp.isCancelled()) {
            rset.close();
            stmt.close();
            return;
          }
          rp.setValue(count);
          handleRecord(rec);
        } catch (Exception ex) {
          System.err.println("Prso zapis: " + count);
        }
      }
      rset.close();
      stmt.close();

//      totalRecords = 167443;
//      int count = 0;
//      rp.setMaximum(totalRecords);
//      rp.setValue(0);
//      java.io.RandomAccessFile in = new java.io.RandomAccessFile(
//          "/home/branko/records.dat", "r");
//      String line = "";
//      while ((line = in.readLine()) != null) {
//        line = line.trim();
//        rp.setValue(++count);
//        if (line.equals(""))
//          continue;
//        try {
//          Record rec = RecordFactory.fromUNIMARC(line);
//          handleRecord(rec);
//        } catch (Exception ex) {
//          System.err.println("Prso zapis: " + count);
//        }
//      }
//      in.close();

      List items = new ArrayList();
      items.addAll(itemMap.values());
      Collections.sort(items);
      items.add(makeSumItem(items));

      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = items.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      buff.append("\n</report>\n");
      
      params.put("today", sdf.format(new Date()));
      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          StatistikaPoInventarnimKnjigama.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/bg/StatistikaPoInventarnimKnjigama.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
    Iterator it996 = rec.getFields("996").iterator();
    String internaOznaka = "";
    List invKnjige = new ArrayList();
    boolean recordAddedToSum = false;
    while (it996.hasNext()) {
      Field f996 = (Field)it996.next();
      Subfield sf996o = f996.getSubfield('o');
      if (sf996o == null || sf996o.getContent() == null || sf996o.getContent().trim().length() == 0)
        continue;
      String sDatInv = sf996o.getContent().trim();
      Date datInv = null;
      try {
        datInv = intern.parse(sDatInv);
      } catch (Exception ex) {
        continue;
      }
      if (datInv.before(startDate) || datInv.after(endDate))
        continue;
      Subfield sf996f = f996.getSubfield('f');
      if (sf996f == null || sf996f.getContent() == null || sf996f.getContent().trim().length() < 4)
        continue;
      String invKnjiga = sf996f.getContent().trim().substring(2, 4);
      Item i = getItem(invKnjiga);
      if (!invKnjige.contains(invKnjiga)) {
        i.add(1, 1);
        invKnjige.add(invKnjiga);
        if (!recordAddedToSum) {
          recordSum++;
          recordAddedToSum = true;
        }
      } else {
        i.add(0, 1);
      }
    }
  }

  public String nvl(String s) {
    return s == null ? "" : s;
  }

  public class Item implements Comparable {
    public String inv;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String inv) {
      super();
      this.inv = inv;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return inv.compareTo(b.inv);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item  total=\"");
      buf.append(isTotal);
      buf.append("\">\n    <inv>");
      buf.append(getName(inv));
      buf.append("</inv>\n    <brNas>");
      buf.append(brNas);
      buf.append("</brNas>\n   <brPr>");
      buf.append(brPr);
      buf.append("</brPr>\n    </item>");
      return buf.toString();
    }

    public void add(int brNas, int brPr) {
      this.brNas += brNas;
      this.brPr += brPr;
    }

    public void addItem(Item i) {
      brNas += i.brNas;
      brPr += i.brPr;
    }
  }

  public String getName(String code) {
    if (code == null || code.equals(""))
      return "nerazvrstano";
    if (code.equals("00"))
      return "X";
    if (code.equals("20"))
      return "B";
    if (code.equals("30"))
      return "D";
    if (code.equals("60"))
      return "L";
    if (code.equals("UKUPNO"))
      return "UKUPNO";
    else
      return code + " - nerazvrstano";
  }

  private Item makeSumItem(List items) {
    Item item = new Item("UKUPNO");
    item.brNas = recordSum;
    for (int i = 0; i < items.size(); i++) {
      Item it = (Item)items.get(i);
      item.brPr += it.brPr;
    }
    return item;
  }

  private Item getItem(String invKnjiga) {
    Item item = (Item)itemMap.get(invKnjiga);
    if (item == null) {
      item = new Item(invKnjiga);
      itemMap.put(invKnjiga, item);
    }
    return item;
  }

  private Date startDate;
  private Date endDate;
  private String dateRange;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  int recordSum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  Map itemMap = new HashMap();
}
