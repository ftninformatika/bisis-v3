/*
 * Created on 2005.4.4
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.actions;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.client.BisisSearchForm;
import com.gint.app.bisis.xmlmessaging.client.MessagingError;
import com.gint.app.bisis.xmlmessaging.communication.CommunicationThread;
import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.gint.app.bisis.xmlmessaging.util.MessageUtils;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RetrieveRecordsActionCall implements AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(com.gint.app.bisis.xmlmessaging.client.BisisSearchForm, java.util.LinkedHashMap)
	 */
	public void initiateAction(BisisSearchForm caller,LinkedHashMap requestParams) {
		try{
			LinkedHashMap hits = (LinkedHashMap)requestParams.get("hits");
			BisisSearchForm holdingForm = caller;
			String convId=(String)requestParams.get("convId");
			boolean compress=((Boolean)requestParams.get("compress")).booleanValue();
			if(hits.keySet().size()>0){
				Iterator it=(hits.keySet()).iterator();
				while(it.hasNext()){
					LibraryServerDesc keyValue=(LibraryServerDesc)it.next();
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("keyValue="+keyValue);
					LinkedHashMap recordsFromOneAddress=(LinkedHashMap)hits.get(keyValue);
					Document doc=createRecordsRequest(recordsFromOneAddress,keyValue.getUrlAddress(),convId);
					CommunicationThread ct=new CommunicationThread(this,keyValue,doc,holdingForm,convId,MessagingEnvironment.RETRIEVEREQUEST, compress);
					ct.start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Document createRecordsRequest(LinkedHashMap selectedHits,String to, String convId){
		Document retVal=null;
		Element bisisReq=MessageUtils.createBisisHeader(to,"RetrieveRecords","records",convId);
		
        Element action=new Element("action");
        action.setAttribute("name","RetrieveRecords");
        Element params=new Element("params");
        params.setAttribute("name","records");
       
        Iterator it=selectedHits.values().iterator();
        int i=0;
        while(it.hasNext()){
        	Element recId=new Element("recordId");
        	recId.setAttribute("id",(String)it.next());
        	params.addContent(recId);
        	i++;
        }
        action.addContent(params);
        bisisReq.addContent(action);
        retVal=new Document(bisisReq);
		return retVal;
	}
	
	public synchronized Vector processResponse(Document retVal,LibraryServerDesc libServ,MessagingError me){
		Object[] res;
		Vector results=null;
		boolean error=false;
		String errorCode="";
		if(retVal!=null){
			res=getRetrieveRecordsResponse(retVal,me);
			if(res==null){
				if(me.isActive()){
					error=true;
					me.setCode("\n\tUnable to retrieve records"+me);
				}else{
					error=true;
					me.setActive(true);
					me.setCode("\n\tNo results returned!");
				}
			}else{
				int activeIndex=0;
				if(res.length>0){
					results=new Vector();
					for(int i=0; i<res.length; i++){
						results.add(res[i]);									
					}
				}
			}
		}else{
			error=true;
			me.setActive(true);
			me.setCode("\n\tNo response from server!");
		}
		return results;
	}

	private Record[] getRetrieveRecordsResponse(Document doc,MessagingError error){
		XMLOutputter Element2String=new XMLOutputter();
		Record []db=null;
		if(doc!=null){
			Element root=doc.getRootElement();
			Element content=(root.getChild("action")).getChild("unimarc");
			if(content!=null){
				int count=0;
				try{
					List records=content.getChildren("record");
					if(records.size()>0){
						db=new Record[records.size()];
						for(int i=0;i<db.length;i++){
							db[i]=RecordFactory.fromLooseXML(Element2String.outputString((Element)records.get(i)));
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
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
