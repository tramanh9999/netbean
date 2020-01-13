package ultilities;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class DBUltilities {

    private static final Object LOCK = new Object();

    private DBUltilities() {
    }

    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        synchronized (LOCK) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("TPGamingGearPU");

            }
        }
        return emf.createEntityManager();
    }
}
