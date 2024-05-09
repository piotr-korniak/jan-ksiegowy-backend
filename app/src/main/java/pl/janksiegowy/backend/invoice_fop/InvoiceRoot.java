package pl.janksiegowy.backend.invoice_fop;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.fop.Root;
import pl.janksiegowy.backend.shared.style.Template;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name = "template")
public class InvoiceRoot extends Template {
    {
        match= "/";
    }

    @XmlElement( name= "root", namespace= "http://www.w3.org/1999/XSL/Format")
    public Root root;
    /*= new Root() {{
        setPageSequence( List.of( new PageSequence() {{


        }}));*/
/*
        setLayoutMasterSet( new LayoutMasterSet() {{

            valueOf = new ValueOf(){{
                select= transform("@Faktura/Podmiot1/DaneIdentyfikacyjne/Nazwa@");
            }};

        }});

        setLayoutMasterSet( new LayoutMasterSet() {{

            valueOf = new ValueOf(){{
                select= transform("Tylko Etykieta");
            }};

        }});

        setLayoutMasterSet( new LayoutMasterSet() {{
            valueOf = new ValueOf(){{
                select= transform( "NIP: @Faktura/Podmiot1/DaneIdentyfikacyjne/Nazwa@:@Faktura/Podmiot1/DaneIdentyfikacyjne/NIP@coś");

            }};

        }});

    }};
*/


}
