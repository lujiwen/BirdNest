package com.example.birdnest.net;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXml {
	
	public static HashMap<String, String> parseXml(InputStream inStream) throws Exception
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inStream);
		Element root = document.getDocumentElement();
		NodeList childNodes = root.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++)
		{
			Node childNode = (Node) childNodes.item(j);
			if (childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) childNode;
				hashMap.put(childElement.getNodeName(),childElement.getFirstChild().getNodeValue());
			}
		}
		return hashMap;
	}
	
	HashMap <String,String> mHashMap=new HashMap<String,String>();
    public HashMap<String,String> getHashfromRemoteXML(final String urladdr)
    {
        Thread thread=new Thread(new Runnable(){
            @Override
            public void run() {
                try { 
                URL url = new URL(urladdr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                connection.setConnectTimeout(20000);
                InputStream inStream = connection.getInputStream();
                mHashMap=ParseXml.parseXml(inStream);
                inStream.close();
                connection.disconnect();
                }
                catch (Exception e) {
                e.printStackTrace();
                }
            }   
        });
        thread.start();
        if(thread.isAlive())
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mHashMap;
    }
}
