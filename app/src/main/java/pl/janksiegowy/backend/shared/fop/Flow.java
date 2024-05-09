package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement( name= "flow")
public class Flow {
    @XmlElements({
      //      @XmlElement(name = "marker", type = Marker.class),
      //      @XmlElement(name = "block", type = Block.class),
            @XmlElement(name = "block-container", type = BlockContainer.class)
      //      @XmlElement(name = "table", type = ProTable.class),
      //      @XmlElement(name = "list-block", type = ListBlock.class),
      //      @XmlElement(name = "table-and-caption", type = TableAndCaption.class),
      //      @XmlElement(name = "wrapper", type = Wrapper.class),
      //      @XmlElement(name = "retrieve-marker", type = RetrieveMarker.class),
      //      @XmlElement(name = "multi-switch", type = MultiSwitch.class),
      //      @XmlElement(name = "multi-properties", type = MultiProperties.class),
      //      @XmlElement(name = "float", type = Float.class),
      //      @XmlElement(name = "footnote", type = Footnote.class),
      //      @XmlElement( name= "apply-templates", type= ApplyTemplates.class, namespace= "http://www.w3.org/1999/XSL/Transform")

    })
    protected List<Object> container;

    @XmlAttribute( name= "flow-name", required= true)
    protected String flowName;
}
