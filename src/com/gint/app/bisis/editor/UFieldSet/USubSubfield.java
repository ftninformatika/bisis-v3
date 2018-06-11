package com.gint.app.bisis.editor.UFieldSet;

import java.util.Enumeration;
import java.util.Vector;
import com.objectspace.jgl.*;

import com.gint.util.xml.XMLUtils;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

/** Potpotpolje. */
public class USubSubfield {
	public USubSubfield(String      fieldID,
                   String      subfieldID,
									 String      subSubfieldID,
                   int         length,
                   boolean     mandatory,
                   boolean     repeatable,
                   String      name,
                   String         status,
                   String      defValue, 
                   int         controlID) {
    this.fieldID = new String(fieldID);
    this.subfieldID = new String(subfieldID);
    this.subSubfieldID = new String(subSubfieldID);
    this.length = length;
    this.mandatory = mandatory;
    this.repeatable = repeatable;
    this.name = new String(name);
    this.status = status;
    this.defValue = defValue == null ? null : new String(defValue);
    this.controlID = controlID;
		this.controlType = null;
		this.subfieldCodes = null;
  }
  
  public USubSubfield(String fieldID, String subfieldID, Node root) {
    NamedNodeMap attrs = root.getAttributes();
    
    this.fieldID = fieldID;
    this.subfieldID = subfieldID;
    this.subSubfieldID = attrs.getNamedItem("name").getNodeValue();
    length       = Integer.parseInt(attrs.getNamedItem("length").getNodeValue());
    mandatory    = attrs.getNamedItem("mandatory").getNodeValue().equals("true") ? true : false;
    repeatable   = attrs.getNamedItem("repeatable").getNodeValue().equals("true") ? true : false;
    name         = attrs.getNamedItem("desc").getNodeValue();
    status       = attrs.getNamedItem("status").getNodeValue();
    defValue     = attrs.getNamedItem("default").getNodeValue();
    if (defValue.equals("*null*"))
      defValue = null;
    controlID    = Integer.parseInt(attrs.getNamedItem("controlID").getNodeValue()); 

    controlType = new UControlType(XMLUtils.getSubnodeByName(root, "UControlType"));
    
    Node curr;
    Vector usc = XMLUtils.getSubnodesByName(root, "USubfieldCodes");
    if (usc != null) {
      subfieldCodes = new HashMap();
      for (int i = 0; i < usc.size(); i++) {
        curr = (Node)usc.elementAt(i);
        attrs = curr.getAttributes();
        subfieldCodes.add(attrs.getNamedItem("name").getNodeValue(), 
                         new USubfieldCodes(curr));
      }
    } else
      subfieldCodes = null;
    
  }
  public USubSubfield(USubSubfield sf, HashMap tControlTypes, HashMap tSubfieldCodes) {
    this.fieldID = new String(sf.fieldID);
    this.subfieldID = new String(sf.subfieldID);
    this.subSubfieldID = new String(sf.subSubfieldID);
    this.length = sf.length;
    this.mandatory = sf.mandatory;
    this.repeatable = sf.repeatable;
    this.name = new String(sf.name);
    this.status = sf.status;
    this.defValue = sf.defValue == null ? null : new String(sf.defValue);
    this.controlID = sf.controlID;

    controlType  = new UControlType((UControlType)tControlTypes.get(""+ controlID));  // trazimo po contrID-u

    //
    // Collect subfieldCodes
    //
    HashSet hs;
    Enumeration e;
    hs = (HashSet)tSubfieldCodes.get(subSubfieldID+subfieldID+fieldID);
    if(hs != null) {
      subfieldCodes = new HashMap();
      e = hs.elements();
      while(e.hasMoreElements()) {
        USubfieldCodes sfc = (USubfieldCodes)e.nextElement();
        subfieldCodes.add(sfc.getSfcID(), new USubfieldCodes(sfc));
      }
    }
  }

  public void setSubSubfieldID(String subfieldID) {
    this.subSubfieldID = new String(subSubfieldID);
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  public void setRepeatable(boolean repeatable) {
    this.repeatable = repeatable;
  }

  public void setName(String name) {
    this.name = new String(name);
  }

  public void setStatus(String status) {
    this.status = status;
  }
  
  public void setLength(int length) {
    this.length = length;
  }

  public void setDefValue(String defValue) {
    this.defValue = new String(defValue);
  }

  public void setControlID(int controlID) {
    this.controlID = controlID;
  }

	public void setControlType(UControlType controlType) {
		this.controlType = controlType;
	}

	public void setSubfieldCodes(HashMap subfieldCodes) {
		this.subfieldCodes = subfieldCodes;
	}

  //GET!!!
  public String getFieldID() {
    return subfieldID;
  }

  public String getSubfieldID() {
    return subfieldID;
  }

  public String getSubSubfieldID() {
    return subSubfieldID;
  }

  public boolean getMandatory() {
    return mandatory;
  }

  public boolean getRepeatable() {
    return repeatable;
  }

  public int getLength() {
    return length;
  }

  public String getStatus() {
    return status;
  }

  public String getName() {
    return name;
  }

  public String getDefValue() {
    return defValue;
  }

  public int getControlID() {
    return controlID;
  }

	public UControlType getControlType() {
		return controlType;
	}

	public HashMap getSubfieldCodes() {
		return subfieldCodes;
	}

  private String        fieldID;
  private String        subfieldID;
  private String        subSubfieldID;
  private int           length;
  private boolean       mandatory;
  private boolean       repeatable;
  private String        name;
  private String        status;
  private String        defValue;
  private int           controlID;

  private UControlType  controlType;
	private HashMap       subfieldCodes;

  public String toString() {
    return fieldID + ", subfieldID: " + subfieldID + ", subSubfieldID: " + subSubfieldID + 
              ", length: " + length + ", mandatory: " + mandatory + ", repatable: " + repeatable + 
              ", name: " + name + ", status: " + status + ", defValue: " + defValue + 
              ", controlID: " + controlID + ", controlType: " + controlType +
              ", subfieldCodes: " + subfieldCodes;
  }

  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("USubSubfield");
    root.setAttribute("name", "" + this.subSubfieldID);
    root.setAttribute("desc", this.name);
    root.setAttribute("length", "" + this.length);
    root.setAttribute("mandatory", "" + this.mandatory);
    root.setAttribute("repeatable", "" + this.repeatable);
    root.setAttribute("status", this.status);
    root.setAttribute("default", (this.defValue != null ? this.defValue : "*null*"));
    root.setAttribute("controlID", "" + this.controlID);

    root.appendChild(controlType.createXML(document));
    
    String key;
    Enumeration en;
    if (subfieldCodes != null) {
      en = subfieldCodes.keys();
      while (en.hasMoreElements()) {
        key = (String)en.nextElement();
        USubfieldCodes usc = (USubfieldCodes)subfieldCodes.get(key);
        root.appendChild(usc.createXML(document));
      }
    }

    df.appendChild(root);
    
    return df;
  }
	
}

