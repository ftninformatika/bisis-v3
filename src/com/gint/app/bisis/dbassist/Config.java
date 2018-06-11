package com.gint.app.bisis.dbassist;

/** Tekuca konfiguracija aplikacije. */
public class Config {

  public static String getLocale() {
    return locale;
  }
  
  /** Vraca tip baze. */
  public static String getDBType() {
    return DBType;
  }

  /** Vraca JDBC drajver. */
  public static String getDriver() {
    return driver;
  }

  /** Vraca JDBC URL. */
  public static String getUrl() {
    return url;
  }

  /** Vraca korisnicko ime za bazu podataka. */
  public static String getDbUsername() {
    return dbUsername;
  }

  /** Vraca lozinku za bazu podataka. */
  public static String getDbPassword() {
    return dbPassword;
  }

  /** Vraca naziv tablespacea za tabele. */
  public static String getUserTablespace() {
    return userTablespace;
  }

  /** Vraca naziv tablespacea za indekse. */
  public static String getIndexTablespace() {
    return indexTablespace;
  }

  public static void setLocale(String s) {
    locale = s;
  }
  
  /** Postavlja tip baze. */
  public static void setDBType(String s) {
    DBType = s;
  }

  /** Postavlja JDBC drajver. */
  public static void setDriver(String s) {
    driver = s;
  }

  /** Postavlja JDBC URL. */
  public static void setUrl(String s) {
    url = s;
  }

  /** Postavlja korisnicko ime za bazu podataka. */
  public static void setDbUsername(String s) {
    dbUsername = s;
  }

  /** Postavlja lozinku za bazu podataka. */
  public static void setDbPassword(String s) {
    dbPassword = s;
  }

  /** Postavlja ime tablespacea za tabele. */
  public static void setUserTablespace(String s) {
    userTablespace = s;
  }

  /** Postavlja ime tablespacea za indekse. */
  public static void setIndexTablespace(String s) {
    indexTablespace = s;
  }

  /** Locale for messages. */
  private static String locale;  
  /** Tip baze. */
  private static String DBType;
  /** JDBC drajver. */
  private static String driver;
  /** Adresa servera. */
  private static String url;
  /** Korisnicko ime za bazu podataka. */
  private static String dbUsername;
  /** Lozinka za bazu podataka. */
  private static String dbPassword;
  /** Ime tablespacea za tabele. */
  private static String userTablespace;
  /** Ime tablespacea za indekse. */
  private static String indexTablespace;

}
