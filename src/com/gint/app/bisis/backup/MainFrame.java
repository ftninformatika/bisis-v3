package com .gint.app.bisis.backup;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.gint.util.gui.SwingWorker;
import com.gint.app.bisis.textsrv.*;
import com.gint.util.string.StringUtils;

public class MainFrame extends JFrame {

  XYLayout xYLayout1;
  JLabel lDest;
  JLabel lDestDir;
  JButton bDest;
  JButton bBackup;
  JFileChooser fcOpen;
  ProgressDlg progressDlg;

  // Construct the frame
  public MainFrame() {
    if (!Config.isNoGUI()) {
	    xYLayout1 = new XYLayout();
	    lDest = new JLabel();
	    lDestDir = new JLabel();
	    bDest = new JButton();
	    bBackup = new JButton();
	    fcOpen = new JFileChooser();
	    progressDlg = new ProgressDlg(this);
	    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	    try  {
	      jbInit();
	      pack();
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
    }
  }

  // Component initialization
  private void jbInit() throws Exception  {
    setIconImage(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/backup/images/icon.gif")).getImage());
    lDest.setText(Messages.get("MAINFRAME_LABEL_DESTINATION"));
    this.getContentPane().setLayout(xYLayout1);
    this.setTitle(Messages.get("MAINFRAME_FRAMETITLE"));
    lDestDir.setText(Config.getDestination());
    bDest.setText(Messages.get("MAINFRAME_BUTTON_DESTINATION"));
    bBackup.setText(Messages.get("MAINFRAME_BUTTON_BACKUP"));
    bBackup.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/backup/images/CD.gif")));
    xYLayout1.setHeight(148);
    xYLayout1.setWidth(400);
    this.getContentPane().add(lDest, new XYConstraints(7, 10, 77, -1));
    this.getContentPane().add(lDestDir, new XYConstraints(90, 10, 295, -1));
    this.getContentPane().add(bBackup, new XYConstraints(139, 68, -1, -1));
    this.getContentPane().add(bDest, new XYConstraints(7, 30, -1, -1));
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.getRootPane().setDefaultButton(bBackup);
    bBackup.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        doBackup();
      }
    });
    bDest.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        pickDestination();
      }
    });
    fcOpen.setApproveButtonText(
      Messages.get("MAINFRAME_OPENDIALOG_BUTTON_APPROVE"));
    fcOpen.setMultiSelectionEnabled(false);
    fcOpen.setDialogTitle(Messages.get("MAINFRAME_OPENDIALOG_FRAMETITLE"));
    fcOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  }

  public void pickDestination() {
    fcOpen.setCurrentDirectory(new File(Config.getDestination()));
    int retVal = fcOpen.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      Config.setDestination(fcOpen.getSelectedFile().getAbsolutePath());
      lDestDir.setText(Config.getDestination());
    }
  }

  public static void doBackup() {
    // get the current date
    Calendar cal = Calendar.getInstance();
    cal.setTime(new java.util.Date());
    String dayOfMonth = StringUtils.padZeros(cal.get(Calendar.DAY_OF_MONTH), 2);
    String month = StringUtils.padZeros(cal.get(Calendar.MONTH) + 1, 2);
    String year = Integer.toString(cal.get(Calendar.YEAR));
    String hour = StringUtils.padZeros(cal.get(Calendar.HOUR), 2);
    String minute = StringUtils.padZeros(cal.get(Calendar.MINUTE), 2);

    String destDir = "BISIS-" + year + "-" + month + "-" + dayOfMonth;
    String s = System.getProperty("file.separator");
    File dest = new File(Config.getDestination() + s + destDir);
    if (dest.exists() && !Config.isNoGUI()) {
      JOptionPane.showMessageDialog(null,
        Messages.get("MAINFRAME_ERRORDIALOG_MESSAGE_TODAYS_BACKUP"),
        Messages.get("MAINFRAME_ERRORDIALOG_FRAMETITLE"),
        JOptionPane.ERROR_MESSAGE);
      return;
    }
    dest.mkdirs();

    // construct filenames
    final String records = dest.getAbsolutePath() + s + 
      "records-" + year + "-" + month + "-" + dayOfMonth + "-" + hour + "-" + minute + ".dat";
    final String libenv = dest.getAbsolutePath() + s + 
      "env-" + year + "-" + month + "-" + dayOfMonth + "-" + hour + "-" + minute + ".dat";
    final String unimarc = dest.getAbsolutePath() + s + 
      "unimarc-" + year + "-" + month + "-" + dayOfMonth + "-" + hour + "-" + minute + ".dat";
    final String circ = dest.getAbsolutePath() + s + 
      "circ-" + year + "-" + month + "-" + dayOfMonth + "-" + hour + "-" + minute + ".dat";

    if (Config.isNoGUI()) {
      export(records, libenv, unimarc, circ);
    } else {
      // start the backup worker thread
      SwingWorker worker = new SwingWorker() {
        public Object construct() {
          return Config.getMainFrame().new ExportTask(records, libenv, unimarc, circ);
        }
      };
      worker.start();
      Config.getMainFrame().progressDlg.timer.start();
      Config.getMainFrame().progressDlg.setVisible(true);
      JOptionPane.showMessageDialog(Config.getMainFrame(), 
        Messages.get("MAINFRAME_SUCCESSDIALOG_INFOMESSAGE"), 
        Messages.get("MAINFRAME_SUCCESSDIALOG_FRAMETITLE"), 
        JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** 
   * Export process class. Invoked in the background thread by 
   * <code>SwingWorker</code>.
   */
  public class ExportTask {
    public ExportTask(String records, String libenv, String unimarc, String circ) {
      MainFrame.export(records, libenv, unimarc, circ);
    }
  }
  
  public static void export(String records, String libenv, String unimarc, 
      String circ) {
    
    String lineSep = System.getProperty("line.separator");
    
    // connect to the database
    if (!Config.isNoGUI())
      Config.getMainFrame().progressDlg.setMessage(
          Messages.get("MAINFRAME_PROGRESSDIALOG_MESSAGE_CONNECTING"));
    Connection conn = null;
    try {
      Class.forName(Config.getDriver());
    } catch (ClassNotFoundException ex) {
      showMessage(Messages.get("MAINFRAME_ERRORDIALOG_MESSAGE_NODRIVER"));
      Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }
    try {
      conn = DriverManager.getConnection(Config.getUrl(), 
        Config.getDbUsername(), Config.getDbPassword());
      conn.setAutoCommit(false);
    } catch (SQLException ex) {
      showMessage(Messages.get(
          "MAINFRAME_ERRORDIALOG_MESSAGE_CONNECTION_FAILED"));
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }

    // backup records
    if (!Config.isNoGUI())
      Config.getMainFrame().progressDlg.setMessage(
          Messages.get("MAINFRAME_PROGRESSDIALOG_MESSAGE_RECORDS"));
    RandomAccessFile out = null;
    try {
      out = new RandomAccessFile(records, "rw");
    } catch (IOException ex) {
      showMessage(Messages.get(
          "MAINFRAME_ERRORDIALOG_MESSAGE_CANNOT_OPEN") + records);
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }
    
    String rezance = null;
    String line = null;
    try {
      TextServer ts = new TextServer(Config.getDBType(), conn, 
        Config.getUrl(), Config.getDbUsername(), Config.getDbPassword());
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT doc_id FROM documents");
      ResultSet rset = pstmt.executeQuery();
      int docID;
      String doc;
      while (rset.next()) {
        docID = rset.getInt(1);
        doc = ts.getDoc(docID);
        if (doc == null)
          continue;
        doc = ExportEscapes.escapeText(doc);
        if (!doc.equals(""))
          out.writeBytes(doc + lineSep);
      }
      rset.close();
      pstmt.close();
      out.close();
    } catch (IOException ex) {
      ex.printStackTrace();
      showMessage(Messages.get(
      		"MAINFRAME_ERRORDIALOG_MESSAGE_CANNOT_WRITE") + records);
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    } catch (Exception ex) {
      ex.printStackTrace();
      showMessage(Messages.get(
  				"MAINFRAME_ERRORDIALOG_MESSAGE_UNKNOWN"));
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }

    PrintWriter outw = null;

    // backup environment
    if (!Config.isNoGUI())
      Config.getMainFrame().progressDlg.setMessage(
          Messages.get("MAINFRAME_PROGRESSDIALOG_MESSAGE_ENVIRONMENT"));
    try {
      outw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(libenv), "UTF-8")));
      TableData tableData = new TableData();
      for (int i = 0; i < Config.okruzTableNames.length; i++) {
        TableData.saveData(conn, outw, Config.okruzTableNames[i], 
          Config.okruzTableCols[i]);
      }
      outw.close();
    } catch (IOException ex) {
      showMessage(Messages.get("MAINFRAME_ERRORDIALOG_MESSAGE_UNKNOWN") + 
          ex.getMessage());
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }

    // backup unimarc
    if (!Config.isNoGUI())
      Config.getMainFrame().progressDlg.setMessage(
          Messages.get("MAINFRAME_PROGRESSDIALOG_MESSAGE_UNIMARC"));
    try {
      outw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(unimarc), "UTF-8")));
      for (int i = 0; i < Config.unimarcTableNames.length; i++) {
        TableData.saveData(conn, outw, Config.unimarcTableNames[i], 
            Config.unimarcTableCols[i]);
      }
      outw.close();
    } catch (IOException ex) {
      showMessage(Messages.get("MAINFRAME_ERRORDIALOG_MESSAGE_UNKNOWN") + 
          ex.getMessage());
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }

    // backup circulation
    if (!Config.isNoGUI())
      Config.getMainFrame().progressDlg.setMessage(
          Messages.get("MAINFRAME_PROGRESSDIALOG_MESSAGE_CIRC"));
    try {
      outw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(circ), "UTF-8")));
      for (int i = 0; i < Config.circTableNames.length; i++) {
        TableData.saveData(conn, outw, Config.circTableNames[i], 
          Config.circTableCols[i]);
      }
      outw.close();
    } catch (IOException ex) {
      showMessage(Messages.get("MAINFRAME_ERRORDIALOG_MESSAGE_UNKNOWN") + 
          ex.getMessage());
      if (!Config.isNoGUI())
        Config.getMainFrame().progressDlg.setFinished(true);
      return;
    }

    // close the database connection
    try { conn.close(); } catch (SQLException ex) { }
    if (!Config.isNoGUI())
      Config.getMainFrame().progressDlg.setFinished(true);
  }

  public static void showMessage(String msg) {
    if (Config.isNoGUI())
      System.err.println(msg);
    else
      JOptionPane.showMessageDialog(Config.getMainFrame(), msg, 
          Messages.get("MAINFRAME_ERRORDIALOG_FRAMETITLE"),
          JOptionPane.ERROR_MESSAGE);
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
}
