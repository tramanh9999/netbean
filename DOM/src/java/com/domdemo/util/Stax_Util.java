/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.util;

import com.domdemo.dto.Account;
import com.domdemo.dto.StudentDTO;
import com.domdemo.dto.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author ADMIN
 */
public class Stax_Util {

    /**
     * copy all data into new file then update in the searched element then keep
     * copy to new file in the end delete old file , then change name of new
     * file to name of oldones
     */
    public boolean add(StudentDTO dto, File file) {
        try {
            FileOutputStream fileOutputStream = null;

            FileInputStream fileInputStream;
            System.out.println(file.getPath() + file.getName() + ".new");
            File newFile = new File(file.getPath() + file.getName() + ".new");

            fileOutputStream = new FileOutputStream(newFile);
            fileInputStream = new FileInputStream(file);

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

            XMLEventReader eventReader = inputFactory.createXMLEventReader(fileInputStream);
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(fileOutputStream);

            JAXBContext jaxb = JAXBContext.newInstance(StudentDTO.class);
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();

            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            while (eventReader.hasNext()) {
                if (eventReader.peek().isStartDocument()
                        && eventReader.peek().asStartElement().getName()
                        .getLocalPart().equals("student")) {
                    StudentDTO o = (StudentDTO) unmarshaller.unmarshal(eventReader);
                    if (o.getId().equals(dto.getId())) {
                        o.setAddress(dto.getAddress());
                        o.setFirstname(dto.getFirstname());
                        o.setMiddlename(dto.getMiddlename());
                        o.setLastname(dto.getLastname());
                        o.setSex(dto.getSex());
                        o.setStatus(dto.getStatus());
                    }

                    marshaller.marshal(o, eventWriter);

                } else {
                    eventWriter.add(eventReader.nextEvent());
                }

            }

            eventWriter.flush();
            eventWriter.close();

            fileInputStream.close();
            fileOutputStream.close();
            String path = file.getPath() + file.getName();
            file.delete();

            file = null;

            newFile.renameTo(new File(path));
            return true;

        } catch (XMLStreamException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public static List<User> readListUser(File xmlFile) {
        try {
            List<User> listStudents = new ArrayList<>();
            User student = null;
            String tagContent = null;

            InputStream is;
            is = new FileInputStream(xmlFile);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(is);

            // duyệt các phần tử student
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        // tạo đối tượng student
                        if ("user".equals(reader.getLocalName())) {
                            User student1 = new User();
                            int countAttr = reader.getAttributeCount();
                            for (int i = 0; i < countAttr; i++) {
                                if (reader.getAttributeName(i).equals(new QName("username"))) {
                                    student1.setUsername(reader.getAttributeValue(i));
                                } else if (reader.getAttributeName(i).equals(new QName("password"))) {
                                    student1.setPassword(reader.getAttributeValue(i));
                                }
                            }
                            listStudents.add(student1);
                        }

                }
            }
            return listStudents;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    List<StudentDTO> searchStudentUsingStax(String search) {
        List<StudentDTO> studentDTOs = null;
        return studentDTOs;

    }

    public XMLEventReader parseFileToReader(File file) {
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(file));
            return eventReader;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

//    public StudentDTO findById(String id, File f) {
//
//    }
    public Account findAccountUsingCursorAPIStax(String username, String password, File file) {
        try {
            List<Account> listStudents = new ArrayList<>();
            Account user = null;
            String tagContent = null;

            InputStream is;
            is = new FileInputStream(file);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(is);
String content;
            // duyệt các phần tử student
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    // tạo đối tượng student
                    StartElement se = event.asStartElement();
                    if ("customer".equals(se.getName().getLocalPart())) {
                        user = new Account();

                    } else if (event.isCharacters()) {
                        
                        Characters characters=  event.asCharacters();
                        content= 
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        User user = new Stax_Util().findUserUsingIteratorAPIStax("tomcat", "tomcat", new File("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\DOM\\build\\web\\WEB-INF\\tomcat-users.xml"));
        System.out.println(user);
    }
    boolean found = false;

    public User findUserUsingCursorAPIStax(String username, String password, File file) {
        try {
            List<User> listStudents = new ArrayList<>();
            User user = null;
            String tagContent = null;

            InputStream is;
            is = new FileInputStream(file);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(is);

            // duyệt các phần tử student
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        // tạo đối tượng student
                        if ("user".equals(reader.getLocalName())) {
                            user = new User();
                            int countAttr = reader.getAttributeCount();

                            //itera
                            for (int i = 0; i < countAttr; i++) {
                                if (reader.getAttributeName(i).equals(new QName("username"))) {
                                    user.setUsername(reader.getAttributeValue(i));
                                } else if (reader.getAttributeName(i).equals(new QName("password"))) {
                                    user.setPassword(reader.getAttributeValue(i));

                                }
                            }
                            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                                return user;
                            }

                        }

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User findUserUsingIteratorAPIStax(String username, String password, File file) {
        try {
            List<User> listStudents = new ArrayList<>();
            User user = null;
            String tagContent = null;

            InputStream is;
            is = new FileInputStream(file);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(is);
            // duyệt các phần tử student
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if ("user".equals(startElement.getName().getLocalPart())) {
                        user = new User();
                        Iterator attributes = startElement.getAttributes();

                        while (attributes.hasNext()) {
                            Attribute attribute = (Attribute) attributes.next();
                            QName qname = attribute.getName();

                            if (qname.getLocalPart().equals("username")) {

                                user.setUsername(attribute.getValue());

                            } else if (qname.getLocalPart().equals("password")) {
                                user.setPassword(attribute.getValue());

                            }

                        }
                        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                            return user;
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Stax_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
