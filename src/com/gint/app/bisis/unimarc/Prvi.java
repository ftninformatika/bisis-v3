package com.gint.app.bisis.unimarc;

import java.awt.*;
import javax.swing.*;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import java.awt.event.*;
import java.sql.*;

import com.borland.jbcl.layout.*;

import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;
import com.gint.util.gui.SplashScreen;

import com.gint.app.bisis.unimarc.unim.*;

/**Klasa koja pretstavlja UNIMARC format.
*/
public class Prvi extends JDialog {
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JPanel jPanel10 = new JPanel();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JButton jButton10 = new JButton();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JButton jButton8 = new JButton();
  JButton jButton9 = new JButton();

  /** Uspostavlja konekciju sa bazom podataka */
  Connection conn;
  XYLayout xYLayout1 = new XYLayout();

  /** Konstruktor */
  public Prvi() {  //uspostavljamo konekciju sa bazom
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
     try {
       String driver = iniFile.getString("database", "driver");
       String url=iniFile.getString("database", "url");
       String username = iniFile.getString("database", "username");
       String password = iniFile.getString("database", "password");
       Class.forName(driver);
       conn = DriverManager.getConnection(url, username, password);
       conn.setAutoCommit(false);
     }
     catch(Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, Messages.get("PRVI_ERRDLG_DBCONNFAILED_TEXT"), Messages.get("PRVI_ERRDLG_DBCONNFAILED_TITLE"), JOptionPane.ERROR_MESSAGE);
      System.exit(0);
     }
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /** Inicijalna metoda za podesavanje osobina svih komponenti na ekranskoj formi */
  private void jbInit() throws Exception {
    jPanel1.setLayout(xYLayout1);

    this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    jLabel1.setBackground(Color.blue);
    jLabel1.setFont(new java.awt.Font("Serif", 3, 20));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText(Messages.get("PRVI_LABEL_TITLE"));
    jLabel1.setVerticalTextPosition(SwingConstants.TOP);
    jLabel2.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel2.setHorizontalAlignment(SwingConstants.LEADING);
    jLabel2.setText(Messages.get("PRVI_LABEL_SUBFIELD"));
    jLabel3.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel3.setText(Messages.get("PRVI_LABEL_FIELD"));
    jLabel4.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel4.setText(Messages.get("PRVI_LABEL_SUBSUBFIELD"));
    jLabel5.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel5.setText(Messages.get("PRVI_LABEL_FIRSTINDICATOR"));
    jLabel6.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel6.setText(Messages.get("PRVI_LABEL_SECONDINDICATOR"));
    jLabel7.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel7.setText(Messages.get("PRVI_LABEL_EXTERNTYPECODER"));
    jLabel8.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel8.setText(Messages.get("PRVI_LABEL_EXTERNCODER"));
    jLabel9.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel9.setText(Messages.get("PRVI_LABEL_INTERNCODER"));

    jLabel10.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel10.setText(Messages.get("PRVI_LABEL_LOCALCODER"));
    jLabel11.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel11.setText(Messages.get("PRVI_LABEL_CONTROLTYPE"));
    this.setTitle(Messages.get("PRVI_MAINFRAME_TITLE"));
    jButton10.setMinimumSize(new Dimension(40, 20));
    jButton10.setPreferredSize(new Dimension(40, 20));
    jButton10.setText("...");
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton1.setMinimumSize(new Dimension(40, 20));
    jButton1.setPreferredSize(new Dimension(40, 20));
    jButton1.setText("...");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setMinimumSize(new Dimension(40, 20));
    jButton2.setPreferredSize(new Dimension(40, 20));
    jButton2.setText("...");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton3.setMinimumSize(new Dimension(40, 20));
    jButton3.setPreferredSize(new Dimension(40, 20));
    jButton3.setText("...");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jButton4.setMinimumSize(new Dimension(40, 20));
    jButton4.setPreferredSize(new Dimension(40, 20));
    jButton4.setText("...");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setMinimumSize(new Dimension(40, 20));
    jButton5.setPreferredSize(new Dimension(40, 20));
    jButton5.setText("...");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setMinimumSize(new Dimension(40, 20));
    jButton6.setPreferredSize(new Dimension(40, 20));
    jButton6.setText("...");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setMinimumSize(new Dimension(40, 20));
    jButton7.setPreferredSize(new Dimension(40, 20));
    jButton7.setText("...");
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setMinimumSize(new Dimension(40, 20));
    jButton8.setPreferredSize(new Dimension(40, 20));
    jButton8.setText("...");
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jButton9.setMinimumSize(new Dimension(40, 20));
    jButton9.setPreferredSize(new Dimension(40, 20));
    jButton9.setText("...");
    jButton9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton9_actionPerformed(e);
      }
    });

    xYLayout1.setHeight(360);
    xYLayout1.setWidth(300);
    
    this.getContentPane().add(jPanel1);
    
    jPanel1.add(jLabel1, new XYConstraints(60, 10, 0, 0));

    jPanel1.add(jLabel3, new XYConstraints(15, 80, 0, 0));
    jPanel1.add(jButton2, new XYConstraints(250, 80, 0, 0));
    jPanel1.add(jLabel2, new XYConstraints(15, 110, 0, 0));
    jPanel1.add(jButton1, new XYConstraints(250, 110, 0, 0));
    jPanel1.add(jLabel4, new XYConstraints(15, 140, 0, 0));
    jPanel1.add(jButton3, new XYConstraints(250, 140, 0, 0));        
    jPanel1.add(jLabel5, new XYConstraints(15, 170, 0, 0));
    jPanel1.add(jButton4, new XYConstraints(250, 170, 0, 0));
    jPanel1.add(jLabel6, new XYConstraints(15, 200, 0, 0));
    jPanel1.add(jButton5, new XYConstraints(250, 200, 0, 0));
    jPanel1.add(jLabel7, new XYConstraints(15, 230, 0, 0));
    jPanel1.add(jButton6, new XYConstraints(250, 230, 0, 0));        
    jPanel1.add(jLabel8, new XYConstraints(15, 260, 0, 0));
    jPanel1.add(jButton7, new XYConstraints(250, 260, 0, 0));
    jPanel1.add(jLabel9, new XYConstraints(15, 290, -1, -1));
    jPanel1.add(jButton8, new XYConstraints(250, 290, -1, -1));
    jPanel1.add(jLabel10, new XYConstraints(15, 320, 0, 0));    
    jPanel1.add(jButton9, new XYConstraints(250, 320, 0, 0));
    //jPanel1.add(jLabel11, new XYConstraints(15, 350, 0, 0));
    //jPanel1.add(jButton10, new XYConstraints(250, 350, 0, 0));    
                
}
  /** Poziva se kada se pritisne sistemsko dugme za zatvaranje ekranske forme */
  void this_windowClosing(WindowEvent e) {
    try {
      conn.close();
       }
    catch(SQLException e1) {
      e1.printStackTrace();
    }
    System.exit(0);
  }

  /** Glavni metod koji se izvrsava po poktetanju programa, a prikazuje na ekransku formu 'Unimarc' */
  public static  void main(String[] arg){
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }
    SplashScreen splash = new SplashScreen("/com/gint/app/bisis/unimarc/images/unimarc_logo.gif");
    Prvi  prvi=new Prvi();
    prvi.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = prvi.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    prvi.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    splash.dispose();
    splash = null;
    prvi.setVisible(true);

  }

  /** Poziva se kada se spritisne dugme 'Polje' na ekranskoj formi 'Unimarc' */
  void jButton2_actionPerformed(ActionEvent e) {  //dugme -> polje
    String query="select * from tip_kontrole";
    if(exist(query)) {
      Polje p=new Polje(conn);
      p.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = p.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
    	frameSize.width = screenSize.width;
      p.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      p.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_FIELD_MISSINGCONTROLTYPE_TEXT"),Messages.get("PRVI_INFDLG_FIELD_MISSINGCONTROLTYPE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }

  }
  /** Poziva se kada se spritisne dugme 'Potpolje' na ekranskoj formi 'Unimarc' */
  void jButton1_actionPerformed(ActionEvent e) {  //dugme -> polje
    String query="select * from polje";
    if(exist(query)) {
      String query1="select * from tip_kontrole";
      if(exist(query1)) {
        Potpolja pp=new Potpolja(conn);
    	pp.pack();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension frameSize = pp.getSize();
    	if (frameSize.height > screenSize.height)
      	  frameSize.height = screenSize.height;
    	if (frameSize.width > screenSize.width)
      	  frameSize.width = screenSize.width;
    	pp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    	pp.setVisible(true);
      }
      else {
      	JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_SUBFIELD_MISSINGCONTROLTYPE_TEXT"),Messages.get("PRVI_INFDLG_SUBFIELD_MISSINGCONTROLTYPE_TITLE"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_SUBFIELD_MISSINGFIELD_TEXT"),Messages.get("PRVI_INFDLG_SUBFIELD_MISSINGFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Potpotpolja' na ekranskoj formi 'Unimarc' */
  void jButton3_actionPerformed(ActionEvent e) { // dugme -> prvi indikator
    String query="select * from potpolja";
    if(exist(query)) {
      Potpotpolja ppp = new Potpotpolja(conn);
      ppp.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = ppp.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      ppp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      ppp.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_SUBSUBFIELD_MISSINGSUBFIELD_TEXT"),Messages.get("PRVI_INFDLG_SUBSUBFIELD_MISSINGSUBFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Prvi indikator' na ekranskoj formi 'Unimarc' */
  void jButton4_actionPerformed(ActionEvent e) { // dugme -> prvi indikator
    String query="select * from polje";
    if(exist(query)) {
      Pindikator pi = new Pindikator(conn);
      pi.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = pi.getSize();
      if (frameSize.height > screenSize.height)
      	frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      pi.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      pi.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_FIRSTINDICATOR_MISSINGFIELD_TEXT"),Messages.get("PRVI_INFDLG_FIRSTINDICATOR_MISSINGFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Drugi indikator' na ekranskoj formi 'Unimarc' */
  void jButton5_actionPerformed(ActionEvent e) {
    String query="select * from polje";
    if(exist(query)) {
      Dindikator dind = new Dindikator(conn);
      dind.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = dind.getSize();
      if (frameSize.height > screenSize.height)
      	frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      dind.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      dind.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_SECONDINDICATOR_MISSINGFIELD_TEXT"),Messages.get("PRVI_INFDLG_SECONDINDICATOR_MISSINGFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Tip eksternog sifarnika' na ekranskoj formi 'Unimarc' */
  void jButton6_actionPerformed(ActionEvent e) {
    Tesifarnika tesf = new Tesifarnika(conn);
    tesf.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = tesf.getSize();
    if (frameSize.height > screenSize.height)
     	frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
     	frameSize.width = screenSize.width;
    tesf.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    tesf.setVisible(true);
  }
  /** Poziva se kada se spritisne dugme 'Eksterni sifarnik' na ekranskoj formi 'Unimarc' */
  void jButton7_actionPerformed(ActionEvent e) {
    String query="select distinct TESID from tipekst_sifarnika";
    if(exist(query)) {
      Esifarnik esf = new Esifarnik(conn);
      esf.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = esf.getSize();
      if (frameSize.height > screenSize.height)
      	frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      esf.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      esf.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_EXTERNTYPECODER_MISSINGEXTERNTYPECODER_TEXT"),Messages.get("PRVI_INFDLG_EXTERNTYPECODER_MISSINGEXTERNTYPECODER_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Interni sifarnik' na ekranskoj formi 'Unimarc' */
  void jButton8_actionPerformed(ActionEvent e) {
    String query="select * from potpolja";
    if(exist(query)) {
      Isifarnik isf = new Isifarnik(conn);
      isf.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = isf.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      isf.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      isf.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_INTERNCODER_MISSINGSUBFIELD_TEXT"),Messages.get("PRVI_INFDLG_INTERNCODER_MISSINGSUBFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Lokalni sifarnik' na ekranskoj formi 'Unimarc' */
  void jButton9_actionPerformed(ActionEvent e) {
    String query="select * from potpotpolja";
    if(exist(query)) {
      Lsifarnik lsf = new Lsifarnik(conn);
      lsf.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = lsf.getSize();
      if (frameSize.height > screenSize.height)
      	frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      lsf.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      lsf.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_INFDLG_LOCALECODER_MISSINGSUBFIELD_TEXT"),Messages.get("PRVI_INFDLG_LOCALECODER_MISSINGSUBFIELD_TITLE"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Tip kontrole' na ekranskoj formi 'Unimarc' */
  void jButton10_actionPerformed(ActionEvent e) {
    Tipkontrole tk = new Tipkontrole(conn);
    tk.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = tk.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    tk.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    tk.setVisible(true);
  }

  boolean exist(String query) {
    boolean e=false;
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if(rs.next())
    	e=true;
      rs.close();
      stmt.close();
      return e;
    }
    catch(SQLException e1) {
      e1.printStackTrace();
      return e;
    }
  }
}