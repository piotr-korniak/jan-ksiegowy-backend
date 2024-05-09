package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import pl.janksiegowy.backend.shared.style.ValueOf;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name = "", propOrder = {
        "content",
        "value"
})
@XmlRootElement( name = "block")
public class Block {
    protected String content;

    @XmlElement( name = "value-of", namespace = "http://www.w3.org/1999/XSL/Transform")
    protected ValueOf value;
}
