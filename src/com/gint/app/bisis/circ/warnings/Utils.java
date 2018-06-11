package com.gint.app.bisis.circ.warnings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.gint.app.bisis.circ.reports.InvNumUtils;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.Signature;

public class Utils {

	private static String autor = "";
	private static String naslov = "";
	private static String cena = "";
	private static String sig = "";
	
	public static void getPubData(Connection conn, String ctlg_no){
		List ls = new ArrayList();
		ls.add(ctlg_no);
		List recs = InvNumUtils.getRecords(ls, conn);
		Record rec = (Record) recs.get(0);
		String empty;
		autor = "";
        naslov = "";
        cena = "";
        sig = "";
		if (rec != null){
//			try {
//	            autor = rec.getField("700").getSubfield('a').getContent() + " "
//	                + rec.getField("700").getSubfield('b').getContent();
//	            empty = autor.substring(0, 2);
//	          } catch (Exception e1) {
//	            try {
//	              autor = rec.getField("701").getSubfield('a').getContent() + " "
//	                  + rec.getField("701").getSubfield('b').getContent();
//	              empty = autor.substring(0, 2);
//	            } catch (Exception e2) {
//	              try {
//	                autor = rec.getField("710").getSubfield('a').getContent();
//	                empty = autor.substring(0, 1);
//	              } catch (Exception e3) {
//	                try {
//	                  autor = rec.getField("711").getSubfield('a').getContent();
//	                } catch (Exception e4) {
//
//	                }
//	              }
//	            }
//	          }
	          
	          try {
	            autor = rec.getField("200").getSubfield('f').getContent();
	          } catch (Exception e1) {
	          	
	          }
	          
	          try {
	            naslov = rec.getField("200").getSubfield('a').getContent();
	            empty = naslov.substring(0, 1);
	          } catch (Exception e1) {
	            try {
	              naslov = rec.getField("200").getSubfield('c').getContent();
	              empty = naslov.substring(0, 1);
	            } catch (Exception e2) {
	              try {
	                naslov = rec.getField("200").getSubfield('d').getContent();
	                empty = naslov.substring(0, 1);
	              } catch (Exception e3) {
	                try {
	                  naslov = rec.getField("200").getSubfield('i').getContent();
	                } catch (Exception e4) {

	                }
	              }
	            }
	          }
	          
	          List temp = rec.getFields("996");
	          Iterator it = temp.iterator();
	          boolean found = false;
	          while (it.hasNext() && !found) {
	        	  try{
		        	  Field tmp = (Field)it.next();
		        	  if (tmp.getSubfield('f').getContent().equals(ctlg_no)){
		        		  if (tmp.getSubfieldContent('3') != null)
		        			  cena = tmp.getSubfieldContent('3');
		        		  found = true;
		        	  }
	        	  }catch (Exception e){
		        	  
		          }
	          }
	          
	          
	         sig = Signature.userDisplay(rec, ctlg_no);
	          
		}
	}
	
	
	public static String getAutor(){
	    return autor;
	}
	
	public static String getNaslov(){
	    return naslov;
	}
	
	public static String getCena(){
	    return cena;
	}
	
	public static String getSignatura(){
	    return sig;
	}
	
	public static int getUserPeriod(Connection conn, String user_id){
		
		String query_str = "";
	    int user_type, user_ctgr = -1;
	    int users_period = 0, ctgr_period = 0;
	    long l1 = System.currentTimeMillis();

	    try{
	      Statement stmt = conn.createStatement();
	      query_str = "select user_type from users where id = '" + user_id + "'";
	      ResultSet rset = stmt.executeQuery(query_str);
	      rset.next();
	      user_type = rset.getInt(1);
	      rset.close();
	      query_str = "select period from user_types where id = " + user_type;
	      rset = stmt.executeQuery(query_str);
	      rset.next();
	      users_period = rset.getInt(1);
	      rset.close();
	      if (user_type == 1){
	        query_str = "select user_ctgr from single where user_id = '"+user_id+"'";
	        rset = stmt.executeQuery(query_str);
	        rset.next();
	        user_ctgr = rset.getInt(1);
	        rset.close();
	        query_str = "select period from user_categs where id = "+user_ctgr;
	        rset = stmt.executeQuery(query_str);
	        rset.next();
	        ctgr_period = rset.getInt(1);

	        rset.close();
	        stmt.close();
	        users_period = criteria(ctgr_period,users_period);
	      }
	    }
	    catch(Exception e){
	      users_period = -1;
	      e.printStackTrace();
	    }
	    long l2 = System.currentTimeMillis();
	//System.out.println(l2-l1);
	    return users_period;
	}
	
	
	public static int getLendPeriod(Connection conn, int lend_type){
		
		String query_str = "";
		try{
			Statement stmt = conn.createStatement();
		    query_str = "select period from lend_types where id = " + lend_type;
		    ResultSet rset = stmt.executeQuery(query_str);
		    rset.next();
		    int lend_period = rset.getInt(1);
		    rset.close();
		    stmt.close();
		    return lend_period;
		}catch(Exception e){
		    e.printStackTrace();
		    return -1;
		}
	}
		
	
  public static int criteria(int first, int second){
    return criteria(first, second, Utils.MAX);
  }

  private static int criteria(int first, int second, int fnct){
    int ret_val = -1;
    switch(fnct){
      case Utils.MAX: ret_val = Math.max(first, second); break;
      case Utils.MIN: ret_val = Math.min(first, second); break;
    }
    return ret_val;
  }
  
  
  public static boolean existWarning(Connection conn, String user_id, String ctlg_no, int warn_type){
	  try{
			Statement stmt = conn.createStatement();
		    ResultSet rset = stmt.executeQuery("select * from warnings where user_id = '"+user_id+"' and ctlg_no = '"+ctlg_no+"' and warn_type = "+warn_type);
		    if (rset.next()){
		    	rset.close();
		    	stmt.close();
		    	return true;
		    }else{
		    	rset.close();
		    	stmt.close();
		    	return false;
		    }
		}catch(Exception e){
		    e.printStackTrace();
		    return true;
		}
  }
  
  public static boolean isSetWarnInd(Connection conn, String user_id){
	  try{
		    Statement stmt = conn.createStatement();
		    ResultSet rset = stmt.executeQuery("select warning from users where id = '"+user_id+"'");
		    if (rset.next()){
		    	int warn = rset.getInt(1);
		    	if (warn == 0){
		    		return false;
		    	}else {
		    		return true;
		    	}
		    }else{
		    	return true;
		    }
	  }catch(Exception e){
		    e.printStackTrace();
		    return true;
		}
  }
  
  
  public static String latinToCyr(String str) {
	    StringTokenizer st = new StringTokenizer(str);
	    String res = "";
	    String pom;
	    String arabicNum;
	    while(st.hasMoreTokens()) {
	      pom = st.nextToken();
	      arabicNum = arabicNumber(pom);
	      if(arabicNum.equals("")) {
	        char c, c1=0;
	        for(int i=0; i<pom.length(); i++){
	          c = pom.charAt(i);
	          if(i<(pom.length()-1))
	            c1 = pom.charAt(i+1);
	          switch (c) {
	            case 'a': res+="\u0430";
	                      break;
	            case 'b': res+="\u0431";
	                      break;
	            case 'c': res+="\u0446";
	                      break;
	            case '\u010D': res+="\u0447";
	                            break;
	            case '\u0107': res+="\u045B";
	                            break;
	            case 'd': if(i < (str.length()-1)) {
	                        switch(c1) {
	                          case '\u017E': res+="\u045F";
	                                    i++;
	                                    break;
	                          case '\u017D': res+="\u045F";
                              			i++;
                              			break;          
	                          case 'j': res+="\u0452";
	                                    i++;
	                                    break;
	                          case 'J': res+="\u0452";
                              			i++;
                              			break;         
	                          default: res+="\u0434";
	                        }
	                      }
	                      else {
	                        res+="\u0434";
	                      }
	                      break;
	            case '\u0111': res+="\u0452";
	            		  break;
	            case 'e': res+="\u0435";
	                      break;
	            case 'f': res+="\u0444";
	                      break;
	            case 'g': res+="\u0433";
	                      break;
	            case 'h': res+="\u0445";
	                      break;
	            case 'i': res+="\u0438";
	                      break;
	            case 'j': res+="\u0458";
	                      break;
	            case 'k': res+="\u043A";
	                      break;
	            case 'l': if(i < (str.length()-1)) {
	                        switch(c1) {
	                          case 'j': res+="\u0459";
	                                    i++;
	                                    break;
	                          case 'J': res+="\u0459";
                              			i++;
                              			break;          
	                          default: res+="\u043B";
	                        }
	                      }
	                      else {
	                        res+="\u043B";
	                      }
	                      break;
	            case 'm': res+="\u043C";
	                      break;
	            case 'n': if(i < (str.length()-1)) {
	                        switch(c1) {
	                          case 'j': res+="\u045A";
	                                    i++;
	                                    break;
	                          case 'J': res+="\u045A";
                              			i++;
                              			break;          
	                          default: res+="\u043D";
	                        }
	                      }
	                      else {
	                        res+="\u043D";
	                      }
	                      break;
	            case 'o': res+="\u043E";
	                      break;
	            case 'p': res+="\u043F";
	                      break;
	            case 'r': res+="\u0440";
	                      break;
	            case 's': res+="\u0441";
	                      break;
	            case '\u0161': res+="\u0448";
	                      break;
	            case 't': res+="\u0442";
	                      break;
	            case 'u': res+="\u0443";
	                      break;
	            case 'v': res+="\u0432";
	                      break;
	            case 'z': res+="\u0437";
	                      break;
	            case '\u017E': res+="\u0436";
	                      break;
	            case 'A': res+="\u0410";
	                      break;
	            case 'B': res+="\u0411";
	                      break;
	            case 'C': res+="\u0426";
	                      break;
	            case '\u010C': res+="\u0427";
	                           break;
	            case '\u0106': res+="\u040B";
	                           break;
	            case 'D': if(i < (str.length()-1)) {
	                        switch(c1) {
	                          case '\u017E': res+="\u040F";
	                                    i++;
	                                    break;
	                          case '\u017D': res+="\u040F";
			                            i++;
			                            break;
	                          case 'j': res+="\u0402";
	                                    i++;
	                                    break;
	                          case 'J': res+="\u0402";
                              			i++;
                              			break;
	                          default: res+="\u0414";
	                        }
	                      }
	                      else {
	                        res+="\u0414";
	                      }
	            		  break;
	            case '\u0110': res+="\u0402";
	                      break;
	            case 'E': res+="\u0415";
	                      break;
	            case 'F': res+="\u0424";
	                      break;
	            case 'G': res+="\u0413";
	                      break;
	            case 'H': res+="\u0425";
	                      break;
	            case 'I': res+="\u0418";
	                      break;
	            case 'J': res+="\u0408";
	                      break;
	            case 'K': res+="\u041A";
	                      break;
	            case 'L': if(i < (str.length()-1)) {
	                        switch(c1) {
	                          case 'j': res+="\u0409";
	                                    i++;
	                                    break;
	                          case 'J': res+="\u0409";
                              			i++;
                              			break;        
	                          default: res+="\u041B";
	                        }
	                      }
	                      else {
	                        res+="\u041B";
	                      }
	                      break;
	            case 'M': res+="\u041C";
	                      break;
	            case 'N': if(i < (str.length()-1)) {
	                        switch(c1) {
	                          case 'j': res+="\u040A";
	                                    i++;
	                                    break;
	                          case 'J': res+="\u040A";
                              			i++;
                              			break;         
	                          default: res+="\u041D";
	                        }
	                      }
	                      else {
	                        res+="\u041D";
	                      }
	                      break;
	            case 'O': res+="O";
	                      break;
	            case 'P': res+="\u041F";
	                      break;
	            case 'R': res+="\u0420";
	                      break;
	            case 'S': res+="\u0421";
	                      break;
	            case '\u0160': res+="\u0428";
	                      break;
	            case 'T': res+="\u0422";
	                      break;
	            case 'U': res+="\u0423";
	                      break;
	            case 'V': res+="\u0412";
	                      break;
	            case 'Z': res+="\u0417";
	                      break;
	            case '\u017D': res+="\u0416";
	                      break;
	            default: res+=""+c;
	          }
	        }
	      }
	      else {
	        res+=arabicNum;
	      }
	      res+=" ";
	    }
	    return res;
	  }
  
  
  public static String cyrToLatin(String str) {
	    StringTokenizer st = new StringTokenizer(str);
	    String res = "";
	    String pom;
	    String arabicNum;
	    while(st.hasMoreTokens()) {
	      pom = st.nextToken();
	      arabicNum = arabicNumber(pom);
	      if(arabicNum.equals("")) {
	        char c, c1=0;
	        for(int i=0; i<pom.length(); i++){
	          c = pom.charAt(i);
	          if(i<(pom.length()-1))
	            c1 = pom.charAt(i+1);
	          switch (c) {
	            case '\u0430': res+="a";
	                      break;
	            case '\u0431': res+="b";
	                      break;
	            case '\u0446': res+="c";
	                      break;
	            case '\u0447': res+="\u010D";
                          break;
	            case '\u045B': res+="\u0107";
                          break;
	            case '\u0434': res+="d";
        				  break;
	            case '\u045F': res+="d\u017E";
        				  break;
	            case '\u0452': res+="\u0111";
	            		  break;
	            case '\u0435': res+="e";
	                      break;
	            case '\u0444': res+="f";
	                      break;
	            case '\u0433': res+="g";
	                      break;
	            case '\u0445': res+="h";
	                      break;
	            case '\u0438': res+="i";
	                      break;
	            case '\u0458': res+="j";
	                      break;
	            case '\u043A': res+="k";
	                      break;
	            case '\u043B': res+="l";
	            		  break;
	            case '\u0459': res+="lj";
	            		  break;     
	            case '\u043C': res+="m";
	                      break;
	            case '\u043D': res+="n";
	            		  break;
	            case '\u045A': res+="nj";
	            		  break;
	            case '\u043E': res+="o";
	                      break;
	            case '\u043F': res+="p";
	                      break;
	            case '\u0440': res+="r";
	                      break;
	            case '\u0441': res+="s";
	                      break;
	            case '\u0448': res+="\u0161";
	                      break;
	            case '\u0442': res+="t";
	                      break;
	            case '\u0443': res+="u";
	                      break;
	            case '\u0432': res+="v";
	                      break;
	            case '\u0437': res+="z";
	                      break;
	            case '\u0436': res+="\u017E";
	                      break;
	            case '\u0410': res+="A";
	                      break;
	            case '\u0411': res+="B";
	                      break;
	            case '\u0426': res+="C";
	                      break;
	            case '\u0427': res+="\u010C";
	                      break;
	            case '\u040B': res+="\u0106";
	                      break;
	            case '\u0414': res+="D";
	            		  break;
	            case '\u040F': res+="D\u017E";
                          break;			   
	            case '\u0402': res+="\u0110";
	                      break;
	            case '\u0415': res+="E";
	                      break;
	            case '\u0424': res+="F";
	                      break;
	            case '\u0413': res+="G";
	                      break;
	            case '\u0425': res+="H";
	                      break;
	            case '\u0418': res+="I";
	                      break;
	            case '\u0408': res+="J";
	                      break;
	            case '\u041A': res+="K";
	                      break;
	            case '\u041B': res+="L";
	            		  break;
	            case '\u0409': res+="Lj";
    					  break;        		  
	            case '\u041C': res+="M";
	                      break;
	            case '\u041D': res+="N";
	            		  break;
	            case '\u040A': res+="Nj";
	            		  break;
	            case 'O': res+="O";
	                      break;
	            case '\u041F': res+="P";
	                      break;
	            case '\u0420': res+="R";
	                      break;
	            case '\u0421': res+="S";
	                      break;
	            case '\u0428': res+="\u0160";
	                      break;
	            case '\u0422': res+="T";
	                      break;
	            case '\u0423': res+="U";
	                      break;
	            case '\u0412': res+="V";
	                      break;
	            case '\u0417': res+="Z";
	                      break;
	            case '\u0416': res+="\u017D";
	                      break;
	            default: res+=""+c;
	          }
	        }
	      }
	      else {
	        res+=arabicNum;
	      }
	      res+=" ";
	    }
	    return res;
	  }
  
	  private static String arabicNumber(String str) {
	    if(str.equals("I"))
	      return "I";
	    else if(str.equals("II"))
	      return "II";
	    else if(str.equals("III"))
	      return "III";
	    else if(str.equals("IV"))
	      return "IV";
	    else if(str.equals("V"))
	      return "V";
	    else if(str.equals("VI"))
	      return "VI";
	    else if(str.equals("VII"))
	      return "VII";
	    else if(str.equals("VIII"))
	      return "VIII";
	    else if(str.equals("IX"))
	      return "IX";
	    else if(str.equals("X"))
	      return "X";
	    else if(str.equals("XI"))
	      return "XI";
	    else if(str.equals("XII"))
	      return "XII";
	    else if(str.equals("XIII"))
	      return "XIII";
	    else if(str.equals("XIV"))
	      return "XIV";
	    else if(str.equals("XV"))
	      return "XV";
	    else if(str.equals("XVI"))
	      return "XVI";
	    else if(str.equals("XVII"))
	      return "XVII";
	    else if(str.equals("XVIII"))
	      return "XVIII";
	    else if(str.equals("XIX"))
	      return "XIX";
	    else if(str.equals("XX"))
	      return "XX";
	    else if(str.equals("XXI"))
	      return "XXI";
	    else
	      return "";
	  }
	  
  public static String convert(String str, boolean cyr){
	  if (str != null){
		  if (cyr){
			  return latinToCyr(str);
		  }else{
			  return cyrToLatin(str);
		  }
	  }
	  return null;
  }
  
  public static String getDBStartDate(String startDate){
	  try{
		  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		  SimpleDateFormat dbsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		  return dbsdf.format(sdf.parse(startDate));
	  }catch (Exception e){
		  return null;
	  }
  }
  
  
  public static String getDBEndDate(String endDate){
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	    SimpleDateFormat dbsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	    try{
			Calendar cl = Calendar.getInstance();
			cl.setTime(sdf.parse(endDate));
			cl.set(Calendar.HOUR_OF_DAY,23);
			cl.set(Calendar.MINUTE,59);
			cl.set(Calendar.SECOND, 59);
			cl.set(Calendar.MILLISECOND,999);
			return dbsdf.format(cl.getTime());
			//return (new Timestamp(cl.getTimeInMillis())).toString();
	    }catch (Exception e){
	    	return null;
	    }
  }
  
  public static boolean validDate(String date){
	  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	  try{
		  sdf.parse(date);
		  return true;
	  }catch (Exception e){
		  return false;
	  }
  }
  
  public static boolean checkDates(String startDate, String endDate){
	  return (startDate.equals("") || validDate(startDate)) && (endDate.equals("") || validDate(endDate));
  }
  
  public static int dayDistance(Calendar date1, Calendar date2){
	  int noDays=0;
	  
	  if (date1.before(date2)){
		  Calendar temp = Calendar.getInstance();
		  temp.setTimeInMillis(date1.getTimeInMillis());
		  while (temp.before(date2)) {
		      temp.add(Calendar.DATE, 1);
		      noDays++;
		  }
	  }else if (date1.after(date2)){
		  Calendar temp = Calendar.getInstance();
		  temp.setTimeInMillis(date2.getTimeInMillis());
		  while (temp.before(date1)) {
		      temp.add(Calendar.DATE, 1);
		      noDays++;
		  }
	  }
	  return noDays;
  }
  
  
  private static final int MAX = 1;
  private static final int MIN = 2;

}
