package com.gint.app.bisis.editor;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/**
  Jednolinijski dijalog boks za unos upita u DIALOG jeziku.
*/
public class SingleLineDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lSelect = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JTextField tfSingleLine = new JTextField();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  MainFrame parent;

  public SingleLineDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    this.parent = (MainFrame)frame;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public SingleLineDlg() {
    this(null, "", true);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lSelect.setText("select");
    xYLayout1.setHeight(132);
    xYLayout1.setWidth(400);
    bOK.setText(Messages.get("BISISAPP_SINGLELINEDLG_BUTTONOK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(Messages.get("BISISAPP_SINGLELINEDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    tfSingleLine.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfSingleLine_keyReleased(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(lSelect, new XYConstraints(9, 19, 49, -1));
    panel1.add(tfSingleLine, new XYConstraints(52, 17, 332, -1));
    panel1.add(bCancel, new XYConstraints(204, 82, -1, -1));
    panel1.add(bOK, new XYConstraints(102, 82, 90, -1));
    setSize(400, 132);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((d.width - getSize().width) / 2 , (d.height - getSize().height) / 2);
    getRootPane().setDefaultButton(bOK);
  }

  /** Unet upit se nalazi u ovom atributu. */
  private String query = "";

  /** Vraca otkucan upit. */
  public String getQuery() {
    return query;
  }

  void bOK_actionPerformed(ActionEvent e) {
    query = tfSingleLine.getText().trim();
    setVisible(false);
    parent.select(getQuery());
  }

  void bCancel_actionPerformed(ActionEvent e) {
    query = "";
    setVisible(false);
  }

  void tfSingleLine_keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
      bOK.doClick();
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      bCancel.doClick();
  }
}
