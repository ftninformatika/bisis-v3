package com.gint.app.bisis.textsrv;

import java.util.*;

/** WordParser klasa je namenjena za rastavljanje datog teksta na tokene.
 *  Tokeni su odredjeni datim delimiterima za reci i recenice. Podrazumeva
 *  se da su svi delimiteri duzine jednog karaktera. Delimiteri
 *  su smesteni u bazi podataka.<P>
 *  Rezultat parsiranja je Vector tokena. Recenice u okviru Vectora su
 *  odvojene specijalnim tokenom "." (stringom koji sadrzi samo tacku).
 *  @author Branko Milosavljevic
 *  @author mbranko@uns.ns.ac.yu
 *  @version 1.0
 */
public class WordParser {

  /** Konstruise objekat.
   *  @param wordDelims delimiteri reci
   *  @param sentDelims delimiteri recenica
   */
  public WordParser(String wordDelims, String sentDelims) {
    this.wordDelims = wordDelims;
    this.sentDelims = sentDelims;
  }

  /** Parsira dati tekst na tokene. Rezultat je Vector tokena pri cemu
   *  su recenice odvojene posebnim tokenom "." (stringom tacka).
   *  @param text tekst koji se parsira
   *  @return Vector tokena
   */
  public Vector parseText(String text) {
    String temp, temp2;
    Vector retVal = new Vector();
    StringTokenizer wordTokenizer;
    StringTokenizer sentTokenizer = new StringTokenizer(text, sentDelims);
    while (sentTokenizer.hasMoreTokens()) {
      temp2 = sentTokenizer.nextToken();
      if (!temp2.equals("")) {
        wordTokenizer = new StringTokenizer(temp2, wordDelims);
        while (wordTokenizer.hasMoreTokens()) {
          temp = wordTokenizer.nextToken();
          if (!temp.equals(""))
            retVal.addElement(new String(temp));
        }
        retVal.addElement(".");
      }
    }
    return retVal;
  }

  /** String koji sadrzi delimitere reci */
  private String wordDelims;

  /** String koji sadrzi delimitere recenica */
  private String sentDelims;
}