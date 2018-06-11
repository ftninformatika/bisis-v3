package com.gint.app.bisis.editor.UFieldSet;

import java.util.Enumeration;
import com.objectspace.jgl.*;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Tipovi eksternog sifarnika. */
public class UExtCodeTypes {
  public UExtCodeTypes(String ectID, String name) {
    this.ectID = new String(ectID);
    this.name  = new String(name);
  }

  public UExtCodeTypes(UExtCodeTypes ect) {
    this.ectID = new String(ect.ectID);
    this.name  = new String(ect.name);
  }

  public void setEctID(String ectID) {
    this.ectID = new String(ectID);
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public String getEctID() {
    return ectID;
  }

  public String getName() {
    return name;
  }

  private String  ectID;
  private String  name;

  public String toString() {
    return "ectID: " + ectID + ", name: " + name;
  }

  public DocumentFragment createXML(DocumentImpl document, HashMap tExternalCodes) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("UExtCodeTypes");
    root.setAttribute("name", "" + this.ectID);
    root.setAttribute("desc", this.name);
    
    HashSet hs = (HashSet)tExternalCodes.get("" + ectID);
    if (hs != null) {
      Enumeration en2 = hs.elements();
      while (en2.hasMoreElements()) {
        UExternalCodes ec = (UExternalCodes)en2.nextElement();
        root.appendChild(ec.createXML(document));
      }
    }

    df.appendChild(root);
    
    return df;
  }
}

