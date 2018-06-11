package com.gint.app.bisis.editor.recordstats.zr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.gint.app.bisis4.common.records.RecordID;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

public class KnjigaInventara extends RecordReport {

  public static final int FOND_UNDEFINED  = 0;
  public static final int FOND_SRPSKI     = 1;
  public static final int FOND_MADJARSKI  = 2;
  public static final int FOND_STRANI     = 3;
  public static final int FOND_SLIKOVNICE = 4;
  public static final int FOND_VRTICI     = 5;
  public static final int FOND_BOLNICE    = 6;
  
  public void execute() {
    try {
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate");
      fond = ((Integer)params.get("fond")).intValue();
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//    totalRecords = 129487;
      items = new ArrayList(totalRecords);
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

      Collections.sort(items);

      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = items.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      buff.append("\n</report>\n");
      
      if (noInvDate > 0)
        problems.append("Ukupno " + noInvDate + " zapisa bez datuma ra\u010duna (9961q)\n");

      params.put("today", sdf.format(new Date()));
      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          KnjigaInventara.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/zr/KnjigaInventara.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      rp.setProblems(problems.toString());
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void handleRecord(Record rec) {
    String udk = rec.getSubfieldContent("675a");
    if (udk == null)
      udk = " ";
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = " ";
    String autor = getAutor(rec); 
    if (autor == null)
      autor = " ";
    String izdavac = rec.getSubfieldContent("210c");
    if (izdavac == null)
      izdavac = " ";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = " ";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = " ";
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
      
      // chech inv num
      Subfield sf996f = f.getSubfield('f');
      if (sf996f == null || sf996f.getContent().length() < 5) {
        problems.append("Nedostaje ili neispravan 996f (inventarni broj) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noInvNum++;
        continue;
      }
      String invbrInternal = sf996f.getContent();
      String odel = invbrInternal.substring(0, 4);
      String invbr = invbrInternal.substring(4);
      
      // check fond
      switch (fond) {
        case FOND_SRPSKI:
          if (!(odel.equals("0101") || odel.equals("0201") ||odel.equals("0301") || odel.equals("0401")))
            continue;
          break;
        case FOND_MADJARSKI:
          if (!(odel.equals("0102") || odel.equals("0105") ||odel.equals("0202") || odel.equals("0302") || odel.equals("0402")))
            continue;
          break;
        case FOND_STRANI:
          if (!(odel.equals("0103") || odel.equals("0203") ||odel.equals("0303") || odel.equals("0403")))
            continue;
          break;
        case FOND_SLIKOVNICE:
          if (!(odel.equals("0104")))
            continue;
          break;
        case FOND_VRTICI:
          if (!(odel.equals("0197")))
            continue;
          break;
        case FOND_BOLNICE:
          if (!(odel.equals("0198")))
            continue;
          break;
      }
      
      String pov = f.getSubfieldContent('s');
      if (pov == null || pov.equals(""))
        pov = " ";

      String brrac = " ";
      Subsubfield ssf9961m = sf9961.getSubsubfield('m');
      if (ssf9961m != null && ssf9961m.getContent().length() > 0)
        brrac = ssf9961m.getContent();
      
      
      String vrnab = f.getSubfieldContent('v');
      if (vrnab == null || vrnab.equals(""))
        vrnab = " ";
      String datnab = sdf.format(invDate);
      String cena = f.getSubfieldContent('3');
      if (cena == null || cena.equals(""))
        cena = " ";
      String datras = " "; 
      String nap = f.getSubfieldContent('r');
      if (nap == null || nap.equals(""))
        nap = " ";
      
      Item i = new Item();
      i.invbrInternal = invbrInternal;
      i.invbr = invbr;
      i.odel = odel;
      i.autor = autor;
      i.naslov = naslov;
      i.udk = udk;
      i.mesto = mesto;
      i.izdavac = izdavac;
      i.god = god;
      i.pov = pov;
      i.vrnab = vrnab;
      i.brrac = brrac;
      i.datnab = datnab;
      i.cena = cena;
      i.datras = datras;
      i.nap = nap;
      items.add(i);
    }
  }
  
  public String getAutor(Record rec) {
    if (rec.getField("700") != null) {
      String sfa = rec.getSubfieldContent("700a");
      if (sfa == null)
        sfa = " ";
      String sfb = rec.getSubfieldContent("700b");
      if (sfb == null)
        sfb = " ";
      if (rec.getField("700").getInd2() == '1')
        return sfb + " " + sfa; 
      else
        return sfa + " " + sfb; 
    } else if (rec.getField("701") != null) {
      String sfa = rec.getSubfieldContent("701a");
      if (sfa == null)
        sfa = " ";
      String sfb = rec.getSubfieldContent("701b");
      if (sfb == null)
        sfb = " ";
      if (rec.getField("701").getInd2() == '1')
        return sfb + " " + sfa; 
      else
        return sfa + " " + sfb; 
    } else if (rec.getField("702") != null) {
      String sfa = rec.getSubfieldContent("702a");
      if (sfa == null)
        sfa = " ";
      String sfb = rec.getSubfieldContent("702b");
      if (sfb == null)
        sfb = " ";
      if (rec.getField("702").getInd2() == '1')
        return sfb + " " + sfa; 
      else
        return sfa + " " + sfb; 
    }
    return null;
  }

  public class Item implements Comparable {
    public String invbrInternal;
    public String invbr;
    public String odel;
    public String autor;
    public String naslov;
    public String udk;
    public String mesto;
    public String izdavac;
    public String god;
    public String pov;
    public String vrnab;
    public String brrac;
    public String datnab;
    public String cena;
    public String datras;
    public String nap;
    
    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item)o;
        return invbr.compareTo(b.invbr);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item>\n    <invbr>");
      buf.append(invbr);
      buf.append("</invbr>\n    <odel>");
      buf.append(odel);
      buf.append("</odel>\n    <autor>");
      buf.append(StringUtils.adjustForHTML(autor));
      buf.append("</autor>\n    <naslov>");
      buf.append(StringUtils.adjustForHTML(naslov));
      buf.append("</naslov>\n    <udk>");
      buf.append(StringUtils.adjustForHTML(udk));
      buf.append("</udk>\n    <mesto>");
      buf.append(StringUtils.adjustForHTML(mesto));
      buf.append("</mesto>\n    <izdavac>");
      buf.append(StringUtils.adjustForHTML(izdavac));
      buf.append("</izdavac>\n    <god>");
      buf.append(StringUtils.adjustForHTML(god));
      buf.append("</god>\n    <pov>");
      buf.append(StringUtils.adjustForHTML(pov));
      buf.append("</pov>\n    <vrnab>");
      buf.append(StringUtils.adjustForHTML(getVrsta(vrnab.charAt(0), brrac)));
      buf.append("</vrnab>\n    <brrac>");
      buf.append(StringUtils.adjustForHTML(brrac));
      buf.append("</brrac>\n    <datnab>");
      buf.append(datnab);
      buf.append("</datnab>\n    <cena>");
      buf.append(StringUtils.adjustForHTML(cena));
      buf.append("</cena>\n    <datras>");
      buf.append(datras);
      buf.append("</datras>\n    <nap>");
      buf.append(StringUtils.adjustForHTML(nap));
      buf.append("</nap>\n  </item>");
      return buf.toString();
    }
    
    public String getVrsta(char c, String brrac) {
      switch (c) {
        case 'a':
          return "Kupovina";
        case 'b':
          return "Razmena";
        case 'c':
          if (brrac.toUpperCase().indexOf("PM") != -1)
            return "Poklon ministarstva";
          return "Poklon";
        case 'd':
          return "Obavezni primerak";
        case 'e':
          return "Zate\u010deni (stari) fond";
        case 'f':
          return "Sopstvena izdanja";
      }
      return " ";
    }
  }

  Date startDate;
  Date endDate;
  int fond = FOND_UNDEFINED;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  List items;
}
