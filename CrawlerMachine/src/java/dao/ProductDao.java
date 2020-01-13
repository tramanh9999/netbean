package dao;


import entities.TblProduct;
import ultilities.DBUltilities;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN****
 */
public class ProductDao {

    private static ProductDao instance;
    private final static Object LOCK = new Object();

    
    /**
     Only one thread can excecute in synchronized block code upon 1 object */
    public static ProductDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductDao();
            }
            return instance;
        }
    }
    
    public TblProduct getProductBy(String productName, String catagoryId, String domain){
        EntityManager em= DBUltilities.getEntityManager();
        EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        List<TblProduct> result= em.createNamedQuery("TblProduct.findByNameAndCatagoryId", TblProduct.class)
                .setParameter("prodcutName", productName)
                .setParameter("catagoryId", catagoryId)
                .setParameter("resourceDomain", domain ).getResultList();
        transaction.commit();
        if(result!= null && !result.isEmpty()){
            return result.get(0);
        }
        return null;
        
    }

}
