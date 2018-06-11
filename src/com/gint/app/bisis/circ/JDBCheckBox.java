package com.gint.app.bisis.circ;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
/*
  Kod ove komponente je potrebno da se programski odradi post metoda,
  odnosno snimanje podataka u bazu
*/

public class JDBCheckBox extends JCheckBox{

  private DataSet dataSet = null;
  private Column column = null;
  private int colPosition = -1;
  private boolean dataChanged = false;
  private boolean newData = false;
  private boolean checked = false;
  private String trueValue = new String("1");
  private String falseValue = new String("0");

  public JDBCheckBox(){
    super();
  }

  public JDBCheckBox(DataSet dSet, String colName){
    super();
    dataSet = dSet;
    colPosition = dataSet.getColumnIndex(colName);
    column = dataSet.getColumn(colPosition);
    addListeners();
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
        String s = (String)dataSet.getRow().getData(colPosition);
        int status;
        if (s == null || s.equals(""))
          status = 0;
        else
          status = (int)Double.parseDouble(s);
        setState(""+status);
      }
    });

    addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        checked = isSelected();
        if(!dataChanged) dataChanged = true;
        post();
      }
    });
  }

  public void setTrue(String val){
    trueValue = new String(val);
  }

  public void setFalse(String val){
    falseValue = new String(val);
  }

  public DataSet getDataSet(){
    return dataSet;
  }

  public void printValue(){
    System.out.println("<JDBCheckBox> DataSet:"+ dataSet +" ColValue:"+dataSet.getRow().getDBData(colPosition));
  }

  public Column getColumn(){
    return column;
  }

  public void post(){
    post(dataChanged);
  }


  public void post(boolean force){
    String temp;
    if(force){
      checked = isSelected();

      if (checked == true)
        temp = trueValue;
      else
        temp = falseValue;
      dataSet.getRow().setData(temp,colPosition);
      dataChanged = false;
    }
  }

  public void setState(String val){
    if(trueValue.equals(val)){
       checked = true;
       setSelected(true);
    }
    else if(falseValue.equals(val)){
       checked = false;
       setSelected(false);
    }
  }

  public void setSelected(boolean sel){
    super.setSelected(sel);
    dataChanged = true;
    if (dataSet != null) post();
  }
}
