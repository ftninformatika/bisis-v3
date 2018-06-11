package com.gint.app.bisis.editor;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

public class HitListDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea taHitList = new JTextArea();
  JButton bBack = new JButton();

  public HitListDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public HitListDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    bBack.setText(Messages.get("BISISAPP_HITLISTDLG_BUTTONBACK"));
    bBack.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bBack_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(341);
    xYLayout1.setWidth(700);
    taHitList.setFont(new java.awt.Font("Monospaced", 0, 12));
    taHitList.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        taHitList_keyReleased(e);
      }
    });
    taHitList.setLineWrap(false);
    panel1.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        panel1_focusGained(e);
      }
    });
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    getContentPane().add(panel1);
    panel1.add(jScrollPane1, new XYConstraints(10, 11, 678, 278));
    panel1.add(bBack, new XYConstraints(11, 301, -1, -1));
    jScrollPane1.getViewport().add(taHitList, null);
    getRootPane().setDefaultButton(bBack);
    setSize(700, 341);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  public void setList(Vector lines) {
    String s = "";
    int n = lines.size();
    taHitList.setRows(n+5);
    for (int i = 0; i < n; i++)
        s += (String)lines.elementAt(i) + "\n";
    taHitList.setText(s);
    taHitList.setCaretPosition(0);
    taHitList.requestFocus();
  }

  void taHitList_keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      bBack.doClick();
  }

  void bBack_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void panel1_focusGained(FocusEvent e) {
    taHitList.requestFocus();
  }
}

