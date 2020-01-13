/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.staxiterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author ADMIN
 */
public class StaxIterator {

    public static void main(String[] args) {
        Reader fileReader = null;

        try {

            XMLInputFactory xmlif = XMLInputFactory.newFactory();
            
            
            fileReader = new FileReader(new File("src\\main\\resources\\library.xml"));
            XMLEventReader reader = xmlif.createXMLEventReader(fileReader);
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    System.out.println("Start element:" + startElement.getName());
                    Iterator attributes = startElement.getAttributes();
                    while (attributes.hasNext()) {
                        Attribute next = (Attribute) attributes.next();
                        System.out.println("    Attribute: " + next.getName().getLocalPart() + "=" + next.getValue());
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    System.out.println("End element:" + endElement.getName());

                }
                if (event.isCharacters()) {
                    Characters chars = event.asCharacters();
                    if (!chars.isWhiteSpace()) {
                        System.out.println("Text: " + chars.getData());
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(StaxIterator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StaxIterator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(StaxIterator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
