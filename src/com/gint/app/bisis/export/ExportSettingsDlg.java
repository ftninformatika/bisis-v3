package com.gint.app.bisis.export;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/** Dijalog za podesavanje parametara eksporta: opseg identifikatora
    zapisa (od-do) za koje se vrsi eksport.
  */
public class ExportSettingsDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lMsg1 = new JLabel();
  JTextField tfFrom = new JTextField();
  JLabel lMsg2 = new JLabel();
  JTextField tfTo = new JTextField();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();

  public ExportSettingsDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ExportSettingsDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lMsg1.setText(Messages.get("EXPORTSETTINGSDLG_LABEL_EXPORTFROM"));
    lMsg2.setText(Messages.get("EXPORTSETTINGSDLG_LABEL_EXPORTTO"));
    xYLayout1.setHeight(105);
    xYLayout1.setWidth(365);
    bOK.setText(Messages.get("EXPORTSETTINGSDLG_BUTTON_OK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bOK.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/check.gif")));
    bCancel.setText(Messages.get("EXPORTSETTINGSDLG_BUTTON_CANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bCancel.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/delete.gif")));
    getContentPane().add(panel1);
    panel1.add(lMsg1, new XYConstraints(15, 16, 123, -1));
    panel1.add(tfFrom, new XYConstraints(144, 14, 70, -1));
    panel1.add(lMsg2, new XYConstraints(228, 16, 38, -1));
    panel1.add(tfTo, new XYConstraints(273, 14, 70, -1));
    panel1.add(bCancel, new XYConstraints(192, 59, 134, -1));
    panel1.add(bOK, new XYConstraints(40, 59, 134, -1));
    getRootPane().setDefaultButton(bOK);
    setSize(365, 105);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  /** Vraca docID od koga se vrsi eksport. */
  public int getFrom() {
    return from;
  }

  /** Vraca docID do koga se vrsi eksport. */
  public int getTo() {
    return to;
  }

  private int from = -1;
  private int to = -1;

  void bOK_actionPerformed(ActionEvent e) {
    try {
      from = Integer.parseInt(tfFrom.getText().trim());
    } catch (NumberFormatException ex) {
      from = -1;
    }
    try {
      to = Integer.parseInt(tfTo.getText().trim());
    } catch (NumberFormatException ex) {
      to = -1;
    }
    setVisible(false);
  }

  void bCancel_actionPerformed(ActionEvent e) {
    setVisible(false);
  }
}
