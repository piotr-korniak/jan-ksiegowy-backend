package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "")
public class PageSequence {

    @XmlElement( required= true)
    protected Flow flow;

    @XmlAttribute( name= "master-reference", required= true)
    protected String masterReference;
}
