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
        "tableRow",
        "tableCell"
})
@XmlRootElement( name= "table-header")
public class TableHeader {

    @XmlElement( name= "table-row")
    protected List<TableRow> tableRow;

    @XmlElement(name = "table-cell")
    protected List<TableCell> tableCell;

    public List<TableRow> getTableRow() {
        return tableRow!= null?
                tableRow: new ArrayList<>();
    }

    public List<TableCell> getTableCell() {
        return tableCell!= null?
                tableCell: new ArrayList<TableCell>();
    }
}
