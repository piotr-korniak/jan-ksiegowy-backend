//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.12 at 11:04:52 AM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._09.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie niefizycznej
 * 
 * &lt;p&gt;Java class for TIdentyfikatorOsobyNiefizycznej complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TIdentyfikatorOsobyNiefizycznej"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/09/eD/DefinicjeTypy/}TNrNIP"/&amp;gt;
 *         &amp;lt;element name="PelnaNazwa"&amp;gt;
 *           &amp;lt;simpleType&amp;gt;
 *             &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *               &amp;lt;minLength value="1"/&amp;gt;
 *               &amp;lt;maxLength value="240"/&amp;gt;
 *             &amp;lt;/restriction&amp;gt;
 *           &amp;lt;/simpleType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;element name="REGON" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/09/eD/DefinicjeTypy/}TNrREGON" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyNiefizycznej", propOrder = {
    "nip",
    "pelnaNazwa",
    "regon"
})
public class TIdentyfikatorOsobyNiefizycznej {

    @XmlElement(name = "NIP", required = true)
    protected String nip;
    @XmlElement(name = "PelnaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String pelnaNazwa;
    @XmlElement(name = "REGON")
    protected String regon;

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
     * Gets the value of the regon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGON() {
        return regon;
    }

    /**
     * Sets the value of the regon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGON(String value) {
        this.regon = value;
    }

}
