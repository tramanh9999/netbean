/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.processing;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ADMIN
 */
public class SaxInstruction  extends DefaultHandler{

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

        System.out.println("target:"+target);
        System.out.println("data:"+data);
    
    }
    
    
    public static void main(String[] args) {
        try {
            SaxInstruction saxObject= new SaxInstruction();
            SAXParserFactory spf= SAXParserFactory.newInstance();
            System.out.println("validating : "+ spf.isValidating()+"..\n");
            SAXParser parser= spf.newSAXParser();
            parser.parse(new File("src\\main\\resources\\instruction.xml"), saxObject);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(SaxInstruction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

