package pl.janksiegowy.backend.invoice_fop;


import jakarta.xml.bind.annotation.*;
import pl.janksiegowy.backend.shared.style.StyleSheet;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name = "", propOrder = {

})
@XmlRootElement( name= "", namespace= "")
public class InvoiceStyle extends StyleSheet {



    @XmlElement( name= "template")
    InvoiceRoot template= new InvoiceRoot();


}
