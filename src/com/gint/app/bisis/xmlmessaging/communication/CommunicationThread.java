/*
 * Created on 2004.10.25
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.communication;

import javax.swing.SwingUtilities;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.jdom.Document;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall;
import com.gint.app.bisis.xmlmessaging.client.BisisSearchForm;
import com.gint.app.bisis.xmlmessaging.util.SOAPUtilClient;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommunicationThread extends Thread {
	
	private LibraryServerDesc libServ;
	private Document doc;
	private Object[] res;
	private BisisSearchForm holdingForm;
	private SOAPUtilClient sender;
	private boolean compress;
	private AbstractRemoteCall owner;
	
	private boolean error=false;
	private String errorCode;
	int opType=0;
	private String convId;
	public CommunicationThread(){
		super();
		sender=new SOAPUtilClient();
		convId="";
	}
	
	
	
	/**
	 * @param address
	 * @param tableData
	 * @param errors
	 * @param infoPane
	 */
	public CommunicationThread(AbstractRemoteCall owner,LibraryServerDesc libServ, Document doc, BisisSearchForm holdingForm,String convId, int opType, boolean compress) {
		super();
		this.owner=owner;
		this.libServ=libServ;
		this.doc=doc;
		this.holdingForm=holdingForm;
		this.opType=opType;
		this.convId=convId;
		this.compress=compress;
		sender=new SOAPUtilClient();
	}
	public void run(){
		try {
			SOAPMessage retVal=null;
			try{
				SOAPMessage msg=sender.prepareMessage(doc,libServ.getUrlAddress(),convId,opType,compress);
				retVal=sender.sendReceive(msg,libServ.getUrlAddress());
				//results=processResponse(retVal);
			}catch(SOAPException e){
				e.printStackTrace();
			  if(MessagingEnvironment.DEBUG==1)
					System.out.println(e+"\n"+retVal);
				error=true;
				errorCode="\nSERVER ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] Greska u komunikaciji!";
	//			holdingForm.setErrors("\nSERVER ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] communication problems!");
			}
			if(error){
				final String errCode=new String(errorCode);
		        sleep( 1000);
		        Runnable errorUpdater = new Runnable() {
		          public void run() {
		          		holdingForm.setErrors(errCode);
		          }
		        };
		        SwingUtilities.invokeLater(errorUpdater);
			}else{
				final Document returned=sender.extractResponse(retVal);
		        Runnable resultUpdater = new Runnable() {
		        	public void run() {
		        		holdingForm.processResponse(returned,owner,libServ);
		            }
		        };
			    SwingUtilities.invokeLater(resultUpdater);
			}
			if(MessagingEnvironment.DEBUG==1)
				System.out.println("Thread: "+libServ.getLibName()+" @ "+libServ.getUrlAddress()+" Finishing");
		} catch (InterruptedException interrupt) {
	         interrupt.printStackTrace();
	    }
	}
}
