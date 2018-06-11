package com.gint.app.bisis.textsrv;

/**  Klasa ElemTypes sadrzi konstante koje oznacavaju
  *  tip elemenata infiksne i postfiksne liste i stabla
  *  pretrage. Vrednosti iz ove klase se dodeljuju
  *  atributu type u klasama TreeNode i ListElem.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see ListElem
  *  @see TreeNode
  */
public class ElemTypes {
  public static final int OPERATOR       = 1;
  public static final int TERM           = 2;
  public static final int OPEN_BRACKET   = 3;
  public static final int CLOSED_BRACKET = 4;
} 