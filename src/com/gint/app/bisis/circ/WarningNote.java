package com.gint.app.bisis.circ;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.JTextField;

public class WarningNote extends JDialog {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;

	/**
	 * This is the default constructor
	 */
	public WarningNote(Dialog owner, boolean modal) {
		super(owner, modal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(200, 100);
		this.setContentPane(getJContentPane());
		this.setTitle("");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("Napomena:");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(jLabel, gridBagConstraints);
			jContentPane.add(getJTextField(), gridBagConstraints1);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}
	
	public String getValue(){
		return getJTextField().getText().trim();
	}
	
	public void addListener(java.awt.event.KeyAdapter ka){
		jTextField.addKeyListener(ka);
	}

}
