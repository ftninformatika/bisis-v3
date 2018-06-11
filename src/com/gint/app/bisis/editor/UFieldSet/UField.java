package com.gint.app.bisis.editor.UFieldSet;

import java.util.Vector;
import java.util.Enumeration;
import com.objectspace.jgl.*;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

import com.gint.util.xml.XMLUtils;

/** Polje. */
public class UField {

  public UField(String     fieldID,
                boolean    mandatory,
                boolean    repeatable,
                boolean    secondarity,
                String     name,
                String     desc_fi,
                String     desc_si,
                String     def_fi,
                String     def_si,
                HashMap    tFirstIndicators,
                HashMap    tSecondIndicators,
                HashMap    tSubfields, 
                HashMap    tControlTypes,
                HashMap    tInternalCodes,
								HashMap    tSubSubfields,
								HashMap    tSubfieldCodes,
								HashMap    tExtCodeTypes, HashMap tExternalCodes) {
    
    this.fieldID = new String(fieldID);
    this.mandatory = mandatory;
    this.repeatable = repeatable;
    this.secondarity = secondarity;
    this.name = new String(name);
    this.desc_fi = desc_fi == null ? null : new String(desc_fi);
    this.desc_si = desc_si == null ? null : new String(desc_si);
    this.def_fi = def_fi == null ? null : new String(def_fi);
    this.def_si = def_si == null ? null : new String(def_si);

    //
    // Collect indicators
    //
    first =  null;
    second = null;

    //
    // Store First indicators
    // 
    UInd ind;
    HashSet hs;
    Enumeration e;
    hs = (HashSet)tFirstIndicators.get(fieldID);
    if(hs != null) {
      first =  new HashMap();
      e = hs.elements();
      while(e.hasMoreElements()) {
        ind = (UInd)e.nextElement();
        first.add(ind.getIndID(), new UInd(ind));
      }
    }

    //
    // Store Second indicators
    // 
    hs = (HashSet)tSecondIndicators.get(fieldID);
    if(hs != null) {
      second = new HashMap();
      e = hs.elements();
      while (e.hasMoreElements()) {
        ind = (UInd)e.nextElement();
        second.add(ind.getIndID(), new UInd(ind));
      }
    }
    
    //
    // Collect subfields
    //
    hs = (HashSet)tSubfields.get(fieldID);
    if(hs != null) {
      subfields = new HashMap();
      e = hs.elements();
      while(e.hasMoreElements()) {
        USubfield sf = (USubfield)e.nextElement();
        subfields.add(sf.getSubfieldID(), 
					            new USubfield(sf, tControlTypes, tInternalCodes, 
											              tSubSubfields, tSubfieldCodes, tExtCodeTypes, tExternalCodes));
      }
    }

  }

  public UField(Node root, HashMap tExtCodeTypes, HashMap tExternalCodes) {
    NamedNodeMap attrs = root.getAttributes();
    
    fieldID     = attrs.getNamedItem("name").getNodeValue();
    mandatory   = attrs.getNamedItem("mandatory").getNodeValue().equals("true") ? true : false;
    repeatable  = attrs.getNamedItem("repeatable").getNodeValue().equals("true") ? true : false;
    secondarity = attrs.getNamedItem("secondarity").getNodeValue().equals("true") ? true : false;
    name        = attrs.getNamedItem("desc").getNodeValue();
    
    Node curr;
    first =  null;
    second = null;
    NodeList values;
    Node indicator = XMLUtils.getSubnodeByName(root, "first");
    if (indicator != null) {
      first = new HashMap();
      attrs = indicator.getAttributes();
      
      def_fi = attrs.getNamedItem("default").getNodeValue();
      if (def_fi.equals("*null*"))
        def_fi = null;
      
      desc_fi = attrs.getNamedItem("desc").getNodeValue();
      
      values = indicator.getChildNodes();
      for (int i= 0; i < values.getLength(); i++) {
        curr = values.item(i);
        attrs = curr.getAttributes();
        first.add(attrs.getNamedItem("number").getNodeValue(), new UInd(curr));
      }
    }

    indicator = XMLUtils.getSubnodeByName(root, "second");
    if (indicator != null) {
      second = new HashMap();
      attrs  = indicator.getAttributes();
      
      def_si = attrs.getNamedItem("default").getNodeValue();
      if (def_si.equals("*null*"))
        def_si = null;
      
      desc_si = attrs.getNamedItem("desc").getNodeValue();
      
      values = indicator.getChildNodes();
      for (int i= 0; i < values.getLength(); i++) {
        curr = values.item(i);
        attrs = curr.getAttributes();
        second.add(attrs.getNamedItem("number").getNodeValue(), new UInd(curr));
      }
    }
    
    String key;
    Vector uSubfields = XMLUtils.getSubnodesByName(root, "USubfield");
    if (uSubfields != null) {
      subfields = new HashMap();
      for (int i = 0; i < uSubfields.size(); i++) {
        curr = (Node)uSubfields.elementAt(i);
        attrs = curr.getAttributes();
        key = attrs.getNamedItem("name").getNodeValue();
        subfields.add(key, new USubfield(fieldID, curr, tExtCodeTypes, tExternalCodes));
      }
    }
  }
  
  public void setFieldID(String fieldID) {
    this.fieldID = new String(fieldID);
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  public void setRepeatable(boolean repeatable) {
    this.repeatable = repeatable;
  }

  public void setSecondarity(boolean secondarity) {
    this.secondarity = secondarity;
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public void setFirst(HashMap first) {
    this.first = first;
  }

  public void setSecond(HashMap second) {
    this.second = second;
  }

  public void setDesc_fi(String desc_fi) {
    this.desc_fi = new String(desc_fi);
  }

  public void setDesc_si(String desc_si) {
    this.desc_si = new String(desc_si);
  }

  public void setDesf_fi(String def_fi) {
    this.def_fi = new String(def_fi);
  }

  public void setDef_fi(String def_fi) {
    this.def_fi = new String(def_fi);
  }

  public void setSubfields(HashMap subfields) {
    this.subfields = subfields;
  }

  public String getFieldID() {
    return fieldID;
  }

  public String getName() {
    return name;
  }

  public boolean getMandatory() {
    return mandatory;
  }

  public boolean getRepeatable() {
    return repeatable;
  }

  public boolean getSecondarity() {
    return secondarity;
  }

  public HashMap getFirst() {
    return first;
  }

  public HashMap getSecond() {
    return second;
  }

  public String getDesc_fi() {
    return desc_fi;
  }

  public String getDesc_si() {
    return desc_si;
  }

  public String getDef_fi() {
    return def_fi;
  }

  public String getDef_si() {
    return def_si;
  }

  public HashMap getSubfields() {
    return subfields;
  }

  private String     fieldID;
  private boolean    mandatory;
  private boolean    repeatable;
  private boolean    secondarity;
  private String     name;
  private HashMap    first;
  private HashMap    second;
  private HashMap    subfields;
  private String     desc_fi;
  private String     desc_si;
  private String     def_fi;
  private String     def_si;
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(fieldID)
    .append(", mandatory: ").append(mandatory)
    .append(", repatable: ").append(repeatable)
    .append(", secondarity: ").append(secondarity)
    .append(", name: ").append(name)
    .append(", first: ").append(first)
    .append(", second: ").append(second)
    .append(", subfields: ").append(subfields)
    .append(", desc_fi: ").append(desc_fi)
    .append(", desc_si: ").append(desc_si)
    .append(", def_fi: ").append(def_fi)
    .append(", def_si: ").append(def_si);
    return sb.toString();
  }
  
  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("UField");
    root.setAttribute("name", "" + this.fieldID);
    root.setAttribute("desc", this.name);
    root.setAttribute("mandatory", "" + this.mandatory);
    root.setAttribute("repeatable", "" + this.repeatable);
    root.setAttribute("secondarity", "" + this.secondarity);
    
    Enumeration en;
    String key;
    if (first != null) {
      Element firstInd = document.createElement("first");
      if (desc_fi != null)
        firstInd.setAttribute("desc", desc_fi);
      else
        firstInd.setAttribute("desc", "*null*");

      if (def_fi != null)
        firstInd.setAttribute("default", def_fi);
      else
        firstInd.setAttribute("default", "*null*");
      
      en = first.keys();
      while (en.hasMoreElements()) {
        key = (String)en.nextElement();
        UInd ui = (UInd)first.get(key);
        firstInd.appendChild(ui.createXML(document));
      }
      root.appendChild(firstInd);
    } 
    
    if (second != null) {
      Element secondInd = document.createElement("second");
      if (desc_si != null)
        secondInd.setAttribute("desc", desc_si);
      else
        secondInd.setAttribute("desc", "*null*");

      if (def_si != null)
        secondInd.setAttribute("default", def_si);
      else
        secondInd.setAttribute("default", "*null*");

      en = second.keys();
      while (en.hasMoreElements()) {
        key = (String)en.nextElement();
        UInd ui = (UInd)second.get(key);
        secondInd.appendChild(ui.createXML(document));
      }
      root.appendChild(secondInd);
    } 
    
    en = subfields.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      USubfield usf = (USubfield)subfields.get(key);
      root.appendChild(usf.createXML(document));
    }

    df.appendChild(root);
    return df;
  }
  
}
