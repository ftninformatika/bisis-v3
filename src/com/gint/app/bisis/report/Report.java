package com.gint.app.bisis.report;

import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.*;


import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;
import com.gint.util.string.UnimarcConverter;
import freemarker.template.*;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;



// FIZICKI FORMAT LISTICA: (15cm x 7,5cm) odnosno (5,91'' x 2,95'')
// OBAVEZNO !!!!!!!!!!!
// Potrebno je na sistemu defilisati format BibListic koji je 13,50cm x 7,70cm i TopMargin je 0,50 cm

/** Report -- pocetna klasa za generisanje listica.
    @author Tatjana Zubic tanja@uns.ns.ac.yu
    @version 1.0
  */
public class Report {

	  public static int brRed;
	  public static String currentType="";
	  public  static String locale;
	  public  static String nextPage;
	  public  static int brRedova;
	  private static UnimarcConverter conv = new UnimarcConverter();
	  
	  public static String[] reportTypeDesc;
	  public static String[] reportTypes;
	  public static int translateX;
	  public static int translateY;
	  public static String fontSize;
	  static String izlaz1;  
	  static int brmax; 
	  static int bkmax;
	 
  /** Formira izvestaj za vise zapisa.
      @param docIDs niz ID zapisa
      @param rezances niz odgovarajucih zapisa
      @param typeCode tip listica
      @return HTML izvestaj
    */
  public static String makeMulti(int[] docIDs, String[] rezances, String typeCode) {
    String retVal = "";
    for (int i = 0; i < docIDs.length; i++) {
       retVal += makeOne(docIDs[i], rezances[i], typeCode);
    }
    return retVal;
  }
 
  /** Formira izvestaj za jedan zapis.
      @param docID ID zapisa
      @param rezance Zapis
      @param typeCode tip listica
      @return HTML izvestaj
    */
  
  public static String makeOne(int docID, String rezance, String typeCode){
  	
    String izlaz;
    boolean format=true;
    boolean formatRed=true;
    boolean formatStrana=true;
    int brSignatura;
    String type;
    
    
    Configuration  cfg=Configuration.getDefaultConfiguration();
    cfg.setClassForTemplateLoading(Report.class,"templejti/"+locale+"/");
    
    
    Base Base=new Base(docID, rezance, typeCode);   
    
  	Template temp=null;
  	Template temp1=null;
  	try{
  		 temp = cfg.getTemplate(typeCode+"_"+locale+".ftl");
        
  	}catch(Exception e1){
  		try{
  			 temp = cfg.getTemplate(typeCode+".ftl");
  			
  		}catch(Exception e2){
  			return izlaz="Ne postojeci tip listica";
  		}
  		
        
  	}
  	
  	try{
			 temp1 = cfg.getTemplate("_novaStr_"+locale+".ftl");
		}catch(Exception e3){
			try{
				 temp1 = cfg.getTemplate("_novaStr.ftl");
			}catch(Exception e4){
				return izlaz=com.gint.app.bisis.editor.Messages.get("BISISAPP_CMD_ELIST_UNKNOWNTYPE");
		  		
			}
			
		}		
		
		Map root=Base.struktura();
		root.put("docID",new Integer(Base.getID()));
		root.put("nextPage",nextPage);
		
		try{
			ResourceBundle rb = PropertyResourceBundle.getBundle(
			        Report.class.getPackage().getName()+".templejti."+locale+"."+typeCode,new Locale(locale));
			
			
		    //format=rb.getString("format").equals("true");
		    formatRed=rb.getString("formatRed").equals("true");
			formatStrana=rb.getString("formatStrana").equals("true");
			
			brmax=Base.toInt(rb.getString("brmax"));
			bkmax=Base.toInt(rb.getString("bkmax"));
			
			type=rb.getString("type");
			brSignatura=Base.toInt(rb.getString("brSignatura"));
			root.put("brSignatura",new Integer(brSignatura));
			root.put("brk",new Integer (bkmax-1));
		}catch(Exception ex1){			
				return izlaz="Greska u odredjivanju osobina";
		}
	
	if (!Base.checkPubType(type))
		return izlaz = "<BR><BR>" + com.gint.app.bisis.editor.Messages.get("BISISAPP_REPORT_WRONGPUBTYPE") + "<BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR>";

    Writer out=new StringWriter();
    Writer out1=new StringWriter();
    try {    	
		temp.process(root,out);
		temp1.process(root,out1);
	} catch (Exception ex) {System.out.println("Ne valja templejt1");}
	
	try {
		out.flush();
		out1.flush();
	} catch (IOException e) {		
		e.printStackTrace();
	}
	
	
    izlaz1=out1.toString();
    izlaz1=Base.format(izlaz1);
    
    izlaz=out.toString();
   // if (format)
       izlaz=Base.format(izlaz);
       //System.out.println(izlaz);
    if(formatRed && !formatStrana) {
        izlaz=Base.formatRed1(izlaz);        
    }
    else if (formatRed){
    	izlaz=Base.formatRed(izlaz);
    	//System.out.println(izlaz);
    }
    if(formatStrana)
        if(!izlaz.equals("")){
            izlaz=Base.formatStrana(izlaz);
            //System.out.println(izlaz);
        }
    return izlaz;
  }

  public static void ucitajParam(){
  	String str="";
  	try{
	  INIFile iniFile = new INIFile(
  	      FileUtils.getClassDir(Report.class) + "/config.ini"); 
	  translateX=Base.toInt(iniFile.getString("report", "translateX"));
      translateY=Base.toInt(iniFile.getString("report", "translateY"));
      fontSize=iniFile.getString("report", "fontSize"); 
      brRedova=Base.toInt(iniFile.getString("report", "brRedova"));
      locale = iniFile.getString("report", "locale");
      nextPage= iniFile.getString("report", "nextPage");
      currentType=iniFile.getString("report", "currentType");
  	}catch(Exception e) {
  		try{
  			ResourceBundle rb = PropertyResourceBundle.getBundle(
  	        "com.gint.app.bisis.web.Settings");
  		  //INIFile iniFile = new INIFile(
  	  	     // FileUtils.getClassDir(Report.class) + "/config.ini"); 
  	  		//String driver     = rb.getString("driver");
  		  //translateX=Base.toInt(iniFile.getString("report", "translateX"));
  	      //translateY=Base.toInt(iniFile.getString("report", "translateY"));
  	      //fontSize=iniFile.getString("report", "fontSize"); 
  	      //brRedova=Base.toInt(rb.getString( "brRedova"));
  	      locale = rb.getString( "localeB");
  	      //nextPage= iniFile.getString("report", "nextPage");
  	      //currentType=iniFile.getString("report", "currentType");

  	  	}catch(Exception e1) {
  	  		
  			e1.printStackTrace();
  		}
		e.printStackTrace();
	}
	
      
  }

  public final static void loadReportTypes()  {
  	  String str=""; 
      try {
        String dirName = "/com/gint/app/bisis/report/templejti/"+locale;
  		String[] files = FileUtils.listFiles(Report.class, dirName);        
        int brojFajlova=0;
        for (int i = 0; i < files.length; i++) {        	 
          if (files[i].endsWith(".ftl")){
            str=files[i].replaceAll("_"+locale+".ftl","");
  		    str=str.substring(str.lastIndexOf(locale)+locale.length()+1);
  		    if (!str.startsWith("_")) {  		
          	  brojFajlova++;       
  		    }
          }
        }
        reportTypes = new String[brojFajlova];
        boolean existCurrent=false;
        int j=0;
        for (int i = 0; i < files.length; i++) {
        	if (files[i].endsWith(".ftl")) {
        		str=files[i].replaceAll("_"+locale+".ftl","");
        		str=str.substring(str.lastIndexOf(locale)+locale.length()+1);   
        		
        		if( !str.startsWith("_")) {
        			 reportTypes[j]=str; 
            	    if (str.equals(currentType))
            	    	existCurrent=true;
            	    j++;
        	    }
            }
          }
        if(!existCurrent ){        	
        	JOptionPane.showMessageDialog(null, "Ne postoji tekuci tip");
            
        }
        	
      } catch (Exception ex) {
      	System.out.println("Ne postoji direktorijum");
      }
  }

}
