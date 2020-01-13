/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.dao.inteface;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface IGenericDao<T, PK> {

    public T create(T t);

    public T findById(PK pk);

    public T update(T t);

    public boolean delete(T t);

    public List<T> getAll(String querry);
}
