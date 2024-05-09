//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie fizycznej z identyfikatorem NIP
 * 
 * <p>Java class for TIdentyfikatorOsobyFizycznej2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TIdentyfikatorOsobyFizycznej2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNrNIP"/>
 *         <element name="ImiePierwsze" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TImie"/>
 *         <element name="Nazwisko" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNazwisko"/>
 *         <element name="DataUrodzenia" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TData"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
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

    /**
     * Identyfikator podatkowy NIP
     * 
     */
    @XmlElement(name = "NIP", required = true)
    protected String nip;
    /**
     * Pierwsze imię
     * 
     */
    @XmlElement(name = "ImiePierwsze", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String imiePierwsze;
    /**
     * Nazwisko
     * 
     */
    @XmlElement(name = "Nazwisko", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nazwisko;
    /**
     * Data urodzenia
     * 
     */
    @XmlElement(name = "DataUrodzenia", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataUrodzenia;

    /**
     * Identyfikator podatkowy NIP
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
     * @see #getNIP()
     */
    public void setNIP(String value) {
        this.nip = value;
    }

    /**
     * Pierwsze imię
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
     * @see #getImiePierwsze()
     */
    public void setImiePierwsze(String value) {
        this.imiePierwsze = value;
    }

    /**
     * Nazwisko
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
     * @see #getNazwisko()
     */
    public void setNazwisko(String value) {
        this.nazwisko = value;
    }

    /**
     * Data urodzenia
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
     * @see #getDataUrodzenia()
     */
    public void setDataUrodzenia(XMLGregorianCalendar value) {
        this.dataUrodzenia = value;
    }

}
