/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.util;

import com.domdemo.dto.StudentDTO;
import com.domdemo.dto.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author ADMIN
 */
public class Sax_util {

    
    
    
    
    
    public List<StudentDTO> readListStudents(File file) throws IOException {

        FileInputStream fileInputStream = null;
        try {
            List<StudentDTO> sdtos = new ArrayList<>();
            SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
            SAXParser newSAXParser = sAXParserFactory.newSAXParser();
            SaxReadAllHandler saxHandler = new SaxReadAllHandler();
            fileInputStream = new FileInputStream(file);
            newSAXParser.parse(fileInputStream, saxHandler);
            return saxHandler.getSdtos();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sax_util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Sax_util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Sax_util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Sax_util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public List<StudentDTO> findStudentListByAddress(String searchvalue, File file) {

        try {
            SAXParserFactory saxpf = SAXParserFactory.newInstance();

            SAXParser parser = saxpf.newSAXParser();
//            SaxSearchByUserNameHandler saxHandler = new SaxSearchByUserNameHandler(searchvalue);
//            parser.parse(file, saxHandler);
//
//            return saxHandler.getStudents();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Sax_util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Sax_util.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return null;
    }

    public StudentDTO findStudent(String id, File file) {

        try {
            StudentDTO dTO = null;
            SAXParserFactory saxpf = SAXParserFactory.newInstance();

            SAXParser parser = saxpf.newSAXParser();
            SaxReadAllHandler saxHandler = new SaxReadAllHandler();
            parser.parse(file, saxHandler);

        } catch (SAXException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public User findUser(String us) {
        
        return null;
    }

}
