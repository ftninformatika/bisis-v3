package com.gint.app.bisis.editor.report.izvrsnoVece;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.gint.app.bisis.editor.Environment;
import com.gint.app.bisis.editor.RezanceUtilities;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;
import com.gint.app.bisis.editor.report.Catalog;
import com.gint.app.bisis.editor.report.Concept;
import com.gint.app.bisis.editor.report.ReportFields;
import com.gint.util.string.Charset;
import com.gint.util.string.UnimarcConverter;

public class Sredstva {
	static String status="";
	static String nazivStatusa="";
	static HashMap co996q;
	static UnimarcConverter conv = new UnimarcConverter();
	  private static HashMap getIntCodes(String po, String pp) {
		    Statement stmt;
		    ResultSet rset;
		    HashMap intCodes = new HashMap();

		    try {
		      Connection conn=Environment.getConnection();
		      stmt = conn.createStatement();
		      rset = stmt.executeQuery("SELECT isid, is_naziv FROM interni_sifarnik WHERE ppo_po_poljeid=" + po + " AND ppo_potpoljaid='" + pp + "' ORDER BY isid");
		      while (rset.next()) {
		        String key = Charset.YUSCII2Unicode(rset.getString(1).trim());
		        String value = Charset.YUSCII2Unicode(rset.getString(2).trim());
		        intCodes.put(key, value);
		      }
		      rset.close();
		      stmt.close();
		    }
		    catch (SQLException ex) {
		      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_DBERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
		    }
		    return intCodes;
		  }
  private static String getCodeValue(HashMap ic, String co) {

		    if (ic.isEmpty() || co.equals(""))
		      return "";
		    if (ic.containsKey(co))  {
		      return (String)ic.get(co);
		    }
		    else
		      return com.gint.app.bisis.editor.Messages.get("BISISAPP_INVBOOK_UNKNOWNCODE");
    }
   private static boolean exist996q(Field f){
	   Vector v=f.getSubfieldsByName("q");
	   if (v.size()==0){
	     return false;
	   }else{
		   return true;
	   }
   }
	public static void toFile(String docID,String doc,BufferedWriter out){
		try {
		
		Concept c = new Concept();
		ReportFields rf = new ReportFields();
		if(doc!=null){
		Vector fields = RezanceUtilities.unpackRezance(conv.Unimarc2Unicode(doc));
		Catalog cat = new Catalog(Integer.parseInt(docID),doc,"");
		int typePubl = cat.getType();
		int i=0;
		int brojPrimeraka=0;
		boolean exist;
		if (typePubl==1){
			String naslov="";
			String izdavac="";
			Vector v=c.getField(fields, "200", " ", " ");
			Vector v1=c.getField(fields, "210", " ", " ");
			Vector v2=c.getField(fields, "996", " ", " ");
			if (v.size()>0){
				Field f=(Field)v.elementAt(0);
				if(f.getSubfieldsByName("a").size()!=0)
				naslov=(String)((Subfield)(f.getSubfieldsByName("a").elementAt(0))).getContent();
			 }
			if (v1.size()>0){
				Field f1=(Field)v1.elementAt(0);
				if(f1.getSubfieldsByName("d").size()>0){
					izdavac=(String)((Subfield)(f1.getSubfieldsByName("d").elementAt(0))).getContent();
				}
			}
			while (i < v2.size()) {
				exist=exist996q((Field)v2.elementAt(i));
				
				if (!exist){ //ako polje 996q ne postoji
					nazivStatusa="Aktivno";
					
				}else{
					status=rf.getFieldContent996((Field)v2.elementAt(i),"q");
					co996q = getIntCodes("996", "q");
					nazivStatusa= getCodeValue(co996q, status);
				}
				if (nazivStatusa.compareToIgnoreCase("Aktivno")==0)
                    brojPrimeraka++;
				i++;
			}
			if(brojPrimeraka>0){
			   out.write(docID);
			   out.write("#");
		       out.write(Charset.YUSCII2Unicode(naslov));
		       out.write("#");
		       out.write(Charset.YUSCII2Unicode(izdavac));
		       out.write("#");
			   out.write(Integer.toString(brojPrimeraka));
		       out.newLine();
		      
			}
		}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
