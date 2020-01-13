package crawler;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class BaseCrawler {
    ServletContext context;
    public BaseCrawler(ServletContext context){
        this.context= context;
    }
    public ServletContext getContext(){
        return context;
        
    }
    
    
    protected BufferedReader getBufferReaderForURL(String urlString) throws UnsupportedEncodingException, IOException{
        URL url= new URL(urlString);
        URLConnection connection= url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is= connection.getInputStream();
        BufferedReader reader= new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }
    
    
    /**
     create event reader (SAX) to read xml string section
     */
    protected XMLEventReader parseStringToXMLEventReader(String xmlSection) throws XMLStreamException, UnsupportedEncodingException{
        byte[] byteArray= xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream= new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory= XMLInputFactory.newInstance();
        XMLEventReader reader= inputFactory.createXMLEventReader(inputStream);
        return reader;
    }
            
}
