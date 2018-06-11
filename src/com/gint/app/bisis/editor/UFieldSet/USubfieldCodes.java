package com.gint.app.bisis.editor.UFieldSet;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Sifarnici potpolja. */
public class USubfieldCodes {
  public USubfieldCodes(String sfcID, String name) {
    this.sfcID  = new String(sfcID);
    this.name  = new String(name);
  }

  public USubfieldCodes(Node root) {
    NamedNodeMap attrs = root.getAttributes();
    this.sfcID = attrs.getNamedItem("name").getNodeValue();
    this.name  = attrs.getNamedItem("desc").getNodeValue();
  }

  public USubfieldCodes(USubfieldCodes sfc) {
    this.sfcID  = new String(sfc.sfcID);
    this.name  = new String(sfc.name);
  }

  public void setSfcID(String sfcID) {
    this.sfcID = new String(sfcID);
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public String getSfcID() {
    return new String(sfcID);
  }

  public String getName() {
    return new String(name);
  }

  private String sfcID;
  private String name;

  public String toString() {
    return "sfcID: " + sfcID + ", name: " + name;
  }

  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("USubfieldCodes");
    root.setAttribute("name", "" + this.sfcID);
    root.setAttribute("desc", this.name);
    df.appendChild(root);
    
    return df;
  }
}

