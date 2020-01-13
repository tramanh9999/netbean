/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tramanh.restful.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMI
 */
public class RegistrationDAO {

    public List<RegistrationDTO> findByLikeName(String search) {
        Connection com = null;
        try {
            String connString = "jdbc:sqlserver://localhost:1433;databaseName=PaperPark";
            String username = "sa";
            String password = "123456";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            com = DriverManager.getConnection(connString, username, password);
            String sql = "select username, fullname, role from Registration where fullname like ?";
            PreparedStatement pre = com.prepareStatement(sql);

            pre.setString(1, "%" + search + "%");

            ResultSet rs = pre.executeQuery();
            ArrayList<RegistrationDTO> result = new ArrayList<RegistrationDTO>();

            while (rs.next()) {
                String us = rs.getString("username");
                String fullname = rs.getString("fullname");
                String role = rs.getString("role");
                RegistrationDTO dto = new RegistrationDTO();
                dto.setFullname(fullname);
                dto.setUsername(us);
                dto.setRole(role);
                result.add(dto);
            }

            return result;

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
             ex.printStackTrace();
        }
        return null;
    }

    public String checkLogin(String us, String pass) {
        String role = "faild";

        try {
            String connString = "jdbc:sqlserver://localhost:1456;databaseName=PaperPark";
            String username = "sa";
            String password = "123456";
            String sql = "select Role from Registration";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServer");
            Connection com = DriverManager.getConnection(connString, username, password);
            PreparedStatement preparedStatement = com.prepareStatement(sql);
            preparedStatement.setString(1, us);
            preparedStatement.setString(2, pass);
            role = "success";
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;

    }
}
