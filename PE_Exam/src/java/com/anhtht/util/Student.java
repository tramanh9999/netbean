/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhtht.util;

 
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private String marks;
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getMarks() {
        return marks;
    }
 
    public void setMarks(String marks) {
        this.marks = marks;
    }
 
    @Override
    public String toString() {
        return "@Student, id=" + id + ", firstName=" + firstName 
                + ", lastName=" + lastName + ", marks=" + marks;
    }
}