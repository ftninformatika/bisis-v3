package com.gint.app.bisis.circ;

import java.sql.*;

// Struktura u kojoj se nalaze podaci odnosno vrednosti vrste tabele (samo iz jednog reda tabele)
public class DataRow{
  private DataSet dataSet;
  private Column [] columns;
  private String [] data;
  private boolean caseInsensitive = false;
  private int id = -1;
  private int rowState = RowState.NOCHANGE;

  public DataRow(DataSet dataSet){
    this.dataSet = dataSet;
    columns = this.dataSet.getColumns();
    data = new String[columns.length];
  }

  public void setRowState(int state){
    rowState = state;
  }

  public int getRowState(){
    return rowState;
  }

  public void setID(int newID){
    id = newID;
  }

  public int getID(){
    return id;
  }

// postavlja sadrzaj dat u kolonu na poziciji col
  public boolean setData(String dat, int col){
    if(col < columns.length){
      if (dat == null) 
        dat = "";
      else if (dat.equals("null"))
        dat = "";

      if ((columns[col].getDataType()==Types.TIMESTAMP || 
           columns[col].getDataType()==Types.DATE) && !dat.equals(""))
        dat=DBConnection.toDatabaseDate(dat);

      data[col] = dat;
      if (rowState == RowState.NOCHANGE)
        rowState = RowState.TOUPDATE;
      return true;
    }
    else
      return false;
  }

  public boolean setData(int dat, int col) {
    if (col < columns.length) {
      data[col] = java.lang.Integer.toString(dat);
      if(rowState == RowState.NOCHANGE)
        rowState = RowState.TOUPDATE;
      return true;
    }
    else
      return false;
  }

  public void setData(String data, String name) {
    setData(data, dataSet.getColumnIndex(name));
  }

  public Object getDBData(int col) {
    Object dataObject = data[col];
    if(dataObject == null)
      return "null";
    else if (dataObject.equals(""))
      return "null";
    else if((columns[col].getDataType()==Types.TIMESTAMP)
         || (columns[col].getDataType()==Types.VARCHAR)
         || (columns[col].getDataType()==Types.CHAR)
         || (columns[col].getDataType()==Types.DATE))
      return dataObject = "\'"+dataObject+"\'" ;
    else
      return data[col];
  }

  public String getData(int col){
    if(data[col] == null )
      return "";
    else {
      String res = data[col];
      if ((columns[col].getDataType() == Types.TIMESTAMP || 
           columns[col].getDataType()==Types.DATE) && 
           !res.equals(""))
        res = DBConnection.fromDatabaseDate(res);
      return res;
    }
  }

  public String getData(String name){
    return getData(dataSet.getColumnIndex(name));
  }

  public Column [] getColumns(){
    return columns;
  }

  public void setColumns(Column [] cols){
    columns = cols;
  }

  public Column getColumn(int i){
    return columns[i];
  }


  private Column getColumnIndex(Column[] someCols, String col){
    boolean found = false;
    int i = 0;
    while(!found && i < someCols.length){
      found = col.equals(someCols[i].getName());
      i++;
    }
    if(found)
      return someCols[i-1];
    else
      return null;
  }

  public void setInsensitive(boolean insens){
    caseInsensitive = insens;
  }

  public String toString(){
    String toStr = new String();
    for(int i = 0; i < columns.length; i++)
      toStr += (columns[i].getName() + ":" + getData(i) + "\n");
    return toStr;
  }
}
