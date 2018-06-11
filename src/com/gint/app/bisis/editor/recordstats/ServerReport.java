package com.gint.app.bisis.editor.recordstats;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

public abstract class ServerReport {

  public abstract void execute() throws Exception;
  
  public void setParams(Map params) {
    this.params = params;
  }
  
  public JasperPrint getReport() {
    return report;
  }
  
  protected Map params = new HashMap();
  protected JasperPrint report;
}
