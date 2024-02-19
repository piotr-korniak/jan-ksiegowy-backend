//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.01.30 at 10:37:45 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Zestaw danych identyfikacyjnych dla osoby niefizycznej zagranicznej
 * 
 * &lt;p&gt;Java class for TIdentyfikatorOsobyNiefizycznejZagranicznej complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TIdentyfikatorOsobyNiefizycznejZagranicznej"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="PelnaNazwa"&amp;gt;
 *           &amp;lt;simpleType&amp;gt;
 *             &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *               &amp;lt;minLength value="1"/&amp;gt;
 *               &amp;lt;maxLength value="240"/&amp;gt;
 *             &amp;lt;/restriction&amp;gt;
 *           &amp;lt;/simpleType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;element name="SkroconaNazwa" minOccurs="0"&amp;gt;
 *           &amp;lt;simpleType&amp;gt;
 *             &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *               &amp;lt;minLength value="1"/&amp;gt;
 *               &amp;lt;maxLength value="70"/&amp;gt;
 *             &amp;lt;/restriction&amp;gt;
 *           &amp;lt;/simpleType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNrNIP" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyNiefizycznejZagranicznej", propOrder = {
    "pelnaNazwa",
    "skroconaNazwa",
    "nip"
})
public class TIdentyfikatorOsobyNiefizycznejZagranicznej {

    @XmlElement(name = "PelnaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String pelnaNazwa;
    @XmlElement(name = "SkroconaNazwa")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String skroconaNazwa;
    @XmlElement(name = "NIP")
    protected String nip;

    /**
     * Gets the value of the pelnaNazwa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPelnaNazwa() {
        return pelnaNazwa;
    }

    /**
     * Sets the value of the pelnaNazwa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPelnaNazwa(String value) {
        this.pelnaNazwa = value;
    }

    /**
     * Gets the value of the skroconaNazwa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkroconaNazwa() {
        return skroconaNazwa;
    }

    /**
     * Sets the value of the skroconaNazwa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkroconaNazwa(String value) {
        this.skroconaNazwa = value;
    }

    /**
     * Gets the value of the nip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIP() {
        return nip;
    }

    /**
     * Sets the value of the nip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIP(String value) {
        this.nip = value;
    }

}
