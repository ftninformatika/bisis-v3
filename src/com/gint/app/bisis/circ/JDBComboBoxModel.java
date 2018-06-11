package com.gint.app.bisis.circ;

import javax.swing.*;
import javax.swing.event.*;

public class JDBComboBoxModel implements ComboBoxModel{

  protected EventListenerList listenerList = new EventListenerList();
  private DataSet dataSet;
  private Column showCol;
  private int colPos;
  protected boolean dataChanged = false;
  private String selectedItem;
  private String [] items;
  private int size = -1;

  public JDBComboBoxModel(DataSet dSet, String colName){
    super();
    dataSet = dSet;
    colPos = dataSet.getColumnIndex(colName);
    showCol = dataSet.getColumn(colPos);
    size = dataSet.getSize();
    items = new String[size];
    dataSet.first();
    setSelectedItem((String)dataSet.getRow().getData(colPos));
    for(int i = 0; i < size; i++){
      items[i] = (String)dataSet.getRow().getData(colPos);
      dataSet.next();
    }
  }


  public Object getSelectedItem(){
    return selectedItem;
  }

  public void setSelectedItem(Object anItem){
    dataSet.setCurrentRow(new Column[] {showCol}, new String[] {(String)anItem});
    selectedItem = (String) anItem;
    fireListChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,-1,-1));
  }

  public void post(DataSet dSet, int col, boolean cond){
    if(cond){
      dSet.getRow().setData((String)getSelectedItem(),col);
      dataChanged = false;
    }
  }

// post upisuje vrednost u odgovarajucu kolonu DataSet-a
  public void post(){
    post(dataSet, colPos, dataChanged);
  }

  public void post(boolean force){
    post(dataSet, colPos, force);
  }

  public DataSet getDataSet(){
    return dataSet;
  }

  public int getColumnPosition(){
    return colPos;
  }

  public Column getColumn(){
    return showCol;
  }

  public void addListDataListener(ListDataListener l) {
    listenerList.add(ListDataListener.class, l);
  }

  public void removeListDataListener(ListDataListener l) {
    listenerList.remove(ListDataListener.class, l);
  }

  protected void fireListChanged(ListDataEvent e) {
	  // Guaranteed to return a non-null array
	  Object[] listeners = listenerList.getListenerList();
	  // Process the listeners last to first, notifying
	  // those that are interested in this event
	  for (int i = listeners.length-2; i>=0; i-=2) {
	    if (listeners[i]==ListDataListener.class) {
		    ((ListDataListener)listeners[i+1]).contentsChanged(e);
	    }
	  }
  }

  public int getSize(){
    return size;
  }

  public Object getElementAt(int position){
    return items[position];
  }

  public boolean isDataChanged(){
    return dataChanged;
  }

  public void setDataChanged(boolean chng){
    dataChanged = chng;
  }
}
