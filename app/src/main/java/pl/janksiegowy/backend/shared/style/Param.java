package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "param", namespace= "")
public class Param {

    @XmlAttribute( name= "name")
    protected String name;

    @XmlAttribute( name= "select")
    protected String select;
}
