/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.servlet;

import com.paperpark.dao.model.ModelDAO;
import com.paperpark.entity.Model;
import com.paperpark.models.ModelList;
import com.paperpark.utils.JAXBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author NhanTT
 */
@WebServlet(name = "SearchModelServlet", urlPatterns = {"/searchModel"})
public class SearchModelServlet extends HttpServlet {

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
        response.setContentType("text/xml;charset=UTF-8");

        String modelName = request.getParameter("modelName");

        try (PrintWriter out = response.getWriter()) {
            ModelDAO modelDAO = ModelDAO.getInstance();

            HttpSession session = request.getSession();
            Integer skillLevel = (Integer) session.getAttribute("SKILL_LEVEL");

            List<Model> models;
            if (skillLevel != null) {
                List<Model> allModels = modelDAO.getAllModels(session, skillLevel);
                models = new ArrayList<>();
                allModels.forEach((model) -> {
                    if (model.getName().toLowerCase().contains(modelName.toLowerCase())) {
                        models.add(model);
                    }
                });
            } else {
                models = modelDAO.getModelsByName(modelName);
            }

            ModelList resultModels = new ModelList();
            resultModels.setModelList(models);

            String resultModelsXml = JAXBUtils.marshall(resultModels, resultModels.getClass());

            out.write(resultModelsXml);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            Logger.getLogger(SearchModelServlet.class.getName())
                    .log(Level.SEVERE, null, e);
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
