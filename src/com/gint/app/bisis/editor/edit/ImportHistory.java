package com.gint.app.bisis.editor.edit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 * Specificno za beogradsku biblioteku - ako postoje tabele koje opisuju
 * preuzimanje zapisa iz centralne biblioteke, prilikom preuzimanja u njih
 * treba upisati odgovarajuce podatke
 * @author branko
 */
public class ImportHistory {
  
  /**
   * Vraca <code>true</code> ako tabele koje opisuju preuzimanje zapisa postoje.
   * @param conn Konekcija sa bazom podataka
   * @return <code>true</code> ako postoje tabele vezane za preuzimanje zapisa
   */
  public static boolean exists(Connection conn) {
    Statement stmt = null;
    ResultSet rset = null;
    boolean retVal = true;
    try {
      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT * FROM record_map");
      rset.next();
    } catch (Exception ex) {
      retVal = false;
    } finally {
      try {
        if (rset != null)
          rset.close();
        if (stmt != null)
          stmt.close();
      } catch (Exception ex1) {
      }
    }
    return retVal;
  }
  
  /**
   * Dodaje stavku koja predstavlja mapiranje lokalnog RN na originalni RN.
   * @param conn Konekcija sa bazom
   * @param myRN Lokalni RN
   * @param origRN RN zapisa u originalnoj bazi
   * @param date Datum poslednje sinhronizacije (= datum preuzimanja u ovom slucaju)
   * @return <code>true</code> ako je operacija uspesno izvrsena
   */
  public static boolean addToRecordMap(Connection conn, int myRN, int origRN, Date date) {
    if (myRN == 0) {
      System.err.println("RECORD_MAP: lokalni RN je 0");
      return false;
    }
    if (origRN == 0) {
      System.err.println("RECORD_MAP: originalni RN je 0");
      return false;
    }
    PreparedStatement stmt = null;
    boolean retVal = true;
    try {
      stmt = conn.prepareStatement("INSERT INTO record_map (my_record_rn, imported_record_rn, last_sync_date) VALUES (?, ?, ?)");
      stmt.setInt(1, myRN);
      stmt.setInt(2, origRN);
      stmt.setDate(3, new java.sql.Date(date.getTime()));
      stmt.executeUpdate();
      conn.commit();
    } catch (Exception ex) {
      retVal = false;
    } finally {
      try {
        if (stmt != null)
          stmt.close();
      } catch (Exception ex1) {
      }
    }
    return retVal;
  }
}
