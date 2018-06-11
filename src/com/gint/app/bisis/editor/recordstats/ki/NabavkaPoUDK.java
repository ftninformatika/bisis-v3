package com.gint.app.bisis.editor.recordstats.ki;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class NabavkaPoUDK extends RecordReport {
  
  public NabavkaPoUDK() {
    p80_1 = Pattern.compile(".*80");
    p80_2 = Pattern.compile(".*81");
    p80_3 = Pattern.compile(".*82.*\\(091\\)");
    p80_4 = Pattern.compile(".*82.*\\.0");
    pDecje1 = Pattern.compile(".*\\(.*\\.053\\.2\\).*");
    pDecje2 = Pattern.compile(".*\\(.*\\.053\\.6\\).*");
    pDecje3 = Pattern.compile(".*-93");
    branches.put("01", new Branch("01"));
    branches.put("02", new Branch("02"));
    branches.put("03", new Branch("03"));
    branches.put("04d", new Branch("04d"));
    branches.put("04o", new Branch("04o"));
    branches.put("05d", new Branch("05d"));
    branches.put("05o", new Branch("05o"));
    branches.put("06d", new Branch("06d"));
    branches.put("06o", new Branch("06o"));
    branches.put("07d", new Branch("07d"));
    branches.put("07o", new Branch("07o"));
    branches.put("08d", new Branch("08d"));
    branches.put("08o", new Branch("08o"));
    branches.put("09d", new Branch("09d"));
    branches.put("09o", new Branch("09o"));
    branches.put("10d", new Branch("10d"));
    branches.put("10o", new Branch("10o"));
    branches.put("11d", new Branch("11d"));
    branches.put("11o", new Branch("11o"));
    branches.put("12d", new Branch("12d"));
    branches.put("12o", new Branch("12o"));
    branches.put("13", new Branch("13"));
    branches.put("14", new Branch("14"));
  }
  
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

      List branchList = new ArrayList();
      branchList.addAll(branches.values());
      addTotals(branchList);
      Collections.sort(branchList);
      
      StringBuffer buff = new StringBuffer();
      buff.append("<report>");
      Iterator it = branchList.iterator();
      while (it.hasNext())
        buff.append(it.next().toString());
      
      buff.append("</report>\n");

      System.out.println(buff);
      System.out.println("noUDC: " + noUDC);
      System.out.println("noBranch: " + noBranch);
      System.out.println("noInvDate: " + noInvDate);
      System.out.println("noInvNum: " + noInvNum);
      
      params.put("today", sdf.format(new Date()));
      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/branch");

      JasperPrint jp = JasperFillManager.fillReport(
          NabavkaPoUDK.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/ki/NabavkaPoUDK.jasper")
          .openStream(), params, dataSource);
      rp.setReport(jp);
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void handleRecord(Record rec) {
    int language = LANG_OTHER;
    String sf101a = rec.getSubfieldContent("101a");
    if ("scc".equals(sf101a))
      language = LANG_SER;
    if ("scr".equals(sf101a))
      language = LANG_SER;
    if ("hun".equals(sf101a))
      language = LANG_HUN;
    Subfield sf675a = rec.getSubfield("675a");
    if (sf675a == null) {
      noUDC++;
      return;
    }
    String sig = sf675a.getContent().trim();
    char c = getFirstDigit(sig);
    if (c == ' ') {
      noUDC++;
      return;
    }
    if (sig.startsWith("087.5"))
      c = 'x'; // slikovnica
    if (c == '8') {
      Matcher m1 = p80_1.matcher(sig);
      Matcher m2 = p80_2.matcher(sig);
      Matcher m3 = p80_3.matcher(sig);
      Matcher m4 = p80_4.matcher(sig);
      if (m1.matches() || m2.matches() || m3.matches() || m4.matches())
        c = 'a';
      else 
        c = 'b';
    }
    boolean isDecje = false;
    Matcher m1 = pDecje1.matcher(sig);
    Matcher m2 = pDecje2.matcher(sig);
    Matcher m3 = pDecje3.matcher(sig);
    if (m1.matches() || m2.matches() || m3.matches())
      isDecje = true;
    
    int po = rec.getFields("600").size();
    po += rec.getFields("601").size();
    po += rec.getFields("602").size();
    po += rec.getFields("603").size();
    po += rec.getFields("604").size();
    po += rec.getFields("605").size();
    po += rec.getFields("606").size();
    po += rec.getFields("607").size();
    po += rec.getFields("608").size();
    po += rec.getFields("609").size();
    po += rec.getFields("610").size();
    
    
    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      Subfield sff = f.getSubfield('f');
      if (sff == null || sff.getContent().equals("") || sff.getContent().length() < 4) {
        noInvNum++;
        continue;
      }
      Subfield sfw = f.getSubfield('w');
      if (sfw == null || sfw.getContent().equals("")) {
        noBranch++;
        continue;
      }
      Subfield sfo = f.getSubfield('o');
      if (sfo == null) {
        noInvDate++;
        continue;
      }
      Date invDate = null;
      try {
        invDate = intern.parse(sfo.getContent().trim());
      } catch (Exception ex) {
        noInvDate++;
        continue;
      }
      if (invDate.compareTo(startDate) < 0 || invDate.compareTo(endDate) > 0)
        continue;

      String sigla = sfw.getContent();
      Branch b = getBranch(sigla, isDecje);
      b.po += po;
      switch (language) {
        case LANG_SER:
          switch (c) {
            case '0':
              b.udk0s++;
              break;
            case '1':
              b.udk1s++;
              break;
            case '2':
              b.udk2s++;
              break;
            case '3':
              b.udk3s++;
              break;
            case '5':
              b.udk5s++;
              break;
            case '6':
              b.udk6s++;
              break;
            case '7':
              b.udk7s++;
              break;
            case 'a':
              b.udk80s++;
              break;
            case 'b':
              b.udk82s++;
              break;
            case '9':
              b.udk9s++;
              break;
            case 'x':
              b.sliks++;
              break;
          }
          break;
        case LANG_HUN:
          switch (c) {
            case '0':
              b.udk0m++;
              break;
            case '1':
              b.udk1m++;
              break;
            case '2':
              b.udk2m++;
              break;
            case '3':
              b.udk3m++;
              break;
            case '5':
              b.udk5m++;
              break;
            case '6':
              b.udk6m++;
              break;
            case '7':
              b.udk7m++;
              break;
            case 'a':
              b.udk80m++;
              break;
            case 'b':
              b.udk82m++;
              break;
            case '9':
              b.udk9m++;
              break;
            case 'x':
              b.slikm++;
              break;
          }
          break;
        case LANG_OTHER:
          switch (c) {
            case '0':
              b.udk0o++;
              break;
            case '1':
              b.udk1o++;
              break;
            case '2':
              b.udk2o++;
              break;
            case '3':
              b.udk3o++;
              break;
            case '5':
              b.udk5o++;
              break;
            case '6':
              b.udk6o++;
              break;
            case '7':
              b.udk7o++;
              break;
            case 'a':
              b.udk80o++;
              break;
            case 'b':
              b.udk82o++;
              break;
            case '9':
              b.udk9o++;
              break;
            case 'x':
              b.sliko++;
              break;
          }
          break;
      }
    }
    
  }
  
  private Branch getBranch(String sigla, boolean isDecje) {
    try {
      int test = Integer.parseInt(sigla);
      if (test >= 4 && test <= 12)
        sigla = sigla + (isDecje? "d" : "o");
    } catch (Exception ex) {
    }
    Branch b = (Branch)branches.get(sigla);
    if (b != null)
      return b;
    b = new Branch();
    b.sigla = sigla;
    branches.put(sigla, b);
    return b;
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
    while (!Character.isDigit(s.charAt(pos)) && pos < s.length())
      pos++;
    if (pos == s.length())
      return ' ';
    return s.charAt(pos);
  }
  
  public class Branch implements Comparable {
    public Branch() {
    }
    
    public Branch(String sigla) {
      this.sigla = sigla;
    }
    
    public String sigla = "";
    public int udk0s = 0;
    public int udk0m = 0;
    public int udk0o = 0;
    public int udk1s = 0;
    public int udk1m = 0;
    public int udk1o = 0;
    public int udk2s = 0;
    public int udk2m = 0;
    public int udk2o = 0;
    public int udk3s = 0;
    public int udk3m = 0;
    public int udk3o = 0;
    public int udk5s = 0;
    public int udk5m = 0;
    public int udk5o = 0;
    public int udk6s = 0;
    public int udk6m = 0;
    public int udk6o = 0;
    public int udk7s = 0;
    public int udk7m = 0;
    public int udk7o = 0;
    public int udk80s = 0;
    public int udk80m = 0;
    public int udk80o = 0;
    public int udk82s = 0;
    public int udk82m = 0;
    public int udk82o = 0;
    public int udk9s = 0;
    public int udk9m = 0;
    public int udk9o = 0;
    public int sliks = 0;
    public int slikm = 0;
    public int sliko = 0;
    public int po = 0; 
    public boolean isTotal = false;

    public int compareTo(Object o) {
      if (o instanceof Branch) {
        Branch b = (Branch)o;
        return sigla.compareTo(b.sigla);
      }
      return 0;
    }
    
    public void add(Branch b) {
      udk0s += b.udk0s;
      udk1s += b.udk1s;
      udk2s += b.udk2s;
      udk3s += b.udk3s;
      udk5s += b.udk5s;
      udk6s += b.udk6s;
      udk7s += b.udk7s;
      udk80s += b.udk80s;
      udk82s += b.udk82s;
      udk9s += b.udk9s;
      udk0m += b.udk0m;
      udk1m += b.udk1m;
      udk2m += b.udk2m;
      udk3m += b.udk3m;
      udk5m += b.udk5m;
      udk6m += b.udk6m;
      udk7m += b.udk7m;
      udk80m += b.udk80m;
      udk82m += b.udk82m;
      udk9m += b.udk9m;
      udk0o += b.udk0o;
      udk1o += b.udk1o;
      udk2o += b.udk2o;
      udk3o += b.udk3o;
      udk5o += b.udk5o;
      udk6o += b.udk6o;
      udk7o += b.udk7o;
      udk80o += b.udk80o;
      udk82o += b.udk82o;
      udk9o += b.udk9o;
      sliks += b.sliks;
      slikm += b.slikm;
      sliko += b.sliko;
      po += b.po;
    }
    
    public int getTotalS() {
      return udk0s + udk1s + udk2s + udk3s + udk5s + udk6s + udk7s + udk80s + udk82s + udk9s + sliks;
    }
    
    public int getTotalM() {
      return udk0m + udk1m + udk2m + udk3m + udk5m + udk6m + udk7m + udk80m + udk82m + udk9m + slikm;
    }
    
    public int getTotalO() {
      return udk0o + udk1o + udk2o + udk3o + udk5o + udk6o + udk7o + udk80o + udk82o + udk9o + sliko;
    }
    
    public int getTotal() {
      return getTotalS() + getTotalM() + getTotalO();
    }
    
    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <branch id=\"");
      buf.append(sigla);
      buf.append("\" name=\"");
      buf.append(getName(sigla));
      buf.append("\" isTotal=\"");
      buf.append(isTotal);
      buf.append("\">\n    <udk0s>");
      buf.append(udk0s);
      buf.append("</udk0s>\n    <udk1s>");
      buf.append(udk1s);
      buf.append("</udk1s>\n    <udk2s>");
      buf.append(udk2s);
      buf.append("</udk2s>\n    <udk3s>");
      buf.append(udk3s);
      buf.append("</udk3s>\n    <udk5s>");
      buf.append(udk5s);
      buf.append("</udk5s>\n    <udk6s>");
      buf.append(udk6s);
      buf.append("</udk6s>\n    <udk7s>");
      buf.append(udk7s);
      buf.append("</udk7s>\n    <udk80s>");
      buf.append(udk80s);
      buf.append("</udk80s>\n    <udk82s>");
      buf.append(udk82s);
      buf.append("</udk82s>\n    <udk9s>");
      buf.append(udk9s);
      buf.append("</udk9s>\n    <sliks>");
      buf.append(sliks);
      buf.append("</sliks>\n    <totals>");
      buf.append(getTotalS());
      buf.append("</totals>\n    <udk0m>");
      buf.append(udk0m);
      buf.append("</udk0m>\n    <udk1m>");
      buf.append(udk1m);
      buf.append("</udk1m>\n    <udk2m>");
      buf.append(udk2m);
      buf.append("</udk2m>\n    <udk3m>");
      buf.append(udk3m);
      buf.append("</udk3m>\n    <udk5m>");
      buf.append(udk5m);
      buf.append("</udk5m>\n    <udk6m>");
      buf.append(udk6m);
      buf.append("</udk6m>\n    <udk7m>");
      buf.append(udk7m);
      buf.append("</udk7m>\n    <udk80m>");
      buf.append(udk80m);
      buf.append("</udk80m>\n    <udk82m>");
      buf.append(udk82m);
      buf.append("</udk82m>\n    <udk9m>");
      buf.append(udk9m);
      buf.append("</udk9m>\n    <slikm>");
      buf.append(slikm);
      buf.append("</slikm>\n    <totalm>");
      buf.append(getTotalM());
      buf.append("</totalm>\n    <udk0o>");
      buf.append(udk0o);
      buf.append("</udk0o>\n    <udk1o>");
      buf.append(udk1o);
      buf.append("</udk1o>\n    <udk2o>");
      buf.append(udk2o);
      buf.append("</udk2o>\n    <udk3o>");
      buf.append(udk3o);
      buf.append("</udk3o>\n    <udk5o>");
      buf.append(udk5o);
      buf.append("</udk5o>\n    <udk6o>");
      buf.append(udk6o);
      buf.append("</udk6o>\n    <udk7o>");
      buf.append(udk7o);
      buf.append("</udk7o>\n    <udk80o>");
      buf.append(udk80o);
      buf.append("</udk80o>\n    <udk82o>");
      buf.append(udk82o);
      buf.append("</udk82o>\n    <udk9o>");
      buf.append(udk9o);
      buf.append("</udk9o>\n    <sliko>");
      buf.append(sliko);
      buf.append("</sliko>\n    <totalo>");
      buf.append(getTotalO());
      buf.append("</totalo>\n    <totalu>");
      buf.append(getTotal());
      buf.append("</totalu>\n    <po>");
      buf.append(po);
      buf.append("</po>\n  </branch>");
      return buf.toString();
    }
  }
  
  public String getName(String sigla) {
    if (sigla.equals("01"))
      return "De\u010dje odeljenje";
    if (sigla.equals("02"))
      return "Odeljenje za odrasle";
    if (sigla.equals("03"))
      return "Stru\u010dne knjige";
    if (sigla.equals("04d"))
      return "Banatska Topola (D)";
    if (sigla.equals("04o"))
      return "Banatska Topola (O)";
    if (sigla.equals("05d"))
      return "Ban. Veliko Selo (D)";
    if (sigla.equals("05o"))
      return "Ban. Veliko Selo (O)";
    if (sigla.equals("06d"))
      return "Ba\u0161aid (D)";
    if (sigla.equals("06o"))
      return "Ba\u0161aid (O)";
    if (sigla.equals("07d"))
      return "I\u0111o\u0161 (D)";
    if (sigla.equals("07o"))
      return "I\u0111o\u0161 (O)";
    if (sigla.equals("08d"))
      return "Mokrin (D)";
    if (sigla.equals("08o"))
      return "Mokrin (O)";
    if (sigla.equals("09d"))
      return "Nakovo (D)";
    if (sigla.equals("09o"))
      return "Nakovo (O)";
    if (sigla.equals("10d"))
      return "Novi Kozarci (D)";
    if (sigla.equals("10o"))
      return "Novi Kozarci (O)";
    if (sigla.equals("11d"))
      return "Rusko Selo (D)";
    if (sigla.equals("11o"))
      return "Rusko Selo (O)";
    if (sigla.equals("12d"))
      return "Sajan (D)";
    if (sigla.equals("12o"))
      return "Sajan (O)";
    if (sigla.equals("13"))
      return "Zavi\u010dajno";
    if (sigla.equals("14"))
      return "Legat";
    if (sigla.equals("xx"))
      return "UKUPNO";
    return sigla;
  }
  
  public void addTotals(List branches) {
    Branch total = new Branch();
    total.sigla = "xx";
    total.isTotal = true;
    Iterator it = branches.iterator();
    while (it.hasNext()) {
      Branch b = (Branch)it.next();
      total.add(b);
    }
    branches.add(total);
  }
  
  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noUDC = 0;
  int noBranch = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  HashMap branches = new HashMap();
  private Pattern p80_1;
  private Pattern p80_2;
  private Pattern p80_3;
  private Pattern p80_4;
  private Pattern pDecje1;
  private Pattern pDecje2;
  private Pattern pDecje3;
  private static final int LANG_SER = 1;
  private static final int LANG_HUN = 2;
  private static final int LANG_OTHER = 3;
}
