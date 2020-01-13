
import generate.sample.Customer;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
            JAXBContext jc = JAXBContext.newInstance(Customer.class);
            Unmarshaller u = jc.createUnmarshaller();
            File f = new File("src/customers.xml");
            Customer item =  (Customer) u.unmarshal(f);
            System.out.println(item.getCustomersId());
            System.out.println(item.getName());
            System.out.println(item.getPhone());
            System.out.println(item.getAddress());
            System.out.println(item.getCity());

        } catch (JAXBException ex) {
            Logger.getLogger(UnmarshalingDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
