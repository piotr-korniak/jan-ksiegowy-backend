//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._09._13.ed.ordzu;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Symbol wzoru formularza
 * 
 * <p>Java class for TKodFormularza_ZU</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="TKodFormularza_ZU">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="ORD-ZU"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "TKodFormularza_ZU")
@XmlEnum
public enum TKodFormularzaZU {

    @XmlEnumValue("ORD-ZU")
    ORD_ZU("ORD-ZU");
    private final String value;

    TKodFormularzaZU(String v) {
        value = v;
    }

    /**
     * Gets the value associated to the enum constant.
     * 
     * @return
     *     The value linked to the enum.
     */
    public String value() {
        return value;
    }

    /**
     * Gets the enum associated to the value passed as parameter.
     * 
     * @param v
     *     The value to get the enum from.
     * @return
     *     The enum which corresponds to the value, if it exists.
     * @throws IllegalArgumentException
     *     If no value matches in the enum declaration.
     */
    public static TKodFormularzaZU fromValue(String v) {
        for (TKodFormularzaZU c: TKodFormularzaZU.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
