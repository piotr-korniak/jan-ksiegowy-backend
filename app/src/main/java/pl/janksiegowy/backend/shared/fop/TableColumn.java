package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "")
@XmlRootElement( name= "table-column")
public class TableColumn {

    @XmlAttribute( name = "column-width")
    protected String columnWidth;

    @XmlAttribute( name = "background-color")
    protected String backgroundColor;

}
