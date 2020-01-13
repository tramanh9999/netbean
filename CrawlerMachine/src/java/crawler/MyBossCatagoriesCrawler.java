package crawler;


import config.AppContant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class MyBossCatagoriesCrawler extends BaseCrawler {

    public MyBossCatagoriesCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCatagories(String url) {

        BufferedReader reader = null;

        try {
            reader = getBufferReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isFound = false;

            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("</li><li style=\"display")) {
                    break;
                }
                if (isStart) {
                    document += line.trim();
                }
                if (isFound && line.contains("</a>")) {
                    isStart = true;
                }
                if (line.contains("<a href=\"thiet-bi-choi-game-cl\"")) {
                    isFound = true;
                }
            }

            return stAXParserForCatagories(document);
        } catch (IOException ex) {
            Logger.getLogger(MyBossCatagoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MyBossCatagoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(MyBossCatagoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;

    }

    private Map<String, String> stAXParserForCatagories(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();

        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> catagories = new HashMap<>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    String link = AppContant.urlMyBoss + attrHref.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    catagories.put(link, character.getData());
                }
            }
        }
        return catagories;
    }
    
    

}
