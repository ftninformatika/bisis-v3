package com.gint.app.bisis4.common.records;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.gint.util.string.Signature;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

/**
 * Contains methods for building records from various sources and serializing
 * them to various formats.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class RecordFactory {

  /** Field separator in UNIMARC format. */
  public static final String FIELD_SEP = "\036";
  /** Subfield separator in UNIMARC format. */
  public static final String SUBFIELD_SEP = "\037";
  /** Subsubfield separator in UNIMARC format. */
  public static final String SUBSUBFIELD_SEP = "\\";

  /**
   * Creates a record from a UNIMARC-encoded string.
   * 
   * @param s String with the UNIMARC-encoded content. UNIMARC character 
   *   encoding is assumed (higher bytes are 0, lower bytes are read from file).
   * @return An instance of a <code>Record</code>.
   */
  public static Record fromUNIMARC(String s) {
    if (s == null || s.equals(""))
      return null;
    UnimarcConverter conv = new UnimarcConverter();
    Record retVal = new Record();
    StringTokenizer st1 = new StringTokenizer(s, FIELD_SEP);
    while (st1.hasMoreTokens()) {
      String fieldText = st1.nextToken();
      Field field = new Field();
      retVal.getFields().add(field);
      field.setName(fieldText.substring(0, 3));
      field.setInd1(fieldText.charAt(3));
      field.setInd2(fieldText.charAt(4));
      if ((field.getName().equals("421") || field.getName().equals("423") || field.getName().equals("464") || field.getName().equals("469")) && !retVal.getPubType().equals(Record.TYPE_S1) && !retVal.getPubType().equals(Record.TYPE_NO)) {
        String tmp = fieldText.substring(5);
        while (tmp != null) {
         Subfield sf = new Subfield();
          sf.setName('1');
          field.getSubfields().add(sf);
          int sepPos = tmp.indexOf(SUBFIELD_SEP + "1", 1);
          String secFieldText;
          if (sepPos == -1) {
            secFieldText = tmp.substring(2);
            tmp = null;
          } else {
            secFieldText = tmp.substring(2, sepPos);
            tmp = tmp.substring(sepPos);
          }
          Field secF = new Field();
          if (secFieldText.length() > 5) {
            secF.setName(secFieldText.substring(0, 3));
            secF.setInd1(secFieldText.charAt(3));
            secF.setInd2(secFieldText.charAt(4));
            sf.setSecField(secF);
            StringTokenizer st2 = new StringTokenizer(secFieldText.substring(5), SUBFIELD_SEP);
            while (st2.hasMoreTokens()) {
              String subfieldText = st2.nextToken();
              Subfield secSF = new Subfield();
              secSF.setName(subfieldText.charAt(0));
              secSF.setContent(conv.Unimarc2Unicode(subfieldText.substring(1)));
              secF.getSubfields().add(secSF);
            }
          }
        }
      } else {
        StringTokenizer st2 = new StringTokenizer(fieldText.substring(5), SUBFIELD_SEP);
        while (st2.hasMoreTokens()) {
          String subfieldText = st2.nextToken();
          Subfield sf = new Subfield();
          sf.setName(subfieldText.charAt(0));
          if (containsSubsubfields(field.getName(), sf.getName())) {
            StringTokenizer st3 = new StringTokenizer(subfieldText.substring(1), SUBSUBFIELD_SEP);
            while (st3.hasMoreTokens()) {
              String subsubfieldText = st3.nextToken();
              Subsubfield ssf = new Subsubfield();
              ssf.setName(subsubfieldText.charAt(0));
              ssf.setContent(conv.Unimarc2Unicode(subsubfieldText.substring(1)));
              sf.getSubsubfields().add(ssf);
            }
          } else {
            sf.setContent(conv.Unimarc2Unicode(subfieldText.substring(1)));
            if (field.getName().equals("000") && sf.getName() == 'a') {
              retVal.setPubType(subfieldText.substring(1, 3));
            }
          }
          field.getSubfields().add(sf);
        }
      }
    }
    return retVal;
  }
  
  private static boolean containsSubsubfields(String fName, char sfName) {
    if ((sfName == 'd') && (fName.equals("996") || fName.equals("997") || fName.equals("998")))
      return true;
    if ((sfName == '1') && (fName.equals("996") || fName.equals("997")))
      return true;
    return false;
  }
  
  /**
   * Serializes the record to a UNIMARC-encoded string.
   * @param record The record to be serialized.
   * @return UNIMARC-encoded string with the serialized record. UNIMARC 
   *   character encoding is assumed (higher bytes are 0, lower bytes to be 
   *   written to a file).
   */
  public static String toUNIMARC(Record record) {
    UnimarcConverter conv = new UnimarcConverter();
    StringBuffer retVal = new StringBuffer(1024);
    for (int i = 0; i < record.getFields().size(); i++) {
      if (i > 0)
        retVal.append(FIELD_SEP);
      Field field = (Field)record.getFields().get(i);
      fieldToUNIMARC(retVal, field);
    }
    return retVal.toString();
  }
  
  /**
   * Serializes one field to a UNIMARC-encoded string.
   * @param buff Buffer to append the output to.
   * @param field field to be serialized.
   */
  private static void fieldToUNIMARC(StringBuffer buff, Field field) {
    UnimarcConverter conv = new UnimarcConverter();
    buff.append(field.getName());
    buff.append(field.getInd1());
    buff.append(field.getInd2());
    for (int i = 0; i < field.getSubfields().size(); i++) {
      buff.append(SUBFIELD_SEP);
      Subfield subfield = field.getSubfield(i);
      buff.append(subfield.getName());
      if (subfield.getSecField() != null) {
        fieldToUNIMARC(buff, subfield.getSecField());
      } else if (subfield.getSubsubfields().size() > 0) {
        for (int k = 0; k < subfield.getSubsubfields().size(); k++) {
          Subsubfield subsubfield = subfield.getSubsubfield(k);
          if (k > 0)
            buff.append(SUBSUBFIELD_SEP);
          buff.append(subsubfield.getName());
          buff.append(conv.Unicode2Unimarc(subsubfield.getContent(), true));
        }
      } else {
        buff.append(conv.Unicode2Unimarc(subfield.getContent(), true));
      }
    }
  }
  
  /**
   * Serializes a record in the full format for display. Can produce 
   * HTML-compliant output.
   * 
   * @param record Record to be serialized.
   * @param forHTML If <code>true</code>, produces HTML-compatible output.
   * @return The serialized record.
   */
  public static String toFullFormat(Record record, boolean forHTML) {
    StringBuffer retVal = new StringBuffer(1024);
    for (int i = 0; i < record.getFields().size(); i++) {
      Field field = record.getField(i);
      fieldToFullFormat(retVal, field, forHTML);
      if (forHTML)
        retVal.append("<br/>");
      retVal.append('\n');
    }
    return retVal.toString();
  }
  
  /**
   * Serializes one field in the full format. Can produce HTML-compatible 
   * output.
   * 
   * @param buff Buffer to append the output to.
   * @param field Field to be serialized
   * @param forHTML If <code>true</code>, produces HTML-compatible output.
   */
  private static void fieldToFullFormat(StringBuffer buff, Field field, boolean forHTML) {
    if (forHTML)
      buff.append("<b>");
    buff.append(field.getName());
    if (forHTML)
      buff.append("</b>");
    buff.append(' ');
    buff.append(field.getInd1() == ' ' ? '#' : field.getInd1());
    buff.append(field.getInd2() == ' ' ? '#' : field.getInd2());
    buff.append(' ');
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      if (forHTML)
        buff.append("<b>");
      buff.append('[');
      buff.append(subfield.getName());
      buff.append(']');
      if (forHTML)
        buff.append("</b>");
      if (subfield.getSecField() != null) {
        if (forHTML)
          buff.append("<b>");
        buff.append("{{");
        if (forHTML)
          buff.append("</b>");
        fieldToFullFormat(buff, subfield.getSecField(), forHTML);
        if (forHTML)
          buff.append("<b>");
        buff.append("}}");
        if (forHTML)
          buff.append("</b>");
      } else if (subfield.getSubsubfieldCount() > 0) {
        for (int j = 0; j < subfield.getSubsubfieldCount(); j++) {
          Subsubfield subsubfield = subfield.getSubsubfield(j);
          if (forHTML)
            buff.append("<b>");
          buff.append('(');
          buff.append(subsubfield.getName());
          buff.append(')');
          if (forHTML)
            buff.append("</b>");
          if (forHTML)
            buff.append(StringUtils.adjustForHTML(subsubfield.getContent()));
          else
            buff.append(subsubfield.getContent());
        }
      } else {
        if (forHTML)
          buff.append(StringUtils.adjustForHTML(subfield.getContent()));
        else
          buff.append(subfield.getContent());
      }
    }
  }
  
  /**
   * Parses a record from a full-format representation. NOT IMPLEMENTED YET.
   * 
   * @param s String containing the full-format representation.
   * @return An instance of the record.
   */
  public static Record fromFullFormat(String s) {
    return null;
  }
  
  /**
   * Serializes the record to the "loose" XML format. XML string is not
   * indented for pretty-printing.
   * 
   * @param rec The record to be serialized.
   * @return The XML document representing the record.
   */
  public static String toLooseXML(Record rec) {
    StringBuffer retVal = new StringBuffer(2048);
    retVal.append("<record libraryID=\"");
    retVal.append(rec.getRecordID().getLibraryID());
    retVal.append("\" localID=\"");
    retVal.append(rec.getRecordID().getLocalRecordID());
    retVal.append("\">\n");
    for (int i = 0; i < rec.getFieldCount(); i++) {
      Field field = rec.getField(i);
      fieldToLooseXML(retVal, field);
    }
    retVal.append("</record>\n");
    return retVal.toString();
  }
  
  /**
   * Serializes one field to the "loose" XML format.
   * 
   * @param buff Buffer to append the output to.
   * @param field The field to be serialized.
   */
  private static void fieldToLooseXML(StringBuffer buff, Field field) {
    buff.append("  <field name=\"");
    buff.append(field.getName());
    buff.append("\" ind1=\"");
    buff.append(field.getInd1());
    buff.append("\" ind2=\"");
    buff.append(field.getInd2());
    buff.append("\">\n");
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      buff.append("    <subfield name=\"");
      buff.append(subfield.getName());
      buff.append("\">");
      if (subfield.getSecField() != null) {
        fieldToLooseXML(buff, subfield.getSecField());
        buff.append("    </subfield>\n");
      } else if (subfield.getSubsubfieldCount() > 0) {
        for (int j = 0; j < subfield.getSubsubfieldCount(); j++) {
          Subsubfield subsubfield = subfield.getSubsubfield(j);
          buff.append("\n      <subsubfield name=\"");
          buff.append(subsubfield.getName());
          buff.append("\">");
          buff.append(StringUtils.adjustForHTML(subsubfield.getContent()));
          buff.append("</subsubfield>");
        }
        buff.append("\n    </subfield>\n");
      } else {
        buff.append(StringUtils.adjustForHTML(subfield.getContent()));
        buff.append("</subfield>\n");
      }
    }
    buff.append("  </field>\n");
  }
  
  /**
   * Reads the record from the "loose" XML representation.
   * @param xml String with the XML document. UTF-16 encoding is assumed.
   * @return The initalized record.
   */
  public static Record fromLooseXML(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      LooseSAXHandler handler = new LooseSAXHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getRecord();
    } catch (Exception ex) {
      return null;
    }
  }
  
  /**
   * Serializes the record to the "tight" XML format. XML string is not indented
   * for pretty-printing.
   * 
   * @param record The record to be serialized.
   * @return The serialized record.
   */
  public static String toTightXML(Record record) {
    StringBuffer retVal = new StringBuffer();
    retVal.append("<record");
    retVal.append(">\n");
    for (int i = 0; i < record.getFieldCount(); i++) {
      Field field = record.getField(i);
      fieldToTightXML(retVal, field);
    }
    retVal.append("</record>\n");
    return retVal.toString();
  }
  
  /**
   * Serializes the given field to the "tight" XML format.
   * @param buff Buffer to append the output to.
   * @param field Field to be serialized.
   */
  private static void fieldToTightXML(StringBuffer buff, Field field) {
    buff.append("  <f");
    buff.append(field.getName());
    buff.append(">\n");
    if (field.getInd1() != ' ') {
      buff.append("    <ind1>");
      buff.append(field.getInd1());
      buff.append("</ind1>\n");
    }
    if (field.getInd2() != ' ') {
      buff.append("    <ind2>");
      buff.append(field.getInd2());
      buff.append("</ind2>\n");
    }
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i); 
      buff.append("    <sf");
      buff.append(subfield.getName());
      buff.append(">");
      if (subfield.getSecField() != null) {
        fieldToTightXML(buff, subfield.getSecField());
      } else if (subfield.getSubsubfieldCount() > 0) {
        for (int j = 0; j < subfield.getSubsubfieldCount(); j++) {
          Subsubfield ssf = subfield.getSubsubfield(j);
          buff.append("<ssf");
          buff.append(ssf.getName());
          buff.append(">");
          buff.append(StringUtils.adjustForHTML(ssf.getContent()));
          buff.append("</ssf");
          buff.append(ssf.getName());
          buff.append(">");
        }
      } else {
        buff.append(StringUtils.adjustForHTML(subfield.getContent()));
      }
      buff.append("</sf");
      buff.append(subfield.getName());
      buff.append(">\n");
    }
    buff.append("  </f");
    buff.append(field.getName());
    buff.append(">\n");
  }
  
  /**
   * Reads the record from the "tight" XML representation.
   * @param xml String with the XML document. UTF-16 encoding is assumed.
   * @return The initalized record.
   */
  public static Record fromTightXML(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      TightSAXHandler handler = new TightSAXHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getRecord();
    } catch (Exception ex) {
      return null;
    }
  }
  
  public static List toPrefixes(Record record) {
    List retVal = new ArrayList();
    for (int i = 0; i < record.getFieldCount(); i++) {
      Field field = record.getField(i);
      fieldToPrefixes(retVal, field);
    }
    return retVal;
  }
  
  private static void fieldToPrefixes(List dest, Field field) {
    if (field.getName().startsWith("70") || field.getName().startsWith("90")){
      String au = prefixHandler.createAuthor(field);
      if (!au.equals(""))
        dest.add(new PrefixValue("AU", au));
    }
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      if (subfield.getSecField() != null) {
        fieldToPrefixes(dest, subfield.getSecField());
      } else if (subfield.getSubsubfieldCount() > 0) {
        String content = prefixHandler.concatenateSubsubfields(subfield);
        if (!content.equals("")) {
          List prefList = prefixMap.getPrefixes(
              field.getName()+subfield.getName());
          if (prefList != null) {
            Iterator it = prefList.iterator();
            while (it.hasNext()) {
              String prefName = (String)it.next();
              dest.add(new PrefixValue(prefName, content));
            }
          }
        }
      } else {
        List prefList = (List)prefixMap.getPrefixes(
            field.getName()+subfield.getName());
        if (prefList != null) {
          Iterator it = prefList.iterator();
          while (it.hasNext()) {
            String prefName = (String)it.next();
            if (prefName.equals("AU"))
              continue;
            if (!subfield.getContent().equals(""))
              dest.add(new PrefixValue(prefName, subfield.getContent()));
          }
        }
      }
    }
  }
  
  public static String toISISFormat(Record record) {
    StringBuffer buff = new StringBuffer(1024);
    buff.append("! ID ");
    buff.append(StringUtils.padChars(Integer.toString(
        record.getRecordID().getLocalRecordID()), '0', 6));
    buff.append('\n');
    Iterator it = record.getFields().iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      if (f.getName().equals("421") || f.getName().equals("423") || f.getName().equals("464") || f.getName().equals("469"))
        continue;
      buff.append("!v");
      buff.append(f.getName());
      buff.append('!');
      Iterator it2 = f.getSubfields().iterator(); 
      while (it2.hasNext()) {
        Subfield sf = (Subfield)it2.next();
        buff.append('^');
        buff.append(sf.getName());
        if ((f.getName().equals("996") || f.getName().equals("997")) && sf.getName() == 'd')
          buff.append(Signature.userDisplay(sf.getContent()));
        else
          buff.append(sf.getContent());
      }
      buff.append('\n');
    }
    return buff.toString();
  }
  
  public static PrefixConfig getPrefixConfig() {
    return prefixConfig;
  }
  
  static SAXParserFactory factory;
  static PrefixConfig prefixConfig;
  static PrefixHandler prefixHandler;
  static PrefixMap prefixMap;
  
  static {
    factory = SAXParserFactory.newInstance();
    prefixConfig = PrefixConfigFactory.getPrefixConfig();
    prefixHandler = prefixConfig.getPrefixHandler();
    prefixMap = prefixConfig.getPrefixMap();
  }

}
