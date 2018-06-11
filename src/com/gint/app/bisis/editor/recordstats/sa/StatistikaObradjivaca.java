package com.gint.app.bisis.editor.recordstats.sa;

/*
 inicijali: Loncar - dl, Nada - nr, Duska - dv, Nebojsa - nc, Jelena - jp, Sanja 
 (ce biti) - sm, Vesna - va.  
 za broj rekatalogizovanih primeraka hteli smo da tebe konsultujemo. U starom 
 programu oznaka rekat. primerka bilo je 5r (sto je prepisano u 996 - 5), a 
 broj takvih primeraka i datum obrade pisani su u lokacijskim podacima (sto 
 je prepisano u 998). Mi to nismo nastavili da unosimo, posto je jedno vreme 
 996 - 5 bilo neaktivno. Sada te pitamo: da li je bolje da nastavimo da unosimo 
 u 996 - 5r ili je bolje da unesemo novo potpolje u 992 za ozanku vrste primerka 
 (n - novi; s - stari ili r - rekat.), a broj takvih primeraka u 992 - 6? 
 Ukrstanjem ovih podataka sa datumom i inicijalima iz 992b mogao bi dobiti i 
 broj primeraka po obradjivacu i vrsti primerka, kao i ukupan broj takvih 
 primeraka, sto je bitno i za statistiku baze.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

public class StatistikaObradjivaca extends RecordReport {

  public void execute() {
    try {
      dateRange = (String)params.get("dateRange");
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate");
      params.put("today", sdf.format(new Date()));
      items.clear();

      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 40015;
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
//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/BISIS-sites/sabac/records.dat", "r");
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
      
      StringBuffer buff = new StringBuffer();
      buff.append("<report>\n");
      Iterator it = items.values().iterator();
      while (it.hasNext()) {
        buff.append(it.next());
      }
      buff.append("</report>\n");

      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          KnjigaInventara.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/sa/StatistikaObradjivacaGBSA.jasper")
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
    Iterator i = rec.getFields("992").iterator(); 
    while (i.hasNext()) {
      Field f = (Field)i.next();
      Subfield sf6 = f.getSubfield('6');
      Subfield sfb = f.getSubfield('b');
      if (sfb == null || sfb.getContent() == null || sfb.getContent().length() != 12)
        continue;
      int brPrimeraka = 1;
      if (sf6 != null && sf6.getContent() != null) {
        try { brPrimeraka = Integer.parseInt(sf6.getContent()); } catch (Exception ex) { }
      }
      String type = sfb.getContent().substring(0, 2).toLowerCase();
      String obr = sfb.getContent().substring(2, 4).toLowerCase();
      String sdate = sfb.getContent().substring(4);
      Date date = null;
      try { 
        date = intern.parse(sdate); 
        if (date.compareTo(startDate) < 0 || date.compareTo(endDate) > 0)
          continue;
      } catch (Exception ex) { 
        continue; 
      }
      
      Item item = getItem(obr);
      if ("cr".equals(type)) 
        item.add(1, 0, 0, 0, 0);
      else if ("dp".equals(type))
        item.add(0, 1, 0, 0, 0);
      else if ("co".equals(type))
        item.add(0, 0, 1, 0, 0);
      else if ("re".equals(type))
        item.add(0, 0, 0, brPrimeraka, 0);
      else if ("nv".equals(type))
        item.add(0, 0, 0, 0, brPrimeraka);
    }
  }
  
  public class Item {
    public String obr = "";
    public int cr = 0;
    public int dp = 0;
    public int co = 0;
    public int re = 0;
    public int nov = 0;
    public String toString() {
      return "<item><obr>"+getName()+"</obr><cr>"+cr+"</cr><dp>"+dp+"</dp><co>"+co+"</co><re>"+re+"</re><nov>"+nov+"</nov></item>\n";
    }
    public int hashCode() {
      return obr.hashCode();
    }
    public boolean equals(Object o) {
      Item i = (Item)o;
      return obr.equals(i.obr);
    }
    public void add(int cr, int dp, int co, int re, int nov) {
      this.cr += cr;
      this.dp += dp;
      this.co += co;
      this.re += re;
      this.nov += nov;
    }
    
    public String getName() {
      if ("dl".equals(obr))
        return "\u041b\u043e\u043d\u0447\u0430\u0440"; // Loncar
      if ("nr".equals(obr))
        return "\u041d\u0430\u0434\u0430"; // Nada
      if ("dv".equals(obr))
        return "\u0414\u0443\u0448\u043a\u0430"; // Duska
      if ("nc".equals(obr))
        return "\u041d\u0435\u0431\u043e\u0458\u0448\u0430"; // Nebojsa
      if ("jp".equals(obr))
        return "\u0408\u0435\u043b\u0435\u043d\u0430"; // Jelena
      if ("sm".equals(obr))
        return "\u0421\u0430\u045a\u0430"; // Sanja
      if ("va".equals(obr))
        return "\u0412\u0435\u0441\u043d\u0430"; // Vesna
      return obr;
    }
  }
  
  public Item getItem(String obr) {
    Item i = (Item)items.get(obr);
    if (i == null) {
      i = new Item();
      i.obr = obr;
      items.put(obr, i);
    }
    return i;
  }
  
  Date startDate;
  Date endDate;
  String dateRange;
  int totalRecords = 0;
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  
  Map items = new HashMap();
}
