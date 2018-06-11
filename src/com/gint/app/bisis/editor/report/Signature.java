package com.gint.app.bisis.editor.report;

import java.util.StringTokenizer;
import com.gint.util.string.StringUtils;


/** Generise prikaz bibliotekarske signature za korisnika. */
public class Signature {

  /** Generise prikaz bibliotekarske signature za korisnika.
   *  @param sg Signatura kako je uneta ubazi, sa potpotpoljima i UNIMARC
   *            kodnim rasporedom
   *  @return Interpretirana signatura
   */
  public  String user(String sg) {
    String d, f, s, n, a, i, l, u, w, x, y, z;
    d = ""; f = ""; s = ""; n = ""; a = ""; i = ""; l = ""; u = "";
    w = ""; x = ""; y = ""; z = "";
    StringTokenizer st = new StringTokenizer(sg, "\\");
    while (st.hasMoreTokens()) {
      String sgPiece = st.nextToken();
      if (sgPiece.length() < 2)
        continue;
      char subsubfieldID = sgPiece.charAt(0);
      switch (subsubfieldID) {
        case 'd': // Dublet
          d = sgPiece.substring(1);
          break;
        case 'f': // Format
          f = StringUtils.arabicToRoman(sgPiece.substring(1));
          break;
        case 's': // Numeracija
          s = sgPiece.substring(1);
          break;
        case 'n': // Tekuci broj
          n = sgPiece.substring(1);
          break;
        case 'a': // Oznaka varijante ili ABC oznaka
          a = sgPiece.substring(1);
          break;
        case 'i': // Interna oznaka
          i = sgPiece.substring(1);
          break;
        case 'l': // Oznaka podlokacije
          l = sgPiece.substring(1);
          break;
        case 'u': // Slobodan pristup UDK
          u = sgPiece.substring(1);
          break;
        case 'w': // Razresenje numeracije (4. nivo)
          w = sgPiece.substring(1);
          break;
        case 'x': // Razresenje numeracije (1. nivo)
          x = sgPiece.substring(1);
          break;
        case 'y': // Razresenje numeracije (2. nivo)
          y = sgPiece.substring(1);
          break;
        case 'z': // Razre�enje numeracije (3. nivo)
          z = sgPiece.substring(1);
          break;
      }
    }
    String retVal = "";
    if (!u.equals(""))
        retVal += u;
   /* if (!d.equals(""))
     retVal += d;
    if (!l.equals(""))
     retVal += (retVal.equals("") ? "" : " ") + l;
    if (!f.equals(""))
      retVal += (retVal.equals("") ? "" : " ") + f + "-";
    if (!n.equals(""))
      retVal += n;
    if (!s.equals(""))
      retVal += "/"+s;*/
    return retVal;
  }

 
}
