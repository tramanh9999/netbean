package tramanh.stax.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
public class RegisterDAO {

    public String findByName(String search) {
        String result = null;

        try {

            Class.forName();
            String sql = "select username, password, fullname, role from Registration\n"
                    + "for XML Path('account'), root('accounts')";

            String connstring = "jdbc:sqlserver://localhost:1433; databaseName=PaperPark";

            String user = "sa";
            String password = "123456";

            Connection connection = DriverManager.getConnection(connstring, user, password);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "%"+ search+"%");

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString(1);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }
    
    
    
    
    
}
