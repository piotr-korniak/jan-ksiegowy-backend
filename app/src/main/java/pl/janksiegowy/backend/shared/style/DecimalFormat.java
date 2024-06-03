package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", namespace= "")
public class DecimalFormat {

    @XmlAttribute( name= "name")
    protected String name= "european";

    @XmlAttribute( name= "decimal-separator")
    protected String decimalSeparator= ",";

    @XmlAttribute( name= "grouping-separator")
    protected String groupingSeparator= ".";

}
