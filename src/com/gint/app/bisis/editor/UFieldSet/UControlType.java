package com.gint.app.bisis.editor.UFieldSet;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Tip kontrole kod Unimarc formata. */
public class UControlType 
{
  public UControlType(int controlID, String name) {
    this.controlID = controlID;
    this.name      = new String(name);
  }
  
  public UControlType(Node root) {
    NamedNodeMap attrs = root.getAttributes();
    
    controlID = Integer.parseInt(attrs.getNamedItem("name").getNodeValue());
    name      = attrs.getNamedItem("desc").getNodeValue();
  }

  public UControlType(UControlType ct) {
    this.controlID = ct.controlID;
    this.name      = new String(ct.name);
  }

  public void setControlID(int controlID) {
    this.controlID = controlID;
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public int getControlID() {
    return controlID;
  }

  public String getName() {
    return new String(name);
  }

  private int controlID;
  private String name;
  
  public String toString() {
    return "controlID: " + this.controlID + ", name: " + this.name;
  }

  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("UControlType");
    root.setAttribute("name", "" + this.controlID);
    root.setAttribute("desc", this.name);
    df.appendChild(root);
    
    return df;
  }
}

