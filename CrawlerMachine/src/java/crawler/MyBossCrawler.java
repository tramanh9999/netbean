package crawler;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class MyBossCrawler extends BaseCrawler implements Runnable {

    String catagory;
    String catagoryName;
    private String url;

    public MyBossCrawler(ServletContext context) {
        super(context);
    }

   public  MyBossCrawler(ServletContext context, String key, String value) {

   super(context);
   }

    
    
    @Override
    public void run() {
        catagory = createCatagory(catagoryName);
        if (catagory == null) {
            Logger.getLogger(MyBossCrawler.class.getName()).log(Level.SEVERE, null, new Exception("Error: catagory null"));

            return;
        }

        BufferedReader reader = null;

        try {

            reader = getBufferReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isEnding = false;
            int divCounter = 0;
            int divOpen = 0, divClose = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<div id=\"phantrang\">")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line;
                    if (line.contains("</div>")) {
                        break;
                    }
                }
            }

            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSupended()) {
                    BaseThread.getInstance().wait();
                }
            }

            int lastPage = getLastPage(document);
for(int i=0; i< lastPage; i++){
    String pageUrl= url+ "?page="+ (i+1);
    Thread pageCrawlingThread= new Thread(new MyBossEachPageCrawler(this.getContext(),pageUrl, catagory));
pageCrawlingThread.start();
synchronized(BaseThread.getInstance()){
    while(BaseThread.isSupended()){
        BaseThread.getInstance().wait();
    }
}

};
        } catch (IOException ex) {
            Logger.getLogger(MyBossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyBossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String createCatagory(String catagoryName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getLastPage(String document) {

    }

}
