package dao;


import config.CatagoryEnum;
import ultilities.DBUltilities;
import ultilities.MyUltilities;
import entities.TblCatagory;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class CatagoryDao extends BaseDao<TblCatagory, String> {

    private CatagoryDao() {

    }

    
    
    private static CatagoryDao instance;
    private final static Object LOCK = new Object();

    public static CatagoryDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CatagoryDao();
            }
            return instance;
        }
    }

    public synchronized TblCatagory getFirstCatagoryByName(String catagoryName) {
        EntityManager em = DBUltilities.getEntityManager();
        List<TblCatagory> result = em.createNamedQuery("tblCatagory.findByCatagoryName", TblCatagory.class)
                .setParameter("catagoryName", catagoryName).getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);

        }
        return null;
    }
    
    
   protected TblCatagory cratecatagory(String catagoryName){
       synchronized(LOCK){
           TblCatagory catagory= null;
           
           
           String realCatagoryName= CatagoryEnum.getRealCatagoryName(catagoryName);
           if(realCatagoryName!= null){
               CatagoryDao dao= CatagoryDao.getInstance();
               catagory= dao.getFirstCatagoryByName(realCatagoryName);
               if(catagory== null){
                   catagory= new TblCatagory(MyUltilities.generateUUID(),realCatagoryName);
                   
               }
           }
       }
        return null;
   }
}
