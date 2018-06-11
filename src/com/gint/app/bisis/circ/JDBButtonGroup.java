package com.gint.app.bisis.circ;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class JDBButtonGroup extends ButtonGroup{
  protected EventListenerList listenerList = new EventListenerList();
  private DataSet dataSet;
  private Column column = null;
  private int colPosition = -1;
  protected boolean dataChanged = false;
  private boolean postOnFocusLost = true;

  public JDBButtonGroup() {
    super();
    if (dataSet != null)
      addListeners();
  }

  public void post(DataSet dSet, int col, boolean cond){
    if(cond){
//      System.out.println("Posting.."+getSelectedItem());

System.out.println("JDBButtonGroup - post int="+getSelectedButton().getPosition());
      int val = getSelectedButton().getPosition();
      dSet.getRow().setData(val,col);
      dataChanged = false;
    }
  }

// post upisuje vrednost u odgovarajucu kolonu DataSet-a
  public void post(){
    post(dataSet, colPosition, dataChanged);
  }

  public void post(boolean force){
    post(dataSet, colPosition, force);
  }

  public DataSet getDataSet(){
    return dataSet;
  }

  public void setDataSet(DataSet dSet) {
    if (dataSet == null)
      dataSet = dSet;
      if(colPosition != -1)
        addListeners();
  }

  public void setColumn(String colName){
    if(colPosition == -1){
      colPosition = dataSet.getColumnIndex(colName);
      column = dataSet.getColumn(colPosition);
      if(dataSet != null) addListeners();
    }
  }

  public boolean isDataChanged(){
    return dataChanged;
  }

  public void setDataChanged(boolean chng){
    dataChanged = chng;
  }

  public JDBRadioButton getButton(int pos) {
    JDBRadioButton elem = new JDBRadioButton();
    for ( Enumeration e = getElements() ; e.hasMoreElements() ;) {
      elem =(JDBRadioButton) e.nextElement();
      if (elem.getPosition() == pos)
        return elem;
    }
    return null;
  }

  public JDBRadioButton getSelectedButton() {
    ButtonModel but;
    but = getSelection();
    return ((JDBRadioButton) but);
  }

  public void addListeners(){
    dataSet.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if (dataSet.getEventType() != State.EMPTY)
          getButton((int) Integer.parseInt(dataSet.getRow().getData(colPosition))).setSelected(true);
      }
    });
  }
}