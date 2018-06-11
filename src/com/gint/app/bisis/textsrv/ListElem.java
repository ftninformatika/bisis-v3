package com.gint.app.bisis.textsrv;

/**  Klasa ListElem predstavlja elemenat infiksne
  *  i postfiksne liste koje se kreiraju tokom generisanja
  *  stabla pretrage.
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see ExpressionTree
  */
public class ListElem {

  /**  Konstrukcija objekta.
    *  @param type Tip elementa (vidi konstante)
    *  @param content Sadrzaj elementa
    *  @param prefix Prefiks po kome se pretrazuje
    */
  public ListElem(int type, String content, String prefix, int priority) {
    this.type = type;
    this.content = content;
    this.prefix = prefix;
    this.priority = priority;
  }

  public int getType() {
    return type;
  }

  public String getContent() {
    return content;
  }

  public String getPrefix() {
    return prefix;
  }

  public int getPriority() {
    return priority;
  }

  public String toString() {
    return "{type: "+type+" content: "+content+" prefix: "+prefix+" priority: "+priority+"}";
  }

  /** Tip elementa liste. Tipovi su pobrojani kao konstante. */
  private int type;
  /** Sadrzaj elementa liste. */
  private String content;
  /** Prefiks po kome se pretrazuje. Ako je null, pretrazuje se po baznim prefiksima. */
  private String prefix;
  /** Prioritet operatora */
  private int priority;
}