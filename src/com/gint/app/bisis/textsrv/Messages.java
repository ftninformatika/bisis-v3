package com.gint.app.bisis.textsrv;

import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.Locale;
import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;
import com.gint.util.system.SystemTools;

/**
 *
 * Provides language-dependent messages stored in .properties files
 * 
 * @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 */
public class Messages {

  /** 
   * Retrieves a language-dependent message from the properties file
   * 
   * @param msgName Message name
   * @return language-dependent version of the message
   */
  public static String get(String msgName) {
    return rb.getString(msgName);
  }
  
  /** Resource bundle used for reading messages. */
  private static ResourceBundle rb;
  
  static {
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(Messages.class) + "/config.ini"); 
    String locale = iniFile.getString("general", "locale");
    if (SystemTools.getJavaVersion().compareTo("1.4") >= 0)
      rb = PropertyResourceBundle.getBundle(
        Messages.class.getPackage().getName()+".Messages", new Locale(locale));
    else
      rb = PropertyResourceBundle.getBundle(
        Messages.class.getPackage().getName()+".Messages", new Locale(locale, locale));
  }
  
}
