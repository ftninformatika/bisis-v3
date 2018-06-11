package com.gint.app.bisis.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.RecordID;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;

/**
 * @author mbranko@uns.ns.ac.yu
 *
 */
class LooseSAXHandler extends DefaultHandler {

  public LooseSAXHandler(Connection conn) {
    this.conn = conn;
  }
  
  public void startDocument() throws SAXException {
    try {
      //pstmt = conn.prepareStatement(
          //"UPDATE documents SET document=? WHERE doc_id=?");
      pstmt = conn.prepareStatement(
          "INSERT INTO documents (document, doc_id) VALUES (? ,?)");
    } catch (Exception ex) {
    }
  }

  public void endDocument() throws SAXException {
    try {
      pstmt.close();
    } catch (Exception ex) {
    }
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    try {
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
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
    try {
    if (qName.equals("record")) {
      currRecord.sort();
      try {
        pstmt.clearParameters();
        pstmt.setString(1, RecordFactory.toUNIMARC(currRecord));
        pstmt.setInt(2, currRecord.getRecordID().getLocalRecordID());
        pstmt.executeUpdate();
        conn.commit();
        System.out.println("imported record ID: " + currRecord.getRecordID().getLocalRecordID());
      } catch (Exception ex) {
      }
      currRecord = null;
    } else if (qName.equals("field")) {
      currField = null;
    } else if (qName.equals("subfield")) {
      currSubfield = null;
    } else if (qName.equals("subsubfield")) {
      currSubsubfield = null;
    }
    } catch (Exception ex) {
      ex.printStackTrace();
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
  Connection conn;
  PreparedStatement pstmt;
}
