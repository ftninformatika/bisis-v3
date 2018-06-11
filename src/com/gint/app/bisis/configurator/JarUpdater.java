package com.gint.app.bisis.configurator;

import java.io.*;
import java.util.zip.*;
import java.util.StringTokenizer;


public class JarUpdater {

  /** Postavlja nazive svih jar fajlova koje treba konfigurisati. */
  public static void setFileNames(String[] names) {
    fileNames = names;
  }

  /** Azurira sve poznate JAR fajlove, odnosno {jarname}.ini
      fajlove u njima tako da promeni podatke o serveru na date vrednosti.
      @param server Adresa servera
      @param port Broj porta servera
      @param username Korisnicko ime pod kojim radi program
      @param password Lozinka sa kojom se prijavljuje na bazu
    */
  public static void updateAll(Config config) {
    for (int i = 0; i < fileNames.length; i++)
      updateJar(fileNames[i], config); // dbType, driver, url, username, password, validator, serverType, reportType);
  }

  /** Azurira konkretan JAR fajl.
    */
  private static void updateJar(String jarName, Config config) {
    byte[] buffer = new byte [BUFFER_LENGTH];
    try {
      String appName = jarName.substring(0, jarName.lastIndexOf('.'));
      String propsFile = "com/gint/app/bisis/" + appName + "/" + appName + ".ini";
      ZipInputStream in = new ZipInputStream(new FileInputStream(jarName));
      ZipOutputStream out = new ZipOutputStream(new FileOutputStream("x"+jarName));
      ZipEntry zipEntry;
      while ((zipEntry = in.getNextEntry()) != null) {
        if (zipEntry.getName().equals(propsFile)) {
          out.putNextEntry(new ZipEntry(zipEntry.getName()));
          int total = 0;
          int read;
          byte[] accumulator = new byte [BUFFER_LENGTH];
          while ((read = in.read(buffer, 0, BUFFER_LENGTH)) != -1) {
            for (int i = 0; i < read; i++)
               accumulator[total+i] = buffer[i];
            total += read;
          }
          String fileText = new String(accumulator, 0, total);
          fileText = alterFile(fileText, config);
          byte[] buffer2 = fileText.getBytes();
          out.write(buffer2, 0, buffer2.length);
        } else {
          out.putNextEntry(zipEntry);
          int read;
          while ((read = in.read(buffer, 0, BUFFER_LENGTH)) != -1) {
            out.write(buffer, 0, read);
          }
        }
      }
      out.close();
      in.close();

      File original = new File(jarName);
      original.delete();
      File updated = new File("x"+jarName);
      updated.renameTo(original);
    } catch (FileNotFoundException ex) {
      // za ovo nas bas briga; jednostavno nema tog fajla instaliranog
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Menja sadrzaj {jarname}.ini fajla.
    */
  private static String alterFile(String fileText, Config config) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(fileText, "\r\n");
    while (st.hasMoreTokens()) {
      String line = st.nextToken().trim();
      if (line.indexOf("url") == 0) {
        retVal.append("url = ");
        retVal.append(config.getURL());
        retVal.append("\r\n");
      } else if (line.indexOf("type") == 0) {
        retVal.append("type = ");
        retVal.append(config.getDbType());
        retVal.append("\r\n");
      } else if (line.indexOf("driver") == 0) {
        retVal.append("driver = ");
        retVal.append(config.getDriver());
        retVal.append("\r\n");
      } else if (line.indexOf("username") == 0) {
        retVal.append("username = ");
        retVal.append(config.getUsername());
        retVal.append("\r\n");
      } else if (line.indexOf("password") == 0) {
        retVal.append("password = ");
        retVal.append(config.getPassword());
        retVal.append("\r\n");
      } else if (line.indexOf("validatorClass") == 0) {
        retVal.append("validatorClass = ");
        retVal.append(config.getValidator());
        retVal.append("\r\n");
      } else if (line.indexOf("appServer") == 0) {
        retVal.append("appServer = ");
        retVal.append(config.getServerType());
        retVal.append("\r\n");
      } else if (line.indexOf("client") == 0) {
        retVal.append("client = ");
        retVal.append(config.getReportType());
        retVal.append("\r\n");
      } else if (line.indexOf("locale") == 0) {
        retVal.append("locale = ");
        retVal.append(config.getLocale());
        retVal.append("\r\n");
      } else {
        retVal.append(line);
        retVal.append("\r\n");
      }
    }
    return retVal.toString();
  }

  /** 
   * Azurira JAR fajl servera za uzajamnu katalogizaciju, ali uzima u obradu i 
   * dva dodatna polja: username bibliotekara i password bibliotekara.
   */
  public static void updateJar(String jarName, String propsFile, 
      ServerConfig serverConfig) {
    byte[] buffer = new byte [BUFFER_LENGTH];
    try {
      ZipInputStream in = new ZipInputStream(new FileInputStream(jarName));
      ZipOutputStream out = new ZipOutputStream(new FileOutputStream("x"+jarName));
      ZipEntry zipEntry;
      while ((zipEntry = in.getNextEntry()) != null) {
        if (zipEntry.getName().equals(propsFile)) {
          out.putNextEntry(new ZipEntry(zipEntry.getName()));
          int total = 0;
          int read;
          byte[] accumulator = new byte [BUFFER_LENGTH];
          while ((read = in.read(buffer, 0, BUFFER_LENGTH)) != -1) {
            for (int i = 0; i < read; i++)
               accumulator[total+i] = buffer[i];
            total += read;
          }
          String fileText = new String(accumulator, 0, total);
          String fileText2 = alterFile2(fileText, serverConfig);
          byte[] buffer2 = fileText2.getBytes();
          out.write(buffer2, 0, buffer2.length);
        } else {
          out.putNextEntry(zipEntry);
          int read;
          while ((read = in.read(buffer, 0, BUFFER_LENGTH)) != -1) {
            out.write(buffer, 0, read);
          }
        }
      }
      out.close();
      in.close();

      File original = new File(jarName);
      original.delete();
      File updated = new File("x"+jarName);
      updated.renameTo(original);
    } catch (FileNotFoundException ex) {
      // za ovo nas bas briga; jednostavno nema tog fajla instaliranog
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Menja sadrzaj sharedsrv.ini fajla.
    */
  private static String alterFile2(String fileText, ServerConfig sc) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(fileText, "\r\n");
    while (st.hasMoreTokens()) {
      String line = st.nextToken().trim();
      if (line.indexOf("url") == 0) {
        retVal.append("url = ");
        retVal.append(sc.getURL());
        retVal.append("\r\n");
      } else if (line.indexOf("type") == 0) {
        retVal.append("type = ");
        retVal.append(sc.getDbType());
        retVal.append("\r\n");
      } else if (line.indexOf("driver") == 0) {
        retVal.append("driver = ");
        retVal.append(sc.getDriver());
        retVal.append("\r\n");
      } else if (line.indexOf("username") == 0) {
        retVal.append("username = ");
        retVal.append(sc.getUsername());
        retVal.append("\r\n");
      } else if (line.indexOf("password") == 0) {
        retVal.append("password = ");
        retVal.append(sc.getPassword());
        retVal.append("\r\n");
      } else if (line.indexOf("validatorClass") == 0) {
        retVal.append("validatorClass = ");
        retVal.append(sc.getValidator());
        retVal.append("\r\n");
      } else if (line.indexOf("biblusername") == 0) {
        retVal.append("biblusername = ");
        retVal.append(sc.getLibUsername());
        retVal.append("\r\n");
      } else if (line.indexOf("biblpassword") == 0) {
        retVal.append("biblpassword = ");
        retVal.append(sc.getLibPassword());
        retVal.append("\r\n");
      } else if (line.indexOf("locale") == 0) {
        retVal.append("locale = ");
        retVal.append(sc.getLocale());
        retVal.append("\r\n");
      } else {
        retVal.append(line);
        retVal.append("\r\n");
      }
    }
    return retVal.toString();
  }

  private static final int BUFFER_LENGTH = 65536;
  private static String[] fileNames;
}
