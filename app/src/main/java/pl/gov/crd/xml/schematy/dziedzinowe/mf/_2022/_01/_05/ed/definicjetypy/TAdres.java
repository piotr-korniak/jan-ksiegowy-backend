//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.01.30 at 10:37:45 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Dane określające adres
 * 
 * &lt;p&gt;Java class for TAdres complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TAdres"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;sequence&amp;gt;
 *           &amp;lt;element name="AdresPol" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TAdresPolski"/&amp;gt;
 *         &amp;lt;/sequence&amp;gt;
 *         &amp;lt;sequence&amp;gt;
 *           &amp;lt;element name="AdresZagr" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/01/05/eD/DefinicjeTypy/}TAdresZagraniczny"/&amp;gt;
 *         &amp;lt;/sequence&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAdres", propOrder = {
    "adresPol",
    "adresZagr"
})
@XmlSeeAlso({
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TOsobaFizyczna.AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TOsobaFizyczna1 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TOsobaFizyczna2 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TOsobaNiefizyczna.AdresSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TPodmiotDowolny.AdresZamieszkaniaSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TOsobaFizycznaPelna.AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TOsobaNiefizycznaPelna.AdresSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TPodmiotDowolnyPelny.AdresZamieszkaniaSiedziby.class
})
public class TAdres {

    @XmlElement(name = "AdresPol")
    protected TAdresPolski adresPol;
    @XmlElement(name = "AdresZagr")
    protected TAdresZagraniczny adresZagr;

    /**
     * Gets the value of the adresPol property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresPolski }
     *     
     */
    public TAdresPolski getAdresPol() {
        return adresPol;
    }

    /**
     * Sets the value of the adresPol property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresPolski }
     *     
     */
    public void setAdresPol(TAdresPolski value) {
        this.adresPol = value;
    }

    /**
     * Gets the value of the adresZagr property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresZagraniczny }
     *     
     */
    public TAdresZagraniczny getAdresZagr() {
        return adresZagr;
    }

    /**
     * Sets the value of the adresZagr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresZagraniczny }
     *     
     */
    public void setAdresZagr(TAdresZagraniczny value) {
        this.adresZagr = value;
    }

}
