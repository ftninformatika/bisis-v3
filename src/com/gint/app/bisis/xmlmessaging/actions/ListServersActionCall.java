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

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListServersActionCall implements AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(com.gint.app.bisis.xmlmessaging.client.BisisSearchForm, java.util.LinkedHashMap)
	 */
	public void initiateAction(BisisSearchForm caller,LinkedHashMap requestParams) {
		try{
			LinkedHashMap libs = (LinkedHashMap)requestParams.get("libs");
			BisisSearchForm holdingForm = caller;
			String convId=(String)requestParams.get("convId");
			boolean compress=((Boolean)requestParams.get("compress")).booleanValue();
			if(libs.keySet().size()>0){
				Iterator it=(libs.keySet()).iterator();
				while(it.hasNext()){
					String keyValue=(String)it.next();
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("keyValue="+keyValue);
					LibraryServerDesc thisLib=(LibraryServerDesc)libs.get(keyValue);
					Document doc=createListServersRequest(thisLib.getUrlAddress(),convId);
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("izbacujem comm threadove");
					CommunicationThread ct=new CommunicationThread(this,thisLib,doc,holdingForm,convId,MessagingEnvironment.LISTSERVERS, compress);
					ct.start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public synchronized Vector processResponse(Document retVal, LibraryServerDesc libServ, MessagingError me){
		if(MessagingEnvironment.DEBUG==1)
			System.out.println("LIstServers processing response");
		Object[] res;
		Vector results=null;
		boolean error=false;
		String errorCode="";
		if(retVal!=null){
			res=getListServersResponse(retVal,me);
			if(res==null){
				if(me.isActive()){
					error=true;
					me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] "+me);
				}else{
					error=true;
					me.setActive(true);
					me.setCode("\tSpisak dodatnih servera trenutno nedostupan!");
				}
			}else{
				int activeIndex=0;
				if(res.length>0){
					results=new Vector();
					for(int i=0; i<res.length; i++){
						results.add((LibraryServerDesc)res[i]);									
					}
				}
			}
		}else{
			error=true;
			me.setActive(true);
			me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] \n\tServer nije korektno odgovorio na ListServers zahtev!");
		}
		return results;
	}
	
	/* This private function is currently unused. 
	 * It enables server list queries through the XML messaging protocol.
	 * This query has been replaced by the plain http get to servers.xml
	 */
	private Document createListServersRequest(String to, String convId){
		Document retVal=null;
		Element bisisReq=MessageUtils.createBisisHeader(to,"ListServers","serverquery",convId);
		
        Element action=new Element("action");
        action.setAttribute("name","ListServers");
        
        bisisReq.addContent(action);
        retVal=new Document(bisisReq);
		return retVal;
	}
	
	
	private LibraryServerDesc[] getListServersResponse(Document doc,MessagingError error){
		XMLOutputter Element2String=new XMLOutputter();
		LibraryServerDesc []db=null;
		if(doc!=null){
			Element root=doc.getRootElement();
			Element content=(root.getChild("action")).getChild("servers");
			if(content!=null){
				int count=0;
				try{
					List servers=content.getChildren("server");
					if(servers.size()>0){
						db=new LibraryServerDesc[servers.size()];
						for(int i=0;i<db.length;i++){
							Element oneServer=(Element)servers.get(i);
							int id;
							String serverName="";
							String serverUrl="";
							String serverInstitution="";
							try{
								id=Integer.parseInt(oneServer.getAttributeValue("id"));
							}catch(Exception e){
								id=-1;
							}
							Element name=oneServer.getChild("name");
							if(name!=null)
								serverName=name.getText();
							Element url=oneServer.getChild("url");
							if(url!=null)
								serverUrl=url.getText();
							Element institution=oneServer.getChild("institution");
							if(institution!=null)
								serverInstitution=institution.getText();
							db[i]=new LibraryServerDesc(id,serverName,serverUrl,serverInstitution);
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
