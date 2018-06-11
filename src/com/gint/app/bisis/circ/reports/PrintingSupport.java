package com.gint.app.bisis.circ.reports;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import javax.swing.*;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.Messages;

public class PrintingSupport {
  private JFrame parent;
  private File file;
  private String[] msg1 = { Messages.get("PRINTINGSUPPORT_MSG1_STR1"),
                             Messages.get("PRINTINGSUPPORT_MSG1_STR2"),
                             Messages.get("PRINTINGSUPPORT_MSG1_STR3"),
                             Messages.get("PRINTINGSUPPORT_MSG1_STR4"),
                             "     "+Messages.get("PRINTINGSUPPORT_MSG1_STR5"),
                             Messages.get("PRINTINGSUPPORT_MSG1_STR6"),
                             Messages.get("PRINTINGSUPPORT_MSG1_STR7"),
                             Messages.get("PRINTINGSUPPORT_MSG1_STR8")};
  private String[] msg2 = {Messages.get("PRINTINGSUPPORT_MSG2_STR1"),
                            Messages.get("PRINTINGSUPPORT_MSG2_STR2"),
                            "     "+Messages.get("PRINTINGSUPPORT_MSG2_STR3"),
                            Messages.get("PRINTINGSUPPORT_MSG2_STR4"),
                            Messages.get("PRINTINGSUPPORT_MSG2_STR5"),
                            Messages.get("PRINTINGSUPPORT_MSG2_STR6")};
  public PrintingSupport(JFrame parent) {
    this.parent = parent;
  }

  private void prepareForPrinting(String content){
    try{
      file = File.createTempFile("rep", ".html");
      file.deleteOnExit();
//      RandomAccessFile rfile = new RandomAccessFile(file, "rw");
      FileOutputStream rfile = new FileOutputStream(file);
//      rfile.writeUTF(content);
      rfile.write(content.getBytes("Unicode"));
    }
    catch(IOException io){
      System.out.println(" Can not create temp file!");
      io.printStackTrace();
    }
  }

  public void printWithoutDialog(String content){
//    String cmd = System.getProperties().getProperty("print.command");
    String cmd = DBConnection.getPrintCommand();
    if (cmd != null){
      prepareForPrinting(content);
      try{
        Process proc = Runtime.getRuntime().exec(cmd + " \"" + file.getPath() + "\"");
      }
      catch(Exception e){
        showMessage(e.getMessage());
      }
    }
    else{
      showMessage(msg2);
    }
  }

  public void printWithDialog(String content){
//    String path = System.getProperties().getProperty("browser.path");
    String path = DBConnection.getPrintBrowser();
    if (path != null){
      prepareForPrinting(content);
      try{
        Process proc = Runtime.getRuntime().exec(path + " \"" + file.getPath() + "\"");
//        proc.waitFor();
      }
      catch(Exception e){
        showMessage(e.getMessage());
      }
    }
    else{
      showMessage(msg1);
    }
  }

  private void showMessage(Object msg){
    JOptionPane.showOptionDialog(parent, msg,Messages.get("PRINTINGSUPPORT_OPTIONSPANE_MSG1"), JOptionPane.OK_OPTION,
                                 JOptionPane.ERROR_MESSAGE, null, new String[] {Messages.get("PRINTINGSUPPORT_OPTIONSPANE_MSG2")}, null);
  }
}
