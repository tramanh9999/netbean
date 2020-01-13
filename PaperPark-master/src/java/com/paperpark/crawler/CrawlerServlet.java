/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler;

import com.paperpark.config.crawler.CrawlerConfig;
import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.kit168.Kit168Thread;
import com.paperpark.crawler.papercraftmuseum.MuseumThread;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author NhanTT
 */
@WebServlet(name = "CrawlerServlet", urlPatterns = {"/crawler"})
public class CrawlerServlet extends HttpServlet {

    private static final String URL = "crawler.html";
    private static final String DISABLE_URL = "crawler-disable.html";

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

        String realPath = getServletContext().getRealPath("/");
        CrawlerConfig config = CrawlerConfig.getCrawlerConfig(realPath);
        if (!config.isEnableCrawler()) {
            response.sendRedirect(DISABLE_URL);
            return;
        }

        ServletContext context = getServletContext();
        String action = request.getParameter("action");
        Kit168Thread kit168Thread = (Kit168Thread) context.getAttribute("KIT168_THREAD");
        MuseumThread museumThread = (MuseumThread) context.getAttribute("MUSEUM_THREAD");
        
        switch (action) {
            case "start":
                kit168Thread = new Kit168Thread(context);
                kit168Thread.start();
                if (ConfigConstants.DEBUG) {
                    System.out.println("DEBUG Kit168 Thread start with Id = " + kit168Thread.getId());
                }

                museumThread = new MuseumThread(context);
                museumThread.start();
                if (ConfigConstants.DEBUG) {
                    System.out.println("DEBUG Museum Thread start with Id = " + museumThread.getId());
                }
                
                break;
            case "pause":
                kit168Thread.suspendThread();
                museumThread.suspendThread();
                break;
            case "resume":
                kit168Thread.resumeThread();
                museumThread.resumeThread();
                break;
            default:
        }
        response.sendRedirect(URL);
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
