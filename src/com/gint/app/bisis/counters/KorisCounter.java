package com.gint.app.bisis.counters;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class KorisCounter {

  
  /**
   * @param branchID
   * @param lastValue
   */
  public KorisCounter(Integer branchID, String description, Integer lastValue) {
    this.branchID = branchID;
    this.description = description;
    this.lastValue = lastValue;
  }
  
  
  /**
   * @return Returns the branchID.
   */
  public Integer getBranchID() {
    return branchID;
  }
  /**
   * @param branchID The branchID to set.
   */
  public void setBranchID(Integer branchID) {
    this.branchID = branchID;
  }
  /**
   * @return Returns the lastValue.
   */
  public Integer getLastValue() {
    return lastValue;
  }
  /**
   * @param lastValue The lastValue to set.
   */
  public void setLastValue(Integer lastValue) {
    this.lastValue = lastValue;
  }

  /**
   * @return Returns the description.
   */
  public String getDescription() {
    return description;
  }
  /**
   * @param description The description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }
  private Integer branchID;
  private String description;
  private Integer lastValue;
}
