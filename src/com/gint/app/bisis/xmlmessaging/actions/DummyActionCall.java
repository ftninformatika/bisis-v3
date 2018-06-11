/*
 * Created on 2005.4.4
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.actions;

import java.util.LinkedHashMap;
import java.util.Vector;

import org.jdom.Document;

import com.gint.app.bisis.xmlmessaging.client.BisisSearchForm;
import com.gint.app.bisis.xmlmessaging.client.MessagingError;
import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DummyActionCall implements AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(com.gint.app.bisis.xmlmessaging.client.BisisSearchForm, java.util.LinkedHashMap)
	 */
	public void initiateAction(BisisSearchForm caller,
			LinkedHashMap requestParams) {
			caller.processResponse(null,this,null);
	}
	
	public Vector processResponse(Document response,LibraryServerDesc lib, MessagingError me){
		me.setActive(true);
		me.setCode("\nPozvana nepostojeca operacija - interna greska!");
		return null;
	}

}
