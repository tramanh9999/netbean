/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jaxb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author ADMIN
 */
public class JAXBUnmarshal {
    public static void main(String[] args) {
        try {
            JAXBContext jAXBContext=  JAXBContext.newInstance("sample.jaxb");
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBUnmarshal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
