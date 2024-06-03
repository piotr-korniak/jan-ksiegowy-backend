package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.*;
import pl.janksiegowy.backend.invoice_fop.InvoiceRoot;

import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement( name= "stylesheet", namespace= "http://www.w3.org/1999/XSL/Transform")
public class StyleSheet {

    @XmlAttribute( name= "version", required= true)
    protected String version= "2.0";

    @XmlElement( name= "decimal-format")
    protected DecimalFormat decimalFormat= new DecimalFormat();

    @XmlElement( name= "param")
    protected List<Param> parameters= List.of(
            new Param().setName( "image-directory"),
            new Param().setName( "jednosci_lista")
                    .setSelect( "tokenize( 'zero :jeden :dwa :trzy :cztery :pięć :sześć :" +
                                "siedem :osiem :dziewięć ', ':')"),
            new Param().setName( "nastki_lista")
                    .setSelect( "tokenize( 'dziesięć :jedenaście :dwanaście :trzynaście :czternaście :piętnaście :" +
                                " szesnaście :siedemnaście :osiemnaście :dziewiętnaście ', ':')"),
            new Param().setName( "dziesiatki_lista")
                    .setSelect( "tokenize( 'dwadzieścia :trzydzieści :czterdzieści :pięćdziesiąt :sześćdziesiąt :" +
                                "siedemdziesiąt :osiemdziesiąt :dziewięćdziesiąt ', ':')"),
            new Param().setName( "setki_lista")
                    .setSelect( "tokenize( 'sto :dwieście :trzysta :czterysta :pięćset :sześćset :siedemset :" +
                                "osiemset :dziewięćset ', ':')"),
            new Param().setName( "tysiace_lista").setSelect( "tokenize( 'tysiąc :tysiące :tysięcy ', ':')"),
            new Param().setName( "miliony_lista").setSelect( "tokenize( 'milion :miliony :milionów ', ':')"));

    @XmlElement( name= "template")
    protected NumberToWords numberToWords= new NumberToWords();

    @XmlElement( name= "template")
    protected FormatAccountNumber formatAccountNumber= new FormatAccountNumber();


//    @XmlElement( name= "template")
//    InvoiceRoot template= new InvoiceRoot();

    @XmlElement( name= "template")
    private List<Template> templates;

    public StyleSheet setTemplates( List<Template> templates) {
        this.templates= templates;
        return this;
    }
}
