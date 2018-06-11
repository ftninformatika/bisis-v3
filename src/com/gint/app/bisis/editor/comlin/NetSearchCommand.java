package com.gint.app.bisis.editor.comlin;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Vector;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.RezanceUtilities;
import com.gint.app.bisis.xmlmessaging.client.BisisSearchForm;

/**
 *
 * @author branko
 */
public class NetSearchCommand extends AbstractCommand {

  public NetSearchCommand(MainFrame mf) {
    this.mf = mf;
    bsf = null;
    if (Environment.getProxyHost() != null && Environment.getProxyPort() != null) {
      System.getProperties().put("proxySet", "true");
      System.getProperties().put("http.proxyHost", Environment.getProxyHost());
      System.getProperties().put("http.proxyPort", Environment.getProxyPort());
      if (Environment.getProxyUser() != null && Environment.getProxyPass() != null) {
        System.getProperties().put("http.proxyUser", Environment.getProxyUser());
        System.getProperties().put("http.proxyPassword", Environment.getProxyPass());
        Authenticator.setDefault(new MyAuthenticator());
      }
    }
  }
  
  public void execute() {
    if (bsf == null)
      bsf = new BisisSearchForm(this);

    if (!bsf.isVisible())
      bsf.setVisible(true);
    mf.tfComLin.setText("");
  }
  
  public void transferRecord(String rec) {
    if (mf.edDlg.isVisible()) {
      // razresi kasnije
    } else {
      Vector fields = RezanceUtilities.unpackRezance(rec);
      fields = mf.edDlg.init(fields);
      mf.edDlg.utm.setFields(fields);
      Environment.setNewRezance(true);
      Environment.setDocID(-1);
      mf.edDlg.setVisible(true);
    }
  }

  private MainFrame mf;
  private BisisSearchForm bsf;
  
  public class MyAuthenticator extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(System.getProperty("http.proxyUser"), System.getProperty("http.proxyPassword").toCharArray());
    }
  }
}
