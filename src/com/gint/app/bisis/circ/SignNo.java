package com.gint.app.bisis.circ;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import java.util.*;

import com.gint.app.bisis.textsrv.*;
import com.gint.util.string.StringUtils;
import com.gint.util.string.UnimarcConverter;

public class SignNo extends JDialog{
  boolean closed = true;
  JPanel panel1 = new JPanel();
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
  JComboBox cbOp1 = new JComboBox();
  JComboBox cbOp2 = new JComboBox();
  JComboBox cbOp3 = new JComboBox();
  JComboBox cbOp4 = new JComboBox();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  ActionListener alOK, alCancel;
  TextServer textSrv;
  PrefixListDlg prefixListDlg;
  public Vector expandResults;
  public ListDlg listDlg = new ListDlg(null, "", true);
  private Connection conn;

  public SignNo(JFrame frame, String title, boolean modal, Connection conn) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
      this.conn = conn;
      prefixListDlg = new PrefixListDlg(frame, this.conn);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public SignNo(Connection conn) {
    this(CircApplet.parent, "", false, conn);
  }

  public SignNo(String title, boolean modal, Connection conn){
    this(CircApplet.parent,title,modal, conn);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(271);
    xYLayout1.setWidth(470);


    lPref1.setText("AU =");
    lPref2.setText("TI =");
    lPref3.setText("KW =");
    lPref4.setText("UT =");
    lPref5.setText("AP =");
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

    btnOK.setText(Messages.get("SIGNNO_BTNOK_TEXT"));
    btnCancel.setText(Messages.get("SIGNNO_BTNCANCEL_TEXT"));
    getContentPane().add(panel1);
    panel1.add(lPref1, new XYConstraints(8, 12, -1, -1));
    panel1.add(lPref2, new XYConstraints(8, 42, -1, -1));
    panel1.add(lPref3, new XYConstraints(8, 72, -1, -1));
    panel1.add(lPref4, new XYConstraints(8, 101, -1, -1));
    panel1.add(lPref5, new XYConstraints(8, 131, -1, -1));
    panel1.add(tfPref1, new XYConstraints(46, 10, 284, -1));
    panel1.add(tfPref2, new XYConstraints(46, 40, 284, -1));
    panel1.add(tfPref3, new XYConstraints(46, 70, 284, -1));
    panel1.add(tfPref4, new XYConstraints(46, 100, 284, -1));
    panel1.add(tfPref5, new XYConstraints(46, 130, 284, -1));
    panel1.add(cbOp3, new XYConstraints(343, 71, 68, 20));
    panel1.add(cbOp2, new XYConstraints(343, 41, 68, 20));
    panel1.add(cbOp1, new XYConstraints(343, 11, 68, 20));
    panel1.add(cbOp4, new XYConstraints(343, 101, 68, 20));
    panel1.add(btnOK, new XYConstraints(45, 205, 85, 23));
    panel1.add(btnCancel, new XYConstraints(161, 205, 85, 23));
    setSize(700, 350);

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        closed = true;
        clear();
        setVisible(false);
      }
    });


/*   tfPref1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref1_keyReleased(e);
      }
    });
    tfPref2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref2_keyReleased(e);
      }
    });
    tfPref3.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref3_keyReleased(e);
      }
    });
    tfPref4.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref4_keyReleased(e);
      }
    });
    tfPref5.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfPref5_keyReleased(e);
      }
    });
 }*/
  
  tfPref1.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(KeyEvent e) {
      tfPref1_keyReleased(e);
    }
  });
  tfPref2.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(KeyEvent e) {
      tfPref2_keyReleased(e);
    }
  });
  tfPref3.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(KeyEvent e) {
      tfPref3_keyReleased(e);
    }
  });
  tfPref4.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(KeyEvent e) {
      tfPref4_keyReleased(e);
    }
  });
  tfPref5.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(KeyEvent e) {
      tfPref5_keyReleased(e);
    }
  });
}

  public void addOKListener(ActionListener act){
    alOK = act;
    btnOK.addActionListener(alOK);
  }

  public void removeOKListener(){
    btnOK.removeActionListener(alOK);
  }

  public void addCancelListener(ActionListener act){
    alCancel = act;
    btnCancel.addActionListener(alCancel);
  }

  public void removeCancelListener(){
    btnCancel.removeActionListener(alCancel);
  }

/** Odradjuje pretrazivanje baze po SELECT naredbi DIALOG jezika.
    @param s String sa upitom. Ako je prazan, onda ova metoda sama pokupi
             parametre pretrage iz polja i sama sklopi upit.
*/
  public int searchDocs(String s,Connection conn) {
//    textSrv=new TextServer("sapdb", conn, "jdbc:sapdb://zozon.tmd.ns.ac.yu/BISIS?sqlmode=ORACLE&unicode=yes", "bis", "bis");
//    textSrv = new TextServer(conn);
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
//      errorMessage = "";
//      setFinished(false);

      final String query = new String(s);
      try {
        return getTextSrv().select(query);
      }
      catch (ExpressionException ee){
        System.out.println("<SigNo> select:"+ee);
        return 0;
      }
    }
    else
       return 0;
  }


  /** Generise upit u DIALOG jeziku na osnovu prefiksa i vrednosti.
      @param pref Prefiks po kome ce se vrsiti pretrazivanje.
      @param str Vrednost po kojoj se pretrazuje po zadatom prefiksu.
      @return Upit u DIALOG jeziku.
    */
  private String transform2DIALOG(String pref, String str){
    String retVal = "";
    str = StringUtils.clearDelimiters(str, getTextSrv().getAllDelimiters());
    StringTokenizer st = new StringTokenizer(str, " ");
    if (!st.hasMoreTokens())
      return "";
    retVal = pref+"="+st.nextToken();
    while (st.hasMoreTokens())
      retVal += " [W] "+pref+"="+st.nextToken();
    return retVal;
  }

   public Vector getValue(int hitCount) {
     Vector v = new Vector();
     for (int i=1;i<=hitCount;i++)
        v.addElement(new Integer(getTextSrv().getDocID(i)));
     return v;
  }

  public void clear() {
    tfPref1.setText("");
    tfPref2.setText("");
    tfPref3.setText("");
    tfPref4.setText("");
    tfPref5.setText("");
  }

  public void setVisible(boolean vis) {
    if (vis)
      tfPref1.requestFocus();
    super.setVisible(vis);
  }


  /** Svaki pritisak tastera u nekom od 5 TextField-ova se odradjuje u ovoj metodi. */
  public void handleKeys(JLabel lPref, JTextField tfPref, KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
            btnOK.doClick();
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
      case KeyEvent.VK_F9:
        choosePrefix(lPref, tfPref);
      	break;
      case KeyEvent.VK_F8:
        expand(tfPref);
        break;
      default:
          break;
    }
  }

  void tfPref1_keyReleased(KeyEvent e) {
    handleKeys(lPref1, tfPref1, e);
  }

  void tfPref2_keyReleased(KeyEvent e) {
    handleKeys(lPref2, tfPref2, e);
  }

  void tfPref3_keyReleased(KeyEvent e) {
    handleKeys(lPref3, tfPref3, e);
  }

  void tfPref4_keyReleased(KeyEvent e) {
    handleKeys(lPref4, tfPref4, e);
  }

  void tfPref5_keyReleased(KeyEvent e) {
    handleKeys(lPref5, tfPref5, e);
  }
  
  private void choosePrefix(JLabel lPref, JTextField tfPref) {
    String prefix = lPref.getText().substring(0, 2);
    prefixListDlg.moveTo(prefix);
    prefixListDlg.setVisible(true);
    if (prefixListDlg.isSelected()) {
      prefix = prefixListDlg.getSelectedPrefix();
      lPref.setText(prefix + " =");
    }
  }
  
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
      JOptionPane.showMessageDialog(null, Messages.get("SIGNNO_QUERYERROR"), Messages.get("SIGNNO_SEARCHERROR"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    String query = tf.getText().trim().replace(',', ' ');
    final String prefix1 = prefix;
    final String query1 = query;
    expandResults = getTextSrv().expand(prefix, query);

    if (expandResults.size() == 0)
      JOptionPane.showMessageDialog(null, Messages.get("SIGNNO_EXPANDERROR"), Messages.get("SIGNNO_ERROR"), JOptionPane.ERROR_MESSAGE);
    else {
      Vector v = new Vector();
      int n = expandResults.size();
      for (int i = 0; i < n; i++)
        v.addElement(""+((ExpandPair)expandResults.elementAt(i)).getCount()+": "+conv.Unimarc2Unicode(((ExpandPair)expandResults.elementAt(i)).getContent()));
      listDlg.setTitle(Messages.get("SIGNNO_EXPANDEDPREFIX")+ " " + prefix); //"Ekspandovani prefiks: "
      listDlg.setListData(v);
      listDlg.setVisible(true);
      String sel = listDlg.getSelection();
      if (!sel.equals(""))
        tf.setText(sel.substring(sel.indexOf(':')+2));
    }
  }
  
  
  private TextServer getTextSrv(){
  	if (textSrv == null){
  		textSrv=new TextServer("sapdb", conn, "jdbc:sapdb://zozon.tmd.ns.ac.yu/BISIS?sqlmode=ORACLE&unicode=yes", "bis", "bis");
  	}
  	return textSrv;
  }
  
  private static UnimarcConverter conv = new UnimarcConverter();
}




