/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.test;

import com.paperpark.dao.model.ModelDAO;
import com.paperpark.entity.Model;
import com.paperpark.entity.Tag;
import com.paperpark.models.ModelList;
import com.paperpark.utils.JAXBUtils;
import java.util.ArrayList;
import java.util.List;
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
public class MiscTest {
    
    private static final String realPath = "E:\\Java\\Summer_2019\\PaperPark\\web\\";

    public static void main(String[] args) {
        String s = "hello&amp;#1345;s world&amp;#3544;";
        s = s.replace("&amp;", "&");
        System.out.println(s);
    }
}
