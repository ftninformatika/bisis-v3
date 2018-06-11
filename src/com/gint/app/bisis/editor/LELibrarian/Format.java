package com.gint.app.bisis.editor.LELibrarian;

import java.util.*;

/** Ova klasa enkapsulira sve potrebne radnje vezane za
   format ispisa pogodaka. Postoje dve kategorije formata:
   <ul>
   <li>Predefinisani
   <li>Korisnicki zadati
   </ul>
   Predefinisani formati se ucitavaju iz okruzenja bibliotekara,
   a korisnicki definisani se definisu u toku rada aplikacije.
   <br>Oblik je sledeci:
  <pre>
   +---------+-------+-------+-------+----
   |naziv    |prefix1|prefix2|prefix3|...
   +---------+-------+-------+-------+----+
   |osnovni  |AU     | TI    |PY     |LA  |
   +---------+-------+-------+-------+----+----+----+----+
   |prosireni|AU     | TI    |PY     | PP | PU | IN | CN |
   +---------+-------+-------+-------+----+----+----+----+
   ...
  </pre>
*/
public class Format {
  /** Niz nizova sa spiskom predefinisanih formata.
   Oni se postavljaju prilikom citanja okruzenja bibliotekara.
  */
  private String[][] predefinisani =  new String[100][];

  /** Broj predefinisanih formata. */
  private int noOfPredefinisani;

  /** Niz nizova sa spiskom korisnicki definisanih formata.
   Oblik je isti kao kod predefinisanih formata.
  */
  private String[][] userDefined = new String[10][];
  /** Broj korisnicki definisanih formata. */
  private int noOfUserDefined = 0;

  /** Naziv tekuceg formata. */
  private String name = null;
  /** Rezultat postavljanja tekuceg formata.
  */
  private boolean res = false;

  /** Tekuci format. To je niz prefiksa koji su trenutno aktuelni. */
  public String[] radni = null;

  /** Broj formata. */
  private int count;

  public boolean first= true;
  private int hits;

  /** Vektor prefiksa. Koristi se za proveru ispravnosti formata.
  Svaki prefiks koji se zadaje u sklopu formata mora da se nalati u ovom vektoru.
  */
  private Vector prefixes = new Vector();

  /** Konstruktor koji postavi full format kao default format, svakako. */
  public Format() {
    predefinisani[0] = new String[2];
    predefinisani[0][0] = new String("full");
    predefinisani[0][1] = new String("full format");
    noOfPredefinisani = 1;
  }

  /** Postavlja tekuci format na prosledjeni.
   Na osnovu prosledjenog stringa moze da uradi sledece:
   <ol>
   <li> Postavi tekuci format na neki od predefinisanih ili korisnicki
        definisanih. Tada je <i>niz_ime</i> samo naziv formata.
   <li> Predefinise korisnicki definisan format.
        Tada se <i>niz_ime</i> sastoji iz naziva postojeceg formata, razmaka i
        liste prefiksa, razdvojenih zarezima.
   <li> Definise novi korisnicki format.
        Tada se <i>niz_ime</i> sastoji iz naziva novog formata, razmaka i
        liste prefiksa, razdvojenih zarezima.
   </ol>
  */
  public void postavi(String niz_ime) {
    // prvo potrazimo u listi predefinisanih formata
    for(int br = 0; br < noOfPredefinisani; br++) {
      if(niz_ime.equals(predefinisani[br][0])) {
        postavi(predefinisani[br]);
        res = true;
        return;
      }
    } // for
    // ako nismo nasli u predefinisanim, idemo kroz userDefined
    int index;
    res = false; // pesimisticka varijanta
    if((index = niz_ime.indexOf(" ")) != -1) {// moguca definicija
      String ime = new String(niz_ime.substring(0, index));
      String format = new String(niz_ime.substring(index+1));
      res = postavi(ime, format);
    } else { // nije definicija, vec postavljanje formata na neki postojeci
      for(int i = 0; i < noOfUserDefined; i++) {
        if(userDefined[i][0].equals(niz_ime)) {
          res = true;
          postavi(userDefined[i]);
          break;
        } // if
      } // for
    }
  }

  /** Postavlja tekuci format na prosledjeni.
   @param niz Niz stringova u kojima se nalaze prefiksi kojima se definise
              format.
  */
  private void postavi(String[] niz) {
    this.name = new String(niz[0]);
    int i;
    int length;
    radni = new String[niz.length];
    for (i = 1; i < niz.length; i++) {
        radni[i-1] = new String(niz[i]);
    } // for
    count = i-1;
  }

  /** Postavlja tekuci format na osnovu prosledjenog imena formata i stringa
    sa listom prefiksa, razdvojenih zarezima.
    @param name Ime formata
    @param s String sa definicijom formata.
    @return Vraca True ako je format ispravan.
  */
  public boolean postavi(String name, String s) {
    int i, index;
    boolean found = false;
    boolean greska = false;
    // Potrezimo format u predefinisanim (napunjenim iz baze)
    for(i = 0; i < noOfPredefinisani; i++) {
      if(predefinisani[i][0].equals(name)) {
        postavi(predefinisani[i]);
        return true;
      } // if
    } // for

    // Ako ga tamo nismo pronasli, potrazimo ga medju predefinisanim
    for(i = 0; i < noOfUserDefined; i++) {
      if(userDefined[i][0].equals(name)) {
        found = true;
        break;
      } // if
    } // for

    if(found == true) // Ako smo ga pronasli, jos uvek ga ne postavljamo,
      index = i;      // jer je mozda redefinicija formata, pa ga samo pripamtimo.
    else
    // Ako ga nismo ni medju predefinisanim pronasli, onda je to moguca definicija novog formata
      index = (noOfUserDefined < 10)?noOfUserDefined : 9;

    // Pronasao ili ne, treba postaviti format na zadatu vrednost.
    // Ako je pronasao, u tabeli userDefined[index] ce upisati redefiniciju formata (ako je ima)
    // Ako ga nije pronasao, u tabelu userDefined[index] ce uneti definiciju formata
    // pa ce na kraju, svakako postaviti format na zadati.
    StringTokenizer st;
    // promenljiva i sadrzi broj pronadjenih prefiksa
    i = 0;
    s = clean(s);
    st = new StringTokenizer(s, ",");
    while (st.hasMoreTokens()) {
       String pref = (new String (st.nextToken())).toUpperCase();
       if (prefixes.indexOf(pref) == -1) {
          i = 0;
          break;
       }
       //st.nextToken();
       i++;
    } // while
    if (i == 0) {  // ako je bilo koji prefiks neispravan, prijavimo gresku.
       return false;
    }
    else {
      userDefined[index] = new String[i+1]; // zauzmemo potreban broj mesta za prefikse
      userDefined[index][0] = new String(name);
      st = new StringTokenizer(s, ",");
      i = 1;
      while (st.hasMoreTokens())  {
        userDefined[index][i] = new String(st.nextToken());
        userDefined[index][i] = new String(userDefined[index][i].toUpperCase());
        i++;
      } // while
      count = i-1;
      postavi(userDefined[index]);
      if(found == false) {// posto ga nije pronasao, morao ga je staviti u listu predefinisanih,
        if(index < 10)   // pa azurirajmo brojac predefinisanih formata
          noOfUserDefined = ++index;
        else
          noOfUserDefined = 10;
      }
      return true;
    }
  }

  /** Cisti prosledjeni string od razmaka. */
  String clean(String str) {
    int i = 0;
    while((i = str.indexOf(" ")) != -1) {
      str = new String(str.substring(0, i)) + new String(str.substring(i+1));
    }
    return str;
  }

  /** Vraca prefiks sa zadate pozicije.
   @param i Pozicija sa koje zelimo da dobijemo prefiks.
  */
  public String getPrefixAt(int i) {
    String s;
    try {
      s = new String(radni[i]);
    }
    catch (Exception ex) {
      s = new String("AU");
    }
    return s;
  }

  /** Vraca rezultat postavljanja formata. */
  public boolean getRes() {
    return res;
  }

  /** postavlja rezultat postavljanja formata */
  public void setRes(boolean res) {
    this.res = res;
  }

  /** Vraca naziv tekuceg formata. */
  public String getName() {
    return new String(name);
  }

  /** Postavlja predefinisane formate sukcesivnim pozivom ove metode.
      Poziva se iz LELibrariana, prilikom inicijalizacije, kada se ucitavaju formati za
      tekuceg bibliotekara.
   @param ime Naziv formata
   @param format Vektor stringova sa nazivima prefiksa za ovaj format.
  */
  void setPredefinisani(String ime, Vector format) {
    predefinisani[noOfPredefinisani] = new String[format.size()+1];
    predefinisani[noOfPredefinisani][0] = new String(ime);
    this.name = new String(ime);
//    res = new String(ime);
    for (int i = 0; i < format.size(); i++) {
      predefinisani[noOfPredefinisani][i+1] = new String((String)format.elementAt(i));
    }
    postavi(predefinisani[noOfPredefinisani]);
    noOfPredefinisani++;
  }

  /** Postavlja vektor svih prefiksa. Poziva se iz LELibrarian-a prilikom inicijalizacije,
      a koristi se za utvrdjivanje ispravnosti formata, tj. za kontorlu ispravnosti
      zadavanja prefiksa unutar formata.
  */
  public void setPrefixes(Vector p) {
    prefixes = p;
  }

  /** Vraca string sa spiskom prefiksa iz tekuceg formata,
   razdvojene zarezom.
  */
  public String getPrefixesFromCurrentFormat() {
    String tmp = "";
    for(int i = 0; i < count; i++)
      tmp = new String(tmp + radni[i] + ((i < count-1)?", ":""));
    return tmp;
  }

  /** Vraca string sa spiskom prefiksa iz prosledjenog formata,
   razdvojene zarezom.
  */
  private String getPrefixesFromCurrentFormat(String[] format) {
    String tmp = "";
    for(int i = 1; i < format.length; i++)
      tmp = new String(tmp + format[i] + ((i < format.length-1)?", ":""));
    return tmp;
  }

  /** Vraca vektor sa stringovima u kojima se nalazi spisak svih formata
   (predefinisani i korisnicki definisani)
  */
  public Vector getAllFormats() {
    Vector retValue = new Vector();
    int i;
    for(i = 0; i < noOfPredefinisani; i++)
      retValue.addElement(new String(predefinisani[i][0] + "(" +getPrefixesFromCurrentFormat(predefinisani[i]) + ")"));
    for(; i < (noOfPredefinisani + noOfUserDefined); i++)
      retValue.addElement(new String(userDefined[i-noOfPredefinisani][0] + "(" + getPrefixesFromCurrentFormat(userDefined[i-noOfPredefinisani])+ ")"));
    return retValue;
  }


  /** Vraca broj formata. */
  public int getCount() {
    return count;
  }

  /** Vraca broj predefinisanih formata. */
  public int getPredefinedCount() {
    return noOfPredefinisani;
  }

  /** Vraca broj korisnicki definisanih formata. */
  public int getUserDefinedCount() {
    return noOfUserDefined;
  }

}

