
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:
//Company:      Your Company
//Description:  Your description
package com.gint.app.bisis.unimarc.unim;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import com.gint.app.bisis.unimarc.dbmodel.*;
import com.borland.jbcl.layout.*;

public class PronadjiPPP extends JDialog {
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JComboBox jComboBox3 = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboBox4 = new JComboBox();
  JLabel jLabel4 = new JLabel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();

  Connection conn;

  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;

  Potpotpolja ppp;
  String fld="";
  String sfld="";
  String ssfld="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  public PronadjiPPP(Connection conn,Potpotpolja ppp) {
    try  {
      this.conn=conn;
      this.ppp=ppp;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel2.setText(Messages.get("PRONADJIPPP_LABEL_FIELD"));
    jComboBox2.setPreferredSize(new Dimension(58, 21));
    jComboBox2.setMinimumSize(new Dimension(58, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIPPP_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jPanel5.setLayout(xYLayout2);
    jPanel5.setMinimumSize(new Dimension(480, 200));
    jPanel5.setPreferredSize(new Dimension(480, 200));
    jComboBox3.setMinimumSize(new Dimension(40, 21));
    jComboBox3.setPreferredSize(new Dimension(40, 21));
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    jLabel3.setText(Messages.get("PRONADJIPPP_LABEL_SUBFIELD"));
    jComboBox4.setMinimumSize(new Dimension(40, 21));
    jComboBox4.setPreferredSize(new Dimension(40, 21));
    jComboBox4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox4_actionPerformed(e);
      }
    });
    jLabel4.setText(Messages.get("PRONADJIPPP_LABEL_SUBSUBFIELD"));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.setMinimumSize(new Dimension(80, 21));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("PRONADJIPPP_BUTTON_CANCEL"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.setMinimumSize(new Dimension(80, 21));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("PRONADJIPPP_BUTTON_OK"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(350);
    jLabel1.setForeground(new java.awt.Color(50, 54, 120));
    jLabel5.setForeground(new java.awt.Color(50, 54, 120));
    jLabel6.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jButton2, new XYConstraints(55, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(160, 150, -1, -1));
    jPanel1.add(jPanel5, new XYConstraints(10, 10, 338, 134));
    jPanel5.add(jLabel2, new XYConstraints(15, 25, -1, -1));
    jPanel5.add(jLabel3, new XYConstraints(15, 65, -1, -1));
    jPanel5.add(jComboBox2, new XYConstraints(95, 25, -1, -1));
    jPanel5.add(jLabel4, new XYConstraints(15, 105, -1, -1));
    jPanel5.add(jComboBox3, new XYConstraints(95, 65, -1, -1));
    jPanel5.add(jComboBox4, new XYConstraints(95, 105, -1, -1));
    jPanel5.add(jLabel1, new XYConstraints(165, 25, 160, -1));
    jPanel5.add(jLabel5, new XYConstraints(165, 65, 160, -1));
    jPanel5.add(jLabel6, new XYConstraints(165, 105, 160, -1));
    this.setModal(true);

    fld=ppp.getField().trim();
    sfld=ppp.getSField().trim();
    ssfld=ppp.getSSField().trim();
    trans=true;

    dbcm1 = new DBComboModel(conn,"select distinct ppp.PPO_PO_POLJEID,p.PO_NAZIV from potpotpolja ppp, polje p where ppp.PPO_PO_POLJEID=p.POLJEID order by ppp.PPO_PO_POLJEID",true);
    dbcm2 = new DBComboModel(conn,"select distinct ppp.PPO_POTPOLJAID,pp.PP_NAZIV from potpotpolja ppp, potpolja pp"+
    		              " where ppp.PPO_POTPOLJAID=pp.POTPOLJAID and ppp.PPO_PO_POLJEID=pp.PO_POLJEID and PPO_PO_POLJEID="+fld+" order by ppp.PPO_POTPOLJAID",true);
    dbcm3 = new DBComboModel(conn,"select distinct POTPID,POTP_NAZIV from potpotpolja"+
    													" where PPO_PO_POLJEID="+fld+" and PPO_POTPOLJAID='"+sfld+"' order by POTPID",true);

    jComboBox2.setModel(dbcm1);
    jComboBox2.setSelectedItem(fld);
    jComboBox3.setModel(dbcm2);
    jComboBox3.setSelectedItem(sfld);
    jComboBox4.setModel(dbcm3);
    jComboBox4.setSelectedItem(ssfld);
    trans=false;
    setNameField();
    setNameSField();
    setNameSSField();
  }

  void setNameField() {
    jLabel1.setText((String)dbcm1.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameSField() {
    jLabel5.setText((String)dbcm2.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void setNameSSField() {
    jLabel6.setText((String)dbcm3.getNameAt(jComboBox4.getSelectedIndex()));
  }

  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox2.getSelectedItem()).trim();
    String sfld = ((String)jComboBox3.getSelectedItem()).trim();
    String ssfld = ((String)jComboBox4.getSelectedItem()).trim();
    ppp.find(fld,sfld,ssfld);
    dispose();
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameField();
      trans=true;
      String fld = ((String)jComboBox2.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct ppp.PPO_POTPOLJAID,pp.PP_NAZIV from potpotpolja ppp,potpolja pp where ppp.PPO_POTPOLJAID=pp.POTPOLJAID and ppp.PPO_PO_POLJEID=pp.PO_POLJEID and ppp.PPO_PO_POLJEID="+
                        fld+" order by ppp.PPO_POTPOLJAID",true);
      jComboBox3.setSelectedIndex(0);
      String sfld = ((String)jComboBox3.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct POTPID,POTP_NAZIV from potpotpolja where PPO_PO_POLJEID="+
                        fld+" and PPO_POTPOLJAID='"+sfld+"' order by POTPID",true);
      jComboBox4.setSelectedIndex(0);
      setNameSField();
      setNameSSField();
      trans=false;
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameSField();
      trans=true;
      String fld = ((String)jComboBox2.getSelectedItem()).trim();
      String sfld = ((String)jComboBox3.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct POTPID,POTP_NAZIV from potpotpolja where PPO_PO_POLJEID="+
  	                fld+" and PPO_POTPOLJAID='"+sfld+"' order by POTPID",true);
      jComboBox4.setSelectedIndex(0);
      setNameSSField();
      trans=false;
    }
  }
  void jComboBox4_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameSSField();
    }
  }
}
