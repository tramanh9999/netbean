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
@WebServlet(name = "SuggestModelServlet", urlPatterns = {"/suggestModel"})
public class SuggestModelServlet extends HttpServlet {

    private static final String URL = "searchResult.jsp";

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

        String skillLevelStr = request.getParameter("skillLevel");
        String difficultyStr = request.getParameter("difficulty");
        String totalHoursStr = request.getParameter("totalHours");

        try (PrintWriter writer = response.getWriter()) {
            int skillLevel = Integer.parseInt(skillLevelStr);
            int difficulty = Integer.parseInt(difficultyStr);
            double totalHours = Double.parseDouble(totalHoursStr);

            ModelDAO modelDAO = ModelDAO.getInstance();
            
            HttpSession session = request.getSession();
            List<Model> models = modelDAO.getAllModels(session, skillLevel);

            List<Model> foundModels = new ArrayList<>();
            models.forEach((model) -> {
                if ((model.getDifficulty() + 1) / 2 == difficulty
                        && model.getEstimateTime() <= totalHours) {
                    foundModels.add(model);
                }
            });

            ModelList resultModels = new ModelList();
            resultModels.setModelList(foundModels);

            String resultModelsXml = JAXBUtils.marshall(resultModels, resultModels.getClass());

            writer.write(resultModelsXml);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            Logger.getLogger(SuggestModelServlet.class.getName())
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
