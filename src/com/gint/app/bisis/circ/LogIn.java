package com.gint.app.bisis.circ;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;

public class LogIn extends EscapeDialog{
  DBConnection dbConn;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  ActionListener alOK, alCancel;
  FlowLayout flowLayout2 = new FlowLayout();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JPasswordField jPasswordField1 = new JPasswordField();
  FlowLayout flowLayout3 = new FlowLayout();
  
  //OKI
  XYLayout xyLayout1=new XYLayout();
  XYLayout xyLayout2=new XYLayout();
  XYLayout xyLayout3=new XYLayout();
  //OKI  
  
  
  private int retVal = -1;

  public LogIn(JFrame frame, String title,boolean modal, DBConnection dbc) {
    super(frame, title, modal);
    dbConn = dbc;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public LogIn() {
    this(null, "", false, null);
  }

  public LogIn(String title, DBConnection db, boolean modal){
    this(null,title,modal,db);
  }

  void jbInit() throws Exception {
    panel1.setMaximumSize(new Dimension(285, 115));
    panel1.setPreferredSize(new Dimension(285, 115));
    panel1.setMinimumSize(new Dimension(285, 115));
    panel1.setSize(new Dimension(285, 115));
    jPanel1.setLayout(flowLayout2);
    jPanel2.setLayout(xyLayout1);
    btnOK.setPreferredSize(new Dimension(85, 25));
    this.getRootPane().setDefaultButton(btnOK);
    btnOK.setText(Messages.get("LOGIN_BTNOK_TEXT"));
    btnCancel.setText(Messages.get("LOGIN_BTNCANCEL_TEXT"));
    flowLayout1.setHgap(38);
    flowLayout2.setHgap(15);
    jPanel3.setLayout(xyLayout3);
    jLabel2.setText(Messages.get("LOGIN_LABEL2_TEXT"));
    jTextField2.setPreferredSize(new Dimension(80, 20));
    jTextField2.setAlignmentY((float) 20.0);
    jTextField2.setMinimumSize(new Dimension(80, 20));
    jPasswordField1.setMaximumSize(new Dimension(80, 20));
    jPasswordField1.setPreferredSize(new Dimension(80, 20));
    jPasswordField1.setMinimumSize(new Dimension(80, 20));
    flowLayout3.setVgap(10);

    jTextField2.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          btnOK.doClick();
      }
    });
    jPasswordField1.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
          btnOK.doClick();
      }
    });

    jLabel1.setText(Messages.get("LOGIN_LABEL1_TEXT"));
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);
    jPanel3.add(jLabel2,new XYConstraints(55, 10, -1, -1));
    jPanel3.add(jTextField2,new XYConstraints(150, 8, -1, -1));
    panel1.add(jPanel3, BorderLayout.NORTH);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnCancel, null);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel2.add(jLabel1,new XYConstraints(55, 10, -1, -1));
    jPanel2.add(jPasswordField1,new XYConstraints(150, 8, -1, -1));
    panel1.add(jPanel2, BorderLayout.CENTER);
    jTextField2.requestDefaultFocus();

    btnOK.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String pass = new String(jPasswordField1.getPassword());

        String query = "select bibid, bibtip, bibnobr, opis_bibl from bibliotekar where bibid = '" + jTextField2.getText().trim() + "' and bibtajozn = '"+pass+"'";
        try{
          Statement stmt = dbConn.getConnection().createStatement();
          ResultSet rset = stmt.executeQuery(query);
          if(rset.next()){
            dbConn.setOfficial(rset.getObject(1).toString());
            if (rset.getObject(4) != null){
            	dbConn.setOfficialName(rset.getObject(4).toString());
            }else {
            	dbConn.setOfficialName(rset.getObject(1).toString());
            }
            if (rset.getString(2).equals("CR") && rset.getInt(3) == 1){
            	dbConn.setOfficialAdmin(false);
            }else{
            	dbConn.setOfficialAdmin(true);
            }
            retVal = 1;
            setVisible(false);
          }
          else{
            JOptionPane.showMessageDialog(null, Messages.get("LOGIN_OPTIONPANE_MSG1"), Messages.get("LOGIN_OPTIONPANE_MSG2"), JOptionPane.ERROR_MESSAGE);
            retVal = 0;
          }
          rset.close();
          stmt.close();
          dbConn.getConnection().commit();
        }catch(SQLException ex){
          retVal = 0;
          ex.printStackTrace();
          setVisible(false);
        }

      }
    });

    btnCancel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        retVal = 0;
        setVisible(false);
      }
    });
  }

  public int setModalVisible(){
    jTextField2.requestFocus();
    setVisible(true);
    return retVal;
  }

  public void clear(){
    jTextField2.setText("");
  }
}
