package com.gint.app.bisis.editor.LELibrarian;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.sql.*;

import com.objectspace.jgl.*;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

import com.gint.util.string.Charset;
import com.gint.util.xml.XMLUtils;

/** Klasa koja kupi iz baze podatke o tekucem bibliotekaru, tipa publikacije,
    nivou obrade, nivou obveznosti, dodeljenim prefiksima.
    Ova klasa kupi i spisak svih prefiksa sa opisom.
*/
public class LELibrarian {

  /** Konstruktor. Prima konekciju, Oracle username, Oracle pasword i bibliotekarov username.
    Postavlja trenutni kontekst na default.
    @exception Exception Baca izuzetak ako iz bilo kog razloga pukne veza sa bazom (nema bibliotekara ili nekakva nekonzistentnost).
  */
  public LELibrarian(Connection conn, String libName, String libPwd) throws Exception {

    // pokupi podatke o bibliotekaru
    String query = new String("select bibtip, bibnobr, bibnobav, bibtajozn from bibliotekar where bibid = '" + libName + "'");
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery(query);
    if (rset.next()) {
      // nadjen bibliotekar
      // !@#$
      defPubType = new String(Charset.YUSCII2Unicode(rset.getString(1).trim()));
      defProcLevel = rset.getInt(2);
      defManLevel  = rset.getInt(3);
      this.libName = libName;
      if (!libPwd.equals(Charset.YUSCII2Unicode(rset.getString(4).trim())))
        throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_LELIBRARIAN_WRONGPASS"));
    }
    else {
      // nema tog bibliotekara
      throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_LELIBRARIAN_NONEXISTINGLIBRARIAN"));
    }
    rset.close();
    stmt.close();

    initContextList(conn);
    setCurrContext(defPubType, defProcLevel, defManLevel);
    getPrefixes(conn);
    getPrefixNames(conn);
    getFormats(conn);
    formats.setPrefixes(allPrefixes);
    setDefaultFormat(conn);
  }

  /** Konstruktor. Nakon sto podesi okruzenje bibliotekara, postavlja trenutni kontekst na default.
      @param doc XML dokument sa opisom okruzenja bibliotekara.
    */
  public LELibrarian(Document doc) throws Exception {
    Node nDefPubType = XMLUtils.getNodeByName(doc, "defPubType");
    if (nDefPubType != null)
      defPubType = nDefPubType.getFirstChild().getNodeValue();
    else
      throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_LELIBRARIAN_NONEXISTINGDEFPUBTYPE"));

    Node nDefProcLevel = XMLUtils.getNodeByName(doc, "defProcLevel");
    if (nDefProcLevel != null)
      defProcLevel = Integer.parseInt(nDefProcLevel.getFirstChild().getNodeValue());
    else
      throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_LELIBRARIAN_NONEXISTINGDEFPROCLEVEL"));

    Node nDefManLevel = XMLUtils.getNodeByName(doc, "defManLevel");
    if (nDefManLevel != null)
      defManLevel  = Integer.parseInt(nDefManLevel.getFirstChild().getNodeValue());
    else
      throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_LELIBRARIAN_NONEXISTINGDEFMANLEVEL"));

    initContextList(doc);
    setCurrContext(defPubType, defProcLevel, defManLevel);
    getPrefixes(doc);
    getPrefixNames(doc);
    getFormats(doc);
    formats.setPrefixes(allPrefixes);
    setDefaultFormat(doc);
  }

  /** Popunjava vektor <i>prefixes</i> listom prefiksa koji su dodeljeni bibliotekaru.
    U pitanju su onih pet prefiksa za pretrazivanje u MultiLineState.
  */
  public void getPrefixes(Connection conn) {
    try {
      String query = new String(
        "select ppr_poljebprid from poljebpr_tip_obrade where to_bib_bibid = '"
        + libName + "' and to_nov_nob_tp_tipid = '" + getPubType()
        + "' and to_nov_nob_nobrid = " + getProcLevel()
        + " and to_nov_nobavid = " + getManLevel());
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      while (rset.next())
      // !@#$
        prefixes.addElement(Charset.YUSCII2Unicode(rset.getString(1).trim()));
      rset.close();
      stmt.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Popunjava vektor <i>prefixes</i> listom prefiksa koji su dodeljeni bibliotekaru.
    U pitanju su onih pet prefiksa za pretrazivanje u MultiLineState.
  */
  public void getPrefixes(Document doc) {
    Node prfs = XMLUtils.getNodeByName(doc, "prefixes");
    if (prfs != null) {
      NodeList prf = prfs.getChildNodes();
      Node currPrf;
      NamedNodeMap attrs;
      for (int i = 0; i < prf.getLength(); i++)  {
        currPrf = prf.item(i);
        attrs = currPrf.getAttributes();
        prefixes.addElement(attrs.getNamedItem("name").getNodeValue());
      }
    }
  }

  /** Vraca vektor <i>prefixes</i> sa listom prefiksa koji su dodeljeni bibliotekaru.
    U pitanju su onih pet prefiksa za pretrazivanje u MultiLineState.
  */
  public Vector getPrefixes() {
    return prefixes;
  }

  /** Popunjava vektor <i> prefixNames</i> listom stringova
    "prefix - opis prefiksa".
  */
  public void getPrefixNames(Connection conn) {
    try {
      String query = new String(
        "select poljebprid, pbpr_naziv from poljebpr order by poljebprid");
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      while (rset.next()) {
      // !@#$
        String pref = Charset.YUSCII2Unicode(rset.getString(1).trim());
        prefixNames.addElement(pref + " - " + Charset.YUSCII2Unicode(rset.getString(2)));
        // napunimo vektor svih prefiksa
        allPrefixes.addElement(pref);
      }
      rset.close();
      stmt.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Popunjava vektor <i> prefixNames</i> listom stringova
    "prefix - opis prefiksa".
  */
  public void getPrefixNames(Document doc) {
    Node prf = XMLUtils.getNodeByName(doc, "prefixNames");
    if (prf != null) {
      NodeList names = prf.getChildNodes();
      Node prefix;
      String prefixName, prefixDesc;
      for (int i = 0; i < names.getLength(); i++) {
        prefix = names.item(i);
        prefixName = prefix.getAttributes().getNamedItem("name").getNodeValue();
        prefixDesc = prefix.getAttributes().getNamedItem("desc").getNodeValue();
        prefixNames.addElement(prefixName + " - " + prefixDesc);
        // napunimo vektor svih prefiksa
        allPrefixes.addElement(prefixName);
      }
    }
  }

  /** Popunjava objekat <i>formats</i> spiskom svih formata
    koji su na raspolaganju.
  */
  public void getFormats(Connection conn) {
    try {
      String query = "select for_formatid, ppr_poljebprid from formatf_poljebpr";
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      HashMap hm = new HashMap();
      Vector vec = new Vector();
      // prvo napunimo hashmapu i vektor svim parovima (format, prefix)
      while (rset.next()) {
      // !@#$
        String format = Charset.YUSCII2Unicode(rset.getString(1)).trim();
        String prefix = Charset.YUSCII2Unicode(rset.getString(2)).trim();
        hm.add(format, format);
        FormatPrefix fp = new FormatPrefix(format, prefix);
        vec.addElement(fp);
      }
      rset.close();
      stmt.close();
      Enumeration e = hm.elements();
      while (e.hasMoreElements()) {
        String formatName = (String)e.nextElement();
        if (!formatName.toLowerCase().equals("full")) { // ako nije full
          Vector prefVec = new Vector();
          for(int i = 0; i < vec.size(); i++) {
            FormatPrefix fp = (FormatPrefix)vec.elementAt(i);
            if (fp.format.equals(formatName)) {
              prefVec.addElement(new String(fp.prefix));
            }
          } // for
          formats.setPredefinisani(formatName, prefVec);
        } // if
      } // while
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Popunjava objekat <i>formats</i> spiskom svih formata
    koji su na raspolaganju.
  */
  public void getFormats(Document doc) {
    Node prf = XMLUtils.getNodeByName(doc, "formats");
    if (prf != null) {
      NodeList predefinisani = prf.getChildNodes();
      Node curr;
      String formatName, formatPrefixes, fp;
      StringTokenizer st;
      for (int i = 0; i < predefinisani.getLength(); i++) {
        curr = predefinisani.item(i);
        formatName = curr.getAttributes().getNamedItem("naziv").getNodeValue();
        formatPrefixes = curr.getFirstChild().getNodeValue();

        // tokeniziramo po zarezu, posto je formatPrefixes lista prefiksa oblika:
        // AU, TI, PY
        if (!formatName.toLowerCase().equals("full")) { // ako nije full
          Vector prefVec = new Vector();
          st = new StringTokenizer(formatPrefixes, ",");
          while (st.hasMoreTokens()) {
            fp = st.nextToken().trim();
            prefVec.addElement(new String(fp));
          } // for
          formats.setPredefinisani(formatName, prefVec);
        } // if
      }
    }
  }

  /** Postavlja default format za bibliotekara na osnovu default tipa
   *  publikacije, nivoa obrade i nivoa obaveznosti koji su ranije
   *  odredjeni. Ako u bazi nema nista o tome, onda nista i ne dira.
   */
  public void setDefaultFormat(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT for_formatid FROM tip_obrade WHERE bib_bibid='"
        +getLibName()+"' and nov_nob_tp_tipid='"+getPubType()+"' and nov_nob_nobrid="
        +getProcLevel()+" and nov_nobavid="+getManLevel());
      if (rset.next())
        defaultFormat = rset.getString(1);
      rset.close();
      stmt.close();
      if (defaultFormat == null)
        return;
      defaultFormat = defaultFormat.trim();
      if (defaultFormat.toLowerCase().equals("full"))
        defaultFormat = "full";
      getFormat().postavi(defaultFormat);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /** Postavlja default format za bibliotekara na osnovu default tipa
   *  publikacije, nivoa obrade i nivoa obaveznosti koji su ranije
   *  odredjeni.
   */
  public void setDefaultFormat(Document doc) {
    Node defFormat = XMLUtils.getNodeByName(doc, "defaultFormat");
    defaultFormat = defFormat.getFirstChild().getNodeValue();
    if (defaultFormat.toLowerCase().equals("full"))
      defaultFormat = "full";
    getFormat().postavi(defaultFormat);
  }

  /** Inicira listu konteksta na osnovu imena bibliotekara.
   Svaki kontekst je uredjena trojka
     (tip publikacije, nivo obrade, nivo obaveznosti).
   Jedan bibliotekar moze da ima vise ovakvih konteksta, i svi se nalaze
   u listi <i>contextList</i>.
   Kontekst jos sadrzi i listu tipova potpolja (<i>typeSubfields</i>),
   listu obaveznih potpolja (<i>manSubfields</i>), kao i
   listu potpolja koja su obavezna za zadati nivo obrade (<i>procFields</i>).
   Svaki kontekst je jedan objekat klase LEContext.
   <pre>
     =========== contextList ============
               +================+
               | pubType        |
               +----------------+
     LEContext | procLevel      |
               +----------------+
               | manLevel       |
               +----------------+      +------------+
               | procSubfields  | ---> |            | --> ...
               +----------------+      +------------+
               | manSubfields   | ---> ...
               +----------------+
               | typeSubfields  | ---> ...
               +================+
               ...
   </pre>
  */
  public void initContextList(Connection conn) {
    try {

      // pokupi sva DOPUSTENA potpolja za sve kontekste datog bibliotekara
                             //        1             2
      String bfQuery = "select ppo_po_poljeid, ppo_potpoljaid, "
                        //     3            4
                       + "nob_tp_tipid, nob_nobrid from "
                       + "nivo_obrade_potpolja where (nob_tp_tipid, nob_nobrid) "
                       + "in (select nov_nob_tp_tipid, nov_nob_nobrid from "
                       + "tip_obrade where bib_bibid = '" + libName + "')";
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(bfQuery);
      //      allFields       allSubfields    tipPub     nivoObr
      // [0]    100                 a           M1         10
      // [1]    100                 b
      // [2]    101                 b
      // [3]    101                 c
      Vector allFields = new Vector();
      Vector allSubfields = new Vector();
      Vector tipPub = new Vector();
      Vector nivoObr = new Vector();
      while (rset.next()) {
      // !@#$
        allFields.addElement(Charset.YUSCII2Unicode(rset.getString(1).trim()));
        allSubfields.addElement(Charset.YUSCII2Unicode(rset.getString(2).trim()));
        tipPub.addElement(Charset.YUSCII2Unicode(rset.getString(3).trim()));
        nivoObr.addElement(new Integer(rset.getInt(4)));
      }
      rset.close();
      stmt.close();

      // pokupi sva OBAVEZNA potpolja za sve kontekste datog bibliotekara
                    //        1                2
      bfQuery = "select ppo_po_poljeid, ppo_potpoljaid, "
                       //    3                  4               5
                       + "nov_nob_tp_tipid, nov_nob_nobrid, nov_nobavid from "
                       + "nivo_obaveznosti_potpolja where (nov_nob_tp_tipid, "
                       + "nov_nob_nobrid, nov_nobavid) "
                       + "in (select nov_nob_tp_tipid, nov_nob_nobrid, "
                       + "nov_nobavid from "
                       + "tip_obrade where bib_bibid = '" + libName + "')";
      stmt = conn.createStatement();
      rset = stmt.executeQuery(bfQuery);
      //      allFields2       allSubfields2    tipPub2     nivoObr2   nivoObav2
      // [0]    100                 a             M1          10           1
      // [1]    100                 b
      // [2]    101                 b
      // [3]    101                 c
      Vector allFields2 = new Vector();
      Vector allSubfields2 = new Vector();
      Vector tipPub2 = new Vector();
      Vector nivoObr2 = new Vector();
      Vector nivoObav2 = new Vector();
      while (rset.next()) {
      // !@#$
        allFields2.addElement(Charset.YUSCII2Unicode(rset.getString(1).trim()));
        allSubfields2.addElement(Charset.YUSCII2Unicode(rset.getString(2).trim()));
        tipPub2.addElement(Charset.YUSCII2Unicode(rset.getString(3).trim()));
        nivoObr2.addElement(new Integer(rset.getInt(4)));
        nivoObav2.addElement(new Integer(rset.getInt(5)));
      }
      rset.close();
      stmt.close();

      //
      // pokupi sve default vrednosti potpolja za tip publikacije
      //                                    1                2
      String defValQuery = "select ppo_potpoljaid, ppo_po_poljeid, "
                        //        3        4
                           + "tp_tipid, defvr_tipa from def_tipa order by tp_tipid, ppo_po_poljeid, ppo_potpoljaid";
      HashMap defValMap = new HashMap();
      stmt = conn.createStatement();
      rset = stmt.executeQuery(defValQuery);
      Vector allFields3 = new Vector();
      Vector allSubfields3 = new Vector();
      Vector allPubTypes3 = new Vector();
      while (rset.next()) {
      // !@#$
        String field = Charset.YUSCII2Unicode(rset.getString(2).trim());
        String subfield = Charset.YUSCII2Unicode(rset.getString(1).trim());
        String pType = Charset.YUSCII2Unicode(rset.getString(3).trim());
        String dVal = rset.getString(4);
        // @#$ Korekcija, jer je promenjeno da dVal moze biti null
        // ja cu staviti u tom slucajeu da je dVal="" (prazan string)
        if (dVal == null)
          dVal = new String("");
        dVal = Charset.YUSCII2Unicode(dVal);
        allFields3.addElement(field);
        allSubfields3.addElement(subfield);
        allPubTypes3.addElement(pType);
        defValMap.add(field + subfield + pType, dVal);
      }
      rset.close();
      stmt.close();

      //
      // pokupi sve default vrednosti potpolja za bibliotekara i tip publikacije
      // (ove default vrednosti imaju veci prioritet od prethodnih, ako postoje)
      //                                    1               2
      String tipBiblQuery = "select ppo_potpoljaid, ppo_po_poljeid, "
                           //     3           4
                           + "tp_tipid, defvr_tipbib from tipbibliotekara "
                           + "where bib_bibid = '" + libName + "'";
      HashMap tipBiblMap = new HashMap();
      stmt = conn.createStatement();
      rset = stmt.executeQuery(tipBiblQuery);
      while (rset.next()) {
      // !@#$
        tipBiblMap.add(Charset.YUSCII2Unicode(rset.getString(2).trim()) +
                      Charset.YUSCII2Unicode(rset.getString(1).trim()) +
                      Charset.YUSCII2Unicode(rset.getString(3).trim()),
                      Charset.YUSCII2Unicode(rset.getString(4)));
      }
      rset.close();
      stmt.close();

      //
      // Na osnovu pokupljenih podataka o svim kontekstima za svaki kontekst
      // konstruisemo objekat sa vektorima dopustenih i obaveznih potpolja
      //                              1               2               3
      String query1 = "select nov_nob_tp_tipid, nov_nob_nobrid, nov_nobavid "
                       + " from tip_obrade where bib_bibid = '" + libName + "'";
      Statement stmt1 = conn.createStatement();
      ResultSet rset1 = stmt1.executeQuery(query1);
      while (rset1.next()) {
      // !@#$
        LEContext lc = new LEContext(Charset.YUSCII2Unicode(rset1.getString(1).trim()),
                                     rset1.getInt(2), rset1.getInt(3));
        for(int i = 0; i < allFields.size(); i++) {
          if (((String)tipPub.elementAt(i)).equals(lc.getPubType())
               && ((Integer)nivoObr.elementAt(i)).intValue() == lc.getProcLevel()) {
            String defValue = null;
            String defTipa = (String)defValMap.get((String)allFields.elementAt(i)+(String)allSubfields.elementAt(i)+lc.getPubType());
            String defBibl = (String)tipBiblMap.get((String)allFields.elementAt(i)+(String)allSubfields.elementAt(i)+lc.getPubType());
            if (defBibl != null)
              defValue = new String(defBibl);
            else if (defTipa != null)
              defValue = new String(defTipa);
            lc.addProcSubfield((String)allFields.elementAt(i), (String)allSubfields.elementAt(i), defValue);
          }
        }
        for(int i = 0; i < allFields2.size(); i++) {
          if (((String)tipPub2.elementAt(i)).equals(lc.getPubType())
               && ((Integer)nivoObr2.elementAt(i)).intValue() == lc.getProcLevel()
               && ((Integer)nivoObav2.elementAt(i)).intValue() == lc.getManLevel()) {
            String defValue = null;
            String defTipa = (String)defValMap.get((String)allFields2.elementAt(i)+(String)allSubfields2.elementAt(i)+lc.getPubType());
            String defBibl = (String)tipBiblMap.get((String)allFields2.elementAt(i)+(String)allSubfields2.elementAt(i)+lc.getPubType());
            if (defBibl != null)
              defValue = new String(defBibl);
            else if (defTipa != null)
              defValue = new String(defTipa);
            lc.addManSubfield((String)allFields2.elementAt(i), (String)allSubfields2.elementAt(i), defValue);
          }
        }
        for(int i = 0; i < allFields3.size(); i++) {
          if (((String)allPubTypes3.elementAt(i)).equals(lc.getPubType())) {
            String defValue = null;
            String defTipa = (String)defValMap.get((String)allFields3.elementAt(i)+(String)allSubfields3.elementAt(i)+lc.getPubType());
            String defBibl = (String)tipBiblMap.get((String)allFields3.elementAt(i)+(String)allSubfields3.elementAt(i)+lc.getPubType());
            if (defBibl != null)
              defValue = new String(defBibl);
            else if (defTipa != null)
              defValue = new String(defTipa);
            lc.addTypeSubfield((String)allFields3.elementAt(i), (String)allSubfields3.elementAt(i), defValue);
          }
        }
        contextList.addElement(lc);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Inicira listu konteksta na osnovu imena bibliotekara.
    *  Svaki kontekst je uredjena trojka
    * (tip publikacije, nivo obrade, nivo obaveznosti).
    * Jedan bibliotekar moze da ima vise ovakvih konteksta, i svi se nalaze
    * u listi <i>contextList</i>.
    * Kontekst jos sadrzi i listu tipova potpolja (<i>typeSubfields</i>),
    * listu obaveznih potpolja (<i>manSubfields</i>), kao i
    * listu potpolja koja su obavezna za zadati nivo obrade (<i>procFields</i>).
    * Svaki kontekst je jedan objekat klase LEContext.
    * <pre>
    *   =========== contextList ============
    *             +================+
    *             | pubType        |
    *             +----------------+
    *   LEContext | procLevel      |
    *             +----------------+
    *             | manLevel       |
    *             +----------------+      +------------+
    *             | procSubfields  | ---> |            | --> ...
    *             +----------------+      +------------+
    *             | manSubfields   | ---> ...
    *             +----------------+
    *             | typeSubfields  | ---> ...
    *             +================+
    *             ...
    * </pre>
    */
  public void initContextList(Document doc) {
    Node initContextList = XMLUtils.getNodeByName(doc, "contextList");
    if (initContextList != null) {
      NodeList contexts = initContextList.getChildNodes();
      Node curr;
      LEContext context;
      for (int i = 0; i < contexts.getLength(); i++) {
        curr = contexts.item(i);
        context = new LEContext(curr);
        contextList.addElement(context);
      }
    }
  }

  /** Postavlja tekuci kontekst iz liste konteksta na osnovu prosledjenog
    tipa publikacije, nivoa obrade i nivoa obaveznosti.
  */
  public void setCurrContext(String pubType, int procLevel, int manLevel) {
    for(int i = 0; i < contextList.size(); i++)
      if (((LEContext)contextList.elementAt(i)).equals(pubType, procLevel, manLevel)) {
        currContext = i;
        break;
      }
  }

  /** Proverava postojanje potpolja u listi potpolja koja su obavezna za
    zadati tip publikacije i nivo obrade.
  */
  public boolean checkProcSubfield(String field, String subfield) {
    return ((LEContext)contextList.elementAt(currContext)).checkProcSubfield(field, subfield);
  }

  /** Proverava postojanje potpolja u listi potpolja koja su obavezna za
    zadati tip publikacije, nivo obrade i nivo obaveznosti.
  */
  public boolean checkManSubfield(String field, String subfield) {
    return ((LEContext)contextList.elementAt(currContext)).checkManSubfield(field, subfield);
  }

  /** Vraca trenutni tip publikacije. */
  public String getPubType() {
    return ((LEContext)contextList.elementAt(currContext)).getPubType();
  }
  /** Vraca trenutni nivo obrade. */
  public int getProcLevel() {
    return ((LEContext)contextList.elementAt(currContext)).getProcLevel();
  }
  /** Vraca trenutni nivo obaveznosti. */
  public int getManLevel() {
    return ((LEContext)contextList.elementAt(currContext)).getManLevel();
  }

  public String getLibName() {
     return libName;
  }

  /** Pronalazi da li postoji nivo obrade za uredjenu trojku (tip publikacije, nivo obrade i nivo obaveznosti)
      unutar liste konteksta. Vraca -1 ako nije nasao.
  */
  public int findProcLevel(String pubType, int procLevel, int manLevel) {
    boolean nasao = false;
    int index = 0;
    for (int i = 0; i < contextList.size(); i++) {
      if (!nasao && ((LEContext)contextList.elementAt(i)).getPubType().equals(pubType)) {
         index = i;
         nasao = true;
      }
      if (((LEContext)contextList.elementAt(i)).equals(pubType, procLevel, manLevel))
        return procLevel;
    }
    if (nasao)
        return ((LEContext)contextList.elementAt(index)).getProcLevel();
    else
        return -1;
  }

  /** Vraca Default vrednost za zadato potpolje.
    Ako vrati null, mora se pogledati u Unimarc (UFieldSet).
  */
  public String getDefValue(String field, String subfield) {
    LEContext lc = (LEContext)(contextList.elementAt(currContext));
    return lc.getDefValue(field, subfield);
  }

  /** Vraca vektor polja i potpolja za zadati tip publikacije tekuceg konteksta. */
  public Vector getTypeLESubfields() {
    return ((LEContext)contextList.elementAt(currContext)).getTypeSubfields();
  }

  /** Vraca boolean da li je dato polje u tipu publikacije tekuceg konteksta. */
  public boolean memberFieldTP(String fieldN, Vector pubTypeLESubfields) {
     int i = 0;
     while (i < pubTypeLESubfields.size()) {
        if (fieldN.equals(((LESubfield)pubTypeLESubfields.elementAt(i)).getField()))
           return true;
        i++;
     }
     return false;
  }

  /** Vraca vektor obaveznih potpolja za tip publikacije i nivo obrade. */
  public Vector getProcLESubfields() {
    LEContext lc = (LEContext)(contextList.elementAt(currContext));
    return lc.getProcSubfields();
  }

  /** Vraca vektor obaveznih potpolja za tip publikacije, nivo obrade i nivo obaveznosti. */
  public Vector getManLESubfields() {
    LEContext lc = (LEContext)(contextList.elementAt(currContext));
    return lc.getManSubfields();
  }

  /** Vraca formate koji su na raspolaganju. */
  public Format getFormat() {
    return formats;
  }

  /** Vraca  vektor tipova publikacije za ulogovanog bibliotekara.*/
  public Vector getPubTypeList() {
    Vector retVal = new Vector();
    HashMap hs = new HashMap();
    for(int i = 0; i < contextList.size(); i++) {
      hs.add(((LEContext)contextList.elementAt(i)).getPubType(), ((LEContext)contextList.elementAt(i)).getPubType());
    }

    Enumeration e = hs.elements();
    while (e.hasMoreElements()) {
      String s = (String)e.nextElement();
      retVal.addElement(s);
    }
    return retVal;
  }

  /** Vraca  vektor nivoa obrade za zadati tip publikacije.*/
  public Vector getProcLevelList(String pType) {
    Vector retVal = new Vector();
    HashMap hs = new HashMap();
    for(int i = 0; i < contextList.size(); i++) {
      LEContext lc = (LEContext)contextList.elementAt(i);
      if (lc.getPubType().equals(pType))
        hs.add(new String("" + lc.getProcLevel()), new String("" + lc.getProcLevel()));
    }

    Enumeration e = hs.elements();
    while (e.hasMoreElements()) {
      String s = (String)e.nextElement();
      retVal.addElement(s);
    }
    return retVal;
  }

  /** Vraca  vektor nivoa obaveznosti za zadati tip publikacije i nivo obrade.*/
  public Vector getManLevelList(String pType, int pLevel) {
    Vector retVal = new Vector();
    HashMap hs = new HashMap();
    for(int i = 0; i < contextList.size(); i++) {
      LEContext lc = (LEContext)contextList.elementAt(i);
      if (lc.getPubType().equals(pType) &&
          lc.getProcLevel() == pLevel)
        hs.add(new String("" + lc.getManLevel()), new String("" + lc.getManLevel()));
    }

    Enumeration e = hs.elements();
    while (e.hasMoreElements()) {
      String s = (String)e.nextElement();
      retVal.addElement(s);
    }
    return retVal;
  }

  /** Vraca vektor objekata u kojima se kriju parovi (prefix, opis prefiksa).
    U pitanju je string "prefix - opis".
  */
  public Vector getPrefixNames() {
    return prefixNames;
  }

  /** Inner klasa koja se koristi kod definisanja formata.
      Sadrzi ime formata i prefiks.
  */
  private class FormatPrefix {
    String format;
    String prefix;

    public FormatPrefix(String format, String prefix) {
      this.format = new String(format);
      this.prefix = new String(prefix);
    }
  };


   /** Kompresuje vektor tako da nemam npr. 100a, 100b i 100c, vec
    100abc.
  */
  public Vector compressSubfields(Vector v) {
    v = sortLESubfields(v);
    Vector retVal = new Vector();
    String prevField = "";
    String subfield = "";
    String defVal = null;
    if (v.size() > 0) {
      prevField = ((LESubfield)v.elementAt(0)).getField();
      defVal = ((LESubfield)v.elementAt(0)).getDefValue();
    }
    for(int i = 0; i < v.size(); i++) {
      LESubfield sf = (LESubfield)v.elementAt(i);
      if (!sf.getField().equals(prevField)) {
        retVal.addElement(new LESubfield(prevField, subfield, defVal));
        prevField = new String(sf.getField());
        subfield = new String(sf.getSubfield(prevField));
        defVal = sf.getDefValue();
      }
      else {
        subfield = subfield + sf.getSubfield(sf.getField());
        defVal = ((LESubfield)v.elementAt(i)).getDefValue();
      }
    }
    retVal.addElement(new LESubfield(prevField, subfield, defVal));
    return sortLESubfields(retVal);
  }

  private Vector sortLESubfields(Vector v) {
    Vector retVal = new Vector();
    LESubfield niz[] = null;
    LESubfield sniz[] = null;
    int i, j;
    niz = new LESubfield[v.size()];
    v.copyInto(niz);
    qsort(0, niz.length - 1, niz);
    for (i = 0; i < niz.length; i++)
      retVal.addElement(niz[i]);
    return retVal;
  }

  /** Zapravo ovo sortira, tako sto podeli niz na dva podniza, a onda ih rekurzivno sortira. */
  public void qsort(int d, int g, LESubfield[] a) {
    int i, j;
    LESubfield k;
    // sort jednoelementnog niza
    if (d >= g)
      return;
    // sort dvoelementnog niza
    if (d + 1 == g) {
      if (a[d].getField().compareTo(a[g].getField()) > 0) {
        //zameni(d, g, a);
        k    = a[d];
        a[d] = a[g];
        a[g] = k;
      }
      return;
    }
    // svi ostali slucajevi
    //j = podeli(d, g, a);
    j = d;
    for (i = d + 1; i <= g; i++)
      if (a[i].getField().compareTo(a[d].getField()) <= 0) {
        //zameni(++j, i, a);
        j++;
        k    = a[j];
        a[j] = a[i];
        a[i] = k;
      }
    //zameni(d, j, a);
    k    = a[d];
    a[d] = a[j];
    a[j] = k;

    qsort(d, j - 1, a);
    qsort(j + 1, g, a);
  }

  /** Vraca kod za prosledjeni tip publikacije.
      @param pubType Tip publikacije oblika "M1" (monografija)
      @return Kod za prosledjeni tip publikacije (npr. "001" za "M1"), a "" ako ne prepozna.
     */
  public String pubTypeDecode(String pubType) {
    if (pubType.equals("M1"))
       return "001";
    else if (pubType.equals("M2"))
       return "002";
    else if (pubType.equals("M3"))
       return "003";
    else if (pubType.equals("S1"))
       return "004";
    else if (pubType.equals("S2"))
       return "005";
    else if (pubType.equals("A1"))
       return "006";
    else if (pubType.equals("NO"))
       return "007";
    else if (pubType.equals("C_"))
       return "008";
    else if (pubType.equals("DS"))
       return "009";
    else if (pubType.equals("R_"))
       return "010";
    else if (pubType.equals("NK"))
       return "017";
    else if (pubType.equals("NN"))
       return "027";
    else if (pubType.equals("RD"))
       return "037";
    else
       return "";
  }

  /** Vraca string sa imenom tipa publikacije na osnovu prosledjenog koda.
      @param pubType Tip publikacije oblika "001" (monografija)
      @return Naziv tipa publikacije (npr. "M1" za "001") a <b>null</b> ako ne prepozna.
     */
  public String pubTypeCode(String pubType) {
    if (pubType.equals("001"))
       return "M1";
    else if (pubType.equals("002"))
       return "M2";
    else if (pubType.equals("003"))
       return "M3";
    else if (pubType.equals("004"))
       return "S1";
    else if (pubType.equals("005"))
       return "S2";
    else if (pubType.equals("006"))
       return "A1";
    else if (pubType.equals("007"))
       return "NO";
    else if (pubType.equals("008"))
       return "C_";
    else if (pubType.equals("009"))
       return "DS";
    else if (pubType.equals("010"))
       return "R_";
    else if (pubType.equals("017"))
       return "NK";
    else if (pubType.equals("027"))
       return "NN";
    else if (pubType.equals("037"))
       return "RD";
    else
       return null;
  }


  /** Ime bibliotekara */
  private String libName;
  /** Default tip publikacije */
  private String defPubType;
  /** Default nivo obrade */
  private int defProcLevel;
  /** Default nivo obaveznosti */
  private int defManLevel;

  /** Trenutni vektor polja */
  private Vector fields = new Vector();
  /** Trenutni vektor prefiksa za pretragu */
  private Vector prefixes = new Vector();
  /** Imena svih prefiksa ,sa opisima */
  private Vector prefixNames = new Vector();
  /** Vektor svih prefiksa. */
  private Vector allPrefixes = new Vector();
  /** Trenutni formati za prikaz rezultata */
  private Format formats = new Format();
  /** Default format */
  String defaultFormat = null;

  /** Tekuci (tip_pub, nivo_obr, nivo_obav)   */
  private int currContext;
  /** Lista mogucih konteksta bibliotekara */
  private Vector contextList = new Vector();
  /** Zbog konverzije iz YUSCII u Unicode. */
  private Charset conv = new Charset();



  public static void createXML(DocumentImpl document, LELibrarian l) {
    Node root = document.createElement("LELibrarian");
    document.appendChild(root);

    Element defPubType = document.createElement("defPubType");
    defPubType.appendChild(document.createTextNode(l.defPubType));
    root.appendChild(defPubType);

    Element defProcLevel = document.createElement("defProcLevel");
    defProcLevel.appendChild(document.createTextNode("" + l.defProcLevel));
    root.appendChild(defProcLevel);

    Element defManLevel = document.createElement("defManLevel");
    defManLevel.appendChild(document.createTextNode("" + l.defManLevel));
    root.appendChild(defManLevel);

    String s;
    Element prefixes = document.createElement("prefixes");
    Element prefix;
    for (int i = 0; i < l.prefixes.size(); i++) {
      s = (String)(l.prefixes.elementAt(i));
      prefix = document.createElement("prefix");
      prefix.setAttribute("name", s);
      prefixes.appendChild(prefix);
    }
    root.appendChild(prefixes);

    Element prefixNames = document.createElement("prefixNames");
    String name;
    String desc;
    Element prefixName;
    int idx;
    for (int i = 0; i < l.prefixNames.size(); i++) {
      s = (String)(l.prefixNames.elementAt(i));
      idx = s.indexOf(" - ");
      name = s.substring(0, idx);
      desc = s.substring(idx + 3);
      prefixName = document.createElement("prefixName");
      prefixName.setAttribute("name", name);
      prefixName.setAttribute("desc", desc);
      prefixNames.appendChild(prefixName);
    }
    root.appendChild(prefixNames);

    Element formats = document.createElement("formats");
    Vector v = l.formats.getAllFormats();
    String prefiksi, naziv;
    Element currFormat;
    for (int i = 0; i < l.formats.getPredefinedCount(); i++) {
      s = (String)(v.elementAt(i));
      idx = s.indexOf("(");
      naziv = s.substring(0,idx);
      prefiksi = s.substring(idx+1);
      prefiksi = prefiksi.substring(0, prefiksi.length() - 1);
      currFormat = document.createElement("predefinisan");
      currFormat.setAttribute("naziv", naziv);
      currFormat.appendChild(document.createTextNode(prefiksi));
      formats.appendChild(currFormat);
    }
    root.appendChild(formats);

    Element defaultFormat = document.createElement("defaultFormat");
    defaultFormat.appendChild(document.createTextNode("" + l.defaultFormat));
    root.appendChild(defaultFormat);

    Element contextList = document.createElement("contextList");
    LEContext ctx;
    for (int i = 0; i < l.contextList.size(); i++) {
      ctx = (LEContext)(l.contextList.elementAt(i));
      contextList.appendChild(ctx.createXML(document));
    }
    root.appendChild(contextList);

  }


  public static String toString(LELibrarian l) {
    StringBuffer sb = new StringBuffer();
    sb.append("defPubType: " + l.defPubType + "\n");
    sb.append("defProcLevel: " + l.defProcLevel + "\n");
    sb.append("defManLevel: " + l.defManLevel + "\n");

    String s;
    for (int i = 0; i < l.prefixes.size(); i++) {
      s = (String)(l.prefixes.elementAt(i));
      sb.append("prefixes[" + i + "]: " + s + ",\t");
    }
    sb.append("\n");

    for (int i = 0; i < l.prefixNames.size(); i++) {
      s = (String)(l.prefixNames.elementAt(i));
      sb.append("prefix[" + i + "]: " + s + "\t");
    }
    sb.append("\n");

    Vector v = l.formats.getAllFormats();
    for (int i = 0; i < l.formats.getPredefinedCount(); i++) {
      s = (String)(v.elementAt(i));
      sb.append("predefinisani[" + i + "]: " + s + "\n");
    }

    sb.append("defaultFormat: " + l.defaultFormat + "\n");

    LEContext ctx;
    LESubfield lsf;
    for (int i = 0; i < l.contextList.size(); i++) {
      ctx = (LEContext)(l.contextList.elementAt(i));
      sb.append("contex[" + i + "]: ");
      sb.append("pubType: " + ctx.getPubType() + "\t");
      sb.append("procLevel: " + ctx.getProcLevel() + "\t");
      sb.append("manLevel: " + ctx.getManLevel() + "\n");

      for (int j = 0; j < ctx.getTypeSubfields().size(); j++) {
        lsf = (LESubfield)(ctx.getTypeSubfields().elementAt(j));
        sb.append("typeSubfields[" + i + "]: " + lsf.toString() + "\n");
      }

      for (int j = 0; j < ctx.getProcSubfields().size(); j++) {
        lsf = (LESubfield)(ctx.getProcSubfields().elementAt(j));
        sb.append("procSubfields[" + i + "]: " + lsf.toString() + "\n");
      }

      for (int j = 0; j < ctx.getManSubfields().size(); j++) {
        lsf = (LESubfield)(ctx.getManSubfields().elementAt(j));
        sb.append("manSubfields[" + i + "]: " + lsf.toString() + "\n");
      }
    }
    return sb.toString();
  }

  public static void main(String args[]) {
    try {
      // Load the Oracle JDBC driver
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

      // Connect to the database
      // You must put a database name after the @ sign in the connection URL.
      // You can use either the fully specified SQL*net syntax or a short cut
      // syntax as <host>:<port>:<sid>.  The example uses the short cut syntax.
      Connection conn =
        DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1526:BIS8",
  				   "bis", "mesec2");
      LELibrarian l = new LELibrarian(conn, "demo", "demo");
//      System.out.println(l.toString(l));
      DocumentImpl document = new DocumentImpl();
      createXML(document, l);
//      XMLUtils.print(document);
      l = new LELibrarian(document);
      System.out.println(LELibrarian.toString(l));
    } catch ( Exception e ) {
       e.printStackTrace();
    }
  }
}


