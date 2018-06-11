package com.gint.app.bisis.editor.recordstats.sa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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

public class StatistikaBaze extends RecordReport {

  public void execute() {
    try {
      dateRange = (String)params.get("dateRange");
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate");
      odeljenja.clear();
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

      ArrayList items = new ArrayList();
      items.addAll(odeljenja.values());
      Collections.sort(items);
      Item ukupno = new Item();
      ukupno.odeljenje = 5;
      for (int i = 0; i < items.size(); i++) {
        Item it = (Item)items.get(i);
        ukupno.add(it);
      }
      ukupno.zapisa = realRecords;
      items.add(ukupno);
      
      StringBuffer buff = new StringBuffer();
      buff.append("<report>\n");
      Iterator it = items.iterator();
      while (it.hasNext()) {
        buff.append(it.next());
        buff.append("\n");
      }
      buff.append("\n</report>\n");
      
      System.out.println(buff.toString());
      
      params.put("today", sdf2.format(new Date()));
      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          KnjigaInventara.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/sa/StatistikaBazeGBSA.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  private void handleRecord(Record rec) {
    if (rec == null)
      return;
    boolean recordCounted = false;
    boolean zapis01 = false;
    boolean zapis02 = false;
    boolean zapis03 = false;
    boolean zapis04 = false;
    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      
      // ako ne moze da se ustanovi odeljenje, ovaj primerak se ignorise
      Subfield sfw = f.getSubfield('w');
      int ogr = 0;
      if (sfw == null) {
        Subfield sff = f.getSubfield('f');
        if (sff == null)
          continue;
        if (sff.getContent().length() < 2)
          continue;
        ogr = Integer.parseInt(sff.getContent().substring(0, 2));
      } else {
        if (sfw.getContent().length() != 2)
          continue;
        ogr = Integer.parseInt(sfw.getContent());
      }
      
      // ako je primerak rekatalogizovan u datom periodu, to se racuna
      Item i = getItem(ogr);
      String rekat = f.getSubfieldContent('5');
      if (rekat != null) {
        if (rekat.toUpperCase().startsWith("R")) {
          String datum = rekat.substring(1);
          try {
            Date rekatDate = intern.parse(datum);
            if (rekatDate.compareTo(startDate) >= 0 && rekatDate.compareTo(endDate) <= 0)
              i.rekatalogizovanih++;
          } catch (ParseException ex) {
            
          }
        }
      }

      // ako datum inventarisanja ne postoji ili nije u datom opsegu,
      // ovaj primerak se ignorise
      String sDatInv = f.getSubfieldContent('o');
      if (sDatInv == null)
        continue;
      Date datInv = null;
      try {
        datInv = intern.parse(sDatInv);
      } catch (ParseException e1) {
        try {
          datInv = sdf1.parse(sDatInv);
        } catch (ParseException e2) {
          try {
            datInv = sdf2.parse(sDatInv);
          } catch (ParseException e3) {
            continue;
          }
        }
      }
      if (datInv == null)
        continue;
      if (datInv.compareTo(startDate) < 0 || datInv.compareTo(endDate) > 0)
        continue;
      
      if (!recordCounted) {
        realRecords++;
        recordCounted = true;
      }
      
      i.primeraka++;
      if (!zapis01 && ogr==1) {
        i.zapisa++;
        zapis01 = true;
      }
      if (!zapis02 && ogr==2) {
        i.zapisa++;
        zapis02 = true;
      }
      if (!zapis03 && ogr==3) {
        i.zapisa++;
        zapis03 = true;
      }
      if (!zapis04 && ogr==4) {
        i.zapisa++;
        zapis04 = true;
      }
      
      String status = f.getSubfieldContent('q');
      String nabavka = f.getSubfieldContent('v');
      if ("9".equals(status))
        i.otpisanih++;
      else if ("a".equals(nabavka) || "k".equals(nabavka))
        i.kupljenih++;
      else if ("c".equals(nabavka) || "p".equals(nabavka))
        i.poklonjenih++;
      else if ("o".equals(nabavka))
        i.izotkupa++;
      
    }
  }
  
  public class Item implements Comparable {
    public int odeljenje;
    public int rekatalogizovanih;
    public int otpisanih;
    public int poklonjenih;
    public int kupljenih;
    public int izotkupa;
    public int primeraka;
    public int zapisa;
    
    public int compareTo(Object o) {
      Item i = (Item)o;
      if (odeljenje < i.odeljenje)
        return -1;
      else if (odeljenje > i.odeljenje)
        return 1;
      else
        return 0;
    }
    
    public void add(Item i) {
      rekatalogizovanih += i.rekatalogizovanih;
      otpisanih += i.otpisanih;
      poklonjenih += i.poklonjenih;
      kupljenih += i.kupljenih;
      izotkupa += i.izotkupa;
      primeraka += i.primeraka;
      zapisa += i.zapisa; // ovo nema smisla
    }
    
    public String toString() {
      StringBuffer retVal = new StringBuffer();
      retVal.append("<item><odeljenje>");
      retVal.append(getOdeljenje(odeljenje));
      retVal.append("</odeljenje><rekatalog>");
      retVal.append(rekatalogizovanih);
      retVal.append("</rekatalog><otpisanih>");
      retVal.append(otpisanih);
      retVal.append("</otpisanih><poklonjenih>");
      retVal.append(poklonjenih);
      retVal.append("</poklonjenih><kupljenih>");
      retVal.append(kupljenih);
      retVal.append("</kupljenih><izotkupa>");
      retVal.append(izotkupa);
      retVal.append("</izotkupa><primeraka>");
      retVal.append(primeraka - otpisanih);
      retVal.append("</primeraka><zapisa>");
      retVal.append(zapisa);
      retVal.append("</zapisa></item>");
      return retVal.toString();
    }
    
    public String getOdeljenje(int o) {
      switch (o) {
        case 1: 
          return "\u043e\u0434\u0440\u0430\u0441\u043b\u0438"; // odrasli
        case 2: 
          return "\u0434\u0435\u0447\u0458\u0435"; // decje
        case 3:
          return "\u043d\u0430\u0443\u0447\u043d\u043e"; // naucno
        case 4:
          return "\u0437\u0430\u0432\u0438\u0447\u0430\u0458\u043d\u043e"; // zavicajno
        case 5:
          return "\u0423\u041a\u0423\u041f\u041d\u041e"; // UKUPNO
        default:
          return "\u043d\u0435\u043f\u043e\u0437\u043d\u0430\u0442\u043e"; // nepoznato
      }
    }
  }
  
  public Item getItem(int odeljenje) {
    Integer n = new Integer(odeljenje);
    Item i = (Item)odeljenja.get(n);
    if (i == null) {
      i = new Item();
      i.odeljenje = odeljenje;
      odeljenja.put(n, i);
    }
    return i;
  }
  
  Date startDate;
  Date endDate;
  String dateRange;
  int totalRecords = 0;
  int realRecords = 0;
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyy");
  SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy.");
  HashMap odeljenja = new HashMap();
}
