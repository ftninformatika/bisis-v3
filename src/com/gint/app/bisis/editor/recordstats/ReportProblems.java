package com.gint.app.bisis.editor.recordstats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.gint.util.gui.WindowUtils;

public class ReportProblems extends JFrame {

  public ReportProblems() {
    
    getContentPane().setLayout(new BorderLayout());
    
    taProblems.setEditable(false);
    spProblems.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    spProblems.setPreferredSize(new Dimension(400, 300));
    spProblems.getViewport().setView(taProblems);
    getContentPane().add(spProblems, BorderLayout.CENTER);
    
    getRootPane().setDefaultButton(btnClose);
    btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        setVisible(false);
      }
    });
    
    JPanel pSouth = new JPanel();
    pSouth.add(btnClose);
    getContentPane().add(pSouth, BorderLayout.SOUTH);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("Nedostaci u zapisima");
    pack();
    WindowUtils.centerOnScreen(this);
  }
  
  public void setText(String text) {
    taProblems.setText(text);
    //spProblems.getVerticalScrollBar().setLocation(0, 0);
  }
  
  JTextArea taProblems = new JTextArea();
  JScrollPane spProblems = new JScrollPane();
  JButton btnClose = new JButton(" Zatvori ");
}
