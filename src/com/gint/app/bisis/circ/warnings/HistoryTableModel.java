package com.gint.app.bisis.circ.warnings;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis.circ.DBConnection;


public class HistoryTableModel extends AbstractTableModel {
	
	private static final int COL_COUNT = 9;
	private String[] columnNames = {"Broj opomene","Tip opomene", "Datum slanja", "Rok vra\u0107anja", "Broj \u010dlana", "Prezime i Ime", "Inventarni broj", "Datum vra\u0107anja", "Napomena"};
	private Vector data= null;
	private DBConnection dbconn= null;
		
	
	public HistoryTableModel(){
		data = new Vector();
	}
	
	public void setContent(String startDate, String endDate, int branchCode, int warnType, DBConnection dbconn){
		try{
			this.dbconn = dbconn;
			String qry = "";
			if (branchCode == 0){
				if (startDate.equals("") && endDate.equals("")){
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where w.warn_type = " + warnType + " and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}else if (startDate.equals("")){
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where deadline <= '"+ Utils.getDBEndDate(endDate) + "' and warn_type = " + warnType +" "+
						"and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}else if (endDate.equals("")){
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where deadline >= '" + Utils.getDBStartDate(startDate) + "' and warn_type = " + warnType +" "+
						"and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}else {
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where deadline >= '" + Utils.getDBStartDate(startDate) + "' and deadline <= '"+ Utils.getDBEndDate(endDate) + "' "+
						"and warn_type =" + warnType + " " + 
						"and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}
			}else{
				if (startDate.equals("") && endDate.equals("")){
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where w.warn_type = " + warnType + " and w.location = " + branchCode + " and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}else if (startDate.equals("")){
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where deadline <= '"+ Utils.getDBEndDate(endDate) + "' and warn_type = " + warnType +" and w.location = " + branchCode +
						"and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}else if (endDate.equals("")){
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where deadline >= '" + Utils.getDBStartDate(startDate) + "' and warn_type = " + warnType +" and w.location = " + branchCode +
						"and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}else {
					qry = "select w.warn_no, wt.name, w.wdate, w.deadline, w.user_id, s.last_name, s. first_name, w.ctlg_no, w.return_date, w.note " +
						"from warnings w, warning_types wt, single s " +
						"where deadline >= '" + Utils.getDBStartDate(startDate) + "' and deadline <= '"+ Utils.getDBEndDate(endDate) + "' "+
						"and warn_type =" + warnType + " and w.location = " + branchCode + 
						"and w.warn_type = wt.id and w.user_id = s.user_id " +
						"order by w.user_id, w.warn_no";
				}
			}
	
			Statement stmt = dbconn.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(qry);
			
			
			while (rset.next()){
				Vector row = new Vector();
				row.add(rset.getString(1));
				row.add(rset.getString(2));
				row.add(rset.getTimestamp(3));
				row.add(rset.getTimestamp(4));
				row.add(rset.getString(5));
				row.add(rset.getString(6) + " " + rset.getString(7));
				row.add(rset.getString(8));
				row.add(rset.getTimestamp(9));
				row.add(rset.getString(10));
				data.add(row);
			}
			rset.close();
			stmt.close();
			fireTableDataChanged();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void resetModel(){
		data.removeAllElements();
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		return COL_COUNT;
	}
	
	public int getRowCount(){
		return data.size();
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public Object getValueAt(int row, int col){
		
		return ((Vector)data.get(row)).get(col);
		
	}
	
	public void setValueAt(Object aValue, int row, int col){
		((Vector)data.get(row)).set(col, aValue);
		fireTableDataChanged();
	}
	
	public Class getColumnClass(int col) {
		switch (col){
		 case 0: return String.class;
		 case 1: return String.class;
		 case 2: return Timestamp.class;
		 case 3: return Timestamp.class;
		 case 4: return String.class;
		 case 5: return String.class;
		 case 6: return String.class;
		 case 7: return Timestamp.class;
		 case 8: return String.class;
		 default: return String.class;
		}
    }
	
	public boolean isCellEditable(int row, int col) {
        return false;
    }
	
}
