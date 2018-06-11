package com.gint.app.bisis.dbassist;


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;
import com.gint.util.gui.SplashScreen;

import net.infonode.gui.laf.InfoNodeLookAndFeel;


/** Klasa sa main() funkcijom; obavi se inicijalizacija i otvori glavni frejm.
  */
public class AssistApp {

  public AssistApp() {
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }
    SplashScreen splash = new SplashScreen(
      "/com/gint/app/bisis/dbassist/images/dbassist_logo.gif");

    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
    Config.setLocale(iniFile.getString("general", "locale"));
    Config.setDBType(iniFile.getString("database", "type"));
    Config.setDriver(iniFile.getString("database", "driver"));
    Config.setUrl(iniFile.getString("database", "url"));
    Config.setDbUsername(iniFile.getString("database", "username"));
    Config.setDbPassword(iniFile.getString("database", "password"));

    MainFrame frame = new MainFrame();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    splash.dispose();
    splash = null;
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new AssistApp();
  }
}
