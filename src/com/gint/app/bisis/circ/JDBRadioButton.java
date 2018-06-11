package com.gint.app.bisis.circ;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class JDBRadioButton extends JRadioButton {
  private int position=-1;
  protected EventListenerList listenerList = new EventListenerList();
  private DataSet dataSet;
  private Column column = null;
  private int colPosition = -1;
  protected boolean dataChanged = false;

  public JDBRadioButton() {
    super();
  }
  
  

  public JDBRadioButton(int pos) {
    super();
    position = pos;
    if (dataSet != null)
    	addListeners();
    
  }
  
 
    public void post(DataSet dSet, int col, boolean cond){
    if(cond){
      if (isSelected())
        dSet.getRow().setData(position,col);
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

  public int getPosition() {
    return position;
  }

  public void addListeners(){
    dataSet.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
      	
        String s = dataSet.getRow().getData(colPosition);
        if (s != null && !s.equals("") && !s.equals(" "))
          if (dataSet.getEventType() != State.EMPTY)
            if ((int) Integer.parseInt(s) == position) {            	
              setSelected(true);
              doClick();
            }
      }
    });

  addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(!isDataChanged()) setDataChanged(true);
        post();
      }
  });
  }
}