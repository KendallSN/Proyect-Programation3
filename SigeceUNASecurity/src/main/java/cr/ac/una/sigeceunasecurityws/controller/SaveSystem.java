
package cr.ac.una.sigeceunasecurityws.controller;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveSystem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveSystem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SystemToRegister" type="{http://controller.sigeceunasecurityws.una.ac.cr/}systemsDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveSystem", propOrder = {
    "systemToRegister"
})
public class SaveSystem {

    @XmlElement(name = "SystemToRegister")
    protected SystemsDto systemToRegister;

    /**
     * Gets the value of the systemToRegister property.
     * 
     * @return
     *     possible object is
     *     {@link SystemsDto }
     *     
     */
    public SystemsDto getSystemToRegister() {
        return systemToRegister;
    }

    /**
     * Sets the value of the systemToRegister property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemsDto }
     *     
     */
    public void setSystemToRegister(SystemsDto value) {
        this.systemToRegister = value;
    }

}
