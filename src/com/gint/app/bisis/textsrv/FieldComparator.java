package com.gint.app.bisis.textsrv;

import com.objectspace.jgl.*;

/**  Klasa FieldComparator se koristi za poredjenje SearchHit pogodaka
  *  pretrage kada se koristi blizinski operator [F]. Kada se koristi
  *  FieldComparator obavezno je racunati presek skupova pogodaka.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see SearchHit
  *  @see TreeNode
  */
public class FieldComparator implements BinaryPredicate{

  /**  Vraca true ko su dva objekta jednaka.
    *  @param left Levi objekat u poredjenju
    *  @param right Desni objekat u poredjenju
    *  @return Rezultat poredjenja
    */
  public boolean execute(Object left, Object right) {
    SearchHit l = (SearchHit)left;
    SearchHit r = (SearchHit)right;
    if ((l.getDocID() == r.getDocID()) &&
        (l.getPrefID() == r.getPrefID()))
      return true;
    else
      return false;
  }
}
