package com.gint.app.bisis.export;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.util.gui.SplashScreen;
import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;
import com.gint.util.gui.WindowUtils;

/** Klasa sa main() funkcijom: inicijalizacija konfiguracije i otvaranje
    osnovnog frejma.
  */
public class ImportApp {

  public ImportApp() {
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }
    SplashScreen splash = new SplashScreen(
      "/com/gint/app/bisis/export/images/import_logo.gif");
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
    Config.setLocale(iniFile.getString("general", "locale"));
    Config.setDBType(iniFile.getString("database", "type"));
    Config.setDriver(iniFile.getString("database", "driver"));
    Config.setUrl(iniFile.getString("database", "url"));
    Config.setDbUsername(iniFile.getString("database", "username"));
    Config.setDbPassword(iniFile.getString("database", "password"));
    Config.setConfirm(iniFile.getInt("export", "confirm") != 0);

    MainFrame frame = new MainFrame();
    WindowUtils.centerOnScreen(frame);
    splash.dispose();
    splash = null;
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new ImportApp();
  }
}
