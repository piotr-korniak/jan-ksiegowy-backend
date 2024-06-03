package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.style.CallTemplate;
import pl.janksiegowy.backend.shared.style.ValueOf;

import java.util.List;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name = "", propOrder = {
        "content",
        "valueOf",
        "callTemplate"
})
@XmlRootElement( name= "block")
public class Block {

    @XmlElementRefs({
//            @XmlElementRef(name = "marker", namespace = "http://www.w3.org/1999/XSL/Format", type = Marker.class, required = false),
//            @XmlElementRef(name = "initial-property-set", namespace = "http://www.w3.org/1999/XSL/Format", type = InitialPropertySet.class, required = false),
//            @XmlElementRef(name = "character", namespace = "http://www.w3.org/1999/XSL/Format", type = Character.class, required = false),
            @XmlElementRef(name = "external-graphic", namespace= "http://www.w3.org/1999/XSL/Format", type= ExternalGraphic.class, required = false),
//            @XmlElementRef(name = "instream-foreign-object", namespace = "http://www.w3.org/1999/XSL/Format", type = InstreamForeignObject.class, required = false),
//            @XmlElementRef(name = "inline", namespace = "http://www.w3.org/1999/XSL/Format", type = Inline.class, required = false),
//            @XmlElementRef(name = "leader", namespace = "http://www.w3.org/1999/XSL/Format", type = Leader.class, required = false),
//            @XmlElementRef(name = "page-number", namespace = "http://www.w3.org/1999/XSL/Format", type = PageNumber.class, required = false),
//            @XmlElementRef(name = "page-number-citation", namespace = "http://www.w3.org/1999/XSL/Format", type = PageNumberCitation.class, required = false),
//            @XmlElementRef(name = "basic-link", namespace = "http://www.w3.org/1999/XSL/Format", type = BasicLink.class, required = false),
//            @XmlElementRef(name = "bidi-override", namespace = "http://www.w3.org/1999/XSL/Format", type = BidiOverride.class, required = false),
//            @XmlElementRef(name = "inline-container", namespace = "http://www.w3.org/1999/XSL/Format", type = InlineContainer.class, required = false),
//            @XmlElementRef(name = "multi-toggle", namespace = "http://www.w3.org/1999/XSL/Format", type = MultiToggle.class, required = false),
            @XmlElementRef(name = "block", namespace= "http://www.w3.org/1999/XSL/Format", type= Block.class, required = false),
            @XmlElementRef(name = "block-container", namespace= "http://www.w3.org/1999/XSL/Format", type= BlockContainer.class, required = false),
//            @XmlElementRef(name = "table", namespace = "http://www.w3.org/1999/XSL/Format", type = ProTable.class, required = false),
//            @XmlElementRef(name = "list-block", namespace = "http://www.w3.org/1999/XSL/Format", type = ListBlock.class, required = false),
//            @XmlElementRef(name = "table-and-caption", namespace = "http://www.w3.org/1999/XSL/Format", type = TableAndCaption.class, required = false),
//            @XmlElementRef(name = "wrapper", namespace = "http://www.w3.org/1999/XSL/Format", type = Wrapper.class, required = false),
//            @XmlElementRef(name = "retrieve-marker", namespace = "http://www.w3.org/1999/XSL/Format", type = RetrieveMarker.class, required = false),
//            @XmlElementRef(name = "multi-switch", namespace = "http://www.w3.org/1999/XSL/Format", type = MultiSwitch.class, required = false),
//            @XmlElementRef(name = "multi-properties", namespace = "http://www.w3.org/1999/XSL/Format", type = MultiProperties.class, required = false),
//            @XmlElementRef(name = "float", namespace = "http://www.w3.org/1999/XSL/Format", type = Float.class, required = false)
//            @XmlElementRef(name = "footnote", namespace = "http://www.w3.org/1999/XSL/Format", type = Footnote.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;

    @XmlElement( name= "value-of", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected ValueOf valueOf;

    @XmlElement( name= "call-template", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected CallTemplate callTemplate;

    @XmlAttribute( name= "space-before")
    protected String spaceBefore;

    @XmlAttribute( name= "text-align")
    protected TextAlignType textAlign;

    @XmlAttribute( name= "font-size")
    protected String fontSize;

    @XmlAttribute( name= "font-weight")
    protected String fontWeight;
}
