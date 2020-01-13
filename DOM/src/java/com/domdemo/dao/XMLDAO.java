/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.dao;

import com.domdemo.dto.StudentDTO;
import com.domdemo.util.XMLUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ADMIN
 */
public class XMLDAO {

    public StudentDTO convertNodeToStudentDTO(NodeList elementInStudent) {
        StudentDTO sdto = new StudentDTO();
        for (int i = 0; i < elementInStudent.getLength(); i++) {
            Node node = elementInStudent.item(i);
            String nodeName = node.getNodeName();
            if (nodeName.equals("firstname")) {
                sdto.setFirstname(node.getTextContent().trim());
            } else if (nodeName.equals("middlename")) {
                sdto.setMiddlename(node.getTextContent().trim());

            } else if (nodeName.equals("lastname")) {
                sdto.setLastname(node.getTextContent().trim());

            } else if (nodeName.equals("sex")) {
                sdto.setSex(node.getTextContent().trim());

            } else if (nodeName.equals("address")) {
                sdto.setAddress(node.getTextContent().trim());

            } else if (nodeName.equals("status")) {
                sdto.setStatus(node.getTextContent().trim());

            }

        }

        return sdto;
    }

    public static void main(String[] args) {

        XPath xp = XMLUtility.createXPath();
        String exp = "//student[@id=1]";
        Document doc = XMLUtility.parseFileToDOM("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\DOM\\web\\WEB-INF\\student_account.xml");
        try {
            Node x = (Node) xp.evaluate(exp, doc, XPathConstants.NODE);
            System.out.println(x.getChildNodes());

        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<StudentDTO> findListStudentByAddress(Document doc, String searchAdd) {
        if (doc != null) {
            try {
                XPath xp = XMLUtility.createXPath();
                String exp = "//student[contains(address,'" + searchAdd + "')]";

                xp.compile(exp);
                NodeList studentNodeList = (NodeList) xp.evaluate(exp, doc, XPathConstants.NODESET);

                if (studentNodeList != null) {
                    List<StudentDTO> listStudent = new ArrayList<>();
                    for (int i = 0; i < studentNodeList.getLength(); i++) {

                        // in ra thong tin cua 1 node
                        Node temNode = studentNodeList.item(i);
//                        System.out.println(temNode.cloneNode(true));

                        Node node = studentNodeList.item(i);

                        StudentDTO sdto = new StudentDTO();

                        NamedNodeMap attrs = node.getAttributes();

                        // set bvalue
                        sdto.setId(attrs.getNamedItem("id").getNodeValue());

                        NodeList children = node.getChildNodes();
                        for (int j = 0; j < children.getLength(); j++) {
                            Node tmp = children.item(j);
                            if (tmp.getNodeName().equals("firstname")) {
                                sdto.setFirstname(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("middlename")) {
                                sdto.setMiddlename(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("lastname")) {
                                sdto.setLastname(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("address")) {
                                sdto.setAddress(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("sex")) {
                                sdto.setSex(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("status")) {
                                sdto.setStatus(tmp.getTextContent().trim());
                            }

                        }

                        listStudent.add(sdto);

                    }
                    return listStudent;
                }
            } catch (XPathExpressionException ex) {
                Logger.getLogger(XMLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public StudentDTO findStudentById(Document doc, String id) {
        if (doc != null) {
            try {
                String exp = "//student[@id=" + id + "]";
                XPath xPath = XMLUtility.createXPath();
                xPath.compile(exp);

                Element searchStudentNode = (Element) xPath.evaluate(exp, doc, XPathConstants.NODE);

                if (searchStudentNode == null) {
                    return null;
                } else {
                    // convert node to StudentDTO
                    StudentDTO convertNodeToStudentDTO = convertNodeToStudentDTO(searchStudentNode.getChildNodes());
                    return convertNodeToStudentDTO;
                }
            } catch (XPathExpressionException ex) {
                Logger.getLogger(XMLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;

    }

    boolean updateNode(Document doc, StudentDTO dto) {

        XPath xPath = XMLUtility.createXPath();
//
//        Node searchNode = finD(doc, dto.getId());
//        String exp = "//student"
//        
//        
        
        return true;
    }

}
