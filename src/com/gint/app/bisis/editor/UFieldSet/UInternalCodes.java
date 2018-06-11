package com.gint.app.bisis.editor.UFieldSet;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Interni sifarnici. */
public class UInternalCodes {
  public UInternalCodes(String icID, String name) {
    this.icID  = new String(icID);
    this.name  = new String(name);
  }
  
  public UInternalCodes(Node root) {
    NamedNodeMap attrs = root.getAttributes();
    
    this.icID  = attrs.getNamedItem("name").getNodeValue();
    this.name  = attrs.getNamedItem("desc").getNodeValue();
  }

  public UInternalCodes(UInternalCodes ic) {
    this.icID  = new String(ic.icID);
    this.name  = new String(ic.name);
  }

  public void setIcID(String icID) {
    this.icID = new String(icID);
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public String getIcID() {
    return new String(icID);
  }

  public String getName() {
    return new String(name);
  }

  private String icID;
  private String name;

  public String toString() {
    return "icID: " + icID + ", name: " + name;
  }

  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("UInternalCodes");
    root.setAttribute("name", "" + this.icID);
    root.setAttribute("desc", this.name);
    df.appendChild(root);
    
    return df;
  }
}

