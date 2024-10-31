
package cr.ac.una.sigeceunasecurityws.controller;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="roleCollection" type="{http://controller.sigeceunasecurityws.una.ac.cr/}roleDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="usrCelphonenumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="usrIdentification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrLastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrPhoto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrTelephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrTemppassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usrVersion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDto", propOrder = {
    "roleCollection",
    "usrCelphonenumber",
    "usrEmail",
    "usrId",
    "usrIdentification",
    "usrLanguage",
    "usrLastname",
    "usrName",
    "usrPassword",
    "usrPhoto",
    "usrState",
    "usrSurname",
    "usrTelephone",
    "usrTemppassword",
    "usrVersion"
})
public class UserDto {

    @XmlElement(nillable = true)
    protected List<RoleDto> roleCollection;
    protected String usrCelphonenumber;
    protected String usrEmail;
    protected Long usrId;
    protected String usrIdentification;
    protected String usrLanguage;
    protected String usrLastname;
    protected String usrName;
    protected String usrPassword;
    protected String usrPhoto;
    protected String usrState;
    protected String usrSurname;
    protected String usrTelephone;
    protected String usrTemppassword;
    protected Long usrVersion;

    /**
     * Gets the value of the roleCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the roleCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoleDto }
     * 
     * 
     */
    public List<RoleDto> getRoleCollection() {
        if (roleCollection == null) {
            roleCollection = new ArrayList<RoleDto>();
        }
        return this.roleCollection;
    }

    /**
     * Gets the value of the usrCelphonenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrCelphonenumber() {
        return usrCelphonenumber;
    }

    /**
     * Sets the value of the usrCelphonenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrCelphonenumber(String value) {
        this.usrCelphonenumber = value;
    }

    /**
     * Gets the value of the usrEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrEmail() {
        return usrEmail;
    }

    /**
     * Sets the value of the usrEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrEmail(String value) {
        this.usrEmail = value;
    }

    /**
     * Gets the value of the usrId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUsrId() {
        return usrId;
    }

    /**
     * Sets the value of the usrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUsrId(Long value) {
        this.usrId = value;
    }

    /**
     * Gets the value of the usrIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrIdentification() {
        return usrIdentification;
    }

    /**
     * Sets the value of the usrIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrIdentification(String value) {
        this.usrIdentification = value;
    }

    /**
     * Gets the value of the usrLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrLanguage() {
        return usrLanguage;
    }

    /**
     * Sets the value of the usrLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrLanguage(String value) {
        this.usrLanguage = value;
    }

    /**
     * Gets the value of the usrLastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrLastname() {
        return usrLastname;
    }

    /**
     * Sets the value of the usrLastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrLastname(String value) {
        this.usrLastname = value;
    }

    /**
     * Gets the value of the usrName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrName() {
        return usrName;
    }

    /**
     * Sets the value of the usrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrName(String value) {
        this.usrName = value;
    }

    /**
     * Gets the value of the usrPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrPassword() {
        return usrPassword;
    }

    /**
     * Sets the value of the usrPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrPassword(String value) {
        this.usrPassword = value;
    }

    /**
     * Gets the value of the usrPhoto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrPhoto() {
        return usrPhoto;
    }

    /**
     * Sets the value of the usrPhoto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrPhoto(String value) {
        this.usrPhoto = value;
    }

    /**
     * Gets the value of the usrState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrState() {
        return usrState;
    }

    /**
     * Sets the value of the usrState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrState(String value) {
        this.usrState = value;
    }

    /**
     * Gets the value of the usrSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrSurname() {
        return usrSurname;
    }

    /**
     * Sets the value of the usrSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrSurname(String value) {
        this.usrSurname = value;
    }

    /**
     * Gets the value of the usrTelephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrTelephone() {
        return usrTelephone;
    }

    /**
     * Sets the value of the usrTelephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrTelephone(String value) {
        this.usrTelephone = value;
    }

    /**
     * Gets the value of the usrTemppassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrTemppassword() {
        return usrTemppassword;
    }

    /**
     * Sets the value of the usrTemppassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrTemppassword(String value) {
        this.usrTemppassword = value;
    }

    /**
     * Gets the value of the usrVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUsrVersion() {
        return usrVersion;
    }

    /**
     * Sets the value of the usrVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUsrVersion(Long value) {
        this.usrVersion = value;
    }

}
