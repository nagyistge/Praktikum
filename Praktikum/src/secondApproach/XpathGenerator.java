package secondApproach;
/**
 * Created by Anar on 7/21/15.
 */
    import java.util.HashMap;
    import java.util.Map;

    import org.xml.sax.Attributes;
    import org.xml.sax.SAXException;
    import org.xml.sax.XMLReader;
    import org.xml.sax.helpers.DefaultHandler;

    public class XpathGenerator extends DefaultHandler {

        private String xPath = "/";
        private XMLReader xmlReader;
        private XpathGenerator parent;
        private StringBuilder characters = new StringBuilder();
        private Map<String, Integer> elementNameCount = new HashMap<String, Integer>();

        public XpathGenerator(XMLReader xmlReader) {
            this.xmlReader = xmlReader;
        }

        private XpathGenerator(String xPath, XMLReader xmlReader, XpathGenerator parent) {
            this(xmlReader);
            this.xPath = xPath;
            this.parent = parent;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            Integer count = elementNameCount.get(qName);
            if(null == count) {
                count = 1;
            } else {
                count++;
            }
            elementNameCount.put(qName, count);
            String childXPath = xPath + "/" + qName + "[" + count + "]";

            int attsLength = atts.getLength();
            for(int x=0; x<attsLength; x++) {
                System.out.println(childXPath + "[@" + atts.getQName(x) + "='" + atts.getValue(x) + ']');
            }

            XpathGenerator child = new XpathGenerator(childXPath, xmlReader, this);
            xmlReader.setContentHandler(child);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String value = characters.toString().trim();
            if(value.length() > 0) {
                System.out.println(xPath + "='" + characters.toString() + "'");
            }
            xmlReader.setContentHandler(parent);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            characters.append(ch, start, length);
        }

    }


