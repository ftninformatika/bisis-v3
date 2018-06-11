//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;
import com.gint.util.string.StringUtils;

import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.border.*;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class NewMember extends EscapeDialog {
  int selection = -1;
  DBConnection dbConn;
  private String usCtgr = "";
  private String mbType = "";


  boolean closed = false;

  MasterLinkDescriptor mld;

  DataSet dsSingle, dsInsts, dsUsers, dsGroup, dsCategs, dsEduc, dsMmbrType, dsBranch, dsBranchID, dsSigning, dsLanguage;
  JDBLookupModel cmCategs, cmEduc, cmMmbrType;
  //JDBLookupModel cmGroup;
 
  JPanel basePanel = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();

  JList lstUsrTypes = new JList(new String[] {Messages.get("NEWMEMBER_LSTUSRTYPES_STR1"),
                                               Messages.get("NEWMEMBER_LSTUSRTYPES_STR2"),
                                               Messages.get("NEWMEMBER_LSTUSRTYPES_STR3")});
  JScrollPane jScrollPane1 = new JScrollPane();
  JDBTextArea tSingleNote = new JDBTextArea();
  JLabel lUsrType = new JLabel();
  JButton btnSave = new JButton();
  JButton btnCancel = new JButton();
  JButton btnInsert = new JButton();
  JButton btnExit = new JButton();

  CardLayout cardLayout1 = new CardLayout();

  JPanel cardPanel = new JPanel();
  JTabbedPane tabsSingle = new JTabbedPane();
  JLabel lMessage = new JLabel();

  JPanel panel1 = new JPanel();

  JPanel panel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();

  JPanel pSingle1 = new JPanel();
  JLabel lSingleMmbrID = new JLabel();
  JLabel lSingleDate = new JLabel();
//  JDBTextField tfSingleMmbrID = new JDBTextField();
  JDBTextField tfSingleMmbrID = new JDBTextField();
  JDBTextField tfSingleMmbrBranch = new JDBTextField();
  JDBTextField tfMmbrIDFinal = new JDBTextField();
  JDBTextField tfSingleDate = new JDBTextField();
  JDBCheckBox  chkSingleWarn = new JDBCheckBox();
  JButton btnAuto = new JButton();
//  JDBCheckBox  chkSinglePermit = new JDBCheckBox();

  JPanel pSingle2 = new JPanel();

  JPanel pSingle3 = new JPanel();
  XYLayout xYLayout5 = new XYLayout();
  JPanel gboxox1 = new JPanel();
  XYLayout xYLayout12 = new XYLayout();
  JPanel gboxox2 = new JPanel();
  XYLayout xYLayout13 = new XYLayout();
  JLabel lSingleZvanje = new JLabel();
  JLabel lOccupation = new JLabel();
  JLabel lOrganization = new JLabel();
  JLabel lOrgAddress = new JLabel();
  JLabel lOrgZIP = new JLabel();
  JLabel lOrgPhone = new JLabel();
  JLabel lOrgPlace = new JLabel();
  JLabel lResideAddress = new JLabel();
  JLabel lResidePlace = new JLabel();
  JLabel lResideZIP = new JLabel();
  JLabel lResidePhone = new JLabel();
  JDBTextField tfSingleZvanje = new JDBTextField();
  JDBTextField tfSingleOcuppation = new JDBTextField();
  JDBTextField tfSingleOrgName = new JDBTextField();
  JDBTextField tfSingleResideAddress = new JDBTextField();
  JDBTextField tfSingleEmail = new JDBTextField();
  JDBTextField tfSingleOrgPlace = new JDBTextField();
  JDBTextField tfSingleOrgAddress = new JDBTextField();
  JDBTextField tfSingleOrgPhone = new JDBTextField();
  JDBTextField tfSingleResidePlace = new JDBTextField();
  JDBTextField tfSingleResideZIP = new JDBTextField();
  JDBTextField tfSingleResidePhone = new JDBTextField();
  JDBTextField tfSingleOrgZIP = new JDBTextField();
  JDBTextField tfSingleGrpID = new JDBTextField();
  
  

  ButtonGroup rgpGroup = new ButtonGroup();
  ButtonGroup rgpPol = new ButtonGroup();
  ButtonGroup rgpDocNo = new ButtonGroup();
  ButtonGroup rgpAge = new ButtonGroup();

  JPanel panel3 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JTabbedPane tabsGroup = new JTabbedPane();
  JPanel pGroup1 = new JPanel();
  JLabel lGrpMmbrID = new JLabel();
  JLabel lGrpDate = new JLabel();
  JDBTextField tfGrpMmbrID = new JDBTextField();
//  JDBLabel tfGrpMmbrID = new JDBLabel();
  JLabel lGrpBranch = new JLabel();
  JDBTextField tfGrpMmbrBranch = new JDBTextField();
  JDBTextField tfGrpMmbrIDFinal = new JDBTextField();
  JDBTextField tfGrpDate = new JDBTextField();
  JButton btnAutoGrp = new JButton();

  JPanel pGroup2 = new JPanel();
  JLabel lGrpName = new JLabel();
  JLabel lGrpAddress = new JLabel();
  JLabel lGrpPlace = new JLabel();
  JLabel lGrpZIP = new JLabel();
  JLabel lGrpPhone = new JLabel();
  JLabel lGrpFax = new JLabel();
  JLabel lGrpTelex = new JLabel();
  JDBTextField tfGrpName = new JDBTextField();
  JDBTextField tfGrpAddress = new JDBTextField();
  JDBTextField tfGrpPlace = new JDBTextField();
  JDBTextField tfGrpZIP = new JDBTextField();
  JDBTextField tfGrpPhone = new JDBTextField();
  JDBTextField tfGrpFax = new JDBTextField();
  JDBTextField tfGrpTelex = new JDBTextField();
  JLabel lGrpEmail = new JLabel();
  JDBTextField tfGrpEmail = new JDBTextField();

  JPanel pGroup3 = new JPanel();
  JLabel lGrpSecAddress = new JLabel();
  JLabel lGrpSecPlace = new JLabel();
  JLabel lGrpSecZIP = new JLabel();
  JLabel lGrpSecPhone = new JLabel();
  JDBTextField tfGrpSecAddress = new JDBTextField();
  JDBTextField tfGrpSecPlace = new JDBTextField();
  JDBTextField tfGrpSecZIP = new JDBTextField();
  JDBTextField tfGrpSecPhone = new JDBTextField();

  JPanel pGroup4 = new JPanel();
  XYLayout xYLayout7 = new XYLayout();
  JLabel lGrpContLastName = new JLabel();
  JLabel lGrpContFirstName = new JLabel();
  JLabel lGrpContEmail = new JLabel();
  JDBTextField tfGrpContLastName = new JDBTextField();
  JDBTextField tfGrpContFirstName = new JDBTextField();
  JDBTextField tfGrpContEmail = new JDBTextField();

  JTabbedPane tabsInsts = new JTabbedPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel panel4 = new JPanel();

  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout8 = new XYLayout();
  XYLayout xYLayout9 = new XYLayout();
  XYLayout xYLayout10 = new XYLayout();
  XYLayout xYLayout11 = new XYLayout();

  JPanel pInsts1 = new JPanel();
  JLabel lInstMmbrID = new JLabel();
  JLabel lInstDate = new JLabel();
  JDBTextField tfInstMmbrID = new JDBTextField();
//  JDBLabel tfInstMmbrID = new JDBLabel();
  JDBTextField tfInstDate = new JDBTextField();
  JDBCheckBox  chkInstWarn = new JDBCheckBox();
  JLabel lInstBranch = new JLabel();
  JDBTextField tfInstMmbrBranch = new JDBTextField();
  JDBTextField tfInstMmbrIDFinal = new JDBTextField();
  JButton btnAutoInst = new JButton();


  JPanel pInsts2 = new JPanel();
  JLabel lInstName = new JLabel();
  JLabel lInstAddress = new JLabel();
  JLabel lInstPlace = new JLabel();
  JLabel lInstZIP = new JLabel();
  JLabel lInstPhone = new JLabel();
  JLabel lInstFax = new JLabel();
  JLabel lInstTelex = new JLabel();
//  JLabel lInstSecPhone = new JLabel();
  JLabel lInstEmail = new JLabel();
  JDBTextField tfInstName = new JDBTextField();
  JDBTextField tfInstAddress = new JDBTextField();
  JDBTextField tfInstPlace = new JDBTextField();
  JDBTextField tfInstZIP = new JDBTextField();
  JDBTextField tfInstPhone = new JDBTextField();
  JDBTextField tfInstFax = new JDBTextField();
  JDBTextField tfInstTelex = new JDBTextField();
//  JDBTextField tfInstSecPhone = new JDBTextField();
  JDBTextField tfInstEmail = new JDBTextField();

  JPanel pInsts3 = new JPanel();
  JLabel lInstContLastName = new JLabel();
  JLabel lInstContFirstName = new JLabel();
  JLabel lInstContEmail = new JLabel();
  JLabel lInstContPhone = new JLabel();
  JDBTextField tfInstContLastName = new JDBTextField();
  JDBTextField tfInstContFirstName = new JDBTextField();
  JDBTextField tfInstContEmail = new JDBTextField();
  JDBTextField tfInstContPhone = new JDBTextField();
  XYLayout xYLayout16 = new XYLayout();

  JPanel gboxSingleGrp = new JPanel();
  //JDBComboBox cboGroup;
  JRadioButton rdbGrpNo = new JRadioButton();
  JLabel lSingleUntil = new JLabel();
  JLabel lSingleMmbrship = new JLabel();
  JRadioButton rdbGrpYes = new JRadioButton();
  XYLayout xYLayout15 = new XYLayout();
  JDBTextField tfSingleUntil = new JDBTextField();
  JLabel lSingleGrp = new JLabel();
  JDBTextField tfSingleMmbrship = new JDBTextField();
  JDBComboBox cboCategs;
  JLabel lSingleCategs = new JLabel();
  JLabel lSingleMmbrType = new JLabel();
  JDBComboBox cboMmbrType;
  JPanel gboxPol = new JPanel();
  JPanel gboxSingleID = new JPanel();
  XYLayout xYLayout17 = new XYLayout();
/* 20.04.00.*/
  JDBRadioButton rdbGrpNo1 = new JDBRadioButton(1);
  JDBRadioButton rdbGrpYes1 = new JDBRadioButton(2);
  XYLayout xYLayout21 = new XYLayout();
  JLabel lSingleLastName = new JLabel();
  JDBTextField tfSingleLastName = new JDBTextField();
  JDBTextField tfSingleFirstName = new JDBTextField();
  JLabel lSingleFirstName = new JLabel();
  JDBTextField tfSingleAscName = new JDBTextField();
  JLabel lSingleAscName = new JLabel();
  JPanel gboxSingleDocument = new JPanel();
  JDBRadioButton rdbDocI = new JDBRadioButton(2);
  JLabel lSingleJMBG = new JLabel();
  JLabel lSingleIndex = new JLabel();
  JLabel lSingleDocPlace = new JLabel();
  JDBRadioButton rdbDocL = new JDBRadioButton(1);
  XYLayout xYLayout18 = new XYLayout();
  JDBTextField tfSingleJMBG = new JDBTextField();
  JDBTextField tfSingleIndex = new JDBTextField();
  JLabel lSingleDocNo = new JLabel();
  JDBTextField tfSingleDocPlace = new JDBTextField();
  JDBTextField tfSingleDocNo = new JDBTextField();
  JDBRadioButton rdbDocP = new JDBRadioButton(3);
  JLabel lResidePlace1 = new JLabel();
  JLabel lResidePhone1 = new JLabel();
  JLabel lResideEmail = new JLabel();
  JPanel gboxox3 = new JPanel();
  JDBTextField tfSinglePlace = new JDBTextField();
  JDBTextField tfSinglePhone = new JDBTextField();
  JDBTextField tfSingleAddress = new JDBTextField();
  JLabel lResideAddress1 = new JLabel();
  XYLayout xYLayout19 = new XYLayout();
  JLabel lResideZIP1 = new JLabel();
  JDBTextField tfSingleZIP = new JDBTextField();
  JLabel lSingleEduc = new JLabel();
  JDBComboBox cboEduc;
  JButton btnPrint = new JButton();
  JLabel lSingleNapomena = new JLabel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JLabel lSingleUDK = new JLabel();
  JDBTextField tfInteresovanja = new JDBTextField();
  TitledBorder titledBorder1;
  
  JDBTextField tfSingleReceipt = new JDBTextField();
  JLabel lSingleReceipt = new JLabel();
  JLabel BranchLabel = new JLabel();
  
  JDBComboBox cboBranch, cboBranchID, cboLanguage, cboGrpBranch, cboInstBranch;
  JDBLookupModel cmBranch, cmLanguage, cmGrpBranch, cmInstBranch;
  JDBComboBoxModel cmBranchID;
  JLabel lLanguage = new JLabel();
  JDBRadioButton rdbAgeA = new JDBRadioButton(1);
  JDBRadioButton rdbAgeC = new JDBRadioButton(2);
  JPanel gboxAge = new JPanel();
  XYLayout xYLayout20 = new XYLayout();
  
  private static String zipcode = "",city = "", branch = "", jmbg = "";

  String mID;
  String currentDateStr;
  ZipPlaceDlg zipplace;
  ActionListener al;

// Nizovi obaveznih tekstfildova

// Single
  JDBTextField compulsorySingleTF [] = {tfSingleDate , tfSingleFirstName ,
                                        tfSingleLastName , tfSingleAddress , tfSinglePlace ,
                                        tfSingleZIP ,tfSingleDocNo, tfSingleMmbrship,tfSingleUntil,tfSingleMmbrID, tfSingleDocPlace, tfSingleJMBG};
  String [] compSingleNames = {Messages.get("NEWMEMBER_COMPSINGLENAMES_STR1"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR2"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR3"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR4"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR5"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR6"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR7"),
                               Messages.get("NEWMEMBER_COMPSINGLENAMES_STR8"),
							   Messages.get("NEWMEMBER_COMPSINGLENAMES_STR9"),
							   Messages.get("NEWMEMBER_COMPSINGLENAMES_STR10"),
							   Messages.get("NEWMEMBER_COMPSINGLENAMES_STR11"),
							   Messages.get("NEWMEMBER_COMPSINGLENAMES_STR12")};
  
  JDBTextField compulsorySingleTF2 [] = {tfSingleDate , tfSingleFirstName ,
          									tfSingleLastName , tfSingleAddress , tfSinglePlace ,
          									tfSingleZIP , tfSingleMmbrship,tfSingleUntil,tfSingleMmbrID};
  
  String [] compSingleNames2 = {Messages.get("NEWMEMBER_COMPSINGLENAMES_STR1"),
		  					    Messages.get("NEWMEMBER_COMPSINGLENAMES_STR2"),
							    Messages.get("NEWMEMBER_COMPSINGLENAMES_STR3"),
								Messages.get("NEWMEMBER_COMPSINGLENAMES_STR4"),
								Messages.get("NEWMEMBER_COMPSINGLENAMES_STR5"),
							    Messages.get("NEWMEMBER_COMPSINGLENAMES_STR6"),
								Messages.get("NEWMEMBER_COMPSINGLENAMES_STR8"),
								Messages.get("NEWMEMBER_COMPSINGLENAMES_STR9"),
								Messages.get("NEWMEMBER_COMPSINGLENAMES_STR10"),};

  JDBTextField lengthSingleTF [] = { tfSingleDate,tfSingleLastName, tfSingleFirstName, tfSingleAscName,
                                     tfSingleOcuppation, tfSingleZvanje, tfSingleAddress, tfSinglePlace,
                                     tfSingleIndex, tfSinglePhone, tfSingleEmail,
                                     tfSingleJMBG, tfSingleDocPlace, tfSingleDocNo,
                                     tfSingleOrgName, tfSingleOrgPlace, tfSingleOrgAddress,tfSingleOrgPhone,
                                     tfSingleResideAddress,tfSingleResidePlace,tfSingleResidePhone,
                                     tfInteresovanja,tfSingleMmbrship,tfSingleUntil,tfSingleReceipt,tfSingleMmbrID };

  int lengthSingle[] = { 10,30,30,30,
                         30,30,150,45,
                         15,50,50,
                         13,20,15,
                         150,30,150,30,
                         150,50,15,
                         60,10,10,30,9};

  String [] lengthSingleNames = { Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR1"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR2"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR3"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR4"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR5"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR6"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR7"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR8"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR9"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR10"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR11"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR12"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR13"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR14"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR15"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR16"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR17"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR18"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR19"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR20"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR21"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR22"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR25"),
                                  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR26"),
								  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR27"),
								  Messages.get("NEWMEMBER_LENGTHSINGLENAMES_STR28")};

  JDBTextField typesSingleTF [] ={tfSingleDate,tfSingleZIP,tfSingleResideZIP,tfSingleOrgZIP,tfSingleJMBG,
  								  tfSingleUntil,tfSingleMmbrship,tfSingleMmbrID};
  int typesSingleType [] = {1,0,0,0,2,1,0,0};
  String [] typesSingleNames = {Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR1"),
                                Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR2"),
                                Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR3"),
                                Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR4"),
                                Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR5"),
                                Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR7"),
								Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR8"),
								Messages.get("NEWMEMBER_TYPESSINGLENAMES_STR9")};

// Kolektivni

  JDBTextField compulsoryGroupTF [] = { tfGrpDate , tfGrpName, tfGrpAddress ,
                                        tfGrpPlace , tfGrpZIP , tfGrpMmbrID};
  String [] compGroupNames = {Messages.get("NEWMEMBER_COMPGROUPNAMES_STR1"),
                              Messages.get("NEWMEMBER_COMPGROUPNAMES_STR2"),
                              Messages.get("NEWMEMBER_COMPGROUPNAMES_STR3"),
                              Messages.get("NEWMEMBER_COMPGROUPNAMES_STR4"),
                              Messages.get("NEWMEMBER_COMPGROUPNAMES_STR5"),
							  Messages.get("NEWMEMBER_COMPGROUPNAMES_STR6")};

  JDBTextField lengthGroupTF [] = { tfGrpDate,tfGrpName,tfGrpAddress,tfGrpPlace,
                                    tfGrpPhone,tfGrpFax,tfGrpTelex,tfGrpEmail,
                                    tfGrpSecAddress,tfGrpSecPlace,tfGrpSecPhone,
                                    tfGrpContLastName,tfGrpContFirstName,tfGrpContEmail, tfGrpMmbrID};
  int lengthGroup[] = { 10,45,150,45,
                        50,15,15,50,
                        45,45,15,
                        30,30,30,9};
  String [] lengthGroupNames = {Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR1"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR2"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR3"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR4"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR5"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR6"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR7"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR8"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR9"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR10"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR11"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR12"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR13"),
                                Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR14"),
								Messages.get("NEWMEMBER_LENGTHGROUPNAMES_STR15")};

  JDBTextField typesGroupTF [] ={tfGrpDate,tfGrpZIP,tfGrpSecZIP,tfGrpMmbrID};
  int typesGroupType [] = {1,0,0,0};
  String [] typesGroupNames = { Messages.get("NEWMEMBER_TYPESGROUPNAMES_STR1"),
                                Messages.get("NEWMEMBER_TYPESGROUPNAMES_STR2"),
                                Messages.get("NEWMEMBER_TYPESGROUPNAMES_STR3"),
								Messages.get("NEWMEMBER_TYPESGROUPNAMES_STR4")};

// Institucije pozajmice
  JDBTextField compulsoryInstsTF [] = {tfInstDate , tfInstName ,tfInstAddress ,
                                       tfInstPlace ,tfInstZIP ,  tfInstContFirstName ,tfInstContLastName,tfInstMmbrID};

  String [] compInstNames = { Messages.get("NEWMEMBER_COMPINSTNAMES_STR1"),
                              Messages.get("NEWMEMBER_COMPINSTNAMES_STR2"),
                              Messages.get("NEWMEMBER_COMPINSTNAMES_STR3"),
                              Messages.get("NEWMEMBER_COMPINSTNAMES_STR4"),
                              Messages.get("NEWMEMBER_COMPINSTNAMES_STR5"),
                              Messages.get("NEWMEMBER_COMPINSTNAMES_STR6"),
                              Messages.get("NEWMEMBER_COMPINSTNAMES_STR7"),
							  Messages.get("NEWMEMBER_COMPINSTNAMES_STR8")};


  JDBTextField lengthInstTF [] = { tfInstDate,tfInstName,tfInstAddress,tfInstPlace,
                                   tfInstPhone,tfInstFax,tfInstTelex,tfInstEmail,
                                   tfInstContLastName,tfInstContFirstName,tfInstContEmail,tfInstContPhone,tfInstMmbrID};

  int lengthInst[] = { 10,45,150,45,
                       50,15,15,50,
                       30,30,30,15,9};

  String [] lengthInstNames = { Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR1"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR2"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR3"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR4"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR5"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR6"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR7"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR8"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR9"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR10"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR11"),
                                Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR12"),
								Messages.get("NEWMEMBER_LENGTHINSTNAMES_STR13")};

  JDBTextField typesInstTF [] ={tfInstDate,tfInstZIP,tfInstMmbrID};
  int typesInstType [] = {1,0,0};
  String [] typesInstNames = {Messages.get("NEWMEMBER_TYPESINSTNAMES_STR1"),
                              Messages.get("NEWMEMBER_TYPESINSTNAMES_STR2"),
							  Messages.get("NEWMEMBER_TYPESINSTNAMES_STR3")};
                              

  public NewMember(Frame frame, String title, boolean modal, DBConnection db) {
    super(frame, title, modal);
    dbConn = db;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public NewMember() {
    this(CircApplet.parent, "", false, null);
  }

  public NewMember(String title,DBConnection db, boolean modal){
    this(CircApplet.parent,title,modal,db);
  }

  void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    pSingle2.setMinimumSize(new Dimension(371, 450));
    pSingle2.setPreferredSize(new Dimension(371, 450));
    
    INIFile iniFile = new INIFile(
            FileUtils.getClassDir(NewMember.class) + "/config.ini"); 
    //zipcode = iniFile.getString("circ", "defaultZipCode");
    //city = iniFile.getString("circ", "defaultCity");
    branch = iniFile.getString("circ", "homeBranch");
    jmbg = iniFile.getString("circ", "jmbg");

    dsUsers = dbConn.getUsersModel();
    dsSingle = dbConn.getSingleModel();
    dsGroup = dbConn.getGroupModel();
    dsCategs = dbConn.getCategs();
    dsEduc = dbConn.getEduLvl();
    dsMmbrType = dbConn.getMmbrType();
    dsInsts = dbConn.getInstModel();
    dsBranch = dbConn.getBranch();
    dsSigning = dbConn.getSigning();
    dsLanguage = dbConn.getLanguage();
    dsBranchID = new DataSet(dbConn.getConnection(),"BRANCH", new String[]{"ID"});
    currentDateStr = DBConnection.getTodaysDate();
    
    ResultSet rset = dsBranch.executeQuery("select name from branch where id ="+branch);
    if (rset.next())
    	branch = rset.getString(1);

    mld = new MasterLinkDescriptor(dsUsers,new String[] {"ID"},new String[] {"USER_ID"});

    cmCategs = new JDBLookupModel(dsCategs,"NAME",dsSingle,"USER_CTGR","ID");
    cmEduc = new JDBLookupModel(dsEduc,"NAME",dsSingle,"EDU_LVL","ID");
    cmMmbrType = new JDBLookupModel(dsMmbrType,"NAME",dsSingle,"MMBR_TYPE","ID");
//    cmGroup = new JDBLookupModel(dsGroup,"INST_NAME",dsSingle,"GRP_ID","USER_ID");
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cmBranchID = new JDBComboBoxModel(dsBranchID,"NAME");
    cmLanguage = new JDBLookupModel(dsLanguage,"NAME", dsSingle,"LANGUAGE","ID");
    cmGrpBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cmInstBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    
    cboBranch = new JDBComboBox(cmBranch);
    cboBranchID = new JDBComboBox(cmBranchID);
    cboLanguage = new JDBComboBox(cmLanguage);
//   cboGroup = new JDBComboBox(cmGroup);
    cboMmbrType = new JDBComboBox(cmMmbrType);
    lSingleMmbrType.setText(Messages.get("NEWMEMBER_LSINGLEMEMBERTYPE_TEXT"));
    lSingleCategs.setText(Messages.get("NEWMEMBER_LSINGLECATEGS_TEXT"));
    cboCategs = new JDBComboBox(cmCategs);
    cboGrpBranch = new JDBComboBox(cmBranch);
    cboInstBranch = new JDBComboBox(cmBranch);
    cboEduc = new JDBComboBox(cmEduc);
//    cmBranch.setSelectedItem(branch);
//    tfSingleMmbrBranch.setText(StringUtils.padZeros(Integer.parseInt(cmBranch.getData()),2));
//    cmLanguage.setSelectedItem(cboLanguage.getSelectedItem());
//    cmMmbrType.setSelectedItem(cboMmbrType.getSelectedItem());
//    cmCategs.setSelectedItem(cboCategs.getSelectedItem());
//    cmGrpBranch.setSelectedItem(branch);
//    tfGrpMmbrBranch.setText(StringUtils.padZeros(Integer.parseInt(cmGrpBranch.getData()),2));
//    cmInstBranch.setSelectedItem(branch);
//    tfInstMmbrBranch.setText(StringUtils.padZeros(Integer.parseInt(cmInstBranch.getData()),2));
//    cmEduc.setSelectedItem(cboEduc.getSelectedItem());
//    setMemberFee();

    lSingleEduc.setText(Messages.get("NEWMEMBER_LSINGLEEDUC_TEXT"));
    lSingleGrp.setText(Messages.get("NEWMEMBER_LSINGLEGRP_TEXT"));
    rdbGrpYes.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpGroup.getSelection().equals(rdbGrpYes))
          setGroupEnabled(true);
        else
          setGroupEnabled(false);
      }
    });
    rdbGrpYes.setText(Messages.get("NEWMEMBER_RDPGRPYES_TEXT"));
    lSingleMmbrship.setText(Messages.get("NEWMEMBER_LSINGLEMMBRSHIP_TEXT"));
    lSingleUntil.setText(Messages.get("NEWMEMBER_LSINGLEUNTIL_TEXT"));
    rdbGrpNo.setSelected(true);
    rdbGrpNo.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpGroup.getSelection().equals(rdbGrpNo))
          setGroupEnabled(false);
        else
          setGroupEnabled(true);
      }
    });
    rdbGrpNo.setText(Messages.get("NEWMEMBER_RDPGRPNO_TEXT"));

    tfSingleEmail.setDataSet(dsUsers);
    tfSingleEmail.setColumn("EMAIL");
    lGrpEmail.setText(Messages.get("NEWMEMBER_LGRPEMAIL_TEXT"));
    lInstTelex.setText(Messages.get("NEWMEMBER_LINSTTELEX_TEXT"));

    cardPanel.setPreferredSize(new Dimension(417, 600));
    panel2.setPreferredSize(new Dimension(417, 600));
    lSingleReceipt.setText(Messages.get("NEWMEMBER_LSINGLERECEIPT_TEXT"));
    BranchLabel.setText(Messages.get("NEWMEMBER_BRANCHLABEL_TEXT"));
    tfSingleMmbrBranch.setEnabled(false);
    lLanguage.setText(Messages.get("NEWMEMBER_LLANGUAGE_TEXT"));
    rgpGroup.add(rdbGrpYes);
    rgpGroup.add(rdbGrpNo);

    gboxSingleGrp.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOXSINGLEGRP_BORDERTITLE")));
    gboxSingleGrp.setLayout(xYLayout15);

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed) initState();
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        closed = true;
// dodat switch 24.02.00.
        selection = lstUsrTypes.getSelectedIndex();
//        dsUsers.removeFilter();    // dodato 15.11.00.
        switch(selection){
          case 0: dsSingle.removeMasterLink();
                  break;
          case 1: dsGroup.removeMasterLink();
                  break;
          case 2: dsInsts.removeMasterLink();
                  break;
        }

        clearPanels();
        setVisible(false);
      }
    });

    this.setContentPane(basePanel);
    lUsrType.setText(Messages.get("NEWMEMBER_LUSRTYPE_TEXT"));


// dodaje se ChangeListener koji obavestava o promenama nad podacima

    cardPanel.setBackground(Color.gray);
    lMessage.setText(Messages.get("NEWMEMBER_LMESSAGE_TEXT"));
    lMessage.setFont(new Font("Dialog", 1, 20));
    lSingleMmbrID.setText(Messages.get("NEWMEMBER_LSINGLEMMBRID_TEXT"));
    lSingleDate.setText(Messages.get("NEWMEMBER_LSINGLEDATE_TEXT"));
    chkSingleWarn.setText(Messages.get("NEWMEMBER_CHKSINGLEWARN_TEXT"));
    chkSingleWarn.setSelected(true);
    chkSingleWarn.setDataSet(dsUsers);
    chkSingleWarn.setColumn("WARNING");
    lSingleZvanje.setText(Messages.get("NEWMEMBER_LSINGLEZVANJE_TEXT"));
    lOccupation.setText(Messages.get("NEWMEMBER_LOCCUPATION_TEXT"));
    lOrganization.setText(Messages.get("NEWMEMBER_LORGANISATION_TEXT"));
    lOrgAddress.setText(Messages.get("NEWMEMBER_LORGADDRESS_TEXT"));
    lOrgZIP.setText(Messages.get("NEWMEMBER_LORGZIP_TEXT"));
    lOrgPhone.setText(Messages.get("NEWMEMBER_LORGPHONE_TEXT"));
    lResideAddress.setText(Messages.get("NEWMEMBER_RESIDEADDRESS_TEXT"));
    lResidePlace.setText(Messages.get("NEWMEMBER_RESIDEPLACE_TEXT"));
    lResideZIP.setText(Messages.get("NEWMEMBER_RESIDEZIP_TEXT"));
    lResidePhone.setText(Messages.get("NEWMEMBER_RESIDEPHONE_TEXT"));
    lOrgPlace.setText(Messages.get("NEWMEMBER_LORGPLACE_TEXT"));
    gboxox1.setLayout(xYLayout12);
    gboxox1.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOX1_TITLE"))); // bilo Prebivali\u0161te
    gboxox2.setLayout(xYLayout13);
    gboxox2.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOX2_TITLE")));
    basePanel.setMinimumSize(new Dimension(586, 600));
    basePanel.setPreferredSize(new Dimension(586, 600));
    tabsSingle.setMinimumSize(new Dimension(417, 600));
    tabsSingle.setPreferredSize(new Dimension(417, 600));

    basePanel.setLayout(xYLayout1);

    cardPanel.setLayout(cardLayout1);
    panel1.setLayout(xYLayout16);
    panel2.setLayout(borderLayout2);
    panel3.setLayout(borderLayout3);
    panel4.setLayout(borderLayout4);

    pSingle1.setLayout(xYLayout2);
    pSingle2.setLayout(xYLayout3);
    pSingle3.setLayout(xYLayout5);

    pGroup1.setLayout(xYLayout4);
    pGroup2.setLayout(xYLayout6);
    pGroup3.setLayout(xYLayout7);
    pGroup4.setLayout(xYLayout8);

    pInsts3.setLayout(xYLayout11);
    pInsts2.setLayout(xYLayout10);
    pInsts1.setLayout(xYLayout9);

    xYLayout1.setHeight(537);
    xYLayout1.setWidth(586);

    lGrpMmbrID.setText(Messages.get("NEWMEMBER_LGRPMMBRID_TEXT"));
    lGrpBranch.setText(Messages.get("NEWMEMBER_LGRPBRANCH_TEXT"));
    lGrpDate.setText(Messages.get("NEWMEMBER_LGRPDATE_TEXT"));
    lGrpName.setText(Messages.get("NEWMEMBER_LGRPNAME_TEXT"));
    lGrpAddress.setText(Messages.get("NEWMEMBER_LGRPADDRESS_TEXT"));
    lGrpPlace.setText(Messages.get("NEWMEMBER_LGRPPLACE_TEXT"));
    lGrpZIP.setText(Messages.get("NEWMEMBER_LGRPZIP"));
    lGrpPhone.setText(Messages.get("NEWMEMBER_LGRPPHONE_TEXT"));
    lGrpFax.setText(Messages.get("NEWMEMBER_LGRPFAX_TEXT"));
    lGrpTelex.setText(Messages.get("NEWMEMBER_LGRPTELEX_TEXT"));
    lGrpSecAddress.setText(Messages.get("NEWMEMBER_LGRPSECADDRESS_TEXT"));
    lGrpSecPlace.setText(Messages.get("NEWMEMBER_LGRPSECPLACE_TEXT"));
    lGrpSecZIP.setText(Messages.get("NEWMEMBER_LGRPSECZIP_TEXT"));
    lGrpSecPhone.setText(Messages.get("NEWMEMBER_LGRPSECPHONE_TEXT"));
    lGrpContLastName.setText(Messages.get("NEWMEMBER_LGRPCONTLASTNAME_TEXT"));
    lGrpContFirstName.setText(Messages.get("NEWMEMBER_LGRPCONTFIRSTNAME_TEXT"));
    lGrpContEmail.setText(Messages.get("NEWMEMBER_LGRPCONTEMAIL_TEXT"));

    lInstMmbrID.setText(Messages.get("NEWMEMBER_LINSTMMBRID_TEXT"));
    lInstBranch.setText(Messages.get("NEWMEMBER_LINSTBRANCH_TEXT"));
    lInstDate.setText(Messages.get("NEWMEMBER_LINSTDATE_TEXT"));
    chkInstWarn.setText(Messages.get("NEWMEMBER_CHKINSTWARN_TEXT"));
    chkInstWarn.setDataSet(dsUsers);
    chkInstWarn.setColumn("WARNING");
    lInstName.setText(Messages.get("NEWMEMBER_LINSTNAME_TEXT"));
    lInstAddress.setText(Messages.get("NEWMEMBER_LINSTADDRESS_TEXT"));
    lInstPlace.setText(Messages.get("NEWMEMBER_LINSTPLACE_TEXT"));
    lInstZIP.setText(Messages.get("NEWMEMBER_LINSTZIP_TEXT"));
    lInstPhone.setText(Messages.get("NEWMEMBER_LINSTPHONE_TEXT"));
    lInstFax.setText(Messages.get("NEWMEMBER_LINSTFAX_TEXT"));
    lInstContLastName.setText(Messages.get("NEWMEMBER_LINSTCONLASTNAME_TEXT"));
    lInstContFirstName.setText(Messages.get("NEWMEMBER_LINSTCONTFIRSTNAME_TEXT"));
    lInstContEmail.setText(Messages.get("NEWMEMBER_LINSTCONTEMAIL_TEXT"));
    lInstEmail.setText(Messages.get("NEWMEMBER_LINSTEMAIL_TEXT"));
    lInstContPhone.setText(Messages.get("NEWMEMBER_LINSTCONTPHONE_TEXT"));

    btnSave.setText(Messages.get("NEWMEMBER_BTNSAVE_TEXT"));


    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });

    btnInsert.setText(Messages.get("NEWMEMBER_BTNINSERT_TEXT"));
    btnInsert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnInsert_actionPerformed(e);
      }
    });

    btnCancel.setText(Messages.get("NEWMEMBER_BTNCANCEL_TEXT"));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });

    btnExit.setText(Messages.get("NEWMEMBER_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });
    
    //btnAuto.setText(Messages.get("NEWMEMBER_BTNAUTO_TEXT"));
    btnAuto.setText("<< Auto");
    btnAuto.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAuto_actionPerformed(e);
      }
    });
    
    btnAutoGrp.setText("<< Auto");
    btnAutoGrp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAutoGrp_actionPerformed(e);
      }
    });
    
    btnAutoInst.setText("<< Auto");
    btnAutoInst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAutoInst_actionPerformed(e);
      }
    });


    lstUsrTypes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent e) {
        list_valueChanged(e);
      }
    });

    
    tfMmbrIDFinal.setDataSet(dsUsers);
    tfMmbrIDFinal.setColumn("ID");
    tfSingleDate.setDataSet(dsUsers);
    tfSingleDate.setColumn("SIGN_DATE");
    tfSingleZIP.setDataSet(dsUsers);
    tfSingleZIP.setColumn("ZIPCODE");
    

    tfSingleZvanje.setDataSet(dsSingle);
    tfSingleZvanje.setColumn("TITLE");
    tfSingleOcuppation.setDataSet(dsSingle);
    tfSingleOcuppation.setColumn("OCCUPATION");
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
    tfSingleReceipt.setDataSet(dsSigning);
    tfSingleReceipt.setColumn("RECEIPT_ID");
    tfSingleMmbrship.setDataSet(dsSigning);
    tfSingleMmbrship.setColumn("COST");
    tfSingleUntil.setDataSet(dsSigning);
    tfSingleUntil.setColumn("UNTIL_DATE");
    tfSingleGrpID.setDataSet(dsSingle);
    tfSingleGrpID.setColumn("GRP_ID");

    tfGrpName.setDataSet(dsGroup);
    tfGrpName.setColumn("INST_NAME");
    tfGrpFax.setDataSet(dsGroup);
    tfGrpFax.setColumn("FAX");
    tfGrpTelex.setDataSet(dsGroup);
    tfGrpTelex.setColumn("TELEX");
    tfGrpSecAddress.setDataSet(dsGroup);

    tfGrpEmail.setDataSet(dsUsers);
    tfGrpEmail.setColumn("EMAIL");

    tfGrpSecAddress.setColumn("SEC_ADDRESS");
    tfGrpSecPlace.setDataSet(dsGroup);
    tfGrpSecPlace.setColumn("SEC_ADDRESS");
    tfGrpSecZIP.setDataSet(dsGroup);
    tfGrpSecZIP.setColumn("SEC_ZIP");
    tfGrpSecPhone.setDataSet(dsGroup);
    tfGrpSecPhone.setColumn("SEC_PHONE");
    tfGrpContLastName.setDataSet(dsGroup);
    tfGrpContLastName.setColumn("CONT_LNAME");
    tfGrpContFirstName.setDataSet(dsGroup);
    tfGrpContFirstName.setColumn("CONT_FNAME");
    tfGrpContEmail.setDataSet(dsGroup);
    tfGrpContEmail.setColumn("CONT_EMAIL");

    
    tfGrpDate.setDataSet(dsUsers);
    tfGrpDate.setColumn("SIGN_DATE");
    tfGrpAddress.setDataSet(dsUsers);
    tfGrpAddress.setColumn("ADDRESS");
    tfGrpPlace.setDataSet(dsUsers);
    tfGrpPlace.setColumn("CITY");
    tfGrpZIP.setDataSet(dsUsers);
    tfGrpZIP.setColumn("ZIPCODE");
    tfGrpPhone.setDataSet(dsUsers);
    tfGrpPhone.setColumn("PHONE");

   
    tfInstDate.setDataSet(dsUsers);
    tfInstDate.setColumn("SIGN_DATE");
    tfInstAddress.setDataSet(dsUsers);
    tfInstAddress.setColumn("ADDRESS");
    tfInstPlace.setDataSet(dsUsers);
    tfInstPlace.setColumn("CITY");
    tfInstZIP.setDataSet(dsUsers);
    tfInstZIP.setColumn("ZIPCODE");
    tfInstPhone.setDataSet(dsUsers);
    tfInstPhone.setColumn("PHONE");

    tfInstName.setDataSet(dsInsts);
    tfInstName.setColumn("INST_NAME");

    tfInstFax.setDataSet(dsInsts);
    tfInstFax.setColumn("FAX");
    tfInstEmail.setDataSet(dsUsers);
    tfInstEmail.setColumn("EMAIL");
    tfInstTelex.setDataSet(dsInsts);
    tfInstTelex.setColumn("TELEX");
    tfInstContLastName.setDataSet(dsInsts);
    tfInstContLastName.setColumn("CONT_LNAME");
    tfInstContFirstName.setDataSet(dsInsts);
    tfInstContFirstName.setColumn("CONT_FNAME");
    tfInstContEmail.setDataSet(dsInsts);
    tfInstContEmail.setColumn("CONT_EMAIL");
    tfInstContPhone.setDataSet(dsInsts);
    tfInstContPhone.setColumn("CONT_PHONE");

    gboxPol.setLayout(xYLayout17);
    gboxPol.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOXPOL_TITLE")));
    gboxAge.setLayout(xYLayout20);
    gboxAge.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOXAGE_TITLE")));
    gboxSingleID.setLayout(xYLayout21);
    gboxSingleID.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_LSINGLEMMBRID_TEXT")));

    rdbGrpNo1.setText(Messages.get("NEWMEMBER_RDBGRPNO1_TEXT"));
    rdbGrpYes1.setText(Messages.get("NEWMEMBER_RDBGRPYES1_TEXT"));
    rdbGrpYes1.setDataSet(dsSingle);
    rdbGrpNo1.setDataSet(dsSingle);
    rdbGrpYes1.setColumn("GENDER");
    rdbGrpNo1.setColumn("GENDER");
    rdbGrpNo1.setSelected(true);
    rgpPol.add(rdbGrpNo1);
    rgpPol.add(rdbGrpYes1);
    
    rdbAgeA.setText(Messages.get("NEWMEMBER_RDBAGEA_TEXT"));
    rdbAgeC.setText(Messages.get("NEWMEMBER_RDBAGEC_TEXT"));
    rdbAgeA.setDataSet(dsSingle);
    rdbAgeC.setDataSet(dsSingle);
    rdbAgeA.setColumn("AGE");
    rdbAgeC.setColumn("AGE");
    rdbAgeA.setSelected(true);
    rgpAge.add(rdbAgeA);
    rgpAge.add(rdbAgeC);

    lSingleLastName.setText(Messages.get("NEWMEMBER_LSINGLELASTNAME_TEXT"));
    tfSingleLastName.setDataSet(dsSingle);
    tfSingleLastName.setColumn("LAST_NAME");
    tfSingleFirstName.setDataSet(dsSingle);
    tfSingleFirstName.setColumn("FIRST_NAME");
    lSingleFirstName.setText(Messages.get("NEWMEMBER_LSINGLEFIRSTNAME_TEXT"));
    tfSingleAscName.setDataSet(dsSingle);
    tfSingleAscName.setColumn("PARENT_NAME");
    lSingleAscName.setText(Messages.get("NEWMEMBER_LSINGLEPARENTNAME_TEXT"));
    gboxSingleDocument.setLayout(xYLayout18);
    gboxSingleDocument.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOXSINGLEDOCUMENT_TITLE")));
    rdbDocI.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpDocNo.getSelection().equals(rdbDocI) || rgpDocNo.getSelection().equals(rdbDocL))
          setJMBGEnabled(false);
        else
          setJMBGEnabled(true);
      }
    });
    rdbDocI.setText(Messages.get("NEWMEMBER_RDBDOCI_TEXT"));

    lSingleIndex.setText(Messages.get("NEWMEMBER_LSINGLEINDEX_TEXT"));
    tfSingleIndex.setDataSet(dsSingle);
    tfSingleIndex.setColumn("INDEX_NO");
    lSingleJMBG.setText(Messages.get("NEWMEMBER_LSINGLEJMBG_TEXT"));
    tfSingleJMBG.setDataSet(dsSingle);
    tfSingleJMBG.setColumn("JMBG");
    lSingleDocPlace.setText(Messages.get("NEWMEMBER_LSINGLEDOCPLACE_TEXT"));
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
    rdbDocL.setText(Messages.get("NEWMEMBER_RDBCDOCL_TEXT"));
    lSingleDocNo.setText(Messages.get("NEWMEMBER_LSINGLEDOCNO_TEXT"));
    tfSingleDocNo.setDataSet(dsSingle);
    tfSingleDocNo.setColumn("DOC_NO");
    rdbDocP.setText(Messages.get("NEWMEMBER_RDBDOCP_TEXT"));

    rdbDocL.setDataSet(dsSingle);
    rdbDocI.setDataSet(dsSingle);
    rdbDocP.setDataSet(dsSingle);
    rdbDocL.setColumn("DOC_ID");
    rdbDocI.setColumn("DOC_ID");
    rdbDocP.setColumn("DOC_ID");

    rgpDocNo.add(rdbDocL);
    rgpDocNo.add(rdbDocP);
    rgpDocNo.add(rdbDocI);
    rdbDocP.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(rgpDocNo.getSelection().equals(rdbDocP))
          setJMBGEnabled(true);
        else
          setJMBGEnabled(false);
      }
    });
    lResidePlace1.setText(Messages.get("NEWMEMBER_LRESIDEPLACE1_TEXT"));
    lResidePhone1.setText(Messages.get("NEWMEMBER_LRESIDEPHONE1_TEXT"));
    gboxox3.setBorder(BorderFactory.createTitledBorder(Messages.get("NEWMEMBER_GBOXOX3_TITLE"))); // bilo
    gboxox3.setLayout(xYLayout19);
    tfSinglePlace.setDataSet(dsUsers);
    tfSinglePlace.setColumn("CITY");
    tfSinglePhone.setDataSet(dsUsers);
    tfSinglePhone.setColumn("PHONE");
    tfSingleAddress.setDataSet(dsUsers);
    tfSingleAddress.setColumn("ADDRESS");
    lResideAddress1.setText(Messages.get("NEWMEMBER_LRESIDEADDRESS1_TEXT"));
    lResideZIP1.setText(Messages.get("NEWMEMBER_LRESIDEZIP1_TEXT"));
    lResideEmail.setText(Messages.get("NEWMEMBER_LRESIDEEMAIL_TEXT"));
    
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

    btnPrint.setText(Messages.get("NEWMEMBER_BTNPRINT_TEXT"));
    btnPrint.setEnabled(false);
    btnPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrint_actionPerformed(e);
      }
    });
    lSingleNapomena.setText(Messages.get("NEWMEMBER_LSINGLENAPOMENA_TEXT"));
    jScrollPane2.getViewport().setBackground(Color.white);
    jScrollPane2.getViewport().add(tSingleNote,null);
    jScrollPane2.getViewport().setView(tSingleNote);
    tSingleNote.setDataSet(dsSingle);
    tSingleNote.setColumn("NOTE");

    lSingleUDK.setText(Messages.get("NEWMEMBER_LSINGLEUDK_TEXT"));
    tfInteresovanja.setDataSet(dsSingle);
    tfInteresovanja.setColumn("INTERESTS");
    basePanel.add(lUsrType, new XYConstraints(25, 19, -1, -1));
    basePanel.add(btnSave, new XYConstraints(36, 189, 80,25));
    basePanel.add(btnInsert, new XYConstraints(36, 230, 80, 25));
    basePanel.add(btnCancel, new XYConstraints(36, 271, 80, 25));
    basePanel.add(btnExit, new XYConstraints(36, 312, 80, 25));
    basePanel.add(cardPanel,     new XYConstraints(169, 0, 428, 580));
    cardPanel.add(panel1, "init");
    panel1.add(lMessage, new XYConstraints(98, 116, -1, -1));
    cardPanel.add(panel2, "individue");
    panel2.add(tabsSingle, BorderLayout.CENTER);
    tabsSingle.addTab(Messages.get("NEWMEMBER_TABSSINGLE_TEXT1861"), pSingle1);
    pSingle1.add(gboxSingleID,new XYConstraints(40, 20, 340, 110));
    gboxSingleID.add(cboBranchID,    new XYConstraints(40, 10, 220, -1));
    gboxSingleID.add(tfSingleMmbrID, new XYConstraints(80, 50, 80, -1));
    gboxSingleID.add(tfSingleMmbrBranch, new XYConstraints(40, 50, 30, -1));
    gboxSingleID.add(btnAuto, new XYConstraints(180, 48, 80, 22));
    pSingle1.add(lSingleDate, new XYConstraints(40, 163, -1, -1));
    pSingle1.add(BranchLabel,    new XYConstraints(80, 206, -1, -1));
    pSingle1.add(cboBranch,    new XYConstraints(170, 203, 210, -1));
    pSingle1.add(tfSingleDate, new XYConstraints(170, 163, 120, -1));
    pSingle1.add(chkSingleWarn, new XYConstraints(170, 250, -1, -1));
    

    tabsSingle.addTab(Messages.get("NEWMEMBER_TABSSINGLE_TEXT2868"), pSingle2);
    pSingle2.add(gboxSingleGrp,   new XYConstraints(6, 60, 380, 140));
    gboxSingleGrp.add(rdbGrpYes, new XYConstraints(12, 0, -1, -1));
    gboxSingleGrp.add(rdbGrpNo, new XYConstraints(168, 0, -1, -1));
    gboxSingleGrp.add(lSingleGrp, new XYConstraints(13, 24, -1, -1));
//    gboxSingleGrp.add(cboGroup, new XYConstraints(14, 47, 134, -1));
    gboxSingleGrp.add(tfSingleGrpID, new XYConstraints(14, 47, 120, -1));
    gboxSingleGrp.add(tfSingleMmbrship, new XYConstraints(254, 24, 70, -1));
    gboxSingleGrp.add(lSingleMmbrship, new XYConstraints(172, 24, -1, -1));
    gboxSingleGrp.add(tfSingleUntil, new XYConstraints(255, 51, 70, -1));
    gboxSingleGrp.add(lSingleUntil, new XYConstraints(177, 51, -1, -1));
    gboxSingleGrp.add(lSingleReceipt,    new XYConstraints(162, 78, -1, -1));
    gboxSingleGrp.add(tfSingleReceipt, new XYConstraints(255, 78, 70, -1));
    pSingle2.add(lSingleCategs, new XYConstraints(9, 8, -1, -1));
    pSingle2.add(cboCategs, new XYConstraints(142, 6, 150, -1));
    pSingle2.add(cboMmbrType, new XYConstraints(143, 34, 150, -1));
    pSingle2.add(lSingleMmbrType, new XYConstraints(10, 36, -1, -1));
    pSingle2.add(gboxPol,  new XYConstraints(240, 202, 75, 80));
    gboxPol.add(rdbGrpYes1, new XYConstraints(0, 0, -1, -1));
    gboxPol.add(rdbGrpNo1, new XYConstraints(0, 28, -1, -1));
    pSingle2.add(gboxAge, new XYConstraints(320, 202, 75, 80));
    gboxAge.add(rdbAgeA, new XYConstraints(0, 0, -1, -1));
    gboxAge.add(rdbAgeC, new XYConstraints(0, 28, -1, -1));
    

    pSingle2.add(lSingleLastName,  new XYConstraints(10, 209, 69, -1));
    pSingle2.add(tfSingleLastName, new XYConstraints(85, 207, 150, -1));
    pSingle2.add(tfSingleFirstName, new XYConstraints(85, 232, 150, -1));
    pSingle2.add(lSingleFirstName,  new XYConstraints(10, 234, 69, -1));
    pSingle2.add(tfSingleAscName, new XYConstraints(85, 260, 151, -1));
    pSingle2.add(lSingleAscName,  new XYConstraints(10, 262, 79, -1));

    pSingle2.add(gboxSingleDocument,   new XYConstraints(11, 287, 385, 101));
    gboxSingleDocument.add(rdbDocL, new XYConstraints(10, 0, -1, -1));
    gboxSingleDocument.add(lSingleDocPlace, new XYConstraints(15, 52, -1, -1));
    gboxSingleDocument.add(lSingleDocNo, new XYConstraints(14, 31, -1, -1));
    gboxSingleDocument.add(rdbDocI, new XYConstraints(264, 0, -1, -1));
    gboxSingleDocument.add(rdbDocP, new XYConstraints(131, 0, -1, -1));
    gboxSingleDocument.add(tfSingleDocNo, new XYConstraints(110, 27, 89, -1));
    gboxSingleDocument.add(tfSingleDocPlace, new XYConstraints(110, 53, 88, -1));
    gboxSingleDocument.add(tfSingleIndex, new XYConstraints(281, 26, 90, -1));
    gboxSingleDocument.add(lSingleIndex, new XYConstraints(210, 29, -1, -1));
    gboxSingleDocument.add(tfSingleJMBG, new XYConstraints(251, 51, 120, -1));
    gboxSingleDocument.add(lSingleJMBG, new XYConstraints(210, 52, -1, -1));
    pSingle2.add(gboxox3,  new XYConstraints(11, 390, 385, 142));
    gboxox3.add(lResidePhone1, new XYConstraints(3, 89, 49, 16));
    gboxox3.add(tfSinglePhone, new XYConstraints(46, 88, 120, -1));
    gboxox3.add(tfSingleEmail, new XYConstraints(226, 89, 117, -1));
    gboxox3.add(lResideEmail, new XYConstraints(185, 89, 43, 21));
    gboxox3.add(tfSingleZIP, new XYConstraints(103, 30, 121, -1));
    gboxox3.add(tfSingleAddress, new XYConstraints(104, 4, 150, -1));
    gboxox3.add(lResideAddress1, new XYConstraints(48, 2, 48, 20));
    gboxox3.add(lResideZIP1, new XYConstraints(53, 31, 29, -1));
    gboxox3.add(lResidePlace1, new XYConstraints(50, 53, 43, 21));
    gboxox3.add(tfSinglePlace, new XYConstraints(102, 58, 150, -1));

    tabsSingle.addTab(Messages.get("NEWMEMBER_TABSSINGLE_TEXT3917"), pSingle3);
    pSingle3.add(cboEduc, new XYConstraints(147, 12, 150, -1));
    pSingle3.add(lSingleEduc, new XYConstraints(14, 14, -1, -1));
    pSingle3.add(lOccupation, new XYConstraints(14, 47, 67, -1));
    pSingle3.add(tfSingleOcuppation, new XYConstraints(147, 44, 150, -1));
    pSingle3.add(tfSingleZvanje, new XYConstraints(147, 74, 150, -1));
    pSingle3.add(lSingleZvanje, new XYConstraints(14, 77, -1, -1));
    pSingle3.add(lLanguage, new XYConstraints(14, 107, -1, -1));
    pSingle3.add(cboLanguage, new XYConstraints(147, 104, 150, -1));
    pSingle3.add(gboxox2, new XYConstraints(8, 142, 400, 130));
    gboxox2.add(tfSingleOrgName, new XYConstraints(80, 11, 150, -1));
    gboxox2.add(lOrganization, new XYConstraints(4, 13, -1, -1));
    gboxox2.add(lOrgAddress, new XYConstraints(4, 43, -1, -1));
    gboxox2.add(tfSingleOrgPlace, new XYConstraints(80, 75, 150, -1));
    gboxox2.add(lOrgZIP, new XYConstraints(255, 43, -1, -1));
    gboxox2.add(tfSingleOrgZIP, new XYConstraints(293, 41, 70, -1));
    gboxox2.add(lOrgPhone, new XYConstraints(237, 13, -1, -1));
    gboxox2.add(tfSingleOrgPhone, new XYConstraints(293, 11, 70, -1));
    gboxox2.add(lOrgPlace, new XYConstraints(4, 70, -1, 29));
    gboxox2.add(tfSingleOrgAddress, new XYConstraints(80, 41, 150, -1));
    pSingle3.add(gboxox1, new XYConstraints(8, 273, 400, 150));
    gboxox1.add(lResidePhone, new XYConstraints(75, 98, 49, 16));
    gboxox1.add(lResideZIP, new XYConstraints(75, 66, 29, 17));
    gboxox1.add(lResideAddress, new XYConstraints(75, 8, 48, 20));
    gboxox1.add(lResidePlace, new XYConstraints(75, 36, 43, 21));
    gboxox1.add(tfSingleResideAddress, new XYConstraints(140, 9, 150, -1));
    gboxox1.add(tfSingleResidePlace, new XYConstraints(140, 37, 150, -1));
    gboxox1.add(tfSingleResideZIP, new XYConstraints(140, 65, 122, -1));
    gboxox1.add(tfSingleResidePhone, new XYConstraints(140, 95, 120, -1));
    pSingle3.add(btnPrint, new XYConstraints(312, 506, 80, 26));
    pSingle3.add(lSingleNapomena, new XYConstraints(14, 432, 85, -1));
    pSingle3.add(jScrollPane2, new XYConstraints(105, 434, 259, 46));
    pSingle3.add(lSingleUDK, new XYConstraints(12, 498, 85, -1));
    pSingle3.add(tfInteresovanja, new XYConstraints(108, 499, 161, 22));

    cardPanel.add(panel3, "kolektivni");
    panel3.add(tabsGroup, BorderLayout.CENTER);
    tabsGroup.addTab(Messages.get("NEWMEMBER_TABSGROUP_TEXT1953"), pGroup1);
    pGroup1.add(lGrpMmbrID, new XYConstraints(22, 76, -1, -1));
    pGroup1.add(lGrpDate, new XYConstraints(22, 123, -1, -1));
    pGroup1.add(tfGrpMmbrID, new XYConstraints(195, 76, 80, -1));
    pGroup1.add(tfGrpDate, new XYConstraints(158, 123, 120, -1));
    pGroup1.add(cboGrpBranch,new XYConstraints(158, 33, 220, -1));
    pGroup1.add(lGrpBranch, new XYConstraints(60, 36, -1, -1));
    pGroup1.add(tfGrpMmbrBranch, new XYConstraints(158, 76, 30, -1));
    pGroup1.add(btnAutoGrp, new XYConstraints(290, 75, 80, 22));
    tabsGroup.addTab(Messages.get("NEWMEMBER_TABSGROUP_TEXT2958"), pGroup2);
    pGroup2.add(tfGrpName, new XYConstraints(174, 18, 120, -1));
    pGroup2.add(tfGrpAddress, new XYConstraints(174, 59, 120, -1));
    pGroup2.add(tfGrpPlace, new XYConstraints(174, 100, 120, -1));
    pGroup2.add(tfGrpZIP, new XYConstraints(174, 142, 100, -1));
    pGroup2.add(tfGrpPhone, new XYConstraints(174, 183, 100, -1));
    pGroup2.add(tfGrpFax, new XYConstraints(174, 224, 100, -1));
    pGroup2.add(tfGrpTelex, new XYConstraints(174, 265, 100, -1));
    pGroup2.add(lGrpName, new XYConstraints(41, 18, -1, -1));
    pGroup2.add(lGrpAddress, new XYConstraints(41, 62, -1, -1));
    pGroup2.add(lGrpPlace, new XYConstraints(41, 102, -1, -1));
    pGroup2.add(lGrpZIP, new XYConstraints(41, 144, -1, -1));
    pGroup2.add(lGrpPhone, new XYConstraints(41, 185, -1, -1));
    pGroup2.add(lGrpFax, new XYConstraints(41, 226, 37, -1));
    pGroup2.add(lGrpTelex, new XYConstraints(41, 267, -1, -1));
    pGroup2.add(lGrpEmail, new XYConstraints(41, 301, 81, 19));
    pGroup2.add(tfGrpEmail, new XYConstraints(174, 301, 100, 20));
    tabsGroup.addTab(Messages.get("NEWMEMBER_TABSGROUP_TEXT3975"), pGroup3);
    pGroup3.add(lGrpSecAddress, new XYConstraints(64, 24, -1, -1));
    pGroup3.add(lGrpSecPlace, new XYConstraints(64, 81, -1, -1));
    pGroup3.add(lGrpSecZIP, new XYConstraints(64, 137, -1, -1));
    pGroup3.add(lGrpSecPhone, new XYConstraints(64, 194, -1, -1));
    pGroup3.add(tfGrpSecAddress, new XYConstraints(197, 24, 120, -1));
    pGroup3.add(tfGrpSecPlace, new XYConstraints(197, 81, 120, -1));
    pGroup3.add(tfGrpSecZIP, new XYConstraints(197, 137, 100, -1));
    pGroup3.add(tfGrpSecPhone, new XYConstraints(197, 194, 100, -1));
    tabsGroup.addTab(Messages.get("NEWMEMBER_TABSGROUP_TEXT4984"), pGroup4);
    pGroup4.add(lGrpContLastName, new XYConstraints(86, 57, -1, -1));
    pGroup4.add(lGrpContFirstName, new XYConstraints(86, 114, -1, -1));
    pGroup4.add(lGrpContEmail, new XYConstraints(86, 183, -1, -1));
    pGroup4.add(tfGrpContLastName, new XYConstraints(186, 57, 120, -1));
    pGroup4.add(tfGrpContFirstName, new XYConstraints(186, 114, 120, -1));
    pGroup4.add(tfGrpContEmail, new XYConstraints(186, 183, 120, -1));
    cardPanel.add(panel4, "institucije");
    panel4.add(tabsInsts, BorderLayout.CENTER);
    tabsInsts.addTab(Messages.get("NEWMEMBER_TABSINST_TEXT1993"), pInsts1);
    pInsts1.add(lInstMmbrID, new XYConstraints(22, 76, -1, -1));
    pInsts1.add(lInstDate, new XYConstraints(22, 123, -1, -1));
    pInsts1.add(lInstBranch,    new XYConstraints(60, 36, -1, -1));
    pInsts1.add(cboInstBranch,    new XYConstraints(158, 33, 220, -1));
    pInsts1.add(tfInstMmbrID, new XYConstraints(195, 76, 80, -1));
    pInsts1.add(tfInstMmbrBranch, new XYConstraints(158, 76, 30, -1));
    pInsts1.add(btnAutoInst, new XYConstraints(290, 75, 80, 22));
    pInsts1.add(tfInstDate, new XYConstraints(158, 123, 120, -1));
    pInsts1.add(chkInstWarn, new XYConstraints(161, 180, -1, -1));
    
    tabsInsts.addTab(Messages.get("NEWMEMBER_TABSINST_TEXT2999"), pInsts2);
    pInsts2.add(lInstName, new XYConstraints(89, 18, -1, -1));
    pInsts2.add(lInstAddress, new XYConstraints(89, 62, -1, -1));
    pInsts2.add(lInstPlace, new XYConstraints(89, 106, -1, -1));
    pInsts2.add(lInstZIP, new XYConstraints(89, 150, -1, -1));
    pInsts2.add(lInstPhone, new XYConstraints(89, 195, -1, -1));
    pInsts2.add(lInstFax, new XYConstraints(89, 245, -1, -1));
    pInsts2.add(tfInstName, new XYConstraints(174, 18, 120, -1));
    pInsts2.add(tfInstAddress, new XYConstraints(174, 62, 120, -1));
    pInsts2.add(tfInstPlace, new XYConstraints(174, 106, 120, -1));
    pInsts2.add(tfInstZIP, new XYConstraints(174, 150, 100, -1));
    pInsts2.add(tfInstPhone, new XYConstraints(174, 195, 100, -1));
    pInsts2.add(tfInstFax, new XYConstraints(174, 245, 100, -1));
    pInsts2.add(tfInstTelex, new XYConstraints(174, 289, 100, 20));
    pInsts2.add(lInstTelex, new XYConstraints(89, 289, 78, 19));
    pInsts2.add(tfInstEmail, new XYConstraints(174, 333, 100, -1));
    pInsts2.add(lInstEmail, new XYConstraints(89, 333, -1, -1));
    tabsInsts.addTab(Messages.get("NEWMEMBER_TABSINST_TEXT31016"), pInsts3);
    pInsts3.add(lInstContLastName, new XYConstraints(86, 57, -1, -1));
    pInsts3.add(lInstContFirstName, new XYConstraints(86, 114, -1, -1));
    pInsts3.add(lInstContEmail, new XYConstraints(86, 183, -1, -1));
    pInsts3.add(tfInstContLastName, new XYConstraints(186, 57, 120, -1));
    pInsts3.add(tfInstContFirstName, new XYConstraints(186, 114, 120, -1));
    pInsts3.add(tfInstContEmail, new XYConstraints(186, 183, 120, -1));
    pInsts3.add(lInstContPhone, new XYConstraints(86, 243, -1, -1));
    pInsts3.add(tfInstContPhone, new XYConstraints(186, 242, 120, -1));
    jScrollPane1.getViewport().add(lstUsrTypes, null);
    basePanel.add(jScrollPane1, new XYConstraints(11, 42, 121, 123));

    btnSave.setEnabled(false);
    btnInsert.setEnabled(false);

    setGroupEnabled(true);

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
    cboBranchID.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	tfSingleMmbrBranch.setText(StringUtils.padZeros(Integer.parseInt(cmBranchID.getDataSet().getRow().getData("ID")),DBConnection.getCodeSize()));
        	tfSingleMmbrID.setText("");
        }
      });
    
    cboGrpBranch.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	tfGrpMmbrBranch.setText(StringUtils.padZeros(Integer.parseInt(cmGrpBranch.getData()),DBConnection.getCodeSize()));
        	tfGrpMmbrID.setText("");
        }
      });
    
    cboInstBranch.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	tfInstMmbrBranch.setText(StringUtils.padZeros(Integer.parseInt(cmInstBranch.getData()),DBConnection.getCodeSize()));
        	tfInstMmbrID.setText("");
        }
      });
    
    jScrollPane2.setBorder(titledBorder1);
    jScrollPane2.setOpaque(true);
    jScrollPane2.setDoubleBuffered(true);
  }

  void btnSave_actionPerformed(ActionEvent e) {
    if(canWrite() && !(checkLength()) && !(checkTypes()) && maxID()){
      switch(selection){
        case 0:
          if(rdbGrpNo.isSelected()){
            dsSingle.getRow().setData("0","GRP_ID");
          }else if(rdbGrpYes.isSelected()){
          	dsSingle.getRow().setData(DBConnection.toDBMember(tfSingleGrpID.getText()),"GRP_ID");
          }
/*          else if(rdbGrpYes.isSelected()){
            cmGroup.post();
          }*/
          tfMmbrIDFinal.setText(mID);
          chkSingleWarn.post(true);
          cmCategs.setData();
          cmEduc.setData();
          cmMmbrType.setData();
          cmBranch.setData();
          cmLanguage.setData();
          rdbGrpNo1.post(true);
          //rdbGrpYes1.post(true);
          rdbDocL.post(true);
          rdbAgeA.post(true);
          dsUsers.getRow().setData("1","USER_TYPE");
          dsSigning.getRow().setData(mID,"SINGLE_ID");
          dsSigning.getRow().setData(tfSingleDate.getText(),"SDATE");
          dsSigning.getRow().setData(dbConn.getOfficial(), "BIB_ID");
          dsSigning.getRow().setRowState(RowState.TOINSERT);
          break;
        case 1:
          tfMmbrIDFinal.setText(mID);
          dsUsers.getRow().setData("2","USER_TYPE");
          dsUsers.getRow().setData("0","WARNING");
          cmGrpBranch.setData();
          break;
        case 2:
          tfMmbrIDFinal.setText(mID);
          chkInstWarn.post(true);
          dsUsers.getRow().setData("3","USER_TYPE");
          cmInstBranch.setData();
          break;
      }
      dsUsers.saveRow();
      if (selection == 0){
      		dsSigning.saveRow();
      }
      if (dsUsers.hasError() && dsSingle.hasError() && dsGroup.hasError() && dsInsts.hasError()) {
        lstUsrTypes.setEnabled(true);
        btnInsert.setEnabled(true);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        btnPrint.setEnabled(true);
        int tmin=dsUsers.getMax();
        int tmax=dsUsers.getMin();
        JOptionPane.showMessageDialog(null,Messages.get("NEWMEMBER_OPTIONPANE_MSG1"),Messages.get("NEWMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
      }
      else {
        if (dsUsers.hasError()) {
           dsUsers.deleteMasterRow();
        }
      }
    }
  }

  void btnInsert_actionPerformed(ActionEvent e) {
  	clearPanels();
    insertState();
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    clearPanels();
    initState();
  }


  private void clearPanels(){
    JComponent comp = null;
    switch (selection){
      case 0:
        comp = tabsSingle;
        break;
      case 1:
        comp = tabsGroup;
        break;
      case 2:
        comp = tabsGroup;
        break;
    }
    if(comp != null) clearJDBTextFields(comp);
  }

  void btnExit_actionPerformed(ActionEvent e) {
    closed = true;
    clearPanels();
// dodat switch 24.02.00.
    selection = lstUsrTypes.getSelectedIndex();
//    dsUsers.removeFilter();    // dodato 15.11.00.
    switch(selection){
        case 0: dsSingle.removeMasterLink();
                break;
        case 1: dsGroup.removeMasterLink();
                break;
        case 2: dsInsts.removeMasterLink();
                break;
    }

    this.setVisible(false);
  }
  
  void btnAuto_actionPerformed(ActionEvent e) {
  	String branch = cmBranchID.getDataSet().getRow().getData("ID");
  	try {
  		Statement stmt = dbConn.getConnection().createStatement();
  		ResultSet rset = stmt.executeQuery("SELECT last_user_id FROM branch WHERE id="+branch+" FOR UPDATE");
		rset.next();
		int id = rset.getInt(1) + 1;
		rset.close();
		stmt.executeUpdate("UPDATE branch SET last_user_id = last_user_id + 1 WHERE id="+branch);
		dbConn.getConnection().commit();
		while (exist(id)){
			rset = stmt.executeQuery("SELECT last_user_id FROM branch WHERE id="+branch+" FOR UPDATE");
			rset.next();
			id = rset.getInt(1) + 1;
			rset.close();
			stmt.executeUpdate("UPDATE branch SET last_user_id = last_user_id + 1 WHERE id="+branch);
			dbConn.getConnection().commit();
		}
		stmt.close();
		tfSingleMmbrID.setText(new Integer(id).toString());
		
  	}catch (Exception ex){
  		ex.printStackTrace();
  	}
  }
  
  void btnAutoGrp_actionPerformed(ActionEvent e) {
  	String branch = cmGrpBranch.getData();
  	try {
  		Statement stmt = dbConn.getConnection().createStatement();
  		ResultSet rset = stmt.executeQuery("SELECT last_user_id FROM branch WHERE id="+branch+" FOR UPDATE");
		rset.next();
		int id = rset.getInt(1) + 1;
		rset.close();
		stmt.executeUpdate("UPDATE branch SET last_user_id = last_user_id + 1 WHERE id="+branch);
		dbConn.getConnection().commit();
		while (exist(id)){
			rset = stmt.executeQuery("SELECT last_user_id FROM branch WHERE id="+branch+" FOR UPDATE");
			rset.next();
			id = rset.getInt(1) + 1;
			rset.close();
			stmt.executeUpdate("UPDATE branch SET last_user_id = last_user_id + 1 WHERE id="+branch);
			dbConn.getConnection().commit();
		}
		stmt.close();
		tfGrpMmbrID.setText(new Integer(id).toString());
		
  	}catch (Exception ex){
  		ex.printStackTrace();
  	}
  }
  
  void btnAutoInst_actionPerformed(ActionEvent e) {
  	String branch = cmInstBranch.getData();
  	try {
  		Statement stmt = dbConn.getConnection().createStatement();
  		ResultSet rset = stmt.executeQuery("SELECT last_user_id FROM branch WHERE id="+branch+" FOR UPDATE");
		rset.next();
		int id = rset.getInt(1) + 1;
		rset.close();
		stmt.executeUpdate("UPDATE branch SET last_user_id = last_user_id + 1 WHERE id="+branch);
		dbConn.getConnection().commit();
		while (exist(id)){
			rset = stmt.executeQuery("SELECT last_user_id FROM branch WHERE id="+branch+" FOR UPDATE");
			rset.next();
			id = rset.getInt(1) + 1;
			rset.close();
			stmt.executeUpdate("UPDATE branch SET last_user_id = last_user_id + 1 WHERE id="+branch);
			dbConn.getConnection().commit();
		}
		stmt.close();
		tfInstMmbrID.setText(new Integer(id).toString());
		
  	}catch (Exception ex){
  		ex.printStackTrace();
  	}
  }
  
  private boolean exist(int id){
  	boolean exist = true;
  	String br = "";
  	switch (selection){
  		case 0:
  			br= tfSingleMmbrBranch.getText() + StringUtils.padZeros(id,11-DBConnection.getCodeSize());
  			break;
  		case 1:
  			br= tfGrpMmbrBranch.getText() + StringUtils.padZeros(id,11-DBConnection.getCodeSize());
  			break;
		case 2:
			br= tfInstMmbrBranch.getText() + StringUtils.padZeros(id,11-DBConnection.getCodeSize());
  			break;
  	}
  	
  	try{
		Statement stmt = dbConn.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery("select * from users where id = '"+br+"'");
			if (rset.next()){
				exist = true;
			}else {
				exist = false;
			}
			rset.close();
			stmt.close();
			dbConn.getConnection().commit();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return exist; 	
  }

  void btnPrint_actionPerformed(ActionEvent e) {
	  Map params = new HashMap(3);
      params.put("datum", tfSingleDate.getText());
      params.put("ime", tfSingleLastName.getText() +" "+ tfSingleFirstName.getText());
      params.put("broj", mID);
      try{
	      JasperPrint jp = JasperFillManager.fillReport(
	              NewMember.class.getResource(
	                "/com/gint/app/bisis/circ/reports/newmember.jasper").openStream(), 
	                params, dbConn.getConnection());
	      JasperViewer.viewReport(jp, false);
      }catch (Exception e1){
    	  e1.printStackTrace();
      }
  }

  public void list_valueChanged(javax.swing.event.ListSelectionEvent e){
    if(!e.getValueIsAdjusting()){
      int wasSelection = selection;
      switch(selection){
        case 0: dsSingle.removeMasterLink();
//                dsUsers.removeFilter(); // 15.11.00
                break;
        case 1: dsGroup.removeMasterLink();
//                dsUsers.removeFilter();    // 15.11.00
                break;
        case 2: dsInsts.removeMasterLink();
//                dsUsers.removeFilter(); // 15.11.00
                break;
      }
      selection = lstUsrTypes.getSelectedIndex();

      switch(selection){
        case 0:
//          cmGroup = new JDBLookupModel(dsGroup,"INST_NAME",dsSingle,"GRP_ID","USER_ID"); // dodato 28.02.00.
//          cboGroup.setComboModel(cmGroup); // dodato 28.02.00.
          dsSingle.setMasterLink(mld);
//    dsUsers.setFilter(" USER_TYPE = 1"); // dodato 15.11.00.
//    dsUsers.getMin(); // dodato 15.11.00.
//    dsUsers.getMax(); // dodato 15.11.00.

          cardLayout1.show(cardPanel,"individue");
        break;
        case 1:
          dsGroup.setMasterLink(mld);
//    dsUsers.setFilter(" USER_TYPE = 2"); // dodato 15.11.00.
//    dsUsers.getMin(); // dodato 15.11.00.
//    dsUsers.getMax(); // dodato 15.11.00.
          cardLayout1.show(cardPanel,"kolektivni");
        break;
        case 2:
          dsInsts.setMasterLink(mld);
//    dsUsers.setFilter(" USER_TYPE = 3"); // dodato 15.11.00.
//    dsUsers.getMin(); // dodato 15.11.00.
//    dsUsers.getMax(); // dodato 15.11.00.
          cardLayout1.show(cardPanel,"institucije");
        break;
      }
      if(selection == -1)
        initState();
      else
        insertState();
    }
  }

// na osnovu vrednosti sekvence mmbridseq odredjuje sledecu vrednost za broj clana
  private boolean maxID() {
  	boolean uredu = false;
  	String tmp1 = "", tmp2 = "";
  	switch (selection){
  		case 0:
  			tmp1 = tfSingleMmbrBranch.getText();
  			tmp2 = tfSingleMmbrID.getText();
  			break;
  		case 1:
  			tmp1 = tfGrpMmbrBranch.getText();
  			tmp2 = tfGrpMmbrID.getText();
  			break;
  		case 2:	
  			tmp1 = tfInstMmbrBranch.getText();
  			tmp2 = tfInstMmbrID.getText();
  			break;
  	}
  	tmp2 = StringUtils.padZeros(Integer.parseInt(tmp2),11-DBConnection.getCodeSize());
  	if (tmp2 != null){
  		mID = tmp1 + tmp2;
  		try{
  			Statement stmt = dbConn.getConnection().createStatement();
  			ResultSet rset = stmt.executeQuery("select * from users where id = '"+mID+"'");
  			if (rset.next()){
  				JOptionPane.showMessageDialog(null,Messages.get("NEWMEMBER_OPTIONPANE_MSG9"),Messages.get("NEWMEMBER_OPTIONPANE_MSG10"),JOptionPane.ERROR_MESSAGE);
  			}else {
  				uredu = true;
  			}
  			rset.close();
  			stmt.close();
  			dbConn.getConnection().commit();
  		}catch (Exception ex){
  			ex.printStackTrace();
  		}
  	}       
     return uredu; 
    
  }


// popunjava podatke o broju clana i trenutnom datumu
  void insertData(){
    switch(selection){
      case 0: tfSingleMmbrID.setText(mID);
              tfSingleDate.setText(currentDateStr);
              //tfSingleZIP.setText(zipcode);
              //tfSinglePlace.setText(city);
              cmBranch.setSelectedItem(branch);
              cmBranchID.setSelectedItem(branch);
              cboBranchID.setSelectedItem(branch);
              cmMmbrType.setSelectedItem(cboMmbrType.getSelectedItem());
              cmCategs.setSelectedItem(cboCategs.getSelectedItem());
              cmLanguage.setSelectedItem(cboLanguage.getSelectedItem());
              cmEduc.setSelectedItem(cboEduc.getSelectedItem());
              setMemberFee();
              chkSingleWarn.setSelected(true); /* dodato 19.04.*/
              break;
      case 1: tfGrpMmbrID.setText(mID);
              tfGrpDate.setText(currentDateStr);
              cmGrpBranch.setSelectedItem(branch);
//              chkGrpWarn.setSelected(true); /* dodato 19.04.*/
              break;
      case 2: tfInstMmbrID.setText(mID);
              tfInstDate.setText(currentDateStr);
              cmInstBranch.setSelectedItem(branch);
              chkInstWarn.setSelected(true); /* dodato 19.04.*/
              break;
    }
  }

// proverava da li su popunjeni obavezni podaci
  private boolean canWrite(){
    JDBTextField temp [] = null;
    String [] names = null;
    boolean hasText = true;
    int i = 0;
    switch(selection){
      case 0: 
    	  if (jmbg.equals("1")){
    		  temp = compulsorySingleTF; names = compSingleNames; 
    	  } else {
    		  temp = compulsorySingleTF2; names = compSingleNames2; 
    	  }
    	  break;
      case 1: temp = compulsoryGroupTF; names = compGroupNames; break;
      case 2: temp = compulsoryInstsTF;names = compInstNames; break;
    }
    while(hasText && i < temp.length){
    	if (temp[i].isEnabled())
    		hasText = !(temp[i].getText().equals(""));
        i++;
    }
    if(!hasText) {
      JOptionPane.showMessageDialog(null,"  "+Messages.get("NEWMEMBER_OPTIONPANE_MSG3")+" "+names[i-1]+" "+Messages.get("NEWMEMBER_OPTIONPANE_MSG4"),Messages.get("NEWMEMBER_OPTIONPANE_MSG5"),JOptionPane.ERROR_MESSAGE);
      temp[i-1].requestFocus();
    }
    return hasText;
  }


  private boolean checkLength(){
    JDBTextField temp [] = null;
    String [] names = null;
    int tempLength [] =null;
    boolean hasLength = false;
    int i = 0;
    switch(selection){
      case 0: temp = lengthSingleTF; names = lengthSingleNames; tempLength=lengthSingle; break;
      case 1: temp = lengthGroupTF; names = lengthGroupNames; tempLength=lengthGroup;break;
      case 2: temp = lengthInstTF;names = lengthInstNames; tempLength=lengthInst;break;
    }
    while(!hasLength && i < temp.length){
    	if (temp[i].isEnabled()){
			if (temp[i].equals(tfSingleJMBG) && temp[i].getText().trim().length() != 0){
				hasLength = (temp[i].getText().length() != tempLength[i]);
			} else {
				hasLength = (temp[i].getText().length() > tempLength[i]);
			}
    	}
		i++;
    }
    if(hasLength) {
      JOptionPane.showMessageDialog(null,Messages.get("NEWMEMBER_OPTIONPANE_MSG7")+" "+names[i-1],Messages.get("NEWMEMBER_OPTIONPANE_MSG6"),JOptionPane.ERROR_MESSAGE);
      temp[i-1].requestFocus();
    }
    return hasLength;
  }


  private boolean checkTypes(){
    boolean hasTypes = false;

    JDBTextField temp [] = null;
    String [] names = null;
    int tempTypes [] =null;
    int i = 0;
    String message="";
    String sadrzaj="";

    switch(selection){
        case 0: temp = typesSingleTF; names = typesSingleNames; tempTypes=typesSingleType; break;
        case 1: temp = typesGroupTF; names = typesGroupNames; tempTypes=typesGroupType; break;
        case 2: temp = typesInstTF;names = typesInstNames; tempTypes=typesInstType; break;
    }

    while(!hasTypes && i < temp.length){
    	if (temp[i].isEnabled()){
	      sadrzaj=temp[i].getText();
	      if (!(sadrzaj.equals(""))) {
	        switch (tempTypes[i]) {
	          case 0:  // integer
	                  try {
	                    int ret=(int) Double.parseDouble(sadrzaj);
	                  }
	                  catch (NumberFormatException ex){
	                    message=Messages.get("NEWMEMBER_MESSAGE_GRCEO")+" ";
	                    hasTypes=true;
	                  }
	                  break;
	          case 1:  // date
	                    boolean dig=true;
	                    for (int j=0; dig&&(j<sadrzaj.length()); j++){
	                       dig=java.lang.Character.isDigit(sadrzaj.charAt(j));
	                       if (!(dig) && (sadrzaj.charAt(j)=='.'))
	                        dig=true;
	                    }
	                    if (!dig) {
	                      message=Messages.get("NEWMEMBER_MESSAGE_GRDAT")+" ";
	                      hasTypes=true;
	                    }
	
	                  break;
	
	          case 2: // double
	                  try {
	                    Double ret=new Double(Double.parseDouble(sadrzaj));
	                  }
	                  catch (NumberFormatException ex){
	                    message=Messages.get("NEWMEMBER_MESSAGE_GRREL")+" ";
	                    hasTypes=true;
	                  }
	                  break;
	        }
	      }
    	}
      i++;
    }
    if(hasTypes) {
      JOptionPane.showMessageDialog(null,message+names[i-1],Messages.get("NEWMEMBER_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
      temp[i-1].requestFocus();
    }
    return hasTypes;
  }


  void setGroupEnabled(boolean enb){
    //lSingleMmbrship.setEnabled(enb);
    //tfSingleMmbrship.setEnabled(enb);
    //lSingleUntil.setEnabled(enb);
    //tfSingleUntil.setEnabled(enb);
    //lSingleReceipt.setEnabled(enb);
    //tfSingleReceipt.setEnabled(enb);
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

  private void clearJDBTextFields(JComponent cont){
    for(int i = 0; i < cont.getComponentCount(); i++){
      Component temp = cont.getComponent(i);
      if(temp instanceof JPanel) clearJDBTextFields ((JPanel) temp);
      if(temp instanceof JDBTextField && temp.isEnabled())
        ((JDBTextField)temp).setText("");
    }
  }

// ova metoda postavlja inicijalni cardPanel kao aktivni
  private void initState(){
    btnSave.setEnabled(false);
    btnInsert.setEnabled(false);
    lstUsrTypes.setEnabled(true);
    lstUsrTypes.clearSelection();
    cardLayout1.show(cardPanel,"init");
    tabsSingle.setSelectedIndex(0);
    tabsGroup.setSelectedIndex(0);
    tabsInsts.setSelectedIndex(0);
  }

/*
  Ova metoda postavlja inicijalno stanje za
  unos novog korisnika
*/
  private void insertState(){
//    maxID();
    mID="";
    dsUsers.emptyRow();
    dsSigning.emptyRow();
    insertData();
    btnSave.setEnabled(true);
    btnInsert.setEnabled(false);
    btnCancel.setEnabled(true);
    lstUsrTypes.setEnabled(false);
    tabsSingle.setSelectedIndex(0);
    tabsGroup.setSelectedIndex(0);
    tabsInsts.setSelectedIndex(0);
  }

  public void reloadCMB() { // dodato 28.02.00.
    cmCategs = new JDBLookupModel(dsCategs,"NAME",dsSingle,"USER_CTGR","ID");
    cmEduc = new JDBLookupModel(dsEduc,"NAME",dsSingle,"EDU_LVL","ID");
    cmMmbrType = new JDBLookupModel(dsMmbrType,"NAME",dsSingle,"MMBR_TYPE","ID");
//    cmGroup = new JDBLookupModel(dsGroup,"INST_NAME",dsSingle,"GRP_ID","USER_ID");
    cmBranch = new JDBLookupModel(dsBranch,"NAME", dsUsers,"BRANCH_ID","ID");
    cmLanguage = new JDBLookupModel(dsLanguage,"NAME", dsSingle,"LANGUAGE","ID");
    cboCategs.setComboModel(cmCategs);
    cboEduc.setComboModel(cmEduc);
    cboMmbrType.setComboModel(cmMmbrType);
//    cboGroup.setComboModel(cmGroup);
    cboBranch.setComboModel(cmBranch);
    cboLanguage.setComboModel(cmLanguage);
    cboGrpBranch.setComboModel(cmGrpBranch);
    cboInstBranch.setComboModel(cmInstBranch);
  }

// postavlja vrednost clanarine u odnosu na odabranu vrstu uclanjenja i kategoriju korinika
  public void setMemberFee(){
    CoolDate cd = new CoolDate(System.currentTimeMillis());
    usCtgr = cmCategs.getData();
    mbType = cmMmbrType.getData();
    String qry = "select cost from membership where user_ctgr = " + usCtgr + " and mmbr_type = " + mbType +" order by mdate desc";
    try{
      Statement stmt = dbConn.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(qry);
      if(rset.next()){
        tfSingleMmbrship.setText(rset.getObject(1).toString());
      }
      else
        tfSingleMmbrship.setText(Messages.get("NEWMEMBER_TFSINGLEMMBRSHIP_TEXT"));
      rset.close();
      qry = "select period from mmbr_types where id = " + mbType;
      rset = stmt.executeQuery(qry);
      if(rset.next()){
        int days = rset.getInt(1);
        CoolDate endDate = new CoolDate((cd.plus(days)).getTime());
        tfSingleUntil.setText(DBConnection.formatDate(endDate));
      }
      else
        tfSingleUntil.setText(Messages.get("NEWMEMBER_TFSINGLEUNTIL_TEXT"));
      rset.close();
      stmt.close();
      dbConn.getConnection().commit();
// print nema
    }catch(SQLException e){
      tfSingleMmbrship.setText(Messages.get("NEWMEMBER_TFSINGLEMMBRSHIP_TEXT"));
      e.printStackTrace();
    }

  }
  


}


