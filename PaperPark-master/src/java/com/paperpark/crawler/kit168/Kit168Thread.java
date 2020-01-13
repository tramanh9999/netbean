/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.kit168;

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
public class Kit168Thread extends BaseThread implements Runnable {

    private final String URL = URLConstants.KIT168;
    
    private ServletContext context;

    public Kit168Thread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Kit168CategoryCrawler categoryCrawler = new Kit168CategoryCrawler(context);
                Map<String, String> categories = categoryCrawler.getCategories(URL);
                
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    Thread pageCrawlingThread = new Thread(
                        new Kit168CategoryPageCrawler(context, entry.getKey(), entry.getValue()));
                    pageCrawlingThread.start();
                    
                    if (ConfigConstants.DEBUG) {
                        System.out.println("DEBUG Kit168 Id = " + pageCrawlingThread.getId()
                            + "(name, link) = " + entry.getKey() + ", " + entry.getValue());
                    }
                    
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
                
                Kit168Thread.sleep(ConfigConstants.CRAWLING_INTERVAL);
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException e) {
                Logger.getLogger(Kit168Thread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    
}
