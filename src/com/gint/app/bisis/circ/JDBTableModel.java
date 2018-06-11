package com.gint.app.bisis.circ;

import java.text.DateFormat;
import java.sql.*;
import javax.swing.table.*;
import javax.swing.event.*;

/*
  JDBTableModel se koristi kao veza izmedju DataSet-a i komponente JTable.
  Sadrzaj tabele se puni u strukturu data, koja je matrica stringova. Sve
*/

public class JDBTableModel extends AbstractTableModel{

  private DataSet dataSet;
  private String [][] data;
  private String [] rows;
  private int [] def;
  private String [] val;
  private int defSize = 0;
  private boolean [] changed;
  private int cols = 0;
  private int size = 0;
  private boolean editable = true;
  private String query = "";
  private String [] keyVals;
  private int keyNo;
  private DateFormat dateFormatter;

  public JDBTableModel(DataSet dSet){
    super();
    dataSet = dSet;
    keyNo = dataSet.getKeyCols().length;
    keyVals = new String[keyNo];
    addTableModelListener( new TableModelListener(){
      public void tableChanged(TableModelEvent e){
      }
    });
    reload();
  }

  public JDBTableModel(DataSet dSet, String qry){
    super();
    dataSet = dSet;
    keyNo = dataSet.getKeyCols().length;
    keyVals = new String[keyNo];
    query = qry;
    addTableModelListener( new TableModelListener(){
      public void tableChanged(TableModelEvent e){
      }
    });
    reloadSet();
  }

  public void reloadSet(){
    long l1 = System.currentTimeMillis();
    cols = dataSet.getColumnCount();
    int i = 0;
    try{
      size = 0;
      Statement stmt = dataSet.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(query);
      while(rset.next()){
        expand(""+i);
        fillColumns(rset,i);
        i++;
      }
      stmt.close();
      rset.close();
    }catch (SQLException e){
      e.printStackTrace();
    }
    fireTableChanged(new TableModelEvent(this));
    long l2 = System.currentTimeMillis();
  }


  public void reload(){
    long l1 = System.currentTimeMillis();
    cols = dataSet.getColumnCount();
    size = dataSet.getSize();
    rows = new String[size];
    changed = new boolean[size];
    data = new String [size][cols];
    dataSet.first();
    for(int i = 0; i < size; i++){
      rows[i] = new String(dataSet.getNavigateValue());
      changed[i] = false;
      for(int j = 0; j < cols; j++){
        data[i][j] = (String)dataSet.getRow().getData(j);
      }
      dataSet.next();
    }
    fireTableChanged(new TableModelEvent(this));
    long l2 = System.currentTimeMillis();
  }

// dodaje prazan red u model (a samim tim i u tabelu)
  public void expand(){
    expand("");
  }


  private void expand(String r){
    String [][] temp = new String [size][cols];
    String [] tempRows = new String [size];
    boolean [] tempChanged = new boolean [size];
    long l1 = System.currentTimeMillis();
    for(int i = 0; i < size; i++){
      tempRows[i] = rows[i];
      tempChanged[i] = changed[i];
      for(int j = 0; j < cols; j++)
        temp[i][j] = data[i][j];
    }

    size++;
    data = new String[size][cols];
    rows = new String[size];
    changed = new boolean[size];
    for(int i = 0; i < size-1; i++){
      for(int j = 0; j < cols; j++)
        data[i][j] = temp[i][j];
      rows[i] = tempRows[i];
      changed[i] = tempChanged[i];
    }
    changed[size -1] = false;
    if(r.equals(""))
      rows[size - 1] = "";
    else
      rows[size - 1] = r;

    fireTableChanged(new TableModelEvent(this, size-1,size-1,TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
    long l2 = System.currentTimeMillis();
  }


  private void shrink(int row){
    String [][] temp = new String[size][cols];
    String [] tempRows = new String [size];
    boolean [] tempChanged = new boolean [size];
    int l = 0;
    long l1 = System.currentTimeMillis();
    for(int k = 0; k < size; k++){
      tempRows[k] = rows[k];
      tempChanged[k] = changed[k];
      for(int j = 0; j < cols; j++)
        temp[k][j] = data[k][j];
    }
    size--;
    data = new String[size][cols];
    rows = new String[size];
    changed = new boolean[size];
    for(int i = 0; i < size; i++){
      if(l == row) l++;
      rows[i] = tempRows[l];
      changed[i] = tempChanged[l];
      for(int j = 0; j < cols; j++)
        data[i][j] = temp[l][j];
      l++;
    }
    fireTableChanged(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS,TableModelEvent.DELETE));
    long l2 = System.currentTimeMillis();
  }

  public DataSet getDataSet(){
    return dataSet;
  }

  public int getRowCount(){
    return size;
  }

  public void setRowCount(){
    size = dataSet.getSize();
  }

  public int getColumnCount(){
    return cols;
  }

  public Object getValueAt(int row, int col){
    if(row > -1 && col > -1)
      return data[row][col];
    else
      return null;
  }

  public void setValueAt(Object anObject, int row, int col){
    if(row > -1 && col > -1){
      changed[row] = true;
      data[row][col] = (String) anObject;
    }
  }

// snima sadrzaj tabele direktno u bazu
  public void saveData(){
    boolean insert = false;
    boolean tmp = false;
    for(int i = 0; i < size; i++){
      if(changed[i]){
        tmp = true;
        if((rows[i] == null) || (rows[i].equals(""))){
          dataSet.emptyRow();
          insert = true;
        }
        else{
          dataSet.setCurrentRow(dataSet.getKeyCols(),getKeyVals(i));
        }
        for (int j = 0; j < cols; j++){
          if(insert){
            int pos = isDefCol(j);
            if(pos != -1)
              dataSet.getRow().setData(val[pos],j);
            else
              dataSet.getRow().setData(data[i][j],j);
          }
          else
            dataSet.getRow().setData(data[i][j],j);
        }
        insert = false;
        changed[i] = false;
        dataSet.saveRow();
      }
    }
    if (tmp){
      try{
        dataSet.getConnection().commit();
      }catch(java.sql.SQLException e){
        e.printStackTrace();
      }
      if (query.equals(""))
        reload();  // 14.03. - bilo pod komentarom ...
      else
        reloadSet();  // 12.07. - dodato jer je pucalo RenewMembership
    }
  }

  public void deleteRow(int r){
    if(!(rows[r].equals(""))){
      dataSet.setCurrentRow(dataSet.getKeyCols(),getKeyVals(r));
// ovo je iskomentarisano 16.02.00. jer je pucalo brisanje
//      System.out.println("Sid:"+dataSet.getRow().getData("SINGLE_ID"));
//      System.out.println("oid:"+dataSet.getRow().getData("OFFICIAL_ID"));
      try{
        dataSet.deleteRow();
        dataSet.getConnection().commit();
      } catch(java.sql.SQLException e){
        e.printStackTrace();
      }
    }
    if (dataSet.hasError()){
      shrink(r);
      fireTableChanged(new TableModelEvent(this));
    }
  }

  public String getColumnName(int column) {
    return dataSet.getColumnName(column);
  }

  public boolean isCellEditable(int row, int column) {
    return editable;
  }

  public void setDefaultCol(int col, String value){
    String [] valtmp;
    int [] deftmp;
    if(defSize > 0){
      valtmp = new String [defSize];
      deftmp = new int[col];
      for(int i = 0; i < defSize; i++){
        deftmp[i] = def[i];
        valtmp[i] = val[i];
      }
      defSize++;
      def = new int [defSize];
      val = new String [defSize];
      for(int i = 0; i < deftmp.length; i++){
        def[i] = deftmp[i];
        val[i] = valtmp[i];
      }
    }
    else {
      defSize = 1;
      val = new String[defSize];
      def = new int[defSize];
    }
    def[defSize-1]=col;
    val[defSize-1]=value;
  }

  public void removeDefaultCol(int col){
  }

  public int isDefCol(int col){
    int ret = -1, i = 0;
    boolean found = false;
    while(!found && i < defSize){
      found = (def[i] == col);
      if(found) ret = i;
      i++;
    }
    return ret;
  }

  public String[] getKeyVals(int row){
    int [] tmp = dataSet.getKeyCols();
    for(int i = 0; i < keyNo; i++)
      keyVals[i] = data[row][tmp[i]];
    return keyVals;
  }

  private void fillColumns(ResultSet rset, int row){
    try{
      for (int i = 0; i < cols; i++){
        switch(rset.getMetaData().getColumnType(i+1)){
          case Types.DATE:
          case Types.TIMESTAMP:
            Object ts = DBConnection.TSToObject(rset.getTimestamp(i+1));
            data[row][i] = ""+ts;
            break;
          case Types.DOUBLE:
            data[row][i] = ""+rset.getDouble(i+1);
            break;
          case Types.VARCHAR:
            data[row][i] = ""+rset.getString(i+1);
            break;
          case Types.CHAR:
            data[row][i] = ""+rset.getString(i+1);
            break;
          case Types.INTEGER:
            data[row][i] = ""+rset.getInt(i+1);
            break;
          default:
            data[row][i] = ""+rset.getObject(i+1);
        }//switch
      }//for
    }//try
    catch(SQLException ex){
      System.out.println("<JDBTableModel> SQLException");
      ex.printStackTrace();
    }
    catch(Exception ex){
      System.out.println("<JDBTableModel> Exception");
      ex.printStackTrace();
    }
  }

  public int getLastRow(){
    return size-1;
  }

  public void setEditable(boolean ed){
    editable = ed;
  }

  public boolean isEditable(){
    return editable;
  }

}
