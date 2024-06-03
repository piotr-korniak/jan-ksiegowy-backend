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
        "tableColumn",
        "tableHeader",
        "tableFooter",
        "tableBody"
})
@XmlRootElement( name= "table")
public class Table {

    @XmlElement( name= "table-column")
    protected List<TableColumn> tableColumn;

    @XmlElement( name= "table-header")
    protected TableHeader tableHeader;

    @XmlElement( name= "table-footer")
    protected TableFooter tableFooter;

    @XmlElement( name= "table-body", required = true)
    protected List<TableBody> tableBody;

    @XmlAttribute( name= "border")
    protected String border;

    public List<TableColumn> getTableColumn() {
        if( tableColumn== null)
            tableColumn= new ArrayList<>();
        return tableColumn;
    }

    public List<TableBody> getTableBody() {
        if( tableBody== null)
            tableBody= new ArrayList<>();
        return tableBody;
    }
}
