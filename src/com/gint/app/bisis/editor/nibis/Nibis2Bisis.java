/*
 * Created on 2004.4.27
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.gint.app.bisis.editor.nibis;

import java.util.Vector;

/**
 * @author Miroslav Zaric
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Nibis2Bisis {

	static final int  HEADER_CHUNK=12; //velicina jednog bloka u headeru 3+4+5
	static final char NIBIS_FIELD_SEPARATOR    ='#';
	static final char NIBIS_SUBFIELD_SEPARATOR ='$';
	static final char BISIS_FIELD_SEPARATOR    ='\036';
	static final char BISIS_SUBFIELD_SEPARATOR ='\037';
	static final String NIBIS_RECORD_SEPARATOR = "#%";

	public Nibis2Bisis(){
	}

	/**
	 * Funkcija za konverziju Nibis Unimarc formata u Bisis.
	 * Prihvata String koji predstavlja niz Nibis Unimarc-a i vraca
	 * niz stringova koji predstavljaju Bisis Unimarc-e
	 * @param input
	 * @return
	 */
	public static String []convert(String input){
		String []inputUnimarcs=input.split(NIBIS_RECORD_SEPARATOR);
		String []headers=new String[inputUnimarcs.length];
		String []contents=new String[inputUnimarcs.length];
		String []outputUnimarcs=null;
		Vector output=new Vector();
		//trim the leading 23 chars

		for (int i=0;i<inputUnimarcs.length;i++){
			inputUnimarcs[i]=inputUnimarcs[i].substring(23);
			headers[i]=inputUnimarcs[i].substring(0,inputUnimarcs[i].indexOf('*'));
			contents[i]=inputUnimarcs[i].substring(inputUnimarcs[i].indexOf('*'),inputUnimarcs[i].length());
			//System.out.println("Header: "+headers[i]+"\ncontent: "+contents[i]);
			String newUnimarc=null;
			String oneHeader=headers[i];

			//check if header is valid
			if(oneHeader.length()%HEADER_CHUNK == 0){
				newUnimarc="";
				while(oneHeader.length()>0){
					try{
						String fieldNo=oneHeader.substring(0,3);
						int fieldNumber=Integer.parseInt(fieldNo);
						oneHeader=oneHeader.substring(3);
						int fieldLength=Integer.parseInt(oneHeader.substring(0,4));
						oneHeader=oneHeader.substring(4);
						int fieldOffset=Integer.parseInt(oneHeader.substring(0,5));
						oneHeader=oneHeader.substring(5);
						String fieldContent=contents[i].substring(fieldOffset+1,fieldOffset+fieldLength);
						//System.out.println("Field number: "+fieldNumber+" > content: "+fieldContent);
						newUnimarc+="#"+fieldNo+fieldContent;
					}catch(NumberFormatException inf){
						newUnimarc=null;
						break;
					}catch(StringIndexOutOfBoundsException aioob){
						newUnimarc=null;
						break;
					}
				}
				if(newUnimarc!=null && !newUnimarc.equals("")){
					newUnimarc=newUnimarc.replace('#','\036');
					newUnimarc=newUnimarc.replace('$','\037');
					output.add(newUnimarc);
					//System.out.println("NU : "+newUnimarc);
				}
			}

		}
		if(output.size()>0){
			outputUnimarcs=new String[output.size()];
			for(int i=0;i<output.size();i++){
				outputUnimarcs[i]=(String)output.get(i);
			}
		}
		return outputUnimarcs;
	}
}
