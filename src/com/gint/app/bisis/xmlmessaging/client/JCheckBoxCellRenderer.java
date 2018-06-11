/*
 * Created on 2004.10.5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.client;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JCheckBoxCellRenderer extends JCheckBox implements
		TableCellRenderer {

	public JCheckBoxCellRenderer() {
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(
			JTable jTable, Object obj, boolean isSelected, boolean hasFocus,
			int row, int column) {
//			 set color & border here
			
			return this;
	}
	

}