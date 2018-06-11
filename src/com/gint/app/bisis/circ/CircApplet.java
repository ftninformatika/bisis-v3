package com.gint.app.bisis.circ;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

import javax.swing.*;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.util.gui.SplashScreen;
import com.gint.util.gui.MonitorFrame;

import com.gint.app.bisis.circ.reports.*;
import com.gint.app.bisis.circ.warnings.Warnings;

public class CircApplet extends JApplet {
  private int base = 0;
  private static int ret = -1;
  boolean isStandalone = false;
  DBConnection db_conn;
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar Singel = new JMenuBar();
  JMenu jMnSign = new JMenu();
  JMenuItem jMiSignNew = new JMenuItem();
  JMenuItem jMiSignLenght = new JMenuItem();
  JMenu jMnBorrow = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem4 = new JMenuItem();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem7 = new JMenuItem();
  JMenuItem jMenuItem8 = new JMenuItem();
  JMenuItem jMenuItem9 = new JMenuItem();
  JMenu jMenu3 = new JMenu();
  ActionListener aMmbrIDOK, aMmbrIDCancel ;

//  PleaseWaitDlg pleaseWaitDlg = new PleaseWaitDlg(null, "Cirkulacija", true);
  NewMember newMember;
  SingleMember singleMember;
  GroupMember groupMember;
  Institutions institutions;
  MembersGeneral membersGeneral;
  MembersFee membersFee;
  ResumeLending resumeLending;
  RenewMembership renewMembership;
  Places places;
  Returning returning;
  ExchangeOut exchangeOut;
  ExchangeIn exchangeIn;
  ExchangeAdmins exchangeAdmins;
  Lending lending;
  Reservation reservation;
  CancelReservation cancelReservation;
  ResumeReservation resumeReservation;
  MmbrID mmbrID;
  SignNo signNo;
  CircDocs circDocs;
  CtlgNo ctlgno;
  LogIn logIn;
  FindMember findMember;
  FindSign findSign;
  FindLendedPubl findLendedPubl;
//  NotReturned notReturned;
//  Report1 circReport1;
//  KnjigaUpisa knjigaUpisa;
  LendedHistory lendedHistory;
  Bibliotekar bibliotekar;
  Vrsta vrsta;
  Kategorija kategorija;
  Najcitanije najcitanije;
  NajcitanijeUdk najcitanijeudk;
  Kartica kartica;
  IzdatoVraceno izdatovraceno;
  IzdatoVracenoJezik izdatovracenojezik;
  CtgrUdk ctgrudk;
  Slikovnice slikovnice;
  Istorija istorija;
  Struktura struktura;
  QuickLending quickl;
  Zbstatistika zbstat;
  MonitorFrame memoryMonitor = new MonitorFrame();
  Strposetilaca strpos;
  Posetioci posetioci;
  Duplicate dup;
  Warnings warnings;
  Najcitaoci najcitaoci;
  ZipPlaceDlg zipplace;
  MemberBook mmbrbook;
  Groups groups;
  MembersByGroup mmbrbygrp;
  Blocked blocked;
  
  JMenuItem jMenuItem14 = new JMenuItem();
  JMenuItem jMenuItem15 = new JMenuItem();
  JMenuItem jMenuItem16 = new JMenuItem();
  JMenu jMenu5 = new JMenu();
  JMenuItem jMenuItem11 = new JMenuItem();
  JMenuItem jMnIFindSgn = new JMenuItem();
  JMenuItem jMnISearch = new JMenuItem();
//  JMenuItem jMnINotRet = new JMenuItem();
  JMenuItem jMnILendedPubl = new JMenuItem();

//  JMenuItem jMnIReport1 = new JMenuItem();

//  JMenuItem jMnIKnjigaUpisa = new JMenuItem();

  JMenuItem jMnILendedHistory = new JMenuItem();

  JMenuItem jMiFundSize = new JMenuItem();
  
  JMenu jMnIstorija = new JMenu(Messages.get("CIRCAPPLET_JMNISTORIJA_TEXT"));
  
  JMenu jMnKnjiga = new JMenu(Messages.get("CIRCAPPLET_JMNKNJIGA_TEXT"));
  
  JMenu jMnStatisticki = new JMenu(Messages.get("CIRCAPPLET_JMNSTATISTICKI_TEXT"));
  
  JMenuItem jMiBibliotekar = new JMenuItem(Messages.get("CIRCAPPLET_JMIBIBLIOTEKAR_TEXT"));
  
  JMenuItem jMiVrsta = new JMenuItem(Messages.get("CIRCAPPLET_JMIVRSTA_TEXT"));
  
  JMenuItem jMiKategorija = new JMenuItem(Messages.get("CIRCAPPLET_JMIKATEGORIJA_TEXT"));
  
  JMenuItem jMiNajcitanije = new JMenuItem(Messages.get("CIRCAPPLET_JMINAJCITANIJE_TEXT"));
  
  JMenuItem jMiNajcitanijeudk = new JMenuItem(Messages.get("CIRCAPPLET_JMINAJCITANIJEUDK_TEXT"));
  
  JMenuItem jMiKartica = new JMenuItem(Messages.get("CIRCAPPLET_JMIKARTICA_TEXT"));
  
  JMenuItem jMiIzdatovraceno = new JMenuItem(Messages.get("CIRCAPPLET_JMIIZDATOVRACENO_TEXT"));
  
  JMenuItem jMiIzdatovracenojezik = new JMenuItem(Messages.get("CIRCAPPLET_JMIIZDATOVRACENOJEZIK_TEXT"));
  
  JMenuItem jMiCtgrudk = new JMenuItem(Messages.get("CIRCAPPLET_JMICTGRUDK_TEXT"));
  
  JMenuItem jMiSlikovnice = new JMenuItem(Messages.get("CIRCAPPLET_JMISLIKOVNICE_TEXT"));
  
  JMenuItem jMiIstorija = new JMenuItem(Messages.get("CIRCAPPLET_JMIISTORIJA_TEXT"));
  
  JMenuItem jMiStruktura = new JMenuItem(Messages.get("CIRCAPPLET_JMISTRUKTURA_TEXT"));
  
  JMenuItem jMiStrpos = new JMenuItem(Messages.get("CIRCAPPLET_JMISTRPOS_TEXT"));
  
  JMenuItem jMiPosetioci = new JMenuItem(Messages.get("CIRCAPPLET_JMIPOSETIOCI_TEXT"));
  
  JMenuItem jMiZbstat = new JMenuItem(Messages.get("CIRCAPPLET_JMIZBSTAT_TEXT"));
  
  JMenuItem jMiQuickl = new JMenuItem(Messages.get("CIRCAPPLET_JMIQUICKL_TEXT"));
  
  JMenuItem jMiMemoryMonitor = new JMenuItem("Monitor");
  
  JMenuItem jMiWarnings = new JMenuItem(Messages.get("CIRCAPPLET_JMIWARNINGS_TEXT"));

  JMenuItem jMiNajcitaoci = new JMenuItem(Messages.get("CIRCAPPLET_JMINAJCITAOCI_TEXT"));
  
  JMenuItem jMiMmbrbook = new JMenuItem(Messages.get("CIRCAPPLET_JMIMMBRBOOK_TEXT"));
  
  JMenu jMnGroups = new JMenu(Messages.get("CIRCAPPLET_JMNGROUPS_TEXT"));
  
  JMenuItem jMiGroups = new JMenuItem(Messages.get("CIRCAPPLET_JMIGROUPS_TEXT"));
  
  JMenuItem jMiMmbrbygrp = new JMenuItem(Messages.get("CIRCAPPLET_JMIMMBRBYGRP_TEXT"));
  
  JMenuItem jMiBlocked = new JMenuItem(Messages.get("CIRCAPPLET_JMIBLOCKED_TEXT"));
  
//Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

//Construct the applet
  public CircApplet() {
//    System.setSecurityManager(new RMISecurityManager());
  }


//Initialize the applet
  public void init() {
    this.setSize(700, 400);
    try {
      SplashScreen splash = new SplashScreen("/com/gint/app/bisis/circ/images/"+Messages.get("SPLASH_FILE_NAME")); //setup.gif");

  // kacenje na bazu
      db_conn = new DBConnection();
      splash.setVisible(false);
      splash = null;
      logIn = new LogIn(Messages.get("CIRCAPPLET_LOGIN_TITLE"),db_conn, true);
      logIn.setLocationRelativeTo(null);
      logIn.setResizable(false);

      ret = logIn.setModalVisible();
      if (ret != 1) {
        db_conn.getConnection().close();
        System.out.println("Connection closed.");
        System.exit(0);
      }
      splash = new SplashScreen("/com/gint/app/bisis/circ/images/"+Messages.get("SPLASH_FILE_NAME")); //setup.gif");
      jbInit();
      splash.setVisible(false);
      splash = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


//Component initialization
  private void jbInit() throws Exception {
    // kreiranje DataSet-ova
    db_conn.createDataSets();

    
    zipplace = new ZipPlaceDlg();
    zipplace.setLocationRelativeTo(null);
    zipplace.setResizable(false);
    newMember = new NewMember(Messages.get("CIRCAPPLET_NEWMEMBER_TITLE"), db_conn, false);
    newMember.setLocationRelativeTo(null);
    newMember.setResizable(false);
    dup = new Duplicate(null, db_conn, true);
    dup.setLocationRelativeTo(null);
    dup.setResizable(false);
    singleMember = new SingleMember(Messages.get("CIRCAPPLET_SINGLEMEMBER_TITLE"), db_conn, false, dup);
    singleMember.setLocationRelativeTo(null);
    singleMember.setResizable(false);
    groupMember = new GroupMember(Messages.get("CIRCAPPLET_GROUPMEMBER_TITLE"), db_conn, true);
    groupMember.setLocationRelativeTo(null);
    groupMember.setResizable(false);
    institutions = new Institutions(Messages.get("CIRCAPPLET_INSTITUTIONS_TITLE"), db_conn, true);
    institutions.setLocationRelativeTo(null);
    institutions.setResizable(false);
    membersGeneral = new MembersGeneral(Messages.get("CIRCAPPLET_MEMBERSGENERAL_TITLE"), db_conn, true,newMember,singleMember);
    membersGeneral.setLocationRelativeTo(null);
    membersGeneral.setResizable(false);
    membersFee = new MembersFee(Messages.get("CIRCAPPLET_MEMBERSFEE_TITLE"), db_conn, true);
    membersFee.setLocationRelativeTo(null);
    membersFee.setResizable(false);

    resumeLending = new ResumeLending(Messages.get("CIRCAPPLET_RESUMELENDING_TITLE"), db_conn, true);
    resumeLending.setLocationRelativeTo(null);
    resumeLending.setResizable(false);
    renewMembership = new RenewMembership(Messages.get("CIRCAPPLET_RENEWMEMBERSHIP_TITLE"), db_conn, true);
    renewMembership.setLocationRelativeTo(null);
    renewMembership.setResizable(false);
    
    istorija = new Istorija(Messages.get("CIRCAPPLET_ISTORIJA_TITLE"), db_conn, false);
    istorija.setLocationRelativeTo(null);
    istorija.setResizable(false);
    
    returning = new Returning(Messages.get("CIRCAPPLET_RETURNING_TITLE"), db_conn, true);
    returning.setLocationRelativeTo(null);
    returning.setResizable(false);
    exchangeOut = new ExchangeOut(Messages.get("CIRCAPPLET_EXCHANGEOUT_TITLE"), db_conn, true);
    exchangeOut.setLocationRelativeTo(null);
    exchangeOut.setResizable(false);
    exchangeIn = new ExchangeIn(Messages.get("CIRCAPPLET_EXCHANGEIN_TITLE"), db_conn, true);
    exchangeIn.setLocationRelativeTo(null);
    exchangeIn.setResizable(false);
    exchangeAdmins = new ExchangeAdmins(Messages.get("CIRCAPPLET_EXCHANGEADMINS_TITLE"),db_conn, true);
    exchangeAdmins.setLocationRelativeTo(null);
    exchangeAdmins.setResizable(false);
    findSign = new FindSign(Messages.get("CIRCAPPLET_FINDSIGN_TITLE"), db_conn, true);
    findSign.setLocationRelativeTo(null);
    findSign.setResizable(false);
    signNo = new SignNo(Messages.get("CIRCAPPLET_SIGNNO_TITLE"), true, db_conn.getConnection());
    signNo.setLocationRelativeTo(null);
    signNo.setResizable(false);
    circDocs = new CircDocs(Messages.get("CIRCAPPLET_CIRCDOCS_TITLE"),db_conn, true,signNo);
    circDocs.setLocationRelativeTo(null);
    circDocs.setResizable(false);
    lending = new Lending(Messages.get("CIRCAPPLET_LENDING_TITLE"), db_conn, false, circDocs,returning,istorija);
    lending.setLocationRelativeTo(null);
    lending.setResizable(false);
    reservation = new Reservation(Messages.get("CIRCAPPLET_RESERVATION_TITLE"), db_conn, true);
    reservation.setLocationRelativeTo(null);
    reservation.setResizable(false);
    cancelReservation = new CancelReservation (Messages.get("CIRCAPPLET_CANCELRESERVATION_TITLE"), db_conn);
    cancelReservation.setLocationRelativeTo(null);
    cancelReservation.setResizable(false);
    resumeReservation=new ResumeReservation(Messages.get("CIRCAPPLET_RESUMERESERVATION"), db_conn, true);
    resumeReservation.setLocationRelativeTo(null);
    resumeReservation.setResizable(false);
    findMember = new FindMember(Messages.get("CIRCAPPLET_FINDMEMBER_TITLE"), db_conn, true);
    findMember.setLocationRelativeTo(null);
    findMember.setResizable(false);
    ctlgno = new CtlgNo(Messages.get("CIRCAPPLET_FINDLENDEDPUBL_TITLE"), true);
    ctlgno.setLocationRelativeTo(null);
    ctlgno.setResizable(false);
    findLendedPubl = new FindLendedPubl(Messages.get("CIRCAPPLET_FINDLENDEDPUBL_TITLE"), db_conn, true,circDocs,ctlgno);
    findLendedPubl.setLocationRelativeTo(null);
    findLendedPubl.setResizable(false);
    mmbrID = new MmbrID(Messages.get("CIRCAPPLET_MMBRID_TITLE"), findMember, true);
    mmbrID.setLocationRelativeTo(null);
    mmbrID.setResizable(false);
   
    quickl = new QuickLending(Messages.get("CIRCAPPLET_QUICKL_TITLE"), false, db_conn,returning, istorija, singleMember, resumeLending);
    quickl.setLocationRelativeTo(null);
    quickl.setResizable(false);
/*
    logIn = new LogIn("Prijavljivanje",db_conn, true);
    logIn.setLocationRelativeTo(null);
    logIn.setResizable(false);
*/
/*
    notReturned = new NotReturned("Spisak du\u017enika", db_conn, true);
    notReturned.setLocationRelativeTo(null);
    notReturned.setResizable(false);
*/
//    circReport1 = new Report1(Messages.get("CIRCAPPLET_REPORT1_TITLE"), db_conn, true);
//    circReport1.setLocationRelativeTo(null);
//    circReport1.setResizable(false);

//    knjigaUpisa = new KnjigaUpisa(Messages.get("CIRCAPPLET_KNJIGAUPISA_TITLE"), db_conn, true);
//    knjigaUpisa.setLocationRelativeTo(null);
//    knjigaUpisa.setResizable(false);

    lendedHistory = new LendedHistory(Messages.get("CIRCAPPLET_LENDED_HISTORY"), db_conn, true);
    lendedHistory.setLocationRelativeTo(null);
    lendedHistory.setResizable(false);
    
    bibliotekar = new Bibliotekar(Messages.get("CIRCAPPLET_BIBLIOTEKAR_TITLE"), db_conn, false);
    bibliotekar.setLocationRelativeTo(null);
    bibliotekar.setResizable(false);
    
    vrsta = new Vrsta(Messages.get("CIRCAPPLET_VRSTA_TITLE"), db_conn, false);
    vrsta.setLocationRelativeTo(null);
    vrsta.setResizable(false);
    
    kategorija = new Kategorija(Messages.get("CIRCAPPLET_KATEGORIJA_TITLE"), db_conn, false);
    kategorija.setLocationRelativeTo(null);
    kategorija.setResizable(false);
    
    najcitanije = new Najcitanije(Messages.get("CIRCAPPLET_NAJCITANIJE_TITLE"), db_conn, false);
    najcitanije.setLocationRelativeTo(null);
    najcitanije.setResizable(false);
    
    najcitanijeudk = new NajcitanijeUdk(Messages.get("CIRCAPPLET_NAJCITANIJEUDK_TITLE"), db_conn, false);
    najcitanijeudk.setLocationRelativeTo(null);
    najcitanijeudk.setResizable(false);
    
    kartica = new Kartica(Messages.get("CIRCAPPLET_KARTICA_TITLE"), db_conn, false);
    kartica.setLocationRelativeTo(null);
    kartica.setResizable(false);
    
    izdatovraceno = new IzdatoVraceno(Messages.get("CIRCAPPLET_IZDATOVRACENO_TITLE"), db_conn, false);
    izdatovraceno.setLocationRelativeTo(null);
    izdatovraceno.setResizable(false);
    
    izdatovracenojezik = new IzdatoVracenoJezik(Messages.get("CIRCAPPLET_IZDATOVRACENOJEZIK_TITLE"), db_conn, false);
    izdatovracenojezik.setLocationRelativeTo(null);
    izdatovracenojezik.setResizable(false);
    
    ctgrudk = new CtgrUdk(Messages.get("CIRCAPPLET_CTGRUDK_TITLE"), db_conn, false);
    ctgrudk.setLocationRelativeTo(null);
    ctgrudk.setResizable(false);

    places = new Places(Messages.get("CIRCAPPLET_PLACES_HISTORY"), db_conn, true, lending,newMember,singleMember);
    places.setLocationRelativeTo(null);
    places.setResizable(false);
    
    slikovnice = new Slikovnice(Messages.get("CIRCAPPLET_SLIKOVNICE_TITLE"), db_conn, false);
    slikovnice.setLocationRelativeTo(null);
    slikovnice.setResizable(false);
    
    
    zbstat = new Zbstatistika(Messages.get("CIRCAPPLET_ZBSTAT_TITLE"), db_conn, false);
    zbstat.setLocationRelativeTo(null);
    zbstat.setResizable(false);
    
    struktura = new Struktura(Messages.get("CIRCAPPLET_STRUKTURA_TITLE"), db_conn, false);
    struktura.setLocationRelativeTo(null);
    struktura.setResizable(false);
    
    strpos = new Strposetilaca(Messages.get("CIRCAPPLET_STRPOS_TITLE"), db_conn, false);
    strpos.setLocationRelativeTo(null);
    strpos.setResizable(false);
    
    posetioci = new Posetioci(Messages.get("CIRCAPPLET_POSETIOCI_TITLE"), db_conn, false);
    posetioci.setLocationRelativeTo(null);
    posetioci.setResizable(false);
    
    najcitaoci = new Najcitaoci(Messages.get("CIRCAPPLET_NAJCITAOCI_TITLE"), db_conn, false);
    najcitaoci.setLocationRelativeTo(null);
    najcitaoci.setResizable(false);
    
    mmbrbook = new MemberBook(Messages.get("CIRCAPPLET_MMBRBOOK_TITLE"), db_conn, false);
    mmbrbook.setLocationRelativeTo(null);
    mmbrbook.setResizable(false);
    
    groups = new Groups(Messages.get("CIRCAPPLET_GROUPS_TITLE"), db_conn, false);
    groups.setLocationRelativeTo(null);
    groups.setResizable(false);
    
    mmbrbygrp = new MembersByGroup(Messages.get("CIRCAPPLET_MMBRBYGRP_TITLE"), db_conn, false);
    mmbrbygrp.setLocationRelativeTo(null);
    mmbrbygrp.setResizable(false);
    
    blocked = new Blocked(Messages.get("CIRCAPPLET_BLOCKED_TITLE"), db_conn, false);
    blocked.setLocationRelativeTo(null);
    blocked.setResizable(false);

    aMmbrIDOK = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String value = mmbrID.getValue();      
        mmbrID.clear();
        mmbrID.setVisible(false);
        switch(findUserType(value)){
          case -1:
            JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSGDIALOG_MSG1")+value+Messages.get("CIRCAPPLET_OPTIONPANE_MSGDIALOG_MSG2"), Messages.get("CIRCAPPLET_OPTIONPANE_MSGDIALOG_MSG2"),JOptionPane.ERROR_MESSAGE);
            break;
          case 1:
            activateDialog(1+base, value);
            break;
          case 2:
            activateDialog(2+base, value);
            break;
          case 3:
            activateDialog(3+base, value);
            break;          	
          default:
        }
      }
    };

    aMmbrIDCancel = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        mmbrID.clear();
        mmbrID.setVisible(false);
      }
    };

    mmbrID.addOKListener(aMmbrIDOK);
    mmbrID.addCancelListener(aMmbrIDCancel);

    this.getContentPane().setLayout(borderLayout1);
    this.setSize(700,400);
    jMnSign.setText(Messages.get("CIRCAPPLET_JMNSIGN_TEXT"));
    jMnSign.setMnemonic(KeyEvent.VK_U);

    jMiSignLenght.setText(Messages.get("CIRCAPPLET_JMNSIGNLENGTH_TEXT"));
    jMiSignLenght.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMiSignLenght_actionPerformed(e);
      }
    });
    jMiSignNew.setText(Messages.get("CIRCAPPLET_JMNSIGNNEW_TEXT"));
    jMiSignNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
    jMiSignNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMiSignNew_actionPerformed(e);
      }
    });
    jMnBorrow.setText(Messages.get("CIRCAPPLET_JMNBORROW_TEXT"));
//    jMnBorrow.setMnemonic(KeyEvent.VK_Z);
    jMenuItem1.setText(Messages.get("CIRCAPPLET_JMENUITEM1_TEXT"));
//    jMenuItem1.setMnemonic(KeyEvent.VK_Z);
    jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));  // Tanja
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem1_actionPerformed(e);
      }
    });

    jMenuItem2.setText(Messages.get("CIRCAPPLET_JMENUITEM2_TEXT"));
//    jMenuItem2.setMnemonic(KeyEvent.VK_R);
    jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));  // Tanja
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem2_actionPerformed(e);
      }
    });

    jMenuItem3.setText(Messages.get("CIRCAPPLET_JMENUITEM3_TEXT"));
//    jMenuItem3.setMnemonic(KeyEvent.VK_P);
    jMenuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,0));  // Tanja
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem3_actionPerformed(e);
      }
    });

    jMenu1.setText(Messages.get("CIRCAPPLET_JMENU1_TEXT"));
    jMenuItem4.setText(Messages.get("CIRCAPPLET_JMENUITEM4_TEXT"));
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem4_actionPerformed(e);
      }
    });

    jMnIFindSgn.setText(Messages.get("CIRCAPPLET_JMNIFINDSIGN_TEXT"));
    jMnIFindSgn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnIFindSgn_actionPerformed(e);
      }
    });

/*
    jMnINotRet.setText("Spisak du\u017enika");
    jMnINotRet.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnINotRet_actionPerformed(e);
      }
    });
*/
/*    jMnIReport1.setText(Messages.get("CIRCAPPLET_JMNIREPORT1_TEXT"));
    jMnIReport1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnIReport1_actionPerformed(e);
      }
    });

    jMnIKnjigaUpisa.setText(Messages.get("CIRCAPPLET_JMNIKNJIGAUPISA_TEXT"));
    jMnIKnjigaUpisa.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnIKnjigaUpisa_actionPerformed(e);
      }
    });*/

    jMnILendedHistory.setText(Messages.get("CIRCAPPLET_JMNILENDEDHISTORY_TEXT"));
    jMnILendedHistory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnILendedHistory_actionPerformed(e);
      }
    });

    jMiFundSize.setText(Messages.get("CIRCAPP_JMNIFUNDSIZE_TEXT"));
    jMiFundSize.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMiFundSize_actionPerformed(e);
      }
    });


    jMenuItem5.setText(Messages.get("CIRCAPPLET_JMENUITEM5_TEXT"));
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });

    jMenuItem6.setText(Messages.get("CIRCAPPLET_JMENUITEM6_TEXT"));
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem6_actionPerformed(e);
      }
    });

    jMenu2.setText(Messages.get("CIRCAPPLET_JMENU2_TEXT"));
    jMenu2.setMnemonic(KeyEvent.VK_P);
    jMenuItem7.setText(Messages.get("CIRCAPPLET_JMENUITEM7_TEXT"));
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem7_actionPerformed(e);
      }
    });

    jMenuItem8.setText(Messages.get("CIRCAPPLET_JMENUITEM8_TEXT"));
    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem8_actionPerformed(e);
      }
    });

    jMenuItem9.setText(Messages.get("CIRCAPPLET_JMENUITEM9_TEXT"));
    jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem9_actionPerformed(e);
      }
    });

    jMenu3.setText(Messages.get("CIRCAPPLET_JMENU3_TEXT"));
    jMenu3.setMnemonic(KeyEvent.VK_S);

    jMenuItem14.setText(Messages.get("CIRCAPPLET_JMENUITEM14_TEXT"));
    jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem14_actionPerformed(e);
      }
    });

    jMenuItem15.setText(Messages.get("CIRCAPPLET_JMENUITEM15_TEXT"));
    jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem15_actionPerformed(e);
      }
    });

    jMenuItem16.setText(Messages.get("CIRCAPPLET_JMENUITEM16_TEXT"));
    jMenu5.setText(Messages.get("CIRCAPPLET_JMENU5_TEXT"));
    jMenu5.setMnemonic(KeyEvent.VK_I);
    jMenuItem11.setText(Messages.get("CIRCAPPLET_JMENUITEM11_TEXT"));
    jMnISearch.setText(Messages.get("CIRCAPPLET_JMNISEARCH_TEXT"));
    jMnISearch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnISearch_actionPerformed(e);
      }
    });
    jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem11_actionPerformed(e);
      }
    });
    jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem16_actionPerformed(e);
      }
    });

    jMnILendedPubl.setText(Messages.get("CIRCAPPLET_JMNILENDEDPUBL_TEXT"));
    jMnILendedPubl.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMnILendedPubl_actionPerformed(e);
      }
    });
    
    jMnKnjiga.setText(Messages.get("CIRCAPPLET_JMNIKNJIGAUPISA_TEXT"));
    jMiBibliotekar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMiBibliotekar_actionPerformed(e);
      }
    });
    
    jMiKategorija.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiKategorija_actionPerformed(e);
        }
      });
    
    jMiVrsta.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiVrsta_actionPerformed(e);
        }
      });
    
    jMnStatisticki.setText("Statisti\u010dki");
    jMiNajcitanije.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiNajcitanije_actionPerformed(e);
        }
      });
    jMiNajcitanijeudk.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiNajcitanijeudk_actionPerformed(e);
        }
      });
    jMiKartica.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiKartica_actionPerformed(e);
        }
      });
    jMiIzdatovraceno.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiIzdatovraceno_actionPerformed(e);
        }
      });
    jMiIzdatovracenojezik.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiIzdatovracenojezik_actionPerformed(e);
        }
      });
    jMiCtgrudk.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiCtgrudk_actionPerformed(e);
        }
      });
    
    jMiSlikovnice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiSlikovnice_actionPerformed(e);
        }
      });
    
    jMiIstorija.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiIstorija_actionPerformed(e);
        }
      });
    
    jMiZbstat.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiZbstat_actionPerformed(e);
        }
      });
    
    jMiStruktura.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiStruktura_actionPerformed(e);
        }
      });
    
    jMiStrpos.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiStrpos_actionPerformed(e);
        }
      });
    
    jMiPosetioci.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiPosetioci_actionPerformed(e);
        }
      });
    
    jMiQuickl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
    jMiQuickl.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiQuickl_actionPerformed(e);
        }
      });
    
    jMiMemoryMonitor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMiMemoryMonitor_actionPerformed(e);
      }
    });
    
    jMiWarnings.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            jMiWarnings_actionPerformed(e);
          }
        });
        
    jMiNajcitaoci.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiNajcitaoci_actionPerformed(e);
        }
      });
   
    
    jMiMmbrbook.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiMmbrbook_actionPerformed(e);
        }
      });
    
    jMiGroups.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiGroups_actionPerformed(e);
        }
      });
    
    jMiMmbrbygrp.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiMmbrbygrp_actionPerformed(e);
        }
      });
    
    jMiBlocked.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jMiBlocked_actionPerformed(e);
        }
      });

    Singel.add(jMnSign);
    Singel.add(jMnBorrow);
    Singel.add(jMenu1);
    Singel.add(jMenu2);
    Singel.add(jMenu5);
    Singel.add(Box.createHorizontalGlue());
    Singel.add(jMenu3);
    jMnSign.add(jMiSignNew);
    jMnSign.add(jMiSignLenght);
    jMnSign.addSeparator();
    jMnSign.add(jMenuItem11);
    jMnBorrow.add(jMenuItem1);
    jMnBorrow.add(jMenuItem2);
    jMnBorrow.add(jMiQuickl);
    jMnBorrow.addSeparator();
    jMnBorrow.add(jMenuItem3);
    jMnBorrow.addSeparator();
    jMnBorrow.add(jMiSlikovnice);
    jMenu1.add(jMenuItem4);
    jMenu1.add(jMenuItem5);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem6);
    jMenu2.add(jMenuItem7);
    jMenu2.add(jMenuItem8);
    jMenu2.addSeparator();
    jMenu2.add(jMenuItem9);
//    jMenu3.add(jMenuItem10);
    jMenu3.add(jMenuItem14);
    jMenu3.add(jMenuItem15);
    jMenu3.add(jMenuItem16);
    jMenu3.addSeparator();
    jMenu3.add(jMnIFindSgn);
    jMenu3.addSeparator();
    jMenu3.add(jMiMemoryMonitor);
    this.getRootPane().setJMenuBar(Singel);
    jMenu5.add(jMnISearch);
    jMenu5.add(jMnILendedPubl);
//    jMenu5.add(jMnINotRet);
//    jMenu5.add(jMnIReport1);
//    jMenu5.add(jMnIKnjigaUpisa);
    jMenu5.add(jMiStruktura);
    jMenu5.add(jMiZbstat);
    jMenu5.add(jMnKnjiga);
    jMenu5.add(jMiStrpos);
    jMenu5.add(jMiPosetioci);
    jMenu5.add(jMnGroups);
    jMenu5.add(jMnIstorija);
    jMenu5.add(jMiFundSize);
    jMenu5.add(jMiBlocked);
    jMenu5.add(jMnStatisticki);
    jMenu5.add(jMiWarnings);
    jMnKnjiga.add(jMiBibliotekar);
    jMnKnjiga.add(jMiVrsta);
    jMnKnjiga.add(jMiKategorija);
    jMnKnjiga.add(jMiMmbrbook);
    jMnStatisticki.add(jMiNajcitanije);
    jMnStatisticki.add(jMiNajcitanijeudk);
    jMnStatisticki.add(jMiKartica);
    jMnStatisticki.add(jMiIzdatovraceno);
    jMnStatisticki.add(jMiIzdatovracenojezik);
    jMnStatisticki.add(jMiCtgrudk);
    jMnStatisticki.add(jMiNajcitaoci);
    jMnIstorija.add(jMiIstorija);
    jMnIstorija.add(jMnILendedHistory);
    jMnGroups.add(jMiGroups);
    jMnGroups.add(jMiMmbrbygrp);
    
    

    Singel.requestFocus();
  }

//Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }

//Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }

  void jMiSignNew_actionPerformed(ActionEvent e) {
    newMember.setVisible(true);
  }

// produzenje
  void jMiSignLenght_actionPerformed(ActionEvent e) {
    base = 20;
    mmbrID.setVisible(true);
  }

// lending
  void jMenuItem1_actionPerformed(ActionEvent e) {
    base = 30;
    mmbrID.setVisible(true);
  }

// returning
  void jMenuItem2_actionPerformed(ActionEvent e) {
    base = 40;
    mmbrID.setVisible(true);
  }

// resume lending
  void jMenuItem3_actionPerformed(ActionEvent e) {
    base = 50;
    mmbrID.setVisible(true);
  }
  
  void jMiSlikovnice_actionPerformed(ActionEvent e){
  	base = 60;
  	mmbrID.setVisible(true);
  }
  
  void jMiQuickl_actionPerformed(ActionEvent e){
  	base = 70;
  	mmbrID.setVisible(true);
  }
  
  void jMiMemoryMonitor_actionPerformed(ActionEvent e) {
    memoryMonitor.setVisible(true);
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {
    reservation.setVisible(true);
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
    resumeReservation.setVisible(true);
  }

  void jMenuItem6_actionPerformed(ActionEvent e) {
    cancelReservation.setVisible(true);
  }

  void jMenuItem7_actionPerformed(ActionEvent e) {
    exchangeOut.setVisible(true);
  }

  void jMenuItem8_actionPerformed(ActionEvent e) {
    exchangeIn.setVisible(true);
  }

  void jMenuItem9_actionPerformed(ActionEvent e) {
    exchangeAdmins.setVisible(true);
  }

// promena podataka
  void jMenuItem11_actionPerformed(ActionEvent e) {
    base = 10;
    mmbrID.setVisible(true);
  }

  void jMenuItem14_actionPerformed(ActionEvent e) {
	  if (db_conn.isOfficialAdmin()){
		  membersGeneral.setVisible(true);
	  }else{
		  JOptionPane.showMessageDialog(null, Messages.get("CIRCAPPLET_OPTIONPANE_MSG10"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG9"), JOptionPane.ERROR_MESSAGE);
	  }
  }

  void jMenuItem15_actionPerformed(ActionEvent e) {
	  if (db_conn.isOfficialAdmin()){
		  membersFee.setVisible(true);
	  }else{
		  JOptionPane.showMessageDialog(null, Messages.get("CIRCAPPLET_OPTIONPANE_MSG10"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG9"), JOptionPane.ERROR_MESSAGE);
	  } 
  }

  void jMenuItem16_actionPerformed(ActionEvent e) {
	  if (db_conn.isOfficialAdmin()){
		  places.setVisible(true);
	  }else{
		  JOptionPane.showMessageDialog(null, Messages.get("CIRCAPPLET_OPTIONPANE_MSG10"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG9"), JOptionPane.ERROR_MESSAGE);
	  }  
  }

  void jMenuItem12_actionPerformed(ActionEvent e) {
    singleMember.setVisible(true);
  }

  void jMenuItem13_actionPerformed(ActionEvent e) {
    groupMember.setVisible(true);
  }

  void jMenuItem17_actionPerformed(ActionEvent e) {
    institutions.setVisible(true);
  }
  
  void jMiBibliotekar_actionPerformed(ActionEvent e){
  	bibliotekar.setVisible(true);
  }
  
  void jMiKategorija_actionPerformed(ActionEvent e){
  	kategorija.setVisible(true);
  }
  
  void jMiNajcitanije_actionPerformed(ActionEvent e){
  	najcitanije.setVisible(true);
  }
  
  void jMiNajcitanijeudk_actionPerformed(ActionEvent e){
  	najcitanijeudk.setVisible(true);
  }
  
  void jMiKartica_actionPerformed(ActionEvent e){
  	kartica.setVisible(true);
  }
  
  void jMiIzdatovraceno_actionPerformed(ActionEvent e){
  	izdatovraceno.setVisible(true);
  }
  
  void jMiIzdatovracenojezik_actionPerformed(ActionEvent e){
	  	izdatovracenojezik.setVisible(true);
	  }
  
  void jMiCtgrudk_actionPerformed(ActionEvent e){
  	ctgrudk.setVisible(true);
  }
  
  void jMiVrsta_actionPerformed(ActionEvent e){
  	vrsta.setVisible(true);
  }
  
  void jMiIstorija_actionPerformed(ActionEvent e){
  	istorija.setVisible(true);
  }
  
  void jMiStruktura_actionPerformed(ActionEvent e){
  	struktura.setVisible(true);
  }
  void jMiStrpos_actionPerformed(ActionEvent e){
  	strpos.setVisible(true);
  }
  void jMiPosetioci_actionPerformed(ActionEvent e){
  	posetioci.setVisible(true);
  }
  void jMiZbstat_actionPerformed(ActionEvent e){
  	zbstat.setVisible(true);
  }
  void jMiWarnings_actionPerformed(ActionEvent e){
	  if (db_conn.isOfficialAdmin()){
	  	warnings = new Warnings(db_conn);
	    warnings.setLocationRelativeTo(null);
	    warnings.setResizable(false);
	    warnings.setVisible(true);
	  }else{
		  JOptionPane.showMessageDialog(null, Messages.get("CIRCAPPLET_OPTIONPANE_MSG10"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG9"), JOptionPane.ERROR_MESSAGE);
	  }
  }
  
  void jMiNajcitaoci_actionPerformed(ActionEvent e){
  	najcitaoci.setVisible(true);
  }
  
  void jMiMmbrbook_actionPerformed(ActionEvent e){
  	mmbrbook.setVisible(true);
  }
  
  void jMiGroups_actionPerformed(ActionEvent e){
  	groups.setVisible(true);
  }
  
  void jMiMmbrbygrp_actionPerformed(ActionEvent e){
  	mmbrbygrp.setVisible(true);
  }
  
  void jMiBlocked_actionPerformed(ActionEvent e){
  	blocked.setVisible(true);
  }

  public static void main(String [] args){
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }
    parent = new JFrame();
    parent.setTitle(Messages.get("CIRCAPPLET_FRAME_TITLE"));
    CircApplet ca = new CircApplet();
    parent.setSize(700,400);
    parent.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
     	System.exit(0);
      }
    });
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    parent.setLocation((d.width - parent.getSize().width) / 2 , (d.height - parent.getSize().height) / 2);
    // na frejm dodajemo aplet CircApplet
    parent.getContentPane().add(ca);
    ca.init();
    parent.setVisible(true);
  }

// vraca tip korisnika na osnovu user_id-a
  private int findUserType(String value){
    int ret = -1;
    if(!value.equals("")){
      try{
        Statement stmt = db_conn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery("select user_type from users where id='"+value+"'");
        while(rset.next()){
          ret = rset.getInt(1);
        }
        rset.close();
        db_conn.getConnection().commit();
        stmt.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    return ret;
  }

  private void activateDialog(int type, String val){
    switch(type){
      case 11 :
        singleMember.setVisible(true, val);
        break;
      case 12 :
        groupMember.setVisible(true, val);
        break;
      case 13 :
        institutions.setVisible(true,val);
        break;
      case 21 :
        renewMembership.setVisible(true, val);
        break;
      case 22:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG4"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG5"),JOptionPane.ERROR_MESSAGE);
        break;
      case 23:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG6"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG7"),JOptionPane.ERROR_MESSAGE);
        break;
      case 31 :
        lending.setVisible(true,val);
        break;
      case 32:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG8"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG9"),JOptionPane.ERROR_MESSAGE);
        break;
      case 33:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG10"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG11"),JOptionPane.ERROR_MESSAGE);
        break;
      case 41 :
        returning.setVisible(true,val);
        break;
      case 42:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG12"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG13"),JOptionPane.ERROR_MESSAGE);
        break;
      case 43:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG14"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG15"),JOptionPane.ERROR_MESSAGE);
        break;
      case 51 :
        resumeLending.setVisible(true,val);
        break;
      case 52:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG16"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG17"),JOptionPane.ERROR_MESSAGE);
        break;
      case 53:
        JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG18"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG19"),JOptionPane.ERROR_MESSAGE);
        break;
      case 61:
      	slikovnice.setVisible(true,val);
      	break;
      case 62:
      	JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG16"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG17"),JOptionPane.ERROR_MESSAGE);
      	break;
      case 63:
      	JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG16"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG17"),JOptionPane.ERROR_MESSAGE);
      	break;
      case 71:
      	quickl.setVisible(true,val);
      	break;
      case 72:
      	JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG16"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG17"),JOptionPane.ERROR_MESSAGE);
      	break;
      case 73:
      	JOptionPane.showMessageDialog(null,Messages.get("CIRCAPPLET_OPTIONPANE_MSG16"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG17"),JOptionPane.ERROR_MESSAGE);
      	break;	
      default:
    }
  }

  void jMnISearch_actionPerformed(ActionEvent e) {
    findMember.setVisible(true);
  }

  void jMnIFindSgn_actionPerformed(ActionEvent e) {
    findSign.setVisible(true);
  }

/*
  void jMnINotRet_actionPerformed(ActionEvent e) {
    notReturned.setVisible(true);
  }
*/
/*  void jMnIReport1_actionPerformed(ActionEvent e) {
    circReport1.setVisible(true);
  }

  void jMnIKnjigaUpisa_actionPerformed(ActionEvent e) {
    knjigaUpisa.setVisible(true);
  }*/

  void jMnILendedHistory_actionPerformed(ActionEvent e) {
    lendedHistory.setVisible(true);
  }


  void jMiFundSize_actionPerformed(ActionEvent e) {
  	if (db_conn.isOfficialAdmin()){
  		  FundSize fundSize = new FundSize(db_conn);
	  }else{
		  JOptionPane.showMessageDialog(null, Messages.get("CIRCAPPLET_OPTIONPANE_MSG10"), Messages.get("CIRCAPPLET_OPTIONPANE_MSG9"), JOptionPane.ERROR_MESSAGE);
	  } 
    
  }


  void jMnILendedPubl_actionPerformed(ActionEvent e) {
    findLendedPubl.setVisible(true);
  }

  public static JFrame parent;
}

