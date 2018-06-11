package com.gint.app.bisis.editor;

import java.io.*;
import javax.swing.*;

public class PrintingSupport {
  private JFrame parent;
  private File file;
  private String[] msg1 = {
    Messages.get("BISISAPP_PRINTSUPPORT_BROWSERPATHSETTINGS1"),
    "-Dbrowser.path=\"C:/Program Files/Plus!/Microsoft Internet/Iexplore.exe\"",
    "-Dbrowser.path=\"rundll32 url.dll,FileProtocolHandler\"",
    "-Dbrowser.path=\"rundll32 mshtml.dll,RunHTMLApplication\"",
    "           " + Messages.get("BISISAPP_PRINTSUPPORT_OR"),
    "-Dbrowser.path=\" << putanja do nekog drugog browsera>> "};
  private String[] msg2 = {
    Messages.get("BISISAPP_PRINTSUPPORT_BROWSERPATHSETTINGS2"),
    "-Dprint.command=\"rundll32.exe C:/WINNT/System32/mshtml.dll,PrintHTML\"",
    "           " + Messages.get("BISISAPP_PRINTSUPPORT_OR"),
    "-Dprint.command=\"C:/Program Files/Microsoft Office/Office/msohtmed.exe /p\"",
    "(ili ne\u0161to sli\u010dno) kod startovanja JVM."};

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
      System.out.println(Messages.get("BISISAPP_PRINTSUPPORT_ERRORMSG"));
      io.printStackTrace();
    }
  }

  public void printWithoutDialog(String content){
//    String cmd = System.getProperties().getProperty("print.command");
    String cmd = Environment.getPrintCommand();
    if (cmd != null){
      prepareForPrinting(content);
      try{
        Process proc = Runtime.getRuntime().exec(cmd+ " \"" + file.getPath() + "\"");
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
    String path = Environment.getPrintBrowser();
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
    JOptionPane.showOptionDialog(parent, msg, Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.OK_OPTION,
                                 JOptionPane.ERROR_MESSAGE, null, new String[] {Messages.get("BISISAPP_ERRORDLG_OK")}, null);
  }
}
