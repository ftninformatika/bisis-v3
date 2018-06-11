package com.gint.app.bisis.editor.edit;
/** Klasa koja cuva informaciju o tome koje sekundarno polje sadrzi koje potpolje.
  @author Milan Vidakovic i Branko Milosavljevic
  @version 4.0
*/
public class SecFieldInfo {
  public SecFieldInfo(String fieldName, String subfieldID, int subIndex, int index) {
    this.fieldName  = new String(fieldName);
    this.subfieldID = new String(subfieldID);
    this.subIndex = subIndex;
    this.index = index;
  }

  public SecFieldInfo(SecFieldInfo sfi) {
    this.fieldName  = new String(sfi.fieldName);
    this.subfieldID = new String(sfi.subfieldID);
    this.subIndex = sfi.subIndex;
    this.index = sfi.index;
  }

  /** Vraca string reprezentaciju ove klase. */
  public String toString() {
    return new String("Field: " + fieldName + "; subfield: " + subfieldID + "; subfield index: " + subIndex + "; index: " + index + "\n");
  }

  /** Ime sekundarnog polja (na primer 200) ili polja u kome se nalaze sekundarna polja (na primer 421). */
  String fieldName;
  /** Ime potpolja unutar sekundarnog polja (na primer a) ili potpolja samog polja (na primer 1). */
  String subfieldID;
  /** Redni broj potpolja unutar polja (koje sadrzi sekundarna polja). */
  int subIndex;
  /** Redni broj potpolja unutar sekundarnog polja.
   Ovo je -1 ako je u pitanju obicno potpolje (na primer 421<b>1</b>).
  */
  int index;
}

