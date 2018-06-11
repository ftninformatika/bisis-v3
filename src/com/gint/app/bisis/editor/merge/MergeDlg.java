package com.gint.app.bisis.editor.merge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.gint.app.bisis.editor.MainFrame;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.util.gui.WindowUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class MergeDlg extends JDialog {
  
  public MergeDlg(MainFrame mf) {
    super(mf, "Spajanje zapisa", true);
    this.mf = mf;
    initialize();
  }
  
  public void initialize() {
    getContentPane().setLayout(new BorderLayout());
    JPanel pSouth = new JPanel();
    pSouth.setLayout(new FlowLayout());
    pSouth.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    btnCancel = new JButton(" Odustani ");
    btnOK = new JButton("   OK   ");
    pSouth.add(btnCancel);
    pSouth.add(btnOK);
    getRootPane().setDefaultButton(btnOK);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleCancel();
      }
    });
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleOK();
      }
    });
    getContentPane().add(pSouth, BorderLayout.SOUTH);

    JScrollPane spCenter = new JScrollPane();
    spCenter.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), "Originalni zapisi"));
    spCenter.setPreferredSize(new Dimension(700, 350));
    taSource = new JTextArea();
    taSource.setFont(new Font("Monospaced", Font.PLAIN, 12));
    taSource.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
          btnCancel.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ENTER)
          btnOK.doClick();
      }
    });
    spCenter.getViewport().setView(taSource);
    getContentPane().add(spCenter, BorderLayout.CENTER);
    
    JScrollPane spNorth = new JScrollPane();
    spNorth.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), "Rezultuju\u0107i zapis"));
    spNorth.setPreferredSize(new Dimension(700, 200));
    taResult = new JTextArea();
    taResult.setFont(new Font("Monospaced", Font.PLAIN, 12));
    taResult.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
          btnCancel.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ENTER)
          btnOK.doClick();
      }
    });
    spNorth.getViewport().setView(taResult);
    getContentPane().add(spNorth, BorderLayout.NORTH);
    pack();
    WindowUtils.centerOnScreen(this);
  }
  
  public void setVisible(boolean visible) {
    if (visible)
      confirmed = false;
    super.setVisible(visible);
  }
  
  public void handleCancel() {
    confirmed = false;
    setVisible(false);
  }
  
  public void setSource(Record[] records) {
    taSource.setText("");
    for (int i = 0; i < records.length; i++) {
      taSource.append("------------------------------\n");
      String s = RecordFactory.toFullFormat(records[i], false);
      taSource.append(s);
      taSource.append("\n");
    }
    taSource.setCaretPosition(0);
  }
  
  public void setResult(Record record) {
    taResult.setText(RecordFactory.toFullFormat(record, false));
    taResult.setCaretPosition(0);
  }
  
  public void handleOK() {
    confirmed = true;
    setVisible(false);
  }
  
  public boolean isConfirmed() {
    return confirmed;
  }
  
  MainFrame mf;
  JButton btnOK;
  JButton btnCancel;
  JTextArea taResult;
  JTextArea taSource;
  boolean confirmed = false;
}
