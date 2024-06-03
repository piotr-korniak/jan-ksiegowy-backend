package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class WithParam {

    @XmlAttribute( name= "name")
    protected String name;

    @XmlAttribute( name= "select")
    protected String select;
}
