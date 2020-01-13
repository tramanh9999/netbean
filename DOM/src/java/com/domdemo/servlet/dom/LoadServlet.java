/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.servlet.dom;

import com.domdemo.db.StudentService;
import com.domdemo.dto.StudentDTO;
import com.domdemo.util.XMLUtility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ADMIN
 */
public class LoadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final String xmlFile = "/WEB-INF/student_account.xml";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String realPath = getServletContext().getRealPath("/");
        String filepath = realPath + xmlFile;
        Document doc = XMLUtility.parseFileToDOM(filepath);

        XPath xp = XMLUtility.createXPath();
        String exp = "//student";
        try {
            xp.compile(exp);

            NodeList studentNodeList = (NodeList) xp.evaluate(exp, doc, XPathConstants.NODESET);

            if (studentNodeList != null) {
                List<StudentDTO> listStudent = new ArrayList<>();
                for (int i = 0; i < studentNodeList.getLength(); i++) {

                    // in ra thong tin cua 1 node
                    Node temNode = studentNodeList.item(i);
//                        System.out.println(temNode.cloneNode(true));

                    Node node = studentNodeList.item(i);

                    StudentDTO sdto = new StudentDTO();

                    NamedNodeMap attrs = node.getAttributes();

                    // set bvalue
                    sdto.setId(attrs.getNamedItem("id").getNodeValue());

                    NodeList children = node.getChildNodes();
                    for (int j = 0; j < children.getLength(); j++) {
                        Node tmp = children.item(j);
                        if (tmp.getNodeName().equals("firstname")) {
                            sdto.setFirstname(tmp.getTextContent().trim());
                        } else if (tmp.getNodeName().equals("middlename")) {
                            sdto.setMiddlename(tmp.getTextContent().trim());
                        } else if (tmp.getNodeName().equals("lastname")) {
                            sdto.setLastname(tmp.getTextContent().trim());
                        } else if (tmp.getNodeName().equals("address")) {
                            sdto.setAddress(tmp.getTextContent().trim());
                        } else if (tmp.getNodeName().equals("sex")) {
                            sdto.setSex(tmp.getTextContent().trim());
                        } else if (tmp.getNodeName().equals("status")) {
                            sdto.setStatus(tmp.getTextContent().trim());
                        }

                    }

                    listStudent.add(sdto);

                }

                StudentService studentService = new StudentService();
//                boolean isInsertToDb = studentService.insertAll(listStudent);
                request.setAttribute("INFO", listStudent);
                request.getRequestDispatcher("search.jsp").forward(request, response);
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(LoadServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
