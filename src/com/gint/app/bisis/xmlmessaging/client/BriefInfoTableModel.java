/*
 * Created on 2004.10.6
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.client;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.gint.app.bisis.xmlmessaging.util.DocumentBriefs;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BriefInfoTableModel extends AbstractTableModel{
	
	private boolean DEBUG = false;
	private LinkedHashMap selection;
    private Vector columnNames = new Vector();
    private Vector data;
    private Vector allData;
    private BisisSearchForm holdingForm=null; 
    
    private int maxRows=0;
    private int lastActive=0;
    private int firstActive=0;
    
    public Vector getData(){
    	return data;
    }
    
    public BriefInfoTableModel(int maxRows, Vector data, Vector allData, LinkedHashMap selection, BisisSearchForm holdingForm){
    	this.maxRows=maxRows;
    	this.data=data;
    	this.allData=allData;
    	this.selection=selection;
    	this.holdingForm=holdingForm;
    	columnNames.add(new String("R.br."));
    	columnNames.add(new String("Detaljno"));
    	columnNames.add(new String("Izvor"));
    	columnNames.add(new String("Sa\u017eetak"));
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return (String)columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        return ((Vector)data.get(row)).get(col);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        if (col == 1) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
    	
    	if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }
        if(col==1){
        	try{
        		int position=((Integer)getValueAt(row,0)).intValue()-1;
        		allData.setElementAt(((Vector)data).get(row),position);
        		int docId=((DocumentBriefs)getValueAt(row,3)).getDocId();
        		if(((Boolean)value).booleanValue()==true){
        			LibraryServerDesc thisLib=(LibraryServerDesc)getValueAt(row,2);
        			LinkedHashMap hitsToThisAddress=(LinkedHashMap)selection.get(thisLib);
        			if(hitsToThisAddress==null){
        				hitsToThisAddress=new LinkedHashMap();
        			}
        			hitsToThisAddress.put(""+docId,""+docId);
        			selection.put(thisLib,hitsToThisAddress);
        		}else{
        			LibraryServerDesc thisLib=(LibraryServerDesc)getValueAt(row,2);
	    			LinkedHashMap hitsToThisAddress=(LinkedHashMap)selection.get(thisLib);
	    			if(hitsToThisAddress!=null){
	    				hitsToThisAddress.remove(""+docId);
	    				selection.put(thisLib,hitsToThisAddress);
	    			}
        		}
        		if(selection.size()>0){
        			(holdingForm.getJViewDetailButton()).setEnabled(true);
        			(holdingForm.getJViewDetailButton()).setVisible(true);
        		}else{
        			(holdingForm.getJViewDetailButton()).setEnabled(false);
        			(holdingForm.getJViewDetailButton()).setVisible(false);
        		}
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
        }
        ((Vector)data.get(row)).set(col,value);
        
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }
    
    public synchronized void addResultsToTable(Vector newRes){
    	int currSize=allData.size();
    	for(int i=0;i<newRes.size();i++){
    		Vector newItem=(Vector)newRes.get(i);
    		newItem.add(0,new Integer(currSize+i+1));
    		allData.add(newItem);
        	if(data.size()<maxRows){
        		data.add(newItem);
        		fireTableRowsInserted(data.size()-1,data.size()-1);
        	}
    	}
    	firstActive=((Integer)((Vector)data.get(0)).get(0)).intValue();
    	lastActive=((Integer)((Vector)data.get(data.size()-1)).get(0)).intValue();
    }
    
    public synchronized void replaceContent(int direction){
    	try{
    		if(direction==0){ //down
    			int low=firstActive-maxRows-1;
    			if(low<0) low=0;
    			int high=low+maxRows;
    			if(high>allData.size()-1) high=allData.size();
    			data.clear();
    			for(int i=low;i<high;i++){
    				data.add(allData.get(i));
    			}
    		}else{
    			int low=lastActive;
    			if(low>allData.size()-1) low=allData.size()-1;
    			int high=low+maxRows;
    			if(high>allData.size()-1) high=allData.size();
    			data.clear();
    			for(int i=low;i<high;i++){
    				data.add(allData.get(i));
    			}
    		}
    		fireTableRowsInserted(data.size()-1,data.size()-1);
    	}catch(ArrayIndexOutOfBoundsException e){
    		//ignore
    	}
    	firstActive=((Integer)((Vector)data.get(0)).get(0)).intValue();
    	lastActive=((Integer)((Vector)data.get(data.size()-1)).get(0)).intValue();
    }
    
    public synchronized void clearTable(){
    	data.clear();
    	allData.clear();
    	Iterator it=(selection.keySet()).iterator();
    	while(it.hasNext()){
    		LinkedHashMap oneEntry=(LinkedHashMap)selection.get(it.next());
    		if(oneEntry!=null)
    			oneEntry.clear();
    	}
    	selection.clear();
    	fireTableDataChanged();
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
        	System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
            	System.out.print("  " +getValueAt(i,j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
    
	/**
	 * @return Returns the firstActive.
	 */
	public int getFirstActive() {
		return firstActive;
	}
	/**
	 * @return Returns the lastActive.
	 */
	public int getLastActive() {
		return lastActive;
	}
}
