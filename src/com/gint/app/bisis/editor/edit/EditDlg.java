package com.gint.app.bisis.editor.edit;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;
import com.borland.jbcl.layout.*;
import com.objectspace.jgl.HashMap;
import com.objectspace.jgl.HashSet;
import java.awt.event.*;

import com.gint.util.string.StringUtils;
import com.gint.util.string.UnimarcConverter;
import com.gint.util.sort.Sorter;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;
import com.gint.app.bisis.editor.list.*;
import com.gint.app.bisis.editor.registries.RegistryDlg;
import com.gint.app.bisis.editor.registries.RegistryUtils;
import com.gint.app.bisis.editor.UFieldSet.*;
import com.gint.app.bisis.editor.comlin.*;

public class EditDlg extends JDialog {
  RegistryDlg registryDlg;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lStatLin1 = new JLabel();
  JLabel lStatLin2 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  public JTable tEdit = new JTable();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea taContents = new JTextArea();
  ListDlg listDlg = new ListDlg(null, "", true);
  public IndDlg indDlg = new IndDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_INDTITLE"), true);
  public AddFieldDlg afDlg = new AddFieldDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_ADDINSERTFIELDTITLE"), true);
  public AddSubfieldDlg asfDlg = new AddSubfieldDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_ADDINSERTSUBFIELDTITLE"), true);
  public SubsubfieldDlg ssfDlg = new SubsubfieldDlg(this, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_SUBSUBFIELDTITLE"), true);
  public CharacterLookup lookup = new CharacterLookup(null);
  public static final int INSERT_MODE = 0;
  public static final int ADD_MODE    = 1;
  public int insertMode = ADD_MODE;
  public boolean cameFromSubsubfieldList = false;
  public MainFrame mf;
  public UnimarcTableModel utm = new UnimarcTableModel();
  public FieldCellRenderer fcr = new FieldCellRenderer();
  // Polje 000 se ne prikazuje, vec se samo azurira i tek pri snimanju ukljucuje
  // nazad u rezanac.
  public Field field000;
  JLabel lCommand = new JLabel();
  public JTextField tfComLin = new JTextField();
  JTextField tfStatLin = new JTextField();
  JTextField tfContents = new JTextField();
  public CommandParser commParser = new CommandParser(this);
  public static final int CELL_SPACING = 5;
  public int minCellWidth;
  private UnimarcConverter conv = new UnimarcConverter();
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

  public EditDlg(MainFrame frame, String title, boolean modal) {
    super(frame, title, modal);
    this.mf = frame;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    registryDlg = new RegistryDlg(frame);
  }

  public EditDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    tEdit.setModel(utm);
    tEdit.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tEdit.setColumnSelectionAllowed(true);
    tEdit.setRowSelectionAllowed(true);
    tEdit.setShowHorizontalLines(false);
    tEdit.setShowVerticalLines(false);
    tEdit.setTableHeader(null);
    tEdit.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseReleased(MouseEvent e) {
        tEdit_mouseReleased(e);
      }
    });
    tEdit.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        tEdit_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        tEdit_keyReleased(e);
      }
    });
    for (int i = 0; i < UnimarcTableModel.COL_COUNT; i++)
      tEdit.getColumnModel().getColumn(i).setCellRenderer(fcr);

    panel1.setLayout(xYLayout1);
    lStatLin1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELSTATLIN1"));
    lStatLin2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELSTATLIN2"));
    xYLayout1.setHeight(500);
    xYLayout1.setWidth(700);
    lCommand.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELCOMLIN"));
    taContents.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        taContents_keyReleased(e);
      }
    });
    tfContents.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfContents_keyReleased(e);
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
    getContentPane().add(panel1);
    panel1.add(lStatLin1, new XYConstraints(12, 12, -1, -1));
    panel1.add(lStatLin2, new XYConstraints(12, 220, -1, -1));
    panel1.add(jScrollPane1, new XYConstraints(12, 39, 673, 170));
    panel1.add(tfContents, new XYConstraints(12, 249, 672, -1));
    panel1.add(jScrollPane2, new XYConstraints(12, 249, 672, 178));
    jScrollPane2.getViewport().add(taContents, null);
    panel1.add(lCommand, new XYConstraints(12, 444, 77, -1));
    panel1.add(tfComLin, new XYConstraints(95, 442, 589, -1));
    panel1.add(tfStatLin, new XYConstraints(12, 472, 672, -1));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane1.getViewport().add(tEdit, null);
    setSize(700, 500);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    minCellWidth = 665 / UnimarcTableModel.COL_COUNT;
  }

  public void setMultiLine(boolean multi) {
    if (multi) {
      tfContents.setVisible(false);
      jScrollPane2.setVisible(true);
      taContents.setVisible(true);
    } else {
      tfContents.setVisible(true);
      jScrollPane2.setVisible(false);
      taContents.setVisible(false);
    }
  }

  /** Vraca prethodno polje (ako ga ima).
     @return Prethodno polje.
   */
  public Field getPrevField() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if ((row == 0) && (col == 0))
      return null;
    else {
      if (col > 0) {
        col--;
      } else {
        row--;
        col = UnimarcTableModel.COL_COUNT-1;
      }
      return (Field)utm.getValueAt(row, col);
    }
  }

  /** Vraca tekuce polje.
     @return Tekuce polje.
   */
  public Field getCurrField() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    return (Field)utm.getValueAt(row, col);
  }

  /** Vraca sledece polje (ako ga ima).
     @return Sledece polje.
   */
  public Field getNextField() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (!utm.isThereAFieldRight(row, col))
      return null;
    else {
      if ((col == UnimarcTableModel.COL_COUNT-1) && (row < utm.getRowCount()-1)) {
        col = 0;
        row++;
      } else
        col++;
    }
    return (Field)utm.getValueAt(row, col);
  }

  /** Podesava sirine kolona u tabeli prema najsirim poljima
    */
  public void updateColumnWidths() {
    int currWidth;
    int maxWidth;
    int newTotalWidth = 0;
    int currentTotalWidth = tEdit.getSize().width;
    int height = tEdit.getSize().height;
    TableColumn tc;
    for (int i = 0; i < utm.getColumnCount(); i++) {
      tc = tEdit.getColumnModel().getColumn(i);
      currWidth = tc.getWidth();
      if (currWidth < minCellWidth)
        currWidth = minCellWidth;
      maxWidth = utm.getMaxWidth(i);
      if (currWidth < maxWidth + CELL_SPACING) {
        // sirimo kolonu prema najsirem polju u koloni
        tc.setPreferredWidth(maxWidth + CELL_SPACING);
        tc.setMinWidth(maxWidth + CELL_SPACING);
        tc.setMaxWidth(maxWidth + CELL_SPACING);
        newTotalWidth += maxWidth + CELL_SPACING;
      } else if (currWidth > minCellWidth) {
        // suzavamo kolonu na vecu vrednost od (najsire polje u koloni, minCellWidth)
        int newCurr = (maxWidth > minCellWidth) ? maxWidth : minCellWidth;
        tc.setPreferredWidth(newCurr);
        tc.setMinWidth(newCurr);
        tc.setMaxWidth(newCurr);
        newTotalWidth += newCurr;
      } else {
        tc.setPreferredWidth(minCellWidth);
        tc.setMinWidth(minCellWidth);
        tc.setMaxWidth(minCellWidth);
        newTotalWidth += minCellWidth;
      }
    }
    if (newTotalWidth != currentTotalWidth)
      tEdit.setSize(new Dimension(newTotalWidth, height));
  }


  /** Azurira vektor polja sa potpoljima na osnovu okruzenja. Iz okruzenja kupi
      sva potrebna polja i potpolja kod tekuceg tipa publikacije i dodaje ih
      (ako ih nema u vektoru).
      @param fields Vektor polja koji treba azurirati na osnovu okruzenja
      @return Azurirani vektor polja
     */
  public Vector init(Vector fields) {
    row_ = 0;
    col_ = 0;
//    setDirty(false);

    Vector retVal = new Vector();
    // Pokupimo vektor potpolja za tekuci tip publikacije i nivo obrade.
    Vector v = Environment.getLib().getProcLESubfields();
    // Kompresujemo ga u oblik kompatibilan sa vektorom fields (pogledaj compressSubfields())
    v = Environment.getLib().compressSubfields(v);

    boolean ubaci;
    int counter = 0;
    int indexLE = 0;
    if (fields.size() == 0) { // ako je fields prazan, pravimo nov rezanac (komanda new)
      // Napravicemo polje 000, 001 i 100 rucno
      field000 = new Field("000", " ", " ");
      // kreiramo potpolja za polje 000
      // potpolje a ima tip publikacije!
      Subfield sf0 = new Subfield("a", Environment.getLib().pubTypeDecode(Environment.getLib().getPubType()));
      field000.addSubfield(sf0);
      sf0 = new Subfield("b", " ");
      field000.addSubfield(sf0);
      sf0 = new Subfield("c", " ");
      field000.addSubfield(sf0);
      sf0 = new Subfield("d", " ");
      field000.addSubfield(sf0);
      sf0 = new Subfield("e", " ");
      field000.addSubfield(sf0);
      sf0 = new Subfield("f", " ");
      field000.addSubfield(sf0);
      sf0 = new Subfield("g", " ");
      field000.addSubfield(sf0);

      // Probamo da ubacimo obavezna polja iz okruzenja
      for ( ; indexLE < v.size(); indexLE++) {
        LESubfield le = (LESubfield) v.elementAt(indexLE);
        Field newField = new Field(le.getField(),
                             Environment.getFs().getDefFirstIndicator(le.getField()),
                             Environment.getFs().getDefSecondIndicator(le.getField()));
        String subfields = Sorter.qsort(le.getSubfield(le.getField()));
        for (int brPotpolja = 0; brPotpolja < subfields.length(); brPotpolja++ ) {
          Subfield sf;
          if (brPotpolja == (subfields.length() -1))
            sf =new Subfield(subfields.substring(brPotpolja), "");
          else
            sf =new Subfield(subfields.substring(brPotpolja, brPotpolja + 1), "");
          // dodavanje default vrednosti za polje i potpolje
//          if (newField.getName().equals("001")) {
//            if (sf.getName().equals("a"))
//              sf.setContent(Environment.getLib().getDefValue("001", "a"));
//            else if (sf.getName().equals("b"))
//              sf.setContent(Environment.getLib().getDefValue("001", "b"));
//            else if (sf.getName().equals("c"))
//              sf.setContent(Environment.getLib().getDefValue("001", "c"));
//            else if (sf.getName().equals("d"))
//              sf.setContent(Environment.getLib().getDefValue("001", "d"));
//          } else if (newField.getName().equals("100")) {
//            if (sf.getName().equals("h"))
//              sf.setContent(Environment.getLib().getDefValue("100", "h"));
//          }
          String temp = Environment.getLib().getDefValue(newField.getName(), sf.getName());
          if (temp != null && !temp.equals("")) {
            sf.setContent(temp);
          } else {
            USubfield usf = Environment.getFs().getSubfield(newField.getName(), sf.getName());
            if (usf != null) {
              temp = usf.getDefValue();
              if (temp != null)
                sf.setContent(temp);
            }
          }
          newField.addSubfield(sf);
        }
        // dodamo polje u povratni vektor.
        retVal.insertElementAt(newField, counter);
        counter++;
      }
    } else { // inace, editujem postojeci rezanac (komanda edit)
      // Krenemo kroz sva polja
      for( int brojac = 0; brojac < fields.size(); brojac++) {//while (fieldIter.hasMoreElements()) {
        ubaci = true;
        // Pokupimo tekuce polje.
        Field currField = (Field)fields.elementAt(brojac);
        // ako naidjemo na polje 000 ne ubacujemo ga u strukturu, vec ga
        // cuvamo u posebnoj strukturi
        if (currField.getName().equals("000")) {
          field000 = currField;
          continue;
        } // if
        // Probamo da ubacimo obavezna polja iz okruzenja
        for ( ; indexLE < v.size(); indexLE++) {
          LESubfield le = (LESubfield) v.elementAt(indexLE);
          // jednaki
          if (le.getField().equals(currField.getName())) {   // kada su jednaka imena treba
            String subfields = Sorter.qsort(le.getSubfield(le.getField()));// proci po potpoljima
            String subfield;
            for (int i = 0; i < subfields.length(); i++) {
              subfield = i < (subfields.length()) ? subfields.substring(i, i+1) : subfields.substring(i);
              if(currField.indexOf(subfield) == -1) { // ne postoji, i treba dodati ovo potpolje
                Subfield sf = new Subfield(subfield, "");
                // potrazim indeks gde cu da smestim novo potpolje.
                int indeks = currField.findPos(subfield);
                if (indeks != -1)
                  currField.insertSubfieldAt(sf, indeks);
                else
                  currField.addSubfield(sf);
              }
            } // for
            // dodamo polje u povratni vektor.
            retVal.addElement(currField);
            counter++;
            indexLE++;
            ubaci = false; // ne ubacuj ponovo u  listu newField-ova
            break;
          } else if (le.getField().compareTo(currField.getName()) < 0) {// le manji od currField -> treba ga ubaciti ispred currField
            // kada treba dodati polje iz okruzenja
            Field newField = new Field(le.getField(),
                                 Environment.getFs().getDefFirstIndicator(le.getField()),
                                 Environment.getFs().getDefSecondIndicator(le.getField()));
            String subfields =  Sorter.qsort(le.getSubfield(le.getField()));
            for(int brPotpolja = 0; brPotpolja < subfields.length(); brPotpolja++ ) {
              Subfield sf;
              if (brPotpolja == (subfields.length() -1))
                sf = new Subfield(subfields.substring(brPotpolja), "");
              else
                sf = new Subfield(subfields.substring(brPotpolja, brPotpolja + 1), "");
              newField.addSubfield(sf);
            }
            // dodamo polje u povratni vektor.
            retVal.insertElementAt(newField, counter);
            counter++;
          } else { // le veci od currField
            // dodajemo polje iz rezanca
            // dodamo polje u povratni vektor.
            retVal.addElement(currField);
            counter++;
            ubaci = false; // ne ubacuj ponovo u  listu Field-ova
            break;
          }
        } //for
        if (indexLE >= v.size() && (ubaci == true)) { // poslednji element dodajemo iz rezanca
          // dodamo polje u povratni vektor.
          retVal.addElement(currField);
          counter++;
        }
      } // for
      // Probamo da ubacimo ostatak polja polja iz okruzenja
      for ( ; indexLE < v.size(); indexLE++) {
        LESubfield le = (LESubfield) v.elementAt(indexLE);
        Field newField = new Field(le.getField(),
                             Environment.getFs().getDefFirstIndicator(le.getField()),
                             Environment.getFs().getDefSecondIndicator(le.getField()));
        String subfields =  Sorter.qsort(le.getSubfield(le.getField()));
        for(int brPotpolja = 0; brPotpolja < subfields.length(); brPotpolja++ ) {
          Subfield sf;
          if (brPotpolja == (subfields.length() -1))
            sf = new Subfield(subfields.substring(brPotpolja), "");
          else
            sf = new Subfield(subfields.substring(brPotpolja, brPotpolja + 1), "");
          newField.addSubfield(sf);
        }
        // dodamo newField u vektor newField-ova.
        retVal.insertElementAt(newField, counter);
        // Povecamo broj tekuceg stanja.
        counter++;
      } // for
    }
    return retVal;
  }

  public void setVisible(boolean v) {
    tfContents.setVisible(false);
    jScrollPane2.setVisible(false);
    showDescription();
    setRowSelectionInterval(0, 0);
    setColumnSelectionInterval(0, 0);
    updateStatLin(mf.updateStatLin());
    updateColumnWidths();
    super.setVisible(v);
  }

  public void updateStatLin(String s) {
    tfStatLin.setText(s);
  }

  public void displaySubfieldList() {
    Vector vres = new Vector();
    Vector vf = Environment.getLib().getTypeLESubfields();
    int i = 0;
    String prev = "";
    String sres = "";
    while (i < vf.size()) {
     LESubfield ls = (LESubfield)vf.elementAt(i);
     if (!ls.getField().equals(prev)){
         if (!sres.equals("")){
             vres.addElement(sres);
             sres = "";
         }
         sres = ls.getField();
         prev = ls.getField();
     }
     sres = sres.concat(ls.getSubfield(ls.getField()));
     i++;
    }
    vres = Sorter.sortVector(vres);
    listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_TITLEPUBTYPESUBFIELDS") + " " +Environment.getLib().getPubType());
    listDlg.setListData(vres);
    listDlg.setVisible(true);
  }


  /** Priprema sifarnik za listanje.
    @param s Sadrzaj polja za unos vrednost. Na osnovu ovog parametra se lista
             suzeni skup vrednosti sifara.
    @return  Vektor stringova kojim se napuni list-box
  */
  public Vector prepareCodes(String fName, String sfName, String s) {
    Vector retVal = new Vector();
    USubfield usf = Environment.getFs().getSubfield(fName, sfName);
    USubSubfield ussf = null;
    if (Environment.getFs().containsSubSubfields(fName, sfName))
      ussf = Environment.getFs().getSubSubfield(fName, sfName, ssfDlg.getSsfName());
    HashSet hs;
    Enumeration e;
    if (ussf == null) { // NISU POTPOTPOLJA
      switch (usf.getControlID()) {
        case 2: // interni sifarnik
          if (usf.getInternalCodes() != null) {
            e = usf.getInternalCodes().elements();
            while (e.hasMoreElements()) {
              UInternalCodes ic = (UInternalCodes)e.nextElement();
              if (s.equals(""))
                retVal.addElement(ic.getIcID().trim()+ " - " + ic.getName());
              else if (ic.getIcID().trim().indexOf(s) == 0)
                retVal.addElement(ic.getIcID().trim()+ " - " + ic.getName());
            }
            listDlg.setTitle("  " + com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_TITLEINTCODE") + " - "
                   + fName + "/" + sfName);
          } else
            JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_INTCODEMISSING"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          break;
        case 1: // eksterni sifarnik
          String tesID = usf.getTesID();
          HashMap externalCodes = usf.getExternalCodes();
          hs = (HashSet)externalCodes.get(tesID);
          if (hs != null) {
            Enumeration en = hs.elements();
            while (en.hasMoreElements()) {
              UExternalCodes ec = (UExternalCodes)en.nextElement();
              if (s.equals(""))
                retVal.addElement(ec.getEcID().trim()+ " - " + ec.getName());
              else if (ec.getEcID().trim().indexOf(s) == 0)
                retVal.addElement(ec.getEcID().trim()+ " - " + ec.getName());
             }
             listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_TITLEEXTCODE") + " - " + sfName);
          } else
            JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_EXTCODEMISSING"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          break;
        default:
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_CODEMISSING"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      }
    } else { // POTPOTPOLJA
      switch (ussf.getControlID()) {
        case 2: // interni sifarnik
          if (ussf.getSubfieldCodes() != null) {
            e = ussf.getSubfieldCodes().elements();
            while (e.hasMoreElements()) {
              USubfieldCodes sc = (USubfieldCodes)e.nextElement();
              retVal.addElement(sc.getSfcID().trim()+ " - " + sc.getName());
            }
            listDlg.setTitle(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_TITLEINTCODESSF") + " - "
                   + fName + "/" + sfName);//+"/"+ssf.getName());
          } else
            JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_INTSSFCODEMISSING"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          break;
           /*case 1: // eksterni sifarnik
              break;*/
        default:
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_SSFCODEMISSING"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      }
    }
    return Sorter.sortVector(retVal);
  }

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

  /** Popunjava sadrzaj sekundarnog zadatim stringom. */
  private boolean updateSecField(Field f, String content) {
    SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(f.getActiveSubfieldIndex());
    Subfield sf = f.getSubfieldByIndex(sfi.subIndex);
    if (sfi.index == -1) { // ako stojim na 421[1]
      try {
        if (content.equals(""))
          throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_EMPTYSECFIELD"));
        Environment.getValidator().isValid(f, sf, content);
      } catch (Exception ex) {
        String errorMessage = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, errorMessage, com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return false;
      }
      String fname = content.substring(0, 3);
      // -->
      // ako je korisnik pritisnuo F12 na 421[1] a nije menjao njegov prethodni
      // sadržaj tada nemoj praviti novo sekundarno polje, samo ostavi staro
      if (sf.getSecField() != null && sf.getSecField().getName().equals(fname)) {
        return true;
      }
      // <--
      String ind1 = " ";
      String ind2 = " ";
      try {
        ind1 = content.substring(3, 4);
        ind2 = content.substring(4, 5);
      } catch (Exception ex) {
      }
      Field secF = new Field(fname, ind1, ind2);
      sf.setSecField(secF);
      f.init();
    } else { // stojim na 421[a] (tj. na 200a unutar 4211)
      Field secF = sf.getSecField();
      Subfield secSf = secF.getSubfieldByIndex(sfi.index);
      try {
        Environment.getValidator().isValid(secF, secSf, content);
      } catch (Exception ex) {
        String errorMessage = ex.toString().substring(ex.toString().indexOf(':')+1);
        JOptionPane.showMessageDialog(null, errorMessage, com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return false;
      }
      secSf.setContent(conv.Unicode2Unimarc(content, true));
      f.init();
    }
    return true;
  }

  /** Popunjava sardzaj potpotpolja zadatim stringom. */
  private boolean updateSubsubfield(Field f, Subfield sf, String content) {
    boolean retVal = true;
    Subfield ssf = sf.getSubSubfieldByName(ssfDlg.getSsfName());
    try {
      Environment.getValidator().isValid(f, sf, ssf, content, true);
      ssf.setContent(conv.Unicode2Unimarc(content, true));
    } catch (Exception ex) {
      String errorMessage = ex.toString().substring(ex.toString().indexOf(':')+1);
      JOptionPane.showMessageDialog(null, errorMessage, com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      retVal = false;
    }
    return retVal;
  }

  /** Popunjava sadrzaj potpolja zadatim stringom. */
  public boolean updateSubfield(Field f, String content) {
    Subfield sf;
    if (Environment.getFs().containsSecondaryFields(f.getName()))
      return updateSecField(f, content);
    else if (Environment.getFs().containsSubSubfields(f.getName(), (sf=f.getSubfieldByIndex(f.getActiveSubfieldIndex())).getName() ))
      return updateSubsubfield(f, sf, content);
    boolean retVal = true;
    try {  // OBICNO POLJE
      sf = f.getSubfieldByIndex(f.getActiveSubfieldIndex());
      Environment.getValidator().isValid(f, sf, content);
      // provera da li se inventarni brojevi ponavljaju lokalno u okviru ovog zapisa
      if ((f.getName().equals("996") || f.getName().equals("997")) && sf.getName().equals("f")) {
        checkInvNumberDuplicatesInField(f);
        checkInvNumberDuplicatesInRecord(f, sf, conv.Unicode2Unimarc(content, true));
      }
      sf.setContent(conv.Unicode2Unimarc(content, true));
    } catch (Exception ex) {
      String errorMessage = ex.toString().substring(ex.toString().indexOf(':')+1);
      JOptionPane.showMessageDialog(null, errorMessage, com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      retVal = false;
    }
    return retVal;
  }
  
  private void checkInvNumberDuplicatesInField(Field f) throws Exception {
    Vector sfs = f.getSubfieldsByName("f");
    for (int i = 0; i < sfs.size(); i++) {
      Subfield sfi = (Subfield)sfs.elementAt(i);
      for (int j = 0; j < sfs.size(); j++) {
        if (i == j)
          continue;
        Subfield sfj = (Subfield)sfs.elementAt(j);
        if (sfi.getContent().equals(sfj.getContent()))
          throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_INVNUMREPEATED") + " " + sfi.getContent());
      }
    }
  }
  
  private void checkInvNumberDuplicatesInRecord(Field f, Subfield sf, String content) throws Exception {
    Vector fields = utm.getFields();
    for (int i = 0; i < fields.size(); i++) {
      Field fi = (Field)fields.elementAt(i);
      if (fi == f)
        continue;
      if (!(fi.getName().equals("996") || fi.getName().equals("997")))
        continue;

      Vector sfi = fi.getSubfieldsByName("f");
      for (int j = 0; j < sfi.size(); j++) {
        String contentj = ((Subfield)sfi.elementAt(j)).getContent();
        if (contentj.equals(content))
          throw new Exception(com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_INVNUMREPEATED") + " " + content);
      }
    }
  }

  private int row_, col_;

  public int getSelectedRow() {
    return row_;
  }

  public int getSelectedColumn() {
    return col_;
  }

  public void setRowSelectionInterval(int from, int to) {
    row_ = from;
    tEdit.setRowSelectionInterval(row_, row_);
  }

  public void setColumnSelectionInterval(int from, int to) {
    col_ = from;
    tEdit.setColumnSelectionInterval(col_, col_);
  }

  /** Brise tekuce selektovano polje iz strukture. */
  public void cutField() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      return;
    if (col == -1)
      return;
    if (!utm.isThereAFieldRight(row, col)) // ako smo obrisali poslednjeg, pomerimo se desno
      moveFieldLeft();
    Field f = (Field)utm.getValueAt(row, col);
    Environment.setUndoField(f);
    utm.getFields().removeElementAt(utm.getFieldIndex(row, col));
    updateColumnWidths();
    utm.fireTableDataChanged();
  }
  
  public void copyField() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      return;
    if (col == -1)
      return;
    Field f = (Field)utm.getValueAt(row, col);
    Environment.setUndoField(f);
    JOptionPane.showMessageDialog(null, "Polje " + f.getName() + " je kopirano!", "Kopiranje polja", JOptionPane.INFORMATION_MESSAGE);
  }

  /** Brise tekuce selektovano potpolje iz strukture, pri tom proverava da
      li je to moguce itd. */
  public void deleteSubfield() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      return;
    if (col == -1)
      return;
    Field f = (Field)utm.getValueAt(row, col);
    int index = f.getActiveSubfieldIndex();
    SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);

    // ako nema sekundarnih polja
    if (Environment.getFs().containsSecondaryFields(f.getName()) == false) {
      // brisemo samo ako postoji bar 2 potpolja
    	if (f.getSubfields().size() > 1) {
        Vector subfields = f.getSubfields();
        Subfield sf = (Subfield)subfields.elementAt(index);
        Environment.setUndoSubfield(sf);
        Environment.setUndoSubfieldOwner(f);
        Environment.setUndoSecField(null);
        if (index == subfields.size() - 1)
          f.setActiveSubfieldIndex(index-1);
        subfields.removeElementAt(index);
      } else {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_CANNOTDELETEREMAININGSUBFIELD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
      f.init();
      updateColumnWidths();
      utm.fireTableDataChanged();
    } else { // ako ima sekundarnih polja
      Vector subfields = f.getSubfields();
      // brise se 421[1] potpolje
      if (sfi.fieldName.equals(f.getName())) {
        // brisemo samo ako postoji bar 2 potpolja
      	if (f.getSubfields().size() > 1) {
          Subfield sf = (Subfield)subfields.elementAt(sfi.subIndex);
          Environment.setUndoSubfield(sf);
          Environment.setUndoSubfieldOwner(f);
          Environment.setUndoSecField(null);
          if (sfi.subIndex == f.getSubfields().size() - 1)
            f.setActiveSubfieldIndex(index - 1);
          subfields.removeElementAt(sfi.subIndex);
        } else {
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_CANNOTDELETEREMAININGSUBFIELD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        f.init();
        updateColumnWidths();
        utm.fireTableDataChanged();
      } else { // brise se 4211[a] potpolje
        Subfield sf = (Subfield)subfields.elementAt(sfi.subIndex);
        Field secF = sf.getSecField();
        Vector secSubfields = secF.getSubfields();
        Subfield secSf = (Subfield)secSubfields.elementAt(sfi.index);
        // brisemo samo ako postoji bar 2 potpolja u okviru sekundarnog polja
      	if (secSubfields.size() > 1) {
          Environment.setUndoSubfield(secSf);
          Environment.setUndoSubfieldOwner(f);
          Environment.setUndoSecField(secF);
          if (index == f.getSecFieldInfo().size() - 1)
            f.setActiveSubfieldIndex(index - 1);
          secSubfields.removeElementAt(sfi.index);
        } else {
          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_CANNOTDELETEREMAININGSUBFIELD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          return;
        }
        f.init();
        updateColumnWidths();
        utm.fireTableDataChanged();
      }
    }
  }

  /** Dodaje prethodno obrisano polje na tekuce mesto (u skladu sa modom za unos). */
  public void pasteField() {
    if (Environment.getUndoField() == null)
      return;
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      return;
    if (col == -1)
      return;
    Vector allowedFields = afDlg.getAllowedFields();
    boolean found = false;
    int n = allowedFields.size();
    for (int i = 0; i < n && !found; i++)
      if (Environment.getUndoField().getName().equals((String)allowedFields.elementAt(i)))
        found = true;
    if (found) {
      boolean clearInvNum = false;
      String fname = Environment.getUndoField().getName();
      if (fname.equals("996") || fname.equals("997"))
        clearInvNum = utm.hasField(fname);
      if (insertMode == ADD_MODE) {
        if (utm.isThereAFieldRight(row, col)) {
          if ((col == UnimarcTableModel.COL_COUNT-1) && (row < utm.getRowCount()-1)) {
            col = 0;
            row++;
          } else
            col++;
          utm.insertValueAt(new Field(Environment.getUndoField(), clearInvNum), row, col);
        } else {
          utm.addValue(new Field(Environment.getUndoField(), clearInvNum));
        }
      } else { // INSERT_MODE
        utm.insertValueAt(new Field(Environment.getUndoField(), clearInvNum), row, col);
      }
    } else
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_CANNOTINSERTFIELDHERE"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
  }

  public void pasteSubfield() {
    if (Environment.getUndoSubfield() == null)
      return;
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      return;
    if (col == -1)
      return;
    Field f = (Field)utm.getValueAt(row, col);
    if (f != Environment.getUndoSubfieldOwner()) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_SFDOESNTBELOGTOFIELD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    Field undoSecF = Environment.getUndoSecField();
    if (undoSecF != null) {
      int index = f.getActiveSubfieldIndex();
      SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
      Subfield sf = f.getSubfieldByIndex(sfi.subIndex);
      Field secF = sf.getSecField();
      if (secF != undoSecF) {
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_SFDOESNTBELOGTOSECFIELD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        return;
      }
    }
    Vector allowedSubfields = asfDlg.getAllowedSubfields(f);
    boolean found = false;
    int n = allowedSubfields.size();
    for (int i = 0; i < n && !found; i++)
      if (Environment.getUndoSubfield().getName().equals((String)allowedSubfields.elementAt(i)))
        found = true;
    if (found) {
      String sfname = f.getName() + Environment.getUndoSubfield().getName();
      Subfield newSubfield = null;
      System.out.println(sfname);
      System.out.println(utm.hasField(f.getName()));
      if ("996f".equals(sfname) || "997f".equals(sfname))
        newSubfield = new Subfield(Environment.getUndoSubfield(), "");
      else
        newSubfield = new Subfield(Environment.getUndoSubfield());
      if (insertMode == ADD_MODE)
        asfDlg.insertSubfieldAfter(f, null, newSubfield);
      else
        asfDlg.insertSubfield(f, null, newSubfield);
    } else {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_CANNOTADDSUBFIELD"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    updateColumnWidths();
    utm.fireTableDataChanged();
  }

  /** Ispisuje opis tekuceg polja i potpolja. */
  public void showDescription() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      row = 0;
    if (col == -1)
      col = 0;
    Field f = (Field)utm.getValueAt(row, col);
    String name = f.getName();
    String ind1 = f.getInd1();
    String ind2 = f.getInd2();
    String desc1 = Environment.getFs().getFieldName(name);
    lStatLin1.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELIND1") + " " +ind1+
                      "   " + com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELIND2") + " "+ind2+
                      "  " + com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELFIELDNAME") + " "+desc1);
    // sada potpolja
    String desc2;
    int index = f.getActiveSubfieldIndex();
    if (Environment.getFs().containsSecondaryFields(f.getName()) == true) { // sekundarna polja
      String fname = f.getName();
      SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
      desc2 = Environment.getFs().getSubfieldName(sfi.fieldName, sfi.subfieldID);
    } else {
      Subfield sf = (Subfield)f.getSubfields().elementAt(index);
      String sfname = sf.getName();
      desc2 = Environment.getFs().getSubfieldName(name, sfname);
    }
    lStatLin2.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_LABELSTATLIN2") + " "+desc2);
    setScrollers(row, col, f);
  }

  public void setScrollers(int row, int col, Field f) {
    JScrollBar scrollbar = jScrollPane1.getVerticalScrollBar();
    float min = scrollbar.getMinimum();
    float max = scrollbar.getMaximum();
    float ext = scrollbar.getModel().getExtent();
    float val = (max - min) * ((float)row / (float)utm.getRowCount()) /* - ext / 2 */;
    if (val < min)
      val = min;
    else if (val > max - ext)
      val = max - ext;
    scrollbar.setValue((int)val);

    scrollbar = jScrollPane1.getHorizontalScrollBar();
    min = scrollbar.getMinimum();
    max = scrollbar.getMaximum();
    ext = scrollbar.getModel().getExtent();
    val = (max - min) * ((float)col / (float)utm.getColumnCount()) /* - ext / 2 */;
    if (val < min)
      val = min;
    else if (val > max - ext)
      val = max - ext;
    scrollbar.setValue((int)val);

    utm.fireTableDataChanged();
  }

  public void moveFieldRight() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if ((col < UnimarcTableModel.COL_COUNT-1) && utm.isThereAFieldRight(row, col)) {
      col++;
      setColumnSelectionInterval(col, col);
      showDescription();
    } else if ((col == UnimarcTableModel.COL_COUNT-1) && (row < utm.getRowCount()-1)) {
      col = 0;
      row++;
      setColumnSelectionInterval(col, col);
      setRowSelectionInterval(row, row);
      showDescription();
    }
  }

  public void moveFieldUp() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row > 0) {
      row--;
      setRowSelectionInterval(row, row);
      showDescription();
    }
  }

  public void moveFieldDown() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if ((row < utm.getRowCount()-1) && utm.isThereAFieldBelow(row, col)) {
      row++;
      setRowSelectionInterval(row, row);
      showDescription();
    }
  }

  public void moveFieldLeft() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (col > 0) {
      col--;
      setColumnSelectionInterval(col, col);
      showDescription();
    } else if ((col == 0) && (row > 0)) {
      row--;
      col = UnimarcTableModel.COL_COUNT-1;
      setColumnSelectionInterval(col, col);
      setRowSelectionInterval(row, row);
      showDescription();
    }
  }


  public void moveSubfieldLeft() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    Field f = (Field)utm.getValueAt(row, col);
    int index = f.getActiveSubfieldIndex();
    if (index > 0) {
      f.setActiveSubfieldIndex(index-1);
      tEdit.repaint();
      showDescription();
    }
  }

  public void moveSubfieldRight() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    Field f = (Field)utm.getValueAt(row, col);
    int index = f.getActiveSubfieldIndex();
    int size = f.getSecFieldInfo().size();
    if (index < size-1) {
      f.setActiveSubfieldIndex(index+1);
      tEdit.repaint();
      showDescription();
    }
  }

  void tEdit_keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        tEdit_handleEnter();
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_HOME:
      case KeyEvent.VK_END:
      case KeyEvent.VK_PAGE_DOWN:
      case KeyEvent.VK_PAGE_UP:
        e.consume();
        break;
    }
  }

  public void tEdit_handleEnter() {
    int row = getSelectedRow();
    int col = getSelectedColumn();
    if (row == -1)
      row = 0;
    if (col == -1)
      col = 0;
    Field f = (Field)utm.getValueAt(row, col);
    int index = f.getActiveSubfieldIndex();
    Subfield sf;
    String s;
    String fieldName;
    String subfieldName;
    if (Environment.getFs().containsSecondaryFields(f.getName())) {
      SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
      if (sfi.index == -1) { // stojimo na 421[1]
        sf = (Subfield)f.getSubfields().elementAt(sfi.subIndex);
        fieldName = f.getName();
        subfieldName = sf.getName();
        Field secF = sf.getSecField();
        if (secF != null)
          s = secF.getName()+secF.getInd1()+secF.getInd2();
        else
          s = "";
      } else { // stojimo na 4211[a]
        sf = (Subfield)f.getSubfields().elementAt(sfi.subIndex);
        Field secF = sf.getSecField();
        sf = (Subfield)secF.getSubfields().elementAt(sfi.index);
        fieldName = secF.getName();
        subfieldName = sf.getName();
        s = sf.getContent();
      }
      s = conv.Unimarc2Unicode(s);
      if (Environment.getFs().getSubfieldLength(fieldName, subfieldName) == 0) {
        setMultiLine(true);
        taContents.setRows(10);
        taContents.setText(s);
        taContents.requestFocus();
      } else {
        setMultiLine(false);
        tfContents.setText(s);
        tfContents.requestFocus();
      }
    } else if (Environment.getFs().containsSubSubfields(f.getName(), (sf=f.getSubfieldByIndex(index)).getName() )) {
      ssfDlg.setData(f, sf);
      ssfDlg.setVisible(true);
    } else {
      sf = (Subfield)f.getSubfields().elementAt(index);
      s = sf.getContent();
      s = conv.Unimarc2Unicode(s);
      fieldName = f.getName();
      subfieldName = sf.getName();
      // AJOJ
      // AJOJ
      /*String desc = f.getName()+f.getSubfieldByIndex(index).getName();*/
      //s = Environment.getValidator().preAdjust(desc, f, s, utm.getFields());
      if (Environment.getFs().getSubfieldLength(fieldName, subfieldName) == 0) {
        setMultiLine(true);
        taContents.setRows(10);
        taContents.setText(s);
        taContents.requestFocus();
        taContents.setEditable(!"001e".equals(fieldName+subfieldName));
      } else {
        setMultiLine(false);
        tfContents.setText(s);
        tfContents.requestFocus();
        tfContents.setEditable(!"001e".equals(fieldName+subfieldName));
      }
    }
  }

  void tEdit_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        //tEdit_handleEnter();
        break;
      case KeyEvent.VK_ESCAPE:
        JLabel msg = new JLabel(com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_WANNASAVEBEFOREEXIT"));
        msg.setForeground(Color.black);
        String defBtn = "    " + com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_EXITBUTTONYES") + "    ";
        String[] buttons = {defBtn, "    " + com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_EXITBUTTONNO") + "    ", " " + com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_EXITBUTTONCANCEL") + " "};
        switch (JOptionPane.showOptionDialog(
              null,
              msg,
              com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_EXITRECORD"),
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.INFORMATION_MESSAGE,
              null,
              buttons,
              defBtn)) {
          case JOptionPane.YES_OPTION:
            SaveCommand sc = new SaveCommand(this);
            sc.execute();
            if (sc.fieldsOK)
              setVisible(false);
            break;
          case JOptionPane.NO_OPTION:
            setVisible(false);
            break;
          case JOptionPane.CANCEL_OPTION:
            break;
        }
        break;
      case KeyEvent.VK_LEFT:
        if (e.getModifiers() == KeyEvent.SHIFT_MASK)
          moveSubfieldLeft();
        else
          moveFieldLeft();
        break;
      case KeyEvent.VK_NUMPAD1:
        moveSubfieldLeft();
        break;
      case KeyEvent.VK_RIGHT:
        if (e.getModifiers() == KeyEvent.SHIFT_MASK)
          moveSubfieldRight();
        else
          moveFieldRight();
        break;
      case KeyEvent.VK_NUMPAD3:
        moveSubfieldRight();
        break;
      case KeyEvent.VK_UP:
        moveFieldUp();
        break;
      case KeyEvent.VK_DOWN:
        moveFieldDown();
        break;
      case KeyEvent.VK_NUMPAD7:
        // dodavanje polja
        afDlg.setVisible(true);
        break;
      case KeyEvent.VK_NUMPAD4:
        // dodavanje potpolja
        asfDlg.setData(getCurrField());
        asfDlg.setVisible(true);
        break;
      case KeyEvent.VK_NUMPAD2:
        // indikatori
        indDlg.setVisible(true);
        break;
      case KeyEvent.VK_INSERT:
        if (insertMode == ADD_MODE)
          insertMode = INSERT_MODE;
        else
          insertMode = ADD_MODE;
        tfStatLin.setText(mf.updateStatLin());
        break;
      case KeyEvent.VK_F12:
        mf.pubTypeDlg.setData(Environment.getLib().getPubType(), false);
        mf.pubTypeDlg.setVisible(true);
        break;
      case KeyEvent.VK_F9:
        displaySubfieldList();
        break;
      case KeyEvent.VK_F6:
        tfComLin.requestFocus();
        break;
      case KeyEvent.VK_NUMPAD9:
        if (e.getModifiers() == KeyEvent.CTRL_MASK)
          pasteField();
        else
          cutField();
        break;
      case KeyEvent.VK_NUMPAD6:
          copyField();
        break;
      case KeyEvent.VK_NUMPAD5:
        if (e.getModifiers() == KeyEvent.CTRL_MASK)
          pasteSubfield();
        else
          deleteSubfield();
    }
  }
/*
  private boolean dirty;

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }
*/
  void taContents_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_F1:
        handleLookup(taContents);
        break;
      case KeyEvent.VK_F12:
//        setDirty(true);
        // update contents
        int row = getSelectedRow();
        int col = getSelectedColumn();
        Field f = (Field)utm.getValueAt(row, col);
        int index = f.getActiveSubfieldIndex();
        // AJOJ
        // AJOJ
        /*String desc = f.getName()+f.getSubfieldByIndex(index).getName();
        if (Environment.getFs().containsSubSubfields(f.getName(), f.getSubfieldByIndex(index).getName()))
          desc += ssfDlg.getSsfName();*/
        //taContents.setText(Environment.getValidator().postAdjust(desc, f, taContents.getText(), utm.getFields()));
        if (updateSubfield(f, taContents.getText().trim().replace('\t', ' '))) {
          if (cameFromSubsubfieldList) {
            Subfield sf = (Subfield)f.getSubfields().elementAt(index);
            ssfDlg.setData(f, sf);
            ssfDlg.setVisible(true);
          } else {
            taContents.setText("");
            jScrollPane2.setVisible(false);
            moveSubfieldRight();
            tEdit.requestFocus();
            this.repaint();
          }
        }
        break;
      case KeyEvent.VK_ESCAPE:
        // revert to old
        if (cameFromSubsubfieldList) {
          ssfDlg.setVisible(true);
        } else {
          taContents.setText("");
          jScrollPane2.setVisible(false);
          tEdit.requestFocus();
          this.repaint();
        }
        break;
      case KeyEvent.VK_F9:
        // sifarnik
        f = getCurrField();
        index = f.getActiveSubfieldIndex();
        Subfield sf;
        if (Environment.getFs().containsSecondaryFields(f.getName())) {
          SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
          sf = (Subfield)f.getSubfields().elementAt(sfi.subIndex);
        } else
          sf = (Subfield)f.getSubfields().elementAt(index);
        String fName = f.getName();
        String sfName = sf.getName();
        Vector listData = prepareCodes(fName, sfName, taContents.getText().trim());
        if (listData.size() > 0) {
          listDlg.setListData(listData, taContents.getText());
          listDlg.setVisible(true);
          String s = listDlg.getSelection();
          if (!s.equals(""))
            taContents.setText(s.substring(0, s.indexOf(" -")));
        }
        break;
      case KeyEvent.VK_F5:
        f = getCurrField();
	      index = f.getActiveSubfieldIndex();
	      if (Environment.getFs().containsSecondaryFields(f.getName())) {
	        SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
	        sf = (Subfield)f.getSubfields().elementAt(sfi.subIndex);
	      } else
	        sf = (Subfield)f.getSubfields().elementAt(index);
        fName = f.getName();
        sfName = sf.getName();
        if ((fName.equals("996") || fName.equals("997")) && sfName.equals("f")) {
          String content = tfContents.getText().trim();
          try {
            Integer.parseInt(content);
            if (content.length() != 4)
              throw new Exception("Neispravan ogranak!");
            int number = Environment.getWriteTs().getNewNumber(content);
            if (number == -1)
              throw new Exception("Neispravan ogranak!");
            String padded = StringUtils.padZeros(number, 7);
            tfContents.setText(content + padded);
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Neispravan broj ogranka!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
          }
        }
	      break;
      case KeyEvent.VK_F8:
        Date today = new Date();
        String cnt = taContents.getText().trim() + sdf.format(today);
        taContents.setText(cnt);
        taContents.setCaretPosition(cnt.length());
        break;
      case KeyEvent.VK_F4:
        if (RegistryUtils.registriesAvailable()) {
          registryDlg.setVisible(true);
          if (registryDlg.getValue() != null) {
            if (taContents.getText().equals(""))
              taContents.setText(registryDlg.getValue());
            else
              taContents.setText(taContents.getText() + " " + registryDlg.getValue());
          }
        } else
          JOptionPane.showMessageDialog(null, "Registri nisu instalirani!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
        break;
    }
  }

  void tfContents_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_F1:
        handleLookup(tfContents);
        break;
      case KeyEvent.VK_F12:
//        setDirty(true);
        // update contents
        int row = getSelectedRow();
        int col = getSelectedColumn();
        Field f = (Field)utm.getValueAt(row, col);
        int index = f.getActiveSubfieldIndex();
        // AJOJ
        // AJOJ
        /*String desc = f.getName()+f.getSubfieldByIndex(index).getName();
        if (Environment.getFs().containsSubSubfields(f.getName(), f.getSubfieldByIndex(index).getName()))
          desc += ssfDlg.getSsfName();*/
        //tfContents.setText(Environment.getValidator().postAdjust(desc, f, tfContents.getText(), utm.getFields()));
        if (updateSubfield(f, tfContents.getText().trim().replace('\t', ' '))) {
          if (cameFromSubsubfieldList) {
            Subfield sf = (Subfield)f.getSubfields().elementAt(index);
            ssfDlg.setData(f, sf);
            ssfDlg.setVisible(true);
          } else {
            tfContents.setText("");
            tfContents.setVisible(false);
            moveSubfieldRight();
            tEdit.requestFocus();
            this.repaint();
          }
        }
        break;
      case KeyEvent.VK_ESCAPE:
        // revert to old
        if (cameFromSubsubfieldList) {
          ssfDlg.setVisible(true);
        } else {
          tfContents.setText("");
          tfContents.setVisible(false);
          tEdit.requestFocus();
          this.repaint();
        }
        break;
      case KeyEvent.VK_F9:
        // sifarnik
        f = getCurrField();
        index = f.getActiveSubfieldIndex();
        Subfield sf;
        if (Environment.getFs().containsSecondaryFields(f.getName())) {
          SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
          sf = (Subfield)f.getSubfields().elementAt(sfi.subIndex);
        } else
          sf = (Subfield)f.getSubfields().elementAt(index);
        String fName = f.getName();
        String sfName = sf.getName();
        Vector listData = prepareCodes(fName, sfName, tfContents.getText().trim());
        if (listData.size() > 0) {
          listDlg.setListData(listData, tfContents.getText());
          listDlg.setVisible(true);
          String s = listDlg.getSelection();
          if (!s.equals(""))
            tfContents.setText(s.substring(0, s.indexOf(" -")));
        }
        break;
      case KeyEvent.VK_F5:
        f = getCurrField();
	      index = f.getActiveSubfieldIndex();
	      if (Environment.getFs().containsSecondaryFields(f.getName())) {
	        SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(index);
	        sf = (Subfield)f.getSubfields().elementAt(sfi.subIndex);
	      } else
	        sf = (Subfield)f.getSubfields().elementAt(index);
        fName = f.getName();
        sfName = sf.getName();
        if ((fName.equals("996") || fName.equals("997")) && sfName.equals("f")) {
          String content = tfContents.getText().trim();
          try {
            Integer.parseInt(content);
            if (content.length() != 4)
              throw new Exception("Morate uneti prve \u010detiri cifre!");
            int number = Environment.getWriteTs().getNewNumber(content);
            if (number == -1)
              throw new Exception("Neispravan ogranak!");
            String padded = StringUtils.padZeros(number, 7);
            tfContents.setText(content + padded);
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Neispravan broj ogranka!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
          }
        }
	      break;
      case KeyEvent.VK_F8:
        Date today = new Date();
        String cnt = tfContents.getText().trim() + sdf.format(today);
        tfContents.setText(cnt);
        tfContents.setCaretPosition(cnt.length());
        break;
      case KeyEvent.VK_F4:
        if (RegistryUtils.registriesAvailable()) {
          registryDlg.setVisible(true);
          if (registryDlg.getValue() != null) {
            if (tfContents.getText().equals(""))
              tfContents.setText(registryDlg.getValue());
            else
              tfContents.setText(tfContents.getText() + " " + registryDlg.getValue());
          }
        } else
          JOptionPane.showMessageDialog(null, "Registri nisu instalirani!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
        break;
    }
  }

  void tfComLin_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      /*
      case KeyEvent.VK_ENTER:
        AbstractCommand comm = commParser.parse(tfComLin.getText().trim());
        if (comm != null)
          comm.execute();
        else
          JOptionPane.showMessageDialog(mf, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_UNKNOWNCOMMAND"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
        break;
      */
      case KeyEvent.VK_ESCAPE:
        tEdit.requestFocus();
        break;
      case KeyEvent.VK_F12:
        mf.pubTypeDlg.setData(Environment.getLib().getPubType(), false);
        mf.pubTypeDlg.setVisible(true);
        break;
      default:
        break;
    }
  }

  void tfComLin_actionPerformed(ActionEvent e) {
	AbstractCommand comm = commParser.parse(tfComLin.getText().trim());
	if (comm != null)
	  comm.execute();
	else
	  JOptionPane.showMessageDialog(mf, com.gint.app.bisis.editor.Messages.get("BISISAPP_EDITDLG_UNKNOWNCOMMAND"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
  }

  void tEdit_mouseReleased(MouseEvent e) {
    row_ = tEdit.getSelectedRow();
    col_ = tEdit.getSelectedColumn();
    showDescription();
  }

}




