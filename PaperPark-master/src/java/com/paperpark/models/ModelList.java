/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.models;

import com.paperpark.entity.Model;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author NhanTT
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModelList", propOrder = {
    "modelList"
})
@XmlRootElement(name = "model-list")
public class ModelList {

    public ModelList() {
    }

    public ModelList(List<Model> modelList) {
        this.modelList = modelList;
    }
    
    @XmlElement(name = "model")
    private List<Model> modelList;

    /**
     * @return the modelList
     */
    public List<Model> getModelList() {
        if (modelList == null) {
            modelList = new ArrayList<>();
        }
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }
}
