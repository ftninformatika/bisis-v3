package com.gint.app.bisis.editor.edit;

import java.io.*;
import java.util.*;

import com.gint.app.bisis.editor.*;

/**  Snimanje rezanca u pozadinskom procesu
  */
public class SaveTask {

  public SaveTask(String doc, int doc_id, MainFrame mf, int myRN) {
    if (mf.saveCounter.getCount() == 0) {
      mf.saveCounter.increment();
      mf.saveTimer.start();
    } else
      mf.saveCounter.increment();
    int status = Environment.getWriteTs().updateWithNewConnection(doc_id, doc);
    if (status == -1) {
      mf.saveCounter.setLastSuccessful(false, doc_id);
      saveToLog(doc_id, doc);
    } else {
      if (Environment.getOriginalRN() != 0) {
        if (ImportHistory.exists(Environment.getConnection())) {
          int origRN = Environment.getOriginalRN();
          Date today = new Date();
          ImportHistory.addToRecordMap(Environment.getConnection(), myRN, 
              origRN, today);
        }
        Environment.setOriginalRN(0);
      }
    }

    int count = mf.saveCounter.decrement();
  }

  public void saveToLog(int doc_id, String doc) {
    RandomAccessFile raf;
    Date date = new Date();
    try {
      raf = new RandomAccessFile("logfile.txt", "rw");
      // odemo na kraj fajla
      raf.seek(raf.length());
      raf.writeBytes("\r\n");
      raf.writeBytes(date.toString());
      raf.writeBytes("\t");
      raf.writeBytes(""+date.getTime());
      raf.writeBytes("\t");
      raf.writeBytes("" + doc_id);
      raf.writeBytes("\t");
      raf.writeBytes(doc);
      raf.close();
    } catch (Exception ex) {
      return;
    };
  }
}
