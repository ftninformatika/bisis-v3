package com.gint.app.bisis.circ;

import javax.swing.event.*;

/*

  
*/
public class JDBLookupModel extends JDBComboBoxModel{

  private DataSet dataSet, lookupSet;
  private Column dataCol, lookupCol;
  private int dataPos, lookupPos, showPos;
  protected boolean first = true;

/*
  lookupDSet - DataSet iz kojeg se uzimaju vrednosti
  dataDSet - DataSet u koji se upisuje vrednost
  showColName - naziv kolone iz lookupDSet-a, cijim sadrzajem se puni ComboBox
  lookupColName - naziv kolone iz lookupDSet-a koja predstavlja vezu sa dataDSet-om
  dataColName - naziv kolone iz dataDSet-a koja predstavlja vezu sa lookupDSet-om
*/
  public JDBLookupModel(DataSet lookupDSet, String showColName, DataSet dataDSet, String dataColName, String lookupColName){
    super(lookupDSet, showColName);
    lookupPos = lookupDSet.getColumnIndex(lookupColName);
    lookupCol = lookupDSet.getColumn(lookupPos);
    lookupSet = lookupDSet;
    dataSet = dataDSet;
    showPos = super.getColumnPosition();
    dataPos = dataSet.getColumnIndex(dataColName);
    dataCol = dataSet.getColumn(dataPos);    
    addChangeListener();
  }

  public void setSelectedItem(Object anItem){
    first = false;
    super.setSelectedItem(anItem);
    if(dataSet != null){
      String s = lookupSet.getRow().getData(lookupPos);
      dataSet.getRow().setData(s,dataPos);
    }
  }

  public void setData(){
    setSelectedItem(getSelectedItem());
  }

  public String getData(){
    return lookupSet.getRow().getData(lookupPos);
  }

// post upisuje vrednost u odgovarajucu kolonu DataSet-a  
  public void post(){
    post(dataSet, dataPos, dataChanged);
  }

  public void post(boolean force){
    post(dataSet, dataPos, force);
  }

  public void addChangeListener(){
    dataSet.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if(((String)e.getSource()).startsWith("NAVIGATE")){
          int i = lookupSet.setCurrentRow(new Column[] {lookupCol}, new String[]{ (String)dataSet.getRow().getDBData(dataPos)});
          first = true;
          if (i < 0)
            setSelectedItem((String) lookupSet.getRow().getData(0));
          else
            setSelectedItem((String) lookupSet.getRow().getData(showPos));
        }
      }
    });
  }

  public void removeChangeListener(){
    dataSet.removeChangeListener();
  }
}
