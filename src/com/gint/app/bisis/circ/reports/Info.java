//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package com.gint.app.bisis.circ.reports;


import java.util.ArrayList;
import java.util.List;
import com.gint.app.bisis.circ.CircDocs;
import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.Signature;


// Stampanje reversa

public class Info {
  DBConnection db_conn;
  CircDocs cd;

  public Info(DBConnection db, CircDocs cd) {
    db_conn = db;
    this.cd = cd;
  }


  public void makeOne(String ctlg){
    String ispisHTML = new String();
    String temp;
    String empty;
    
    List ls = new ArrayList();
	ls.add(ctlg);
	List recs = InvNumUtils.getRecords(ls, db_conn.getConnection());
	Record rec = (Record) recs.get(0);

    ispisHTML+="<FONT FACE=\"Arial, Helvetica\" SIZE=\"4\">\n";
    ispisHTML+="<B>Signatura: </B>";
//    try{
//    	temp = rec.getField("996").getSubfield('d').getSubsubfield('u').getContent();
//    	empty = temp.substring(0,1);
//    }catch(Exception e){
//    	try{
//    		temp = rec.getField("997").getSubfield('d').getSubsubfield('u').getContent();
//    	}catch(Exception e1){
//    		temp = "";
//    	}
//    }
    temp = Signature.userDisplay(rec, ctlg);
    ispisHTML+=temp+"<BR>\n";
//    ispisHTML+="<B>Numerus currens: </B>";
//    try{
//    	temp = rec.getField("996").getSubfield('d').getSubsubfield('n').getContent();
//    	empty = temp.substring(0,1);
//    }catch(Exception e){
//    	try{
//    		temp = rec.getField("997").getSubfield('d').getSubsubfield('n').getContent();
//    	}catch(Exception e1){
//    		temp = "";
//    	}
//    }
//    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>UDK: </B>";
    try{
    	temp = rec.getField("675").getSubfield('a').getContent();
    	empty = temp.substring(0,1);
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Autor: </B>";
    temp="";
//    try {
//        temp = rec.getField("700").getSubfield('a').getContent() + " "
//            + rec.getField("700").getSubfield('b').getContent();
//        empty = temp.substring(0, 2);
//      } catch (Exception e1) {
//        try {
//        	List f = rec.getFields("701");
//        	Field fl;
//            int i = 0;
//            while (i < f.size()){
//            	fl = (Field)f.get(i);
//            	i++;
//            	try{
//        			if (!temp.equals(""))
//        				temp += ", ";
//        			temp += rec.getField("701").getSubfield('a').getContent() + " "
//						+ rec.getField("701").getSubfield('b').getContent();
//            	}catch(Exception e){
//        				
//    			}
//            }
//        } catch (Exception e2) {
//          try {
//            temp = rec.getField("710").getSubfield('a').getContent();
//            empty = temp.substring(0, 1);
//          } catch (Exception e3) {
//            temp = "";
//          }
//        }
//      }
    try {
        temp = rec.getField("200").getSubfield('f').getContent();
      } catch (Exception e1) {
      	temp = "";
      }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Sekundarna odgovornost: </B>";
    List f = rec.getFields("702");
    temp="";
    Field fl;
    int i = 0;
    while (i < f.size()){
    	fl = (Field)f.get(i);
    	i++;
    	try{
			if (!temp.equals(""))
				temp += ", ";
			temp += fl.getSubfield('a').getContent() + " "
				+ fl.getSubfield('b').getContent() + ", " + fl.getSubfield('4').getContent();
    	}catch(Exception e){
				
		}
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Naslov: </B>";
    try {
        temp = rec.getField("200").getSubfield('a').getContent();
    }catch(Exception e){
    	temp = "";
    }
    try {
        temp += " : " + rec.getField("200").getSubfield('e').getContent();
    }catch(Exception e){
    	temp += "";
    }
    try {
        temp += ". " + rec.getField("200").getSubfield('h').getContent();
    }catch(Exception e){
    	temp += "";
    }
    try {
        temp += " , " + rec.getField("200").getSubfield('i').getContent();
    }catch(Exception e){
    	temp += "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Mesto izdavanja: </B>";
    try {
        temp = rec.getField("210").getSubfield('a').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Izdava\u010d: </B>";
    try {
        temp = rec.getField("210").getSubfield('c').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Godina izdavanja: </B>";
    try {
        temp = rec.getField("210").getSubfield('d').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Mesto \u0161tampanja: </B>";
    try {
        temp = rec.getField("210").getSubfield('e').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Naziv \u0161tampara: </B>";
    try {
        temp = rec.getField("210").getSubfield('g').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Izdanje: </B>";
    try {
        temp = rec.getField("205").getSubfield('a').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Predmetna odrednica: </B>";
    temp = "";
    f = rec.getFields();
    i = 0;
    while (i < f.size()){
    	fl = (Field)f.get(i);
    	i++;
    	if (fl.getName().substring(0,1).equals("6") && !fl.getName().equals("675")){
    		try{
    			if (!temp.equals(""))
    				temp += ", ";
    			temp += fl.getSubfield('a').getContent();
    		}catch(Exception e){
    	    	
    	    }
    	}
    		
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Tip publikacije: </B>";
    try {
        temp = rec.getField("000").getSubfield('a').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="<B>Sadr\u017eaj: </B>";
    try {
        temp = rec.getField("327").getSubfield('a').getContent();
    }catch(Exception e){
    	temp = "";
    }
    ispisHTML+=temp+"<BR>\n";
    ispisHTML+="</FONT>";
        
      
      PrintingWindow rDlg = new PrintingWindow(cd,"Info", false);
      rDlg.setHTML(ispisHTML);
      rDlg.setVisible(true);
    
  }
  
 

}

