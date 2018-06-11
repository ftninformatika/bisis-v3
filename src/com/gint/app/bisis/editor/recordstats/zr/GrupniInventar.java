package com.gint.app.bisis.editor.recordstats.zr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
import com.gint.util.xml.XMLUtils;

public class GrupniInventar extends RecordReport {

  public void execute() {
    try {
      nf = NumberFormat.getInstance(Locale.GERMANY);
      nf.setMinimumFractionDigits(2);
      nf.setMaximumFractionDigits(2);
      startDate = (Date)params.get("startDate"); 
      endDate = (Date)params.get("endDate"); 
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 130158;
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

//      java.io.RandomAccessFile in = new java.io.RandomAccessFile("/home/branko/BISIS-sites/gbzr/records.dat", "r");
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

      List itemList = new ArrayList();
      itemList.addAll(items.values());
      addTotals(itemList);
      Collections.sort(itemList);

      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = itemList.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      buff.append("\n</report>\n");
      
      if (noInvDate > 0)
        problems.append("Ukupno " + noInvDate + " zapisa bez datuma ra\u010duna (9961q)\n");
      
      params.put("today", sdf.format(new Date()));

      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          StatistikaNabavke.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/zr/GrupniInventar.jasper")
          .openStream(), params, dataSource);
      rp.setReport(jp);
      rp.setProblems(problems.toString());
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void handleRecord(Record rec) {
    Subfield sf675a = rec.getSubfield("675a");
    if (sf675a == null) {
      problems.append("Nedostaje 675a (UDK broj) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
      noSignature++;
      return;
    }
    String sig = sf675a.getContent().trim();
    char udkDigit = getFirstDigit(sig);
    if (udkDigit == ' ') {
      problems.append("Neispravan 675a (UDK broj) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
      noSignature++;
      return;
    }
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
      // check inv num
      Subfield sff = f.getSubfield('f');
      if (sff == null || sff.getContent().equals("") || sff.getContent().length() < 4) {
        problems.append("Nedostaje ili neispravan 996f (inventarni broj) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noInvNum++;
        continue;
      }
      // check price
      Subfield sf3 = f.getSubfield('3');
      if (sf3 == null || sf3.getContent().equals("")) {
        problems.append("Nedostaje 9963 (cena) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noPrice++;
        continue;
      }
      double price = 0d;
      try {
        price = Double.parseDouble(sf3.getContent().trim());
      } catch (Exception ex) {
        problems.append("Neispravan 9963 (cena) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
        noPrice++;
        continue;
      }

      String code = sff.getContent().substring(0, 4);
      String invBr = sff.getContent().substring(4);
      Item i = getItem(code);
      i.value += price;
      if (i.ibmin.compareTo(invBr) > 0)
        i.ibmin = invBr;
      if (i.ibmax.compareTo(invBr) < 0)
        i.ibmax = invBr;
      String jez = code.substring(2);
      if (jez.equals("01") || jez.equals("04")) {
        i.srpski++;
        if (sff.getContent().startsWith("01"))
          System.out.println(i.srpski + ":" + sff.getContent());
      } if (jez.equals("02") || jez.equals("05"))
        i.madjarski++;
      if (jez.equals("03"))
        i.strani++;
      if (sig.indexOf("-93") != -1 || sig.indexOf(".053.2") != -1)
        i.dec++;
      if (sig.startsWith("087.5"))
        i.slik++;
      else {
        if (udkDigit == '8') {
          int pos = sig.indexOf('8');
          if (pos < sig.length() - 1) {
            String test = sig.substring(pos, pos+2);
            if (test.equals("81") || test.equals("80")) {
              i.udk801++;
            }
          }
        } else {
          switch (udkDigit) {
            case '0':
              i.udk0++;
              break;
            case '1':
              i.udk1++;
              break;
            case '2':
              i.udk2++;
              break;
            case '3':
              i.udk3++;
              break;
            case '4':
              i.udk4++;
              break;
            case '5':
              i.udk5++;
              break;
            case '6':
              i.udk6++;
              break;
            case '7':
              i.udk7++;
              break;
            case '8':
              i.udk8++;
              break;
            case '9':
              i.udk9++;
              break;
          }
        }
      }
    } 
  }

  private Item getItem(String code) {
    Item i = (Item)items.get(code);
    if (i != null)
      return i;
    i = new Item();
    i.code = code;
    items.put(code, i);
    return i;
  }
  
  public char getFirstDigit(String s) {
    if (s.length() == 0)
      return ' ';
    int pos = 0;
    if (s.charAt(0) == '(') {
      pos = s.indexOf(')') + 1;
      if (pos == 0 || pos == s.length())
        pos = 1;
    }
    while (pos < s.length() && !Character.isDigit(s.charAt(pos)))
      pos++;
    if (pos == s.length())
      return ' ';
    return s.charAt(pos);
  }
  
  public class Item implements Comparable {
    public String code;
    public String ibmin = "99999999999";
    public String ibmax = "00000000000";
    public int udk0;
    public int udk1;
    public int udk2;
    public int udk3;
    public int udk4;
    public int udk5;
    public int udk6;
    public int udk7;
    public int udk8;
    public int udk801;
    public int udk9;
    public int slik;
    public int dec;
    public int srpski;
    public int madjarski;
    public int strani;
    public double value;
    public boolean isTotal = false;

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item)o;
        return code.compareTo(b.code);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item code=\"");
      buf.append(code);
      buf.append("\" name=\"");
      buf.append(getName(code));
      buf.append("\" isTotal=\"");
      buf.append(isTotal ? "true" : "false");
      buf.append("\">\n    <ibmin>");
      buf.append(ibmin);
      buf.append("</ibmin>\n    <ibmax>");
      buf.append(ibmax);
      buf.append("</ibmax>\n    <udk0>");
      buf.append(udk0);
      buf.append("</udk0>\n    <udk1>");
      buf.append(udk1);
      buf.append("</udk1>\n    <udk2>");
      buf.append(udk2);
      buf.append("</udk2>\n    <udk3>");
      buf.append(udk3);
      buf.append("</udk3>\n    <udk4>");
      buf.append(udk4);
      buf.append("</udk4>\n    <udk5>");
      buf.append(udk5);
      buf.append("</udk5>\n    <udk6>");
      buf.append(udk6);
      buf.append("</udk6>\n    <udk7>");
      buf.append(udk7);
      buf.append("</udk7>\n    <udk8>");
      buf.append(udk8);
      buf.append("</udk8>\n    <udk801>");
      buf.append(udk801);
      buf.append("</udk801>\n    <udk9>");
      buf.append(udk9);
      buf.append("</udk9>\n    <slik>");
      buf.append(slik);
      buf.append("</slik>\n    <ukup>");
      buf.append(getTotal());
      buf.append("</ukup>\n    <dec>");
      buf.append(dec);
      buf.append("</dec>\n    <srpski>");
      buf.append(srpski);
      buf.append("</srpski>\n    <madjarski>");
      buf.append(madjarski);
      buf.append("</madjarski>\n    <strani>");
      buf.append(strani);
      buf.append("</strani>\n    <vrednost>");
      buf.append(nf.format(value));
      buf.append("</vrednost>\n  </item>");
      return buf.toString();
    }
    
    public int getTotal() {
      return udk0 + udk1 + udk2 + udk3 + udk4 + udk5 + udk6 + udk7 + udk8 + udk801 + udk9;
    }
    
    public void add(Item i) {
      udk0 += i.udk0;
      udk1 += i.udk1;
      udk2 += i.udk2;
      udk3 += i.udk3;
      udk4 += i.udk4;
      udk5 += i.udk5;
      udk6 += i.udk6;
      udk7 += i.udk7;
      udk8 += i.udk8;
      udk801 += i.udk801;
      udk9 += i.udk9;
      slik += i.slik;
      dec += i.dec;
      srpski += i.srpski;
      madjarski += i.madjarski;
      strani += i.strani;
      value += i.value;
    }
  }
  
  public String getName(String sigla) {
    if (sigla.equals("0101"))
      return "De\u010dje - srpski";
    if (sigla.equals("0102"))
      return "De\u010dje - ma\u0111arski";
    if (sigla.equals("0104"))
      return "De\u010dje - slik. srpski";
    if (sigla.equals("0105"))
      return "De\u010dje - slik. ma\u0111arski";
    if (sigla.equals("0103"))
      return "De\u010dje - strani";
    if (sigla.equals("0197"))
      return "Vrti\u0107i";
    if (sigla.equals("0198"))
      return "Bolnica";
    if (sigla.equals("0201"))
      return "Nau\u010dno - srpski";
    if (sigla.equals("0206"))
      return "Nau\u010dno - srpski VP\u0160";
    if (sigla.equals("0299"))
      return "Nau\u010dno - srpski PTF";
    if (sigla.equals("0202"))
      return "Nau\u010dno - ma\u0111arski";
    if (sigla.equals("0203"))
      return "Nau\u010dno - strani";
    if (sigla.equals("0301"))
      return "Zavi\u010dajno - srpski";
    if (sigla.equals("0302"))
      return "Zavi\u010dajno - ma\u0111arski";
    if (sigla.equals("0303"))
      return "Zavi\u010dajno - strani";
    if (sigla.equals("0401"))
      return "Pozajmno - srpski";
    if (sigla.equals("0402"))
      return "Pozajmno - ma\u0111arski";
    if (sigla.equals("0403"))
      return "Pozajmno - strani";
    if (sigla.equals("01__"))
      return "De\u010dje / ukupno";
    if (sigla.equals("02__"))
      return "Nau\u010dno / ukupno";
    if (sigla.equals("03__"))
      return "Zavi\u010dajno / ukupno";
    if (sigla.equals("04__"))
      return "Pozajmno / ukupno";
    if (sigla.equals("____"))
      return "UKUPNO";
    return sigla;
  }
  
  public void addTotals(List itemList) {
    Item total1 = new Item();
    total1.isTotal = true;
    total1.code = "01__";
    total1.ibmin = "    ";
    total1.ibmax = "    ";
    Iterator it = itemList.iterator();
    while (it.hasNext()) {
      Item i = (Item)it.next();
      if (!i.code.startsWith("01"))
        continue;
      total1.add(i);
    }

    Item total2 = new Item();
    total2.isTotal = true;
    total2.code = "02__";
    total2.ibmin = "    ";
    total2.ibmax = "    ";
    it = itemList.iterator();
    while (it.hasNext()) {
      Item i = (Item)it.next();
      if (!i.code.startsWith("02"))
        continue;
      total2.add(i);
    }
    
    Item total3 = new Item();
    total3.isTotal = true;
    total3.code = "03__";
    total3.ibmin = "    ";
    total3.ibmax = "    ";
    it = itemList.iterator();
    while (it.hasNext()) {
      Item i = (Item)it.next();
      if (!i.code.startsWith("03"))
        continue;
      total3.add(i);
    }

    Item total4 = new Item();
    total4.isTotal = true;
    total4.code = "04__";
    total4.ibmin = "    ";
    total4.ibmax = "    ";
    it = itemList.iterator();
    while (it.hasNext()) {
      Item i = (Item)it.next();
      if (!i.code.startsWith("04"))
        continue;
      total4.add(i);
    }

    Item total = new Item();
    total.isTotal = true;
    total.code = "____";
    total.ibmin = "    ";
    total.ibmax = "    ";
    total.add(total1);
    total.add(total2);
    total.add(total3);
    total.add(total4);
    
    itemList.add(total1);
    itemList.add(total2);
    itemList.add(total3);
    itemList.add(total4);
    itemList.add(total);
  }
  
  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noSignature = 0;
  int noPrice = 0;
  int noInvDate = 0;
  int noBranch = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  NumberFormat nf;
  HashMap items = new HashMap();
}
