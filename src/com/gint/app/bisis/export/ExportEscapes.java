package com.gint.app.bisis.export;

public class ExportEscapes {
  public static String escapeText(String s) {
    return s.replace('\n', '\t');
  }

  public static String unescapeText(String s) {
    return s.replace('\t', '\n');
  }
}