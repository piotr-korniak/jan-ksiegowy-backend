package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "regionBody"
//        "regionBefore",
//        "regionAfter",
//        "regionStart",
//        "regionEnd"
})
@XmlRootElement( name= "simple-page-master")
public class SimplePageMaster {

    @XmlElement( name= "region-body", required= true)
    protected RegionBody regionBody;

    @XmlAttribute( name= "master-name", required= true)
    protected String masterName;

    @XmlAttribute( name= "margin-left")
    protected String marginLeft;
}
