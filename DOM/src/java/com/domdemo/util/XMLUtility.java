/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ADMIN
 */
public class XMLUtility implements Serializable {

    static int count = 0;

    public static void removeBreakLineNode(Node parentNode) {

        NodeList stNodeList = parentNode.getChildNodes();
        System.out.println(stNodeList.getLength());
        for (int i = 0; i < stNodeList.getLength(); i++) {
            Node n = stNodeList.item(i);

            removeBreakLineNode(n);
            System.out.println("no." + i + ": " + n.getNodeName() + ": " + n.getTextContent());
            String textContent = n.getTextContent();
            String regex = "\\s+";
            if (textContent.matches(regex)) {
                parentNode.removeChild(n);
                i--;
            }
        }

//        System.out.println("End node:" + parentNode.getAttributes().getNamedItem("name").getNodeValue());
//        System.out.println("after remove students have: "+ doc.getFirstChild().getChildNodes().get);
    }

    public static void main(String[] args) {

        String filepath = "C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\DOM\\web\\WEB-INF\\student_account_1_1.xml";
        Document doc = XMLUtility.parseFileToDOM(filepath);

        Node studentsNode = doc.getFirstChild();
        NodeList studentNodeList = studentsNode.getChildNodes();

//        XMLUtility.removeBreakLineNode(doc);
//        sNodeList = doc.getFirstChild().getChildNodes();
//
//        System.out.println("test the first student of node students, number of element: " + doc.getFirstChild().getFirstChild().getChildNodes().getLength());
//        
//        System.out.println("test phan tu sau khi xoa:");
//        printElement(doc.getFirstChild().getFirstChild());
        printChildElement(doc);

    }

    public static void printChildElement(Node parNode) {
        NodeList nodeList = parNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            printChildElement(childNode);
            System.out.println("No." + i + ":" + childNode.getNodeName() + "=" + childNode.getTextContent());
            System.out.println("    nodeType:" + printNodeTypeName(childNode.getNodeType()));

        }
    }

    public static String printNodeTypeName(short typeNum) {

        switch (typeNum) {
            case 1:
                return "ELEMENT_NODE";

            case 2:
                return "ATTRIBUTE_NODE";

            case 3:
                return "TEXT_NODE";

            case 4:
                return "CDATA_SECTION_NODE";

            case 5:
                return "ENTITY_REFERENCE_NODE";

            case 6:
                return "ENTITY_NODE";

            case 7:
                return "PROCESSING_INSTRUCTION_NODE";

            case 8:
                return "COMMENT_NODE";

            case 9:
                return "DOCUMENT_NODE";

            case 10:
                return "DOCUMENT_TYPE_NODE";

            case 11:
                return "DOCUMENT_FRAGMENT_NODE";

            case 12:
                return "NOTATION_NODE";

        }
        return "undefind";
    }

    public static Document parseFileToDOM(String fileName) {

        try {
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            doc = db.parse(file);
            return doc;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static XPath createXPath() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        return xPath;
    }

    public static boolean tranformDOMToXMLFile(Node root, String filePath) {

        Source src = new DOMSource(root);
        File f = new File(filePath);
        Result result = new StreamResult(f);

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer t = tf.newTransformer();
            t.transform(src, result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
