/*
 * Created on Sep 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.report;

/**
 * @author Jelena Radjenovic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import com.gint.app.bisis.editor.RezanceUtilities;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Subfield;
import com.gint.app.bisis4.common.records.Subsubfield;

import com.gint.util.string.StringUtils;
import freemarker.template.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Base {
	int docID;
	String code;
	Field fields;
	Record rec; 
	Map root=new HashMap();
	public static int br = 1;
	public int bk = 1;
	public int strana = 1; 
	
	public Base(int docID, String ulazniZapis, String code) {
	    this.code = code;
	    this.docID = docID;

	  /** Prebacivanje zapisa u memorijsku strukturu (raspakivanje)
	  */
	    ulazniZapis=ulazniZapis.replaceAll("<","&lt;");
	    ulazniZapis=ulazniZapis.replaceAll(">","&gt;");
	    rec=RecordFactory.fromUNIMARC(ulazniZapis);
	    
	  }
	public boolean checkPubType(String type){
		String pom=rec.getField("000").getSubfield('a').getContent().substring(0,3);	
	
		for(int i=0;i<=type.length();i++){
			if (i%3==0 && i!=0){
				if (type.substring(i-3,i).equals(pom)){
					return true;
				}
			}
		}
		return false;
	}

	public Map getField(){
		Map root=new HashMap();
	  	String stari=""; 
	  	SimpleList fl;
	  	fl= new SimpleList();
	  	String name="";
	  	SimpleList sf=new SimpleList();
	  	for(int i=0;i<rec.getFieldCount();i++){
	  		Field f=rec.getField(i);
	  		if(f!=null){
	  			name=f.getName();	           
	            SimpleHash field=new SimpleHash();
	            sf=getSubField(f,field);
	            field.put("ind1",f.getInd1()+"");
            	field.put("ind2",f.getInd2()+"");   
            	field.put("sf",sf);
            	if (sf.size()!=0){
            		fl.add(field);
            	}
	            if ((i+1<rec.getFieldCount())){
	            	if(	 (!rec.getField(i+1).getName().equals(name))){
	            		
	            	 if (fl.size()>0){	            	 	
	            	 	root.put("f"+name,fl);
	            	 	
	            	 }
	            	 fl= new SimpleList(); 
	            	}	            	 
	            	 
	            }         
	  		}
	  		
	  	}
	  	if (sf.size()>0){    	 
    	 	root.put("f"+name,fl);
    	 }
  		
	  	return root;
	  }
	public SimpleList getSubField(Field f,SimpleHash field){
		char name=' ';
		char stari=' ';
		boolean polje=false;
		SimpleList sf=new SimpleList();
		
		
		for (int j=0; j<f.getSubfieldCount(); j++){
			Subfield subfield=f.getSubfield(j);
	  		if(subfield!=null){
	  			SimpleHash secField=new SimpleHash();	  			
	  			SimpleList ssf=new SimpleList();	            
	            SimpleHash subField=new SimpleHash();
	            subField.put("name",subfield.getName()+"");
	            subField.put("content",subfield.getContent());	            
	             
	            if(subfield.getSubsubfieldCount()>=1){
	            	ssf=getSubSubField(subfield,subField);
	            	subField.put("ssf",ssf);
	            }
	            
	            if(subfield.getSecField()!=null){
	            	secField=getSecField(subfield,subField);
	            	subField.put("secField",secField);
	             	
	            }
	            if((!subfield.getContent().equals("")&& !subfield.getContent().equals(" ")) ||subfield.getSubsubfieldCount()>=1 || subfield.getSecField()!=null ){	            	
	               sf.add(subField);	               
	            }
	  		}
		}
		return sf;
	}
	public SimpleList getSubSubField(Subfield subfield,SimpleHash subField){
		char name=' ';
		char stari=' ';
		SimpleList ssf=new SimpleList();;
		for (int j=0; j<subfield.getSubsubfieldCount(); j++){
			Subsubfield subsubfield=subfield.getSubsubfield(j);
	  		if(subsubfield!=null){
	            SimpleHash subSubField=new SimpleHash();
	            subSubField.put("name",subsubfield.getName()+"");
	            subSubField.put("content",subsubfield.getContent());
	            
	            if(!subsubfield.getContent().equals("")&& !subsubfield.getContent().equals(" ")){
	            	ssf.add(subSubField);
	            }
	  		}
		}
		return ssf;
	}
	public SimpleHash getSecField(Subfield subfield,SimpleHash subField){
		Field sec=subfield.getSecField();
		char name=' ';
		char stari=' ';
		SimpleHash secField=new SimpleHash();
		
		SimpleList sf=new SimpleList();
		for(int i=0;i<sec.getSubfieldCount();i++){
			Subfield subfield1=sec.getSubfield(i);
			if(subfield!=null){ 
	            SimpleHash subFieldSec=new SimpleHash();
	            subFieldSec.put("name",subfield1.getName()+"");
	            subFieldSec.put("content",subfield1.getContent());
	            if(!subfield1.getContent().equals(""))
	            	sf.add(subFieldSec);
	            
	  		}
		}
		if(sf.size()>=1){
			//secField.put("one",sec.getName());
			subField.put("content",sec.getName());
			secField.put("ind1",sec.getInd1()+"");
			secField.put("ind2",sec.getInd2()+"");
			secField.put("sf",sf);
		}
		
		return secField;
	}
	
	public  Map struktura(){
		Map root=new HashMap();	
		
		root=getField();
		root.put("nemaSignature",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER"));
		root.put("odrednica",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOHEADWORD"));
		root.put("promocija",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_PROMOTION"));
		root.put("mentor",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_MENTOR"));
		root.put("komisija",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_DEFENDBOARD"));
		root.put("odbrana",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_DEFEND"));
		root.put("sadrzaj",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_CONTENT"));
		root.put("mrtitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_MRTITLE"));
		root.put("crtitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_CRTITLE"));
		root.put("ortitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_ORTITLE"));
		root.put("rtitleshead",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_RTITLESHEAD"));
		root.put("rtitleabovetext",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_RTITLEABOVETEXT"));
		root.put("currtitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_CURRTITLE"));
		root.put("hrbtitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_HRBTTITLE"));
		root.put("ssltitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_SSLTTITLE"));
		root.put("addtitle",com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_ADDTITLE"));
		root.put("f423mustHave200", com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423MUSTHAVE200"));
		root.put("f423content", com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423CONTENT"));
		root.put("f423wrongFieldOrder",com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_F423WRONGSECFIELDORDER"));
		
		
		return root;
	}
	
	
	
	
	public int getID(){
	  	return this.docID;
	  }
	public String  format(String in){
	  	
	  	String ret=""; 
	  	int j=0;
	  	int i=0;
	  	
	  	while(i<in.length()){
	  				if (i<(in.length()-3)&& in.substring(i,i+4).equals("<sp>")){
						ret+="&nbsp;";
						i=i+4;
		  		}
	  			else{
	  				ret+=in.substring(i,i+1);
					i++;
	  			}
	  	}	
	  	
	    return ret;
	    
	  }
	  public String formatRed(String in) {
	    
	    String retVal = new String("");
	    String out = new String("");
	    boolean sig=false;
	    boolean pocetak=true;
	    int brojac=0;
	    for (int i=0; i < in.length(); i++)  {
	      if (i<in.length()-14 && in.substring(i,i+15).equals("<BISIS></BISIS>")){
	      	return "";
	      }
	      if (pocetak){
	      	if (i<in.length()-6&& in.substring(i,i+7).equals("<BISIS>")){
	      		pocetak=false;
	      		i=i+6;
	      		
	      	}
	      }
	      else{
	      	    if (i<in.length()-7 && in.substring(i,i+8).equals("</BISIS>")){
	      		        return out;
	      	    }
	    	    if (i<(in.length()-4) && 	in.substring(i,i+5).equals("<sig>")){
	    		    out +=in.substring(i,i+5); 
					i+=4;
					sig=true;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</sig>")){
					out +=in.substring(i,i+6); 
					i+=5;
					sig=false;
				}
	    	    else if (i<(in.length()-3) && in.substring(i,i+4).equals("<DR>")){
	    		    out +=in.substring(i,i+4); 
					i+=3;
					
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("</DR>")){
					out +=in.substring(i,i+5); 
					i+=4;
					
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("&nbsp;")){
					if (bk == Report.bkmax+1 && !sig) {
			                out +="<BR>\n";		                
			               bk = 1;
			        }
					else{
					   out +=in.substring(i,i+6); bk++;
					}
					i+=5;				
				}
				else if(in.substring(i,i+1).equals(" ")){
					if (bk == Report.bkmax+1 && !sig) {
		                out +="<BR>\n";	                
		               bk = 1;
		            }
				    else{
					    out +=in.substring(i,i+1); bk++;
				    }
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("<odr>")){				
					out +=in.substring(i,i+5); 
					i+=4;				
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</odr>")){				
					out +=in.substring(i,i+6); 
					i+=5;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("<sodr>")){
					out +=in.substring(i,i+6); 
					i+=5;
				}
				else if(i<(in.length()-6) && in.substring(i,i+7).equals("</sodr>")){				
					out +=in.substring(i,i+7); 
					i+=6;
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("<zag>")){
					out +=in.substring(i,i+5); 
					i+=4;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</zag>")){
					out +=in.substring(i,i+6); 
					i+=5;
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("<ser>")){
					out +=in.substring(i,i+5); 
					i+=4;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</ser>")){
					out +=in.substring(i,i+6); 
					i+=5;
				}
				
				
		        else  if (i<(in.length()-2) && in.substring(i,i+3).equals("<B>")) {
		                 out += in.substring(i,i+3);
		                 i = i+2;
		        }
		        else if (i<(in.length()-3) && in.substring(i,i+4).equals("</B>"))  {
		                out += in.substring(i,i+4);
		                i = i+3;
		        }	         
		        else  if (i<(in.length()-3) && in.substring(i,i+4).equals("&lt;")) {
	                 out += in.substring(i,i+4);
	                 i = i+3;
	            }
		        else  if (i<(in.length()-3) && in.substring(i,i+4).equals("&gt;")) {
	                 out += in.substring(i,i+4);
	                 i = i+3;
	            }
	            else if (i<(in.length()-3)&& in.substring(i,i+4).equals("<BR>")) {
	                 out += "<BR>\n";
	                 bk = 1;i+=3;
	           }
	          else{
	            
	                int n = i+1;
	                boolean jos=true;               
	                while (n < in.length() && jos ){
	              	
	    			   if(in.substring(n,n+1).equals(" ")){
	    				   jos=false;  
	    			   }
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("&nbsp;")){
	    				  jos=false; 
	    				  
	    			   }
	    			   else if (n<(in.length()-3)&& (in.substring(n,n+4).equals("<BR>")||in.substring(n,n+4).equals("<np>")) ) {  
	    				  jos=false; 
	    			   }    
	    			   
	    			   else if(n<(in.length()-6) && in.substring(n,n+7).equals("</sodr>")){
	    				  jos=false; 
	    			   }
	    			   else if(n<(in.length()-4) &&   ( in.substring(n,n+5).equals("<odr>")||in.substring(n,n+5).equals("<sig>") || in.substring(n,n+5).equals("<zag>") ||in.substring(n,n+5).equals("<ser>"))){
	    				  jos=false;  
	    			   }    			   
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</sig>") ){
	    				  jos=false;  
	    			   }
	    			   else if (i<(in.length()-3) && in.substring(i,i+4).equals("<DR>")){
	    			   	jos=false; 
	    			   }
	    			   else if(i<(in.length()-4) && in.substring(i,i+5).equals("</DR>")){
	    			   	jos=false; 
	    			   }
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</zag>"))
	    			   	jos=false; 
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</ser>"))
	    			   	jos=false; 
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("<sodr>"))
	    			   	jos=false; 
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</odr>"))
	    			   	jos=false; 
	    			   else
	              	     n++;
	             }
	              int pom=n-i+bk; 
	              if (pom-1> Report.bkmax && !sig ) {  // rec je duza od duzine reda
	                out +="<BR>\n"; 
	                bk = 1;
	                out+=in.substring(i,n);
	                bk+=n-i;
	                i=n-1; 
	              }
	              else {
	                out +=in.substring(i,n);
	                bk+=n-i;
	                i=n-1;                 
	              }
	              
	            }
	         }
	      }
	      
	       int pom=out.indexOf("</BISIS>"); 
	       out=out.substring(0,pom);
	      return out;
	   }
	  public String formatRed1(String in) {
	    
	    String retVal = new String("");
	    String out = new String("");
	    boolean sig=false;
	    boolean pocetak=true;
	    int brojac=0;
	    for (int i=0; i < in.length(); i++)  {
	      if (i<in.length()-14 && in.substring(i,i+15).equals("<BISIS></BISIS>")){
	      	return "";
	      }
	      if (pocetak){
	      	if (i<in.length()-6&& in.substring(i,i+7).equals("<BISIS>")){
	      		pocetak=false;
	      		i=i+6;	      		
	      	}
	      }
	      else{
	      	    if (i<in.length()-7 && in.substring(i,i+8).equals("</BISIS>")){
	      	    	    out=out+"<BR>"; //za razdvajanje strana
	      		        return out;
	      	    }
	    	    if (i<(in.length()-4) && 	in.substring(i,i+5).equals("<sig>")){
					i+=4;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</sig>")){
					i+=5;
				}
				else if(i<(in.length()-3) && in.substring(i,i+4).equals("<np>")){
					i+=3;
				}
	    	    else if (i<(in.length()-3) && in.substring(i,i+4).equals("<DR>")){
					i+=3;
					
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("</DR>")){
					i+=4;
					
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("&nbsp;")){
					if (bk == Report.bkmax+1 && !sig) {
			                out +="<BR>\n";		                
			               bk = 1;
			        }
					else{
					   out +=in.substring(i,i+6); bk++;
					}
					i+=5;				
				}
				else if(in.substring(i,i+1).equals(" ")){
					if (bk == Report.bkmax+1 && !sig) {
		                out +="<BR>\n";	                
		               bk = 1;
		            }
				    else{
					    out +=in.substring(i,i+1); bk++;
				    }
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("<odr>")){
					i+=4;				
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</odr>")){	
					i+=5;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("<sodr>")){
					i+=5;
				}
				else if(i<(in.length()-6) && in.substring(i,i+7).equals("</sodr>")){
					i+=6;
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("<zag>")){
					i+=4;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</zag>")){
					i+=5;
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("<ser>")){
					i+=4;
				}
				else if(i<(in.length()-5) && in.substring(i,i+6).equals("</ser>")){
					i+=5;
				}
				
				
		        else  if (i<(in.length()-2) && in.substring(i,i+3).equals("<B>")) {
		                 out += in.substring(i,i+3);
		                 i = i+2;
		        }
		        else if (i<(in.length()-3) && in.substring(i,i+4).equals("</B>"))  {
		                out += in.substring(i,i+4);
		                i = i+3;
		        }	         
		        else  if (i<(in.length()-3) && in.substring(i,i+4).equals("&lt;")) {
	                 out += in.substring(i,i+4);
	                 i = i+3;
	            }
		        else  if (i<(in.length()-3) && in.substring(i,i+4).equals("&gt;")) {
	                 out += in.substring(i,i+4);
	                 i = i+3;
	            }
	            else if (i<(in.length()-3)&& in.substring(i,i+4).equals("<BR>")) {
	                 out += "<BR>\n";
	                 bk = 1;i+=3;
	           }
	          else{
	            
	                int n = i+1;
	                boolean jos=true;               
	                while (n < in.length() && jos ){
	              	   // prolazi kroz string dok ne naidje na pomocni tag ili blank znak (sakuplja rec)
	    			   if(in.substring(n,n+1).equals(" ")){
	    				   jos=false;  
	    			   }
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("&nbsp;")){
	    				  jos=false; 
	    				  
	    			   }
	    			   else if (n<(in.length()-3)&& (in.substring(n,n+4).equals("<BR>")||in.substring(n,n+4).equals("<np>")) ) {  
	    				  jos=false; 
	    			   }    
	    			   
	    			   else if(n<(in.length()-6) && in.substring(n,n+7).equals("</sodr>")){
	    				  jos=false; 
	    			   }
	    			   else if(n<(in.length()-4) &&   ( in.substring(n,n+5).equals("<odr>")||in.substring(n,n+5).equals("<sig>") || in.substring(n,n+5).equals("<zag>") ||in.substring(n,n+5).equals("<ser>"))){
	    				  jos=false;  
	    			   }    			   
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</sig>") ){
	    				  jos=false;  
	    			   }
	    			   else if (i<(in.length()-3) && in.substring(i,i+4).equals("<DR>")){
	    			   	jos=false; 
	    			   }
	    			   else if (i<(in.length()-3) && in.substring(i,i+4).equals("<np>")){
	    			   	jos=false; 
	    			   }
	    			   else if(i<(in.length()-4) && in.substring(i,i+5).equals("</DR>")){
	    			   	jos=false; 
	    			   }
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</zag>"))
	    			   	jos=false; 
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</ser>"))
	    			   	jos=false; 
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("<sodr>"))
	    			   	jos=false; 
	    			   else if(n<(in.length()-5) && in.substring(n,n+6).equals("</odr>"))
	    			   	jos=false; 
	    			   else
	              	     n++;
	             }
	              int pom=n-i+bk; 
	              if (pom-1> Report.bkmax && !sig ) {  // rec je duza od duzine reda
	                out +="<BR>\n"; 
	                bk = 1;
	                out+=in.substring(i,n);
	                bk+=n-i;
	                i=n-1; 
	              }
	              else {
	                out +=in.substring(i,n);
	                bk+=n-i;
	                i=n-1;                 
	              }
	              
	            }
	         }
	      }
	      
	       int pom=out.indexOf("</BISIS>"); 
	       out=out.substring(0,pom);
	       out=out+"<BR>";  //za razdvajanje strana
	      return out;
	   }
	  public String formatStrana(String in){
	  	
	  	String out=new String();
	    br=0; 
	  	int i=0;
	  	boolean sigLog=false;
	  	boolean odrLog=false;
	  	boolean sOdrLog=false;
	  	boolean zagLog=false;
	  	boolean drLog=false;
	    String signatura="";
	    String odrednica="";
	    String sOdrednica="";
	    String zag="";
	    String zagS="";
	    boolean np=false;
	    boolean nl=false;
	    boolean ser=false;
	    boolean tag=false;
	    boolean bold=false;		    
	    int strana=1;
	    String stranica="";
	    
	  	while(i<in.length()){  		
	  		String red=new String(); 
	  		boolean jos=true;
	  		while((i< in.length())&& jos){  			
	  			if (i<(in.length()-4) && 	in.substring(i,i+5).equals("<sig>")){
	  				sigLog=true; 
	  				i+=5;  				
	  			}
	  			else if(i<(in.length()-5) && in.substring(i,i+6).equals("</sig>")){  				
	  				//signatura+="<BR>\n";
	  				tag=true;
	  				sigLog=false;
	  				i+=6;
	  			}
	  			if (i<(in.length()-4) && in.substring(i,i+5).equals("<ser>")){  				
	  				ser=true; 
	  				i+=5;  			
	  				
	  			}
	  			else if(i<(in.length()-5) && in.substring(i,i+6).equals("</ser>")){
	  				tag=true;
	  				ser=false;
	  				i+=6;
	  			}
	  			
	  			else if(i<(in.length()-4) && in.substring(i,i+5).equals("<odr>")){  				
	  				odrLog=true;   
	  				i+=5;
	  			}
	  			else if(i<(in.length()-5) && in.substring(i,i+6).equals("</odr>")){ 
	  				tag=true;
	  				odrLog=false;   
	  				i+=6;
	  			}
	  			else if (i<(in.length()-3) && in.substring(i,i+4).equals("<DR>")){
	    		    drLog=true;  
					i+=4; 
					
				}
				else if(i<(in.length()-4) && in.substring(i,i+5).equals("</DR>")){
					tag=true;
					drLog=false;   
					i+=5;
					
				}
	  			else if(i<(in.length()-5) && in.substring(i,i+6).equals("<sodr>")){  				
	  				sOdrLog=true;
	  				i+=6;
	  			}
	  			else if(i<(in.length()-6) && in.substring(i,i+7).equals("</sodr>")){
	  				tag=true;
	  				sOdrLog=false;
	  				i+=7;
	  			}
	  			else if(i<(in.length()-4) && in.substring(i,i+5).equals("<zag>")){
	  				zagLog=true;  
	  				i+=5;
	  			}
	  			else if(i<(in.length()-5) && in.substring(i,i+6).equals("</zag>")){
	  				tag=true;
	  				zagLog=false;
	  				i+=6;
	  			}
	  			else if(i<(in.length()-3) && in.substring(i,i+4).equals("<np>")){
	  				signatura="";
	  				zag="";
	  				odrednica="";
	  				sOdrednica="";
	  				jos=false;
	  				np=true;
	  				i+=4;
	  			}
	  			else{
	  				 if (in.substring(i,i+1).equals("\n")) {
	  				 	    
	  				 	    if (red.endsWith("<BR>")){
	  				        jos=false; 
	  				        nl=true; 
	  				 	    }  				        
	  			   }  			  
	  			   if (sigLog)
	  			       signatura+=in.substring(i,i+1);
	  			   else if (odrLog){
	   			       odrednica+=in.substring(i,i+1);  
	  			   }
	  			   else if (sOdrLog){
	    			   sOdrednica+=in.substring(i,i+1); 
	  			   }
	  			   else if (zagLog){  			   	   
	    			   	zag+=in.substring(i,i+1);
	    			   	
	  			   }
	  			 else if (ser){  			   	   
				   	zagS+=in.substring(i,i+1);
				   	
				   	i++;
				   }
	  			   if(!tag || !ser ){
	  			   	if (!ser){
	  			    	if (!drLog ){
	  			    		
	  			    		if(i<(in.length()-2) && in.substring(i,i+3).equals("<B>")){
	  			  				bold=true;	   	  				
	  			  			}
	  			  			else if(i<(in.length()-3) && in.substring(i,i+4).equals("</B>")){	  				
	  			  				bold=false;	    			
	  			  			}
	  			            red+=in.substring(i,i+1);
	  			    	}
	   			       i++;	
	  			   }
	  			   }
	  			   else{
	  			   	tag=false;
	  			   }
	  			}  			
	  		}
	  		
	  		if (nl){  			
	  		   br++; //System.out.println(br);
	  		   }
	  		if (np){
	  			np=false;
	  			for(int k=br;k<Report.brmax;k++ ){
	  				stranica=stranica+"<BR>\n";   
	  			}  			
	  			br=0;  
	  			strana=1;
	  			
	  		    stranica=stranica+"<BR>\n"; 
	  			
	  			
	  			out+=stranica;
	  			stranica="";
	  		}  		
	  		else if (br==Report.brmax){
	  			
	  			String in1 = StringUtils.replace(in.substring(i), "&nbsp;", " ");
	  			
	  	          if (in1.length() > Report.bkmax || (in1.indexOf("<BR>")!=-1)) { 
	  	          	      if (bold && !red.startsWith("<B>") ){ 	  	                  
			         	       stranica+="</B>"; 
	  	          	      }
	  	          	      
	  			          strana++;bk=1;
	  			          if (Report.nextPage!=""){
	  			          	out+=stranica+Report.izlaz1+"<BR>\n"+rightAlign(String.valueOf(strana))+"<BR>\n";
	  			            br=2+brojRedova(signatura); 
	  			          }
	  			          else{
	  			          	if (strana==2){
	  			          		out+=rightAlign(String.valueOf(strana-1))+"<BR>\n"; 
	  			          	    out+=stranica+rightAlign(String.valueOf(strana))+"<BR>\n";
	  			          	    br=2+brojRedova(signatura); 
	  			          		
	  			          	}
	  			          else	{
	  			          	out+=stranica+"<BR>\n"+rightAlign(String.valueOf(strana))+"<BR>\n";
	  			            br=2+brojRedova(signatura); 
	  			            }
	  			          }
	  			          stranica="";
	  			          stranica+=signatura; 
				         
				        if (!sOdrednica.equals("")){
				        	  
				   	          out+=sOdrednica;
					          br+=brojRedova(sOdrednica);
				        }
				        if (!odrednica.equals("")){			   	            
				        	stranica+=odrednica;
					             br+=brojRedova(odrednica);
				        }
				        else if (!zag.equals("")){			        	      
				        	stranica+=zag;  
					              br+=brojRedova(zag);
				        }		
				        else if (!zagS.equals("")){		        	      
				        	stranica+=zagS;  
				              br+=brojRedova(zagS);
			        }
				        if (bold && !red.startsWith("<B>")){
				        	stranica+="<B>"; 		
				        }
	  	          }
	  		}
	  		
	  		stranica+=red;
	  	}
	  	
	  	    out+=stranica;
	  	    
	  	    	for(int k=br;k<Report.brmax;k++ ){
					out=out+"<BR>\n";   br++; 
				}
	  	   
	  		
	  	return out;
	  }
	  public int brojRedova(String word){
	  	int j=0;
	  	for (int i=0;i<word.length();i++){
	  		if( word.substring(i,i+1).equals("\n")){
	  			j++;
	  		}
	  	}
	  	return j;
	  }
	  /** Desno ravnanje stringa na stranici
      @param in ulazni string koji se poravnava
      @return desno poravnat string
  */
  private String rightAlign(String in) {

    if (!in.equals("")) {
     String blanko = new String("");
     for (int j=bk; j < (Report.bkmax-in.trim().length()); j++)
        blanko += "&nbsp;";
     in = blanko + in;
 //     in = blanko + in + "<BR>\n";
    }
    return in;
  }
  public static int toInt(String num){
  	br=0; int k=1;
  	for(int i=num.length()-1;i>=0;i--){
  		int pom;   		
  		if (num.charAt(i)=='1')
  			pom=1;
  		else if (num.charAt(i)=='2')
  			pom=2;
  		else if (num.charAt(i)=='3')
  			pom=3;
  		else if (num.charAt(i)=='4')
  			pom=4;
  		else if (num.charAt(i)=='5')
  			pom=5;
  		else if (num.charAt(i)=='6')
  			pom=6;
  		else if (num.charAt(i)=='7')
  			pom=7;
  		else if (num.charAt(i)=='8')
  			pom=8;
  		else if (num.charAt(i)=='9')
  			pom=9;
  		else 
  			pom=0;
  		br+=pom*k;
  		k*=10;
			
  	}
  	return br;
  }

}
