
package com.gint.app.bisis.circ;

import javax.swing.table.DefaultTableModel;
import com.gint.app.bisis.circ.TableSorter;


public class ZipPlaceTableSorter extends TableSorter{

  
	public ZipPlaceTableSorter(DefaultTableModel model) {
	    super(model);
	}

	public int getColumnCount(){
		return super.getColumnCount();
	}

	public int getRowCount(){
		return super.getRowCount();
	}

	public String getColumnName(int col) {
		return super.getColumnName(col);
	}

	public Object getValueAt(int row, int col){
		return super.getValueAt(row, col);
	}

	public void setValueAt(Object aValue, int row, int col){
		super.setValueAt(aValue, row, col);
	}

	public Class getColumnClass(int col) {
		return super.getColumnClass(col);
	}

	public boolean isCellEditable(int row, int col) {
		return super.isCellEditable(row, col);
	}
}
