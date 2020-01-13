package com.mycompany.locatorInteface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ADMIN
 */
public class SaxLocator extends DefaultHandler {

    private Locator locator;

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File("src/main/java/booka.xml"), new SaxLocator());

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(SaxLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }
    
    

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        super.characters(chars, start, length); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endElement(String string, String string1, String string2) throws SAXException {
        super.endElement(string, string1, string2); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("booktitle")) {
            System.out.println("Process element start");
        } else {
            String   location = "";
            if (this.locator != null) {
                location = locator.getSystemId();
                location += "line " + this.locator.getLineNumber() + " and clolumn " + this.locator.getColumnNumber();
            }
            System.out.println("location " + location);
        }
    }

}
