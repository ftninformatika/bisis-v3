package com.gint.app.bisis.editor.report.izvrsnoVece;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.MainFrame;
import com.gint.util.gui.WindowUtils;

public class SredstvaDlg extends JDialog {
	  
	  public SredstvaDlg(final MainFrame mf) {
		super(mf, "Osnovna sredstva", true);
	    this. mf=mf;
	    init();
	  }
      public void init(){
    	  setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	  btnOK.setFocusable(false);
  	      getRootPane().setDefaultButton(btnOK);
  	      btnOK.addActionListener(new ActionListener() {
  	      public void actionPerformed(ActionEvent e) {
  	        handleOK();
  	       }
  	      });
  	      btnCancel.setFocusable(false);
    	  btnCancel.addActionListener(new ActionListener() {
    	  	  public void actionPerformed(ActionEvent e) {
    	  	      setVisible(false);
    	  	   }
    	  });
  	      btnChooseFile.addActionListener(new ActionListener(){
  	      public void actionPerformed(ActionEvent e){
  	    		fc = new JFileChooser();
  			    fc.showSaveDialog(mf);
  			    if(fc.getSelectedFile()!=null)
  			    tfFileName.setText(fc.getSelectedFile().getAbsolutePath());
  	    	}
  	      });

  	      btnChooseFile.setFocusable(false);
          GridBagLayout grid = new GridBagLayout();
          GridBagConstraints c=new GridBagConstraints();
  	      getContentPane().setLayout(grid);
  	      c.gridy=1;
  	      c.insets=new Insets(15, 5, 5, 5);
  	      getContentPane().add(new JLabel("Odredi\u0161te za fajl:"),c);
  	      getContentPane().add(tfFileName,c);
  	      c.gridwidth=GridBagConstraints.REMAINDER;
  	      getContentPane().add(btnChooseFile,c);
  	      c.gridy=3;
  	      c.insets=new Insets(5, 5, 5, 105);
  	      getContentPane().add(btnOK,c);
  	      c.insets=new Insets(5, 65, 5, 5);
  	      c.gridx=1;
  	      getContentPane().add(btnCancel,c);
  	      
  	      pack();
  	      WindowUtils.centerOnScreen(this);
    	  
      }
	  private void handleOK() {
	    String fileName = tfFileName.getText().trim();
	    if ("".equals(fileName))
	      return;
	    Connection conn=Environment.getConnection();
        Statement stmt;
        try{
        BufferedWriter importFile = new BufferedWriter(new OutputStreamWriter(
	    		new FileOutputStream(fileName),"UTF-8"));
	    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery("SELECT * from Documents d");
		while (rs.next()) {
   		 Sredstva.toFile(rs.getString("DOC_ID"),rs.getString("DOCUMENT"),importFile); //obrađuje jedan zapis i pise u fajl
   		 }
   		 stmt.close();
   		 importFile.close();
   		 setVisible(false);
   		 JOptionPane.showMessageDialog(null, "Fajl je snimljen.", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
        	e.printStackTrace();
        }
	 
	  }
	  
	  JButton btnOK = new JButton("Start");
	  JButton btnCancel = new JButton("Odustani");
	  JFileChooser fc;
	  JTextField tfFileName = new JTextField(20);
	  JButton btnChooseFile = new JButton("...");
	  private MainFrame mf;
	}
