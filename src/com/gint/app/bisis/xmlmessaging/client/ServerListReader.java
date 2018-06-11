/*
 * Created on 2005.4.6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis.xmlmessaging.client;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.adapters.XML4JDOMAdapter;
import org.jdom.input.DOMBuilder;

import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerListReader {
	
	/* Function that will create list of available servers 
	 * by requesting servers.xml
	 */
	public static Vector prepareServerList(){
		if(MessagingEnvironment.DEBUG==1)
			System.out.println("PrepareServerList called");
		Vector result=null;
		LinkedHashMap libServers=MessagingEnvironment.getLibServers();
		Document received=readServersListData();
		if(received!=null){
			Element root=received.getRootElement();
			if(root.getName().equals("servers")){
				List servers=root.getChildren("server");
				if(servers.size()>0){
					result=new Vector();
					for(int i=0;i<servers.size();i++){
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
							serverName=name.getText().trim();
						Element url=oneServer.getChild("url");
						if(url!=null)
							serverUrl=url.getText().trim();
						Element institution=oneServer.getChild("institution");
						if(institution!=null)
							serverInstitution=institution.getText().trim();
						result.add(new LibraryServerDesc(id,serverName,serverUrl,serverInstitution));
					}
				}
			}
		}
		return result;
	}
	
	private static Document readServersListData(){
		Document resp=null;
		XML4JDOMAdapter getter = new XML4JDOMAdapter();
		DOMBuilder builder=new DOMBuilder();
		try{
			URL testURL=new URL(MessagingEnvironment.getMainURL());
			URLConnection conn=testURL.openConnection();
			InputStream urlConnStream=conn.getInputStream();
			resp=builder.build(getter.getDocument(urlConnStream,false));
			urlConnStream.close();
		}catch(Exception e){
      e.printStackTrace();
			System.out.println("Pristup mrezi za razmenu trenutno onemogucen.");
		}
		return resp;
	}
}
