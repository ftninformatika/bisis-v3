package com.gint.app.bisis.textsrv;

/**  Klasa ExpressionException predstavlja izuzetak
  *  koji moze biti bacen tokom interpretiranja upita.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see ExpressionTree
  */
public class ExpressionException extends Exception {
  /**  Default konstruktor. */
  public ExpressionException() { }
  /**  Konstrukcija izuzetka sa datom porukom o gresci.
    *  @param msg Poruka o gresci
    */
  public ExpressionException(String msg) {
    super(msg);
    this.msg = msg;
  }
  public String toString(){
    return msg;
  }
  private String msg;
}
