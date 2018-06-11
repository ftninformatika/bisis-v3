/*
 * Created on Sep 24, 2004
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.util;

/**
 * @author Miki
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.xml.messaging.URLEndpoint;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.communication.RemoteActionDescription;

public class SOAPUtilClient {
     public static final String NS_PREFIX_EB = "eb";
     public static final String NS_URI_EB =
        "http://www.ebxml.org/namespaces/messageHeader";
     public static final String NS_PREFIX_XLINK = "xlink";
     public static final String NS_URI_XLINK =
        "http://www.w3.org/1999/xlink";

     public static boolean available=true;
 	 
 	 public SOAPMessage prepareMessage(Document payloadDoc,String to, String convId, int opType, boolean compress){
 	 	SOAPMessage result=null;
 	 	switch(opType){
	 		case MessagingEnvironment.SEARCHREQUEST: result=prepareSOAPMessage(payloadDoc, "PerformSearch", MessagingEnvironment.getMyLibServer().getUrlAddress(), to, convId, compress);
	 			break;
	 		case MessagingEnvironment.RETRIEVEREQUEST:result=prepareSOAPMessage(payloadDoc,"RetrieveRecords",MessagingEnvironment.getMyLibServer().getUrlAddress(), to, convId, compress);
	 			break;
	 		case MessagingEnvironment.LISTSERVERS:result=prepareSOAPMessage(payloadDoc,"ListServers",MessagingEnvironment.getMyLibServer().getUrlAddress(), to, convId, compress);
 				break;
 	 	}
 	 	return result;
 	 }
 	 
 	 public SOAPMessage prepareSOAPMessage(Document payloadDoc, String action, String from, String to, String convId, boolean compressData){
     	SOAPMessage msg=null;
     	try{
     	 RemoteActionDescription ra=(RemoteActionDescription)(MessagingEnvironment.getActions()).get(action);
     	 MessageFactory mf = MessageFactory.newInstance();
	     msg = mf.createMessage();
	     
	     SOAPPart sp = msg.getSOAPPart();

         //create the header container
         SOAPEnvelope envelope = sp.getEnvelope();
         if(MessagingEnvironment.DEBUG==1)
         	System.out.println("setting eb:"+ra.getServiceName()+ra.getActionName()+" action->"+action);
         ebSetup(envelope, from, to, MessagingEnvironment.CPAID, convId, ra.getServiceName(), ra.getActionName());
         SOAPBody body = envelope.getBody();
        
         SOAPElement manifest = body.addBodyElement(
        		envelope.createName("Manifest",
                SOAPUtilClient.NS_PREFIX_EB, SOAPUtilClient.NS_URI_EB));

         SOAPElement reference =
          manifest.addChildElement(
          		envelope.createName("Reference",
                SOAPUtilClient.NS_PREFIX_EB, SOAPUtilClient.NS_URI_EB));
         reference.addAttribute(
         		envelope.createName("href",
            SOAPUtilClient.NS_PREFIX_XLINK, SOAPUtilClient.
            NS_URI_XLINK), "requestpart1");
         reference.addAttribute(
        		envelope.createName("type",
            SOAPUtilClient.NS_PREFIX_XLINK, SOAPUtilClient.
            NS_URI_XLINK), "simple");

         SOAPElement description =
          reference.addChildElement(
          		envelope.createName("Description",
                SOAPUtilClient.NS_PREFIX_EB, SOAPUtilClient.NS_URI_EB));
         description.addTextNode("Bisis Request");
         
         StringWriter sw = new StringWriter();
         try {
              XMLOutputter outputter = new XMLOutputter();
              outputter.output(payloadDoc, sw);
           } catch (java.io.IOException e) {
            e.printStackTrace();
         }
         
         AttachmentPart transferAttach;
         if(!compressData){
	         transferAttach = msg.createAttachmentPart( sw.toString(), "text/plain; charset=UTF-8");
	         transferAttach.setContentType("text/plain; charset=UTF-8");
	         transferAttach.setContentId("requestpart1");
	         msg.addAttachmentPart(transferAttach);
         }else{
         	//compressData
         	if(MessagingEnvironment.DEBUG==1)
         		System.out.println("data should be compressed");
         	transferAttach=msg.createAttachmentPart();
         	//msg.createAttachmentPart(public DataContentHandler createDataContentHandler(java.lang.String mimeType))
         	byte[] zippedContent=GzipUtility.zipText(sw.toString());
         	if(MessagingEnvironment.DEBUG==1)
         		System.out.println("Unzipped:\n+"+GzipUtility.unzipText(zippedContent));
         	DataHandler dh=new DataHandler(new ByteArrayDataSource(zippedContent, "application/x-gzip"));
         	transferAttach.setDataHandler(dh);
         	transferAttach.setContentId("requestpart1");
	        msg.addAttachmentPart(transferAttach);
         }
	     
     	}catch(Exception e){
     		e.printStackTrace();
     	}
         return msg;
     }
     
     
     public SOAPMessage sendReceive(SOAPMessage msg, String URLAddress)
     	throws SOAPException{
	     	URLEndpoint endPoint = new URLEndpoint(URLAddress);
	        SOAPMessage reply=null; 
	        SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
	        SOAPConnection con = factory.createConnection();
	        if(con==null){
	        	if(MessagingEnvironment.DEBUG==1)
	        		System.out.println("SOAPConnection failure!");
	        }else{
	        	reply = con.call(msg, endPoint);
	           	con.close();
	           	if(MessagingEnvironment.DEBUG==1)
	           		System.out.println("\nSending to: "+URLAddress+" success");
	        }
	        return reply;
     }
     
     
     public Document extractResponse(SOAPMessage reply){
     	Document doc=null;
     	if (reply != null) {
     		try{
	            Iterator iter = reply.getAttachments();
	            AttachmentPart af = (AttachmentPart) iter.next();
	            String attcontent=null;
	            if(af.getContentType().equalsIgnoreCase("application/x-gzip")){
                	
                	java.io.InputStream zippedStream = (java.io.InputStream)af.getContent();
                	attcontent=GzipUtility.unzipFromStream(zippedStream);
                	attcontent.trim();
                	zippedStream.close();
                	if(MessagingEnvironment.DEBUG==1)
                		System.out.println("Unziped xml:\n"+attcontent);
                }else{
                	BufferedReader br=new BufferedReader(new InputStreamReader((InputStream)af.getContent(),"UTF-8"));
                	String temp;
                	attcontent=new String();
                	while((temp=br.readLine())!=null)
                		attcontent+=temp;
                	br.close();
                	if(MessagingEnvironment.DEBUG==1)
                		System.out.println("Content\n"+attcontent);
                }
	            SAXBuilder builder;
	            builder=new SAXBuilder("org.apache.xerces.parsers.SAXParser",true);
	            builder.setFeature("http://xml.org/sax/features/validation",  true);
	            builder.setFeature("http://apache.org/xml/features/validation/schema",  true);
	            builder.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
	            builder.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",MessagingEnvironment.getRespSchemaURL());
	            //builder=new SAXBuilder();
	            
	            doc = builder.build(new StringReader(attcontent));
     		}catch(Exception e){
     			e.printStackTrace();
     		}
         }
     	return doc;
     }
     private void ebSetup(SOAPEnvelope envelope,
               String frparty,
               String toparty,
               String cpaId,
               String conversId,
               String service,
               String action
               ) {
          try {
              SOAPHeader header = envelope.getHeader();

        SOAPHeaderElement ebHeader = header.addHeaderElement(
        envelope.createName("MessageHeader", NS_PREFIX_EB, NS_URI_EB));
        ebHeader.setMustUnderstand(true);
        ebHeader.addAttribute(envelope.createName("version"), "1.0");

         SOAPElement ebFrom = ebHeader.addChildElement(
          envelope.createName("From", NS_PREFIX_EB, NS_URI_EB));
        SOAPElement ebPartyFrom = ebFrom.addChildElement(
          envelope.createName("PartyId", NS_PREFIX_EB, NS_URI_EB));
        ebPartyFrom.addTextNode(frparty);

        SOAPElement ebTo = ebHeader.addChildElement(
          envelope.createName("To", NS_PREFIX_EB, NS_URI_EB));
        SOAPElement ebPartyTo = ebTo.addChildElement(
          envelope.createName("PartyId", NS_PREFIX_EB, NS_URI_EB));
        ebPartyTo.addTextNode(toparty);

        SOAPElement ebCPAId = ebHeader.addChildElement(
          envelope.createName("CPAId", NS_PREFIX_EB, NS_URI_EB));
        ebCPAId.addTextNode(cpaId);

        SOAPElement ebConv = ebHeader.addChildElement(
          envelope.createName("ConversationId"
             , NS_PREFIX_EB, NS_URI_EB));
        ebConv.addTextNode(conversId);

        SOAPElement ebService = ebHeader.addChildElement(
          envelope.createName("Service", NS_PREFIX_EB, NS_URI_EB));
        ebService.addTextNode(service);

        SOAPElement ebAction = ebHeader.addChildElement(
          envelope.createName("Action", NS_PREFIX_EB, NS_URI_EB));
        ebAction.addTextNode(action);

        SOAPElement ebMesData = ebHeader.addChildElement(
          envelope.createName("MessageData", NS_PREFIX_EB, NS_URI_EB));

        SOAPElement ebMesId = ebMesData.addChildElement(
          envelope.createName("MessageId", NS_PREFIX_EB, NS_URI_EB));
        ebMesId.addTextNode(System.currentTimeMillis() + "");

        SOAPElement ebMesTime = ebMesData.addChildElement(
          envelope.createName("Timestamp", NS_PREFIX_EB, NS_URI_EB));
        ebMesTime.addTextNode(new Date().toString());

      } catch(Exception e) {
        e.printStackTrace();
      }
   }
   
   
   public static SOAPElement getChild
      (SOAPEnvelope envelope, SOAPElement elem, String child)
      throws SOAPException {
      Iterator iter =
        elem.getChildElements(envelope.createName
           (child, NS_PREFIX_EB, NS_URI_EB));
      if (iter.hasNext()) {
        SOAPElement e = (SOAPElement) iter.next();
        return e;
      }
      else
        return null;
   }
}