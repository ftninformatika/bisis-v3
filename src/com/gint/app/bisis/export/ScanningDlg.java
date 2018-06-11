package com.gint.app.bisis.export;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog da zabavlja korisnika dok se import fajl "skenira". */
public class ScanningDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lMessage = new JLabel();
  ImageIcon circle0 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle0.gif"));
  ImageIcon circle1 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle1.gif"));
  ImageIcon circle2 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle2.gif"));
  ImageIcon circle3 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle3.gif"));
  ImageIcon circle4 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle4.gif"));
  ImageIcon circle5 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle5.gif"));
  ImageIcon circle6 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle6.gif"));
  ImageIcon circle7 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle7.gif"));
  ImageIcon circle8 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle8.gif"));
  ImageIcon circle9 = new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis/export/images/circle9.gif"));
  Timer timer;

  public ScanningDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ScanningDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lMessage.setText(Messages.get("SCANNINGDLG_MESSAGE"));
    lMessage.setIcon(circle0);
    xYLayout1.setHeight(71);
    xYLayout1.setWidth(204);
    getContentPane().add(panel1);
    panel1.add(lMessage, new XYConstraints(32, 23, -1, -1));
    setSize(204, 71);
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
          setVisible(false);
        } else {
          if (++count == 10)
            count = 0;
          lMessage.setIcon(images[count]);
        }
      }
    });
  }

  /** Postavlja fleg da li je proces zavrsen. */
  public synchronized void setFinished(boolean finished) {
    this.finished = finished;
  }

  /** Vraca fleg da li je proces zavrsen. */
  public synchronized boolean isFinished() {
    return finished;
  }

  /** Fleg da li je proces zavrsen. */
  private boolean finished = false;
  /** Brojac u krug: 0->9 */
  private int count = 0;
  /** Slicice koje se vrte u dijalogu, vezane za tajmer. */
  private ImageIcon[] images;
}

