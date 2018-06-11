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

public class OpstinskePremaNacinuNabavke extends RecordReport {

  public static final boolean DEBUG = false; 
  
  public void execute() {
    startDate = (Date)params.get("startDate"); 
    endDate = (Date)params.get("endDate");
    log = new StringBuffer();
    ogrMap.clear();
    ukupnoNaslova.clear();
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
            "/com/gint/app/bisis/editor/recordstats/bg/OpstinskePremaNacinuNabavke.jasper")
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
      // nacin nabavke
      Subfield sf996v = f996.getSubfield('v');
      String nacinNabavke = null;
      if (sf996v == null || sf996v.getContent() == null || sf996v.getContent().trim().length() == 0) {
        log.append("RN: " + RN + " nedostaje na\u010din nabavke za IN=" + invBroj + "\n");
        error = true;
      } else
        nacinNabavke = sf996v.getContent().trim();
      // ogranak
      Subfield sf996w = f996.getSubfield('w');
      String ogranakID = null;
      if (sf996w == null || sf996w.getContent() == null || sf996w.getContent().trim().length() == 0) {
        if (Environment.getBgNerazvrstani())
          ogranakID = "\u043d\u0435\u0440\u0430\u0437\u0432\u0440\u0441\u0442\u0430\u043d\u0438"; // nerazvrstani
        else {
          log.append("RN: " + RN + " nedostaje ogranak za IN=" + invBroj + "\n");
          error = true;
        }
      } else
        ogranakID = sf996w.getContent().trim();
      
      if (error)
        continue;
      
      Ogranak o = getOgranak(ogranakID);
      o.add(RN, nacinNabavke);
    }
  }
  
  public class Ogranak implements Comparable {
    public String ogr;
    public int na, pa;
    public int ni, pi;
    public int nk, pk;
    public int nl, pl;
    public int nm, pm;
    public int nn, pn;
    public int no, po;
    public int np, pp;
    public int nr, pr;
    public int nt, pt;
    public int nz, pz;
    public int nu, pu;
    public HashSet aset = new HashSet();
    public HashSet iset = new HashSet();
    public HashSet kset = new HashSet();
    public HashSet lset = new HashSet();
    public HashSet mset = new HashSet();
    public HashSet nset = new HashSet();
    public HashSet oset = new HashSet();
    public HashSet pset = new HashSet();
    public HashSet rset = new HashSet();
    public HashSet tset = new HashSet();
    public HashSet zset = new HashSet();
    public HashSet uset = new HashSet();
    public int compareTo(Object o) {
      if (o instanceof Ogranak) {
        Ogranak og1 = (Ogranak)o;
        return ogr.compareTo(og1.ogr);
      }
      return 0;
    }
    public void add(String rn, String nacin) {
      char n = nacin.charAt(0);
      switch (n) {
        case 'a':
          pa++;
          if (rn != null)
            if (!aset.contains(rn)) {
              na++;
              aset.add(rn);
            }
          break;
        case 'i':
          pi++;
          if (rn != null)
            if (!iset.contains(rn)) {
              ni++;
              iset.add(rn);
            }
          break;
        case 'k':
          pk++;
          if (rn != null)
            if (!kset.contains(rn)) {
              nk++;
              kset.add(rn);
            }
          break;
        case 'l':
          pl++;
          if (rn != null)
            if (!lset.contains(rn)) {
              nl++;
              lset.add(rn);
            }
          break;
        case 'm':
          pm++;
          if (rn != null)
            if (!mset.contains(rn)) {
              nm++;
              mset.add(rn);
            }
          break;
        case 'n':
          pn++;
          if (rn != null)
            if (!nset.contains(rn)) {
              nn++;
              nset.add(rn);
            }
          break;
        case 'o':
          po++;
          if (rn != null)
            if (!oset.contains(rn)) {
              no++;
              oset.add(rn);
            }
          break;
        case 'p':
          pp++;
          if (rn != null)
            if (!pset.contains(rn)) {
              np++;
              pset.add(rn);
            }
          break;
        case 'r':
          pr++;
          if (rn != null)
            if (!rset.contains(rn)) {
              nr++;
              rset.add(rn);
            }
          break;
        case 't':
          pt++;
          if (rn != null)
            if (!tset.contains(rn)) {
              nt++;
              tset.add(rn);
            }
          break;
        case 'z':
          pz++;
          if (rn != null)
            if (!zset.contains(rn)) {
              nz++;
              zset.add(rn);
            }
          break;
      }
      if (n != 't') {
        pu++;
        if (rn != null) {
          if (!uset.contains(rn)) {
            nu++;
            uset.add(rn);
          }
          if (!ukupnoNaslova.contains(rn))
            ukupnoNaslova.add(rn);
        }
      }
    }
    public String getOgr() {
      return ogr;
    }
    public void setOgr(String ogr) {
      this.ogr = ogr;
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
    public int getNi() {
      return ni;
    }
    public void setNi(int ni) {
      this.ni = ni;
    }
    public int getPi() {
      return pi;
    }
    public void setPi(int pi) {
      this.pi = pi;
    }
    public int getNk() {
      return nk;
    }
    public void setNk(int nk) {
      this.nk = nk;
    }
    public int getPk() {
      return pk;
    }
    public void setPk(int pk) {
      this.pk = pk;
    }
    public int getNl() {
      return nl;
    }
    public void setNl(int nl) {
      this.nl = nl;
    }
    public int getPl() {
      return pl;
    }
    public void setPl(int pl) {
      this.pl = pl;
    }
    public int getNm() {
      return nm;
    }
    public void setNm(int nm) {
      this.nm = nm;
    }
    public int getPm() {
      return pm;
    }
    public void setPm(int pm) {
      this.pm = pm;
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
    public int getNo() {
      return no;
    }
    public void setNo(int no) {
      this.no = no;
    }
    public int getPo() {
      return po;
    }
    public void setPo(int po) {
      this.po = po;
    }
    public int getNp() {
      return np;
    }
    public void setNp(int np) {
      this.np = np;
    }
    public int getPp() {
      return pp;
    }
    public void setPp(int pp) {
      this.pp = pp;
    }
    public int getNr() {
      return nr;
    }
    public void setNr(int nr) {
      this.nr = nr;
    }
    public int getPr() {
      return pr;
    }
    public void setPr(int pr) {
      this.pr = pr;
    }
    public int getNt() {
      return nt;
    }
    public void setNt(int nt) {
      this.nt = nt;
    }
    public int getPt() {
      return pt;
    }
    public void setPt(int pt) {
      this.pt = pt;
    }
    public int getNz() {
      return nz;
    }
    public void setNz(int nz) {
      this.nz = nz;
    }
    public int getPz() {
      return pz;
    }
    public void setPz(int pz) {
      this.pz = pz;
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
    public HashSet getOset() {
      return oset;
    }
    public void setOset(HashSet oset) {
      this.oset = oset;
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
      sum.na += o.na; sum.pa += o.pa;
      sum.ni += o.ni; sum.pi += o.pi;
      sum.nk += o.nk; sum.pk += o.pk;
      sum.nl += o.nl; sum.pl += o.pl;
      sum.nm += o.nm; sum.pm += o.pm;
      sum.nn += o.nn; sum.pn += o.pn;
      sum.no += o.no; sum.po += o.po;
      sum.np += o.np; sum.pp += o.pp;
      sum.nr += o.nr; sum.pr += o.pr;
      sum.nt += o.nt; sum.pt += o.pt;
      sum.nz += o.nz; sum.pz += o.pz;
      sum.nu += o.nu; sum.pu += o.pu;
    }
    sum.nu = ukupnoNaslova.size();
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
  private StringBuffer log;
}
