package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name= "external-graphic")
public class ExternalGraphic {

    @XmlAttribute( name= "content-height")
    protected String contentHeight= "scale-to-fit";

    @XmlAttribute( name= "height")
    protected String height;

    @XmlAttribute( name= "width")
    protected String width;

    @XmlAttribute( name= "src")
    protected String src;
}
