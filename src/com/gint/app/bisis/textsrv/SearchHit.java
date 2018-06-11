package com.gint.app.bisis.textsrv;

/**  Klasa SearchHit predstavlja jedan pogodak
  *  prilikom pretrazivanja. Pogodak predstavlja uredjenu
  *  cetvorku (docID, prefID, wordPos, sentPos) gde je docID
  *  identifikator dokumenta, prefID je identifikator prefiksa,
  *  wordPos je redni broj reci prefiksu, a sentPos je redni
  *  broj recenice u prefiksu.<P>
  *  Posebnu paznju treba obratiti na metode equals() i hashCode()
  *  koje se koriste prilikom rada sa HashSet klasom.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see TreeNode
  *  @see BooleanComparator
  *  @see DistanceComparator
  *  @see FieldComparator
  *  @see SentenceComparator
  */

public class SearchHit {

  /**  Konstruise objekat sa datim vrednostima atributa.
    *  @param docID Identifikator dokumenta
    *  @param prefID Identifikator prefiksa
    *  @param wordPos Redni broj reci u prefiksu pogotka
    *  @param sentPos Redni broj recenice u prefiksu pogotka
    */
  public SearchHit(int docID, int prefID, int wordPos, int sentPos) {
    this.docID = docID;
    this.prefID = prefID;
    this.wordPos = wordPos;
    this.sentPos = sentPos;
  }

  /**  Vraca docID (identifikator dokumenta) pogotka.
    *  @return Identifikator dokumenta
    */
  public int getDocID() {
    return docID;
  }

  /**  Vraca prefID (identifikator prefiksa) pogotka.
    *  @return Identifikator prefiksa
    */
  public int getPrefID() {
    return prefID;
  }

  /**  Vraca wordPos (redni broj reci u prefiksu) pogotka.
    *  @return Redni broj reci u prefiksu
    */
  public int getWordPos() {
    return wordPos;
  }

  /**  Vraca sentPos (redni broj recenice u prefiksu) pogotka.
    *  @return Redni broj recenice u prefiksu
    */
  public int getSentPos() {
    return sentPos;
  }

  /**  Postavlja docID (identifikator dokumenta) za dati pogodak.
    *  @param docID Identifikator dokumenta
    */
  public void setDocID(int docID) {
    this.docID = docID;
  }

  /**  Postavlja prefID (identifikator prefiksa) za dati pogodak.
    *  @param prefID Identifikator prefiksa
    */
  public void setPrefID(int prefID) {
    this.prefID = prefID;
  }

  /**  Postavlja wordPos (redni broj reci u prefiksu) za dati pogodak.
    *  @param wordPos Redni broj reci u prefiksu
    */
  public void setWordPos(int wordPos) {
    this.wordPos = wordPos;
  }

  /** Postavlja sentPos (redni broj recenice u prefiksu) za dati pogodak.
    *  @param sentPos Redni broj recenice u prefiksu
    */
  public void setSentPos(int sentPos) {
    this.sentPos = sentPos;
  }

  /**  Konvertuje stanje objekta u string.
    *  @return String reprezentacija objekta
    */
  public String toString() {
    return "["+docID+","+prefID+","+wordPos+","+sentPos+"]";
  }

  /**  Vraca hash code datog objekta. Hash code se izracunava
    *  kao hash code stringa koji ima oblik "[docID]", tako da
    *  svi pogoci sa jednakim docID imaju i jednak hashCode.
    *  Na taj nacin ce svi pogoci koji imaju jednak docID [samim
    *  tim i hashCode()] biti smesteni u isti hash bucket u objektu
    *  klase HashSet, pa ce biti moguce vrsiti poredjenje objekata
    *  u zavisnosti od trenutno potrebnog pravila definisanog
    *  pomocu jednog od BinaryPredicate objekata.
    *  @return Hash code objekta
    */
  public int hashCode() {
    return (""+docID).hashCode();
  }

  /**  Vraca true ako su dva pogotka jednaka. Dva pogotka su
    *  jednaka ako su odgovarajuce komponente uredjene cetvorke
    *  (docID, prefID, wordPos, sentPos) medjusobno jednake.
    *  @param x Objekat sa kojim se poredi
    *  @return Rezultat poredjenja
    */
  public boolean equals(Object x) {
    if (!(x instanceof SearchHit))
      return false;
    SearchHit xo = (SearchHit)x;
    return (this.docID == xo.docID) &&
           (this.prefID == xo.prefID) &&
           (this.wordPos == xo.wordPos) &&
           (this.sentPos == xo.sentPos);
  }

  /** Identifikator dokumenta */
  private int docID;
  /** Identifikator prefiksa */
  private int prefID;
  /** Redni broj reci u prefiksu */
  private int wordPos;
  /** Redni broj recenice u prefiksu */
  private int sentPos;
}
