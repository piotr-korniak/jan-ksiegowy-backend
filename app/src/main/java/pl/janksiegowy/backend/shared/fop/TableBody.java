package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.style.ForEach;
import pl.janksiegowy.backend.shared.style.If;

import java.util.ArrayList;
import java.util.List;

@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", propOrder= {
        "tableRow",
        "tableCell",
        "forEach",
        "rowIf"
})
@XmlRootElement( name= "table-body")
public class TableBody {

    @XmlElement( name= "table-row")
    protected List<TableRow> tableRow;

    @XmlElement( name= "table-cell")
    protected List<TableCell> tableCell;

    @XmlElement( name= "for-each", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected ForEach forEach;

    @XmlElement( name= "if", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected If rowIf;

    public List<TableRow> getTableRow() {
        return tableRow!= null?
                tableRow: new ArrayList<>();
    }

    public List<TableCell> getTableCell() {
        return tableCell!= null?
                tableCell: new ArrayList<TableCell>();
    }
}
