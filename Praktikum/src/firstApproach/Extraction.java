package firstApproach;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Extraction {
 // lest information aus CDA Dokument & f�gt es in Fhir Dokument ein
	public static void main(String argv[]) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {

		final String CDA_XPATH = "CDA_PATH";
		final String FHIR_XPATH = "FHIR_PATH";
		
		DocumentBuilderFactory documentumentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentumentBuilder = documentumentBuilderFactory.newDocumentBuilder();
		
		//Document doc = documentumentBuilder.newDocument();
 
		Document cdaDocument = documentumentBuilder.parse("Rezept_mit_Verordnungen.xml");
		Document fhirDocument = documentumentBuilder.parse("fhirPatient.xml");

		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		List<Map<String,String>> mappings = MappingReader.readMappings(); // new ArrayList<Map<String,String>>();
		
		//Map CDA->FHIR
		for (Map<String, String> mapping:mappings) {
			//Get XPath for CDA Document
			String cdaPath = mapping.get(CDA_XPATH);
			String fhirPath = mapping.get(FHIR_XPATH);
			
			XPathExpression cdaexpr = xpath.compile(cdaPath);	
			XPathExpression fhirexpr = xpath.compile(fhirPath);
			
			//Read value from CDA Document
			String value = cdaexpr.evaluate(cdaDocument);
			//System.out.println("VAL :"+value);
		
			
			NodeList fhirNode = (NodeList) fhirexpr.evaluate(fhirDocument, XPathConstants.NODESET);
			//NodeList fhirNode = (NodeList) fhirexpr.evaluate(doc, XPathConstants.NODESET);
			//fhirNode.item(0).setTextContent(value);
			
			if(value.equals("Male")){
				value = "male";
			}
			
			((Element) fhirNode.item(0)).setAttribute("value", value);
			
		  }
		
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				try {
					transformer = transformerFactory.newTransformer();
					
					DOMSource source = new DOMSource(fhirDocument);
					//DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("fhirPatient.xml"));
			 
					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
					
					transformer.transform(source, result);
					
					System.out.println("File saved!");
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
		}
	}

