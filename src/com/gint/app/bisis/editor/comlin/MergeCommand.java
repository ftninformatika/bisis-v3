package com.gint.app.bisis.editor.comlin;

import java.util.Iterator;

import javax.swing.JOptionPane;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.util.gui.SwingWorker;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class MergeCommand extends AbstractCommand {
  
  public MergeCommand(MainFrame mf) {
    this.mf = mf;
  }
  
  public void execute() {
    if (Environment.getReadTs().getHitCount() == 0) {
      JOptionPane.showMessageDialog(mf, "Nema pogodaka!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (params.size() < 2) {
      JOptionPane.showMessageDialog(mf, "Komanda mora imati bar dva parametra!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
      return;
    }
    int[] docIDs = new int[params.size()];
    Record[] records = new Record[params.size()];
    for (int i = 0; i < docIDs.length; i++) {
      int hit = 0;
      try {
        hit = Integer.parseInt((String)params.elementAt(i)); 
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(mf, "Neispravna vrednost parametra: " + (String)params.elementAt(i), "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
        return;
      }
      docIDs[i] = Environment.getReadTs().getDocID(hit);
      records[i] = RecordFactory.fromUNIMARC(Environment.getReadTs().getDoc(docIDs[i]));
    }
    Record result = merge(records);
    if (result == null)
      return;
    mf.mergeDlg.setSource(records);
    mf.mergeDlg.setResult(result);
    mf.mergeDlg.setVisible(true);
    if (mf.mergeDlg.isConfirmed()) {
      int resultDocID = docIDs[0];
      int[] deletedDocIDs = new int[docIDs.length - 1];
      for (int i = 0; i < docIDs.length - 1; i++)
        deletedDocIDs[i] = docIDs[i + 1];
      String resultDoc = RecordFactory.toUNIMARC(result);
      final int p1 = resultDocID;
      final String p2 = resultDoc;
      final int[] p3 = deletedDocIDs;
      final SwingWorker worker = new SwingWorker() {
        public Object construct() {
          return new MergeTask(p1, p2, p3);
        }
      };
      worker.start();
    }
    mf.tfComLin.setText("");
  }
  
  private Record merge(Record[] records) {
    if (records.length == 0)
      return null;
    Record retVal = new Record();
    records[0].pack();
    retVal.getFields().addAll(records[0].getFields());
    for (int i = 1; i < records.length; i++) {
      records[i].pack();
      Iterator i996 = records[i].getFields("996").iterator();
      while (i996.hasNext())
        retVal.getFields().add(i996.next());
      Iterator i997 = records[i].getFields("997").iterator();
      while (i997.hasNext())
        retVal.getFields().add(i997.next());
    }

    // prepisi UDK iz prvog zapisa koji ga sadrzi (pocevsi od drugog!)
    String udk = "";
    for (int i = 1; i < records.length; i++) {
      String test = records[i].getSubfieldContent("675a");
      if (test != null) {
        udk = test;
        break;
      }
    }
    Field f675 = retVal.getField("675");
    if (f675 == null) {
      f675 = new Field("675");
      retVal.getFields().add(f675);
    }
    Subfield sf675a = f675.getSubfield('a');
    if (sf675a == null) {
      sf675a = new Subfield('a');
      f675.add(sf675a);
    }
    sf675a.setContent(udk);

    retVal.sort();
    return retVal;
  }
  
  public class MergeTask {
    public MergeTask(int docID, String doc, int[] docIDs) {
      mf.saveCounter.increment();
      if (mf.saveCounter.getCount() > 0)
        mf.saveTimer.start();
      Environment.getWriteTs().merge(docID, doc, docIDs);
      mf.saveCounter.decrement();
    }
  }

}
