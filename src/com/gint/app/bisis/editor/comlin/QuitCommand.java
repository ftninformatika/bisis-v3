package com.gint.app.bisis.editor.comlin;

import java.awt.*;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;

public class QuitCommand extends AbstractCommand {

  public QuitCommand(MainFrame mf) {
    this.mf = mf;
    this.ed = null;
  }

  public QuitCommand(EditDlg ed) {
    this.mf = null;
    this.ed = ed;
  }

  public void execute() {
    if (mf != null) {
      JLabel msg = new JLabel(Messages.get("BISISAPP_CMDQUIT_LABELWANNAEXIT"));
      msg.setForeground(Color.black);
      String defBtn = "  " + Messages.get("BISISAPP_CMDQUIT_BUTTONYES") + "  ";
      String[] buttons = {defBtn, "  " + Messages.get("BISISAPP_CMDQUIT_BUTTONNO") + "  "};
      if (JOptionPane.showOptionDialog(
             null,
             msg,
            Messages.get("BISISAPP_CMDQUIT_EXITAPP"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
        mf.exit(0);
      }
      mf.tfComLin.setText("");
    } else {
      JLabel msg = new JLabel(Messages.get("BISISAPP_CMDQUIT_QUITWITHOUTSAVE"));
      msg.setForeground(Color.black);
      String defBtn = "  " + Messages.get("BISISAPP_CMDQUIT_BUTTONYES") + "  ";
      String[] buttons = {defBtn, "  " + Messages.get("BISISAPP_CMDQUIT_BUTTONNO") + "  "};
      if (JOptionPane.showOptionDialog(
             null,
             msg,
            Messages.get("BISISAPP_CMDQUIT_EXITAPP"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
        ed.setVisible(false);
      }
      ed.tfComLin.setText("");
    }
  }

  private EditDlg ed;
}
