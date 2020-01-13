/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.listener;

import com.paperpark.config.crawler.CrawlerConfig;
import com.paperpark.categories_mapping.CategoryMappings;
import com.paperpark.config.model.ModelEstimation;
import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.papercraftmuseum.MuseumThread;
import com.paperpark.crawler.kit168.Kit168Thread;
import com.paperpark.dao.model.ModelDAO;
import com.paperpark.entity.Model;
import com.paperpark.utils.DBUtils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author NhanTT
 */
public class PaperParkContextListener implements ServletContextListener {

    private static Kit168Thread kit168Thread;
    private static MuseumThread museumThread;
    private static String realPath;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();

        realPath = context.getRealPath("/");

        CategoryMappings categoryMappings = getCategoryMappings(realPath);
        context.setAttribute("CATEGORY_MAPPINGS", categoryMappings);

        List<Model> models = getAllModels();
        context.setAttribute("MODELS", models);
        context.setAttribute("CACHE_TIME", System.currentTimeMillis());
        if (models != null) {
            System.out.println("INFO models cached with " + models.size() + " models at " + System.currentTimeMillis());
        }

        CrawlerConfig crawlerConfig = CrawlerConfig.getCrawlerConfig(realPath);
        if (!crawlerConfig.isEnableCrawler()) {
            System.out.println("INFO Crawler has been disabled.");
            return;
        }

        ModelEstimation modelEstimation = getModelEstimationConfig(realPath);
        context.setAttribute("MODEL_ESTIMATION", modelEstimation);

        kit168Thread = new Kit168Thread(context);
        kit168Thread.start();
        if (ConfigConstants.DEBUG) {
            System.out.println("DEBUG Kit168 Thread start with Id = " + kit168Thread.getId());
        }

        museumThread = new MuseumThread(context);
        museumThread.start();
        if (ConfigConstants.DEBUG) {
            System.out.println("DEBUG Museum Thread start with Id = " + museumThread.getId());
        }

        context.setAttribute("KIT168_THREAD", kit168Thread);
        context.setAttribute("MUSEUM_THREAD", museumThread);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtils.getEntityManager();
        if (em != null) {
            em.close();
        }
    }

    private CategoryMappings getCategoryMappings(String realPath) {
        return CategoryMappings.getCategoryMappings(realPath);
    }

    private ModelEstimation getModelEstimationConfig(String realPath) {
        return ModelEstimation.getModelEstimation(realPath);
    }

    public static String getRealPath() {
        return realPath;
    }

    private List<Model> getAllModels() {
        ModelDAO modelDAO = ModelDAO.getInstance();
        return modelDAO.getAllModels();
    }
}
