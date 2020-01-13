
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import thanhpc.jaxb.app.Customer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class UnmarshalingDemo {

    public static void main(String[] args) {
        try {
            JAXBContext jaxbc = JAXBContext.newInstance(Customer.class);
            Unmarshaller um = jaxbc.createUnmarshaller();
            File f = new File("customer.xml");
            Customer cus = (Customer) um.unmarshal(f);
            
        } catch (JAXBException ex) {
            Logger.getLogger(UnmarshalingDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
