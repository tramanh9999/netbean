/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.models;

import com.paperpark.entity.Model;
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
@XmlType(name = "ModelDetail", propOrder = {
    "mainModel",
    "relatedModels"
})
@XmlRootElement(name = "model-detail")
public class ModelDetail {

    @XmlElement(name = "main-model")
    private Model mainModel;

    @XmlElement(name = "model-list")
    private ModelList relatedModels;

    public ModelDetail() {
    }

    public ModelDetail(Model mainModel, ModelList relatedModels) {
        this.mainModel = mainModel;
        this.relatedModels = relatedModels;
    }

    /**
     * @return the mainModel
     */
    public Model getMainModel() {
        return mainModel;
    }

    /**
     * @param mainModel the mainModel to set
     */
    public void setMainModel(Model mainModel) {
        this.mainModel = mainModel;
    }

    /**
     * @return the relatedModels
     */
    public ModelList getRelatedModels() {
        return relatedModels;
    }

    /**
     * @param relatedModels the relatedModels to set
     */
    public void setRelatedModels(ModelList relatedModels) {
        this.relatedModels = relatedModels;
    }

}
