package com.gint.app.bisis.editor;

import java.sql.*;
import javax.swing.*;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;
import com.gint.util.gui.SplashScreen;
import com.gint.util.gui.WindowUtils;

import com.gint.app.bisis.editor.login.*;
import com.gint.app.bisis.editor.edit.validation.*;
import com.gint.app.bisis.editor.report.*;

public class BisisApp {

  public BisisApp(String bibid, String bibtajozn) {
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }
    SplashScreen splash = new SplashScreen(
      "/com/gint/app/bisis/editor/images/editor_logo.gif");
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
    Environment.setLocale(iniFile.getString("general", "locale"));
    Environment.setDBType(iniFile.getString("database", "type"));
    Environment.setDriver(iniFile.getString("database", "driver"));
    Environment.setURL(iniFile.getString("database", "url"));
    Environment.setUsername(iniFile.getString("database", "username"));
    Environment.setPassword(iniFile.getString("database", "password"));

    String appServer = iniFile.getString("editor", "appServer");
    if (appServer.equals("ORION_SERVER"))
      Environment.setAppServer(Environment.ORION_SERVER);
    else
      Environment.setAppServer(Environment.RMI_SERVER);


    Environment.setPrintCommand(iniFile.getString("printing", "print.command"));
    Environment.setPrintBrowser(iniFile.getString("printing", "browser.path"));

    Environment.setProxyHost(iniFile.getString("editor", "proxyHost"));
    Environment.setProxyPort(iniFile.getString("editor", "proxyPort"));
    Environment.setProxyUser(iniFile.getString("editor", "proxyUser"));
    Environment.setProxyPass(iniFile.getString("editor", "proxyPass"));
    Environment.setNibisService(iniFile.getString("editor", "nibisService"));
    Environment.setReportServlet(iniFile.getString("editor", "reportServlet"));

    String client = iniFile.getString("editor", "client");
    if (client.equals("SMIP"))
      Environment.setClient(Environment.SMIP);
    else
      Environment.setClient(Environment.STANDARD);

    // ucitaj klase koje su oznacene kao tekuci validator i kao default
    // validator. ako se tekuci validator nije registrovao za korisceni
    // server, upotrebi default validator.
    try {
      String validatorClass = iniFile.getString("validation", "validatorClass");
      Class.forName(validatorClass);
      Environment.setValidator(ValidatorManager.getValidator());
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    
    Environment.setLibraryName(iniFile.getString("circ", "libraryName"));
    Environment.setBgNerazvrstani(
        "true".equalsIgnoreCase(iniFile.getString("editor", "nerazvrstani")));

    try {
      Class.forName(Environment.getDriver());
      conn = DriverManager.getConnection(Environment.getURL(), 
        Environment.getUsername(), Environment.getPassword());
      conn.setAutoCommit(false);
      Environment.setConnection(conn);
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, 
        Messages.get("BISISAPP_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    splash.dispose();
    splash = null;

    LoginDlg ld = new LoginDlg(null, 
      Messages.get("BISISAPP_LOGINDLG_FRAMETITLE"), true, conn, bibid, 
      bibtajozn);
    while (!ld.getCanceled() && !ld.getSuccessful())
      ld.setVisible(true);

    if (ld.getCanceled())
      System.exit(0);

    splash = new SplashScreen(
      "/com/gint/app/bisis/editor/images/editor_logo.gif");

    Report.setConnection(conn);

    MainFrame.setPingTime(iniFile.getInt("editor", "pingTime") * 1000);
    MainFrame frame = new MainFrame(conn);
    WindowUtils.centerOnScreen(frame);
    frame.updateStatLin();

    splash.dispose();
    splash = null;

    frame.setVisible(true);


/*
    if (Environment.getAppServer() == Environment.RMI_SERVER)
      System.setSecurityManager(new RMISecurityManager());
*/
  }

  public static void main(String[] args) {
    if (args.length < 2)
      new BisisApp(null, null);
    else
      new BisisApp(args[0], args[1]);
  }

  private Connection conn;
}
