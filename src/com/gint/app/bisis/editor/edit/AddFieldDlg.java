package com.gint.app.bisis.editor.edit;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import com.borland.jbcl.layout.*;

import com.gint.util.sort.Sorter;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;

public class AddFieldDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lTitle = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lPreview = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  JLabel lField = new JLabel();
  JLabel lSubfield = new JLabel();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  JButton bAdd = new JButton();
  JButton bRemove = new JButton();
  JList liField = new JList();
  JList liSubfield = new JList();
  EditDlg ed;

  public AddFieldDlg(EditDlg frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    ed = (EditDlg)frame;
  }

  public AddFieldDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lTitle.setText(Messages.get("BISISAPP_ADDFIELDDLG_LABELADDINSERTFIELD"));
    lPreview.setHorizontalAlignment(SwingConstants.CENTER);
    lPreview.setText(Messages.get("BISISAPP_ADDFIELDDLG_LABELFIELD"));
    lField.setText(Messages.get("BISISAPP_ADDFIELDDLG_LABELFIELD"));
    lSubfield.setText(Messages.get("BISISAPP_ADDFIELDDLG_LABELSUBFIELD"));
    bOK.setText(Messages.get("BISISAPP_ADDFIELDDLG_BUTTONOK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(Messages.get("BISISAPP_ADDFIELDDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bAdd.setText(Messages.get("BISISAPP_ADDFIELDDLG_BUTTONADD"));
    bAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bAdd_actionPerformed(e);
      }
    });
    bRemove.setText(Messages.get("BISISAPP_ADDFIELDDLG_BUTTONREMOVE"));
    bRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bRemove_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(429);
    xYLayout1.setWidth(400);
    liField.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        liField_valueChanged(e);
      }
    });
    liField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        liField_keyReleased(e);
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
    panel1.add(lSubfield, new XYConstraints(13, 246, 62, -1));
    panel1.add(lPreview, new XYConstraints(-1, 47, 400, -1));
    panel1.add(lTitle, new XYConstraints(0, 11, 400, -1));
    panel1.add(lField, new XYConstraints(13, 92, 47, -1));
    panel1.add(jScrollPane1, new XYConstraints(13, 113, 375, 113));
    jScrollPane1.getViewport().add(liField, null);
    panel1.add(bAdd, new XYConstraints(211, 390, 86, -1));
    panel1.add(bRemove, new XYConstraints(304, 390, 86, -1));
    panel1.add(bOK, new XYConstraints(13, 390, 82, -1));
    panel1.add(bCancel, new XYConstraints(102, 390, 86, -1));
    panel1.add(jScrollPane2, new XYConstraints(13, 268, 376, 113));
    jScrollPane2.getViewport().add(liSubfield, null);
    setSize(700, 500);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    getRootPane().setDefaultButton(bOK);
  }

  /** Popunjava list box-ove potpoljima za dodavanje */
  void showSubfields() {
    String field = (String)liField.getSelectedValue();
    liSubfield.removeAll();
    Vector v = Environment.getLib().getTypeLESubfields();
    int i = 0;
    Vector data = new Vector();
    while (i < v.size()) {
      LESubfield ls = (LESubfield) v.elementAt(i);
      if (ls.equals(field)) {
        // ako se radi o polju koje sadrzi sekundarna polja, a tip publikacije nije S1 treba dodati samo potpolje 1
        if (Environment.getFs().containsSecondaryFields(field) == true && !Environment.getLib().getPubType().equals("S1")) {
           if (!lPreview.getText().equals("") && lPreview.getText().substring(3).lastIndexOf("1") != -1) {
              if (Environment.getFs().getSubfield(field,"1").getRepeatable()) {
                 data.addElement("1");
              }
           }
           else
              data.addElement("1");
           break;
        }
        else { // obicna polja
           if (!lPreview.getText().equals("") && lPreview.getText().substring(3).lastIndexOf(ls.getSubfield(field)) != -1) {
             if (Environment.getFs().getSubfield(field,ls.getSubfield(field)).getRepeatable()) {
                data.addElement(ls.getSubfield(field));
             }
           }
           else {
              data.addElement(ls.getSubfield(field));
           }
        }
      }
      i++;
    }
    liSubfield.setListData(data);
    if (data.size() > 0)
      liSubfield.setSelectedIndex(0);
  }

  /** Redefinise setVisible da odradi jos posla */
  public void setVisible(boolean vis) {
    if (vis == false) {
      super.setVisible(false);
      return;
    }
    // Pobrisemo sve item-e
    liField.removeAll();
    liSubfield.removeAll();

    lPreview.setText("");

    Vector data = getAllowedFields();
    liField.setListData(data);
    liField.requestFocus();
    if (data.size() > 0) {
      liField.setSelectedIndex(0);
      JScrollBar scrollBar = jScrollPane1.getVerticalScrollBar();
      scrollBar.setValue(scrollBar.getMinimum());
      showSubfields();
      super.setVisible(true);
    } else {
       JOptionPane.showMessageDialog(this, Messages.get("BISISAPP_ADDFIELDDLG_NOFIELDSHERE"), Messages.get("BISISAPP_ADDFIELDDLG_INFO"), JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public Vector getAllowedFields() {
    Field sCurr = ed.getCurrField();
    Field sNext = ed.getNextField();
    Field sPrev = ed.getPrevField();

    Vector v = Environment.getLib().getTypeLESubfields();
    v = Environment.getLib().compressSubfields(v);
    Vector data = new Vector();
    if (ed.insertMode == EditDlg.ADD_MODE) {  // ADD - dodavanje posle tekuceg polja
      int i = 0;
      if (sNext == null) { // ako nema sledeceg polja -> tekuci je poslednji
        // radi od tekuceg polja do kraja tipa publ.
        if (Environment.getFs().getField(sCurr.getName()).getRepeatable()
            && Environment.getLib().memberFieldTP(sCurr.getName(), v))
          data.addElement(sCurr.getName());
        while (i < v.size()) {
          LESubfield ls = (LESubfield) v.elementAt(i);
          if (ls.getField().compareTo(sCurr.getName()) > 0) {
            data.addElement(ls.getField());
          }
          i++;
        }
      } else {
        // radi od sCurr do sNext
        if (Environment.getFs().getField(sCurr.getName()).getRepeatable()
            && Environment.getLib().memberFieldTP(sCurr.getName(), v) )
          data.addElement(sCurr.getName());
        while (i < v.size()) {
          LESubfield ls = (LESubfield) v.elementAt(i);
          if (ls.getField().compareTo(sNext.getName()) >= 0)
            break;
          if (ls.getField().compareTo(sCurr.getName()) > 0 && ls.getField().compareTo(sNext.getName()) < 0)
            data.addElement(ls.getField());
          i++;
        }
        if (Environment.getFs().getField(sNext.getName()).getRepeatable()
             && Environment.getLib().memberFieldTP(sNext.getName(), v)
             && !sNext.getName().equals(sCurr.getName()))
          data.addElement(sNext.getName());
      }
    } else {  // INSERT_MODE - umetanje ispred tekuceg
      int i = 0;
      if (sPrev == null) {
        // radi od pocetka tipa publ. do prvog
        while (i < v.size()) {
          LESubfield ls = (LESubfield) v.elementAt(i);
          if (ls.getField().compareTo(sCurr.getName()) < 0) {
            data.addElement(ls.getField());
          }
          i++;
        }
        if (Environment.getFs().getField(sCurr.getName()).getRepeatable()
            && Environment.getLib().memberFieldTP(sCurr.getName(), v))
          data.addElement(sCurr.getName());
      } else {
        // radi od sPrev do sCurr
        if (Environment.getFs().getField(sPrev.getName()).getRepeatable()
            && Environment.getLib().memberFieldTP(sPrev.getName(), v) )
          data.addElement(sPrev.getName());
        while (i < v.size()) {
          LESubfield ls = (LESubfield) v.elementAt(i);
          if (ls.getField().compareTo(sCurr.getName()) >= 0)
            break;
          if (ls.getField().compareTo(sPrev.getName()) > 0 && ls.getField().compareTo(sCurr.getName()) < 0)
            data.addElement(ls.getField());
          i++;
        }
        if (Environment.getFs().getField(sCurr.getName()).getRepeatable()
            && Environment.getLib().memberFieldTP(sCurr.getName(), v)
            && !sPrev.getName().equals(sCurr.getName()))
          data.addElement(sCurr.getName());
      }
    }
    return data;
  }

  /** Ubacuje polje nakon tekuceg
     @param s Ime polja sa potpoljima, poput: 200abc.
   */
  public void insertFieldAfter(String s) {
    int row = ed.getSelectedRow();
    int col = ed.getSelectedColumn();
    String fieldName = new String(s.substring(0, 3));
    String subfields = new String(s.substring(3));
    Field f = new Field(fieldName, "", "");
    String subfieldName;
    for(int i = 0; i < subfields.length(); i++) {
      subfieldName = new String(i < (subfields.length() -1) ?
                                subfields.substring(i, i+1) :
                                subfields.substring(i));
      Subfield sf = new Subfield(subfieldName, "");
      f.addSubfield(sf);
    }
    if (ed.utm.isThereAFieldRight(row, col)) {
      if ((col == UnimarcTableModel.COL_COUNT-1) && (row < ed.utm.getRowCount()-1)) {
        col = 0;
        row++;
      } else
        col++;
      ed.utm.insertValueAt(f, row, col);
    } else {
      ed.utm.addValue(f);
    }
  }

  /** Ubacuje polje ispred tekuceg
     @param s Ime polja sa potpoljima, poput: 200abc.
   */
  public void insertField(String s) {
    int row = ed.getSelectedRow();
    int col = ed.getSelectedColumn();
    String fieldName = new String(s.substring(0, 3));
    String subfields = new String(s.substring(3));
    Field f = new Field(fieldName, "", "");
    String subfieldName;
    for(int i = 0; i < subfields.length(); i++) {
      subfieldName = new String(i < (subfields.length() -1) ?
                                subfields.substring(i, i+1) :
                                subfields.substring(i));
      Subfield sf = new Subfield(subfieldName, "");
      f.addSubfield(sf);
    }
    ed.utm.insertValueAt(f, row, col);
  }

  void bOK_actionPerformed(ActionEvent e) {
    if (lPreview.getText().length() > 3) {
      if (ed.insertMode == EditDlg.ADD_MODE)
         insertFieldAfter(lPreview.getText());
      else
         insertField(lPreview.getText());
      ed.updateColumnWidths();
      ed.utm.fireTableDataChanged();
      liField.requestFocus();
      setVisible(false);
    }
  }

  void bCancel_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void liField_valueChanged(ListSelectionEvent e) {
    lPreview.setText((String)liField.getSelectedValue());
    showSubfields();
  }

  void liField_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        liSubfield.requestFocus();
        break;
      case KeyEvent.VK_ESCAPE:
        bCancel.doClick();
        break;
    }
  }

  void liSubfield_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        bAdd.doClick();
      break;
      case KeyEvent.VK_ESCAPE:
        bCancel.doClick();
      break;
    }
  }

  void liSubfield_mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2)
      bAdd.doClick();
  }

  void bAdd_actionPerformed(ActionEvent e) {
    Object[] s = liSubfield.getSelectedValues();
    String tmp = "";
    for (int i = s.length - 1; i >= 0; i--)
      tmp += (String)s[i];
    lPreview.setText(lPreview.getText() + tmp);
    String subfields = lPreview.getText().substring(3);
    String[] s1 = new String[subfields.length()];
    for (int i = 0; i < subfields.length(); i++)
      s1[i] = ""+subfields.charAt(i);
    Sorter.qsort(s1);
    subfields = "";
    for (int i = 0; i < s1.length; i++)
      subfields += s1[i];
    lPreview.setText(lPreview.getText().substring(0, 3)+subfields);
    showSubfields();
  }

  void bRemove_actionPerformed(ActionEvent e) {
    lPreview.setText((String)liField.getSelectedValue());
    showSubfields();
  }


}

