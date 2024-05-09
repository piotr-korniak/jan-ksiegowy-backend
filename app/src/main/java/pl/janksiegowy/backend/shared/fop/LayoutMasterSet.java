package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name = "")
public class LayoutMasterSet {

    @XmlElements({
            @XmlElement( name= "simple-page-master", type= SimplePageMaster.class)
            //@XmlElement(name = "page-sequence-master", type = PageSequenceMaster.class)
    })
    protected List<Object> simplePageMasterOrPageSequenceMaster;


}
