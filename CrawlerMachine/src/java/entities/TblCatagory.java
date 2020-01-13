package entities;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */

@Entity
@Table(name = "Tbl_Catagory", catalog = "NTPGamingGear", schema = "dbo")
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
        // catagoryName
        })
        
        @XmlRootElement(name = "Catagory", namespace = "www.catagory.vn")
        @NamedQueries({
        @NamedQuery(name = "TblCatagory.findAll", query = "SELECT t FROM TblCatagory t"),
        @NamedQuery(name = "TblCatagory.findByCatagoryId", query = "select t from TblCatagory t where t.catagoryId= :catagoryId"), 
        @NamedQuery(name = "TblCatagory.findByCatagpryId", query = "    SELECT t FROM TblCatagory t WHERE t.catagoryName=:catagoryName   "),
        })
public class TblCatagory implements Serializable{
    
    private static  final long serialVersionUID=1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CatagoryId", nullable = false,length = 250)
    @XmlAttribute(name = "CatagoryId", required = true)
    private String catagoryId;
    
     @Column(name = "CatagoryId", nullable = false,length = 250)
    @XmlAttribute(name = "CatagoryName", required = true)
    String catagoryName;

    @Override
    public String toString() {
        return  "entities.TblCatagory[ catagoryId= "+ catagoryId+"]";
    }

    public String getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(String catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public TblCatagory() {
    }

    public TblCatagory(String catagoryId, String catagoryName) {
        this.catagoryId = catagoryId;
        this.catagoryName = catagoryName;
    }
    
    
    
    
    
}
