package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.*;
import pl.janksiegowy.backend.invoice_fop.InvoiceRoot;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", namespace= "")
@XmlSeeAlso( {InvoiceRoot.class})
public class Template {

    @XmlAttribute( name= "match")
    protected String match;

    @XmlAttribute( name= "name")
    protected String name;

}
