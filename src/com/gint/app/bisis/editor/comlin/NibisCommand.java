package com.gint.app.bisis.editor.comlin;

import java.util.Vector;

import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.nibis.NibisSearchForm;

public class NibisCommand extends AbstractCommand {
  
  public NibisCommand(MainFrame mf) {
    this.mf = mf;
    nibisForm = new NibisSearchForm();
  }
  
  public void execute() {
    String proxyHost = Environment.getProxyHost();
    String proxyPort = Environment.getProxyPort();
    String nibisService = null;
    if (params.size() > 0)
      nibisService = (String)params.elementAt(0);
    else
      nibisService = Environment.getNibisService();
    
    nibisForm.setProxyHost(proxyHost);
    nibisForm.setProxyPort(proxyPort);
    nibisForm.setNibisService(nibisService);

    nibisForm.showGUI(mf);
    
    if (nibisForm.isRecordPicked()) {
      Vector fields = nibisForm.getSelectedRecord();
      mf.tfComLin.setText("");
      fields = mf.edDlg.init(fields);
      mf.edDlg.utm.setFields(fields);
      Environment.setNewRezance(true);
      Environment.setDocID(-1);
      mf.edDlg.setVisible(true);
    }
  }
  
  private NibisSearchForm nibisForm;
  private MainFrame mf;
}
