package com.gint.app.bisis.configurator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.gint.util.gui.SwingWorker;

/** Glavni frejm aplikacije. */
public class MainFrame extends JFrame {

  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel pConfig = new JPanel();
  JPanel pServer = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JPanel pRoot = new JPanel();
  CardLayout cardLayout1 = new CardLayout();

  // client tabbed pane components
  JLabel lClientLocale = new JLabel();
  JComboBox cmbClientLocale = new JComboBox();
  JLabel lDriver = new JLabel();
  JLabel lURL = new JLabel();
  JLabel lUsername = new JLabel();
  JLabel lPassword = new JLabel();
  JTextField tfDriver = new JTextField();
  JTextField tfURL = new JTextField();
  JPasswordField pfUsername = new JPasswordField();
  JPasswordField pfPassword = new JPasswordField();
  JLabel lDBType = new JLabel();
  JComboBox cmbDBType = new JComboBox();
  JLabel lValidator = new JLabel();
  JTextField tfValidator = new JTextField();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();

  // server tabbed pane components
  JLabel lServerLocale = new JLabel();
  JComboBox cmbServerLocale = new JComboBox();
  JLabel lBiblUsername = new JLabel();
  JLabel lBiblPassword = new JLabel();
  JPasswordField pfBiblUsername = new JPasswordField();
  JPasswordField pfBiblPassword = new JPasswordField();
  JLabel lValidator1 = new JLabel();
  JTextField tfURL1 = new JTextField();
  JPasswordField pfUsername1 = new JPasswordField();
  JTextField tfDriver1 = new JTextField();
  JTextField tfValidator1 = new JTextField();
  JLabel lURL1 = new JLabel();
  JPasswordField pfPassword1 = new JPasswordField();
  JLabel lDriver1 = new JLabel();
  JLabel lPassword1 = new JLabel();
  JLabel lUsername1 = new JLabel();
  JButton bOK1 = new JButton();
  JButton bCancel1 = new JButton();

  BorderLayout borderLayout1 = new BorderLayout();
  JLabel lPicture = new JLabel();
  JLabel lPicture1 = new JLabel();

  PleaseWaitDlg pleaseWaitDlg = new PleaseWaitDlg(this,
    Messages.get("MAINFRAME_PLEASEDLG_FRAMETITLE"), true);

  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception  {
    cmbDBType.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cmbDBType_actionPerformed(e);
      }
    });
    this.setSize(600, 450);
    setIconImage(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/icon.gif")).getImage());

    lDriver.setText(Messages.get("MAINFRAME_LABEL_DRIVER"));

    this.getContentPane().setLayout(borderLayout1);
    this.setTitle(Messages.get("MAINFRAME_FRAMETITLE"));

    lClientLocale.setText(Messages.get("MAINFRAME_LABEL_LOCALE"));
    cmbClientLocale.addItem(new ComboItem("Srpski", "sr"));
    cmbClientLocale.addItem(new ComboItem("English", "en"));
    cmbClientLocale.addItem(new ComboItem("Deutsch", "de"));
    cmbClientLocale.addItem(new ComboItem("\u0395\u03bb\u03bb\u03b5\u03bd\u03b9\u03ba\u03b7", "gr"));
    cmbClientLocale.addItem(new ComboItem("\u041c\u0430\u043a\u0435\u0434\u043e\u043d\u0441\u043a\u0438", "mk"));

    lServerLocale.setText(Messages.get("MAINFRAME_LABEL_LOCALE"));
    cmbServerLocale.addItem(new ComboItem("Srpski", "sr"));
    cmbServerLocale.addItem(new ComboItem("English", "en"));
    cmbServerLocale.addItem(new ComboItem("Deutsch", "de"));
    cmbServerLocale.addItem(new ComboItem("\u0395\u03bb\u03bb\u03b5\u03bd\u03b9\u03ba\u03b7", "gr"));
    cmbServerLocale.addItem(new ComboItem("\u041c\u0430\u043a\u0435\u0434\u043e\u043d\u0441\u043a\u0438", "mk"));

    lURL.setText(Messages.get("MAINFRAME_LABEL_URL"));
    lUsername.setText(Messages.get("MAINFRAME_LABEL_DBUSERNAME"));
    lPassword.setText(Messages.get("MAINFRAME_LABEL_DBPASSWORD"));

    lDBType.setText(Messages.get("MAINFRAME_LABEL_DBTYPE"));
    cmbDBType.addItem("sapdb");
    cmbDBType.addItem("oracle");

    bOK.setText(Messages.get("MAINFRAME_BUTTON_APPLY"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });

    bOK.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/check.gif")));
    bCancel.setText(Messages.get("MAINFRAME_BUTTON_CANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });

    bCancel.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/delete.gif")));

    tfDriver.setText("com.sap.dbtech.jdbc.DriverSapDB");
    tfURL.setText("jdbc:sapdb://zozon.tmd.ns.ac.yu/BISIS?sqlmode=ORACLE&unicode=yes");
    pfUsername.setText("bis");
    pfPassword.setText("bis");

    lValidator.setText(Messages.get("MAINFRAME_LABEL_VALIDATOR"));
    tfValidator.setText("com.gint.app.bisis.editor.edit.validation.NSFFValidator");
    lBiblUsername.setText(Messages.get("MAINFRAME_LABEL_LIBUSERNAME"));
    lBiblPassword.setText(Messages.get("MAINFRAME_LABEL_LIBPASSWORD"));
    pfBiblUsername.setText("demo");
    pfBiblPassword.setText("demo");
    pConfig.setLayout(xYLayout2);
    pServer.setLayout(xYLayout3);
    lValidator1.setText(Messages.get("MAINFRAME_LABEL_VALIDATOR"));
    tfURL1.setText("jdbc:sapdb://zozon.tmd.ns.ac.yu/BISIS?sqlmode=ORACLE&unicode=yes");
    pfUsername1.setText("bis");
    tfDriver1.setText("com.sap.dbtech.jdbc.DriverSapDB");
    tfValidator1.setText("com.gint.app.bisis.editor.edit.validation.NSFFValidator");
    lURL1.setText(Messages.get("MAINFRAME_LABEL_URL"));
    pfPassword1.setText("bis");
    lDriver1.setText("Driver:");
    lPassword1.setText(Messages.get("MAINFRAME_LABEL_DBPASSWORD"));
    lUsername1.setText(Messages.get("MAINFRAME_LABEL_DBUSERNAME"));
    bOK1.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/check.gif")));
    bOK1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK1_actionPerformed(e);
      }
    });
    bOK1.setText(Messages.get("MAINFRAME_BUTTON_APPLY"));
    bCancel1.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/delete.gif")));
    bCancel1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel1_actionPerformed(e);
      }
    });
    bCancel1.setText(Messages.get("MAINFRAME_BUTTON_CANCEL"));
    lPicture.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/wheels.gif")));
    lPicture1.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/configurator/images/wheels.gif")));
    jLabel1.setText(Messages.get("MAINFRAME_LABEL_DBTYPE"));
    cmbDBType1.addItem("sapdb");
    cmbDBType1.addItem("oracle");

    jLabel2.setText(Messages.get("MAINFRAME_LABEL_SERVERTYPE"));

    jLabel3.setText(Messages.get("MAINFRAME_LABEL_REPORTTYPE"));
    cmbDBType1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cmbDBType1_actionPerformed(e);
      }
    });

    jTabbedPane1.add(pConfig, Messages.get("MAINFRAME_TAB_CLIENT"));
    jTabbedPane1.add(pServer, Messages.get("MAINFRAME_TAB_SERVER"));
    this.getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
    pConfig.add(lClientLocale, new XYConstraints(125, 25, -1, -1));
    pConfig.add(cmbClientLocale, new XYConstraints(220, 25, -1, -1));
    pConfig.add(pfPassword, new XYConstraints(122, 208, 120, -1));
    pConfig.add(lDriver, new XYConstraints(24, 97, 89, -1));
    pConfig.add(tfDriver, new XYConstraints(122, 95, 435, -1));
    pConfig.add(lURL, new XYConstraints(24, 131, 89, -1));
    pConfig.add(tfURL, new XYConstraints(122, 129, 436, -1));
    pConfig.add(lUsername, new XYConstraints(24, 174, 89, -1));
    pConfig.add(pfUsername, new XYConstraints(122, 172, 120, -1));
    pConfig.add(lPassword, new XYConstraints(24, 211, 89, -1));
    pConfig.add(jLabel2, new XYConstraints(24, 284, -1, -1));
    pConfig.add(cmbTipServera1, new XYConstraints(122, 280, -1, -1));
    pConfig.add(lValidator, new XYConstraints(24, 252, 89, -1));
    pConfig.add(tfValidator, new XYConstraints(122, 249, 437, -1));
    pConfig.add(cmbTipIzvestaja, new XYConstraints(122, 318, -1, -1));
    pConfig.add(jLabel3, new XYConstraints(23, 321, -1, -1));
    pConfig.add(bOK,  new XYConstraints(122, 350, 123, -1));
    pConfig.add(bCancel1, new XYConstraints(261, 350, 123, -1));
    pConfig.add(lPicture, new XYConstraints(7, 10, 472, -1));
    pConfig.add(lDBType, new XYConstraints(24, 62, 89, -1));
    pConfig.add(cmbDBType, new XYConstraints(122, 60, 188, -1));
    pServer.add(lServerLocale, new XYConstraints(125, 25, -1, -1));
    pServer.add(cmbServerLocale, new XYConstraints(220, 25, -1, -1));
    pServer.add(pfUsername1, new XYConstraints(125, 151, 129, -1));
    pServer.add(lDriver1, new XYConstraints(28, 93, 89, -1));
    pServer.add(jLabel1, new XYConstraints(27, 65, -1, -1));
    pServer.add(cmbDBType1, new XYConstraints(126, 62, 182, -1));
    pServer.add(lUsername1, new XYConstraints(27, 153, 89, -1));
    pServer.add(lPassword1, new XYConstraints(27, 190, 89, -1));
    pServer.add(pfPassword1, new XYConstraints(125, 187, 129, -1));
    pServer.add(lValidator1, new XYConstraints(27, 226, 89, -1));
    pServer.add(tfValidator1, new XYConstraints(125, 223, 446, -1));
    pServer.add(lBiblUsername, new XYConstraints(27, 264, 94, -1));
    pServer.add(pfBiblUsername, new XYConstraints(125, 263, 127, -1));
    pServer.add(lBiblPassword, new XYConstraints(27, 300, 95, -1));
    pServer.add(pfBiblPassword, new XYConstraints(125, 297, 127, -1));
    pServer.add(bOK1, new XYConstraints(111, 345, 123, -1));
    pServer.add(tfDriver1, new XYConstraints(126, 91, 443, -1));
    pServer.add(tfURL1, new XYConstraints(125, 120, 444, -1));
    pServer.add(lURL1, new XYConstraints(28, 124, 89, -1));
    pServer.add(lPicture1, new XYConstraints(8, 8, 472, -1));
    pServer.add(bCancel, new XYConstraints(251, 345, 123, -1));

    cmbTipServera1.addItem("RMI server");

    cmbTipIzvestaja.addItem("Standard");
    cmbTipIzvestaja.addItem("SMIP");
  }

  private static final int CLIENT = 1;
  private static final int SERVER = 2;
  private static final int RMI_SERVER   = 0;
  private static final int ORION_SERVER = 1;
  private static final int STANDARD = 0;
  private static final int SMIP     = 1;
  JLabel jLabel1 = new JLabel();
  JComboBox cmbDBType1 = new JComboBox();
  JComboBox cmbTipServera1 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JComboBox cmbTipIzvestaja = new JComboBox();

  public class ComboItem {
    public ComboItem(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String toString() {
      return name;
    }

    public String getValue() {
      return value;
    }

    private String name;
    private String value;
  }

  /** Klasa koja opisuje proces azuriranja JAR fajlova. Izvrsava se u
      posebnom threadu zahvaljujuci SwingWorker klasi.
    */
  public class UpdateTask {
    public UpdateTask(int type) {
      String reportType = (cmbTipIzvestaja.getSelectedIndex() == SMIP)? "SMIP" : "STANDARD";
      Config config = new Config(((ComboItem)cmbClientLocale.getSelectedItem()).getValue(),
        cmbDBType.getSelectedItem().toString(),
        tfDriver.getText().trim(), tfURL.getText().trim(),
        new String(pfUsername.getPassword()).trim(),
        new String(pfPassword.getPassword()).trim(),
        tfValidator.getText().trim(),
        "RMI_SERVER",
        reportType);
      ServerConfig serverConfig = new ServerConfig(((ComboItem)cmbServerLocale.getSelectedItem()).getValue(),
        cmbDBType.getSelectedItem().toString(),
        tfDriver1.getText().trim(), tfURL1.getText().trim(),
        new String(pfUsername1.getPassword()).trim(),
        new String(pfPassword1.getPassword()).trim(),
        tfValidator.getText().trim(),
        "RMI_SERVER", reportType,
        new String(pfBiblUsername.getPassword()).trim(),
        new String(pfBiblPassword.getPassword()).trim());

      switch (type) {
        case CLIENT:
          JarUpdater.updateAll(config);
          pleaseWaitDlg.setFinished(true);
        break;
        case SERVER:
          JarUpdater.updateJar("sharedsrv.jar",
            "com/gint/app/bisis/sharedsrv/sharedsrv.ini", serverConfig);
          pleaseWaitDlg.setFinished(true);
        break;
      }
    }
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void bOK_actionPerformed(ActionEvent e) {
    pleaseWaitDlg.setFinished(false);
    SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new UpdateTask(CLIENT);
      }
    };
    worker.start();
    pleaseWaitDlg.timer.start();
    pleaseWaitDlg.setVisible(true);
  }

  void bCancel_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void bOK1_actionPerformed(ActionEvent e) {
    pleaseWaitDlg.setFinished(false);
    SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new UpdateTask(SERVER);
      }
    };
    worker.start();
    pleaseWaitDlg.timer.start();
    pleaseWaitDlg.setVisible(true);
  }

  void bCancel1_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void cmbDBType_actionPerformed(ActionEvent e) {
    switch (cmbDBType.getSelectedIndex()) {
      case 0: // SAP DB
        tfDriver.setText("com.sap.dbtech.jdbc.DriverSapDB");
        tfURL.setText("jdbc:sapdb://zozon.tmd.ns.ac.yu/BISIS?sqlmode=ORACLE&unicode=yes");
        break;
      case 1: // ORACLE DB
        tfDriver.setText("oracle.jdbc.driver.OracleDriver");
        tfURL.setText("jdbc:oracle:thin:@sunce.tmd.ns.ac.yu:1526:BIS8");
        break;
    }

  }

  void cmbDBType1_actionPerformed(ActionEvent e) {
    switch (cmbDBType1.getSelectedIndex()) {
      case 0: // SAP DB
        tfDriver1.setText("com.sap.dbtech.jdbc.DriverSapDB");
        tfURL1.setText("jdbc:sapdb://zozon.tmd.ns.ac.yu/BISIS?sqlmode=ORACLE&unicode=yes");
        break;
      case 1: // ORACLE DB
        tfDriver1.setText("oracle.jdbc.driver.OracleDriver");
        tfURL1.setText("jdbc:oracle:thin:@sunce.tmd.ns.ac.yu:1526:BIS8");
        break;
    }
  }
}
