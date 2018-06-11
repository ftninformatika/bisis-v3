package com.gint.app.bisis.counters;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class OpomeneCounter {

  
  /**
   * @param warnType
   * @param warnYear
   * @param lastNo
   */
  public OpomeneCounter(Integer warnType, Integer warnYear, Integer lastNo) {
    this.warnType = warnType;
    this.warnYear = warnYear;
    this.lastNo = lastNo;
  }
  
  
  /**
   * @return Returns the warnType.
   */
  public Integer getWarnType() {
    return warnType;
  }
  /**
   * @param warnType The warnType to set.
   */
  public void setWarnType(Integer warnType) {
    this.warnType = warnType;
  }
  /**
   * @return Returns the lastNo.
   */
  public Integer getLastNo() {
    return lastNo;
  }
  /**
   * @param lastNo The lastNo to set.
   */
  public void setLastNo(Integer lastNo) {
    this.lastNo = lastNo;
  }

  /**
   * @return Returns the warnYear.
   */
  public Integer getWarnYear() {
    return warnYear;
  }
  /**
   * @param warnYear The warnYear to set.
   */
  public void setWarnYear(Integer warnYear) {
    this.warnYear = warnYear;
  }
  private Integer warnType;
  private Integer warnYear;
  private Integer lastNo;
}
