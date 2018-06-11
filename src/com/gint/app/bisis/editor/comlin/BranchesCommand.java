package com.gint.app.bisis.editor.comlin;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.recordstats.ReportProgress;
import com.gint.app.bisis.editor.recordstats.ReportUtils;
import com.gint.app.bisis.editor.recordstats.ns.PogociPoOgrancima;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class BranchesCommand extends AbstractCommand {

  public BranchesCommand(MainFrame mf) {
    this.mf = mf;
  }
  
  public void execute() {
    if (params.size() > 0) {
      JOptionPane.showMessageDialog(mf, "Ova komanda nema parametre", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (Environment.getReadTs().getHitCount() == 0) {
      JOptionPane.showMessageDialog(mf, "Nema pogodaka pretra\u017eivanja", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      mf.tfComLin.setText("");
      return;
    }
    int hitCount = Environment.getReadTs().getHitCount();
    ReportProgress rp = new ReportProgress(mf);
    HashMap params = new HashMap();
    params.put("MainFrame", mf);
    ReportUtils.showReport(hitCount > 200, rp, PogociPoOgrancima.class, params);
    mf.tfComLin.setText("");
  }
  
}
