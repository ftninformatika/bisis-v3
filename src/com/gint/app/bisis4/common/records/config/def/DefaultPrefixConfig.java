package com.gint.app.bisis4.common.records.config.def;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import com.gint.app.bisis4.common.records.PrefixConfig;
import com.gint.app.bisis4.common.records.PrefixHandler;
import com.gint.app.bisis4.common.records.PrefixMap;
import com.gint.app.bisis4.common.records.PrefixValue;

/**
 * Implements a default prefix configuration.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class DefaultPrefixConfig implements PrefixConfig {

  public PrefixHandler getPrefixHandler() {
    return prefixHandler;
  }

  public PrefixMap getPrefixMap() {
    return prefixMap;
  }

  public Set getAllPrefixes() {
    return allPrefixes;
  }

  public Set getBasePrefixes() {
    return basePrefixes;
  }

  public String getWordDelimiters() {
    return ", ;:\"()[]{}-+/'";
  }

  public String getSentenceDelimiters() {
    return ".?!";
  }
  
  public String getAllDelimiters() {
    return ", ;:\"()[]{}-+/'.?!";
  }
  
  public List getPrefixNames() {
    return (List)prefixNameLists.get(null);
  }
  
  public List getPrefixNames(Locale locale) {
    List retVal = (List)prefixNameLists.get(locale);
    if (retVal == null) {
      retVal = initPrefixNameList(locale);
      prefixNameLists.put(locale, retVal);
    }
    return retVal;
  }
  
  public DefaultPrefixConfig() {
    prefixHandler = new DefaultPrefixHandler();
    prefixMap = new DefaultPrefixMap();
    allPrefixes = new HashSet();
    basePrefixes = new HashSet();
    allPrefixes.add("AB");
    allPrefixes.add("AM");
    allPrefixes.add("AN");
    allPrefixes.add("AP");
    allPrefixes.add("AT");
    allPrefixes.add("AU");
    allPrefixes.add("BI");
    allPrefixes.add("BN");
    allPrefixes.add("CB");
    allPrefixes.add("CC");
    allPrefixes.add("CD");
    allPrefixes.add("CH");
    allPrefixes.add("CL");
    allPrefixes.add("CN");
    allPrefixes.add("CO");
    allPrefixes.add("CP");
    allPrefixes.add("CR");
    allPrefixes.add("CS");
    allPrefixes.add("DA");
    allPrefixes.add("DC");
    allPrefixes.add("DE");
    allPrefixes.add("DR");
    allPrefixes.add("DS");
    allPrefixes.add("DT");
    allPrefixes.add("ES");
    allPrefixes.add("FC");
    allPrefixes.add("FD");
    allPrefixes.add("FI");
    allPrefixes.add("FN");
    allPrefixes.add("FQ");
    allPrefixes.add("FS");
    allPrefixes.add("GE");
    allPrefixes.add("GM");
    allPrefixes.add("GN");
    allPrefixes.add("GS");
    allPrefixes.add("IC");
    allPrefixes.add("ID");
    allPrefixes.add("II");
    allPrefixes.add("IN");
    allPrefixes.add("IR");
    allPrefixes.add("KT");
    allPrefixes.add("KW");
    allPrefixes.add("LA");
    allPrefixes.add("LC");
    allPrefixes.add("LI");
    allPrefixes.add("LO");
    allPrefixes.add("ND");
    allPrefixes.add("NM");
    allPrefixes.add("NT");
    allPrefixes.add("P2");
    allPrefixes.add("PA");
    allPrefixes.add("PM");
    allPrefixes.add("PN");
    allPrefixes.add("PP");
    allPrefixes.add("PR");
    allPrefixes.add("PU");
    allPrefixes.add("PY");
    allPrefixes.add("RE");
    allPrefixes.add("RJ");
    allPrefixes.add("RS");
    allPrefixes.add("RT");
    allPrefixes.add("SC");
    allPrefixes.add("SG");
    allPrefixes.add("SI");
    allPrefixes.add("SK");
    allPrefixes.add("SN");
    allPrefixes.add("SP");
    allPrefixes.add("SR");
    allPrefixes.add("SS");
    allPrefixes.add("ST");
    allPrefixes.add("SU");
    allPrefixes.add("TI");
    allPrefixes.add("TN");
    allPrefixes.add("TP");
    allPrefixes.add("TS");
    allPrefixes.add("TY");
    allPrefixes.add("UG");
    allPrefixes.add("US");
    allPrefixes.add("UT");
    allPrefixes.add("Y1");
    allPrefixes.add("Y2");
    basePrefixes.add("CB");
    basePrefixes.add("FN");
    basePrefixes.add("PN");
    basePrefixes.add("TS");
    basePrefixes.add("TI");
    basePrefixes.add("KT");
    basePrefixes.add("AB");
    basePrefixes.add("CN");
    basePrefixes.add("UT");
    basePrefixes.add("TN");
    basePrefixes.add("GN");
    basePrefixes.add("CH");
    basePrefixes.add("FS");
    basePrefixes.add("GE");
    
    prefixNameLists = new HashMap();
    prefixNameLists.put(null, initPrefixNameList(null));
  }
  
  private List initPrefixNameList(Locale locale) {
    List retVal = new ArrayList();
    ResourceBundle rb = null;
    if (locale == null)
      rb = PropertyResourceBundle.getBundle(
          "com.gint.app.bisis4.common.records.config.def.PrefixNames");
    else
      rb = PropertyResourceBundle.getBundle(
          "com.gint.app.bisis4.common.records.config.def.PrefixNames", locale);
    if (rb == null)
      return retVal;
    Enumeration keys = rb.getKeys();
    while (keys.hasMoreElements()) {
      String key = (String)keys.nextElement();
      String value = rb.getString(key);
      retVal.add(new PrefixValue(key.toUpperCase(), value));
    }
    Collections.sort(retVal);
    return retVal;
  }
  
  private PrefixHandler prefixHandler;
  private PrefixMap prefixMap;
  private Set allPrefixes;
  private Set basePrefixes;
  private HashMap prefixNameLists;
}
