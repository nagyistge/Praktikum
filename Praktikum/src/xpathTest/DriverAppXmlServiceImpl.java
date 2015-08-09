package xpathTest;

/**
 * 
 */


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jaxen.JaxenException;
import org.xml.sax.SAXException;

/**
 * @author bchild
 *
 */
public class DriverAppXmlServiceImpl  {
  
	
	private static final String ROOT = "Patient";
	private static final String BASE_XPATH = "/" + ROOT;

	/* (non-Javadoc)
	 * @see com.bmchild.service.driverapp.xml.DriverAppXMLService#buildXml()
	 */
	public void buildXml() throws Exception{
		
		final Document document = DocumentHelper.createDocument(DocumentHelper.createElement(ROOT));


		DocumentBuilderFactory documentumentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentumentBuilder = documentumentBuilderFactory.newDocumentBuilder();
		
		final org.w3c.dom.Document cdaDocument = documentumentBuilder.parse("CDA.xml");
		
		
		MappingReader mappingsReader = new MappingReader();
		
		mappingsReader.readMappings();
		
		
		mappingsReader.enumerateWithEnumerator(new MappingEnumerator() {
			public void enumerate(String xpathCDA, String xPathFhir ) {
				
				String cdaValue = null ;// doc.valueOf( xpathCDA);
				
				  try {

					XPathFactory xpathFactory = XPathFactory.newInstance();
					XPath xpath = xpathFactory.newXPath();
					  
					XPathExpression cdaexpr = xpath.compile(xpathCDA);
					
					cdaValue = cdaexpr.evaluate(cdaDocument);
					
					System.out.println("VALUE: " + cdaValue+ "("+xpathCDA+")");
					
					
				} catch ( XPathExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				addElementToParent(document, xPathFhir, cdaValue);
				
				//System.out.println("CDA: "+xpathCDA+", FHIR: "+xPathFhir);
			}
		});
		
		
		
		printDoc(document);
		
	}
	
	/**
	 * @param string
	 * @return
	 */
	private String prependBase(String string) {
		return BASE_XPATH + string;
	}

	private void printDoc(Document document) {
		
		System.out.println("PRINT DOC");
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("ISO-8859-1");
		//StringWriter writer = new StringWriter();
		
	    XMLWriter writer;
		try {
			writer = new XMLWriter(
			        new FileWriter( "output.xml" )
			    );
			
	        writer.write( document );
	        writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

	}
	
	/**
	 * Recursive method to create an element and, if necessary, its parents and siblings
	 * @param document
	 * @param xpath to single element
	 * @param value if null an empty element will be created
	 * @return the created Node
	 */
	private Node addElementToParent(Document document, String xpath, String value) {

		
		String elementName = XPathUtils.getChildElementName(xpath);
		String parentXPath = XPathUtils.getParentXPath(xpath);
		Node parentNode = document.selectSingleNode(parentXPath);
		if(parentNode == null) {
			parentNode = addElementToParent(document, parentXPath, null);
		}
		
		// create younger siblings if needed
		Integer childIndex = XPathUtils.getChildElementIndex(xpath);
		if(childIndex > 1) {
			List<?> nodelist = document.selectNodes(XPathUtils.createPositionXpath(xpath, childIndex));
			// how many to create = (index wanted - existing - 1 to account for the new element we will create)
			int nodesToCreate = childIndex - nodelist.size() - 1;
			for(int i = 0; i < nodesToCreate; i++) {
				((Element)parentNode).addElement(elementName);
			}
		}
		
		// create requested element
		Element created = ((Element)parentNode).addElement(elementName);
		if(null != value) {
			created.addText(value);
		}
		return created;
	}

}
