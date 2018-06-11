package com.gint.app.bisis.circ;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.gint.util.gui.WindowUtils;

/**
 * 
 * @author branko
 */
public class PrefixListDlg extends JDialog {

  private javax.swing.JPanel jContentPane = null;
  private JPanel pButtons = null;
  private JButton btnOK = null;
  private JButton btnCancel = null;
  private JScrollPane spPrefixList = null;
  private JList lbPrefixList = null;
  private Connection conn;

  /**
   * This is the default constructor
   */
  public PrefixListDlg(Frame owner, Connection conn) {
    super(owner, "Izbor prefiksa", true);
    this.conn = conn;
    initialize();
    WindowUtils.centerOnScreen(this);
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize() {
    this.setSize(300, 200);
    this.setContentPane(getJContentPane());
    this.pack();
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private javax.swing.JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new javax.swing.JPanel();
      jContentPane.setLayout(new java.awt.BorderLayout());
      jContentPane.add(getPButtons(), java.awt.BorderLayout.SOUTH);
      jContentPane.add(getSpPrefixList(), java.awt.BorderLayout.CENTER);
    }
    return jContentPane;
  }

  /**
   * This method initializes jPanel
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getPButtons() {
    if (pButtons == null) {
      pButtons = new JPanel();
      pButtons.add(getBtnCancel(), null);
      pButtons.add(getBtnOK(), null);
    }
    return pButtons;
  }

  /**
   * This method initializes jButton
   * 
   * @return javax.swing.JButton
   */
  private JButton getBtnOK() {
    if (btnOK == null) {
      btnOK = new JButton();
      btnOK.setText("  OK  ");
      btnOK.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          selected = true;
          setVisible(false);
        }
      });
      getRootPane().setDefaultButton(btnOK);
    }
    return btnOK;
  }

  /**
   * This method initializes jButton
   * 
   * @return javax.swing.JButton
   */
  private JButton getBtnCancel() {
    if (btnCancel == null) {
      btnCancel = new JButton();
      btnCancel.setText("Odustani");
      btnCancel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          selected = false;
          setVisible(false);
        }
      });
    }
    return btnCancel;
  }

  /**
   * This method initializes jScrollPane
   * 
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getSpPrefixList() {
    if (spPrefixList == null) {
      spPrefixList = new JScrollPane();
      spPrefixList.setViewportView(getLbPrefixList());
      spPrefixList.setPreferredSize(new java.awt.Dimension(400, 150));
      spPrefixList.setFocusable(false);
    }
    return spPrefixList;
  }

  /**
   * This method initializes jList
   * 
   * @return javax.swing.JList
   */
  private JList getLbPrefixList() {
    if (lbPrefixList == null) {
      lbPrefixList = new JList();
      lbPrefixList.setModel(getPlm());
      lbPrefixList.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            getBtnOK().doClick();
            return;
          }
          if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            getBtnCancel().doClick();
            return;
          }
          char c = e.getKeyChar();
          int pos = plm.getSelection(c);
          if (pos > -1) {
            lbPrefixList.setSelectedIndex(pos);
            JScrollBar sb = getSpPrefixList().getVerticalScrollBar();
            sb.setValue((sb.getMaximum() - sb.getMinimum())
                / lbPrefixList.getModel().getSize() * pos);

          }
        }
      });
      lbPrefixList.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
          if (e.getClickCount() > 1)
            getBtnOK().doClick();
        }
      });
    }
    return lbPrefixList;
  }
  
  private PrefixListModel getPlm(){
  	if (plm == null){
  		plm = new PrefixListModel(conn);
  	}
  	return plm;
  }

  /**
   * @return Returns the selected.
   */
  public boolean isSelected() {
    return selected;
  }

  public String getSelectedPrefix() {
    return getPlm().getPrefixName(getLbPrefixList().getSelectedIndex());
  }

  public void moveTo(String s) {
    int pos = getPlm().getSelection(s);
    if (pos == -1)
      pos = 0;
    lbPrefixList.setSelectedIndex(pos);
    JScrollBar sb = getSpPrefixList().getVerticalScrollBar();
    sb.setValue((sb.getMaximum() - sb.getMinimum())
        / lbPrefixList.getModel().getSize() * pos);
  }

  private boolean selected = false;

  private PrefixListModel plm = null;
}