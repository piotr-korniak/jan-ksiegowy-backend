package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", propOrder= {
        "layoutMasterSet",
        "pageSequence"
})
public abstract class Root {

    @XmlElement( name= "layout-master-set", required= true)
    private LayoutMasterSet layoutMasterSet;

    @XmlElement( name= "page-sequence", required= true)
    private List<PageSequence> pageSequence;

    @XmlAttribute( name= "font-family")
    protected String fontFamily;

    @XmlAttribute( name= "font-size")
    protected String fontSize;
}
