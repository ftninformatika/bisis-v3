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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;
import com.gint.util.xml.XMLUtils;

public class StatistikaNacinaNabavkePoFondovima extends RecordReport {
  
  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    dateRange = (String)params.get("dateRange");
    itemMap.clear();
    subtotals.clear();
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
      while (it.hasNext()) {
        Item item = (Item)it.next();
        Collections.sort(item.subitems);
        buff.append(item.toString());
      }
      buff.append("\n</report>\n");
      
      params.put("today", sdf.format(new Date()));
      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperReport subreport = (JasperReport)JRLoader.loadObject(
          StatistikaNacinaNabavkePoFondovima.class.getResource(
              "/com/gint/app/bisis/editor/recordstats/bg/StatistikaNacinaNabavkePoFondovima_part.jasper")
              .openStream());
      params.put("subjasper", subreport);
      JasperPrint jp = JasperFillManager.fillReport(
          StatistikaNacinaNabavkePoFondovima.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/bg/StatistikaNacinaNabavkePoFondovima.jasper")
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
    List nabavkePoFondu = new ArrayList();
    Map dodatUNabavku = new HashMap();
    while (it996.hasNext()) {
      Field f996 = (Field)it996.next();
      Subfield sf996d = f996.getSubfield('d');
      if (sf996d != null) {
        Subsubfield ssf996di = sf996d.getSubsubfield('i');
        if (ssf996di != null && ssf996di.getContent() != null && ssf996di.getContent().trim().length() > 0) {
          internaOznaka = ssf996di.getContent().trim();
        }
      }
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
      Item item = getItem(nacinNabavke);
      SubItem subItem = item.getSubItem(internaOznaka);
      if (!nabavkePoFondu.contains(nacinNabavke+":"+internaOznaka)) {
        subItem.add(1, 1);
        nabavkePoFondu.add(nacinNabavke+":"+internaOznaka);
        Boolean dodat = (Boolean)dodatUNabavku.get(nacinNabavke);
        if (dodat == null) {
          dodat = new Boolean(false);
          dodatUNabavku.put(nacinNabavke, dodat);
        }
        if (!dodat.booleanValue()) {
          Integer sum = (Integer)subtotals.get(nacinNabavke);
          if (sum == null) {
            sum = new Integer(0);
            subtotals.put(nacinNabavke, sum);
          }
          int s = sum.intValue();
          s += 1;
          subtotals.put(nacinNabavke, new Integer(s));
          dodatUNabavku.put(nacinNabavke, new Boolean(true));
        }
      } else {
        subItem.add(0, 1);
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
  
  public class Item implements Comparable {
    public String nacinNab;
    public List subitems;

    public Item(String nacinNab) {
      this.nacinNab = nacinNab;
      subitems = new ArrayList();
    }

    public List getSubItems() {
      return this.subitems;
    }
    
    public SubItem getSubItem(String internaOznaka) {
      Iterator it = subitems.iterator();
      while (it.hasNext()) {
        SubItem si = (SubItem)it.next();
        if (internaOznaka.equals(si.inv))
          return si;
      }
      SubItem si = new SubItem(internaOznaka);
      subitems.add(si);
      return si;
    }

    public int compareTo(Object o) {
      Item obj = (Item) o;
      return nacinNab.compareTo(obj.nacinNab);
    }

    public String toString() {
      StringBuffer buff = new StringBuffer();
      buff.append("  <item>\n <nacinNab>");
      buff.append(getNameNabavka(nacinNab));
      buff.append("</nacinNab>\n");
      int sum = 0;
      int sum1 = 0;
      for (int i = 0; i < subitems.size(); i++) {
        String ib = ((SubItem) subitems.get(i)).inv;
        int brN = ((SubItem) subitems.get(i)).brNas;
        int brP = ((SubItem) subitems.get(i)).brPr;
        sum1 += brP;
        buff.append("    <subItem> <inv>");
        buff.append(getNameFond(ib));
        buff.append("</inv>\n  <brNas>");
        buff.append(brN);
        buff.append("</brNas>\n <brPr>");
        buff.append(brP);
        buff.append("</brPr>\n <isTotal>false</isTotal>\n</subItem>");
      }
      buff.append("    <subItem> <inv>UKUPNO</inv> <brNas>");
      Integer subtotal = (Integer)subtotals.get(nacinNab);
      int sub = 0;
      if (subtotal != null) {
        sub = subtotal.intValue();
      }
      buff.append(sub);
      buff.append("</brNas>\n <brPr>");
      buff.append(sum1);
      buff.append("</brPr>\n <isTotal>true</isTotal>\n</subItem>");
      buff.append("  </item>\n");
      return buff.toString();
    }
  }

  public class SubItem implements Comparable {
    public String inv;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public SubItem(String inv) {
      this.inv = inv;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      SubItem si = (SubItem) o;
      return inv.compareTo(si.inv);
    }

    public void add(int brNas, int brPr) {
      this.brNas += brNas;
      this.brPr += brPr;
    }

    public void addItem(SubItem si) {
      brNas += si.brNas;
      brPr += si.brPr;
    }
  }
  
  public String getNameNabavka(String code) {
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
    else
      return code + " - nepoznato";
  }

  public String getNameFond(String code) {
    if (code == null || code.equals(""))
      return "nerazvrstano";
    if (code.equals("B"))
      return "B - Zavi\u010dajni fond - Beograd";
    if (code.equals("BL"))
      return "BL - Bibliotekarstvo";
    if (code.equals("BLS"))
      return "BLS - Bibliotekarstvo - strana knjiga";
    if (code.equals("BPR"))
      return "BPR - Beogradika - priru\u010dnici";
    if (code.equals("BPS"))
      return "BPS - Beogradika - priru\u010dnici - strana knjiga";
    if (code.equals("BS"))
      return "BS - Beogradika - strana knjiga";
    if (code.equals("D"))
      return "D - De\u010dji fond";
    if (code.equals("DEP"))
      return "DEP - Depozit";
    if (code.equals("DF"))
      return "DF - Dijafilmovi";
    if (code.equals("DP"))
      return "DP - De\u010dje - priru\u010dnici";
    if (code.equals("DPS"))
      return "DPS - De\u010dje - priru\u010dnici - strana knjiga";
    if (code.equals("DR"))
      return "DR - De\u010dje - retka knjiga";
    if (code.equals("DRS"))
      return "DRS - De\u010dje - retka knjiga - strana";
    if (code.equals("DS"))
      return "DS - De\u010dje - strana knjiga";
    if (code.equals("F"))
      return "F - Fotografije";
    if (code.equals("FA"))
      return "FA - Fotografije - zavi\u010dajni fond";
    if (code.equals("FM"))
      return "FM - Film";
    if (code.equals("FMS"))
      return "FMS - Film - strana knjiga";
    if (code.equals("FS"))
      return "FS - Fotografija - strana knjiga";
    if (code.equals("FTI"))
      return "FTI - Fototipska izdanja";
    if (code.equals("G"))
      return "G - Gramofonske plo\u010de";
    if (code.equals("GK"))
      return "GK - Geografske karte";
    if (code.equals("IB"))
      return "IB - Izdavanje - Beograd";
    if (code.equals("IBS"))
      return "IBS - Izdavanje - Beograd - strana knjiga";
    if (code.equals("K"))
      return "K - Kasete";
    if (code.equals("L"))
      return "L - Legat";
    if (code.equals("M"))
      return "M - Muzika - knjige";
    if (code.equals("MF"))
      return "MF - Mikrofilmovi";
    if (code.equals("MS"))
      return "MS - Muzika - strana knjiga";
    if (code.equals("O"))
      return "O - Referalna zbirka";
    if (code.equals("OS"))
      return "OS - Referalna zbirka - strana knjiga";
    if (code.equals("P"))
      return "P - Pozori\u0161te";
    if (code.equals("PBL"))
      return "PBL - Periodika bibliografije";
    if (code.equals("PF"))
      return "PF - Pokretni fond";
    if (code.equals("PFS"))
      return "PFS - Pokretni fond - strana knjiga";
    if (code.equals("PK"))
      return "PK - Plakati";
    if (code.equals("PN"))
      return "PN - Priru\u010dnici - nabavno";
    if (code.equals("PNS"))
      return "PNS - Priru\u010dnici - nabavno - strana knjiga";
    if (code.equals("PPR"))
      return "PPR - Periodika - priru\u010dnici";
    if (code.equals("PPS"))
      return "PPS - Periodika - priru\u010dnici - strana knjiga";
    if (code.equals("PS"))
      return "PS - Pozori\u0161te - strana knjiga";
    if (code.equals("R"))
      return "R - Rukopisi";
    if (code.equals("RI"))
      return "RI - Retka izdanja";
    if (code.equals("RIS"))
      return "RIS - Retka izdanja - strana knjiga";
    if (code.equals("U"))
      return "U - Umetnost";
    if (code.equals("US"))
      return "US - Umetnost - strana knjiga";
    if (code.equals("X"))
      return "X - Op\u0161ti fond";
    if (code.equals("XS"))
      return "XS - Op\u0161ti fond - strana knjiga";
    if (code.equals("\u0160"))
      return "\u0160 - \u0161ah";
    if (code.equals("\u0160S"))
      return "\u0160S - \u0161ah - strana knjiga";
    if (code.equals("XX"))
      return "ukupno";
    else
      return code + " - nepoznato";
  }

  private Date startDate;
  private Date endDate;
  private String dateRange;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  Map subtotals = new HashMap();
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  Map itemMap = new HashMap();
}
