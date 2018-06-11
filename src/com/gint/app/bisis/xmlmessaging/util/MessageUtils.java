/*
 * Created on 2004.10.3
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.util;

import java.util.Date;

import org.jdom.Element;
import org.jdom.Namespace;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;

/**
 * @author mikiz
 *
 * class is intended to be used as one stop palce for preparing the Bisis XML exchange documents
 */
public class MessageUtils {
	static final char BISIS_FIELD_SEPARATOR    ='\036';
	static final char BISIS_SUBFIELD_SEPARATOR ='\037';
	static final char BISIS_SUBSUBFIELD_SEPARATOR ='\\';
	
	public static String reqSchemaURL="http://bisis.ns.ac.yu:8080/bisisXMLM/bisis_req.xsd";
	public static String respSchemaURL="http://bisis.ns.ac.yu:8080/bisisXMLM/bisis_resp.xsd";
	public static String thisLibNode="bisisClient@localhost";
	
	public static Element createBisisHeader(String toServer, String requestedAction, String requestedParams,String convId){
		Element bisisReq=new Element("bisis_request");
        Namespace nmsp=Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
        bisisReq.setAttribute("noNamespaceSchemaLocation",MessagingEnvironment.getReqSchemaURL(),nmsp);
        Element header=new Element("header");
        Element from=new Element("from");
        if(MessagingEnvironment.DEBUG==1)
        	System.out.println("Messaging: "+MessagingEnvironment.getMyLibServer().getUrlAddress());
        from.setText(MessagingEnvironment.getMyLibServer().getUrlAddress());
        header.addContent(from);
        Element to=new Element("to");
        to.setText(toServer);
        header.addContent(to);
        Element datestamp=new Element("datestamp");
        datestamp.setText(new Date().toGMTString());
        header.addContent(datestamp);
        Element convid=new Element("conversation_id");
        convid.setText(convId);
        header.addContent(convid);
        bisisReq.addContent(header);
        
        Element action=new Element("action");
        action.setAttribute("name",requestedAction);
        Element params=new Element("params");
        params.setAttribute("name",requestedParams);
		return bisisReq;
	}
}
