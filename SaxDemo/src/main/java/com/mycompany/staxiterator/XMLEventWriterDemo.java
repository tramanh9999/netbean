/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.staxiterator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author ADMIN
 */
public class XMLEventWriterDemo {
    
    public static void main(String[] args) {
        
        FileWriter fileWriter = null;
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            XMLEventFactory eventFactory = XMLEventFactory.newFactory();
            File file = new File("src/main/resources/persons.xml");
            fileWriter = new FileWriter(file);
            
            XMLEventWriter writer = factory.createXMLEventWriter(fileWriter);
            
            XMLEvent event = eventFactory.createStartDocument();
            writer.add(event);
            event = eventFactory.createStartElement("", null, "persons");
            writer.add(event);
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(XMLEventWriterDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLEventWriterDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLEventWriterDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
    
}
