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
public interface AbstractRemoteCall {
	public void initiateAction(BisisSearchForm caller,LinkedHashMap requestParams);
	
	public Vector processResponse(Document response, LibraryServerDesc lib, MessagingError me);
}
