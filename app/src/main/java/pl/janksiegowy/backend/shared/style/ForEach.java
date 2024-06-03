package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.fop.BlockContainer;
import pl.janksiegowy.backend.shared.fop.TableRow;

import java.util.List;

@Setter
@Accessors( chain= true)
@XmlType( name= "", propOrder= {

})
public class ForEach {

    @XmlAttribute( name= "select")
    protected String select;

    @XmlElement( name= "value-of")
    protected ValueOf valueOf;

    @XmlElement( name= "block-container", namespace= "http://www.w3.org/1999/XSL/Format")
    protected BlockContainer blockContainer;

    @XmlElement( name= "table-row", namespace= "http://www.w3.org/1999/XSL/Format")
    protected TableRow tableRow;

}
