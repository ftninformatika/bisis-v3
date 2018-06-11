package com.gint.app.bisis.editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

public class ExpandProgressDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lMessage = new JLabel();
  JButton bCancel = new JButton();
  Timer timer;

  public ExpandProgressDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(95);
    xYLayout1.setWidth(185);
    lMessage.setHorizontalAlignment(SwingConstants.CENTER);
    lMessage.setText(Messages.get("BISISAPP_EXPANDDLG_EXPANDINPROGRESS"));
    bCancel.setText(Messages.get("BISISAPP_EXPANDDLG_BUTTONABORT"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(lMessage, new XYConstraints(1, 16, 184, -1));
    panel1.add(bCancel, new XYConstraints(40, 51, 98, -1));
    setSize(185, 95);
    getRootPane().setDefaultButton(bCancel);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    timer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (isFinished()) {
          timer.stop();
          setVisible(false);
        }
      }
    });
  }

  public synchronized void setFinished(boolean finished) {
    this.finished = finished;
  }

  public synchronized boolean isFinished() {
    return finished;
  }

  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }

  public boolean isCanceled() {
    return canceled;
  }

  private boolean canceled;
  private boolean finished;

  void bCancel_actionPerformed(ActionEvent e) {
    setCanceled(true);
    try { Environment.getReadTs().cancelExpand(); } catch (Exception ex) {}
  }
}
