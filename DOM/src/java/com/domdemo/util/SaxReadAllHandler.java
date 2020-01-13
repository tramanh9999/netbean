/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.util;

import com.domdemo.dto.StudentDTO;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.events.StartElement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ADMIN
 */
public class SaxReadAllHandler extends DefaultHandler {

    String content;
    StudentDTO studentDTO;
    List<StudentDTO> sdtos = new ArrayList<>();

    public SaxReadAllHandler() {
    }

    @Override
    public void characters(char[] chars, int start, int length) {

        content = String.copyValueOf(chars, start, length).trim();

    }

    public List<StudentDTO> getSdtos() {
        return sdtos;
    }

    public void setSdtos(List<StudentDTO> sdtos) {
        this.sdtos = sdtos;
    }

    @Override
    public void endElement(String uri, String localname, String qname){
        switch (qname) {

            case "student":
                sdtos.add(studentDTO);
                break;
            case "address":
                studentDTO.setAddress(content);
                break;
            case "firstname":
                studentDTO.setFirstname(content);
                break;
            case "lastname":
                studentDTO.setLastname(content);
                break;
            case "middlename":
                studentDTO.setMiddlename(content);
                break;
            case "sex":
                studentDTO.setSex(content);
                break;
            case "status":
                studentDTO.setStatus(content);
                break;
        }
    }

    
    @Override
    public void startElement(String uri, String localname, String qname, Attributes atrbts) throws SAXException {
        if (qname.equalsIgnoreCase("student")) {
            studentDTO = new StudentDTO();
            studentDTO.setId(atrbts.getValue("id"));
        }

    }

}
