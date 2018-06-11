package com.gint.app.bisis.circ;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.gint.util.file.FileUtils;

public class ZipPlaceDlg extends JDialog {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private DefaultTableModel model = null;
	private ZipPlaceTableSorter sorter = null;
	
	public ZipPlaceDlg() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(300, 400);
		this.setTitle("");
		this.setContentPane(getJContentPane());
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable(getTableModel());
			getTableModel().setTableHeader(jTable.getTableHeader());
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.setDefaultEditor(Object.class,null);
			jTable.addKeyListener(new KeyAdapter(){
			      public void keyPressed(KeyEvent e){
			        if(e.getKeyCode()==KeyEvent.VK_ENTER){
			          jButton.doClick();
			        }
			      }
			    });
		}
		return jTable;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("OK");
		}
		return jButton;
	}
	
	private ZipPlaceTableSorter getTableModel(){
		if (model == null){
			Vector data = new Vector();
			Vector columnNames = new Vector();
			columnNames.add("Broj");
			columnNames.add("Mesto");
			try{
				String line = "";
				StringTokenizer st;
				BufferedReader in = new BufferedReader(new InputStreamReader
		                (new FileInputStream (FileUtils.getClassDir(getClass()) + "/places.txt"), "UTF-8"));
				try{
					while ((line = in.readLine()) != null) {
						line = line.trim();
		                if (!line.equals("")){
		                	st = new StringTokenizer(line, " ");
		                	if (st.hasMoreTokens()) {
		                		Vector temp = new Vector();
		                        temp.add(st.nextToken().trim());
		                        String tmp = "";
		                        while (st.hasMoreTokens()) {
		                          tmp = tmp + st.nextToken().trim() + " ";
		                        }
		                        temp.add(tmp.trim());
		                        data.add(temp);
		                        
		                     }
		                }
					}
				}catch (IOException ex){
	            	ex.printStackTrace();
	            }
			}catch(Exception e){
				
			}
			model = new DefaultTableModel(data, columnNames);
			sorter = new ZipPlaceTableSorter(model);
			sorter.setTableHeader(getJTable().getTableHeader());
			
		}
		return sorter;
	}
	
	public void addListener(java.awt.event.ActionListener l){
		jButton.addActionListener(l);
	}
	
	public void removeListener(java.awt.event.ActionListener l){
		jButton.removeActionListener(l);
	}
	
	public String getZip(){
		if (getJTable().getSelectedRow() != -1){
			return (String)getJTable().getValueAt(getJTable().getSelectedRow(), 0);
		}else{
			return "";
		}
	}
	
	public String getPlace(){
		if (getJTable().getSelectedRow() != -1){
			return (String)getJTable().getValueAt(getJTable().getSelectedRow(), 1);
		}else{
			return "";
		}	
	}

}
