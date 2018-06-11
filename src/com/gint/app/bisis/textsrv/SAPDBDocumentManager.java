package com.gint.app.bisis.textsrv;

import java.sql.*;
import java.util.*;

import com.gint.util.string.*;

/**  Klasa SAPDBDocumentManager implementira DocumentManager
  *  interfejs za potrebe rada sa bazom SAP DB (verzija 7.3).
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  *  @see DocumentManager
  */
public class SAPDBDocumentManager implements DocumentManager {

  /**  Konstruise objekat sa datim parametrima.
    *  @param conn Database connection
    *  @param wp konstruisani objekat klase WordParser
    *  @param dp Konstruisani objekat klase DocumentParser
    *  @param hexConv flag da li se radi hex konverzija
    */
  public SAPDBDocumentManager(Connection conn) {
    this.conn = conn;
    conv = new UnimarcConverter();
    allPrefixes = new com.objectspace.jgl.HashSet();
    basePrefixes = new com.objectspace.jgl.HashSet();
    try {
      // ucitaj spiskove svih i baznih prefiksa
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT name, type FROM Prefixes");
      String name;
      while (rset.next()) {
        name = rset.getString(1).toUpperCase();
        if (rset.getInt(2) != 2)
          basePrefixes.add(name.trim());
        allPrefixes.add(name.trim());
      }
      rset.close();

      // ucitaj delimitere (za WordParser)
      rset = stmt.executeQuery("SELECT wordDelims, sentDelims FROM Delimiters WHERE configID = 1");
      rset.next();
      wordDelims = new String(rset.getString(1).trim());
      sentDelims = new String(rset.getString(2).trim());
      rset.close();
      allDelims = wordDelims + sentDelims;

      // ucitaj mapiranje potpolje->prefiksi (za DocumentParser)
      prefixMap = new com.objectspace.jgl.HashMap(true);
      rset = stmt.executeQuery("select subfield_id, pref_name from Prefix_map");
      while (rset.next())
        prefixMap.add(new String(rset.getString(1).trim()), new String(rset.getString(2).trim()));
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    wp = new WordParser(wordDelims, sentDelims);
    dp = new DocumentParser(prefixMap, "\36", "\37");
  }

  public SAPDBDocumentManager(SAPDBDocumentManager odm, Connection conn) {
    this.wp = odm.wp;
    this.dp = odm.dp;
    conv = new UnimarcConverter();
    this.prefixMap = odm.prefixMap;
    this.allDelims = odm.allDelims;
    this.wordDelims = odm.wordDelims;
    this.sentDelims = odm.sentDelims;
    this.allPrefixes = odm.allPrefixes;
    this.basePrefixes = odm.basePrefixes;
    this.conn = conn;
  }

  /**  Dodaje dokument u bazu.
    *  @param doc Sadrzaj novog dokumenta
    *  @return Identifikator dokumenta (docID > 0) ako je dodavanje bilo uspesno; inace -1
    */
  public int insertDocument(String doc) {
    int retVal;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    Statement stmt = null;
    ResultSet rset;
    PrefixPair pp;
    int wordCount;
    int sentCount;
    try {
      Vector prefixes = dp.parseDocument(doc);

      pstmt = conn.prepareStatement("INSERT INTO Documents (doc_id, document) VALUES (DocSeq.NEXTVAL, ?)");
      //ByteArrayInputStream baos = new ByteArrayInputStream(StringUtils.getLowerBytes(doc));
      //pstmt.setBinaryStream(1, baos, baos.available());
      pstmt.setString(1, doc);
      //pstmt.setString(1, StringUtils.getLowerBytes(doc));
      pstmt.executeUpdate();
      pstmt.close();

      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT DocSeq.CURRVAL FROM DUAL");
      rset.next();
      retVal = rset.getInt(1);
      rset.close();

      pstmt = conn.prepareStatement("INSERT INTO Doc_shadow (doc_id) VALUES (?)");
      pstmt.setInt(1, retVal);
      pstmt.executeUpdate();
      pstmt.close();

      pstmt = conn.prepareStatement("INSERT INTO Prefix_contents (prefID, pref_name, doc_id, content) VALUES (PrefContSeq.NEXTVAL, ?, ?, ?)");

      Enumeration e = prefixes.elements();
      while (e.hasMoreElements()) {
        pp = (PrefixPair)e.nextElement();

        // dodavanje u PREFIX_CONTENTS tabelu
        pstmt.clearParameters();
        pstmt.setString(1, pp.prefName);
        pstmt.setInt(2, retVal);
        //ByteArrayInputStream baos2 = new ByteArrayInputStream(StringUtils.getLowerBytes(pp.value));
        //pstmt.setBinaryStream(3, baos2, baos2.available());
        pstmt.setString(3, pp.value);
        //pstmt.setString(3, StringUtils.getLowerBytes(pp.value));
        pstmt.executeUpdate();

        rset = stmt.executeQuery("SELECT PrefContSeq.CURRVAL FROM DUAL");
        rset.next();
        int prefID = rset.getInt(1);
        rset.close();

        // dodavanje u PREF_XX tabele
        wordCount = 0;
        sentCount = 1;
        Vector words = wp.parseText(pp.value);
        Enumeration enum2 = words.elements();
        while (enum2.hasMoreElements()) {
          String parsedWord = (String)enum2.nextElement();
          parsedWord = conv.Unicode2Unimarc(conv.Unimarc2Unicode(parsedWord).toUpperCase(), false);
          if (parsedWord.equals("."))
            sentCount++;
          else {
            wordCount++;
            if (!parsedWord.equals("")) {
              String insertStr = new String("INSERT INTO pref_"+pp.prefName+
                " (doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+retVal+","+prefID+",'"
                +StringUtils.adjustQuotes(parsedWord)+"',"+wordCount+","+sentCount+")");
              stmt.executeUpdate(insertStr);
              String newWord = Charset.containsUnimarcYUChars(parsedWord);
              if (!newWord.equals("")) {
                insertStr = new String("INSERT INTO pref_"+pp.prefName+
                  "(doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+retVal+","+prefID+
                  ",'"+StringUtils.adjustQuotes(newWord)+"',"+wordCount+","+sentCount+")");
                stmt.executeUpdate(insertStr);
              }
            }
          }
        }
      }

      Vector circEntries = CirculationStuff.prepareForCirculation(retVal, doc, new Vector());
      pstmt2 = conn.prepareStatement("INSERT INTO circ_docs (sig, ctlg_no, docid, status, type, state) VALUES (?, ?, ?, 0, ?, ?)");
      for (int i = 0; i < circEntries.size(); i++) {
        CircEntry ce = (CircEntry)circEntries.elementAt(i);
        pstmt2.clearParameters();
        pstmt2.setString(1, ce.getSig());
        pstmt2.setString(2, ce.getInv());
        pstmt2.setInt(3, ce.getDocID());
        pstmt2.setString(4, ce.getType());
        pstmt2.setString(5, ce.getState());
        if (ce.isPart()) {
          try {
            pstmt2.executeUpdate();
          } catch (Exception ex) {
          }
        } else
          pstmt2.executeUpdate();
      }

      pstmt2.close();
      pstmt.close();
      stmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        conn.rollback();
      	if (pstmt2 != null)
      	  pstmt2.close();
      	if (pstmt != null)
          pstmt.close();
      	if (stmt != null)
          stmt.close();
      } catch (SQLException e) {}
      retVal = -1;
    }
    return retVal;
  }

  /**  Uklanja dokument iz baze.
    *  @param doc_id Identifikator dokumenta koji se brise
    *  @return status operacije: 0 - uspesno; -1 neuspesno
    */
  public int deleteDocument(int doc_id) {
    int retVal;
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate("DELETE FROM Doc_shadow WHERE doc_id="+doc_id);
      stmt.executeUpdate("DELETE FROM Documents WHERE doc_id="+doc_id);
      stmt.executeUpdate("DELETE FROM circ_docs WHERE docid="+doc_id);
      stmt.close();
      conn.commit();
      retVal = 0;
    } catch (Exception ex) {
      try {
        conn.rollback();
        if (stmt != null)
          stmt.close();
      } catch (SQLException e) {}
      retVal = -1;
    }
    return retVal;
  }

  /**  Azurira dokument u bazi.
    *  @param doc_id identifikator dokumenta koji se azurira
    *  @param doc Novi sadrzaj dokumenta
    *  @return Status operacije: 0 - uspesno; -1 - neuspesno
    */
  public int updateDocument(int doc_id, String doc) {
    int retVal;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    ResultSet rset;
    PrefixPair pp;
    int wordCount;
    int sentCount;

    try {
      Vector prefixes = dp.parseDocument(doc);

      // update rezanca u DOCUMENTS tabeli
      pstmt = conn.prepareStatement("UPDATE Documents SET document=? WHERE doc_id="+doc_id);
      //ByteArrayInputStream baos = new ByteArrayInputStream(StringUtils.getLowerBytes(doc));
      //pstmt.setBinaryStream(1, baos, baos.available());
      pstmt.setString(1, doc);
      //pstmt.setString(1, StringUtils.getLowerBytes(doc));
      retVal = pstmt.executeUpdate();
      pstmt.close();

      if (retVal == 0) {
        return -1;
      }

      // brisemo sve vezano za dati dokument u PREFIX_CONTENTS i PREF_XX tabelama
      // pomocu FOREIGN KEY CONSTRAINT-a (ON DELETE CASCADE) vezanih za tabelu DOC_SHADOW
      stmt = conn.createStatement();
      stmt.executeUpdate("DELETE FROM Doc_shadow WHERE doc_id="+doc_id);

      // ponovo dodajemo stavku u DOC_SHADOW
      pstmt = conn.prepareStatement("INSERT INTO Doc_shadow (doc_id) VALUES (?)");
      pstmt.setInt(1, doc_id);
      pstmt.executeUpdate();
      pstmt.close();

      pstmt = conn.prepareStatement("INSERT INTO Prefix_contents (prefID, pref_name, doc_id, content) VALUES (PrefContSeq.NEXTVAL, ?, ?, ?)");

      Enumeration e = prefixes.elements();
      while (e.hasMoreElements()) {
        pp = (PrefixPair)e.nextElement();

        // dodavanje u PREFIX_CONTENTS tabelu
        pstmt.clearParameters();
        pstmt.setString(1, pp.prefName);
        pstmt.setInt(2, doc_id);
        //ByteArrayInputStream baos2 = new ByteArrayInputStream(StringUtils.getLowerBytes(pp.value));
        //pstmt.setBinaryStream(3, baos2, baos2.available());
        pstmt.setString(3, pp.value);
        //pstmt.setString(3, StringUtils.getLowerBytes(pp.value));
        pstmt.executeUpdate();

        rset = stmt.executeQuery("SELECT PrefContSeq.CURRVAL FROM DUAL");
        rset.next();
        int prefID = rset.getInt(1);
        rset.close();

        // dodavanje u PREF_XX tabele
        wordCount = 0;
        sentCount = 1;
        Vector words = wp.parseText(pp.value);
        Enumeration enum2 = words.elements();
        while (enum2.hasMoreElements()) {
          String parsedWord = (String)enum2.nextElement();
          parsedWord = conv.Unicode2Unimarc(conv.Unimarc2Unicode(parsedWord).toUpperCase(), false);
          if (parsedWord.equals("."))
            sentCount++;
          else {
            wordCount++;
            if (!parsedWord.equals("")) {
              String insertStr = new String("INSERT INTO pref_"+pp.prefName+
                " (doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+
                doc_id+","+prefID+",'"+StringUtils.adjustQuotes(parsedWord)+"',"+wordCount+","+sentCount+")");
              stmt.executeUpdate(insertStr);
              String newWord = Charset.containsUnimarcYUChars(parsedWord);
              if (!newWord.equals("")) {
                insertStr = new String("INSERT INTO pref_"+pp.prefName+
                  "(doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+
                  doc_id+","+prefID+",'"+StringUtils.adjustQuotes(newWord)+"',"+wordCount+","+sentCount+")");
                stmt.executeUpdate(insertStr);
              }
            }
          }
        }
      }

      Vector oldCircEntries = new Vector();
      rset = stmt.executeQuery("SELECT sig, ctlg_no, status, type, state FROM circ_docs WHERE docid="+doc_id);
      while (rset.next()) {
        String sig = rset.getString(1);
        String ctlg_no = rset.getString(2);
        int status = rset.getInt(3);
        String type = rset.getString(4);
        String state = rset.getString(5);
        oldCircEntries.addElement(new CircEntry(doc_id, sig, ctlg_no, type, state, status));
      }
      rset.close();

      stmt.executeUpdate("DELETE FROM circ_docs WHERE docid="+doc_id);

      // update CIRC_DOCS tabele
      Vector newCircEntries = CirculationStuff.prepareForCirculation(doc_id, doc, oldCircEntries);
      pstmt2 = conn.prepareStatement("INSERT INTO circ_docs (sig, ctlg_no, docid, status, type, state) VALUES (?, ?, ?, ?, ?, ?)");
      for (int i = 0; i < newCircEntries.size(); i++) {
        CircEntry ce = (CircEntry)newCircEntries.elementAt(i);
        pstmt2.clearParameters();
        pstmt2.setString(1, ce.getSig());
        pstmt2.setString(2, ce.getInv());
        pstmt2.setInt(3, ce.getDocID());
        pstmt2.setInt(4, ce.getAvailable());
        pstmt2.setString(5, ce.getType());
        pstmt2.setString(6, ce.getState());
        if (ce.isPart()) {
          try {
            pstmt2.executeUpdate();
          } catch (Exception ex) {
          }
        } else
          pstmt2.executeUpdate();
      }

      pstmt2.close();
      pstmt.close();
      stmt.close();
      conn.commit();
      retVal = 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        conn.rollback();
        if (pstmt2 != null)
          pstmt2.close();
        if (pstmt != null)
          pstmt.close();
        if (stmt != null)
          stmt.close();
      } catch (SQLException e) {}
      retVal = -1;
    }
    return retVal;
  }

  /**  Zauzima mesto za novi (prazan) dokument i vraca njegov
    *  DocID. Taj dokument se posle moze update-ovati na osnovu
    *  ovog DocID-a.
    *  @return Identifikator novog dokumenta
    */
  public int  reserveDocument() {
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rset = null;
    int retVal;
    try {
      pstmt = conn.prepareStatement("INSERT INTO Documents (doc_id, document) VALUES (DocSeq.NEXTVAL, ?)");
      //ByteArrayInputStream baos = new ByteArrayInputStream((new String("")).getBytes());
      //pstmt.setBinaryStream(1, baos, baos.available());
      pstmt.setString(1, "");
      pstmt.executeUpdate();
      pstmt.close();

      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT DocSeq.CURRVAL FROM DUAL");
      rset.next();
      retVal = rset.getInt(1);
      rset.close();
      stmt.close();
      conn.commit();
      return retVal;
    } catch (Exception ex) {
      ex.printStackTrace();
      try{
        conn.rollback();
        if (rset != null)
          rset.close();
        if (pstmt != null)
          pstmt.close();
        if (stmt != null)
          stmt.close();
      } catch (SQLException ex1) { }
      return -1;
    }
  }

  /**  Vrsi azuriranje indeksa za postojeci zapis. Ne menja njegov
    *  identifikator. Koristi se u slucajevima kada se promeni
    *  konfiguracija tekst servera (delimiteri, mapiranje na prefikse).
    *  @param doc_id Identifikator zapisa koji se reindeksira
    *  @return Status izvrsene operacije: 0 - uspesno; -1 - neuspesno
    */
  public int reindexDocument(int doc_id) {
    int retVal = 0;
    try {

      // procitamo sadrzaj dokumenta
      String doc = "";
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT document FROM documents WHERE doc_id="+doc_id);
      if (rset.next())
        doc = StringUtils.getStringLower(rset.getBytes(1));
      else {
        rset.close();
        return -1;
      }
      rset.close();
      stmt.close();

      // brisemo sve vezano za dati dokument u PREFIX_CONTENTS i PREF_XX tabelama
      // pomocu FOREIGN KEY CONSTRAINT-a (ON DELETE CASCADE) vezanih za tabelu DOC_SHADOW
      stmt = conn.createStatement();
      stmt.executeUpdate("DELETE FROM doc_shadow WHERE doc_id="+doc_id);
      stmt.close();

      Vector prefixes = dp.parseDocument(doc);

      // ponovo dodajemo stavku u DOC_SHADOW
      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Doc_shadow (doc_id) VALUES (?)");
      pstmt.setInt(1, doc_id);
      pstmt.executeUpdate();
      pstmt.close();

      String SG = "Ne postoji";
      com.objectspace.jgl.HashMap IN = new com.objectspace.jgl.HashMap();

      pstmt = conn.prepareStatement("INSERT INTO Prefix_contents (prefID, pref_name, doc_id, content) VALUES (PrefContSeq.NEXTVAL, ?, ?, ?)");

      stmt = conn.createStatement();
      Enumeration e = prefixes.elements();
      while (e.hasMoreElements()) {
        PrefixPair pp = (PrefixPair)e.nextElement();

        // citanje podataka vezanih za CIRC_DOCS tabelu
        if (pp.prefName.equals("SG"))
          SG = pp.value;
        if (pp.prefName.equals("IN"))
          IN.add(new String(pp.value), new Integer(0));

        // dodavanje u PREFIX_CONTENTS tabelu
        pstmt.clearParameters();
        pstmt.setString(1, pp.prefName);
        pstmt.setInt(2, doc_id);
        //ByteArrayInputStream baos2 = new ByteArrayInputStream(StringUtils.getLowerBytes(pp.value));
        //pstmt.setBinaryStream(3, baos2, baos2.available());
        pstmt.setString(3, pp.value);
        //pstmt.setString(3, StringUtils.getLowerBytes(pp.value));
        pstmt.executeUpdate();

        rset = stmt.executeQuery("SELECT PrefContSeq.CURRVAL FROM DUAL");
        rset.next();
        int prefID = rset.getInt(1);
        rset.close();

        // dodavanje u PREF_XX tabele
        int wordCount = 0;
        int sentCount = 1;
        Vector words = wp.parseText(pp.value);
        Enumeration enum2 = words.elements();
        while (enum2.hasMoreElements()) {
          String parsedWord = (String)enum2.nextElement();
          parsedWord = conv.Unicode2Unimarc(conv.Unimarc2Unicode(parsedWord).toUpperCase(), false);
          if (parsedWord.equals("."))
            sentCount++;
          else {
            wordCount++;
            if (!parsedWord.equals("")) {
              String insertStr = new String("INSERT INTO pref_"+pp.prefName+
                " (doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+
                doc_id+","+prefID+",'"+StringUtils.adjustQuotes(parsedWord)+"',"+wordCount+","+sentCount+")");
              stmt.executeUpdate(insertStr);
              String newWord = Charset.containsUnimarcYUChars(parsedWord);
              if (!newWord.equals("")) {
                insertStr = new String("INSERT INTO pref_"+pp.prefName+
                  "(doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+
                  doc_id+","+prefID+",'"+StringUtils.adjustQuotes(newWord)+"',"+wordCount+","+sentCount+")");
                stmt.executeUpdate(insertStr);
              }
            }
          }
        }
      }

      rset = stmt.executeQuery("SELECT sig, ctlg_no, status, type, state FROM circ_docs WHERE docid="+doc_id);
      String type = "";
      String state = "";
      while (rset.next()) {
        String sig = rset.getString(1);
        String ctlg_no = rset.getString(2);
        int status = rset.getInt(3);
        type = rset.getString(4);
        state = rset.getString(5);
        if (IN.get(ctlg_no) != null)
          IN.put(ctlg_no, new Integer(status));
      }
      rset.close();

      stmt.executeUpdate("DELETE FROM circ_docs WHERE docid="+doc_id);

      // update CIRC_DOCS tabele
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO circ_docs (sig, ctlg_no, docid, status, type, state) VALUES (?, ?, ?, ?, ?, ?)");
      Enumeration enum2 = IN.keys();
      while (enum2.hasMoreElements()) {
        String temp = (String)enum2.nextElement();
        pstmt2.clearParameters();
        pstmt2.setString(1, SG);
        pstmt2.setString(2, temp);
        pstmt2.setInt(3, doc_id);
        pstmt2.setInt(4, ((Integer)IN.get(temp)).intValue());
        pstmt2.setString(5, type);
        pstmt2.setString(6, state);
        pstmt2.executeUpdate();
      }

      pstmt2.close();
      pstmt.close();
      stmt.close();
      conn.commit();
      retVal = 0;

    } catch (SQLException ex) {
      ex.printStackTrace();
      retVal = -1;
    }
    return retVal;
  }

  /**  U bazi pronalazi pogotke za elementarni upit.
    *  @param prefix Prefiks po kome se pretrazuje. Ako je null, trazi se po svim baznim prefiksima.
    *  @param term Izraz koji se trazi.
    *  @return Skup pogodaka kao HashSet koji sadrzi SearchHit objekte.
    */
  public com.objectspace.jgl.HashSet executeSelect(String prefix, String term) {
    String queryStr;
    String whereStr;
    com.objectspace.jgl.HashSet retVal = new com.objectspace.jgl.HashSet();

    if (prefix != null) {
      if (prefix.equals("ID")) {
        try {
          selectStatement = conn.createStatement();
          ResultSet rset = selectStatement.executeQuery("SELECT doc_id FROM Documents WHERE doc_id="+term);
          if (rset.next())
            retVal.add(new SearchHit(rset.getInt(1), 0, 0, 0));
          rset.close();
          selectStatement.close();
        } catch (SQLException ex) {
        }
        return retVal;
      }
    }

    whereStr = " WHERE content LIKE '"+StringUtils.adjustQuotes(conv.Unicode2Unimarc(term.replace('*','%').replace('?','_'), false))+"'";

    if (prefix != null) {  // kvalifikovani upit
      queryStr = "SELECT doc_id, pref_id, word_pos, sent_pos FROM pref_"+prefix+whereStr;
    } else {   // nekvalifikovani upit
      Enumeration e = basePrefixes.elements();
      queryStr = "SELECT doc_id, pref_id, word_pos, sent_pos FROM pref_"+e.nextElement()+whereStr;
      while (e.hasMoreElements()) {
        queryStr += " UNION SELECT doc_id, pref_id, word_pos, sent_pos FROM pref_"+e.nextElement()+whereStr;
      }
    }
    try {
      selectStatement = conn.createStatement();
      ResultSet rset = selectStatement.executeQuery(queryStr);
      while (rset.next())
        retVal.add(new SearchHit(rset.getInt(1),rset.getInt(2),rset.getInt(3),rset.getInt(4)));
      rset.close();
      selectStatement.close();
    } catch (SQLException ex) {
    }
    return retVal;
  }

  /**  Izvrsava Expand naredbu.
    *  @param query term koji se trazi
    *  @return vektor ExpandPair objekata
    */
  public Vector expand(String prefix, String query) {
    Vector retVal = new Vector();
    com.objectspace.jgl.HashMap hMap = new com.objectspace.jgl.HashMap();

    query = conv.Unicode2Unimarc(query.toUpperCase(), false).trim();
    query = StringUtils.clearDelimiters(query, getAllDelimiters());

    StringTokenizer st = new StringTokenizer(query);
    int count = 1;
    String term = st.nextToken();
    if (st.countTokens() == 0)
      term += "%";
    String queryStr = "SELECT /*+USE_HASH(pref_"+prefix+") */ a.content FROM Prefix_contents a, pref_"+prefix+" b WHERE b.content LIKE '"+term+"' AND word_pos = 1 AND a.prefid = b.pref_id";
    while (st.hasMoreTokens()) {
      count++;
      term = st.nextToken();
      if (st.countTokens() == 0)
        term += "%";
      queryStr += " AND b.pref_id IN (SELECT pref_id FROM pref_"+prefix+" WHERE word_pos="+count+" AND content LIKE '"+term+"'";
    }
    for (int i = 1; i < count; i++)
      queryStr += ")";

    try {
      expandStatement = conn.createStatement();
      ResultSet rset = expandStatement.executeQuery(queryStr);
      while (rset.next()) {
        String temp = rset.getString(1);
        if (hMap.count(temp) == 0)
          hMap.add(temp, new Integer(1));
        else {
          Integer i = (Integer)hMap.get(temp);
          Integer i1 = new Integer(i.intValue()+1);
          hMap.remove(temp);
          hMap.add(temp, i1);
        }
      }
      rset.close();
      expandStatement.close();
      Enumeration keys = hMap.keys();
      while (keys.hasMoreElements()) {
        String temp = (String)keys.nextElement();
        retVal.addElement(new ExpandPair(temp, ((Integer)hMap.get(temp)).intValue()));
      }
      retVal = sortVector(retVal);
    } catch (Exception ex) {
      retVal.removeAllElements();
    }
    return retVal;
  }

  /**  Vraca skup svih definisanih prefiksa.
    *  @return Skup svih definisanih prefiksa
    */
  public com.objectspace.jgl.HashSet getAllPrefixes() {
    return allPrefixes;
  }

  /**  Vraca skup svih baznih prefiksa.
    *  @return Skup svih baznih prefiksa
    */
  public com.objectspace.jgl.HashSet getBasePrefixes() {
    return basePrefixes;
  }

  /**  Vraca mapiranje potpolja->prefiksi.
    *  @return Mapa sa preslikavanjem potpolja->prefiksi
    */
  public com.objectspace.jgl.HashMap getPrefixMap() {
    return prefixMap;
  }

  /**  Dohvata dokumente iz baze podataka na osnovu rezultata pretrage.
    *  @param docIDs [izlazni parametar] niz identifikatora dokumenata
    *  @param docs [izlazni parametar] niz sadrzaja dokumenata
    */
  public void getDocsRange(int[] docIDs, String[] docs) {
    String queryStr = "SELECT doc_id, document FROM documents WHERE doc_id IN ("+docIDs[0];
    for(int i = 1; i < docIDs.length; i++)
      queryStr += ","+docIDs[i];
    queryStr += ") ORDER BY doc_id";
    try {
      Statement stmt = conn.createStatement();
      //((oracle.jdbc.driver.OracleStatement)stmt).defineColumnType(1, Types.INTEGER);
      //((oracle.jdbc.driver.OracleStatement)stmt).defineColumnType(2, Types.LONGVARBINARY);
      ResultSet rset = stmt.executeQuery(queryStr);
      int i = 0;
      while (rset.next()) {
        docIDs[i] = rset.getInt(1);
        docs[i++] = rset.getString(2);
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**  Dohvata zeljeni dokument iz baze
    *  @param docID Identifikator dokumenta
    *  @return Sadrzaj dokumenta
    */
  public String getDoc(int docID) {
    String queryStr = "SELECT document FROM documents WHERE doc_id = "+docID;
    String retVal;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(queryStr);
      if (rset.next())
        retVal = rset.getString(1);
      else
        retVal = "";
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
      retVal = "";
    }
    return retVal;
  }

  /**
   * Radi update prvog zapisa a brisanje zapisa iz niza. Pri tome se
   * ne brisu stavke iz CIRC_DOCS tabele vec se one prevezu na prvi zapis.
   * @param docID ID zapisa koji je rezultat merge operacije
   * @param doc zapis koji je rezultat merge operacije
   * @param docIDs ID zapisa koji se brisu
   * @return 0 - uspesno, -1 - neuspesno
   */
  public int merge(int docID, String doc, int[] docIDs) {
    int retVal = -1;
    try {
      Statement stmt = conn.createStatement();
      for (int i = 0; i < docIDs.length; i++) {
        stmt.executeUpdate("DELETE FROM Doc_shadow WHERE doc_id="+docIDs[i]);
        stmt.executeUpdate("DELETE FROM Documents WHERE doc_id="+docIDs[i]);
        stmt.executeUpdate("UPDATE circ_docs SET docid="+docID+" WHERE docid="+docIDs[i]);
      }
      stmt.close();

      Vector prefixes = dp.parseDocument(doc);

      // update rezanca u DOCUMENTS tabeli
      PreparedStatement pstmt = conn.prepareStatement("UPDATE Documents SET document=? WHERE doc_id="+docID);
      pstmt.setString(1, doc);
      retVal = pstmt.executeUpdate();
      pstmt.close();
      if (retVal == 0) {
        return -1;
      }

      // brisemo sve vezano za dati dokument u PREFIX_CONTENTS i PREF_XX tabelama
      // pomocu FOREIGN KEY CONSTRAINT-a (ON DELETE CASCADE) vezanih za tabelu DOC_SHADOW
      stmt = conn.createStatement();
      stmt.executeUpdate("DELETE FROM Doc_shadow WHERE doc_id="+docID);

      // ponovo dodajemo stavku u DOC_SHADOW
      pstmt = conn.prepareStatement("INSERT INTO Doc_shadow (doc_id) VALUES (?)");
      pstmt.setInt(1, docID);
      pstmt.executeUpdate();
      pstmt.close();

      pstmt = conn.prepareStatement("INSERT INTO Prefix_contents (prefID, pref_name, doc_id, content) VALUES (PrefContSeq.NEXTVAL, ?, ?, ?)");

      Enumeration e = prefixes.elements();
      while (e.hasMoreElements()) {
        PrefixPair pp = (PrefixPair)e.nextElement();

        // dodavanje u PREFIX_CONTENTS tabelu
        pstmt.clearParameters();
        pstmt.setString(1, pp.prefName);
        pstmt.setInt(2, docID);
        pstmt.setString(3, pp.value);
        pstmt.executeUpdate();

        ResultSet rset = stmt.executeQuery("SELECT PrefContSeq.CURRVAL FROM DUAL");
        rset.next();
        int prefID = rset.getInt(1);
        rset.close();

        // dodavanje u PREF_XX tabele
        int wordCount = 0;
        int sentCount = 1;
        Vector words = wp.parseText(pp.value);
        Enumeration enum2 = words.elements();
        while (enum2.hasMoreElements()) {
          String parsedWord = (String)enum2.nextElement();
          parsedWord = conv.Unicode2Unimarc(conv.Unimarc2Unicode(parsedWord).toUpperCase(), false);
          if (parsedWord.equals("."))
            sentCount++;
          else {
            wordCount++;
            if (!parsedWord.equals("")) {
              String insertStr = new String("INSERT INTO pref_"+pp.prefName+
                " (doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+
                docID+","+prefID+",'"+StringUtils.adjustQuotes(parsedWord)+"',"+wordCount+","+sentCount+")");
              stmt.executeUpdate(insertStr);
              String newWord = Charset.containsUnimarcYUChars(parsedWord);
              if (!newWord.equals("")) {
                insertStr = new String("INSERT INTO pref_"+pp.prefName+
                  "(doc_id, pref_id, content, word_pos, sent_pos) VALUES ("+
                  docID+","+prefID+",'"+StringUtils.adjustQuotes(newWord)+"',"+wordCount+","+sentCount+")");
                stmt.executeUpdate(insertStr);
              }
            }
          }
        }
      }
      conn.commit();
      retVal = 0;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return retVal;
  }

  /** Vraca string sa svim delimiterima iz tekuce konfiguracije baze.
    */
  public String getAllDelimiters() {
    return allDelims;
  }

  /** Vraca string sa delimiterima reci iz tekuce konfiguracije baze.
    */
  public String getWordDelimiters() {
    return wordDelims;
  }

  /** Vraca string sa delimiterima recenica iz tekuce konfiguracije baze.
    */
  public String getSentenceDelimiters() {
    return sentDelims;
  }

  /** Prekida EXPAND upit koji se izvrsava u drugom threadu.
    */
  public void cancelExpand() {
    if (expandStatement != null)
      try { expandStatement.cancel(); } catch (SQLException ex) { }
  }

  /** Prekida SELECT upit koji se izvrsava u drugom threadu.
    */
  public void cancelSelect() {
    if (selectStatement != null)
      try { selectStatement.cancel(); } catch (SQLException ex) { }
  }

  /** Vraca novi jedinstveni broj iz datog brojaca (sekvence) */
  public int getNewNumber(String counterID) {
    int retVal = -1;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT last_value FROM InvCounters WHERE counter_id='" + counterID + "' FOR UPDATE");
      if (rset.next()) {
        retVal = rset.getInt(1) + 1;
        stmt.executeUpdate("UPDATE InvCounters SET last_value=last_value+1 WHERE counter_id='" + counterID + "'");
      }
      rset.close();
      stmt.close();
      conn.commit();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return retVal;
  }

  /** Proverava da li vec postoji dati inventarni broj */
  public boolean checkInvNumber(String inv, int docID) {
    String query = "SELECT sig FROM circ_docs WHERE ctlg_no='"+inv+"'";
    if (docID != -1)
      query += " AND NOT docid = "+docID;
    boolean retVal = false;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      if (rset.next())
        retVal = true;
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return retVal;
  }




  /** Zapravo ovo sortira, tako sto podeli niz na dva podniza, a onda ih rekurzivno sortira. */
  private static void qsort(int d, int g, ExpandPair[] a) {
    int i, j;
    ExpandPair k;
    // sort jednoelementnog niza
    if (d >= g)
      return;
    // sort dvoelementnog niza
    if (d + 1 == g) {
      if (a[d].getContent().compareTo(a[g].getContent()) > 0) {
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
      if (a[i].getContent().compareTo(a[d].getContent()) <= 0) {
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

 /** Sortira vektor.
   @param v Vektor koji treba sortirati.
  */
  private static Vector sortVector(Vector v) {
    Vector retVal = new Vector();
    ExpandPair niz[] = null;
    int i, j;
    niz = new ExpandPair[v.size()];
    v.copyInto(niz);
    qsort(0, v.size()-1, niz);
    for (i = 0; i < niz.length; i++)
      retVal.addElement(niz[i]);
    return retVal;
  }


  /** Database connection */
  private Connection conn;
  /** Referenca na objekat klase WordParser */
  private WordParser wp;
  /** Referenca na objekat klase DocumentParser */
  private DocumentParser dp;
  /** Skup svih definisanih prefiksa */
  private com.objectspace.jgl.HashSet allPrefixes;
  /** Skup svih baznih prefiksa */
  private com.objectspace.jgl.HashSet basePrefixes;
  /** String koji sadrzi delimitere reci */
  private String wordDelims;
  /** String koji sadrzi delimitere recenica */
  private String sentDelims;
  /** String koji sadrzi sve delimitere */
  private String allDelims;
  /** HashMap koji sadrzi mapiranje potpolje->prefiksi */
  private com.objectspace.jgl.HashMap prefixMap;
  /** Statement koji se koristi za EXPAND operaciju. */
  private Statement expandStatement = null;
  /** Statement koji se koristi za SELECT operaciju. */
  private Statement selectStatement = null;
  /** Konverter za karakter setove. */
  private UnimarcConverter conv;
}
