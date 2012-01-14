package cl.ht.facturacion.testing;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		MainTest main = new MainTest();
//		main.readExternalXML();
		readXML();
	}
	
	public void readExternalXML() {
		InputStream is = getClass().getResourceAsStream("plugins/pfplugin-helloworld/plugin-properties.xml");
		InputStreamReader isr = new InputStreamReader(is);
	}
	
	public static void readXML() {
		try {
			  File file = new File("plugins/pfplugin-helloworld/plugin-properties.xml");
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			  NodeList nodeLst = doc.getElementsByTagName("plugin");
			  System.out.println("Information of current plugin");

			  for (int s = 0; s < nodeLst.getLength(); s++) {
				  Node fstNode = nodeLst.item(s);
			    
				    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
				    	Element fstElmnt = (Element) fstNode;
				    	NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("about");
				    	Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				    	System.out.println("Sobre este plugin: "+fstNmElmnt.getAttribute("detail"));
				    	
				    	NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("action");
				    	Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
				    	System.out.println("Action class del plugin: "+lstNmElmnt.getAttribute("class"));
				    	
				    	NodeList nodeOwner = fstElmnt.getElementsByTagName("owner");
				    	Element elementOwner = (Element) nodeOwner.item(0);
				    	System.out.println("Action class del plugin: "+elementOwner.getAttribute("name"));
				    }
			  }
			  
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	}
}