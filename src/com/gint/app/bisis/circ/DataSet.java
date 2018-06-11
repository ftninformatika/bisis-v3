package com.gint.app.bisis.circ;

/** Klasa <b>DataSet</b> predstavlja interfejs izmedju Swing zasnovanih komponenti
 * i tabele iz baze podataka. Komponente koriste ovaj objekat za pristup, azuriranje,
 * dodavanje i brisanje podataka iz odredjene tabele.
 */

import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;

public class DataSet{


/** Konstruktor
 * @param connection Connection objekat
 * @param tableName string koji sadrzi naziv tabele
 * @param keysArray niz stringova koji sadrze nazive polja koja ulaze u sastav primarnog kljuca tabele
 */
  public DataSet(Connection connection, String tableName, String keysArray[]){
    try {
      conn = connection;
      conn.setAutoCommit(false);
      keys = keysArray;
      keyNo = keys.length;
      keyCols = new int [keyNo];
      table = tableName;
      String subquery = " select min";
      subquery += ("("+keys[0]+") from " + table);
      query = "select * from " + table+ " where ";
      query += (keys[0] +" = (" + subquery + ")");
      stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      meta = rset.getMetaData();
      colCount = meta.getColumnCount();
      columns = new Column[colCount];
      currentRow = new DataRow(this);
      int key = 0;
      for(int i = 0; i < colCount; i++) {
        String colName = meta.getColumnLabel(i + 1);
        Column column = new Column(colName, meta.getColumnType(i + 1), i);
        if(isKey(colName, keys)){
          column.setRowId(true);
          keyCols[key] = i;
          where += (keys[key] + " = "+"%p"+i+"%");
          if(key < keys.length -1)
            where += " AND ";
          key++;
        }
        if(meta.isNullable(i+1) != 1){
          column.setRequired(true);
        }
        columns[i]=column;
        insert += column.getName();
        update += (column.getName() + "="+"%p"+i+"%");
        parameters += ("%p"+i+"%");
        if( i < colCount - 1){
          insert += ",";
          update += ", ";
          parameters += ",";
        }
      }
      insertParams = new String("insert into " + table + " (" + insert +" ) values (" + parameters + " )");
      updateParams = new String("update " + table + " set  " + update + where);
      deleteParams = new String("delete from " + table + where);

      rset.close();
      setKey();
    }
    catch (SQLException ex) {
      System.err.println("Error in creating statement or fetching data.");
      System.err.println(ex);
    }
  }

/** Vraca ResultSetMetaData objekat
 * @return meta ResultSetMetaData objekat
 */
  public ResultSetMetaData getMetaData(){
    return meta;
  }

/** Vraca naziv tabele
 * @return naziv tabele
 */
  public String getTableName(){
    return table;
  }

/** Vraca Connection objekat
 * @return Connection objekat;
 */
  public Connection getConnection(){
    return conn;
  }

  public int [] getKeyCols(){
    return keyCols;
  }

  public String [] getKeys(){
    return keys;
  }

  public Column [] getColumns(){
    return columns;
  }
// nije u funkciji
  public void setFilter(String fil){
    filter = new String(" AND " + fil);
    maxFilter = new String(" WHERE " + fil);
    hasFilter = true;
  }
// nije u funkciji
  public void removeFilter(){
    filter = "";
    maxFilter ="";
    hasFilter = false;
  }

  public Column getColumn(int i){
    return columns[i];
  }

  public Column getColumn(String name){
    return columns[getColumnIndex(name)];
  }

  public int getColumnCount(){
    return colCount;
  }

  public int getColumnIndex(String col){
    boolean found = false;
    int i = -1;
    while (!found && ++i < columns.length)
      found = col.equals(columns[i].getName());
    if(found)
      return i;
    else
      return -1;
  }

  public String getColumnName(int column) {
    if (columns == null || columns.length <= column)
      return "null";
    return columns[column].getName();
  }

  public int getCursor() {
    return cursor;
  }

  public void setCursor(int row) {
    cursor = row;
    fireStateChanged();
  }

  public DataRow getRow(){
    return currentRow;
  }

  public void setRow(DataRow n){
    currentRow = n;
  }

  public void setKey(){
    if(keys.length < 2)
      setNavigateColumn(keyCols[0]);
  }

  public void setNavigateColumn(String col){
    setNavigateColumn(getColumnIndex(col));
  }

  public void setNavigateColumn(int col){
    navigating = true;
    navColPos = col;
    navigateColumn = columns[col];
    base = new String("select * from ");
    base += (table +" where " + navigateColumn.getName()+ " = ");
    min = new String("select min(");
    min += (navigateColumn.getName()+ ") from "+table);
    max = new String("select max(");
    max += (navigateColumn.getName()+ ") from "+table);
    getMax(); getMin();
  }

  public Column getNavigateColumn(){
    return navigateColumn;
  }

  public String getNavigateValue(){
    return currentRow.getData(navigateColumn.getName());
  }


  public void next() {
// izmenjeno 21.02.00.
    int count = 1;
    long l1 = System.currentTimeMillis();
    if (navigating){
    try{
      while(count != 0 && (currentValue+count) <= maxValue){
        ResultSet rset = stmt.executeQuery(base + (currentValue+count) + filter);
        if(rset.next()){
          setId(rset);
          count = 0;
          navigation = 1;
          fillColumns(rset);
          rset.close();
        }
        else
          count++;
      }
      long l2 = System.currentTimeMillis();
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    }
  }

  public void prev() {
// izmenjeno 21.02.00.
    int count = 1;
    if(navigating){
    try{
      long l1 = System.currentTimeMillis();
      if(currentValue == minValue)
        first = true;
      else
        first = false;
      while(count != 0 && (currentValue-count) >= minValue){
        ResultSet rset = stmt.executeQuery(base+(currentValue-count) + filter);
        if(rset.next()){
          setId(rset);
          count = 0;
          navigation = 0;
          fillColumns(rset);
          rset.close();
        }
        else
          count++;
        long l2 = System.currentTimeMillis();
      }
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    }
  }

  public void first() {
    if (navigating) {
    first = true;
    try{   	
      ResultSet rset = stmt.executeQuery(base + minValue + filter);
      if(rset.next()){
        setId(rset);
        fillColumns(rset);
        rset.close();
      }
      else {
// izmenjeno 21.02.00.
        currentValue = minValue;
        next();
      }
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    }
  }

  public void last() {
    if(navigating){
    try{
      ResultSet rset = stmt.executeQuery(base + maxValue + filter);
      if(rset.next()){
        setId(rset);
        fillColumns(rset);
        rset.close();
       }
       else {
// izmenjeno 21.02.00.
          currentValue = maxValue;
          prev();
       }
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    }
  }

// postavlja vrednost za currentRow na osnovu kriterijuma za pretrazivanje
// cols - pozicija kolone, vals - vrednost koja se trazi
// vraca broj redova koji zadovoljavaju kriterijum; ako ne postoji vraca -1
  public int setCurrentRow(int cols[], String[] vals){
    Column[] col = new Column[vals.length];
    for(int i=0; i < vals.length; i++)
        col[i] = columns[cols[i]];
    return setCurrentRow(col,vals);
  }

// cols - naziv kolone, vals - vrednost koja se trazi
  public int setCurrentRow(String[] cols, String[] vals){
    Column[] col = new Column[vals.length];
    for(int i=0; i < vals.length; i++){
      int index = getColumnIndex(cols[i]);
      if(index != -1)
        col[i] = columns[index];
      else
        return -1;
    }
    return setCurrentRow(col,vals);
  }

// cols - objekat kolona, vals - vrednost koja se trazi
  public int setCurrentRow(Column[] cols, String [] vals){

    String query = new String("select * from "+table+" where ");
    String value = new String();
    int count = 0;
    for(int i=0; i < vals.length; i++){
      value = getValue(cols[i], vals[i]);
      query += (cols[i].getName() +" = " + value);
      if(i < cols.length - 1)
        query += " and ";
    }
    try{	
    	
      ResultSet rSet = stmt.executeQuery(query);   
      while (rSet.next()){
        count++;
        if(count == 1){
          int temp = currentValue;
          setId(rSet);
//          if(temp != currentValue)
          fillColumns(rSet);
        }
      }
      rSet.close();
      return count;
    }catch(SQLException ex){
      return -1;
    }
  }

  public void setId(ResultSet rset){
    if(navigating){
    try{
           currentValue = rset.getInt(navigateColumn.getPosition()+1);
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    }
  }

  public int getMax(){
    if(navigating){
      int ret = -1;
      try{
        ResultSet rset = stmt.executeQuery(max + maxFilter);
        rset.next();
        String s = rset.getString(1);
        if (s == null)
          s = "0";
        
	        String s2 = s;
	        for (int i = 0; i < (s.length()-1); i++)
	          if (!Character.isDigit(s.charAt(i)))
	            s2 = s.substring(i+1);
	        try {
	          ret = Integer.parseInt(s2);
	        } catch (NumberFormatException ex) {
	          ret = -1;
	        }
	        maxValue = ret;
	        rset.close();
	      }catch(SQLException ex){
	         ex.printStackTrace();
	      }
      return ret;
    }
    return -1;
  }

/*
  public int getMax(){
    if(navigating){
    int ret = -1;
    try{
      ResultSet rset = stmt.executeQuery(max + maxFilter);
      rset.next();
      ret = rset.getInt(1);
      maxValue = ret;
      rset.close();
    }catch(SQLException ex){
       ex.printStackTrace();
    }
    return ret;
    }
    return -1;
  }
*/
/*
public int getMin(){
    if(navigating){
    int ret = -1;
    try{
      ResultSet rset = stmt.executeQuery(min + maxFilter);
      rset.next();
      ret = rset.getInt(1);
      minValue = ret;
      rset.close();
    }catch(SQLException ex){
      ex.printStackTrace();
    }
    return ret;
    }
    return -1;
}
*/

  public int getMin(){
    if(navigating){
      int ret = -1;
      try{
        ResultSet rset = stmt.executeQuery(min + maxFilter);
        rset.next();
        String s = rset.getString(1);
        if (s == null)
          s = "0";
 
        	String s2 = s;
        	for (int i = 0; i < (s.length()-1); i++)
        		if (!Character.isDigit(s.charAt(i)))
        			s2 = s.substring(i+1);
        	try {
			ret = Integer.parseInt(s2);
        	} catch (NumberFormatException ex) {
				ret = -1;
        	}
        	minValue = ret;
        	rset.close();
      }catch(SQLException ex){
         ex.printStackTrace();
      }
      return ret;
    }
    return -1;
  }
  

  public String getValue(Column col, String value){
    String s;
    switch(col.getDataType()){
      case Types.TIMESTAMP:
        if (!value.equals(""))
          value=DBConnection.toDatabaseDate(value);
      case Types.DATE:
        if (!value.equals(""))
          value=DBConnection.toDatabaseDate(value);
      case Types.VARCHAR:
      case Types.CHAR:
                s = "\'"+value+"\'";
                break;

      default:
        s = value;
    }
    return s;
  }

  public boolean isKeyColumn(int col){
    return columns[col].isRowId();
  }

  public boolean isRequiredColumn(int col){
    return columns[col].isRequired();
  }

  public boolean isKey(String col, String [] keys){
    boolean found = false;
    int i = -1;
    while (!found && ++i < keys.length)
      found = col.equals(keys[i]);
    return found;
  }

  public ResultSet executeQuery(String query){
    try{
      return stmt.executeQuery(query);
    }catch(SQLException e){
       JOptionPane.showMessageDialog(null,Messages.get("DATASET_OPTIONPANE_MSG1")+e.getMessage(), Messages.get("DATASET_OPTIONPANE_MSG2"),JOptionPane.ERROR_MESSAGE);
//      e.printStackTrace();
      return null;
    }
  }

// kreira strukturu koja simulira prazan red u tabeli
// i ona postaje currentRow;
  public void emptyRow(){
    currentRow = new DataRow(this);
    currentRow.setRowState(RowState.TOINSERT);
    eventType = State.EMPTY;
    fireStateChanged();
  }

  private void insertRow(){
    insertRow(insertParams);
  }

  private void insertRow(String ins){
    no_error = true;
    insertString = replaceValues(ins);
    System.out.println("<DataSet> Insert:"+insertString);
    try{
      int i = stmt.executeUpdate(insertString);

      getMax();getMin();
      eventType = State.INSERT;
      currentRow.setRowState(RowState.TOINSERT);

      if(isMaster())
        fireStateChanged();
      else
        conn.commit();
//      stmt.close();
    }catch(NumberFormatException e){
      e.getMessage();
    }catch(SQLException ex){
      no_error = false;
      JOptionPane.showMessageDialog(null,Messages.get("DATASET_OPTIONPANE_MSG3")+ex.getMessage(), Messages.get("DATASET_OPTIONPANE_MSG4"),JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
  }

  public void deleteRow(){
    no_error = true;
    deleteString = deleteParams;
    for(int i = 0; i < keyNo; i++)
      deleteString = replaceValues(deleteString,keyCols[i]);
    System.out.println("<DataSet> Delete:"+deleteString);
    eventType = State.DELETE;
    getMin(); getMax();
    if(isMaster())
      fireStateChanged();
    try{
      int i = stmt.executeUpdate(deleteString);
      eventType = State.DELETE;
      if(isMaster()){
        conn.commit();
        if(first)
          next();
        else
          prev();
      }
//      stmt.close();
    }catch(SQLException e){
      no_error = false;
      JOptionPane.showMessageDialog(null,Messages.get("DATASET_OPTIONPANE_MSG5")+e.getMessage(), Messages.get("DATASET_OPTIONPANE_MSG6"),JOptionPane.ERROR_MESSAGE);
//      e.printStackTrace();
    }
  }

  public void deleteMasterRow(){
    no_error = true;
    deleteString = deleteParams;
    for(int i = 0; i < keyNo; i++)
      deleteString = replaceValues(deleteString,keyCols[i]);
System.out.println("<DataSet> Delete:"+deleteString);
    eventType = State.DELETE;
    getMin(); getMax();

    try{
      int i = stmt.executeUpdate(deleteString);
      eventType = State.DELETE;
      conn.commit();
//      emptyRow();

//      stmt.close();
    }catch(SQLException e){
      no_error = false;
      JOptionPane.showMessageDialog(null,Messages.get("DATASET_OPTIONPANE_MSG7")+e.getMessage(), Messages.get("DATASET_OPTIONPANE_MSG8"),JOptionPane.ERROR_MESSAGE);
//      e.printStackTrace();
    }
  }

  private void updateRow(){
    no_error = true;
    updateString = replaceValues(updateParams); 
    System.out.println("<DataSet> Update:"+updateString);
    try{
      int i  = stmt.executeUpdate(updateString);

      currentRow.setRowState(RowState.NOCHANGE);
      eventType = State.UPDATE;
      if(isMaster())
        fireStateChanged();
      else
        conn.commit();
//      rset.close();
    }catch(SQLException e){
      no_error = false;
      JOptionPane.showMessageDialog(null,Messages.get("DATASET_OPTIONPANE_MSG9")+e.getMessage(), Messages.get("DATASET_OPTIONPANE_MSG10"),JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }
  }

  public void saveRow(){
    eventType = State.TOSAVE;
    fireStateChanged();
    if (currentRow.getRowState() == RowState.TOUPDATE){
      updateRow();
    }
    else if (currentRow.getRowState() == RowState.TOINSERT){
      insertRow();
    }
  }

  public void addChangeListener(ChangeListener l) {
    changeListener = l;
    listenerList.add(ChangeListener.class, changeListener);
  }

  public void removeChangeListener(ChangeListener l) {
    listenerList.remove(ChangeListener.class, l);
  }

  public void removeChangeListener() {
    listenerList.remove(ChangeListener.class, changeListener);
  }

  public void fireStateChanged() {
    String message = new String("");
    switch (eventType){
      case State.NAVIGATE:
        message = "NAVIGATE";
        break;
      case State.EMPTY:
        message = "EMPTY";
        break;
      case State.INSERT:
        message = "INSERT";
        break;
      case State.UPDATE:
        message = "UPDATE";
        break;
      case State.DELETE:
        message = "DELETE";
        break;
      case State.TOSAVE:
        message = "TOSAVE";
    }
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==ChangeListener.class) {
//        if (changeEvent == null)
        changeEvent = new ChangeEvent(message);
        ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
      }
    }
  }

  private void fillColumns(ResultSet rset){
    fillColumns(rset, true);
  }

//  public void dataSet_stateChanged(ChangeEvent e){
  private void fillColumns(ResultSet rset, boolean fireEvent){
    try{
      for (int i = 0; i < colCount; i++){
        switch(columns[i].getDataType()){
          case Types.DATE:
          case Types.TIMESTAMP:
            Object ts = DBConnection.TSToObject(rset.getTimestamp(i+1));
            currentRow.setData(""+ts, i);
            break;
          case Types.DOUBLE:
            rset.getDouble(i+1);
            if (rset.wasNull() == true)
              currentRow.setData(null, i);
            else
              currentRow.setData(""+rset.getDouble(i+1), i);
            break;
          case Types.VARCHAR:
            currentRow.setData(rset.getString(i+1), i);
            break;
          case Types.CHAR:
            currentRow.setData(rset.getString(i+1), i);
            break;
          case Types.INTEGER:
              currentRow.setData(""+rset.getInt(i+1), i);
            break;
          default:
            currentRow.setData(""+rset.getObject(i+1), i);
        }//switch
      }//for
      currentRow.setRowState(RowState.NOCHANGE);

      if(fireEvent){
        eventType = State.NAVIGATE;
        fireStateChanged();
      }
    }//try
    catch(SQLException ex){
      System.out.println("<DataSet> SQLException: ");
      ex.printStackTrace();
    }
    catch(Exception ex){
      System.out.println("<DataSet> Exception: ");
      ex.printStackTrace();
    }
  }

  private String fillDetailColumns(String qry){
    for(int i = 0; i < mld.getMasterLink().length; i++)
      qry = replaceValues(qry,getColumnIndex(mld.getDetailLink()[i]), masterDataSet.getRow(),masterDataSet.getColumnIndex(mld.getMasterLink()[i]));
    return qry;
  }

  private String replaceString(String source, String ch, String value){
    String temp = new String(source);
    int pos = 0;
    int offset = 0;
    while (pos != -1){
      pos = temp.indexOf(ch, pos + offset);
      if(pos != -1){
        temp = temp.substring(0,pos) + value + temp.substring(pos+ch.length(),temp.length());
        offset = value.length();
      }
    }
    return temp;
  }

  private String replaceValues(String source, int param){
    return replaceValues(source, param, currentRow);
  }

  private String replaceValues(String source, int param, DataRow row){
    return replaceValues(source, param, currentRow, param);
  }

  private String replaceValues(String source, int paramNo, DataRow row, int colNo){
    String c = new String("%p");
    c +=(""+paramNo+"%");
    String s = (String)row.getDBData(colNo);   
    source = replaceString(source, c, s);
    return source;
  }

  public void setMasterLink(MasterLinkDescriptor masterld){
    mld = masterld;
    masterDataSet = mld.getMasterDataSet();
    masterChangeListener =new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        masterSet_stateChanged(e);
      }
    };
    masterDataSet.addChangeListener(masterChangeListener);
  }

  public void masterSet_stateChanged(ChangeEvent e){
    String qry  = new String("select * from " + table + " where ");
    try{
      if(((String)e.getSource()).startsWith("NAVIGATE")){
        for(int i = 0; i < mld.getMasterLink().length; i++){
          qry += (mld.getDetailLink()[i] + " = ");
          qry += masterDataSet.getRow().getDBData(masterDataSet.getColumnIndex(mld.getMasterLink()[i]));
          if(i != (mld.getMasterLink().length -1)) qry += " and ";
        }
        ResultSet rset = stmt.executeQuery(qry);
        if(rset.next()){
          fillColumns(rset);
          rset.close();
        }
        else if(masterDataSet.navigation == 1){
          masterDataSet.next();
//          navigation = -1;
        } 
        else if(masterDataSet.navigation == 0){
          masterDataSet.prev();
//          navigation = -1;
        }
      } else if (e.getSource().equals("EMPTY")){
          emptyRow();
      } else if (e.getSource().equals("INSERT")){
          insertString = fillDetailColumns(insertParams);
          insertRow(insertString);
      } else if (e.getSource().equals("UPDATE")){
          updateString = fillDetailColumns(updateParams);
          updateRow();
      } else if (e.getSource().equals("DELETE")){
          deleteString = fillDetailColumns(deleteParams);
          deleteRow();
      }
    }catch(SQLException ex){
      ex.printStackTrace();
    }
  }

  public MasterLinkDescriptor getMasterLink(){
    return mld;
  }

  public void removeMasterLink(){
//    masterDataSet.setMaster(false);
    masterDataSet.removeChangeListener(masterChangeListener);
    mld = null;
  }

  public int getSize(){
    try{
      ResultSet rset = stmt.executeQuery("select count(*) from "+table);
      if(rset.next())
        return rset.getInt(1);
      else
        return 0;
    }
    catch(SQLException e){
      e.printStackTrace();
      return -1;
    }
  }

  private String replaceValues(String source){
    for(int i = 0; i < colCount; i++)
      source = replaceValues(source, i);
    return source;
  }

  public boolean isMaster(){
    return master;
  }

  public void setMaster(boolean m){
    master = m;
  }

  public String toString(){
    return table;
  }

  public boolean isFiltered(){
    return hasFilter;
  }

  public boolean hasError(){
    return no_error;
  }

  public String getQuery(){
    return query;
  }

  public int getEventType() {
    return eventType;
  }

  // connection to DB
  private Connection conn;
  // statement object
  private Statement stmt;
  // resultset object
//  private ResultSet rset;
  // metadata - for getting column names, detecting nullable columns ...
  private ResultSetMetaData meta;
  // table from which will be displayed data
  private String table;
  // columns which will be displayed
  private Column [] columns;
  // CURSOR position
// bio je protected
  private int cursor = -1;
  // Array of indexes of key columns
// bio je protected
  private int keyCols[];
  // Number of keys
// bio je protected
  private int keyNo;
  // Array of keys
// bio je protected
  private String keys [];
  // Number of columns in the structure
  private int colCount = 0;

  private Column navigateColumn ;

  private int navColPos = -1;

  private int minValue = 0;

  private int maxValue = 0;

  private int currentValue = 0;

  private DataRow currentRow = null;

  private DataSet masterDataSet = null;

  private MasterLinkDescriptor mld = null;
  private ChangeListener masterChangeListener = null;
  private String insertString  = new String();
  private String updateString  = new String();
  private String deleteString  = new String();

  private String insertParams  = new String();
  private String updateParams  = new String();
  private String deleteParams  = new String();


  private String query = new String();
  // string with iINSERT expression
  private String insert = new String();
  // string with UPDATE expression
  private String update = new String();
  // string for parameters
  private String parameters = new String();
  // string with WHERE expression
  private String where = new String(" WHERE ");;

  private String base;
  private String min;
  private String max;

  // new type of event - ChangeEvent for detecting changes in data
  protected transient ChangeEvent changeEvent = null;
  // List of EventListeners - for ChangeEvent
  protected EventListenerList listenerList = new EventListenerList();

  private ChangeListener changeListener = null;
// bio je public
  private int eventType = State.INIT;

  private boolean no_error = true;

  private boolean master = false;

  private String filter = new String("");

  private String maxFilter = new String();

  private boolean first = false;

// bio je public
  private int navigation = -1;
// bio je public
  private boolean hasFilter = false;

  private boolean navigating = false;

} // End of class

