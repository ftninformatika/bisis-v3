package com.gint.app.bisis4.common.records;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains miscellaneous methods for modifying records.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class RecordFixes {

  /**
   * Inserts the field 000 with its default values into the given record.
   * @param record Record to be modified.
   */
  public static void addField000(Record record) {
    addField000(record, "import@foo.com");
  }
  
  public static void addField000(Record record, String importer) {
    Field f000 = new Field("000");
    record.getFields().add(0, f000);

    Subfield sfa = new Subfield('a');
    sfa.setContent(record.getPubType() + "01");
    f000.getSubfields().add(sfa);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String dateString = formatter.format(new Date());
    Subfield sfb = new Subfield('b');
    sfb.setContent(dateString + "00000000");
    f000.getSubfields().add(sfb);

    Subfield sfc = new Subfield('c');
    sfc.setContent(importer);
    f000.getSubfields().add(sfc);

    Subfield sfd = new Subfield('d');
    sfd.setContent(importer);
    f000.getSubfields().add(sfd);

    Subfield sfe = new Subfield('e');
    sfe.setContent("0");
    f000.getSubfields().add(sfe);

    Subfield sff = new Subfield('f');
    sff.setContent("0");
    f000.getSubfields().add(sff);
  }
}
