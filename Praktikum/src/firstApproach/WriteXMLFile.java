package firstApproach;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@SuppressWarnings("unused")
public class WriteXMLFile {



	
	public static void main(String argv[]) throws SAXException, IOException, XPathExpressionException {
		 
		  try {
	 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			

	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Patient");
			doc.appendChild(rootElement);
			
			// set attribute to root element
			Attr attr = doc.createAttribute("xmlns");
			attr.setValue("http://hl7.org/fhir");
			rootElement.setAttributeNode(attr);
			//rootElement.setAttribute("xmlns", "http://hl7.org/fhir");
			
			Element subnode = doc.createElement("id");
			subnode.appendChild(doc.createTextNode(""));
			rootElement.appendChild(subnode);
			subnode.setAttribute("value", "spark17");
	        
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("fhirPatient.xml"));
	 
			// Output to console for testing
			//StreamResult result = new StreamResult(System.out);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			transformer.transform(source, result);
			
			System.out.println("File saved!");
			
			NodeCreator.createElement("identifier");
			NodeCreator.createElement("identifier");
			NodeCreator.createElement("name");
			NodeCreator.createSubElement("prefix", "name");
			NodeCreator.createSubElement("prefix", "name");
			NodeCreator.createSubElement("family", "name");
			NodeCreator.createSubElement("given", "name");
			NodeCreator.createSubElement("given", "name");
			NodeCreator.createSubElement("suffix",  "name");
			NodeCreator.createSubElement("suffix",  "name");
			NodeCreator.createElement("telecom");
			NodeCreator.createSubElement("system",  "telecom");
			NodeCreator.createSubElement("value",  "telecom");
			NodeCreator.createSubElement("use",  "telecom");
			NodeCreator.createElement("gender");
			NodeCreator.createElement("birthDate");
			NodeCreator.createElementWithAttribute("deceasedBoolean", "false");
			NodeCreator.createElement("address");
			NodeCreator.createSubElement("line", "address");
			NodeCreator.createSubElement("city", "address");
			NodeCreator.createSubElement("state", "address");
			NodeCreator.createSubElement("postalCode", "address");
			NodeCreator.createSubElement("country", "address");
			NodeCreator.createElement("maritalStatus");
			NodeCreator.createSubElement("coding", "maritalStatus");
			NodeCreator.createSubElement("system", "coding");
			NodeCreator.createSubElement("code", "coding");
			NodeCreator.createSubElement("display", "coding");
			NodeCreator.createSubElement("text", "maritalStatus");
			NodeCreator.createElementWithAttribute("multipleBirthBoolean", "false");
			NodeCreator.createElement("photo");
			NodeCreator.createElement("contact");
			NodeCreator.createSubElement("relationship", "contact");
			NodeCreator.createSubElementforContact("contact");
			/*NodeCreator.createSubElement("name", "contact");
			NodeCreator.createSubElement("family", "name");
			NodeCreator.createSubElement("given", "name");
			NodeCreator.createSubElement("given", "name");
			//final String ROOT = "Patient";
	        //org.dom4j.Document document = DocumentHelper.createDocument(DocumentHelper.createElement(ROOT));
	        //NodeCreator2.addElementToParent("Patient/test/tes", "test");
			NodeCreator.createSubElement("telecom", "contact");
			NodeCreator.createSubElement("contactAddress", "contact");
			NodeCreator.createSubElement("line", "contactAddress");
			NodeCreator.createSubElement("city", "contactAddress");
			NodeCreator.createSubElement("state", "contactAddress");
			NodeCreator.createSubElement("postalCode", "contactAddress");
			NodeCreator.createSubElement("country", "contactAddress"); */
			NodeCreator.createSubElement("gender", "contact");
			NodeCreator.createSubElement("organization", "contact");
			NodeCreator.createSubElement("period", "contact");
			NodeCreator.createElement("animal");
			NodeCreator.createSubElement("species", "animal");
			NodeCreator.createSubElement("breed", "animal");
			NodeCreator.createSubElement("genderStatus", "animal");
			NodeCreator.createElement("communication");
			NodeCreator.createSubElement("language", "communication");
			NodeCreator.createSubElement("coding", "language");
			NodeCreator.createSubElement("system", "coding");
			NodeCreator.createSubElement("code", "coding");
			NodeCreator.createSubElement("display", "coding");
			NodeCreator.createSubElement("text", "language");
			NodeCreator.createSubElement("preferred", "communication");
			NodeCreator.createElement("managingOrganization");
			NodeCreator.createSubElement("reference", "managingOrganization");
			NodeCreator.createSubElement("display", "managingOrganization");
			NodeCreator.createElement("link");
			NodeCreator.createSubElement("other", "link");
			NodeCreator.createSubElement("type", "link");
			NodeCreator.createElementWithAttribute("active", "true");
			
			
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		}
}
