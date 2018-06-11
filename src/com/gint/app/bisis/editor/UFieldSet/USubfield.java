package com.gint.app.bisis.editor.UFieldSet;

import java.util.Vector;
import java.util.Enumeration;
import com.objectspace.jgl.*;

import com.gint.util.xml.XMLUtils;

// XML stuff
import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;


/** Potpolje. */
public class USubfield {

  public USubfield(String      fieldID,
                   String      subfieldID,
                   int         length,
                   boolean     mandatory,
                   boolean     repeatable,
                   String      name,
                   String         status,
                   String      defValue, 
                   String      tesID,
                   int         controlID) {
    this.fieldID = new String(fieldID);
    this.subfieldID = new String(subfieldID);
    this.length = length;
    this.mandatory = mandatory;
    this.repeatable = repeatable;
    this.name = new String(name);
    this.status = status;
    this.defValue = defValue == null ? null : new String(defValue);
    this.tesID = tesID;
    this.controlID = controlID;

		this.controlType = null;
		this.internalCodes = null;
		this.subSubfields = null;
  }

  public USubfield(String fieldID, Node root, HashMap tExtCodeTypes, HashMap tExternalCodes) {
    NamedNodeMap attrs = root.getAttributes();
    
    this.fieldID = fieldID;
    subfieldID   = attrs.getNamedItem("name").getNodeValue();
    length       = Integer.parseInt(attrs.getNamedItem("length").getNodeValue());
    mandatory    = attrs.getNamedItem("mandatory").getNodeValue().equals("true") ? true : false;
    repeatable   = attrs.getNamedItem("repeatable").getNodeValue().equals("true") ? true : false;
    name         = attrs.getNamedItem("desc").getNodeValue();
    status       = attrs.getNamedItem("status").getNodeValue();
    defValue     = attrs.getNamedItem("default").getNodeValue();
    if (defValue.equals("*null*"))
      defValue = null;
    tesID        = attrs.getNamedItem("typeOfExternalCodeBook").getNodeValue(); 
    controlID    = Integer.parseInt(attrs.getNamedItem("controlID").getNodeValue()); 

    controlType = new UControlType(XMLUtils.getSubnodeByName(root, "UControlType"));

    Node curr;
    Vector intCodes = XMLUtils.getSubnodesByName(root, "UInternalCodes");
    if (intCodes != null) {
      internalCodes = new HashMap();
      for (int i = 0; i < intCodes.size(); i++) {
        curr = (Node)intCodes.elementAt(i);
        attrs = curr.getAttributes();
        internalCodes.add(attrs.getNamedItem("name").getNodeValue(), new UInternalCodes(curr));
      }
    } else
      internalCodes = null;
    
    Vector ssf = XMLUtils.getSubnodesByName(root, "USubSubfield");
    if (ssf != null) {
      subSubfields = new HashMap();
      for (int i = 0; i < ssf.size(); i++) {
        curr = (Node)ssf.elementAt(i);
        attrs = curr.getAttributes();
        subSubfields.add(attrs.getNamedItem("name").getNodeValue(), 
                         new USubSubfield(fieldID, this.subfieldID, curr));
      }
    } else
      subSubfields = null;


		extCodeTypes  = tExtCodeTypes;
		externalCodes = tExternalCodes;
  }

  public USubfield(USubfield sf,
		               HashMap tControlTypes, HashMap tInternalCodes, HashMap tSubSubfields,
									 HashMap tSubfieldCodes, HashMap tExtCodeTypes, HashMap tExternalCodes) {
    this.fieldID = new String(sf.fieldID);
    this.subfieldID = new String(sf.subfieldID);
    this.length = sf.length;
    this.mandatory = sf.mandatory;
    this.repeatable = sf.repeatable;
    this.name = new String(sf.name);
    this.status = sf.status;
    this.defValue = sf.defValue == null ? null : new String(sf.defValue);
    this.tesID = sf.tesID;
    this.controlID = sf.controlID;

    controlType  = new UControlType((UControlType)tControlTypes.get(""+ controlID));  // trazimo po contrID-u

    //
    // Collect internal Codes
    //
    HashSet hs;
    Enumeration e;
    hs = (HashSet)tInternalCodes.get(subfieldID+fieldID);
    if(hs != null) {
      internalCodes = new HashMap();
      e = hs.elements();
      while(e.hasMoreElements()) {
        UInternalCodes ic = (UInternalCodes)e.nextElement();
        internalCodes.add(ic.getIcID(), new UInternalCodes(ic));
      }
    }

    //
    // Collect subSubfields
    //
    hs = (HashSet)tSubSubfields.get(subfieldID+fieldID);
    if(hs != null) {
      subSubfields = new HashMap();
      e = hs.elements();
      while(e.hasMoreElements()) {
        USubSubfield ssf = (USubSubfield)e.nextElement();
        subSubfields.add(ssf.getSubSubfieldID(), new USubSubfield(ssf, tControlTypes, tSubfieldCodes));
      }
    }
		
		extCodeTypes  = tExtCodeTypes;
		externalCodes = tExternalCodes;
 }

  public void setSubfieldID(String subfieldID) {
    this.subfieldID = new String(subfieldID);
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

  public void setTesID(String tesID) {
    this.tesID = new String(tesID);
  }

	public void setControlType(UControlType controlType) {
		this.controlType = controlType;
	}

	public void setInternalCodes(HashMap internalCodes) {
		this.internalCodes = internalCodes;
	}

	public void setSubSubfields(HashMap subSubfields) {
		this.subSubfields = subSubfields;
	}

  //GET!!!
  public String getSubfieldID() {
    return subfieldID;
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

  public String getTesID() {
    return tesID;
  }

	public UControlType getControlType() {
		return controlType;
	}

	public HashMap getInternalCodes() {
		return internalCodes;
	}
	public HashMap getExtCodeTypes() {
		return extCodeTypes;
	}
	public HashMap getExternalCodes() {
		return externalCodes;
	}

	public HashMap getSubSubfields() {
		return subSubfields;
	}

  private String        fieldID;
  private String        subfieldID;
  private int           length;
  private boolean       mandatory;
  private boolean       repeatable;
  private String        name;
  private String        status;
  private String        defValue;
  private String        tesID;       // Tip eksternog sifarnika
  private int           controlID;

  private UControlType  controlType;
  private HashMap       internalCodes;
	private HashMap       subSubfields;
	private HashMap       extCodeTypes;
	private HashMap       externalCodes;

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(fieldID).append(", subfieldID: ").append(subfieldID)
    .append(", length: ").append(length)
    .append(", mandatory: ").append(mandatory)
    .append(", repatable: ").append(repeatable)
    .append(", name: ").append(name)
    .append(", status: ").append(status)
    .append(", defValue: ").append(defValue)
    .append(", tesID: ").append(tesID)
    .append(", controlID: ").append(controlID)
    .append(", controlType: ").append(controlType)
    .append(", internalCodes: ").append(internalCodes)
    .append(", subSubfields: ").append(subSubfields);
//    .append(", extCodeTypes: ").append(extCodeTypes)
//    .append(", externalCodes: ").append(externalCodes);
    return sb.toString();
  }
  
  public DocumentFragment createXML(DocumentImpl document) {
    DocumentFragment df = document.createDocumentFragment();
    Element root = document.createElement("USubfield");
    root.setAttribute("name", "" + this.subfieldID);
    root.setAttribute("desc", this.name);
    root.setAttribute("length", "" + this.length);
    root.setAttribute("mandatory", "" + this.mandatory);
    root.setAttribute("repeatable", "" + this.repeatable);
    root.setAttribute("status", this.status);
    root.setAttribute("default", (this.defValue != null ? this.defValue : "*null*"));
    root.setAttribute("typeOfExternalCodeBook", (this.tesID != null)?this.tesID:"*null*");
    root.setAttribute("controlID", "" + this.controlID);
    
    root.appendChild(controlType.createXML(document));
    
    String key;
    Enumeration en;
    if (internalCodes != null) {
      en = internalCodes.keys();
      while (en.hasMoreElements()) {
        key = (String)en.nextElement();
        UInternalCodes uic = (UInternalCodes)internalCodes.get(key);
        root.appendChild(uic.createXML(document));
      }
    }
    
    if (subSubfields != null) {
      en = subSubfields.keys();
      while (en.hasMoreElements()) {
        key = (String)en.nextElement();
        USubSubfield ussf = (USubSubfield)subSubfields.get(key);
        root.appendChild(ussf.createXML(document));
      }
    }
    
    df.appendChild(root);
    return df;
  }
}

