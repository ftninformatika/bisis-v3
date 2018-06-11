package com.gint.app.bisis.editor.edit;

/**  Brojac SaveThreadova koji su aktivni; od toga zavisi
  *  tekst u statusnoj liniji.
  */
public class SaveCounter {

  public SaveCounter() {
    count = 0;
    lastSuccessful = true;
    lastDocID = -1;
  }

  public synchronized int increment() {
    return count++;
  }

  public synchronized int decrement() {
    if (count > 0)
      return --count;
    else
      return 0;
  }

  public synchronized int getCount() {
    return count;
  }

  public synchronized boolean isLastSuccessful() {
    return lastSuccessful;
  }

  public synchronized void setLastSuccessful(boolean b, int i) {
    lastSuccessful = b;
    lastDocID = i;
  }

  public synchronized int getLastDocID() {
    return lastDocID;
  }

  private int count;
  private boolean lastSuccessful;
  private int lastDocID;
}
