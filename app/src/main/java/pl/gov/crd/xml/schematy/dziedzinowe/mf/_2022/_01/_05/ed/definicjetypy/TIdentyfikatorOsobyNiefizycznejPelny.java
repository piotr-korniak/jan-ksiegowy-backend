//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Pełny zestaw danych identyfikacyjnych o osobie niefizycznej
 * 
 * <p>Java class for TIdentyfikatorOsobyNiefizycznejPelny complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TIdentyfikatorOsobyNiefizycznejPelny">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNrNIP" minOccurs="0"/>
 *         <element name="PelnaNazwa">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               <minLength value="1"/>
 *               <maxLength value="240"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="SkroconaNazwa">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               <minLength value="1"/>
 *               <maxLength value="70"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="REGON" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TNrREGON"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorOsobyNiefizycznejPelny", propOrder = {
    "nip",
    "pelnaNazwa",
    "skroconaNazwa",
    "regon"
})
public class TIdentyfikatorOsobyNiefizycznejPelny {

    /**
     * Identyfikator podatkowy NIP
     * 
     */
    @XmlElement(name = "NIP")
    protected String nip;
    /**
     * Pełna nazwa
     * 
     */
    @XmlElement(name = "PelnaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String pelnaNazwa;
    /**
     * Skrócona nazwa
     * 
     */
    @XmlElement(name = "SkroconaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String skroconaNazwa;
    /**
     * Numer REGON
     * 
     */
    @XmlElement(name = "REGON", required = true)
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
     * Skrócona nazwa
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
     * @see #getSkroconaNazwa()
     */
    public void setSkroconaNazwa(String value) {
        this.skroconaNazwa = value;
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
