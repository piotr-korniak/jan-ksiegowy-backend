package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType( name= "position_Type")
@XmlEnum
public enum PositionType {

    @XmlEnumValue( "static")
    STATIC("static"),
    @XmlEnumValue( "relative")
    RELATIVE("relative"),
    @XmlEnumValue( "absolute")
    ABSOLUTE("absolute"),
    @XmlEnumValue( "fixed")
    FIXED("fixed"),
    @XmlEnumValue("inherit")
    INHERIT("inherit");

    private final String value;

    PositionType( String v) {
        value= v;
    }

    public String value() {
        return value;
    }

    public static PositionType fromValue( String v) {
        for (PositionType c: PositionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
