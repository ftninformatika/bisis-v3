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

public class GBValidator implements Validator {

  static {
    ValidatorManager.registerValidator(new GBValidator());
  }

  public String preAdjust(String id, Field f, String oldValue, Vector fields) {
    return oldValue;
  }

  public String postAdjust(String id, Field f, String oldValue, Vector fields) {
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
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_UNKNOWNCONTROLTYPE"));
    }
  }

  /** Proverava duzinu datog podatka o Unimarc-u. */
  public boolean validLength(String content, int length) throws Exception {
    if (length != 0) {
      if (content.length() > length)
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_MAXLENEXCEEDED"));
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
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_NOTPRESENTINEXTCODE"));
      } else
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_NONEXISTINGEXTERNALCODE"));
    } else
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_NONEXISTINGEXTERNALCODE"));
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
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_NOTPRESENTININTCODE"));
    } else
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_NONEXISTINGINTERNALCODE"));
  }

  /** Proverava sintaksu podatka tipa godina GGGG. Dopusta i upitnike. */
  public boolean validYear(String content) throws Exception {
    if (content.length() != 4)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_YEARMUSTHAVE4DIGITS"));
    else {
      for (int i=0; i < 4; i++) {
        char c = content.charAt(i);
        if ((!Character.isDigit(c)) && (c != '?'))
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGYEARFORMAT"));
      }
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa ISBN broj. */
  public boolean validISBN(String content) throws Exception {
    int l = content.length();
    if (l != 10 && l != 13 && l != 17)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_ISBNLENGTHIS13"));
    else {
      int i = 0;
      int numPov = 0;
      boolean isValid = true;
      while (i < l && isValid) {
        if (!(Character.isDigit(content.charAt(i))
             || content.charAt(i) == '-'
             || content.charAt(i) == 'X'
             || content.charAt(i) == 'x'))
         isValid = false;
        if (content.charAt(i) == '-')
          numPov++;
        i++;
      }
      if ((numPov != 3 && numPov != 0 && l == 13) || 
          (numPov != 4 && l == 17) || 
          !isValid)
        throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_WRONGISBNNUMBER"));
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa ISSN broj. */
  public boolean validISSN(String content) throws Exception {
    if (content.length() != 9)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_ISSNLENGTHIS9"));
    else {
      int i = 0;
      boolean isValid = true;
      while (i < content.length() && isValid) {
        if (i == 4 && content.charAt(i) != '-')
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_5THCHARERROR"));
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
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGISSNNUMBER"));
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa Broj taga. */
  public boolean validTagNum(String content) throws Exception {
    boolean ind1OK = false;
    boolean ind2OK = false;
    if (content.length() < 3 || content.length() > 5)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_SUBFIELD1WRONGLENGTH"));
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
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_FIELDNAMEMUSTHAVEDIGITSONLY"));
        i++;
      }
      if (Environment.getFs().getField(polje) == null)
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_FIELDDOESNTEXIST"));
      if (!Environment.getFs().getField(polje).getSecondarity())
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_SUBFIELDNOTSEC"));
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
            throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_UNKNOWNFIRSTIND"));
        } else
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_MISSINGFIRSTIND"));
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
            throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_UNKNOWNSECIND"));
        } else
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_MISSINGSECIND"));
      }
    }
    return true;
  }

  /** Proverava sintaksu podatka, koji je sadrzaj potpolja '6'. */
  public boolean validSF6(String content) throws Exception {
    if (content.length() > 4)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_SF6LENGTHEXCEEDS4"));
    else {
      int i = 0;
      boolean DigitSpace = true;
      while (i < content.length() && DigitSpace) {
        if (!(Character.isDigit(content.charAt(i)) || content.charAt(i) == ' '))
          DigitSpace = false;
        i++;
      }
      if (!DigitSpace)
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGSF6"));
    }
    return true;
  }

  /** Metoda proverava sintaksu podatka tipa Identifikacioni broj. */
  public boolean validIDNum(String content) throws Exception {
    if (content.length() != 10)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGIDLEN"));
    return true;
  }

  /** Proverava sintaksu podatka tipa Datum GGGGMMDD. */
  public boolean validDate(String content) throws Exception {
    if (content.length() != 8)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGDATELENGTH"));
    else {
      int i = 0;
      while (i < content.length()) {
        if (!(Character.isDigit(content.charAt(i))))
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGDATEFORMAT"));
        i++;
      }
      int godina = Integer.parseInt(content.substring(0,4));
      int mesec = Integer.parseInt(content.substring(4,6));
      int dan = Integer.parseInt(content.substring(6,8));
      if (mesec < 1 || mesec > 12)
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGMONTHVALUE"));
      if (dan < 1 || dan > 31)
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGDAYVALUE"));
      if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
        if (dan > 30)
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGDAYVALUE30"));
      } else {
        if (((godina%4 == 0 && !(godina%100 == 0)) || godina%400 == 0) && mesec == 2) {
          if (dan > 29)
            throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGDAYVALUE29"));
        } else
          if (mesec == 2 && dan > 28)
            throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_WRONGDAYVALUE28"));
      }
    }
    return true;
  }

  /** Proverava sintaksu podatka tipa Inventarni broj. */
  public boolean validInvNum(String content) throws Exception {
    if (content.length() != 11 && content.length() != 15)
      throw new Exception(Messages.get("BISISAPP_GBVALIDATOR_INVNUMLEN11OR15"));
    for (int i = 0; i <= 10; i++)
       if (!(Character.isDigit(content.charAt(i))))
         throw new Exception(Messages.get("BISISAPP_GBVALIDATOR_INVDIG"));
    if (content.length() == 15) {
       if (content.charAt(11) != '.')
         throw new Exception(Messages.get("BISISAPP_GBVALIDATOR_INV12THCHAR"));
       if (!Character.isDigit(content.charAt(12)) || !Character.isDigit(content.charAt(13))
            || !Character.isDigit(content.charAt(14)))
         throw new Exception(Messages.get("BISISAPP_GBVALIDATOR_LAST3CHAR"));
    }
    if (Environment.getWriteTs().checkInvNumber(content, Environment.getDocID()))
      throw new Exception(Messages.get("BISISAPP_GBVALIDATOR_INVEXISTS"));
    return true;
  }

  /** Proverava sintaksu podatka potpolja 122a. */
  public boolean valid122a(String content) throws Exception {
    if (content.length() != 5)
      throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_122ALEN"));
    else {
      if (content.charAt(0) != 'c' && content.charAt(0) != 'd')
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_122AFIRST"));
      for (int i = 1; i < 5; i++) {
        if (!(Character.isDigit(content.charAt(i))))
          throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_122A2TO5"));
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
        throw new Exception(Messages.get("BISISAPP_NSPMFVALIDATOR_MEMBERSUBSUBSIF"));
    } else
      throw new Exception(Messages.get("BISISAPP_NSFFVALIDATOR_MEMBERSUBSUBSIFMISS"));
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

}
