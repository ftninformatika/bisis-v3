package com.gint.app.bisis.editor.UFieldSet;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Indikatori. */
public class UInd {
  public UInd(String indID, String name) {
    this.indID = new String(indID);
    this.name  = new String(name);
  }

  public UInd(Node root) {
    NamedNodeMap attrs = root.getAttributes();
    this.indID = attrs.getNamedItem("number").getNodeValue();
    this.name  = attrs.getNamedItem("desc").getNodeValue();
  }

  public UInd(UInd ind) {
    this.indID = new String(ind.indID);
    this.name  = new String(ind.name);
  }

  public void setIndID(String indID) {
    this.indID = new String(indID);
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public String getIndID() {
    return indID;
  }

  public String getName() {
    return name;
  }

  private String  indID;
  private String  name;

  public String toString() {
    return "indID: " + indID + ", name: " + name;
  }
  
  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("value");
    root.setAttribute("number", "" + this.indID);
    root.setAttribute("desc", this.name);
    df.appendChild(root);
    
    return df;
  }
}

