package crawler;


import config.AppContant;
import crawler.MyBossCrawler;
import crawler.MyBossCatagoriesCrawler;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class MyBossThread extends BaseThread implements Runnable {

    private ServletContext context;

    public MyBossThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {

            while (true) {
                try {
                    MyBossCatagoriesCrawler catagoriesCrawler = new MyBossCatagoriesCrawler(context);
                    Map<String, String> catagories = catagoriesCrawler.getCatagories(AppContant.urlMyBoss);
                    for (Map.Entry<String, String> entry : catagories.entrySet()) {
                        Thread crawlingThread = new Thread(new MyBossCrawler(context, entry.getKey(), entry.getValue()));
                        crawlingThread.start();
                        synchronized (BaseThread.getInstance()) {
                            while (BaseThread.isSupended()) {
                                BaseThread.getInstance().wait();
                            }
                        }
                        
                    }
                    MyBossThread.sleep(TimeUnit.DAYS.toMillis(AppContant.breakTimeCrawling));
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSupended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyBossThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        

    }
    }
}
