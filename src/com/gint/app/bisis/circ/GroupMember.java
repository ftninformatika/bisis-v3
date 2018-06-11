//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

public class GroupMember extends EscapeDialog {
  private DBConnection dbConn;

  private boolean closed = true;
  private static final String constTitle = new String(Messages.get("GROUPMEMBER_CONSTTITLE_TEXT"));

  private DataSet dsUsers, dsGroup;
  private MasterLinkDescriptor mld;

  JPanel panel3 = new JPanel();
  XYLayout borderLayout1 = new XYLayout();

  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout8 = new XYLayout();
  JTabbedPane tabsGroup = new JTabbedPane();
  JPanel pGroup1 = new JPanel();
  JLabel lGrpMmbrID = new JLabel();
  JLabel lGrpDate = new JLabel();
  JDBLabel tfGrpMmbrID = new JDBLabel();
  JDBLabel tfGrpDate = new JDBLabel();

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

  JLabel lGrpEmail = new JLabel();
  JDBTextField tfGrpEmail = new JDBTextField();

  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();
  JButton btnExit = new JButton();
  JButton btnNext = new JButton();
  JButton btnPrev = new JButton();
  JButton btnFirst = new JButton();
  JButton btnLast = new JButton();

  JDBTextField compulsoryGroupTF [] = {  tfGrpName, tfGrpAddress ,
                                        tfGrpPlace , tfGrpZIP };
  String [] compGroupNames = {Messages.get("GROUPMEMBER_COMPGROUPNAMES_STR1"),
                              Messages.get("GROUPMEMBER_COMPGROUPNAMES_STR2"),
                              Messages.get("GROUPMEMBER_COMPGROUPNAMES_STR3"),
                              Messages.get("GROUPMEMBER_COMPGROUPNAMES_STR4")};

  JDBTextField lengthGroupTF [] = { tfGrpName,tfGrpAddress,tfGrpPlace,
                                    tfGrpPhone,tfGrpFax,tfGrpTelex,tfGrpEmail,
                                    tfGrpSecAddress,tfGrpSecPlace,tfGrpSecPhone,
                                    tfGrpContLastName,tfGrpContFirstName,tfGrpContEmail};
  int lengthGroup[] = { 45,45,45,
                        15,15,15,30,
                        45,45,15,
                        30,30,30};

  String [] lengthGroupNames = {Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR1"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR2"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR3"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR4"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR5"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR6"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR7"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR8"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR9"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR10"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR11"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR12"),
                                Messages.get("GROUPMEMBER_LENGTHGROUPNAMES_STR13")};

  JDBTextField typesGroupTF [] ={tfGrpZIP,tfGrpSecZIP};
  int typesGroupType [] = {0,0};
  String [] typesGroupNames = {Messages.get("GROUPMEMBER_TYPESGROUPNAMES_STR1"),
                               Messages.get("GROUPMEMBER_TYPESGROUPNAMES_STR2")
                              };


  public GroupMember(Frame frame, String title, boolean modal, DBConnection db) {
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

  public GroupMember() {
    this(CircApplet.parent, "", false,null);
  }

  public GroupMember(String title, DBConnection db, boolean modal) {
    this(CircApplet.parent, title, modal,db);
  }

  public GroupMember(DBConnection db) {
    this(CircApplet.parent, constTitle, false,db);
  }


  void jbInit() throws Exception {
    dsGroup = dbConn.getGroupModel();
    dsUsers = dbConn.getUsersModel();
    mld = new MasterLinkDescriptor(dsUsers,new String[] {"ID"},new String[] {"USER_ID"});

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if (closed){
//          dsGroup.setMasterLink(mld);
          dsUsers.first();
        }
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        dsGroup.removeMasterLink();
        dsUsers.removeFilter(); // dodato 24.02.00.
        closed = true;
        setVisible(false); //01.11.00. stavljeno umesto dispose();

      }
    });

    tfGrpEmail.setDataSet(dsUsers);
    tfGrpEmail.setColumn("EMAIL");

    tfGrpName.setDataSet(dsGroup);
    tfGrpName.setColumn("INST_NAME");
    tfGrpFax.setDataSet(dsGroup);
    tfGrpFax.setColumn("FAX");
    tfGrpTelex.setDataSet(dsGroup);
    tfGrpTelex.setColumn("TELEX");
    tfGrpSecAddress.setDataSet(dsGroup);
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

    tfGrpMmbrID.setForeground(Color.blue);
    tfGrpMmbrID.setDataSet(dsUsers);
    tfGrpMmbrID.setColumn("ID");

    tfGrpDate.setForeground(Color.blue);
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

    borderLayout1.setHeight(415);
    borderLayout1.setWidth(530);
    btnSave.setText(Messages.get("GROUPMEMBER_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });

    btnDelete.setText(Messages.get("GROUPMEMBER_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });

    btnExit.setText(Messages.get("GROUPMEMBER_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });

    btnNext.setText(">>");
    btnNext.setToolTipText(Messages.get("GROUPMEMBER_BTNNEXT_TOOLTIP"));
    btnNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNext_actionPerformed(e);
      }
    });

    btnPrev.setText("<<");
    btnPrev.setToolTipText(Messages.get("GROUPMEMBER_BTNPREV_TOOLTIP"));
    btnPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrev_actionPerformed(e);
      }
    });
    btnFirst.setText("|<");
    btnFirst.setToolTipText(Messages.get("GROUPMEMBER_BTNFIRST_TOOLTIP"));
    btnFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFirst_actionPerformed(e);
      }
    });
    btnLast.setText(">|");
    btnLast.setToolTipText(Messages.get("GROUPMEMBER_BTNLAST_TOOLTIP"));
    btnLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnLast_actionPerformed(e);
      }
    });

    tabsGroup.addTab(Messages.get("GROUPMEMBER_TABSGROUP_TAB1"),pGroup1);
    tabsGroup.addTab(Messages.get("GROUPMEMBER_TABSGROUP_TAB2"),pGroup2);
    tabsGroup.addTab(Messages.get("GROUPMEMBER_TABSGROUP_TAB3"),pGroup3);
    tabsGroup.addTab(Messages.get("GROUPMEMBER_TABSGROUP_TAB4"),pGroup4);

    pGroup1.setLayout(xYLayout4);
    pGroup2.setLayout(xYLayout6);
    pGroup3.setLayout(xYLayout7);
    pGroup4.setLayout(xYLayout8);

    lGrpMmbrID.setText(Messages.get("GROUPMEMBER_LGRPMMBRID_TEXT"));
    lGrpDate.setText(Messages.get("GROUPMEMBER_LGRPDATE_TEXT"));
    lGrpName.setText(Messages.get("GROUPMEMBER_LGRPNAME_TEXT"));
    lGrpAddress.setText(Messages.get("GROUPMEMBER_LGRPADDRESS_TEXT"));
    lGrpPlace.setText(Messages.get("GROUPMEMBER_LGRPPLACE_TEXT"));
    lGrpZIP.setText(Messages.get("GROUPMEMBER_LGRPZIP_TEXT"));
    lGrpPhone.setText(Messages.get("GROUPMEMBER_LGRPPHONE_TEXT"));
    lGrpFax.setText(Messages.get("GROUPMEMBER_LGRPFAX_TEXT"));
    lGrpTelex.setText(Messages.get("GROUPMEMBER_LGRPTELEX_TEXT"));
    lGrpSecAddress.setText(Messages.get("GROUPMEMBER_LGRPSECADDRESS_TEXT"));
    lGrpSecPlace.setText(Messages.get("GROUPMEMBER_LGRPSECPLACE_TEXT"));
    lGrpSecZIP.setText(Messages.get("GROUPMEMBER_LGRPSECZIP_TEXT"));
    lGrpSecPhone.setText(Messages.get("GROUPMEMBER_LGRPSECPHONE_TEXT"));
    lGrpContLastName.setText(Messages.get("GROUPMEMBER_LGRPCONTLASTNAME_TEXT"));
    lGrpContFirstName.setText(Messages.get("GROUPMEMBER_LGRPCONTLASTNAME_TEXT"));
    lGrpContEmail.setText(Messages.get("GROUPMEMBER_LGRPCONTEMAIL_TEXT"));

    panel3.setLayout(borderLayout1);
    lGrpEmail.setText(Messages.get("GROUPMEMBER_LGRPCONTEMAIL_TEXT"));
    getContentPane().add(panel3);
    panel3.add(tabsGroup, new XYConstraints(110, 5, 415, 403));
    xYLayout5.setWidth(530);
    xYLayout5.setHeight(355);

    pGroup1.add(lGrpMmbrID, new XYConstraints(22, 33, -1, -1));
    pGroup1.add(lGrpDate, new XYConstraints(22, 78, -1, -1));
    pGroup1.add(tfGrpMmbrID, new XYConstraints(158, 33, 120, -1));
    pGroup1.add(tfGrpDate, new XYConstraints(158, 78, 120, -1));
//    pGroup1.add(chkGrpWarn, new XYConstraints(161, 123, -1, -1));
    pGroup2.add(tfGrpName, new XYConstraints(174, 18, 120, -1));
    pGroup2.add(tfGrpAddress, new XYConstraints(174, 59, 120, -1));
    pGroup2.add(tfGrpPlace, new XYConstraints(174, 100, 120, -1));
    pGroup2.add(tfGrpZIP, new XYConstraints(174, 142, 100, -1));
    pGroup2.add(tfGrpPhone, new XYConstraints(174, 183, 100, -1));
    pGroup2.add(tfGrpFax, new XYConstraints(174, 224, 100, -1));
    pGroup2.add(tfGrpTelex, new XYConstraints(174, 265, 100, -1));
    pGroup2.add(lGrpName, new XYConstraints(54, 18, -1, -1));
    pGroup2.add(lGrpAddress, new XYConstraints(54, 62, -1, -1));
    pGroup2.add(lGrpPlace, new XYConstraints(54, 102, -1, -1));
    pGroup2.add(lGrpZIP, new XYConstraints(54, 144, -1, -1));
    pGroup2.add(lGrpPhone, new XYConstraints(54, 185, -1, -1));
    pGroup2.add(lGrpFax, new XYConstraints(54, 226, 37, -1));
    pGroup2.add(lGrpTelex, new XYConstraints(54, 267, -1, -1));
    pGroup2.add(tfGrpEmail, new XYConstraints(174, 306, 100, 20));
    pGroup2.add(lGrpEmail, new XYConstraints(54, 309, -1, -1));
    pGroup3.add(lGrpSecAddress, new XYConstraints(64, 24, -1, -1));
    pGroup3.add(lGrpSecPlace, new XYConstraints(64, 81, -1, -1));
    pGroup3.add(lGrpSecZIP, new XYConstraints(64, 137, -1, -1));
    pGroup3.add(lGrpSecPhone, new XYConstraints(64, 194, -1, -1));
    pGroup3.add(tfGrpSecAddress, new XYConstraints(197, 24, 120, -1));
    pGroup3.add(tfGrpSecPlace, new XYConstraints(197, 81, 120, -1));
    pGroup3.add(tfGrpSecZIP, new XYConstraints(197, 137, 100, -1));
    pGroup3.add(tfGrpSecPhone, new XYConstraints(197, 194, 100, -1));
    pGroup4.add(lGrpContLastName, new XYConstraints(86, 57, -1, -1));
    pGroup4.add(lGrpContFirstName, new XYConstraints(86, 114, -1, -1));
    pGroup4.add(lGrpContEmail, new XYConstraints(86, 183, -1, -1));
    pGroup4.add(tfGrpContLastName, new XYConstraints(186, 57, 120, -1));
    pGroup4.add(tfGrpContFirstName, new XYConstraints(186, 114, 120, -1));
    pGroup4.add(tfGrpContEmail, new XYConstraints(186, 183, 120, -1));

    panel3.add(btnSave, new XYConstraints(12, 170, 80, 25));
//    panel3.add(btnDelete, new XYConstraints(12, 220, 80, 25));
    panel3.add(btnExit, new XYConstraints(12, 220, 80, 25));
//    panel3.add(btnNext, new XYConstraints(56, 63, 50, 25));
//    panel3.add(btnPrev, new XYConstraints(5, 63, 50, 25));
//    panel3.add(btnFirst, new XYConstraints(5, 28, 50, 25));
//    panel3.add(btnLast, new XYConstraints(56, 28, 50, 25));
//    dsUsers.first();
  }


// Save
  void btnSave_actionPerformed(ActionEvent e) {
    if(canWrite() && !(checkLength()) && !(checkTypes())){
      System.out.println("<GroupMember> Save...");
      dsUsers.saveRow();
      if (dsUsers.hasError() && dsGroup.hasError()) {
        JOptionPane.showMessageDialog(null,Messages.get("GROUPMEMBER_OPTIONPANE_MSG1"),Messages.get("GROUPMEMBER_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

// Delete
  void btnDelete_actionPerformed(ActionEvent e) {
    dsUsers.deleteRow();
  }

// Exit
  void btnExit_actionPerformed(ActionEvent e) {
    dsGroup.removeMasterLink(); // TANJA - 31.10.00.
    dsUsers.removeFilter(); // dodato 21.02.00.
    setVisible(false);  // 01.11.00. stavljeno umesto dispose();
  }

// Next
  void btnNext_actionPerformed(ActionEvent e) {
    dsUsers.next();
  }

// Prev
  void btnPrev_actionPerformed(ActionEvent e) {
    dsUsers.prev();
  }

// First
  void btnFirst_actionPerformed(ActionEvent e) {
    dsUsers.first();
  }

// Last
  void btnLast_actionPerformed(ActionEvent e) {
    dsUsers.last();
  }

  public void setVisible(boolean vis){
    closed = true;
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
    closed = false;

    dsGroup.setMasterLink(mld);
    dsUsers.setFilter(" USER_TYPE = 2"); // dodato 21.02.00.
    dsUsers.getMin(); // dodato 24.02.00.
    dsUsers.getMax(); // dodato 24.02.00.
    dsUsers.setCurrentRow(new String[]{"ID"}, new String[]{id});
    super.setVisible(vis);
  }

  private boolean canWrite(){
    boolean hasText = true;
    int i = 0;
    while(hasText && i < compulsoryGroupTF.length){
      hasText = !(compulsoryGroupTF[i].getText().equals(""));
      i++;
    }
    if(!hasText) {
      JOptionPane.showMessageDialog(null,Messages.get("GROUPMEMBER_OPTIONPANE_MSG3")+compGroupNames[i-1]+ Messages.get("GROUPMEMBER_OPTIONPANE_MSG4"),Messages.get("GROUPMEMBER_OPTIONPANE_MSG5"),JOptionPane.ERROR_MESSAGE);
      compulsoryGroupTF[i-1].requestFocus();
    }
    return hasText;
  }

  private boolean checkLength(){
    boolean hasLength = false;
    int i = 0;
    while(!hasLength && i < lengthGroupTF.length){
      hasLength = (lengthGroupTF[i].getText().length() > lengthGroup[i]);
      i++;
    }
    if(hasLength) {
      JOptionPane.showMessageDialog(null,Messages.get("GROUPMEMBER_OPTIONPANE_MSG6")+lengthGroupNames[i-1],Messages.get("GROUPMEMBER_OPTIONPANE_MSG7"),JOptionPane.ERROR_MESSAGE);
      lengthGroupTF[i-1].requestFocus();
    }
    return hasLength;
  }

  private boolean checkTypes(){
    boolean hasTypes = false;
    int i = 0;
    String message="";
    while(!hasTypes && i < typesGroupTF.length){
      if (!((typesGroupTF[i].getText()).equals(""))) {
        switch (typesGroupType[i]) {
          case 0:  // integer
                  try {
                    int ret=(int) Double.parseDouble(typesGroupTF[i].getText());
                  }
                  catch (NumberFormatException ex){
                    message=Messages.get("GROUPMEMBER_MESSAGE_MSG1");
                    hasTypes=true;
                  }
                  break;
          case 1:  // date

                    String s=typesGroupTF[i].getText();
                    boolean dig=true;
                    for (int j=0; dig&&(j<s.length()); j++){
                       dig=java.lang.Character.isDigit(s.charAt(j));
                       if (!(dig) && (s.charAt(j)=='.'))
                        dig=true;
                    }
                    if (!dig) {
                      message=Messages.get("GROUPMEMBER_MESSAGE_MSG2");
                      hasTypes=true;
                    }

                  break;

          case 2: // double
                  try {
                    Double ret=new Double(Double.parseDouble(typesGroupTF[i].getText()));
                  }
                  catch (NumberFormatException ex){
                    message=Messages.get("GROUPMEMBER_MESSAGE_MSG3");
                    hasTypes=true;
                  }
                  break;
        }
      }
      i++;
    }
    if(hasTypes) {
      JOptionPane.showMessageDialog(null,message+typesGroupNames[i-1],Messages.get("GROUPMEMBER_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
      typesGroupTF[i-1].requestFocus();
    }
    return hasTypes;
  }

}

