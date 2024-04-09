//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._08.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie niefizycznej
 * 
 * <p>Java class for TIdentyfikatorOsobyNiefizycznej complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TIdentyfikatorOsobyNiefizycznej">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrNIP"/>
 *         <element name="PelnaNazwa">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               <minLength value="1"/>
 *               <maxLength value="240"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="REGON" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TNrREGON" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
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

    /**
     * Identyfikator podatkowy NIP
     * 
     */
    @XmlElement(name = "NIP", required = true)
    protected String nip;
    /**
     * Pełna nazwa
     * 
     */
    @XmlElement(name = "PelnaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String pelnaNazwa;
    /**
     * Numer REGON
     * 
     */
    @XmlElement(name = "REGON")
    protected String regon;

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
     * Pełna nazwa
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
     * @see #getPelnaNazwa()
     */
    public void setPelnaNazwa(String value) {
        this.pelnaNazwa = value;
    }

    /**
     * Numer REGON
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
     * @see #getREGON()
     */
    public void setREGON(String value) {
        this.regon = value;
    }

}
