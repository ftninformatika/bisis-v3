package com.gint.app.bisis.editor.comlin;

import java.util.Vector;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.list.*;
import com.gint.app.bisis.report.*;

public class ListCommand extends AbstractCommand {
  
  public ListCommand(MainFrame mf) {
    this.mf = mf;
    listDlg = new ListDlg(null, "", true);
    reportList = new Vector();
    Report.ucitajParam();
    Report.loadReportTypes();

    for (int i = 0; i < Report.reportTypes.length; i++)
      reportList.addElement(Report.reportTypes[i]);
  }

  public void execute() {
    if (params.size() == 0) {
      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_CURRENTFORMATIS")+ " " + Report.currentType, Messages.get("BISISAPP_CMDLIST_CARDS"), JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    int hit;
    if (params.size() == 1) {  // jedan parametar
      String f = (String)params.elementAt(0); // pokupi parametar
      if (f.equals("?")) {// parametar "?" kojim se listaju svi raspolozivi formati
        listDlg.setListData(reportList);
        listDlg.setTitle(Messages.get("BISISAPP_CMDLIST_CURRENTFORMATS"));
        listDlg.setVisible(true);
        String choice = listDlg.getSelection(); // pokupimo sta je odabrao korisnik
        if (!choice.equals("")) {
          Report.currentType = choice.toString();
        } else // odabrao Cancel dugme
          return;
      } else {
        if (Environment.getReadTs().getHitCount() == 0) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_NOHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        try {
          hit = Integer.parseInt((String)params.elementAt(0));
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGNUMBERFORMAT"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }

        int docID = -1;
        docID = Environment.getReadTs().getDocID(hit);

        if (docID == -1) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGHITNUMBER"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        // pokupimo rezanac na osnovu DocID-a
        String rez = "";
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
        
        String HTML = Report.makeOne(docID, rez, Report.currentType);
        mf.reportDlgLis.setHTML(HTML);
        mf.reportDlgLis.setVisible(true);
      }
    } else if (params.size() == 2) { // dva parametra
      if (Environment.getReadTs().getHitCount() == 0) {
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_NOHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      // 1) prvi je naziv listica, a drugi je broj pogotka
      // 2) interval pogodaka: prvi i poslednji
      String item1 = (String)params.elementAt(0);
      String item2 = (String)params.elementAt(1);
      int first = 0, last = 0;
      // ako je prvi parametar broj pogotka (a ne tip listica)
      try {
        first = Integer.parseInt(item1);
        last = Integer.parseInt(item2);
        if (last < first) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGRANGEOFHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        int[] docIDs = new int[last-first+1];
        String[] docs = new String[last-first+1];
        if (Environment.getReadTs().getDocsRange(first, last, docIDs, docs)) {
          String HTML = Report.makeMulti(docIDs, docs, Report.currentType);
          mf.reportDlgLis.setHTML(HTML);
          mf.reportDlgLis.setVisible(true);
        } else {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGRANGEOFHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }

      } catch (NumberFormatException ex) {
        //if (!Report.reportTypeExists(item1)) {
         // JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGTYPEOFCARD") + " " + item1, Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
         // return;
        //}
        try {
          last = Integer.parseInt(item2);
        } catch (NumberFormatException ex1) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGNUMBERFORMAT"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        int docID = -1;
        docID = Environment.getReadTs().getDocID(last);
        if (docID == -1) {
          JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGHITNUMBER"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        // pokupimo rezanac na osnovu DocID-a
        String rez = "";
        rez = Environment.getReadTs().getDoc(docID);
        String HTML = Report.makeOne(docID, rez, item1);
        mf.reportDlgLis.setHTML(HTML);
        mf.reportDlgLis.setVisible(true);
      }
    } else if (params.size() == 3) { // tri parametra
      // prvi mora biti naziv listica, pa sledeca dva oznacavaju opseg
      String reportType = (String)params.elementAt(0);
      int first = 0, last = 0;
      //if (!Report.reportTypeExists(reportType)) {
        //JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGTYPEOFCARD") + " " + reportType, Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        //return;
      //}
      try {
        first = Integer.parseInt((String)params.elementAt(1));
        last = Integer.parseInt((String)params.elementAt(2));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGNUMBERFORMAT"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (last < first) {
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGRANGEOFHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      int[] docIDs = new int[last-first+1];
      String[] docs = new String[last-first+1];
      if (Environment.getReadTs().getDocsRange(first, last, docIDs, docs)) {
        String HTML = Report.makeMulti(docIDs, docs, reportType);
        mf.reportDlgLis.setHTML(HTML);
        mf.reportDlgLis.setVisible(true);
      } else {
        JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGRANGEOFHITS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }

    } else {
      // moron je nakucao previse parametara ili nijedan
      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDLIST_WRONGNUMBEROFPARAMS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    }
    mf.tfComLin.setText("");
  }

  private MainFrame mf;
  private ListDlg listDlg;
  private Vector reportList;
}
