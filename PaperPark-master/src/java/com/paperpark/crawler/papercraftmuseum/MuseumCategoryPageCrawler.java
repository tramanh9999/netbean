/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.papercraftmuseum;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.contants.URLConstants;
import com.paperpark.crawler.BaseCrawler;
import com.paperpark.crawler.BaseThread;
import com.paperpark.dao.category.CategoryDAO;
import com.paperpark.entity.Category;
import com.paperpark.utils.CategoryHelper;
import com.paperpark.utils.ElementChecker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author NhanTT
 */
public class MuseumCategoryPageCrawler extends BaseCrawler implements Runnable {

    private static final String MUSEUM_URL = URLConstants.PAPERCRAFTMUSEUM_MUSEUM;

    private String pageUrl;
    private String categoryName;

    public MuseumCategoryPageCrawler(ServletContext context, String pageUrl,
            String categoryName) {
        super(context);
        this.pageUrl = pageUrl.replaceAll(" ", "%20");
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        Category category = createCategory(categoryName);
        if (category == null) {
            Logger.getLogger(MuseumCategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, new Exception("Error: category null"));
            return;
        }

        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getCategoryPageDocument(reader);
            
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }

            int lastPage = getLastPage(document);

            for (int i = 1; i <= lastPage; ++i) {
                String categoryPageUrl
                        = MUSEUM_URL + "/page/" + i + "/?cate=" + categoryName;

                Thread modelCrawler = new Thread(
                        new MuseumModelCrawler(getContext(), categoryPageUrl, category));
                modelCrawler.start();
                
                if (i % ConfigConstants.CRAWL_THREAD_REDUCE > 0) {
                    modelCrawler.join();
                }
                
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (IOException | InterruptedException | XMLStreamException ex) {
            Logger.getLogger(MuseumCategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getCategoryPageDocument(BufferedReader reader) throws IOException {
        String line = "";
        String document = "<categoryPages>";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<div class=\"pagesnav\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</div>")) {
                break;
            }
        }
        document += "</categoryPages>";
        return document;
    }

    private int getLastPage(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        XMLEvent event;
        int lastPage = 1;
        boolean isStartCounter = false;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();

                if (ElementChecker.isElementWith(startElement, "div", "class", "pagesnav")) {
                    isStartCounter = true;
                } else if (ElementChecker.isElementWith(startElement, "a")) {
                    ++lastPage;
                }
            } else if (isStartCounter && event.isEndElement()) {
                EndElement endElement = event.asEndElement();

                if (ElementChecker.isElementWith(endElement, "div")) {
                    break;
                }
            }
        }

        return lastPage;
    }

    private static final Object LOCK = new Object();

    protected Category createCategory(String name) {
        synchronized (LOCK) {
            Category category = null;
            String realName = getRealCategoryName(name);
            if (realName != null) {
                CategoryDAO dao = CategoryDAO.getInstance();
                category = dao.getFirstCategory(realName);
                if (category == null) {
                    category = new Category(CategoryHelper.generateUUID(), realName);
                    dao.create(category);
                }
            }
            return category;
        }
    }

    private String getRealCategoryName(String altName) {
        CategoryHelper helper = new CategoryHelper(getContext());
        return helper.getRealCategoryName(altName);
    }
}
