package com.gint.app.bisis.backup;

import java.sql.Types;

/** Tekuca konfiguracija aplikacije. */
public class Config {

  /** Locale for application messages */
  public static String getLocale() {
    return locale;
  }

  /** Vraca tip baze. */
  public static String getDBType() {
    return DBType;
  }

  /** Vraca naziv JDBC drajvera. */
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

  /** Postavlja tip baze. */
  public static void setDBType(String s) {
    DBType = s;
  }

  /** Postavlja naziv JDBC drajvera. */
  public static void setDriver(String s) {
    driver = s;
  }

  /** Postavlja JDBC URL. */
  public static void setUrl(String s) {
    url = s;
  }

  /** Vraca odrediste za backup. */
  public static String getDestination() {
    return destination;
  }

  /** Postavlja korisnicko ime za bazu podataka. */
  public static void setDbUsername(String s) {
    dbUsername = s;
  }

  /** Postavlja lozinku za bazu podataka. */
  public static void setDbPassword(String s) {
    dbPassword = s;
  }

  /** Postavlja odrediste za backup. */
  public static void setDestination(String s) {
    destination = s;
  }
  
  /** Sets locale for application messages. */
  public static void setLocale(String s) {
    locale = s;
  }
  
  public static void setNoGUI(boolean b) {
    noGUI = b;
  }
  
  public static boolean isNoGUI() {
    return noGUI;
  }
  
  public static void setMainFrame(MainFrame mf) {
    mainFrame = mf;
  }
  
  public static MainFrame getMainFrame() {
    return mainFrame;
  }

  /** Tip baze. */
  private static String DBType;
  /** Naziv JDBC drajvera. */
  private static String driver;
  /** Adresa servera. */
  private static String url;
  /** Korisnicko ime za bazu podataka. */
  private static String dbUsername;
  /** Lozinka za bazu podataka. */
  private static String dbPassword;
  /** Da li se trazi ("are you sure") potvrda neposredno pre importa. */
  private static String destination;
  /** Locale for application messages. */
  private static String locale;
  /** Indicates GUI or console operation. */
  private static boolean noGUI;
  /** Reference to the MainFrame window (if running in GUI mode). */
  private static MainFrame mainFrame;

  /** Nazivi tabela za podsistem bibliotekarskog okruzenja. */
  public static String[] okruzTableNames = { "FORMATF", "POLJEBPR",
    "TIP_PUBLIKACIJE", "NIVO_OBRADE", "NIVO_OBAVEZNOSTI", "BIBLIOTEKAR",
    "DEF_TIPA", "TIP_OBRADE", "NIVO_OBAVEZNOSTI_POTPOLJA", "NIVO_OBRADE_POTPOLJA",
    "POLJEBPR_TIP_OBRADE", "FORMATF_POLJEBPR", "POLJEBPR_POTPOLJA" };
  /** Nazivi kolona u tabelama za podsistem bibliotekarskog okruzenja. */
  public static String[] okruzTableCols = {
    "FORMATID,FOR_OPIS", // FORMATF
    "POLJEBPRID,PBPR_NAZIV", // POLJEBPR
    "TIPID,TIP_NAZIV", // TIP_PUBLIKACIJE
    "TP_TIPID,NOBRID,NOBR_NAZIV", // NIVO_OBRADE
    "NOB_NOBRID,NOB_TP_TIPID,NOBAVID,NOBAV_NAZIV", // NIVO_OBAVEZNOSTI
    "BIBID,BIBTIP,BIBNOBR,BIBNOBAV,BIBINTOZN,BIBTAJOZN,OPIS_BIBL", // BIBLIOTEKAR
    "PPO_POTPOLJAID,PPO_PO_POLJEID,TP_TIPID,DEFVR_TIPA", // DEF_TIPA
    "NOV_NOB_NOBRID,NOV_NOB_TP_TIPID,NOV_NOBAVID,BIB_BIBID,FOR_FORMATID", // TIP_OBRADE
    "NOV_NOB_NOBRID,NOV_NOB_TP_TIPID,NOV_NOBAVID,PPO_POTPOLJAID,PPO_PO_POLJEID", // NIVO_OBAVEZNOSTI_POTPOLJA
    "NOB_NOBRID,NOB_TP_TIPID,PPO_POTPOLJAID,PPO_PO_POLJEID", // NIVO_OBRADE_POTPOLJA
    "PPR_POLJEBPRID,TO_NOV_NOB_NOBRID,TO_NOV_NOB_TP_TIPID,TO_NOV_NOBAVID,TO_BIB_BIBID", // POLJEBPR_TIP_OBRADE
    "FOR_FORMATID,PPR_POLJEBPRID", // FORMATF_POLJEBPR
    "PPR_POLJEBPRID,PPO_POTPOLJAID,PPO_PO_POLJEID" }; // POLJEBPR_POTPOLJA
  /** Tipovi kolona */
  public static int[][] okruzColTypes = {
    { Types.CHAR, Types.VARCHAR }, // FORMATF
    { Types.CHAR, Types.VARCHAR }, // POLJEBPR
    { Types.CHAR, Types.VARCHAR }, // TIP_PUBLIKACIJE
    { Types.CHAR, Types.INTEGER, Types.VARCHAR }, // NIVO_OBRADE
    { Types.INTEGER, Types.CHAR, Types.INTEGER, Types.VARCHAR }, // NIVO_OBAVEZNOSTI
    { Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.CHAR, Types.VARCHAR }, // BIBLIOTEKAR
    { Types.CHAR, Types.CHAR, Types.CHAR, Types.VARCHAR }, // DEF_TIPA
    { Types.INTEGER, Types.CHAR, Types.INTEGER, Types.CHAR, Types.CHAR }, // TIP_OBRADE
    { Types.INTEGER, Types.CHAR, Types.INTEGER, Types.CHAR, Types.CHAR }, // NIVO_OBAVEZNOSTI_POTPOLJA
    { Types.INTEGER, Types.CHAR, Types.CHAR, Types.CHAR }, // NIVO_OBRADE_POTPOLJA
    { Types.CHAR, Types.INTEGER, Types.CHAR, Types.INTEGER, Types.CHAR }, // POLJEBPR_TIP_OBRADE
    { Types.CHAR, Types.CHAR }, // FORMATF_POLJEBPR
    { Types.CHAR, Types.CHAR, Types.CHAR }}; // POLJEBPR_POTPOLJA

  /** Nazivi tabela za podsistem bibliotekarskog okruzenja. */
  public static String[] unimarcTableNames = { "POLJE", "PRVI_INDIKATOR",
    "DRUGI_INDIKATOR", "TIP_KONTROLE", "TIPEKST_SIFARNIKA", "POTPOLJA",
    "INTERNI_SIFARNIK", "EKSTERNI_SIFARNIK", "POTPOTPOLJA",
    "SIFARNIK_POTPOTPOLJA" };
  /** Nazivi kolona u tabelama za podsistem bibliotekarskog okruzenja. */
  public static String[] unimarcTableCols = {
    "POLJEID,OBAVEZNOST,PONOVLJIVOST,PO_NAZIV,SEKUNDARNOST,OPIS_DI,OPIS_PI,DEF_PI,DEF_DI",
    "PO_POLJEID,PRINDID,PI_NAZIV",
    "PO_POLJEID,DRINDID,DI_NAZIV",
    "KONTRID,KO_NAZIV",
    "TESID,TES_NAZIV",
    "PO_POLJEID,POTPOLJAID,KONTROLA_KONTRID,OBAVEZNOSTPP,PONOVLJIVOSTPP,PP_NAZIV,DUZINA,STATUS,TES_TESID,DEFVREDNOST",
    "PPO_POTPOLJAID,PPO_PO_POLJEID,ISID,IS_NAZIV",
    "TES_TESID,ESID,ES_NAZIV",
    "PPO_POTPOLJAID,PPO_PO_POLJEID,POTPID,KONTROLA_KONTRID,POTP_NAZIV,POTPDUZINA,POTPONOV,POTPOBAVEZ,STATUSPPP,POTPDEFVR",
    "PPP_POTPID,PPP_PPO_POTPOLJAID,PPP_PPO_PO_POLJEID,SIFPOTPID,SIFPOTP_NAZIV" };
  /** Tipovi kolona */
  public static int[][] unimarcColTypes = {
    { Types.CHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR }, // POLJE
    { Types.CHAR, Types.CHAR, Types.VARCHAR }, // PRVI_INDIKATOR
    { Types.CHAR, Types.CHAR, Types.VARCHAR }, // DRUGI_INDIKATOR
    { Types.INTEGER, Types.VARCHAR }, // TIP_KONTROLE
    { Types.INTEGER, Types.VARCHAR }, // TIPEKST_SIFARNIKA
    { Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.INTEGER, Types.VARCHAR }, // POTPOLJA
    { Types.CHAR, Types.CHAR, Types.CHAR, Types.VARCHAR }, // INTERNI_SIFARNIK
    { Types.INTEGER, Types.CHAR, Types.VARCHAR }, // EKSTERNI_SIFARNIK
    { Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.CHAR, Types.VARCHAR }, // POTPOTPOLJA
    { Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.VARCHAR }}; // SIFARNIK_POTPOTPOLJA

/////////
  /** Nazivi tabela za podsistem cirkulacija. */
  public static String[] circTableNames = {
    "BRANCH",
    "LANGUAGE",
    "USER_TYPES",
    "USERS",
    "EDU_LVL",
    "USER_CATEGS",
    "MMBR_TYPES",
  "DUPLICATE",
  "BLOCK_REASONS",
  "BLOCKED_CARDS",
    "GROUPS",
    "LEND_TYPES",
    "LOCATION",
    "SINGLE",
    "INSTITUTIONS",
    "MEMBERSHIP",
    "SIGNING",
    "LENDING",
    "SLIKOVNICE",
  "WARNING_TYPES",
  "WARN_COUNTERS",
  "WARNINGS"};

  /** Nazivi kolona u tabelama za podsistem cirkulacija. */
  public static String[] circTableCols = {
    /* branch       */ "ID,NAME,LAST_USER_ID",
    /* language     */ "ID,NAME",
    /* user_types   */ "ID,NAME,TITLES_NO,PERIOD",
    /* users        */ "ID,CITY,ZIPCODE,ADDRESS,SIGN_DATE,WARNING,PHONE,EMAIL,USER_TYPE,BRANCH_ID,BRANCH_CARD,BRANCH_SIGN_DATE",
    /* edu_lvl      */ "ID,NAME",
    /* user_categs  */ "ID,NAME,TITLES_NO,PERIOD",
    /* mmbr_types   */ "ID,NAME,TITLES_NO,PERIOD",
  /* duplicate    */ "USER_ID,DUP_NO,DUP_DATE",
  /* block_reasons*/ "ID,NAME",
  /* blocked_cards*/ "USER_ID,REASON_ID,NOTE",
    /* groups       */ "USER_ID,INST_NAME,SEC_ADDRESS,SEC_CITY,SEC_ZIP,SEC_PHONE,FAX,TELEX,CONT_FNAME,CONT_LNAME,CONT_EMAIL",
    /* lend_types   */ "ID,NAME,TITLES_NO,PERIOD",
    /* location     */ "ID,NAME",
    /* single       */ "USER_ID,DOB,FIRST_NAME,PERMISSION,LAST_NAME,OCCUPATION,COUNTRY,PASSWORD,PASSPORT,ORGANIZATION,ORG_ADDRESS,ORG_CITY,ORG_ZIP,ORG_PHONE,TEMP_ADDR,TEMP_CITY,TEMP_ZIP,TEMP_PHONE,INDEX_NO,JMBG,EDU_LVL,USER_CTGR,MMBR_TYPE,GRP_ID,PARENT_NAME,DOC_NO,DOC_CITY,TITLE,NOTE,INTERESTS,DOC_ID,GENDER,LANGUAGE,AGE",
    /* institutions */ "USER_ID,INST_NAME,CONT_FNAME,CONT_LNAME,CONT_EMAIL,CONT_PHONE,SEC_PHONE,FAX,TELEX",
    /* membership   */ "USER_CTGR,MMBR_TYPE,MDATE,COST,HARD_CURR",
    /* signing      */ "SINGLE_ID,BIB_ID,SDATE,COST,HARD_CURR,RECEIPT_ID,UNTIL_DATE",
    /* lending      */ "CTLG_NO,SINGLE_ID,LEND_DATE,RETURN_DATE,RESUME_DATE,BIB_ID,LEND_TYPE,LOCATION,DESCRIPTION,COUNTER",
    /* slikovnice   */ "USER_ID,DATUM,IZDATO,VRACENO,STANJE,BIB_ID",
    /* warning_types*/ "ID,NAME,WTEXT",
  /* warn_counters*/ "WARN_TYPE,WARN_YEAR,LAST_NO",
    /* warnings     */ "WARN_TYPE,USER_ID,CTLG_NO,WARN_NO,WDATE,LOCATION,RETURN_DATE,NOTE"};

  /** Tipovi kolona */
  public static int[][] circColTypes = {
    /* branch       */ { Types.INTEGER, Types.VARCHAR, Types.INTEGER },
    /* language     */ { Types.INTEGER, Types.VARCHAR },
    /* user_types   */ { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR },
    /* users        */ { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.DATE },
    /* edu_lvl      */ { Types.INTEGER, Types.VARCHAR },
    /* user_categs  */ { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR },
    /* mmbr_types   */ { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR },
  /* duplicate    */ { Types.VARCHAR, Types.INTEGER, Types.DATE},
  /* block_reasons*/ { Types.INTEGER, Types.VARCHAR },
  /* blocked_cards*/ { Types.VARCHAR, Types.INTEGER, Types.VARCHAR },
    /* groups       */ { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
    /* lend_types   */ { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR },
    /* location     */ { Types.INTEGER, Types.VARCHAR },
    /* single       */ { Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER },
    /* institutions */ { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
    /* membership   */ { Types.INTEGER, Types.INTEGER, Types.DATE, Types.DOUBLE, Types.DOUBLE },
    /* signing      */ { Types.VARCHAR, Types.CHAR, Types.DATE, Types.DOUBLE, Types.DOUBLE, Types.VARCHAR, Types.DATE },
    /* lending      */ { Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE, Types.DATE, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER },
    /* slikovnice   */ { Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.CHAR},
    /* warning_types*/ { Types.INTEGER, Types.VARCHAR, Types.VARCHAR},
  /* warn_counters*/ { Types.INTEGER, Types.INTEGER, Types.INTEGER},
  /* warnings     */ { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.DATE, Types.VARCHAR}};
}
