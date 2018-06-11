package com.gint.app.bisis.editor.comlin;

import java.util.Vector;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;

public class EditCommand extends AbstractCommand {

  public EditCommand(MainFrame mf) {
    this.mf = mf;
  }


  public void execute() {
    int hit;
    if (Environment.isInternal() == false) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_NOEDITINGEXTERNAL"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (params.size() > 1) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDPRMPT_TOOMANYPARAMS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    try {
      if (Environment.getReadTs().getHitCount() == 0) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_NOHITS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
    } catch (Exception ex) {}
    if (params.size() != 1) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_WRONGNUMBEROFPARAMS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    } else {  // jedan parametar
      try {
        hit = Integer.parseInt((String)params.elementAt(0));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_WRONGNUMBERFORMAT"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      int docID = -1;
      try { docID = Environment.getReadTs().getDocID(hit); } catch (Exception ex) {}
      if (docID == -1) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_WRONGHITNUMBER"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      // pokupimo rezanac na osnovu DocID-a
      String rez = "";
      try { 
      	rez = Environment.getReadTs().getDoc(docID); 
				if (rez.equals("")) {
      	  if (Environment.getReadTs() == Environment.getInternalTs()) {
						JLabel msg = new JLabel(com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_DELETEEMPTYRECORD"));
						msg.setForeground(java.awt.Color.black);
						String defBtn = "  " + com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_YES") + "  ";
						String[] buttons = {defBtn, "  " + com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_NO") + "  "};
						if (JOptionPane.showOptionDialog(null, msg, com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"),	JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, defBtn) == JOptionPane.YES_OPTION) {
							Environment.getWriteTs().delete(docID);
						}
						return;
      	  } else
      	    JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_EMPTYRECORD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
				}
      } catch (Exception ex) {
      }
      // raspakujemo rezanac u vektor polja
      Vector fields = RezanceUtilities.unpackRezance(rez);

      // !!! OVDE IDE PROVERA DA LI JE REZANAC OK PRE NEGO STO SE
      // KRENE SA EDITOVANJEM
      if (fieldsOk(fields) == false) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_CORRUPTEDRECORD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }

      // Azuriramo vektor polja na osnovu okruzenja
      fields = mf.edDlg.init(fields);

      // napunimo tabelu iz editora ovim poljima
      mf.edDlg.utm.setFields(fields);

      mf.tfComLin.setText("");

      Environment.setNewRezance(false);
      Environment.setDocID(docID);

      // prikazemo editor
      mf.edDlg.setVisible(true);
    }
  }

  private boolean fieldsOk(Vector fields) {
    boolean retVal = false;
    // pokupimo polje 000
    Field field = (Field)fields.elementAt(0);
    String pubType = ((Subfield)field.getSubfields().elementAt(0)).getContent().substring(0,3);
    if (Environment.getLib().pubTypeCode(pubType) == null) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_UNKNOWNPUBTYPE"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    }
    else {
      // ako se tip publikacije iz zapisa ne poklapa sa tipom publikacije iz okruzenja
      if (!Environment.getLib().pubTypeCode(pubType).equals(Environment.getLib().getPubType())) {
        // proveri da li postoji nivo obrade u okruzenju, za uredjenu trojku:
        // (tip publikacije iz zapisa, nivo obrade iz okruzenja i nivo obaveznosti=1)
        int procLevel = Environment.getLib().findProcLevel(Environment.getLib().pubTypeCode(pubType),
                                                           Environment.getLib().getProcLevel(),
                                                           1);
        if (procLevel != -1) {
          // podesi nivo obrade na osnovu vrednosti iz zapisa!!!!!!!!!!??????????????????!?!!!?!?!?!
          Environment.getLib().setCurrContext(Environment.getLib().pubTypeCode(pubType),procLevel,1);
          // azuriraj statusnu liniju
          mf.updateStatLin();
          retVal = true;
        }
        else {
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDEDIT_UNKNOWNPROCLEVEL"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        }
      } else {  // ako se tip publikacije iz zapisa poklapa sa tipom publikacije iz okruzenja => OK!
        retVal = true;
      }
    }
    return retVal;
  }

}
