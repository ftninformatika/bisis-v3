package com.gint.app.bisis.editor.report;

import java.util.*;

import com.gint.app.bisis.editor.edit.*;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.StringUtils;


/** ReportsFields -- Obradjuje polja koja ulaze u sastav izvestaja.
    @author Tatjana Zubic tanja@uns.ns.ac.yu
    @version 1.0
  */
public class ReportFields {

  public ReportFields() {
    conv = new UnimarcConverter();
  }

  private UnimarcConverter conv;

  public String getFieldContent(Field currField, String nizPP) {
    return com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_NOTPROCESSED");
  }


  public String getFieldContent001(Field currField, String nizPP){
    String retVal = new String("");
    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
           case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
           default:
                  break;
        }
      }
    }
    return retVal;
  }


  public String getFieldContent010(Field currField, String nizPP){
    String retVal = new String("");
    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
           case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
           default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent011(Field currField, String nizPP){
    String retVal = new String("");
    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'e':
                  if (nizPP.indexOf("e") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }


  public String getFieldContent200(Field currField, String nizPP){

    String retVal = new String("");
    String past = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();

    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    if (past.indexOf("a") != -1)
                       retVal += " ; ";
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " [" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + "] ";
                  }
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += " = " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += " : " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " / " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'g':
                  if (nizPP.indexOf("g") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " ; " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'i':
                  if (nizPP.indexOf("i") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (past.endsWith("h") ? ", " : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'v':   // PASIVNO- ako nije resiti slucaj kad je ispred a
                 /* if (nizPP.indexOf("v") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  past += "v";
                  break; */
          default:
                  break;
        }
        past += sf.getName();
      }
    }
    return retVal;
  }



  public String getFieldContent205(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " = ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " / ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'g':
                  if (nizPP.indexOf("g") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " ; ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent207(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals(""))  {
        switch (sf.getName().charAt(0)) {
         case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + (conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).endsWith("-") ? "    " : "");
                  }
                  break;
         default:
                  break;
        }
      }
    }
    return retVal;
  }

    public String getFieldContent210(Field currField, String nizPP){

    String retVal = new String("");
    String past = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (past.indexOf("a") != -1 ? " ; " : "") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " : ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ", ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + (conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).endsWith("-") ? "    " : "");
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += (past.indexOf("e") != -1 ? " ; " : " (") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'g':
                  if (nizPP.indexOf("g") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ((past.endsWith("e") || past.endsWith("g")) ? " : " : " (") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ((past.endsWith("e") || past.endsWith("g") || past.endsWith("h")) ? ", " : " (") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
        past += sf.getName();
      }
    }
    if (past.indexOf("e") != -1 || past.indexOf("g") != -1 || past.indexOf("h") != -1)
      retVal += ") ";
    return retVal;
  }


  public String getFieldContent215(Field currField, String nizPP){
    String retVal = new String("");
    String past = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                     retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " : ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " ; ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " + ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f'://  nemam interpunkciju
                  break;
          case 'g':// broj knjige za clanak
                  if (nizPP.indexOf("g") != -1/* || nizPP.indexOf("@") != -1*/) {
                    retVal += "Knj. " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':// broj serijske za clanak
                  if (nizPP.indexOf("h") != -1/* || nizPP.indexOf("@") != -1*/) {
                    retVal += ", br. " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'i': // godiste serijske za clanak
                  if (nizPP.indexOf("i") != -1/* || nizPP.indexOf("@") != -1*/) {
                    retVal += "God. " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
        past += sf.getName();
      }
    }
    return retVal;
  }

  public String getFieldContent225(Field currField, String nizPP){
    String retVal = new String("");
    String past = new String("");
    boolean next = false;
    Subfield sf = null;

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements() || next) {
      if (!next)  {
        sf = (Subfield)subfieldIter.nextElement();
      }
      else
        next = false;
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                     retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " = ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " : ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : " / ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1) {
                    String cur = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                    if (past.length() > 0)  {
                      if (subfieldIter.hasMoreElements()) {
                        sf = (Subfield)subfieldIter.nextElement();
                        retVal += (sf.getName().equals("v") && !conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) ? " ; " : ". ";
                        next = true;
                      }
                      else
                        retVal += ". ";
                    }
                    retVal += cur;
                  }
                  break;
          case 'i':
                  if (nizPP.indexOf("i") != -1 || nizPP.indexOf("@") != -1) {
                      retVal += (retVal.equals("") ? "" : (past.endsWith("h") ? ", " : ". ")) + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'v':
                  if (nizPP.indexOf("v") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : (past.endsWith("h") ? ", " : " ; ")) + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" :  ", ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
        past += sf.getName();
      }
    }
    return retVal;
  }

  public String getFieldContent300(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }


  public String getFieldContent321(Field currField, String nizPP){ //za serijske
    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                     retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                    retVal += (retVal.endsWith("-") ? "    " : "");
                  }
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "ISSN" : " ISSN" ) + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }



  public String getFieldContent328(Field currField, String nizPP){
    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                     retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;

          case 'g':
                  if (nizPP.indexOf("g") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }//switch
      } //if
    }
    return retVal;
  }


  public String getFieldContent421(Field currField, String nizPP){
    String retVal = new String("");
    String value = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) { //ide po tagovima - 1
      Subfield sf = (Subfield)subfieldIter.nextElement();
      Vector secFields = sf.getSecFieldInVect();
      if (secFields.size() > 0) {  // dati tag sadrzi sekundarno polje
        for (int si = 0; si < secFields.size(); si++) { //idemo na to sek polje
          Field secf = (Field)secFields.elementAt(si);
          if (secf.getName().equals("200")) {
            if (!(value = getFieldContent200(secf, "aefg")).equals(""))
               retVal += value;
          }
          else if (secf.getName().equals("205")) {
            if (!(value = getFieldContent205(secf, "a")).equals(""))
              retVal += (retVal.equals("") ? "" : ". - ") + value ;
          }
          else if (secf.getName().equals("210")) {
            if (!(value = getFieldContent210(secf, "d")).equals(""))
              retVal += (retVal.equals("") ? "" : ". - ") + value;
          }
          else if (secf.getName().equals("215")) {
            if (!(value = getFieldContent215(secf, "acd")).equals(""))
              retVal += (retVal.equals("") ? "" : ". - ") + value;
          }
          else if (secf.getName().equals("225")) {
            if (!(value = getFieldContent225(secf, "@")).equals(""))
              retVal += (retVal.equals("") ? "" : ". - ") + value;
          } //uneti ispravku za 321 328
          else if (secf.getName().equals("300") || secf.getName().equals("314") || secf.getName().equals("320") ||
                   secf.getName().equals("322") || secf.getName().equals("323") || secf.getName().equals("324") ||
                   secf.getName().equals("326") || secf.getName().equals("328") || secf.getName().equals("330")) {
            if (!(value = getFieldContent300(secf, "a")).equals(""))
              retVal += (retVal.equals("") ? "" : ". - ") + value;
          }
          else if (secf.getName().equals("321") || secf.getName().equals("327")) {
            if (!(value = getFieldContent200(secf, "a")).equals("")) //radimo sa 200a jer je u 321 i 327 pp. a ponovljivo
              retVal += (retVal.equals("") ? "" : ". - ") + value;
          }
        } //for
      }
    } //while
    return retVal;
  }






  private Vector getSecFieldsVect(Field currField) {
    Vector secFieldsVect = new Vector();

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      Vector secFields = sf.getSecFieldInVect();
      if (secFields.size() > 0)
          secFieldsVect.addElement((Field)secFields.elementAt(0));
    }
    return secFieldsVect;
  }




  private Vector getSecFieldsVectII(Field currField) {
    Vector secFieldsVect = new Vector();

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      Vector secFields = sf.getSecFieldInVect();
      if (secFields.size() > 0) {
        Field secF = (Field)secFields.elementAt(0);
        if (secF.getName().equals("700") || secF.getName().equals("701") || secF.getName().equals("702")
            || secF.getName().equals("710") || secF.getName().equals("711") || secF.getName().equals("712")
            || secF.getName().equals("503"))
          secFieldsVect.addElement(secF);
      }
    }
    return secFieldsVect;
  }


  private String getContentSec(Field sf) {
    String retVal = new String("");

    if (sf.getName().equals("700") || sf.getName().equals("701") || sf.getName().equals("702")) {
      retVal = getFieldContent700(sf, "@");
    }
    else if (sf.getName().equals("710") || sf.getName().equals("711") || sf.getName().equals("712")) {
      retVal = getFieldContent710(sf, "@");
    }
    else if (sf.getName().equals("503"))  {
      retVal = getFieldContent710(sf, "@");
    }
    return retVal;
  }

  private int br200=0;

  public int getBr200() { //potrebno kod 423
    return br200;
  }


  public String getFieldContent423(Field currField, String nizPP, int br){
    String retVal = new String("");
    String val = new String("");
    Concept c = new Concept();
    int pos = 0;

    Vector secFields = getSecFieldsVect(currField);  // sva sekundarna
    if (secFields.size() == 0)
      return retVal;
    Vector v200 = c.getField(secFields, "200", " ", " ");
    Vector v500 = c.getField(secFields, "500", " ", " ");

    Vector secFieldsAutor = getSecFieldsVectII(currField); // sekundarna za autore (700,701...)

    if (v200.size() > 0)
      br200 = v200.size();
    else if (v500.size() > 0)
      br200 = v500.size();
    else if (secFields.size() > 0)  {
      if (secFieldsAutor.size() > 0)
        return com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423MUSTHAVE200");
      else
        return com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423CONTENT");
    }

    for (int i=0; i < secFields.size(); i++) {
      Field secF = (Field)secFields.elementAt(i);
      if (secF.getName().equals("700") || secF.getName().equals("701") || secF.getName().equals("702")
          || secF.getName().equals("710") || secF.getName().equals("711") || secF.getName().equals("712")
          || secF.getName().equals("503"))  {  // ako je autor
          if (i==0)
            return com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423WRONGSECFIELDORDER");
          else
             pos++;
      }
      else if (secF.getName().equals("200") || secF.getName().equals("500")) { //ako je naslov
         //uzimamo autora ako ga ima
         val =  (secFieldsAutor.size() > 0) ? getContentSec((Field)secFieldsAutor.elementAt(pos)) : "";
         if (secF.getName().equals("200")) {
// treba provera da li 200 ima vise potpolja a
           String val200 = getFieldContent200(secF,"@"); //sadrzaj 200
           if (!val200.equals(""))  {
             retVal += (retVal.equals("") ? "" : " ") + br + ". " + val + (val.equals("") ? "" : ": ") + val200;
             br++;
           }
         }
         else if (secF.getName().equals("500")) {
             String val500 = getFieldContent500(secF,"@"); //sadrzaj 500
             if (!val500.equals(""))  {
               retVal += (retVal.equals("") ? "" : " ") + br + ". " + val + (val.equals("") ? "" : ": ") + val500;
               br++;
             }
         }
      }
    }
    return retVal;
  }


/// formira vektor nizova koji ce biti odrednice za autorski listic (analitika)
  public Vector getFieldContent423Analitika(Field currField, String nizPP){
    String retVal = new String("");
    String val = new String("");
    Concept c = new Concept();
    Vector out = new Vector();
    int pos = 0;

    Vector secFields = getSecFieldsVect(currField);  // sva sekundarna
    if (secFields.size() == 0)
      return out;
    Vector v200 = c.getField(secFields, "200", " ", " ");
    Vector v500 = c.getField(secFields, "500", " ", " ");

    Vector secFieldsAutor = getSecFieldsVectII(currField); // sekundarna za autore (700,701...)

    if (v200.size() > 0)
      br200 = v200.size();
    else if (v500.size() > 0)
      br200 = v500.size();
    else if (secFields.size() > 0)  {
      if (secFieldsAutor.size() > 0)  {
         out.addElement(com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423MUSTHAVE200"));
         return out;
      }
      else {
        out.addElement(com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423CONTENT"));
        return out;
      }
    }

    for (int i=0; i < secFields.size(); i++) {
      Field secF = (Field)secFields.elementAt(i);
      if (secF.getName().equals("700") || secF.getName().equals("701") || secF.getName().equals("702")
          || secF.getName().equals("710") || secF.getName().equals("711") || secF.getName().equals("712")
          || secF.getName().equals("503"))  {  // ako je autor
          if (i==0) {
            out.addElement(com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423WRONGSECFIELDORDER"));
            return out;
          }
          else
             pos++;
      }
      else if (secF.getName().equals("200") || secF.getName().equals("500")) { //ako je naslov
         //uzimamo autora ako ga ima
         val =  (secFieldsAutor.size() > 0) ? getContentSec((Field)secFieldsAutor.elementAt(pos)) : "";
         if (secF.getName().equals("200")) {
// treba provera da li 200 ima vise potpolja a
           String val200 = getFieldContent200(secF,"@"); //sadrzaj 200
           if (!val200.equals(""))  {
             retVal = val + (val.equals("") ? "" : ": ") + val200;
             out.addElement(retVal);
           }
         }
         else if (secF.getName().equals("500")) {
             String val500 = getFieldContent500(secF,"@"); //sadrzaj 500
             if (!val500.equals(""))  {
               retVal = val + (val.equals("") ? "" : ": ") + val500;
               out.addElement(retVal);
             }
         }
      }
    }
    return out;
  }




  public String getFieldContent469(Field currField, String nizPP){
    String retVal = new String("");
    String value = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) { //ide po tagovima - 1
      Subfield sf = (Subfield)subfieldIter.nextElement();
      Vector secFields = sf.getSecFieldInVect();
      if (secFields.size() > 0) {  // dati tag sadrzi sekundarno polje
        for (int si = 0; si < secFields.size(); si++) { //idemo na to sek polje
          Field secf = (Field)secFields.elementAt(si);
          if (secf.getName().equals("200")) {
            if (!(value = getFieldContent200(secf, "aefg")).equals(""))
               retVal += value;
          }
          else if (secf.getName().equals("215")) {
            if (!(value = getFieldContent215(secf, "a")).equals(""))
              retVal += " (" + value + ") ";
          }
       //videti 333, 900 proveriti 326,7,8,330
          else if (secf.getName().equals("300") || secf.getName().equals("314") || secf.getName().equals("320") || secf.getName().equals("321") ||
                   secf.getName().equals("322") || secf.getName().equals("323") || secf.getName().equals("324") || secf.getName().equals("328")) {
            if (!(value = getFieldContent300(secf, "a")).equals(""))
               retVal += (retVal.equals("") ? "" : ". - ") + value;
          }
        } //for
      }
    } //while
    return retVal;
  }



  public String getFieldContent500(Field currField, String nizPP){
    String retVal = new String("");
    String past = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                     retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'i':
                  if (nizPP.indexOf("i") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'k':
                  if (nizPP.indexOf("k") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'l':
                  if (nizPP.indexOf("l") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'm':
                  if (nizPP.indexOf("m") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " [" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + "] ";
                  }
                  break;
          default:
                  break;
        }
        past += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
      }
    }
    return retVal;
  }

  public String getFieldContent503(Field currField, String nizPP){    //redosled a,j,b
    String retVal = new String("");
    String past = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                     retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += (retVal.equals("") ? "" : ". ") + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  past += "b";
                  break;
          case 'j':
                  if (nizPP.indexOf("j") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += " (" +conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + ") ";
                  }
                  break;
          default:
                  break;
        }
        past += sf.getName();
      }
    }
    return retVal;
  }

  public String getFieldContent532(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      switch (sf.getName().charAt(0)) {
         case 'a':
                  retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
         default:
                  break;
      }
    }
    return retVal;
  }

  public String getFieldContent600(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':  //obavezno a
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += " " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'y':
                  if (nizPP.indexOf("y") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'z':
                  if (nizPP.indexOf("z") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'w':
                  if (nizPP.indexOf("w") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent601(Field currField, String nizPP){

    String retVal = new String("");
    String past = new String("");
    boolean open = false; // otvorena zagrada

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':        //obavezno a
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " (" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + ") ";
                  }
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += " (" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                    open = true;
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1|| nizPP.indexOf("@") != -1) {
                    if (past.indexOf("e") != -1)
                      retVal += ", ";
                    else {
                      if (open)
                        retVal += " ; " ;
                      else  {
                        retVal += " (";
                        open = true;
                      }
                    }
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                 if (nizPP.indexOf("f") != -1|| nizPP.indexOf("@") != -1) {
                    if (open)
                      retVal += " ; ";
                    else {
                      retVal += " (";
                      open = true;
                    }
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'g':
                  if (nizPP.indexOf("g") != -1 || nizPP.indexOf("@") != -1) {
                     if (open) {
                       retVal += "). ";
                       open = false;
                     }
                     else
                       retVal += ". ";
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1) {
                     if (open)  {
                       retVal += "). ";
                       open = false;
                     }
                     else
                       retVal += ". ";
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1) {
                    if (open) {
                         retVal += ")";
                         open = false;
                    }
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'y':
                  if (nizPP.indexOf("y") != -1 || nizPP.indexOf("@") != -1) {
                    if (open) {
                         retVal += ")";
                         open = false;
                    }
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'z':
                  if (nizPP.indexOf("z") != -1 || nizPP.indexOf("@") != -1) {
                    if (open)  {
                         retVal += ")";
                         open = false;
                    }
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'w':
                  if (nizPP.indexOf("w") != -1 || nizPP.indexOf("@") != -1) {
                    if (open)  {
                         retVal += ")";
                         open = false;
                    }
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
         }
         past += sf.getName();
      }
    }
    if (open)
      retVal += ")";
    return retVal;
  }

  public String getFieldContent602(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':  //obavezno
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1)
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'y':
                  if (nizPP.indexOf("y") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'z':
                  if (nizPP.indexOf("z") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'w':
                  if (nizPP.indexOf("w") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent605(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1)
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'i':
                  if (nizPP.indexOf("i") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'k':
                  if (nizPP.indexOf("k") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'l':
                  if (nizPP.indexOf("l") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'm':
                  if (nizPP.indexOf("m") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " [" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + "] ";
                  break;
          case 'n':
                  if (nizPP.indexOf("n") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " [" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + "] ";
                  break;
          case 'q':
                  if (nizPP.indexOf("q") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " [" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + "] ";
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'y':
                  if (nizPP.indexOf("y") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'z':
                  if (nizPP.indexOf("z") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'w':
                  if (nizPP.indexOf("w") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent606(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':  //obavezno
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1)
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'x':
                  if (nizPP.indexOf("x") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'y':
                  if (nizPP.indexOf("y") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'z':
                  if (nizPP.indexOf("z") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'w':
                  if (nizPP.indexOf("w") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " - " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent700(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':  //obavezno
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1)
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1|| nizPP.indexOf("@") != -1)
                    retVal += " " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'f':    //
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }

  public String getFieldContent710(Field currField, String nizPP){

    String retVal = new String("");
    String past = new String("");
    boolean open = false; //otvorena zagrada

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a':  //obavezno
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1)
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'b':
                  if (nizPP.indexOf("b") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'c':
                  if (nizPP.indexOf("c") != -1 || nizPP.indexOf("@") != -1)
                    retVal += " (" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + ") ";
                  break;
          case 'd':
                  if (nizPP.indexOf("d") != -1|| nizPP.indexOf("@") != -1) {
                    retVal += " (" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                    open = true;
                  }
                  break;
          case 'e':
                  if (nizPP.indexOf("e") != -1|| nizPP.indexOf("@") != -1) {
                    if (past.indexOf("e") != -1)
                      retVal += ", ";
                    else {
                      if (open)
                        retVal += " ; ";
                      else {
                        retVal += " (";
                        open = true;
                      }
                    }
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1|| nizPP.indexOf("@") != -1) {
                    if (open)
                      retVal += " ; ";
                    else  {
                      retVal += " (";
                      open = true;
                    }
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'g':
                  if (nizPP.indexOf("g") != -1 || nizPP.indexOf("@") != -1) {
                    if (open) {
                       retVal += ") ";
                       open = false;
                    }
                    if (past.endsWith("c"))
                      retVal += " ";
                    else
                      retVal += ". ";
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'h':
                  if (nizPP.indexOf("h") != -1 || nizPP.indexOf("@") != -1)
                    if (open) {
                      retVal += ") ";
                      open = false;
                    }
                    retVal += ". " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
        past += sf.getName();
      }
    }
    if (open)
      retVal += ")";
    return retVal;
  }


   public String getFieldContent720(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'a': //obavezno
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1)
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1)
                    retVal += ", " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }
   public String getFieldContent996(Field currField, String nizPP){

    String retVal = new String("");
    String d, f, s, n, a, i, l, u, w, x, y, z,q;
    d = ""; f = ""; s = ""; n = ""; a = ""; i = ""; l = ""; u = "";
    w = ""; x = ""; y = ""; z = "";q="";

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (sf.getName().equals("d")&& nizPP.indexOf("d") != -1) {
        if  (conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals(""))  {
          Vector ssfv = sf.getSubsubfields();
          if (ssfv.size() != 0) { // ako ima potpotpolja
            for (int jj = 0; jj < ssfv.size(); jj++) {
              Subfield ssf = (Subfield)ssfv.elementAt(jj);
              String ssfContent = conv.Unimarc2Unicode(StringUtils.adjustForHTML(ssf.getContent()));
              if (!ssfContent.equals("")) {
              switch (ssf.getName().charAt(0)) {
                case 'd': // Dublet
                          d = ssfContent;
                          break;
                case 'f': // Format
                          f = StringUtils.arabicToRoman(ssfContent);
                          break;
                case 's': // Numeracija
                          s = ssfContent;
                          break;
                case 'n': // Tekuci broj
                          n = ssfContent;
                          break;
                case 'a': // Oznaka varijante ili ABC oznaka
                          a = ssfContent;
                          break;
                case 'i': // Interna oznaka
                          i = ssfContent;
                          break;
                case 'l': // Oznaka podlokacije
                          l = ssfContent;
                          break;
                case 'u': // Slobodan pristup UDK
                          u = ssfContent;
                          break;
                case 'w': // Razresenje numeracije (4. nivo)
                          w = ssfContent;
                          break;
                case 'x': // Razresenje numeracije (1. nivo)
                          x = ssfContent;
                          break;
                case 'y': // Razresenje numeracije (2. nivo)
                          y = ssfContent;
                          break;
                case 'z': // Razresenje numeracije (3. nivo)
                          z = ssfContent;
                          break; 
              } //switch
              } //if
           } //for
            
            if (!d.equals(""))
              retVal += d;
            if (!i.equals(""))
              retVal += (retVal.equals("") ? "" : " ") + i;
            if (!l.equals(""))
              retVal += (retVal.equals("") ? "" : " ") + l;
            if (!f.equals(""))
              retVal += (retVal.equals("") ? "" : " ") + f + " -";
            if (!n.equals(""))
              retVal += (retVal.equals("") ? "" : " ") + n;
            if (!s.equals(""))
              retVal += "/"+s;
          }
        }
      }
      else if (sf.getName().equals("f") && nizPP.indexOf("f") != -1) {
        if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
          retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).trim(); //zbog citanja iz fajla dodat trim()
        }
      }
      else if (sf.getName().equals("q") && nizPP.indexOf("q") != -1) {
          if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
            retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).trim(); //zbog citanja iz fajla dodat trim()
          }else{
        	  retVal =sf.getContent();
          }
        }
    }
//    if (!retVal.equals("")) {
//     String blanko = new String("");
//     for (int j=0; j < (Report.bkmax-retVal.trim().length()); j++)
//        blanko += " ";
//System.out.println("blanko=#"+blanko.length()+"#   inventar = #"+retVal.length()+"# bkmax=#"+Report.bkmax);
//System.out.println("inventar pre blanka = #"+retVal+"#");
//     retVal = blanko + retVal;
//    }
    return retVal;
   }


   public String getFieldContent996I(Field currField, String nizPP){

    String retVal = new String("");
    String d, f, s, n, a, i, l, u, w, x, y, z;
    d = ""; f = ""; s = ""; n = ""; a = ""; i = ""; l = ""; u = "";
    w = ""; x = ""; y = ""; z = "";

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (sf.getName().equals("d")&& nizPP.indexOf("d") != -1) {
        if  (conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals(""))  {
          Vector ssfv = sf.getSubsubfields();
          if (ssfv.size() != 0) { // ako ima potpotpolja
            for (int jj = 0; jj < ssfv.size(); jj++) {
              Subfield ssf = (Subfield)ssfv.elementAt(jj);
              String ssfContent = conv.Unimarc2Unicode(StringUtils.adjustForHTML(ssf.getContent()));
              if (!ssfContent.equals("")) {
              switch (ssf.getName().charAt(0)) {
                case 'u': 
                          u = ssfContent;
                	      
                          break;
               
              } //switch
              } //if
           } //for
           
            if (!u.equals(""))
              retVal += (retVal.equals("") ? "" : " ") + u; 
          }
        }
      }
      else if (sf.getName().equals("f") && nizPP.indexOf("f") != -1) {
        if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
          retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).trim(); //zbog citanja iz fajla dodat trim()
        }
      }
    }
    return retVal;
   }


   public String getFieldContent996II(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'f':
                  if (nizPP.indexOf("f") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'o':
                  if (nizPP.indexOf("o") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 's':
                  if (nizPP.indexOf("s") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'v':
                  if (nizPP.indexOf("v") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case '3':  
                  if (nizPP.indexOf("3") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'r':  
                  if (nizPP.indexOf("r") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'q': 
                  if (nizPP.indexOf("q") != -1 || nizPP.indexOf("@") != -1) {
                      retVal=conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));  
                     
                  }
          default:
                  break;
        }
      }
    }
    return retVal;
  }


   public String getFieldContent997(Field currField, String nizPP){

    String retVal = new String("");

    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
          case 'j':
                  if (nizPP.indexOf("j") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          case 'k':
                  if (nizPP.indexOf("k") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " (" + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())) + ") ";
                  }
                  break;
          case 'm':
                  if (nizPP.indexOf("m") != -1 || nizPP.indexOf("@") != -1) {
                    retVal += " : " + conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
          default:
                  break;
        }
      }
    }
    return retVal;
  }
   
	

/**
 * @param field
 * @param string
 * @return
 */
public String getFieldContent610(Field currField, String nizPP) {
	 String retVal = new String("");
	    boolean ponov=false;

	    Enumeration subfieldIter = currField.getSubfieldsEnum();
	    while (subfieldIter.hasMoreElements()) {
	      Subfield sf = (Subfield)subfieldIter.nextElement();
	      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
	        switch (sf.getName().charAt(0)) {
	          case 'a':  //obavezno
	                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1){
	                  	if(!ponov){
	                  		ponov=true;
	                  	    retVal += conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
	                  	}
	                  	else{
	                  		retVal +=","+ conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
	                  	}
	                  }            	
	                                     
	                  break;
	          default:
	          	break;
	        }
	        }  
	    }
	    return retVal;
}


/**
 * @param field
 * @param string
 * @return
 */
public String getFieldContent998(Field currField, String nizPP) {
	
	String retVal = new String("");
    Enumeration subfieldIter = currField.getSubfieldsEnum();
    while (subfieldIter.hasMoreElements()) {
      Subfield sf = (Subfield)subfieldIter.nextElement();
      if (!conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent())).equals("")) {
        switch (sf.getName().charAt(0)) {
           case 'a':
                  if (nizPP.indexOf("a") != -1 || nizPP.indexOf("@") != -1) {
                    retVal = conv.Unimarc2Unicode(StringUtils.adjustForHTML(sf.getContent()));
                  }
                  break;
                
           default:
                  break;
        }
      }
    }
    return retVal;
}

} // kraj klase
