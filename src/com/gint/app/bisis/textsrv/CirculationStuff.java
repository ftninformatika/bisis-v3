package com.gint.app.bisis.textsrv;

import java.util.Vector;
import java.util.StringTokenizer;

import com.gint.util.string.Signature;
import com.gint.util.string.UnimarcConverter;

public class CirculationStuff {

  /** Generise vektor CircEntry objekata koji predstavlja redove koje
      treba uneti u CIRC_DOCS tabelu prilikom indeksiranja. Uzima u
      obzir tekuce stanje u CIRC_DOCS tabeli (status pojedinih
      inventarnih brojeva).
      @param docID Identifikator zapisa
      @param rez Rezanac
      @param old Vektor CircEntry objekata koji predstavlja tekuce stanje
      pre indeksiranja
    */
  public static Vector prepareForCirculation(int docID, String rez, Vector old) {
    return updateAvailability(parseRezance(docID, rez), old);
  }

  /** Parsira rezanac i generise vektor CircEntry objekata. Pravilo je da se
      rezanac cita redom i da jedna signatura vazi za sve inventarne brojeve
      sve dok se ne naidje na novu signaturu. Inventarni brojevi koji se
      pojavljuju pre prve signature se ignorisu.
      @param docID Identifikator zapisa
      @param rez Rezanac
      @return Vektor CircEntry objekata
    */
  public static Vector parseRezance(int docID, String rez) {
    Vector retVal = new Vector();
    String activeSig = null;
    StringTokenizer fieldTokenizer = new StringTokenizer(rez, "\36");
    boolean hasDF = false;
    String aSubfield = "";
    String qSubfield = "";
    String fSubfield = "";
    while (fieldTokenizer.hasMoreTokens()) {
      String temp = fieldTokenizer.nextToken();
      String field = temp.substring(0,3);
      if (field.equals("000")) {
        String subfields = temp.substring(5);
        StringTokenizer subfieldTokenizer = new StringTokenizer(subfields, "\37");
        while (subfieldTokenizer.hasMoreTokens()) {
          String temp2 = subfieldTokenizer.nextToken();
          String subfield = temp2.substring(0, 1);
          if (subfield.equals("a"))
            aSubfield = temp2.substring(1,4);
        }
      }
      if (field.equals("996") || field.equals("997")) {
        String subfields = temp.substring(5);
        StringTokenizer subfieldTokenizer = new StringTokenizer(subfields, "\37");
        while (subfieldTokenizer.hasMoreTokens()) {
          String temp2 = subfieldTokenizer.nextToken();
          String subfield = temp2.substring(0, 1);
          if (subfield.equals("d")) {
            String sig = Signature.userDisplay(
                conv.Unimarc2Unicode(temp2.substring(1)));
            activeSig = sig;
            //activeSig = temp2.substring(1);
          } else if (subfield.equals("f")) {
            fSubfield = temp2.substring(1);
            qSubfield = "";
          } else if (subfield.equals("q")) {
            qSubfield = temp2.substring(1);
          }
        }
        if (!fSubfield.equals("")) {
          // big shit here
          // ako je inventarni broj vec prisutan preskoci ga.
          // inv.br. moze biti prisutan ako u zapisu ima vise
          // brojeva oblika xyz.001, xyz.002, xyz.003
          CircEntry candidate = new CircEntry(docID, activeSig, fSubfield, aSubfield, qSubfield, 0); 
          if (!alreadyThere(retVal, candidate))
            retVal.addElement(candidate);
          fSubfield = "";
        }
      }
    }
    return retVal;
  }

  /** Azurira available status za svaki CircEntry objekat novog rezanca: ako
      vec postoji u bazi takav inventarni broj, preuzima se njegovo stanje
      available.
      @param new_ Vektor CircEntry objekata za novi zapis
      @param old Vektor CircEntry objekata koji su pripadali zapisu pre snimanja
      @return Azuriran vektor CircEntry objekata (vektor new_)
    */
  public static Vector updateAvailability(Vector new_, Vector old) {
    for (int i = 0; i < new_.size(); i++) {
      CircEntry ce = (CircEntry)new_.elementAt(i);
      int pos = old.indexOf(ce);
      if (pos != -1)
        ce.setAvailable(((CircEntry)old.elementAt(pos)).getAvailable());
    }
    return new_;
  }
  
  private static boolean alreadyThere(Vector vec, CircEntry ce) {
    for (int i = 0; i < vec.size(); i++) {
      CircEntry ce1 = (CircEntry)vec.elementAt(i);
      if (ce.equals(ce1))
        return true;
    }
    return false;
  }

  private static UnimarcConverter conv = new UnimarcConverter();
}
