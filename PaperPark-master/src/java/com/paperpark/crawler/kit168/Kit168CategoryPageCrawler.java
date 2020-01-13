/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.kit168;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.BaseCrawler;
import com.paperpark.crawler.BaseThread;
import com.paperpark.dao.category.CategoryDAO;
import com.paperpark.entity.Category;
import com.paperpark.utils.CategoryHelper;
import com.paperpark.utils.ElementChecker;
import com.paperpark.utils.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author NhanTT
 */
public class Kit168CategoryPageCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private String categoryName;

    public Kit168CategoryPageCrawler(ServletContext context, String pageUrl,
            String categoryName) {
        super(context);
        this.pageUrl = pageUrl;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        Category category = createCategory(categoryName);
        if (category == null) {
            Logger.getLogger(Kit168CategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, new Exception("Error: category null"));
            return;
        }

        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getCategoryPageDocument(reader);
            
            document = TextUtils.refineHtml(document);

            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
            
            int lastPage = getLastPage(document);
            
            for (int i = 1; i <= lastPage; ++i) {
                String categoryPageUrl = pageUrl + "page/" + i;
                
                Thread modelListCrawler = new Thread(
                        new Kit168ModelListCrawler(getContext(), categoryPageUrl, category));
                modelListCrawler.start();
                
                if (i % ConfigConstants.CRAWL_THREAD_REDUCE > 0) {
                    modelListCrawler.join();
                }
                
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (IOException | InterruptedException | 
                XMLStreamException | NumberFormatException ex) {
            Logger.getLogger(Kit168CategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getCategoryPageDocument(BufferedReader reader) throws IOException {
        String line = "";
        String document = "";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<nav class=\"page-navigation\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</nav>")) {
                break;
            }
        }
        return document;
    }

    private int getLastPage(String document)
            throws UnsupportedEncodingException, XMLStreamException, NumberFormatException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                
                if (ElementChecker.isElementWith(startElement, "li", "class", "bpn-last-page-link")) {
                    event = (XMLEvent) eventReader.next();
                    startElement = event.asStartElement();
                    
                    String href = getHref(startElement);
                    String[] hrefTokens = href.split("/");
                    String pageStr = hrefTokens[hrefTokens.length - 1];
                    
                    return Integer.parseInt(pageStr);
                }
            }
        }
        return 1;
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

    private String getHref(StartElement a) {
        Attribute href = a.getAttributeByName(new QName("href"));
        return href == null ? "" : href.getValue();
    }
}
