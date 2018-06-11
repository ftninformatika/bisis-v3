package com.gint.app.bisis.sharedsrv;

import java.util.*;
import java.sql.*;
import java.rmi.*;
import java.rmi.server.*;

import com.gint.app.bisis.textsrv.*;
import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;

  // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  //    Deo koda koji se bavi TextServerPool-om.
  // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
public class RMITextServerPool extends UnicastRemoteObject implements Runnable, DistributedTextServerManager {
  /** Broj TextServera koji se unapred naprave. */
  private static int preconnectCount;
  /** Tekuci broj TextServera. */
  private int connectCount;
  /** Maksimalan broj TextServera koji ostaje otvoren u idle stanju. */
  private static int maxIdleConn;
  /** Maksimalan broj TextServera koji se moze napraviti. */
  private static int maxConn;
  /** Vremenski period izmedju uzastopnih provera TextServera koji su zauzeti. */
  private static int timeToCheckDeadTextServers;
  /** Broj provera TextServera nakon kojeg se TextServer proglasava neaktivnim. */
  private static int limit;
  /** Vektor sa zauzetim TextServerima. */
  private Vector usedTextServers;
  /** Vektor sa slobodnim TextServerima. */
  private Vector freeTextServers;
  /** Klasa JDBC drajvera. */
  private static String jdbcDriver;
  /** URL do DB servera. */
  private static String url;
  /** KOrisnicko ime na DB serveru. */
  private static String dbUsername;
  /** Sifra na DB serveru. */
  private static String dbPassword;

  /**  Default konstruktor. */
  public RMITextServerPool() throws RemoteException {
  }

  /** Inicijalizuje TextServerPool.
    * Konfiguracione podatke cita iz TextServerPool.properties fajla.
    * Kreira zadati broj TextServera i smesta ih u <b>freeTextServers</b> vektor.
    */
  public void init() throws ClassNotFoundException, SQLException, RemoteException {
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
    String jdbcDriver          = iniFile.getString("sharedsrv", "driver");
    String sURL                = iniFile.getString("sharedsrv", "url");
    String sDbUsername         = iniFile.getString("sharedsrv", "username");
    String sDbPassword         = iniFile.getString("sharedsrv", "password");
    String sPreconnectCount    = iniFile.getString("sharedsrv", "preconnectCount");
    String sMaxIdleTextServers = iniFile.getString("sharedsrv", "maxIdleTextServers");
    String sMaxTextServers     = iniFile.getString("sharedsrv", "maxTextServers");
    String sTimeToCheckDeadTextServers = iniFile.getString("sharedsrv", "timeToCheckDeadTextServers");
    String sLimit              = iniFile.getString("sharedsrv", "limit");
    int preconnectCount = 0;
    int maxIdleTextServers = 0;
    int maxTextServers = 0;
    int timeToCheckDeadTextServers = 300;
    int limit = 4;
    Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_TEXTSRVPOOLINITIALIZED"));
    try {
      preconnectCount = Integer.parseInt(sPreconnectCount);
      maxIdleTextServers = Integer.parseInt(sMaxIdleTextServers);
      maxTextServers = Integer.parseInt(sMaxTextServers);
      timeToCheckDeadTextServers = Integer.parseInt(sTimeToCheckDeadTextServers) * 1000;
      limit = Integer.parseInt(sLimit);
    } catch (NumberFormatException ex) {
      Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_BADNUMBER"));
    }
    try {
      setup(sURL, jdbcDriver, preconnectCount, maxIdleTextServers, maxTextServers, timeToCheckDeadTextServers, limit, sDbUsername, sDbPassword);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    freeTextServers = new Vector();
    usedTextServers = new Vector();
    Class.forName(jdbcDriver);
    for (int i = 0; i < preconnectCount; i++) {
      Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
      conn.setAutoCommit(false);
      freeTextServers.addElement(new RMITextServer(new TextServer(Environment.getDBType(), conn, url, dbUsername, dbPassword)));
    }
    connectCount = preconnectCount;
    Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_SERVERAPP_SUCCESSFULLY"));
    Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_POOLREADY"));
  }

  /** Podesava parametre TextServerPool-a. */
  public static void setup(String URL1, String jdbcDrv,int preconnectCount1,int maxIdleConn1, int maxConn1, int timeToCheckDeadTextServers1, int limit1, String dbUsername1, String dbPassword1) {
    url = URL1;
    preconnectCount = preconnectCount1;
    maxIdleConn = maxIdleConn1;
    maxConn = maxConn1;
    jdbcDriver = jdbcDrv;
    timeToCheckDeadTextServers = timeToCheckDeadTextServers1;
    limit = limit1;
    dbUsername = dbUsername1;
    dbPassword = dbPassword1;
  }

  /** Uzima slobodan TextServer iz Pool-a i prosledjuje ga korisniku. To znaci da ga uzima iz
    * <b>freeTextServers</b> vektora, premesta ga u <b>usedTextServers</b> vektor
    * i vraca taj TextServer korisniku.
    */
  public DistributedTextServer checkOut() throws SQLException, RemoteException {
    synchronized (this) {
      Connection conn = null;
      RMITextServer ts = null;
      if (freeTextServers.size() > 0) {
        ts   = (RMITextServer)freeTextServers.elementAt(0);
        freeTextServers.removeElementAt(0);
        usedTextServers.addElement(ts);
      } else {
        if (connectCount < maxConn) {
          conn = DriverManager.getConnection(Environment.getURL(), dbUsername, dbPassword);
          conn.setAutoCommit(false);
          ts   = new RMITextServer(new TextServer(Environment.getDBType(), conn, url, dbUsername, dbPassword));
          usedTextServers.addElement(ts);
          connectCount++;
        } else {
          try {
            wait();
            ts   = (RMITextServer)freeTextServers.elementAt(0);
            freeTextServers.removeElementAt(0);
            usedTextServers.addElement(ts);
          } catch (InterruptedException ex) {
            ex.printStackTrace();
          }
        }
      }
Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_TOOKTXTSERVER"));
      dump();
      return ts;
    }
	}

  /** Vraca TextServer u Pool. To znaci da ga uzima iz <b>usedTextServers</b> vektora i
    * premesta ga u <b>freeTextServers</b> vektor. Ako ima potrebe, oslobadja onoliko
    * TextServera, dok ne dostigne <b>maxIdleConn</b>.
    */
  public void checkIn(DistributedTextServer aTs) throws RemoteException {
    synchronized (this) {
      if (aTs ==  null)
        return;
      if (usedTextServers.removeElement(aTs)) {
        aTs.clearHits();
        freeTextServers.addElement(aTs);
        while (freeTextServers.size() > maxIdleConn) {
          int lastOne = freeTextServers.size() - 1;
          Connection conn = ((RMITextServer)freeTextServers.elementAt(lastOne)).getTextServer().conn;
          try {
            conn.close();
          } catch (SQLException ex) {
          }
          freeTextServers.removeElementAt(lastOne);
        }
        notify();
      }
  Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_RETRIEVEDTXTSERVER"));
  dump();
    }
	}

  /** Vraca TextServer u Pool. To znaci da ga uzima iz <b>usedTextServers</b> vektora i
   * premesta ga u <b>freeTextServers</b> vektor. Ako ima potrebe, oslobadja onoliko
   * TextServera, dok ne dostigne <b>maxIdleConn</b>.
   */
 public void checkIn(int serverUID) throws RemoteException {
   synchronized (this) {
     if (serverUID == 0)
       return;
     int pos = 0;
     RMITextServer rts = null;
     for (int i = 0; i < usedTextServers.size(); i++) {
       rts = (RMITextServer)usedTextServers.elementAt(i);
       if (rts.getServerUID() == serverUID) {
         pos = i;
         break;
       }
     }
     if (usedTextServers.removeElement(rts)) {
       rts.clearHits();
       freeTextServers.addElement(rts);
       while (freeTextServers.size() > maxIdleConn) {
         int lastOne = freeTextServers.size() - 1;
         Connection conn = ((RMITextServer)freeTextServers.elementAt(lastOne)).getTextServer().conn;
         try {
           conn.close();
         } catch (SQLException ex) {
         }
         freeTextServers.removeElementAt(lastOne);
       }
       notify();
     }
 Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_RETRIEVEDTXTSERVER"));
 dump();
   }
 }

  /** Dampuje na konzolu stanje Pool-a. */
  private void dump() {
    Environment.log("( ");
    Environment.log("preconnectCount: " + preconnectCount);
    Environment.log(", connectCount: " + connectCount);
    Environment.log(", maxIdleConn: " + maxIdleConn);
    Environment.log(", maxConn: " + maxConn);
    Environment.log(", usedTextServers size: " + usedTextServers.size());
    Environment.log(", freeTextServers size: " + freeTextServers.size());
    Environment.log(" )\n");
  }

  /** Vektor kandidata za uklanjanje. TexServer-i koji su neaktivni moraju se
    * ukloniti iz <b>usedTextServers</b> vektora i njihove konekcije ka bazi zatvoriti.
    */
  private Vector toRemove = new Vector();

  /** Thread metoda koja na svakih <b>timeToCheckDeadTextServers</b> sekundi proverava
    * Pool i ako nadje TextServer koji je <b>limit</b> puta bio prozvan, proglasava ga
    * neaktivnim i izbacuje ga iz <b>usedTextServers</b> vektora, gasi mu konekciju i
    * pravi jedan nov TextServer koja ubacuje u <b>freeTextServers</b> vektor.
    */
  public void run() {
    while(true) {
      toRemove.clear();

      // prodjemo kroz sve used Text Servere i pogasimo konekcije sa bazom za one koji su istekli, a zatim
      // ih dodamo u vektor toRemove iz kojeg cemo redom da ih poizbacujemo. Za svaki izbaceni tekstserver
      // napravicemo jedan nov.
      for (int i = 0; i < usedTextServers.size(); i++) {
        RMITextServer t = (RMITextServer)usedTextServers.elementAt(i);
        t.incTimeoutCounter();
        if (t.getTimeoutCounter() == limit) {
          // ubijemo ovaj TextServer jer je istekao timeout
          Connection conn = t.getTextServer().conn;
          try {
            conn.close();
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
          t.getTextServer().conn = null;
          toRemove.addElement(t);
        }
      }

      // uklonimo sve kandidate
      for (int i = 0; i < toRemove.size(); i++) {
        TextServer t = (TextServer)toRemove.elementAt(i);
        usedTextServers.removeElement(t);
        // dodamo nov
        try {
          Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
          conn.setAutoCommit(false);
          freeTextServers.addElement(new TextServer(Environment.getDBType(), conn, url, dbUsername, dbPassword));
          Environment.log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_RMITXTSRVPOOL_REMOVEANDADD"));
          dump();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      // sacekamo zadati broj sekundi pre ponovne provere
      try { Thread.sleep(timeToCheckDeadTextServers); } catch (Exception ex) {}
    }
  }
  // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
  // Ovaj deo se odnosi na EJB!
  // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
  public void remove () throws java.rmi.RemoteException {
  }

  public boolean isIdentical(javax.ejb.EJBObject obj) throws java.rmi.RemoteException {
    return false;
  }

  public javax.ejb.Handle getHandle() throws java.rmi.RemoteException {
    return null;
  }

  public java.lang.Object getPrimaryKey() throws java.rmi.RemoteException {
    return null;
  }

  public javax.ejb.EJBHome getEJBHome() throws java.rmi.RemoteException {
    return null;
  }
}
