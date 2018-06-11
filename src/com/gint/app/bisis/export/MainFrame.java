package com.gint.app.bisis.export;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.gint.util.gui.SwingWorker;
import com.gint.app.bisis.textsrv.*;

/** Glavni frejm aplikacije. */
public class MainFrame extends JFrame {
  XYLayout xYLayout1 = new XYLayout();
  ConfigDlg configDlg = new ConfigDlg(null, 
    Messages.get("MAINFRAME_CONFIGDLG_FRAMETITLE"), true);
  JLabel lPicture = new JLabel();
  JButton bClose = new JButton();
  JButton bConfig = new JButton();
  JFileChooser fcOpen = new JFileChooser();
  JFileChooser fcSave = new JFileChooser();
  ScanningDlg scanningDlg = new ScanningDlg(this, 
    Messages.get("MAINFRAME_SCANNINGDLG_FRAMETITLE"), true);
  ImportProgressDlg importDlg = new ImportProgressDlg(this, 
    Messages.get("MAINFRAME_IMPORTPROGRESSDLG_FRAMETITLE"), true);
  ExportSettingsDlg exportSettingsDlg = new ExportSettingsDlg(this, 
    Messages.get("MAINFRAME_EXPORTSETTINGSDLG_FRAMETITLE"), true);
  ExportProgressDlg exportProgressDlg = new ExportProgressDlg(this, 
    Messages.get("MAINFRAME_EXPORTPROGRESSDLG_FRAMETITLE"), true);
  IgnoreFieldsDlg ignoreFieldsDlg = new IgnoreFieldsDlg(this, 
    Messages.get("MAINFRAME_IGNOREDFIELDSDLG_FRAMETITLE"), true);
  NeuspeliZapisiDlg neuspeliZapisiDlg = new NeuspeliZapisiDlg(this, 
    Messages.get("MAINFRAME_FAILEDRECORDSDLG_FRAMETITLE"), true);
  com.gint.app.bisis.export.libenv.ProgressDlg progressDlg = new 
    com.gint.app.bisis.export.libenv.ProgressDlg(this, "", true);
  JMenuBar mbMainMenu = new JMenuBar();
  JMenu mPodaci = new JMenu(Messages.get("MAINFRAME_MENU_RECORDS"));
  JMenu mOkruzenje = new JMenu(Messages.get("MAINFRAME_MENU_ENVIRONMENT"));
  JMenu mUnimarc = new JMenu(Messages.get("MAINFRAME_MENU_UNIMARC"));
  JMenu mCirkulacija = new JMenu(Messages.get("MAINFRAME_MENU_CIRCULATION"));

  JMenuItem miZapisiImport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_IMPORTRECORDS"));
  JMenuItem miZapisiExport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_EXPORTRECORDS"));
  JMenuItem miNeuspeli = new JMenuItem(
    Messages.get("MAINFRAME_MENU_FAILEDRECORDS"));
  JMenuItem miOkruzImport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_IMPORTENV"));
  JMenuItem miOkruzExport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_EXPORTENV"));
  JMenuItem miUnimarcImport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_IMPORTUNIMARC"));
  JMenuItem miUnimarcExport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_EXPORTUNIMARC"));
  JMenuItem miCircImport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_IMPORTCIRC"));
  JMenuItem miCircExport = new JMenuItem(
    Messages.get("MAINFRAME_MENU_EXPORTCIRC"));

  //Construct the frame
  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    miZapisiImport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miZapisiImport_actionPerformed(e);
      }
    });
    miZapisiExport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miZapisiExport_actionPerformed(e);
      }
    });
    miNeuspeli.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miNeuspeli_actionPerformed(e);
      }
    });
    miOkruzImport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miOkruzImport_actionPerformed(e);
      }
    });
    miOkruzExport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miOkruzExport_actionPerformed(e);
      }
    });
    miUnimarcImport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miUnimarcImport_actionPerformed(e);
      }
    });
    miUnimarcExport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miUnimarcExport_actionPerformed(e);
      }
    });
    miCircImport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miCircImport_actionPerformed(e);
      }
    });
    miCircExport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miCircExport_actionPerformed(e);
      }
    });

    mPodaci.add(miZapisiImport);
    mPodaci.add(miZapisiExport);
    mPodaci.add(miNeuspeli);
    mOkruzenje.add(miOkruzImport);
    mOkruzenje.add(miOkruzExport);
    mUnimarc.add(miUnimarcImport);
    mUnimarc.add(miUnimarcExport);
    mCirkulacija.add(miCircImport);
    mCirkulacija.add(miCircExport);
    mbMainMenu.add(mPodaci);
    mbMainMenu.add(mOkruzenje);
    mbMainMenu.add(mUnimarc);
    mbMainMenu.add(mCirkulacija);
    setJMenuBar(mbMainMenu);
    setIconImage(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/icon.gif")).getImage());
    lPicture.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/import.gif")));
    this.getContentPane().setLayout(xYLayout1);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setTitle(Messages.get("MAINFRAME_FRAMETITLE"));
    xYLayout1.setHeight(385);
    xYLayout1.setWidth(440);
    bClose.setText(Messages.get("MAINFRAME_BUTTON_CLOSE"));
    bClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bClose_actionPerformed(e);
      }
    });
    bClose.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/delete.gif")));
    bConfig.setText(Messages.get("MAINFRAME_BUTTON_SERVER"));
    bConfig.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bConfig_actionPerformed(e);
      }
    });
    this.getContentPane().add(lPicture, new XYConstraints(15, 17, -1, -1));
    this.getContentPane().add(bClose, new XYConstraints(305, 286, 112, -1));
    this.getContentPane().add(bConfig, new XYConstraints(165, 284, 93, 33));
    setSize(440, 385);
    fcOpen.setApproveButtonText(
      Messages.get("MAINFRAME_FILEOPEN_BUTTON_APPROVE"));
    fcOpen.setMultiSelectionEnabled(false);
    fcOpen.setCurrentDirectory(new File("."));
    fcSave.setMultiSelectionEnabled(false);
    fcSave.setCurrentDirectory(new File("."));
  }

  public int recordCount = 0;

  /** Klasa koja opisuje proces "skeniranja" import fajla. "Skeniranje"
      sluzi da bi se prebrojali redovi u datoteci, odnosno da se zna
      broj zapisa koji ce se importovati. A to sve zbog progress bara.
      Konstruktor se izvrsava u pozadinskom threadu pomocu SwingWorker
      klase.
    */
  public class ScanTask {
    public ScanTask(String fileName) {
      BufferedReader in = null;
      try { in = new BufferedReader(new FileReader(fileName)); } 
      catch (FileNotFoundException ex) {}
      int count = 0;
      String line;
      try {
        while ((line = in.readLine()) != null)
          count++;
        in.close();
        recordCount = count;
      } catch (IOException ex) {
      }
      scanningDlg.setFinished(true);
    }
  }

  /** Klasa koja opisuje proces importovanja. Konstruktor se izvrsava u
      pozadinskom threadu pomocu SwingWorker klase.
    */
  public class ImportTask {
    public ImportTask(String fileName, Connection conn) {
      RandomAccessFile in = null;
      RandomAccessFile out = null;
      try {
        in = new RandomAccessFile(fileName, "r");
        if (Config.getUseBadFile())
          out = new RandomAccessFile(Config.getBadFileName(), "rw");
      } catch (Exception ex) {
      }
      TextServer textServer = new TextServer(Config.getDBType(), conn, 
        Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      int lineCount = 0;
      int badCount = 0;
      String line;
      try {
        while ((line = in.readLine()) != null) {
          if (importDlg.isStopFlag())
            break;
          line = line.trim();
          if (Config.getIgnore000())
            line = trim000(line);
          if (Config.getIgnore9xx())
            line = trim9xx(line);
          if (Config.getTrimFirst())
            line = line.substring(Config.getTrimHowMany());
          if (line.equals(""))
            continue;
          line = ExportEscapes.unescapeText(line);
          importDlg.setRecordCount(++lineCount);
          try {
            int retCode = textServer.insert(line);
            if (retCode == -1) {
              if (Config.getUseBadFile())
                out.writeBytes(line+"\r\n");
              badCount++;
            }
          } catch (Exception ex) {
            if (Config.getUseBadFile())
              out.writeBytes(line+"\r\n");
            badCount++;
          }
          importDlg.setBadCount(badCount);
        }
        in.close();
        if (Config.getUseBadFile())
          out.close();
      } catch (IOException ex) {
      }
      if (badCount == 0)
        if (Config.getUseBadFile())
          new File(Config.getBadFileName()).delete();
      importDlg.setFinished(true);
    }
  }

  /** Klasa koja opisuje proces eksportovanja. Kostruktor se izvrsava u
      pozadinskom threadu pomocu SwingWorker klase.
    */
  public class ExportTask {
    public ExportTask(String filename, Connection conn, int from, int to) {
      RandomAccessFile out = null;
      try { out = new RandomAccessFile(filename, "rw"); } 
      catch (IOException ex) { }
      String url = Config.getUrl();
      String username = Config.getDbUsername();
      String password = Config.getDbPassword();
      TextServer textServer = new TextServer(Config.getDBType(), conn, url, 
        username, password);
      int current = from;
      String line;
      String rezance;
      try {
        while (current <= to) {
          exportProgressDlg.setCurrent(current);
          rezance = textServer.getDoc(current);
          line = ExportEscapes.escapeText(rezance);
          if (!line.equals(""))
          	out.writeBytes(line+"\r\n");
          current++;
        }
        out.close();
      } catch (IOException ex) {
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      exportProgressDlg.setFinished(true);
    }
  }

  /** Ceo posao oko importovanja:<br>
      <ol>
      <li>pita da li da se pravi fajl za neispravne zapise i
          eventualno za njegovo ime
      <li>pita da li se ignorisu polja 000 i 9xx
      <li>pokrece task za skeniranje uz prikazivanje odgovarajuceg dijaloga
      <li>povezuje se sa bazom
      <li>pokrece task za importovanje uz prikazivanje odgovarajuceg dijaloga
      <li>zatvara konekciju sa bazom
      </ol>
    */
  public void importFile(String fileName) {
    // pita da li da se pravi fajl sa neuspesno importovanim rezancima
    String badfileName = "";
    JLabel msg = new JLabel(Messages.get("MAINFRAME_FAILEDDLG_MESSAGE"));
    msg.setForeground(Color.black);
    String defBtn = Messages.get("MAINFRAME_FAILEDDLG_BUTTON_YES");
    String[] buttons = {defBtn, Messages.get("MAINFRAME_FAILEDDLG_BUTTON_NO")};
    if (JOptionPane.showOptionDialog(
          null,
          msg,
          Messages.get("MAINFRAME_FAILEDDLG_FRAMETITLE"),
          JOptionPane.YES_NO_OPTION,
          JOptionPane.INFORMATION_MESSAGE,
          null,
          buttons,
          defBtn) == JOptionPane.YES_OPTION) {
      Config.setUseBadFile(true);
      fcSave.setDialogTitle(Messages.get("MAINFRAME_FILESAVE_FRAMETITLE"));
      int retVal = fcSave.showSaveDialog(null);
      String filename = fcSave.getSelectedFile().getAbsolutePath();
      Config.setBadFileName(filename);
      File file = new File(filename);
      if (file.exists()) {
        if (file.canWrite()) {
          JLabel msg2 = new JLabel(
            Messages.get("MAINFRAME_OVERWRITEDLG_MESSAGE"));
          msg.setForeground(Color.black);
          String defBtn2 = Messages.get("MAINFRAME_OVERWRITEDLG_BUTTON_YES");
          String[] buttons2 = {defBtn, 
              Messages.get("MAINFRAME_OVERWRITEDLG_BUTTON_NO")};
          if (JOptionPane.showOptionDialog(
                 null,
                 msg2,
                Messages.get("MAINFRAME_OVERWRITEDLG_FRAMETITLE"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                buttons2,
                defBtn2) == JOptionPane.YES_OPTION) {
            Config.setUseBadFile(true);
          }
        } else {
          JOptionPane.showMessageDialog(null,
            Messages.get("MAINFRAME_CANNOTWRITEDLG_MESSAGE"),
            Messages.get("MAINFRAME_CANNOTWRITEDLG_FRAMETITLE"),
            JOptionPane.ERROR_MESSAGE);
          Config.setUseBadFile(false);
        }
      }
    } else {
      Config.setUseBadFile(false);
    }

    ignoreFieldsDlg.setVisible(true);
    final Connection conn;
    final String fName = fileName;

    // "skenira" import fajl
    scanningDlg.setFinished(false);
    SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new ScanTask(fName);
      }
    };
    worker.start();
    scanningDlg.timer.start();
    scanningDlg.setVisible(true);

    // importuje rezance
    try { Class.forName(Config.getDriver()); } 
    catch (ClassNotFoundException ex) {}
    try {
       conn = DriverManager.getConnection(Config.getUrl(), 
        Config.getDbUsername(), Config.getDbPassword());
       conn.setAutoCommit(false);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
    importDlg.initJob(recordCount);
    SwingWorker worker2 = new SwingWorker() {
      public Object construct() {
        return new ImportTask(fName, conn);
      }
    };
    worker2.start();
    importDlg.timer.start();
    importDlg.setVisible(true);

    // gotovo je, zatvara konekciju
    try { conn.close(); } catch (SQLException ex) { }
  }

  /** Ceo posao oko eksportovanja:<br>
      <ol>
      <li>povezivanje sa bazom
      <li>pokretanje taska za eksportovanje uz prikazivanje odgovarajuceg dijaloga
      <li>zatvaranje konekcije
      </ol>
    */
  public void exportFile(final String filename, final int from, final int to) {
    final Connection conn;
    try { Class.forName(Config.getDriver()); } 
    catch (ClassNotFoundException ex) {}
    try {
       conn = DriverManager.getConnection(Config.getUrl(), 
        Config.getDbUsername(), Config.getDbPassword());
       conn.setAutoCommit(false);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
    exportProgressDlg.initJob(from, to);
    SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new ExportTask(filename, conn, from, to);
      }
    };
    worker.start();
    exportProgressDlg.timer.start();
    exportProgressDlg.setVisible(true);

    // gotovo je, zatvara konekciju
    try { conn.close(); } catch (SQLException ex) { }
  }

  /** Uklanja polje 000 iz rezanca (ako ga ima).
      @param s Rezanac
      @return Rezanac bez polja 000
    */
  public String trim000(String s) {
    int pos = s.indexOf("\36"+"001");
    if (pos > -1)
      return s.substring(pos+1);
    else
      return s;
  }

  /** Uklanja sva polja 9xx iz rezanca (ako ih ima).
      @param s Rezanac
      @return Rezanac bez polja 9xx
    */
  public String trim9xx(String s) {
    int pos = s.indexOf("\36"+"9");
    if (pos > -1)
      return s.substring(0, pos);
    else
      return s;
  }

  /** Importuje bibliotekarsko okruzenje. */
  public void importOkruz(final String filename) {
    try { Class.forName(Config.getDriver()); } catch (ClassNotFoundException ex) {}
    try {
      final Connection conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(true);
      progressDlg.setFinished(false);
      SwingWorker worker2 = new SwingWorker() {
        public Object construct() {
          return new com.gint.app.bisis.export.libenv.Importer(filename, conn, progressDlg);
        }
      };
      worker2.start();
      progressDlg.setMessage(
        Messages.get("MAINFRAME_PROGRESSDLG_MESSAGE_IMPORTENV"));
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  /** Eksportuje bibliotekarsko okruzenje. */
  public void exportOkruz(final String filename) {
    try { Class.forName(Config.getDriver()); } catch (ClassNotFoundException ex) {}
    try {
      final Connection conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(false);
      progressDlg.setFinished(false);
      SwingWorker worker2 = new SwingWorker() {
        public Object construct() {
          return new com.gint.app.bisis.export.libenv.Exporter(filename, conn, progressDlg);
        }
      };
      worker2.start();
      progressDlg.setMessage(
        Messages.get("MAINFRAME_PROGRESSDLG_MESSAGE_EXPORTENV"));
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  /** Importuje konfiguraciju UNIMARC-a. */
  public void importUnimarc(final String filename) {
    try { Class.forName(Config.getDriver()); } catch (ClassNotFoundException ex) {}
    try {
      final Connection conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(true);
      progressDlg.setFinished(false);
      SwingWorker worker2 = new SwingWorker() {
        public Object construct() {
          return new com.gint.app.bisis.export.unimarc.Importer(filename, conn, progressDlg);
        }
      };
      worker2.start();
      progressDlg.setMessage(
        Messages.get("MAINFRAME_PROGRESSDLG_MESSAGE_IMPORTUNIMARC"));
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  /** Eksportuje konfiguraciju UNIMARC-a. */
  public void exportUnimarc(final String filename) {
    try { Class.forName(Config.getDriver()); } catch (ClassNotFoundException ex) {}
    try {
      final Connection conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(false);
      progressDlg.setFinished(false);
      SwingWorker worker2 = new SwingWorker() {
        public Object construct() {
          return new com.gint.app.bisis.export.unimarc.Exporter(filename, conn, progressDlg);
        }
      };
      worker2.start();
      progressDlg.setMessage(
        Messages.get("MAINFRAME_PROGRESSDLG_MESSAGE_EXPORTUNIMARC"));
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  /** Importuje Cirkulaciju. */
  public void importCirkulacija(final String filename) {
    try { Class.forName(Config.getDriver()); } catch (ClassNotFoundException ex) {}
    try {

      final Connection conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(true);
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_INITRECORDSDLG_MESSAGE"));
        final boolean brisi;

        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_INITRECORDSDLG_BUTTON_YES");
        String[] buttons = {defBtn, 
            Messages.get("MAINFRAME_INITRECORDSDLG_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_INITRECORDSDLG_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION)
          brisi = true;
        else
          brisi = false;

      progressDlg.setFinished(false);
      SwingWorker worker2 = new SwingWorker() {
        public Object construct() {
          return new com.gint.app.bisis.export.circ.Importer(filename, conn, 
            progressDlg, brisi);
        }
      };
      worker2.start();
      progressDlg.setMessage(
        Messages.get("MAINFRAME_PROGRESSDLG_MESSAGE_IMPORTCIRC"));
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  /** Eksportuje Cirkulaciju. */
  public void exportCirkulacija(final String filename) {
    try { Class.forName(Config.getDriver()); } 
    catch (ClassNotFoundException ex) {}
    try {

      final Connection conn = DriverManager.getConnection(Config.getUrl(), 
        Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(false);
      progressDlg.setFinished(false);

      SwingWorker worker2 = new SwingWorker() {
        public Object construct() {
          return new com.gint.app.bisis.export.circ.Exporter(filename, conn, 
            progressDlg);
        }
      };
      worker2.start();
      progressDlg.setMessage(
        Messages.get("MAINFRAME_PROGRESSDLG_MESSAGE_EXPORTCIRC"));
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
  }


  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void bClose_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void bConfig_actionPerformed(ActionEvent e) {
    configDlg.initData();
    configDlg.setVisible(true);
  }

  void miZapisiImport_actionPerformed(ActionEvent e) {
    String fileName = null;
    fcOpen.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_IMPORTRECORDS"));
    int retVal = fcOpen.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      fileName = fcOpen.getSelectedFile().getAbsolutePath();
      if (Config.getConfirm()) {
        JLabel msg = new JLabel(Messages.get("MAINFRAME_CONFIRMDLG_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_YES");
        String[] buttons = {defBtn, 
            Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
               null,
               msg,
              Messages.get("MAINFRAME_CONFIRMDLG_FRAMETITLE"),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.INFORMATION_MESSAGE,
              null,
              buttons,
              defBtn) == JOptionPane.YES_OPTION) {
          importFile(fileName);
        }
      } else {
        importFile(fileName);
      }
    }
  }

  void miZapisiExport_actionPerformed(ActionEvent e) {
    exportSettingsDlg.setVisible(true);
    int from = exportSettingsDlg.getFrom();
    int to = exportSettingsDlg.getTo();
    if (from != -1 && to != -1 && from <= to) {
      fcSave.setDialogTitle(
        Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_EXPORTRECORDS"));
      int retVal = fcSave.showSaveDialog(null);
      String filename = fcSave.getSelectedFile().getAbsolutePath();
      File file = new File(filename);
      if (file.exists()) {
        if (file.canWrite()) {
          JLabel msg = new JLabel(
            Messages.get("MAINFRAME_CONFIRMDLG2_MESSAGE"));
          msg.setForeground(Color.black);
          String defBtn = Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_YES");
          String[] buttons = {defBtn, 
            Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_NO")};
          if (JOptionPane.showOptionDialog(
              null,
              msg,
              Messages.get("MAINFRAME_CONFIRMDLG2_FRAMETITLE"),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.INFORMATION_MESSAGE,
              null,
              buttons,
              defBtn) == JOptionPane.YES_OPTION) {
            exportFile(filename, from, to);
          }
        } else {
          JOptionPane.showMessageDialog(null, 
            Messages.get("MAINFRAME_CANNOTWRITEDLG_MESSAGE"),
            Messages.get("MAINFRAME_CANNOTWRITEDLG_FRAMETITLE"),
            JOptionPane.ERROR_MESSAGE);
        }
      } else {
        exportFile(filename, from, to);
      }
    }
  }

  void miNeuspeli_actionPerformed(ActionEvent e) {
    neuspeliZapisiDlg.setVisible(true);
  }

  void miOkruzImport_actionPerformed(ActionEvent e) {
    String fileName = null;
    fcOpen.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_IMPORTENV"));
    int retVal = fcOpen.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      fileName = fcOpen.getSelectedFile().getAbsolutePath();
      if (Config.getConfirm()) {
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_CONFIRMDLG_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_YES");
        String[] buttons = {defBtn, 
          Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_CONFIRMDLG_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
          importOkruz(fileName);
        }
      } else {
        importOkruz(fileName);
      }
    }
  }

  void miOkruzExport_actionPerformed(ActionEvent e) {
    fcSave.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_EXPORTENV"));
    int retVal = fcSave.showSaveDialog(null);
    if (retVal != JFileChooser.APPROVE_OPTION)
      return;
    String filename = fcSave.getSelectedFile().getAbsolutePath();
    File file = new File(filename);
    if (file.exists()) {
      if (file.canWrite()) {
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_CONFIRMDLG2_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_YES");
        String[] buttons = {defBtn, 
          Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_CONFIRMDLG2_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
          exportOkruz(filename);
        }
      } else {
        JOptionPane.showMessageDialog(null, 
            Messages.get("MAINFRAME_CANNOTWRITEDLG_MESSAGE"),
            Messages.get("MAINFRAME_CANNOTWRITEDLG_FRAMETITLE"),
          JOptionPane.ERROR_MESSAGE);
      }
    } else {
      exportOkruz(filename);
    }
  }

  void miUnimarcImport_actionPerformed(ActionEvent e) {
    String fileName = null;
    fcOpen.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_IMPORTUNIMARC"));
    int retVal = fcOpen.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      fileName = fcOpen.getSelectedFile().getAbsolutePath();
      if (Config.getConfirm()) {
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_CONFIRMDLG_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_YES");
        String[] buttons = {defBtn, 
          Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_CONFIRMDLG_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
          importUnimarc(fileName);
        }
      } else {
        importUnimarc(fileName);
      }
    }
  }

  void miUnimarcExport_actionPerformed(ActionEvent e) {
    fcSave.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_EXPORTUNIMARC"));
    int retVal = fcSave.showSaveDialog(null);
    if (retVal != JFileChooser.APPROVE_OPTION)
      return;
    String filename = fcSave.getSelectedFile().getAbsolutePath();
    File file = new File(filename);
    if (file.exists()) {
      if (file.canWrite()) {
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_CONFIRMDLG2_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_YES");
        String[] buttons = {defBtn, 
          Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_CONFIRMDLG2_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
          exportUnimarc(filename);
        }
      } else {
        JOptionPane.showMessageDialog(null, 
          Messages.get("MAINFRAME_CANNOTWRITEDLG_MESSAGE"),
          Messages.get("MAINFRAME_CANNOTWRITEDLG_FRAMETITLE"),
          JOptionPane.ERROR_MESSAGE);
      }
    } else {
      exportUnimarc(filename);
    }
  }

  void miCircImport_actionPerformed(ActionEvent e) {
    String fileName = null;
    fcOpen.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_IMPORTCIRC"));
    int retVal = fcOpen.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      fileName = fcOpen.getSelectedFile().getAbsolutePath();
      if (Config.getConfirm()) {
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_CONFIRMDLG_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_YES");
        String[] buttons = {defBtn, 
          Messages.get("MAINFRAME_CONFIRMDLG_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_CONFIRMDLG_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
          importCirkulacija(fileName);
        }
      } else {
        importCirkulacija(fileName);
      }
    }
  }

  void miCircExport_actionPerformed(ActionEvent e) {
    fcSave.setDialogTitle(
      Messages.get("MAINFRAME_FILEOPEN_FRAMETITLE_EXPORTCIRC"));
    int retVal = fcSave.showSaveDialog(null);
    if (retVal != JFileChooser.APPROVE_OPTION)
      return;
    String filename = fcSave.getSelectedFile().getAbsolutePath();
    File file = new File(filename);
    if (file.exists()) {
      if (file.canWrite()) {
        JLabel msg = new JLabel(
          Messages.get("MAINFRAME_CONFIRMDLG2_MESSAGE"));
        msg.setForeground(Color.black);
        String defBtn = Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_YES");
        String[] buttons = {defBtn, 
          Messages.get("MAINFRAME_CONFIRMDLG2_BUTTON_NO")};
        if (JOptionPane.showOptionDialog(
            null,
            msg,
            Messages.get("MAINFRAME_CONFIRMDLG2_FRAMETITLE"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            buttons,
            defBtn) == JOptionPane.YES_OPTION) {
          exportCirkulacija(filename);
        }
      } else {
        JOptionPane.showMessageDialog(null, 
          Messages.get("MAINFRAME_CANNOTWRITEDLG_MESSAGE"),
          Messages.get("MAINFRAME_CANNOTWRITEDLG_FRAMETITLE"),
          JOptionPane.ERROR_MESSAGE);
      }
    } else {
      exportCirkulacija(filename);
    }
  }
}
