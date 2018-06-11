package com.gint.app.bisis4.common.records;

/**
 * Represents a UNIMARC record.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Record implements Serializable {

  // Publication type codes
  public static final String TYPE_M1 = "001";
  public static final String TYPE_M2 = "002";
  public static final String TYPE_M3 = "003";
  public static final String TYPE_S1 = "004";
  public static final String TYPE_S2 = "005";
  public static final String TYPE_A1 = "006";
  public static final String TYPE_NO = "007";
  public static final String TYPE_C_ = "008";
  public static final String TYPE_DS = "009";
  public static final String TYPE_R_ = "010";
  public static final String TYPE_NK = "017";
  public static final String TYPE_NN = "027";
  public static final String TYPE_RD = "037";
  
  /**
   * Default constructor. Initial publication type is monograph (TYPE_M1).
   */
  public Record() {
    recordID = new RecordID();
    pubType = TYPE_M1;
    fields = new ArrayList();
  }

  /**
   * Initializes the new record with the given record id. The initial 
   * publication type is monograph (TYPE_M1).
   * @param recordID The record identifier.
   */
  public Record(RecordID recordID) {
    this.recordID = recordID;
    pubType = TYPE_M1;
    fields = new ArrayList();
  }

  /**
   * Initializes the new record with the given record id and the list of fields.
   * @param recordID The given record identifier
   * @param fields The initial list of fields 
   */
  public Record(RecordID recordID, List fields) {
    this.recordID = recordID;
    pubType = TYPE_M1;
    this.fields = fields;
  }

  public RecordID getRecordID() {
    return recordID;
  }

  public void setRecordID(RecordID recordID) {
    this.recordID = recordID;
  }

  public String getPubType() {
    return pubType;
  }

  public void setPubType(String pubType) {
    this.pubType = pubType;
  }

  public List getFields() {
    return fields;
  }
  
  public int getFieldCount() {
    return fields.size();
  }
  
  public Field getField(int index) {
    if (index >= fields.size() || index < 0)
      return null;
    return (Field)fields.get(index);
  }
  
  public Field getField(String name) {
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field)fields.get(i);
      if (field.getName().equals(name)) {
        return field;
      }
    }
    return null;
  }
  
  public List getFields(String name) {
    ArrayList retVal = new ArrayList();
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field)fields.get(i);
      if (field.getName().equals(name))
        retVal.add(field);
    }
    return retVal;
  }
  
  public Subfield getSubfield(String name) {
    if (name.length() != 4)
      return null;
    String fieldName = name.substring(0, 3);
    Field f = getField(fieldName);
    if (f == null)
      return null;
    Subfield sf = f.getSubfield(name.charAt(3));
    return sf;
  }
  
  public List getSubfields(String name) {
    List retVal = new ArrayList();
    if (name.length() != 4)
      return retVal;
    String fieldName = name.substring(0, 3);
    Iterator it1 = getFields(fieldName).iterator();
    while (it1.hasNext()) {
      Field f = (Field)it1.next();
      Iterator it2 = f.getSubfields(name.charAt(3)).iterator();
      while (it2.hasNext()) {
        retVal.add(it2.next());
      }
    }
    return retVal;
  }
  
  public String getSubfieldContent(String name) {
    Subfield sf = getSubfield(name);
    if (sf == null)
      return null;
    else
      return sf.getContent();
  }

  public void sort() {
    for (int i = 1; i < fields.size(); i++) {
      for (int j = 0; j < fields.size() - i; j++) {
        Field f1 = (Field)fields.get(j);
        Field f2 = (Field)fields.get(j+1);
        if (f1.getName().compareTo(f2.getName()) > 0) {
          fields.set(j, f2);
          fields.set(j+1, f1);
        }
      }
    }
    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field)fields.get(i);
      f.sort();
    }
  }
  
  public void pack() {
    Iterator it = fields.iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      f.pack();
      if (f.getSubfieldCount() == 0)
        it.remove();
    }
  }

  public String toString() {
    StringBuffer retVal = new StringBuffer();
    for (int i = 0; i < fields.size(); i++) {
      retVal.append(fields.get(i).toString());
    }
    return retVal.toString();
  }

  private RecordID recordID;
  private String pubType;
  private List fields;
}
