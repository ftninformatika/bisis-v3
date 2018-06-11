package com.gint.app.bisis.editor.comlin;

import javax.swing.JOptionPane;

import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.recordstats.StatsMenuFrame;

/**
 *
 * @author branko
 */
public class StatCommand extends AbstractCommand {

  public StatCommand(MainFrame mf) {
    this.mf = mf;
    smf = new StatsMenuFrame(mf);
  }

  public void execute() {
    if (params.size() > 0) {
      JOptionPane.showMessageDialog(mf, "Ova komanda nema parametre!", 
          "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      return;
    }
    smf.setVisible(true);
    mf.tfComLin.setText("");
  }
  
  StatsMenuFrame smf;

}
