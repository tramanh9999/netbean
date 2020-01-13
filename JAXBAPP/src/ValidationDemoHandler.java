
import generate.sample.Customer;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class ValidationDemoHandler implements ValidationEventHandler {

    @Override
    public boolean handleEvent(ValidationEvent ve) {
        System.out.println("sevierity:"+ve.getSeverity());   
        if (ve.getSeverity() == ValidationEvent.FATAL_ERROR
                || ve.getSeverity() == ValidationEvent.ERROR) {
            ValidationEventLocator locator = ve.getLocator();
            System.out.println("Invalid booking document: " + locator.getURL());
            System.out.println("Error: " + ve.getMessage());
            System.out.println("Error at column: " + locator.getColumnNumber() + " and " + locator.getLineNumber());
        }
        return true;
    }
}
