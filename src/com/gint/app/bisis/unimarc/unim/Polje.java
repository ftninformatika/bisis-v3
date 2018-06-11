package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;


/**Klasa koja pretstavlja ekransku formu Polje.
  @version 1.0
*/
public class Polje extends JDialog {

  public static final int UNDEFINED = 0;
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  /** Labela 'Sifra' */
  JLabel jLabel1 = new JLabel();
   /** Labela 'Naziv' */
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  /** CheckBox za proveru da li se dato polje moze pojavljivati vise puta u okviru jednog zapisa*/
  JCheckBox jCheckBox1 = new JCheckBox();
  /** CheckBox za proveru da li je dato polje obavezno*/
  JCheckBox jCheckBox2 = new JCheckBox();
  /** CheckBox za proveru da se dato polje moze javiti kao sadrzaj nekog drugog polja*/
  JCheckBox jCheckBox3 = new JCheckBox();
  /** Tekst polje za prikaz/unos sifre polja*/
  JTextField jTextField1 = new JTextField();
  /** Polje za tekst za prikaz/unos naziva polja*/
  JTextArea jTextArea1 = new JTextArea();
  JScrollPane jScrollPane1 = new JScrollPane();
  JButton jButton1 = new JButton();
  JPanel jPanel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JButton jButton2 = new JButton();
  /** ComboBox za prikaz prvih indikatora datog polja*/
  JComboBox jComboBox1 = new JComboBox();
  JScrollPane jScrollPane2 = new JScrollPane();
  Border border3;
  TitledBorder titledBorder2;
  Border border4;
  JButton jButton3 = new JButton();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JScrollPane jScrollPane3 = new JScrollPane();
  /** ComboBox za prikaz drugih indikatora datog polja*/
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel9 = new JLabel();
  Border border5;
  TitledBorder titledBorder3;
  Border border6;
  JTextArea jTextArea2 = new JTextArea();
  JTextArea jTextArea3 = new JTextArea();
  /** Dugme kojim se vrsi upis podataka u ekransku formu*/
  JButton jButton10 = new JButton();
  /** Dugme kojim se vrsi izmena podataka iz ekranske forme*/
  JButton jButton11 = new JButton();
  /** Dugme kojim se vrsi brisanje date pojave iz ekranske forme*/
  JButton jButton12 = new JButton();
  /** Dugme kojim se vrsi azuriranje baze podataka*/
  JButton jButton13 = new JButton();
   /** Dugme kojim se vrsi odustanak od prethodne akcije*/
  JButton jButton14 = new JButton();
   /** Dugme za izlaz iz ove ekranske forme*/
  JButton jButton15 = new JButton();
   /** Dugme za prikaz prve pojave iz date tabele baze podataka*/
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
   /** Dugme kojim se pregled pojava date tabele u bazi*/
  JButton jButton8 = new JButton();
  JButton jButton9 = new JButton();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  Border border7;
  Border border8;

  Connection conn;
  DBMPolje dbmP;
  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DPotpolja dpp;
  DPindikatora dpi;
  DDindikatora ddi;
  DBMDPPolje dbmpp;
  DBMDPInd dbmpi;
  DBMDDInd dbmdi;
  boolean defSubfield=false;
  boolean dfPi=false;
  boolean dfDi=false;

  int mod = UNDEFINED;
  int row = 0;
  boolean trans = false;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  boolean isEmpty=false;

  /** Konstruktor
   @param conn Prosledjuje konekciju sa bazom
   */
   public Polje(Connection conn) {
    try  {
      this.conn=conn;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /** Inicijalna metoda za podesavanje osobina svih komponenti na ekranskoj formi */
  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,Messages.get("POLJE_TITLEDBORDER1_FIELD"));
    border2 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),Messages.get("POLJE_LINEBORDER_FIELDS")),BorderFactory.createEmptyBorder(3,3,3,3));
    border3 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border3,Messages.get("POLJE_TITLEDBORDER2_FIRSTINDICATOR"));
    border4 = BorderFactory.createCompoundBorder(titledBorder2,BorderFactory.createEmptyBorder(3,3,3,3));
    border5 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder3 = new TitledBorder(border5,Messages.get("POLJE_TITLEDBORDER3_SECONDINDICATOR"));
    border6 = BorderFactory.createCompoundBorder(titledBorder3,BorderFactory.createEmptyBorder(3,3,3,3));
    border7 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    border8 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),BorderFactory.createEmptyBorder(2,2,2,2));
    jScrollPane1.setMinimumSize(new Dimension(260, 70));
    jScrollPane1.setPreferredSize(new Dimension(260, 70));
    jTextArea1.setMargin(new Insets(0, 2, 0, 2));
    jTextArea1.setPreferredSize(new Dimension(250, 119));
    jTextField1.setMinimumSize(new Dimension(42, 21));
    jTextField1.setPreferredSize(new Dimension(42, 21));
    jTextField1.setMargin(new Insets(0, 2, 0, 2));
    jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
    jCheckBox3.setText(Messages.get("POLJE_CHECKBOX_REPEATED"));
    jCheckBox2.setText(Messages.get("POLJE_CHECKBOX_MANDATORY"));
    jCheckBox1.setText(Messages.get("POLJE_CHECKBOX_SECONDARY"));
    jLabel3.setText(Messages.get("POLJE_LABEL_SUBFIELDDEF"));
    jLabel2.setText(Messages.get("POLJE_LABEL_NAME"));
    jLabel1.setText(Messages.get("POLJE_LABEL_CODE"));
    this.setTitle(Messages.get("POLJE_DIALOGTITLE_FIELD"));
    this.getContentPane().add(jPanel8);
    jPanel8.setLayout(xYLayout1);
    jButton1.setFont(new java.awt.Font("Dialog", 1, 12));
    jButton1.setMinimumSize(new Dimension(80, 21));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("POLJE_BUTTON1_DOTS"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
       jButton1_actionPerformed(e);
      }
    });
    jPanel1.setLayout(xYLayout2);
    jPanel1.setBorder(border2);
    jPanel1.setMinimumSize(new Dimension(666, 132));
    jPanel1.setPreferredSize(new Dimension(666, 139));
    jPanel2.setLayout(xYLayout6);
    jLabel4.setText(Messages.get("POLJE_LABEL_DESCRIPTION1"));
    jLabel5.setText(Messages.get("POLJE_LABEL_DEFAULTVALUE1"));
    jLabel6.setText(Messages.get("POLJE_LABEL_DEFINITION1"));
    jButton2.setFont(new java.awt.Font("Dialog", 1, 12));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("POLJE_BUTTON2_DOTS"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel2.setBorder(border4);
    jPanel3.setLayout(xYLayout7);
    jButton3.setFont(new java.awt.Font("Dialog", 1, 12));
    jButton3.setPreferredSize(new Dimension(80, 21));
    jButton3.setText(Messages.get("POLJE_BUTTON3_DOTS"));
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jLabel7.setText(Messages.get("POLJE_LABEL_DESCRIPTION2"));
    jLabel8.setText(Messages.get("POLJE_LABEL_DEFAULTVALUE2"));
    jLabel9.setText(Messages.get("POLJE_LABEL_DEFINITION2"));
    jPanel3.setBorder(border6);
    jComboBox1.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jScrollPane2.setPreferredSize(new Dimension(270, 90));
    jTextArea3.setPreferredSize(new Dimension(250, 187));
    jTextArea3.setMargin(new Insets(0, 2, 0, 2));
    jScrollPane3.setPreferredSize(new Dimension(270, 90));
    jTextArea2.setPreferredSize(new Dimension(250, 187));
    jTextArea2.setMargin(new Insets(0, 2, 0, 2));
    jButton10.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton10.setPreferredSize(new Dimension(80, 21));
    jButton10.setText(Messages.get("POLJE_BUTTON_INPUT"));
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton11.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton11.setPreferredSize(new Dimension(80, 21));
    jButton11.setText(Messages.get("POLJE_BUTTON_CHANGE"));
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton11_actionPerformed(e);
      }
    });
    jButton12.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton12.setPreferredSize(new Dimension(80, 21));
    jButton12.setText(Messages.get("POLJE_BUTTON_CLEAR"));
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton12_actionPerformed(e);
      }
    });
    jButton13.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton13.setPreferredSize(new Dimension(80, 21));
    jButton13.setText(Messages.get("POLJE_BUTTON_OK"));
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton13_actionPerformed(e);
      }
    });
    jButton14.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton14.setPreferredSize(new Dimension(80, 21));
    jButton14.setText(Messages.get("POLJE_BUTTON_CANCEL"));
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton14_actionPerformed(e);
      }
    });
    jButton15.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton15.setPreferredSize(new Dimension(80, 21));
    jButton15.setText(Messages.get("POLJE_BUTTON_EXIT"));
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton15_actionPerformed(e);
      }
    });
    jButton4.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton4.setPreferredSize(new Dimension(80, 21));
    jButton4.setText(Messages.get("POLJE_BUTTON_FIRST"));
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton5.setPreferredSize(new Dimension(80, 21));
    jButton5.setText(Messages.get("POLJE_BUTTON_PREVIOUS"));
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton6.setPreferredSize(new Dimension(80, 21));
    jButton6.setText(Messages.get("POLJE_BUTTON_NEXT"));
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton7.setPreferredSize(new Dimension(80, 21));
    jButton7.setText(Messages.get("POLJE_BUTTON_LAST"));
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton8.setPreferredSize(new Dimension(80, 21));
    jButton8.setText(Messages.get("POLJE_BUTTON_VIEW"));
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jButton9.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton9.setPreferredSize(new Dimension(80, 21));
    jButton9.setText(Messages.get("POLJE_BUTTON_HELP"));
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    jPanel5.setLayout(xYLayout3);
    jPanel6.setLayout(xYLayout5);
    jPanel7.setLayout(xYLayout4);
    jPanel6.setBorder(border7);
    jPanel7.setBorder(border8);
    xYLayout1.setHeight(400);
    xYLayout1.setWidth(650);
    jPanel8.add(jPanel2, new XYConstraints(5, 155, 322, 152));
    jPanel2.add(jButton2, new XYConstraints(100, 60, 75, -1));
    jPanel2.add(jLabel6, new XYConstraints(5, 60, -1, -1));
    jPanel2.add(jLabel4, new XYConstraints(5, 0, -1, -1));
    jPanel2.add(jComboBox1, new XYConstraints(170, 95, 60, -1));
    jPanel2.add(jScrollPane2, new XYConstraints(84, 0, 200, 50));
    jPanel2.add(jLabel5, new XYConstraints(5, 95, -1, -1));
    jScrollPane2.getViewport().add(jTextArea2, null);
    jPanel8.add(jPanel3, new XYConstraints(328, 155, 322, 152));
    jPanel3.add(jButton3, new XYConstraints(100, 60, 75, -1));
    jPanel3.add(jLabel7, new XYConstraints(5, 0, -1, -1));
    jPanel3.add(jLabel8, new XYConstraints(5, 95, -1, -1));
    jPanel3.add(jScrollPane3, new XYConstraints(84, 0, 200, 50));
    jScrollPane3.getViewport().add(jTextArea3, null);
    jPanel3.add(jComboBox2, new XYConstraints(170, 95, 60, -1));
    jPanel3.add(jLabel9, new XYConstraints(5, 60, -1, -1));
    jPanel8.add(jPanel1, new XYConstraints(7, 10, 630, -1));
    jPanel1.add(jTextField1, new XYConstraints(47, 0, -1, -1));
    jPanel1.add(jLabel2, new XYConstraints(5, 31, -1, -1));
    jPanel1.add(jScrollPane1, new XYConstraints(47, 31, -1, -1));
    jPanel1.add(jCheckBox2, new XYConstraints(344, 2, -1, -1));
    jPanel1.add(jLabel1, new XYConstraints(5, 2, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(510, 74, -1, -1));
    jPanel1.add(jCheckBox1, new XYConstraints(344, 37, -1, -1));
    jPanel1.add(jCheckBox3, new XYConstraints(343, 72, -1, -1));
    jPanel1.add(jLabel3, new XYConstraints(495, 50, -1, -1));
    jPanel8.add(jPanel5, new XYConstraints(5, 320, -1, -1));
    jPanel5.add(jButton6, new XYConstraints(229, 3, -1, -1));
    jPanel5.add(jButton9, new XYConstraints(565, 3, -1, -1));
    jPanel5.add(jPanel7, new XYConstraints(336, 30, -1, -1));
    jPanel7.add(jButton14, new XYConstraints(115, 1, -1, -1));
    jPanel7.add(jButton13, new XYConstraints(2, 1, -1, -1));
    jPanel5.add(jButton15, new XYConstraints(565, 36, -1, -1));
    jPanel5.add(jButton7, new XYConstraints(341, 3, -1, -1));
    jPanel5.add(jButton5, new XYConstraints(117, 3, -1, -1));
    jPanel5.add(jButton8, new XYConstraints(453, 3, -1, -1));
    jPanel5.add(jButton4, new XYConstraints(5, 3, -1, -1));
    jPanel5.add(jPanel6, new XYConstraints(0, 30, -1, -1));
    jPanel6.add(jButton10, new XYConstraints(3, 1, -1, -1));
    jPanel6.add(jButton12, new XYConstraints(228, 1, -1, -1));
    jPanel6.add(jButton11, new XYConstraints(115, 1, -1, -1));
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.setModal(true);

    dbmP = new DBMPolje(conn);
    if(dbmP.getRowCount() !=0) { //ovo jos proveriti
      trans=true;
      dbcm1 = new DBComboModel(conn,"select distinct PRINDID from prvi_indikator"+
                                " where PO_POLJEID="+Integer.parseInt((String)dbmP.getValueAt(row,0))+" order by PRINDID");
      dbcm2 = new DBComboModel(conn,"select distinct DRINDID from drugi_indikator"+
                          " where PO_POLJEID="+Integer.parseInt((String)dbmP.getValueAt(row,0))+" order by DRINDID");
      jComboBox1.setModel(dbcm1);
      jComboBox2.setModel(dbcm2);
      trans=false;
    }

    if (dbmP.getRowCount() != 0){
      trans=true;
      setValues(row);
      trans=false;
    }
    else {
      mod = INSERT;
      isEmpty=true;
    }
    setButtonEnabled();
    setComponentEnabled();
  }

  void setButtonEnabled() {
    switch(mod) {
      case UNDEFINED :jButton1.setEnabled(false);
                      jButton2.setEnabled(false);
                      jButton3.setEnabled(false);
                      jButton4.setEnabled(true);
                      jButton5.setEnabled(true);
                      jButton6.setEnabled(true);
                      jButton7.setEnabled(true);
                      jButton8.setEnabled(true);
                      jButton9.setEnabled(true);
                      jButton10.setEnabled(true);
                      jButton11.setEnabled(true);
                      jButton12.setEnabled(true);
                      jButton13.setEnabled(false);
                      jButton14.setEnabled(false);
                      jButton15.setEnabled(true);
                      break;

      case INSERT : jButton1.setEnabled(true);
                    jButton2.setEnabled(true);
                    jButton3.setEnabled(true);
                    jButton4.setEnabled(false);
                    jButton5.setEnabled(false);
                    jButton6.setEnabled(false);
                    jButton7.setEnabled(false);
                    jButton8.setEnabled(false);
                    jButton9.setEnabled(true);
                    jButton10.setEnabled(false);
                    jButton11.setEnabled(false);
                    jButton12.setEnabled(false);
                    jButton13.setEnabled(true);
                    jButton14.setEnabled(true);
                    jButton15.setEnabled(false);
                    break;

      case UPDATE : jButton1.setEnabled(true);
                    jButton2.setEnabled(true);
                    jButton3.setEnabled(true);
                    jButton4.setEnabled(false);
                    jButton5.setEnabled(false);
                    jButton6.setEnabled(false);
                    jButton7.setEnabled(false);
                    jButton8.setEnabled(false);
                    jButton9.setEnabled(true);
                    jButton10.setEnabled(false);
                    jButton11.setEnabled(false);
                    jButton12.setEnabled(false);
                    jButton13.setEnabled(true);
                    jButton14.setEnabled(true);
                    jButton15.setEnabled(false);
                    break;

      case DELETE : jButton1.setEnabled(false);
                    jButton2.setEnabled(false);
                    jButton3.setEnabled(false);
                    jButton4.setEnabled(false);
                    jButton5.setEnabled(false);
                    jButton6.setEnabled(false);
                    jButton7.setEnabled(false);
                    jButton8.setEnabled(false);
                    jButton9.setEnabled(true);
                    jButton10.setEnabled(false);
                    jButton11.setEnabled(false);
                    jButton12.setEnabled(false);
                    jButton13.setEnabled(true);
                    jButton14.setEnabled(true);
                    jButton15.setEnabled(false);
                    break;
    }
  }

  void setComponentEnabled() {
    switch(mod) {
      case UNDEFINED :jTextField1.setEnabled(false);
                      jTextArea1.setEnabled(false);
                      jTextArea2.setEnabled(false);
                      jTextArea3.setEnabled(false);
                      jCheckBox1.setEnabled(false);
                      jCheckBox2.setEnabled(false);
                      jCheckBox3.setEnabled(false);
                      jComboBox1.setEnabled(false);
                      jComboBox2.setEnabled(false);
                      break;
    case INSERT : jTextField1.setEnabled(true);
                  jTextArea3.setEnabled(true);
                  jTextArea2.setEnabled(true);
                  jTextArea1.setEnabled(true);
                  jTextField1.requestFocus();
                  jTextField1.setText("");
                  jTextArea3.setText("");
                  jTextArea2.setText("");
                  jTextArea1.setText("");
                  jCheckBox1.setEnabled(true);
                  jCheckBox2.setEnabled(true);
                  jCheckBox3.setEnabled(true);
                  jComboBox1.setEnabled(true);
                  jComboBox2.setEnabled(true);
                  break;
      case UPDATE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(true);
                    jTextArea1.requestFocus();
                    jTextArea2.setEnabled(true);
                    jTextArea3.setEnabled(true);
                    jCheckBox1.setEnabled(true);
                    jCheckBox2.setEnabled(true);
                    jCheckBox3.setEnabled(true);
                    jComboBox1.setEnabled(true);
                    jComboBox2.setEnabled(true);
                    break;
      case DELETE : jTextField1.setEnabled(false);
                    jTextArea1.setEnabled(false);
                    jTextArea2.setEnabled(false);
                    jTextArea3.setEnabled(false);
                    jComboBox1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    jCheckBox1.setEnabled(false);
                    jCheckBox2.setEnabled(false);
                    jCheckBox3.setEnabled(false);
                    break;
    }
  }

  void setValues(int row){
    dbcm1.reloadItemsWithBlank("select distinct PRINDID from prvi_indikator where PO_POLJEID="+Integer.parseInt((String)dbmP.getValueAt(row,0))+" order by PRINDID");
    dbcm2.reloadItemsWithBlank("select distinct DRINDID from drugi_indikator where PO_POLJEID="+Integer.parseInt((String)dbmP.getValueAt(row,0))+" order by DRINDID");
    jTextField1.setText((String)dbmP.getValueAt(row,0));
    jTextArea1.setText((String)dbmP.getValueAt(row,1));
    jTextArea2.setText((String)dbmP.getValueAt(row,5));
    jTextArea3.setText((String)dbmP.getValueAt(row,6));
    String ponov = ((String)dbmP.getValueAt(row,2)).trim();
    String obav = ((String)dbmP.getValueAt(row,3)).trim();
    String sek = ((String)dbmP.getValueAt(row,4)).trim();
    if((String)dbmP.getValueAt(row,7) != null)
      jComboBox1.setSelectedItem((String)dbmP.getValueAt(row,7));
    if((String)dbmP.getValueAt(row,8) != null)
      jComboBox2.setSelectedItem(((String)dbmP.getValueAt(row,8)).trim());
    if(Integer.parseInt(ponov) == 1) {
      jCheckBox1.setSelected(true);
    }
    else {
      jCheckBox1.setSelected(false);
    }
    if(Integer.parseInt(obav) == 1) {
      jCheckBox2.setSelected(true);
    }
    else {
      jCheckBox2.setSelected(false);
    }
    if(Integer.parseInt(sek) == 1) {
      jCheckBox3.setSelected(true);
    }
    else {
      jCheckBox3.setSelected(false);
    }
  }

  boolean checkValues() {
    if (mod == INSERT) {
      if(jTextField1.getText().length() != 3) {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_DIFF3CHAR_TEXT"),Messages.get("POLJE_INFDLG_FIELD_DIFF3CHAR_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
      if(jTextField1.getText().length() == 0) {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_MISSINGFIELD_TEXT"),Messages.get("POLJE_INFDLG_FIELD_MISSINGFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
      if(!(Character.isDigit(jTextField1.getText().charAt(0)))) {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_FIRSTCHARMUSTBENUMERIC_TEXT"),Messages.get("POLJE_INFDLG_FIELD_FIRSTCHARMUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
      if (jTextField1.getText().length() == 2)
        if(!(Character.isDigit(jTextField1.getText().charAt(1)))) {
          JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_SECONDCHARMUSTBENUMERIC1_TEXT"),Messages.get("POLJE_INFDLG_FIELD_SECONDCHARMUSTBENUMERIC1_TITLE"),JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
      if (jTextField1.getText().length() == 3) {
        if(!(Character.isDigit(jTextField1.getText().charAt(1)))) {
          JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_SECONDCHARMUSTBENUMERIC2_TEXT="),Messages.get("POLJE_INFDLG_FIELD_SECONDCHARMUSTBENUMERIC2_TITLE"),JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
        if(!(Character.isDigit(jTextField1.getText().charAt(2)))) {
          JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_THIRDCHARMUSTBENUMERIC_TEXT"),Messages.get("POLJE_INFDLG_FIELD_THIRDCHARMUSTBENUMERIC_TITLE"),JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
      }
    }
    if(jTextArea1.getText().length() > 160) {
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELDNAME_LENGTHEXCEEDED_TEXT"),Messages.get("POLJE_INFDLG_FIELDNAME_LENGTHEXCEEDED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    if(jTextArea1.getText().length() == 0) {
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELDNAME_NONAME_TEXT"),Messages.get("POLJE_INFDLG_FIELDNAME_NONAME_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    return true;
  }

  void find(String fld) {
    String value = fld;
    int lengths = fld.length();
    int pos = dbmP.find(value,lengths);
    if (pos!=-1){
      row=pos;
      trans=true;
      setValues(row);
      trans=false;
    }
  }

  public String getField() {
    //return (String)dbmP.getValueAt(row,0);
    return jTextField1.getText().trim();
  }

  public String getName() {
    //return (String)dbmP.getValueAt(row,1);
    return jTextArea1.getText().trim();
  }

  void first() {
    trans=true;
    row=0;
    setValues(row);
    trans=false;
  }

  void last() {
    trans=true;
    row=dbmP.getRowCount()-1;
    setValues(row);
    trans=false;
  }

  void next() {
    if(row == dbmP.getRowCount()-1)
      return;
    else {
      trans=true;
      row = row+1;
      setValues(row);
      trans=false;
    }
  }

  void prev() {
    if(row == 0)
      return;
    else {
      trans=true;
      row = row-1;
      setValues(row);
      trans=false;
    }
  }

  void jButton4_actionPerformed(ActionEvent e)  {
    first();
  }

  void jButton5_actionPerformed(ActionEvent e)  {
    prev();
  }
  void jButton6_actionPerformed(ActionEvent e)  {
    next();
  }
  void jButton7_actionPerformed(ActionEvent e)  {
    last();
  }
  void jButton8_actionPerformed(ActionEvent e)  {
    PronadjiP pp = new PronadjiP(conn,this);
    pp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = pp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    pp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    pp.setVisible(true);
  }
  void jButton10_actionPerformed(ActionEvent e)  {
    insert();
  }
  void insert() {
    mod = INSERT;
    jCheckBox1.setSelected(false);
    jCheckBox2.setSelected(false);
    jCheckBox3.setSelected(false);
    if(dbcm1 != null) {
      dbcm1.reloadItems(new Vector());
    }
    else {
      jComboBox1.removeAllItems();
      jComboBox1.addItem("");
    }
    if(dbcm2 != null) {
      dbcm2.reloadItems(new Vector());
    }
    else {
      jComboBox2.removeAllItems();
      jComboBox2.addItem("");
    }
    jComboBox1.setSelectedIndex(0);
    jComboBox2.setSelectedIndex(0);
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton11_actionPerformed(ActionEvent e)  {
    update();
  }
  void update() {
    mod = UPDATE;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton12_actionPerformed(ActionEvent e)  {
    delete();
  }
  void delete() {
    if(dbmP.getRowCount() == 1)
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_RECORD_LASTRECDELETED_TEXT"),Messages.get("POLJE_INFDLG_RECORD_LASTRECDELETED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    mod = DELETE;
    setButtonEnabled();
    setComponentEnabled();
  }
  void jButton13_actionPerformed(ActionEvent e)  {
    doAction();
  }
  void doAction()  {
    int sfCount=0;
    if(defSubfield) {
      dbmpp=dpp.getInstance();
      sfCount=dbmpp.getSfieldId().size();
    }
    int piCount=0;
    if(dfPi) {
      dbmpi=dpi.getInstance();
      piCount=dbmpi.getPrindId().size();
    }
    int diCount=0;
    if(dfDi) {
      dbmdi=ddi.getInstance();
      diCount=dbmdi.getDrindId().size();
    }
    String fld = jTextField1.getText().trim();
    String naziv = jTextArea1.getText().trim();
    String defPi="";
    String defDi="";
    if(jComboBox1.getSelectedItem()!= null)
      defPi = ((String)jComboBox1.getSelectedItem()).trim();
    if(jComboBox2.getSelectedItem()!= null)
      defDi = ((String)jComboBox2.getSelectedItem()).trim();
    String opisDi = jTextArea3.getText().trim();
    String opisPi = jTextArea2.getText().trim();
    dbmP.setField(fld);
    dbmP.setNazivField(naziv);
    dbmP.setDefPi(defPi);
    dbmP.setDefDi(defDi);
    dbmP.setOpisPi(opisPi);
    dbmP.setOpisDi(opisDi);
    if(jCheckBox1.isSelected())
      dbmP.setPonov("1");
    else
      dbmP.setPonov("0");
    if(jCheckBox2.isSelected())
      dbmP.setObav("1");
    else
      dbmP.setObav("0");
    if(jCheckBox3.isSelected())
      dbmP.setSek("1");
    else
      dbmP.setSek("0");

    if (mod == INSERT) {
      if(jTextField1.getText().trim().length() != 0) {
        if((defSubfield && sfCount!=0) || (!defSubfield && checkSFCount())) {
          if(opisPi.length() == 0 || ((dfPi && piCount!=0) || (!dfPi && checkPICount()))) {
            if(opisDi.length() == 0 || ((dfDi && diCount!=0) || (!dfDi && checkDICount()))) {
              int pos = dbmP.findPos(fld);
              if (checkValues()&& pos!=-1) {
    row = pos;
    dbmP.setTo(row,mod);
    dbmP.saveToDB(mod,row);
    if(isEmpty)
      isEmpty=false;
    if(defSubfield) {
      processSubfields();
      defSubfield=false;
    }
    if(dfPi) {
      processPInd();
      dfPi=false;
    }
    if(dfDi) {
      processDInd();
      dfDi=false;
    }
      if(dbcm1 == null) {
          dbcm1 = new DBComboModel(conn,"select distinct PRINDID from prvi_indikator"+
                                  " where PO_POLJEID="+jTextField1.getText().trim()+" order by PRINDID");
                  jComboBox1.setModel(dbcm1);
      }
      if(dbcm2 == null) {
          dbcm2 = new DBComboModel(conn,"select distinct DRINDID from drugi_indikator"+
                                    " where PO_POLJEID="+jTextField1.getText().trim()+" order by DRINDID");
      jComboBox2.setModel(dbcm2);
      }
      mod = UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
              }
              else {
                if(pos == -1) {
            JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_EXISTANCEINDATABASE_TEXT"),Messages.get("POLJE_INFDLG_FIELD_EXISTANCEINDATABASE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
                }
              }
            }
            else {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_SECONDINDICATOR_NOVALUE_TEXT1"),Messages.get("POLJE_INFDLG_SECONDINDICATOR_NOVALUE_TITLE1"),JOptionPane.INFORMATION_MESSAGE);
        jButton3.setEnabled(true);
            }
          }
          else {
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIRSTINDICATOR_NOVALUE_TEXT1"),Messages.get("POLJE_INFDLG_FIRSTINDICATOR_NOVALUE_TITLE1"),JOptionPane.INFORMATION_MESSAGE);
      jButton2.setEnabled(true);
          }
        }
        else {
    JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_SUBFIELD_NOTDEFINED_TEXT2"),Messages.get("POLJE_INFDLG_SUBFIELD_NOTDEFINED_TITLE2"),JOptionPane.INFORMATION_MESSAGE);
    jButton1.setEnabled(true);
        }
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TEXT1"),Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TITLE1"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else if (mod == UPDATE) {
      if((defSubfield && sfCount!=0) || (!defSubfield && checkSFCount())) {
        if(opisPi.length() == 0 || ((dfPi && piCount!=0) || (!dfPi && checkPICount()))) {
          if(opisDi.length() == 0 || ((dfDi && diCount!=0) || (!dfDi && checkDICount()))) {
            if (checkValues()) {
              dbmP.setTo(row,mod);
              dbmP.saveToDB(mod,row);
              if(defSubfield) {
                processSubfields();
                defSubfield=false;
              }
              if(dfPi) {
          processPInd();
                dfPi=false;
              }
        if(dfDi) {
          processDInd();
                dfDi=false;
              }
              mod = UNDEFINED;
              setButtonEnabled();
              setComponentEnabled();
            }
          }
          else {
            JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_SECONDINDICATOR_NOVALUE_TEXT2"),Messages.get("POLJE_INFDLG_SECONDINDICATOR_NOVALUE_TITLE2"),JOptionPane.INFORMATION_MESSAGE);
            jButton3.setEnabled(true);
          }
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIRSTINDICATOR_NOVALUE_TEXT2="),Messages.get("POLJE_INFDLG_FIRSTINDICATOR_NOVALUE_TITLE2"),JOptionPane.INFORMATION_MESSAGE);
          jButton2.setEnabled(true);
        }
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_SUBFIELD_NOTDEFINED_TEXT2"),Messages.get("POLJE_INFDLG_SUBFIELD_NOTDEFINED_TITLE2"),JOptionPane.INFORMATION_MESSAGE);
        jButton1.setEnabled(true);
      }
    }
    else if (mod == DELETE) {
      dbmP.removeRow(row);
      dbmP.saveToDB(mod,row);
      if (row == dbmP.getRowCount()) {
        row=row-1;
      }
      if(dbmP.getRowCount() == 0) {
        dispose();
      }
      else {
        if(defSubfield)
          removeAllSF();
        if(dfPi)
          removeAllPInd();
        if(dfDi)
          removeAllDInd();
        trans=true;
        setValues(row);
        trans=false;
        mod = UNDEFINED;
        setButtonEnabled();
        setComponentEnabled();
      }
    }
  }
  void jButton14_actionPerformed(ActionEvent e)  {
    doCancel();
  }
  void doCancel() {
    if(!isEmpty) {
      if (mod==INSERT || mod==UPDATE) {
        trans=true;
        setValues(row);
        trans=false;
      }
      mod = UNDEFINED;
      setButtonEnabled();
      setComponentEnabled();
    }
    else {
      dispose();
    }
  }

  void jButton15_actionPerformed(ActionEvent e)  {
    dispose();
  }

  void jButton1_actionPerformed(ActionEvent e)  {
    if(jTextField1.getText().trim().length() != 0) {
      jButton1.setEnabled(false);
      defSubfield=true;
      dpp = new DPotpolja(conn,jTextField1.getText().trim(),this);
      dpp.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = dpp.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
      dpp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      dpp.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TEXT2"),Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TITLE2"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jButton2_actionPerformed(ActionEvent e)  {
    if(jTextField1.getText().trim().length() != 0) {
      jButton2.setEnabled(false);
      dfPi=true;
      dpi = new DPindikatora(conn,jTextField1.getText().trim(),this);
      dpi.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = dpi.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
      dpi.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      dpi.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TEXT3"),Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TITLE3"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void jButton3_actionPerformed(ActionEvent e)  {
    if(jTextField1.getText().trim().length() != 0) {
      jButton3.setEnabled(false);
      dfDi=true;
      ddi = new DDindikatora(conn,jTextField1.getText().trim(),this);
      ddi.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = ddi.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
      ddi.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      ddi.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TEXT4"),Messages.get("POLJE_INFDLG_FIELD_NOTENTERED_TITLE4"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void removeAllSF() {
    dbmpp.removeAll();
  }
  void removeAllPInd() {
    dbmpi.removeAll();
  }
  void removeAllDInd() {
    dbmdi.removeAll();
  }

  boolean checkSFCount() {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select POTPOLJAID from potpolja where PO_POLJEID="+jTextField1.getText().trim());
      if(rset.next()) {
        return true;
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  boolean checkPICount() {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select prindid from prvi_indikator where po_poljeid="+jTextField1.getText().trim());
      if(rset.next()) {
        return true;
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  boolean checkDICount() {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("select drindid from drugi_indikator where po_poljeid="+jTextField1.getText().trim());
      if(rset.next()) {
        return true;
      }
      rset.close();
      stmt.close();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  void processSubfields() {
    Vector flag=dbmpp.getFlag();
    Vector sfieldId=dbmpp.getSfieldId();
    Vector tesId = dbmpp.getTesId();
    Vector sfieldNaziv =dbmpp.getSfieldNaziv();
    Vector kontrola = dbmpp.getKontrola();
    Vector duzina = dbmpp.getDuzina();
    Vector ponov = dbmpp.getPonov();
    Vector obavez =dbmpp.getObavez();
    Vector status = dbmpp.getStatus();
    Vector defval = dbmpp.getDefval();
    String field=getField();
    Vector sfForDel=dbmpp.getSfForDel();

    for(int i=0;i<sfieldId.size();i++) {
      if(((String)flag.elementAt(i)).equals("0")){
        String insertRow;
        if(tesId.elementAt(i) != "")
          insertRow = "insert into potpolja (PO_POLJEID,POTPOLJAID,TES_TESID,PP_NAZIV,KONTROLA_KONTRID,DUZINA"+
                      ",PONOVLJIVOSTPP,OBAVEZNOSTPP,STATUS,DEFVREDNOST) values ('"+field+"','"+(String)sfieldId.elementAt(i)+ "',"+Integer.parseInt((String)tesId.elementAt(i))+
                ",'"+(String)sfieldNaziv.elementAt(i)+"',"+Integer.parseInt((String)kontrola.elementAt(i))+ ","+Integer.parseInt((String)duzina.elementAt(i))+ ","+
                Integer.parseInt((String)ponov.elementAt(i))+ ","+Integer.parseInt((String)obavez.elementAt(i))+ ",'"+(String)status.elementAt(i)+ "','"+(String)defval.elementAt(i)+ "')";
        else
          insertRow = "insert into potpolja (PO_POLJEID,POTPOLJAID,TES_TESID,PP_NAZIV,KONTROLA_KONTRID,DUZINA"+
                      ",PONOVLJIVOSTPP,OBAVEZNOSTPP,STATUS,DEFVREDNOST) values ('"+field+"','"+(String)sfieldId.elementAt(i)+ "',null"+
                ",'"+(String)sfieldNaziv.elementAt(i)+"',"+Integer.parseInt((String)kontrola.elementAt(i))+ ","+Integer.parseInt((String)duzina.elementAt(i))+ ","+
                Integer.parseInt((String)ponov.elementAt(i))+ ","+Integer.parseInt((String)obavez.elementAt(i))+ ",'"+(String)status.elementAt(i)+ "','"+(String)defval.elementAt(i)+ "')";
        try {
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(insertRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
      else if(((String)flag.elementAt(i)).equals("1")) {
        String updateRow;
        if(tesId.elementAt(i) != "")
          updateRow = "update potpolja set PP_NAZIV ='"+(String)sfieldNaziv.elementAt(i)+"' ,KONTROLA_KONTRID="+Integer.parseInt((String)kontrola.elementAt(i))+
                      " ,DUZINA="+Integer.parseInt((String)duzina.elementAt(i))+" , PONOVLJIVOSTPP="+Integer.parseInt((String)ponov.elementAt(i))+" ,OBAVEZNOSTPP="
                      +Integer.parseInt((String)obavez.elementAt(i))+" ,STATUS='"+(String)status.elementAt(i)+"' ,DEFVREDNOST='"+(String)defval.elementAt(i)+"' ,TES_TESID="+Integer.parseInt((String)tesId.elementAt(i))+
                      " where PO_POLJEID="+field+" and POTPOLJAID='"+(String)sfieldId.elementAt(i)+"'";
        else
        updateRow = "update potpolja set PP_NAZIV ='"+(String)sfieldNaziv.elementAt(i)+"' ,KONTROLA_KONTRID="+Integer.parseInt((String)kontrola.elementAt(i))+
                      " ,DUZINA="+Integer.parseInt((String)duzina.elementAt(i))+" , PONOVLJIVOSTPP="+Integer.parseInt((String)ponov.elementAt(i))+" ,OBAVEZNOSTPP="
                      +Integer.parseInt((String)obavez.elementAt(i))+" ,STATUS='"+(String)status.elementAt(i)+"' ,DEFVREDNOST='"+(String)defval.elementAt(i)+"' ,TES_TESID=null"+
                      " where PO_POLJEID="+field+" and POTPOLJAID='"+(String)sfieldId.elementAt(i)+"'";
        try {
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(updateRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
    }
    if(sfForDel.size()>0) {
      for(int i=0;i<sfForDel.size();i++) {
        String deleteRow = "delete from potpolja where PO_POLJEID="+field+
                            " and POTPOLJAID='"+(String)sfForDel.elementAt(i)+"'";
        try{
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(deleteRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
      dbmpp.removeForDelete();
    }
  }

  void processPInd() {
    Vector flag=dbmpi.getFlag();
    Vector prindId=dbmpi.getPrindId();
    Vector prindNaziv =dbmpi.getPrindNaziv();
    String field=getField();
    Vector piForDel=dbmpi.getPiForDel();

    for(int i=0;i<prindId.size();i++) {
      if(((String)flag.elementAt(i)).equals("0")){
        String insertRow = "insert into prvi_indikator (PO_POLJEID,PRINDID,PI_NAZIV) values ('"+field+"','"+(String)prindId.elementAt(i)+
                        "','"+(String)prindNaziv.elementAt(i)+"')";
        try {
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(insertRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
      else if(((String)flag.elementAt(i)).equals("1")) {
        String updateRow = "update prvi_indikator set PI_NAZIV ='"+(String)prindNaziv.elementAt(i)+"'"+
                      " where PO_POLJEID="+field+" and PRINDID='"+(String)prindId.elementAt(i)+"'";
        try {
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(updateRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
    }

    if(piForDel.size()>0) {
      for(int i=0;i<piForDel.size();i++) {
        String deleteRow = "delete from prvi_indikator where PO_POLJEID="+field+
                          " and PRINDID='"+(String)piForDel.elementAt(i)+"'";
        try{
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(deleteRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
      dbmpi.removeForDelete();
    }
  }

  void processDInd() {
    Vector flag=dbmdi.getFlag();
    Vector drindId=dbmdi.getDrindId();
    Vector drindNaziv =dbmdi.getDrindNaziv();
    String field=getField();
    Vector diForDel=dbmdi.getDiForDel();

    for(int i=0;i<drindId.size();i++) {
      if(((String)flag.elementAt(i)).equals("0")){
        String insertRow = "insert into drugi_indikator (PO_POLJEID,DRINDID,DI_NAZIV) values ('"+field+"','"+(String)drindId.elementAt(i)+
                      "','"+(String)drindNaziv.elementAt(i)+"')";
        try {
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(insertRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
      else if(((String)flag.elementAt(i)).equals("1")) {
        String updateRow = "update drugi_indikator set DI_NAZIV ='"+(String)drindNaziv.elementAt(i)+"'"+
            " where PO_POLJEID="+field+" and DRINDID='"+(String)drindId.elementAt(i)+"'";
        try {
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(updateRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
    }

    if(diForDel.size()>0) {
      for(int i=0;i<diForDel.size();i++) {
        String deleteRow = "delete from drugi_indikator where PO_POLJEID="+field+
                            " and DRINDID='"+(String)diForDel.elementAt(i)+"'";
        try{
          Statement stmt = conn.createStatement();
          int rez = stmt.executeUpdate(deleteRow);
          stmt.close();
          conn.commit();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
      }
      dbmdi.removeForDelete();
    }
  }

  void reloadPrind(Vector vItems) {
    if(dbcm1 != null) {
      dbcm1.reloadItems(vItems);
    }
    else {
      jComboBox1.addItem("");
      for(int i=0; i<vItems.size();i++) {
        jComboBox1.addItem(vItems.elementAt(i));
      }
    }
    if(vItems.size()==0)
      jComboBox1.setSelectedIndex(0);
  }

  void reloadDrind(Vector vItems) {
    if(dbcm2 != null) {
      dbcm2.reloadItems(vItems);
    }
    else {
      jComboBox2.addItem("");
      for(int i=0; i<vItems.size();i++) {
        jComboBox2.addItem(vItems.elementAt(i));
      }
    }
    if(vItems.size()==0)
      jComboBox2.setSelectedIndex(0);
  }

  String getCurrDefPI() {
    if (jComboBox1.getSelectedItem() != null)
      return ((String)jComboBox1.getSelectedItem()).trim();
    return "";
  }

  String getCurrDefDI() {
    if (jComboBox2.getSelectedItem() != null)
      return ((String)jComboBox2.getSelectedItem()).trim();
    return "";
  }

  void selectEmptyPI() {
    jComboBox1.setSelectedItem("");
  }

  void selectEmptyDI() {
    jComboBox2.setSelectedItem("");
  }

  void this_keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN:if(mod == UNDEFINED) {
                            if(dbmP.getRowCount() !=0) {
            next();
          }
         }
         break;
      case KeyEvent.VK_PAGE_UP: if(mod == UNDEFINED) {
                            if(dbmP.getRowCount() !=0) {
            prev();
          }
              }
        break;
      case KeyEvent.VK_HOME:if(mod == UNDEFINED) {
                  if(dbmP.getRowCount() !=0) {
        first();
                              }
          }
          break;
      case KeyEvent.VK_END:if(mod == UNDEFINED) {
          if(dbmP.getRowCount() !=0) {
            last();
          }
         }
         break;
      case KeyEvent.VK_F1:if(mod == UNDEFINED) {
          if(dbmP.getRowCount() !=0) {
                              insert();
                            }
        }
        break;
      case KeyEvent.VK_F2:if(mod == UNDEFINED) {
                      if(dbmP.getRowCount() !=0) {
            update();
          }
        }
        break;
      case KeyEvent.VK_F3:if(mod == UNDEFINED) {
                      if(dbmP.getRowCount() !=0) {
            delete();
                            }
        }
        break;
      case KeyEvent.VK_F5:if(mod==INSERT || mod==DELETE || mod==UPDATE) {
                      doAction();
        }
        break;
      case KeyEvent.VK_F6:if(mod==INSERT || mod==DELETE || mod==UPDATE) {
                      doCancel();
        }
        break;
      default:
    }
  }
  protected void processWindowEvent(WindowEvent e){   // ZASTITA OD X-ICA U TOKU NEKE AKCIJE
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      if(mod != UNDEFINED) {
        JOptionPane.showMessageDialog(null,Messages.get("POLJE_INFDLG_CLOSING_ACTIONNOTFINISHED_TEXT"),Messages.get("POLJE_INFDLG_CLOSING_ACTIONNOTFINISHED_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      else {
        super.processWindowEvent(e);
      }
    }
    else {
      super.processWindowEvent(e);
    }
  }
}
