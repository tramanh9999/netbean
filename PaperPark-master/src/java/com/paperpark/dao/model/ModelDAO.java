/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.dao.model;

import com.paperpark.config.model.DifficultyEstimation;
import com.paperpark.config.model.ModelEstimation;
import com.paperpark.contants.ConfigConstants;
import com.paperpark.dao.BaseDAO;
import com.paperpark.entity.Model;
import com.paperpark.utils.DBUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author NhanTT
 */
public class ModelDAO extends BaseDAO<Model, Integer> {

    private ModelDAO() {

    }

    private static ModelDAO instance;
    private static final Object LOCK = new Object();

    public static ModelDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ModelDAO();
            }
        }
        return instance;
    }

    public Model getModelByLink(String link) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Model> models = em.createNamedQuery("Model.findByLink")
                    .setParameter("link", link)
                    .getResultList();

            transaction.commit();

            if (models != null && !models.isEmpty()) {
                return models.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public List<Model> getModelsByName(String name) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Model> models = em.createNamedQuery("Model.findByName")
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

            transaction.commit();

            return models;
        } catch (Exception e) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public Model getModelById(int id) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Model model = em.createNamedQuery("Model.findById", Model.class)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();

            return model;
        } catch (Exception e) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public synchronized Model saveModelWhileCrawling(ServletContext context, Model model) {
        Model existedModel = getModelByLink(model.getLink());
        if (existedModel == null) {
            refineModel(context, model);
            return create(model);
        }
        refineModel(context, model);
        model.setId(existedModel.getId());
        return update(model);
    }

    public synchronized void refineModel(ServletContext context, Model model) {
        ModelEstimation modelEstimation
                = (ModelEstimation) context.getAttribute("MODEL_ESTIMATION");
        if (modelEstimation == null) {
            return;
        }

        Integer numOfParts = model.getNumOfParts();
        Integer numOfSheets = model.getNumOfSheets();

        if (model.getDifficulty() == null || model.getDifficulty() == 0) {
            if (numOfSheets != null && numOfSheets > 0) {
                if (numOfParts != null && numOfParts > 0) {
                    Double partsPerSheet = 1.0 * numOfParts / numOfSheets;
                    Integer difficulty = estimateDifficulty(partsPerSheet, modelEstimation);

                    model.setDifficulty(difficulty);
                } else {
                    Integer difficulty = estimateDifficulty(numOfSheets, modelEstimation);
                    model.setDifficulty(difficulty);
                }
            }
        }
    }

    private synchronized Integer estimateDifficulty(Double partsPerSheet, ModelEstimation estimation) {
        DifficultyEstimation lowestDifficulty = estimation.getDifficultyEstimation().get(0);

        if (partsPerSheet <= lowestDifficulty.getMaxPartsPerSheet().doubleValue()) {
            return lowestDifficulty.getDifficulty().intValue();
        }

        for (int i = 1; i < estimation.getDifficultyEstimation().size(); ++i) {
            DifficultyEstimation de = estimation.getDifficultyEstimation().get(i);
            if (partsPerSheet <= de.getMaxPartsPerSheet().doubleValue()) {
                return de.getDifficulty().intValue();
            }
        }

        return 0;
    }

    private synchronized Integer estimateDifficulty(Integer numOfSheets, ModelEstimation estimation) {
        DifficultyEstimation lowestDifficulty = estimation.getDifficultyEstimation().get(0);

        if (numOfSheets <= lowestDifficulty.getMaxNumberOfSheets().intValue()) {
            return lowestDifficulty.getDifficulty().intValue();
        }

        for (int i = 1; i < estimation.getDifficultyEstimation().size(); ++i) {
            DifficultyEstimation de = estimation.getDifficultyEstimation().get(i);
            if (numOfSheets <= de.getMaxNumberOfSheets().intValue()) {
                return de.getDifficulty().intValue();
            }
        }
        return 0;
    }

    public long getCountModels() {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            long count = (long) em.createNamedQuery("Model.getCountModels").getSingleResult();
            transaction.commit();

            return count;
        } catch (Exception e) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return 0;
    }

    /**
     * get all models directly from db
     *
     * @return models
     */
    public List<Model> getAllModels() {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<Model> models = em.createNamedQuery("Model.findAll").getResultList();
            transaction.commit();

            if (models == null) {
                models = new ArrayList<>();
            }

            return models;
        } catch (Exception e) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return new ArrayList<>();
    }

    /**
     * get cached models from session scope
     *
     * @param session
     * @param skillLevel
     * @return
     */
    public List<Model> getAllModels(HttpSession session, int skillLevel) {
        List<Model> models = (List<Model>) session.getAttribute("MODELS");
        Long cacheTime = (Long) session.getAttribute("CACHE_TIME");

        long now = System.currentTimeMillis();

        ServletContext context = session.getServletContext();

        if (models == null || cacheTime == null
                || (now - cacheTime > ConfigConstants.CACHE_MODELS_TIMEOUT)) {

            models = getAllModels(session.getServletContext());
            estimateModelsMakingTime(context, models, skillLevel);

            session.setAttribute("MODELS", models);
            session.setAttribute("SKILL_LEVEL", skillLevel);
            session.setAttribute("CACHE_TIME", now);
        }

        Integer cachedSkillLevel = (Integer) session.getAttribute("SKILL_LEVEL");
        if (cachedSkillLevel == null || cachedSkillLevel != skillLevel) {
            estimateModelsMakingTime(context, models, skillLevel);

            session.setAttribute("MODELS", models);
            session.setAttribute("SKILL_LEVEL", skillLevel);
            session.setAttribute("CACHE_TIME", now);
        }

        return models;
    }

    /**
     * get cached models from application scope
     *
     * @param context
     * @return
     */
    public List<Model> getAllModels(ServletContext context) {
        List<Model> models = (List<Model>) context.getAttribute("MODELS");
        Long cacheTime = (Long) context.getAttribute("CACHE_TIME");

        long now = System.currentTimeMillis();

        if (models == null || cacheTime == null
                || (now - cacheTime > ConfigConstants.CACHE_MODELS_TIMEOUT)
                || models.size() < getCountModels()) {

            ModelDAO modelDAO = ModelDAO.getInstance();
            models = modelDAO.getAllModels();

            context.setAttribute("MODELS", models);
            context.setAttribute("CACHE_TIME", now);
        }

        return models;
    }

    /**
     * estimate making time for models
     *
     * @param context
     * @param models
     * @param skillLevel
     */
    private void estimateModelsMakingTime(ServletContext context, List<Model> models, int skillLevel) {
        ModelEstimation estimation = (ModelEstimation) context.getAttribute("MODEL_ESTIMATION");
        if (estimation == null) {
            estimation = ModelEstimation.getModelEstimation(context.getRealPath("/"));
            context.setAttribute("MODEL_ESTIMATION", estimation);
        }

        final ModelEstimation me = estimation;

        models.forEach((model) -> {
            model.estimateMakingTime(me, skillLevel);
        });
    }
}
