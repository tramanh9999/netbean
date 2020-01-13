/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.db;

import com.domdemo.dto.StudentDTO;
import com.domdemo.dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class StudentService {

    Connection connect;

    public StudentService() {
        connect = DbConnect.getConnection();
    }

    public boolean insert(StudentDTO dto) {

        if (dto.getStatus().contains("block")) {
            return false;
        } else {
            String sql = "insert into Student(id, firstname,lastname,middlename,sex,status,address) values(?,?,?,?,?,?,?)";
            PreparedStatement pre = null;
            try {
                connect.setAutoCommit(false);

                pre = connect.prepareStatement(sql);
                pre.setString(1, dto.getId());
                pre.setString(2, dto.getFirstname());
                pre.setString(3, dto.getLastname());
                pre.setString(4, dto.getMiddlename());
                pre.setString(5, dto.getSex());
                pre.setString(6, dto.getStatus());
                pre.setString(7, dto.getAddress());
                pre.addBatch();

                pre.executeBatch();
                connect.commit();
            } catch (SQLException ex) {
                Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);

                try {
                    connect.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex1);
                }
                return false;

            }

            return true;
        }

    }

    public boolean insertAll(List<User> sdtos) {

        String sql = "insert into users(user_name, password) values(?,?)";
        PreparedStatement pre = null;
        try {
            connect.setAutoCommit(false);
            for (int i = 0; i < sdtos.size(); i++) {
                User dto = sdtos.get(i);
                pre = connect.prepareStatement(sql);
                pre.setString(1, dto.getUsername());
                pre.setString(2, dto.getPassword());
                pre.addBatch();
            }
            pre.executeBatch();
            connect.commit();
        } catch (SQLException ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);

            try {
                connect.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;

        }
        return true;

    }

    public StudentDTO findById(String id) {

        String sql = "select  id, firstname, lastname, middlename, sex, status,"
                + " address  from Student where id=" + id;
        Statement statement;
        StudentDTO searchStudent = null;

        try {
            statement = connect.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next() != false) {
                searchStudent = new StudentDTO();
                searchStudent.setId(rs.getString("id"));
                searchStudent.setFirstname(rs.getString("firstname"));
                searchStudent.setLastname(rs.getString("lastname"));
                searchStudent.setMiddlename(rs.getString("middlename"));
                searchStudent.setSex(rs.getString("sex"));
                searchStudent.setStatus(rs.getString("status"));
                searchStudent.setAddress(rs.getString("address"));
            } else {
                return null;
            }

        } catch (Exception ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return searchStudent;

    }

    public static void main(String[] args) {
        StudentDTO sdto = new StudentDTO("3", "hung", "vuong", "thu 18", "2", "dop", "phiytho");
        new StudentService().update(sdto);
    }

    /**
     * Update into db arg= updatedStudent incase find out its id, if not then
     * insert updatedStudent into db
     */
    public boolean update(StudentDTO updatedStudent) {
        StudentDTO sdto = findById(updatedStudent.getId());
        //if student with id is exist then update 
        // else add in to db.
        if (sdto != null) {
            try {
                String sql = "UPDATE[dbo].[Student]SET[firstname]=?,[lastname]=?,[middlename]=?,[sex]=?,[status]=?,[address]=? WHERE id=?";
                PreparedStatement preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, updatedStudent.getFirstname());
                preparedStatement.setString(2, updatedStudent.getLastname());
                preparedStatement.setString(3, updatedStudent.getMiddlename());
                preparedStatement.setString(4, updatedStudent.getSex());
                preparedStatement.setString(5, updatedStudent.getStatus());
                preparedStatement.setString(6, updatedStudent.getAddress());
                preparedStatement.setString(7, updatedStudent.getId());

                int updateRow = preparedStatement.executeUpdate();
                if (updateRow == 1) {
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);

            }
            return false;
        } else {

            // insert new records incase not find out student with that id
            boolean inserted = insert(updatedStudent);
            if (inserted == false) {
                return false;
            } else {
                return true;
            }
        }

    }

}
