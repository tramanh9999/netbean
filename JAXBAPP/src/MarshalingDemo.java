
import generate.sample.Customer;
import java.io.File;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class MarshalingDemo {

    public static void main(String[] args) {

        Customer c = new Customer();
        c.setCustomersId("trammnId");
        c.setCity("hasnoi");
        c.setName("tramsnh");
        c.setPhone(new BigInteger("234232342"));
        c.setAddress("thi trans");
        try {
            JAXBContext jc = JAXBContext.newInstance(Customer.class);
            Marshaller ms = jc.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ms.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            ms.marshal(c, new File("src/customers_2.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(MarshalingDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
