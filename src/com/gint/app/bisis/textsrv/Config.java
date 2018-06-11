package com.gint.app.bisis.textsrv;

/**  Klasa Config je namenjena za cuvanje tekuce konfiguracije
  *  programa. Tekuca konfiguracija se smesta u INI fajl i
  *  cita se prilikom inicijalizacije programa.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  */
public class Config {

  /* Tip RDBMS: nijedan */
  public static final int DB_NONE        = 0;
  /* Tip RDBMS: Microsoft Access */
  public static final int DB_ACCESS      = 1;
  /* Tip RDBMS: Microsoft SQL Server */
  public static final int DB_SQLSERVER   = 2;
  /* Tip RDBMS: Oracle */
  public static final int DB_ORACLE      = 3;
  /* Tip RDBMS: Borland DataGateway */
  public static final int DB_DATAGATEWAY = 4;

  private String username = null;
  private String password = null;
  private String connectionString = null;


  /**  Kontruise objekat sa parametrima zapisanim u datoj INI datoteci.
    *  @param filename Ime INI datoteke. Podrazumeva se tekuci direktorijum
    */
  public Config() {
   username = "newbis";
   password = "mesec2";
   connectionString = "jdbc:oracle:thin:@sunce.tmd.ns.ac.yu:1526:BIS8";
  }

  public Config(String connectionString, String username, String password) {
    this.connectionString = new String(connectionString);
    this.username = new String(username);
    this.password = new String(password);
  }


  /**  Vraca tip RDBMS (vidi predefinisane konstante)
    *  @return Tip RDBMS-a
    */
  public int getDbType() {
    return DB_ORACLE;
  }

  /**  Vraca naziv klase koja predstavlja JDBC drajver
    *  @return Naziv JDBC drajvera
    */
  public String getJdbcDriver() {
    return "oracle.jdbc.driver.OracleDriver";
  }

  /**  Vraca Connection string za dati RDBMS
    *  @return Connection string
    */
  public String getConnectionString() {
    return connectionString;
  }

  /**  Vraca RDBMS username pod kojim ce raditi program
    *  @return RDBMS username
    */
  public String getUsername() {
    return username;
  }

  /**  Vraca RDBMS password za korisnika pod kojim radi program
    *  @return RDBMS password
    */
  public String getPassword() {
    return password;
  }

  /**  Vraca identifikator configuracije parsera.
    *  @return Identifikator konfiguracije parsera
    */
  public int getParserConfigID() {
    return 1;
  }

  /**  Vraca true ako je potrebno raditi hex konverziju prilikom
    *  smestanja podataka u bazu.
    *  @return true ako treba raditi hex konverziju
    */
  public boolean getHexConv() {
    return false;
  }
}
