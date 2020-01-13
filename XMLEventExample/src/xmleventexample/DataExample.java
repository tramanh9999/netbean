package xmleventexample;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class DataExample extends BaseCrawler {

    public DataExample() {

    }

    public static void main(String[] args) {
        new DataExample().stAXParseForEachPage(document);

    }

    public void stAXParseForEachPage(String document) {
        try {
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMLEventReader(document);
            Map<String, String> categories = new HashMap<String, String>();
            String detailLink = "";

            String imgLink = "";
            String price = "";
            String productName = "";
            boolean isStart = false;
            while (eventReader.hasNext()) {
                String tagName = "";
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    if ("a".equals(tagName)) {

                        XMLEvent x = (XMLEvent) eventReader.next();

                        x = eventReader.nextTag();
                        x = (XMLEvent) eventReader.next();
                        x = (XMLEvent) eventReader.next();
                        x = (XMLEvent) eventReader.next();
                    }
                }

            }
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();

        }

    }

    public static String document = "<a id=\"0\"><a id=\"1\"><a id=\"1.5\"></a></a><a id=\"2\"></a><a id=\"3\"></a><a id=\"4\"></a><a id=\"5\"></a><a id=\"6\"></a><a id=\"7\"></a></a>";
}
