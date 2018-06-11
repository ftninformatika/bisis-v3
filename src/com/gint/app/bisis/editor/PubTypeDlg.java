package com.gint.app.bisis.editor;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/** Dijalog boks za prikaz tipa publikacije, kao i nivoa obrade i nivoa obaveznosti.
 */
public class PubTypeDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lTitle = new JLabel();
  JLabel lPubType = new JLabel();
  JLabel lProcLevel = new JLabel();
  JLabel lManLevel = new JLabel();
  JComboBox cbPubType = new JComboBox();
  JComboBox cbProcLevel = new JComboBox();
  JComboBox cbManLevel = new JComboBox();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  MainFrame mf;

  public PubTypeDlg(Frame frame, String title, boolean modal, MainFrame mf) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    this.mf = mf;
  }

  public PubTypeDlg() {
    this(null, "", false, null);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lTitle.setText(Messages.get("BISISAPP_PUBTYPEDLG_LABELEDITINGPARAMETERS"));
    lPubType.setText(Messages.get("BISISAPP_PUBTYPEDLG_LABELPUBTYPE"));
    lProcLevel.setText(Messages.get("BISISAPP_PUBTYPEDLG_LABELPROCLEVEL"));
    lManLevel.setText(Messages.get("BISISAPP_PUBTYPEDLG_LABELMANLEVEL"));
    xYLayout1.setHeight(239);
    xYLayout1.setWidth(377);
    bOK.setText(Messages.get("BISISAPP_PUBTYPEDLG_BUTTONOK"));
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    bCancel.setText(Messages.get("BISISAPP_PUBTYPEDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    cbPubType.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbPubType_actionPerformed(e);
      }
    });
    cbProcLevel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbProcLevel_actionPerformed(e);
      }
    });
    cbPubType.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        bCancel.doClick();
      }
    }, "", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);
    cbProcLevel.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        bCancel.doClick();
      }
    }, "", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);
    cbManLevel.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        bCancel.doClick();
      }
    }, "", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);

    getContentPane().add(panel1);
    panel1.add(lTitle, new XYConstraints(131, 13, -1, -1));
    panel1.add(lPubType, new XYConstraints(15, 53, -1, -1));
    panel1.add(cbPubType, new XYConstraints(138, 48, 219, -1));
    panel1.add(cbProcLevel, new XYConstraints(138, 89, 219, -1));
    panel1.add(cbManLevel, new XYConstraints(138, 129, 219, -1));
    panel1.add(lProcLevel, new XYConstraints(15, 94, -1, -1));
    panel1.add(lManLevel, new XYConstraints(15, 134, -1, -1));
    panel1.add(bOK, new XYConstraints(80, 188, 89, -1));
    panel1.add(bCancel, new XYConstraints(189, 188, 89, -1));
    getRootPane().setDefaultButton(bOK);
    setSize(377, 239);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  /** Popunjava kombo boksove raspolozivim tipovima publikacije, nivoima obrade
   *  i nivoima obaveznosti, a zatim selektuje tekuci tip publikacije,
   *  nivo obrade i nivo obaveznosti.
   */
  public void setData() {
    String currPubType = Environment.getLib().getPubType();
    int currProcLevel = Environment.getLib().getProcLevel();
    int currManLevel = Environment.getLib().getManLevel();

    Vector pubTypes = Environment.getLib().getPubTypeList();
    if (cbPubType.getItemCount() > 0)
      cbPubType.removeAllItems();
    for (int i = 0; i < pubTypes.size(); i++)
      cbPubType.addItem(pubTypes.elementAt(i));
    cbPubType.setSelectedItem(currPubType);

    Vector procLevels = Environment.getLib().getProcLevelList(currPubType);
    if (cbProcLevel.getItemCount() > 0)
      cbProcLevel.removeAllItems();
    for (int i = 0; i < procLevels.size(); i++)
      cbProcLevel.addItem(procLevels.elementAt(i));
    cbProcLevel.setSelectedItem(""+currProcLevel);

    Vector manLevels = Environment.getLib().getManLevelList(currPubType, currProcLevel);
    if (cbManLevel.getItemCount() > 0)
      cbManLevel.removeAllItems();
    for (int i = 0; i < manLevels.size(); i++)
      cbManLevel.addItem(manLevels.elementAt(i));
    cbManLevel.setSelectedItem(""+currManLevel);

    cbPubType.setEnabled(true);
  }

  /** Popunjava kombo boksove zadatim tipom publikacije, nivoima obrade
   *  i nivoima obaveznosti, a zatim selektuje nivo obrade i nivo obaveznosti
   *  na osnovu prosledjenih vrednosti.
   */
  public void setData(String currPubType, boolean pubTypeEnabled) {
    int currProcLevel = Environment.getLib().getProcLevel();
    int currManLevel = Environment.getLib().getManLevel();

    if (!pubTypeEnabled) {
      cbPubType.removeAllItems();
      cbPubType.addItem(currPubType);
    }
    Vector procLevels = Environment.getLib().getProcLevelList(currPubType);
    if (cbProcLevel.getItemCount() > 0)
      cbProcLevel.removeAllItems();
    for (int i = 0; i < procLevels.size(); i++)
      cbProcLevel.addItem(procLevels.elementAt(i));
    cbProcLevel.setSelectedItem(""+currProcLevel);

    Vector manLevels = Environment.getLib().getManLevelList(currPubType, currProcLevel);
    if (cbManLevel.getItemCount() > 0)
      cbManLevel.removeAllItems();
    for (int i = 0; i < manLevels.size(); i++)
      cbManLevel.addItem(manLevels.elementAt(i));
    cbManLevel.setSelectedItem(""+currManLevel);

    cbPubType.setEnabled(pubTypeEnabled);
  }

  void bOK_actionPerformed(ActionEvent e) {
    try {
      Environment.getLib().setCurrContext((String)cbPubType.getSelectedItem(),
        Integer.parseInt((String)cbProcLevel.getSelectedItem()),
        Integer.parseInt((String)cbManLevel.getSelectedItem()));
    } catch (NumberFormatException ex) {
    }
    mf.updateStatLin();
    setVisible(false);
  }

  void bCancel_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void cbPubType_actionPerformed(ActionEvent e) {
    String newPubType = (String)cbPubType.getSelectedItem();
    setData(newPubType, true);
  }

  void cbProcLevel_actionPerformed(ActionEvent e) {
    String currPubType = (String)cbPubType.getSelectedItem();
    int newProcLevel = 0;
    try { newProcLevel = Integer.parseInt((String)cbProcLevel.getSelectedItem()); } catch (NumberFormatException ex) { }

    Vector manLevels = Environment.getLib().getManLevelList(currPubType, newProcLevel);
    if (cbManLevel.getItemCount() > 0)
      cbManLevel.removeAllItems();
    for (int i = 0; i < manLevels.size(); i++)
      cbManLevel.addItem(manLevels.elementAt(i));
  }

}

