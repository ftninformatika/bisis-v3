package com.gint.app.bisis.circ.warnings;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.infonode.gui.laf.InfoNodeLookAndFeel;

import com.gint.app.bisis.circ.DBConnection;

public class Standalone {

	public static void main(String[] args) {
		Standalone standalone = new Standalone();
		standalone.init();
	}
	
	public void init(){
		String osname = System.getProperty("os.name");
	    if (osname != null && osname.equals("Linux")) {
	      try {
	        UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
	      } catch (UnsupportedLookAndFeelException ex) {
	        System.err.println("Unsupported look and feel: InfoNode");
	      }
	    }
		DBConnection dbconn = new DBConnection();
		Warnings w = new Warnings(dbconn);
		w.setVisible(true);
	}

}
