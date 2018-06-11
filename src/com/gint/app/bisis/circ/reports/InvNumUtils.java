package com.gint.app.bisis.circ.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.RecordID;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class InvNumUtils {

  /**
   * Vraca listu zapisa (koji sadrze date inventarne brojeve) za datu listu 
   * inventarnih brojeva (stringova).
   * 
   * @param invNums Lista inventarnih brojeva
   * @param conn Konekcija sa bazom koja se koristi
   * @return Lista <code>Record</code> objekata koji reprezentuju zapise;
   *   element liste moze biti <code>null</code> ako ne postoji zapis sa datim
   *   inventarnim brojem
   */
  public static List getRecords(List invNums, Connection conn) {
    List retVal = new ArrayList();
    try {
      PreparedStatement pstmt1 = conn.prepareStatement(
        "SELECT doc_id FROM pref_in WHERE content=?");
      PreparedStatement pstmt2 = conn.prepareStatement(
      	"SELECT document FROM documents WHERE doc_id=?");
  
      Iterator it = invNums.iterator();
      while (it.hasNext()) {
        String invNum = (String)it.next();
        pstmt1.setString(1, invNum);
        ResultSet rset1 = pstmt1.executeQuery();
        if (!rset1.next())
          retVal.add(null);
        else {
          int docID = rset1.getInt(1);
          pstmt2.setInt(1, docID);
          ResultSet rset2 = pstmt2.executeQuery();
          if (!rset2.next())
            retVal.add(null);
          else {
            String doc = rset2.getString(1);
            Record rec = RecordFactory.fromUNIMARC(doc);
            rec.setRecordID(new RecordID(0, docID));
            retVal.add(rec);
          }
          rset2.close();
        }
        rset1.close();
      }
      pstmt2.close();
      pstmt1.close();
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return retVal;
  }
}