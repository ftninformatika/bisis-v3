package com.gint.app.bisis.dbassist;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/** Dijalog za izbor tablespace-a za tabele i indekse. */
public class TablespaceDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList lUserTablespace = new JList();
  JList lIndexTablespace = new JList();
  JLabel lTable = new JLabel();
  JLabel lIndex = new JLabel();
  JButton bOK = new JButton();

  public TablespaceDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public TablespaceDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lTable.setText(Messages.get("TABLESPACEDLG_LABEL_TABLE"));
    lIndex.setText(Messages.get("TABLESPACEDLG_LABEL_INDEX"));
    bOK.setText(Messages.get("TABLESPACEDLG_BUTTON_OK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bOK.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/dbassist/images/check.gif")));
    xYLayout1.setHeight(245);
    xYLayout1.setWidth(395);
    getContentPane().add(panel1);
    panel1.add(jScrollPane1, new XYConstraints(22, 43, 165, 124));
    jScrollPane1.getViewport().add(lUserTablespace, null);
    panel1.add(jScrollPane2, new XYConstraints(208, 43, 165, 124));
    panel1.add(lTable, new XYConstraints(22, 16, -1, -1));
    panel1.add(lIndex, new XYConstraints(208, 16, -1, -1));
    panel1.add(bOK, new XYConstraints(133, 197, 125, -1));
    jScrollPane2.getViewport().add(lIndexTablespace, null);
    setSize(395, 245);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  void bOK_actionPerformed(ActionEvent e) {
    setVisible(false);
  }
}

