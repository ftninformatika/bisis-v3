package com.gint.app.bisis.editor.edit;

import java.util.Vector;
import java.util.Enumeration;
import java.awt.*;
import javax.swing.*;

import com.gint.app.bisis.editor.*;

public class Field extends JComponent {

  public Field(String name, String ind1, String ind2, Vector subfields) {
    this.name = name;
    this.ind1 = ind1;
    this.ind2 = ind2;
    this.subfields = subfields;
    activeSubfieldIndex = 0;
    fieldActive = false;
    secFieldInfo = new Vector();
    fontMetrics = getFontMetrics(Font.decode("dialog"));
  }

  public Field(String name, String ind1, String ind2) {
    this (name, ind1, ind2, new Vector());
  }

  public Field(Field f) {
    this.name = new String(f.name);
    this.ind1 = new String(f.ind1);
    this.ind2 = new String(f.ind2);
    subfields = new Vector();
    activeSubfieldIndex = 0;
    fieldActive = false;
    fontMetrics = getFontMetrics(Font.decode("dialog"));
    for (int i = 0; i < f.subfields.size(); i++) {
      subfields.addElement(new Subfield(f.getSubfieldByIndex(i)));
    }
    init();
  }
  
  public Field(Field f, boolean cleanInvNum) {
    this.name = new String(f.name);
    this.ind1 = new String(f.ind1);
    this.ind2 = new String(f.ind2);
    subfields = new Vector();
    activeSubfieldIndex = 0;
    fieldActive = false;
    fontMetrics = getFontMetrics(Font.decode("dialog"));
    for (int i = 0; i < f.subfields.size(); i++) {
      if ((f.getName().equals("996") || f.getName().equals("997")) && f.getSubfieldByIndex(i).getName().equals("f") && cleanInvNum) {
        subfields.addElement(new Subfield(f.getSubfieldByIndex(i), ""));
      } else {
        subfields.addElement(new Subfield(f.getSubfieldByIndex(i)));
      }
    }
    init();
  }

  public void paint(Graphics g) {
    if (fieldActive)
      drawActiveField(g);
    else
      drawInactiveField(g);
  }

  public void drawActiveField(Graphics g) {
    if (containsSecFields()) {
      drawActiveSecField(g);
      return;
    }
    // prvo crtamo naziv polja invertovano
    FontMetrics fm = g.getFontMetrics();
    int width = fm.stringWidth(name);
    int height = fm.getHeight();
    g.setPaintMode();
    g.setColor(activeBgColor);
    g.fillRect(HORIZONTAL_OFFSET, VERTICAL_OFFSET, width, height);
    g.setColor(activeFgColor);
    g.drawString(name, HORIZONTAL_OFFSET, VERTICAL_OFFSET+CHAR_HEIGHT);

    // crtamo sva neaktivna potpolja pre aktivnog potpolja
    // ako ih ima, dakako
    String sfname = "";
    if (activeSubfieldIndex > 0) {
      for (int i = 0; i < activeSubfieldIndex; i++) {
         sfname += ((Subfield)subfields.elementAt(i)).getName();
      }
      width = fm.stringWidth(sfname);
      height = fm.getHeight();
      g.setColor(inactiveBgColor);
      g.fillRect(HORIZONTAL_OFFSET + fm.stringWidth(name), VERTICAL_OFFSET, width, height);
      g.setColor(inactiveFgColor);
      g.drawString(sfname, HORIZONTAL_OFFSET + fm.stringWidth(name), VERTICAL_OFFSET+CHAR_HEIGHT);
    }

    // crtamo aktivno potpolje
    // dakako, ono uvek postoji
    String asfname = ((Subfield)subfields.elementAt(activeSubfieldIndex)).getName();
    width = fm.stringWidth(asfname);
    height = fm.getHeight();
    g.setColor(activeBgColor);
    g.fillRect(HORIZONTAL_OFFSET + fm.stringWidth(name + sfname), VERTICAL_OFFSET, width, height);
    g.setColor(activeFgColor);
    g.drawString(asfname, HORIZONTAL_OFFSET + fm.stringWidth(name + sfname), VERTICAL_OFFSET+CHAR_HEIGHT);

    // crtamo neaktivna potpolja posle aktivnog potpolja
    // ako ih ima, dakako
    int n = subfields.size();
    String sfname2 = "";
    if (activeSubfieldIndex < n - 1) {
      for (int i = activeSubfieldIndex+1; i < n; i++) {
         sfname2 += ((Subfield)subfields.elementAt(i)).getName();
      }
      width = fm.stringWidth(sfname2);
      height = fm.getHeight();
      g.setColor(inactiveBgColor);
      g.fillRect(HORIZONTAL_OFFSET + fm.stringWidth(name+sfname+asfname), VERTICAL_OFFSET, width, height);
      g.setColor(inactiveFgColor);
      g.drawString(sfname2, HORIZONTAL_OFFSET + fm.stringWidth(name+sfname+asfname), VERTICAL_OFFSET+CHAR_HEIGHT);
    }
  }

  public void drawActiveSecField(Graphics g) {
    // prvo crtamo naziv polja invertovano
    FontMetrics fm = g.getFontMetrics();
    int width = fm.stringWidth(name);
    int height = fm.getHeight();
    g.setPaintMode();
    g.setColor(activeBgColor);
    g.fillRect(HORIZONTAL_OFFSET, VERTICAL_OFFSET, width, height);
    g.setColor(activeFgColor);
    g.drawString(name, HORIZONTAL_OFFSET, VERTICAL_OFFSET+CHAR_HEIGHT);

    // crtamo sva neaktivna potpolja pre aktivnog potpolja
    // ako ih ima, dakako
    String sfname = "";
    int totalWidth = width;
    if (activeSubfieldIndex > 0) {
      for (int i = 0; i < activeSubfieldIndex; i++) {
        SecFieldInfo sfi = (SecFieldInfo)secFieldInfo.elementAt(i);
        String slovo = sfi.subfieldID;
        width = fm.stringWidth(slovo);
        height = fm.getHeight();
        g.setColor(inactiveBgColor);
        g.fillRect(HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET, width, height+SUBSCRIPT_OFFSET);
        g.setColor(inactiveFgColor);
        if (sfi.index == -1)  // naisli smo na potpolje sekundarnog polja
          g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT);
        else
          g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT+SUBSCRIPT_OFFSET);
        totalWidth += width;
      }
    }

    // crtamo aktivno potpolje
    // dakako, ono uvek postoji
    SecFieldInfo sfi = (SecFieldInfo)secFieldInfo.elementAt(activeSubfieldIndex);
    String slovo = sfi.subfieldID;
    width = fm.stringWidth(slovo);
    height = fm.getHeight();
    g.setColor(activeBgColor);
    g.fillRect(HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET, width, height);
    g.setColor(activeFgColor);
    if (sfi.index == -1)
      g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT);
    else
      g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT+SUBSCRIPT_OFFSET);
    totalWidth += width;

    // crtamo neaktivna potpolja posle aktivnog potpolja
    // ako ih ima, dakako
    int n = secFieldInfo.size();
    String sfname2 = "";
    if (activeSubfieldIndex < n - 1) {
      for (int i = activeSubfieldIndex+1; i < n; i++) {
        sfi = (SecFieldInfo)secFieldInfo.elementAt(i);
        slovo = sfi.subfieldID;
        width = fm.stringWidth(slovo);
        height = fm.getHeight();
        g.setColor(inactiveBgColor);
        g.fillRect(HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET, width, height+SUBSCRIPT_OFFSET);
        g.setColor(inactiveFgColor);
        if (sfi.index == -1)  // naisli smo na potpolje sekundarnog polja
          g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT);
        else
          g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT+SUBSCRIPT_OFFSET);
        totalWidth += width;
      }
    }
  }

  public void drawInactiveField(Graphics g) {
    if (containsSecFields()) {
      drawInactiveSecField(g);
      return;
    }
    String s = name + getAllSubfieldsAsString();
    g.setColor(Color.black);
    g.drawString(s, HORIZONTAL_OFFSET, VERTICAL_OFFSET+CHAR_HEIGHT);
  }

  /** Vraca string sastavljen iz naziva svih potpolja u polju. */
  public String getAllSubfieldsAsString() {
    StringBuffer retVal = new StringBuffer();
    int n = subfields.size();
    for (int i = 0; i < n; i++) {
      Subfield sf = (Subfield)subfields.elementAt(i);
      retVal.append(sf.getName());
      Field secF = sf.getSecField();
      if (secF != null) {
        int nn = secF.getSubfields().size();
        for (int j = 0; j < nn; j++) {
          retVal.append(((Subfield)secF.getSubfields().elementAt(j)).getName());
        }
      }
    }
    return retVal.toString();
  }

  public void drawInactiveSecField(Graphics g) {
    // prvo crtamo naziv polja
    FontMetrics fm = g.getFontMetrics();
    int width = fm.stringWidth(name);
    int height = fm.getHeight();
    g.setPaintMode();
    g.setColor(inactiveBgColor);
    g.fillRect(HORIZONTAL_OFFSET, VERTICAL_OFFSET, width, height);
    g.setColor(inactiveFgColor);
    g.drawString(name, HORIZONTAL_OFFSET, VERTICAL_OFFSET+CHAR_HEIGHT);

    String sfname = "";
    int totalWidth = width;
    int n = secFieldInfo.size();
    for (int i = 0; i < n; i++) {
      SecFieldInfo sfi = (SecFieldInfo)secFieldInfo.elementAt(i);
      String slovo = sfi.subfieldID;
      width = fm.stringWidth(slovo);
      height = fm.getHeight();
      g.setColor(inactiveBgColor);
      g.fillRect(HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET, width, height+SUBSCRIPT_OFFSET);
      g.setColor(inactiveFgColor);
      if (sfi.index == -1)  // naisli smo na potpolje sekundarnog polja
        g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT);
      else
        g.drawString(slovo, HORIZONTAL_OFFSET + totalWidth, VERTICAL_OFFSET+CHAR_HEIGHT+SUBSCRIPT_OFFSET);
      totalWidth += width;
    }
  }

  /** Vraca sirinu komponente u pikselima */
  public int getFieldWidth() {
    return fieldWidth;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setInd1(String ind1) {
    ind1 = ind1.trim();
    if (ind1.equals(""))
      ind1 = " ";
    this.ind1 = ind1;
  }

  public void setInd2(String ind2) {
    ind2 = ind2.trim();
    if (ind2.equals(""))
      ind2 = " ";
    this.ind2 = ind2;
  }

  public String getName() {
    return name;
  }

  public String getInd1() {
    ind1 = ind1.trim();
    if (ind1.equals(""))
      return " ";
    else
      return ind1;
  }

  public String getInd2() {
    ind2 = ind2.trim();
    if (ind2.equals(""))
      return " ";
    else
      return ind2;
  }

  public Vector getSubfields() {
    return subfields;
  }

  /** Vraca potpolje na osnovu rednog broja. */
  public Subfield getSubfieldByIndex(int index) {
    Subfield retVal = null;
    if(index >= 0 && index < subfields.size()) {
      retVal = (Subfield)subfields.elementAt(index);
    }
    return retVal;
  }
  
  /** Vraca vektor potpolja sa datim imenom; prazan vektor ako takvih nema. */
  public Vector getSubfieldsByName(String name) {
    Vector retVal = new Vector();
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = (Subfield)subfields.elementAt(i);
      if (sf.getName().equals(name))
        retVal.addElement(sf);
    }
    return retVal;
  }


  /** Vraca enumerator potpolja. */
  public Enumeration getSubfieldsEnum() {
    return subfields.elements();
  }

  public Vector getSecFieldInfo() {
    return secFieldInfo;
  }

  public void setSubfields(Vector subfields) {
    this.subfields = subfields;
  }

  public boolean getFieldActive() {
    return fieldActive;
  }

  /** Postavlja polje da je aktivno (da je na njemu fokus). */
  public void setFieldActive(boolean fieldActive) {
    this.fieldActive = fieldActive;
  }

  public int getActiveSubfieldIndex() {
    return activeSubfieldIndex;
  }

  /** Postavlja aktivno potpolje.
      @param activeSubfieldIndex Redni broj potpolja koje treba da postane aktivno
     */
  public void setActiveSubfieldIndex(int activeSubfieldIndex) {
    this.activeSubfieldIndex = activeSubfieldIndex;
  }

  /** Vraca redni broj potpolja koje je vece od zadatog.
    U protivnom vraca -1.
  */
  public int findPos(String subfieldID) {
    int retVal = -1;
    for (int i = 0; i < subfields.size(); i++) {
      // tekuce potpolje je vece od zadatog potpolja
      if (((Subfield)subfields.elementAt(i)).getName().compareTo(subfieldID) > 0) {
        retVal = i;
        break;
      } // if
    } // for
    return retVal;
  }

  /** Dodaje potpolje u vektor potpolja. */
  public void addSubfield(Subfield sf) {
    subfields.addElement(sf);
  }


  /** Umece potpolje na zadati indeks. */
  public void insertSubfieldAt(Subfield s, int index) {
    subfields.insertElementAt(s, index);
  }

  /** Vraca redni broj potpolja u vektoru na osnovu ID-a.
   Ako ga nema, vraca -1.
  */
  public int indexOf(String subfieldID) {
    int retVal = -1;
    for (int i = 0; i < subfields.size(); i++) {
      if (((Subfield)subfields.elementAt(i)).getName().equals(subfieldID)) {
        retVal = i;
        break;
      }
    }
    return retVal;
  }

  /** Inicira polje po pitanju potpolja sa sekundarnim poljima i potpotpoljima */
  public void init() {
    String tmp = "";
    Enumeration subfieldIter = getSubfieldsEnum();
    int subIndex  = 0;
    secFieldInfo = new Vector();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      Vector secFields = sf.getSecFieldInVect();
      Vector subSubfields = sf.getSubsubfields();
      Subfield secsf = null;
      Vector v = null;
      if (secFields.size() > 0) { // sec field
        tmp += sf.getName();
       	secFieldInfo.addElement(new SecFieldInfo(getName(), sf.getName(), subIndex, -1));
        for (int i = 0; i < secFields.size(); i ++) {
          Field secf = (Field)secFields.elementAt(i);
          v = secf.getSubfields();
          int secFIndex = 0;
          for (int j = 0; j < v.size(); j ++) {
            secsf = (Subfield)v.elementAt(j);
            tmp += secsf.getName();
	          secFieldInfo.addElement(new SecFieldInfo(secf.getName(), secsf.getName(), subIndex, secFIndex));
            secFIndex++;
          }
        }
        subIndex++;
      } else if (Environment.getFs().containsSubSubfields(name, sf.getName())) {
//      } else if (subSubfields.size() > 0) {
        // sada na osnovu UNIMARC -a dodamo sva potpotpolja,
        // a onda pronadjemo samo ona koja ima ju sadrzaj i popunimo ih.
        Vector subSubfields2 = new Vector();
        Subfield sf2;
        Vector usf = Environment.getFs().getSubSubfieldList(getName(), sf.getName());
        // prodjem kroz listu svih potpotpolja po UNIMARC-u
        for( int brojac = 0; brojac < usf.size(); brojac++) {
          String tmp2 = (String)usf.elementAt(brojac);
          // napravimo jedno potpotpolje
          sf2 = new Subfield (tmp2, "");
          // ako postoji sadrzaj iz rezanca
          Subfield sf3 = sf.getSubSubfieldByName(sf2.getName());
          if (sf3 != null) {
            // onda taj sadrzaj prepisemo u novo potpotpolje
            sf2.setContent(sf3.getContent());
          }
          // posle ovoga, dodam ga u vektor potpotpolja
          subSubfields2.addElement(sf2);
        }
        // sada prespojimo listu potpotpolja preko stare
        sf.setSubsubfields(subSubfields2);

        secFieldInfo.addElement(new SecFieldInfo(getName(), sf.getName(), subIndex, -1));
        subIndex++;
      } else  {  // OBICNO POTPOLJE
       	secFieldInfo.addElement(new SecFieldInfo(getName(), sf.getName(), subIndex, -1));
        subIndex++;
      }
    }

    // izracunavanje sirine u pikselima
    if (containsSecFields()) {
      int width = fontMetrics.stringWidth(name);
      String sfname = "";
      int totalWidth = width;
      int n = secFieldInfo.size();
      for (int i = 0; i < n; i++) {
        SecFieldInfo sfi = (SecFieldInfo)secFieldInfo.elementAt(i);
        String slovo = sfi.subfieldID;
        width = fontMetrics.stringWidth(slovo);
        totalWidth += width;
      }
      fieldWidth = totalWidth;
    } else
      fieldWidth = fontMetrics.stringWidth(name + getAllSubfieldsAsString());
  }

  public boolean containsSubsubfields() {
    boolean retVal = false;
    int n = subfields.size();
    for (int i = 0; i < n; i++)
      retVal |= ((Subfield)subfields.elementAt(i)).containsSubsubfields();
    return retVal;
  }

  public boolean containsSecFields() {
    boolean retVal = false;
    int n = subfields.size();
    for (int i = 0; i < n; i++)
      retVal |= ((Subfield)subfields.elementAt(i)).containsSecField();
    return retVal;
  }

  public String toString() {
    return name + " " + getAllSubfieldsAsString();
  }

  private String name;
  private String ind1;
  private String ind2;
  private Vector subfields;
  private Vector secFieldInfo;

  private boolean fieldActive;
  private int activeSubfieldIndex;
  private Color activeBgColor = Color.blue;
  private Color activeFgColor = Color.white;
  private Color inactiveBgColor = Color.white;
  private Color inactiveFgColor = Color.black;
  private FontMetrics fontMetrics;
  private int fieldWidth;

  private static final int VERTICAL_OFFSET = 1;
  private static final int HORIZONTAL_OFFSET = 2;
  private static final int CHAR_HEIGHT = 12;
  private static final int SUBSCRIPT_OFFSET = 2;
}
