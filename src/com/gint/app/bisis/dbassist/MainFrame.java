package com.gint.app.bisis.dbassist;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.gint.util.gui.SwingWorker;
import com.gint.util.string.Charset;
import com.gint.util.string.StringUtils;

/** Osnovni frejm aplikacije. */
public class MainFrame extends JFrame {
  XYLayout xYLayout1 = new XYLayout();
  JLabel lText1 = new JLabel();
  JRadioButton rbCreate = new JRadioButton();
  JRadioButton rbRemove = new JRadioButton();
  JLabel lImage = new JLabel();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  JButton bServer = new JButton();
  ConfigDlg configDlg = new ConfigDlg(this, 
    Messages.get("MAINFRAME_CONFIGDLG_FRAMETITLE"), true);
  ProgressDlg progressDlg = new ProgressDlg(this, 
    Messages.get("MAINFRAME_PROGRESSDLG_FRAMETITLE"), true);
  DropProgressDlg dropProgressDlg = new DropProgressDlg(this, 
    Messages.get("MAINFRAME_DROPPROGRESSDLG_FRAMETITLE"), true);

  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception  {
    setIconImage(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/dbassist/images/icon.gif")).getImage());
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    lText1.setText(Messages.get("MAINFRAME_LABEL_CHOOSEOPERATION"));
    this.getContentPane().setLayout(xYLayout1);
    this.setTitle(Messages.get("MAINFRAME_FRAMETITLE"));
    rbCreate.setSelected(true);
    rbCreate.setText(Messages.get("MAINFRAME_RADIO_CREATE"));
    rbRemove.setText(Messages.get("MAINFRAME_RADIO_REMOVE"));
    ButtonGroup b = new ButtonGroup();
    bCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bServer.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bServer_actionPerformed(e);
      }
    });
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    b.add(rbCreate);
    b.add(rbRemove);
    xYLayout1.setHeight(316);
    xYLayout1.setWidth(423);
    lImage.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/dbassist/images/setup.gif")));
    bOK.setText(Messages.get("MAINFRAME_BUTTON_OK"));
    bOK.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/dbassist/images/check.gif")));
    bCancel.setText(Messages.get("MAINFRAME_BUTTON_CANCEL"));
    bCancel.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/dbassist/images/delete.gif")));
    bServer.setText(Messages.get("MAINFRAME_BUTTON_SERVER"));
    this.getContentPane().add(lImage, new XYConstraints(12, 14, -1, -1));
    this.getContentPane().add(lText1, new XYConstraints(197, 20, -1, -1));
    this.getContentPane().add(rbCreate, new XYConstraints(197, 53, -1, -1));
    this.getContentPane().add(rbRemove, new XYConstraints(197, 80, -1, -1));
    this.getContentPane().add(bOK, new XYConstraints(153, 240, 112, -1));
    this.getContentPane().add(bCancel, new XYConstraints(285, 240, 113, -1));
    this.getContentPane().add(bServer, new XYConstraints(317, 118, -1, -1));
    getRootPane().setDefaultButton(bOK);
    setSize(423, 316);
  }

  /** Klasa koja opisuje proces kreiranja seme baze podataka. Koristi se
      u saradnji sa SwingWorker klasom da bi se ovaj posao izvrsavao u
      pozadini, dok je osnovni thread i dalje zaduzen za korisnicki interfejs.
      Osvezavanje sadrzaja komponenti korisnickog interfejsa se radi pomocu
      tajmera.
    */
  public class CreateDBTask {
    public CreateDBTask(Connection conn) {
      URL url;
      BufferedReader in;
      String line = "";
      Statement stmt;
      int maxStatements;
      int statementCount;

      // kreiranje podseme tekst servera
      try {
        url = this.getClass().getResource(
          "/com/gint/app/bisis/dbassist/scripts/" + Config.getDBType() + 
          "/textsrv.sql");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while (in.readLine() != null)
          statementCount++;
        in.close();
        progressDlg.pbProgress.setMinimum(0);
        progressDlg.pbProgress.setMaximum(statementCount);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while ((line = in.readLine()) != null) {
          progressDlg.setValue(++statementCount);
          line = line.trim();  // <-- ne sme imati konverziju YUSCII->Unicode
          if (Config.getDBType().equals("oracle")) {
            line = StringUtils.replace(line, "%TABLES%", 
              Config.getUserTablespace());
            line = StringUtils.replace(line, "%INDEXES%", 
              Config.getIndexTablespace());
          }
          stmt = conn.createStatement();
          stmt.executeUpdate(line);
          stmt.close();
        }
        in.close();
      } catch (Exception ex) {
        System.out.println(line);
        ex.printStackTrace();
        String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, 
          msg+"\n"+line, 
          Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
          JOptionPane.ERROR_MESSAGE);
      }
      progressDlg.setPhase(2);

      // kreiranje podseme za unimarc standard
      try {
        url = this.getClass().getResource(
          "/com/gint/app/bisis/dbassist/scripts/" + Config.getDBType() + 
          "/unimarc.sql");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while (in.readLine() != null)
          statementCount++;
        in.close();
        progressDlg.pbProgress.setMinimum(0);
        progressDlg.pbProgress.setMaximum(statementCount);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while ((line = in.readLine()) != null) {
          progressDlg.setValue(++statementCount);
          line = Charset.YUSCII2Unicode(line.trim());
          if (Config.getDBType().equals("oracle")) {
            line = StringUtils.replace(line, "%TABLES%", 
              Config.getUserTablespace());
            line = StringUtils.replace(line, "%INDEXES%", 
              Config.getIndexTablespace());
          }
          stmt = conn.createStatement();
          stmt.executeUpdate(line);
          stmt.close();
        }
        in.close();
      } catch (Exception ex) {
        System.out.println(line);
        ex.printStackTrace();
        String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, 
          msg+"\n"+line, 
          Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
          JOptionPane.ERROR_MESSAGE);
      }
      progressDlg.setPhase(3);

      // kreiranje podseme za okruzenje bibliotekara
      try {
        url = this.getClass().getResource(
          "/com/gint/app/bisis/dbassist/scripts/" + Config.getDBType() + 
          "/okruzenje.sql");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while (in.readLine() != null)
          statementCount++;
        in.close();
        progressDlg.pbProgress.setMinimum(0);
        progressDlg.pbProgress.setMaximum(statementCount);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while ((line = in.readLine()) != null) {
          progressDlg.setValue(++statementCount);
          line = Charset.YUSCII2Unicode(line.trim());
          if (Config.getDBType().equals("oracle")) {
            line = StringUtils.replace(line, "%TABLES%", 
              Config.getUserTablespace());
            line = StringUtils.replace(line, "%INDEXES%", 
              Config.getIndexTablespace());
          }
          stmt = conn.createStatement();
          stmt.executeUpdate(line);
          stmt.close();
        }
        in.close();
      } catch (Exception ex) {
        System.out.println(line);
        ex.printStackTrace();
        String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, 
          msg+"\n"+line, 
          Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
          JOptionPane.ERROR_MESSAGE);
      }
      progressDlg.setPhase(4);

      // kreiranje podseme za cirkulaciju
      try {
        url = this.getClass().getResource(
          "/com/gint/app/bisis/dbassist/scripts/" + Config.getDBType() + 
          "/cirkulacija.sql");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while (in.readLine() != null)
          statementCount++;
        in.close();
        progressDlg.pbProgress.setMinimum(0);
        progressDlg.pbProgress.setMaximum(statementCount);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while ((line = in.readLine()) != null) {
          progressDlg.setValue(++statementCount);
          line = Charset.YUSCII2Unicode(line.trim());
          if (Config.getDBType().equals("oracle")) {
            line = StringUtils.replace(line, "%TABLES%", 
              Config.getUserTablespace());
            line = StringUtils.replace(line, "%INDEXES%", 
              Config.getIndexTablespace());
          }
          stmt = conn.createStatement();
          stmt.executeUpdate(line);
          stmt.close();
        }
        in.close();
      } catch (Exception ex) {
        System.out.println(line);
        ex.printStackTrace();
        String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, 
          msg+"\n"+line, 
          Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
          JOptionPane.ERROR_MESSAGE);
      }
      progressDlg.setPhase(5);
    }
  }

  /** Klasa koja opisuje proces uklanjanja seme baze podataka. Koristi se
      u saradnji sa SwingWorker klasom da bi se ovaj posao izvrsavao u
      pozadini, dok je osnovni thread i dalje zaduzen za korisnicki interfejs.
      Osvezavanje sadrzaja komponenti korisnickog interfejsa se radi pomocu
      tajmera.
    */
  public class DropDBTask {
    public DropDBTask(Connection conn) {
      URL url;
      BufferedReader in;
      String line = "";
      Statement stmt;
      int maxStatements;
      int statementCount;

      // obrisi sve tabele i sekvence iz baze
      try {
        url = this.getClass().getResource(
          "/com/gint/app/bisis/dbassist/scripts/" + Config.getDBType() + 
          "/dropall.sql");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while (in.readLine() != null)
          statementCount++;
        in.close();
        dropProgressDlg.pbProgress.setMinimum(0);
        dropProgressDlg.pbProgress.setMaximum(statementCount);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        statementCount = 0;
        while ((line = in.readLine()) != null) {
          dropProgressDlg.setValue(++statementCount);
          line = line.trim();
          stmt = conn.createStatement();
          stmt.executeUpdate(line);
          stmt.close();
        }
        in.close();
      } catch (Exception ex) {
        System.out.println(line);
        ex.printStackTrace();
        String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, 
          msg+"\n"+line, 
          Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
          JOptionPane.ERROR_MESSAGE);
      }
      dropProgressDlg.setPhase(2);
    }
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void bCancel_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void bServer_actionPerformed(ActionEvent e) {
    configDlg.initData();
    configDlg.setVisible(true);
  }

  void bOK_actionPerformed(ActionEvent e) {
    try {
      Class.forName(Config.getDriver());
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_NODRIVER"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      return;
    }
    try {
      conn = DriverManager.getConnection(Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(true);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, 
        Messages.get("MAINFRAME_ERRORDLG_CONNECTIONFAILED"), 
        Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
        JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
      return;
    }

    if (rbCreate.isSelected()) {
      // ako je izabrano kreiranje baze
      // Proveri da tamo vec ne postoje tabele
      try {
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
          "SELECT table_name FROM user_tables WHERE table_name = 'DOCUMENTS'");
        if (rset.next()) {
          JOptionPane.showMessageDialog(null, 
            Messages.get("MAINFRAME_ERRORDLG_DATABASEEXISTS"), 
            Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
            JOptionPane.ERROR_MESSAGE);
          return;
        }
        rset.close();
        stmt.close();

        if (Config.getDBType().equals("oracle")) {
          /*
          ----- IZBOR TABLESPACE-a: ORACLE-SPECIFIC
          */
          // procitaj sve tablespace-ove pa da korisnik izabere user i index tablespace
          stmt = conn.createStatement();
          rset = stmt.executeQuery(
            "SELECT tablespace_name FROM user_tablespaces");
          Vector tablespaceNames = new Vector();
          while (rset.next())
            tablespaceNames.addElement(rset.getString(1).trim());
          rset.close();
          stmt.close();
          TablespaceDlg td = new TablespaceDlg(null, 
            Messages.get("MAINFRAME_TABLESPACEDLG_FRAMETITLE"), true);
          td.lUserTablespace.setListData(tablespaceNames);
          td.lUserTablespace.setSelectedIndex(0);
          td.lIndexTablespace.setListData(tablespaceNames);
          td.lIndexTablespace.setSelectedIndex(0);
          td.setVisible(true);
          Config.setUserTablespace((String)tablespaceNames.elementAt(td.lUserTablespace.getSelectedIndex()));
          Config.setIndexTablespace((String)tablespaceNames.elementAt(td.lIndexTablespace.getSelectedIndex()));
        }
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, 
            Messages.get("MAINFRAME_ERRORDLG_UNKNOWN"), 
            Messages.get("MAINFRAME_ERRORDLG_FRAMETITLE"), 
            JOptionPane.ERROR_MESSAGE);
          return;
      }

      SwingWorker worker = new SwingWorker() {
        public Object construct() {
          return new CreateDBTask(conn);
        }
      };
      worker.start();
      progressDlg.timer.start();
      progressDlg.setVisible(true);
    } else {
      JLabel msg = new JLabel(
        Messages.get("MAINFRAME_AREYOUSURE_LABEL_AREYOUSURE"));
      msg.setForeground(Color.black);
      String defBtn = Messages.get("MAINFRAME_AREYOUSURE_BUTTON_YES");
      String[] buttons = {defBtn, 
        Messages.get("MAINFRAME_AREYOUSURE_BUTTON_NO")};
      if (JOptionPane.showOptionDialog(
          null,
          msg,
          Messages.get("MAINFRAME_AREYOUSURE_FRAMETITLE"),
          JOptionPane.YES_NO_OPTION,
          JOptionPane.INFORMATION_MESSAGE,
          null,
          buttons,
          defBtn) == JOptionPane.YES_OPTION) {
        // ako je izabrano brisanje baze
        SwingWorker worker = new SwingWorker() {
          public Object construct() {
            return new DropDBTask(conn);
          }
        };
        worker.start();
        dropProgressDlg.timer.start();
        dropProgressDlg.setVisible(true);
      }
    }
  }

  Connection conn = null;
}
