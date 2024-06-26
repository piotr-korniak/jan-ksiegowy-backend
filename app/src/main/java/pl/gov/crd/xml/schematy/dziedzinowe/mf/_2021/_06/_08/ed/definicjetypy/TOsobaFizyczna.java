//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._06._08.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Podstawowy zestaw danych o osobie fizycznej
 * 
 * <p>Java class for TOsobaFizyczna complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TOsobaFizyczna">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="OsobaFizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TIdentyfikatorOsobyFizycznej"/>
 *         <element name="AdresZamieszkania">
 *           <complexType>
 *             <complexContent>
 *               <extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TAdres">
 *                 <attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" />
 *               </extension>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TOsobaFizyczna", propOrder = {
    "osobaFizyczna",
    "adresZamieszkania"
})
public class TOsobaFizyczna {

    @XmlElement(name = "OsobaFizyczna", required = true)
    protected TIdentyfikatorOsobyFizycznej osobaFizyczna;
    @XmlElement(name = "AdresZamieszkania", required = true)
    protected AdresZamieszkania adresZamieszkania;

    /**
     * Gets the value of the osobaFizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyFizycznej }
     *     
     */
    public TIdentyfikatorOsobyFizycznej getOsobaFizyczna() {
        return osobaFizyczna;
    }

    /**
     * Sets the value of the osobaFizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyFizycznej }
     *     
     */
    public void setOsobaFizyczna(TIdentyfikatorOsobyFizycznej value) {
        this.osobaFizyczna = value;
    }

    /**
     * Gets the value of the adresZamieszkania property.
     * 
     * @return
     *     possible object is
     *     {@link AdresZamieszkania }
     *     
     */
    public AdresZamieszkania getAdresZamieszkania() {
        return adresZamieszkania;
    }

    /**
     * Sets the value of the adresZamieszkania property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdresZamieszkania }
     *     
     */
    public void setAdresZamieszkania( AdresZamieszkania value) {
        this.adresZamieszkania = value;
    }


    /**
     * <p>Java class for anonymous complex type</p>.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.</p>
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <extension base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/06/08/eD/DefinicjeTypy/}TAdres">
     *       <attribute name="rodzajAdresu" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="RAD" />
     *     </extension>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AdresZamieszkania
        extends TAdres
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
