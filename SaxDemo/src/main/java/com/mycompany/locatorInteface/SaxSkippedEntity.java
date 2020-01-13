/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.locatorInteface;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * // * @author ADMIN
 */
public class SaxSkippedEntity extends DefaultHandler {

    Locator l = null;

    public static void main(String[] args) {
        try {

            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(new SaxSkippedEntity());

            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            reader.parse("src/main/resources/skipDemo.xml");

        } catch (SAXException ex) {
            Logger.getLogger(SaxSkippedEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaxSkippedEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int count = 0;

    @Override
    public void startElement(String string, String string1, String string2, Attributes atrbts) throws SAXException {

        System.out.println("Start element:" + string1);
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDocumentLocator(Locator lctr) {
        this.l = lctr;
    }

    @Override
    public void skippedEntity(String name) throws SAXException {

        System.out.println("Skip Entity");
        System.out.println("Entity name reference " + name);
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {

        String location = "";
        if (l != null) {
            location = l.getSystemId();
            location += "line " + l.getLineNumber() + " and cloumn " + l.getColumnNumber();

        }
        System.out.println("location " + location);
        String string = new String(chars, start, length);
        System.out.println("Content :" + string);
        //To change body of generated methods, choose Tools | Templates.
        ;
    }

}
