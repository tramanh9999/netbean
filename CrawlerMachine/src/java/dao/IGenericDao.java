package dao;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
interface IGenericDao<T, PK> {

    public T create(T t);

    public T findById();

    public T update();

    public T delete(T t);

     public List<T> getAll(String namedQuery);

}
