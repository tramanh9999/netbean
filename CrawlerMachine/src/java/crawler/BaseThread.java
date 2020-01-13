package crawler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class BaseThread extends Thread{
    protected  BaseThread(){
        
    }
    
    private static BaseThread instance;
    private final static Object LOCK= new Object();
    public static BaseThread getInstance(){
        synchronized(LOCK){
            if(instance== null){
                instance= new BaseThread();
            }
            return instance; 
        }
    }
    
    private static  boolean suspened = false;
    public static boolean isSupended(){
        return suspened;
    }

    public  void setSuspened(boolean suspened) {
     this.suspened = suspened;
    }
    
    
    public void suspendThread(){
        setSuspened(true);
        System.out.println("suspended");
    }
    
    
    public synchronized void resumeThread(){
        setSuspened(false);
        notify();
        System.out.println("resume");
    }

  
    
    
    
}
