package com.gint.app.bisis.dbassist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog koji prikazuje tekuce stanje procesa kreiranja
    seme baze podataka.
  */
public class ProgressDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lPhase1 = new JLabel();
  JLabel lPhase2 = new JLabel();
  JLabel lPhase3 = new JLabel();
  JLabel lPhase4 = new JLabel();
  JProgressBar pbProgress = new JProgressBar();
  ImageIcon finger = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/dbassist/images/fingerright.gif"));
  ImageIcon check = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/dbassist/images/check.gif"));
  ImageIcon blank = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/dbassist/images/blank.gif"));
  Timer timer;

  public ProgressDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ProgressDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lPhase1.setText(Messages.get("PROGRESSDLG_LABEL_PHASE1"));
    lPhase1.setIcon(finger);
    lPhase2.setText(Messages.get("PROGRESSDLG_LABEL_PHASE2"));
    lPhase2.setIcon(blank);
    lPhase3.setText(Messages.get("PROGRESSDLG_LABEL_PHASE3"));
    lPhase3.setIcon(blank);
    lPhase4.setText(Messages.get("PROGRESSDLG_LABEL_PHASE4"));
    lPhase4.setIcon(blank);
    xYLayout1.setHeight(271);
    xYLayout1.setWidth(400);
    pbProgress.setStringPainted(true);
    getContentPane().add(panel1);
    panel1.add(lPhase1, new XYConstraints(18, 20, 233, -1));
    panel1.add(lPhase2, new XYConstraints(18, 60, 233, -1));
    panel1.add(lPhase3, new XYConstraints(18, 99, 233, -1));
    panel1.add(lPhase4, new XYConstraints(18, 139, 233, -1));
    panel1.add(pbProgress, new XYConstraints(18, 195, 361, 23));
    setSize(400, 271);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    timer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        // ovde radi check faze. i postavi ikonu
        pbProgress.setValue(getValue());
        if (getPhase() == 2 && updated < 2) {
          lPhase1.setIcon(check);
          lPhase2.setIcon(finger);
          updated = 2;
        } else if (getPhase() == 3 && updated < 3) {
          lPhase2.setIcon(check);
          lPhase3.setIcon(finger);
          updated = 3;
        } else if (getPhase() == 4 && updated < 4) {
          lPhase3.setIcon(check);
          lPhase4.setIcon(finger);
          updated = 4;
        } else if (getPhase() == 5 && updated < 5) {
          lPhase4.setIcon(check);
          updated = 5;
          JOptionPane.showMessageDialog(null,
            Messages.get("PROGRESSDLG_SUCCESSDLG_MESSAGE"), 
            Messages.get("PROGRESSDLG_SUCCESSDLG_FRAMETITLE"), 
            JOptionPane.INFORMATION_MESSAGE);
          setVisible(false);
          System.exit(0); // <-- !!!!
        }
      }
    });
  }

  private int phase = 1;
  private int updated = 1;
  private int value = 0;

  /** Postavlja vrednost za progress bar. */
  public synchronized void setValue(int value) {
    this.value = value;
  }

  /** Vraca tekuce stanje progress bara. */
  public synchronized int getValue() {
    return value;
  }

  /** Postavlja vrednost za fazu u kojoj se nalazi proces kreiranja:<br>
      1 - kreiranje podseme za tekst server
      2 - kreiranje podseme za UNIMARC standard
      3 - kreiranje podseme za okruzenje bibliotekara
      4 - kreiranje podseme za cirkulaciju
    */
  public synchronized void setPhase(int phase) {
    this.phase = phase;
  }

  /** Vraca tekucu vrednost faze:<br>
      1 - kreiranje podseme za tekst server
      2 - kreiranje podseme za UNIMARC standard
      3 - kreiranje podseme za okruzenje bibliotekara
      4 - kreiranje podseme za cirkulaciju
    */
  public synchronized int getPhase() {
    return phase;
  }

}

