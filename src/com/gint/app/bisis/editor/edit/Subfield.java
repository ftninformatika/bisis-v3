package com.gint.app.bisis.editor.edit;

import java.util.Vector;

public class Subfield {

  public Subfield(String name, String content, Field secField, Vector subsubfields) {
    this.name = name;
    this.content = content;
    this.secFields = new Vector();
    if (secField != null)
      this.secFields.addElement(secField);
    this.subsubfields = subsubfields;
  }

  public Subfield(String name, String content) {
    this (name, content, null, new Vector());
  }

  public Subfield(Subfield sf) {
    this.name = new String(sf.name);
    this.content = new String(sf.content);
    this.secFields = new Vector();
    if (sf.secFields.size() > 0)
      this.secFields.addElement(new Field(sf.getSecField()));
    this.subsubfields = new Vector();
    for (int i = 0; i < sf.subsubfields.size(); i++)
      this.subsubfields.addElement(new Subfield((Subfield)sf.subsubfields.elementAt(i)));
  }
  
  public Subfield(Subfield sf, String content) {
    this.name = sf.name;
    this.content = content;
    this.secFields = new Vector();
    if (sf.secFields.size() > 0)
      this.secFields.addElement(new Field(sf.getSecField()));
    this.subsubfields = new Vector();
    for (int i = 0; i < sf.subsubfields.size(); i++)
      this.subsubfields.addElement(new Subfield((Subfield)sf.subsubfields.elementAt(i)));
  }

  public String getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  public Field getSecField() {
    if (secFields.size() > 0)
      return (Field)secFields.elementAt(0);
    else
      return null;
  }
  
  public Vector getSubsubfields() {
    return subsubfields;
  }

  /** Vraca potpotpolje na osnovu ID-a. */
  public Subfield getSubSubfieldByName(String s) {
    for(int i = 0; i < subsubfields.size(); i++) {
      if(((Subfield)subsubfields.elementAt(i)).getName().equals(s)) {
        return (Subfield)subsubfields.elementAt(i);
      } // if
    } // for
    return null;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setSecField(Field secField) {
    secFields = new Vector();
    secFields.addElement(secField);
  }

  public void setSubsubfields(Vector subsubfields) {
    this.subsubfields = subsubfields;
  }

  public void addSubsubfield(Subfield sf) {
    subsubfields.addElement(sf);
  }

  public Vector getSecFieldInVect() {
    return secFields;
  }

  public boolean containsSecField() {
    return secFields.size() > 0;
  }

  public boolean containsSubsubfields() {
    return subsubfields.size() > 0;
  }

  public String toString() {
    return name + "["+ content + "]";
  }

  private String name;
  private String content;
  private Vector secFields;
  private Vector subsubfields;
}
