
import generate.sampleperson.PersonType;
import generate.sampleperson.Persons;
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
public class MarshalingDemo2 {

    public static void main(String[] args) {
        try {
            PersonType person = new PersonType();
            person.setPersonId("12344");
            person.setCity("ha noi");
            person.setName("tram anh");
            person.setPhone(new BigInteger("24224512"));
            person.setAddress("phutho");
            
            PersonType person1 = new PersonType();
            person1.setPersonId("435234");
            person1.setCity("hcm");
            person1.setName("ha nhi");
            person1.setPhone(new BigInteger("2384812843"));
            person1.setAddress("thi tran song thao");
            Persons p = new Persons();
            p.getPerson().add(person);
            p.getPerson().add(person1);
            
            
            
            
            JAXBContext jaxbc = JAXBContext.newInstance(Persons.class);
            Marshaller ms = jaxbc.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            ms.marshal(p, new File("src/marshalerPersons.xml"));
            
        } catch (JAXBException ex) {
            Logger.getLogger(MarshalingDemo2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
