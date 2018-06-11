package com.gint.app.bisis4.common.records;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a field in a UNIMARC format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class Field implements Serializable {

  public Field() {
    name = "";
    ind1 = ' ';
    ind2 = ' ';
    subfields = new ArrayList();
  }

  public Field(String name) {
    this.name = name;
    ind1 = ' ';
    ind2 = ' ';
    subfields = new ArrayList();
  }

  public String getName() {
    return name;
  }

  public char getInd1() {
    return ind1;
  }

  public char getInd2() {
    return ind2;
  }

  public List getSubfields() {
    return subfields;
  }

  public int getSubfieldCount() {
    return subfields.size();
  }

  public Subfield getSubfield(int index) {
    if (index < subfields.size())
      return (Subfield)subfields.get(index);
    else
      return null;
  }

  public Subfield getSubfield(char name) {
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = (Subfield)subfields.get(i);
      if (sf.getName() == name)
        return sf;
    }
    return null;
  }
  
  public List getSubfields(char name) {
    List retVal = new ArrayList();
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = (Subfield)subfields.get(i);
      if (sf.getName() == name)
        retVal.add(sf);
    }
    return retVal;
  }
  
  public String getSubfieldContent(char name) {
    Subfield sf = getSubfield(name);
    if (sf == null)
      return null;
    return sf.getContent();
  }
  
  public void add(Subfield sf) {
    subfields.add(sf);
  }
  
  public void remove(Subfield sf) {
    subfields.remove(sf);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setInd1(char ind1) {
    this.ind1 = ind1;
  }

  public void setInd2(char ind2) {
    this.ind2 = ind2;
  }

  public void setSubfields(List subfields) {
    this.subfields = subfields;
  }

  public void sort() {
    for (int i = 1; i < subfields.size(); i++) {
      for (int j = 0; j < subfields.size() - i; j++) {
        Subfield sf1 = (Subfield)subfields.get(j);
        Subfield sf2 = (Subfield)subfields.get(j+1);
        if (sf1.getName() > sf2.getName()) {
          subfields.set(j, sf2);
          subfields.set(j+1, sf1);
        }
      }
    }
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = (Subfield)subfields.get(i);
      sf.sort();
    }
  }
  
  public void pack() {
    Iterator it = subfields.iterator();
    while (it.hasNext()) {
      Subfield sf = (Subfield)it.next();
      sf.pack();
      if (sf.getSecField() == null && sf.getSubsubfieldCount() == 0 && 
          sf.getContent().trim().equals(""))
        it.remove();
    }
  }

  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("{");
    retVal.append(name);
    retVal.append(":");
    retVal.append(ind1);
    retVal.append(ind2);
    retVal.append(":");
    for (int i = 0; i < subfields.size(); i++) {
      retVal.append(subfields.get(i).toString());
    }
    retVal.append("}");
    return retVal.toString();
  }

  private String name;
  private char ind1;
  private char ind2;
  private List subfields;
}