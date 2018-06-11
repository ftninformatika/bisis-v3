package com.gint.app.bisis.editor.edit;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import com.gint.util.sort.Sorter;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;

public class AddSubfieldDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lTitle = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lField = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList liSubfield = new JList();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();

  public AddSubfieldDlg(EditDlg frame, String title, boolean modal) {
    super(frame, title, modal);
    this.editDlg = frame;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public AddSubfieldDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lTitle.setText(Messages.get("BISISAPP_ADDSUBFIELDDLG_LABELADDINSERTSUBFIELD"));
    lField.setText(Messages.get("BISISAPP_ADDSUBFIELDDLG_LABELFIELD"));
    lField.setHorizontalAlignment(SwingConstants.CENTER);
    xYLayout1.setHeight(300);
    xYLayout1.setWidth(325);
    bOK.setText(Messages.get("BISISAPP_ADDSUBFIELDDLG_BUTTONOK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(Messages.get("BISISAPP_ADDSUBFIELDDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    liSubfield.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        liSubfield_keyReleased(e);
      }
    });
    liSubfield.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        liSubfield_mouseClicked(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(lTitle, new XYConstraints(0, 9, 325, -1));
    panel1.add(lField, new XYConstraints(0, 57, 324, -1));
    panel1.add(jScrollPane1, new XYConstraints(17, 88, 284, 149));
    panel1.add(bOK, new XYConstraints(44, 256, 87, -1));
    panel1.add(bCancel, new XYConstraints(174, 256, 87, -1));
    jScrollPane1.getViewport().add(liSubfield, null);
    setSize(325, 300);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    getRootPane().setDefaultButton(bOK);
  }

  private EditDlg editDlg;

  /** Vraca spisak dopustivih potpolja za dato polje. */
  public Vector getAllowedSubfields(Field f) {
    Vector data = null;
    String pubType = Environment.getLib().getPubType();
    String fName = f.getName();
    if (Environment.getFs().containsSecondaryFields(fName) == true) {
      SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(f.getActiveSubfieldIndex());
      Subfield sf = f.getSubfieldByIndex(sfi.subIndex);
      if (sfi.index == -1) { // ako stojim na 421[1]
        // ako je tip tekuce publikacije S1, polje 421/423/469 ne moze da
        // sadrzi sekundarna polja a inace moze
        if (!pubType.equals("S1"))
          data = listSubfields(f, true);
        else
          data = listSubfields(f, false);
        if (editDlg.insertMode == EditDlg.ADD_MODE) {
          // listam samo potpolja vezana za sekundarno polje
          if (sf.getSecFieldInVect().size() > 0) {
            data = listSubfields((Field)sf.getSecField(), false);
          }
        }
     } else { // ako stojim na 4211[a]
        // onda listam samo potpolja vezana za sekundarno polje
        if (sf.getSecFieldInVect().size() > 0) {
           data = listSubfields((Field)sf.getSecField(), false);
        } // if
      }
    } else
    	 data = listSubfields(f, false);
    return data;
  }

  private Vector listSubfields(Field field, boolean primarno) {
    Vector data = new Vector();
    Vector v = Environment.getLib().getTypeLESubfields();
    int i = 0;
    while (i < v.size()) {
      LESubfield ls = (LESubfield)v.elementAt(i);
      // ako tekuce polje postoji u spisku dozvoljenih polja
      if (ls.getField().equals(field.getName())) {
        // Ako dozvoljeno potpolje vec postoji u tekucem polju...
        if (primarno) {
          data.addElement("1");
          break;
        } else {
          if (field.indexOf(ls.getSubfield(field.getName())) != -1) {
            // ako je ponovljivo
            if (Environment.getFs().getSubfield(field.getName(), ls.getSubfield(field.getName())).getRepeatable())
              data.addElement(ls.getSubfield(field.getName()));
          } else // ako ne postoji, dodacemo ga
            data.addElement(ls.getSubfield(field.getName()));
        }
      }
      i++;
    }
    return data;
  }

  /** Popunjava list-box spiskom potpolja koja se mogu dodati za zadato polje. */
  public void setData(Field f) {
    liSubfield.setListData(getAllowedSubfields(f));
    lField.setText(Messages.get("BISISAPP_ADDSUBFIELDDLG_LABELFIELD") + " " + f.getName());
    liSubfield.setSelectedIndex(0);
  }

  public void setVisible(boolean visible) {
    if (visible)
      liSubfield.requestFocus();
    super.setVisible(visible);
  }

  /** Ubacuje potpolje na trenutnim koordinatama.
   Napomena: Kod sekundarnih polja ubacivanje potpolja zavisi od moda ubacivanja:
   <pre>
    Legenda:
    <ul>
    <li> 1 - primarno potpolje koje sadrzi sekundarno polje
    <li> a - sekundarno potpolje (potpolje sekundarnog polja)

//          |INSERT | ADD |
//      ----+-------+-----+---
//        1 |  1    | 1/a |
//      ----+-------+-----+---
//        a |  a    |  a  |
//      ----+-------+-----+---
//          |       |     |
//
   </pre>
   @param f Polje u koje se ubacuje potpolje.
   @param s String koji sadrzi ID potpolja koje treba ubaciti.
   @param suBfieLd Ako je null, dodaje se novo prazno potpolje. Ako nije,
          ubacuje se to sto je dato.
  */
  void insertSubfield(Field f, String s, Subfield suBfieLd) {
    if (!Environment.getFs().containsSecondaryFields(f.getName())) {
      if (suBfieLd == null) {
        Subfield sf = new Subfield(s, "");
        f.insertSubfieldAt(sf, f.getActiveSubfieldIndex());
      } else {
        f.insertSubfieldAt(suBfieLd, f.getActiveSubfieldIndex());
      }
      f.init();
    } else {
      SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(f.getActiveSubfieldIndex());
      if (sfi.fieldName.equals(f.getName())) {
        // stojim na 421[1]
        if (suBfieLd == null) {
          Subfield sf = new Subfield(s, "");
          f.insertSubfieldAt(sf, sfi.subIndex);
        } else {
          f.insertSubfieldAt(suBfieLd, sfi.subIndex);
        }
        f.init();
      } else {
        // stojim na 4211[a]
        Subfield sf = f.getSubfieldByIndex(sfi.subIndex);
        Field secF = sf.getSecField();
        if (suBfieLd == null) {
          Subfield secSf = new Subfield(s, "");
          secF.insertSubfieldAt(secSf, sfi.index);
        } else {
          secF.insertSubfieldAt(suBfieLd, sfi.index);
        }
        f.init();
    	}
    }
  }

  /** Ubacuje potpolje desno od trenutnog potpolja
   Napomena: Kod sekundarnih polja ubacivanje potpolja zavisi od moda ubacivanja:
   <pre>
    Legenda:
    <ul>
    <li> 1 - primarno potpolje koje sadrzi sekundarno polje
    <li> a - sekundarno potpolje (potpolje sekundarnog polja)

//          |INSERT | ADD |
//      ----+-------+-----+---
//        1 |  1    | 1/a |
//      ----+-------+-----+---
//        a |  a    |  a  |
//      ----+-------+-----+---
//          |       |     |
//
   </pre>
   @param f Polje u koje se ubacuje potpolje.
   @param s String koji sadrzi ID potpolja koje treba ubaciti.
   @param suBfieLd Ako je null, dodaje se novo prazno potpolje. Ako nije,
          ubacuje se to sto je dato.
  */
  void insertSubfieldAfter(Field f, String s, Subfield suBfieLd) {
    if (!Environment.getFs().containsSecondaryFields(f.getName())) {
      Subfield sf = new Subfield(s, "");
      int index = f.getActiveSubfieldIndex();
      if (suBfieLd == null) {
        if( index < (f.getSubfields().size() - 1))
          f.insertSubfieldAt(sf, index + 1);
        else
          f.addSubfield(sf);
      } else {
        if( index < (f.getSubfields().size() - 1))
          f.insertSubfieldAt(suBfieLd, index + 1);
        else
          f.addSubfield(suBfieLd);
      }
      f.init();
    } else {
      SecFieldInfo sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(f.getActiveSubfieldIndex());
      if(sfi.fieldName.equals(f.getName())) {
        // stojim na 421[1]
        if (s == null) // %%%%%%%%%%%%% radimo paste (seljacenje) %%%%%%%%%%%%%%%%%
          s = suBfieLd.getName();
        if (s.equals(f.getSubfieldByIndex(0).getName())) { // posebno hendlam prvu 1
          // stojim na 1 i
          // dodajem 1 (potpolje od 421) posle svih potpolja
          if (suBfieLd == null) {
            Subfield sf = new Subfield(s, "");
            f.addSubfield(sf);
          } else
            f.addSubfield(suBfieLd);
          f.init();
        } else {
          // stojim na 421[1] i
          // dodajem a (potpolje sekundarnog polja 100)
          Subfield sf = f.getSubfieldByIndex(sfi.subIndex);
          Field secF;
          if(sf.getSecFieldInVect().size() != 0) // ako *imam* sekundarno polje
            secF = sf.getSecField();
          else
            return;
          Subfield secSf = new Subfield(s, "");
          if(secF.getSubfields().size() != 0) {
            if(sfi.index != -1) { // stojim na potpolju a
              if (suBfieLd == null) {
                if( sfi.index < (secF.getSubfields().size() - 1))
                  secF.insertSubfieldAt(secSf, sfi.index);
                else
                  secF.addSubfield(secSf);
              } else {
                if( sfi.index < (secF.getSubfields().size() - 1))
                  secF.insertSubfieldAt(suBfieLd, sfi.index);
                else
                  secF.addSubfield(suBfieLd);
              }
            } else { // stojim na potpolju 1
              sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(f.getActiveSubfieldIndex() + 1);
              if (suBfieLd == null)
                secF.insertSubfieldAt(secSf, sfi.index);
              else
                secF.insertSubfieldAt(suBfieLd, sfi.index);
            }
          } else {
            if (suBfieLd == null)
              secF.addSubfield(secSf);
            else
              secF.addSubfield(suBfieLd);
          }
          f.init();
        }
      } else {
        // stojim na 4211[a] i mogu da dodajem samo a, b, itd.
        Subfield sf = f.getSubfieldByIndex(sfi.subIndex);
        Field secF;
        if(sf.getSecFieldInVect().size() != 0)
          secF = sf.getSecField();
        else
          return;
        Subfield secSf = new Subfield(s, "");
        if(secF.getSubfields().size() != 0) {
          if(sfi.index != -1) {
            if (suBfieLd == null) {
              if( sfi.index < (secF.getSubfields().size() - 1))
                secF.insertSubfieldAt(secSf, sfi.index);
              else
                secF.addSubfield(secSf);
            } else {
              if( sfi.index < (secF.getSubfields().size() - 1))
                secF.insertSubfieldAt(suBfieLd, sfi.index);
              else
                secF.addSubfield(suBfieLd);
            }
          } else {
            sfi = (SecFieldInfo)f.getSecFieldInfo().elementAt(f.getActiveSubfieldIndex() + 1);
            if (suBfieLd == null) {
              secF.insertSubfieldAt(secSf, sfi.index);
            } else {
              secF.insertSubfieldAt(suBfieLd, sfi.index);
            }
          }
        } else {
          if (suBfieLd == null) {
            secF.addSubfield(secSf);
          } else {
            secF.addSubfield(suBfieLd);
          }
        }
        f.init();
      }
    }
  }

  void bOK_actionPerformed(ActionEvent e) {
    Field  f = editDlg.getCurrField();
    Object[] s = liSubfield.getSelectedValues();
    String[] s1 = new String[s.length];
    for (int i = 0; i < s.length; i++)
      s1[i] = (String)s[i];
    Sorter.qsort(s1);
    for (int i = s.length - 1; i >= 0; i--)
      if (editDlg.insertMode == EditDlg.ADD_MODE)
        insertSubfieldAfter(f, s1[i], null);
      else
        insertSubfield(f, s1[i], null);
    editDlg.updateColumnWidths();
    editDlg.utm.fireTableDataChanged();
    setVisible(false);
  }

  void bCancel_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void liSubfield_keyReleased(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        bOK.doClick();
      break;
      case KeyEvent.VK_ESCAPE:
        bCancel.doClick();
      break;
    }
  }

  void liSubfield_mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2)
      bOK.doClick();
  }
}

