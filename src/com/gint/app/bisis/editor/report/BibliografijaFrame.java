package com.gint.app.bisis.editor.report;

import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

import com.gint.app.bisis.editor.*;
import com.gint.util.string.UnimarcConverter;

public class BibliografijaFrame extends JFrame {
  XYLayout xYLayout1 = new XYLayout();
  JLabel lSortType = new JLabel();
  JComboBox cbSortFirst = new JComboBox();
  JLabel lOd = new JLabel();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
//  public ReportDlg reportDlg = new ReportDlg(this, "Bibliografija", true);
  public PrintingWindow reportDlg = new PrintingWindow(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_TITLE"), true);
  String[] lista = {com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LAU"),
                    com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LTI"),
                    com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LPY")};

  public BibliografijaFrame(Connection conn) {
//    int br=0;
    this.conn = conn;
    for (int i=0;i<lista.length;i++) {
      cbSortFirst.addItem(lista[i]); // br++
      if (i!=0)
        cbSortSecond.addItem(lista[i]); // br++
      if (i!=0 && i!= 1)
        cbSortThird.addItem(lista[i]); // br++
    }
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    lSortType.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LABELSORT"));
    this.getContentPane().setLayout(xYLayout1);
    bOK.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_BUTTONPRINT"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });

    cbSortFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbSortFirst_actionPerformed(e);
      }
    });

    cbSortSecond.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbSortSecond_actionPerformed(e);
      }
    });


    xYLayout1.setHeight(300);
    xYLayout1.setWidth(450);
    this.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_TITLE"));
    this.getContentPane().add(lSortType, new XYConstraints(35, 57, 136, 54));
    this.getContentPane().add(cbSortFirst, new XYConstraints(180, 69, 245, -1));
    this.getContentPane().add(cbSortSecond, new XYConstraints(180, 112, 244, 21));
    this.getContentPane().add(cbSortThird, new XYConstraints(181, 150, 242, 21));
    this.getContentPane().add(bOK, new XYConstraints(100, 200, 100, 35));
    this.getContentPane().add(bCancel, new XYConstraints(250, 200, 100, 35));
    setSize(450, 300);
  }


  void cbSortFirst_actionPerformed(ActionEvent e) {
    cbSortSecond.removeAllItems();
    for (int i=0;i<lista.length;i++) {
      if (lista[i].equals(cbSortFirst.getSelectedItem()));
      else
        cbSortSecond.addItem(lista[i]);
    }
  }

  void cbSortSecond_actionPerformed(ActionEvent e) {
    cbSortThird.removeAllItems();
    for (int i=0;i<lista.length;i++) {
      if (lista[i].equals(cbSortFirst.getSelectedItem()) ||
        lista[i].equals(cbSortSecond.getSelectedItem()));
      else
        cbSortThird.addItem(lista[i]);
    }
  }

  void bOK_actionPerformed(ActionEvent e) {
    Bibliografija bib = new Bibliografija(conn);
    int first=1;
    int last = -1;
    //try {
      last=Environment.getReadTs().getHitCount();
    //} catch (java.rmi.RemoteException ex) {
    //}
    if (first>last)
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_NODATA"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    else {
      int[] docIDs = new int[last-first+1];
      String[] docs = new String[last-first+1];
      String prvi="", drugi="", treci="";

      //try {
        if (Environment.getReadTs().getDocsRange(first, last, docIDs, docs)) {
          int i=0;
          Vector docIDsV = new Vector();
          Vector docsV = new Vector();
          for (i=0;i<docIDs.length;i++)
            docIDsV.add(new Integer(docIDs[i]));
          for (i=0;i<docs.length;i++)
            docsV.add(docs[i]);

          if (((String)cbSortFirst.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LAU")))
            prvi="AU";
          else if (((String)cbSortFirst.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LTI")))
            prvi="TI";
          else if (((String)cbSortFirst.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LPY")))
            prvi="PY";
          else
            prvi="??";

          if (((String)cbSortSecond.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LAU")))
            drugi="AU";
          else if (((String)cbSortSecond.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LTI")))
            drugi="TI";
          else if (((String)cbSortSecond.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LPY")))
            drugi="PY";
          else
            drugi="??";

          if (((String)cbSortThird.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LAU")))
            treci="AU";
          else if (((String)cbSortThird.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LTI")))
            treci="TI";
          else if (((String)cbSortThird.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_BIBLIOGR_LPY")))
            treci="PY";
          else
            treci="??";

          String HTML = bib.getBibliografija(prvi,drugi,treci,docIDsV, docsV);

          this.reportDlg.setHTML(HTML);
          this.reportDlg.setVisible(true);
        }
      //} catch (java.rmi.RemoteException ex) {
      //}
    }
  }



  void bCancel_actionPerformed(ActionEvent e) {
     this.dispose();
  }

  private UnimarcConverter conv = new UnimarcConverter();
  private Connection conn;
  JComboBox cbSortSecond = new JComboBox();
  JComboBox cbSortThird = new JComboBox();
}