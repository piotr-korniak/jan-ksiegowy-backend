//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.01.30 at 10:37:45 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Podstawowy zestaw danych o osobie fizycznej z identyfikatorem NIP albo PESEL - bez elementu Poczta w adresie polskim
 * 
 * &lt;p&gt;Java class for TOsobaFizyczna3 complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TOsobaFizyczna3"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OsobaFizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TIdentyfikatorOsobyFizycznej1"/&amp;gt;
 *         &amp;lt;element name="AdresZamieszkania"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TAdres1"&amp;gt;
 *                 &amp;lt;attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" /&amp;gt;
 *               &amp;lt;/extension&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TOsobaFizyczna3", propOrder = {
    "osobaFizyczna",
    "adresZamieszkania"
})
public class TOsobaFizyczna3 {

    @XmlElement(name = "OsobaFizyczna", required = true)
    protected TIdentyfikatorOsobyFizycznej1 osobaFizyczna;
    @XmlElement(name = "AdresZamieszkania", required = true)
    protected TOsobaFizyczna3 .AdresZamieszkania adresZamieszkania;

    /**
     * Gets the value of the osobaFizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyFizycznej1 }
     *     
     */
    public TIdentyfikatorOsobyFizycznej1 getOsobaFizyczna() {
        return osobaFizyczna;
    }

    /**
     * Sets the value of the osobaFizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyFizycznej1 }
     *     
     */
    public void setOsobaFizyczna(TIdentyfikatorOsobyFizycznej1 value) {
        this.osobaFizyczna = value;
    }

    /**
     * Gets the value of the adresZamieszkania property.
     * 
     * @return
     *     possible object is
     *     {@link TOsobaFizyczna3 .AdresZamieszkania }
     *     
     */
    public TOsobaFizyczna3 .AdresZamieszkania getAdresZamieszkania() {
        return adresZamieszkania;
    }

    /**
     * Sets the value of the adresZamieszkania property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOsobaFizyczna3 .AdresZamieszkania }
     *     
     */
    public void setAdresZamieszkania(TOsobaFizyczna3 .AdresZamieszkania value) {
        this.adresZamieszkania = value;
    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TAdres1"&amp;gt;
     *       &amp;lt;attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" /&amp;gt;
     *     &amp;lt;/extension&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AdresZamieszkania
        extends TAdres1
    {

        @XmlAttribute(name = "rodzajAdresu", required = true)
        protected String rodzajAdresu;

        /**
         * Gets the value of the rodzajAdresu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRodzajAdresu() {
            if (rodzajAdresu == null) {
                return "RAD";
            } else {
                return rodzajAdresu;
            }
        }

        /**
         * Sets the value of the rodzajAdresu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRodzajAdresu(String value) {
            this.rodzajAdresu = value;
        }

    }

}
