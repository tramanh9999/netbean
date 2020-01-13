/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.saxdemo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author ADMIN
 */
public class SaxDemo {
    public static void main(String[] args) {
        
        try {
            ContentHandlerImplDemo handlerImplDemo= new ContentHandlerImplDemo();
            
            XMLReader reader= XMLReaderFactory.createXMLReader();
            
            reader.setContentHandler(handlerImplDemo);
            reader.parse("Book.xml");
        } catch (SAXException ex) {
            Logger.getLogger(SaxDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaxDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

        
 }
    
}
