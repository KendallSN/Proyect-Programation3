
package cr.ac.una.sigeceunasecurityws.controller;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for systemsDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="systemsDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="systDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="systId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="systName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="systVersion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "systemsDto", propOrder = {
    "systDescription",
    "systId",
    "systName",
    "systVersion"
})
public class SystemsDto {

    protected String systDescription;
    protected Long systId;
    protected String systName;
    protected Long systVersion;

    /**
     * Gets the value of the systDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystDescription() {
        return systDescription;
    }

    /**
     * Sets the value of the systDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystDescription(String value) {
        this.systDescription = value;
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

    /**
     * Gets the value of the systName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystName() {
        return systName;
    }

    /**
     * Sets the value of the systName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystName(String value) {
        this.systName = value;
    }

    /**
     * Gets the value of the systVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSystVersion() {
        return systVersion;
    }

    /**
     * Sets the value of the systVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSystVersion(Long value) {
        this.systVersion = value;
    }

}
