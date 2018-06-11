
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
import com.borland.jbcl.layout.*;
import com.gint.app.bisis.unimarc.dbmodel.*;

public class PronadjiTES extends JDialog {
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel4 = new JPanel();

  Connection conn;
  DBComboModel dbcm;
  Tesifarnika tes;
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();


  public PronadjiTES(Connection conn,Tesifarnika tes) {
    try  {
      this.conn=conn;
      this.tes=tes;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setText(Messages.get("PRONADJITES_LABEL_TYPECODEEXTERN"));
    jComboBox1.setMinimumSize(new Dimension(45, 21));
    jComboBox1.setPreferredSize(new Dimension(45, 21));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    this.setResizable(false);
    this.setTitle(Messages.get("PRONADJITES_DIALOGTITLE_FIND"));
    this.getContentPane().add(jPanel1);
    jPanel1.setLayout(xYLayout1);
    jButton1.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton1.setPreferredSize(new Dimension(80, 21));
    jButton1.setText(Messages.get("PRONADJITES_BUTTON_CANCEL"));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 11));
    jButton2.setPreferredSize(new Dimension(80, 21));
    jButton2.setText(Messages.get("PRONADJITES_BUTTON_OK"));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel4.setLayout(xYLayout2);
    jPanel4.setMinimumSize(new Dimension(350, 80));
    jPanel4.setPreferredSize(new Dimension(350, 80));
    xYLayout1.setHeight(200);
    xYLayout1.setWidth(350);
    jLabel3.setText(Messages.get("PRONADJITES_LABEL_CODER"));
    jLabel2.setForeground(new java.awt.Color(50, 54, 120));
    jPanel1.add(jPanel4, new XYConstraints(10, 10, 363, -1));
    jPanel4.add(jComboBox1, new XYConstraints(145, 25, -1, -1));
    jPanel4.add(jLabel1, new XYConstraints(25, 25, 115, -1));
    jPanel4.add(jLabel2, new XYConstraints(210, 25, 170, -1));
    jPanel4.add(jLabel3, new XYConstraints(25, 40, -1, -1));
    jPanel1.add(jButton2, new XYConstraints(75, 150, -1, -1));
    jPanel1.add(jButton1, new XYConstraints(180, 150, -1, -1));
    this.setModal(true);

    dbcm = new DBComboModel(conn,"select TESID,TES_NAZIV from tipekst_sifarnika order by TESID",true);
    jComboBox1.setModel(dbcm);
    jComboBox1.setSelectedItem(tes.getCurrentTES().trim());
    setName();
  }

  void setName() {
    jLabel2.setText((String)dbcm.getNameAt(jComboBox1.getSelectedIndex()));
  }

  void jButton1_actionPerformed(ActionEvent e)  {
    dispose();
  }

  void jButton2_actionPerformed(ActionEvent e)  {
    tes.find(((String)jComboBox1.getSelectedItem()).trim());
    dispose();
  }

  void jComboBox1_actionPerformed(ActionEvent e)  {
    setName();
  }
}
