package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlType( name= "value-of", namespace = "http://www.w3.org/1999/XSL/Transform")
public class ValueOf {

    @XmlAttribute
    protected String select;
}
