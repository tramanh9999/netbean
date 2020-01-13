/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.entity;

import com.paperpark.config.model.ModelEstimation;
import com.paperpark.listener.PaperParkContextListener;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author NhanTT
 */
@Entity
@Table(name = "Model", catalog = "PaperPark", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Model", propOrder = {
    "id",
    "name",
    "numOfSheets",
    "numOfParts",
    "difficulty",
    "format",
    "imageSrc",
    "link",
    "hasInstruction",
    "estimateTime",
    "tagCollection",
    "categoryId"
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Model.findAll", query = "SELECT m FROM Model m")
    , @NamedQuery(name = "Model.findById", query = "SELECT m FROM Model m WHERE m.id = :id")
    , @NamedQuery(name = "Model.findByName", query = "SELECT m FROM Model m WHERE m.name LIKE :name")
    , @NamedQuery(name = "Model.findByNumOfSheets", query = "SELECT m FROM Model m WHERE m.numOfSheets = :numOfSheets")
    , @NamedQuery(name = "Model.findByNumOfParts", query = "SELECT m FROM Model m WHERE m.numOfParts = :numOfParts")
    , @NamedQuery(name = "Model.findByDifficulty", query = "SELECT m FROM Model m WHERE m.difficulty = :difficulty")
    , @NamedQuery(name = "Model.findByFormat", query = "SELECT m FROM Model m WHERE m.format = :format")
    , @NamedQuery(name = "Model.findByImageSrc", query = "SELECT m FROM Model m WHERE m.imageSrc = :imageSrc")
    , @NamedQuery(name = "Model.findByLink", query = "SELECT m FROM Model m WHERE m.link = :link")
    , @NamedQuery(name = "Model.findByHasInstruction",
            query = "SELECT m FROM Model m WHERE m.hasInstruction = :hasInstruction")
    , @NamedQuery(name = "Model.getCountModels", query = "SELECT count(m) FROM Model m")
})
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    @XmlElement(name = "id")
    private Integer id;

    @Column(name = "Name", length = 100)
    @XmlElement(name = "name")
    private String name;

    @Column(name = "NumOfSheets")
    @XmlElement(name = "num-of-sheets")
    private Integer numOfSheets;

    @Column(name = "NumOfParts")
    @XmlElement(name = "num-of-parts")
    private Integer numOfParts;

    @Column(name = "Difficulty")
    @XmlElement(name = "difficulty")
    private Integer difficulty;

    @Column(name = "Format", length = 10)
    @XmlElement(name = "format")
    private String format;

    @Column(name = "ImageSrc", length = 500)
    @XmlElement(name = "image-src")
    private String imageSrc;

    @Column(name = "Link", length = 500)
    @XmlElement(name = "link")
    private String link;

    @Column(name = "HasInstruction")
    @XmlElement(name = "has-instruction")
    private Boolean hasInstruction;

    @JoinTable(name = "Model_Tag_Mapping", joinColumns = {
        @JoinColumn(name = "ModelId", referencedColumnName = "Id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "TagId", referencedColumnName = "Id", nullable = false)})
    @ManyToMany
    private Collection<Tag> tagCollection;
    @JoinColumn(name = "CategoryId", referencedColumnName = "Id")
    @ManyToOne
    private Category categoryId;
    
    @Transient
    @XmlElement(name = "estimate-time")
    private Double estimateTime;

    public Model() {
    }

    public Model(Integer id) {
        this.id = id;
    }

    public Model(Integer id, String name, Integer numOfSheets, Integer numOfParts,
            Integer difficulty, String format, String imageSrc, String link,
            Boolean hasInstruction, Collection<Tag> tagCollection, Category categoryId) {
        this.id = id;
        this.name = name;
        this.numOfSheets = numOfSheets;
        this.numOfParts = numOfParts;
        this.difficulty = difficulty;
        this.format = format;
        this.imageSrc = imageSrc;
        this.link = link;
        this.hasInstruction = hasInstruction;
        this.tagCollection = tagCollection;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOfSheets() {
        return numOfSheets;
    }

    public void setNumOfSheets(Integer numOfSheets) {
        this.numOfSheets = numOfSheets;
    }

    public Integer getNumOfParts() {
        return numOfParts;
    }

    public void setNumOfParts(Integer numOfParts) {
        this.numOfParts = numOfParts;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getHasInstruction() {
        return hasInstruction;
    }

    public void setHasInstruction(Boolean hasInstruction) {
        this.hasInstruction = hasInstruction;
    }

    @XmlTransient
    public Collection<Tag> getTagCollection() {
        return tagCollection;
    }

    public void setTagCollection(Collection<Tag> tagCollection) {
        this.tagCollection = tagCollection;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model)) {
            return false;
        }
        Model other = (Model) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.paperpark.entity.Model[ id=" + id + " ]";
    }
    
    public Double getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Double estimateTime) {
        this.estimateTime = estimateTime;
    }

    public void estimateMakingTime(ModelEstimation estimation, int skillLevel) {
        double standardPartsPerSheet = estimation.getDefaultPartsPerSheet().doubleValue();

        double partsPerSheet = standardPartsPerSheet;

        if (numOfSheets != null && numOfSheets > 0 && numOfParts != null && numOfParts > 0) {
            partsPerSheet = 1.0 * numOfParts / numOfSheets;
        }

        this.estimateTime = getEstimateMakingTime(skillLevel, difficulty,
                partsPerSheet, standardPartsPerSheet, numOfSheets);
    }

    private Double getEstimateMakingTime(int skillLevel, int difficulty,
            double partsPerSheet, double standardPartsPerSheet, int numOfSheets) {

        Double hoursPerSheet = 0.75 * (difficulty / (0.625 * skillLevel + 1.875))
                * (partsPerSheet / standardPartsPerSheet);

        Double totalTime = hoursPerSheet * numOfSheets;

        return totalTime;
    }
    
    public void copyValueOf(Model model) {
        if (model == null) {
            return;
        }
        
        id = model.id;
        name = model.name;
        numOfSheets = model.numOfSheets;
        numOfParts = model.numOfParts;
        difficulty = model.difficulty;
        format = model.format;
        imageSrc = model.imageSrc;
        link = model.link;
        hasInstruction = model.hasInstruction;
        categoryId = model.categoryId;
        tagCollection = model.tagCollection;
        estimateTime = model.estimateTime;
    }

}
