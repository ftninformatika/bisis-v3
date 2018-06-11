package com.gint.app.bisis.editor.recordstats.sa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis.editor.recordstats.SignatureFormat;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;
import com.gint.util.string.LatCyrUtils;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

public class KnjigaInventara extends RecordReport {

  public void execute() {
    try {
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate");
      invNumPrefix = (String)params.get("fond");
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 40015;
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
//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/records.dat", "r");
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
          KnjigaInventara.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/sa/KnjigaInventara.jasper")
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
    String type = rec.getSubfieldContent("000a");
    if (type == null || !type.startsWith("001"))
      return;
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
    String RN = rec.getSubfieldContent("001e");
    if (RN == null)
      RN = "";
    
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
    opis.append(".");
    if (RN.length() > 0)
      opis.append("   RN: " + RN);
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
    String sig = " ";

    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();

      Subfield sf996d = f.getSubfield('d');
      if (sf996d != null)
        sig = SignatureFormat.format(sf996d);
      
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
        try {
          invDate = intern2.parse(sf996o.getContent().trim());
        } catch (Exception ex2) {
          continue;
        }
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
        nabavka = "\u043F\u043E\u043A\u043B\u043E\u043D"; //poklon
        if (dobavljac.length() > 0)
          nabavka += ", " + dobavljac;
      } else if (vrnab.equals("a") || vrnab.equals("k")) {
        nabavka = "\u043A\u0443\u043F\u043E\u0432\u0438\u043D\u0430"; // kupovina
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
        nabavka = "\u0440\u0430\u0437\u043C\u0435\u043D\u0430";  // razmena
      } else if (vrnab.equals("d")) {
        nabavka = "\u043E\u0431\u0430\u0432\u0435\u0437\u043D\u0438 \u043F\u0440\u0438\u043C\u0435\u0440\u0430\u043A"; // obavezni primerak
      } else if (vrnab.equals("e")) {
        nabavka = "\u0437\u0430\u0442\u0435\u0447\u0435\u043d\u0438 \u0444\u043e\u043d\u0434"; // zateceni fond
      } else if (vrnab.equals("f") || vrnab.equals("s")) {
        nabavka = "\u0441\u043e\u043f\u0441\u0442\u0432\u0435\u043d\u0430 \u0438\u0437\u0434\u0430\u045a\u0430"; // sopstvena izdanja
      } else if (vrnab.equals("o")) {
        nabavka = "\u043e\u0442\u043a\u0443\u043f"; // otkup
      }
      
      // check inv num
      Subfield sf996f = f.getSubfield('f');
      if (sf996f == null || sf996f.getContent().length() < 5) {
        noInvNum++;
        continue;
      }
      String invbrInternal = sf996f.getContent();
      if (!invbrInternal.startsWith(invNumPrefix))
        continue;
      
      String invbr = invbrInternal.substring(4);
      
      String pov = f.getSubfieldContent('s');
      if (pov == null || pov.equals(""))
        pov = " ";
      else
        pov = LatCyrUtils.toCyrillic(pov);

      String cena = f.getSubfieldContent('3');
      if (cena == null || cena.equals(""))
        cena = " ";
      String nap = f.getSubfieldContent('r');
      if (nap == null || nap.equals(""))
        nap = " ";
      
      String odeljenje = "O";
      if (invNumPrefix.equals("0200"))
        odeljenje = "D";
      if (invNumPrefix.equals("0301"))
        odeljenje = "N-I";
      if (invNumPrefix.equals("0302"))
        odeljenje = "N";
      if (invNumPrefix.equals("0303"))
        odeljenje = "N-III";
      if (invNumPrefix.equals("0304"))
        odeljenje = "N-IV";
      if (invNumPrefix.equals("0401"))
        odeljenje = "Z-I";
      if (invNumPrefix.equals("0402"))
        odeljenje = "Z";
      if (invNumPrefix.equals("0403"))
        odeljenje = "Z-III";
      if (invNumPrefix.equals("0404"))
        odeljenje = "Z-IV";
      
      Item i = new Item();
      i.invbr =  "" + trimZeros(invbr) + "/" + odeljenje;
      i.datum = invDate;
      i.opis = opis.toString();
      i.povez = pov;
      i.dim = dim;
      i.nabavka = nabavka;
      i.cena = cena;
      i.sig = sig;
      i.napomena = nap;
      items.add(i);
    }
  }
  
  public String getAutor(Record rec) {
    if (rec.getField("700") != null) {
      String sfa = rec.getSubfieldContent("700a");
      String sfb = rec.getSubfieldContent("700b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("701") != null) {
      String sfa = rec.getSubfieldContent("701a");
      String sfb = rec.getSubfieldContent("701b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("702") != null) {
      String sfa = rec.getSubfieldContent("702a");
      String sfb = rec.getSubfieldContent("702b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    }
    return "";
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
      buf.append("</napomena>\n  </item>");
      return buf.toString();
    }
  }
  
  public String trimZeros(String s) {
    if (s == null)
      return null;
    String retVal = s;
    while (retVal.length() > 0 && retVal.charAt(0) == '0')
      retVal = retVal.substring(1);
    return retVal;
  }
  
  public String toSentenceCase(String s) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, " -.", true);
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      if (word.length() > 0)
        retVal.append(Character.toUpperCase(word.charAt(0)) + 
            word.substring(1).toLowerCase());
        
    }
    return retVal.toString();
  }

  Date startDate;
  Date endDate;
  String invNumPrefix;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  List items;
}
