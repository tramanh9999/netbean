/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhtht.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ADMIN
 */
public class XML_DOM {
    
    
    
   public void List<Student> all(){
       
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
       try {
           DocumentBuilder db=   dbf.newDocumentBuilder();
           
           String filePath="C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\PE_Exam\\web\\WEB-INF\\students.xml";
//            String realpath=
           Document doc= db.parse(new File(filePath));
           
           
           
           
           
       } catch (SAXException ex) {
           Logger.getLogger(XML_DOM.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(XML_DOM.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParserConfigurationException ex) {
           Logger.getLogger(XML_DOM.class.getName()).log(Level.SEVERE, null, ex);
       }
            
   }
      
    }
}
