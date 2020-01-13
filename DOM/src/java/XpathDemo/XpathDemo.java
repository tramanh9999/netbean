/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XpathDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ADMIN
 */
public class XpathDemo {

    public static void main(String[] args) throws ParserConfigurationException, FileNotFoundException, XPathExpressionException, SAXException, IOException {
        DocumentBuilderFactory dcBuilderFactory = DocumentBuilderFactory.newInstance();
        dcBuilderFactory.setNamespaceAware(true);
        DocumentBuilder newDocumentBuilder = dcBuilderFactory.newDocumentBuilder();

        String expression = "//users/user:*";
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath newXPath = xPathFactory.newXPath();
        String filePath = "D:\\x.xml";
        File file = new File(filePath);
        Document newDocument = newDocumentBuilder.parse(file);
        NodeList evaluate = (NodeList) newXPath.evaluate(expression, newDocument, XPathConstants.NODESET);
        for (int i = 0; i < evaluate.getLength(); i++) {
            System.out.println(i + ":" + evaluate.item(i).getAttributes().getNamedItem("username"));
        }

    }

}
