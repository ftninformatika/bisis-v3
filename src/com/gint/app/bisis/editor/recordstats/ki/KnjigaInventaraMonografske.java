package com.gint.app.bisis.editor.recordstats.ki;

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
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

public class KnjigaInventaraMonografske extends RecordReport {

  public void execute() {
    try {
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate");
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 28759;
      items = new ArrayList(totalRecords);
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
//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("D:/BISIS/vojvodina/Kikinda/zapisi.dat", "r");
//      //java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/bisis/zr/records-2005-09-09-11-55.dat", "r");
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
          KnjigaInventaraMonografske.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/ki/KnjigaInventaraMonografske.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void handleRecord(Record rec) {
    String type = rec.getSubfieldContent("000a");
    if (type == null || !type.startsWith("001"))
      return;
    String sig = rec.getSubfieldContent("675a");
    if (sig == null)
      sig = " ";
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = getAutor(rec); 
    if (autor == null)
      autor = "";
    autor.trim();
    String izdavac = rec.getSubfieldContent("210c");
    if (izdavac == null)
      izdavac = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = "";
    String brsveske = rec.getSubfieldContent("200v");
    if (brsveske == null)
      brsveske = "";
    
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
    if (opis.length() > 0)
      opis.append(". ");
    opis.append(naslov);
    opis.append(", ");
    opis.append(brsveske);
    if (brsveske.length() > 0)
      opis.append(", ");
    opis.append(mesto);
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(izdavac);
    if (izdavac.length() > 0)
      opis.append(", ");
    opis.append(god);
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
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
        continue;
      }
      if (invDate.compareTo(startDate) < 0 || invDate.compareTo(endDate) > 0)
        continue;
      
      String nabavka = " ";
      String vrnab = f.getSubfieldContent('v');
      if (vrnab == null || vrnab.equals(""))
        vrnab = " ";
      
      String dobavljac = f.getSubfieldContent('2');
      if (dobavljac == null)
        dobavljac = "";
      
      if (vrnab.equals("c") || vrnab.equals("p")) {
        nabavka = "poklon";
        if (dobavljac.length() > 0)
          nabavka += ", " + dobavljac;
      } else if (vrnab.equals("a") || vrnab.equals("k")) {
        nabavka = "kupovina";
        if (dobavljac.length() > 0)
          nabavka += ", " + dobavljac;
        Subfield sf9961 = f.getSubfield('1');
        if (sf9961 != null) {
          Subsubfield ssf9961m = sf9961.getSubsubfield('m');
          if (ssf9961m != null && !ssf9961m.getContent().equals("")) {
            nabavka += ", " + ssf9961m.getContent();
          }
        }
      } else if (vrnab.equals("b")) {
        nabavka = "razmena";
      } else if (vrnab.equals("d")) {
        nabavka = "obavezni primerak";
      } else if (vrnab.equals("e")) {
        nabavka = "zate\u010deni fond";
      } else if (vrnab.equals("f") || vrnab.equals("s")) {
        nabavka = "sopstvena izdanja";
      } else if (vrnab.equals("o")) {
        nabavka = "otkup";
      }
      
      // check inv num
      Subfield sf996f = f.getSubfield('f');
      if (sf996f == null || sf996f.getContent().length() < 5) {
        noInvNum++;
        continue;
      }
      String invbrInternal = sf996f.getContent();
      String invbr = invbrInternal.substring(4);
      
      String pov = f.getSubfieldContent('s');
      if (pov == null || pov.equals(""))
        pov = " ";

      String cena = f.getSubfieldContent('3');
      if (cena == null || cena.equals(""))
        cena = " ";
      String nap = f.getSubfieldContent('r');
      if (nap == null || nap.equals(""))
        nap = " ";
      
      String ogr = f.getSubfieldContent('w');
      if (ogr == null || ogr.equals(""))
        ogr = " ";
      
      Item i = new Item();
      i.invbr = invbr;
      i.datum = invDate;
      i.opis = opis.toString();
      i.povez = pov;
      i.dim = dim;
      i.nabavka = nabavka;
      i.cena = cena;
      i.sig = sig;
      i.napomena = nap;
      i.ogr = ogr;
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
    public String invbr;
    public Date datum;
    public String opis;
    public String povez;
    public String dim;
    public String nabavka;
    public String cena;
    public String sig;
    public String napomena;
    public String ogr;
    
    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item)o;
        return invbr.compareTo(b.invbr);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item>\n    <rbr>");
      buf.append(invbr);
      buf.append("</rbr>\n    <datum>");
      buf.append(sdf.format(datum));
      buf.append("</datum>\n    <opis>");
      buf.append(StringUtils.adjustForHTML(opis));
      buf.append("</opis>\n    <povez>");
      buf.append(povez);
      buf.append("</povez>\n    <dim>");
      buf.append(StringUtils.adjustForHTML(dim));
      buf.append("</dim>\n    <nabavka>");
      buf.append(StringUtils.adjustForHTML(nabavka));
      buf.append("</nabavka>\n    <cena>");
      buf.append(StringUtils.adjustForHTML(cena));
      buf.append("</cena>\n    <signatura>");
      buf.append(StringUtils.adjustForHTML(sig));
      buf.append("</signatura>\n    <napomena>");
      buf.append(StringUtils.adjustForHTML(napomena));
      buf.append("</napomena>\n    <ogr>");
      buf.append(ogr);
      buf.append("</ogr>\n  </item>");
      return buf.toString();
    }
    
  }

  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  List items;
}
