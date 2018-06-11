package com.gint.app.bisis.editor.comlin;

import java.util.Vector;
import javax.swing.*;

import com.gint.app.bisis.editor.list.*;
import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;


public class FormatCommand extends AbstractCommand {

  public FormatCommand(JTextField tf) {
    this.tf = tf;
    listDlg = new ListDlg(null, "", true);
  }

  public void execute() {
    Format format = Environment.getLib().getFormat();
    if (params.size() == 1) { // ako ima parametara
      String f = (String)params.elementAt(0); // pokupi parametar
      if (f.equals("?")) {// parametar "?" kojim se listaju svi raspolozivi formati
        format.setRes(true);
        Vector v = new Vector();
        v = format.getAllFormats();
        listDlg.setListData(v);
        listDlg.setTitle(Messages.get("BISISAPP_CMDFORMAT_DEFINEDFORMATS"));
        listDlg.setVisible(true);
        f = listDlg.getSelection();        // pokupimo sta je odabrao korisnik
        if (!f.equals("")) {
          f = f.substring(0, f.indexOf("(")); // odabrani format postavimo
          format.postavi(f);                  // postavimo format
        } else                                // odabrao Cancel dugme
          return;
      } else                                  // nije "?", onda je neki zadati format
        format.postavi(f);                    // postavimo format
    } else if (params.size() > 1) {
      // vise parametara znaci definicuju novog formata ili redefiniciju postojeceg
      String f = (String)params.elementAt(0); // naziv formata
      f += " ";
      int n = params.size();
      for (int i = 1; i < n; i++) {
        f += (String)params.elementAt(i);
        f += (i<(n-1)) ? "," : "";
      }
      format.postavi(f);
    } else // bez parametara - prikazi samo koji je trenutno aktuelni format. To ce uraditi kod ispod.
      format.setRes(true);  // ovo samo obezbedjuje da se ne prijavi greska.
    if (!format.getRes())
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDFORMAT_WRONGFORMAT"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    else {
      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDFORMAT_FORMATSETTO") + "\n"
                                  + format.getName() + "(" + format.getPrefixesFromCurrentFormat() + ")",
                                  Messages.get("BISISAPP_CMDFORMAT_FORMAT"), JOptionPane.INFORMATION_MESSAGE);
      tf.setText("");
    }
  }

  private JTextField tf;
  private ListDlg listDlg;
}
