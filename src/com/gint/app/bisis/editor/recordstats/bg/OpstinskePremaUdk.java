package com.gint.app.bisis.editor.recordstats.bg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;

public class OpstinskePremaUdk extends RecordReport {

  public static final boolean DEBUG = false; 
  
  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    log = new StringBuffer();
    ogrMap.clear();
    ukupnoNaslova.clear();
    ukupnoSlikovnica.clear();
    try {
      if (DEBUG) {
        totalRecords = 10337;
        int count = 0;
        rp.setMaximum(totalRecords);
        rp.setValue(0);
        java.io.RandomAccessFile in = new java.io.RandomAccessFile(
            "/home/branko/records.dat", "r");
        String line = "";
        while ((line = in.readLine()) != null) {
          line = line.trim();
          rp.setValue(++count);
          if (line.equals(""))
            continue;
          try {
            Record rec = RecordFactory.fromUNIMARC(line);
            handleRecord(rec);
          } catch (Exception ex) {
            System.err.println("Prso zapis: " + count);
          }
        }
        in.close();
      } else {
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
      }

      List items = new ArrayList();
      items.addAll(ogrMap.values());
      Collections.sort(items);
      items.add(makeSum(items));

      params.put("today", sdf.format(new Date()));
      JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

      JasperPrint jp = JasperFillManager.fillReport(
          StatistikaPoNacinuNabavke.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/bg/OpstinskePremaUdk.jasper")
          .openStream(), params, dataSource);
      
      rp.setReport(jp);
      if (log.length() > 0)
        rp.setProblems(log.toString());
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void handleRecord(Record rec) {
    if (rec == null)
      return;
    String RN = rec.getSubfieldContent("001e");
    Iterator it996 = rec.getFields("996").iterator();
    List naciniNabavke = new ArrayList();
    boolean recordAddedToSum = false;
    String currentUdk = null;
    while (it996.hasNext()) {
      Field f996 = (Field)it996.next();
      Subfield sf996v = f996.getSubfield('v');

      // ako je nacin nabavke = zateceno stanje, primerak ne ulazi u statistiku bez obzira kad je inventarisan
      if (sf996v != null && sf996v.getContent() != null && sf996v.getContent().trim().length() == 0) {
        String nacinNabavke = sf996v.getContent().trim();
        if ("t".equalsIgnoreCase(nacinNabavke))
          continue;
        System.out.println("ignorisem");
      }

      Subfield sf996o = f996.getSubfield('o');
      if (sf996o == null || sf996o.getContent() == null || sf996o.getContent().trim().length() == 0)
        continue;
      String sDatInv = sf996o.getContent().trim();
      Date datInv = null;
      try {
        datInv = intern.parse(sDatInv);
      } catch (Exception ex) {
        log.append("RN:" + RN + " neispravan datum inventarisanja: " + sDatInv + "\n");
        continue;
      }
      if (datInv.before(startDate) || datInv.after(endDate))
        continue;

      boolean error = false;
      // inv. broj
      String invBroj = null;
      Subfield sf996f = f996.getSubfield('f');
      if (sf996f == null || sf996f.getContent() == null || sf996f.getContent().trim().length() == 0) {
        log.append("RN: " + RN + " nedostaje inventarni broj\n");
        error = true;
      } else
        invBroj = sf996f.getContent().trim();
      Subfield sf996w = f996.getSubfield('w');
      String ogranakID = null;
      if (sf996w == null || sf996w.getContent() == null || sf996w.getContent().trim().length() == 0) {
        if (Environment.getBgNerazvrstani())
          ogranakID = "\u043d\u0435\u0440\u0430\u0437\u0432\u0440."; // nerazvr.
        else {
          log.append("RN: " + RN + " nedostaje ogranak za IN=" + invBroj + "\n");
          error = true;
        }
      } else
        ogranakID = sf996w.getContent().trim();
      Subfield sf996d = f996.getSubfield('d');
      if (sf996d == null)
        continue;
      Subsubfield ssf996du = sf996d.getSubsubfield('u');
      String udk = null;
      if (ssf996du == null || ssf996du.getContent() == null || ssf996du.getContent().trim().length() == 0)
        udk = currentUdk;
      else {
        udk = ssf996du.getContent().trim();
        currentUdk = udk;
      }
      if (udk == null) {
        log.append("RN: " + RN + " nedostaje 996du za IN=" + invBroj + "\n");
        error = true;
      }
      
      if (error)
        continue;
      
      char digit = getUdkClass(udk);
      if (digit == 's' && "99".equals(invBroj.substring(2, 4)))
        digit = 's';
      else if (digit == 's')
        digit = 'd';
      Ogranak o = getOgranak(ogranakID);
      o.add(RN, digit);
    }
  }
  
  public class Ogranak implements Comparable {
    public String ogr;
    public int n0, p0;
    public int n1, p1;
    public int n2, p2;
    public int n3, p3;
    public int n5, p5;
    public int n6, p6;
    public int n7, p7;
    public int n8d, p8d;
    public int n8s, p8s;
    public int n9, p9;
    public int nd, pd;
    public int nu, pu;
    public int ns, ps;
    public HashSet set0 = new HashSet();
    public HashSet set1 = new HashSet();
    public HashSet set2 = new HashSet();
    public HashSet set3 = new HashSet();
    public HashSet set5 = new HashSet();
    public HashSet set6 = new HashSet();
    public HashSet set7 = new HashSet();
    public HashSet set8d = new HashSet();
    public HashSet set8s = new HashSet();
    public HashSet set9 = new HashSet();
    public HashSet setd = new HashSet();
    public HashSet setu = new HashSet();
    public HashSet sets = new HashSet();
    public int compareTo(Object o) {
      if (o instanceof Ogranak) {
        Ogranak og1 = (Ogranak)o;
        return ogr.compareTo(og1.ogr);
      }
      return 0;
    }
    public void add(String rn, char digit) {
      switch (digit) {
        case '0':
          p0++;
          if (rn != null)
            if (!set0.contains(rn)) {
              n0++;
              set0.add(rn);
            }
          break;
        case '1':
          p1++;
          if (rn != null)
            if (!set1.contains(rn)) {
              n1++;
              set1.add(rn);
            }
          break;
        case '2':
          p2++;
          if (rn != null)
            if (!set2.contains(rn)) {
              n2++;
              set2.add(rn);
            }
          break;
        case '3':
          p3++;
          if (rn != null)
            if (!set3.contains(rn)) {
              n3++;
              set3.add(rn);
            }
          break;
        case '5':
          p5++;
          if (rn != null)
            if (!set5.contains(rn)) {
              n5++;
              set5.add(rn);
            }
          break;
        case '6':
          p6++;
          if (rn != null)
            if (!set6.contains(rn)) {
              n6++;
              set6.add(rn);
            }
          break;
        case '7':
          p7++;
          if (rn != null)
            if (!set7.contains(rn)) {
              n7++;
              set7.add(rn);
            }
          break;
        case '8':
          p8s++;
          if (rn != null)
            if (!set8s.contains(rn)) {
              n8s++;
              set8s.add(rn);
            }
          break;
        case 'h':
          p8d++;
          if (rn != null)
            if (!set8d.contains(rn)) {
              n8d++;
              set8d.add(rn);
            }
          break;
        case '9':
          p9++;
          if (rn != null)
            if (!set9.contains(rn)) {
              n9++;
              set9.add(rn);
            }
          break;
        case 'd':
          pd++;
          if (rn != null)
            if (!setd.contains(rn)) {
              nd++;
              setd.add(rn);
            }
          break;
        case 's':
          ps++;
          if (rn != null)
            if (!sets.contains(rn)) {
              ns++;
              sets.add(rn);
            }
          break;
      }
      if (digit != 's') {
        pu++;
        if (rn != null) {
          if (!setu.contains(rn)) {
            nu++;
            setu.add(rn);
          }
          if (!ukupnoNaslova.contains(rn))
            ukupnoNaslova.add(rn);
        }
      } else {
        if (!ukupnoSlikovnica.contains(rn))
          ukupnoSlikovnica.add(rn);
      }
    }
    public String getOgr() {
      return ogr;
    }
    public void setOgr(String ogr) {
      this.ogr = ogr;
    }
    public int getN0() {
      return n0;
    }
    public void setN0(int n0) {
      this.n0 = n0;
    }
    public int getP0() {
      return p0;
    }
    public void setP0(int p0) {
      this.p0 = p0;
    }
    public int getN1() {
      return n1;
    }
    public void setN1(int n1) {
      this.n1 = n1;
    }
    public int getP1() {
      return p1;
    }
    public void setP1(int p1) {
      this.p1 = p1;
    }
    public int getN2() {
      return n2;
    }
    public void setN2(int n2) {
      this.n2 = n2;
    }
    public int getP2() {
      return p2;
    }
    public void setP2(int p2) {
      this.p2 = p2;
    }
    public int getN3() {
      return n3;
    }
    public void setN3(int n3) {
      this.n3 = n3;
    }
    public int getP3() {
      return p3;
    }
    public void setP3(int p3) {
      this.p3 = p3;
    }
    public int getN5() {
      return n5;
    }
    public void setN5(int n5) {
      this.n5 = n5;
    }
    public int getP5() {
      return p5;
    }
    public void setP5(int p5) {
      this.p5 = p5;
    }
    public int getN6() {
      return n6;
    }
    public void setN6(int n6) {
      this.n6 = n6;
    }
    public int getP6() {
      return p6;
    }
    public void setP6(int p6) {
      this.p6 = p6;
    }
    public int getN7() {
      return n7;
    }
    public void setN7(int n7) {
      this.n7 = n7;
    }
    public int getP7() {
      return p7;
    }
    public void setP7(int p7) {
      this.p7 = p7;
    }
    public int getN8d() {
      return n8d;
    }
    public void setN8d(int n8d) {
      this.n8d = n8d;
    }
    public int getP8d() {
      return p8d;
    }
    public void setP8d(int p8d) {
      this.p8d = p8d;
    }
    public int getN8s() {
      return n8s;
    }
    public void setN8s(int n8s) {
      this.n8s = n8s;
    }
    public int getP8s() {
      return p8s;
    }
    public void setP8s(int p8s) {
      this.p8s = p8s;
    }
    public int getN9() {
      return n9;
    }
    public void setN9(int n9) {
      this.n9 = n9;
    }
    public int getP9() {
      return p9;
    }
    public void setP9(int p9) {
      this.p9 = p9;
    }
    public int getNd() {
      return nd;
    }
    public void setNd(int nd) {
      this.nd = nd;
    }
    public int getPd() {
      return pd;
    }
    public void setPd(int pd) {
      this.pd = pd;
    }
    public int getNu() {
      return nu;
    }
    public void setNu(int nu) {
      this.nu = nu;
    }
    public int getPu() {
      return pu;
    }
    public void setPu(int pu) {
      this.pu = pu;
    }
    public int getNs() {
      return ns;
    }
    public void setNs(int ns) {
      this.ns = ns;
    }
    public int getPs() {
      return ps;
    }
    public void setPs(int ps) {
      this.ps = ps;
    }
    
  }
  
  private Ogranak getOgranak(String ogrID) {
    Ogranak o = (Ogranak)ogrMap.get(ogrID);
    if (o == null) {
      o = new Ogranak();
      o.ogr = ogrID;
      ogrMap.put(ogrID, o);
    }
    return o;
  }
  
  private Ogranak makeSum(List items) {
    Ogranak sum = new Ogranak();
    sum.ogr = "\u0423\u041a\u0423\u041f\u041d\u041e"; // UKUPNO
    for (int i = 0; i < items.size(); i++) {
      Ogranak o = (Ogranak)items.get(i);
      sum.n0 += o.n0; sum.p0 += o.p0;
      sum.n1 += o.n1; sum.p1 += o.p1;
      sum.n2 += o.n2; sum.p2 += o.p2;
      sum.n3 += o.n3; sum.p3 += o.p3;
      sum.n5 += o.n5; sum.p5 += o.p5;
      sum.n6 += o.n6; sum.p6 += o.p6;
      sum.n7 += o.n7; sum.p7 += o.p7;
      sum.n8s += o.n8s; sum.p8s += o.p8s;
      sum.n8d += o.n8d; sum.p8d += o.p8d;
      sum.n9 += o.n9; sum.p9 += o.p9;
      sum.nd += o.nd; sum.pd += o.pd;
      sum.ns += o.ns; sum.ps += o.ps;
      sum.nu += o.nu; sum.pu += o.pu;
    }
    sum.nu = ukupnoNaslova.size();
    sum.ns = ukupnoSlikovnica.size();
    return sum;
  }
  
  /**
   * Vraca sledece vrednosti:
   * s - slikovnice
   * d - decja
   * h - 8-domaca
   * 8 - 8-strana
   * 0,1,2,3,5,6,7,9 - 0,1,2,3,5,6,7,9
   * @param s UDK broj
   * @return
   */
  private char getUdkClass(String s) {
    if (s.startsWith("087.5"))
      return 's';
    Matcher m1 = pDecje1.matcher(s);
    Matcher m2 = pDecje2.matcher(s);
    Matcher m3 = pDecje3.matcher(s);
    Matcher m4 = pDecje4.matcher(s);
    if (m1.matches() || m2.matches() || m3.matches() || m4.matches())
      return 'd';
    if (s.startsWith("821.163.41"))
      return 'h';
    return getFirstDigit(s);
  }
  
  private char getFirstDigit(String s) {
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
  private int totalRecords = 0;
  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  private Map ogrMap = new HashMap();
  private Set ukupnoNaslova = new HashSet();
  private Set ukupnoSlikovnica = new HashSet();
  private StringBuffer log;
  private Pattern pDecje1 = Pattern.compile(".*\\(.*\\.053\\.2\\).*");
  private Pattern pDecje2 = Pattern.compile(".*\\(.*\\.053\\.6\\).*");
  private Pattern pDecje3 = Pattern.compile(".*-93");
  private Pattern pDecje4 = Pattern.compile(".*-93-.*");
}
