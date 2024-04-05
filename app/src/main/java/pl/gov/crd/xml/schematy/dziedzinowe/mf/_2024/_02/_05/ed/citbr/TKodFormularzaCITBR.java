//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2024._02._05.ed.citbr;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Symbol wzoru formularza
 * 
 * <p>Java class for TKodFormularza_CIT_BR</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="TKodFormularza_CIT_BR">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="CIT/BR"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "TKodFormularza_CIT_BR")
@XmlEnum
public enum TKodFormularzaCITBR {

    @XmlEnumValue("CIT/BR")
    CIT_BR("CIT/BR");
    private final String value;

    TKodFormularzaCITBR(String v) {
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
    public static TKodFormularzaCITBR fromValue(String v) {
        for (TKodFormularzaCITBR c: TKodFormularzaCITBR.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
