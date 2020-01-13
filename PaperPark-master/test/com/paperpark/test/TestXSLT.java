/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author NhanTT
 */
public class TestXSLT {
    
    private static final String realPath = "E:\\Java\\Summer_2019\\PaperPark\\web\\";

    public static void main(String[] args) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            
            StreamSource xslFile = new StreamSource("web/xsl/model-detail.xsl");
            Transformer transformer = factory.newTransformer(xslFile);
            
            StreamSource xmlFile = new StreamSource("web/xsl/sample-models.xml");
            StreamResult htmlFile = new StreamResult("web/xsl/result.html");
            
            Integer pageSize = 3;
            transformer.setParameter("pageSize", pageSize);
            
            transformer.transform(xmlFile, htmlFile);
            System.out.println("done");
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(MiscTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(MiscTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
