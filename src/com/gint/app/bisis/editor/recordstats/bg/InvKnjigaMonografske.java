package com.gint.app.bisis.editor.recordstats.bg;

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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

public class InvKnjigaMonografske extends RecordReport {
  
  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    range = (String)params.get("range");
    startNumber = (String)params.get("startNumber");
    endNumber = (String)params.get("endNumber");
    fondID = (String)params.get("fondID");
    int startPage = ((Integer)params.get("startPage")).intValue();
    filterByDate = startDate != null;
    items.clear();
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

      Collections.sort(items);

      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = items.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      buff.append("\n</report>\n");
      
      params.put("today", sdf.format(new Date()));
      //JRXmlDataSource dataSource = new JRXmlDataSource(
          //XMLUtils.getDocumentFromString(buff.toString()), "/report/item");
      JRBeanCollectionDataSource dataSource = 
        new JRBeanCollectionDataSource(items);

      JasperPrint jp = JasperFillManager.fillReport(
          InvKnjigaMonografske.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/bg/InvKnjigaMonografske2.jasper")
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
    String sf700a = nvl(rec.getSubfieldContent("700a"));
    String sf700b = nvl(rec.getSubfieldContent("700b"));
    String sf200a = nvl(rec.getSubfieldContent("200a"));
    String sf200h = nvl(rec.getSubfieldContent("200h"));
    String sf210a = nvl(rec.getSubfieldContent("210a"));
    String sf210c = nvl(rec.getSubfieldContent("210c"));
    String sf210d = nvl(rec.getSubfieldContent("210d"));
    String sf001e = nvl(rec.getSubfieldContent("001e"));
    
    StringBuffer buff = new StringBuffer();
    if (sf700a.length() > 0) {
      buff.append(sf700a);
      buff.append(", ");
      buff.append(sf700b);
      buff.append('\n');
    }
    buff.append("      ");
    buff.append(sf200a);
    if (sf200h.length() > 0) {
      buff.append(" : ");
      buff.append(sf200h);
    }
    buff.append(". - ");
    buff.append(sf210a);
    buff.append(" : ");
    buff.append(sf210c);
    buff.append(", ");
    buff.append(sf210d);
    buff.append('\n');
    buff.append("RN: ");
    buff.append(sf001e);
    String opis = buff.toString();
    
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
      
      Date invDate = null;
      Subfield sf996o = f.getSubfield('o');
      if (sf996o == null) {
        noInvDate++;
        if (filterByDate)
          continue;
      }
      try {
        invDate = intern.parse(sf996o.getContent().trim());
      } catch (Exception ex) {
        try {
          invDate = intern2.parse(sf996o.getContent().trim());
        } catch (Exception ex2) {
          if (filterByDate)
            continue;
        }
      }
      if (filterByDate)
        if (invDate.compareTo(startDate) < 0 || invDate.compareTo(endDate) > 0)
          continue;
      
      Subfield sf996f = f.getSubfield('f');
      if (sf996f == null || sf996f.getContent().length() < 5) {
        noInvNum++;
        if (!filterByDate)
          continue;
      }
      String invbrInternal = sf996f.getContent();
      String fond = invbrInternal.substring(2, 4);
      if (!fondID.equals(fond))
        continue;
      if (!filterByDate) {
        if (invbrInternal.compareTo(startNumber) < 0 || invbrInternal.compareTo(endNumber) > 0)
          continue;
      }

      String nabavka = " ";
      String vrnab = f.getSubfieldContent('v');
      if (vrnab == null || vrnab.equals(""))
        vrnab = " ";
      
      String dobavljac = f.getSubfieldContent('2');
      if (dobavljac == null)
        dobavljac = "";
      
      if (vrnab.equals("p")) {
        nabavka = "\u043F\u043E\u043A\u043B\u043E\u043D"; //poklon
        if (dobavljac.length() > 0)
          nabavka += ", " + dobavljac;
      } else if (vrnab.equals("k")) {
        nabavka = "\u043A\u0443\u043F\u043E\u0432\u0438\u043D\u0430"; // kupovina
        Subfield sf9961 = f.getSubfield('1');
        if (sf9961 != null) {
          String part1 = "";
          String part2 = "";
          Subsubfield ssf9961m = sf9961.getSubsubfield('m');
          if (ssf9961m != null)
            part1 = nvl(ssf9961m.getContent());
          Subsubfield ssf9961g = sf9961.getSubsubfield('g');
          if (ssf9961g != null)
            part2 = nvl(ssf9961g.getContent());
          if (part1.length() > 0 || part2.length() > 0)
            nabavka += '\n';
          if (part1.length() > 0)
            nabavka += part1;
          if (part2.length() > 0)
            nabavka += " / " + part2;
        }
      } else if (vrnab.equals("a")) {
        nabavka = "\u0440\u0430\u0437\u043C\u0435\u043D\u0430";  // razmena
      } else if (vrnab.equals("i")) {
        nabavka = "\u0438\u0437\u0434\u0430\u045a\u0430 \u0411\u0413\u0411"; // izdanja BGB
      } else if (vrnab.equals("o")) {
        nabavka = "\u043e\u0442\u043a\u0443\u043f \u0421\u0413"; // otkup SG
      } else if (vrnab.equals("r")) {
        nabavka = "\u043e\u0442\u043a\u0443\u043f \u0420\u0421"; // otkup RS
      } else if (vrnab.equals("l")) {
        nabavka = "\u043f\u043e\u043a\u043b\u043e\u043d \u0438\u0437\u0434\u0430\u0432\u0430\u0447\u0430"; // poklon izdavaca
      } else if (vrnab.equals("m")) {
        nabavka = "\u043c\u0430\u0440\u043a\u0435\u0442\u0438\u043d\u0433"; // marketing
      } else if (vrnab.equals("n")) {
        nabavka = "\u041e\u041f \u041d\u0411\u0421"; // OP NBS
      } else if (vrnab.equals("t")) {
        nabavka = "\u0437\u0430\u0442\u0435\u0447\u0435\u043d\u043e"; // zateceno
      } else if (vrnab.equals("z")) {
        nabavka = "\u0437\u0430\u043c\u0435\u043d\u0430"; // zamena
      }
      
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
      
      Item i = new Item();
      i.invbr = invbrInternal;
      i.datum = invDate;
      i.opis = opis;
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
      String sfa = nvl(rec.getSubfieldContent("700a")).trim();
      String sfb = nvl(rec.getSubfieldContent("700b")).trim();
      if (sfa.length() > 0) {
        if (sfb.length() > 0)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb.length() > 0)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("701") != null) {
      String sfa = nvl(rec.getSubfieldContent("701a")).trim();
      String sfb = nvl(rec.getSubfieldContent("701b")).trim();
      if (sfa.length() > 0) {
        if (sfb.length() > 0)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb.length() > 0)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("702") != null) {
      String sfa = nvl(rec.getSubfieldContent("702a")).trim();
      String sfb = nvl(rec.getSubfieldContent("702b")).trim();
      if (sfa.length() > 0) {
        if (sfb.length() > 0)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb.length() > 0)
          return toSentenceCase(sfb);
        else
          return "";
      }
    }
    return "";
  }

  
  public String toSentenceCase(String s) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, " -.", true);
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      if (word.length() > 0)
        retVal.append(Character.toUpperCase(word.charAt(0))
            + word.substring(1).toLowerCase());

    }
    return retVal.toString();
  }

  public String nvl(String s) {
    return s == null ? "" : s.trim();
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
    
    public String getRbr() {
      return invbr;
    }
    public String getDatum() {
      return datum == null ? "" : sdf.format(datum);
    }
    public String getOpis() {
      return opis;
    }
    public String getPovez() {
      return povez;
    }
    public String getDim() {
      return dim;
    }
    public String getNabavka() {
      return nabavka;
    }
    public String getCena() {
      return cena;
    }
    public String getSig() {
      return sig;
    }
    public String getNapomena() {
      return napomena;
    }
    public String getOgr() {
      return ogr;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item>\n    <rbr>");
      buf.append(invbr);
      buf.append("</rbr>\n    <datum>");
      buf.append(datum == null ? "" : sdf.format(datum));
      buf.append("</datum>\n    <opis>");
      buf.append(StringUtils.adjustForHTML(opis));
      buf.append("</opis>\n    <povez>");
      buf.append(povez);
      buf.append("</povez>\n    <dim>");
      buf.append(StringUtils.adjustForHTML(nvl(dim)));
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

  private Date startDate;
  private Date endDate;
  private String range;
  private String startNumber;
  private String endNumber;
  private String fondID;
  private boolean filterByDate;
  
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  List items = new ArrayList();
}
