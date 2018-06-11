package com.gint.app.bisis.editor;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

public class CancelSearchDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lMessage = new JLabel();
  JButton bCancel = new JButton();

  public CancelSearchDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public CancelSearchDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lMessage.setHorizontalAlignment(SwingConstants.CENTER);
    lMessage.setText(Messages.get("BISISAPP_CANCELSEARCHDLG_SEARCHINPROGRESS"));
    bCancel.setText(Messages.get("BISISAPP_CANCELSEARCHDLG_ABORT"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(102);
    xYLayout1.setWidth(191);
    getContentPane().add(panel1);
    panel1.add(lMessage, new XYConstraints(0, 20, 191, -1));
    panel1.add(bCancel, new XYConstraints(40, 61, 101, -1));
    setSize(191, 102);
    getRootPane().setDefaultButton(bCancel);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  public void setVisible(boolean v) {
    super.setVisible(v);
  }

  void bCancel_actionPerformed(ActionEvent e) {
    try { Environment.getReadTs().cancelSelect(); } catch (Exception ex) {}
  }

}
