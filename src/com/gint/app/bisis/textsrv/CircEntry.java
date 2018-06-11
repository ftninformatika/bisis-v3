package com.gint.app.bisis.textsrv;

public class CircEntry {

  public CircEntry(int docID, String sig, String inv, String type, String state, int available) {
    this.docID = docID;
    this.sig = sig;
    this.type = type;
    this.state = state;
    this.available = available;
    // big shit here:
    // ako je inventarni broj oblika 99999999.999 odseci mu sve od tacke do kraja
    int pos = inv.indexOf('.');
    if (pos != -1) {
      this.inv = inv.substring(0, pos);
      part = true;
    } else {
      this.inv = inv;
      part = false;
    }
  }

  public int getDocID() {
    return docID;
  }

  public String getSig() {
    return sig;
  }

  public String getInv() {
    return inv;
  }

  public String getType() {
    return type;
  }

  public String getState() {
    return state;
  }

  public int getAvailable() {
    return available;
  }
  
  public boolean isPart() {
    return part;
  }

  public void setAvailable(int available) {
    this.available = available;
  }

  public boolean equals(Object o) {
    if (!(o instanceof CircEntry))
      return false;
    CircEntry ce = (CircEntry)o;
    if (sig == null)
      return (docID == ce.docID) && (ce.sig == null) && (inv.equals(ce.inv));
    else
      return (docID == ce.docID) && (sig.equals(ce.sig)) && (inv.equals(ce.inv));
  }
  
  public String toString() {
    return "["+docID+":"+sig+":"+inv+":"+type+":"+state+":"+available+"]";
  }

  private int docID;
  private String sig;
  private String inv;
  private String type;
  private String state;
  private int available;
  private boolean part;
}
