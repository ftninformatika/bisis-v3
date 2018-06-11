
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class PronadjiLS extends JDialog {
  JComboBox jComboBox3 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel4 = new JLabel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();

  Connection conn;

  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;
  DBComboModel dbcm4;

  Lsifarnik lsf;
  String fld="";
  String sfld="";
  String ssfld="";
  String lSifr="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();

  public PronadjiLS(Connection conn, Lsifarnik lsf) {
    try  {
      this.conn = conn;
      this.lsf = lsf;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel3.setText(Messages.get("PRONADJILS_LABEL_SUBSUBFIELD"));
    jComboBox3.setPreferredSize(new Dimension(40, 21));
    jComboBox3.setMinimumSize(new Dimension(40, 21));
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJILS_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox4.setPreferredSize(new Dimension(175, 21));
    jComboBox4.setMinimumSize(new Dimension(175, 21));
    jComboBox4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox4_actionPerformed(e);
      }
    });
    jLabel1.setText(Messages.get("PRONADJILS_LABEL_FIELD"));
    jLabel2.setText(Messages.get("PRONADJILS_LABEL_SUBFIELD"));
    jLabel4.setText(Messages.get("PRONADJILS_LABEL_LOCALCODER"));
    jButton1.setText(Messages.get("PRONADJILS_BUTTON_CANCEL"));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText(Messages.get("PRONADJILS_BUTTON_OK"));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(550, 200));
    jPanel4.setPreferredSize(new Dimension(550, 200));
    xYLayout1.setHeight(235);
    xYLayout1.setWidth(600);
    jLabel5.setForeground(new java.awt.Color(50, 54, 120));
    jLabel6.setForeground(new java.awt.Color(50, 54, 120));
    jLabel7.setForeground(new java.awt.Color(50, 54, 120));
    jLabel8.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 550, 170));
    jPanel4.add(jLabel1, new XYConstraints(15, 25, -1, -1));
    jPanel4.add(jComboBox1, new XYConstraints(120, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(15, 65, 73, -1));
    jPanel4.add(jLabel3, new XYConstraints(15, 105, 87, -1));
    jPanel4.add(jComboBox2, new XYConstraints(120, 65, -1, -1));
    jPanel4.add(jLabel4, new XYConstraints(15, 145, -1, -1));
    jPanel4.add(jComboBox3, new XYConstraints(120, 105, -1, -1));
    jPanel4.add(jComboBox4, new XYConstraints(120, 145, 163, -1));
    jPanel4.add(jLabel5, new XYConstraints(185, 25, -1, -1));
    jPanel4.add(jLabel6, new XYConstraints(185, 65, -1, -1));
    jPanel4.add(jLabel7, new XYConstraints(185, 105, -1, -1));
    jPanel4.add(jLabel8, new XYConstraints(295, 145, -1, -1));
    jPanel1.add(jButton2, new XYConstraints(115, 185, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(220, 185, -1, -1));
    this.setModal(true);

    fld=lsf.getField().trim();
    sfld=lsf.getSField().trim();
    ssfld=lsf.getSSField().trim();
    lSifr=lsf.getLSifarnik().trim();
    trans=true;

    dbcm1 = new DBComboModel(conn,"select distinct sf.PPP_PPO_PO_POLJEID,p.PO_NAZIV from sifarnik_potpotpolja sf, polje p where sf.PPP_PPO_PO_POLJEID=p.POLJEID order by sf.PPP_PPO_PO_POLJEID",true);
    dbcm2 = new DBComboModel(conn,"select distinct sf.PPP_PPO_POTPOLJAID,pp.PP_NAZIV from sifarnik_potpotpolja sf, potpolja pp"+
                              " where sf.PPP_PPO_POTPOLJAID=pp.POTPOLJAID and sf.PPP_PPO_PO_POLJEID=pp.PO_POLJEID and sf.PPP_PPO_PO_POLJEID="+fld+" order by sf.PPP_PPO_POTPOLJAID",true);
    dbcm3 = new DBComboModel(conn,"select distinct sf.PPP_POTPID,ppp.POTP_NAZIV from sifarnik_potpotpolja sf, potpotpolja ppp"+
                              " where sf.PPP_POTPID=ppp.POTPID and sf.PPP_PPO_POTPOLJAID=ppp.PPO_POTPOLJAID and sf.PPP_PPO_PO_POLJEID=ppp.PPO_PO_POLJEID and sf.PPP_PPO_PO_POLJEID="+fld+" and sf.PPP_PPO_POTPOLJAID='"+sfld+"' order by sf.PPP_POTPID",true);
    dbcm4 = new DBComboModel(conn,"select distinct SIFPOTPID,SIFPOTP_NAZIV from sifarnik_potpotpolja"+
                              " where PPP_PPO_PO_POLJEID="+fld+" and PPP_PPO_POTPOLJAID='"+sfld+"' and PPP_POTPID='"+ssfld+"' order by SIFPOTPID",true);
    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(fld);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sfld);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(ssfld);
    jComboBox4.setModel(dbcm4);
    jComboBox4.setSelectedItem(lSifr);
    setNameField();
    setNameSField();
    setNameSSField();
    setNameLSif();
    trans=false;
  }
  void setNameField() {
    jLabel5.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameSField() {
    jLabel6.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameSSField() {
    jLabel7.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameLSif() {
    jLabel8.setText((String)dbcm4.getNameAt(jComboBox4.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String sfld = ((String)jComboBox2.getSelectedItem()).trim();
    String ssfld = ((String)jComboBox3.getSelectedItem()).trim();
    String lSifr = ((String)jComboBox4.getSelectedItem()).trim();
    lsf.find(fld,sfld,ssfld,lSifr);
    dispose();
  }
  void jComboBox1_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct sf.PPP_PPO_POTPOLJAID,pp.PP_NAZIV from sifarnik_potpotpolja sf, potpolja pp"+
  		        " where sf.PPP_PPO_POTPOLJAID=pp.POTPOLJAID and sf.PPP_PPO_PO_POLJEID=pp.PO_POLJEID and sf.PPP_PPO_PO_POLJEID="+
  			fld+" order by sf.PPP_PPO_POTPOLJAID",true);
      String sfld = ((String)jComboBox2.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct sf.PPP_POTPID,ppp.POTP_NAZIV from sifarnik_potpotpolja sf, potpotpolja ppp"+
  	                " where sf.PPP_POTPID=ppp.POTPID and sf.PPP_PPO_POTPOLJAID=ppp.PPO_POTPOLJAID and sf.PPP_PPO_PO_POLJEID=ppp.PPO_PO_POLJEID"+
  			" and sf.PPP_PPO_PO_POLJEID="+fld+" and sf.PPP_PPO_POTPOLJAID='"+sfld+"' order by sf.PPP_POTPID",true);
      String ssfld = ((String)jComboBox3.getSelectedItem()).trim();
      dbcm4.reloadItems("select distinct SIFPOTPID,SIFPOTP_NAZIV from sifarnik_potpotpolja where PPP_PPO_PO_POLJEID="+
  	                fld+" and PPP_PPO_POTPOLJAID='"+sfld+"' and PPP_POTPID='"+ssfld+"' order by SIFPOTPID",true);
      jComboBox2.setSelectedIndex(0);
      setNameSField();
      jComboBox3.setSelectedIndex(0);
      setNameSSField();
      jComboBox4.setSelectedIndex(0);
      setNameLSif();
      trans=false;
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameSField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      String sfld = ((String)jComboBox2.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct sf.PPP_POTPID,ppp.POTP_NAZIV from sifarnik_potpotpolja sf, potpotpolja ppp"+
  	                " where sf.PPP_POTPID=ppp.POTPID and sf.PPP_PPO_POTPOLJAID=ppp.PPO_POTPOLJAID and sf.PPP_PPO_PO_POLJEID=ppp.PPO_PO_POLJEID"+
  			" and sf.PPP_PPO_PO_POLJEID="+fld+" and sf.PPP_PPO_POTPOLJAID='"+sfld+"' order by sf.PPP_POTPID",true);
      String ssfld = ((String)jComboBox3.getSelectedItem()).trim();
      dbcm4.reloadItems("select distinct SIFPOTPID,SIFPOTP_NAZIV from sifarnik_potpotpolja where PPP_PPO_PO_POLJEID="+
  	                fld+" and PPP_PPO_POTPOLJAID='"+sfld+"' and PPP_POTPID='"+ssfld+"' order by SIFPOTPID",true);
      jComboBox3.setSelectedIndex(0);
      setNameSSField();
      jComboBox4.setSelectedIndex(0);
      setNameLSif();
      trans=false;
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameSSField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      String sfld = ((String)jComboBox2.getSelectedItem()).trim();
      String ssfld = ((String)jComboBox3.getSelectedItem()).trim();
      dbcm4.reloadItems("select distinct SIFPOTPID,SIFPOTP_NAZIV from sifarnik_potpotpolja where PPP_PPO_PO_POLJEID="+
  	                fld+" and PPP_PPO_POTPOLJAID='"+sfld+"' and PPP_POTPID='"+ssfld+"' order by SIFPOTPID",true);
      jComboBox4.setSelectedIndex(0);
      setNameLSif();
      trans=false;
    }
  }
  void jComboBox4_actionPerformed(ActionEvent e)  {
    if(!trans)
      setNameLSif();
  }
}
