package com.gint.app.bisis.editor.recordstats;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.recordstats.ns.BrojPrimeraka;
import com.gint.app.bisis.editor.recordstats.ns.BrojUradjenihZapisa;
import com.gint.app.bisis.editor.recordstats.ns.BrojZapisa;
import com.gint.app.bisis.editor.recordstats.ns.NabavkaPoNacinu1;
import com.gint.app.bisis.editor.recordstats.ns.NabavkaPoNacinu2;
import com.gint.app.bisis.editor.recordstats.ns.NabavkaPoUDK1;
import com.gint.app.bisis.editor.recordstats.ns.NabavkaPoUDK2;
import com.gint.app.bisis.editor.recordstats.ns.PrimerciPoOgrancima;
import com.gint.app.bisis.editor.recordstats.zr.KnjigaInventara;
import com.gint.util.file.INIFile;
import com.gint.util.gui.MonitorFrame;
import com.gint.util.gui.WindowUtils;

/**
 *
 * @author branko
 */
public class StatsMenuFrame extends JFrame {

  public StatsMenuFrame(MainFrame mf) {
    // super(mf, "Statistika baze zapisa", true);
    setTitle("Statistika baze zapisa");
    this.mf = mf;
    memoryMonitor = new MonitorFrame();
    reportProgress = new ReportProgress(this);
    dateRangeDlg = new DateRangeDlg(this);
    dateRangeDlg2 = new DateRangeDlg2(this);
    numberRangeDlg = new NumberRangeDlg(this);
    invRangeDlg = new InvRangeDlg(this);
    invRangeDlg2 = new InvRangeDlg2(this);
    
    menuBar = new JMenuBar();
    getRootPane().setJMenuBar(menuBar);
    
    setupNoviSad();
    setupZrenjanin();
    setupKikinda();
    setupSabac();
    setupTFZR();
    setupBeograd();
    
    menuBar.add(Box.createHorizontalGlue());
    JMenu sistem = new JMenu("Sistem");
    menuBar.add(sistem);
    JMenuItem izlaz = new JMenuItem("Izlaz");
    sistem.add(izlaz);
    izlaz.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_izlaz();
      }
    });
    sistem.addSeparator();
    JMenuItem monitor = new JMenuItem("Monitor");
    sistem.add(monitor);
    monitor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_monitor();
      }
    });
    
    setSize(600, 400);
    WindowUtils.centerOnScreen(this);
  }
  
  public void handle_izlaz() {
    setVisible(false);
  }
  
  public void handle_monitor() {
    memoryMonitor.setVisible(true);
  }
  
  //###############################################
  //##  NOVI SAD
  //###############################################
  private void setupNoviSad() {
    JMenu mNoviSad = new JMenu("GB Novi Sad");
    menuBar.add(mNoviSad);
    JMenu velicinaBaze = new JMenu("Veli\u010dina baze");
    mNoviSad.add(velicinaBaze);
    JMenuItem brojZapisa = new JMenuItem("Broj zapisa po vrstama gra\u0111e");
    brojZapisa.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_brojZapisa();
      }
    });
    velicinaBaze.add(brojZapisa);
    JMenuItem brojPrimeraka = new JMenuItem("Broj primeraka po vrstama gra\u0111e");
    brojPrimeraka.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_brojPrimeraka();
      }
    });
    velicinaBaze.add(brojPrimeraka);

    JMenu stanjeFonda = new JMenu("Stanje fonda");
    mNoviSad.add(stanjeFonda);
    JMenuItem brojUradjenihZapisa = new JMenuItem("Broj ura\u0111enih zapisa");
    brojUradjenihZapisa.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_brojUradjenihZapisa();
      }
    });
    stanjeFonda.add(brojUradjenihZapisa);
    JMenuItem stanjePoOgrancima = new JMenuItem("Stanje po ograncima");
    stanjePoOgrancima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_stanjePoOgrancima();
      }
    });
    stanjeFonda.add(stanjePoOgrancima);
    
    JMenu mNabavka = new JMenu("Nabavka");
    mNoviSad.add(mNabavka);
    JMenuItem nabavkaPoNacinu1 = new JMenuItem("Po na\u010dinu nabavke za teku\u0107u godinu");
    nabavkaPoNacinu1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_nabavkaPoNacinu1();
      }
    });
    mNabavka.add(nabavkaPoNacinu1);
    JMenuItem nabavkaPoNacinu2 = new JMenuItem("Po na\u010dinu nabavke za izabrani period");
    nabavkaPoNacinu2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_nabavkaPoNacinu2();
      }
    });
    mNabavka.add(nabavkaPoNacinu2);
    JMenuItem nabavkaPoUDK1 = new JMenuItem("Po UDK za teku\u0107u godinu");
    nabavkaPoUDK1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_nabavkaPoUDK1();
      }
    });
    mNabavka.add(nabavkaPoUDK1);
    JMenuItem nabavkaPoUDK2 = new JMenuItem("Po UDK za izabrani period");
    nabavkaPoUDK2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_nabavkaPoUDK2();
      }
    });
    mNabavka.add(nabavkaPoUDK2);
  }
  public void handle_brojZapisa() {
    ReportUtils.showServerReport(BrojZapisa.class, null);
  }
  
  public void handle_brojPrimeraka() {
    ReportUtils.showServerReport(BrojPrimeraka.class, null);
  }

  public void handle_brojUradjenihZapisa() {
    ReportUtils.showServerReport(BrojUradjenihZapisa.class, null);
  }

  public void handle_stanjePoOgrancima() {
    ReportUtils.showServerReport(PrimerciPoOgrancima.class, null);
  }
  
  public void handle_nabavkaPoNacinu1() {
    ReportUtils.showServerReport(NabavkaPoNacinu1.class, null);
  }

  public void handle_nabavkaPoNacinu2() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showServerReport(NabavkaPoNacinu2.class, params);
  }

  public void handle_nabavkaPoUDK1() {
    ReportUtils.showServerReport(NabavkaPoUDK1.class, null);
  }

  public void handle_nabavkaPoUDK2() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showServerReport(NabavkaPoUDK2.class, params);
  }

  //#########################################
  //####  ZRENJANIN
  //#########################################
  
  private void setupZrenjanin() {
    JMenu mZrenjanin = new JMenu("GB Zrenjanin");
    menuBar.add(mZrenjanin);
    JMenu mZRNabavka = new JMenu("Nabavka");
    mZrenjanin.add(mZRNabavka);
    JMenuItem miZRNabavkaPoUDK = new JMenuItem("Po UDK");
    mZRNabavka.add(miZRNabavkaPoUDK);
    miZRNabavkaPoUDK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRnabavkaPoUDK();
      }
    });
    JMenuItem miZRNabavkaPoNacinu = new JMenuItem("Po na\u010dinu nabavke");
    mZRNabavka.add(miZRNabavkaPoNacinu);
    miZRNabavkaPoNacinu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRnabavkaPoNacinu();
      }
    });
    JMenuItem miZRStatistikaNabavke = new JMenuItem("Statistika nabavke");
    mZRNabavka.add(miZRStatistikaNabavke);
    miZRStatistikaNabavke.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRstatistikaNabavke();
      }
    });
    JMenuItem miZRGrupniInventar = new JMenuItem("Grupni inventar");
    mZRNabavka.add(miZRGrupniInventar);
    miZRGrupniInventar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRgrupniInventar();
      }
    });
    JMenuItem miZRnabavkaPoRacunu = new JMenuItem("Po ra\u010dunima");
    mZRNabavka.add(miZRnabavkaPoRacunu);
    miZRnabavkaPoRacunu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRnabavkaPoRacunu();
      }
    });
    JMenu mZRstanjeFonda = new JMenu("Stanje fonda");
    mZrenjanin.add(mZRstanjeFonda);
    JMenuItem miZRstanjeFondaPoUDK = new JMenuItem("Po UDK");
    mZRstanjeFonda.add(miZRstanjeFondaPoUDK);
    miZRstanjeFondaPoUDK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRstanjeFondaPoUDK();
      }
    });
    JMenuItem miZRstanjeFondaPoJezicima = new JMenuItem("Po jezicima");
    mZRstanjeFonda.add(miZRstanjeFondaPoJezicima);
    miZRstanjeFondaPoJezicima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRstanjeFondaPoJezicima();
      }
    });
    JMenu mZRknjigaInventara = new JMenu("Knjiga inventara");
    mZrenjanin.add(mZRknjigaInventara);
    JMenuItem mZRKIsrpski = new JMenuItem("Srpski jezik");
    mZRknjigaInventara.add(mZRKIsrpski);
    mZRKIsrpski.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRknjigaInventara(KnjigaInventara.FOND_SRPSKI);
      }
    });
    JMenuItem mZRKImadjarski = new JMenuItem("Ma\u0111arski jezik");
    mZRknjigaInventara.add(mZRKImadjarski);
    mZRKImadjarski.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRknjigaInventara(KnjigaInventara.FOND_MADJARSKI);
      }
    });
    JMenuItem mZRKIstrani = new JMenuItem("Strani jezici");
    mZRknjigaInventara.add(mZRKIstrani);
    mZRKIstrani.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRknjigaInventara(KnjigaInventara.FOND_STRANI);
      }
    });
    JMenuItem mZRKIslikovnice = new JMenuItem("Slikovnice");
    mZRknjigaInventara.add(mZRKIslikovnice);
    mZRKIslikovnice.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRknjigaInventara(KnjigaInventara.FOND_SLIKOVNICE);
      }
    });
    JMenuItem mZRKIvrtici = new JMenuItem("Vrti\u0107i");
    mZRknjigaInventara.add(mZRKIvrtici);
    mZRKIvrtici.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRknjigaInventara(KnjigaInventara.FOND_VRTICI);
      }
    });
    JMenuItem mZRKIbolnice = new JMenuItem("Bolnice");
    mZRknjigaInventara.add(mZRKIbolnice);
    mZRKIbolnice.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_ZRknjigaInventara(KnjigaInventara.FOND_BOLNICE);
      }
    });
  }
  
  public void handle_ZRnabavkaPoUDK() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.NabavkaPoUDK.class, params);
  }

  public void handle_ZRnabavkaPoNacinu() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.PoNacinuNabavke.class, params);
  }

  public void handle_ZRstatistikaNabavke() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.StatistikaNabavke.class, params);
  }

  public void handle_ZRgrupniInventar() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.GrupniInventar.class, params);
  }

  public void handle_ZRnabavkaPoRacunu() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.NabavkaPoRacunima.class, params);
  }

  public void handle_ZRstanjeFondaPoUDK() {
    ReportUtils.askAndShowReport(reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.StanjeFondaPoUDK.class, 
        new HashMap());
  }
  
  public void handle_ZRstanjeFondaPoJezicima() {
    ReportUtils.askAndShowReport(reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.StanjeFondaPoJezicima.class, 
        new HashMap());
  }
  
  public void handle_ZRknjigaInventara(int fond) {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", new Integer(fond));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.zr.KnjigaInventara.class, params);
  }

  //#########################################
  //####   KIKINDA
  //#########################################
  
  private void setupKikinda() {
    JMenu mKikinda = new JMenu("GB Kikinda");
    JMenu mKINabavka = new JMenu("Nabavka");
    JMenu mKIStanjeFonda = new JMenu("Stanje fonda");
    JMenu mKIKnjigaInventara = new JMenu("Knjiga inventara");
    menuBar.add(mKikinda);
    mKikinda.add(mKINabavka);
    mKikinda.add(mKIStanjeFonda);
    mKikinda.add(mKIKnjigaInventara);
    JMenuItem miKINabavkaPoUDK = new JMenuItem("Po UDK");
    mKINabavka.add(miKINabavkaPoUDK);
    miKINabavkaPoUDK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KInabavkaPoUDK();
      }
    });
    JMenuItem miKINabavkaPoNacinu = new JMenuItem("Po na\u010dinu nabavke");
    mKINabavka.add(miKINabavkaPoNacinu);
    miKINabavkaPoNacinu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KInabavkaPoNacinu();
      }
    });
    JMenuItem miKINabavkaPoRacunima = new JMenuItem("Po ra\u010dunima");
    mKINabavka.add(miKINabavkaPoRacunima);
    miKINabavkaPoRacunima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KInabavkaPoRacunima();
      }
    });
    JMenuItem miKIstanjeFondaPoUDK = new JMenuItem("Po UDK");
    mKIStanjeFonda.add(miKIstanjeFondaPoUDK);
    miKIstanjeFondaPoUDK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KIstanjeFondaPoUDK();
      }
    });
    JMenuItem miKIstanjeFondaPoJezicima = new JMenuItem("Po jezicima");
    mKIStanjeFonda.add(miKIstanjeFondaPoJezicima);
    miKIstanjeFondaPoJezicima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KIstanjeFondaPoJezicima();
      }
    });
    JMenuItem miKIstanjeFondaPoNaslovima = new JMenuItem("Po naslovima");
    mKIStanjeFonda.add(miKIstanjeFondaPoNaslovima);
    miKIstanjeFondaPoNaslovima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KIstanjeFondaPoNaslovima();
      }
    });
    JMenuItem miKIknjigaInventaraMonografske = new JMenuItem("Monografske");
    mKIKnjigaInventara.add(miKIknjigaInventaraMonografske);
    miKIknjigaInventaraMonografske.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_KIknjigaInventaraMonografske();
      }
    });
  }
  
  public void handle_KInabavkaPoUDK() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.NabavkaPoUDK.class, params);
  }

  public void handle_KInabavkaPoNacinu() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.NabavkaPoNacinu.class, params);
  }

  public void handle_KIstatistikaNabavke() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
//    ReportUtils.showReport(false, reportProgress, 
//        com.gint.app.bisis.editor.recordstats.ki.StatistikaNabavke.class, params);
  }

  public void handle_KIgrupniInventar() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
//    ReportUtils.showReport(false, reportProgress, 
//        com.gint.app.bisis.editor.recordstats.ki.GrupniInventar.class, params);
  }

  public void handle_KInabavkaPoRacunima() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.NabavkaPoRacunima.class, params);
  }

  public void handle_KIstanjeFondaPoUDK() {
    ReportUtils.askAndShowReport(reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.StanjeFondaPoUDK.class, 
        new HashMap());
  }
  
  public void handle_KIstanjeFondaPoJezicima() {
    ReportUtils.askAndShowReport(reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.StanjeFondaPoJezicima.class, 
        new HashMap());
  }
  
  public void handle_KIstanjeFondaPoNaslovima() {
    ReportUtils.askAndShowReport(reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.StanjePoNaslovima.class, 
        new HashMap());
  }
  
  public void handle_KIknjigaInventaraMonografske() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.ki.KnjigaInventaraMonografske.class, params);
  }
  
  //#########################################
  //####   SABAC
  //#########################################
  
  private void setupSabac() {
    JMenu mSabac = new JMenu("GB \u0160abac");
    menuBar.add(mSabac);
    JMenu mInvKnjiga = new JMenu("Inventarna knjiga");
    mSabac.add(mInvKnjiga);
    JMenuItem miOdrasli = new JMenuItem("Odrasli");
    mInvKnjiga.add(miOdrasli);
    JMenuItem miDecje = new JMenuItem("De\u010dje");
    mInvKnjiga.add(miDecje);
    JMenuItem miNaucnoI = new JMenuItem("Nau\u010dno I");
    mInvKnjiga.add(miNaucnoI);
    JMenuItem miNaucnoII = new JMenuItem("Nau\u010dno II");
    mInvKnjiga.add(miNaucnoII);
    JMenuItem miNaucnoIII = new JMenuItem("Nau\u010dno III");
    mInvKnjiga.add(miNaucnoIII);
    JMenuItem miNaucnoIV = new JMenuItem("Nau\u010dno IV");
    mInvKnjiga.add(miNaucnoIV);
    JMenuItem miZavicajnoI = new JMenuItem("Zavi\u010dajno I");
    mInvKnjiga.add(miZavicajnoI);
    JMenuItem miZavicajnoII = new JMenuItem("Zavi\u010dajno II");
    mInvKnjiga.add(miZavicajnoII);
    JMenuItem miZavicajnoIII = new JMenuItem("Zavi\u010dajno III");
    mInvKnjiga.add(miZavicajnoIII);
    JMenuItem miZavicajnoIV = new JMenuItem("Zavi\u010dajno IV");
    mInvKnjiga.add(miZavicajnoIV);
    
    JMenu mStatistika = new JMenu("Statistika");
    mSabac.add(mStatistika);
    JMenuItem miStatistikaBazePeriod = new JMenuItem("Statistika baze za period");
    mStatistika.add(miStatistikaBazePeriod);
    JMenuItem miStatistikaBazeCela = new JMenuItem("Statistika cele baze");
    mStatistika.add(miStatistikaBazeCela);
    JMenuItem miStatistikaObradjivacaPeriod = new JMenuItem("Statistika obra\u0111iva\u010da za period");
    mStatistika.add(miStatistikaObradjivacaPeriod);
    miOdrasli.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjOdrasli();
      }
    });
    miDecje.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjDecje();
      }
    });
    miNaucnoI.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjNaucnoI();
      }
    });
    miNaucnoII.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjNaucnoII();
      }
    });
    miNaucnoIII.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjNaucnoIII();
      }
    });
    miNaucnoIV.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjNaucnoIV();
      }
    });
    miZavicajnoI.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjZavicajnoI();
      }
    });
    miZavicajnoII.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjZavicajnoII();
      }
    });
    miZavicajnoIII.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjZavicajnoIII();
      }
    });
    miZavicajnoIV.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAInvKnjZavicajnoIV();
      }
    });
    miStatistikaBazePeriod.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAStatistikaBazePeriod();
      }
    });
    miStatistikaBazeCela.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAStatistikaBazeCela();
      }
    });
    miStatistikaObradjivacaPeriod.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_SAStatistikaObradjivaca();
      }
    });
  }
  
  public void handle_SAInvKnjOdrasli() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0100");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjDecje() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0200");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjNaucnoI() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0301");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjNaucnoII() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0302");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjNaucnoIII() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0303");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjNaucnoIV() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0304");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjZavicajnoI() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0401");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjZavicajnoII() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0402");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjZavicajnoIII() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0403");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAInvKnjZavicajnoIV() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    params.put("fond", "0404");
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.KnjigaInventara.class, params);
  }

  public void handle_SAStatistikaBazePeriod() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.StatistikaBaze.class, params);
  }

  public void handle_SAStatistikaBazeCela() {
    Map params = new HashMap();
    params.put("dateRange", "");
    ReportUtils.askAndShowReport(reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.StatistikaBazeCela.class, params);
  }

  public void handle_SAStatistikaObradjivaca() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.sa.StatistikaObradjivaca.class, 
        params);
  }

  //////////////////////////////////////////////////////
  // TF "Mihajlo Pupin" Zrenjanin
  //////////////////////////////////////////////////////
  private void setupTFZR() {
    JMenu mSabac = new JMenu("TF Zrenjanin");
    menuBar.add(mSabac);
    JMenu mInvKnjiga = new JMenu("Inventarna knjiga");
    mSabac.add(mInvKnjiga);
    JMenuItem miMonografske = new JMenuItem("Monografske publikacije");
    mInvKnjiga.add(miMonografske);
    JMenuItem miRadovi = new JMenuItem("Diplomski radovi");
    mInvKnjiga.add(miRadovi);
    miMonografske.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_TFZRInvKnjMonografske();
      }
    });
    miRadovi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_TFZRInvKnjRadovi();
      }
    });
  }
  
  public void handle_TFZRInvKnjMonografske() {
    numberRangeDlg.setVisible(true);
    if (numberRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("numberRange", numberRangeDlg.getNumberRange());
    params.put("startDate", new Integer(numberRangeDlg.getStartNumber()));
    params.put("endDate", new Integer(numberRangeDlg.getEndNumber()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.tfzr.KnjigaInventaraMonografske.class, params);
  }

  public void handle_TFZRInvKnjRadovi() {
    numberRangeDlg.setVisible(true);
    if (numberRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("numberRange", numberRangeDlg.getNumberRange());
    params.put("startDate", new Integer(numberRangeDlg.getStartNumber()));
    params.put("endDate", new Integer(numberRangeDlg.getEndNumber()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.tfzr.KnjigaInventaraRadovi.class, params);
  }
  
  //##########################################################
  //## BEOGRAD
  //##########################################################
  
  private void setupBeograd() {
    JMenu menuBG = new JMenu("BGB");
    menuBar.add(menuBG);
    JMenuItem mInvKnjiga = new JMenu("Inventarna knjiga");
    menuBG.add(mInvKnjiga);
    JMenu mOpstiFond = new JMenu("Op\u0161ti fond");
    mInvKnjiga.add(mOpstiFond);
    JMenu mZavicajniFond = new JMenu("Zavi\u010dajni fond");
    mInvKnjiga.add(mZavicajniFond);
    JMenu mDecijiFond = new JMenu("De\u010diji fond");
    mInvKnjiga.add(mDecijiFond);
    JMenu mPrirucniciFond = new JMenu("Priru\u010dnici");
    mInvKnjiga.add(mPrirucniciFond);
    JMenu mLegatFond = new JMenu("Legat");
    mInvKnjiga.add(mLegatFond);
    JMenuItem miIKOFBroj = new JMenuItem("po inv. broju");
    miIKOFBroj.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKOFBroj();
      }
    });
    mOpstiFond.add(miIKOFBroj);
    JMenuItem miIKOFDatum = new JMenuItem("po datumu inv.");
    miIKOFDatum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKOFDatum();
      }
    });
    mOpstiFond.add(miIKOFDatum);
    JMenuItem miIKZFBroj = new JMenuItem("po inv. broju");
    miIKZFBroj.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKZFBroj();
      }
    });
    mZavicajniFond.add(miIKZFBroj);
    JMenuItem miIKZFDatum = new JMenuItem("po datumu inv.");
    miIKZFDatum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKZFDatum();
      }
    });
    mZavicajniFond.add(miIKZFDatum);
    JMenuItem miIKDFBroj = new JMenuItem("po inv. broju");
    miIKDFBroj.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKDFBroj();
      }
    });
    mDecijiFond.add(miIKDFBroj);
    JMenuItem miIKDFDatum = new JMenuItem("po datumu inv.");
    miIKDFDatum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKDFDatum();
      }
    });
    mDecijiFond.add(miIKDFDatum);
    JMenuItem miIKPFBroj = new JMenuItem("po inv. broju");
    miIKPFBroj.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKPFBroj();
      }
    });
    mPrirucniciFond.add(miIKPFBroj);
    JMenuItem miIKPFDatum = new JMenuItem("po datumu inv.");
    miIKPFDatum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKPFDatum();
      }
    });
    mPrirucniciFond.add(miIKPFDatum);
    JMenuItem miIKLFBroj = new JMenuItem("po inv. broju");
    miIKLFBroj.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKLFBroj();
      }
    });
    mLegatFond.add(miIKLFBroj);
    JMenuItem miIKLFDatum = new JMenuItem("po datumu inv.");
    miIKLFDatum.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGIKLFDatum();
      }
    });
    mLegatFond.add(miIKLFDatum);
    
    JMenu mBrojInventarisanih = new JMenu("Broj inventarisanih jedinica");
    menuBG.add(mBrojInventarisanih);
    JMenuItem miBrojPremaUDK = new JMenuItem("prema UDK");
    miBrojPremaUDK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGBrojPremaUDK();
      }
    });
    mBrojInventarisanih.add(miBrojPremaUDK);
    JMenuItem miBrojPremaNacinuNabavke = new JMenuItem("prema na\u010dinu nabavke");
    miBrojPremaNacinuNabavke.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGBrojPremaNacinuNabavke();
      }
    });
    mBrojInventarisanih.add(miBrojPremaNacinuNabavke);
    JMenuItem miBrojPremaInvKnjigama = new JMenuItem("prema inventarnim knjigama");
    miBrojPremaInvKnjigama.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGBrojPremaInvKnjigama();
      }
    });
    mBrojInventarisanih.add(miBrojPremaInvKnjigama);
    JMenuItem miBrojPremaFondovima = new JMenuItem("prema fondovima");
    miBrojPremaFondovima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGBrojPremaFondovima();
      }
    });
    mBrojInventarisanih.add(miBrojPremaFondovima);
    JMenuItem miBrojPremaNacinuNabavkePoFondovima = new JMenuItem(
        "prema na\u010dinu nabavke po fondovima");
    miBrojPremaNacinuNabavkePoFondovima.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handle_BGBrojPremaNacinuNabavkePoFondovima();
      }
    });
    mBrojInventarisanih.add(miBrojPremaNacinuNabavkePoFondovima);
    
    JMenu mOpstinske = new JMenu("Op\u0161tinske biblioteke");
    menuBG.add(mOpstinske);
    JMenuItem miPoNacinuNabavke = new JMenuItem("po na\u010dinu nabavke");
    mOpstinske.add(miPoNacinuNabavke);
    miPoNacinuNabavke.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_BGOpstinskePoNacinuNabavke();
      }
    });
    JMenuItem miPoUdk = new JMenuItem("po UDK");
    mOpstinske.add(miPoUdk);
    miPoUdk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_BGOpstinskePoUdk();
      }
    });
    JMenuItem miStvarnoStanje = new JMenuItem("stvarno stanje fonda");
    mOpstinske.add(miStvarnoStanje);
    miStvarnoStanje.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_BGOpstinskeStvarnoStanje();
      }
    });
    JMenuItem miOpstinskeInvKnjigaPoInvBroju = new JMenuItem("inv. knjiga po inv. broju");
    mOpstinske.add(miOpstinskeInvKnjigaPoInvBroju);
    miOpstinskeInvKnjigaPoInvBroju.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_BGOpstinskeInvKnjigaPoInvBroju();
      }
    });
    JMenuItem miOpstinskeInvKnjigaPoDatumu = new JMenuItem("inv. knjiga po datumu");
    mOpstinske.add(miOpstinskeInvKnjigaPoDatumu);
    miOpstinskeInvKnjigaPoDatumu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_BGOpstinskeInvKnjigaPoDatumu();
      }
    });
  }
  
  private void handle_BGOpstinskeInvKnjigaPoInvBroju() {
    invRangeDlg2.setStartNumber("");
    invRangeDlg2.setEndNumber("");
    invRangeDlg2.setVisible(true);
    if (invRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("range", invRangeDlg2.getNumberRange());
    params.put("startNumber", invRangeDlg2.getStartNumber());
    params.put("endNumber", invRangeDlg2.getEndNumber());
    params.put("startPage", new Integer(invRangeDlg2.getStartPage()));
    String libraryName = Environment.getLibraryName();
    if (libraryName == null)
      libraryName = " ";
    params.put("libraryName", libraryName);
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.OpstinskeInvKnjigaMonografske.class, 
        params);
  }

  private void handle_BGOpstinskeInvKnjigaPoDatumu() {
    dateRangeDlg2.setVisible(true);
    if (dateRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("range", dateRangeDlg2.getDateRange());
    params.put("startDate", dateRangeDlg2.getStartDate());
    params.put("endDate", dateRangeDlg2.getEndDate());
    params.put("startPage", new Integer(dateRangeDlg2.getStartPage()));
    String libraryName = Environment.getLibraryName();
    if (libraryName == null)
      libraryName = " ";
    params.put("libraryName", libraryName);
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.OpstinskeInvKnjigaMonografske.class, 
        params);
  }

  private void handle_BGOpstinskePoNacinuNabavke() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    String libraryName = Environment.getLibraryName();
    if (libraryName == null)
      libraryName = " ";
    params.put("libraryName", libraryName);
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.OpstinskePremaNacinuNabavke.class, 
        params);
  }
  
  private void handle_BGOpstinskePoUdk() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    String libraryName = Environment.getLibraryName();
    if (libraryName == null)
      libraryName = " ";
    params.put("libraryName", libraryName);
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.OpstinskePremaUdk.class, 
        params);
  }
  
  private void handle_BGOpstinskeStvarnoStanje() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    String libraryName = Environment.getLibraryName();
    if (libraryName == null)
      libraryName = " ";
    params.put("libraryName", libraryName);
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.OpstinskeStvarnoStanjeFonda.class, 
        params);
  }

  private void handle_BGIKOFBroj() {
    invRangeDlg2.setStartNumber("0100XXXXXXX");
    invRangeDlg2.setEndNumber("0100XXXXXXX");
    invRangeDlg2.setVisible(true);
    if (invRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u041e\u043f\u0448\u0442\u0438 \u0444\u043e\u043d\u0434");
    params.put("fondID", "00");
    params.put("range", invRangeDlg2.getNumberRange());
    params.put("startNumber", invRangeDlg2.getStartNumber());
    params.put("endNumber", invRangeDlg2.getEndNumber());
    params.put("startPage", new Integer(invRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKOFDatum() {
    dateRangeDlg2.setVisible(true);
    if (dateRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u041e\u043f\u0448\u0442\u0438 \u0444\u043e\u043d\u0434");
    params.put("fondID", "00");
    params.put("range", dateRangeDlg2.getDateRange());
    params.put("startDate", dateRangeDlg2.getStartDate());
    params.put("endDate", dateRangeDlg2.getEndDate());
    params.put("startPage", new Integer(dateRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKZFBroj() {
    invRangeDlg2.setStartNumber("0120XXXXXXX");
    invRangeDlg2.setEndNumber("0120XXXXXXX");
    invRangeDlg2.setVisible(true);
    if (invRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u0417\u0430\u0432\u0438\u0447\u0430\u0458\u043d\u0438 \u0444\u043e\u043d\u0434");
    params.put("fondID", "20");
    params.put("range", invRangeDlg2.getNumberRange());
    params.put("startNumber", invRangeDlg2.getStartNumber());
    params.put("endNumber", invRangeDlg2.getEndNumber());
    params.put("startPage", new Integer(invRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKZFDatum() {
    dateRangeDlg2.setVisible(true);
    if (dateRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u0417\u0430\u0432\u0438\u0447\u0430\u0458\u043d\u0438 \u0444\u043e\u043d\u0434");
    params.put("fondID", "20");
    params.put("range", dateRangeDlg2.getDateRange());
    params.put("startDate", dateRangeDlg2.getStartDate());
    params.put("endDate", dateRangeDlg2.getEndDate());
    params.put("startPage", new Integer(dateRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKDFBroj() {
    invRangeDlg2.setStartNumber("0130XXXXXXX");
    invRangeDlg2.setEndNumber("0130XXXXXXX");
    invRangeDlg2.setVisible(true);
    if (invRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u0414\u0435\u0447\u0438\u0458\u0438 \u0444\u043e\u043d\u0434");
    params.put("fondID", "30");
    params.put("range", invRangeDlg2.getNumberRange());
    params.put("startNumber", invRangeDlg2.getStartNumber());
    params.put("endNumber", invRangeDlg2.getEndNumber());
    params.put("startPage", new Integer(invRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKDFDatum() {
    dateRangeDlg2.setVisible(true);
    if (dateRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u0414\u0435\u0447\u0438\u0458\u0438 \u0444\u043e\u043d\u0434");
    params.put("fondID", "30");
    params.put("range", dateRangeDlg2.getDateRange());
    params.put("startDate", dateRangeDlg2.getStartDate());
    params.put("endDate", dateRangeDlg2.getEndDate());
    params.put("startPage", new Integer(dateRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKPFBroj() {
    invRangeDlg2.setStartNumber("0121XXXXXXX");
    invRangeDlg2.setEndNumber("0121XXXXXXX");
    invRangeDlg2.setVisible(true);
    if (invRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u041f\u0440\u0438\u0440\u0443\u0447\u043d\u0438\u0446\u0438");
    params.put("fondID", "21");
    params.put("range", invRangeDlg2.getNumberRange());
    params.put("startNumber", invRangeDlg2.getStartNumber());
    params.put("endNumber", invRangeDlg2.getEndNumber());
    params.put("startPage", new Integer(invRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKPFDatum() {
    dateRangeDlg2.setVisible(true);
    if (dateRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u041f\u0440\u0438\u0440\u0443\u0447\u043d\u0438\u0446\u0438");
    params.put("fondID", "21");
    params.put("range", dateRangeDlg2.getDateRange());
    params.put("startDate", dateRangeDlg2.getStartDate());
    params.put("endDate", dateRangeDlg2.getEndDate());
    params.put("startPage", new Integer(dateRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKLFBroj() {
    invRangeDlg2.setStartNumber("0160XXXXXXX");
    invRangeDlg2.setEndNumber("0160XXXXXXX");
    invRangeDlg2.setVisible(true);
    if (invRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u041b\u0435\u0433\u0430\u0442");
    params.put("fondID", "60");
    params.put("range", invRangeDlg2.getNumberRange());
    params.put("startNumber", invRangeDlg2.getStartNumber());
    params.put("endNumber", invRangeDlg2.getEndNumber());
    params.put("startPage", new Integer(invRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGIKLFDatum() {
    dateRangeDlg2.setVisible(true);
    if (dateRangeDlg2.isCancelled())
      return;
    Map params = new HashMap();
    params.put("fond", "\u041b\u0435\u0433\u0430\u0442");
    params.put("fondID", "60");
    params.put("range", dateRangeDlg2.getDateRange());
    params.put("startDate", dateRangeDlg2.getStartDate());
    params.put("endDate", dateRangeDlg2.getEndDate());
    params.put("startPage", new Integer(dateRangeDlg2.getStartPage()));
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.InvKnjigaMonografske.class, 
        params);
  }
  
  private void handle_BGBrojPremaUDK() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.StatistikaPoUDK.class, 
        params);
  }
  
  private void handle_BGBrojPremaNacinuNabavke() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.StatistikaPoNacinuNabavke.class, 
        params);
  }
  
  private void handle_BGBrojPremaInvKnjigama() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.StatistikaPoInventarnimKnjigama.class, 
        params);
  }
  
  private void handle_BGBrojPremaFondovima() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.StatistikaPoFondovima.class, 
        params);
  }
  
  private void handle_BGBrojPremaNacinuNabavkePoFondovima() {
    dateRangeDlg.setVisible(true);
    if (dateRangeDlg.isCancelled())
      return;
    Map params = new HashMap();
    params.put("dateRange", dateRangeDlg.getDateRange());
    params.put("startDate", dateRangeDlg.getStartDate());
    params.put("endDate", dateRangeDlg.getEndDate());
    ReportUtils.showReport(false, reportProgress, 
        com.gint.app.bisis.editor.recordstats.bg.StatistikaNacinaNabavkePoFondovima.class, 
        params);
  }
  
  private boolean areYouSure() {
    String defBtn = " Da ";
    String[] buttons = {defBtn, " Ne "};
    int answer = JOptionPane.showOptionDialog(this, 
        "Ova operacija mo\u017ee dugo trajati. Da li ste sigurni da \u017eelite da je pokrenete?", 
        "Dugotrajna operacija", JOptionPane.YES_NO_OPTION, 
        JOptionPane.INFORMATION_MESSAGE, null, buttons, defBtn);
    return answer == JOptionPane.YES_OPTION;
  }
  
  public static void main(String[] args) {
    try {
//      System.out.println("opening a connection...");
//      Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
//      java.sql.Connection conn = java.sql.DriverManager.getConnection(
//          "jdbc:sapdb://localhost/BISIS?sqlmode=ORACLE&unicode=yes&timeout=0", 
//          "centralna", "centralna");
//      conn.setAutoCommit(false);
//      com.gint.app.bisis.editor.Environment.setConnection(conn);
//      com.gint.app.bisis.editor.Environment.setReportServlet(
//          "http://localhost:8080/bisis-reports");
//      System.out.println("connected.");
      INIFile iniFile = new INIFile("config.ini"); 
      Environment.setLibraryName(iniFile.getString("circ", "libraryName"));
      Environment.setBgNerazvrstani(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      StatsMenuFrame frame = new StatsMenuFrame(null);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
  }
  
  JMenuBar menuBar;
  MainFrame mf;
  MonitorFrame memoryMonitor;
  ReportProgress reportProgress;
  DateRangeDlg dateRangeDlg;
  DateRangeDlg2 dateRangeDlg2;
  NumberRangeDlg numberRangeDlg;
  InvRangeDlg invRangeDlg;
  InvRangeDlg2 invRangeDlg2;
}
