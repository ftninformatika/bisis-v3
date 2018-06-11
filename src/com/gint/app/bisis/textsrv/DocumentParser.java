package com.gint.app.bisis.textsrv;

import java.util.*;

/** DocumentParser klasa je namenjena za konverziju dokumenta
  * iz UNIMARC formata u skup prefiksa koji se koriste za
  * pretragu. Preslikavanje polja i potpolja UNIMARC formata
  * je definisano tabelom <CODE>Prefix_map</CODE>. Podaci iz
  * ove tabele se ucitavaju samo jednom, tokom konstrukcije
  * objekta, tako da se objekat klase DocumentParser kreira
  * samo jednom, za celu aplikaciju.
  * @author Branko Milosavljevic
  * @author mbranko@uns.ns.ac.yu
  * @author Milan Vidakovic
  * @version 1.0
  */
public class DocumentParser {

  /** Konstruise objekat klase DocumentParser.
    * @param prefixMap HashMap-a parova prefiks-polje.
    * @param fieldDelimiter String koji sadrzi delimitere polja UNIMARC zapisa
    * @param subfieldDelimiter String koji sadrzi delimitere potpolja UNIMARC zapisa
    */
  public DocumentParser(com.objectspace.jgl.HashMap prefixMap, String fieldDelimiter, String subfieldDelimiter) {
    this.fieldDelimiter = fieldDelimiter;
    this.subfieldDelimiter = subfieldDelimiter;
    this.prefixMap = prefixMap;
    //converter = new Converter();
  }

  /** Konvertuje dokument u UNIMARC formatu u prefikse.
    * @param doc dokument koji se konvertuje
    * @return Vector PrefixPair objekata koji sadrze parove (prefixName, value)
    */
  public Vector parseDocument(String doc) {

    StringTokenizer fieldTokenizer;      // tokenizer polja
    StringTokenizer subfieldTokenizer;   // tokenizer potpolja (unutar polja)
    String fieldPiece;                   // jedno polje izdvojeno iz rezanca (tokenizerom)
    String fieldID;                      // identifikator polja izdvojen iz fieldPiece
    String indicator;                    // indikator za polja izdvojen iz fieldPiece
    String subfieldPiece;                // jedno potpolje izdvojeno iz fieldPiece (tokenizerom)
    String subfieldID;                   // identifikator potpolja izdvojen iz subfieldPiece
    String content;                      // sadrzaj potpolja izdvojen iz subfieldPiece
    boolean pubTypeS1 = false;           // indikator da li je tip publikacije S1
    boolean pubTypeNO = false;           // indikator da li je tip publikacije NO

    AUString AUstring = new AUString("", false);

    Vector retVal = new Vector();
    Vector tempVec;
    Enumeration e;

    // rastavi rezanac na polja i obradi svako polje u while petlji
    fieldTokenizer = new StringTokenizer(doc, fieldDelimiter);
    while (fieldTokenizer.hasMoreTokens()) {
      fieldPiece = new String(fieldTokenizer.nextToken());
      fieldID    = new String(fieldPiece.substring(0, 3));
      indicator  = new String(fieldPiece.substring(3, 5));

      if (!AUstring.isEmpty()) {
        retVal.addElement(new PrefixPair("AU", AUstring.getContent()));
        AUstring.setContent("");
        AUstring.setFinished(false);
      }

      // polja 421, 423, 464 i 469 su sekundarna polja
      if ((fieldID.equals("421") || fieldID.equals("423") || fieldID.equals("464") || fieldID.equals("469")) && !pubTypeS1 && !pubTypeNO) {
        String temp = new String(fieldPiece.substring(7));
        while (temp != null) {
          int sepPos = temp.indexOf("\37"+"1");
          if (sepPos == -1) {
            tempVec = parseDocument(temp);
            temp = null;
          } else {
            tempVec = parseDocument(temp.substring(0, sepPos));
            temp = new String(temp.substring(sepPos + 2));
          }
          for(int i=0; i<tempVec.size(); i++)
            retVal.addElement(tempVec.elementAt(i));
          tempVec.removeAllElements();
        }

      // obrada normalnih potpolja
      } else {
        subfieldTokenizer = new StringTokenizer(fieldPiece, subfieldDelimiter);
        subfieldTokenizer.nextToken(); // preskoci header polja, npr. "200__"
        while (subfieldTokenizer.hasMoreTokens()) {
          subfieldPiece = new String(subfieldTokenizer.nextToken());
          subfieldID    = new String(subfieldPiece.substring(0, 1));
          content       = new String(subfieldPiece.substring(1));  // do kraja stringa

          //// ako je potpolje 000a onda treba proveriti da li je S1 ili NO tip publikacije
          if ((fieldID + subfieldID).equals("000a")) {
            pubTypeS1 = content.substring(0, 3).equals("004");
            pubTypeNO = content.substring(0, 3).equals("007");
          }

          // AU prefiks zahteva poseban tretman: potpolja 70[0-9][a|b] i 90[0-9][a|b]
          if ((fieldID.substring(0,2).equals("70") || fieldID.substring(0,2).equals("90")) &&
              (subfieldID.equals("a") || subfieldID.equals("b"))) {
            AUFix(fieldID, indicator, subfieldID, content, AUstring);
            if (AUstring.getFinished()) {
              content = new String(AUstring.getContent());
              AUstring.setContent("");
              e = prefixMap.values(new String(fieldID+subfieldID));
              while (e.hasMoreElements())
                if (!content.equals(""))
                  retVal.addElement(new PrefixPair((String)(e.nextElement()), content));
                else
                  e.nextElement();
            }
          } else { // normalno (ne-AU) potpolje
            e = prefixMap.values(new String(fieldID+subfieldID));
            while (e.hasMoreElements())
              if (!content.equals(""))
                retVal.addElement(new PrefixPair((String)(e.nextElement()), content));
              else
                e.nextElement();
          }
        }
      }
    }
    return retVal;
  }

  /** Metoda za kumulativno sastavljanje sadrzaja prefiksa AU. Kada
    * je nakon izvrsavanja ove metode vrednost AUstring.getFinished() == true,
    * sadrzaj prefiksa je konacno formiran i moze se dodati u listu gotovih
    * prefiksa.
    * @param fieldID identifikator polja (tri karaktera, npr "700")
    * @param indicator sadrzaj prvog i drugog indikatora (dva karaktera, npr. " 1")
    * @param subfieldID identifikator potpolja (jedan karakter, npr. "a")
    * @param content sadrzaj potpolja koji se obradjuje
    * @param AUstring handle na objekat u kojem se vrsi akumulacija za sastavljanje prefiksa
    */
  private void AUFix(String fieldID, String indicator, String subfieldID, String content, AUString AUstring) {
    char ind; // vrednost drugog indikatora

    ind = indicator.charAt(1);
    AUstring.setFinished(false);

    if (fieldID.substring(0,2).equals("90")) {
      if (ind == '0' || ind == '1' || ind == '2') {
        if (subfieldID.equals("a")) {
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentAfter(content);
            AUstring.setFinished(true);
            return;
          }
        } else { // potpolje b
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentBefore(content);
            AUstring.setFinished(true);
            return;
          }
        }
      } else { // indikator je 3, 4 ili 5
        if (subfieldID.equals("a")) {
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentBefore(content);
            AUstring.setFinished(true);
            return;
          }
        } else { // potpolje b
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentAfter(content);
            AUstring.setFinished(true);
            return;
          }
        }
      }
    } else { // u pitanju su polja 70x
      if (ind == '1') {
        if (subfieldID.equals("a")) {
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentBefore(content);
            AUstring.setFinished(true);
            return;
          }
        } else { // potpolje b
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentAfter(content);
            AUstring.setFinished(true);
            return;
          }
        }
      } else { // indikator je 0
        if (subfieldID.equals("a")) {
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentAfter(content);
            AUstring.setFinished(true);
            return;
          }
        } else { // potpolje b
          if (AUstring.isEmpty()) {
            AUstring.setContent(content);
            AUstring.setFinished(false);
            return;
          } else {
            AUstring.addContentBefore(content);
            AUstring.setFinished(true);
            return;
          }
        }
      }
    }
  }

  /** Klasa AUString se koristi za kumulativno sastavljanje sadrzaja prefiksa AU.
    * Sadrzaj prefiksa AU se sastavlja iz vise potpolja. Zato je potrebno pamtiti
    * tekucu vrednost sadrzaja (string) i status da li je konkatenacija zavrsena ili
    * nije.
    */
  private class AUString {
    public AUString(String content, boolean finished) {
      this.content = content;
      this.finished = finished;
    }
    public void setContent(String content) {
      this.content = content;
    }
    public void setFinished(boolean finished) {
      this.finished = finished;
    }
    public String getContent() {
      return content;
    }
    public boolean getFinished() {
      return finished;
    }
    public boolean isEmpty() {
      return content.equals("");
    }
    public void addContentAfter(String content) {
      this.content = this.content + "\34" + ", " + content;
    }
    public void addContentBefore(String content) {
      this.content = content + "\34" + ", " + this.content;
    }
    private String content;
    private boolean finished;
  }

  /* com.objectspace.jgl.HashMap objekat koji cuva preslikavanje potpolja UNIMARC-a na prefikse. */
  private com.objectspace.jgl.HashMap prefixMap;

  /** String koji sadrzi delimitere polja u UNIMARC zapisu */
  private String fieldDelimiter;

  /** String koji sadrzi delimitere potpolja u UNIMARC zapisu */
  private String subfieldDelimiter;

  /** Objekat za konverziju stringova */
  //private Converter converter;
}
