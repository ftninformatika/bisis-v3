package com.gint.app.bisis.counters;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;
import com.gint.util.gui.WindowUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class CounterApp extends JFrame {
  
  public CounterApp(Connection conn) {
    this.conn = conn;
    setTitle("Pode\u0161avanje broja\u010da");
    getContentPane().setLayout(new BorderLayout());
    JPanel pSouth = new JPanel();
    pSouth.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    getContentPane().add(pSouth, BorderLayout.SOUTH);
    pSouth.setLayout(new FlowLayout());
    pSouth.add(btnSave);
    pSouth.add(btnClose);
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleSave();
      }
    });
    btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleClose();
      }
    });
    
    JPanel pInv = new JPanel();
    pInv.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    pInv.setLayout(new BorderLayout());
    JScrollPane spInv = new JScrollPane();
    spInv.setPreferredSize(new Dimension(400, 300));
    pInv.add(spInv, BorderLayout.CENTER);
    tabbedPane.add("Inventarni brojevi", pInv);
    invTM = new InvBrTableModel(conn);
    tblInv = new JTable(invTM);
    spInv.getViewport().setView(tblInv);
    tblInv.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tblInv.getColumnModel().getColumn(0).setHeaderValue("Vrsta građe");
    tblInv.getColumnModel().getColumn(1).setHeaderValue("Poslednja vrednost");
    tblInv.getColumnModel().getColumn(0).setMaxWidth(150);
    JButton btnAddRow1 = new JButton(new ImageIcon(CounterApp.class.getResource(
        "/com/gint/app/bisis/counters/images/add.gif")));
    JButton btnDelRow1 = new JButton(new ImageIcon(CounterApp.class.getResource(
        "/com/gint/app/bisis/counters/images/del.gif")));
    JPanel pButtons = new JPanel();
    pInv.add(pButtons, BorderLayout.SOUTH);
    pButtons.setLayout(new FlowLayout());
    pButtons.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    pButtons.add(btnAddRow1);
    pButtons.add(btnDelRow1);
    btnAddRow1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        invTM.addRow();
      }
    });
    btnDelRow1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int index = tblInv.getSelectedRow();
        if (index > -1)
          invTM.delRow(index);
      }
    });
    
    JPanel pKor = new JPanel();
    pKor.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    pKor.setLayout(new BorderLayout());
    JScrollPane spKor = new JScrollPane();
    spKor.setPreferredSize(new Dimension(400, 300));
    pKor.add(spKor, BorderLayout.CENTER);
    tabbedPane.add("Korisni\u010dki brojevi", pKor);
    korTM = new KorisTableModel(conn);
    tblKor = new JTable(korTM);
    spKor.getViewport().setView(tblKor);
    tblKor.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tblKor.getColumnModel().getColumn(0).setHeaderValue("Ogranak");
    tblKor.getColumnModel().getColumn(1).setHeaderValue("Naziv");
    tblKor.getColumnModel().getColumn(2).setHeaderValue("Poslednji broj");
    tblKor.getColumnModel().getColumn(0).setMaxWidth(100);
    tblKor.getColumnModel().getColumn(1).setMaxWidth(100);
    
    JPanel pOpm = new JPanel();
    pOpm.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    pOpm.setLayout(new BorderLayout());
    JScrollPane spOpm = new JScrollPane();
    spOpm.setPreferredSize(new Dimension(400, 300));
    pOpm.add(spOpm, BorderLayout.CENTER);
    tabbedPane.add("Brojevi opomena", pOpm);
    opmTM = new OpomeneTableModel(conn);
    tblOpm = new JTable(opmTM);
    spOpm.getViewport().setView(tblOpm);
    tblOpm.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tblOpm.getColumnModel().getColumn(0).setHeaderValue("Tip opomene");
    tblOpm.getColumnModel().getColumn(1).setHeaderValue("Godina");
    tblOpm.getColumnModel().getColumn(2).setHeaderValue("Poslednji broj");
    tblOpm.getColumnModel().getColumn(0).setMaxWidth(100);
    tblOpm.getColumnModel().getColumn(1).setMaxWidth(100);
    JButton btnAddRow2 = new JButton(new ImageIcon(CounterApp.class.getResource(
    	"/com/gint/app/bisis/counters/images/add.gif")));
	JButton btnDelRow2 = new JButton(new ImageIcon(CounterApp.class.getResource(
	    "/com/gint/app/bisis/counters/images/del.gif")));
	JPanel pButtons2 = new JPanel();
	pOpm.add(pButtons2, BorderLayout.SOUTH);
	pButtons2.setLayout(new FlowLayout());
	pButtons2.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	pButtons2.add(btnAddRow2);
	pButtons2.add(btnDelRow2);
	btnAddRow2.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {
	    opmTM.addRow();
	  }
	});
	btnDelRow2.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {
	    int index = tblOpm.getSelectedRow();
	    if (index > -1)
	      opmTM.delRow(index);
	  }
	});

    pack();
    WindowUtils.centerOnScreen(this);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        handleClose();
      }
    });
  }
  
  public void handleSave() {
    invTM.save(conn);
    korTM.save(conn);
    opmTM.save(conn);
    JOptionPane.showMessageDialog(this, "Snimljeno!", "Snimanje", JOptionPane.INFORMATION_MESSAGE);
  }
  
  public void handleClose() {
    int answer = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da \u017eelite da iza\u0111ete?", "Izlaz", JOptionPane.YES_NO_OPTION);
    if (answer == JOptionPane.YES_OPTION) {
      try { conn.close(); } catch (Exception ex) { }
      System.exit(0);
    }
  }
  
  public static void main(String[] args) {
    Connection conn = null;
    INIFile iniFile = new INIFile(
        FileUtils.getClassDir(CounterApp.class) + "/config.ini");
    String driver = iniFile.getString("database", "driver");
    String url = iniFile.getString("database", "url");
    String username = iniFile.getString("database", "username");
    String password = iniFile.getString("database", "password");
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false);
    } catch (Exception ex) {
      ex.printStackTrace();
      return;
    }

    try {
      UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
    } catch (UnsupportedLookAndFeelException ex) {
      System.err.println("Unsupported look and feel: InfoNode");
    }
    
    CounterApp app = new CounterApp(conn);
    app.setVisible(true);
  }
  
  JButton btnSave = new JButton("  Snimi  ");
  JButton btnClose = new JButton(" Zatvori ");
  JTable tblInv;
  InvBrTableModel invTM;
  KorisTableModel korTM;
  OpomeneTableModel opmTM;
  JTable tblKor;
  JTable tblOpm;
  Connection conn;
}
