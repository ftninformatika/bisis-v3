
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

public class Institutions extends EscapeDialog {
  DBConnection dbConn;
  
  boolean closed = true;
  MasterLinkDescriptor mld;

  DataSet dsUsers, dsInsts;

  JTabbedPane tabsUsers = new JTabbedPane();
  XYLayout borderLayout4 = new XYLayout();
  JPanel panel4 = new JPanel();

  XYLayout xYLayout9 = new XYLayout();
  XYLayout xYLayout10 = new XYLayout();
  XYLayout xYLayout11 = new XYLayout();

  JButton btnSave = new JButton();
  JButton btnDelete = new JButton();
  JButton btnExit = new JButton();
  JButton btnNext = new JButton();
  JButton btnPrev = new JButton();
  JButton btnFirst = new JButton();
  JButton btnLast = new JButton();
  
  JPanel pUsers1 = new JPanel();
  JLabel lInstMmbrID = new JLabel();
  JLabel lInstDate = new JLabel();
  JDBLabel tfInstMmbrID = new JDBLabel();
  JDBLabel tfInstDate = new JDBLabel();
  JDBCheckBox  chkInstWarn = new JDBCheckBox();

  JPanel pUsers2 = new JPanel();
  JLabel lInstName = new JLabel();
  JLabel lInstAddress = new JLabel();
  JLabel lInstPlace = new JLabel();
  JLabel lInstZIP = new JLabel();
  JLabel lInstPhone = new JLabel();
  JLabel lInstFax = new JLabel();
  JLabel lInstEmail = new JLabel();
  JLabel lInstTelex = new JLabel();
  JDBTextField tfInstTelex = new JDBTextField();
  JDBTextField tfInstName = new JDBTextField();
  JDBTextField tfInstAddress = new JDBTextField();
  JDBTextField tfInstPlace = new JDBTextField();
  JDBTextField tfInstZIP = new JDBTextField();
  JDBTextField tfInstPhone = new JDBTextField();
  JDBTextField tfInstFax = new JDBTextField();
  JDBTextField tfInstEmail = new JDBTextField();

  JPanel pUsers3 = new JPanel();
  JLabel lInstContLastName = new JLabel();
  JLabel lInstContFirstName = new JLabel();
  JLabel lInstContEmail = new JLabel();
  JLabel lInstContPhone = new JLabel();
  JDBTextField tfInstContLastName = new JDBTextField();
  JDBTextField tfInstContFirstName = new JDBTextField();
  JDBTextField tfInstContEmail = new JDBTextField();
  JDBTextField tfInstContPhone = new JDBTextField();
  XYLayout xYLayout16 = new XYLayout();

  JDBTextField compulsoryInstsTF [] = { tfInstName ,tfInstAddress ,
                                       tfInstPlace ,tfInstZIP ,  tfInstContFirstName ,tfInstContLastName};

  String [] compInstNames = {Messages.get("INSTITUTIONS_COMPINSTNAMES_STR1"),
                             Messages.get("INSTITUTIONS_COMPINSTNAMES_STR2"),
                             Messages.get("INSTITUTIONS_COMPINSTNAMES_STR3"),
                             Messages.get("INSTITUTIONS_COMPINSTNAMES_STR4"),
                             Messages.get("INSTITUTIONS_COMPINSTNAMES_STR5"),
                             Messages.get("INSTITUTIONS_COMPINSTNAMES_STR6")};

  JDBTextField lengthInstTF [] = { tfInstName,tfInstAddress,tfInstPlace,
                                   tfInstPhone,tfInstFax,tfInstTelex,tfInstEmail,
                                   tfInstContLastName,tfInstContFirstName,tfInstContEmail,tfInstContPhone};

  int lengthInst[] = { 45,45,45,
                       15,15,15,30,
                       30,30,30,15};

  String [] lengthInstNames = {Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR1"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR2"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR3"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR4"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR5"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR6"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR7"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR8"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR9"),
                               Messages.get("INSTITUTIONS_LENGTHINSTNAMES_STR10"),
                              };

  JDBTextField typesInstTF [] ={tfInstZIP};
  int typesInstType [] = {0};
  String [] typesInstNames = {Messages.get("INSTITUTIONS_TYPESINSTNAMES_STR")};


  public Institutions(Frame frame, String title, boolean modal, DBConnection db) {
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

  public Institutions() {
    this(CircApplet.parent, "", false,null);
  }

  public Institutions(String title, DBConnection db, boolean modal) {
    this(CircApplet.parent, title, modal,db);
  }

  void jbInit() throws Exception {
// dodaje se ChangeListener koji obavestava o promenama nad podacima
    dsUsers = dbConn.getUsersModel();
    dsInsts = dbConn.getInstModel();
    mld = new MasterLinkDescriptor(dsUsers,new String[] {"ID"},new String[] {"USER_ID"});

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        if(closed){
//          dsInsts.setMasterLink(mld);
          dsUsers.first();
        }
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        dsInsts.removeMasterLink();
        dsUsers.removeFilter(); // dodato 21.02.00.        
        closed = true;
        setVisible(false);  // 01.11.00. stavljeno umesto dispose();
      }
    });


    tfInstMmbrID.setForeground(Color.blue);
    tfInstMmbrID.setDataSet(dsUsers);
    tfInstMmbrID.setColumn("ID");

    tfInstDate.setForeground(Color.blue);
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
    tfInstEmail.setDataSet(dsUsers);
    tfInstEmail.setColumn("EMAIL");

    tfInstTelex.setDataSet(dsInsts);
    tfInstTelex.setColumn("TELEX");
    tfInstName.setDataSet(dsInsts);
    tfInstName.setColumn("INST_NAME");
    tfInstFax.setDataSet(dsInsts);
    tfInstFax.setColumn("FAX");
    tfInstContLastName.setDataSet(dsInsts);
    tfInstContLastName.setColumn("CONT_LNAME");
    tfInstContFirstName.setDataSet(dsInsts);
    tfInstContFirstName.setColumn("CONT_FNAME");
    tfInstContEmail.setDataSet(dsInsts);
    tfInstContEmail.setColumn("CONT_EMAIL");
    tfInstContPhone.setDataSet(dsInsts);
    tfInstContPhone.setColumn("CONT_PHONE");

    borderLayout4.setWidth(530);
    borderLayout4.setHeight(429);
    btnSave.setText(Messages.get("INSTITUTIONS_BTNSAVE_TEXT"));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });

    btnDelete.setText(Messages.get("INSTITUTIONS_BTNDELETE_TEXT"));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });

    btnExit.setText(Messages.get("INSTITUTIONS_BTNEXIT_TEXT"));
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });

    btnNext.setText(">>");
    btnNext.setToolTipText(Messages.get("INSTITUTIONS_BTNNEXT_TOOLTIP"));
    btnNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNext_actionPerformed(e);
      }
    });

    btnPrev.setText("<<");
    btnPrev.setToolTipText(Messages.get("INSTITUTIONS_BTNPREV_TOOLTIP"));
    btnPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrev_actionPerformed(e);
      }
    });
    btnFirst.setText("|<");
    btnFirst.setToolTipText(Messages.get("INSTITUTIONS_BTNFIRST_TOOLTIP"));
    btnFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFirst_actionPerformed(e);
      }
    });
    btnLast.setText(">|");
    btnLast.setToolTipText(Messages.get("INSTITUTIONS_BTNLAST_TOOLTIP"));
    btnLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnLast_actionPerformed(e);
      }
    });

    panel4.setLayout(borderLayout4);
    lInstTelex.setText(Messages.get("INSTITUTIONS_LINSTTELEX_TEXT"));
    getContentPane().add(panel4);
    panel4.add(btnSave, new XYConstraints(11, 182, 80, 25));
    panel4.add(btnDelete, new XYConstraints(11, 232, 80, 25));
    panel4.add(btnExit, new XYConstraints(11, 265, 80, 25));
    panel4.add(btnNext, new XYConstraints(58, 72, 50, 25));
    panel4.add(btnPrev, new XYConstraints(7, 72, 50, 25));
    panel4.add(btnFirst, new XYConstraints(7, 37, 50, 25));
    panel4.add(btnLast, new XYConstraints(58, 37, 50, 25));

    tabsUsers.addTab(Messages.get("INSTITUTIONS_TABSGROUP_TAB1"),pUsers1);
    tabsUsers.addTab(Messages.get("INSTITUTIONS_TABSGROUP_TAB2"),pUsers2);
    tabsUsers.addTab(Messages.get("INSTITUTIONS_TABSGROUP_TAB3"),pUsers3);

    pUsers3.setLayout(xYLayout11);
    pUsers2.setLayout(xYLayout10);
    pUsers1.setLayout(xYLayout9);

    lInstMmbrID.setText(Messages.get("INSTITUTIONS_INSTMMBRID_TEXT"));
    lInstDate.setText(Messages.get("INSTITUTIONS_INSTDATE_TEXT"));
    chkInstWarn.setText(Messages.get("INSTITUTIONS_CHKINSTWARN_TEXT"));
    chkInstWarn.setDataSet(dsUsers);
    chkInstWarn.setColumn("WARNING");
    lInstName.setText(Messages.get("INSTITUTIONS_INSTNAME_TEXT"));
    lInstAddress.setText(Messages.get("INSTITUTIONS_INSTADDRESS_TEXT"));
    lInstPlace.setText(Messages.get("INSTITUTIONS_INSTPLACE_TEXT"));
    lInstZIP.setText(Messages.get("INSTITUTIONS_INSTZIP_TEXT"));
    lInstPhone.setText(Messages.get("INSTITUTIONS_INSTPHONE_TEXT"));
    lInstFax.setText(Messages.get("INSTITUTIONS_INSTFAX_TEXT"));
    lInstContLastName.setText(Messages.get("INSTITUTIONS_INSTCONTLASTNAME_TEXT"));
    lInstContFirstName.setText(Messages.get("INSTITUTIONS_INSTCONTFIRSTNAME_TEXT"));
    lInstContEmail.setText(Messages.get("INSTITUTIONS_INSTCONTEMAIL_TEXT"));
    lInstEmail.setText(Messages.get("INSTITUTIONS_INSTEMAIL_TEXT"));
    lInstContPhone.setText(Messages.get("INSTITUTIONS_INSTCONTPHONE_TEXT"));

    panel4.add(tabsUsers, new XYConstraints(110, 5, 415, 416));
    pUsers1.add(lInstMmbrID, new XYConstraints(22, 33, -1, -1));
    pUsers1.add(lInstDate, new XYConstraints(22, 78, -1, -1));
    pUsers1.add(tfInstMmbrID, new XYConstraints(158, 33, 120, -1));
    pUsers1.add(tfInstDate, new XYConstraints(158, 78, 120, -1));
    pUsers1.add(chkInstWarn, new XYConstraints(161, 123, -1, -1));

    pUsers2.add(lInstName, new XYConstraints(89, 18, -1, -1));
    pUsers2.add(lInstAddress, new XYConstraints(89, 62, -1, -1));
    pUsers2.add(lInstPlace, new XYConstraints(89, 106, -1, -1));
    pUsers2.add(lInstZIP, new XYConstraints(89, 150, -1, -1));
    pUsers2.add(lInstPhone, new XYConstraints(89, 195, -1, -1));
    pUsers2.add(lInstFax, new XYConstraints(89, 245, -1, -1));
    pUsers2.add(tfInstName, new XYConstraints(174, 18, 120, -1));
    pUsers2.add(tfInstAddress, new XYConstraints(174, 62, 120, -1));
    pUsers2.add(tfInstPlace, new XYConstraints(174, 106, 120, -1));
    pUsers2.add(tfInstZIP, new XYConstraints(174, 150, 100, -1));
    pUsers2.add(tfInstPhone, new XYConstraints(174, 195, 100, -1));
    pUsers2.add(tfInstFax, new XYConstraints(174, 245, 100, -1));
    pUsers2.add(tfInstEmail, new XYConstraints(174, 324, 100, -1));
    pUsers2.add(lInstEmail, new XYConstraints(89, 324, -1, -1));
    pUsers2.add(lInstTelex, new XYConstraints(89, 289, 61, -1));
    pUsers2.add(tfInstTelex, new XYConstraints(174, 289, 100, -1));

    pUsers3.add(lInstContLastName, new XYConstraints(86, 57, -1, -1));
    pUsers3.add(lInstContFirstName, new XYConstraints(86, 114, -1, -1));
    pUsers3.add(lInstContEmail, new XYConstraints(86, 183, -1, -1));
    pUsers3.add(tfInstContLastName, new XYConstraints(186, 57, 120, -1));
    pUsers3.add(tfInstContFirstName, new XYConstraints(186, 114, 120, -1));
    pUsers3.add(tfInstContEmail, new XYConstraints(186, 183, 120, -1));
    pUsers3.add(lInstContPhone, new XYConstraints(86, 243, -1, -1));
    pUsers3.add(tfInstContPhone, new XYConstraints(186, 242, 120, -1));
//    dsUsers.first();
  }

// Save
  void btnSave_actionPerformed(ActionEvent e) {
    if(canWrite() && !(checkLength()) && !(checkTypes())){
      dsUsers.saveRow();
      if (dsUsers.hasError() && dsInsts.hasError()) {
        JOptionPane.showMessageDialog(null,Messages.get("INSTITUTIONS_OPTIONPANE_MSG1"),Messages.get("INSTITUTIONS_OPTIONPANE_MSG2"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

// Delete
  void btnDelete_actionPerformed(ActionEvent e) {
    dsUsers.deleteRow();
  }

// Exit
  void btnExit_actionPerformed(ActionEvent e) {
    dsInsts.removeMasterLink(); // TANJA - 31.10.00.
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

  private void zeroMembers(boolean zero){
    chkInstWarn.setEnabled(!zero);
    tfInstMmbrID.setEnabled(!zero);
    tfInstDate.setEnabled(!zero);
    tfInstAddress.setEnabled(!zero);
    tfInstPlace.setEnabled(!zero);
    tfInstZIP.setEnabled(!zero);
    tfInstPhone.setEnabled(!zero);
    tfInstName.setEnabled(!zero);
    tfInstFax.setEnabled(!zero);
    tfInstEmail.setEnabled(!zero);
    tfInstContLastName.setEnabled(!zero);
    tfInstContFirstName.setEnabled(!zero);
    tfInstContEmail.setEnabled(!zero);
    tfInstContPhone.setEnabled(!zero);
  }

  public void setVisible(boolean vis){
    closed = true;
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
    closed = false;
    dsInsts.setMasterLink(mld);
    dsUsers.setFilter(" USER_TYPE = 3"); // dodato 21.02.00.
    
    dsUsers.getMin(); // dodato 24.02.00.
    dsUsers.getMax(); // dodato 24.02.00.
    dsUsers.setCurrentRow(new String[]{"ID"}, new String[]{id});
    super.setVisible(vis);
  }

  private boolean canWrite(){
    boolean hasText = true;
    int i = 0;
    while(hasText && i < compulsoryInstsTF.length){
      hasText = !(compulsoryInstsTF[i].getText().equals(""));
      i++;
    }
    if(!hasText) {
      JOptionPane.showMessageDialog(null,Messages.get("INSTITUTIONS_OPTIONPANE_MSG3")+" "+compInstNames[i-1]+" "+Messages.get("INSTITUTIONS_OPTIONPANE_MSG4"),Messages.get("INSTITUTIONS_OPTIONPANE_MSG5"),JOptionPane.ERROR_MESSAGE);
      compulsoryInstsTF[i].requestFocus();
    }
    return hasText;
  }

  private boolean checkLength(){
    boolean hasLength = false;
    int i = 0;
    while(!hasLength && i < lengthInstTF.length){
      hasLength = (lengthInstTF[i].getText().length() > lengthInst[i]);
      i++;
    }
    if(hasLength) {
      JOptionPane.showMessageDialog(null,Messages.get("INSTITUTIONS_OPTIONPANE_MSG6")+" "+lengthInstNames[i-1],Messages.get("INSTITUTIONS_OPTIONPANE_MSG7"),JOptionPane.ERROR_MESSAGE);
      lengthInstTF[i-1].requestFocus();
    }
    return hasLength;
  }

  private boolean checkTypes(){
    boolean hasTypes = false;
    int i = 0;
    String message="";
    while(!hasTypes && i < typesInstTF.length){
      if (!((typesInstTF[i].getText()).equals(""))) {
        switch (typesInstType[i]) {
          case 0:  // integer
                  try {
                    int ret=(int) Double.parseDouble(typesInstTF[i].getText());
                  }
                  catch (NumberFormatException ex){
                    message=Messages.get("INSTITUTIONS_MESSAGE_MSG1");
                    hasTypes=true;
                  }
                  break;
          case 1:  // date

                    String s=typesInstTF[i].getText();
                    boolean dig=true;
                    for (int j=0; dig&&(j<s.length()); j++){
                       dig=java.lang.Character.isDigit(s.charAt(j));
                       if (!(dig) && (s.charAt(j)=='.'))
                        dig=true;
                    }
                    if (!dig) {
                      message=Messages.get("INSTITUTIONS_MESSAGE_MSG2");
                      hasTypes=true;
                    }

                  break;

          case 2: // double
                  try {
                    Double ret=new Double(Double.parseDouble(typesInstTF[i].getText()));
                  }
                  catch (NumberFormatException ex){
                    message=Messages.get("INSTITUTIONS_MESSAGE_MSG3");
                    hasTypes=true;
                  }
                  break;
        }
      }
      i++;
    }
    if(hasTypes) {
      JOptionPane.showMessageDialog(null,message+typesInstNames[i-1],Messages.get("INSTITUTIONS_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
      typesInstTF[i-1].requestFocus();
    }
    return hasTypes;
  }

}

