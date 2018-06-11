package com.gint.app.bisis.editor.UFieldSet;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Eksterni sifarnik. */
public class UExternalCodes {
  public UExternalCodes(String ectID, String ecID, String name) {
		this.ectID     = new String(ectID);
    this.ecID      = new String(ecID);
    this.name      = new String(name);
  }

  public UExternalCodes(UExternalCodes ec) {
    this.ectID     = new String(ec.ectID);
    this.ecID      = new String(ec.ecID);
    this.name      = new String(ec.name);
  }

  public void setEctID(String ectID) {
		this.ectID = new String(ectID);
  }

  public void setEcID(String ecID) {
		this.ecID = new String(ecID);
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public String getEctID() {
    return ectID;
  }

  public String getEcID() {
    return ecID;
  }

  public String getName() {
    return new String(name);
  }

  private String ectID;
  private String ecID;
  private String name;

  public String toString() {
    return "ectID: " + ectID + ", ecID: " + ecID + ", name: " + name;
  }

  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("UExternalCodes");
    root.setAttribute("type", "" + this.ectID);
    root.setAttribute("name", "" + this.ecID);
    root.setAttribute("desc", this.name);
    df.appendChild(root);
    
    return df;
  }
}
