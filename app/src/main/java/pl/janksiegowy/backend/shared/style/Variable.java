package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "variable", namespace= "")
public class Variable {

    @XmlAttribute( name= "name")
    protected String name;

    @XmlAttribute( name= "select")
    protected String select;

    @XmlElement( name= "value-of", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected ValueOf valueOf;

    @XmlElement( name= "for-each", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected ForEach forEach;
}
