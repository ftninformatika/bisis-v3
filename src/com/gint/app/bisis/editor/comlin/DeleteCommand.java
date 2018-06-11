package com.gint.app.bisis.editor.comlin;

import java.awt.*;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;

public class DeleteCommand extends AbstractCommand {

  public DeleteCommand(EditDlg editDlg) {
    this.editDlg = editDlg;
  }

  public void execute() {
    if (Environment.isInternal() == false) {
      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDDELETE_NODELETINGEXTERNAL"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    int hit;
    try {
      if (Environment.getReadTs().getHitCount() == 0) {
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDDELETE_NOHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
    } catch (Exception ex) {}
    if (params.size() == 0) {  // bez parametara
      if (Environment.isNewRezance()) {
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDDELETE_CANNOTDELETE"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      // pitalica
      JLabel msg = new JLabel(Messages.get("BISISAPP_CMDDELETE_LABELCONFIRM"));
      msg.setForeground(Color.black);
      String defBtn = "  " + Messages.get("BISISAPP_CMDDELETE_BUTTONYES") + "  ";
      String[] buttons = {defBtn, "  " + Messages.get("BISISAPP_CMDDELETE_BUTTONNO") + "  "};
      if (JOptionPane.showOptionDialog(
             null,
             msg,
            Messages.get("BISISAPP_CMDDELETE_TITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
        // brisanje
        editDlg.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        int status = Environment.getWriteTs().delete(Environment.getDocID());
        editDlg.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if (status == 0) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDDELETE_DELETESUCCESSFUL"), Messages.get("BISISAPP_CMDDELETE_TITLE"), JOptionPane.INFORMATION_MESSAGE);
          int hits = -1;
          try { hits = Environment.getReadTs().getHitCount(); } catch (Exception ex) {}
          editDlg.mf.lComLin.setText(editDlg.mf.oblik_lokalni(hits)+" "+hits+" "+editDlg.mf.oblik_pogodak(hits));
          editDlg.setVisible(false);
        }
        else
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDDELETE_FAILED"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDDELETE_WRONGNUMBEROFPARAMS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  EditDlg editDlg;
}


