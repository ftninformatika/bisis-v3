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

public class StatistikaPoUDK extends RecordReport {

  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    dateRange = (String)params.get("dateRange");
    itemMap.clear();
    
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
          StatistikaPoUDK.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/bg/StatistikaPoUDK.jasper")
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
    Subfield udcSubfield = rec.getSubfield("675a");
    char firstDigit = 'x';
    String udc = null;
    if (udcSubfield != null)
      udc = udcSubfield.getContent().trim();
    firstDigit = getFirstDigit(udc);
    if (firstDigit == ' ')
      firstDigit = 'x';
    if (firstDigit == '8' && udc.startsWith("821.163.41"))
      firstDigit = 'd';
    Iterator it996 = rec.getFields("996").iterator();
    boolean recordAdded = false;
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
      Item i = getItem(firstDigit);
      if (!recordAdded) {
        i.add(1, 1);
        recordAdded = true;
      } else {
        i.add(0, 1);
      }
    }
  }
  
  private Item getItem(char firstDigit) {
    String key = ""+firstDigit;
    if (firstDigit == '8')
      key = "8-strana";
    else if (firstDigit == 'd')
      key = "8-doma\u0107a";
    else if (firstDigit == 'x')
      key = "bez UDK";
    Item item = (Item)itemMap.get(key);
    if (item == null) {
      item = new Item(key);
      itemMap.put(key, item);
    }
    return item;
  }

  public class Item implements Comparable {
    public String udkDigit;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String udkDigit) {
      this.udkDigit = udkDigit;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return udkDigit.compareTo(b.udkDigit);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item  total=\"");
      buf.append(isTotal);
      buf.append("\">\n    <udk>");
      buf.append(udkDigit);
      buf.append("</udk>\n    <brNas>");
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
      this.isTotal = true;
    }
  }

  public char getFirstDigit(String s) {
    if (s == null)
      return ' ';
    if (s.length() == 0)
      return ' ';
    int pos = 0;
    if (s.charAt(0) == '(') {
      pos = s.indexOf(')') + 1;
      if (pos == 0 || pos == s.length())
        pos = 1;
    }
    try {
      while (pos < s.length() && (!Character.isDigit(s.charAt(pos))))
        pos++;
      if (pos == s.length())
        return ' ';
      return s.charAt(pos);
    } catch (Exception e) {
      e.printStackTrace();
      return ' ';
    }
  }

  private Date startDate;
  private Date endDate;
  private String dateRange;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  Map itemMap = new HashMap();
}
