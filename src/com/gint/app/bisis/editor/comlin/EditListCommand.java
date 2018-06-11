package com.gint.app.bisis.editor.comlin;

import java.util.*;
import javax.swing.*;
import java.text.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.report.*;

public class EditListCommand extends AbstractCommand {

  public EditListCommand(EditDlg editDlg) {
    this.editDlg = editDlg;
  }

  public void execute() {
    int hit;
    if (params.size() > 1) {
      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMD_ELIST_WRONGNUMBEROFPARAMS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    String reportType = "";
    if (params.size() == 1) {
      reportType = (String)params.elementAt(0);
      //if (!Report.reportTypeExists(reportType)) {
        //JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMD_ELIST_UNKNOWNTYPE"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        //return;
      //}
    } else
      reportType = Report.currentType;

    Vector fields = initRezance();
    String rez = RezanceUtilities.packRezance(fields);
    String HTML = Report.makeOne(Environment.getDocID(), rez, reportType);
    editDlg.mf.reportDlgLis.setHTML(HTML);
    editDlg.mf.reportDlgLis.setVisible(true);
//    editDlg.mf.reportDlg.setHTML(HTML);
//    editDlg.mf.reportDlg.setVisible(true);
    editDlg.tfComLin.setText("");
  }

  private Vector initRezance() {
    // zajednicka inicijalizacija
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String dateString = formatter.format(new Date());
    String adresa = Environment.getAddress();

    Vector subfields000 = editDlg.field000.getSubfields();

    if (Environment.isNewRezance()) { // ako je u pitanju novi rezanac
      int i = 0;
      while (i < subfields000.size()) {
        String subfiID = ((Subfield)subfields000.elementAt(i)).getName();
        if (subfiID.equals("a")) {
          ((Subfield)subfields000.elementAt(i)).setContent(Environment.getLib().pubTypeDecode(Environment.getLib().getPubType())+"01");
        }
        if (subfiID.equals("b")) {
          ((Subfield)subfields000.elementAt(i)).setContent(dateString+"00000000");
        }
        if (subfiID.equals("c")) {
          ((Subfield)subfields000.elementAt(i)).setContent(Environment.getLib().getLibName() + "@" + adresa);
        }
        if (subfiID.equals("d")) {
          ((Subfield)subfields000.elementAt(i)).setContent(Environment.getLib().getLibName() + "@" + adresa);
        }
        if (subfiID.equals("e")) {
          ((Subfield)subfields000.elementAt(i)).setContent("0");
        }
        if (subfiID.equals("f")) {
          ((Subfield)subfields000.elementAt(i)).setContent("0");
        }
        i++;
      }
      Environment.setDocID(0);
    } else { // ako je u pitanju postojeci rezanac (radi se update)
      int i = 0;
      while (i < subfields000.size()) {
        if (((Subfield)subfields000.elementAt(i)).getName().equals("b"))
          ((Subfield)subfields000.elementAt(i)).setContent(((Subfield)subfields000.elementAt(i)).getContent().substring(0, 8) + dateString);
        if (((Subfield)subfields000.elementAt(i)).getName().equals("d"))
          ((Subfield)subfields000.elementAt(i)).setContent(Environment.getLib().getLibName() + "@" + adresa);
        i++;
      }
    }

    // dodamo polje 000 na pocetak vektora polja
    Vector fields = editDlg.utm.getFields();
    Vector fields2 = new Vector();
    fields2.addElement(editDlg.field000);
    for (int i = 0; i < fields.size(); i++)
      fields2.addElement(fields.elementAt(i));
    return fields2;
  }

  private EditDlg editDlg;
}
