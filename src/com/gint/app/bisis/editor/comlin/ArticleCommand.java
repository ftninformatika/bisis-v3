package com.gint.app.bisis.editor.comlin;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis.editor.RezanceUtilities;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;

public class ArticleCommand extends AbstractCommand {

  public ArticleCommand(MainFrame mf) {
    this.mf = mf;
  }
  
  public void execute() {
    
    Vector procLevelList = Environment.getLib().getProcLevelList("A1");
    if (procLevelList.size() == 0) {
      JOptionPane.showMessageDialog(mf, "Nemate pravo rada sa tipom publikacije A1", "Greska", JOptionPane.ERROR_MESSAGE);
      return;
    }
    Vector p2 = new Vector();
    for (int i = 0; i < procLevelList.size(); i++) {
      String s = (String)procLevelList.elementAt(i);
      Integer I = new Integer(s);
      p2.addElement(I);
    }
    Integer maxProcLevel = (Integer)Collections.max(p2);
    
    Vector manLevelList = Environment.getLib().getManLevelList("A1", maxProcLevel.intValue());
    if (manLevelList.size() == 0) {
      JOptionPane.showMessageDialog(mf, "Nemate pravo rada sa tipom publikacije A1", "Greska", JOptionPane.ERROR_MESSAGE);
      return;
    }
    p2 = new Vector();
    for (int i = 0; i < manLevelList.size(); i++) {
      String s = (String)manLevelList.elementAt(i);
      Integer I = new Integer(s);
      p2.addElement(I);
    }
    Integer maxManLevel = (Integer)Collections.max(p2);
    
    Environment.getLib().setCurrContext("A1", maxProcLevel.intValue(), maxManLevel.intValue());
    
    if (params.size() != 1) {
      JOptionPane.showMessageDialog(mf, "Komanda article ima jedan parametar", "Greska", JOptionPane.ERROR_MESSAGE);
      return;
    }
    int hit;
    try {
      hit = Integer.parseInt((String)params.elementAt(0));
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(mf, "Neispravan parametar -- mora biti redni broj pogotka", "Greska", JOptionPane.ERROR_MESSAGE);
      return;
    }
    int docID = -1;
    try { docID = Environment.getReadTs().getDocID(hit); } catch (Exception ex) {}
    if (docID == -1) {
      JOptionPane.showMessageDialog(mf, "Pogre\u0161an broj pogotka", "Greska", JOptionPane.ERROR_MESSAGE);
      return;
    }
    // pokupi vrednost potpolja 001[e] iz master zapisa i upisi ga u 474[1]
    String rez = "";
    try { 
      rez = Environment.getReadTs().getDoc(docID);
      if (rez.equals("")) {
        if (Environment.getReadTs() == Environment.getInternalTs()) {
          JLabel msg = new JLabel("Zapis je prazan. Da ga obri\u0161emo?");
          msg.setForeground(java.awt.Color.black);
          String defBtn = "  Da  ";
          String[] buttons = {defBtn, "  Ne  "};
          if (JOptionPane.showOptionDialog(mf, msg, "Greska",
              JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
              null, buttons, defBtn) == JOptionPane.YES_OPTION) {
            Environment.getWriteTs().delete(docID);
          }
          return;
        } else {
          JOptionPane.showMessageDialog(mf, "Zapis je prazan!", "Greska", 
              JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
    } catch (Exception ex) {
    }
    Vector masterFields = RezanceUtilities.unpackRezance(rez);
    Field f001 = null;
    Iterator it = masterFields.iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      if (f.getName().equals("001")) {
        f001 = f;
        break;
      }
    }
    if (f001 == null) {
      JOptionPane.showMessageDialog(mf, "U zapisu nema broja zapisa (001e)!", "Greska", 
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    String recID = null;
    it = f001.getSubfields().iterator();
    while (it.hasNext()) {
      Subfield sf = (Subfield)it.next();
      if (sf.getName().equals("e") && !sf.getContent().trim().equals("")) {
        recID = sf.getContent().trim();
        break;
      }
    }
    if (recID == null) {
      JOptionPane.showMessageDialog(mf, "U zapisu nema broja zapisa (001e)!", "Greska", 
          JOptionPane.ERROR_MESSAGE);
      return;
    }
      
    Vector fields = new Vector();
    fields = mf.edDlg.init(fields);
    add4741(fields, recID);
    mf.edDlg.utm.setFields(fields);
    mf.tfComLin.setText("");
    Environment.setNewRezance(true);
    Environment.setDocID(-1);
    mf.edDlg.setVisible(true);
  }
  
  private void add4741(Vector fields, String recID) {
    Field f474 = null;
    int size = fields.size();
    for (int i = 0; i < size; i++) {
      Field f = (Field)fields.elementAt(i);
      if (f.getName().equals("474")) {
        Vector v = f.getSubfields();
        if (v.size() == 0) {
          v.addElement(new Subfield("1", recID));
        } else {
          for (int j = 0; j < v.size(); j++) {
            Subfield sf = (Subfield)v.elementAt(j);
            if (sf.getName().equals("1")) {
              sf.setContent(recID);
              break;
            }
          }
        }
        break;
      }
      if (f.getName().compareTo("474") > 0) {
        Vector v = new Vector();
        v.addElement(new Subfield("1", recID));
        f474 = new Field("474", " ", " ", v);
        fields.insertElementAt(f474, i);
        break;
      }
    }
  }
}
