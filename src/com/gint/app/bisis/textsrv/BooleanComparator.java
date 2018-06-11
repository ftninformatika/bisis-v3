package com.gint.app.bisis.textsrv;

import com.objectspace.jgl.*;

/**  Klasa BooleanComparator se koristi za poredjenje SearchHit pogodaka
  *  pretrage kada se koristi neki od logickih operatora AND ili OR.
  *  Ako je u pitanju AND racuna se presek, a ako je u pitanju OR racuna
  *  se unija skupova pogodaka.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see SearchHit
  *  @see TreeNode
  */
public class BooleanComparator implements BinaryPredicate {

  /**  Vraca true ako su dva objekta jednaka.
    *  @param left Levi objekat u poredjenju
    *  @param right Desni objekat u poredjenju
    *  @return rezultat poredjenja
    */
  public boolean execute(Object left, Object right) {
    return (((SearchHit)left).getDocID() == ((SearchHit)right).getDocID());
  }
}