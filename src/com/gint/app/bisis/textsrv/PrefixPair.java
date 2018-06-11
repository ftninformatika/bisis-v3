package com.gint.app.bisis.textsrv;

/** PrefixPair klasa se koristi da grupise dve vrednosti <CODE>(prefName, value)</CODE>
  * u par. Metoda parseDocument klase DocumentParser vraca Vector objekata klase
  * PrefixPair. Ova metoda je namenjena za konverziju UNIMARC zapisa u prefikse za
  * pretragu.
  * @see DocumentParser
  * @author Branko Milosavljevic
  * @author mbranko@uns.ns.ac.yu
  * @version 1.0
  */ 
public class PrefixPair {
  
  /** Konstruise PrefixPair objekat.
    * @param prefName naziv prefiksa
    * @param value sadrzaj prefiksa
    */
  public PrefixPair(String prefName, String value) {
    this.prefName = new String(prefName);
    this.value = new String(value);
  }
  
  /** Konstruise PrefixPair objekat sa praznim vrednostima.
    */
  public PrefixPair() {
    prefName = new String("");
    value = new String("");
  }
  
  /** Naziv prefiksa */
  public String prefName;
  
  /** Sadrzaj prefiksa */
  public String value;
}
