package com.gint.app.bisis.textsrv;

import com.objectspace.jgl.*;

/**  Klasa DistanceComparator se koristi za poredjenje SearchHit pogodaka
  *  pretrage kada se koristi blizinski operator [Wn]. Kada se koristi
  *  DistanceComparator obavezno je racunati presek skupova pogodaka.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see SearchHit
  *  @see TreeNode
  */
public class DistanceComparator implements BinaryPredicate {
  /**  Konstruise objekat sa datom maksimalnom udaljenoscu.
    *  @param maxDistance Najveca dozvoljena udaljenost izmedju reci
    */
  public DistanceComparator(int maxDistance) {
    max = maxDistance;
  }

  /**  Vraca true ako su dva objekta jednaka.
    *  @param left Levi objekat u poredjenju
    *  @param right Desni objekat u poredjenju
    *  @return Rezultat poredjenja
    */
  public boolean execute(Object left, Object right) {
    SearchHit l = (SearchHit)left;
    SearchHit r = (SearchHit)right;
    if ((l.getDocID() == r.getDocID()) &&
        (l.getPrefID() == r.getPrefID()) &&
        (abs(l.getWordPos() - r.getWordPos()) <= max))
      return true;
    else
      return false;
  }

  /**  Racuna apsolutnu vrednost datog celog broja.
    *  @param a Broj cija se apsolutna vrednost racuna
    *  @return Apsolutna vrednost datog celog broja
    */
  private int abs(int a) {
    return (a > 0) ? a : (-a);
  }

  /** Najveca dozvoljena udaljenost izmedju reci */
  private int max;
}