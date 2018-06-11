package com.gint.app.bisis.export;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog koji prikazuje tekuce stanje procesa importa. Omogucava
    da se proces prekine.
  */
public class ImportProgressDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lCounter = new JLabel();
  JProgressBar pbProgress = new JProgressBar();
  JLabel lBadCount = new JLabel();
  JButton bCancel = new JButton();
  Timer timer;

  public ImportProgressDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ImportProgressDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lCounter.setText(Messages.get("IMPORTPROGRESSDLG_LABEL_COUNTER_INIT"));
    xYLayout1.setHeight(176);
    xYLayout1.setWidth(317);
    lBadCount.setText(Messages.get("IMPORTPROGRESSDLG_LABEL_BADCOUNT_INIT"));
    bCancel.setText(Messages.get("IMPORTPROGRESSDLG_BUTTON_CANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bCancel.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/delete.gif")));
    pbProgress.setStringPainted(true);
    getContentPane().add(panel1);
    panel1.add(lCounter, new XYConstraints(15, 19, 283, -1));
    panel1.add(pbProgress, new XYConstraints(13, 50, 285, 23));
    panel1.add(lBadCount, new XYConstraints(14, 87, 283, -1));
    panel1.add(bCancel, new XYConstraints(85, 118, 133, -1));
    setSize(317, 176);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    timer = new Timer(250, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (isFinished()) {
          timer.stop();
          setVisible(false);
        } else {
          int rc = getRecordCount();
          pbProgress.setValue(rc);
          lCounter.setText(
            Messages.get("IMPORTPROGRESSDLG_LABEL_COUNTER_SOFAR") + "  " + rc + "  " +
              Messages.get("IMPORTPROGRESSDLG_LABEL_COUNTER_OF") + "  " + 
              maxRecords);
          lBadCount.setText(Messages.get("IMPORTPROGRESSDLG_LABEL_BADCOUNT") + "  " +
            getBadCount());
        }
      }
    });
  }

  /** Inicijalzicija komponenti dijaloga pre pocetka procesa importa. */
  public void initJob(int maxRecords) {
    this.maxRecords = maxRecords;
    stopFlag = false;
    finished = false;
    recordCount = 0;
    badCount = 0;
    pbProgress.setMinimum(0);
    pbProgress.setMaximum(maxRecords);
  }

  /** Postavlja broj neispravnih zapisa. */
  public synchronized void setBadCount(int badCount) {
    this.badCount = badCount;
  }

  /** Vraca broj neispravnih zapisa. */
  public synchronized int getBadCount() {
    return badCount;
  }

  /** Postavlja fleg za zaustavljanje importa (kad se klikne na Cancel). */
  public synchronized void setStopFlag(boolean stopFlag) {
    this.stopFlag = stopFlag;
  }

  /** Vraca vrednost flega za zaustavljanje importa. */
  public synchronized boolean isStopFlag() {
    return stopFlag;
  }

  /** Postavlja tekucu vrednost za progress bar. */
  public synchronized void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }

  /** Vraca tekucu vrednost za progress bar. */
  public synchronized int getRecordCount() {
    return recordCount;
  }

  /** Postavlja fleg koji oznacava da je proces importovanja zavrsen. */
  public synchronized void setFinished(boolean finished) {
    this.finished = finished;
  }

  /* Vraca vrednost flega koji oznacava da je proces importovanja zavrsen. */
  public synchronized boolean isFinished() {
    return finished;
  }

  private int maxRecords = 0;
  private int badCount = 0;
  private boolean stopFlag = false;
  private int recordCount = 0;
  private boolean finished = false;

  void bCancel_actionPerformed(ActionEvent e) {
    setStopFlag(true);
  }
}

