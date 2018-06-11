package com.gint.app.bisis.sharedsrv;

import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import com.gint.app.bisis.textsrv.*;

public class RMITextServer extends UnicastRemoteObject implements DistributedTextServer {

  private transient TextServer textServer;
  public TextServer getTextServer() {
    return textServer;
  }

  /**  Default konstruktor. */
  public RMITextServer() throws RemoteException {
    serverUID = ++serverUIDCounter;
  }

  public RMITextServer(TextServer ts) throws RemoteException {
    this();
    textServer = ts;
  }

  public int select(String query) throws ExpressionException {
    resetTimeoutCounter();
    int num = textServer.select(query);
//System.out.println("Nasao pogodaka: " + num);
    return num;
  }

  public Vector expand(String prefix, String query) {
    resetTimeoutCounter();
    return textServer.expand(prefix, query);
  }

  public boolean getDocsRange(int startIndex, int endIndex, int[] docIDs, String[] docs) {
    resetTimeoutCounter();
    return textServer.getDocsRange(startIndex, endIndex, docIDs, docs);
  }

  public Vector getDocsRangePacked(int startIndex, int endIndex) {
    resetTimeoutCounter();
    return textServer.getDocsRangePacked(startIndex, endIndex);
  }

  public String getDoc(int docID) {
    resetTimeoutCounter();
    return textServer.getDoc(docID);
  }

  public int getHitCount() {
    resetTimeoutCounter();
    return textServer.getHitCount();
  }

  public Vector getSubqueries() {
    resetTimeoutCounter();
    return textServer.getSubqueries();
  }

  public int getDocID(int hit) {
    resetTimeoutCounter();
    return textServer.getDocID(hit);
  }

  public void cancelSelect() {
    resetTimeoutCounter();
    textServer.cancelSelect();
  }

  public void cancelExpand() {
    resetTimeoutCounter();
    textServer.cancelExpand();
  }

  public void clearHits() {
    resetTimeoutCounter();
    textServer.clearHits();
  }

  // Za potrebe TextServerPool-a, svaki RMITextServer mora biti u stanju da odgovara na prizivku servera.
  private int timeoutCounter;
  public int getTimeoutCounter() {
    return timeoutCounter;
  }
  public void setTimeoutCounter(int t) {
    timeoutCounter = t;
  }
  /** Uvecava <b>timeoutCounter</b> za jedan. */
  public void incTimeoutCounter() {
    timeoutCounter++;
  }
  /** Postavlja timeoutCounter na 0. Poziva se prilikom izvrsenja svih metoda
    * koje se pozivaju sa klijentske strane. Time se TextServer proglasava aktivnim.
    */
  public void resetTimeoutCounter() {
    timeoutCounter = 0;
  }
  
  public int getServerUID() {
    return serverUID;
  }
  
  private int serverUID;
  private static int serverUIDCounter = 0;

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