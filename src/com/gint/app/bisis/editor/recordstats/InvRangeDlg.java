package com.gint.app.bisis.editor.recordstats;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gint.util.gui.WindowUtils;

public class InvRangeDlg extends JDialog {

  public InvRangeDlg(JFrame parent) {
    super(parent, "Opseg inv. brojeva", true);
    
    Box box1 = Box.createHorizontalBox();
    Box box2 = Box.createVerticalBox();
    Box box3 = Box.createVerticalBox();
    
    box2.add(new JLabel("Po\u010detni broj: "));
    box2.add(new JLabel("Krajnji broj: "));
    box3.add(tfFrom);
    box3.add(tfTo);
    box1.add(box2);
    box1.add(box3);
    box1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JPanel pSouth = new JPanel();
    pSouth.add(btnCancel);
    pSouth.add(btnOK);
    btnCancel.setFocusable(false);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleCancel();
      }
    });
    btnOK.setFocusable(false);
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleOK();
      }
    });
    tfFrom.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.VK_ESCAPE)
          btnCancel.doClick();
      }
    });
    tfTo.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.VK_ESCAPE)
          btnCancel.doClick();
      }
    });
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(box1, BorderLayout.CENTER);
    getContentPane().add(pSouth, BorderLayout.SOUTH);
    getRootPane().setDefaultButton(btnOK);
    pack();
    WindowUtils.centerOnScreen(this);
  }
  
  public void setVisible(boolean visible) {
    if (visible) {
      initNumbers();
      cancelled = true;
    }
    tfFrom.requestFocus();
    super.setVisible(visible);
  }
  
  public void handleCancel() {
    cancelled = true;
    setVisible(false);
  }
  
  public void handleOK() {
    try {
      String sStart = tfFrom.getText().trim();
      String sEnd = tfTo.getText().trim();
      if (sStart.length() != 11 || sEnd.length() != 11)
        throw new Exception("11");
      int start = Integer.parseInt(sStart);
      int end = Integer.parseInt(sEnd);
      startNumber = sStart;
      endNumber = sEnd;
      cancelled = false;
      setVisible(false);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Neispravan broj!", "Gre\u0161ka", 
          JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public String getStartNumber() {
    return startNumber;
  }
  
  public String getEndNumber() {
    return endNumber;
  }
  
  public void setStartNumber(String startNumber) {
    this.startNumber = startNumber;
  }
  
  public void setEndNumber(String endNumber) {
    this.endNumber = endNumber;
  }
  
  public String getNumberRange() {
    return startNumber + " - " + endNumber;
  }
  
  public boolean isCancelled() {
    return cancelled;
  }
  
  private void initNumbers() {
    tfFrom.setText(startNumber);
    tfTo.setText(endNumber);
  }

  private JButton btnOK = new JButton("   OK   ");
  private JButton btnCancel = new JButton("Odustani");
  private JTextField tfFrom = new JTextField(12);
  private JTextField tfTo = new JTextField(12);
  
  private boolean cancelled;
  private String startNumber = "";
  private String endNumber = "";
}
