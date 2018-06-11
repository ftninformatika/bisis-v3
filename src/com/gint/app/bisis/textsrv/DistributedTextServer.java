package com.gint.app.bisis.textsrv;

import java.rmi.*;
import java.util.*;

/** Interfejs kojim se TextServer predstavlja udaljenom klijentu. Ovaj moze da koristi
  * samo ovde navedene metode.
  */
public interface DistributedTextServer extends javax.ejb.EJBObject {
  public int select(String query) throws ExpressionException, RemoteException;
  public Vector expand(String prefix, String query) throws RemoteException;
  public boolean getDocsRange(int startIndex, int endIndex, int[] docIDs, String[] docs) throws RemoteException;
  public Vector getDocsRangePacked(int startIndex, int endIndex) throws RemoteException;
  public String getDoc(int docID) throws RemoteException;
  public int getHitCount() throws RemoteException;
  public Vector getSubqueries() throws RemoteException;
  public int getDocID(int hit) throws RemoteException;
  public void cancelSelect() throws RemoteException;
  public void cancelExpand() throws RemoteException;
  public void clearHits() throws RemoteException;
  public int getServerUID() throws RemoteException;
}
