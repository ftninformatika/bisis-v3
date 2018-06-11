package com.gint.app.bisis.editor.recordstats.zr;

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
import com.gint.util.xml.XMLUtils;

public class StanjeFondaPoUDK extends RecordReport {
  public void execute() {
    try {
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT count(*) FROM documents");
      rset.next();
      totalRecords = rset.getInt(1);
      rset.close();
      stmt.close();
//      totalRecords = 129487;
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

      if (params == null)
        params = new HashMap();
      params.put("today", sdf.format(new Date()));

      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/branch");

      JasperPrint jp = JasperFillManager.fillReport(
          StanjeFondaPoUDK.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/zr/StanjeFondaPoUDK.jasper")
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
    char c = getFirstDigit(sig);
    if (c == ' ') {
      problems.append("Neispravan 675a (UDK broj) u zapisu: " + rec.getRecordID().getLocalRecordID() + "\n");
      noSignature++;
      return;
    }
    if (sig.startsWith("087.5"))
      c = 'x'; // slikovnica
    
    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      Subfield sfw = f.getSubfield('w');
      if (sfw == null || sfw.getContent().equals("")) {
        problems.append("Nedostaje 996w (odeljenje) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noBranch++;
        continue;
      }
      Subfield sff = f.getSubfield('f');
      if (sff == null || sff.getContent().equals("") || sff.getContent().length() < 4) {
        problems.append("Nedostaje ili neispravan 996f (inventarni broj) u zapisu " + rec.getRecordID().getLocalRecordID() + "\n");
        noInvNum++;
        continue;
      }

      String sigla = sff.getContent().substring(0, 4);
      if (sigla.equals("0104"))
        sigla = "0101";
      if (sigla.equals("0105"))
        sigla = "0102";
      Branch b = getBranch(sigla);
      
      switch (c) {
        case '0':
          b.udk0++;
          break;
        case '1':
          b.udk1++;
          break;
        case '2':
          b.udk2++;
          break;
        case '3':
          b.udk3++;
          break;
        case '4':
          b.udk4++;
          break;
        case '5':
          b.udk5++;
          break;
        case '6':
          b.udk6++;
          break;
        case '7':
          b.udk7++;
          break;
        case '8':
          b.udk8++;
          break;
        case '9':
          b.udk9++;
          break;
        case 'x':
          b.slik++;
          break;
      }
    }
    
  }
  
  private Branch getBranch(String sigla) {
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
    while (pos < s.length() && !Character.isDigit(s.charAt(pos)))
      pos++;
    if (pos == s.length())
      return ' ';
    return s.charAt(pos);
  }
  
  public class Branch implements Comparable {
    public String sigla = "";
    public int udk0 = 0;
    public int udk1 = 0;
    public int udk2 = 0;
    public int udk3 = 0;
    public int udk4 = 0;
    public int udk5 = 0;
    public int udk6 = 0;
    public int udk7 = 0;
    public int udk8 = 0;
    public int udk9 = 0;
    public int slik = 0;
    public boolean isTotal = false;

    public int compareTo(Object o) {
      if (o instanceof Branch) {
        Branch b = (Branch)o;
        return sigla.compareTo(b.sigla);
      }
      return 0;
    }
    
    public void add(Branch b) {
      udk0 += b.udk0;
      udk1 += b.udk1;
      udk2 += b.udk2;
      udk3 += b.udk3;
      udk4 += b.udk4;
      udk5 += b.udk5;
      udk6 += b.udk6;
      udk7 += b.udk7;
      udk8 += b.udk8;
      udk9 += b.udk9;
      slik += b.slik;
    }
    
    public int getTotal() {
      return udk0 + udk1 + udk2 + udk3 + udk4 + udk5 + udk6 + udk7 + udk8 + udk9 + slik;
    }
    
    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <branch id=\"");
      buf.append(sigla);
      buf.append("\" name=\"");
      buf.append(getName(sigla));
      buf.append("\" isTotal=\"");
      buf.append(isTotal);
      buf.append("\">\n    <udk0>");
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
      buf.append("</udk8>\n    <udk9>");
      buf.append(udk9);
      buf.append("</udk9>\n    <slik>");
      buf.append(slik);
      buf.append("</slik>\n    <total>");
      buf.append(getTotal());
      buf.append("</total>\n  </branch>");
      return buf.toString();
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
    if (sigla.equals("01xx"))
      return "De\u010dje / ukupno";
    if (sigla.equals("02xx"))
      return "Nau\u010dno / ukupno";
    if (sigla.equals("03xx"))
      return "Zavi\u010dajno / ukupno";
    if (sigla.equals("04xx"))
      return "Pozajmno / ukupno";
    if (sigla.equals("xxxx"))
      return "UKUPNO";
    return sigla;
  }
  
  public void addTotals(List branches) {
    Branch totalDecje = new Branch();
    totalDecje.sigla = "01xx";
    totalDecje.isTotal = true;
    Iterator it = branches.iterator();
    while (it.hasNext()) {
      Branch b = (Branch)it.next();
      if (b.sigla.startsWith("01"))
        totalDecje.add(b);
    }

    Branch totalNaucno = new Branch();
    totalNaucno.sigla = "02xx";
    totalNaucno.isTotal = true;
    it = branches.iterator();
    while (it.hasNext()) {
      Branch b = (Branch)it.next();
      if (b.sigla.startsWith("02"))
        totalNaucno.add(b);
    }

    Branch totalZavicajno = new Branch();
    totalZavicajno.sigla = "03xx";
    totalZavicajno.isTotal = true;
    it = branches.iterator();
    while (it.hasNext()) {
      Branch b = (Branch)it.next();
      if (b.sigla.startsWith("03"))
        totalZavicajno.add(b);
    }

    Branch totalPozajmno = new Branch();
    totalPozajmno.sigla = "04xx";
    totalPozajmno.isTotal = true;
    it = branches.iterator();
    while (it.hasNext()) {
      Branch b = (Branch)it.next();
      if (b.sigla.startsWith("04"))
        totalPozajmno.add(b);
    }

    Branch totalSve = new Branch();
    totalSve.sigla = "xxxx";
    totalSve.isTotal = true;
    totalSve.add(totalDecje);
    totalSve.add(totalNaucno);
    totalSve.add(totalZavicajno);
    totalSve.add(totalPozajmno);
    
    branches.add(totalDecje);
    branches.add(totalNaucno);
    branches.add(totalZavicajno);
    branches.add(totalPozajmno);
    branches.add(totalSve);

  }
  
  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noSignature = 0;
  int noBranch = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  HashMap branches = new HashMap();
}
