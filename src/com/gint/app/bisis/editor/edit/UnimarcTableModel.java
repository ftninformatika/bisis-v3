package com.gint.app.bisis.editor.edit;

import java.util.Vector;
import javax.swing.table.*;

public class UnimarcTableModel extends AbstractTableModel {

  public static final int COL_COUNT = 5;

  public int getRowCount() {
    int n = fields.size();
    int retVal = n / COL_COUNT;
    if (n % COL_COUNT > 0)
      retVal++;
    return retVal;
  }

  public int getColumnCount() {
    return COL_COUNT;
  }

  public Object getValueAt(int row, int col) {
    int index = row * COL_COUNT + col;
    if (index < fields.size())
      return fields.elementAt(index);
    else
      return null;
  }

  public void insertValueAt(Object obj, int row, int col) {
    // inicijalizacija
    ((Field)obj).init();
    int index = row * COL_COUNT + col;
    fields.insertElementAt(obj, index);
    fireTableDataChanged();
  }

  public void addValue(Object obj) {
    ((Field)obj).init();
    fields.addElement(obj);
    fireTableDataChanged();
  }

  public boolean isCellEditable(int row, int col) {
    return false;
  }

  public Class getColumnClass(int col) {
    return Field.class;
  }

  public Vector getFields() {
    return fields;
  }

  public int getFieldIndex(int row, int col) {
    int retVal = row * COL_COUNT + col;
    if (retVal >= fields.size())
      retVal = -1;
    return retVal;
  }

  public void setFields(Vector fields) {
    // a sada ona inicijalizacija
    for (int i = 0; i < fields.size(); i++)
      ((Field)fields.elementAt(i)).init();

    this.fields = fields;
    fireTableDataChanged();
  }

  public boolean isThereAFieldBelow(int row, int col) {
    return (row*COL_COUNT+col)+COL_COUNT < (fields.size());
  }

  public boolean isThereAFieldRight(int row, int col) {
    return row*COL_COUNT+col < (fields.size()-1);
  }

  /** Vraca sirinu najsireg polja u datoj koloni */
  public int getMaxWidth(int colIndex) {
    int retVal = -1;
    int currWidth;
    int n = fields.size();
    for (int i = 0; i < getRowCount(); i++) {
      if (i*COL_COUNT+colIndex < n) {
        currWidth = ((Field)fields.elementAt(i*COL_COUNT+colIndex)).getFieldWidth();
        if (currWidth > retVal)
          retVal = currWidth;
      }
    }
    return retVal;
  }
  
  public boolean hasField(String name) {
    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field)fields.elementAt(i);
      if (f.getName().equals(name))
        return true;
    }
    return false;
  }

  private Vector fields = new Vector();
}
