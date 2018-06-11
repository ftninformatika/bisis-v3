/*
 * Created on 2004.10.5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.client;

import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author mikiz
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JEditorPaneCellRenderer extends JEditorPane implements
    TableCellRenderer {

  public JEditorPaneCellRenderer() {
    super("text/html", "");
    //setLineWrap(true);
    //setWrapStyleWord(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
   *      java.lang.Object, boolean, boolean, int, int)
   */
  public Component getTableCellRendererComponent(JTable jTable, Object obj,
      boolean isSelected, boolean hasFocus, int row, int column) {
    // set color & border here
    setText((obj == null) ? "" : obj.toString());
    setSize(jTable.getColumnModel().getColumn(column).getWidth(),
        getPreferredSize().height);
    if (jTable.getRowHeight(row) != getPreferredSize().height) {
      jTable.setRowHeight(row, getPreferredSize().height);
    }
    return this;
  }

}