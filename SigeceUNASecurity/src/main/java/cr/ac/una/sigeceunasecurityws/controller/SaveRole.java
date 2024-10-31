
package cr.ac.una.sigeceunasecurityws.controller;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveRole"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RoleToSave" type="{http://controller.sigeceunasecurityws.una.ac.cr/}roleDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveRole", propOrder = {
    "roleToSave"
})
public class SaveRole {

    @XmlElement(name = "RoleToSave")
    protected RoleDto roleToSave;

    /**
     * Gets the value of the roleToSave property.
     * 
     * @return
     *     possible object is
     *     {@link RoleDto }
     *     
     */
    public RoleDto getRoleToSave() {
        return roleToSave;
    }

    /**
     * Sets the value of the roleToSave property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleDto }
     *     
     */
    public void setRoleToSave(RoleDto value) {
        this.roleToSave = value;
    }

}
