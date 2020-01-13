/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.crawler.site1;

import com.spider.constant.AppConstant;
import com.spider.crawler.BaseCrawler;
import com.spider.crawler.BaseThread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
public class Site1EachPageCrawler implements Runnable {

    private String url;

    public Site1EachPageCrawler(String url) {
        this.url = url;
    }

    public Site1EachPageCrawler() {
    }

    public static void main(String[] args) {

        Site1EachPageCrawler x = new Site1EachPageCrawler();
        x.url = "https://www.adidas.com/us/women-shoes";
        x.run();
    }

    protected BufferedReader getBufferedReaderForUrl(String urlString) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64 ; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    @Override
    public void run() {

        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(url);
            String line = "";
            String document = "<document>";
            boolean isStart = false;
            boolean isFound = false;
            int divCounter = 0;
            int divOpen = 0;
            int divClose = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<ul class=\"ul product-list\">")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line;

                }
                if (line.contains("</ul>")) {
                    isStart
                            = false;
                }

            }
            document
                    += "</document>";
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSupspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Site1Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
//            stAXParseForEachPage(document);
System.out.println(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Site1EachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Site1EachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//
//    private void stAXParseForEachPage(String document) {
//        try {
//            document = document.trim();
//            XMLEventReader eventReader = parseStringToXMLEventReader(document);
//            Map<String, String> categories = new HashMap<String, String>();
//            String detailLink = "";
//
//            String imgLink = "";
//            String price = "";
//            String productName = "";
//
//            String discount = "";
//            String oldprice = "";
//
//            String status = "";
//            boolean isStart = false;
//            while (eventReader.hasNext()) {
//                String tagName = "";
//                XMLEvent event = (XMLEvent) eventReader.next();
//                if (event.isStartElement()) {
//                    StartElement startElement = event.asStartElement();
//                    tagName = startElement.getName().getLocalPart();
//                    if ("li".equals(tagName)) {
//                        // start crawl li in ul
//                        isStart = true;
//                    } else if ("a".equals(tagName) && isStart) {
//                        //move to </a>
//                        eventReader.next();
//
//                        //move to <img>
//                        event = (XMLEvent) eventReader
//                                .next();
//
//                        startElement = event.asStartElement();
//                        Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
//                        //check value of image src
//                        imgLink = attrSrc == null ? "" : attrSrc.getValue();
////move to </img>
//                        eventReader.next();
//                        // </a>
//                        eventReader.nextTag();
//                        //h2
//                        eventReader.next();
//                        //a há product name
//                        event = eventReader.nextTag();
//
//                        startElement = event.asStartElement();
//                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
//
//                        // detail link  that not absolute link
//                        detailLink = attrHref == null ? "" : attrHref.getValue();
//
//                        Characters character = event.asCharacters();
//
//                        productName = character.getData().trim();
//                        eventReader.nextTag();
//                        eventReader.next();
//                        //move to span tag = price
//                        event = (XMLEvent) eventReader.next();
//                        //get price
//                        character = event.asCharacters();
//                        price = character.getData().trim();
//
//                        eventReader.nextTag();
//                        event = (XMLEvent) eventReader.next();
//                        //get dícount
//                        character = event.asCharacters();
//                        discount = character.getData().trim();
//
//                        eventReader.nextTag();
//                        event = (XMLEvent) eventReader.next();
//                        //get old price
//                        character = event.asCharacters();
//                        oldprice = character.getData().trim();
//
//                        eventReader.nextTag();
//                        eventReader.next();
//                        eventReader.nextTag();
//                        event = (XMLEvent) eventReader.next();
//                        //get status
//
//                        // chek lại có thể không get được luôn data của span này vì có i chặn bên trên
//                        character = event.asCharacters();
//                        status = character.getData().trim();
//                        if (!detailLink.isEmpty()) {
//                            detailLink = AppConstant.URL_SITE_1 + detailLink;
//
//                        }
//
//                        if (imgLink.isEmpty() == false) {
//                            imgLink = AppConstant.URL_SITE_1 + imgLink;
//                        }
//
//                        isStart = false;
//                        try {
//                            price = price.replaceAll("// VNĐ", "");
//                            BigInteger realPrice = new BigInteger(price);
//                            String categoryId = this.category.getCategoryId();
//
//                            TblProduct product = new TblProduc(new Long(1), productName, readlPrice, imgLink,
//                                    categoryId, true, AppContant.DOMAIN_ANKHANG);
//
//                            ProductDao.getInstance().saveProductWhenCrawling(product);
//
//                        } catch (NumberFormatException e) {
//                            Logger.getLogger(AddidasPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    }
//                }
//
//            }
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(Site1EachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Site1EachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

}
