//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.border.*;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import com.borland.jbcl.layout.*;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;

public class SingleMember extends EscapeDialog {
  private DBConnection dbConn;

  private boolean listen = false;
  private boolean closed = true;
  private String usCtgr = "";
  private String mbType = "";

  private boolean singleFirst = true;
  private boolean singleChanged = false;
  private boolean singleTransition = false;
  private boolean usersChanged = false;
  private boolean usersTransition = false;
  private static final String constTitle = new String(Messages.get("SINGLEMEMBER_CONSTTILE_STR"));
  private MasterLinkDescriptor mld;
  private DataSet dsSingle = null, dsUsers = null, dsGroup = null, dsCategs = null
                              , dsEduc = null, dsMmbrType = null, dsLanguage = null, dsBranch = null, dsSigning = null, dsLending = null;
  JDBLookupModel cmCategs = null, cmEduc = null, cmMmbrType = null,  cmLanguage = null, cmBranch = null;
//  JDBLookupModel cmGroup = null;

  JTabbedPane tabsSingle = new JTabbedPane();

  JPanel panel1 = new JPanel();

  XYLayout borderLayout1 = new XYLayout();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout17 = new XYLayout();

  JPanel pSingle1 = new JPanel();
  JLabel lSingleMmbrID = new JLabel();
  JLabel lSingleDate = new JLabel();
  JDBLabel tfSingleMmbrID = new JDBLabel();
//  JDBTextField tfSingleDate = new JDBTextField();
  JDBLabel tfSingleDate = new JDBLabel();
  JDBCheckBox  chkSingleWarn = new JDBCheckBox();
  JLabel lSingleBranchID = new JLabel();

  JPanel pSingle2 = new JPanel();
  JLabel lSingleLastName = new JLabel();
  JLabel lSingleFirstName = new JLabel();
  JLabel lSingleAscName = new JLabel();
  JLabel lSingleOccup = new JLabel();
  JLabel lSingleZvanje = new JLabel();
  JLabel lSinglePhone = new JLabel();
  JLabel lSingleAddress = new JLabel();
  JLabel lSinglePlace = new JLabel();
  JLabel lSingleZIP = new JLabel();
  JLabel lSingleEmail = new JLabel();
  JLabel lSingleIndex = new JLabel();
  JLabel lLanguage = new JLabel();
  JDBTextField tfSingleLastName = new JDBTextField();
  JDBTextField tfSingleFirstName = new JDBTextField();
  JDBTextField tfSingleAscName = new JDBTextField();
  JDBTextField tfSingleOccup = new JDBTextField();
  JDBTextField tfSingleZvanje = new JDBTextField();
  JDBTextField tfSingleAddress = new JDBTextField();
  JDBTextField tfSinglePlace = new JDBTextField();
  JDBTextField tfSingleZIP = new JDBTextField();
  JDBTextField tfSingleIndex = new JDBTextField();
  JDBTextField tfSinglePhone = new JDBTextField();
  JDBTextField tfSingleEmail = new JDBTextField();
  
  JDBRadioButton rdbDocI = new JDBRadioButton(2);
  JLabel lSingleJMBG = new JLabel();
  JLabel lSingleDocPlace = new JLabel();
  JDBRadioButton rdbDocL = new JDBRadioButton(1);
  XYLayout xYLayout18 = new XYLayout();
  JDBTextField tfSingleJMBG = new JDBTextField();
  JLabel lSingleDocNo = new JLabel();
  JDBTextField tfSingleDocPlace = new JDBTextField();
  JDBTextField tfSingleDocNo = new JDBTextField();
  JDBRadioButton rdbDocP = new JDBRadioButton(3);

  JPanel pSingle3 = new JPanel();
  XYLayout xYLayout5 = new XYLayout();
  JPanel gboxox1 = new JPanel();
  JPanel gboxPol = new JPanel();
  JPanel gboxSingleDocument = new JPanel();
  XYLayout xYLayout12 = new XYLayout();
  JPanel gboxox2 = new JPanel();
  XYLayout xYLayout13 = new XYLayout();
  JLabel lState = new JLabel();
  JLabel lOrganization = new JLabel();
  JLabel lOrgAddress = new JLabel();
  JLabel lOrgZIP = new JLabel();
  JLabel lOrgPhone = new JLabel();
  JLabel lOrgPlace = new JLabel();
  JLabel lResideAddress = new JLabel();
  JLabel lResidePlace = new JLabel();
  JLabel lResideZIP = new JLabel();
  JLabel lResidePhone = new JLabel();
  JLabel lSingleUDK = new JLabel();
  JDBTextField tfSingleOrgName = new JDBTextField();
  JDBTextField tfSingleResideAddress = new JDBTextField();
  JDBTextField tfSingleOrgPlace = new JDBTextField();
  JDBTextField tfSingleOrgAddress = new JDBTextField();
  JDBTextField tfSingleOrgPhone = new JDBTextField();
  JDBTextField tfSingleResidePlace = new JDBTextField();
  JDBTextField tfSingleResideZIP = new JDBTextField();
  JDBTextField tfSingleResidePhone = new JDBTextField();
  JDBTextField tfSingleOrgZIP = new JDBTextField();
  JDBTextField tfInteresovanja = new JDBTextField();
  JDBTextField tfSingleGrpID = new JDBTextField();
  JScrollPane jScrollPane2 = new JScrollPane();
  JDBTextArea tSingleNote = new JDBTextArea();
  JLabel lSingleNapomena = new JLabel();

  JLabel lWarning = new JLabel();
  JLabel lWarningDesc = new JLabel();

  XYLayout xYLayout14 = new XYLayout();
  JLabel lSingleCategs = new JLabel();
  JLabel lSingleEduc = new JLabel();
  JLabel lSingleMmbrType = new JLabel();
  JLabel lSingleGrp = new JLabel();
  JDBComboBox cboCategs;
  JDBComboBox cboEduc;
  JDBComboBox cboMmbrType;
//  JDBComboBox cboGroup;
  JDBComboBox cboLanguage;
  JDBComboBox cboBranch;
  JPanel gboxSingleGrp = new JPanel();
  XYLayout xYLayout15 = new XYLayout();
  JRadioButton rdbGrpYes = new JRadioButton();
  JRadioButton rdbGrpNo = new JRadioButton();
  JDBRadioButton rdbGrpNo1 = new JDBRadioButton(1);
  JDBRadioButton rdbGrpYes1 = new JDBRadioButton(2);
  JDBRadioButton rdbAgeA = new JDBRadioButton(1);
  JDBRadioButton rdbAgeC = new JDBRadioButton(2);
  JDBTextField tfSingleMmbrship = new JDBTextField();
  JDBTextField tfSingleUntil = new JDBTextField();
  ButtonGroup rgpGroup = new ButtonGroup();
  ButtonGroup rgpPol = new ButtonGroup();
  ButtonGroup rgpDocNo = new ButtonGroup();
  ButtonGroup rgpAge = new ButtonGroup();
  JPanel gboxAge = new JPanel();
  JLabel lSingleMmbrship = new JLabel();
  JLabel lSingleUntil = new JLabel();
  XYLayout xYLayout20 = new XYLayout();
  JLabel lSingleDup = new JLabel();
  //JLabel lSingleDupDate = new JLabel();

  TitledBorder titledBorder1;

  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();
  JButton btnExit = new JButton();
  JButton btnNext = new JButton();
  JButton btnPrev = new JButton();
  JButton btnFirst = new JButton();
  JButton btnLast = new JButton();
  JButton btnPrint = new JButton();
  JButton btnBlock = new JButton();
  JButton btnDup = new JButton();
  Duplicate dup;
  int dup_no = 0;
  Timestamp dup_date = null;
  GridLayout gridLayout1 = new GridLayout();
  boolean blocked;
  BlockReason breason;
  ZipPlaceDlg zipplace;
  ActionListener al;
  private static String jmbg = "";

  JDBTextField compulsorySingleTF [] = {tfSingleLastName , tfSingleAscName,
                                        tfSingleFirstName , tfSingleAddress , tfSinglePlace ,
                                        tfSingleZIP, tfSingleJMBG, tfSingleDocPlace, tfSingleDocNo };
  String [] compSingleNames = {Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR1"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR2"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR3"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR4"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR5"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR6"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR7"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR8"),
                               Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR9")};
  
  JDBTextField compulsorySingleTF2 [] = {tfSingleLastName , tfSingleAscName,
        tfSingleFirstName , tfSingleAddress , tfSinglePlace ,
        tfSingleZIP};
  
  String [] compSingleNames2 = {Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR1"),
  								Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR2"),
								Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR3"),
								Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR4"),
								Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR5"),
								Messages.get("SINGLEMEMBER_COMPSINGLENAMES_STR6")};

  JDBTextField lengthSingleTF [] = { tfSingleLastName, tfSingleFirstName, tfSingleAscName,
                                     tfSingleOccup, tfSingleZvanje, tfSingleAddress, tfSinglePlace,
                                     tfSingleIndex, tfSinglePhone, tfSingleEmail,
                                     tfSingleJMBG, tfSingleDocPlace, tfSingleDocNo,
                                     tfSingleOrgName, tfSingleOrgPlace, tfSingleOrgAddress,tfSingleOrgPhone,
                                     tfSingleResideAddress,tfSingleResidePlace,tfSingleResidePhone,
                                     tfInteresovanja};

  int lengthSingle[] = { 30,30,30,
                            30,30,150,45,
                            15,50,50,
                            13,30,15,
                            150,30,150,30,
                            150,50,15,
                            60};

  String [] lengthSingleNames = {Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR1"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR2"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR3"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR4"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR5"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR6"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR7"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR8"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR9"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR10"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR11"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR12"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR13"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR14"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR15"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR16"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR17"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR18"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR19"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR20"),
                                 Messages.get("SINGLEMEMBER_LENGTHSINGLENAMES_STR21")};

  JDBTextField typesSingleTF [] ={tfSingleZIP,tfSingleResideZIP,tfSingleOrgZIP,tfSingleJMBG};
  int typesSingleType [] = {0,0,0,2};
  String [] typesSingleNames = {Messages.get("SINGLEMEMBER_TYPESSINGLENAMES_STR1"),
                                Messages.get("SINGLEMEMBER_TYPESSINGLENAMES_STR2"),
                                Messages.get("SINGLEMEMBER_TYPESSINGLENAMES_STR3"),
                                Messages.get("SINGLEMEMBER_TYPESSINGLENAMES_STR4")};

  public SingleMember(Frame frame, String title, boolean modal, DBConnection db, Duplicate dup) {
    super(frame, title, modal);
    dbConn = db;
    this.dup = dup;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public SingleMember(String title, DBConnection dbConn, boolean modal, Duplicate dup) {
    this(CircApplet.parent, title, modal,dbConn, dup);
  }

  public SingleMember() {
    this(CircApplet.parent, "", false,null, null);
  }

  void jbInit() throws Exception {
    borderLayout1.setHeight(480);
    borderLayout1.setWidth(552);
    titledBorder1 = new TitledBorder("");
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane2.setBorder(titledBorder1);
    jScrollPane2.setOpaque(true);
    jScrollPane2.setDoubleBuffered(true);
    
    INIFile iniFile = new INIFile(
            FileUtils.getClassDir(NewMember.class) + "/config.ini"); 
    jmbg = iniFile.getString("circ", "jmbg");

    dsCategs = dbConn.getCategs(); // new DataSet(dbConn.getConnection(),"USER_CATEGS",new String[]{"ID"});
    dsEduc = dbConn.getEduLvl(); // new DataSet(dbConn.getConnection(),"EDU_LVL",new String[]{"ID"});
    dsMmbrType = dbConn.getMmbrType(); // new DataSet(dbConn.getConnection(),"MMBR_TYPES",new String[]{"ID"});
    dsSingle = dbConn.getSingleModel();
    dsGroup = dbConn.getGroupModel();
    dsUsers = dbConn.getUsersModel();
    dsLanguage = dbConn.getLanguage();
    dsBranch = dbConn.getBranch();
    dsSigning = dbConn.getSigning();
    dsLending = dbConn.getLending();

    cmCategs = new JDBLookupModel(dsCategs,"NAME",dsSingle,"USER_CTGR","ID");
    cmEduc = new JDBLookupModel(dsEduc,"NAME",dsSingle,"EDU_LVL","ID");
    cmMmbrType = new JDBLookupModel(dsMmbrType,"NAME",dsSingle,"MMBR_TYPE","ID");
//    cmGroup = new JDBLookupModel(dsGroup,"INST_NAME",dsSingle,"GRP_ID","USER_ID");
    cmLanguage = new JDBLookupModel(dsLanguage,"NAME", dsSingle,"LANGUAGE","ID");
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    mld = new MasterLinkDescriptor(dsUsers,new String[] {"ID"},new String[] {"USER_ID"});
    mld.setCascadeInserts(true);
    mld.setCascadeDeletes(true);
    mld.setCascadeUpdates(true);
    mld.setCascadeSaves(true);
    cboCategs = new JDBComboBox(cmCategs);
    cboEduc = new JDBComboBox(cmEduc);
    cboMmbrType = new JDBComboBox(cmMmbrType);
//    cboGroup = new JDBComboBox(cmGroup);
    cboLanguage = new JDBComboBox(cmLanguage);
    cboBranch = new JDBComboBox(cmBranch);

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed){
//          dsSingle.setMasterLink(mld);
//          dsUsers.setFilter("USER_TYPE = 1");
          dsUsers.first();
        }
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        dsSingle.removeMasterLink();  // TANJA - 31.10.00.
        dsUsers.removeFilter(); // dodato 24.02.00.
        closed = true;
        setVisible(false);  //01.11.00. stavljeno umesto dispose();
      }
    });

    tfSingleMmbrID.setForeground(Color.blue);
    tfSingleMmbrID.setDataSet(dsUsers);
    tfSingleMmbrID.setColumn("ID");

    tfSingleDate.setForeground(Color.blue);
    tfSingleDate.setDataSet(dsUsers);
    tfSingleDate.setColumn("SIGN_DATE");

    tfSingleAddress.setDataSet(dsUsers);
    tfSingleAddress.setColumn("ADDRESS");

    tfSinglePlace.setDataSet(dsUsers);
    tfSinglePlace.setColumn("CITY");

    tfSingleZIP.setDataSet(dsUsers);
    tfSingleZIP.setColumn("ZIPCODE");

    tfSinglePhone.setDataSet(dsUsers);
    tfSinglePhone.setColumn("PHONE");

    tfSingleEmail.setDataSet(dsUsers);
    tfSingleEmail.setColumn("EMAIL");

    tfSingleLastName.setDataSet(dsSingle);
    tfSingleLastName.setColumn("LAST_NAME");

    tfSingleFirstName.setDataSet(dsSingle);
    tfSingleFirstName.setColumn("FIRST_NAME");

    tfSingleAscName.setDataSet(dsSingle);
    tfSingleAscName.setColumn("PARENT_NAME");
    lSingleAscName.setText(Messages.get("SINGLEMEMBER_LSINGLEASCNAME_TEXT"));
    
    zipplace = new ZipPlaceDlg();
    zipplace.setLocationRelativeTo(null);
    zipplace.setResizable(false);
    
    al = new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			tfSinglePlace.setText(zipplace.getPlace());
			tfSingleZIP.setText(zipplace.getZip());
			zipplace.setVisible(false);
		}
	};
	zipplace.addListener(al);
	
	
    tfSinglePlace.addKeyListener(new KeyAdapter(){
	      public void keyReleased(KeyEvent e){
		        if(e.getKeyCode()==KeyEvent.VK_F12){
		        	zipplace.setVisible(true);
		        }
	      }
    });
  
    tfSingleZIP.addKeyListener(new KeyAdapter(){
	      public void keyReleased(KeyEvent e){
		        if(e.getKeyCode()==KeyEvent.VK_F12){
		        	zipplace.setVisible(true);
		        }
	      }
    });
    
           
    gboxSingleDocument.setLayout(xYLayout18);
    gboxSingleDocument.setBorder(BorderFactory.createTitledBorder(Messages.get("SINGLEMEMBER_GBOXSINGLEDOCUMENT_TITLE")));
    rdbDocI.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpDocNo.getSelection().equals(rdbDocI) || rgpDocNo.getSelection().equals(rdbDocL))
          setJMBGEnabled(false);
        else
          setJMBGEnabled(true);
      }
    });

    rdbDocI.setText(Messages.get("SINGLEMEMBER_RDBDOCI_TEXT"));

    lSingleIndex.setText(Messages.get("SINGLEMEMBER_LSINGLEINDEX_TEXT"));
    tfSingleIndex.setDataSet(dsSingle);
    tfSingleIndex.setColumn("INDEX_NO");
    lSingleJMBG.setText(Messages.get("SINGLEMEMBER_LSINGLEJMBG_TEXT"));
    tfSingleJMBG.setDataSet(dsSingle);
    tfSingleJMBG.setColumn("JMBG");
    lSingleBranchID.setText(Messages.get("SINGLEMEMBER_LSINGLEBRANCHID_TEXT"));
    
    lSingleDocPlace.setText(Messages.get("SINGLEMEMBER_LSINGLEDOCPLACE_TEXT"));
    lLanguage.setText(Messages.get("SINGLEMEMBER_LLANGUAGE_TEXT"));
    
    tfSingleDocPlace.setDataSet(dsSingle);
    tfSingleDocPlace.setColumn("DOC_CITY");
    rdbDocL.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpDocNo.getSelection().equals(rdbDocL) || rgpDocNo.getSelection().equals(rdbDocI))
          setJMBGEnabled(false);
        else
          setJMBGEnabled(true);
      }
    });

    rdbDocL.setSelected(true);
    rdbDocL.setText(Messages.get("SINGLEMEMBER_RDBDOCL_TEXT"));
    lSingleDocNo.setText(Messages.get("SINGLEMEMBER_LSINGLEDOCNO_TEXT"));
    tfSingleDocNo.setDataSet(dsSingle);
    tfSingleDocNo.setColumn("DOC_NO");
    rdbDocP.setText(Messages.get("SINGLEMEMBER_RDBDOCP_TEXT"));

    rdbDocL.setDataSet(dsSingle);
    rdbDocI.setDataSet(dsSingle);
    rdbDocP.setDataSet(dsSingle);
    rdbDocL.setColumn("DOC_ID");
    rdbDocI.setColumn("DOC_ID");
    rdbDocP.setColumn("DOC_ID");

    rgpDocNo.add(rdbDocL);
    rgpDocNo.add(rdbDocI);
    rgpDocNo.add(rdbDocP);
    rdbDocP.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpDocNo.getSelection().equals(rdbDocP))
          setJMBGEnabled(true);
        else
          setJMBGEnabled(false);
      }
    });

    tfSingleOccup.setDataSet(dsSingle);
    tfSingleOccup.setColumn("OCCUPATION");

    tfSingleZvanje.setDataSet(dsSingle);
    tfSingleZvanje.setColumn("TITLE");

    tfSingleOrgName.setDataSet(dsSingle);
    tfSingleOrgName.setColumn("ORGANIZATION");

    tfSingleOrgPlace.setDataSet(dsSingle);
    tfSingleOrgPlace.setColumn("ORG_CITY");

    tfSingleOrgAddress.setDataSet(dsSingle);
    tfSingleOrgAddress.setColumn("ORG_ADDRESS");

    tfSingleOrgPhone.setDataSet(dsSingle);
    tfSingleOrgPhone.setColumn("ORG_PHONE");

    tfSingleOrgZIP.setDataSet(dsSingle);
    tfSingleOrgZIP.setColumn("ORG_ZIP");

    tfSingleResideZIP.setDataSet(dsSingle);
    tfSingleResideZIP.setColumn("TEMP_ZIP");

    tfSingleResideAddress.setDataSet(dsSingle);
    tfSingleResideAddress.setColumn("TEMP_ADDR");

    tfSingleResidePlace.setDataSet(dsSingle);
    tfSingleResidePlace.setColumn("TEMP_CITY");

    tfSingleResidePhone.setDataSet(dsSingle);
    tfSingleResidePhone.setColumn("TEMP_PHONE");
    
    tfSingleGrpID.setDataSet(dsSingle);
    tfSingleGrpID.setColumn("GRP_ID");

    btnPrint.setText(Messages.get("SINGLEMEMBER_BTNPRINT_TEXT"));
    btnPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrint_actionPerformed(e);
      }
    });

    btnSave.setText(Messages.get("SINGLEMEMBER_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });

    btnDelete.setText(Messages.get("SINGLEMEMBER_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });

    btnExit.setText(Messages.get("SINGLEMEMBER_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    
    btnBlock.setText(Messages.get("SINGLEMEMBER_BTNBLOCK1_TEXT"));
    btnBlock.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnBlock_actionPerformed(e);
      }
    });
    
    btnDup.setText(Messages.get("SINGLEMEMBER_DUPLICATE_TEXT"));
    btnDup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDup_actionPerformed(e);
      }
    });

    btnNext.setText(">>");
    btnNext.setToolTipText(Messages.get("SINGLEMEMBER_BTNNEXT_TOOLTIP"));
    btnNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNext_actionPerformed(e);
      }
    });

    btnPrev.setText("<<");
    btnPrev.setToolTipText(Messages.get("SINGLEMEMBER_BTNPREV_TOOLTIP"));
    btnPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrev_actionPerformed(e);
      }
    });
    btnFirst.setText("|<");
    btnFirst.setToolTipText(Messages.get("SINGLEMEMBER_BTNFIRST_TOOLTIP"));
    btnFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFirst_actionPerformed(e);
      }
    });
    btnLast.setText(">|");
    btnLast.setToolTipText(Messages.get("SINGLEMEMBER_BTNLAST_TOOLTIP"));
    btnLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnLast_actionPerformed(e);
      }
    });

    panel1.setLayout(borderLayout1);
    pSingle1.setMinimumSize(new Dimension(600, 500));
    pSingle1.setPreferredSize(new Dimension(600, 500));
    pSingle2.setMinimumSize(new Dimension(600, 500));
    pSingle2.setPreferredSize(new Dimension(600, 500));
    pSingle3.setMinimumSize(new Dimension(600, 500));
    pSingle3.setPreferredSize(new Dimension(600, 500));
    lSingleZvanje.setMaximumSize(new Dimension(59, 17));
    lSingleZvanje.setMinimumSize(new Dimension(59, 17));
    lSingleZvanje.setPreferredSize(new Dimension(59, 17));
    getContentPane().add(panel1);

    panel1.setMinimumSize(new Dimension(600, 500));
    panel1.setPreferredSize(new Dimension(600, 500));
    tabsSingle.setMinimumSize(new Dimension(600, 500));
    tabsSingle.setPreferredSize(new Dimension(600, 500));

    lWarning.setText(Messages.get("SINGLEMEMBER_LWARN_TEXT"));
    lWarning.setEnabled(false);
    lWarning.setForeground(Color.red);
    lWarningDesc.setForeground(Color.red);
    lSingleDup.setForeground(Color.blue);
//    lSingleDupDate.setForeground(Color.blue);

    lSingleAddress.setText(Messages.get("SINGLEMEMBER_LSINGLEADDRESS_TEXT"));
    lSinglePlace.setText(Messages.get("SINGLEMEMBER_LSINGLEPLACE_TEXT"));
    lSingleZIP.setText(Messages.get("SINGLEMEMBER_LSINGLEZIP_TEXT"));
    lSingleEmail.setText(Messages.get("SINGLEMEMBER_LSINGLEEMAIL_TEXT"));

    lSingleMmbrID.setText(Messages.get("SINGLEMEMBER_LSINGLEMMBRID_TEXT"));
    lSingleDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDATE_TEXT"));
    chkSingleWarn.setText(Messages.get("SINGLEMEMBER_CHKSINGLEWARN_TEXT"));
    chkSingleWarn.setDataSet(dsUsers);
    chkSingleWarn.setColumn("WARNING");
    lSingleLastName.setText(Messages.get("SINGLEMEMBER_LSINGLELASTNAME_TEXT"));
    lSingleFirstName.setText(Messages.get("SINGLEMEMBER_LSINGLEFIRSTNAME_TEXT"));
    lSingleOccup.setText(Messages.get("SINGLEMEMBER_LSINGLEOCCUP_TEXT"));
    lSingleZvanje.setText(Messages.get("SINGLEMEMBER_LSINGLEZVANJE_TEXT"));
    lSinglePhone.setText(Messages.get("SINGLEMEMBER_LSINGLEPHONE_TEXT"));
    lOrganization.setText(Messages.get("SINGLEMEMBER_LORGANISATION_TEXT"));
    lOrgAddress.setText(Messages.get("SINGLEMEMBER_LORGADDRESS_TEXT"));
    lOrgZIP.setText(Messages.get("SINGLEMEMBER_LORGZIP_TEXT"));
    lOrgPhone.setText(Messages.get("SINGLEMEMBER_LORGPHONE_TEXT"));
    lResideAddress.setText(Messages.get("SINGLEMEMBER_LRESIDEADDRESS_TEXT"));
    lResidePlace.setText(Messages.get("SINGLEMEMBER_LRESIDEPLACE_TEXT"));
    lResideZIP.setText(Messages.get("SINGLEMEMBER_LRESIDEZIP_TEXT"));
    lResidePhone.setText(Messages.get("SINGLEMEMBER_LRESIDEPHONE_TEXT"));
    lOrgPlace.setText(Messages.get("SINGLEMEMBER_LORGPLACE_TEXT"));
    gboxox1.setLayout(xYLayout12);
    gboxox1.setBorder(BorderFactory.createTitledBorder(Messages.get("SINGLEMEMBER_GBOXOX1_TITLE")));
    gboxox2.setLayout(xYLayout13);
    gboxox2.setBorder(BorderFactory.createTitledBorder(Messages.get("SINGLEMEMBER_GBOXOX2_TITLE")));

    gboxPol.setLayout(xYLayout17);
    gboxPol.setBorder(BorderFactory.createTitledBorder(Messages.get("SINGLEMEMBER_GBOXPOL_TITLE")));
    gboxAge.setLayout(xYLayout20);
    gboxAge.setBorder(BorderFactory.createTitledBorder(Messages.get("SINGLEMEMBER_GBOXAGE_TITLE")));

    rdbGrpNo1.setText(Messages.get("SINGLEMEMBER_RDBGRPNO1_TEXT"));
    rdbGrpNo1.setSelected(true);
    rdbGrpYes1.setText(Messages.get("SINGLEMEMBER_RDBGRPYES1_TEXT"));
    rdbGrpYes1.setDataSet(dsSingle);
    rdbGrpNo1.setDataSet(dsSingle);
    rdbGrpYes1.setColumn("GENDER");
    rdbGrpNo1.setColumn("GENDER");
    rgpPol.add(rdbGrpNo1);
    rgpPol.add(rdbGrpYes1);
    
    rdbAgeA.setText(Messages.get("SINGLEMEMBER_RDBAGEA_TEXT"));
    rdbAgeC.setText(Messages.get("SINGLEMEMBER_RDBAGEC_TEXT"));
    rdbAgeA.setDataSet(dsSingle);
    rdbAgeC.setDataSet(dsSingle);
    rdbAgeA.setColumn("AGE");
    rdbAgeC.setColumn("AGE");
    rdbAgeA.setSelected(true);
    rgpAge.add(rdbAgeA);
    rgpAge.add(rdbAgeC);

    lSingleCategs.setText(Messages.get("SINGLEMEMBER_LSINGLECATEGS_TEXT"));
    lSingleEduc.setText(Messages.get("SINGLEMEMBER_LSINGLEDUC_TEXT"));
    lSingleMmbrType.setText(Messages.get("SINGLEMEMBER_LSINGLEMMBRTYPE_TEXT"));
    lSingleGrp.setText(Messages.get("SINGLEMEMBER_LSINGLEGRP_TEXT"));
    gboxSingleGrp.setLayout(xYLayout15);
    gboxSingleGrp.setBorder(BorderFactory.createTitledBorder(Messages.get("SINGLEMEMBER_GBOXSINGLEGRP_TEXT")));
    rdbGrpYes.setText(Messages.get("SINGLEMEMBER_RDBGRP_YES"));
    rdbGrpNo.setText(Messages.get("SINGLEMEMBER_RDBGRP_NO"));
    lSingleMmbrship.setText(Messages.get("SINGLEMEMBER_LSINGLEMMBRSHIP_TEXT"));
    lSingleUntil.setText(Messages.get("SINGLEMEMBER_LSINGLEUNTIL_TEXT"));

    lSingleNapomena.setText(Messages.get("SINGLEMEMBER_LSINGLENAPOMENA_TEXT"));
    jScrollPane2.getViewport().setBackground(Color.white);
    jScrollPane2.getViewport().add(tSingleNote,null);
    jScrollPane2.getViewport().setView(tSingleNote);
    tSingleNote.setDataSet(dsSingle);
    tSingleNote.setColumn("NOTE");

    lSingleUDK.setText(Messages.get("SINGLEMEMBER_LSINGLEUDK_TEXT"));
    tfInteresovanja.setDataSet(dsSingle);
    tfInteresovanja.setColumn("INTERESTS");

    rdbGrpYes.setText(Messages.get("SINGLEMEMBER_RDBGRPYES_TEXT"));
    rdbGrpNo.setText(Messages.get("SINGLEMEMBER_RDBGRPNO_TEXT"));
    rgpGroup.add(rdbGrpYes);
    rgpGroup.add(rdbGrpNo);

    cboCategs.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        setMemberFee();
      }
    });

    cboMmbrType.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        setMemberFee();
      }
    });
    
    rdbGrpYes.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpGroup.getSelection().equals(rdbGrpYes))
          setGroupEnabled(true);
        else{
          setGroupEnabled(false);
        }
      }
    });

    rdbGrpNo.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpGroup.getSelection().equals(rdbGrpNo))
          setGroupEnabled(false);
        else{
          setGroupEnabled(true);
        }
      }
    });
    pSingle1.setLayout(xYLayout1);
    pSingle2.setLayout(xYLayout2);
    pSingle3.setLayout(xYLayout3);

    panel1.add(tabsSingle, new XYConstraints(110, 5, 441, 490));
    panel1.add(btnSave, new XYConstraints(14, 233, 80, 25));
//    panel1.add(btnDelete, new XYConstraints(14, 311, 80, 25));
    panel1.add(btnExit, new XYConstraints(14, 272, 80, 25));
//    panel1.add(btnNext, new XYConstraints(56, 62, 50, 25));
//    panel1.add(btnPrev, new XYConstraints(5, 62, 50, 25));
//    panel1.add(btnFirst, new XYConstraints(5, 27, 50, 25));
//    panel1.add(btnLast, new XYConstraints(56, 27, 50, 25));


    tabsSingle.addTab(Messages.get("SINGLEMEMBER_TABSSINGLE_TAB601"), pSingle1);
    pSingle1.add(lSingleMmbrID, new XYConstraints(22, 33, -1, -1));
    pSingle1.add(lSingleDate, new XYConstraints(22, 78, -1, -1));
    pSingle1.add(tfSingleMmbrID, new XYConstraints(158, 33, 120, -1));
    pSingle1.add(tfSingleDate, new XYConstraints(158, 78, 120, -1));
    pSingle1.add(chkSingleWarn, new XYConstraints(170, 165, -1, -1));
    pSingle1.add(lSingleBranchID,    new XYConstraints(22, 125, -1, -1));
    pSingle1.add(cboBranch,    new XYConstraints(158, 125, 220, -1));

    pSingle1.add(lWarning, new XYConstraints(150, 385, -1, -1));
    pSingle1.add(lWarningDesc, new XYConstraints(150, 410, -1, -1));
    pSingle1.add(btnBlock, new XYConstraints(25, 400, 80, 25));
    pSingle1.add(btnDup, new XYConstraints(25, 220, 90, 25));
    pSingle1.add(lSingleDup, new XYConstraints(21, 270, -1, -1));
//    pSingle1.add(lSingleDupDate, new XYConstraints(21, 300, -1, -1));
    tabsSingle.addTab(Messages.get("SINGLEMEMBER_TABSSINGLE_TAB610"), pSingle2);
    tfSingleLastName.setNextFocusableComponent(tfSingleFirstName);
    tfSingleFirstName.setNextFocusableComponent(tfSingleAscName);
    tfSingleOccup.setNextFocusableComponent(tfSingleZvanje);
    tfSingleAddress.setNextFocusableComponent(tfSinglePlace);
    tfSinglePlace.setNextFocusableComponent(tfSingleZIP);
    tfSingleZIP.setNextFocusableComponent(tfSinglePhone);
    tfSinglePhone.setNextFocusableComponent(tfSingleEmail);

    pSingle2.add(lSingleLastName, new XYConstraints(10, 169, 69, -1));
    pSingle2.add(tfSingleLastName, new XYConstraints(90, 167, 150, -1));
    pSingle2.add(tfSingleFirstName, new XYConstraints(90, 192, 150, -1));
    pSingle2.add(lSingleFirstName, new XYConstraints(11, 194, 69, -1));
    pSingle2.add(tfSingleAscName, new XYConstraints(90, 220, 151, -1));
    pSingle2.add(lSingleAscName, new XYConstraints(12, 222, 79, -1));
    pSingle2.add(lSingleCategs, new XYConstraints(9, 8, -1, -1));
    pSingle2.add(cboCategs, new XYConstraints(142, 6, 150, -1));
    pSingle2.add(lSingleMmbrType, new XYConstraints(10, 36, -1, -1));
    pSingle2.add(cboMmbrType, new XYConstraints(143, 34, 150, -1));
    pSingle2.add(gboxSingleGrp, new XYConstraints(6, 57, 400, 101));
    gboxSingleGrp.add(rdbGrpYes, new XYConstraints(12, 0, -1, -1));
    gboxSingleGrp.add(rdbGrpNo, new XYConstraints(168, 0, -1, -1));
    gboxSingleGrp.add(lSingleGrp, new XYConstraints(13, 24, -1, -1));
//    gboxSingleGrp.add(cboGroup, new XYConstraints(14, 47, 134, -1));
    gboxSingleGrp.add(tfSingleGrpID, new XYConstraints(14, 47, 120, -1));

    gboxSingleGrp.add(tfSingleMmbrship, new XYConstraints(254, 24, 70, -1));
    gboxSingleGrp.add(lSingleMmbrship, new XYConstraints(172, 24, -1, -1));
    gboxSingleGrp.add(tfSingleUntil, new XYConstraints(255, 51, 70, -1));
    gboxSingleGrp.add(lSingleUntil, new XYConstraints(177, 51, -1, -1));

    pSingle2.add(gboxPol, new XYConstraints(250, 162, 75, 80));
    gboxPol.add(rdbGrpYes1, new XYConstraints(0, 0, -1, -1));
    gboxPol.add(rdbGrpNo1, new XYConstraints(0, 28, -1, -1));
    pSingle2.add(gboxAge, new XYConstraints(330, 162, 75, 80));
    gboxAge.add(rdbAgeA, new XYConstraints(0, 0, -1, -1));
    gboxAge.add(rdbAgeC, new XYConstraints(0, 28, -1, -1));

    pSingle2.add(gboxSingleDocument, new XYConstraints(8, 247, 400, 105));
    gboxSingleDocument.add(rdbDocL, new XYConstraints(10, 0, -1, -1));


    gboxSingleDocument.add(lSingleDocNo, new XYConstraints(14, 31, -1, -1));
    gboxSingleDocument.add(rdbDocP, new XYConstraints(131, 0, -1, -1));
    gboxSingleDocument.add(rdbDocI, new XYConstraints(264, 0, -1, -1));
    gboxSingleDocument.add(tfSingleJMBG, new XYConstraints(262, 52, 121, -1));
    gboxSingleDocument.add(tfSingleIndex, new XYConstraints(301, 25, 82, -1));
    gboxSingleDocument.add(lSingleIndex, new XYConstraints(223, 29, -1, -1));
    gboxSingleDocument.add(tfSingleDocNo, new XYConstraints(113, 29, 89, -1));
    gboxSingleDocument.add(tfSingleDocPlace, new XYConstraints(113, 57, 88, -1));
    gboxSingleDocument.add(lSingleDocPlace, new XYConstraints(14, 57, -1, -1));
    gboxSingleDocument.add(lSingleJMBG, new XYConstraints(223, 52, -1, -1));

    pSingle2.add(tfSingleAddress, new XYConstraints(76, 358, 120, -1));
    pSingle2.add(lSingleAddress, new XYConstraints(11, 360, -1, -1));
    pSingle2.add(tfSingleZIP, new XYConstraints(77, 387, 120, -1));
    pSingle2.add(lSingleZIP, new XYConstraints(11, 389, 31, -1));
    pSingle2.add(tfSinglePlace, new XYConstraints(77, 416, 120, -1));
    pSingle2.add(lSinglePlace, new XYConstraints(11, 419, 49, -1));
    pSingle2.add(tfSinglePhone, new XYConstraints(287, 371, 120, -1));
    pSingle2.add(lSinglePhone, new XYConstraints(224, 374, -1, -1));
    pSingle2.add(tfSingleEmail, new XYConstraints(287, 407, 120, -1));
    pSingle2.add(lSingleEmail, new XYConstraints(233, 409, -1, -1));

    tabsSingle.addTab(Messages.get("SINGLEMEMBER_TABSSINGLE_TAB670"), pSingle3);
    pSingle3.add(gboxox2, new XYConstraints(10, 115, 400, 121));
    gboxox2.add(lOrgZIP, new XYConstraints(255, 43, 28, -1));
    gboxox2.add(tfSingleOrgZIP, new XYConstraints(293, 41, 70, -1));
    gboxox2.add(lOrgPhone, new XYConstraints(237, 13, 45, -1));
    gboxox2.add(tfSingleOrgPhone, new XYConstraints(293, 11, 70, -1));
    gboxox2.add(tfSingleOrgName, new XYConstraints(62, 6, 150, -1));
    gboxox2.add(lOrganization, new XYConstraints(4, 8, 55, -1));
    gboxox2.add(tfSingleOrgAddress, new XYConstraints(63, 36, 150, -1));
    gboxox2.add(lOrgAddress, new XYConstraints(5, 38, 46, -1));
    gboxox2.add(tfSingleOrgPlace, new XYConstraints(62, 68, 150, -1));
    gboxox2.add(lOrgPlace, new XYConstraints(4, 63, 40, 29));

    pSingle3.add(cboEduc, new XYConstraints(147, 6, 150, -1));
    pSingle3.add(lSingleEduc, new XYConstraints(14, 8, -1, -1));
    pSingle3.add(gboxox1, new XYConstraints(10, 235, 400, 138));
    gboxox1.add(tfSingleResideAddress, new XYConstraints(140, 0, 150, -1));
    gboxox1.add(lResideAddress, new XYConstraints(75, 0, 48, 20));
    gboxox1.add(tfSingleResidePlace, new XYConstraints(140, 29, 150, -1));
    gboxox1.add(lResidePlace, new XYConstraints(75, 28, 43, 21));
    gboxox1.add(tfSingleResideZIP, new XYConstraints(141, 57, 122, -1));
    gboxox1.add(lResideZIP, new XYConstraints(76, 58, 29, -1));
    gboxox1.add(tfSingleResidePhone, new XYConstraints(142, 85, 120, -1));
    gboxox1.add(lResidePhone, new XYConstraints(77, 88, 49, 16));
    pSingle3.add(btnPrint, new XYConstraints(338, 435, 80, 26));
    pSingle3.add(tfSingleOccup, new XYConstraints(147, 37, 150, -1));
    pSingle3.add(lSingleOccup, new XYConstraints(13, 39, 65, -1));
    pSingle3.add(lSingleZvanje, new XYConstraints(14, 62, 65, -1));
    pSingle3.add(tfSingleZvanje, new XYConstraints(147, 62, 150, -1));
    pSingle3.add(lLanguage, new XYConstraints(14, 90, -1, -1));
    pSingle3.add(cboLanguage, new XYConstraints(147, 88, 150, -1));
    pSingle3.add(lSingleUDK, new XYConstraints(15, 430, 81, 22));
    pSingle3.add(jScrollPane2, new XYConstraints(100, 380, 230, 50));
    pSingle3.add(tfInteresovanja, new XYConstraints(100, 435, 161, 25));
    pSingle3.add(lSingleNapomena, new XYConstraints(13, 385, 85, -1));

    rdbGrpNo.setSelected(true);
    setGroupEnabled(true);
    dsUsers.first();
  }


// Last
  void btnLast_actionPerformed(ActionEvent e) {
    dsUsers.last();
    String s = dsSingle.getRow().getData("GRP_ID");
    int status = (int)Double.parseDouble(s);
    if(status == 0) {
      rdbGrpNo.setSelected(true);
      setGroupEnabled(true);
    }
    else {
      rdbGrpYes.setSelected(true);
      setGroupEnabled(false);
    }
    setWarning();
    setMemberFee(); // 18.07.2000. dodat poziv funkcije
  }

// First
  void btnFirst_actionPerformed(ActionEvent e) {
    dsUsers.first();
    String s = dsSingle.getRow().getData("GRP_ID");
    int status = (int)Double.parseDouble(s);
    if(status == 0) {
      rdbGrpNo.setSelected(true);
      setGroupEnabled(true);
    }
    else {
      rdbGrpYes.setSelected(true);
      setGroupEnabled(false);
    }
    setWarning();
    setMemberFee(); // 18.07.2000. dodat poziv funkcije
  }

// Prev
  void btnPrev_actionPerformed(ActionEvent e) {
    dsUsers.prev();
    String s = dsSingle.getRow().getData("GRP_ID");
    int status = (int)Double.parseDouble(s);
    if(status  == 0) {
      rdbGrpNo.setSelected(true);
      setGroupEnabled(true);
    }
    else {
      rdbGrpYes.setSelected(true);
      setGroupEnabled(false);
    }
    setWarning();
    setMemberFee(); // 18.07.2000. dodat poziv funkcije
  }

// Next
  void btnNext_actionPerformed(ActionEvent e) {
    dsUsers.next();
    String s = dsSingle.getRow().getData("GRP_ID");
    int status = (int)Double.parseDouble(s);
    if(status == 0) {
      rdbGrpNo.setSelected(true);
      setGroupEnabled(true);
    }
    else {
      rdbGrpYes.setSelected(true);
      setGroupEnabled(false);
    }
    setWarning();
    setMemberFee();
  }

// Exit
  void btnExit_actionPerformed(ActionEvent e) {
    dsUsers.removeFilter();    // dodato 21.02.00.
    dsSingle.removeMasterLink();  // TANJA - 31.10.00.
    setVisible(false);  //01.11.00. stavljeno umesto dispose();
  }

// Delete
  void btnDelete_actionPerformed(ActionEvent e) {
  	String[] options = {Messages.get("LENDING_OPTIONSPANE_MSG22"),Messages.get("LENDING_OPTIONSPANE_MSG23")}; 
 	int op = JOptionPane.showOptionDialog(this,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG9"), Messages.get("SINGLEMEMBER_OPTIONPANE_MSG10"),JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
 	if (op == JOptionPane.YES_OPTION){
	  	dsLending.deleteRow();
	  	dsSigning.deleteRow();
	    dsUsers.deleteRow();
	
	    String s = dsSingle.getRow().getData("GRP_ID");
	    int status = (int)Double.parseDouble(s);
	    if(status == 0) {
	      rdbGrpNo.setSelected(true);
	      setGroupEnabled(true);
	    }
	    else {
	      rdbGrpYes.setSelected(true);
	      setGroupEnabled(false);
	    }
	    setWarning();
	    setMemberFee(); // 18.07.2000. dodat poziv funkcije
	    JOptionPane.showMessageDialog(null,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG1"),Messages.get("SINGLEMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
  	}
  }

// Save
  void btnSave_actionPerformed(ActionEvent e) {
    if(canWrite() && !(checkLength()) && !(checkTypes())){
      if(rdbGrpNo.isSelected()){
        dsSingle.getRow().setData("0","GRP_ID");
      }else if(rdbGrpYes.isSelected()){
    	dsSingle.getRow().setData(DBConnection.toDBMember(tfSingleGrpID.getText()),"GRP_ID");
      }
/*      else if(rdbGrpYes.isSelected()){
        cmGroup.post();
      }*/
      cmCategs.setData();
      cmEduc.setData();
      cmMmbrType.setData();
      cmLanguage.setData();
      cmBranch.setData();
      dsUsers.saveRow();
      if (dsUsers.hasError() && dsSingle.hasError() && dsGroup.hasError()) {
        JOptionPane.showMessageDialog(null,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG1"),Messages.get("SINGLEMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
      }
      setWarning();
    }
  }

  void btnPrint_actionPerformed(ActionEvent e) {
	  Map params = new HashMap(3);
      params.put("datum", tfSingleDate.getText());
      params.put("ime", tfSingleLastName.getText() +" "+ tfSingleFirstName.getText());
      params.put("broj", tfSingleMmbrID.getText());
      if (dup_no != 0)
      	params.put("duplikat", "Duplikat "+String.valueOf(dup_no));
      if (dup_date != null)
      	params.put("duplikatdatum", DBConnection.fromDatabaseDate(dup_date.toString()));
      try{
	      JasperPrint jp = JasperFillManager.fillReport(
	              NewMember.class.getResource(
	                "/com/gint/app/bisis/circ/reports/singlemember.jasper").openStream(), 
	                params, dbConn.getConnection());
	      JasperViewer.viewReport(jp, false);
      }catch (Exception e1){
    	  e1.printStackTrace();
      }
  }
  
  void btnDup_actionPerformed(ActionEvent e) {
	  dup.setVisible(true, tfSingleMmbrID.getText(), this);
  }
  
  void btnBlock_actionPerformed(ActionEvent e) {
	    if(blocked) {
	    	try{
	      		Statement stmt = dbConn.getConnection().createStatement();
	      		stmt.executeUpdate("delete from blocked_cards where user_id ='"+tfSingleMmbrID.getText()+"' and note is not null");
	      		dbConn.getConnection().commit();
	      		stmt.close();
	      		btnBlock.setText(Messages.get("SINGLEMEMBER_BTNBLOCK1_TEXT"));
	      		blocked = false;
	      		setWarning();
	    	}catch(SQLException e1){
	      	}
	    }else{
	    	breason = new BlockReason(this,true);
	    	breason.setLocationRelativeTo(null);
	    	KeyAdapter ka = new KeyAdapter(){
	    		public void keyReleased(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						if (!breason.getValue().equals("")){
							try{
					      		Statement stmt = dbConn.getConnection().createStatement();
					      		stmt.executeUpdate("insert into blocked_cards (user_id, reason_id, note) values ('"+tfSingleMmbrID.getText()+"',3,'"+breason.getValue()+"')");
					      		dbConn.getConnection().commit();
					      		stmt.close();
					      		btnBlock.setText(Messages.get("SINGLEMEMBER_BTNBLOCK2_TEXT"));
					      		breason.dispose();
					      		blocked = true;
					      		setWarning();
							}catch(SQLException e1){
					      	}
							
						}
					}
				}
	    	};
	    	breason.addListener(ka);
	    	breason.setVisible(true);
	    }
  }

  void setGroupEnabled(boolean enb){
//    lSingleMmbrship.setEnabled(enb);
//    tfSingleMmbrship.setEnabled(enb);
//    lSingleUntil.setEnabled(enb);
//    tfSingleUntil.setEnabled(enb);
    lSingleGrp.setEnabled(!enb);
//    cboGroup.setEnabled(!enb);
    tfSingleGrpID.setEnabled(!enb);
  }

  void setJMBGEnabled(boolean enb){
    if (!enb)
      tfSingleJMBG.setText("");
    tfSingleJMBG.setEnabled(enb);
    lSingleJMBG.setEnabled(enb);
  }


  public void setVisible(boolean vis){
    closed = true;
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
    closed = false;
//    cmGroup = new JDBLookupModel(dsGroup,"INST_NAME",dsSingle,"GRP_ID","USER_ID"); // dodato 28.02.00.
//    cboGroup.setComboModel(cmGroup); // dodato 28.02.00.

    dsSingle.setMasterLink(mld);
    dsUsers.setFilter(" USER_TYPE = 1"); // dodato 21.02.00.
    dsUsers.getMin(); // dodato 24.02.00.
    dsUsers.getMax(); // dodato 24.02.00.
    dsUsers.setCurrentRow(new String[]{"ID"}, new String[]{id});
    dsSigning.setCurrentRow(new String[]{"SINGLE_ID"}, new String[]{id});
    dsLending.setCurrentRow(new String[]{"SINGLE_ID"}, new String[]{id});
    


    String s = dsSingle.getRow().getData("GRP_ID");
    if ((!s.equals("")) && (s != null)) {
      int status = (int)Double.parseDouble(s);
      if(status == 0) {
        rdbGrpNo.setSelected(true);
        setGroupEnabled(true);
      }
      else {
        rdbGrpYes.setSelected(true);
        setGroupEnabled(false);
        tfSingleGrpID.setText(DBConnection.fromDBMember(tfSingleGrpID.getText()));
      }
    }
    setDup();
    setWarning();
    setMemberFee(); // 18.07.2000. dodat poziv funkcije
    super.setVisible(vis);
  }

  public void setMemberFee(){  // 18.07.2000. dodato da upunjava i datum do kog vazi clanarina
    usCtgr = cmCategs.getData();
    mbType = cmMmbrType.getData();

    String datumSAP=DBConnection.toDatabaseDate(tfSingleDate.getText().trim());

    String qry = "select cost from signing where single_id ='" + tfSingleMmbrID.getText() + "' order by until_date desc";
    try{
      Statement stmt = dbConn.getConnection().createStatement();    
      ResultSet rset = stmt.executeQuery(qry);
      if(rset.next() && rset.getObject(1)!=null){
        tfSingleMmbrship.setText(rset.getObject(1).toString());
        tfSingleMmbrship.setEnabled(false);
      }
      else{
        tfSingleMmbrship.setText(Messages.get("SINGLEMEMBER_TFSINGLEMMBRSHIP_TEXT1"));
        tfSingleMmbrship.setEnabled(false);
      }
      rset.close();
      
      qry = "select until_date from signing where single_id ='" + tfSingleMmbrID.getText() + "' order by until_date desc";
      rset = stmt.executeQuery(qry);
      if (rset.next() && rset.getTimestamp(1)!=null){
      	tfSingleUntil.setText(DBConnection.formatDate(rset.getTimestamp(1)).toString());
      	tfSingleUntil.setEnabled(false);
      }else{
        tfSingleUntil.setText(Messages.get("SINGLEMEMBER_TFSINGLEUNTIL_TEXT2"));
        tfSingleUntil.setEnabled(false);
      }
      
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();

      //String s = new String(tfSingleDate.getText());
     
//// ovaj if je stavljen je jer pucao program kad posle ponistenja akcije u NewMember udjemo u SingleMember
//      if ((!s.equals("")) && (s != null)) {
//System.out.println("setMemberFee datum="+s);
//    //    CoolDate cd = new CoolDate(dbConn.StringToDate(tfSingleDate.getText()).getTime());
//    CoolDate cd = new CoolDate(DBConnection.StringToDate(tfSingleDate.getText().trim()).getTime());
//        qry = "select period from mmbr_types where id = " + mbType;
//        rset = stmt.executeQuery(qry);
//       if(rset.next()){
//         int days = rset.getInt(1);
//         CoolDate endDate = new CoolDate((cd.plus(days)).getTime());
//          tfSingleUntil.setText(DBConnection.formatDate(endDate));
//       }
//       else
//          tfSingleUntil.setText(Messages.get("SINGLEMEMBER_TFSINGLEUNTIL_TEXT1"));
//        rset.close();
//      }
//      else
//        tfSingleUntil.setText(Messages.get("SINGLEMEMBER_TFSINGLEUNTIL_TEXT2"));
// print nema
    }catch(SQLException e){
      tfSingleMmbrship.setText(Messages.get("SINGLEMEMBER_TFSINGLEMMBRSHIP_TEXT2"));
      e.printStackTrace();
    }
  }

  public void setWarning() {
  	
  	try{
  		String qry = "select until_date from signing where single_id ='" + tfSingleMmbrID.getText() + "' order by until_date desc";  		
  		Statement stmt = dbConn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        if (rset.next()){
        	Date until = rset.getDate(1);
        	if (until != null){
	        	Date now = new java.sql.Date(System.currentTimeMillis());
	        	if(now.after(until)){
	        		stmt.executeUpdate("insert into blocked_cards (user_id, reason_id) values ('" + dsUsers.getRow().getData("ID") + "', 1)");
	        		dbConn.getConnection().commit();
	            }
        	}
        }
        rset.close();
        stmt.close();
  	}catch(SQLException e){
  	}
  	btnBlock.setText(Messages.get("SINGLEMEMBER_BTNBLOCK1_TEXT"));
  	blocked = false;
  	lWarning.setEnabled(false);
  	lWarningDesc.setText("");
  	try{
  		String qry = "select br.id, br.name, bc.note from blocked_cards bc, block_reasons br where bc.reason_id = br.id and bc.user_id ='" + tfSingleMmbrID.getText() + "'";  		
  		Statement stmt = dbConn.getConnection().createStatement();
        ResultSet rset = stmt.executeQuery(qry);
        if (rset.next()){
        	lWarning.setEnabled(true);
      		lWarningDesc.setText(rset.getString(2));
      		if (rset.getString(3) != null){
      			lWarningDesc.setText(lWarningDesc.getText() + ": "+rset.getString(3));
      			btnBlock.setText(Messages.get("SINGLEMEMBER_BTNBLOCK2_TEXT"));
      			blocked = true;
      		}
      		
        }
        while (rset.next()){	
        	lWarningDesc.setText(lWarningDesc.getText() + ", " + rset.getString(2));
        	if (rset.getString(3) != null){
      			lWarningDesc.setText(lWarningDesc.getText() + ": "+rset.getString(3));
      			btnBlock.setText(Messages.get("SINGLEMEMBER_BTNBLOCK2_TEXT"));
      			blocked = true;
      		} 
        }
        rset.close();
        stmt.close();
        dbConn.getConnection().commit();
  	}catch(SQLException e){
  	}  
/*    if (dsSingle.getRow().getData("NOTE").equals("")){
      lWarning.setEnabled(false);
    }
    else{
      lWarning.setEnabled(true);
    }*/
  }
  
  public void setDup(){
	  try{
	  		String qry = "select dup_no, dup_date from duplicate where user_id ='" + dsUsers.getRow().getData("ID") + "' order by dup_no desc";  		
	  		Statement stmt = dbConn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        if (rset.next()){
	        	dup_no = rset.getInt(1);
	        	dup_date = rset.getTimestamp(2);
	        	lSingleDup.setText(Messages.get("SINGLEMEMBER_LSINGLEDUP_TEXT") + " " + dup_no);
//	        	if (dup_date != null){
//	        		lSingleDupDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDUPDATE_TEXT") + " " + DBConnection.fromDatabaseDate(dup_date.toString()));
//	        	} else {
//	        		lSingleDupDate.setText(Messages.get("SINGLEMEMBER_LSINGLEDUPDATE_TEXT"));
//	        	}
	        }else{
	          dup_no = 0;
	          dup_date = null;
	          lSingleDup.setText("");
	        	//lSingleDupDate.setText("");
	        }
	        rset.close();
	        stmt.close();
	        dbConn.getConnection().commit();
	  	}catch(SQLException e){
	  		e.printStackTrace();
	  	}
  }

  private boolean canWrite(){
  	JDBTextField temp [] = null;
    String [] names = null;
    boolean hasText = true;
    int i = 0;
    if (jmbg.equals("1")){
		  temp = compulsorySingleTF; names = compSingleNames; 
	  } else {
		  temp = compulsorySingleTF2; names = compSingleNames2; 
	  }
    while(hasText && i < temp.length){
      if (temp[i].isEnabled())
      	hasText = !(temp[i].getText().equals(""));
      i++;
    }
    if(!hasText) {
      JOptionPane.showMessageDialog(null,"   "+Messages.get("SINGLEMEMBER_OPTIONPANE_MSG3")+" "+compSingleNames[i-1]+" "+Messages.get("SINGLEMEMBER_OPTIONPANE_MSG4"),Messages.get("SINGLEMEMBER_OPTIONPANE_MSG5"),JOptionPane.ERROR_MESSAGE);
      compulsorySingleTF[i-1].requestFocus();
    }
    return hasText;
  }


  private boolean checkLength(){
    boolean hasLength = false;
    int i = 0;
    while(!hasLength && i < lengthSingleTF.length){
    	if (lengthSingleTF[i].equals(tfSingleJMBG) && lengthSingleTF[i].getText().trim().length() != 0){
    		hasLength = (lengthSingleTF[i].getText().length() != lengthSingle[i]);
    	}else{
    		hasLength = (lengthSingleTF[i].getText().length() > lengthSingle[i]);
    	}
      i++;
    }
    if(hasLength) {
      JOptionPane.showMessageDialog(null,Messages.get("SINGLEMEMBER_OPTIONPANE_MSG6")+" "+lengthSingleNames[i-1],Messages.get("SINGLEMEMBER_OPTIONPANE_MSG7"),JOptionPane.ERROR_MESSAGE);
      lengthSingleTF[i-1].requestFocus();
    }
    return hasLength;
  }

  private boolean checkTypes(){
    boolean hasTypes = false;
    int i = 0;
    String message="";
    while(!hasTypes && i < typesSingleTF.length){
      if (!((typesSingleTF[i].getText()).equals(""))) {
        switch (typesSingleType[i]) {
          case 0:  // integer
                  try {
                    int ret=(int) Double.parseDouble(typesSingleTF[i].getText());
                  }
                  catch (NumberFormatException ex){
                    message=Messages.get("SINGLEMEMBER_MESSAGE_966")+" ";
                    hasTypes=true;
                  }
                  break;
          case 1:  // date

                    String s=typesSingleTF[i].getText();
                    boolean dig=true;
                    for (int j=0; dig&&(j<s.length()); j++){
                       dig=java.lang.Character.isDigit(s.charAt(j));
                       if (!(dig) && (s.charAt(j)=='.'))
                        dig=true;
                    }
                    if (!dig) {
                      message=Messages.get("SINGLEMEMBER_MESSAGE_980")+" ";
                      hasTypes=true;
                    }

                  break;

          case 2: // double
                  try {
                    Double ret=new Double(Double.parseDouble(typesSingleTF[i].getText()));
                  }
                  catch (NumberFormatException ex){
                    message=Messages.get("SINGLEMEMBER_MESSAGE_991")+" ";
                    hasTypes=true;
                  }
                  break;
        }
      }
      i++;
    }
    if(hasTypes) {
      JOptionPane.showMessageDialog(null,message+typesSingleNames[i-1],Messages.get("SINGLEMEMBER_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
      typesSingleTF[i-1].requestFocus();
    }
    return hasTypes;
  }

  public void reloadCMB() {
    cmCategs = new JDBLookupModel(dsCategs,"NAME",dsSingle,"USER_CTGR","ID");
    cmEduc = new JDBLookupModel(dsEduc,"NAME",dsSingle,"EDU_LVL","ID");
    cmMmbrType = new JDBLookupModel(dsMmbrType,"NAME",dsSingle,"MMBR_TYPE","ID");
//    cmGroup = new JDBLookupModel(dsGroup,"INST_NAME",dsSingle,"GRP_ID","USER_ID");
    cmLanguage = new JDBLookupModel(dsLanguage,"NAME", dsSingle,"LANGUAGE","ID");
    cboCategs.setComboModel(cmCategs);
    cboEduc.setComboModel(cmEduc);
    cboMmbrType.setComboModel(cmMmbrType);
//    cboGroup.setComboModel(cmGroup);
    cboLanguage.setComboModel(cmLanguage);
  }
}

