package pl.janksiegowy.backend.shared.fop;

import jakarta.xml.bind.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@Setter
@Accessors( chain= true)

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "", propOrder= {
        "tableCell"
})
@XmlRootElement( name= "table-row")
public class TableRow {

    @XmlElement( name= "table-cell", required= true)
    protected List<TableCell> tableCell;

    public List<TableCell> getTableCell() {
        if( tableCell== null)
            tableCell= new ArrayList<>();
        return tableCell;
    }
}
