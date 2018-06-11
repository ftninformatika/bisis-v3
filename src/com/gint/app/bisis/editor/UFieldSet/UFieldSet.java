package com.gint.app.bisis.editor.UFieldSet;

import java.sql.*;
import java.util.Vector;
import java.util.Enumeration;
import com.objectspace.jgl.*;

import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;

import com.gint.util.string.Charset;

public class UFieldSet {
  Charset conv = new Charset();

  /** Konstruktor. Puni iz baze podataka sve strukture u memoriji.
    * @exception SQLException Baca izuzetak ako ne uspe da prevuce podatke iz baze.
  */
  public UFieldSet(Connection newConn)
    throws SQLException {
    Connection conn = newConn;

    Statement stmt;
    ResultSet rset;

    //
    // Get first indicators into temporary tables
    //
    tFirstIndicators = new HashMap();
    stmt = conn.createStatement();
    rset = stmt.executeQuery("select po_poljeid, prindid, pi_naziv from prvi_indikator");
    while (rset.next()) {
      // !@#$
      String key = Charset.YUSCII2Unicode(rset.getString(1).trim());
      HashSet hs = (HashSet)tFirstIndicators.get(key);
      if(hs == null)
        hs = new HashSet();
      else
        tFirstIndicators.remove(key);
      String prindid = Charset.YUSCII2Unicode(rset.getString(2));
      if (prindid.length() > 1) {
         prindid = new String(prindid.substring(0, 1));
      }
      hs.add(new UInd(prindid, Charset.YUSCII2Unicode(rset.getString(3))));
      tFirstIndicators.add(key, hs);
    }
    rset.close();
    stmt.close();

    tSecondIndicators = new HashMap(true);
    stmt = conn.createStatement();
    rset = stmt.executeQuery("select po_poljeid, drindid, di_naziv from drugi_indikator");
    while (rset.next()) {
      // !@#$
      String key = Charset.YUSCII2Unicode(rset.getString(1).trim());
      HashSet hs = (HashSet)tSecondIndicators.get(key);
      if(hs == null)
        hs = new HashSet();
      else
        tSecondIndicators.remove(key);
      String prindid = Charset.YUSCII2Unicode(rset.getString(2));
      if (prindid.length() > 1) {
         prindid = new String(prindid.substring(0, 1));
      }
      hs.add(new UInd(prindid, Charset.YUSCII2Unicode(rset.getString(3))));
      tSecondIndicators.add(key, hs);
    }
    rset.close();
    stmt.close();

    //
    // Get subfields into temporary tables
    //
    tSubfields = new HashMap();
    stmt = conn.createStatement();  // 1             2           3               4             5         6    7          8             9        10
    rset = stmt.executeQuery("select po_poljeid, potpoljaid, obaveznostpp, ponovljivostpp, pp_naziv, duzina, status, defvrednost, tes_tesid, kontrola_kontrid  from potpolja");
    while (rset.next()) {
      // !@#$
      String key = Charset.YUSCII2Unicode(rset.getString(1).trim());
      HashSet hs = (HashSet)tSubfields.get(key);
      if(hs == null)
        hs = new HashSet();
      else
        tSubfields.remove(key);
      // !@#$
      hs.add(
        new USubfield(key, Charset.YUSCII2Unicode(rset.getString(2).trim()), rset.getInt(6), rset.getBoolean(3),
          rset.getBoolean(4), Charset.YUSCII2Unicode(rset.getString(5).trim()), Charset.YUSCII2Unicode(rset.getString(7).trim()),
          Charset.YUSCII2Unicode(rset.getString(8)), "" + rset.getInt(9), rset.getInt(10))); // @#$ tes_tesid(9)
      tSubfields.add(key, hs);
    }
    rset.close();
    stmt.close();

    //
    // Get Control Types into temporary tables
    //
    tControlTypes = new HashMap();
    stmt = conn.createStatement();
    rset = stmt.executeQuery("select kontrid, ko_naziv from tip_kontrole");
    while (rset.next()) {
      int iKey = rset.getInt(1);
      String key = new String("" + iKey);
      tControlTypes.add(key, new UControlType(iKey, Charset.YUSCII2Unicode(rset.getString(2))));
    }
    rset.close();
    stmt.close();

    //
    // Get internal codes into temporary tables
    //
    tInternalCodes = new HashMap();
    stmt = conn.createStatement();  // 1              2               3       4
    rset = stmt.executeQuery("select ppo_potpoljaid, ppo_po_poljeid, isid, is_naziv from interni_sifarnik order by ppo_po_poljeid,ppo_potpoljaid,isid ASC");
    while (rset.next()) {
      // !@#$
      String key = Charset.YUSCII2Unicode(rset.getString(1).trim())
        + Charset.YUSCII2Unicode(rset.getString(2).trim()); // complex key:subfield+field
      HashSet hs = (HashSet)tInternalCodes.get(key);
      if(hs == null)
        hs = new HashSet();
      else
        tInternalCodes.remove(key);
      // !@#$
      hs.add(new UInternalCodes(Charset.YUSCII2Unicode(rset.getString(3).trim()), Charset.YUSCII2Unicode(rset.getString(4))));
      tInternalCodes.add(key, hs);
    }
    rset.close();
    stmt.close();

    //
    // Get SubSubfiels into temporary tables
    //
    tSubSubfields = new HashMap();
    stmt = conn.createStatement();  // 1              2               3           4        5            6          7          8            9         10
    rset = stmt.executeQuery("select ppo_potpoljaid, ppo_po_poljeid, potpid, potp_naziv, potpduzina, potponov, potpobavez, statusppp, potpdefvr, kontrola_kontrid from potpotpolja");
    while (rset.next()) {
      // !@#$
			String k1 = Charset.YUSCII2Unicode(rset.getString(1).trim());
			String k2 = Charset.YUSCII2Unicode(rset.getString(2).trim());
			String k3 = Charset.YUSCII2Unicode(rset.getString(3).trim());
      String key = k1 + k2; // complex key: subfield+field
      HashSet hs = (HashSet)tSubSubfields.get(key);
      if(hs == null)
        hs = new HashSet();
      else
        tSubSubfields.remove(key);
      // !@#$
      hs.add(
        new USubSubfield(k2, k1, k3,
				  rset.getInt(5),
				  rset.getBoolean(7), rset.getBoolean(6),
					Charset.YUSCII2Unicode(rset.getString(4).trim()),
					Charset.YUSCII2Unicode(rset.getString(8).trim()),
					Charset.YUSCII2Unicode(rset.getString(9)), rset.getInt(10)));
      tSubSubfields.add(key, hs);
    }
    rset.close();
    stmt.close();

    //
    // Get Subfield codes into temporary tables
    //
    tSubfieldCodes = new HashMap();
    stmt = conn.createStatement();  // 1             2               3                       4          5
    rset = stmt.executeQuery("select ppp_potpid, ppp_ppo_potpoljaid, ppp_ppo_po_poljeid, sifpotpid, sifpotp_naziv from sifarnik_potpotpolja");
    while (rset.next()) {
      // !@#$
			String k1 = Charset.YUSCII2Unicode(rset.getString(1).trim());
			String k2 = Charset.YUSCII2Unicode(rset.getString(2).trim());
			String k3 = Charset.YUSCII2Unicode(rset.getString(3).trim());
      String key = k1 + k2 + k3; // complex key: Subsubfield+subfield+field
      HashSet hs = (HashSet)tSubfieldCodes.get(key);
      if(hs == null) {
        hs = new HashSet();
      }
      else
        tSubfieldCodes.remove(key);
      hs.add(
        new USubfieldCodes(Charset.YUSCII2Unicode(rset.getString(4).trim()),
          Charset.YUSCII2Unicode(rset.getString(5))));
      //tInternalCodes.add(key, hs);
      tSubfieldCodes.add(key, hs);
    }
    rset.close();
    stmt.close();

    //
    // Get ExtCodeTypes into temporary tables
    //
    tExtCodeTypes = new HashMap();
    stmt = conn.createStatement();  // 1       2
    rset = stmt.executeQuery("select tesid, tes_naziv from tipekst_sifarnika");
    while (rset.next()) {
			String key = new String("" + rset.getInt(1)); // @#$ bio String
      tExtCodeTypes.add(key, new UExtCodeTypes(key, Charset.YUSCII2Unicode(rset.getString(2))));
    }
    rset.close();
    stmt.close();

    //
    // Get ExternalCodes into temporary tables
    //
    tExternalCodes = new HashMap();
    stmt = conn.createStatement();  //   1       2       3
    rset = stmt.executeQuery("select tes_tesid, esid, es_naziv from eksterni_sifarnik order by tes_tesid,esid DESC");
    while (rset.next()) {
			String key = new String("" + rset.getInt(1)); // !W@#Izmena, bio je String
      HashSet hs = (HashSet)tExternalCodes.get(key);
      if(hs == null)
        hs = new HashSet();
      else
        tExternalCodes.remove(key);
      // !@#$
      hs.add(new UExternalCodes(key, Charset.YUSCII2Unicode(rset.getString(2).trim()), Charset.YUSCII2Unicode(rset.getString(3))));
      tExternalCodes.add(key, hs);
    }
    rset.close();
    stmt.close();

    //
    // Get fields
    //
    fieldMap = new HashMap();

    stmt = conn.createStatement();    //1         2              3             4          5        6       7        8        9
    rset = stmt.executeQuery("select poljeid, obaveznost, ponovljivost, sekundarnost, po_naziv, opis_pi, opis_di, def_pi, def_di from polje");
    while (rset.next()) {
      // !@#$
      String key = Charset.YUSCII2Unicode(rset.getString(1).trim());
      String prindid = Charset.YUSCII2Unicode(rset.getString(8));
      if (prindid != null && prindid.length() > 1) {
         prindid = new String(prindid.substring(0, 1));
      }
      String drindid = Charset.YUSCII2Unicode(rset.getString(9));
      if (drindid != null && drindid.length() > 1) {
         drindid = new String(drindid.substring(0, 1));
      }
      fieldMap.add(key, new UField(key, rset.getBoolean(2), rset.getBoolean(3), rset.getBoolean(4),
                   Charset.YUSCII2Unicode(rset.getString(5)), Charset.YUSCII2Unicode(rset.getString(6)), Charset.YUSCII2Unicode(rset.getString(7)), prindid,
                   drindid,
                   tFirstIndicators, tSecondIndicators, tSubfields, tControlTypes, tInternalCodes,
									 tSubSubfields, tSubfieldCodes, tExtCodeTypes, tExternalCodes));
    }
    rset.close();
    stmt.close();
  }

  /** Konstruktor. Puni iz XML dokumenta sve strukture u memoriji. */
  public UFieldSet(DocumentImpl document) throws Exception {
    String key;
    Node curr;
    NamedNodeMap attrs;

    tExtCodeTypes = new HashMap();
    tExternalCodes = new HashMap();
    NodeList uExtCodeTypes = document.getElementsByTagName("UExtCodeTypes");
    for (int i = 0; i < uExtCodeTypes.getLength(); i++) {
      curr = uExtCodeTypes.item(i); 
      attrs = curr.getAttributes();
			key = new String(attrs.getNamedItem("name").getNodeValue());
      tExtCodeTypes.add(key, new UExtCodeTypes(key, attrs.getNamedItem("desc").getNodeValue()));

      NodeList uExternalCodes = curr.getChildNodes();
      HashSet hs = new HashSet();
      for (int j = 0; j < uExternalCodes.getLength(); j++) {
        curr = uExternalCodes.item(j); 
        attrs = curr.getAttributes();
  			key = new String(attrs.getNamedItem("type").getNodeValue());
        hs.add(new UExternalCodes(key, attrs.getNamedItem("name").getNodeValue(),
                                  attrs.getNamedItem("desc").getNodeValue()));
      }
      tExternalCodes.add(key, hs);
    }
    
    fieldMap = new HashMap();
    NodeList uFields = document.getElementsByTagName("UField");
    for (int i = 0; i < uFields.getLength(); i++) {
      curr = uFields.item(i);
      attrs = curr.getAttributes();
      fieldMap.add(attrs.getNamedItem("name").getNodeValue(),
                   new UField(curr, tExtCodeTypes, tExternalCodes));
    }
  }

  public void setFieldMap(HashMap fieldMap) {
    this.fieldMap = fieldMap;
  }

  public HashMap getFieldMap() {
    return fieldMap;
  }

// FIELDS #########################################################################
  /** Vraca objekat klase UField na osnovu naziva polja. */
  public UField getField(String field) {
    return ((UField)fieldMap.get(field));
  }

  /** Vraca opis polja na osnovu naziva polja. */
  public String getFieldName(String field) {
    String retVal = new String("");
    UField f = getField(field);
    if(f != null) {
      retVal = Charset.YUSCII2Unicode(f.getName());
    }
    return retVal;
  }

  /** Vraca <i>true</i> ako prosledjeno polje sadrzi sekundarna polja. */
  public boolean containsSecondaryFields(String field) {
  	boolean retVal = false;
  	if(field.equals("421"))
    	retVal = true;
    else if(field.equals("423"))
    	retVal = true;
    else if(field.equals("464"))
    	retVal = true;
    else if(field.equals("469"))
      retVal = true;

   	return retVal;
  }

  /** Vraca naziv prvog indikatora prosledjenog polja. */
  public String getDefFirstIndicator(String field) {
    String retVal = " ";
    UField f = getField(field);
    if(f != null) {
      if (f.getDef_fi() != null)
        retVal = new String(f.getDef_fi());
    }
    return retVal;
  }
  /** Vraca naziv drugog indikatora prosledjenog polja. */
  public String getDefSecondIndicator(String field) {
    String retVal = " ";
    UField f = getField(field);
    if(f != null) {
      if (f.getDef_si() != null)
        retVal = new String(f.getDef_si());
    }
    return retVal;
  }

  // SUBFIELDS  ####################################################################
  /** Vraca objekat klase USubfield na osnovu naziva polja i potpolja. */
  public USubfield getSubfield(String field, String subfield) {
    USubfield retVal = null;
    UField f = (UField)fieldMap.get(field);
    if(f != null) {
      HashMap subfields = f.getSubfields();
      if(subfields != null)
        retVal = (USubfield)subfields.get(subfield);
    } // if
    return retVal;
  }

  /** Vraca <i>true</i> ako prosledjeno potpolje sadrzi potpotpolja. */
	public boolean containsSubSubfields(String field, String subfield) {
  	boolean retVal = false;
    USubfield sf = getSubfield(field, subfield);
    if(sf != null) {
      if(sf.getSubSubfields() != null)
      	retVal = true;
    } // if
    return retVal;
  }

  /** Vraca duzinu potpolja. */
  public int getSubfieldLength(String field, String subfield) {
    int retVal = 0;
    USubfield sf = getSubfield(field, subfield);
    if(sf != null) {
      retVal = sf.getLength();
    }
    return retVal;
  }

  /** Vraca naziv potpolja. */
  public String getSubfieldName(String field, String subfield) {
    String retVal = new String("");
    USubfield sf = getSubfield(field, subfield);
    if(sf != null) {
      retVal = Charset.YUSCII2Unicode(sf.getName());
    }
    return retVal;
  }

  /** Vraca spisak naziva potpolja za zadato polje. */
  public Vector getSubfieldNameList(String field) {
    Vector retVal = null;
    UField f = getField(field);
    if(f != null) {
      retVal = new Vector();
      HashMap hm = f.getSubfields();
      if(hm != null) {
        Enumeration e = hm.elements();
	      while(e.hasMoreElements()) {
	        retVal.addElement(((USubfield)e.nextElement()).getName());
	      }
      }
    }
    return retVal;
  }

  /** Vraca spisak ID-ova potpolja za zadato polje. */
  public Vector getSubfieldIDList(String field) {
    Vector retVal = null;
    UField f = getField(field);
    if(f != null) {
      retVal = new Vector();
      HashMap hm = f.getSubfields();
      if(hm != null) {
        Enumeration e = hm.elements();
      	while(e.hasMoreElements()) {
	       retVal.addElement(((USubfield)e.nextElement()).getSubfieldID());
	      }
      }
    }
    return retVal;
  }

  /** Vraca spisak ID-ova i naziva potpolja za zadato polje, razdvojenih separatorom. */
  public Vector getSubfieldList(String field, String separator) {
    Vector retVal = null;
    UField f = getField(field);
    if(f != null) {
      retVal = new Vector();
      HashMap hm = f.getSubfields();
      if(hm != null) {
	      Enumeration e = hm.elements();
	      while(e.hasMoreElements()) {
	        USubfield sf = (USubfield)e.nextElement();
	        retVal.addElement(sf.getSubfieldID() + separator + sf.getName());
	      }
      }
    }
    return retVal;
  }

  // SUBSUBFIELDS  ####################################################################
  /** Vraca objekat klase USubSubfield na osnovu naziva polja, potpolja i potpotpolja. */
  public USubSubfield getSubSubfield(String field, String subfield, String subSubfield) {
    USubSubfield retVal = null;
    USubfield sf = getSubfield(field, subfield);
    if(sf != null) {
      HashMap subSubfields = sf.getSubSubfields();
      if(subSubfields != null)
        retVal = (USubSubfield)subSubfields.get(subSubfield);
    } // if
    return retVal;
  }

  /** Vraca duzinu potpotpolja. */
  public int getSubSubfieldLength(String field, String subfield, String subSubfield) {
    int retVal = 0;
    USubSubfield ssf = getSubSubfield(field, subfield, subSubfield);
    if(ssf != null) {
      retVal = ssf.getLength();
    }
    return retVal;
  }

  /** Vraca naziv potpotpolja. */
  public String getSubSubfieldName(String field, String subfield, String subSubfield) {
    String retVal = new String("");
    USubSubfield ssf = getSubSubfield(field, subfield, subSubfield);
    if(ssf != null)
      retVal = Charset.YUSCII2Unicode(ssf.getName());
    return retVal;
  }

  /** Vraca spisak ID-ova i naziva potpotpolja za zadato polje, razdvojenih separatorom. */
  public Vector getSubSubfieldList(String field, String subfield, String separator) {
    Vector retVal = null;
    USubfield sf = getSubfield(field, subfield);
    if(sf != null) {
      retVal = new Vector();
      HashMap hm = sf.getSubSubfields();
      if(hm != null) {
      	Enumeration e = hm.elements();
	      while(e.hasMoreElements()) {
	        USubSubfield ssf = (USubSubfield)e.nextElement();
	        retVal.addElement("  "+ssf.getSubSubfieldID() + separator + ssf.getName());
	      }
      }
    }
    return retVal;
  }
  /** Vraca spisak ID-ova potpotpolja za zadato polje, razdvojenih separatorom. */
  public Vector getSubSubfieldList(String field, String subfield) {
    Vector retVal = null;
    USubfield sf = getSubfield(field, subfield);
    if(sf != null) {
      retVal = new Vector();
      HashMap hm = sf.getSubSubfields();
      if(hm != null) {
        Enumeration e = hm.elements();
	      while(e.hasMoreElements()) {
	        USubSubfield ssf = (USubSubfield)e.nextElement();
	        retVal.addElement(ssf.getSubSubfieldID());
      	}
      }
    }
    return retVal;
  }

// ###############################################################
  /** HashMap-a sa uredjenim parovima (naziv polja, polje). */
  private HashMap fieldMap;

  private HashMap  tFirstIndicators;
  private HashMap  tSecondIndicators;
  private HashMap  tSubfields;
  private HashMap  tControlTypes;
  private HashMap  tInternalCodes;
  private HashMap  tSubSubfields;
  private HashMap  tSubfieldCodes;
  private HashMap  tExtCodeTypes;
  private HashMap  tExternalCodes;
  
  public void printString() {
    Enumeration en;      
    String key;

    en = fieldMap.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      System.out.println(key + "->" + fieldMap.get(key) + "\n");
    }
/*
    en = tFirstIndicators.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(I): " + tFirstIndicators.get(key) + "\n");
    }

    en = tSecondIndicators.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(II): " + tSecondIndicators.get(key) + "\n");
    }

    en = tSubfields.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(sf): " + tSubfields.get(key) + "\n");
    }

    en = tControlTypes.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(ctrTyp): " + tControlTypes.get(key) + "\n");
    }

    en = tInternalCodes.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(intCod): " + tInternalCodes.get(key) + "\n");
    }

    en = tSubSubfields.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(ssf): " + tSubSubfields.get(key) + "\n");
    }

    en = tSubfieldCodes.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      sb.append(key + "(sfCod): " + tSubfieldCodes.get(key) + "\n");
    }
*/
    en = tExtCodeTypes.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      System.out.println(key + "(extCodTyp): " + tExtCodeTypes.get(key) + "\n");
    }

    en = tExternalCodes.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      System.out.println(key + "(extCod): " + tExternalCodes.get(key) + "\n");
    }
  }

  /** Kreira XML stablo na osnovu atributa. */
  public void createXML(DocumentImpl document) {
    Node root = document.createElement("UFieldSet");
    document.appendChild(root);
    
    Enumeration en;
    String key;
    
    en = fieldMap.keys();
    while (en.hasMoreElements()) {
      key = (String)en.nextElement();
      UField uf = (UField)fieldMap.get(key);
      root.appendChild(uf.createXML(document));
    }

    if (tExtCodeTypes != null) {
      en = tExtCodeTypes.keys();
      while (en.hasMoreElements()) {
        key = (String)en.nextElement();
        UExtCodeTypes uect = (UExtCodeTypes)tExtCodeTypes.get(key);
        root.appendChild(uect.createXML(document, tExternalCodes));
      }
    }
  }
  
  public static void main(String[] args) {
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
      UFieldSet u = new UFieldSet(conn);
//      u.printString();
      DocumentImpl document = new DocumentImpl();
      u.createXML(document);
//      XMLUtils.print(document);
      u = new UFieldSet(document);
//      u.printString();
    } catch ( Exception e ) {
       e.printStackTrace();
    }
  }
}
