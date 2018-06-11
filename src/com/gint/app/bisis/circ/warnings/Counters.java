package com.gint.app.bisis.circ.warnings;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.gint.app.bisis.circ.DBConnection;


public class Counters {
	
	private DBConnection dbconn;
	private int warn_type;
	private HashMap counters = null;
	
	public Counters(DBConnection dbconn, int warn_type){
		this.dbconn = dbconn;
		this.warn_type = warn_type;
		counters = new HashMap();
		
		try{
			String qry = "select warn_year, last_no from warn_counters where warn_type = " + warn_type + " for update";
			Statement stmt = dbconn.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(qry);
			while (rset.next()){
				counters.put(new Integer(rset.getInt(1)), new Integer(rset.getInt(2)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public int getNext(int year){
	  	Integer key = new Integer(year);
	  	int value = 0;
	  	if (counters.containsKey(key)){
	  	     value = ((Integer)counters.get(key)).intValue();
	  	     value++;
	  	     counters.put(key,new Integer(value));
	  	}else{
	  		 counters.put(key, new Integer(1));
	  		 value = 1;
	  	}
	  	return value;
	}
	
	public void turnBack(int year){
		Integer key = new Integer(year);
		int value;
		if (counters.containsKey(key)){
			value = ((Integer)counters.get(key)).intValue();
	  	    value--;
	  	    counters.put(key,new Integer(value));
		}
	}
	
	public void finish(){
		try{
			Statement stmt = dbconn.getConnection().createStatement();
			Set set = counters.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()){
				Integer key = (Integer)it.next();
				Integer value = (Integer)counters.get(key);
				
			    int i = stmt.executeUpdate("update warn_counters set last_no = "+value+" where warn_type = "+warn_type+" and warn_year = "+key);
				if (i == 0){
					stmt.executeUpdate("insert into warn_counters (warn_type, warn_year, last_no) values ("+warn_type+", "+key+", "+value+")");
				}	
			}
			dbconn.getConnection().commit();
		}catch(Exception e){
			rollbackForUpdate();
			e.printStackTrace();
		}
	}
	
	public void rollbackForUpdate(){
		try {
			dbconn.getConnection().rollback();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
