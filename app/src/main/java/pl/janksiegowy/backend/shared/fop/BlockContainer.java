package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", propOrder = {
        "markerOrBlockOrBlockContainer"
})
@XmlRootElement( name= "block-container")
public class BlockContainer {

    @XmlElements({
    //        @XmlElement(name = "marker", type = Marker.class),
            @XmlElement(name = "block", type = Block.class),
    //        @XmlElement(name = "block-container", type = BlockContainer.class),
    //        @XmlElement(name = "table", type = ProTable.class),
    //        @XmlElement(name = "list-block", type = ListBlock.class),
    //        @XmlElement(name = "table-and-caption", type = TableAndCaption.class),
    //        @XmlElement(name = "wrapper", type = Wrapper.class),
    //        @XmlElement(name = "retrieve-marker", type = RetrieveMarker.class),
    //        @XmlElement(name = "multi-switch", type = MultiSwitch.class),
    //        @XmlElement(name = "multi-properties", type = MultiProperties.class),
    //        @XmlElement(name = "float", type = Float.class),
    //        @XmlElement(name = "footnote", type = Footnote.class)
    })
    protected List<Object> markerOrBlockOrBlockContainer;

    @XmlAttribute( name= "top")
    protected String top;

    @XmlAttribute( name= "left")
    protected String left;

    @XmlAttribute( name= "position")
    protected PositionType position;

    @XmlAttribute( name= "width")
    protected String width;

    @XmlAttribute( name= "background-color")
    protected String backgroundColor;

    @XmlAttribute( name= "font-weight")
    protected String fontWeight;

    @XmlAttribute( name= "padding")
    protected String padding;

    @XmlAttribute( name= "padding-top")
    protected String paddingTop;

    @XmlAttribute( name= "padding-left")
    protected String paddingLeft;

    @XmlAttribute( name= "font-size")
    protected String fontSize;

    @XmlAttribute( name= "color")
    protected String color;

    @XmlAttribute( name= "border-bottom")
    protected String borderBottom;

    @XmlAttribute( name= "text-align")
    protected String textAlign;

    public List<Object> getMarkerOrBlockOrBlockContainer() {
        if (markerOrBlockOrBlockContainer== null)
            markerOrBlockOrBlockContainer= new ArrayList<>();
        return markerOrBlockOrBlockContainer;
    }
}
