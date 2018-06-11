package com.gint.app.bisis.editor.recordstats;

import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;

public class SignatureFormat {

  public static String format(Subfield sig) {
    if (sig == null)
      return "";
    
    StringBuffer retVal = new StringBuffer();
    
    String d = getSubsubfieldContent(sig, 'd');
    String l = getSubsubfieldContent(sig, 'l');
    String i = getSubsubfieldContent(sig, 'i');
    String f = getSubsubfieldContent(sig, 'f');
    String n = getSubsubfieldContent(sig, 'n');
    String s = getSubsubfieldContent(sig, 's');
    String u = getSubsubfieldContent(sig, 'u');
    
    if (d != null)
      retVal.append(d + " ");
    if (l != null)
      retVal.append(l + "-");
    if (i != null)
      retVal.append(i + "-");
    if (f != null)
      retVal.append(f + "-");
    if (n != null)
      retVal.append(n);
    if (s != null)
      retVal.append("/"+s);
    if (u != null) {
      if (retVal.length() > 0)
        retVal.append(" ");
      retVal.append(u);
    }
    
    return retVal.toString();
  }
  
  private static String getSubsubfieldContent(Subfield sf, char name) {
    Subsubfield ssf = sf.getSubsubfield(name);
    if (ssf == null)
      return null;
    else
      return ssf.getContent();
  }
}
