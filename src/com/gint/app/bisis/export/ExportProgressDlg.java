package com.gint.app.bisis.export;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog za prikaz tekuceg stanja procesa eksportovanja zapisa. */
public class ExportProgressDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lMessage = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JProgressBar pbProgress = new JProgressBar();
  Timer timer;

  public ExportProgressDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ExportProgressDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lMessage.setText(Messages.get("EXPORTPROGRESSDLG_LABEL_MESSAGE_INIT"));
    xYLayout1.setHeight(85);
    xYLayout1.setWidth(271);
    pbProgress.setStringPainted(true);
    getContentPane().add(panel1);
    panel1.add(lMessage, new XYConstraints(13, 12, 241, -1));
    panel1.add(pbProgress, new XYConstraints(13, 43, 242, 24));
    setSize(271, 85);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    timer = new Timer(250, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (isFinished()) {
          timer.stop();
          setVisible(false);
        } else {
          int c = getCurrent();
          pbProgress.setValue(c);
          lMessage.setText(
            Messages.get("EXPORTPROGRESSDLG_LABEL_MESSAGE_EXPORTFROM") + 
            " " + c + " " + 
            Messages.get("EXPORTPROGRESSDLG_LABEL_MESSAGE_EXPORTTO") + 
            " " + total);
        }
      }
    });
  }

  /** Inicijalizacija komponenti dijaloga pre nego sto se krene
      sa procesom eksportovanja.
    */
  public void initJob(int from, int to) {
    this.from = from;
    this.to = to;
    total = to - from + 1;
    pbProgress.setMinimum(from);
    pbProgress.setMaximum(to);
    finished = false;
    lMessage.setText(
      Messages.get("EXPORTPROGRESSDLG_LABEL_MESSAGE_EXPORTFROM") + 
      " " + from + " " + 
      Messages.get("EXPORTPROGRESSDLG_LABEL_MESSAGE_EXPORTTO") + 
      " " + total);
  }

  /** Postavlja pocetnu vrednost za progress bar. */
  public synchronized void setFrom(int from) {
    this.from = from;
  }

  /** Postavlja krajnju vrednost za progress bar. */
  public synchronized void setTo(int to) {
    this.to = to;
  }

  /** Postavlja tekucu vrednost za progress bar. */
  public synchronized void setCurrent(int current) {
    this.current = current;
  }

  /** Postavlja fleg da je proces zavrsen - dijalog moze da se zatvori. */
  public synchronized void setFinished(boolean finished) {
    this.finished = finished;
  }

  /** Vraca pocetnu vrednost za progress bar. */
  public synchronized int getFrom() {
    return from;
  }

  /** Vraca krajnju vrednost za progress bar. */
  public synchronized int getTo() {
    return to;
  }

  /** Vraca tekucu vrednost za progress bar. */
  public synchronized int getCurrent() {
    return current;
  }

  /** Vraca stanje procesa -- da li je zavrsen ili ne. */
  public synchronized boolean isFinished() {
    return finished;
  }

  private int from = 0;
  private int to = 0;
  private boolean finished = false;
  private int current = 0;
  private int total = 0;
}
