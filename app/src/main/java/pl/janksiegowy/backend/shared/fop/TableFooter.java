package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", propOrder= {
        "tableRow",
        "tableCell"
})
@XmlRootElement( name= "table-footer")
public class TableFooter {

    @XmlElement( name= "table-row")
    protected List<TableRow> tableRow;

    @XmlElement(name = "table-cell")
    protected List<TableCell> tableCell;
}
