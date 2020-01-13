package entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Long productID, String productName, String thumbnail, String catagoryID,
 * boolean isActive, String resourceDomain
 *
 */
@Entity
@Table(name = "TblProduct", catalog = "NTPGamingGear", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = { // "productname",
//"price"
//"thumbnail
})
@XmlSeeAlso({
    TblProduct.class})
@XmlRootElement(name = "ProductType", namespace = "www.product.com")
@NamedQueries({
    @NamedQuery(name = "TblProduct.findAll", query = "SELECT t FROM TblProduct t"),
    @NamedQuery(name = "TblProduct.findByProductID",
            query = "SELECT t FROM TblProduct t where t.productID= :productID"),
    @NamedQuery(name = "TblProduct.findByProductName",
            query = "SELECT t FROM TblProduct t where t.productName= :productName"),
    @NamedQuery(name = "TblProduct.findByProductThumbnail",
            query = "SELECT t FROM TblProduct t where t.productID= :productID"),
    @NamedQuery(name = "TblProduct.findByProductIsActive",
            query = "SELECT t FROM TblProduct t where t.isActive= :isActive"),
    @NamedQuery(name = "TblProduct.findByProductCatagoryID",
            query = "SELECT t FROM TblProduct t where t.catagoryID= :catagoryID"),
    @NamedQuery(name = "TblProduct.findByPrice",
            query = "SELECT t FROM TblProduct t where t.price= :price"),

    @NamedQuery(name = "TblProduct.findByNameAndCatagoryID",
            query = "SELECT t FROM TblProduct t WHERE lower(t.productName) "
            + " LIKE lower(:productName) AND t.catagoryID= :catagoryID "
            + "AND t.resourceDomain= :resourceDomain"),
    @NamedQuery(name = "TblProduct.findTrendingProducts",
            query = "SELECT t FROM TblProduct t ORDER BY t.productID"),
    @NamedQuery(name = "TblProduct.countAllRecordInCatagory",
            query = "SELECT count(t.productID) FROM TblProduct t WHERE t.catagoryID= :catagoryID"),
    @NamedQuery(name = "TblProduct.searchLikeProductName",
            query = "SELECT t FROM TblProduct t where lower(t.productName)"
            + "LIKE lower(:productName)")})
public class TblProduct implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProductID", nullable = false)
    @XmlAttribute(name = "ProductID", required = false)
    private Long productID;

    @XmlElement(name = "ProductName", namespace = "www.product.vn", required = true)
    @Column(name = "ProductName", length = 250)
    private String productName;

    
    @XmlElement(name = "price", namespace = "www.product.vn", required = true)
    @Column(name = "realPrice", length = 250)
    private String realPrice;
    
    @XmlElement(name = "Thumbnail", namespace = "www.product.vn", required = true)
    @Column(name = "Thumbnail", length = 250)
    private String thumbnail;
    @Column(name = "CatagoryID", length = 250)
    @XmlAttribute(name = "CatagoryID", required = true)
    private String catagoryID;
    @Column(name = "IsActive")
    private boolean isActive;
    @Column(name = "ResourceDomain", length = 250)
    @XmlTransient
    private String resourceDomain;

    public TblProduct(Long productID, String productName, String realPrice, String thumbnail, String catagoryID, boolean isActive, String resourceDomain) {
        this.productID = productID;
        this.productName = productName;
        this.realPrice = realPrice;
        this.thumbnail = thumbnail;
        this.catagoryID = catagoryID;
        this.isActive = isActive;
        this.resourceDomain = resourceDomain;
    }

    

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCatagoryID() {
        return catagoryID;
    }

    public void setCatagoryID(String catagoryID) {
        this.catagoryID = catagoryID;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public String getResourceDomain() {
        return resourceDomain;
    }

    public void setResourceDomain(String resourceDomain) {
        this.resourceDomain = resourceDomain;
    }

    public TblProduct(Long productID, String productName, String thumbnail, String catagoryID, boolean isActive, String resourceDomain) {
        this.productID = productID;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.catagoryID = catagoryID;
        this.isActive = isActive;
        this.resourceDomain = resourceDomain;
    }

    @Override
    public String toString() {
        return "entities.TblProduct[ productID=" + productID + "]";

    }

}
