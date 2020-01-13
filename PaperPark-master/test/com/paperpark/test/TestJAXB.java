/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.test;

import com.paperpark.entity.Model;
import com.paperpark.models.ModelDetail;
import com.paperpark.models.ModelList;
import com.paperpark.utils.JAXBUtils;


/**
 *
 * @author NhanTT
 */
public class TestJAXB {
    public static void main(String[] args) {
        
        ModelList modelList = new ModelList();
        modelList.getModelList().add(new Model(0, "hi", 1, 1, 1, "pdf", "google", "facebook", Boolean.TRUE, null, null));
        modelList.getModelList().add(new Model(1, "ha", 1, 1, 1, "img", "amazon", "github", Boolean.TRUE, null, null));
        
        ModelDetail modelDetail = new ModelDetail();
        modelDetail.setMainModel(new Model(111, "main", 1, 1, 1, "pdo", "tiki", "shopee", Boolean.TRUE, null, null));
        modelDetail.setRelatedModels(modelList);
        
        String xml = JAXBUtils.marshall(modelDetail, modelDetail.getClass());
        System.out.println(xml);
    }
}
