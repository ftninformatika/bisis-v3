package com.gint.app.bisis.editor.comlin;

import java.awt.*;
import javax.swing.*;

import com.gint.util.gui.SwingWorker;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;

public class ExitCommand extends AbstractCommand {

  public ExitCommand(EditDlg editDlg) {
    this.editDlg = editDlg;
  }

  public void execute() {
    JLabel msg = new JLabel(Messages.get("BISISAPP_CMDEXIT_LABELCONFIRM"));
    msg.setForeground(Color.black);
    String defBtn = "  " + Messages.get("BISISAPP_CMDEXIT_BUTTONYES") + "  ";
    String[] buttons = {defBtn, "  " + Messages.get("BISISAPP_CMDEXIT_BUTTONNO") + "  "};
    if (JOptionPane.showOptionDialog(
           null,
           msg,
          Messages.get("BISISAPP_CMDEXIT_TITLEEXIT"),
          JOptionPane.YES_NO_OPTION,
          JOptionPane.INFORMATION_MESSAGE,
          null,
          buttons,
          defBtn) == JOptionPane.YES_OPTION) {
      // snimanje
      SaveCommand sc = new SaveCommand(editDlg);
      final String rez = sc.exitExecute();
      final SaveCommand sc1 = sc;
      if (!rez.equals("")) {
        editDlg.tfComLin.setText("");
        editDlg.setVisible(false);
        final SwingWorker worker = new SwingWorker() {
          public Object construct() {
            return new SaveTask(rez, Environment.getDocID(), editDlg.mf, sc1.newRN);
          }
        };
        worker.start();
      }
    } else {
      editDlg.tfComLin.setText("");
      editDlg.tEdit.requestFocus();
      editDlg.setVisible(false);
    }
  }

  private EditDlg editDlg;
}
