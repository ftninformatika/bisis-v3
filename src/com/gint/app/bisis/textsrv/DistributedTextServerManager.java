package com.gint.app.bisis.textsrv;

import java.rmi.*;
import java.sql.*;

/** Interfejs koji implementiraju menadzeri TextServera.
  */
public interface DistributedTextServerManager extends javax.ejb.EJBObject {
  public DistributedTextServer checkOut() throws SQLException, RemoteException;
  public void checkIn(int serverUID) throws RemoteException;
}