package com.gint.app.bisis.sharedsrv;

import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Klasa glavnog prozora od kog pocinje bisis. */
public class MainTask extends JFrame implements Task {

  public MainTask() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    this.setVisible(true);
  }

  private void jbInit() throws Exception  {
    this.setTitle("BISIS SERVER v3.01");
    this.getContentPane().setLayout(borderLayout1);
    setSize(400, 300);
    bZatvori.setText(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_MAINTASK_BUTTONCLOSE"));
    bZatvori.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bZatvori_actionPerformed(e);
      }
    });
    taLog.setText("");
    jPanel1.setLayout(borderLayout2);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(taLog, null);
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(bZatvori, BorderLayout.EAST);
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void bZatvori_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  public void start() {
    try {
      LocateRegistry.createRegistry(1099);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

//    System.setSecurityManager(new RMISecurityManager());
    try {
      RMITextServerPool t = new RMITextServerPool();
      t.init();
      Thread thr = new Thread(t);
      thr.start();
      Naming.bind("//localhost/RMITextServerPool", t);
      log(com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_MAINTASK_SERVERREADY"));
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_MAINTASK_SERVERREADY"), com.gint.app.bisis.sharedsrv.Messages.get("SHAREDSRVAPP_SERVERAPP_ERROR"), JOptionPane.ERROR_MESSAGE);//"Server ne mo\u017ee da se prijavi na servis."
      System.exit(1);
    }
  }

  public void log(String s) {
    if (logCounter++ < logTreshold)
      taLog.append(s);
    else {
      taLog.setText("");
      logCounter = 0;
    }
  }

  // atribut koji definise maksimalan broj redova u log-u
  private int logTreshold = 1000;
  public int getLogTreshold() {
    return logTreshold;
  }
  public void setLogTreshold(int i) {
    logTreshold = i;
  }

  private int logCounter = 0;
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel jPanel1 = new JPanel();
  JButton bZatvori = new JButton();
  JTextArea taLog = new JTextArea();
  BorderLayout borderLayout2 = new BorderLayout();

}
