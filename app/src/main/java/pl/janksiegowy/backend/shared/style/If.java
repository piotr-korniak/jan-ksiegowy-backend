package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.fop.TableRow;

@Setter
@Accessors( chain= true)
@XmlType( name= "", propOrder= {
        "valueOf",
        "tableRow",
        "suffix"
})
public class If {

    @XmlAttribute( name= "test")
    protected String test;

    @XmlElement( name= "value-of")
    protected ValueOf valueOf;

    @XmlElement( name = "text")
    protected Text suffix;

    @XmlElement( name= "table-row", namespace= "http://www.w3.org/1999/XSL/Format")
    protected TableRow tableRow;

}
