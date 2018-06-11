package com.gint.app.bisis.textsrv;

import java.util.*;

/**  Interfejs DocumentManager je namenjen za izdvajanje
  *  operacija koje zavise od konktrnog RDBMS u posebne
  *  klase koje ga implementiraju. Ostatak koda koristi
  *  ovaj interfejs umesto konkretne klase koja zavisi od
  *  tipa RDBMS. Varijabla tipa DocumentManager se
  *  konstruise kao instanca odgovarajuce klase tokom
  *  inicijalizacije programa.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  */
interface DocumentManager {

  /**  Dodaje novi dokument u bazu. Vraca status izvrsene operacije.
    *  @param doc Dokument (UNIMARC rezanac) koji se dodaje
    *  @return Identifikator dokumenta (docID > 0) ukoliko je dodavanje
    *  bilo uspesno; inace -1
    */
  int  insertDocument(String doc);

  /**  Azurira postojeci dokument u bazi.
    *  @param doc_id Identifikator dokumenta koji se azurira
    *  @param doc Novi sadrzaj dokumenta
    *  @return Status izvrsene operacije: 0 - uspesno; -1 - neuspesno
    */
  int  updateDocument(int doc_id, String doc);

  /**  Brisanje dokumenta iz baze.
    *  @param doc_id Identifikator dokumenta koji se brise
    *  @return Status izvrsene operacije: 0 - uspesno; -1 - neuspesno
    */
  int  deleteDocument(int doc_id);

  /**  Vrsi azuriranje indeksa za postojeci zapis. Ne menja njegov
    *  identifikator. Koristi se u slucajevima kada se promeni
    *  konfiguracija tekst servera (delimiteri, mapiranje na prefikse).
    *  @param doc_id Identifikator zapisa koji se reindeksira
    *  @return Status izvrsene operacije: 0 - uspesno; -1 - neuspesno
    */
  int reindexDocument(int doc_id);

  /**  Zauzima mesto za novi (prazan) dokument i vraca njegov
    *  DocID. Taj dokument se posle moze update-ovati na osnovu
    *  ovog DocID-a.
    *  @return Identifikator novog dokumenta
    */
  int  reserveDocument();

  /**  Dohvata dokumente iz baze podataka na osnovu rezultata pretrage.
    *  @param docIDs [izlazni parametar] niz identifikatora dokumenata
    *  @param docs [izlazni parametar] niz sadrzaja dokumenata
    */
  void getDocsRange(int[] docIDs, String[] docs);

  /**  Dohvata zeljeni dokument iz baze
    *  @param docID Identifikator dokumenta
    *  @return Sadrzaj dokumenta
    */
  String getDoc(int docID);

  /**  U bazi pronalazi pogotke za elementarni upit.
    *  @param prefix Prefiks po kome se pretrazuje. Ako je null, trazi se po svim baznim prefiksima.
    *  @param term Izraz koji se trazi.
    *  @return Skup pogodaka kao HashSet koji sadrzi SearchHit objekte.
    */
  com.objectspace.jgl.HashSet executeSelect(String prefix, String term);

  /**  Izvrsava Expand naredbu.
    *  @param query term koji se trazi
    *  @return vektor ExpandPair objekata
    */
  Vector expand(String prefix, String query);
  
  /**
   * Radi update prvog zapisa a brisanje zapisa iz niza. Pri tome se
   * ne brisu stavke iz CIRC_DOCS tabele vec se one prevezu na prvi zapis.
   * @param docID ID zapisa koji je rezultat merge operacije
   * @param doc zapis koji je rezultat merge operacije
   * @param docIDs ID zapisa koji se brisu
   * @return 0 - uspesno, -1 - neuspesno
   */
  int merge(int docID, String doc, int[] docIDs);

  /**  Vraca skup svih definisanih prefiksa.
    *  @return Skup svih definisanih prefiksa
    */
  com.objectspace.jgl.HashSet getAllPrefixes();

  /**  Vraca skup svih baznih prefiksa.
    *  @return Skup svih baznih prefiksa
    */
  com.objectspace.jgl.HashSet getBasePrefixes();

  /**  Vraca mapiranje potpolja->prefiksi.
    *  @return Mapa sa preslikavanjem potpolja->prefiksi
    */
  com.objectspace.jgl.HashMap getPrefixMap();

  /** Vraca string sa svim delimiterima iz tekuce konfiguracije baze.
    */
  String getAllDelimiters();

  /** Vraca string sa delimiterima reci iz tekuce konfiguracije baze.
    */
  public String getWordDelimiters();

  /** Vraca string sa delimiterima recenica iz tekuce konfiguracije baze.
    */
  public String getSentenceDelimiters();

  /**  Prekida EXPAND upit koji se izvrsava u drugom threadu.
    */
  void cancelExpand();

  /**  Prekida SELECT upit koji se izvrsava u drugom threadu.
    */
  void cancelSelect();

  /** Vraca novi jedinstveni broj iz datog brojaca (sekvence) */
  public int getNewNumber(String counterID);

  /** Proverava da li vec postoji dati inventarni broj */
  public boolean checkInvNumber(String inv, int docID);
  
}
