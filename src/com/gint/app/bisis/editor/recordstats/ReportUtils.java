package com.gint.app.bisis.editor.recordstats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import com.gint.util.gui.SwingWorker;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class ReportUtils {

  public static void showServerReport(Class rc, Map params) {
    if (!ServerReport.class.isAssignableFrom(rc))
      return;
    try {
      ServerReport rep = (ServerReport)rc.newInstance();
      if (params != null)
        rep.setParams(params);
      rep.execute();
      JasperPrint jr = rep.getReport();
      if (jr != null)
        JasperViewer.viewReport(jr, false);
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska",
          JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public static void showOnlineServerReport(Class rc, Map params) {
    if (!areYouSure())
      return;
    showServerReport(rc, params);
  }
  
  public static String getXmlReport(URL url) throws Exception {
    String proxyHost = System.getProperty("proxyHost");
    String proxyPort = System.getProperty("proxyHost");
    String proxySet = System.getProperty("proxySet");
    System.setProperty("proxyHost", "");
    System.setProperty("proxyPort", "");
    System.setProperty("proxySet", "false");
    URLConnection conn = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
        conn.getInputStream(), "UTF8"));
    StringBuffer buff = new StringBuffer();
    String line = "";
    while ((line = in.readLine()) != null)
      buff.append(line + "\n");
    in.close();
    System.setProperty("proxyHost", proxyHost == null ? "" : proxyHost);
    System.setProperty("proxyPort", proxyPort == null ? "" : proxyPort);
    System.setProperty("proxySet", proxyHost == null ? "false" : "true");
    return buff.toString();
  }
  
  public static void showReport(ReportProgress rp, Class rc, Map params) {
    final ReportProgress rp1 = rp;
    final Class rc1 = rc;
    final Map params1 = params;
    SwingWorker worker = new SwingWorker() {
      public Object construct() {
        if (RecordReport.class.isAssignableFrom(rc1)) {
          try {
            RecordReport rep = (RecordReport)rc1.newInstance();
            rep.setReportProgress(rp1);
            rep.setParams(params1);
            rep.execute();
            return rep;
          } catch (Exception ex) {
            ex.printStackTrace();
            return new Object();
          }
        } else
          return new Object();
      }
    };
    worker.start();
    rp.setVisible(true);
    if (!rp.isCancelled())
      if (rp.getReport() != null) {
        try { 
          JasperViewer.viewReport(rp.getReport(), false);
          if (!"".equals(rp.getProblems())) {
            reportProblems.setText(rp.getProblems());
            reportProblems.setVisible(true);
          }
        } catch (Exception ex) { }
      }
    
  }
  
  public static void askAndShowReport(ReportProgress rp, Class rc, Map params) {
    if (!areYouSure())
      return;
    showReport(rp, rc, params);
  }
  
  public static void showReport(boolean ask, ReportProgress rp, Class rc, Map params) {
    if (ask)
      askAndShowReport(rp, rc, params);
    else
      showReport(rp, rc, params);
  }

  private static boolean areYouSure() {
    String defBtn = " Da ";
    String[] buttons = {defBtn, " Ne "};
    int answer = JOptionPane.showOptionDialog(null, 
        "Ova operacija mo\u017ee dugo trajati. Da li ste sigurni da \u017eelite da je pokrenete?", 
        "Dugotrajna operacija", JOptionPane.YES_NO_OPTION, 
        JOptionPane.INFORMATION_MESSAGE, null, buttons, defBtn);
    return answer == JOptionPane.YES_OPTION;
  }
  
  private static ReportProblems reportProblems = new ReportProblems();
}
