package com.gint.app.bisis.editor.comlin;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.RezanceUtilities;
import com.gint.app.bisis.editor.edit.EditDlg;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.SaveTask;
import com.gint.app.bisis.editor.edit.Subfield;
import com.gint.app.bisis.editor.list.ListDlg;
import com.gint.app.bisis.textsrv.DocumentParser;
import com.gint.app.bisis.textsrv.PrefixPair;
import com.gint.util.gui.SwingWorker;
import com.gint.util.string.StringUtils;
import com.gint.util.string.UnimarcConverter;


public class SaveCommand extends AbstractCommand {

  public SaveCommand(EditDlg editDlg) {
    this.editDlg = editDlg;
    listDlg = new ListDlg(null, "", true);
  }

  public void execute() {
/*
    if (!editDlg.isDirty()) {
      JOptionPane.showMessageDialog(null, "Zapis nije menjan!", "Greska", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
*/

    fieldsOK = true;
    // provera
    if (!checkRezance()) {
      fieldsOK = false;
      return;
    }

    // inicijalizacija
    Vector fields2 = initRezance();
    final int RN = newRN;
    final String rez = RezanceUtilities.packRezance(fields2);
    if (showPreview(Environment.getDocID(), rez)) {
      final SwingWorker worker = new SwingWorker() {
        public Object construct() {
          editDlg.tfComLin.setText("");
          return new SaveTask(rez, Environment.getDocID(), editDlg.mf, RN);
        }
      };
      worker.start();
    }
    editDlg.tfComLin.setText("");
//    setDirty(false);
  }

  public String exitExecute() {
/*
    if (!editDlg.isDirty()) {
      JOptionPane.showMessageDialog(null, "Zapis nije menjan!", "Greska", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
*/

    // provera
    if (!checkRezance())
      return "";

    // inicijalizacija
    Vector fields2 = initRezance();
    String rez = RezanceUtilities.packRezance(fields2);
    if (showPreview(Environment.getDocID(), rez)) {
      return rez;
//      setDirty(false);
    } else
      return "";
  }

  private boolean checkRezance() {
    boolean retVal = true;
    Vector badFields = RezanceUtilities.checkManFields(editDlg.utm.getFields());
    if (badFields.size() > 0) {
      listDlg.setListData(badFields);
      listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDSAVE_MANFIELDS"));
      listDlg.setVisible(true);
      retVal = false;
    }

    // provera jedinstvenosti svih inventarnih brojeva u zapisu!!!
    Vector fields = editDlg.utm.getFields();
    Vector invNums = new Vector();
    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field)fields.elementAt(i);
      if (f.getName().equals("996") || f.getName().equals("997")) {
        Vector sfs = f.getSubfieldsByName("f");
        for (int j = 0; j < sfs.size(); j++) {
          String content = ((Subfield)sfs.elementAt(j)).getContent();
          if (Environment.getWriteTs().checkInvNumber(content, Environment.getDocID())) {
            invNums.addElement(content);
          }
        }
      }
    }
    if (invNums.size() > 0) {
      listDlg.setListData(invNums);
      listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDSAVE_INVNUMLIST"));
      listDlg.setVisible(true);
      retVal = false;
    }
    
    return retVal;
  }

  private Vector initRezance() {
    // zajednicka inicijalizacija
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String dateString = formatter.format(new Date());
    String adresa = Environment.getAddress();

    Vector subfields000 = editDlg.field000.getSubfields();

    if (Environment.isNewRezance()) { // ako je u pitanju novi rezanac
      Environment.setNewRezance(false);
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
      Environment.setDocID(Environment.getWriteTs().reserveDocument());
      
      // ako se zapis snima prvi put i 001[d]=0 ili 001[d]=1, tada se automatski
      // dodaje potpolje 001[e] i vrednost mu se postavlja na poslednju
      // vrednost RN brojaca
      Field f001 = null;
      Iterator it = editDlg.utm.getFields().iterator();
      while (it.hasNext()) {
        Field f = (Field)it.next();
        if (f.getName().equals("001")) {
          f001 = f;
          break;
        }
      }
      if (f001 != null) {
        Vector vd = f001.getSubfieldsByName("d");
        if (vd.size() > 0) {
          Subfield sf001d = (Subfield)vd.elementAt(0);
          //if (sf001d.getContent().equals("0") || sf001d.getContent().equals("0")) {
            Vector ve = f001.getSubfieldsByName("e");
            if (ve.size() == 0) {
              newRN = getRN();
              Subfield sf001e = new Subfield("e", Integer.toString(newRN));
              f001.addSubfield(sf001e);
            } else {
              Subfield sf001e = (Subfield)ve.elementAt(0);
              sf001e.setContent(Integer.toString(getRN()));
            }
          //}
        }
      }
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

  private boolean showPreview(int docID, String rez) {
    Vector AU = new Vector();
    Vector TI = new Vector();
    Vector SG = new Vector();
    UnimarcConverter conv = new UnimarcConverter();
    DocumentParser dp = null;
    try {
      dp = new DocumentParser(Environment.getWriteTs().getPrefixMap(), "\36", "\37");
    } catch (Exception ex) {}
    Vector prefixes = dp.parseDocument(rez);
    for (int i = 0; i < prefixes.size(); i++) {
      PrefixPair pp = (PrefixPair)prefixes.elementAt(i);
      if (pp.prefName.equals("AU"))
        AU.addElement(conv.Unimarc2Unicode(pp.value));
      else if (pp.prefName.equals("TI"))
        TI.addElement(conv.Unimarc2Unicode(pp.value));
      else if (pp.prefName.equals("SG"))
        SG.addElement(conv.Unimarc2Unicode(pp.value));
    }
    Vector data = new Vector();
    
    Vector fields = RezanceUtilities.unpackRezance(rez);
    String sf001e = RezanceUtilities.getSubfieldContent(fields, "001e");
    if (sf001e != null)
      data.addElement("RN: " + sf001e);
    else
      data.addElement("ID: "+docID);
    for (int i = 0; i < AU.size(); i++)
      data.addElement("AU: "+(String)AU.elementAt(i));
    for (int i = 0; i < TI.size(); i++)
      data.addElement("TI: "+(String)TI.elementAt(i));
    for (int i = 0; i < SG.size(); i++)
      data.addElement("SG: "+(String)SG.elementAt(i));
    listDlg.setListData(data);
    listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDSAVE_RECORDSAVE"));
    listDlg.setVisible(true);
    return !listDlg.getSelection().equals("");
  }
  
  public int getRN() {
    int RN = 0;
    try {
      Connection conn = Environment.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
          "SELECT counter_value FROM misc_counters WHERE counter_name='RN' FOR UPDATE");
      if (rset.next()) {
        RN = rset.getInt(1) + 1;
        stmt.executeUpdate("UPDATE misc_counters SET counter_value=counter_value+1 WHERE counter_name='RN'");
      } else {
        RN = 0;
      }
      rset.close();
      stmt.close();
      conn.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return RN;
  }

  private EditDlg editDlg;
  private ListDlg listDlg;
  public boolean fieldsOK;
  public int newRN = 0;
}
