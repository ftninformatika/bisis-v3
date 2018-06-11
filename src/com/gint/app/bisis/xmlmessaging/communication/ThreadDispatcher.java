/*
 * Created on 2004.11.7
 *
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.communication;

import java.util.LinkedHashMap;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall;
import com.gint.app.bisis.xmlmessaging.actions.DummyActionCall;
import com.gint.app.bisis.xmlmessaging.client.BisisSearchForm;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ThreadDispatcher {
	
	public void invokeThreads(BisisSearchForm bsForm, String callName, LinkedHashMap reqParams){
		try{
			RemoteActionDescription rad=(RemoteActionDescription)MessagingEnvironment.getActions().get(callName); 
			String className=rad.getPerformer();
			AbstractRemoteCall rc=(AbstractRemoteCall)((Class.forName(className)).newInstance());
			rc.initiateAction(bsForm,reqParams);
		}catch(Exception e){
			AbstractRemoteCall rc=new DummyActionCall();
			rc.initiateAction(bsForm,reqParams);
		}
	}
}
