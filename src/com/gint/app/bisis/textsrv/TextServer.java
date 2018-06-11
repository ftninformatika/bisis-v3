package com.gint.app.bisis.textsrv;

import java.util.*;
import java.sql.*;
import com.gint.util.sort.Sorter;

/** TextServer klasa implementira funkcije tekst servera i
  * prilagodjena je ugradjivanju u aplet. Radi i kao TextServerPool,
  * sto znaci da kada se pozove metoda <b>init()</b> onda kreira
  * konfigurabilan broj TextServera i dodeljuje ih korisnicima
  * na zahtev.<br>
  *
  */
public class TextServer {

  public static final int CMD_NONE   = 0;
  public static final int CMD_SELECT = 1;
  public static final int CMD_EXPAND = 2;

  /** Sadrzi kod poslednje komande. */
  public int lastCommand;
  /** Sadrzi tekst poslednje Select komande. */
  public String lastSelect;
  /** Sadrzi tekst poslednje Expand komande. */
  public String lastExpand;

  /** Konekcija na bazu podataka. */
  public Connection conn;
  /** Referenca na <b>DocumentManager</b>. */
  public DocumentManager docManager;
  public int status;
  /** Referenca na klasu koja radi parsiranje DIALOG upita i pravi SQL. */
  public ExpressionTree expressionTree;
  /** HashSet pogodaka za zadati upit. */
  public com.objectspace.jgl.HashSet searchHits;
  /** Niz docId-ova zapisa koji zadovoljavaju zadati upit. */
  public int[] hitArray = new int[0];


  /** Default konstruktor. */
  public TextServer() {
  }

  /**  Konstruktor obavlja inicijalizaciju objekta.
    *  OVAJ KONSTRUKTOR PODRAZUMEVA ORACLE BAZU PODATAKA!
    *  @param conn Otvorena konekcija sa bazom podataka
    *  @param url JDBC URL koji se koristi za nove konekcije
    *  @param username Username u okviru servera za bazu podataka
    *  @param password Lozinka u okviru server za bazu podataka
    */
  public TextServer(Connection conn, String url, String username, String password) {
    dbType = "oracle";
    this.conn = conn;
    this.url = url;
    this.username = username;
    this.password = password;
    docManager = new OracleDocumentManager(conn);
    expressionTree = new ExpressionTree(docManager);
    lastSelect = "";
    lastExpand = "";
    lastCommand = CMD_NONE;
  }

  /**  Konstruktor obavlja inicijalizaciju objekta.
    *  @param dbType Tip baze podataka: oracle, sapdb, ...
    *  @param conn Otvorena konekcija sa bazom podataka
    *  @param url JDBC URL koji se koristi za nove konekcije
    *  @param username Username u okviru servera za bazu podataka
    *  @param password Lozinka u okviru server za bazu podataka
    */
  public TextServer(String dbType, Connection conn, String url, String username, String password) {
    if (dbType == null)
      return;
    this.dbType = dbType;
    this.conn = conn;
    this.url = url;
    this.username = username;
    this.password = password;
    if (dbType.equals("oracle"))
      docManager = new OracleDocumentManager(conn);
    else if (dbType.equals("sapdb"))
      docManager = new SAPDBDocumentManager(conn);
    expressionTree = new ExpressionTree(docManager);
    lastSelect = "";
    lastExpand = "";
    lastCommand = CMD_NONE;
  }

  /**  Konstruktor obavlja inicijalizaciju objekta.
    *  OVAJ KONSTRUKTOR PODRAZUMEVA ORACLE BAZU PODATAKA!
    *  @param conn Otvorena konekcija sa bazom podataka
    */
  public TextServer(Connection conn) {
    dbType = "oracle";
    this.conn = conn;
    docManager = new OracleDocumentManager(conn);
    expressionTree = new ExpressionTree(docManager);
    lastSelect = "";
    lastExpand = "";
    lastCommand = CMD_NONE;
  }

  /**  Konstruktor obavlja inicijalizaciju objekta.
    *  @param dbType Tip baze podataka: oracle, sapdb, ...
    *  @param conn Otvorena konekcija sa bazom podataka
    */
  public TextServer(String dbType, Connection conn) {
    if (dbType == null)
      return;
    this.dbType = dbType;
    this.conn = conn;
    if (dbType.equals("oracle"))
      docManager = new OracleDocumentManager(conn);
    else if (dbType.equals("sapdb"))
      docManager = new SAPDBDocumentManager(conn);
    expressionTree = new ExpressionTree(docManager);
    lastSelect = "";
    lastExpand = "";
    lastCommand = CMD_NONE;
  }

  /** Dodaje novi dokument u bazu.
    * @param document UNIMARC zapis dokumenta
    * @return jedinstveni identifikator dokumenta
    */
  public final int insert(String document) {
    return docManager.insertDocument(document);
  }

  /** Smesta dokument u bazu na mesto vec postojeceg dokumenta,
    * odredjenog svojim identifikatorom.
    * @param doc_id Identifikator dokumenta koji se azurira
    * @param document UNIMARC zapis dokumenta
    * @return Status operacije: 0 - uspesno; -1 - neuspesno
    */
  public final int update(int doc_id, String document) {
    return docManager.updateDocument(doc_id, document);
  }

  /** Uklanja dokument iz baze. Dokument je odredjen svojim identifikatorom.
    * @param doc_id Identifikator dokumenta
    * @return Status operacije: 0 - uspesno; -1 - neuspesno
    */
  public final int delete(int doc_id) {
    int status = docManager.deleteDocument(doc_id);

    // izabacuje se obrisani zapis iz tekuce liste pogodaka (ako je u njoj)
    if (status == 0) {
      int i = 0;
      while (i < hitArray.length && hitArray[i] != doc_id)
        i++;
      if (i < hitArray.length) { // nadjen
        int[] newHitArray = new int [hitArray.length - 1];
        for (int j = 0; j < i; j++)
          newHitArray[j] = hitArray[j];
        for (int j = i+1; j < hitArray.length; j++)
          newHitArray[j-1] = hitArray[j];
        hitArray = newHitArray;
        newHitArray = null;
      }
    }
    return status;
  }

  /**  Vrsi azuriranje indeksa za postojeci zapis. Ne menja njegov
    *  identifikator. Koristi se u slucajevima kada se promeni
    *  konfiguracija tekst servera (delimiteri, mapiranje na prefikse).
    *  @param doc_id Identifikator zapisa koji se reindeksira
    *  @return Status izvrsene operacije: 0 - uspesno; -1 - neuspesno
    */
  public final int reindex(int doc_id) {
    return docManager.reindexDocument(doc_id);
  }

  /**  Pretrazuje bazu za zadati upit i vraca pogodaka koji zadovoljavaju upit.
    *  @param query DIALOG upit
    *  @return Broj pogodaka
    *  @throws ExpressionException
    */
  public int select(String query) throws ExpressionException {
    int i, j, temp;
    try {
      expressionTree.execute(query);
    } catch (ExpressionException e) {
      if (searchHits == null)
        searchHits = new com.objectspace.jgl.HashSet();
      else
        searchHits.clear();
      hitArray = new int [0];
      throw e;
    } catch (Exception ex) {
      searchHits.clear();
      hitArray = new int [0];
      ex.printStackTrace();
      return 0;
    }
    searchHits = expressionTree.getRoot().getHits();

    // preuzimanje rezultata (niza docID-a) iz HashSeta u niz
    // da bi se ocuvao redosled
    hitArray = new int [searchHits.size()];
    Enumeration e = searchHits.elements();
    for (i = 0; e.hasMoreElements(); i++)
      hitArray[i] = ((SearchHit)e.nextElement()).getDocID();
    Sorter.qsort(hitArray);

    lastCommand = CMD_SELECT;
    lastSelect = query;
    return searchHits.size();
  }


  /**  Izvrsava Expand funkciju.
    *  @param prefix Prefiks po kome se ekspaduje
    *  @param query Izraz koji se trazi. Ovom izrazu se automatski dodaje '*'
    *  @return Vektor ExpandPair objekata, sa parovima (broj dokumenata, sadrzaj prefiksa)
    */
  public Vector expand(String prefix, String query) {
    lastExpand = prefix + ":" + query;
    lastCommand = CMD_EXPAND;
    return docManager.expand(prefix, query);
  }

  /**  Dohvata dokumente iz baze podataka na osnovu rezultata pretrage.
    *  @param startIndex Redni broj prvog dokumenta u nizu
    *  @param endIndex Redni broj poslednjeg dokumenta u nizu
    *  @param docIDs [izlazni parametar] Identifikatori dokumenata (podrazumeva se da je niz alociran)
    *  @param docs [izlazni parametar] Sadrzaji dokumenata, "" ako ga nema (podrazumeva se da je niz alociran)
    *  @return false ako su losi ulazni parametri; inace true
    */
  public boolean getDocsRange(int startIndex, int endIndex, int[] docIDs, String[] docs) {
    if ((startIndex < 1) ||
        (endIndex < 1) ||
        (startIndex > endIndex) ||
        (endIndex > searchHits.size()))
      return false;
    int k = 0;
    for (int i = startIndex-1; i < endIndex; i++)
      docIDs[k++] = hitArray[i];
    docManager.getDocsRange(docIDs, docs);
    return true;
  }

  /**  Dohvata dokumente iz baze podataka na osnovu rezultata pretrage.
    *  @param startIndex Redni broj prvog dokumenta u nizu
    *  @param endIndex Redni broj poslednjeg dokumenta u nizu
    *  @return Vektor objekata koji sadrze identifikatore dokumenata i sadrzaje dokumenata, "" ako ga nema
    */
  public Vector getDocsRangePacked(int startIndex, int endIndex) {
    Vector retVal = new Vector();
    if ((startIndex < 1) ||
        (endIndex < 1) ||
        (startIndex > endIndex) ||
        (endIndex > searchHits.size()))
      return retVal;

    int[] docIDs = new int[endIndex-startIndex+1];
    String[] docs = new String[endIndex-startIndex+1];

    int k = 0;
    for (int i = startIndex-1; i < endIndex; i++)
      docIDs[k++] = hitArray[i];
    docManager.getDocsRange(docIDs, docs);

    for (int i = 0; i < docIDs.length; i++) {
      retVal.addElement(new TextServerPackedDoc(docIDs[i], docs[i]));
    }
    return retVal;
  }


  /**  Dohvata zeljeni dokument iz baze
    *  @param docID Identifikator dokumenta
    *  @return Sadrzaj dokumenta, "" ako dokument ne postoji
    */
  public String getDoc(int docID) {
    return docManager.getDoc(docID);
  }

  /**  Vraca broj pogodaka za tekici upit.
    *  @return Broj pogodaka tekuceg upita
    */
  public int getHitCount() {
    return hitArray.length;
  }

  /**  Vraca vektor SubqueryHit objekata koji opisuju
    *  broj pogodaka po podupitima.
    *  @return Vector sa pogocima po podupitima
    */
  public Vector getSubqueries() {
    return expressionTree.getSubqueries();
  }

  /**  Zauzima mesto za novi (prazan) dokument i vraca njegov
    *  DocID. Taj dokument se posle moze update-ovati na osnovu
    *  ovog DocID-a.
    *  @return Identifikator novog dokumenta
    */
  public final int reserveDocument() {
    return docManager.reserveDocument();
  }

  public com.objectspace.jgl.HashSet getAllPrefixes() {
    return docManager.getAllPrefixes();
  }

  public com.objectspace.jgl.HashMap getPrefixMap() {
    return docManager.getPrefixMap();
  }

  /**  Vraca docID dokumenta na osnovu rednog broja u upitu
    *  @param hit redni broj upita
    *  @return int docID dokumenta
    */
  public int getDocID(int hit) {
    int ret = -1;
    try {
      ret = hitArray[hit-1];
    } catch (Exception e) {
    }
    return ret;
  }

  /** Vraca string sa svim delimiterima iz tekuce konfiguracije baze.
    */
  public final String getAllDelimiters() {
    return docManager.getAllDelimiters();
  }

  /** Vraca string sa delimiterima reci iz tekuce konfiguracije baze.
    */
  public final String getWordDelimiters() {
    return docManager.getWordDelimiters();
  }

  /** Vraca string sa delimiterima recenica iz tekuce konfiguracije baze.
    */
  public final String getSentenceDelimiters() {
    return docManager.getSentenceDelimiters();
  }

  /** Prekida SELECT upit koji se izvrsava u drugom threadu.
    */
  public void cancelSelect() {
    expressionTree.cancelSelect();
  }

  /** Prekida EXPAND upit koji se izvrsava u drugom threadu.
    */
  public void cancelExpand() {
    docManager.cancelExpand();
  }

  /** Postavlja skup pogodaka na prazan skup.
    */
  public void clearHits() {
    searchHits = new com.objectspace.jgl.HashSet(); // prazan.
    expressionTree.getRoot().setHits(searchHits);
  }


  /** Smesta dokument u bazu na mesto vec postojeceg dokumenta,
    * odredjenog svojim identifikatorom, ali to radi sa novom JDBC konekcijom
    * koja sluzi samo za to i nakon posla se zatvara.
    * @param doc_id Identifikator dokumenta koji se azurira
    * @param document UNIMARC zapis dokumenta
    * @return Status operacije: 0 - uspesno; -1 - neuspesno
    */
  public int updateWithNewConnection(int docID, String doc) {
    int retVal = -1;
    try {
      retVal = 0;
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
      DocumentManager odm = null;
      if (dbType.equals("oracle"))
        odm = new OracleDocumentManager((OracleDocumentManager)docManager, conn);
      else if (dbType.equals("sapdb"))
        odm = new SAPDBDocumentManager((SAPDBDocumentManager)docManager, conn);
      retVal = odm.updateDocument(docID, doc);
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return retVal;
  }
  
  public int merge(int docID, String doc, int[] docIDs) {
    int retVal = -1;
    try {
      Connection conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(true);
      DocumentManager dm = null;
      if (dbType.equals("oracle"))
        dm = new OracleDocumentManager((OracleDocumentManager)docManager, conn);
      else if (dbType.equals("sapdb"))
        dm = new SAPDBDocumentManager((SAPDBDocumentManager)docManager, conn);
      retVal = dm.merge(docID, doc, docIDs);
      conn.close();
      retVal = 0;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return retVal;
  }

  /** Vraca novi jedinstveni broj iz datog brojaca (sekvence) */
  public int getNewNumber(String counterID) {
    return docManager.getNewNumber(counterID);
  }

  /** Proverava da li vec postoji dati inventarni broj */
  public boolean checkInvNumber(String inv, int docID) {
    return docManager.checkInvNumber(inv, docID);
  }
  
  /** Tip koriscene baze podataka: oracle, sapdb, ... */
  String dbType;
  /** Klasa JDBC drajvera. */
  private String jdbcDriver;
  /** JDBC URL servera. */
  private String url;
  /** Korisnicko ime na DB serveru. */
  private String username;
  /** Sifra na DB serveru. */
  private String password;

}
