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
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie fizycznej z identyfikatorem NIP
 * 
 * &lt;p&gt;Java class for TIdentyfikatorOsobyFizycznej2 complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TIdentyfikatorOsobyFizycznej2"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNrNIP"/&amp;gt;
 *         &amp;lt;element name="ImiePierwsze" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TImie"/&amp;gt;
 *         &amp;lt;element name="Nazwisko" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNazwisko"/&amp;gt;
 *         &amp;lt;element name="DataUrodzenia" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TData"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyFizycznej2", propOrder = {
    "nip",
    "imiePierwsze",
    "nazwisko",
    "dataUrodzenia"
})
public class TIdentyfikatorOsobyFizycznej2 {

    @XmlElement(name = "NIP", required = true)
    protected String nip;
    @XmlElement(name = "ImiePierwsze", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String imiePierwsze;
    @XmlElement(name = "Nazwisko", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nazwisko;
    @XmlElement(name = "DataUrodzenia", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataUrodzenia;

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
     * Gets the value of the imiePierwsze property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImiePierwsze() {
        return imiePierwsze;
    }

    /**
     * Sets the value of the imiePierwsze property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImiePierwsze(String value) {
        this.imiePierwsze = value;
    }

    /**
     * Gets the value of the nazwisko property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Sets the value of the nazwisko property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazwisko(String value) {
        this.nazwisko = value;
    }

    /**
     * Gets the value of the dataUrodzenia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUrodzenia() {
        return dataUrodzenia;
    }

    /**
     * Sets the value of the dataUrodzenia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUrodzenia(XMLGregorianCalendar value) {
        this.dataUrodzenia = value;
    }

}
