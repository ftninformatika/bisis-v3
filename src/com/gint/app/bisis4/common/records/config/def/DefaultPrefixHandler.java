package com.gint.app.bisis4.common.records.config.def;

import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.PrefixHandler;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;

/**
 * Default prefix handler.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class DefaultPrefixHandler implements PrefixHandler {

  /**
   * Concatenates subsubfields in a single string so that they can be
   * displayed as a prefix. The resulting string is structured as follows:
   * <ssf-id>content<ssf-id>content...
   * 
   * @param subfield Subfield to be processed.
   * @see com.gint.app.bisis4.common.records.PrefixHandler#concatenateSubsubfields(com.gint.app.bisis4.common.records.Subfield)
   */
  public String concatenateSubsubfields(Subfield subfield) {
    if (subfield.getSubsubfieldCount() == 0)
      return "";
    StringBuffer retVal = new StringBuffer(100);
    for (int i = 0; i < subfield.getSubsubfieldCount(); i++) {
      Subsubfield ssf = subfield.getSubsubfield(i);
      retVal.append('<');
      retVal.append(ssf.getName());
      retVal.append('>');
      retVal.append(ssf.getContent());
    }
    return retVal.toString();
  }

  /**
   * Creates contents of the author (AU) prefix for the given field.
   * 
   * @param field Field to be processed.
   * @return The concatenated elements representing the name of the author.
   * @see com.gint.app.bisis4.common.records.PrefixHandler#createAuthor(com.gint.app.bisis4.common.records.Field)
   */
  public String createAuthor(Field field) {
    StringBuffer retVal = new StringBuffer(50);
    if (field.getInd2() != '0') {
      if (field.getSubfield('b') != null)
        retVal.append(field.getSubfield('b').getContent());
      if (field.getSubfield('a') != null) {
        if (retVal.length() != 0)
          retVal.append(' ');
        retVal.append(field.getSubfield('a').getContent());
      }
    } else {
      if (field.getSubfield('a') != null)
        retVal.append(field.getSubfield('a').getContent());
    }
    return retVal.toString();
  }
}
