/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anh.leavesearch.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchController", urlPatterns = {"/search"})
public class SearchController extends HttpServlet {

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
        
                    String result="";

        try {
            response.setContentType("text/html;charset=UTF-8");
            Connection connection = null;
            ResultSet resultSet = null;
            String sql = "select username, fullname,role from registration where fullname like ? for XML Path('account', Root('accounts'))";

            String connectionString = "jdbc:sqlserver://localhost;1456;databaseName=PaperPark";

            Class.forName("com.microsoft.sqlserver.jdbc.SqlServewrDriver");

            connection = DriverManager.getConnection(connectionString, "sa", "123456");

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String search = request.getParameter("txtsearch");

            preparedStatement.setString(1, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getString(1);

                request.setAttribute("INFO", resultSet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FirstController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FirstController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           response.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
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
