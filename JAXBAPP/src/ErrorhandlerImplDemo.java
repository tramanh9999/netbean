
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class ErrorhandlerImplDemo implements ErrorHandler{

    @Override
    public void warning(SAXParseException saxpe) throws SAXException {
        System.out.println("Warning: "+ saxpe.getMessage());
    }

    @Override
    public void error(SAXParseException saxpe) throws SAXException {
        
                System.out.println("error: "+ 
                        saxpe.getLocalizedMessage()+" at line "+ saxpe.getLineNumber() 
                        +" and column "+ saxpe.getColumnNumber());
                
    }

    @Override
    public void fatalError(SAXParseException saxpe) throws SAXException {
                System.out.println("fataerror: "+ saxpe.getMessage());

        
        
    }
    
}
