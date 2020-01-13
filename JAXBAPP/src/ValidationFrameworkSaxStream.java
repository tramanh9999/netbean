
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
import javax.xml.transform.Source;
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
public class ValidationFrameworkSaxStream {

    public static void main(String[] args) {
        try {
            JAXBContext jaxbc = JAXBContext.newInstance(Customer.class);
            Unmarshaller u = jaxbc.createUnmarshaller();
            SchemaFactory schemaFactory
                    = SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/customers.xsd"));

            Validator validator = schema.newValidator();

            InputSource inputFile
                    = new InputSource(new BufferedReader(new FileReader("src/customers.xml")));

            validator.validate(new SAXSource(inputFile));

            File f = new File("src/customers.xml");
            Customer item = (Customer) u.unmarshal(f);

            System.out.println(item);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            System.out.println("XSD occur error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
