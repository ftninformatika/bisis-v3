package com.gint.app.bisis.circ;

import java.util.Vector;
import javax.swing.table.*;

public class EditableTableModel extends DefaultTableModel{
  private Vector editable_rows = new Vector();


  public void addRow(Object rowData[]){
    super.addRow(rowData);
    editable_rows.addElement("true");
  }

  public void addRow(Vector rowData){
    super.addRow(rowData);
    editable_rows.addElement("true");
  }

  public void insertRow(int r, Object rowData[]){
    super.insertRow(r, rowData);
    editable_rows.insertElementAt("true",r);
  }

  public void insertRow(int r, Vector rowData){
    super.insertRow(r, rowData);
    editable_rows.insertElementAt("true",r);
  }

  public void removeRow(int r){
    super.removeRow(r);
    editable_rows.removeElementAt(r);
  }

  public void setRowEditable(int row, boolean editable){
    if(editable)
      editable_rows.setElementAt("true",row);
    else
      editable_rows.setElementAt("false",row);
  }

  public boolean isRowEditable(int row){
    if(((String)editable_rows.elementAt(row)).equals("true"))
      return true;
    else
      return false;
  }

  public boolean isCellEditable(int row, int col){
    return isRowEditable(row);
  }
}

