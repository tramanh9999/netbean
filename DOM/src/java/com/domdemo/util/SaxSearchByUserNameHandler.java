/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.util;

import com.domdemo.dto.StudentDTO;
import com.domdemo.dto.User;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Search list of student by address, return list<StudentDTO>
 *
 * @param searchaddress , if any std has address containing searchaddress then
 * add to list
 *
 *
 *
 */
public class SaxSearchByUserNameHandler extends DefaultHandler {

    String username;
    String password;
    User user;
    boolean bfound=false;

  

    public boolean isBfound() {
        return bfound;
    }

    public void setBfound(boolean bfound) {
        this.bfound = bfound;
    }


 

    /**
     * Store tagname by qname that cursor point at any startelement incase qname
     * is student then get id of that element
     *
     */
    @Override
    public void startElement(String uri, String localname, String qname, Attributes attributes) throws SAXException {
        if (qname.equals("user")) {
            this.user = new User();
            for (int i = 0; i < attributes.getLength(); i++) {
                if (attributes.getQName(i).equals(new QName("username"))) {
                    user.setUsername(attributes.getValue(i));
                } else if (attributes.getQName(i).equals(new QName("password"))) {
                    user.setPassword(attributes.getValue(i));
                }

            }

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                bfound = true;
            }

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void endElement(String uri, String localname, String qname) throws SAXException {
        if (qname.equals("user")) {
            if (bfound == false) {
                this.user = null;
            }
        }
    }

}
