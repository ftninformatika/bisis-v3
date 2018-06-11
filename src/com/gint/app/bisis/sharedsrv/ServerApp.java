package com.gint.app.bisis.sharedsrv;

import java.sql.*;
import javax.swing.*;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.app.bisis.editor.edit.validation.*;
import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;

public class ServerApp {

  public ServerApp(String[] args) {
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
    Environment.setLocale(iniFile.getString("sharedsrv", "locale"));
    Environment.setDBType(iniFile.getString("sharedsrv", "type"));
    Environment.setDriver(iniFile.getString("sharedsrv", "driver"));
    Environment.setURL(iniFile.getString("sharedsrv", "url"));
    Environment.setUsername(iniFile.getString("sharedsrv", "username"));
    Environment.setPassword(iniFile.getString("sharedsrv", "password"));
    String bibid     = iniFile.getString("sharedsrv", "biblusername");
    String bibtajozn = iniFile.getString("sharedsrv", "biblpassword");
    int logTreshold = iniFile.getInt("sharedsrv", "logTreshold");
    
    if (args.length > 0)
      Environment.setGUI(true);
    else
    	Environment.setGUI(false);

    // ucitaj klase koje su oznacene kao tekuci validator i kao default
    // validator. ako se tekuci validator nije registrovao za korisceni
    // server, upotrebi default validator.
    try {
      String validatorClass = iniFile.getString("sharedsrv", "validatorClass");
      Class.forName(validatorClass);
      Environment.setValidator(ValidatorManager.getValidator());
    } catch (ClassNotFoundException ex) {
      if (Environment.isGUI())
        JOptionPane.showMessageDialog(null, Messages.get("SHAREDSRVAPP_SERVERAPP_NOVALIDATORCLASS"), Messages.get("SHAREDSRVAPP_SERVERAPP_ERROR"), JOptionPane.ERROR_MESSAGE);
      else
        System.out.println(Messages.get("SHAREDSRVAPP_SERVERAPP_NOVALIDATORCLASS"));
      ex.printStackTrace();
      System.exit(1);
    }
    
    Task mt;
    if (Environment.isGUI()) {
      String osname = System.getProperty("os.name");
      if (osname != null && osname.equals("Linux")) {
        try {
          UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
          System.err.println("Unsupported look and feel: InfoNode");
        }
      }
      mt = new MainTask();
    } else
      mt = new ConsoleTask();
      
    mt.setLogTreshold(logTreshold);
    Environment.setTask(mt);

    Environment.log(Messages.get("SHAREDSRVAPP_SERVERAPP_CONNECTINGTODB"));
    try {
      Class.forName(Environment.getDriver());
      conn = DriverManager.getConnection(Environment.getURL(), Environment.getUsername(), Environment.getPassword());
      conn.setAutoCommit(false);
    } catch (Exception ex) {
      Environment.log(Messages.get("SHAREDSRVAPP_SERVERAPP_FAILED"));
      ex.printStackTrace();
      if (Environment.isGUI())
        JOptionPane.showMessageDialog(null, Messages.get("SHAREDSRVAPP_SERVERAPP_FAILEDTOCONNECTTODB"), Messages.get("SHAREDSRVAPP_SERVERAPP_ERROR"), JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
    Environment.log(Messages.get("SHAREDSRVAPP_SERVERAPP_SUCCESSFULLY"));

    Environment.log(Messages.get("SHAREDSRVAPP_SERVERAPP_LOGGINGIN"));
    LoginTask lt = new LoginTask(conn, bibid, bibtajozn);
    if (lt.isSuccessful()) {
      Environment.log(Messages.get("SHAREDSRVAPP_SERVERAPP_SUCCESSFULLY"));
      mt.start();
    } else {
      Environment.log(Messages.get("SHAREDSRVAPP_SERVERAPP_FAILED"));
      if (Environment.isGUI())
        JOptionPane.showMessageDialog(null, Messages.get("SHAREDSRVAPP_SERVERAPP_FAILEDTOLOGIN"), Messages.get("SHAREDSRVAPP_SERVERAPP_ERROR"), JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
    try {
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new ServerApp(args);
  }

  private Connection conn;
}
