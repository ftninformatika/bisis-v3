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

public class StatistikaPoNacinuNabavke extends RecordReport {
  
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
          StatistikaPoNacinuNabavke.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/bg/StatistikaPoNacinuNabavke.jasper")
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
    List naciniNabavke = new ArrayList();
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
      Subfield sf996v = f996.getSubfield('v');
      String nacinNabavke = null;
      if (sf996v == null || sf996v.getContent() == null || sf996v.getContent().trim().length() == 0)
        nacinNabavke = "";
      else
        nacinNabavke = sf996v.getContent().trim();
      Item i = getItem(nacinNabavke);
      if (!naciniNabavke.contains(nacinNabavke)) {
        i.add(1, 1);
        naciniNabavke.add(nacinNabavke);
        if (!recordAddedToSum) {
          recordSum++;
          recordAddedToSum = true;
        }
      } else {
        i.add(0, 1);
      }
    }
  }

  private Item getItem(String nacinNabavke) {
    Item item = (Item)itemMap.get(nacinNabavke);
    if (item == null) {
      item = new Item(nacinNabavke);
      itemMap.put(nacinNabavke, item);
    }
    return item;
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

  public class Item implements Comparable {
    public String nacinNab;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String nacinNab) {
      this.nacinNab = nacinNab;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return nacinNab.compareTo(b.nacinNab);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item  total=\"");
      buf.append(isTotal);
      buf.append("\">\n    <nacinNab>");
      buf.append(getName(nacinNab));
      buf.append("</nacinNab>\n    <brNas>");
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
    String name;
    if (code == null || code.equals(""))
      return "nerazvrstano";
    else if (code.equals("a"))
      return "a - razmena";
    else if (code.equals("i"))
      return "i - izdanja BGB";
    else if (code.equals("k"))
      return "k - kupovina";
    else if (code.equals("l"))
      return "l - poklon izdava\u010da";
    else if (code.equals("m"))
      return "m - marketing";
    else if (code.equals("n"))
      return "n - OP NBS";
    else if (code.equals("o"))
      return "o - otkup SG";
    else if (code.equals("p"))
      return "p - poklon";
    else if (code.equals("r"))
      return "r - otkup RS";
    else if (code.equals("t"))
      return "t - zate\u010deno";
    else if (code.equals("z"))
      return "z - zamena";
    else if (code.equals("UKUPNO"))
      return "UKUPNO";
    else
      return code + " - nepoznato";
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
