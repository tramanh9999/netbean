/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jaxb;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author ADMIN
 */
public class XJCGenerateJavaObj {

    public static void main(String[] args) {
        FileInputStream is = null;
        try {
            String output = "/src/com/domdemo/dto";
            SchemaCompiler compiler = XJC.createSchemaCompiler();
            compiler.setErrorListener(new ErrorListener() {
                
                @Override
                public void error(SAXParseException saxpe) {
                    System.out.println("error" + saxpe.getMessage());
                }
                
                @Override
                public void fatalError(SAXParseException saxpe) {
                    System.out.println("fatal error:" + saxpe.getMessage());
                    
                }
                
                @Override
                public void warning(SAXParseException saxpe) {
                    System.out.println("warn" + saxpe.getMessage());
                    
                }
                
                @Override
                public void info(SAXParseException saxpe) {
                    System.out.println("info" + saxpe.getMessage());
                    
                }
            }); compiler.forcePackageName("generate.sample");
            File schema = new File("schema.xsd");
            is = new FileInputStream(schema);
            InputSource inputSource= new InputSource(is);
            compiler.parseSchema(inputSource);
            
            S2JJAXBModel code= compiler.bind();
            JCodeModel codeModel= code.generateCode(null, null);
            codeModel.build(new File(output));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XJCGenerateJavaObj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XJCGenerateJavaObj.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(XJCGenerateJavaObj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
