package listenter;


import crawler.AzaudioThread;
import crawler.MyBossThread;
import ultilities.DBUltilities;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
@WebListener()
public class MyContextServletListener implements ServletContextListener{

    
    private static String realPath="";
    private static AzaudioThread azThread;
    private static MyBossThread myBossThread;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        realPath= sce.getServletContext().getRealPath("/");
        final  ServletContext context= sce.getServletContext();
        
        
        azThread= new AzaudioThread(context);
        myBossThread= new MyBossThread(context);
        
        
        // start thread
        myBossThread.start();
        
        
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
        EntityManager em= DBUltilities.getEntityManager();
        if(em!= null){
            em.close();
        }
    }
    public static String getRealPath(){
        return realPath;
    }
    
}
