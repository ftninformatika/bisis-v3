package com.gint.app.bisis.circ;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class JDBLabel extends JLabel{

  private DataSet dataSet = null;
  private Column column = null;
  private int colPosition = -1;
  private boolean dataChanged = false;
  private boolean newData = false;
  private boolean changeble = true;

  public JDBLabel(){
    super();
  }

  public JDBLabel(DataSet dSet, String colName){
    super();
    dataSet = dSet;
    colPosition = dataSet.getColumnIndex(colName);
    column = dataSet.getColumn(colPosition);
    addListeners();
  }

  public void setText(String t){
    super.setText(t);
    dataChanged = true;
    if(dataSet != null) post();
  }


  public void setDataSet(DataSet dSet){
    if(dataSet == null){
      dataSet = dSet;
      if(colPosition != -1) addListeners();
    }
  }

  public void setColumn(String colName){
    if(colPosition == -1){
      colPosition = dataSet.getColumnIndex(colName);
      column = dataSet.getColumn(colPosition);
      if(dataSet != null) addListeners();
    }
  }


  public void addListeners(){
    dataSet.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if(changeble)
          setText((String)dataSet.getRow().getData(colPosition));
      }
    });

    this.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(!dataChanged) dataChanged = true;
      }
    });
  }

  public DataSet getDataSet(){
    return dataSet;
  }

  public Column getColumn(){
    return column;
  }

  public void post(){
//do something..
    if(dataChanged){
      dataSet.getRow().setData(getText(),colPosition);
    }
  }

  public void setChangeble(boolean ch){
    changeble = ch;
  }
}
