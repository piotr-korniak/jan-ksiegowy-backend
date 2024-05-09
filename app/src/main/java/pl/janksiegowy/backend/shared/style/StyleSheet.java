package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.*;
import pl.janksiegowy.backend.invoice_fop.InvoiceRoot;

import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement( name= "stylesheet", namespace= "http://www.w3.org/1999/XSL/Transform")
public class StyleSheet {

    @XmlAttribute( name= "version", required= true)
    protected String version= "1.0";

//    @XmlElement( name= "template")
//    InvoiceRoot template= new InvoiceRoot();

    @XmlElement( name= "template")
    private List<Template> templates;

    public StyleSheet setTemplates( List<Template> templates) {
        this.templates= templates;
        return this;
    }
}
