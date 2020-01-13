
import com.sun.msv.verifier.util.ErrorHandlerImpl;
import generate.sample.Customer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class ValidationFrameworkDomSource {

    public static void main(String[] args) throws SAXException {
        try {
            JAXBContext jaxbc = JAXBContext.newInstance(Customer.class);
            Unmarshaller u = jaxbc.createUnmarshaller();
            SchemaFactory schemaFactory
                    = SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            Schema schema = schemaFactory.newSchema(new File("src/customers.xsd"));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ErrorhandlerImplDemo());
             
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            
            dbf.setNamespaceAware(true);
            File f = new File("src/customers.xml");
            Document doc = documentBuilder.parse(f);
            validator.validate(new DOMSource(doc));
            
            
            
            
            
            
            Customer item = (Customer) u.unmarshal(f);
            System.out.println(item);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
    }

}
