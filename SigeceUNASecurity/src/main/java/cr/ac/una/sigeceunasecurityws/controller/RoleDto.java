
package cr.ac.una.sigeceunasecurityws.controller;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roleDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roleDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rolId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="rolName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rolVersion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="systId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roleDto", propOrder = {
    "rolId",
    "rolName",
    "rolVersion",
    "systId"
})
public class RoleDto {

    protected Long rolId;
    protected String rolName;
    protected Long rolVersion;
    protected Long systId;

    /**
     * Gets the value of the rolId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRolId() {
        return rolId;
    }

    /**
     * Sets the value of the rolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRolId(Long value) {
        this.rolId = value;
    }

    /**
     * Gets the value of the rolName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolName() {
        return rolName;
    }

    /**
     * Sets the value of the rolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolName(String value) {
        this.rolName = value;
    }

    /**
     * Gets the value of the rolVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRolVersion() {
        return rolVersion;
    }

    /**
     * Sets the value of the rolVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRolVersion(Long value) {
        this.rolVersion = value;
    }

    /**
     * Gets the value of the systId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSystId() {
        return systId;
    }

    /**
     * Sets the value of the systId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSystId(Long value) {
        this.systId = value;
    }

}
