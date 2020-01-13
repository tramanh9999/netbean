/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author ADMIN
 */
// t
public class BaseCrawler {

    private ServletContext context;

    public BaseCrawler(ServletContext context) {
        this.context = context;
    }

    public BaseCrawler() {
    }

    public ServletContext getContext() {
        return context;
    }

    protected BufferedReader getBufferedReaderForUrl(String urlString) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64 ; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    public static void main(String[] args) {
        BufferedReader bf = null;
        try {
            BaseCrawler br = new BaseCrawler();
            bf = br.getBufferedReaderForUrl("https://truyenqq.com/the-loai/comic-60/trang-2.html");

            while ((bf.readLine()) != null) {
                String line = bf.readLine();
                System.out.println(line);
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bf.close();
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    protected XMLEventReader parseStringToXMLEventReader(String xmlSection) throws XMLStreamException, UnsupportedEncodingException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }

}
