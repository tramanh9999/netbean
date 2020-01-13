/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.dao;

import com.spider.dao.inteface.IGenericDao;
import java.io.Serializable;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Parameterizable;
import jdk.nashorn.internal.runtime.logging.Loggable;
import org.eclipse.jdt.internal.compiler.env.IGenericMethod;

/**
 *
 * @author ADMIN
 */
/**
 */
public class BaseDAO<T, PK extends Serializable> implements IGenericDao<T, PK> {

    protected Class<T> entityClass;

    // lấy kiểu class của class hiện thòi( là 1 trong những class extand BáeClass
    public BaseDAO() {

        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];

    }

    @Override
    public T create(T t) {
    }

    @Override
    public T findById(PK pk) {
    }

    @Override
    public T update(T t) {
    }

    @Override
    public boolean delete(T t) {
    }

    // lấy 
    @Override
    public List<T> getAll(String querry) {

        try {
            EntityManager em = DBIUtilities.getEntityManaget();
            try {
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();

                List<T> result = em.createNamedQuerry(namedQuerry, entityClass).getResultList();
                transaction.commit();
                return result;

            } catch (Exception e) {
                Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, e);

            } finally {
                if (em != null) {
                    em.close();
                }
            }
            return null;
        }

    }

}
