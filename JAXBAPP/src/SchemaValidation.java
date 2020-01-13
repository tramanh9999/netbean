
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
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class SchemaValidation {
//    
//    public static void main(String[] args) {
//        try {
//            JAXBContext jaxbc= JAXBContext.newInstance(Customer.class);
//            Unmarshaller u= jaxbc.createUnmarshaller();
//            
//            SchemaFactory factory= SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema  s= factory.newSchema(new File("src/customers.xsd"));
//            
//            u.setSchema(s);
//            File f= new File("src/customers.xml");
//            
//              Customer item =  (Customer) u.unmarshal(f);
//            System.out.println(item.getCustomersId());
//            System.out.println(item.getName());
//            System.out.println(item.getPhone());
//            System.out.println(item.getAddress());
//            System.out.println(item.getCity());
//        }  catch (SAXException ex) {
//            Logger.getLogger(SchemaValidation.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(SchemaValidation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
     public static void main(String[] args) {
        try {
            JAXBContext jaxbc= JAXBContext.newInstance(Customer.class);
            Unmarshaller u= jaxbc.createUnmarshaller();
            u.setEventHandler(new ValidationDemoHandler());
            File f= new File("src/customers.xml");
            Customer c= (Customer) u.unmarshal(f);
            System.out.println(c);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }
     
    
     
     
     
}
