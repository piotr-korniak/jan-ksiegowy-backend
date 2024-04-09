//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._08.ed.definicjetypy;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Zestaw danych identyfikacyjnych dla osoby fizycznej zagranicznej
 * 
 * <p>Java class for TIdentyfikatorOsobyFizycznejZagranicznej complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TIdentyfikatorOsobyFizycznejZagranicznej">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ImiePierwsze" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TImie"/>
 *         <element name="Nazwisko" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNazwisko"/>
 *         <element name="DataUrodzenia" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TData"/>
 *         <element name="MiejsceUrodzenia" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TMiejscowosc"/>
 *         <element name="ImieOjca" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TImie" minOccurs="0"/>
 *         <element name="ImieMatki" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TImie" minOccurs="0"/>
 *         <element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrNIP" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyFizycznejZagranicznej", propOrder = {
    "imiePierwsze",
    "nazwisko",
    "dataUrodzenia",
    "miejsceUrodzenia",
    "imieOjca",
    "imieMatki",
    "nip"
})
public class TIdentyfikatorOsobyFizycznejZagranicznej {

    /**
     * Imię pierwsze [First name]
     * 
     */
    @XmlElement(name = "ImiePierwsze", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String imiePierwsze;
    /**
     * Nazwisko [Family name]
     * 
     */
    @XmlElement(name = "Nazwisko", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nazwisko;
    /**
     * Data urodzenia [Date of Birth]
     * 
     */
    @XmlElement(name = "DataUrodzenia", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataUrodzenia;
    /**
     * Miejsce urodzenia [Place of Birth]
     * 
     */
    @XmlElement(name = "MiejsceUrodzenia", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String miejsceUrodzenia;
    /**
     * Imię ojca [Father’s name]
     * 
     */
    @XmlElement(name = "ImieOjca")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String imieOjca;
    /**
     * Imię matki [Mother’s name]
     * 
     */
    @XmlElement(name = "ImieMatki")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String imieMatki;
    /**
     * Identyfikator podatkowy NIP [Tax Identification Number (NIP)]
     * 
     */
    @XmlElement(name = "NIP")
    protected String nip;

    /**
     * Imię pierwsze [First name]
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
     * Nazwisko [Family name]
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
     * Data urodzenia [Date of Birth]
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

    /**
     * Miejsce urodzenia [Place of Birth]
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiejsceUrodzenia() {
        return miejsceUrodzenia;
    }

    /**
     * Sets the value of the miejsceUrodzenia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMiejsceUrodzenia()
     */
    public void setMiejsceUrodzenia(String value) {
        this.miejsceUrodzenia = value;
    }

    /**
     * Imię ojca [Father’s name]
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImieOjca() {
        return imieOjca;
    }

    /**
     * Sets the value of the imieOjca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getImieOjca()
     */
    public void setImieOjca(String value) {
        this.imieOjca = value;
    }

    /**
     * Imię matki [Mother’s name]
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImieMatki() {
        return imieMatki;
    }

    /**
     * Sets the value of the imieMatki property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getImieMatki()
     */
    public void setImieMatki(String value) {
        this.imieMatki = value;
    }

    /**
     * Identyfikator podatkowy NIP [Tax Identification Number (NIP)]
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

}
