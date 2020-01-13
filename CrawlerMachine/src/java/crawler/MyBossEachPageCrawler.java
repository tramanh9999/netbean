package crawler;

import config.AppContant;
import entities.TblProduct;
import dao.ProductDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
public class MyBossEachPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private String catagory;

    public MyBossEachPageCrawler(ServletContext context, String url, String catagory) {
        super(context);
        this.url = url;
        this.catagory = catagory;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        reader = getBufferReaderForURL(url);
        String line = "";
        String document = "<document>";
        boolean isStart = false;
        boolean isEnding = false;
        int divCounter = 0;
        int divOpen = 0;
        int divClose = 0;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.contains("<ul class=\"thumbnail>")) {
                    isStart = true;

                }
                if (isStart) {
                    document += line;
                }
                if (line.contains("</ul>")) {
                    isStart = false;
                }
            }
            document += "</document>";
            try {

                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSupended()) {
                        BaseThread.getInstance().wait();

                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MyBossEachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(MyBossEachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void stAXParserForEachPage(String document) throws XMLStreamException, UnsupportedEncodingException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> catagories = new HashMap<String, String>();
        String detailLink = "", imgLink = "", price = "", productName = "";
        boolean isStart = false;

        while (eventReader.hasNext()) {
            String tagName = "";
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("li".equals(tagName)) {
                    isStart = true;
                } else if ("a".equals(tagName) && isStart) {
                    eventReader.next();
                    event = (XMLEvent) eventReader.next();
                    startElement = event.asStartElement();
                    Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                    imgLink = attrSrc == null ? "" : attrSrc.getValue();
                    eventReader.next();
                    eventReader.nextTag();
                    eventReader.next();
                    event = eventReader.nextTag();
                    startElement = event.asStartElement();
                    Attribute attrrHref = startElement.getAttributeByName(new QName("href"));
                    detailLink = attrrHref == null ? "" : attrrHref.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    productName = character.getData().trim();
                    eventReader.nextTag();
                    eventReader.next();

                    eventReader.nextTag();
                    eventReader.next();
                    eventReader.nextTag();
                    event = (XMLEvent) eventReader.next();
                    character = event.asCharacters();
                    price = character.getData().trim();
                    if (!detailLink.isEmpty()) {
                        detailLink = AppContant.urlMyBoss + detailLink;

                    }
                    if (!imgLink.isEmpty()) {
                        imgLink = AppContant.urlMyBoss + imgLink;
                    }

                }
                isStart = false;
                price = price.replaceAll("\\D", "");
                BigInteger readlPrice = new BigInteger(price);
                String catagoryId = this.catagory.getCatagoryId();
                TblProduct product = new TblProduct(new Long(1), productName, readlPrice, imgLink, catagoryId,
                        true, AppContant.domainMyBoss);
                ProductDao.getInstance().saveProductWhenCrawling(product);

            }
        }

    }
}
