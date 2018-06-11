package com.gint.app.bisis.editor.recordstats.ki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

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

public class NabavkaPoNacinu extends RecordReport {
  
  public NabavkaPoNacinu() {
  }
  
  public void execute() {
    init();
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

      StringBuffer buff = new StringBuffer();
      buff.append("<report>\n  <item>\n");
      buff.append("  <bp_ssr>");
      buff.append(brojPrimeraka_sopstvenimSredstvima);
      buff.append("</bp_ssr>\n");
      buff.append("  <bp_buso>");
      buff.append(brojPrimeraka_budzetOpstine);
      buff.append("</bp_buso>\n");
      buff.append("  <bp_don>");
      buff.append(brojPrimeraka_donacije);
      buff.append("</bp_don>\n");
      buff.append("  <bp_minkul>");
      buff.append(brojPrimeraka_poklonMinistarstva);
      buff.append("</bp_minkul>\n");
      buff.append("  <bp_ost>");
      buff.append(brojPrimeraka_poklonOstali);
      buff.append("</bp_ost>\n");
      buff.append("  <bp_razm>");
      buff.append(brojPrimeraka_razmena);
      buff.append("</bp_razm>\n");
      buff.append("  <bp_obpr>");
      buff.append(brojPrimeraka_obavezniPrimerak);
      buff.append("</bp_obpr>\n");
      buff.append("  <bp_sopiz>");
      buff.append(brojPrimeraka_sopstvenaIzdanja);
      buff.append("</bp_sopiz>\n");
      buff.append("  <bp_ukupno>");
      buff.append(brojPrimeraka_ukupno());
      buff.append("</bp_ukupno>\n");
      buff.append("  <vr_ssr>");
      buff.append(nf.format(vrednost_sopstvenimSredstvima));
      buff.append("</vr_ssr>\n");
      buff.append("  <vr_buso>");
      buff.append(nf.format(vrednost_budzetOpstine));
      buff.append("</vr_buso>\n");
      buff.append("  <vr_don>");
      buff.append(nf.format(vrednost_donacije));
      buff.append("</vr_don>\n");
      buff.append("  <vr_minkul>");
      buff.append(nf.format(vrednost_poklonMinistarstva));
      buff.append("</vr_minkul>\n");
      buff.append("  <vr_ost>");
      buff.append(nf.format(vrednost_poklonOstali));
      buff.append("</vr_ost>\n");
      buff.append("  <vr_razm>");
      buff.append(nf.format(vrednost_razmena));
      buff.append("</vr_razm>\n");
      buff.append("  <vr_obpr>");
      buff.append(nf.format(vrednost_obavezniPrimerak));
      buff.append("</vr_obpr>\n");
      buff.append("  <vr_sopiz>");
      buff.append(nf.format(vrednost_sopstvenaIzdanja));
      buff.append("</vr_sopiz>\n");
      buff.append("  <vr_ukupno>");
      buff.append(nf.format(vrednost_ukupno()));
      buff.append("</vr_ukupno>\n");
      buff.append("  </item>\n  </report>\n");

      System.out.println(buff);
      System.out.println("bez datuma inventarisanja (996o): " + noInvDate);
      System.out.println("bez nacina nabavke (996v): " + noNacinNabavke);
      System.out.println("bez finansijera (996v): " + noFinansijer);
      System.out.println("bez dobavljaca (9962): " + noDobavljac);
      System.out.println("bez cene (9963): " + noCena);
      
      params.put("today", sdf.format(new Date()));
      JRXmlDataSource dataSource = new JRXmlDataSource(
          XMLUtils.getDocumentFromString(buff.toString()), "/report/item");

      JasperPrint jp = JasperFillManager.fillReport(
          NabavkaPoNacinu.class.getResource(
            "/com/gint/app/bisis/editor/recordstats/ki/NabavkaPoNacinu.jasper")
          .openStream(), params, dataSource);
      rp.setReport(jp);
      rp.setFinished(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void handleRecord(Record rec) {
    Iterator it = rec.getFields("996").iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      
      // check date
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
      
      // nacin nabavke
      Subfield sfv = f.getSubfield('v');
      if (sfv == null || sfv.getContent().equals("")) {
        noNacinNabavke++;
        continue;
      }
      
      // finansijer
      Subfield sf4 = f.getSubfield('4');

      // dobavljac
      Subfield sf2 = f.getSubfield('2');

      // cena
      Subfield sf3 = f.getSubfield('3');
      if (sf3 == null || sf3.getContent().equals("")) {
        noCena++;
        continue;
      }
      float cena = 0.0f;
      try {
        cena = Float.parseFloat(sf3.getContent().trim());
      } catch (Exception ex) {
        noCena++;
        continue;
      }
      
      char nacinNabavke = sfv.getContent().charAt(0);
      switch (nacinNabavke) {
        case 'a':
        case 'k':
          // kupovina
          if (sf4 == null || sf4.getContent().trim().equals("")) {
            noFinansijer++;
            continue;
          }
          String finansijer = sf4.getContent().trim(); 
          if (finansijer.equalsIgnoreCase("Sop. sre.") || 
              finansijer.equalsIgnoreCase("Sop.sre.")  ||
              finansijer.equalsIgnoreCase("\u0421\u043e\u043f. \u0441\u0440\u0435.") ||
              finansijer.equalsIgnoreCase("\u0421\u043e\u043f.\u0441\u0440\u0435.")) {
            brojPrimeraka_sopstvenimSredstvima++;
            vrednost_sopstvenimSredstvima += cena;
          } else if (finansijer.equalsIgnoreCase("bu.so")) {
            brojPrimeraka_budzetOpstine++;
            vrednost_budzetOpstine += cena;
          } else if (finansijer.equalsIgnoreCase("don.")) {
            brojPrimeraka_budzetOpstine++;
            vrednost_budzetOpstine += cena;
          }
          break;
        case 'b':
          // razmena
          brojPrimeraka_razmena++;
          vrednost_razmena += cena;
          break;
        case 'c':
        case 'p':
          // poklon
          if (sf2 == null || sf2.getContent().equals("")) {
            noDobavljac++;
            continue;
          }
          String dobavljac = sf2.getContent().trim();
          if (dobavljac.equalsIgnoreCase("Min. kul.") || 
              dobavljac.equalsIgnoreCase("Min.kul.") || 
              dobavljac.equalsIgnoreCase("\u041c\u0438\u043d. \u043a\u0443\u043b.") ||
              dobavljac.equalsIgnoreCase("\u041c\u0438\u043d.\u043a\u0443\u043b.")) {
            brojPrimeraka_poklonMinistarstva++;
            vrednost_poklonMinistarstva += cena;
          } else {
            brojPrimeraka_poklonOstali++;
            vrednost_poklonOstali += cena;
          }
          break;
        case 'd':
          // obavezni primerak
          brojPrimeraka_obavezniPrimerak++;
          vrednost_obavezniPrimerak += cena;
          break;
        case 'e':
          // stari fond
          break;
        case 'f':
        case 's':
          // sopstvena izdanja
          brojPrimeraka_sopstvenaIzdanja++;
          vrednost_sopstvenaIzdanja += cena;
          break;
        case 'o':
          // otkup
          break;
      }

    }
  }
  
  private void init() {
    brojPrimeraka_sopstvenimSredstvima = 0;
    brojPrimeraka_budzetOpstine = 0;
    brojPrimeraka_donacije = 0;
    brojPrimeraka_poklonMinistarstva = 0;
    brojPrimeraka_poklonOstali = 0;
    brojPrimeraka_razmena = 0;
    brojPrimeraka_obavezniPrimerak = 0;
    brojPrimeraka_sopstvenaIzdanja = 0;
    vrednost_sopstvenimSredstvima = 0;
    vrednost_budzetOpstine = 0;
    vrednost_donacije = 0;
    vrednost_poklonMinistarstva = 0;
    vrednost_poklonOstali = 0;
    vrednost_razmena = 0;
    vrednost_obavezniPrimerak = 0;
    vrednost_sopstvenaIzdanja = 0;

    noNacinNabavke = 0;
    noFinansijer = 0;
    noDobavljac = 0;
    noInvDate = 0;
    noCena = 0;
  }
  
  private int brojPrimeraka_ukupno() {
    return brojPrimeraka_sopstvenimSredstvima + brojPrimeraka_budzetOpstine +
      brojPrimeraka_donacije + brojPrimeraka_poklonMinistarstva +
      brojPrimeraka_poklonOstali + brojPrimeraka_razmena +
      brojPrimeraka_obavezniPrimerak + brojPrimeraka_sopstvenaIzdanja;
  }
  
  private float vrednost_ukupno() {
    return vrednost_sopstvenimSredstvima + vrednost_budzetOpstine +
      vrednost_donacije + vrednost_poklonMinistarstva +
      vrednost_poklonOstali + vrednost_razmena +
      vrednost_obavezniPrimerak + vrednost_sopstvenaIzdanja;
  }
  
  int brojPrimeraka_sopstvenimSredstvima = 0;
  int brojPrimeraka_budzetOpstine = 0;
  int brojPrimeraka_donacije = 0;
  int brojPrimeraka_poklonMinistarstva = 0;
  int brojPrimeraka_poklonOstali = 0;
  int brojPrimeraka_razmena = 0;
  int brojPrimeraka_obavezniPrimerak = 0;
  int brojPrimeraka_sopstvenaIzdanja = 0;
  float vrednost_sopstvenimSredstvima = 0;
  float vrednost_budzetOpstine = 0;
  float vrednost_donacije = 0;
  float vrednost_poklonMinistarstva = 0;
  float vrednost_poklonOstali = 0;
  float vrednost_razmena = 0;
  float vrednost_obavezniPrimerak = 0;
  float vrednost_sopstvenaIzdanja = 0;
  
  Date startDate;
  Date endDate;
  int totalRecords = 0;
  int noNacinNabavke = 0;
  int noFinansijer = 0;
  int noDobavljac = 0;
  int noInvDate = 0;
  int noCena = 0;
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  NumberFormat nf;
}
