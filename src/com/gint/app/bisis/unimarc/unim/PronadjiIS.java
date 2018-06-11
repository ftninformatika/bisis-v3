
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

public class PronadjiIS extends JDialog {
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JComboBox jComboBox3 = new JComboBox();
  JLabel jLabel3 = new JLabel();

  Connection conn;

  DBComboModel dbcm1;
  DBComboModel dbcm2;
  DBComboModel dbcm3;

  Isifarnik isf;
  String fld="";
  String sfld="";
  String iSifr="";
  boolean trans;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  public PronadjiIS(Connection conn, Isifarnik isf) {
    try  {
      this.conn = conn;
      this.isf = isf;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel2.setText(Messages.get("PRONADJIIS_LABEL_SUBFIELD"));
    jLabel1.setText(Messages.get("PRONADJIIS_LABEL_FIELD"));
    jComboBox2.setMinimumSize(new Dimension(40, 21));
    jComboBox2.setPreferredSize(new Dimension(40, 21));
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox1.setPreferredSize(new Dimension(58, 21));
    jComboBox1.setMinimumSize(new Dimension(58, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJIIS_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jButton1.setText(Messages.get("PRONADJIIS_BUTTON_CANCEL"));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText(Messages.get("PRONADJIIS_BUTTON_OK"));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(450, 150));
    jPanel4.setPreferredSize(new Dimension(450, 150));
    jComboBox3.setMinimumSize(new Dimension(175, 21));
    jComboBox3.setPreferredSize(new Dimension(175, 21));
    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox3_actionPerformed(e);
      }
    });
    jLabel3.setText(Messages.get("PRONADJIIS_LABEL_INTERNCODER"));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(500);
    jLabel4.setForeground(new java.awt.Color(50, 54, 120));
    jLabel5.setForeground(new java.awt.Color(50, 54, 120));
    jLabel6.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 450, 129));
    jPanel4.add(jLabel1, new XYConstraints(15, 25, -1, -1));
    jPanel4.add(jLabel2, new XYConstraints(15, 65, 81, -1));
    jPanel4.add(jComboBox1, new XYConstraints(120, 25, -1, -1));
    jPanel4.add(jLabel3, new XYConstraints(15, 105, -1, -1));
    jPanel4.add(jComboBox2, new XYConstraints(120, 65, -1, -1));
    jPanel4.add(jComboBox3, new XYConstraints(120, 105, 129, -1));
    jPanel4.add(jLabel4, new XYConstraints(185, 25, -1, -1));
    jPanel4.add(jLabel5, new XYConstraints(185, 65, -1, -1));
    jPanel4.add(jLabel6, new XYConstraints(260, 105, -1, -1));
    jPanel1.add(jButton2, new XYConstraints(115, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(220, 150, -1, -1));
    this.setModal(true);

    fld=isf.getField().trim();
    sfld=isf.getSField().trim();
    iSifr=isf.getISifarnik().trim();

    trans=true;
    dbcm1 = new DBComboModel(conn,"select distinct isf.PPO_PO_POLJEID,p.PO_NAZIV from interni_sifarnik isf, polje p where isf.PPO_PO_POLJEID=p.POLJEID order by isf.PPO_PO_POLJEID",true);
    dbcm2 = new DBComboModel(conn,"select distinct isf.PPO_POTPOLJAID,pp.PP_NAZIV from interni_sifarnik isf,potpolja pp"+
                              " where isf.PPO_POTPOLJAID=pp.POTPOLJAID and isf.PPO_PO_POLJEID=pp.PO_POLJEID and isf.PPO_PO_POLJEID="+fld+" order by isf.PPO_POTPOLJAID",true);
    dbcm3 = new DBComboModel(conn,"select distinct ISID,IS_NAZIV from interni_sifarnik"+
    			      " where PPO_PO_POLJEID="+fld+" and PPO_POTPOLJAID='"+sfld+"' order by ISID",true);

    jComboBox1.setModel(dbcm1);
    jComboBox1.setSelectedItem(fld);
    jComboBox2.setModel(dbcm2);
    jComboBox2.setSelectedItem(sfld);
    jComboBox3.setModel(dbcm3);
    jComboBox3.setSelectedItem(iSifr);
    setNameField();
    setNameSField();
    setNameISif();
    trans=false;
  }

  void setNameField() {
    jLabel4.setText((String)dbcm1.getNameAt(jComboBox1.getSelectedIndex()));
  }
  void setNameSField() {
    jLabel5.setText((String)dbcm2.getNameAt(jComboBox2.getSelectedIndex()));
  }
  void setNameISif() {
    jLabel6.setText((String)dbcm3.getNameAt(jComboBox3.getSelectedIndex()));
  }
  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }
  void jButton2_actionPerformed(ActionEvent e)  {
    String fld = ((String)jComboBox1.getSelectedItem()).trim();
    String sfld = ((String)jComboBox2.getSelectedItem()).trim();
    String iSifr = ((String)jComboBox3.getSelectedItem()).trim();
    isf.find(fld,sfld,iSifr);
    dispose();
  }
  void jComboBox1_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      dbcm2.reloadItems("select distinct isf.PPO_POTPOLJAID,pp.PP_NAZIV from interni_sifarnik isf, potpolja pp"+
  	                " where isf.PPO_POTPOLJAID=pp.POTPOLJAID and isf.PPO_PO_POLJEID=pp.PO_POLJEID and isf.PPO_PO_POLJEID="+
  			fld+" order by isf.PPO_POTPOLJAID",true);
      String sfld = ((String)jComboBox2.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct ISID,IS_NAZIV from interni_sifarnik where PPO_PO_POLJEID="+
  	                fld+" and PPO_POTPOLJAID='"+sfld+"' order by ISID",true);
      jComboBox2.setSelectedIndex(0);
      setNameSField();
      jComboBox3.setSelectedIndex(0);
      setNameISif();
      trans=false;
    }
  }
  void jComboBox2_actionPerformed(ActionEvent e)  {
    if(!trans) {
      setNameSField();
      trans=true;
      String fld = ((String)jComboBox1.getSelectedItem()).trim();
      String sfld = ((String)jComboBox2.getSelectedItem()).trim();
      dbcm3.reloadItems("select distinct ISID,IS_NAZIV from interni_sifarnik where PPO_PO_POLJEID="+
  	                fld+" and PPO_POTPOLJAID='"+sfld+"' order by ISID",true);
      jComboBox3.setSelectedIndex(0);
      setNameISif();
      trans=false;
    }
  }
  void jComboBox3_actionPerformed(ActionEvent e)  {
    if(!trans)
      setNameISif();
  }
}
