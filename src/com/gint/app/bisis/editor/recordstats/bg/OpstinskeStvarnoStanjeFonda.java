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

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.recordstats.RecordReport;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;

public class OpstinskeStvarnoStanjeFonda extends RecordReport {

  public static final boolean DEBUG = false; 
  
  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    log = new StringBuffer();
    ogrMap.clear();
    ukupnoNaslova.clear();
    ukupnoNerashodovanihNaslova.clear();
    try {
      if (DEBUG) {
        totalRecords = 6064;
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
            "/com/gint/app/bisis/editor/recordstats/bg/OpstinskeStvarnoStanjeFonda.jasper")
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
    while (it996.hasNext()) {
      Field f996 = (Field)it996.next();
      // datum inventarisanja
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
      // ogranak
      Subfield sf996w = f996.getSubfield('w');
      String ogranakID = null;
      if (sf996w == null || sf996w.getContent() == null || sf996w.getContent().trim().length() == 0) {
        if (Environment.getBgNerazvrstani())
          ogranakID = "\u043d\u0435\u0440."; // ner.
        else {
          log.append("RN: " + RN + " nedostaje ogranak za IN=" + invBroj + "\n");
          error = true;
        }
      } else
        ogranakID = sf996w.getContent().trim();
      // status
      Subfield sf996q = f996.getSubfield('q');
      if (sf996q == null || sf996q.getContent() == null || sf996q.getContent().trim().length() == 0) {
        log.append("RN: " + RN + " nedostaje status za IN=" + invBroj + "\n");
        error = true;
      }
      
      if (error)
        continue;
      
      char status = sf996q.getContent().trim().charAt(0);
      Ogranak o = getOgranak(ogranakID);
      o.add(RN, status);
    }
  }
  
  public class Ogranak implements Comparable {
    public String ogr;
    public int n0, p0;
    public int n1, p1;
    public int n2, p2;
    public int n3, p3;
    public int n4, p4;
    public int n5, p5;
    public int n6, p6;
    public int n7, p7;
    public int n8, p8;
    public int n9, p9;
    public int na, pa;
    public int nn, pn;
    public int nu, pu;
    public int nu6, pu6;
    public HashSet set0 = new HashSet();
    public HashSet set1 = new HashSet();
    public HashSet set2 = new HashSet();
    public HashSet set3 = new HashSet();
    public HashSet set4 = new HashSet();
    public HashSet set5 = new HashSet();
    public HashSet set6 = new HashSet();
    public HashSet set7 = new HashSet();
    public HashSet set8 = new HashSet();
    public HashSet set9 = new HashSet();
    public HashSet setA = new HashSet();
    public HashSet setN = new HashSet();
    public HashSet setu = new HashSet();
    public HashSet setu6 = new HashSet();
    public int compareTo(Object o) {
      if (o instanceof Ogranak) {
        Ogranak og1 = (Ogranak)o;
        return ogr.compareTo(og1.ogr);
      }
      return 0;
    }
    public void add(String rn, char status) {
      switch (status) {
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
        case '4':
          p4++;
          if (rn != null)
            if (!set4.contains(rn)) {
              n4++;
              set4.add(rn);
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
        case 'o':
          p7++;
          if (rn != null)
            if (!set7.contains(rn)) {
              n7++;
              set7.add(rn);
            }
          break;
        case '8':
          p8++;
          if (rn != null)
            if (!set8.contains(rn)) {
              n8++;
              set8.add(rn);
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
        case 'A':
          pa++;
          if (rn != null)
            if (!setA.contains(rn)) {
              na++;
              setA.add(rn);
            }
          break;
        case 'N':
          pn++;
          if (rn != null)
            if (!setN.contains(rn)) {
              nn++;
              setN.add(rn);
            }
          break;
      }
      pu++;
      if (rn != null) {
        if (!setu.contains(rn)) {
          nu++;
          setu.add(rn);
        }
        if (!ukupnoNaslova.contains(rn))
          ukupnoNaslova.add(rn);
      }
      pu6 = pu - p6;
      if (status == '1' || status == '2') {
        if (!setu6.contains(rn)) {
          setu6.add(rn);
          nu6++;
        }
        if (!ukupnoNerashodovanihNaslova.contains(rn))
          ukupnoNerashodovanihNaslova.add(rn);
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
    public int getN4() {
      return n4;
    }
    public void setN4(int n4) {
      this.n4 = n4;
    }
    public int getP4() {
      return p4;
    }
    public void setP4(int p4) {
      this.p4 = p4;
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
    public int getN8() {
      return n8;
    }
    public void setN8(int n8) {
      this.n8 = n8;
    }
    public int getP8() {
      return p8;
    }
    public void setP8(int p8) {
      this.p8 = p8;
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
    public int getNa() {
      return na;
    }
    public void setNa(int na) {
      this.na = na;
    }
    public int getPa() {
      return pa;
    }
    public void setPa(int pa) {
      this.pa = pa;
    }
    public int getNn() {
      return nn;
    }
    public void setNn(int nn) {
      this.nn = nn;
    }
    public int getPn() {
      return pn;
    }
    public void setPn(int pn) {
      this.pn = pn;
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
    public int getNu6() {
      return nu6;
    }
    public void setNu6(int nu6) {
      this.nu6 = nu6;
    }
    public int getPu6() {
      return pu6;
    }
    public void setPu6(int pu6) {
      this.pu6 = pu6;
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
      sum.n4 += o.n4; sum.p4 += o.p4;
      sum.n5 += o.n5; sum.p5 += o.p5;
      sum.n6 += o.n6; sum.p6 += o.p6;
      sum.n7 += o.n7; sum.p7 += o.p7;
      sum.n8 += o.n8; sum.p8 += o.p8;
      sum.n9 += o.n9; sum.p9 += o.p9;
      sum.na += o.na; sum.pa += o.pa;
      sum.nn += o.nn; sum.pn += o.pn;
      sum.nu += o.nu; sum.pu += o.pu;
      sum.nu6 += o.nu6; sum.pu6 += o.pu6;
    }
   sum.nu = ukupnoNaslova.size();
   sum.nu6 = ukupnoNerashodovanihNaslova.size();
   return sum;
  }
  
  private Date startDate;
  private Date endDate;
  private int totalRecords = 0;
  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  private Map ogrMap = new HashMap();
  private Set ukupnoNaslova = new HashSet();
  private Set ukupnoNerashodovanihNaslova = new HashSet();
  private StringBuffer log;
}
