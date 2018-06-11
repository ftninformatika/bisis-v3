package com.gint.app.bisis.editor;

import java.sql.Connection;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.LELibrarian.*;
import com.gint.app.bisis.editor.UFieldSet.*;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;
import com.gint.app.bisis.editor.edit.validation.*;

/** Klasa namenjena za cuvanje svih bitnih podataka iz
    okruzenja bibliotekara (konfiguracija UNIMARC-a,
    prava pristupa itd).
  */
public class Environment {

  /** Inicijalizacija okruzenja bibliotekara. Poziva se iskljucivo
      u toku prijavljivanja bibliotekara kada se inicijalizuju sve
      potrebne strukture
    */
  static public void init(LELibrarian lib, UFieldSet fs, TextServer readTs, TextServer writeTs, String address) {
    Environment.lib = lib;
    Environment.fs = fs;
    Environment.readTs = readTs;
    Environment.writeTs = writeTs;
    Environment.address = address;
    Environment.internalTs = writeTs;
    Environment.internal = true;
  }

  static private LELibrarian lib = null;
  static public LELibrarian getLib() {
    return lib;
  }

  static private UFieldSet fs = null;
  static public UFieldSet getFs() {
    return fs;
  }

  static private TextServer internalTs = null;
  static public void setInternal(boolean b) {
    internal = b;
  }
  static public TextServer getInternalTs() {
    return internalTs;
  }
  static public void setInternalTs(TextServer t) {
    internalTs = t;
  }

  static private boolean internal = true;
  static public boolean isInternal() {
    return internal;
  }

  static private DistributedTextServerManager externalTSM = null;
  static public DistributedTextServerManager getExternalTextServerManager() {
    return externalTSM;
  }
  static public void setExternalTextServerManager(DistributedTextServerManager t) {
    externalTSM = t;
  }

  static private String externalAddress = null;
  public static String getExternalAddress() {
    return externalAddress;
  }
  static public void setExternalAddress(String s) {
    externalAddress = s;
  }


  static private TextServer readTs = null;
  static public TextServer getReadTs() {
    return readTs;
  }
  static public void setReadTs(TextServer t) {
    readTs = t;
  }

  static private TextServer writeTs = null;
  static public TextServer getWriteTs() {
    return writeTs;
  }

  static private Field undoField = null;
  public static Field getUndoField() {
    return undoField;
  }
  public static void setUndoField(Field f) {
    undoField = f;
  }

  static private Subfield undoSubfield = null;
  public static Subfield getUndoSubfield() {
    return undoSubfield;
  }
  public static void setUndoSubfield(Subfield sf) {
    undoSubfield = sf;
  }

  static private Field undoSubfieldOwner = null;
  public static Field getUndoSubfieldOwner() {
    return undoSubfieldOwner;
  }
  public static void setUndoSubfieldOwner(Field f) {
    undoSubfieldOwner = f;
  }

  static private Field undoSecField = null;
  public static Field getUndoSecField() {
    return undoSecField;
  }
  public static void setUndoSecField(Field f) {
    undoSecField = f;
  }

  static private String address = null;
  public static String getAddress() {
    return address;
  }
  public static void setAddress(String a) {
    address = a;
  }

  static private String locale;
  public static String getLocale() {
    return locale;
  }
  public static void setLocale(String s) {
    locale = s;
  }

  static private String dbType;
  public static String getDBType() {
    return dbType;
  }
  public static void setDBType(String a) {
    dbType = a;
  }

  static private String driver;
  public static String getDriver() {
    return driver;
  }
  public static void setDriver(String a) {
    driver = a;
  }

  static private String url;
  public static String getURL() {
    return url;
  }
  public static void setURL(String a) {
    url = a;
  }

  static private String username;
  public static String getUsername() {
    return username;
  }
  public static void setUsername(String a) {
    username = a;
  }

  static private String password;
  public static String getPassword() {
    return password;
  }
  public static void setPassword(String a) {
    password = a;
  }

  static private String printCommand;
  public static String getPrintCommand() {
    return printCommand;
  }
  public static void setPrintCommand(String a) {
    printCommand = a;
  }

  static private String browserPath;
  public static String getPrintBrowser() {
    return browserPath;
  }
  public static void setPrintBrowser(String a) {
    browserPath = a;
  }

  static private Validator validator;
  public static Validator getValidator() {
    return validator;
  }
  public static void setValidator(Validator v) {
    validator = v;
  }

  static private boolean newRezance;
  public static boolean isNewRezance() {
    return newRezance;
  }
  public static void setNewRezance(boolean b) {
    newRezance = b;
  }

  static private int docID;
  public static int getDocID() {
    return docID;
  }
  public static void setDocID(int d) {
    docID = d;
  }

  public static final int RMI_SERVER = 0;
  public static final int ORION_SERVER = 1;
  static private int appServer;
  public static int getAppServer() {
    return appServer;
  }
  public static void setAppServer(int d) {
    appServer = d;
  }

  public static final int STANDARD = 0;
  public static final int SMIP     = 1;
  static private int client;
  public static int getClient() {
    return client;
  }
  public static void setClient(int c) {
    client = c;
  }
  
  private static String proxyHost;
  public static void setProxyHost(String s) {
    proxyHost = s;
  }
  public static String getProxyHost() {
    return proxyHost;
  }

  private static String proxyPort;
  public static void setProxyPort(String s) {
    proxyPort = s;
  }
  public static String getProxyPort() {
    return proxyPort;
  }
  
  private static String proxyUser;
  public static String getProxyUser() {
    return proxyUser;
  }
  public static void setProxyUser(String s) {
    proxyUser = s;
  }

  private static String proxyPass;
  public static String getProxyPass() {
    return proxyPass;
  }
  public static void setProxyPass(String s) {
    proxyPass = s;
  }

  private static String nibisService;
  public static void setNibisService(String s) {
    nibisService = s;
  }
  public static String getNibisService() {
    return nibisService;
  }
  
  private static String reportServlet;
  public static void setReportServlet(String s) {
    reportServlet = s;
  }
  public static String getReportServlet() {
    return reportServlet;
  }

  public static String getJavaVersion() {
    return System.getProperty("java.version");
  }
  
  private static Connection conn;
  public static void setConnection(Connection c) {
    conn = c;
  }
  public static Connection getConnection() {
    return conn;
  }
  
  private static int originalRN;
  public static void setOriginalRN(int rn) {
    originalRN = rn;
  }
  public static int getOriginalRN() {
    return originalRN;
  }
  
  private static String libraryName;
  public static void setLibraryName(String name) {
    libraryName = name;
  }
  public static String getLibraryName() {
    return libraryName;
  }
  
  private static boolean bgNerazvrstani;
  public static void setBgNerazvrstani(boolean n) {
    bgNerazvrstani = n;
  }
  public static boolean getBgNerazvrstani() {
    return bgNerazvrstani;
  }
}
