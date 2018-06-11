package com.gint.app.bisis.sharedsrv;

import java.rmi.*;
import java.rmi.registry.*;

public class ConsoleTask implements Task {
  // atribut koji definise maksimalan broj redova u log-u
  private int logTreshold = 1000;
  public int getLogTreshold() {
    return logTreshold;
  }
  public void setLogTreshold(int i) {
    logTreshold = i;
  }
  
  public void log(String msg) {
    System.out.println(msg);
  }
  
  public void start() {
    try {
      LocateRegistry.createRegistry(1099);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

//    System.setSecurityManager(new RMISecurityManager());
    try {
      RMITextServerPool t = new RMITextServerPool();
      t.init();
      Thread thr = new Thread(t);
      thr.start();
      Naming.bind("//localhost/RMITextServerPool", t);
      log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_MAINTASK_SERVERREADY"));
    } catch (Exception ex) {
      System.out.println(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_MAINTASK_SERVERREADY"));//"Server ne mo\u017ee da se prijavi na servis."
      ex.printStackTrace();
      System.exit(1);
    }
  }

  
  
}