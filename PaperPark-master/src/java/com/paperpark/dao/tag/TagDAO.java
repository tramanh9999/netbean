/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.dao.tag;

import com.paperpark.dao.BaseDAO;
import com.paperpark.entity.Tag;
import com.paperpark.utils.DBUtils;
import com.paperpark.utils.TagHelper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author NhanTT
 */
public class TagDAO extends BaseDAO<Tag, String> {

    private TagDAO() {
        
    }
    
    private static TagDAO instance;
    private static final Object LOCK = new Object();
    
    public static TagDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new TagDAO();
            }
        }
        return instance;
    }
    
    public synchronized Tag getAndInsertIfNewTag(String name) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<Tag> tags = em.createNamedQuery("Tag.findByName", Tag.class)
                    .setParameter("name", name)
                    .getResultList();
            if (tags != null && !tags.isEmpty()) {
                return tags.get(0);
            }
            
            Tag tag = new Tag(TagHelper.generateUUID(), name);
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(tag);
            transaction.commit();
            return tag;
        } catch (Exception e) {
            Logger.getLogger(TagDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
