package com.gint.app.bisis4.common.records;

/**
 * Represents a subfield in a UNIMARC record.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.gint.util.string.UnimarcConverter;

public class Subfield implements Serializable {

  public Subfield() {
    name = ' ';
    content = "";
    subsubfields = new ArrayList();
    secField = null;
  }

  public Subfield(char name) {
    this.name = name;
    content = "";
    subsubfields = new ArrayList();
    secField = null;
  }

  public char getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  public List getSubsubfields() {
    return subsubfields;
  }

  public Field getSecField() {
    return secField;
  }

  public int getSubsubfieldCount() {
    return subsubfields.size();
  }

  public Subsubfield getSubsubfield(int index) {
    if (index < subsubfields.size())
      return (Subsubfield)subsubfields.get(index);
    else
      return null;
  }

  public Subsubfield getSubsubfield(char name) {
    for (int i = 0; i < subsubfields.size(); i++) {
      Subsubfield ssf = (Subsubfield)subsubfields.get(i);
      if (ssf.getName() == name)
        return ssf;
    }
    return null;
  }

  public void setName(char name) {
    this.name = name;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setSubsubfields(List subsubfields) {
    this.subsubfields = subsubfields;
  }

  public void setSecField(Field secField) {
    this.secField = secField;
  }

  public void sort() {
    if (subsubfields.size() > 0) {
      for (int i = 1; i < subsubfields.size(); i++) {
        for (int j = 0; j < subsubfields.size() - i; j++) {
          Subsubfield ssf1 = (Subsubfield)subsubfields.get(j);
          Subsubfield ssf2 = (Subsubfield)subsubfields.get(j+1);
          if (ssf1.getName() > ssf2.getName()) {
            subsubfields.set(j, ssf2);
            subsubfields.set(j+1, ssf1);
          }
        }
      }
    }
  }
  
  public void pack() {
    if (secField != null) {
      secField.pack();
      if (secField.getSubfieldCount() == 0)
        secField = null;
    } else if (subsubfields.size() > 0) {
      Iterator it = subsubfields.iterator();
      while (it.hasNext()) {
        Subsubfield ssf = (Subsubfield)it.next();
        if (ssf.getContent().trim().equals(""))
          it.remove();
      }
    }
  }

  public String toString() {
    UnimarcConverter conv = new UnimarcConverter();
    StringBuffer retVal = new StringBuffer();
    retVal.append("[");
    retVal.append(name);
    retVal.append("]");
    if (secField != null) {
      retVal.append(secField.toString());
    } else if (subsubfields.size() > 0) {
      for (int i = 0; i < subsubfields.size(); i++) {
        retVal.append(subsubfields.get(i).toString());
      }
    } else {
      retVal.append(content);
    }
    return retVal.toString();
  }

  private char name;
  private String content;
  private List subsubfields;
  private Field secField;
}