package com.gint.app.bisis4.common.records;

import java.io.Serializable;

/**
 * Represents a subsubfield in a UNIMARC record.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class Subsubfield implements Serializable {

  public Subsubfield() {
    name = ' ';
    content = "";
  }

  public Subsubfield(char name) {
    this.name = name;
    content = "";
  }

  public char getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  public void setName(char name) {
    this.name = name;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("<");
    retVal.append(name);
    retVal.append(">");
    retVal.append(content);
    return retVal.toString();
  }

  private char name;
  private String content;
}