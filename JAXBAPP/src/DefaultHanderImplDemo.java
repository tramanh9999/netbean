
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
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
public class DefaultHanderImplDemo  extends DefaultHandler{

    @Override
    public void fatalError(SAXParseException saxpe) throws SAXException {
        
        System.out.println("fatal error: "+ saxpe.getMessage());

    }

    @Override
    public void error(SAXParseException saxpe) throws SAXException {
        System.out.println("error: "+ saxpe.getMessage());
        
    }

    @Override
    public void warning(SAXParseException saxpe) throws SAXException {
                System.out.println("waring: "+ saxpe.getMessage());

    }
    
    
    
    
}
