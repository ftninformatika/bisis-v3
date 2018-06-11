package com.gint.app.bisis.circ.warnings;

import java.util.Vector;

import com.gint.app.bisis.circ.DBConnection;


public class HistoryTableSorter extends TableSorter{
	
	public HistoryTableSorter(HistoryTableModel model) {
	    super(model);
	}
	
	public void setContent(String startDate, String endDate, int branchCode, int warnType, DBConnection dbconn){
		((HistoryTableModel)super.getTableModel()).setContent(startDate, endDate, branchCode, warnType, dbconn);
	}
	
	public void resetModel(){
		((HistoryTableModel)super.getTableModel()).resetModel();
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
