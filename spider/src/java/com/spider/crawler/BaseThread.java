/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.crawler;

import com.sun.media.jfxmedia.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class BaseThread extends Thread {

    private static BaseThread instance;
    private final static Object LOCK = new Object();

    public BaseThread() {

    }

    public static BaseThread getInstance() {

        synchronized (LOCK) {
            if (instance == null) {
                instance = new BaseThread();
            }
        }
        return instance;
    }
// use to pause thread 
    private static boolean supspended = false;

    public static boolean isSupspended() {
        return supspended;
    }

    public static void setSupspended(boolean supspended) {
        BaseThread.supspended = supspended;
    }
// if supspend == true then current thread is sleep=pause

    public void supspendThread() {
        setSupspended(true);
        System.out.println("supspended");
    }

    public synchronized void resumeThread() {
        setSupspended(false);
        notifyAll();
        System.out.println("resume");
    }

}
