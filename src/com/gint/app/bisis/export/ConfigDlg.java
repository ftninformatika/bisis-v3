package com.gint.app.bisis.export;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/** Dijalog za podesavanje tekuce konfiguracije aplikacije. */
public class ConfigDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lDBType = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lDriver = new JLabel();
  JLabel lURL = new JLabel();
  JLabel lDBusername = new JLabel();
  JLabel lDBpassword = new JLabel();
  JComboBox cbDBType = new JComboBox();
  JTextField tfDriver = new JTextField();
  JTextField tfURL = new JTextField();
  JPasswordField pfDBusername = new JPasswordField();
  JPasswordField pfDBpassword = new JPasswordField();
  JCheckBox cbConfirm = new JCheckBox();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();

  public ConfigDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ConfigDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    cbDBType.addItem("oracle");
    cbDBType.addItem("sapdb");
    lDBType.setText(Messages.get("CONFIGDLG_LABEL_DBTYPE"));
    lDriver.setText(Messages.get("CONFIGDLG_LABEL_DRIVER"));
    lURL.setText(Messages.get("CONFIGDLG_LABEL_URL"));
    lDBusername.setText(Messages.get("CONFIGDLG_LABEL_USERNAME"));
    lDBpassword.setText(Messages.get("CONFIGDLG_LABEL_PASSWORD"));
    cbConfirm.setText(Messages.get("CONFIGDLG_CHECKBOX_COMFIRMIMPORT"));
    xYLayout1.setHeight(300);
    xYLayout1.setWidth(369);
    bOK.setText(Messages.get("CONFIGDLG_BUTTON_OK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bOK.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/check.gif")));
    bCancel.setText(Messages.get("CONFIGDLG_BUTTON_CANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bCancel.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/delete.gif")));
    getContentPane().add(panel1);
    panel1.add(lDBType, new XYConstraints(14, 11, 57, -1));
    panel1.add(lDriver, new XYConstraints(14, 44, 57, -1));
    panel1.add(lURL, new XYConstraints(14, 77, 57, -1));
    panel1.add(lDBusername, new XYConstraints(14, 110, 88, -1));
    panel1.add(lDBpassword, new XYConstraints(14, 143, 84, -1));
    panel1.add(cbDBType, new XYConstraints(122, 9, 230, -1));
    panel1.add(tfDriver, new XYConstraints(122, 42, 230, -1));
    panel1.add(tfURL, new XYConstraints(122, 75, 230, -1));
    panel1.add(pfDBusername, new XYConstraints(122, 108, 180, -1));
    panel1.add(pfDBpassword, new XYConstraints(122, 141, 180, -1));
    panel1.add(cbConfirm, new XYConstraints(14, 189, 336, -1));
    panel1.add(bOK, new XYConstraints(52, 243, 115, -1));
    panel1.add(bCancel, new XYConstraints(190, 243, 115, -1));
    getRootPane().setDefaultButton(bOK);
    setSize(369, 300);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  /** Popunjava polja dijaloga na osnovu tekuceg stanja konfiguracije. */
  public void initData() {
    if (Config.getDBType().equals("oracle"))
      cbDBType.setSelectedIndex(0);
    else if (Config.getDBType().equals("sapdb"))
      cbDBType.setSelectedIndex(1);
    tfDriver.setText(Config.getDriver());
    tfURL.setText(Config.getUrl());
    pfDBusername.setText(Config.getDbUsername());
    pfDBpassword.setText(Config.getDbPassword());
    cbConfirm.setSelected(Config.getConfirm());
  }

  /** Azurira konfiguraciju na osnovu sadrzaja polja dijaloga. */
  public void setData() {
    Config.setDBType(cbDBType.getSelectedItem().toString());
    Config.setDriver(tfDriver.getText().trim());
    Config.setUrl(tfURL.getText().trim());
    Config.setDbUsername(new String(pfDBusername.getPassword()).trim());
    Config.setDbPassword(new String(pfDBpassword.getPassword()).trim());
    Config.setConfirm(cbConfirm.isSelected());
  }

  void bCancel_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void bOK_actionPerformed(ActionEvent e) {
    setData();
    setVisible(false);
  }
}

