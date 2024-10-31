
package cr.ac.una.sigeceunasecurityws.controller;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getRolesOfSystem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getRolesOfSystem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="systemDto" type="{http://controller.sigeceunasecurityws.una.ac.cr/}systemsDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRolesOfSystem", propOrder = {
    "systemDto"
})
public class GetRolesOfSystem {

    protected SystemsDto systemDto;

    /**
     * Gets the value of the systemDto property.
     * 
     * @return
     *     possible object is
     *     {@link SystemsDto }
     *     
     */
    public SystemsDto getSystemDto() {
        return systemDto;
    }

    /**
     * Sets the value of the systemDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemsDto }
     *     
     */
    public void setSystemDto(SystemsDto value) {
        this.systemDto = value;
    }

}
