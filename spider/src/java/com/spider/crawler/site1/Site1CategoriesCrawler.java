/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.crawler.site1;

import com.spider.constant.AppConstant;
import com.spider.crawler.BaseCrawler;
import com.spider.crawler.BaseThread;
import com.sun.xml.internal.fastinfoset.stax.util.StAXParserWrapper;
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

/**
 *
 * @author ADMIN
 */
public class Site1CategoriesCrawler extends BaseCrawler {

    private ServletContext context;

    public Site1CategoriesCrawler(ServletContext context) {
        super(context);
    }
    private String key, value;

    public Site1CategoriesCrawler() {
    }

    public Site1CategoriesCrawler(ServletContext context, String key, String value) {
        super(context);
        this.key = key;
        this.value = value;
    }

    public Map<String, String> getCategories(String url) throws IOException {

        try {
            BufferedReader bufferedReader = null;
            bufferedReader = getBufferedReaderForUrl(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isFound = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (isStart && line.contains("<img src=\"")) {
                    break;
                }
                if (isStart && line.contains("</a>")) {
                    document += line.trim();
                    System.out.println(line);
                }

                if (line.contains("<div class=\"navbar-link\">Thể loại</div>")) {
                    isStart = true;
                }

            }
            System.out.println(document);
            return stAXParserForCategories(document);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Site1CategoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Site1CategoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Site1CategoriesCrawler scc = new Site1CategoriesCrawler();

            scc.getCategories("https://truyenqq.com");
        } catch (IOException ex) {
            Logger.getLogger(Site1CategoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * parse từ html sang xml *
     */
    public Map<String, String> stAXParserForCategories(String document) throws XMLStreamException, UnsupportedEncodingException {
        document = document.trim();

        document = "<ul>" + document;
        document += "</ul>";

        XMLEventReader eventReader = parseStringToXMLEventReader(document);

        Map<String, String> categories = new HashMap<>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    String link = attrHref.getValue();

                    //read by lne in ''event"
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();

                    categories.put(link, character.getData());
                }
            }

        }

        return categories;
    }
}
