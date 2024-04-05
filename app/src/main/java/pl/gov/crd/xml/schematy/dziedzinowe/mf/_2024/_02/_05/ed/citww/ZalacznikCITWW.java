//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2024._02._05.ed.citww;

import java.math.BigDecimal;
import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Naglowek" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2024/02/05/eD/CITWW/}TNaglowek_CIT_WW"/>
 *         <element name="PozycjeSzczegolowe">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <sequence>
 *                     <element name="P_7" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                     <element name="P_8" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                     <element name="P_9" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                     <element name="P_10" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                     <element name="P_11" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                   </sequence>
 *                   <sequence>
 *                     <element name="P_12" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
 *                     <element name="P_13" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
 *                     <element name="P_14" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna" minOccurs="0"/>
 *                     <element name="P_15" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
 *                   </sequence>
 *                 </sequence>
 *               </restriction>
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
@XmlType(name = "", propOrder = {
    "naglowek",
    "pozycjeSzczegolowe"
})
@XmlRootElement(name = "Zalacznik_CIT_WW")
public class ZalacznikCITWW {

    @XmlElement(name = "Naglowek", required = true)
    protected TNaglowekCITWW naglowek;
    /**
     * Dochód / koszty (art. 22d ustawy), obliczenie podatku należnego (art. 22d ustawy)
     * 
     */
    @XmlElement(name = "PozycjeSzczegolowe", required = true)
    protected PozycjeSzczegolowe pozycjeSzczegolowe;

    /**
     * Gets the value of the naglowek property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekCITWW }
     *     
     */
    public TNaglowekCITWW getNaglowek() {
        return naglowek;
    }

    /**
     * Sets the value of the naglowek property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekCITWW }
     *     
     */
    public void setNaglowek(TNaglowekCITWW value) {
        this.naglowek = value;
    }

    /**
     * Dochód / koszty (art. 22d ustawy), obliczenie podatku należnego (art. 22d ustawy)
     * 
     * @return
     *     possible object is
     *     {@link PozycjeSzczegolowe }
     *     
     */
    public PozycjeSzczegolowe getPozycjeSzczegolowe() {
        return pozycjeSzczegolowe;
    }

    /**
     * Sets the value of the pozycjeSzczegolowe property.
     * 
     * @param value
     *     allowed object is
     *     {@link PozycjeSzczegolowe }
     *     
     * @see #getPozycjeSzczegolowe()
     */
    public void setPozycjeSzczegolowe( PozycjeSzczegolowe value) {
        this.pozycjeSzczegolowe = value;
    }


    /**
     * <p>Java class for anonymous complex type</p>.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.</p>
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       <sequence>
     *         <sequence>
     *           <element name="P_7" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *           <element name="P_8" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *           <element name="P_9" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *           <element name="P_10" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *           <element name="P_11" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *         </sequence>
     *         <sequence>
     *           <element name="P_12" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
     *           <element name="P_13" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna"/>
     *           <element name="P_14" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwota2Nieujemna" minOccurs="0"/>
     *           <element name="P_15" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/09/13/eD/DefinicjeTypy/}TKwotaCNieujemna"/>
     *         </sequence>
     *       </sequence>
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "p7",
        "p8",
        "p9",
        "p10",
        "p11",
        "p12",
        "p13",
        "p14",
        "p15"
    })
    public static class PozycjeSzczegolowe {

        /**
         * Przychody, o których mowa w art. 7b ust. 1 pkt 6 lit. f ustawy
         * 
         */
        @XmlElement(name = "P_7", required = true)
        protected BigDecimal p7;
        /**
         * Koszty uzyskania przychodów poniesione w roku podatkowym, o których mowa w art.15 ust.11 i 12 ustawy
         * 
         */
        @XmlElement(name = "P_8", required = true)
        protected BigDecimal p8;
        /**
         * Koszty uzyskania przychodów poniesione w latach ubiegłych i niepotrącone w roku poprzednim
         * 
         */
        @XmlElement(name = "P_9", required = true)
        protected BigDecimal p9;
        /**
         * Dochód
         * 
         */
        @XmlElement(name = "P_10", required = true)
        protected BigDecimal p10;
        /**
         * Koszty uzyskania przychodów, które nie zostały potrącone w roku podatkowym
         * 
         */
        @XmlElement(name = "P_11", required = true)
        protected BigDecimal p11;
        /**
         * Dochód z odpłatnego zbycia walut wirtualnych
         * 
         */
        @XmlElement(name = "P_12", required = true)
        protected BigInteger p12;
        /**
         * Podatek należny wg stawki określonej w art. 22d ust. 1 ustawy
         * 
         */
        @XmlElement(name = "P_13", required = true)
        protected BigDecimal p13;
        /**
         * Podatek od dochodów z odpłatnego zbycia walut wirtualnych zapłacony za granicą, o którym mowa w art. 20 ust. 1 ustawy
         * 
         */
        @XmlElement(name = "P_14")
        protected BigDecimal p14;
        /**
         * Podatek należny
         * 
         */
        @XmlElement(name = "P_15", required = true)
        protected BigInteger p15;

        /**
         * Przychody, o których mowa w art. 7b ust. 1 pkt 6 lit. f ustawy
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP7() {
            return p7;
        }

        /**
         * Sets the value of the p7 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP7()
         */
        public void setP7(BigDecimal value) {
            this.p7 = value;
        }

        /**
         * Koszty uzyskania przychodów poniesione w roku podatkowym, o których mowa w art.15 ust.11 i 12 ustawy
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP8() {
            return p8;
        }

        /**
         * Sets the value of the p8 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP8()
         */
        public void setP8(BigDecimal value) {
            this.p8 = value;
        }

        /**
         * Koszty uzyskania przychodów poniesione w latach ubiegłych i niepotrącone w roku poprzednim
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP9() {
            return p9;
        }

        /**
         * Sets the value of the p9 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP9()
         */
        public void setP9(BigDecimal value) {
            this.p9 = value;
        }

        /**
         * Dochód
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP10() {
            return p10;
        }

        /**
         * Sets the value of the p10 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP10()
         */
        public void setP10(BigDecimal value) {
            this.p10 = value;
        }

        /**
         * Koszty uzyskania przychodów, które nie zostały potrącone w roku podatkowym
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP11() {
            return p11;
        }

        /**
         * Sets the value of the p11 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP11()
         */
        public void setP11(BigDecimal value) {
            this.p11 = value;
        }

        /**
         * Dochód z odpłatnego zbycia walut wirtualnych
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getP12() {
            return p12;
        }

        /**
         * Sets the value of the p12 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         * @see #getP12()
         */
        public void setP12(BigInteger value) {
            this.p12 = value;
        }

        /**
         * Podatek należny wg stawki określonej w art. 22d ust. 1 ustawy
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP13() {
            return p13;
        }

        /**
         * Sets the value of the p13 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP13()
         */
        public void setP13(BigDecimal value) {
            this.p13 = value;
        }

        /**
         * Podatek od dochodów z odpłatnego zbycia walut wirtualnych zapłacony za granicą, o którym mowa w art. 20 ust. 1 ustawy
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getP14() {
            return p14;
        }

        /**
         * Sets the value of the p14 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         * @see #getP14()
         */
        public void setP14(BigDecimal value) {
            this.p14 = value;
        }

        /**
         * Podatek należny
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getP15() {
            return p15;
        }

        /**
         * Sets the value of the p15 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         * @see #getP15()
         */
        public void setP15(BigInteger value) {
            this.p15 = value;
        }

    }

}
