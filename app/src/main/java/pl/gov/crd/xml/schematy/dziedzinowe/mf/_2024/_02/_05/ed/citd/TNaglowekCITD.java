//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2024._02._05.ed.citd;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * Nagłówek deklaracji
 * 
 * <p>Java class for TNaglowek_CIT-D complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TNaglowek_CIT-D">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="KodFormularza">
 *           <complexType>
 *             <simpleContent>
 *               <extension base="<http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITD/>TKodFormularza_CD">
 *                 <attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="CIT-D (8)" />
 *                 <attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2-0E" />
 *               </extension>
 *             </simpleContent>
 *           </complexType>
 *         </element>
 *         <element name="WariantFormularza">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               <enumeration value="8"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="OkresOd">
 *           <complexType>
 *             <simpleContent>
 *               <extension base="<http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITD/>TLData">
 *                 <attribute name="poz" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="P_4" />
 *               </extension>
 *             </simpleContent>
 *           </complexType>
 *         </element>
 *         <element name="OkresDo">
 *           <complexType>
 *             <simpleContent>
 *               <extension base="<http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITD/>TLData">
 *                 <attribute name="poz" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="P_5" />
 *               </extension>
 *             </simpleContent>
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
@XmlType(name = "TNaglowek_CIT-D", propOrder = {
    "kodFormularza",
    "wariantFormularza",
    "okresOd",
    "okresDo"
})
public class TNaglowekCITD {

    @XmlElement(name = "KodFormularza", required = true)
    protected KodFormularza kodFormularza;
    @XmlElement(name = "WariantFormularza")
    protected byte wariantFormularza;
    @XmlElement(name = "OkresOd", required = true)
    protected OkresOd okresOd;
    @XmlElement(name = "OkresDo", required = true)
    protected OkresDo okresDo;

    /**
     * Gets the value of the kodFormularza property.
     * 
     * @return
     *     possible object is
     *     {@link KodFormularza }
     *     
     */
    public KodFormularza getKodFormularza() {
        return kodFormularza;
    }

    /**
     * Sets the value of the kodFormularza property.
     * 
     * @param value
     *     allowed object is
     *     {@link KodFormularza }
     *     
     */
    public void setKodFormularza( KodFormularza value) {
        this.kodFormularza = value;
    }

    /**
     * Gets the value of the wariantFormularza property.
     * 
     */
    public byte getWariantFormularza() {
        return wariantFormularza;
    }

    /**
     * Sets the value of the wariantFormularza property.
     * 
     */
    public void setWariantFormularza(byte value) {
        this.wariantFormularza = value;
    }

    /**
     * Gets the value of the okresOd property.
     * 
     * @return
     *     possible object is
     *     {@link OkresOd }
     *     
     */
    public OkresOd getOkresOd() {
        return okresOd;
    }

    /**
     * Sets the value of the okresOd property.
     * 
     * @param value
     *     allowed object is
     *     {@link OkresOd }
     *     
     */
    public void setOkresOd( OkresOd value) {
        this.okresOd = value;
    }

    /**
     * Gets the value of the okresDo property.
     * 
     * @return
     *     possible object is
     *     {@link OkresDo }
     *     
     */
    public OkresDo getOkresDo() {
        return okresDo;
    }

    /**
     * Sets the value of the okresDo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OkresDo }
     *     
     */
    public void setOkresDo( OkresDo value) {
        this.okresDo = value;
    }


    /**
     * <p>Java class for anonymous complex type</p>.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.</p>
     * 
     * <pre>{@code
     * <complexType>
     *   <simpleContent>
     *     <extension base="<http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITD/>TKodFormularza_CD">
     *       <attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="CIT-D (8)" />
     *       <attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2-0E" />
     *     </extension>
     *   </simpleContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class KodFormularza {

        /**
         * Symbol wzoru formularza
         * 
         */
        @XmlValue
        protected TKodFormularzaCD value;
        @XmlAttribute(name = "kodSystemowy", required = true)
        protected String kodSystemowy;
        @XmlAttribute(name = "wersjaSchemy", required = true)
        protected String wersjaSchemy;

        /**
         * Symbol wzoru formularza
         * 
         * @return
         *     possible object is
         *     {@link TKodFormularzaCD }
         *     
         */
        public TKodFormularzaCD getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link TKodFormularzaCD }
         *     
         * @see #getValue()
         */
        public void setValue(TKodFormularzaCD value) {
            this.value = value;
        }

        /**
         * Gets the value of the kodSystemowy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKodSystemowy() {
            if (kodSystemowy == null) {
                return "CIT-D (8)";
            } else {
                return kodSystemowy;
            }
        }

        /**
         * Sets the value of the kodSystemowy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKodSystemowy(String value) {
            this.kodSystemowy = value;
        }

        /**
         * Gets the value of the wersjaSchemy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWersjaSchemy() {
            if (wersjaSchemy == null) {
                return "2-0E";
            } else {
                return wersjaSchemy;
            }
        }

        /**
         * Sets the value of the wersjaSchemy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWersjaSchemy(String value) {
            this.wersjaSchemy = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type</p>.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.</p>
     * 
     * <pre>{@code
     * <complexType>
     *   <simpleContent>
     *     <extension base="<http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITD/>TLData">
     *       <attribute name="poz" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="P_5" />
     *     </extension>
     *   </simpleContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class OkresDo {

        /**
         * Ograniczenie dla daty
         * 
         */
        @XmlValue
        protected XMLGregorianCalendar value;
        @XmlAttribute(name = "poz", required = true)
        protected String poz;

        /**
         * Ograniczenie dla daty
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         * @see #getValue()
         */
        public void setValue(XMLGregorianCalendar value) {
            this.value = value;
        }

        /**
         * Gets the value of the poz property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPoz() {
            if (poz == null) {
                return "P_5";
            } else {
                return poz;
            }
        }

        /**
         * Sets the value of the poz property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPoz(String value) {
            this.poz = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type</p>.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.</p>
     * 
     * <pre>{@code
     * <complexType>
     *   <simpleContent>
     *     <extension base="<http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITD/>TLData">
     *       <attribute name="poz" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="P_4" />
     *     </extension>
     *   </simpleContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class OkresOd {

        /**
         * Ograniczenie dla daty
         * 
         */
        @XmlValue
        protected XMLGregorianCalendar value;
        @XmlAttribute(name = "poz", required = true)
        protected String poz;

        /**
         * Ograniczenie dla daty
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         * @see #getValue()
         */
        public void setValue(XMLGregorianCalendar value) {
            this.value = value;
        }

        /**
         * Gets the value of the poz property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPoz() {
            if (poz == null) {
                return "P_4";
            } else {
                return poz;
            }
        }

        /**
         * Sets the value of the poz property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPoz(String value) {
            this.poz = value;
        }

    }

}
