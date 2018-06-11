package com.gint.app.bisis.textsrv;

import java.io.*;

/** TextServerPackedDoc klasa sadrzi par (docId, doc).
  */
public class TextServerPackedDoc implements Serializable {
  public int docID;
  public String doc;

  public TextServerPackedDoc(int docID, String doc) {
    this.docID = docID;
    this.doc   = doc;
  }

  public String toString() {
    return "" + docID + "; " + doc;
  }
}
