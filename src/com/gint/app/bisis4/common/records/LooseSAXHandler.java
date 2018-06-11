package com.gint.app.bisis4.common.records;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author mbranko@uns.ns.ac.yu
 *
 */
class LooseSAXHandler extends DefaultHandler {

  public Record getRecord() {
    return parsedRecord;
  }
  
  public void startDocument() throws SAXException {
  }

  public void endDocument() throws SAXException {
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    if (qName.equals("record")) {
      currRecord = new Record();
      int libraryID = Integer.parseInt(attrs.getValue("libraryID"));
      int localID = Integer.parseInt(attrs.getValue("localID"));
      currRecord.setRecordID(new RecordID(libraryID, localID));
    } else if (qName.equals("field")) {
      currField = new Field();
      currField.setName(attrs.getValue("name"));
      currField.setInd1(attrs.getValue("ind1").charAt(0));
      currField.setInd2(attrs.getValue("ind2").charAt(0));
      if (currSubfield == null)
        currRecord.getFields().add(currField);
      else
        currSubfield.setSecField(currField);
    } else if (qName.equals("subfield")) {
      currSubfield = new Subfield();
      currSubfield.setName(attrs.getValue("name").charAt(0));
      currField.getSubfields().add(currSubfield);
    } else if (qName.equals("subsubfield")) {
      currSubsubfield = new Subsubfield();
      currSubsubfield.setName(attrs.getValue("name").charAt(0));
      currSubfield.getSubsubfields().add(currSubsubfield);
    }
  }

  public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
    if (qName.equals("record")) {
      currRecord.sort();
      parsedRecord = currRecord;
      currRecord = null;
    } else if (qName.equals("field")) {
      currField = null;
    } else if (qName.equals("subfield")) {
      currSubfield = null;
    } else if (qName.equals("subsubfield")) {
      currSubsubfield = null;
    }
  }

  public void characters(char[] buf, int offset, int len) throws SAXException {
    String cnt = new String(buf, offset, len).trim().replace('\r', ' ').replace('\n', ' ');
    if (cnt.equals(""))
      return;
    if (currSubsubfield != null)
      currSubsubfield.setContent(currSubsubfield.getContent() + cnt);
    else if (currSubfield != null)
      currSubfield.setContent(currSubfield.getContent() + cnt);
  }

  int count = 0;
  Record currRecord = null;
  Field currField = null;
  Subfield currSubfield = null;
  Subsubfield currSubsubfield = null;
  Record parsedRecord = null;
}
