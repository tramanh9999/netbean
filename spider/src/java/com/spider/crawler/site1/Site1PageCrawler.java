/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.crawler.site1;

import com.spider.crawler.BaseCrawler;
import com.spider.crawler.BaseThread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 *
 * crawl data belong to paging
 */
public class Site1PageCrawler extends BaseCrawler implements Runnable {

    private String url;
    String categoryName;
    protected TblCategory category = null;

    public Site1PageCrawler(ServletContext context, String url, String categoryName) {
        super(context);
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        // save category in to db
//        category = createCategory(categoryName);

//        if (category == null) {
//            Logger.getLogger(Site1PageCrawler.class.getName()).log(Level.SEVERE, null, new Exception("Error: category: null"));
//            return;
//        }
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(url);

            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isFound = false;
            int divCounter = 0;
            int divOpen = 0;
            int divClose = 0;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("<div class=\"paging\">")) {
                        isStart = true;
                    }
                    if (isStart) {
                        document += line;
                        if (line.contains("</div>")) {
                            break;
                        }
                    }
                }
                try {
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSupspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Site1PageCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
                int lastPage = getLastPage(document);

                for (int i = 0; i < lastPage; i++) {
                    String pageUrl = url + "?page=" + (i + 1);

                    Thread pageCrawlingThread = new Thread(new Site1EachPageCrawler(this.getContext(), pageUrl, category));
                    pageCrawlingThread.start();
                    try {
                        synchronized (BaseThread.getInstance()) {
                            while (BaseThread.isSupspended()) {
                                BaseThread.getInstance().wait();
                            }
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Site1PageCrawler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(Site1PageCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Site1PageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Site1PageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getLastPage(String document) {

        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        String tagName = "";
        String link = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
            link = attrHref == null ? "" : attrHref.getValue();

                }
            }
        }
        if (link != null && !link.isEmpty()) {
            String regex = "[0-9]+$";
            Pattern pattern = Pattern.compile("regex");

            Matcher matcher = pattern.matcher(link);
            if (matcher.find()) {
                String result = matcher.group();
                try {
                    int number = Integer.parseInt(result);
                    return number;

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;

    }

}
