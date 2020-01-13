package com.cal;


import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ADMIN
 */
@WebService
public class Calculator {

    @WebMethod(operationName = "a")
    public int add(int a, int b) {
        return a + b;
    }

    @WebMethod

    public int sub(int a, int b) {
        return a - b;

    }

    @WebMethod
    public int mul(int a, int b) {
        return a * b;
    }

    @WebMethod
    public int dev(int a, int b) {
        return a / b;
    }
}
