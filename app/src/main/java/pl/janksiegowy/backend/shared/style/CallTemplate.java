package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class CallTemplate {

    @XmlAttribute( name= "name")
    protected String name;

    @XmlElement( name="with-param")
    protected WithParam withParam;
}
