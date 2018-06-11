package com.gint.app.bisis.textsrv;

import java.rmi.*;
import java.util.Vector;

/** Wrapper klasa za TextServer. */
public class RemoteTextServer extends TextServer {
  private DistributedTextServer textServer;

  public RemoteTextServer(DistributedTextServer ts) {
    textServer = ts;
  }
  public DistributedTextServer getTextServer() {
    return textServer;
  }

  public int select(String query) throws ExpressionException {
    try {
      return textServer.select(query);
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return -1;
  }

  public Vector expand(String prefix, String query) {
    try {
      return textServer.expand(prefix, query);
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public boolean getDocsRange(int startIndex, int endIndex, int[] docIDs, String[] docs) {
    try {
      return textServer.getDocsRange(startIndex, endIndex, docIDs, docs);
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public Vector getDocsRangePacked(int startIndex, int endIndex) {
    try {
      return textServer.getDocsRangePacked(startIndex, endIndex);
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public String getDoc(int docID) {
    try {
      return textServer.getDoc(docID);
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public int getHitCount() {
    try {
      return textServer.getHitCount();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return -1;
  }

  public Vector getSubqueries() {
    try {
      return textServer.getSubqueries();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public int getDocID(int hit) {
    try {
      return textServer.getDocID(hit);
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
    return -1;
  }

  public void cancelSelect() {
    try {
      textServer.cancelSelect();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }

  public void cancelExpand() {
    try {
      textServer.cancelExpand();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }

  public void clearHits() {
    try {
      textServer.clearHits();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }
}
