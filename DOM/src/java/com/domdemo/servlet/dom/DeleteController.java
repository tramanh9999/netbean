/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.servlet.dom;

import com.domdemo.util.XMLUtility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "DeleteController", urlPatterns = {"/delete"})
public class DeleteController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static final String ERROR = "error.jsp";
    static final String SUCCESS = "/search.jsp";
    static final String xmlFile = "/WEB-INF/student_account.xml";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URL = ERROR;
        try {

            String id = request.getParameter("id");
            String realPath = getServletContext().getRealPath("/");
            String filepath = realPath + xmlFile;
            Document doc = XMLUtility.parseFileToDOM(filepath);

            if (doc != null) {
                XPath xPath = XMLUtility.createXPath();
                String exp = "//student[@id='" + id + "']";
                Node student = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
                if (student != null) {
                    Node parent = student.getParentNode();
                    // huy la huy tren dom ,nen
                    //ghi  lai cay dom len file

                    parent.removeChild(student);

                    boolean result = XMLUtility.tranformDOMToXMLFile(doc, filepath);
                    if (result) {
                        request.getRequestDispatcher("student.jsp").forward(request, response);

                    } else {
                        request.setAttribute("ERROR", "Delete Failed");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
