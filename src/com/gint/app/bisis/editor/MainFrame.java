package com.gint.app.bisis.editor;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;
import com.borland.jbcl.layout.*;

import com.gint.util.string.UnimarcConverter;
import com.gint.util.string.StringUtils;
import com.gint.util.gui.SwingWorker;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.list.*;
import com.gint.app.bisis.editor.merge.MergeDlg;
import com.gint.app.bisis.editor.comlin.*;
import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.report.*;

/** Klasa glavnog prozora od kog pocinje bisis. */
public class MainFrame extends JFrame {
  public static final int VERSION=3;
  public static final int SUBVERSION=1;
  public static final int BUILD=2;

  XYLayout xYLayout1 = new XYLayout();
  JLabel lPref1 = new JLabel();
  JLabel lPref2 = new JLabel();
  JLabel lPref3 = new JLabel();
  JLabel lPref4 = new JLabel();
  JLabel lPref5 = new JLabel();
  JTextField tfPref1 = new JTextField();
  JTextField tfPref2 = new JTextField();
  JTextField tfPref3 = new JTextField();
  JTextField tfPref4 = new JTextField();
  JTextField tfPref5 = new JTextField();
  JTextField tfStatLin = new JTextField();
  JComboBox cbOp1 = new JComboBox();
  JComboBox cbOp2 = new JComboBox();
  JComboBox cbOp3 = new JComboBox();
  JComboBox cbOp4 = new JComboBox();
  public JLabel lComLin = new JLabel();
  public JTextField tfComLin = new JTextField();
  CharacterLookup lookup = new CharacterLookup(null);
  public ListDlg listDlg = new ListDlg(null, "", true);
  CancelSearchDlg csd = new CancelSearchDlg(this, "", true);
  SingleLineDlg sld = new SingleLineDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_SINGLELINETITLE"), true);
  public HitListDlg hld = new HitListDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_HITLISTTITLE"), true);
  public EditDlg edDlg = new EditDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_RECORDEDITING"), true);
  public PubTypeDlg pubTypeDlg = new PubTypeDlg(this, "", true, this);
  ExpandProgressDlg expandProgressDlg = new ExpandProgressDlg(this, "", true);
  public ReportDlg reportDlg = new ReportDlg(this, "", true);
  public ReportDlgLis reportDlgLis = new ReportDlgLis(this, "", true);
  public SaveCounter saveCounter = new SaveCounter();
  private JTextField last = null;
  private UnimarcConverter conv = new UnimarcConverter();
  public PrintFrame printFrame;// = new PrintFrame();
  public MergeDlg mergeDlg = new MergeDlg(this);
  boolean tfComLinOK = true;


  public MainFrame(Connection conn) {
    pingTimer.start();
    setFinished(false);
    searchTimer.start();
    searchTimer.stop();
    this.conn = conn;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    // Napunimo labele prefiksima iz baze, za tekuceg bibliotekara.
    Vector v = Environment.getLib().getPrefixes();
    lPref1.setText((String)v.elementAt(0)+" =");
    lPref2.setText((String)v.elementAt(1)+" =");
    lPref3.setText((String)v.elementAt(2)+" =");
    lPref4.setText((String)v.elementAt(3)+" =");
    lPref5.setText((String)v.elementAt(4)+" =");
  }

  private void jbInit() throws Exception  {
    this.getContentPane().setLayout(xYLayout1);
    setIconImage(new ImageIcon(getClass().getResource("/com/gint/app/bisis/editor/images/icon.gif")).getImage());
    this.setTitle("BISIS v"+VERSION+"."+SUBVERSION+"."+BUILD);
    lPref1.setText("AU =");
    lPref2.setText("TI =");
    lPref3.setText("KW =");
    lPref4.setText("PU =");
    lPref5.setText("PY =");
    // Operator posle prvog text field-a
    cbOp1.addItem("And");
    cbOp1.addItem("Or");
    cbOp1.addItem("Not");
    // Operator posle drugog text field-a
    cbOp2.addItem("And");
    cbOp2.addItem("Or");
    cbOp2.addItem("Not");
    // Operator posle treceg text field-a
    cbOp3.addItem("And");
    cbOp3.addItem("Or");
    cbOp3.addItem("Not");
    // Operator posle cetvrtog text field-a
    cbOp4.addItem("And");
    cbOp4.addItem("Or");
    cbOp4.addItem("Not");

    xYLayout1.setHeight(350);
    xYLayout1.setWidth(700);
    lComLin.setText(" ");
    tfPref1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref1_keyReleased(e);
      }
    });
    tfPref1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tfPref1_actionPerformed(e);
      }
    });
    tfPref2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref2_keyReleased(e);
      }
    });
    tfPref2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tfPref2_actionPerformed(e);
      }
    });
    tfPref3.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref3_keyReleased(e);
      }
    });
    tfPref3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tfPref3_actionPerformed(e);
      }
    });
    tfPref4.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref4_keyReleased(e);
      }
    });
    tfPref4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tfPref4_actionPerformed(e);
      }
    });
    tfPref5.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref5_keyReleased(e);
      }
    });
    tfPref5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tfPref5_actionPerformed(e);
      }
    });
    tfComLin.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfComLin_keyReleased(e);
      }
    });
	tfComLin.addActionListener(new java.awt.event.ActionListener() {
	  public void actionPerformed(ActionEvent e) {
		tfComLin_actionPerformed(e);
	  }
	});
    tfStatLin.setEditable(false);
    this.getContentPane().add(lPref1, new XYConstraints(8, 12, -1, -1));
    this.getContentPane().add(lPref2, new XYConstraints(8, 42, -1, -1));
    this.getContentPane().add(lPref3, new XYConstraints(8, 72, -1, -1));
    this.getContentPane().add(lPref4, new XYConstraints(8, 101, -1, -1));
    this.getContentPane().add(lPref5, new XYConstraints(8, 131, -1, -1));
    this.getContentPane().add(tfPref1, new XYConstraints(46, 10, 560, -1));
    this.getContentPane().add(cbOp1, new XYConstraints(609, 10, 68, 20));
    this.getContentPane().add(tfPref2, new XYConstraints(46, 40, 560, -1));
    this.getContentPane().add(cbOp2, new XYConstraints(609, 40, 68, 20));
    this.getContentPane().add(tfPref3, new XYConstraints(46, 70, 560, -1));
    this.getContentPane().add(cbOp3, new XYConstraints(609, 70, 68, 20));
    this.getContentPane().add(tfPref4, new XYConstraints(46, 100, 560, -1));
    this.getContentPane().add(cbOp4, new XYConstraints(609, 100, 68, 20));
    this.getContentPane().add(tfPref5, new XYConstraints(46, 130, 560, -1));
    this.getContentPane().add(tfStatLin, new XYConstraints(8, 297, 671, -1));
    this.getContentPane().add(lComLin, new XYConstraints(9, 274, 150, -1));
    this.getContentPane().add(tfComLin, new XYConstraints(165, 272, 514, -1));
    setSize(700, 350);

    //Tanja dodala
    printFrame = new PrintFrame(conn);

  }

//  protected void processWindowEvent(WindowEvent e) {
//    super.processWindowEvent(e);
//    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
//      this.exit(0);
//    }
//  }

  public synchronized void setFinished(boolean finished) {
    this.finished = finished;
  }

  public synchronized void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public synchronized boolean getFinished() {
    return finished;
  }

  public synchronized String getErrorMessage() {
    return errorMessage;
  }

  private static int pingTime;
  public static void setPingTime(int t) {
    pingTime = t;
  }

  /* Tajmer koji odrzava vezu sa RMI serverom tako sto se periodicno "javi". */
  Timer pingTimer = new Timer(pingTime, new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (Environment.isInternal() == false) {
        try { Environment.getReadTs().getHitCount(); } catch (Exception ex) { ex.printStackTrace(); }
      }
    }
  });

  /** Preuzeta referenca na konekciju sa bazom. */
  Connection conn;
  /** Poruka o gresci prilikom pretrazivanja. */
  String errorMessage = "";
  /** Fleg da li je pretrazivanje gotovo. */
  boolean finished = false;
  /** Tajmer za osvezavanje labele sa brojem pogodaka. */
  Timer searchTimer = new Timer(200, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (getFinished()) {
          lComLin.setForeground(new Color(102, 102, 153));
          // IMPORTANT: The comparison below depens on the language!
          // Original error message regarding query interruption is placed in the textsrv package,
          // in the appropriate .properties language resource file.
          if (getErrorMessage().equals(com.gint.app.bisis.textsrv.Messages.get("QUERY_INTERRUPTED"))) {
            lComLin.setText(com.gint.app.bisis.textsrv.Messages.get("QUERY_INTERRUPTED"));
          } else if (!getErrorMessage().equals("")) {
            JOptionPane.showMessageDialog(null, getErrorMessage(), com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_SEARCHERROR"), JOptionPane.ERROR_MESSAGE);
          } else { // search finished successfuly
//            tfComLin.requestFocus();
csd.setVisible(false);

            if (Environment.getJavaVersion().compareTo("1.4") >= 0)
              tfComLin.requestFocus();
            else
              edDlg.mf.requestFocus();

            int hits = -1;
            try { hits = Environment.getReadTs().getHitCount(); } catch (Exception ex) {}
            if (Environment.getLocale().equals("sr"))
              lComLin.setText(oblik_lokalni(hits)+" "+hits+" "+oblik_pogodak(hits));
            else
              lComLin.setText(hits+" "+oblik_pogodak(hits));
          }
          searchTimer.stop();
        }
      }
    });

  /** Tajmer za osvezavanje statusne linije tokom snimanja. */
  public Timer saveTimer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (saveCounter.getCount() == 0)
          saveTimer.stop();
        edDlg.updateStatLin(updateStatLin());
        if (!saveCounter.isLastSuccessful()) {
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_SAVEFAILED_ID")+saveCounter.getLastDocID(), com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_SAVEFAILED"), JOptionPane.ERROR_MESSAGE);
          saveCounter.setLastSuccessful(true, -1);
        }
      }
    });

  /** Interpreter komandne linije. */
  CommandParser commParser = new CommandParser(this);
  /** Vektor rezultata expanda. */
  public Vector expandResults;

  /** Prikazuje Swing dijalog za izbor specijalnog karaktera.
    * @param tf TextField za koga se lookup radi
    */
  private void handleLookup(JTextComponent tf) {
    lookup.setVisible(true);
    if (lookup.isSelected()) {
      char c = lookup.getSelectedChar();
      int pos = tf.getCaretPosition();
      String s1 = tf.getText().substring(0, pos);
      String s2 = (pos == tf.getText().length()) ? "" : tf.getText().substring(pos);
      tf.setText(s1 + c + s2);
      tf.setCaretPosition(pos+1);
    }
  }

  /** Prikazuje dijalog sa listom za izbor prefiksa za pretrazivanje
    * @param l Labela na koju se izbor odnosi
    */
  public void handlePrefixChoice(JLabel l) {
    Vector v = Environment.getLib().getPrefixNames();
    listDlg.setListData(v, l.getText().substring(0, 2));
    listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_CHOOSEPREFIX"));
    listDlg.setVisible(true);
    if (!listDlg.getSelection().equals(""))
      l.setText(listDlg.getSelection().substring(0, 2)+" =");
  }

  /** Obavlja pretrazivanje baze. Poziva se iz metode <code>construct</code>
      interfejsa <b>SwingWorker</b>. Ovim je omoguceno da se pretrazivanje obavlja
      u jednom tredu, tako da moze da se prekine.
      <b>Timer</b> koristi za ispis rezultata pretrage.
      @see select
  */
  public class SearchTask {
    public SearchTask(String query) {
      try {
        int hits =  Environment.getReadTs().select(query);
      } catch (ExpressionException ex) {
        try { Environment.getReadTs().clearHits(); } catch (Exception e) {}
        setErrorMessage(ex.toString().substring(ex.toString().indexOf(':')+1));
      } catch (Exception ex) {
      }
      csd.setVisible(false);
      setFinished(true);
    }
  }

  /** Obavlja expand baze. Poziva se iz metode <code>construct</code>
      interfejsa <b>SwingWorker</b>. Ovim je omoguceno da se expandovanje obavlja
      u jednom tredu, tako da moze da se prekine.
      see expand
  */
  public class ExpandTask {
    public ExpandTask(final String prefix, final String query) {
      try {
        expandResults = Environment.getReadTs().expand(prefix, query);
        expandProgressDlg.setFinished(true);
      } catch (Exception ex) {}
    }
  }

  /** Vrsi expand operaciju nad datim JTextField-om. Otvara dijalog
      sa listom ekspandovanih izraza i nudi izbor jednog od njih.
      @param tf Komponenta na koju se expand odnosi
    */
  public void expand(JTextField tf) {
    String prefix = "";
    if (tf == tfPref1)
      prefix = lPref1.getText().substring(0, 2);
    else if (tf == tfPref2)
      prefix = lPref2.getText().substring(0, 2);
    else if (tf == tfPref3)
      prefix = lPref3.getText().substring(0, 2);
    else if (tf == tfPref4)
      prefix = lPref4.getText().substring(0, 2);
    else if (tf == tfPref5)
      prefix = lPref5.getText().substring(0, 2);

    if (tf.getText().trim().equals("")) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_QUERYERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_SEARCHERROR"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    String query = tf.getText().trim().replace(',', ' ');
    final String prefix1 = prefix;
    final String query1 = query;
    expandProgressDlg.setFinished(false);
    expandProgressDlg.timer.start();
    expandProgressDlg.setCanceled(false);
    final SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new ExpandTask(prefix1, query1);
      }
    };
    worker.start();
    expandProgressDlg.setVisible(true);

    // u ovoj tacki se ceka dok se ne zatvori dijalog...

    if (expandProgressDlg.isCanceled()) {
    } else if (expandResults.size() == 0)
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_EXPANDERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
    else {
      Vector v = new Vector();
      int n = expandResults.size();
      for (int i = 0; i < n; i++)
        v.addElement(""+((ExpandPair)expandResults.elementAt(i)).getCount()+": "+conv.Unimarc2Unicode(((ExpandPair)expandResults.elementAt(i)).getContent()));
      listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_EXPANDEDPREFIX")+ " " + prefix); //"Ekspandovani prefiks: "
      listDlg.setListData(v);
      listDlg.setVisible(true);
      String sel = listDlg.getSelection();
      if (!sel.equals(""))
        tf.setText(sel.substring(sel.indexOf(':')+2));
    }
  }

  /** Odradjuje pretrazivanje baze po SELECT naredbi DIALOG jezika.
      @param s String sa upitom. Ako je prazan, onda ova metoda sama pokupi
               parametre pretrage iz polja i sama sklopi upit.
  */
  public void select(String s) {
    if (s.equals("")) { // ako je prosledjen prazan string, onda se podaci kupe iz vise linija
      String andStr = "";
      if (tfPref1.getText().trim().length() > 0) {
        s += andStr + transform2DIALOG(lPref1.getText().substring(0, 2), tfPref1.getText());
        andStr = s.equals("") ? "" : " " + ((String)cbOp1.getSelectedItem()).toLowerCase() + " ";
      }
      if (tfPref2.getText().trim().length() > 0) {
        s += andStr + transform2DIALOG(lPref2.getText().substring(0, 2), tfPref2.getText());
        andStr = s.equals("") ? "" : " " + ((String)cbOp2.getSelectedItem()).toLowerCase() + " ";
      }
      if (tfPref3.getText().trim().length() > 0) {
        s += andStr + transform2DIALOG(lPref3.getText().substring(0, 2), tfPref3.getText());
        andStr = s.equals("") ? "" : " " + ((String)cbOp3.getSelectedItem()).toLowerCase() + " ";
      }
      if (tfPref4.getText().trim().length() > 0) {
        s += andStr + transform2DIALOG(lPref4.getText().substring(0, 2), tfPref4.getText());
        andStr = s.equals("") ? "" : " " + ((String)cbOp4.getSelectedItem()).toLowerCase() + " ";
      }
      if (tfPref5.getText().trim().length() > 0)
        s += andStr + transform2DIALOG(lPref5.getText().substring(0, 2), tfPref5.getText());
    }
    if (!s.equals("")) { // ako nije prazan string, onda pocni pretrazivanje
      lComLin.setForeground(Color.red);
      errorMessage = "";
      setFinished(false);
      final String query = new String(s);
      searchTimer.stop();
      searchTimer.start();
      final SwingWorker worker = new SwingWorker() {
        public Object construct() {
          return new SearchTask(query);
        }
      };
      worker.start();
      tfComLin.requestFocus();
      csd.setVisible(true);
    }
  }
  
  public java.util.List getQueryContent() {
    java.util.List retVal = new ArrayList();
    String text1 = tfPref1.getText().trim(); 
    if (!text1.equals(""))
      retVal.add(lPref1.getText().substring(0, 2) + " = " + text1);
    String text2 = tfPref2.getText().trim(); 
    if (!text2.equals(""))
      retVal.add(lPref2.getText().substring(0, 2) + " = " + text2);
    String text3 = tfPref3.getText().trim(); 
    if (!text3.equals(""))
      retVal.add(lPref3.getText().substring(0, 2) + " = " + text3);
    String text4 = tfPref4.getText().trim(); 
    if (!text4.equals(""))
      retVal.add(lPref4.getText().substring(0, 2) + " = " + text4);
    String text5 = tfPref5.getText().trim(); 
    if (!text5.equals(""))
      retVal.add(lPref5.getText().substring(0, 2) + " = " + text5);
    return retVal;
  }

  /** Generise upit u DIALOG jeziku na osnovu prefiksa i vrednosti.
      @param pref Prefiks po kome ce se vrsiti pretrazivanje.
      @param str Vrednost po kojoj se pretrazuje po zadatom prefiksu.
      @return Upit u DIALOG jeziku.
    */
  private String transform2DIALOG(String pref, String str){
    String retVal = "";
    str = StringUtils.clearDelimiters(str, Environment.getWriteTs().getAllDelimiters());
    StringTokenizer st = new StringTokenizer(str, " ");
    if (!st.hasMoreTokens())
      return "";
    retVal = pref+"="+st.nextToken();
    while (st.hasMoreTokens())
      retVal += " [W] "+pref+"="+st.nextToken();
    return retVal;
  }

  /** Vraca rec "lokalni" u odgovarajucem obliku zavisno od
      datog broja pogodaka.
      @param koliko Broj pogodaka
      @return Odgovarajuci oblik reci
    */
  public String oblik_lokalni(int koliko) {
    String res = "Lokaln";
    int tens = koliko % 100;
    int units = koliko % 10;
    if (tens >= 11 && tens <= 14)
       res = res + "ih";
    else if (units == 1)
       res = res + "i";
    else if (units > 1 && units < 5)
       res = res + "a";
    else
       res = res + "ih";
    return res;
  }

  /** Vraca rec "pogodak" u odgovarajucem obliku zavisno od
      datog broja pogodaka.
      @param koliko Broj pogodaka
      @return Odgovarajuci oblik reci
    */
  public String oblik_pogodak(int koliko) {
    if (Environment.getLocale().equals("sr")) {
      String res = "pogo";
      int tens = koliko % 100;
      int units = koliko % 10;

      if (tens >= 11 && tens <= 14)
         res = res + "daka";
      else if (units == 1)
         res = res + "dak";
      else if (units > 1 && units < 5)
         res = res + "tka";
      else
         res = res + "daka";
      return res;
    } else {
      if (koliko == 1)
        return com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_LOCALHIT");
      else
        return com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_LOCALHITS");
    }
  }

  /** Azurira statusnu liniju na osnovu parametara koje kupi iz okruzenje. */
  public String updateStatLin() {
    String s = Environment.getLib().getLibName()+"    "+
      Environment.getLib().getPubType()+"         " + com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_STATUSLEVEL") + "  "+
      Environment.getLib().getProcLevel()+
      "/"+Environment.getLib().getManLevel()+"                " +
      (edDlg.insertMode == EditDlg.ADD_MODE ? com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_STATUSADD") : com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_STATUSINSERT"));
    if (saveCounter.getCount() > 0)
      s += "       ---- " + com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_STATUS_SAVEINPROGRESS") + " (" + saveCounter.getCount() + ") ----";
    tfStatLin.setText(s);
    return s;
  }

  /** Svaki pritisak tastera u nekom od 5 TextField-ova se odradjuje u ovoj metodi. */
  public void handleKeys(JLabel lPref, JTextField tfPref, KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_F1:
        handleLookup(tfPref);
        break;
      case KeyEvent.VK_ESCAPE:
        JLabel msg = new JLabel(com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_WANNAEXIT"));
        msg.setForeground(Color.black);
        String defBtn = "  " + com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_YES") + "  ";
        String[] buttons = {defBtn, "  " + com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_NO") + "  "};
        if (JOptionPane.showOptionDialog(
               null,
               msg,
              com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_EXITAPP"),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.INFORMATION_MESSAGE,
              null,
              buttons,
              defBtn) == JOptionPane.YES_OPTION) {
          try { conn.close(); } catch (SQLException ex) { }
          this.exit(0);
        }
        break;
      case KeyEvent.VK_F9:
        handlePrefixChoice(lPref);
        break;
      case KeyEvent.VK_F8:
        expand(tfPref);
        break;
      case KeyEvent.VK_F7:
        sld.setVisible(true);
        break;
      case KeyEvent.VK_F6:
        last = tfPref;
        tfComLin.requestFocus();
        break;
      case KeyEvent.VK_PAUSE:
        last = tfPref;
        select("");
        break;
      case KeyEvent.VK_F12:
        pubTypeDlg.setData();
        pubTypeDlg.setVisible(true);
        break;
      case KeyEvent.VK_DOWN:
        if (tfPref == tfPref1)
          tfPref2.requestFocus();
        else if (tfPref == tfPref2)
          tfPref3.requestFocus();
        else if (tfPref == tfPref3)
          tfPref4.requestFocus();
        else if (tfPref == tfPref4)
          tfPref5.requestFocus();
        else if (tfPref == tfPref5)
          tfPref1.requestFocus();
        break;
      case KeyEvent.VK_UP:
        if (tfPref == tfPref1)
          tfPref5.requestFocus();
        else if (tfPref == tfPref2)
          tfPref1.requestFocus();
        else if (tfPref == tfPref3)
          tfPref2.requestFocus();
        else if (tfPref == tfPref4)
          tfPref3.requestFocus();
        else if (tfPref == tfPref5)
          tfPref4.requestFocus();
        break;
      default:
        break;
    }
  }

  /** Svaki pritisak tastera ENTER u nekom od 5 TextField-ova se odradjuje u ovoj metodi. */
  public void handleEnter(JLabel lPref, JTextField tfPref, ActionEvent e) {
    last = tfPref;
    select("");
  }

  void tfPref1_keyReleased(KeyEvent e) {
    handleKeys(lPref1, tfPref1, e);
  }
  void tfPref1_actionPerformed(ActionEvent e) {
    handleEnter(lPref1, tfPref1, e);
  }

  void tfPref2_keyReleased(KeyEvent e) {
    handleKeys(lPref2, tfPref2, e);
  }
  void tfPref2_actionPerformed(ActionEvent e) {
    handleEnter(lPref2, tfPref2, e);
  }

  void tfPref3_keyReleased(KeyEvent e) {
    handleKeys(lPref3, tfPref3, e);
  }
  void tfPref3_actionPerformed(ActionEvent e) {
    handleEnter(lPref3, tfPref3, e);
  }

  void tfPref4_keyReleased(KeyEvent e) {
    handleKeys(lPref4, tfPref4, e);
  }
  void tfPref4_actionPerformed(ActionEvent e) {
    handleEnter(lPref4, tfPref4, e);
  }

  void tfPref5_keyReleased(KeyEvent e) {
    handleKeys(lPref5, tfPref5, e);
  }
  void tfPref5_actionPerformed(ActionEvent e) {
    handleEnter(lPref5, tfPref5, e);
  }

  /** Komandna linija. */
  void tfComLin_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        String commandText = tfComLin.getText().trim();
        if (commandText.equals(""))
          return;
        if (!tfComLinOK) {
          tfComLinOK = true;
          return;
        }
        AbstractCommand comm = commParser.parse(commandText);
        if (comm != null) {
          comm.execute();
          tfComLinOK = tfComLin.getText().equals("");
        } else{ 
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_UNKNOWN_COMMAND"),com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          tfComLinOK = false;
        }
        break;
      case KeyEvent.VK_ESCAPE:
        if (last != null)
          last.requestFocus();
        else
          tfPref1.requestFocus();
        break;
      case KeyEvent.VK_F12:
        pubTypeDlg.setData();
        pubTypeDlg.setVisible(true);
        break;
      default:
        break;
    }
  }

  void tfComLin_actionPerformed(ActionEvent e) {
    String commandText = tfComLin.getText().trim();
//    if (commandText.equals(""))
//      return;
//    AbstractCommand comm = commParser.parse(commandText);
//	  if (comm != null)
//	    comm.execute();
//	  else
//	    JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_MAINFRAME_UNKNOWN_COMMAND"),com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
  }	

  public void exit(int code) {
    // ako je bio spojen na spoljasnji server, treba vratiti TextServer u Pool
    if (Environment.isInternal() == false) {
      if (Environment.getAppServer() == Environment.RMI_SERVER)
        try { Environment.getExternalTextServerManager().checkIn(
                ((RemoteTextServer)(Environment.getReadTs())).getTextServer().getServerUID()
                                                                );
        } catch (Exception ex) { ex.printStackTrace();
      }
    }
    System.exit(code);
  }
  
}
