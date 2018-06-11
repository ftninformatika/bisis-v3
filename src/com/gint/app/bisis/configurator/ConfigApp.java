package com.gint.app.bisis.configurator;

import java.util.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.util.gui.SplashScreen;
import com.gint.util.file.INIFile;
import com.gint.util.gui.WindowUtils;

/** Klasa sa main() funkcijom: otvara glavni frejm. */
public class ConfigApp {

  public ConfigApp() {
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }

    // show splash screen
    SplashScreen splash = new SplashScreen(
      "/com/gint/app/bisis/configurator/images/configurator_logo.gif");

    // read filenames of programs to be configured from an ini file
    INIFile iniFile = new INIFile(getClass().getResource(
      "/com/gint/app/bisis/configurator/configurator.ini"));
    String files = iniFile.getString("main", "filelist");
    locale = iniFile.getString("general", "locale");
    StringTokenizer st = new StringTokenizer(files, ", ");
    Vector v = new Vector();
    while (st.hasMoreTokens())
      v.addElement(st.nextToken());
    JarUpdater.setFileNames((String[])v.toArray(new String[0]));

    // display the main frame
    MainFrame frame = new MainFrame();
    WindowUtils.centerOnScreen(frame);
    splash.dispose();
    splash = null;
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new ConfigApp();
  }
  
  public static String getLocale() {
    return locale;
  }
  
  private static String locale;
}