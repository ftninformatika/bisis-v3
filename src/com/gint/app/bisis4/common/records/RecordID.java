package com.gint.app.bisis4.common.records;

import java.io.Serializable;

/**
 * Represents a record identifier.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class RecordID implements Serializable {

  /**
   * Constructs a default record identifier.
   */
  public RecordID() {
    libraryID = 0;
    localRecordID = 0;
    libraryAware = true;
  }
  /**
   * Constructs the identifier with the given values.
   * @param libraryID
   * @param localRecordID
   */
  public RecordID(int libraryID, int localRecordID) {
    this.libraryID = libraryID;
    this.localRecordID = localRecordID;
    libraryAware = true;
  }
  
  /**
   * @return Returns the libraryID.
   */
  public int getLibraryID() {
    return libraryID;
  }
  /**
   * @param libraryID The libraryID to set.
   */
  public void setLibraryID(int libraryID) {
    this.libraryID = libraryID;
  }
  /**
   * @return Returns the localRecordID.
   */
  public int getLocalRecordID() {
    return localRecordID;
  }
  /**
   * @param localRecordID The localRecordID to set.
   */
  public void setLocalRecordID(int localRecordID) {
    this.localRecordID = localRecordID;
  }
  /**
   * @return Returns the libraryAware.
   */
  public boolean isLibraryAware() {
    return libraryAware;
  }
  /**
   * @param libraryAware The libraryAware to set.
   */
  public void setLibraryAware(boolean libraryAware) {
    this.libraryAware = libraryAware;
  }
  
  /**
   * Returns <code>true</code> if objects are equal. Equality may be
   * library-aware (default) or not.
   */
  public boolean equals(Object o) {
    if (o instanceof RecordID) {
      RecordID rid = (RecordID)o;
      if (libraryAware) {
        if (localRecordID == rid.localRecordID && libraryID == rid.libraryID)
          return true;
        else
          return false;
      } else {
        if (localRecordID == rid.localRecordID)
          return true;
        else
          return false;
      }
    } else
      return false;
  }
  
  /**
   * Returns the hash code for this object computed as 
   * <code>libraryID + localRecordID</code>. 
   */
  public int hashCode() {
    return libraryID + localRecordID;
  }
  
  /**
   * Returns the string representation of this object.
   */
  public String toString() {
    return "|" + libraryID + "," + localRecordID + "|";
  }

  /** Library identifier. */
  private int libraryID;
  /** Local record identifier. */
  private int localRecordID;
  /** Checks whether identifier comparison is library-aware. */
  private boolean libraryAware;
}
