package com.gint.app.bisis.editor.edit.validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Enumeration;
import com.objectspace.jgl.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.UFieldSet.*;

public class NSFFValidator implements Validator {

  static {
    ValidatorManager.registerValidator(new NSFFValidator());
  }

  public String preAdjust(String id, Field f, String oldValue, Vector fields) {
    // ako nije inventarni broj, ne diraj nista
    if (!id.equals("996f") && !id.equals("997f"))
      return oldValue;

    // da li je signatura uneta?
    String location = sigLocation(f);
    if (location != null) {
      // da li je inv broj vec unet?
      if (oldValue.equals("")) { // nije
        int newNumber = Environment.getWriteTs().getNewNumber("ff"+getSeminarCode(location));
        return getSeminarCode(location)+addZeros(newNumber, 7);
      } else // jeste
        return getSeminarCode(location)+oldValue.substring(2);
    } else {
      return oldValue;
    }
  }

  public String postAdjust(String id, Field f, String oldValue, Vector fields) {
    // ako nije inventarni broj, ne diraj nista
    if (!id.equals("996f") && !id.equals("997f"))
      return oldValue;

    // proveri da li je signatura uneta
    String location = sigLocation(f);
    if (location == null) { // nije
      return oldValue; // trenutno me boli uvo
    } else { // jeste
      Subfield sigL = getSigL(f);
      if (id.equals("996f"))
        sigL.setContent(getSeminarLabel996(Integer.parseInt(oldValue)));
      else
        sigL.setContent(getSeminarLabel997(Integer.parseInt(oldValue)));
      // ako je 996dn prazno, popuni ga
      Subfield sigN = getSigN(f);
      if (sigN == null) {
        //dodaj n i popuni ga
        Subfield newsf = new Subfield("n", stripZeros(oldValue.substring(2)));
        f.getSubfields().addElement(newsf);
      } else if (sigN.equals(""))
        sigN.setContent(stripZeros(oldValue.substring(2)));
    }
    return oldValue;
  }

  public boolean isValid(Field f, Subfield sf, String content) throws Exception {
    return isValid(f, sf, null, content, false);
  }

  public boolean isValid(Field f, Subfield sf, Subfield ssf, String content, boolean hasSubsubfields) throws Exception {
    if (content.equals(""))
      return true;

    USubfield usf = Environment.getFs().getSubfield(f.getName(), sf.getName());
    USubSubfield ussf = null;
    int controlType;
    if (hasSubsubfields) {
      ussf = Environment.getFs().getSubSubfield(f.getName(),sf.getName(),ssf.getName());
      controlType = ussf.getControlID();
    }
    else
      controlType = usf.getControlID();

    int length = usf.getLength();
    switch (controlType) {
      case 0:
        return validLength(content, length);
      case 1:
        return memberExtSif(content, usf);
      case 2:
        if (hasSubsubfields)
          return memberSubSubSif(content, ussf);
        else
          return memberIntSif(content, usf);
      case 3:
        return validYear(content);
      case 4:
        return validISBN(content);
      case 5:
        return validISSN(content);
      case 6:
        return validTagNum(content);
      case 7:
        return validSF6(content);
      case 8:
        return validIDNum(content);
      case 9:
        return validDate(content);
      case 10:
        return validInvNum(content);
      case 11:
        return valid122a(content);
      case 12:
        return true;
      case 13:
        return validMasterRecordID(content);
      default:
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_UNKNOWNCONTROLTYPE"));
    }
  }

  /** Proverava duzinu datog podatka o Unimarc-u. */
  public boolean validLength(String content, int length) throws Exception {
    if (length != 0) {
      if (content.length() > length)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_MAXLENEXCEEDED"));
      else
        return true;
    }
    return true;
  }

  /** Proverava da li je dati podatak element eksternog sifarnika Unimarc-a. */
  public boolean memberExtSif(String content, USubfield sf) throws Exception {
    boolean nasaoExt = false;
    String tesID = sf.getTesID();
    HashMap externalCodes = sf.getExternalCodes();
    if (externalCodes != null) {
      HashSet hs = (HashSet)externalCodes.get(tesID);
      if (hs != null) {
        Enumeration en = hs.elements();
        while (en.hasMoreElements() && !nasaoExt) {
          UExternalCodes ec = (UExternalCodes)en.nextElement();
          if (ec.getEcID().trim().equals(content)) {
            nasaoExt = true;
          }
        }
        if (nasaoExt)
          return true;
        else
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_NOTPRESENTINEXTCODE"));
      } else
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_NONEXISTINGEXTERNALCODE"));
    } else
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_NONEXISTINGEXTERNALCODE"));
  }

  /** Proverava da li je dati podatak element internog sifarnika Unimarc-a. */
  public boolean memberIntSif(String content, USubfield sf) throws Exception {
    boolean nasaoInt = false;
    if (sf.getInternalCodes() != null) {
      Enumeration e = sf.getInternalCodes().elements();
      while (e.hasMoreElements() && !nasaoInt) {
        UInternalCodes ic = (UInternalCodes)e.nextElement();
        if (ic.getIcID().trim().equals(content))
          nasaoInt = true;
      }
      if (nasaoInt)
        return true;
      else
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_NOTPRESENTININTCODE"));
    } else
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_NONEXISTINGINTERNALCODE"));
  }

  /** Proverava sintaksu podatka tipa godina GGGG. */
  public boolean validYear(String content) throws Exception {
    if (content.length() != 4)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_YEARMUSTHAVE4DIGITS"));
    else {
      for (int i=0; i < 4; i++) {
        char c = content.charAt(i);
        if ((!Character.isDigit(c)) && (c != '?'))
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGYEARFORMAT"));
      }
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa ISBN broj. */
  public boolean validISBN(String content) throws Exception {
    if (content.length() != 13 && content.length() != 17)
       throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_ISBNLENGTHIS13"));
    else {
      int i = 0;
      int numPov = 0;
      boolean isValid = true;
      while (i < content.length() && isValid) {
        if (!(Character.isDigit(content.charAt(i))
              || content.charAt(i) == '-'
              || content.charAt(i) == 'X'
              || content.charAt(i) == 'x'))
          isValid = false;
        if (content.charAt(i) == '-')
          numPov++;
        i++;
      }
      if ((numPov != 3 && content.length() == 13) || 
          (numPov != 4 && content.length() == 17) || 
          !isValid)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGISBNNUMBER"));
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa ISSN broj. */
  public boolean validISSN(String content) throws Exception {
    if (content.length() != 9)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_ISSNLENGTHIS9"));
    else {
      int i = 0;
      boolean isValid = true;
      while (i < content.length() && isValid) {
        if (i == 4 && content.charAt(i) != '-')
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_5THCHARERROR"));
        else {
          if (!(Character.isDigit(content.charAt(i))
               || content.charAt(i) == 'X'
               || content.charAt(i) == 'x'
               || (i == 4 && content.charAt(i) == '-')))
            isValid = false;
        }
        i++;
      }
      if (!isValid)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGISSNNUMBER"));
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa Broj taga. */
  public boolean validTagNum(String content) throws Exception {
    boolean ind1OK = false;
    boolean ind2OK = false;
    if (content.length() < 3 || content.length() > 5)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_SUBFIELD1WRONGLENGTH"));
    else {
      String polje = content.substring(0,3);
      String ind1 = " ";
      String ind2 = " ";
      if (content.length() > 3)
        ind1 = content.substring(3,4);
      if (content.length() > 4)
        ind2 = content.substring(4,5);
      int i = 0;
      while (i < polje.length()) {
        if (!(Character.isDigit(content.charAt(i)) || content.charAt(i) == '?'))
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_FIELDNAMEMUSTHAVEDIGITSONLY"));
        i++;
      }
      if (Environment.getFs().getField(polje) == null)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_FIELDDOESNTEXIST"));
      if (!Environment.getFs().getField(polje).getSecondarity())
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_SUBFIELDNOTSEC"));
      if (!ind1.equals(" ")) {
        HashMap hm1 = Environment.getFs().getField(polje).getFirst();
        if (hm1 != null) {
          Enumeration enum1;
          enum1 = hm1.elements();
          while (enum1.hasMoreElements() && !ind1OK) {
            UInd indic1 = (UInd)enum1.nextElement();
            if (ind1.equals(indic1.getIndID()))
              ind1OK = true;
          }
          if (!ind1OK)
            throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_UNKNOWNFIRSTIND"));
        } else
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_MISSINGFIRSTIND"));
      }
      if (!ind2.equals(" ")) {
        HashMap hm2 = Environment.getFs().getField(polje).getSecond();
        if (hm2 != null) {
          Enumeration enum2;
          enum2 = hm2.elements();
          while (enum2.hasMoreElements() && !ind2OK) {
            UInd indic2 = (UInd)enum2.nextElement();
            if (ind2.equals(indic2.getIndID()))
              ind2OK = true;
          }
          if (!ind2OK)
            throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_UNKNOWNSECIND"));
        } else
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_MISSINGSECIND"));
      }
    }
    return true;
  }

  /** Proverava sintaksu podatka, koji je sadrzaj potpolja '6'. */
  public boolean validSF6(String content) throws Exception {
    if (content.length() > 4)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_SF6LENGTHEXCEEDS4"));
    else {
      int i = 0;
      boolean DigitSpace = true;
      while (i < content.length() && DigitSpace) {
        if (!(Character.isDigit(content.charAt(i)) || content.charAt(i) == ' '))
          DigitSpace = false;
        i++;
      }
      if (!DigitSpace)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGSF6"));
    }
    return true;
  }

  /** Metoda proverava sintaksu podatka tipa Identifikacioni broj. */
  public boolean validIDNum(String content) throws Exception {
    if (content.length() != 10)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGIDLEN"));
    return true;
  }

  /** Proverava sintaksu podatka tipa Datum GGGGMMDD. */
  public boolean validDate(String content) throws Exception {
    if (content.length() != 8)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGDATELENGTH"));
    else {
      int i = 0;
      while (i < content.length()) {
        if (!(Character.isDigit(content.charAt(i))))
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGDATEFORMAT"));
        i++;
      }
      int godina = Integer.parseInt(content.substring(0,4));
      int mesec = Integer.parseInt(content.substring(4,6));
      int dan = Integer.parseInt(content.substring(6,8));
      if (mesec < 1 || mesec > 12)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGMONTHVALUE"));
      if (dan < 1 || dan > 31)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGDAYVALUE"));
      if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
        if (dan > 30)
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGDAYVALUE30"));
      } else {
        if (((godina%4 == 0 && !(godina%100 == 0)) || godina%400 == 0) && mesec == 2) {
          if (dan > 29)
            throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGDAYVALUE29"));
        } else
          if (mesec == 2 && dan > 28)
            throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGDAYVALUE28"));
      }
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa Inventarni broj. */
  public boolean validInvNum(String content) throws Exception {
    if (content.length() != 9)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_INVNUMLEN9"));
    for (int i = 0; i <= 8; i++)
       if (!(Character.isDigit(content.charAt(i))))
         throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_INVDIG"));
    if (Environment.getWriteTs().checkInvNumber(content, Environment.getDocID()))
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_INVEXISTS"));
    return true;
  }

  /** Proverava sintaksu podatka potpolja 122a. */
  public boolean valid122a(String content) throws Exception {
    if (content.length() != 5)
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_122ALEN"));
    else {
      if (content.charAt(0) != 'c' && content.charAt(0) != 'd')
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_122AFIRST"));
      for (int i = 1; i < 5; i++) {
        if (!(Character.isDigit(content.charAt(i))))
          throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_122A2TO5"));
      }
    }
    return true;
  }

  /** Proverava da li je dati podatak element internog sifarnika potpotpolja Unimarc-a. */
  public boolean memberSubSubSif(String content, USubSubfield ssf) throws Exception {
    boolean nasaoInt = false;
    if (ssf.getSubfieldCodes() != null) {
      Enumeration e = ssf.getSubfieldCodes().elements();
      while (e.hasMoreElements() && !nasaoInt) {

        USubfieldCodes sc = (USubfieldCodes)e.nextElement();
        if (sc.getSfcID().trim().equals(content))
          nasaoInt = true;
      }
      if (nasaoInt)
        return true;
      else
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_MEMBERSUBSUBSIF"));
    } else
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_MEMBERSUBSUBSIFMISS"));
  }

  /** Vraca numericku oznaku seminara na osnovu oznake lokacije u signaturi
    */
  private String getSeminarCode(String seminar) {
    if (seminar.equals("C") || seminar.equals("\u010c"))
      return "01";
    else if (seminar.equals("L") || seminar.equals("L\u010d"))
      return "02";
    else if (seminar.equals("K") || seminar.equals("K\u010d"))
      return "03";
    else if (seminar.equals("E") || seminar.equals("E\u010d"))
      return "04";
    else if (seminar.equals("N") || seminar.equals("N\u010d"))
      return "05";
    else if (seminar.equals("S") || seminar.equals("S\u010d"))
      return "06";
    else if (seminar.equals("F") || seminar.equals("F\u010d"))
      return "07";
    else if (seminar.equals("H") || seminar.equals("H\u010d"))
      return "08";
    else if (seminar.equals("Sl") || seminar.equals("Sl\u010d"))
      return "09";
    else if (seminar.equals("R") || seminar.equals("R\u010d"))
      return "10";
    else if (seminar.equals("Ru") || seminar.equals("Ru\u010d"))
      return "11";
    else if (seminar.equals("I") || seminar.equals("I\u010d"))
      return "12";
    else if (seminar.equals("P") || seminar.equals("P\u010d"))
      return "13";
    else if (seminar.equals("Ps") || seminar.equals("Ps\u010d"))
      return "14";
    else if (seminar.equals("Dn") || seminar.equals("Dn\u010d"))
      return "15";
    else
      return "01";
  }

  /** Vraca oznaku lokacije na osnovu koda seminara, za polje 996
      (monografije)
    */
  private String getSeminarLabel996(int seminarCode) {
    switch (seminarCode) {
      case 1: return "C";
      case 2: return "L";
      case 3: return "K";
      case 4: return "E";
      case 5: return "N";
      case 6: return "S";
      case 7: return "F";
      case 8: return "H";
      case 9: return "Sl";
      case 10: return "R";
      case 11: return "Ru";
      case 12: return "I";
      case 13: return "P";
      case 14: return "Ps";
      case 15: return "Dn";
      default: return "";
    }
  }

  /** Vraca oznaku lokacije na osnovu koda seminara, za polje 997
      (casopisi)
    */
  private String getSeminarLabel997(int seminarCode) {
    switch (seminarCode) {
      case 1: return "\u010c";
      case 2: return "L\u010d";
      case 3: return "K\u010d";
      case 4: return "E\u010d";
      case 5: return "N\u010d";
      case 6: return "S\u010d";
      case 7: return "F\u010d";
      case 8: return "H\u010d";
      case 9: return "Sl\u010d";
      case 10: return "R\u010d";
      case 11: return "Ru\u010d";
      case 12: return "I\u010d";
      case 13: return "P\u010d";
      case 14: return "Ps\u010d";
      case 15: return "Dn\u010d";
      default: return "";
    }
  }

  /** Vraca vrednost potpolja d/potpotpolja l za dato polje. Ako ne postoji,
      vraca null.
    */
  private String sigLocation(Field f) {
    String retVal = null;
    Vector vsf = f.getSubfields();
    for (int i = 0; i < vsf.size(); i++) {
      Subfield sf = (Subfield)vsf.elementAt(i);
      if (sf.getName().equals("d")) {
        Vector vssf = sf.getSubsubfields();
        for (int j = 0; j < vssf.size(); j++) {
          Subfield ssf = (Subfield)vssf.elementAt(j);
          if (ssf.getName().equals("l") && !ssf.getContent().equals(""))
            retVal = ssf.getContent();
        }
      }
    }
    return retVal;
  }

  /** Vraca vrednost potpolja d/potpotpolja n za dato polje. Ako ne postoji,
      vraca null.
    */
  private String sigNumber(Field f) {
    String retVal = null;
    Vector vsf = f.getSubfields();
    for (int i = 0; i < vsf.size(); i++) {
      Subfield sf = (Subfield)vsf.elementAt(i);
      if (sf.getName().equals("d")) {
        Vector vssf = sf.getSubsubfields();
        for (int j = 0; j < vssf.size(); j++) {
          Subfield ssf = (Subfield)vssf.elementAt(j);
          if (ssf.getName().equals("n") && !ssf.getContent().equals(""))
            retVal = ssf.getContent();
        }
      }
    }
    return retVal;
  }

  /** Vraca potpotpolje l potpolja d datog polja ako postoji. Ako ne postoji
     vraca null.
    */
  private Subfield getSigL(Field f) {
    Subfield retVal = null;
    Vector vsf = f.getSubfields();
    for (int i = 0; i < vsf.size(); i++) {
      Subfield sf = (Subfield)vsf.elementAt(i);
      if (sf.getName().equals("d")) {
        Vector vssf = sf.getSubsubfields();
        for (int j = 0; j < vssf.size(); j++) {
          Subfield ssf = (Subfield)vssf.elementAt(j);
          if (ssf.getName().equals("l"))
            retVal = ssf;
        }
      }
    }
    return retVal;
  }

  /** Vraca potpotpolje n potpolja d datog polja ako postoji. Ako ne postoji
     vraca null.
    */
  private Subfield getSigN(Field f) {
    Subfield retVal = null;
    Vector vsf = f.getSubfields();
    for (int i = 0; i < vsf.size(); i++) {
      Subfield sf = (Subfield)vsf.elementAt(i);
      if (sf.getName().equals("d")) {
        Vector vssf = sf.getSubsubfields();
        for (int j = 0; j < vssf.size(); j++) {
          Subfield ssf = (Subfield)vssf.elementAt(j);
          if (ssf.getName().equals("n"))
            retVal = ssf;
        }
      }
    }
    return retVal;
  }

  /** Dodaje nule na pocetak broja do datog ukupnog broja
      cifara
    */
  private String addZeros(int number, int totalWidth) {
    String retVal = Integer.toString(number);
    while (retVal.length() != totalWidth)
      retVal = "0" + retVal;
    return retVal;
  }

  /** Skida nule sa leve strane broja */
  private String stripZeros(String number) {
    if (number.length() == 0)
      return "";
    int i = 0;
    while (i < number.length()) {
      if (number.charAt(i) != '0')
        break;
      i++;
    }
    return number.substring(i);
  }

  public boolean validMasterRecordID(String content) throws Exception {
    boolean found = false;
    try {
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT doc_id FROM pref_RN WHERE content = '"+content+"'");
      if (rset.next())
        found = true;
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
    }
    if (!found)
      throw new Exception("Nema nadre\u0111enog zapisa sa tim brojem (RN)");
    return true;
  }

/*
Dodatna sema baze potrebna za filozofski fakultet:
(Neophodno je podesiti pocetni broj svake sekvence!)

CREATE SEQUENCE ff01;
CREATE SEQUENCE ff02;
CREATE SEQUENCE ff03;
CREATE SEQUENCE ff04;
CREATE SEQUENCE ff05;
CREATE SEQUENCE ff06;
CREATE SEQUENCE ff07;
CREATE SEQUENCE ff08;
CREATE SEQUENCE ff09;
CREATE SEQUENCE ff10;
CREATE SEQUENCE ff11;
CREATE SEQUENCE ff12;
CREATE SEQUENCE ff13;
CREATE SEQUENCE ff14;
CREATE SEQUENCE ff15;
*/
}
