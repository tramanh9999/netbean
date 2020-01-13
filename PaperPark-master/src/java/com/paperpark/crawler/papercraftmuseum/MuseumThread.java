/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.papercraftmuseum;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.contants.URLConstants;
import com.paperpark.crawler.BaseThread;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author NhanTT
 */
public class MuseumThread extends BaseThread implements Runnable {

    private final String URL = URLConstants.PAPERCRAFTMUSEUM_CATEGORIES;

    private ServletContext context;

    public MuseumThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MuseumCategoryCrawler categoryCrawler = new MuseumCategoryCrawler(context);
                Map<String, String> categories = categoryCrawler.getCategories(URL);
                
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    Thread pageCrawlingThread = new Thread(
                        new MuseumCategoryPageCrawler(context, entry.getKey(), entry.getValue()));
                    pageCrawlingThread.start();
                    
                    if (ConfigConstants.DEBUG) {
                        System.out.println("DEBUG museum id = " + pageCrawlingThread.getId() 
                            + " (name; link) = " + entry.getValue() + "; " + entry.getKey());
                    }
                    
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
                
                MuseumThread.sleep(ConfigConstants.CRAWLING_INTERVAL);
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException e) {
                Logger.getLogger(MuseumThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
