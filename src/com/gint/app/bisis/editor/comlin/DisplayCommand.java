package com.gint.app.bisis.editor.comlin;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.swing.*;
import com.objectspace.jgl.HashMap;

import com.gint.util.string.UnimarcConverter;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.LELibrarian.*;
import com.gint.app.bisis.editor.UFieldSet.*;

public class DisplayCommand extends AbstractCommand {

  public DisplayCommand(MainFrame mf) {
    this.mf = mf;
    conv = new UnimarcConverter();
  }

  public void execute() {
    int hit;
    try {
      if (Environment.getReadTs().getHitCount() == 0) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_NOHITS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
    } catch (Exception ex) {}
    if (params.size() == 1) {  // jedan parametar
      try {
        hit = Integer.parseInt((String)params.elementAt(0));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_WRONGNUMBERFORMAT"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      int docID = -1;
      try { docID = Environment.getReadTs().getDocID(hit); } catch (Exception ex) {}
      if (docID == -1) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_WRONGHITNUMBER"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
			String rez = "";
			try { 
				rez = Environment.getReadTs().getDoc(docID); 
				if (rez == null || rez.equals("")) {
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
      Vector lines = new Vector();
      prepareList(rez, docID, hit, lines);
      mf.hld.setList(lines);
      mf.hld.setVisible(true);
    } else if (params.size() == 2) { // dva parametra
      int startHit, endHit;
      try {
        startHit = Integer.parseInt((String)params.elementAt(0));
        endHit = Integer.parseInt((String)params.elementAt(1));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_WRONGNUMBERFORMAT"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (endHit < startHit) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_WRONGRANGEOFHITS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      Vector lines = new Vector();
      try {
        Vector v = Environment.getReadTs().getDocsRangePacked(startHit, endHit);
        if (v.size() != 0) {
          for (int i = 0; i < v.size(); i++) {
            TextServerPackedDoc p = (TextServerPackedDoc)v.elementAt(i);
            prepareList(p.doc, p.docID, i+startHit, lines);
          }
          mf.hld.setList(lines);
          mf.hld.setVisible(true);
        } else {
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_WRONGRANGEOFHITS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      // moron je nakucao previse parametara ili ni jedan
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_WRONGNUMBEROFPARAMS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    }
    mf.tfComLin.setText("");
  }

  /** Kreira HashMap objekat sa preslikavanjem prefiks->vrednost za dati rezanac.
      @see prepareList
    */
  public HashMap prepareMap(String rezance) {
    HashMap prefixMap = null;
    try { prefixMap = Environment.getWriteTs().getPrefixMap(); } catch (Exception ex) {}
    DocumentParser dp = new DocumentParser(prefixMap, "\36", "\37");
    Vector pv = dp.parseDocument(rezance);
    PrefixPair pp = null;
    HashMap hMap = new HashMap(true);
    for (int h = 0; h < pv.size(); h++) {
      pp = (PrefixPair)pv.elementAt(h);
      hMap.add(pp.prefName, pp.value);
    }
    return hMap;
  }

  /** Kreira vektor stringova na osnovu tekuceg formata i mape prefiksi->vrednosti
      za dati rezanac.
    */
  public void prepareList(String rezance, int docID, int hit, Vector lines) {
    Vector fields = RezanceUtilities.unpackRezance(rezance);
    String sf001e = RezanceUtilities.getSubfieldContent(fields, "001e");
    if (sf001e != null)
      lines.addElement("RN: "+sf001e+"    " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_HITNUMBER") + " "+hit);
    else
      lines.addElement("ID: "+docID+"    " + com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDDISPLAY_HITNUMBER") + " "+hit);
    Format format = Environment.getLib().getFormat();
    if (format.getName().equals("full")) {
      // formatiranje ispisa za full
      handleFull(fields, lines);
    } else {
      // svi formati koji nisu full, tj. po prefiksima
      HashMap prefs = prepareMap(rezance);
      for (int i = 0; i < format.getCount(); i++) {
        String pref = format.getPrefixAt(i);
        Enumeration e = prefs.values(pref);
        if (e != null) {
          while (e.hasMoreElements()) {
            String tmp = (String)e.nextElement();
            if (format.getPrefixAt(i).equals("330a"))
              lines.addElement("Abstract=" + conv.Unimarc2Unicode(tmp));
            else
              lines.addElement(format.getPrefixAt(i) + "=" + conv.Unimarc2Unicode(tmp));
          }
        }
      }
    }
    lines.addElement("");
  }

  /** Lista rezanac u full formatu.
      @param rezance Rezanac koji se lista.
      @param lines Vektor stringova u kojima se nalaze redovi teksta full formata.
    */
  private void handleFull(Vector fields, Vector lines) {
    // uzmem enumerator za polja.
    Enumeration fieldIter = fields.elements();
    String red = "";
    while (fieldIter.hasMoreElements()) {
      red = "";
      // pokupim tekuce polje.
      Field currField = (Field)fieldIter.nextElement();
      // uzmem enumerator potpolja za tekuce polje
      Enumeration subfieldIter = currField.getSubfieldsEnum();
      while (subfieldIter.hasMoreElements()) {
         // pokupim tekuce potpolje
         Subfield sf = (Subfield)subfieldIter.nextElement();
         // uzmem vektor sekundarnih polja
         Vector secFields = sf.getSecFieldInVect();
         if (secFields.size() == 0) { // no sec fields
            // ako nemam sekundarnih polja
            if (!sf.getContent().equals("")) { //dodato!
               USubfield usf = Environment.getFs().getSubfield(currField.getName(),sf.getName());
               String sadrzaj = new String(conv.Unimarc2Unicode(sf.getContent()));
               if (usf != null)
                 if (usf.getLength() == 0)
                   sadrzaj = cleanCrLf(sadrzaj);
               red +=  "[" + sf.getName() + "]" + sadrzaj;
            }
            else { // jeste prazan sadrzaj potpolja
               Vector ssfv = sf.getSubsubfields();
               String redpotpp = "";
               if (ssfv.size() != 0) { // ako ima potpotpolja
                  for (int jj = 0; jj < ssfv.size(); jj++) {
                      Subfield ssf = (Subfield)ssfv.elementAt(jj);
                      String ssfContent = ssf.getContent();
                      if (!ssfContent.equals("")) {
                         USubSubfield ussf = Environment.getFs().getSubSubfield(currField.getName(),sf.getName(),ssf.getName());
                         if (ussf != null)
                           if (ussf.getLength() == 0)
                              ssfContent = cleanCrLf(ssfContent);
                         redpotpp += "<" + ssf.getName() + ">" + ssfContent;
                      }
                  } //for
                  red += "[" + sf.getName() + "]" + conv.Unimarc2Unicode(redpotpp);
               } //if
            } //else
         } //if
         else {
            // ako imam sekundarnih polja
            String stmp = "";
            // prodjem kroz vektor sekundarnih polja
            for (int si = 0; si < secFields.size(); si++) {
                Field secf = (Field)secFields.elementAt(si);
                Vector secSubfields = secf.getSubfields();
                int prazno = 0;
                int broj = 0;
                for (int ssf=0; ssf < secSubfields.size(); ssf++) {
                    broj++;
                    Subfield secsf = (Subfield)secSubfields.elementAt(ssf);
                    String sadrzaj = new String(conv.Unimarc2Unicode(secsf.getContent()));
                    if (!sadrzaj.equals("")) { //dodato
                       USubfield usf = Environment.getFs().getSubfield(currField.getName(),secsf.getName());
                       if (usf != null)
                         if (usf.getLength() == 0)
                           sadrzaj = cleanCrLf(sadrzaj);
                       stmp += "[" + secsf.getName() + "]" + sadrzaj;
                    }
                    else
                       prazno++;
                } // for ssf
                if (prazno < broj)
                   stmp = "[" + sf.getName() + "]"+secf.getName()+ secf.getInd1()+ secf.getInd2()+stmp;
            } // for si
            red = red+stmp;
         } // else
      } // while
      if (red.length() > 3)
         lines.addElement(currField.getName()+ currField.getInd1()+ currField.getInd2()+ red);
    } // while
  }

  /** Cisti iz stringa CrLf znakove. */
  String cleanCrLf(String sadrzaj) {
    String retVal = "";
    StringTokenizer st = new StringTokenizer(sadrzaj, "\n");
    boolean first = true;
    while  (st.hasMoreTokens())  {
      if (first) {
        retVal +=  new String(st.nextToken());
      first = false;
      }
      else
        retVal +=  " " + new String(st.nextToken());
    }
    return retVal;
  }

  private UnimarcConverter conv;
}
