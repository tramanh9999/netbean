/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.servlet.dom;

import com.domdemo.dao.XMLDAO;
import com.domdemo.db.StudentService;
import com.domdemo.dto.StudentDTO;
import static com.domdemo.servlet.dom.DeleteController.SUCCESS;
import com.domdemo.util.XMLUtility;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ADMIN
 */
public class UpdateController extends HttpServlet {

    private String URL = "error.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
    final String xmlFile = "/WEB-INF/student_account.xml";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        Document doc = getDom(xmlFile);

        XMLDAO dao = new XMLDAO();

        if (dao != null) {

            try {
                XPath xp = XMLUtility.createXPath();

                String exp = "//student[@id='" + id + "']";

                xp.compile(exp);
                Node searchNode = (Node) xp.evaluate(exp, doc, XPathConstants.NODE);

                if (searchNode == null) {
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                } else {
                    NodeList studentAttrs = searchNode.getChildNodes();
                    StudentDTO sdto = dao.convertNodeToStudentDTO(studentAttrs);
                    sdto.setId(searchNode.getAttributes().getNamedItem("id").getTextContent());
                    request.setAttribute("INFO", sdto);
                    request.getRequestDispatcher("update_student.jsp").forward(request, response);

                }
            } catch (XPathExpressionException ex) {
                Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public Document getDom(String xmlFilePath) {
        String realpath = getServletContext().getRealPath("/");
        String filePath = realpath + xmlFile;
        Document doc = XMLUtility.parseFileToDOM(filePath);
        return doc;
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

        String id = request.getParameter("txtid");
        String firstname = request.getParameter("txtfn");
        String middlename = request.getParameter("txtmn");
        String lastname = request.getParameter("txtln");
        String sex = request.getParameter("txtsex");
        String address = request.getParameter("txtadd");
        String status = request.getParameter("status");
        //find in db
        StudentService studentService = new StudentService();
        StudentDTO studenDb = studentService.findById(id);
        if (studenDb != null) {
            //update date to db
            StudentDTO updatedStudent = new StudentDTO();
            updatedStudent.setId(id);
            updatedStudent.setFirstname(firstname);
            updatedStudent.setLastname(lastname);
            updatedStudent.setMiddlename(middlename);
            updatedStudent.setLastname(lastname);
            updatedStudent.setSex(sex);
            updatedStudent.setStatus(status);
            boolean updated = studentService.update(updatedStudent);
            if (updated == true) {
                // update to xml file 
//        StudentDTO sdto = new StudentDTO();
//        sdto.setAddress(address);
//        sdto.setFirstname(firstname);
//        sdto.setLastname(lastname);
//        sdto.setSex(sex);
//        sdto.setMiddlename(middlename);
                Document doc = getDom(xmlFile);
                XMLDAO dao = new XMLDAO();
                if (doc != null) {
                    try {
                        XPath xPath = XMLUtility.createXPath();
                        String expression = "//student[@id='" + id + "']";
                        xPath.compile(expression);
                        Node node = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
                        NodeList nodeList = node.getChildNodes();
                        // update date note
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Node temp = nodeList.item(i);
                            String nodeName = temp.getNodeName();
                            if (nodeName.equals("firstname")) {
                                temp.setTextContent(firstname);
                            } else if (nodeName.equals("middlename")) {
                                temp.setTextContent(middlename);
                            } else if (nodeName.equals("lastname")) {
                                temp.setTextContent(lastname);
                            } else if (nodeName.equals("sex")) {
                                temp.setTextContent(sex);
                            } else if (nodeName.equals("address")) {
                                temp.setTextContent(address);
                            }
                        }
                        String realpath = getServletContext().getRealPath("/");
                        String filePath = realpath + xmlFile;
                        boolean result = XMLUtility.tranformDOMToXMLFile(doc, filePath);
                        if (result) {
                            request.getRequestDispatcher("student.jsp").forward(request, response);
                        } else {
                            request.setAttribute("ERROR", "Delete Failed");
                        }
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
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
