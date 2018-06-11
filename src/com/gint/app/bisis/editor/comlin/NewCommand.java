package com.gint.app.bisis.editor.comlin;

import java.util.Vector;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;

public class NewCommand extends AbstractCommand {

  public NewCommand(MainFrame mf) {
    this.mf = mf;
  }

  public void execute() {
    Vector fields = new Vector();
    if (params.size() > 1) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDPRMPT_TOOMANYPARAMS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (params.size() == 1) { // ako je jedan parametar, onda je new/include hit
      try {
        if (Environment.getReadTs().getHitCount() == 0) {
          JOptionPane.showMessageDialog(null,  com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDNEW_NOHITS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
      } catch (Exception ex) {}
      int hit;
      try {
        hit = Integer.parseInt((String)params.elementAt(0));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDNEW_WRONGNUMBERFORMAT"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      int docID = -1;
      try { docID = Environment.getReadTs().getDocID(hit); } catch (Exception ex) {}
      if (docID == -1) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDNEW_WRONGHITNUMBER"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      // pokupimo rezanac na osnovu DocID-a
      String rez = "";
      try { rez = Environment.getReadTs().getDoc(docID); } catch (Exception ex) {}
      // raspakujemo rezanac
      fields = RezanceUtilities.unpackRezance(rez);
      // sada treba pobrisati polja koja su "visak"
      Vector v1 = new Vector();
      for (int i = 0; i < fields.size(); i++) {
        Field fi = (Field)fields.elementAt(i);
        // ako nije u listi polja za brisanje
        if (!existForDelete(fi.getName()))
          v1.addElement(fi);
        
        // obrisi 001e (RN) ako postoji
        if (fi.getName().equals("001")) {
          String originalRN = null;
          for (int j = 0; j < fi.getSubfields().size(); j++) {
            if (fi.getSubfieldByIndex(j).getName().equals("e")) {
              originalRN = fi.getSubfieldByIndex(j).getContent();
              fi.getSubfields().removeElementAt(j);
              break;
            }
          }
          if (originalRN != null) {
            try {
              int origRN = Integer.parseInt(originalRN);
              Environment.setOriginalRN(origRN);
            } catch (NumberFormatException ex) {
              ex.printStackTrace();
            }
          } else {
            Environment.setOriginalRN(0);
          }
        }
      } // for
      fields = v1;
    } else if (params.size() == 0) {
      Environment.setOriginalRN(0);
    }

    // Azuriramo vektor polja na osnovu okruzenja
    fields = mf.edDlg.init(fields);

    // napunimo tabelu iz editora ovim poljima
    mf.edDlg.utm.setFields(fields);

    mf.tfComLin.setText("");

    Environment.setNewRezance(true);
    Environment.setDocID(-1);

    // prikazemo editor
    mf.edDlg.setVisible(true);
  }

  /** Vraca <i>true</i> ako je ime polja 996, 997, 998, 010 i 011. To su polja
      koje ne treba da postoje kod new/incl naredbe.
   */
  private boolean existForDelete(String fName) {
    boolean retVal = false;
    if (
//        fName.equals("010") ||
//        fName.equals("011") ||
        fName.equals("992") ||
        fName.equals("996") ||
        fName.equals("997") ||
        fName.equals("998")
        )
      retVal = true;

    return retVal;
  }


}
