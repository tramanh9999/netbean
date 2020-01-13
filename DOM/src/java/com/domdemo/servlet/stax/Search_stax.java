/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.servlet.stax;

import com.domdemo.dto.StudentDTO;
import com.domdemo.dto.User;
import com.domdemo.util.Sax_util;
import com.domdemo.util.Stax_Util;
import com.domdemo.util.XMLUtility;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Search_stax", urlPatterns = {"/search_stax"})
public class Search_stax extends HttpServlet {

    final String xmlFile = "/WEB-INF/student_account.xml";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String address = request.getParameter("txtadd").trim();
        String realPath = getServletContext().getRealPath("/");
        String filepath = realPath + xmlFile;
        Sax_util xMLSAXUtility= new Sax_util();
        List<StudentDTO> sdtos= xMLSAXUtility.findStudentListByAddress(address, new File(filepath));
        request.setAttribute("INFO", sdtos);
        request.getRequestDispatcher("student.jsp").forward(request, response);
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
         response.setContentType("text/html;charset=UTF-8");
        String us = request.getParameter("txtadd").trim();
        String realPath = getServletContext().getRealPath("/");
        String filepath = realPath + xmlFile;
        Sax_util xMLSAXUtility= new Sax_util();
        User user= xMLSAXUtility.findUser(us);
        if(user!= null){
            
        }
        request.getRequestDispatcher("student.jsp").forward(request, response);
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
