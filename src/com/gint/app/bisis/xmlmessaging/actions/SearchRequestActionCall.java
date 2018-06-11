/*
 * Created on 2005.4.4
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.client.BisisSearchForm;
import com.gint.app.bisis.xmlmessaging.client.MessagingError;
import com.gint.app.bisis.xmlmessaging.communication.CommunicationThread;
import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.gint.app.bisis.xmlmessaging.util.DocumentBriefs;
import com.gint.app.bisis.xmlmessaging.util.MessageUtils;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchRequestActionCall implements AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(java.util.LinkedHashMap)
	 */
	public synchronized void initiateAction(BisisSearchForm caller,LinkedHashMap requestParams) {
		// TODO Auto-generated method stub
		try{
			LinkedHashMap libs = (LinkedHashMap)requestParams.get("libs");
			BisisSearchForm holdingForm = caller;
			String []prefixes = (String[])requestParams.get("prefixes");
			String []values=(String[])requestParams.get("values");
			String []operators=(String [])requestParams.get("operators");
			String convId=(String)requestParams.get("convId");
			boolean compress=((Boolean)requestParams.get("compress")).booleanValue();
			if(libs.keySet().size()>0){
				Iterator it=(libs.keySet()).iterator();
				while(it.hasNext()){
					String keyValue=(String)it.next();
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("keyValue="+keyValue);
					LibraryServerDesc thisLib=(LibraryServerDesc)libs.get(keyValue);
					Document doc=createQuery(prefixes,values,operators,thisLib.getUrlAddress(),convId);
					CommunicationThread ct=new CommunicationThread(this,thisLib,doc,holdingForm,convId,MessagingEnvironment.SEARCHREQUEST, compress);
					ct.start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Document createQuery(String []prefixes,String []values, String []operators, String to, String convId){
		Document retVal=null;
		Element bisisReq=MessageUtils.createBisisHeader(to,"PerformSearch","searchquery",convId);
        Element action=new Element("action");
        action.setAttribute("name","PerformSearch");
        Element params=new Element("params");
        params.setAttribute("name","searchquery");
        
        for(int i=0;i<5;i++){
        	if(!(values[i]).equals("")){
        		if(i>0){
        			Element op=new Element("operator");
        	        op.setAttribute("type",operators[i-1]);
        	        params.addContent(op);
                }
        		Element pref=new Element("prefix");
        		int colon=0;
        		if((colon=prefixes[i].indexOf(":"))!=-1)
        			prefixes[i]=prefixes[i].substring(0,colon);
                pref.setAttribute("name",prefixes[i]);
                //values[i]=Charset.convertFromUnicode(values[i],"UTF-8");
                //pref.setText(values[i]);
				try{
                	pref.setText(URLEncoder.encode(values[i],"UTF-8"));
                }catch(UnsupportedEncodingException uee){
            		pref.setText(values[i]);
            	}
                params.addContent(pref);
        	}
        	
        }
        action.addContent(params);
        bisisReq.addContent(action);
        retVal=new Document(bisisReq);
        retVal.setProperty("encoding","UTF-8");
		return retVal;
	}
	
	public synchronized Vector processResponse(Document retVal, LibraryServerDesc libServ, MessagingError me){
		Object[] res;
		Vector results=null;
		boolean error=false;
		String errorCode="";
		if(retVal!=null){
			res=getSearchResponse(retVal,me);
			if(res==null){
				if(me.isActive()){
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("Ima gresaka!!!!");
					error=true;
					me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] "+me);
				}else{
					error=true;
					me.setActive(true);
					me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] \n\tNema razultata za navedeni upit!");
				}
			}else{
				int activeIndex=0;
				if(res.length>0){
					results=new Vector();
					for(int i=0; i<res.length; i++){
						Vector oneRow=new Vector();
						oneRow.add(new Boolean(false));
						oneRow.add(libServ);
						oneRow.add((DocumentBriefs)res[i]);
						results.add(oneRow);									
					}
				}
			}
		}else{
			error=true;
			me.setActive(true);
			me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] \n\tNo response from server!");
		}
		return results;
	}
	
	private DocumentBriefs[] getSearchResponse(Document doc, MessagingError error){
		DocumentBriefs []db=null;
		if(doc!=null){
			Element root=doc.getRootElement();
			Element content=(root.getChild("action")).getChild("content");
			if(content!=null){
				int count=0;
				try{
					count=Integer.parseInt(content.getAttributeValue("count"));
					List dbriefs=content.getChildren("record_brief");
					for(int i=0;i<dbriefs.size();i++){
						if(db==null) db=new DocumentBriefs[count];
						Element recBrief=(Element)dbriefs.get(i);
						int recId=0;
						recId=Integer.parseInt(recBrief.getAttributeValue("recId"));
						String autor=recBrief.getChildText("author");
						String title=recBrief.getChildText("title");
						String publisher=recBrief.getChildText("publisher");
						String pubYear=recBrief.getChildText("publication_year");
						String language=recBrief.getChildText("language");
						String country=recBrief.getChildText("country");
						db[i]=new DocumentBriefs(recId,autor,title,publisher,pubYear,language,country);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				
				content=(root.getChild("action")).getChild("error");
				try{
					String errCode=content.getAttributeValue("code");
					String errSeverity=content.getAttributeValue("severity");
					String errDescription=content.getValue();
					String errLevel=content.getValue();
					error.setActive(true);
					error.setCode(errCode);
					error.setDescription(errDescription);
					error.setLevel(errLevel);
					error.setSeverity(errSeverity);
				}catch(Exception e){
					error.setActive(true);
					error.setCode("Unknown Error");
					error.setDescription("Unknown Error");
					error.setLevel("1");
					error.setSeverity("SEVERE");
				}
			}			
		}
		return db;
	}

}
