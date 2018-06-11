package com.gint.app.bisis.export;

import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;

public class NeuspeliZapisiDlg extends JDialog {
  JFileChooser fc = new JFileChooser();
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JScrollPane jspNeuspeli = new JScrollPane();
  JTable tNeuspeli = new JTable();
  JButton bClose = new JButton();
  public NeuspeliTableModel ntm;
  JButton bOpen = new JButton();
  JButton bImport = new JButton();

  public NeuspeliZapisiDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public NeuspeliZapisiDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setWidth(600);
    xYLayout1.setHeight(400);
    bClose.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/delete.gif")));
    bClose.setText(Messages.get("NEUSPELIZAPISIDLG_BUTTON_CLOSE"));
    bClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bClose_actionPerformed(e);
      }
    });
    bOpen.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/datastore.gif")));
    bOpen.setText(Messages.get("NEUSPELIZAPISIDLG_BUTTON_OPEN"));
    bOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOpen_actionPerformed(e);
      }
    });
    bImport.setIcon(new ImageIcon(getClass().getResource(
      "/com/gint/app/bisis/export/images/check.gif")));
    bImport.setText(Messages.get("NEUSPELIZAPISIDLG_BUTTON_IMPORT"));
    bImport.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bImport_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jspNeuspeli, new XYConstraints(23, 31, 553, 298));
    panel1.add(bOpen, new XYConstraints(25, 345, -1, -1));
    panel1.add(bClose, new XYConstraints(482, 345, -1, -1));
    panel1.add(bImport, new XYConstraints(247, 345, -1, -1));

    ntm = new NeuspeliTableModel();
    tNeuspeli.setModel(ntm);
    tNeuspeli.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    TableColumn tc = tNeuspeli.getColumnModel().getColumn(0);
    tc.setHeaderValue(Messages.get("NEUSPELIZAPISIDLG_TABLEHEADER_DATE"));
    tc.setMinWidth(70);
    tc.setMaxWidth(70);
    tc.setPreferredWidth(70);
    tc = tNeuspeli.getColumnModel().getColumn(1);
    tc.setHeaderValue(Messages.get("NEUSPELIZAPISIDLG_TABLEHEADER_ID"));
    tc.setMinWidth(70);
    tc.setMaxWidth(70);
    tc.setPreferredWidth(70);
    tc = tNeuspeli.getColumnModel().getColumn(2);
    tc.setHeaderValue(Messages.get("NEUSPELIZAPISIDLG_TABLEHEADER_TITLE"));
    tc = tNeuspeli.getColumnModel().getColumn(3);
    tc.setHeaderValue(Messages.get("NEUSPELIZAPISIDLG_TABLEHEADER_AUTHORS"));
    jspNeuspeli.getViewport().add(tNeuspeli, null);

    fc.setDialogTitle(Messages.get("NEUSPELIZAPISIDLG_FILEOPEN_FRAMETITLE"));
    fc.setApproveButtonText(
      Messages.get("NEUSPELIZAPISIDLG_FILEOPEN_BUTTON_APPROVE"));
    fc.setMultiSelectionEnabled(false);
    fc.setCurrentDirectory(new File("."));

    setSize(600, 400);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
    getRootPane().setDefaultButton(bClose);
  }

  void bClose_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void bOpen_actionPerformed(ActionEvent e) {
    String fileName = null;
    int retVal = fc.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      fileName = fc.getSelectedFile().getAbsolutePath();
      ntm.setData(fileName);
      ntm.fireTableDataChanged();
    }
  }

  void bImport_actionPerformed(ActionEvent e) {
    if (ntm.getRowCount() == 0 || tNeuspeli.getSelectedRow() < 0)
      return;
    JLabel msg = new JLabel(
      Messages.get("NEUSPELIZAPISIDLG_AREYOUSURE_MESSAGE"));
    msg.setForeground(Color.black);
    String defBtn = Messages.get("NEUSPELIZAPISIDLG_AREYOUSURE_BUTTON_YES");
    String[] buttons = {defBtn, 
      Messages.get("NEUSPELIZAPISIDLG_AREYOUSURE_BUTTON_NO")};
    if (JOptionPane.showOptionDialog(
        null,
        msg,
        Messages.get("NEUSPELIZAPISIDLG_AREYOUSURE_FRAMETITLE"),
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        buttons,
        defBtn) == JOptionPane.YES_OPTION) {
      if (!ntm.connect())
        return;
      int[] rows = tNeuspeli.getSelectedRows();
      int id;
      String doc;
      String title;
      int result = 0;
      for (int i = 0; i < rows.length; i++) {
        id  = Integer.parseInt((String)ntm.getValueAt(i, 1));
        doc = (String)ntm.getValueAt(i, 4);
        title = (String)ntm.getValueAt(i, 2);
        if (id == -1)
          result = ntm.insert(doc);
        else
          result = ntm.update(id, doc);
        if (result == -1)
          JOptionPane.showMessageDialog(null, 
            Messages.get("NEUSPELIZAPISIDLG_RECORDFAILED_ID") + " " + id + "\n"+
            Messages.get("NEUSPELIZAPISIDLG_RECORDFAILED_TITLE") + title, 
            Messages.get("NEUSPELIZAPISIDLG_RECORDFAILED_FRAMETITLE"), 
            JOptionPane.ERROR_MESSAGE);
      }
      JOptionPane.showMessageDialog(null, 
        Messages.get("NEUSPELIZAPISIDLG_FINISHED_MESSAGE"), 
        Messages.get("NEUSPELIZAPISIDLG_FINISHED_FRAMETITLE"), 
        JOptionPane.PLAIN_MESSAGE);
      ntm.disconnect();
    }
  }
}

