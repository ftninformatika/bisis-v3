package com.gint.app.bisis.editor.list;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import com.gint.app.bisis.editor.Messages;

/** Dijalog koji prikazuje listu stringova i nudi izbor jednog od njih.
    Moguce je i odustajanje pomocu dugmeta Cancel. OK = ENTER = doubleclick;
    Cancel = ESC.
  */
public class ListDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList liList = new JList();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  JLabel lTitle = new JLabel();

  public ListDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ListDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    bOK.setText(Messages.get("BISISAPP_LISTDLG_BUTTONOK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(Messages.get("BISISAPP_LISTDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(318);
    xYLayout1.setWidth(433);
    lTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lTitle.setText(" ");
    liList.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        liList_keyReleased(e);
      }
      /*
      public void keyPressed(KeyEvent e) {
        liList_keyReleased(e);
      }
      public void keyTyped(KeyEvent e) {
        liList_keyTyped(e);
      }
      */
    });
    liList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        liList_mouseClicked(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jScrollPane1, new XYConstraints(15, 39, 402, 224));
    panel1.add(bOK, new XYConstraints(123, 275, 84, -1));
    panel1.add(bCancel, new XYConstraints(225, 275, -1, -1));
    panel1.add(lTitle, new XYConstraints(0, 7, 432, -1));
    jScrollPane1.getViewport().add(liList, null);
    liList.requestFocus();
    setSize(433, 318);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((d.width - getSize().width) / 2 , (d.height - getSize().height) / 2);
    getRootPane().setDefaultButton(bOK);
  }

  /** Vraca selektovani string iz liste.
      @return Selektovani red iz liste
    */
  public String getSelection() {
    return selection;
  }

  /** Postavlja naslov dijaloga.
      @param title Naslov dijaloga
    */
  public void setTitle(String title) {
    lTitle.setText(title);
  }

  /** Postavlja sadrzaj liste.
      @param data Vector stringova kojim ce se napuniti lista
    */
  public void setListData(Vector data) {
    liList.setListData(data);
    if (data.size() > 0)
      liList.setSelectedIndex(0);
  }

  /** Postavlja sadrzaj liste.
      @param data Vector stringova kojim ce se napuniti lista
      @param initial Neki od item-a, koji treba selektovati
    */
  public void setListData(Vector data, String initial) {
    liList.setListData(data);
    if (data.size() > 0) {
      int index = getSelectionForString(liList.getModel(), initial);
      if (index > -1)
        liList.setSelectedValue(liList.getModel().getElementAt(index), true);
    }
  }

  public void setVisible(boolean visible) {
    if (visible)
      liList.requestFocus();
    super.setVisible(visible);
  }

  /** Selektovani red u listi. */
  private String selection = "";

  void bOK_actionPerformed(ActionEvent e) {
     String s = (String)liList.getSelectedValue();
     if (s != null) {
       selection = s;
       setVisible(false);
     }
  }

  void bCancel_actionPerformed(ActionEvent e) {
    selection = "";
    setVisible(false);
  }

  /** Vraca indeks stavke koja pocinje zadatim slovom */
  int getSelectionForChar(ListModel data, char c) {
    int n = data.getSize();
    for (int i = 0; i < n; i++) {    	
      if (Character.toUpperCase(((String)data.getElementAt(i)).charAt(0)) == Character.toUpperCase(c))
        return i;
    }
    return -1;
  }

  /** Vraca indeks stavke koja pocinje zadatim stringom */
  int getSelectionForString(ListModel data, String s) {
    int n = data.getSize();
    for (int i = 0; i < n; i++)
      if (((String)data.getElementAt(i)).toUpperCase().startsWith(s.toUpperCase()))
        return i;
    return -1;
  }

  void liList_keyReleased(KeyEvent e) {
    /*
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        bOK.doClick();
        break;
      case KeyEvent.VK_ESCAPE:
        bCancel.doClick();
        break;
      default:
        char c = e.getKeyChar();
        int i = getSelectionForChar(liList.getModel(), c);
        if (i > -1) {
          liList.setSelectedIndex(i);
          JScrollBar sb = jScrollPane1.getVerticalScrollBar();
          sb.setValue((sb.getMaximum() - sb.getMinimum())/liList.getModel().getSize() * i);
        }
        break;
    }
    */
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      return;
    }
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      bCancel.doClick();
      return;
    }
    
    char c = e.getKeyChar();
    int i = getSelectionForChar(liList.getModel(), c);
    if (i > -1) {
      liList.setSelectedIndex(i);
      JScrollBar sb = jScrollPane1.getVerticalScrollBar();
      sb.setValue((sb.getMaximum() - sb.getMinimum())/liList.getModel().getSize() * i);
    }
  }

  void liList_mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2)
      bOK.doClick();
  }
}

