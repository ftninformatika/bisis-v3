package com.gint.app.bisis4.common.records;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Represents a configuration of prefix settings.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public interface PrefixConfig {
  public PrefixHandler getPrefixHandler();
  public PrefixMap getPrefixMap();
  public Set getAllPrefixes();
  public Set getBasePrefixes();
  public String getWordDelimiters();
  public String getSentenceDelimiters();
  public String getAllDelimiters();
  public List getPrefixNames();
  public List getPrefixNames(Locale locale);
}
