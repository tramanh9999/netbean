/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
package com.anhtht.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XML_Stax {

    
    public static void main(String[] args) {
        System.out.println( new XML_Stax().all().size());
    }
    List<Student> all() {
        InputStream is = null;
        List<Student> listStudents = new ArrayList<>();

        try {
            Student student = null;
            String tagContent = null;
            File inputFile = new File("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\PE_Exam\\web\\WEB-INF\\students.xml");
            is = new FileInputStream(inputFile);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(is);
            // duyệt các phần tử student
            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        // tạo đối tượng student
                        if ("student".equals(reader.getLocalName())) {
                            student = new Student();
                            student.setId(reader.getAttributeValue(0));
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case "student":
                                listStudents.add(student);
                                break;
                            case "firstname":
                                student.setFirstName(tagContent);
                                break;
                            case "lastname":
                                student.setLastName(tagContent);
                                break;
                            case "marks":
                                student.setMarks(tagContent);
                                break;
                        }
                        break;
                }
            }
            return listStudents;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XML_Stax.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XML_Stax.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(XML_Stax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listStudents;
    }

}
