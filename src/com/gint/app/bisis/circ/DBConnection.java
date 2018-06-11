package com.gint.app.bisis.circ;

import com.objectspace.jgl.HashMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.text.DateFormat;
import javax.swing.*;

import com.gint.util.file.INIFile;
import com.gint.util.file.FileUtils;
import com.gint.util.string.*;

// Klasa koja uspostavlja konekciju sa bazom, i unutar koje se kreiraju osnovni DataSet-ovi
public class DBConnection {
  private Connection  connection;
  private DataSet dsSingle, dsGroup, dsInsts, dsUsers,
                  dsCategs, dsEduLvl, dsMmbrType, dsUserType,
                  dsPlaces, dsLendType, dsMembership, dsBranch, dsSigning, dsLanguage, dsLending;
  private static DateFormat df;
  private static java.util.Date currentDate;
  private String officialID = "";
  private String officialName = "";
  private boolean officialAdmin = false;
  static private String printCommand="";
  static private String printBrowser="";
  private static HashMap co996q = new HashMap();
  private static String dbType = ""; // BRANKO 15.07.2003.
  private static String homeBranch ="";
  private static int codeSize = 2;
  private static java.util.HashMap pref = null;
  private static INIFile iniFile = null;
  
  public DBConnection() {
/********/
  	iniFile = new INIFile(FileUtils.getClassDir(getClass()) + "/config.ini"); 
    String driverClass=iniFile.getString("database", "driver");
    String url=iniFile.getString("database", "url");
    String username=iniFile.getString("database", "username");
    String passwd=iniFile.getString("database", "password");
    dbType = iniFile.getString("database", "type"); // BRANKO 15.07.2003.
    homeBranch = iniFile.getString("circ", "homeBranch");
    try{
    	codeSize = Integer.parseInt(iniFile.getString("circ", "codeSize"));
    }catch(NumberFormatException e){
    	
    }
    
    try {

        Class.forName(driverClass);

      } catch (ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, Messages.get("DBCONNECTION_OPTIONPANE_MSG1"), Messages.get("DBCONNECTION_OPTIONPANE_MSG2"), JOptionPane.ERROR_MESSAGE);
        System.err.println(ex);
        System.exit(0);
      }

    try {
      connection = DriverManager.getConnection(url,username,passwd);

      connection.setAutoCommit(false);

      Statement stmt = connection.createStatement();
      //ResultSet rset = stmt.executeQuery("alter session set nls_date_format = 'dd.mm.yyyy'");
      stmt.execute("alter session set nls_date_format = 'dd.mm.yyyy'");
      //rset.close();
      //stmt.close();
      df = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.GERMANY);
      df.setTimeZone(TimeZone.getDefault());

      printCommand=iniFile.getString("printing", "print.command");
      printBrowser=iniFile.getString("printing", "browser.path");


      String query = "select isid, is_naziv from interni_sifarnik where PPO_POTPOLJAID=\'q\' and PPO_PO_POLJEID=\'996\'";
      Statement stmt1 = connection.createStatement();
      ResultSet rset1 = stmt1.executeQuery(query);
      co996q = new HashMap();
      while (rset1.next()) {
        co996q.put(rset1.getString("ISID").trim(),rset1.getString("IS_NAZIV").trim());
      }
      rset1.close();
      connection.commit();
      stmt1.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, Messages.get("DBCONNECTION_OPTIONPANE_MSG3"), Messages.get("DBCONNECTION_OPTIONPANE_MSG4"), JOptionPane.ERROR_MESSAGE);
      System.err.println("Greska prilikom povezivanja sa bazom!");
      System.exit(0);
    }
        
        
        try {
            pref = new java.util.HashMap();	
            String line = "", key = "", value = "";
            StringTokenizer st;
            BufferedReader in = new BufferedReader(new FileReader(FileUtils.getClassDir(getClass()) + "/map.txt"));
            try{
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.equals(""))
                  continue;
                st = new StringTokenizer(line, "=");
                if (st.hasMoreTokens()) {
                    key = st.nextToken().trim();
                    if (st.hasMoreTokens()) {
                      value = st.nextToken().trim();
                      pref.put(key, value);
                    }
                    
                 }
                
            }
            }catch (IOException ex){
            	ex.printStackTrace();
            }
            } catch (FileNotFoundException e) {
            	JOptionPane.showMessageDialog(null, Messages.get("DBCONNECTION_OPTIONPANE_MSG1"), Messages.get("DBCONNECTION_OPTIONPANE_MSG2"), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(0);
            }


  }

  public DBConnection(String url, String driverName, String user, String passwd) {
    //  BRANKO 15.07.2003. ---
    if (url.indexOf("oracle") != -1)
      dbType = "oracle";
    else if (url.indexOf("sapdb") != -1)
      dbType = "sapdb";
    else
      dbType = "oracle";
    //  BRANKO 15.07.2003. ---
    try {
      Class.forName(driverName);
      connection = DriverManager.getConnection(url, user, passwd);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("Cannot find the database driver classes.");
      System.err.println(ex);
    }
    catch (SQLException ex) {
      System.err.println("Cannot connect to this database.");
      System.err.println(ex);
    }
  }

  public Connection getConnection(){
    return connection;
  }

  public void close() throws SQLException {
    connection.close();
  }

  protected void finalize() throws Throwable {
    super.finalize();
  }

  public void createDataSets(){
    dsUsers = new DataSet(connection, "USERS",new String[] {"ID"});
    dsSingle = new DataSet(connection,"SINGLE",new String[]{"USER_ID"});
    dsGroup = new DataSet(connection,"GROUPS",new String[]{"USER_ID"});
    dsInsts = new DataSet(connection,"INSTITUTIONS",new String[]{"USER_ID"});
    dsUserType = new DataSet(connection,"USER_TYPES",new String[] {"ID"});
    dsCategs = new DataSet(connection,"USER_CATEGS",new String[]{"ID"});
    dsEduLvl = new DataSet(connection,"EDU_LVL",new String[]{"ID"});
    dsMmbrType = new DataSet(connection,"MMBR_TYPES",new String[]{"ID"});
    dsPlaces = new DataSet(connection,"LOCATION",new String[] {"ID"});
    dsLendType = new DataSet(connection,"LEND_TYPES",new String[] {"ID"});
    dsMembership = new DataSet(connection,"MEMBERSHIP", new String[]{"USER_CTGR","MMBR_TYPE","MDATE"});
    dsBranch = new DataSet(connection,"BRANCH", new String[]{"ID"});
    dsSigning = new DataSet(connection, "SIGNING", new String[]{"SINGLE_ID"});
    dsLanguage = new DataSet(connection, "LANGUAGE", new String[]{"ID"});
    dsLending = new DataSet (connection, "LENDING", new String[]{"SINGLE_ID"});
  }

  public DataSet getUsersModel(){
    return dsUsers;
  }

  public DataSet getSingleModel(){
    return dsSingle;
  }

  public DataSet getGroupModel(){
    return dsGroup;
  }

  public DataSet getInstModel(){
    return dsInsts;
  }

  public DataSet getEduLvl(){
    return dsEduLvl;
  }

  public DataSet getCategs(){
    return dsCategs;
  }

  public DataSet getMmbrType(){
    return dsMmbrType;
  }

  public DataSet getUserType(){
    return dsUserType;
  }

  public DataSet getPlaces(){
    return dsPlaces;
  }

  public DataSet getMembership(){
    return dsMembership;
  }

  public DataSet getLendType(){
    return dsLendType;
  }
  
  public DataSet getBranch(){
    return dsBranch;
  } 
  
  public DataSet getSigning(){
    return dsSigning;
  }
  
  public DataSet getLanguage(){
    return dsLanguage;
  } 
  
  public DataSet getLending(){
    return dsLending;
  } 

  public DateFormat getDateFormat(){
    return df;
  }

  public static String getTodaysDate(){
    currentDate = new java.util.Date();
    currentDate.setTime(System.currentTimeMillis());
    return formatDate(currentDate);
  }

  public static String formatDate(java.util.Date unformated){
    return new String (df.format(unformated));
  };

  public static Object ObjectToTS(Object obj){
    Timestamp timestamp = new Timestamp(0);
    try {
      timestamp.setTime((df.parse(obj.toString()).getTime()));
      return timestamp;
    }
    catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }

  public static Object TSToObject(Object obj){
    java.sql.Date date = new java.sql.Date(0);
    if(obj != null){
      Timestamp ts = (Timestamp)obj;
      date.setTime(ts.getTime());
      String s = new String("");
      try {
        s = df.format(date);
        return s;
      }
      catch (Exception e){
        e.printStackTrace();
        return null;
      }
    }else
      return "";
  }

  public static java.util.Date StringToDate(String stringDate){
    java.sql.Date date = new java.sql.Date(0);
    try{
      date.setTime((df.parse(stringDate)).getTime());
    }catch(ParseException e){
      e.printStackTrace();
    }
    return new java.util.Date(date.getTime());
  }

  public static long StringToTime(String stringDate){
    java.sql.Date date = new java.sql.Date(0);
    try{
      date.setTime((df.parse(stringDate)).getTime());
    }catch(ParseException e){
      e.printStackTrace();
    }
    return date.getTime();
  }

  //BRANKO 15.07.2003.
  public static String toDatabaseDate(String date) {
    if (dbType.equals("sapdb"))
      return toSAPDate(date);
    else
      return toOracleDate(date);
  }

  private static String toSAPDate(String stringDate){
    int tacka = stringDate.indexOf(".");
    String res = "";
    if (tacka > 0) {
      res = stringDate.substring(0,tacka);
      stringDate = stringDate.substring(tacka+1);
      tacka = stringDate.indexOf(".");
      if (tacka > 0) {
        res = stringDate.substring(0,tacka) + "-" + res;
        stringDate = stringDate.substring(tacka+1);
        res = stringDate.substring(0) + "-" + res + " 00:00:00.000000";
      }
    }
    return res;
  }
  
  private static String toOracleDate(String stringDate) {
    return stringDate;//.substring(0, stringDate.indexOf(" "));
  }

  //BRANKO 15.07.2003.
  public static String fromDatabaseDate(String date) {
    if (dbType.equals("sapdb"))
      return fromSAPDate(date);
    else
      return fromOracleDate(date); 
  }
  
  private static String fromSAPDate(String stringDate){
    int tacka = stringDate.indexOf("-");
    String res = "";
    if (tacka > 0) {
      res=stringDate.substring(0, tacka);
      stringDate = stringDate.substring(tacka+1);
      tacka = stringDate.indexOf("-");
      if (tacka > 0) {
        res=stringDate.substring(0,tacka) + "." + res;
        stringDate = stringDate.substring(tacka+1);
        res = stringDate.substring(0,2)+"."+res;
      }
    }
    return res;
  }
  
  public static String fromOracleDate(String stringDate) {
    return stringDate;//.substring(0, stringDate.indexOf(" "));
  }

  public void setOfficial(String id){
    officialID = id;
  }

  public String getOfficial(){
    return officialID;
  }
  
  public void setOfficialName(String id){
    officialName= id;
  }

  public String getOfficialName(){
    return officialName;
  }
  
  public void setOfficialAdmin(boolean admin){
	  officialAdmin = admin;
  }
  
  public boolean isOfficialAdmin(){
	  return officialAdmin;
  }


  public static String getPrintCommand(){
    return printCommand;
  }


  public static String getPrintBrowser(){
    return printBrowser;
  }
  
  public static String toDBMember(String id){
  	String dbid = "";
  	int ind = id.indexOf('/');
  	try{
  	if (ind != -1){
  		if (id.substring(ind+1).trim().length()==codeSize ){
  			dbid = id.substring(ind+1).trim();
  		}else{
  			dbid = StringUtils.padZeros(Integer.parseInt(id.substring(ind+1).trim()),codeSize);
  		}
  		dbid = dbid + StringUtils.padZeros(Integer.parseInt(id.substring(0,ind)),11-codeSize);
  	}else{
  		dbid = StringUtils.padZeros(Integer.parseInt(homeBranch),codeSize) + StringUtils.padZeros(Integer.parseInt(id.trim()),11-codeSize);	
  	}
  	}catch(NumberFormatException e){
			
	}
  	return dbid;
  }
  
  public static String fromDBMember(String dbid){
  	String id = "";
  	Integer num = new Integer(dbid.substring(codeSize));
  	id = num.toString()+ "/"+ dbid.substring(0,codeSize);
  	return id;
  }
  
  public static String toDBInventory(String ctlg){
  	String dbctlg = "";
  	int slash = ctlg.indexOf('/');
  	int dash = ctlg.indexOf('-');
  	int dot = ctlg.indexOf(':');
  	if (dot == -1){
  		dot = ctlg.indexOf('!');
  	}
  	
  	try{
  	if (slash != -1){
  		if (dash != -1){
  			String branch = ctlg.substring(slash+1,dash).trim();
  			String mat = ctlg.substring(dash+1).trim();  
  			if (getPref(branch).length() == 4 ){
  				if (dot != -1){
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dot)),7) + ctlg.substring(dot, slash);
  				}else{
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,slash)),7);
  				}     
  			}else{
  				if (dot != -1){
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(mat), 2) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dot)),7) + ctlg.substring(dot, slash);
  				}else{
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(mat), 2) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,slash)),7);
  				} 
  			}
  		}else{
		  	String branch = ctlg.substring(slash+1).trim();
		  	if (getPref(branch).length() == 4 ){
		  		if (dot != -1){
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dot)),7) + ctlg.substring(dot, slash);
  				}else{
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,slash)),7);
  				}  
 			}else{
 				if (dot != -1){
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dot)),9) + ctlg.substring(dot, slash);
  				}else{
  					dbctlg = getPref(branch) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,slash)),9);
  				}   
 			}
  		}
  	}  else {
  		if (dash != -1){
  			String mat = ctlg.substring(dash+1).trim();
  			if (dot != -1){
  				dbctlg = StringUtils.padZeros(Integer.parseInt(homeBranch),2) + StringUtils.padZeros(Integer.parseInt(mat),2) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dot)),7) + ctlg.substring(dot, dash);
			}else{
				dbctlg = StringUtils.padZeros(Integer.parseInt(homeBranch),2) + StringUtils.padZeros(Integer.parseInt(mat),2) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dash)),7);
			}  
  		}else{
  			if (dot != -1){
  				dbctlg = StringUtils.padZeros(Integer.parseInt(homeBranch),2) + StringUtils.padZeros(Integer.parseInt(ctlg.substring(0,dot)),9) + ctlg.substring(dot).trim();
			}else{
				dbctlg = StringUtils.padZeros(Integer.parseInt(homeBranch),2) + StringUtils.padZeros(Integer.parseInt(ctlg.trim()),9);	
			}	
  		}
  	}
  	}catch(NumberFormatException e){
			
	}
  	return dbctlg;
  }
  
  public static String getPref(String userpref){
  	String dbpref = "";
  	if (pref.containsKey(userpref))
  	     dbpref = (String)pref.get(userpref);
  	return dbpref;
  }
  
  public static int getCodeSize(){
  	return codeSize;
  }

  public static boolean isValidDate(String stringDate){
  	String dan;
  	String mesec;
  	String godina;
  	try{
       dan = new String(stringDate.substring(0,2));
       mesec = new String(stringDate.substring(3,5));
       godina = new String(stringDate.substring(6,stringDate.length()));
  	  }catch(java.lang.StringIndexOutOfBoundsException e){
  		  System.out.println("<DBConnection>: isValidDate - Greska u formatu datuma !!!");
          return false;
  	  }
    int xdrez;
    int idan=0, imesec=0, igodina=0;

    if (stringDate.length() != 10)
      return false;

    try {
      idan = (int)Integer.parseInt(dan);
      imesec = (int)Integer.parseInt(mesec);
      igodina = (int)Integer.parseInt(godina);
    }
    catch (NumberFormatException e) {
      System.out.println("<DBConnection>: isValidDate - Greska u formatu datuma !!!");
      return false;
    }
    if (imesec==1||imesec==3||imesec==5||imesec==7||imesec==8||imesec==10||imesec==12) {
      if (idan>=1 && idan<=31);
      else
        return false;
    }
    else if(imesec==4||imesec==6||imesec==9||imesec==11) {
      if (idan>=1 && idan<=30);
      else
        return false;
    }
    else if (igodina%4==0 && imesec==2) {
      if (idan>=1 && idan <=29);
      else
        return false;
    }
    else if (imesec==2) {
      if (idan>=1 && idan<=28);
      else
        return false;
    }

    if ((igodina>=1970) && (igodina<=2100)) xdrez=0;
    else xdrez=1;

    if ((imesec>=1) && (imesec<=12));
    else xdrez=xdrez+1;

    if ((idan>=1) && (idan<=31));
    else xdrez=xdrez+1;

    if (xdrez==0)
      return true;
    else
      return false;
  }

   public static String getState996q(String key) {
      String opis = (String)co996q.get(key.trim());
      return opis;
   }
   
   // BRANKO 15.07.2003.
   public static String getDbType() {
     return dbType;
   }
   
}
