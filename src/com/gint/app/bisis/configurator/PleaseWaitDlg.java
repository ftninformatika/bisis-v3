package com.gint.app.bisis.configurator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog da se korisnik zabavi dok se izvrsava proces azuriranja
    JAR fajlova.
  */
public class PleaseWaitDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lPleaseWait = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  ImageIcon circle0 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle0.gif"));
  ImageIcon circle1 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle1.gif"));
  ImageIcon circle2 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle2.gif"));
  ImageIcon circle3 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle3.gif"));
  ImageIcon circle4 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle4.gif"));
  ImageIcon circle5 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle5.gif"));
  ImageIcon circle6 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle6.gif"));
  ImageIcon circle7 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle7.gif"));
  ImageIcon circle8 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle8.gif"));
  ImageIcon circle9 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/configurator/images/circle9.gif"));
  Timer timer;

  public PleaseWaitDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public PleaseWaitDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lPleaseWait.setText(Messages.get("PLEASEWAITDLG_LABEL_PLEASEWAIT"));
    lPleaseWait.setIcon(circle0);
    xYLayout1.setHeight(67);
    xYLayout1.setWidth(303);
    getContentPane().add(panel1);
    panel1.add(lPleaseWait, new XYConstraints(16, 22, -1, -1));
    setSize(303, 67);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    images = new ImageIcon [10];
    images[0] = circle0;
    images[1] = circle1;
    images[2] = circle2;
    images[3] = circle3;
    images[4] = circle4;
    images[5] = circle5;
    images[6] = circle6;
    images[7] = circle7;
    images[8] = circle8;
    images[9] = circle9;
    timer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (isFinished()) {
          timer.stop();
          JOptionPane.showMessageDialog(PleaseWaitDlg.this,
            Messages.get("PLEASEWAITDLG_MESSAGEDLG_FINISHED"),
            Messages.get("PLEASEWAITDLG_MESSAGEDLG_FRAMETITLE"),
            JOptionPane.INFORMATION_MESSAGE);
          setVisible(false);
        } else {
          if (++count == 10)
            count = 0;
          lPleaseWait.setIcon(images[count]);
        }
      }
    });
  }

  /** Postavlja fleg koji oznacava da li je proces azuriranja zavrsen. */
  public synchronized void setFinished(boolean finished) {
    this.finished = finished;
  }

  /** Vraca fleg koji oznacava da li je proces azuriranja zavrsen. */
  public synchronized boolean isFinished() {
    return finished;
  }

  /** Fleg koji oznacava da li je proces azuriranja zavrsen. */
  private boolean finished = false;
  /** Brojac 0->9 */
  private int count = 0;
  /** Slicice koje se vrte u krug. */
  private ImageIcon[] images;
}

