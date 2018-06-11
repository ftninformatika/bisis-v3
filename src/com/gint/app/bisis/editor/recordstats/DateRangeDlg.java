package com.gint.app.bisis.editor.recordstats;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class DateRangeDlg extends JDialog {

  public DateRangeDlg(JFrame parent) {
    super(parent, "Vremenski period", true);
    
    Box box1 = Box.createHorizontalBox();
    Box box2 = Box.createVerticalBox();
    Box box3 = Box.createVerticalBox();
    
    box2.add(new JLabel("Po\u010detni datum: "));
    box2.add(new JLabel("Krajnji datum: "));
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
      initDates();
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
      Date start = sdf.parse(tfFrom.getText().trim());
      Date end = sdf.parse(tfTo.getText().trim());
      startDate = start;
      endDate = end;
      cancelled = false;
      setVisible(false);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Neispravan datum!", "Gre\u0161ka", 
          JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public Date getStartDate() {
    return startDate;
  }
  
  public Date getEndDate() {
    return endDate;
  }
  
  public String getDateRange() {
    return sdf.format(startDate) + " - " + sdf.format(endDate);
  }
  
  public boolean isCancelled() {
    return cancelled;
  }
  
  private void initDates() {
    Date now = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(now);
    int year = cal.get(Calendar.YEAR);
    cal.set(year, Calendar.JANUARY, 1);
    Date from = cal.getTime();
    cal.set(year, Calendar.DECEMBER, 31);
    Date to = cal.getTime();
    tfFrom.setText(sdf.format(from));
    tfTo.setText(sdf.format(to));
  }

  private JButton btnOK = new JButton("   OK   ");
  private JButton btnCancel = new JButton("Odustani");
  private JTextField tfFrom = new JTextField(12);
  private JTextField tfTo = new JTextField(12);
  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  
  private boolean cancelled;
  private Date startDate = null;
  private Date endDate = null;
}
