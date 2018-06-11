package com.gint.app.bisis.export;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/** Dijalog za biranje da li se polja 000 i 9xx ignorisu. */
public class IgnoreFieldsDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JCheckBox cbIgnore000 = new JCheckBox();
  JCheckBox cbIgnore9xx = new JCheckBox();
  JButton bOK = new JButton();
  JCheckBox cbTrim = new JCheckBox();
  JTextField tfTrim = new JTextField();
  JLabel lTrim = new JLabel();

  public IgnoreFieldsDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public IgnoreFieldsDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    cbIgnore000.setText(Messages.get("IGNOREFIELDSDLG_COMBOBOX_IGNORE000"));
    cbIgnore9xx.setText(Messages.get("IGNOREFIELDSDLG_COMBOBOX_IGNORE9XX"));
    bOK.setText(Messages.get("IGNOREFIELDSDLG_BUTTON_OK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bOK.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/check.gif")));
    xYLayout1.setHeight(188);
    xYLayout1.setWidth(322);
    cbTrim.setText(Messages.get("IGNOREFIELDSDLG_LABEL_IGNOREFIRST"));
    tfTrim.setText("10");
    lTrim.setForeground(Color.black);
    lTrim.setText(Messages.get("IGNOREFIELDSDLG_LABEL_CHARACTERS"));
    getContentPane().add(panel1);
    panel1.add(cbIgnore9xx, new XYConstraints(18, 109, -1, -1));
    panel1.add(cbIgnore000, new XYConstraints(18, 64, -1, -1));
    panel1.add(cbTrim, new XYConstraints(18, 26, -1, -1));
    panel1.add(tfTrim, new XYConstraints(126, 28, 74, -1));
    panel1.add(lTrim, new XYConstraints(213, 30, -1, -1));
    panel1.add(bOK, new XYConstraints(101, 144, 122, -1));
    setSize(181, 188);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  void bOK_actionPerformed(ActionEvent e) {
    Config.setIgnore000(cbIgnore000.isSelected());
    Config.setIgnore9xx(cbIgnore9xx.isSelected());
    Config.setTrimFirst(cbTrim.isSelected());
    int howMany = 0;
    try { howMany = Integer.parseInt(tfTrim.getText().trim()); } 
    catch (NumberFormatException ex) { }
    Config.setTrimHowMany(howMany);
    setVisible(false);
  }
}
