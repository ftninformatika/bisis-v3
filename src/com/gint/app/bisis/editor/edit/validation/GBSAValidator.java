package com.gint.app.bisis.editor.edit.validation;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.Messages;

public class GBSAValidator extends GBValidator {

  static {
    ValidatorManager.registerValidator(new GBSAValidator());
  }

  /** Proverava sintaksu podatka tipa Inventarni broj. */
  public boolean validInvNum(String content) throws Exception {
    if (content.length() < 11)
      throw new Exception("Minimalna du\u017eina inv. broja je 11 znakova!");
    for (int i = 0; i < content.length(); i++) {
      switch (content.charAt(i)) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case ':':
        case '-':
        case '/':
        case '.':
          break;
        default:
          throw new Exception(Messages.get("Pogre\u0161an znak u inv. broju: " 
              + content.charAt(i)));
      }
    }
    if (Environment.getWriteTs().checkInvNumber(content, Environment.getDocID()))
      throw new Exception(Messages.get("BISISAPP_GBVALIDATOR_INVEXISTS"));
    return true;
  }

}
