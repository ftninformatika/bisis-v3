//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import com.borland.jbcl.layout.*;
import com.gint.app.bisis.circ.reports.Info;
import java.awt.event.*;
import java.util.*;

public class CircDocs extends EscapeDialog {
  boolean closed = true;
  boolean added = false;
  boolean available = true;
  int selected_row = -1;
  DBConnection dbConn;
  DataSet dsCircDocs;
  JDBTableModel dmCircDocs;
  EditableTableModel tmDefault;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTable tblCircDocs;// = new JTable();
  JScrollPane sp1;
  JLabel tfSignature = new JLabel("1");
  JLabel lSign = new JLabel();
  JButton btnAdd = new JButton();
  JButton btnExit = new JButton();
  JButton btnInfo = new JButton();
  SignNo sign;
  String value = "";
  String signatura = "";
  String autor = "";
  String naslov = "";    
  ListSelectionModel lsmCircDocs;
  ActionListener asignOK, asignCancel, alAdd, alExit;
  ListSelectionListener circListLisener; // dodat lisener 31.10.00 i izbacena promenljiva visAct. Opet radi samo iz JBuilder-a
  JLabel lTitle = new JLabel();
  JLabel lAuthor = new JLabel();
  JLabel lRealAuthor = new JLabel("1");
  JLabel lRealTitle = new JLabel("1");
  JLabel lCount = new JLabel();
  JLabel Count = new JLabel();
  int count = 0;

  public CircDocs(Frame frame, String title, boolean modal,DBConnection db, SignNo sig) {
    super(frame, title, modal);
    dbConn=db;
    sign = sig;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public CircDocs() {
    this(CircApplet.parent, "", false,null,null);
  }

  public CircDocs(String title,DBConnection dbConn,boolean mod, SignNo sig) {
    this(CircApplet.parent, title, mod,dbConn,sig);
  }

  void jbInit() throws Exception {
    dsCircDocs = new DataSet(dbConn.getConnection(),"CIRC_DOCS", new String[]{"CTLG_NO"});
    tblCircDocs = new JTable();
    lsmCircDocs = tblCircDocs.getSelectionModel();
    lsmCircDocs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblCircDocs.setSelectionModel(lsmCircDocs);
    tblCircDocs.addKeyListener(new KeyAdapter(){
	      public void keyPressed(KeyEvent e){
	        if(e.getKeyCode()==KeyEvent.VK_ENTER){
	          btnAdd.doClick();
	        }
	      }
	    });
    sp1= new JScrollPane(tblCircDocs);
    xYLayout1.setWidth(430);
    xYLayout1.setHeight(306);
    lSign.setText(Messages.get("CIRCDOCS_LSIGN_TEXT"));
/*    btnAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAdd_actionPerformed(e);
      }
    });*/
    btnExit.setText(Messages.get("CIRCDOCS_BTNEXIT_TEXT"));
/*    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnExit_actionPerformed(e);
      }
    });*/
    btnInfo.setText("Info...");
    btnInfo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          btnInfo_actionPerformed(e);
        }
      });
    registerButton(btnAdd);


    asignOK = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        int hitCount = sign.searchDocs("",dbConn.getConnection());
        if (hitCount != 0) {
          Vector val = sign.getValue(hitCount);
          sign.clear();
          sign.setVisible(false);
          setVisible(true,val);
        }
        else {
          JOptionPane.showMessageDialog(null,Messages.get("CIRCDOCS_OPTIONPANE_MSG1"), Messages.get("CIRCDOCS_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
//          sign.clear();
// sa ovim radi, a radi li i bez toga?         sign.setVisible(false);
        }
      }
    };

    asignCancel = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        sign.clear();
        sign.setVisible(false);
      }
    };

    circListLisener = new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmCircDocs_valueChanged(e);
      }
    };


    tblCircDocs.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        tblCircDocs_mouseClicked(e);
      }
    });
    lTitle.setText(Messages.get("CIRCDOCS_LTITLE_TEXT"));
    lAuthor.setText(Messages.get("CIRCDOCS_LAUTHOR_TEXT"));
    lCount.setText(Messages.get("CIRCDOCS_LCOUNT_TEXT"));

    btnAdd.setText(Messages.get("CIRCDOCS_BTNADD_TEXT"));
    if(!added){
      sign.addOKListener(asignOK);
      sign.addCancelListener(asignCancel);
      added = true;
    }

    this.addWindowListener(new WindowAdapter(){
      public void windowActivated(WindowEvent e){
        closed = false;
      }

      public void windowClosing(WindowEvent e){
        closed = true;
        btnExit.doClick();
//        setVisible(false);
      }
    });

    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    tfSignature.setForeground(Color.blue);
    lRealAuthor.setForeground(Color.blue);
    lRealTitle.setForeground(Color.blue);
    Count.setForeground(Color.blue);
    panel1.setLayout(xYLayout1);
    panel1.add(sp1, new XYConstraints(22, 110, 388, 148));
    panel1.add(tfSignature, new XYConstraints(91, 16, -1, -1));
    panel1.add(lSign, new XYConstraints(23, 16, -1, -1));
    panel1.add(btnAdd, new XYConstraints(70, 268, 80, -1));
    panel1.add(btnExit, new XYConstraints(270, 268, 80, -1));
    panel1.add(btnInfo, new XYConstraints(170, 268, 80, -1));
    panel1.add(lTitle, new XYConstraints(23, 37, -1, -1));
    panel1.add(lRealTitle, new XYConstraints(91, 37, 318, -1));
    panel1.add(lAuthor, new XYConstraints(23, 58, -1, -1));
    panel1.add(lRealAuthor, new XYConstraints(91, 58, 318, -1));
    panel1.add(Count, new XYConstraints(91, 88, 318, -1));
    panel1.add(lCount, new XYConstraints(23, 88, -1, -1));
    

/*    lsmCircDocs.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        lsmCircDocs_valueChanged(e);
      }
    });*/
  }

  public void lsmCircDocs_valueChanged(ListSelectionEvent e){
      if(!lsmCircDocs.getValueIsAdjusting()){
        if (tblCircDocs.getRowCount()==1) // 07.12.00.
          tblCircDocs.setRowSelectionInterval(0,0);

        int row = tblCircDocs.getSelectedRow();

        if(row != selected_row)
          selected_row = row;
System.out.println("<CircDocs> Selected row:"+selected_row);
        if (selected_row != -1) {
          value = (String)dmCircDocs.getValueAt(selected_row,1);
          String s = (String)dmCircDocs.getValueAt(selected_row,3);
          String status = (String)tmDefault.getValueAt(selected_row,1);
//          int status = (int)Double.parseDouble(s);
          available = !(status.equals(Messages.get("CIRCDOCS_STATUS_AVAILABLE191")));
          // status == 0;

          String docid = (String)dmCircDocs.getValueAt(selected_row,2);
          Extract.getPrefContents(value, dbConn.getConnection(), true); // bilo docid, false
          tfSignature.setText(Extract.getSigNo());
          lRealAuthor.setText(Extract.getAuthor());
          lRealTitle.setText(Extract.getTitle());
          Count.setText((selected_row + 1 ) + " / " + count);

          signatura=tfSignature.getText();
          autor=lRealAuthor.getText();
          naslov=lRealTitle.getText();
        }
      }
  }

  public void setVisible(boolean vis){
    if (vis)
      lsmCircDocs.addListSelectionListener(circListLisener);
    else
      lsmCircDocs.removeListSelectionListener(circListLisener);
    closed = true;
    super.setVisible(vis);
  }

  public void setVisible(boolean vis, String id){
    if (vis)
      lsmCircDocs.addListSelectionListener(circListLisener);
    else
      lsmCircDocs.removeListSelectionListener(circListLisener);
    closed = true;
//    setPosition(vis,id);
  }

  public void setVisible(boolean vis, Vector id){
    if (vis)
      lsmCircDocs.addListSelectionListener(circListLisener);
    else
      lsmCircDocs.removeListSelectionListener(circListLisener);
    closed = true;
    setPosition(vis,id);
  }


 private void setPosition(boolean vis, Vector docId){
    char cyr = (char)29;
    char lat = (char)28;
    boolean a = false, t = false;
    boolean serijska=false;
    String docid = "";

    lRealAuthor.setText("");
    lRealTitle.setText("");

    String ss="select * from CIRC_DOCS where DOCID in ("+docId.elementAt(0);
    for (int i=1;i<docId.size();i++)
       ss+=","+docId.elementAt(i);
    ss+=") order by CTLG_NO";

    dmCircDocs = new JDBTableModel(dsCircDocs,ss);
    tmDefault = new EditableTableModel();
    tmDefault.addColumn(Messages.get("CIRCDOCS_TMDEFAULT_COL1"));
    tmDefault.addColumn(Messages.get("CIRCDOCS_TMDEFAULT_COL2"));
    tmDefault.addColumn(Messages.get("CIRCDOCS_TMDEFAULT_COL3"));
    count = dmCircDocs.getRowCount();
    if (dmCircDocs.getRowCount()>0){

      for(int k = 0; k < dmCircDocs.getRowCount(); k++){
        Object [] dataRow = new Object[3];
        dataRow[0] = dmCircDocs.getValueAt(k,1);
        String s = (String)dmCircDocs.getValueAt(k,3);
        String type = (String)dmCircDocs.getValueAt(k,5);
        if (type.equals("004") || type.equals("005"))
          serijska=true;
        else
          serijska=false;
        int status = (int)Double.parseDouble(s);
        System.out.println("<CircDocs> setPosition S:"+s);
        if(status == 0)
          dataRow[1] = Messages.get("CIRCDOCS_DATAROW_TXT1");
        else
          if (serijska)
            dataRow[1] = Messages.get("CIRCDOCS_DATAROW_TXT2");
          else
            dataRow[1] = Messages.get("CIRCDOCS_DATAROW_TXT3");

        dataRow[2] = DBConnection.getState996q((String)dmCircDocs.getValueAt(k,6));
        tmDefault.addRow(dataRow);
        tmDefault.setRowEditable(tmDefault.getRowCount()-1,false);
      }
      tblCircDocs.setModel(tmDefault);
      tblCircDocs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tblCircDocs.sizeColumnsToFit(1);

      tblCircDocs.setRowSelectionInterval(0,0); // 21.11.00

//      docid = (String)dmCircDocs.getValueAt(0,2);
      docid = (String)dmCircDocs.getValueAt(0,1);
      Extract.getPrefContents(docid, dbConn.getConnection(), true); // bilo false
      tfSignature.setText(Extract.getSigNo());
      lRealAuthor.setText(Extract.getAuthor());
      lRealTitle.setText(Extract.getTitle());
      super.setVisible(vis);
    }
    else {
      JOptionPane.showMessageDialog(null,Messages.get("CIRCDOCS_OPTIONPANE_MSG3"), Messages.get("CIRCDOCS_OPTIONPANE_MSG4"),JOptionPane.ERROR_MESSAGE);
      setVisible(false);
    }
  }


/*  void btnAdd_actionPerformed(ActionEvent e) {
//    lsmCircDocs.removeListSelectionListener(circListLisener);
System.out.println("<CircDocs> btnAdd_actionPerformed");
//    setVisible(false);
  }

  void btnExit_actionPerformed(ActionEvent e) {
//    lsmCircDocs.removeListSelectionListener(circListLisener);
System.out.println("<CircDocs> btnExit_actionPerformed");
//    setVisible(false);
  }
*/
 
 	void btnInfo_actionPerformed(ActionEvent e) {
 		Info info = new Info(dbConn,this);
 		info.makeOne((String)dmCircDocs.getValueAt(selected_row,1));
 		
	}
 	
  public void addAddListener(ActionListener act){
    alAdd = act;
    btnAdd.addActionListener(alAdd);
  }

  public void removeAddListener(){
    btnAdd.removeActionListener(alAdd);
  }

  public void addExitListener(ActionListener act){
    alExit = act;
    btnExit.addActionListener(alExit);
  }

  public void removeExitListener(){
    btnExit.removeActionListener(alExit);
  }

  public void displaySignNo(){
    sign.setVisible(true);
  }

  public String getValue(){
    return value;
  }

  public String getSignatura(){
    return signatura;
  }

  public String getAutor(){
    return autor;
  }

  public String getNaslov(){
    return naslov;
  }


  public boolean isAvailable(){
    return available;
  }

  void tblCircDocs_mouseClicked(MouseEvent e) {
    if(e.getClickCount() == 2){
      btnAdd.doClick();
    }
  }
}

