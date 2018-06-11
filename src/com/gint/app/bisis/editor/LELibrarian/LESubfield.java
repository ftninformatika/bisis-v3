package com.gint.app.bisis.editor.LELibrarian;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Klasa koja enkapsulira potpolje sa imenom polja kome pripada,
  ID-om potpolja i default vrednoscu potpolja.
*/
public class LESubfield {
  /** Konstruktor. */
  public LESubfield(String field, String subfield, String defValue) {
    this.field = new String(field);
    this.subfield = new String(subfield);
    if (defValue != null)
      this.defValue = new String(defValue);
  }
  /** Konstruktor. */
  public LESubfield(Node root) {
    field   = root.getAttributes().getNamedItem("field").getNodeValue();
    subfield = root.getAttributes().getNamedItem("subfield").getNodeValue();
    defValue  = root.getAttributes().getNamedItem("defValue").getNodeValue();
    if (defValue.equals("*null*")) {
      defValue = null;
    }
  }
  
  /** Copy konstruktor. */
  public LESubfield(LESubfield sf) {
    this.field = new String(sf.getField());
    this.subfield = new String(sf.getSubfield(sf.getField()));
    if (sf.getDefValue() != null)
      this.defValue = new String(sf.getDefValue());
    else
      this.defValue = null;
  }

  /** Postavlja default vrednost potpolja. */
  public void setDefValue(String defValue) {
    this.defValue = defValue;
  }

  /** Vraca default vrednost potpolja. */
  public String getDefValue() {
    return defValue;
  }

  /** Vraca String reprezentaciju potpolja. */
  public String toString() {
    return field + subfield + ": " + (defValue== null ? "*null*":defValue);
  }

  /** Vraca <i>true</i> ako se ovo potpolje jednako prosledjenom. */
  public boolean equals(String field, String subfield) {
    boolean retVal = false;
    if (this.field.equals(field) && this.subfield.equals(subfield))
      retVal = true;
    return retVal;
  }

  /** Vraca <i>true</i> ako se ovo potpolje jednako prosledjenom. */
  public boolean equals(String field) {
  boolean retVal = false;
  if (this.field.equals(field))
    retVal = true;
  return retVal;
  }

 /** Vraca string u kojem se nalazi ID potpolja za zadato polje. */
  public String getSubfield(String field) {
    String retVal = "";
    if (this.field.equals(field))
      retVal = this.subfield;
    return retVal;
  }

  /** Vraca naziv polja kome ovo potpolje pripada. */
  public String getField() {
    String retVal = "";
    retVal = this.field;
    return retVal;
  }

  /** Naziv polja kome ovo potpolje pripada. */
  private String field;
  /** ID potpolja. */
  private String subfield;
  /** Default vrednost potpolja. */
  private String defValue = null;
   
  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("LESubfield");
    df.appendChild(root);
    
    root.setAttribute("field", field);
    root.setAttribute("subfield", getSubfield(field));
    root.setAttribute("defValue", (defValue == null)?"*null*":defValue);
    
    return df;
  }
}
