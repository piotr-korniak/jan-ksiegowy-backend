//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.12 at 11:04:52 AM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._09.ed.definicjetypy;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAdresPolski", propOrder = {
    "kodKraju",
    "wojewodztwo",
    "powiat",
    "gmina",
    "ulica",
    "nrDomu",
    "nrLokalu",
    "miejscowosc",
    "kodPocztowy",
    "poczta"
})
public class TAdresPolski {

    @XmlElement(name = "KodKraju", required = true)
    @XmlSchemaType(name = "normalizedString")
    protected TKodKraju kodKraju;
    @XmlElement(name = "Wojewodztwo", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String wojewodztwo;
    @XmlElement(name = "Powiat", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String powiat;
    @XmlElement(name = "Gmina", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String gmina;
    @XmlElement(name = "Ulica")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ulica;
    @XmlElement(name = "NrDomu", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nrDomu;
    @XmlElement(name = "NrLokalu")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nrLokalu;
    @XmlElement(name = "Miejscowosc", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String miejscowosc;
    @XmlElement(name = "KodPocztowy", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String kodPocztowy;
    @XmlElement(name = "Poczta", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String poczta;

    /**
     * Gets the value of the kodKraju property.
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
     */
    public void setKodKraju(TKodKraju value) {
        this.kodKraju = value;
    }

    /**
     * Gets the value of the wojewodztwo property.
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
     */
    public void setWojewodztwo(String value) {
        this.wojewodztwo = value;
    }

    /**
     * Gets the value of the powiat property.
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
     */
    public void setPowiat(String value) {
        this.powiat = value;
    }

    /**
     * Gets the value of the gmina property.
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
     */
    public void setGmina(String value) {
        this.gmina = value;
    }

    /**
     * Gets the value of the ulica property.
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
     */
    public void setUlica(String value) {
        this.ulica = value;
    }

    /**
     * Gets the value of the nrDomu property.
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
     */
    public void setNrDomu(String value) {
        this.nrDomu = value;
    }

    /**
     * Gets the value of the nrLokalu property.
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
     */
    public void setNrLokalu(String value) {
        this.nrLokalu = value;
    }

    /**
     * Gets the value of the miejscowosc property.
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
     */
    public void setMiejscowosc(String value) {
        this.miejscowosc = value;
    }

    /**
     * Gets the value of the kodPocztowy property.
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
     */
    public void setKodPocztowy(String value) {
        this.kodPocztowy = value;
    }

    /**
     * Gets the value of the poczta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoczta() {
        return poczta;
    }

    /**
     * Sets the value of the poczta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoczta(String value) {
        this.poczta = value;
    }

}
