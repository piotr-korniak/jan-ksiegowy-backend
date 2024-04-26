//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._08.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Informacje opisujące adres polski - bez elementu Poczta
 * 
 * <p>Java class for TAdresPolski1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TAdresPolski1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="KodKraju" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TKodKraju"/>
 *         <element name="Wojewodztwo" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TJednAdmin"/>
 *         <element name="Powiat" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TJednAdmin"/>
 *         <element name="Gmina" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TJednAdmin"/>
 *         <element name="Ulica" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TUlica" minOccurs="0"/>
 *         <element name="NrDomu" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrBudynku"/>
 *         <element name="NrLokalu" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrLokalu" minOccurs="0"/>
 *         <element name="Miejscowosc" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TMiejscowosc"/>
 *         <element name="KodPocztowy" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TKodPocztowy"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAdresPolski1", propOrder = {
    "kodKraju",
    "wojewodztwo",
    "powiat",
    "gmina",
    "ulica",
    "nrDomu",
    "nrLokalu",
    "miejscowosc",
    "kodPocztowy"
})
public class TAdresPolski1 {

    /**
     * Kraj
     * 
     */
    @XmlElement(name = "KodKraju", required = true)
    @XmlSchemaType(name = "normalizedString")
    protected TKodKraju kodKraju;
    /**
     * Województwo
     * 
     */
    @XmlElement(name = "Wojewodztwo", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String wojewodztwo;
    /**
     * Powiat
     * 
     */
    @XmlElement(name = "Powiat", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String powiat;
    /**
     * Gmina
     * 
     */
    @XmlElement(name = "Gmina", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String gmina;
    /**
     * Nazwa ulicy
     * 
     */
    @XmlElement(name = "Ulica")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ulica;
    /**
     * Numer budynku
     * 
     */
    @XmlElement(name = "NrDomu", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nrDomu;
    /**
     * Numer lokalu
     * 
     */
    @XmlElement(name = "NrLokalu")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nrLokalu;
    /**
     * Nazwa miejscowości
     * 
     */
    @XmlElement(name = "Miejscowosc", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String miejscowosc;
    /**
     * Kod pocztowy
     * 
     */
    @XmlElement(name = "KodPocztowy", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String kodPocztowy;

    /**
     * Kraj
     * 
     * @return
     *     possible object is
     *     {@link TKodKraju }
     *     
     */
    public TKodKraju getKodKraju() {
        return kodKraju;
    }

    /**
     * Sets the value of the kodKraju property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKodKraju }
     *     
     * @see #getKodKraju()
     */
    public void setKodKraju(TKodKraju value) {
        this.kodKraju = value;
    }

    /**
     * Województwo
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWojewodztwo() {
        return wojewodztwo;
    }

    /**
     * Sets the value of the wojewodztwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getWojewodztwo()
     */
    public void setWojewodztwo(String value) {
        this.wojewodztwo = value;
    }

    /**
     * Powiat
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPowiat() {
        return powiat;
    }

    /**
     * Sets the value of the powiat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPowiat()
     */
    public void setPowiat(String value) {
        this.powiat = value;
    }

    /**
     * Gmina
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGmina() {
        return gmina;
    }

    /**
     * Sets the value of the gmina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getGmina()
     */
    public void setGmina(String value) {
        this.gmina = value;
    }

    /**
     * Nazwa ulicy
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * Sets the value of the ulica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getUlica()
     */
    public void setUlica(String value) {
        this.ulica = value;
    }

    /**
     * Numer budynku
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrDomu() {
        return nrDomu;
    }

    /**
     * Sets the value of the nrDomu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNrDomu()
     */
    public void setNrDomu(String value) {
        this.nrDomu = value;
    }

    /**
     * Numer lokalu
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrLokalu() {
        return nrLokalu;
    }

    /**
     * Sets the value of the nrLokalu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNrLokalu()
     */
    public void setNrLokalu(String value) {
        this.nrLokalu = value;
    }

    /**
     * Nazwa miejscowości
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiejscowosc() {
        return miejscowosc;
    }

    /**
     * Sets the value of the miejscowosc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMiejscowosc()
     */
    public void setMiejscowosc(String value) {
        this.miejscowosc = value;
    }

    /**
     * Kod pocztowy
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodPocztowy() {
        return kodPocztowy;
    }

    /**
     * Sets the value of the kodPocztowy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getKodPocztowy()
     */
    public void setKodPocztowy(String value) {
        this.kodPocztowy = value;
    }

}