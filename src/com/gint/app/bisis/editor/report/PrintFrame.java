package com.gint.app.bisis.editor.report;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import com.borland.jbcl.layout.*;

public class PrintFrame extends JFrame {
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenuItem jMenuItem4 = new JMenuItem();
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenu jMenu3 = new JMenu();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenuItem jMenuItem7 = new JMenuItem();
  JMenuItem jMenuItem11 = new JMenuItem();
  JMenu jMenu4 = new JMenu();
  JMenu jMenu5 = new JMenu();
  JMenu jMenu6 = new JMenu();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem8 = new JMenuItem();
  JMenuItem jMenuItem9 = new JMenuItem();
  JMenuItem jMenuItem10 = new JMenuItem();
  JMenuItem jMenuItem12 = new JMenuItem();
  XYLayout xYLayout1 = new XYLayout();

  Connection conn;

  public PrintFrame(Connection conn) {
    this.conn = conn;
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(xYLayout1);
    xYLayout1.setHeight(300);
    xYLayout1.setWidth(500);
    jMenu1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_CARDS"));
    jMenuItem1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_ABCD"));
    jMenuItem3.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_UPUTNI"));
    jMenuItem4.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_ZBIRNI"));
    jMenu2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_INVENTORY"));
    jMenuItem5.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_INVBOOK"));
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });
    jMenu3.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_INFORMATION"));
    jMenuItem6.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_OPSISP"));
    jMenuItem7.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_PROISP"));
    jMenuItem11.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_BILISP"));
    jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem11_actionPerformed(e);
      }
    });

    jMenu4.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_BIBLS"));
    jMenuItem10.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_BIBL"));
    jMenuItem12.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_BIBL2"));
    jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem10_actionPerformed(e);
      }
    });
    jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem12_actionPerformed(e);
      }
    });

    jMenu5.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_EXIT"));
    jMenu5.addMenuListener(new javax.swing.event.MenuListener() {
      public void menuSelected(MenuEvent e) {
        jMenu5_menuSelected(e);
      }
      public void menuDeselected(MenuEvent e) {
      }
      public void menuCanceled(MenuEvent e) {
      }
    });

    jMenu6.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_SPOREDNI"));
    jMenuItem2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_AUTOR"));
    jMenuItem8.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_PRED"));
    jMenuItem9.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_UDK"));


    this.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_PRINTFRM_PRINT"));
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenuBar1.add(jMenu3);
    jMenuBar1.add(jMenu4);
    jMenuBar1.add(jMenu5);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenu6);
    jMenu1.add(jMenuItem3);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem4);
    jMenu2.add(jMenuItem5);
    jMenu3.add(jMenuItem6);
    jMenu3.add(jMenuItem7);
    jMenu3.add(jMenuItem11);
    jMenu4.add(jMenuItem10);
    jMenu4.add(jMenuItem12);
    jMenu6.add(jMenuItem2);
    jMenu6.add(jMenuItem8);
    jMenu6.add(jMenuItem9);

    this.getContentPane().add(jMenuBar1, new XYConstraints(8, 12, -1, -1));

    setSize(500, 300);
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
     InventarFrame fInventar = new InventarFrame(conn);
     fInventar.setVisible(true);
  }

  void jMenuItem10_actionPerformed(ActionEvent e) {
     BibliografijaFrame fBibliografija = new BibliografijaFrame(conn);
     fBibliografija.setVisible(true);
  }

  void jMenuItem12_actionPerformed(ActionEvent e) {
    BibliografijaFrame2 fBibliografija = new BibliografijaFrame2(conn);
    fBibliografija.setVisible(true);
 }

  void jMenuItem11_actionPerformed(ActionEvent e) {
     BiltenPrinovljeneFrame fBilten = new BiltenPrinovljeneFrame(conn);
     fBilten.setVisible(true);
  }

  void jMenu5_menuSelected(MenuEvent e) {
     setVisible(false);
  }

}