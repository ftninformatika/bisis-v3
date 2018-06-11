package com.gint.app.bisis4.common.records;

import java.util.Iterator;
import java.util.List;

import com.gint.util.string.StringUtils;

public class Signature {
	
	   /** Generise prikaz bibliotekarske signature za korisnika.
	   *   Format: [d ][l-][i-][f-][n][/s][ u]
	   *  @param rec Objektni model zapisa
	   *  @return Interpretirana signatura
	   */
	  public static String userDisplay(Record rec, String ctlgno) {
	    String d, f, s, n, a, i, l, u, w, x, y, z;
	    d = ""; f = ""; s = ""; n = ""; a = ""; i = ""; l = ""; u = "";
	    w = ""; x = ""; y = ""; z = "";
	    String retVal = "";
	    List fields = rec.getFields("996");
	    Field field = null;
	    boolean found = false;
	    Iterator it = fields.iterator();
	    while (!found && it.hasNext()){
	    	field = (Field)it.next();
	    	if (ctlgno.equals(field.getSubfieldContent('f'))){
	    		found = true;
	    	}
	    }
	    if (found){
	    	if (field.getSubfield('d') != null){
			    List subsubfields = field.getSubfield('d').getSubsubfields();
			    it = subsubfields.iterator();
			    while (it.hasNext()) {
			      Subsubfield tmp = (Subsubfield)it.next();
			      char subsubfieldID = tmp.getName();
			      switch (subsubfieldID) {
			        case 'd': // Dublet
			          d = tmp.getContent();
			          break;
			        case 'f': // Format
			        	try {
			        		int num = Integer.parseInt(tmp.getContent());
			        		f = StringUtils.arabicToRoman(num);
			        	} catch (NumberFormatException ex) { 
			        		f = tmp.getContent();
			        	}
			          break;
			        case 's': // Numeracija
			          s = tmp.getContent();
			          break;
			        case 'n': // Tekuci broj
			          n = tmp.getContent();
			          break;
			        case 'a': // Oznaka varijante ili ABC oznaka
			          a = tmp.getContent();
			          break;
			        case 'i': // Interna oznaka
			          i = tmp.getContent();
			          break;
			        case 'l': // Oznaka podlokacije
			          l = tmp.getContent();
			          break;
			        case 'u': // Slobodan pristup UDK
			          u = tmp.getContent();
			          break;
			        case 'w': // Razresenje numeracije (4. nivo)
			          w = tmp.getContent();
			          break;
			        case 'x': // Razresenje numeracije (1. nivo)
			          x = tmp.getContent();
			          break;
			        case 'y': // Razresenje numeracije (2. nivo)
			          y = tmp.getContent();
			          break;
			        case 'z': // Razresenje numeracije (3. nivo)
			          z = tmp.getContent();
			          break;
			      }
			    }
				    
			    if (!d.equals(""))
			      retVal += d + " ";
			    if (!l.equals(""))
				  retVal += l + "-";
			    if (!i.equals(""))
				  retVal += i + "-";
			    if (!f.equals(""))
				  retVal += f + "-";
			    if (!n.equals(""))
			      retVal += n;
			    if (!s.equals(""))
			      retVal += "/"+s;
			    if (!u.equals("")){
				  if (retVal.length() > 0)
					  retVal += " ";
				  retVal += u;
			    }
		    }
	    }
	    return retVal;
	  }

}
