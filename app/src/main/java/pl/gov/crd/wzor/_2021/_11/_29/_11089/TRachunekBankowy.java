//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.12 at 11:04:52 AM CET 
//


package pl.gov.crd.wzor._2021._11._29._11089;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Informacje o rachunku bankowym
 * 
 * &lt;p&gt;Java class for TRachunekBankowy complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TRachunekBankowy"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;choice&amp;gt;
 *           &amp;lt;element name="NrRBPL" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TNrNRB"/&amp;gt;
 *           &amp;lt;sequence&amp;gt;
 *             &amp;lt;element name="NrRBZagr" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TNrRBZagr"/&amp;gt;
 *             &amp;lt;element name="SWIFT" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}SWIFT_Type"/&amp;gt;
 *           &amp;lt;/sequence&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *         &amp;lt;element name="RachunekWlasnyBanku" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TRachunekWlasnyBanku" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NazwaBanku" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TZnakowy" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRachunekBankowy", propOrder = {
    "nrRBPL",
    "nrRBZagr",
    "swift",
    "rachunekWlasnyBanku",
    "nazwaBanku"
})
public class TRachunekBankowy {

    @XmlElement(name = "NrRBPL")
    protected String nrRBPL;
    @XmlElement(name = "NrRBZagr")
    protected String nrRBZagr;
    @XmlElement(name = "SWIFT")
    protected String swift;
    @XmlElement(name = "RachunekWlasnyBanku")
    protected BigInteger rachunekWlasnyBanku;
    @XmlElement(name = "NazwaBanku")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nazwaBanku;

    /**
     * Gets the value of the nrRBPL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrRBPL() {
        return nrRBPL;
    }

    /**
     * Sets the value of the nrRBPL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrRBPL(String value) {
        this.nrRBPL = value;
    }

    /**
     * Gets the value of the nrRBZagr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrRBZagr() {
        return nrRBZagr;
    }

    /**
     * Sets the value of the nrRBZagr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrRBZagr(String value) {
        this.nrRBZagr = value;
    }

    /**
     * Gets the value of the swift property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSWIFT() {
        return swift;
    }

    /**
     * Sets the value of the swift property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSWIFT(String value) {
        this.swift = value;
    }

    /**
     * Gets the value of the rachunekWlasnyBanku property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRachunekWlasnyBanku() {
        return rachunekWlasnyBanku;
    }

    /**
     * Sets the value of the rachunekWlasnyBanku property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRachunekWlasnyBanku(BigInteger value) {
        this.rachunekWlasnyBanku = value;
    }

    /**
     * Gets the value of the nazwaBanku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazwaBanku() {
        return nazwaBanku;
    }

    /**
     * Sets the value of the nazwaBanku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazwaBanku(String value) {
        this.nazwaBanku = value;
    }

}