package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.style.ForEach;

import java.util.ArrayList;
import java.util.List;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", propOrder= {
        "markerOrBlockOrBlockContainer"
})
@XmlRootElement( name= "table-cell")
public class TableCell {

    @XmlElements({
            //@XmlElement( name= "marker", type = Marker.class),
            @XmlElement( name= "block", type= Block.class),
            @XmlElement( name= "block-container", type= BlockContainer.class),
            @XmlElement(name = "table", type= Table.class),
            //@XmlElement(name = "list-block", type = ListBlock.class),
            //@XmlElement(name = "table-and-caption", type = TableAndCaption.class),
            //@XmlElement(name = "wrapper", type = Wrapper.class),
            //@XmlElement(name = "retrieve-marker", type = RetrieveMarker.class),
            //@XmlElement(name = "multi-switch", type = MultiSwitch.class),
            //@XmlElement(name = "multi-properties", type = MultiProperties.class),
            @XmlElement(name = "float", type = Float.class),
            //@XmlElement(name = "footnote", type = Footnote.class
            @XmlElement( name= "for-each", type= ForEach.class, namespace= "http://www.w3.org/1999/XSL/Transform")
    })
    protected List<Object> markerOrBlockOrBlockContainer;

    @XmlAttribute( name= "padding")
    protected String padding;

    @XmlAttribute( name= "padding-left")
    protected String paddingLeft;

    @XmlAttribute( name= "padding-right")
    protected String paddingRight;

    @XmlAttribute( name= "padding-bottom")
    protected String paddingBottom;

    @XmlAttribute( name= "padding-top")
    protected String paddingTop;

    @XmlAttribute( name= "color")
    protected String color;

    @XmlAttribute( name= "background-color")
    protected String backgroundColor;

    @XmlAttribute( name= "display-align")
    protected String displayAlign;

    @XmlAttribute( name= "border")
    protected String border;

    @XmlAttribute( name= "border-bottom")
    protected String borderBottom;

    public List<Object> getMarkerOrBlockOrBlockContainer() {
        if (markerOrBlockOrBlockContainer== null)
            markerOrBlockOrBlockContainer= new ArrayList<>();
        return markerOrBlockOrBlockContainer;
    }
}
