package com.gint.app.bisis.editor.LELibrarian;

import java.util.*;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

import com.gint.util.xml.XMLUtils;

/** Klasa koja sadrzi podatke o trenutnom kontekstu bibliotekara: <br>
  <ul>
  <li> Tip publikacije
  <li> Nivo obrade
  <li> Nivo obaveznosti
  <li> Lista polja sa potpoljima za zadati tip publikacije
  <li> Lista polja sa potpoljima za zadati tip publikacije i nivo obrade
  <li> Lista polja sa potpoljima za zadati tip publikacije, nivo obrade i nivo obaveznosti
  </ul>
*/
public class LEContext {
  /** Konstruktor. */
  public LEContext(String pubType, int procLevel, int manLevel) {
    this.pubType = new String(pubType);
    this.procLevel = procLevel;
    this.manLevel = manLevel;
  }

  public LEContext(Node root) {
    pubType   = XMLUtils.getSubnodeByName(root, "pubType").getFirstChild().getNodeValue();
    procLevel = Integer.parseInt(XMLUtils.getSubnodeByName(root,
                                 "procLevel").getFirstChild().getNodeValue());
    manLevel  = Integer.parseInt(XMLUtils.getSubnodeByName(root,
                                 "manLevel").getFirstChild().getNodeValue());

    NodeList typeSubfields = XMLUtils.getSubnodeByName(root, "typeSubfields").getChildNodes();
    LESubfield lsf;
    for (int j = 0; j < typeSubfields.getLength(); j++) {
      lsf = new LESubfield(typeSubfields.item(j));
      addTypeSubfield(lsf);
    } 
    
    NodeList procSubfields = XMLUtils.getSubnodeByName(root, "procSubfields").getChildNodes();
    for (int j = 0; j < procSubfields.getLength(); j++) {
      lsf = new LESubfield(procSubfields.item(j));
      addProcSubfield(lsf);
    }

    NodeList manSubfields = XMLUtils.getSubnodeByName(root, "manSubfields").getChildNodes();
    for (int j = 0; j < manSubfields.getLength(); j++) {
      lsf = new LESubfield(manSubfields.item(j));
      addManSubfield(lsf);
    }
  }

  /** Postavlja tip publikacije za ovaj kontekst. */
  public void setPubType(String pubType) {
    this.pubType = new String(pubType);
  }
  /** Postavlja nivo obrade za ovaj kontekst. */
  public void setProcLevel(int procLevel) {
    this.procLevel = procLevel;
  }
  /** Postavlja nivo obaveznosti za ovaj kontekst. */
  public void setManLevel(int manLevel) {
    this.manLevel = manLevel;
  }
  /** Vraca tip publikacije za ovaj kontekst. */
  public String getPubType() {
    return pubType;
  }
  /** Vraca nivo obrade za ovaj kontekst. */
  public int getProcLevel() {
    return procLevel;
  }
  /** Vraca nivo obaveznosti za ovaj kontekst. */
  public int getManLevel() {
    return manLevel;
  }

  /** Vraca vektor potpolja za tip publikacije u ovom kontekstu. */
  public Vector getTypeSubfields() {
    return typeSubfields;
  }
  /** Vraca vektor potpolja za nivo obrade u ovom kontekstu. */
  public Vector getProcSubfields() {
    return procSubfields;
  }
  /** Vraca vektor potpolja za nivo obaveznosti u ovom kontekstu. */
  public Vector getManSubfields() {
    return manSubfields;
  }

  /** Dodaje jednu trojku(polje, potpolje, def. vrednost) u vektor za tip publikacije. */
  public void addTypeSubfield(String field, String subfield, String defValue) {
    typeSubfields.addElement(new LESubfield(field, subfield, defValue));
  }
  /** Dodaje jednu trojku(polje, potpolje, def. vrednost) u vektor za tip publikacije. */
  public void addTypeSubfield(LESubfield lsf) {
    typeSubfields.addElement(lsf);
  }

  /** Dodaje jednu trojku(polje, potpolje, def. vrednost) u vektor za tip publikacije
   i nivo obrade. */
  public void addProcSubfield(String field, String subfield, String defValue) {
    procSubfields.addElement(new LESubfield(field, subfield, defValue));
  }
  /** Dodaje jednu trojku(polje, potpolje, def. vrednost) u vektor za tip publikacije
   i nivo obrade. */
  public void addProcSubfield(LESubfield lsf) {
    procSubfields.addElement(lsf);
  }

  /** Dodaje jednu trojku(polje, potpolje, def. vrednost) u vektor za tip publikacije,
   nivo obrade i nivo obaveznosti. */
  public void addManSubfield(String field, String subfield, String defValue) {
    manSubfields.addElement(new LESubfield(field, subfield, defValue));
  }
  /** Dodaje jednu trojku(polje, potpolje, def. vrednost) u vektor za tip publikacije,
   nivo obrade i nivo obaveznosti. */
  public void addManSubfield(LESubfield lsf) {
    manSubfields.addElement(lsf);
  }

  /** Vraca <i>true</i> ako zadato polje sa potpoljem postoji u vektoru
    potpolja za tip publikacije, u ovom kontekstu.
  */
  public boolean checkTypeSubfield(String field, String subfield) {
    boolean retVal = false;
    for (int i = 0; i < typeSubfields.size(); i++)
      if (((LESubfield)typeSubfields.elementAt(i)).equals(field, subfield)) {
        retVal = true;
        break;
      }
    return retVal;
  }
  /** Vraca <i>true</i> ako zadato polje sa potpoljem postoji u vektoru
    potpolja za nivo obrade, u ovom kontekstu.
  */
  public boolean checkProcSubfield(String field, String subfield) {
    boolean retVal = false;
    for (int i = 0; i < procSubfields.size(); i++)
      if (((LESubfield)procSubfields.elementAt(i)).equals(field, subfield)) {
        retVal = true;
        break;
      }
    return retVal;
  }
  /** Vraca <i>true</i> ako zadato polje sa potpoljem postoji u vektoru
    potpolja za nivo obaveznosti, u ovom kontekstu.
  */
  public boolean checkManSubfield(String field, String subfield) {
    boolean retVal = false;
    for (int i = 0; i < manSubfields.size(); i++)
      if (((LESubfield)manSubfields.elementAt(i)).equals(field, subfield)) {
        retVal = true;
        break;
      }
    return retVal;
  }

  /** Vraca <i>true</i> ako je ovaj kontekst jednak zadatom.
    Zadati kontekst se daje preko uredjene trojke
    (tip_publikacije, nivo_obrade, nivo_obaveznosti).
  */
  public boolean equals(String pubType, int procLevel, int manLevel) {
    boolean retVal = false;
    if (this.pubType.equals(pubType) && this.procLevel == procLevel
         && this.manLevel == manLevel)
      retVal = true;
    return retVal;
  }

  /** Vraca default vrednost zadatog polja sa potpoljem u ovom kontekstu.
     Default vrednost se trazi u vektoru potpolja za nivo obrade.
  */
  public String getDefValue(String field, String subfield) {
    String retVal = null;
    for(int i = 0; i < procSubfields.size(); i++) {
      if (((LESubfield)procSubfields.elementAt(i)).equals(field, subfield)) {
        retVal = ((LESubfield)procSubfields.elementAt(i)).getDefValue();
        break;
      }
    }
    return retVal;
  }

  /** tip publikacije */
  private String pubType;
  /** nivo obrade */
  private int procLevel;
  /** nivo obaveznosti */
  private int manLevel;
  /**  potpolja za tip publikacije ovog konteksta. */
  private Vector typeSubfields = new Vector();   // ...
  /**  potpolja za nivo obrade ovog konteksta. */
  private Vector procSubfields = new Vector();
  /**  potpolja za nivo obaveznosti ovog konteksta. */
  private Vector manSubfields = new Vector();    
  
  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("context");
    df.appendChild(root);

    Element pubType = document.createElement("pubType");
    pubType.appendChild(document.createTextNode(this.pubType));
    root.appendChild(pubType);
    
    Element procLevel = document.createElement("procLevel");
    procLevel.appendChild(document.createTextNode("" + this.procLevel));
    root.appendChild(procLevel);

    Element manLevel = document.createElement("manLevel");
    manLevel.appendChild(document.createTextNode("" + this.manLevel));
    root.appendChild(manLevel);
    
    LESubfield lsf;
    Element typeSubfields = document.createElement("typeSubfields");
    for (int j = 0; j < this.typeSubfields.size(); j++) {
      lsf = (LESubfield)(this.typeSubfields.elementAt(j));
      typeSubfields.appendChild(lsf.createXML(document));
    }
    root.appendChild(typeSubfields);
    
    Element procSubfields = document.createElement("procSubfields");
    for (int j = 0; j < this.procSubfields.size(); j++) {
      lsf = (LESubfield)(this.procSubfields.elementAt(j));
      procSubfields.appendChild(lsf.createXML(document));
    }
    root.appendChild(procSubfields);

    Element manSubfields = document.createElement("manSubfields");
    for (int j = 0; j < this.manSubfields.size(); j++) {
      lsf = (LESubfield)(this.manSubfields.elementAt(j));
      manSubfields.appendChild(lsf.createXML(document));
    }
    root.appendChild(manSubfields);
    
    return df;
  }
  
}
