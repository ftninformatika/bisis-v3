package com.gint.app.bisis.editor.edit;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.*;
import com.objectspace.jgl.HashMap;
import com.borland.jbcl.layout.*;

import com.gint.util.string.Charset;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.UFieldSet.*;

public class IndDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lTitle = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lInd1 = new JLabel();
  JLabel lInd2 = new JLabel();
  JComboBox cbInd1 = new JComboBox();
  JComboBox cbInd2 = new JComboBox();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  EditDlg ed;

  public IndDlg(EditDlg frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    this.ed = frame;
  }

  public IndDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lTitle.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_LABELVALUES"));
    lInd1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_LABELIND1"));
    lInd2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_LABELIND1"));
    bOK.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_BUTTONOK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    cbInd1.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        bCancel.doClick();
      }
    }, "", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);
    cbInd2.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        bCancel.doClick();
      }
    }, "", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);
    xYLayout1.setHeight(194);
    xYLayout1.setWidth(389);
    getContentPane().add(panel1);
    panel1.add(lTitle, new XYConstraints(80, 9, 170, -1));
    panel1.add(lInd1, new XYConstraints(14, 48, 47, -1));
    panel1.add(lInd2, new XYConstraints(14, 90, 47, -1));
    panel1.add(cbInd1, new XYConstraints(76, 43, 293, -1));
    panel1.add(cbInd2, new XYConstraints(76, 85, 293, -1));
    panel1.add(bCancel, new XYConstraints(200, 145, 98, -1));
    panel1.add(bOK, new XYConstraints(82, 145, 98, -1));
    setSize(389, 194);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    getRootPane().setDefaultButton(bOK);
  }

  /** Redefinisemo setVisible da obavi jos posla. */
  public void setVisible(boolean v) {
    if (!v) {
      super.setVisible(false);
      return;
    }
    String s;
    int row = ed.getSelectedRow();
    int col = ed.getSelectedColumn();
    Field f = (Field)ed.utm.getValueAt(row, col);
    String fname1 = f.getName();
    UField fi = Environment.getFs().getField(fname1);
    HashMap hm1 = fi.getFirst();
    HashMap hm2 = fi.getSecond();
    if (hm1 == null && hm2 == null) {
       JOptionPane.showMessageDialog(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_FIRSTANDSECINDARENOTDEFINED"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
       return;
    }
    else {
      lTitle.setText("   " + com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_INDICATORS") + " - "+fname1);
      cbInd1.setEnabled(true);
      cbInd2.setEnabled(true);
      // obrisemo sve item-e
      if (cbInd1.getItemCount() > 0)
        cbInd1.removeAllItems();
      if (cbInd2.getItemCount() > 0)
        cbInd2.removeAllItems();
      if (fi != null) {
        if (hm1 != null) {
          // pokupim prvi indikator
          String tekuci = f.getInd1();
          Enumeration e;
          e = hm1.elements();
          cbInd1.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_UNSPECIFIED"));
          // dodam iz UNIMARC-a sve idnikatore za tekuce polje
          while (e.hasMoreElements()) {
            UInd ind1 = (UInd)e.nextElement();
            s = ind1.getIndID()+" - "+Charset.YUSCII2Unicode(ind1.getName());
            cbInd1.addItem(new String(s));
            // ako se tekuci indikator poklapa sa ovim iz UNIMARC-a,
            // selektujemo ga.
            if (tekuci.equals(ind1.getIndID()))
              cbInd1.setSelectedItem(s);
          }
          if (tekuci.equals(" "))
             cbInd1.setSelectedItem("Neodredjena vrednost");
        }
        else {
          cbInd1.setEnabled(false);
        }
      }
      // isto uradimo i za drugi indikator
      if (fi != null) {
        if (hm2 != null) {
          String tekuci = f.getInd2();
          Enumeration e;
          e = hm2.elements();
          cbInd2.addItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_UNSPECIFIED"));
          while (e.hasMoreElements()) {
            UInd ind2 = (UInd)e.nextElement();
            s = ind2.getIndID()+" - " + Charset.YUSCII2Unicode(ind2.getName());
            cbInd2.addItem(new String(s));
            if (tekuci.equals(ind2.getIndID()))
              cbInd2.setSelectedItem(s);
          }
          if (tekuci.equals(" "))
             cbInd2.setSelectedItem(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_UNSPECIFIED"));
        }
        else {
          cbInd2.setEnabled(false);
        }
      }
    }
    if (cbInd1.isEnabled())
      cbInd1.requestFocus();
    else
      cbInd2.requestFocus();
    super.setVisible(v);
  }

  void bOK_actionPerformed(ActionEvent e) {
    int row = ed.getSelectedRow();
    int col = ed.getSelectedColumn();
    Field f = (Field)ed.utm.getValueAt(row, col);
    String fname1 = f.getName();
    UField fi = Environment.getFs().getField(fname1);
    String ind1 = "";
    String ind2 = "";
    if (fi.getFirst() != null) {
      if (((String)cbInd1.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_UNSPECIFIED"))) {
         f.setInd1(" ");
         ind1 = "*";
      }
      else {
         ind1 = ((String)cbInd1.getSelectedItem()).substring(0,1);
         f.setInd1(ind1);
      }
    }
    if (fi.getSecond() != null) {
      if (((String)cbInd2.getSelectedItem()).equals(com.gint.app.bisis.editor.Messages.get("BISISAPP_INDDLG_UNSPECIFIED"))) {
         f.setInd2(" ");
         ind2 = "*";
      }
      else {
         ind2 = ((String)cbInd2.getSelectedItem()).substring(0,1);
         f.setInd2(ind2);
      }
    }
    ed.showDescription();
    setVisible(false);
  }

  void bCancel_actionPerformed(ActionEvent e) {
    setVisible(false);
  }
}

