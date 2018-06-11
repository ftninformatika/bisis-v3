package com.gint.app.bisis.editor.recordstats;

import java.util.Map;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public abstract class RecordReport {

  public abstract void execute();
  
  public void setReportProgress(ReportProgress rp) {
    this.rp = rp;
  }
  
  public void setParams(Map params) {
    this.params = params;
  }
  
  protected ReportProgress rp;
  protected Map params;
  protected StringBuffer problems = new StringBuffer();
}
