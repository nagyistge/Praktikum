package firstApproach;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;




@SuppressWarnings("unused")
public class NodeCreator {

	//Methode funktioniert noch nicht! Sollte �berpr�fen ob im FHIR dokument der Pfad bereits existiert oder nicht? 
	/*public static Node createIfNotExists(Document document, String path, String value) {
		String[] pathComponents = path.split("\\/");
		

		System.out.println("Components: "+pathComponents);
		String currentPath = "";
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		//Node previousNode = document;
		
		for (String pathComponent : pathComponents) {

			if (pathComponent.length()==0) {
				continue;
			}
			currentPath+="/"+pathComponent;
			
			
			System.out.println("CurrentPath: "+currentPath);
			try {
				Node val = (Node) xpath.compile(currentPath).evaluate(document, XPathConstants.NODE);

				System.out.println("CurrentValue: "+val);
				
				if (val == null) {
					
					System.out.println("Does not exist->create");
					//createElement(pathComponent);
				}
				
				
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	} */
	
	// zb name !!! Diese Methode nicht mehr benutzen?? nur noch createSubElement
	public static void createElement(String addnode){

	try {
        String filepath = "fhirPatient.xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);


        //Node rootNode = doc.getElementsByTagName("Patient").item(0);

        if(doc.getDocumentElement()==null){
        	// root elements
        	Element rootElement = doc.createElement(addnode);
        	doc.appendChild(rootElement);				
        	// set attribute to root element
        	Attr attr = doc.createAttribute("xmlns");
        	attr.setValue("http://hl7.org/fhir");
        	rootElement.setAttributeNode(attr);
        	//rootElement.setAttribute("xmlns", "http://hl7.org/fhir");
        }
        	
        Node rootNode = doc.getDocumentElement();
        
        // append a new node 
        Element tobeadded = doc.createElement(addnode);
        //tobeadded.appendChild(doc.createTextNode(""));
        rootNode.appendChild(tobeadded);
        
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
        transformer.transform(source, result);

        System.out.println("Done");

       } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
       } catch (TransformerException tfe) {
        tfe.printStackTrace();
       } catch (IOException ioe) {
        ioe.printStackTrace();
       } catch (SAXException sae) {
        sae.printStackTrace();
       }
    }
	
	public static void createElementWithAttribute(String addnode, String attribute){

		try {
	        String filepath = "fhirPatient.xml";
	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse(filepath);


	        //Node rootNode = doc.getElementsByTagName("Patient").item(0);

	        if(doc.getDocumentElement()==null){
	        	// root elements
	        	Element rootElement = doc.createElement(addnode);
	        	doc.appendChild(rootElement);				
	        	// set attribute to root element
	        	Attr attr = doc.createAttribute("xmlns");
	        	attr.setValue("http://hl7.org/fhir");
	        	rootElement.setAttributeNode(attr);
	        	//rootElement.setAttribute("xmlns", "http://hl7.org/fhir");
	        }
	        	
	        Node rootNode = doc.getDocumentElement();
	        
	        // append a new node 
	        Element tobeadded = doc.createElement(addnode);
	        //tobeadded.appendChild(doc.createTextNode(""));
	        rootNode.appendChild(tobeadded);
	        tobeadded.setAttribute("value", attribute);
	        
	        // write the content into xml file
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();

	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File(filepath));
	        
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
	        transformer.transform(source, result);

	        System.out.println("Done");

	       } catch (ParserConfigurationException pce) {
	        pce.printStackTrace();
	       } catch (TransformerException tfe) {
	        tfe.printStackTrace();
	       } catch (IOException ioe) {
	        ioe.printStackTrace();
	       } catch (SAXException sae) {
	        sae.printStackTrace();
	       }
	    }	
	
	// zb name/given, name/family
	public static void createSubElement(String addsubnode, String existingnode){
	try {
       
		String filepath = "fhirPatient.xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);

        // Get the root element
        Node rootNode = doc.getFirstChild();

        // Get the excludeList element by tag name directly
        Node node = doc.getElementsByTagName(existingnode).item(0);
        
		
        //subnode 
        Element subnode = doc.createElement(addsubnode);
        subnode.appendChild(doc.createTextNode(""));
		rootNode.appendChild(subnode);
		node.appendChild(subnode);
        
		
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        DOMSource source = new DOMSource(doc);
       // StreamResult result = new StreamResult(new File(filepath));
        StreamResult result = new StreamResult(filepath);
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
        transformer.transform(source, result);


        System.out.println("Subnode added");

       } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
       } catch (TransformerException tfe) {
        tfe.printStackTrace();
       } catch (IOException ioe) {
        ioe.printStackTrace();
       } catch (SAXException sae) {
        sae.printStackTrace();
       }
    }

	public static void createSubElementforContact(String existingnode){
		try {
	       
			String filepath = "fhirPatient.xml";
	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse(filepath);

	        // Get the root element
	        Node rootNode = doc.getFirstChild();

	        // Get the excludeList element by tag name directly
	        Node node = doc.getElementsByTagName(existingnode).item(0);
	        		
			Element contactName = doc.createElement("name");
            Element contactFamily = doc.createElement("family");
            Element contactGiven = doc.createElement("given");
            Element contactGiven2 = doc.createElement("given");
            Element contactTelecom = doc.createElement("telecom");
            Element contactUse = doc.createElement("use");
            Element contactSystem = doc.createElement("system");
            Element contactValue = doc.createElement("value");
            Element contactAdress = doc.createElement("address");
            Element contactLine = doc.createElement("line");
            Element contactCity = doc.createElement("city");
            Element contactState = doc.createElement("state");
            Element contactCode = doc.createElement("postalCode");
            Element contactCountry = doc.createElement("country");
            
            node.appendChild(contactName);
            contactName.appendChild(contactFamily);
            contactName.appendChild(contactGiven);
            contactName.appendChild(contactGiven2);
            
            node.appendChild(contactTelecom);
            contactTelecom.appendChild(contactUse);
            contactTelecom.appendChild(contactSystem);
            contactTelecom.appendChild(contactValue);
            
            node.appendChild(contactAdress);
            contactAdress.appendChild(contactLine);
            contactAdress.appendChild(contactCity);
            contactAdress.appendChild(contactState);
            contactAdress.appendChild(contactCode);
            contactAdress.appendChild(contactCountry);
			
	        // write the content into xml file
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        
	        DOMSource source = new DOMSource(doc);
	       // StreamResult result = new StreamResult(new File(filepath));
	        StreamResult result = new StreamResult(filepath);
	        
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
	        transformer.transform(source, result);


	        System.out.println("Subnode added");

	       } catch (ParserConfigurationException pce) {
	        pce.printStackTrace();
	       } catch (TransformerException tfe) {
	        tfe.printStackTrace();
	       } catch (IOException ioe) {
	        ioe.printStackTrace();
	       } catch (SAXException sae) {
	        sae.printStackTrace();
	       }
	    }
	
}
