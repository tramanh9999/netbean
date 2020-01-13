/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.crawler.site1;

import com.spider.constant.AppConstant;
import com.spider.crawler.BaseThread;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

/**
 *
 * @author ADMIN
 */
public class Site1Thread extends BaseThread implements Runnable {

    private ServletContext context;

    public Site1Thread(ServletContext context) {
        this.context = context;

    }

    @Override
    public void run() {

        while (true) {
            try {
                Site1CategoriesCrawler categoryCrawler = new Site1CategoriesCrawler(context);
                Map<String, String> categories = categoryCrawler.getCategories(AppConstant.URL_SITE_1);

                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    Thread crawlingThread = new Thread(new Si(context, entry.getKey(), entry.getValue()));

                    crawlingThread.start();
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSupspended()) {
                            BaseThread.getInstance()
                                    .wait();
                        }

                    }
                }

                Site1Thread.sleep(TimeUnit.DAYS.toMillis(AppConstant.BREAK_TIME_CRAWLING));

                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSupspended()) {
                        BaseThread.getInstance().wait();
                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Site1Thread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Site1Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
