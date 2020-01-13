package dao;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import ultilities.DBUltilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class BaseDao<T, PK extends Serializable> implements IGenericDao<T, PK> {

    protected Class<T> entityClass;

    public BaseDao() {
        ParameterizedType genericSuperClass
                = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public T create(T t) {
        
        
        
    }

    @Override
    public T findById() {
    }

    @Override
    public T update() {
    }

    @Override
    public T delete(T t) {
    }

    @Override
    public List<T> getAll(String namedQuery) {
        EntityManager em = DBUltilities.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        List<T> result= 
    }
    
    

}
