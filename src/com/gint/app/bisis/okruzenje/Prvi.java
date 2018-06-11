package com.gint.app.bisis.okruzenje;

import java.sql.*;
import java.awt.*;
import javax.swing.*;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.borland.jbcl.layout.*;
import java.awt.event.*;

import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;
import com.gint.util.gui.SplashScreen;

import com.gint.app.bisis.okruzenje.okruz.*;
import com.gint.app.bisis.editor.edit.validation.Validator;
import com.gint.app.bisis.editor.UFieldSet.*;
import com.gint.app.bisis.editor.Environment;

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
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JButton jButton11 = new JButton();
  JButton jButton12 = new JButton();
  JButton jButton13 = new JButton();
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

  private Validator myValidator;
  private UFieldSet ufs;

  /** Konstruktor */
  public Prvi() {  //uspostavljamo konekciju sa bazom
    INIFile iniFile = new INIFile(
      FileUtils.getClassDir(getClass()) + "/config.ini"); 
    try {
       String driver = iniFile.getString("database", "driver");
       String url = iniFile.getString("database", "url");
       String username = iniFile.getString("database", "username");
       String password = iniFile.getString("database", "password");
       Class.forName(driver);
       conn = DriverManager.getConnection(url, username, password);
       conn.setAutoCommit(false);
     }
     catch(Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, Messages.get("PRVI_OPTION_PANE_DB_CONNECT"), Messages.get("PRVI_OPTION_PANE_DB_CONNECT_ERROR"), JOptionPane.ERROR_MESSAGE);
      System.exit(0);
     }
     try {
       String validatorClass = iniFile.getString("validation", "validatorClass");
       Class.forName(validatorClass);
       myValidator = com.gint.app.bisis.editor.edit.validation.ValidatorManager.getValidator();
     } catch (ClassNotFoundException ex) {
       ex.printStackTrace();
     }
     try {
       ufs = new UFieldSet(conn);
       Environment.init(null,ufs,null,null,"");
     }
     catch(SQLException ex) {
       ex.printStackTrace();
     }
     try  {
       jbInit();
       //pack();
     }
     catch(Exception ex) {
       ex.printStackTrace();
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
    jLabel1.setText(Messages.get("PRVI_LABEL_WORKING_ENVIROMENT"));
    jLabel1.setVerticalTextPosition(SwingConstants.TOP);
    jLabel2.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel2.setHorizontalAlignment(SwingConstants.LEADING);
    jLabel2.setText(Messages.get("PRVI_LABEL_PUBLICATION_TYPE"));
    jLabel3.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel3.setText(Messages.get("PRVI_LABEL_PUBLICATION_TYPE_SUBFIELD"));
    jLabel4.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel4.setText(Messages.get("PRVI_LABEL_PROCESS_LEVEL"));
    jLabel5.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel5.setText(Messages.get("PRVI_LABEL_PROCESS_LEVEL_SUBFIELD"));
    jLabel6.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel6.setText(Messages.get("PRVI_LABEL_MANDATORY_LEVEL"));
    jLabel7.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel7.setText(Messages.get("PRVI_LABEL_MANDATORY_LEVEL_SUBFIELD"));
    jLabel8.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel8.setText(Messages.get("PRVI_LABEL_LIBRARIAN"));
    jLabel9.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel9.setText(Messages.get("PRVI_LABEL_LIBRARIAN_SUBFIELD"));
    jLabel10.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel10.setText(Messages.get("PRVI_LABEL_FORMAT"));
    jLabel11.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel11.setText(Messages.get("PRVI_LABEL_SEARCH_PREFIX"));
    jLabel12.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel12.setText(Messages.get("PRVI_LABEL_FORMAT_PREFIX"));
    jLabel13.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel13.setText(Messages.get("PRVI_LABEL_SEARCH_PREFIX_SUBFIELD"));
    jLabel14.setFont(new java.awt.Font("Serif", 2, 16));
    jLabel14.setText(Messages.get("PRVI_LABEL_PROCESS_TYPE"));
    this.setTitle(Messages.get("PRVI_TITLE_WORKING_ENVIROMENT"));
    jButton10.setMinimumSize(new Dimension(40, 20));
    jButton10.setPreferredSize(new Dimension(40, 20));
    jButton10.setText("...");
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jButton11.setMinimumSize(new Dimension(40, 20));
    jButton11.setPreferredSize(new Dimension(40, 20));
    jButton11.setText("...");
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton11_actionPerformed(e);
      }
    });
    jButton12.setMinimumSize(new Dimension(40, 20));
    jButton12.setPreferredSize(new Dimension(40, 20));
    jButton12.setText("...");
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton12_actionPerformed(e);
      }
    });
    jButton13.setMinimumSize(new Dimension(40, 20));
    jButton13.setPreferredSize(new Dimension(40, 20));
    jButton13.setText("...");
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton13_actionPerformed(e);
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
    //jPanel1.add(jLabel9, new XYConstraints(15, 260, -1, -1));
    //jPanel1.add(jButton8, new XYConstraints(250, 260, -1, -1));
    jPanel1.add(jButton9, new XYConstraints(250, 260, 0, 0));
    jPanel1.add(jLabel10, new XYConstraints(15, 260, 0, 0));
    jPanel1.add(jLabel1, new XYConstraints(30, 10, 0, 0));
    //jPanel1.add(jLabel11, new XYConstraints(15, 320, 0, 0));
    jPanel1.add(jLabel12, new XYConstraints(15, 290, 0, 0));
    //jPanel1.add(jLabel13, new XYConstraints(15, 380, 0, 0));
    jPanel1.add(jLabel14, new XYConstraints(15, 320, 0, 0));
    jPanel1.add(jButton11, new XYConstraints(250, 290, 0, 0));
    //jPanel1.add(jButton12, new XYConstraints(250, 380, 0, 0));
    jPanel1.add(jButton13, new XYConstraints(250, 320, 0, 0));
    //jPanel1.add(jButton10, new XYConstraints(250, 320, 0, 0));
    jPanel1.add(jLabel2, new XYConstraints(15, 50, 0, 0));
    jPanel1.add(jButton1, new XYConstraints(250, 50, 0, 0));
    jPanel1.add(jButton3, new XYConstraints(250, 110, 0, 0));
    jPanel1.add(jLabel3, new XYConstraints(15, 80, 0, 0));
    jPanel1.add(jButton2, new XYConstraints(250, 80, 0, 0));
    jPanel1.add(jButton4, new XYConstraints(250, 140, 0, 0));
    jPanel1.add(jLabel7, new XYConstraints(15, 200, 0, 0));
    jPanel1.add(jLabel5, new XYConstraints(15, 140, 0, 0));
    jPanel1.add(jButton6, new XYConstraints(250, 200, 0, 0));
    jPanel1.add(jLabel8, new XYConstraints(15, 230, 0, 0));
    jPanel1.add(jButton7, new XYConstraints(250, 230, 0, 0));
    jPanel1.add(jButton5, new XYConstraints(250, 170, 0, 0));
    jPanel1.add(jLabel6, new XYConstraints(15, 170, 0, 0));
    jPanel1.add(jLabel4, new XYConstraints(15, 110, 0, 0));
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

  /** Glavni metod koji se izvrsava po poktetanju programa, a prikazuje na ekransku formu 'Okruzenje bibliotekara' */
  public static  void main(String[] arg){
    String osname = System.getProperty("os.name");
    if (osname != null && osname.equals("Linux")) {
      try {
        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Unsupported look and feel: InfoNode");
      }
    }
    SplashScreen splash = new SplashScreen("/com/gint/app/bisis/okruzenje/images/okruz_logo.gif");
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

  /** Poziva se kada se spritisne dugme 'Tip publikacije' */
  void jButton1_actionPerformed(ActionEvent e) {
    TypePublDlg tp = new TypePublDlg(null, Messages.get("PRVI_TYPEPUBLDLG_TITLE"), true, conn);
    tp.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = tp.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    tp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    tp.setVisible(true);
  }

  /** Poziva se kada se spritisne dugme 'Potpolja tipa publikacije'*/
  void jButton2_actionPerformed(ActionEvent e) {
    String query="select * from tip_publikacije";
    if(exist(query)) {
      query="select * from potpolja";
      if(exist(query)) {
//     	SFTypePublDlg sftpd = new SFTypePublDlg(null, "Potpolja tipa publikacije", true, conn, myValidator, ufs);
        SFTypePublDlg sftpd = new SFTypePublDlg(null, Messages.get("PRVI_SFTYPEPUBLDLG_TITLE"), true, conn, myValidator);
        sftpd.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = sftpd.getSize();
        if (frameSize.height > screenSize.height)
          frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
          frameSize.width = screenSize.width;
        sftpd.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        sftpd.setVisible(true);
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD"),Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PUBLICATION_TYPE"),Messages.get("PRVI_OPTION_PANE_PUBLICATION_TYPE_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  /** Poziva se kada se spritisne dugme 'Nivo obrade' */
  void jButton3_actionPerformed(ActionEvent e) { // dugme -> prvi indikator
    String query="select * from tip_publikacije";
    if (exist(query)) {
      ProcLevelDlg pl = new ProcLevelDlg(null, Messages.get("PRVI_PROCLEVELDLG_TITLE"), true, conn);
      pl.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = pl.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
      pl.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      pl.setVisible(true);
    } else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PUBLICATION_TYPE_1"),Messages.get("PRVI_OPTION_PANE_PUBLICATION_TYPE_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Potpolja nivoa obrade' */
  void jButton4_actionPerformed(ActionEvent e) { // dugme -> prvi indikator
    //String query="select * from nivo_obrade";
    String query="select distinct dt.TP_TIPID from DEF_TIPA dt"+
                  " where dt.TP_TIPID in (select TP_TIPID from NIVO_OBRADE)";
    if(exist(query)) {
      /*query="select * from def_tipa";
      if(exist(query)) {*/
        query="select * from potpolja";
        if(exist(query)) {
          SFProcLevDlg sfpld = new SFProcLevDlg(null, Messages.get("PRVI_SFPROCLEVDLG_TITLE"), true, conn);
          sfpld.pack();
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          Dimension frameSize = sfpld.getSize();
          if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
          if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
          sfpld.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
          sfpld.setVisible(true);
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_1"),Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
        }
      /*}
      else {
        JOptionPane.showMessageDialog(null,"Morate prvo uneti potpolja tipa publikacije","Upozorenje",JOptionPane.INFORMATION_MESSAGE);
      }*/
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PUBLICATION_SUBFIELD_PROCESS_LEVEL"),Messages.get("PRVI_OPTION_PANE_PUBLICATION_SUBFIELD_PROCESS_LEVEL_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Nivo obaveznosti'*/
  void jButton5_actionPerformed(ActionEvent e) {
    String query="select * from nivo_obrade";
    if(exist(query)) {
      MandLevelDlg ml = new MandLevelDlg(null, Messages.get("PRVI_MANDLEVELDLG_TITLE"), true, conn);
      ml.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = ml.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
      ml.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      ml.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PROCESS_LEVEL"),Messages.get("PRVI_OPTION_PANE_PROCESS_LEVEL_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Potpolja nivoa obaveznosti'*/
  void jButton6_actionPerformed(ActionEvent e) {
    //String query="select * from nivo_obaveznosti";
    String query="select distinct b.NOB_TP_TIPID from NIVO_OBRADE_POTPOLJA a, NIVO_OBAVEZNOSTI b"+
                  " where a.NOB_TP_TIPID=b.NOB_TP_TIPID and a.NOB_NOBRID=b.NOB_NOBRID";
    if(exist(query)) {
      /*query="select * from nivo_obrade_potpolja";
      if(exist(query)) {*/
        query="select * from potpolja";
        if(exist(query)) {
          SFMandLevDlg sfmld = new SFMandLevDlg(null, Messages.get("PRVI_SFMANDLEVDLG_TITLE"), true, conn);
          sfmld.pack();
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          Dimension frameSize = sfmld.getSize();
          if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
          if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
          sfmld.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
          sfmld.setVisible(true);
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_2"),Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_WARNING_2"),JOptionPane.INFORMATION_MESSAGE);
        }
      /*}
      else {
        JOptionPane.showMessageDialog(null,"Morate prvo uneti potpolja nivoa obrade","Upozorenje",JOptionPane.INFORMATION_MESSAGE);
      }*/
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PROCESS_SUBFIELD_MANDATORY_LEVEL"),Messages.get("PRVI_OPTION_PANE_PROCESS_SUBFIELD_MANDATORY_LEVEL_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  /** Poziva se kada se spritisne dugme 'Bibliotekar' */
  void jButton7_actionPerformed(ActionEvent e) {
    String query="select * from nivo_obaveznosti";
    if(exist(query)) {
      query="select * from poljebpr";
      if(exist(query)) {
        query="select * from formatf";
        if(exist(query)) {
          LibrarDlg lid = new LibrarDlg(null, Messages.get("PRVI_LIBRARDLG_TITLE"), true, conn);
          lid.pack();
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          Dimension frameSize = lid.getSize();
          if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
          if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
          lid.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
          lid.setVisible(true);
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_FORMAT_DEFINED"),Messages.get("PRVI_OPTION_PANE_FORMAT_DEFINED_WARNING"),JOptionPane.INFORMATION_MESSAGE);
        }
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED"),Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_MANDATORY_LEVEL"),Messages.get("PRVI_OPTION_PANE_MANDATORY_LEVEL_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Potpolja bibliotekara'*/
  void jButton8_actionPerformed(ActionEvent e) {
    String query="select * from bibliotekar";
    if(exist(query)) {
      query="select * from def_tipa";
      if(exist(query)) {
        query="select * from potpolja";
        if(exist(query)) {
//     	  SFLibrarDlg sflid = new SFLibrarDlg(null, "Potpolja bibliotekara", true, conn, myValidator, ufs);
          SFLibrarDlg sflid = new SFLibrarDlg(null, Messages.get("PRVI_SFLIBRARDLG_TITLE"), true, conn, myValidator);
          sflid.pack();
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          Dimension frameSize = sflid.getSize();
          if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
          if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
          sflid.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
          sflid.setVisible(true);
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_3"),Messages.get("PRVI_OPTION_PANE_UNIMARC_SUBFIELD_WARNING_3"),JOptionPane.INFORMATION_MESSAGE);
        }
      }
      else {
      	JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PUBLICATION_SUBFIELD"),Messages.get("PRVI_OPTION_PANE_PUBLICATION_SUBFIELD_WARNING"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_LIBRARIAN"),Messages.get("PRVI_OPTION_PANE_LIBRARIAN_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Poziva se kada se spritisne dugme 'Format'*/
  void jButton9_actionPerformed(ActionEvent e) {
    FormatDlg fd = new FormatDlg(null, Messages.get("PRVI_FORMATDLG_TITLE"), true, conn);
    fd.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = fd.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    fd.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    fd.setVisible(true);
  }

  /** Poziva se kada se spritisne dugme 'Prefiksi pretrazivanja' */
  void jButton10_actionPerformed(ActionEvent e) {
    String query="select * from poljebpr";
    if(exist(query)) {
      SearchPrefDlg spd = new SearchPrefDlg(null, Messages.get("PRVI_SEARCHPREFDLG_TITLE"), true, conn);
      spd.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = spd.getSize();
      if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      spd.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      spd.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_1"),Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  /** Poziva se kada se spritisne dugme 'Prefiksi formata' */
  void jButton11_actionPerformed(ActionEvent e) {
    String query="select * from formatf";
    if(exist(query)) {
      query="select * from poljebpr";
      if(exist(query)) {
        PrefFormDlg pfd = new PrefFormDlg(null, Messages.get("PRVI_PREFFORMDLG_TITLE"), true, conn);
        pfd.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = pfd.getSize();
        if (frameSize.height > screenSize.height)
          frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
          frameSize.width = screenSize.width;
        pfd.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        pfd.setVisible(true);
      }
      else {
      	JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_2"),Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_WARNING_2"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_FORMAT"),Messages.get("PRVI_OPTION_PANE_FORMAT_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  /** Poziva se kada se spritisne dugme 'Potpolja prefiksa pretrazivanja' */
  void jButton12_actionPerformed(ActionEvent e) {
    String query="select * from all_tables where TABLE_NAME='PREFIX_MAP'";
    if(exist(query)) {
      SFSearchPrefDlg sfspd = new SFSearchPrefDlg(null, Messages.get("PRVI_SFSEARCHPREFDLG_TITLE"), true, conn);
      sfspd.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = sfspd.getSize();
      if (frameSize.height > screenSize.height)
      	frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
      	frameSize.width = screenSize.width;
      sfspd.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      sfspd.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_TABEL_NOT_EXIST"),Messages.get("PRVI_OPTION_PANE_TABEL_NOT_EXIST_WARNING"),JOptionPane.INFORMATION_MESSAGE);
    }
  }
  /** Poziva se kada se spritisne dugme 'Tip obrade' */
  void jButton13_actionPerformed(ActionEvent e) {
    String query="select * from bibliotekar";
    if(exist(query)) {
      query="select * from nivo_obaveznosti";
      if(exist(query)) {
        query="select * from formatf";
        if(exist(query)) {
          query="select * from poljebpr";
          if(exist(query)) {
            ProcTypeDlg ptd = new ProcTypeDlg(null, Messages.get("PRVI_PROCTYPEDLG_TITLE"), true, conn);
            ptd.pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = ptd.getSize();
            if (frameSize.height > screenSize.height)
              frameSize.height = screenSize.height;
            if (frameSize.width > screenSize.width)
              frameSize.width = screenSize.width;
            ptd.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            ptd.setVisible(true);
          }
          else {
            JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_3"),Messages.get("PRVI_OPTION_PANE_PREFIX_DEFINED_WARNING_3"),JOptionPane.INFORMATION_MESSAGE);
          }
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_FORMAT_1"),Messages.get("PRVI_OPTION_PANE_FORMAT_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
        }
      }
      else {
        JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_MANDATORY_LEVEL_1"),Messages.get("PRVI_OPTION_PANE_MANDATORY_LEVEL_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("PRVI_OPTION_PANE_LIBRARIAN_1"),Messages.get("PRVI_OPTION_PANE_LIBRARIAN_WARNING_1"),JOptionPane.INFORMATION_MESSAGE);
    }
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