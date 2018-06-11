package com.gint.app.bisis.counters;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class InvBrCounter {

  /**
   * @param counterID
   * @param lastValue
   */
  public InvBrCounter(String counterID, Integer lastValue) {
    this.counterID = counterID;
    this.lastValue = lastValue;
  }
  /**
   * @return Returns the counterID.
   */
  public String getCounterID() {
    return counterID;
  }
  /**
   * @param counterID The counterID to set.
   */
  public void setCounterID(String counterID) {
    this.counterID = counterID;
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
  private String counterID;
  private Integer lastValue;
}
