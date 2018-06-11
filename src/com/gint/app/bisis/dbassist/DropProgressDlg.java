package com.gint.app.bisis.dbassist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog koji se prikazuje dok se sema baze uklanja. Zatvara se
    iz drugog threada kada se tamo postavi setPhase(2)
  */
public class DropProgressDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lDrop = new JLabel();
  JProgressBar pbProgress = new JProgressBar();
  ImageIcon finger = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/dbassist/images/fingerright.gif"));
  ImageIcon check = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/dbassist/images/check.gif"));
  Timer timer;

  public DropProgressDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DropProgressDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lDrop.setText(Messages.get("DROPPROGRESSDLG_FRAMETITLE"));
    lDrop.setIcon(finger);
    xYLayout1.setHeight(128);
    xYLayout1.setWidth(400);
    pbProgress.setStringPainted(true);
    getContentPane().add(panel1);
    panel1.add(lDrop, new XYConstraints(23, 24, -1, -1));
    panel1.add(pbProgress, new XYConstraints(20, 75, 358, 26));
    setSize(400, 128);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    timer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        pbProgress.setValue(getValue());
        if (getPhase() == 2) {
          lDrop.setIcon(check);
          JOptionPane.showMessageDialog(null,
            Messages.get("DROPPROGRESSDLG_SUCCESSDLG_MESSAGE"),
            Messages.get("DROPPROGRESSDLG_SUCCESSDLG_FRAMETITLE"),
            JOptionPane.INFORMATION_MESSAGE);
          setVisible(false);
          System.exit(0); // <-- !!!!
        }
      }
    });
  }

  private int phase = 1;
  private int value = 0;

  /** Postavlja vrednost za progress bar. */
  public synchronized void setValue(int value) {
    this.value = value;
  }

  /** Vraca trenutno stanje progress bara. */
  public synchronized int getValue() {
    return value;
  }

  /** Postavlja fazu u kojoj se nalazi proces uklanjaja:
      1 - u toku; 2 - gotovo. */
  public synchronized void setPhase(int phase) {
    this.phase = phase;
  }

  /** Vraca fazu u kojoj se nalazi proces uklanjanja:
      1 - u toku; 2 - gotovo. */
  public synchronized int getPhase() {
    return phase;
  }
}

