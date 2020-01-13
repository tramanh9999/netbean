
import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
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
public class customerJavaObject {

    public static void main(String[] args) {
        try {
            String output = "src/";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(SAXParseException saxpe) {
                    System.out.println("error:" + saxpe);
                }
                
                @Override
                public void fatalError(SAXParseException saxpe) {
                    System.out.println("error:" + saxpe);
                    
                }
                
                @Override
                public void warning(SAXParseException saxpe) {
                    System.out.println("error:" + saxpe);
                    
                }
                
                @Override
                public void info(SAXParseException saxpe) {
                    System.out.println("error:" + saxpe);
                    
                }
            });
            sc.forcePackageName("thanhpc.jaxb.app");
            
            File schema = new File("customer.xsd");
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
        } catch (IOException ex) {
            Logger.getLogger(customerJavaObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
