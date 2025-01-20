package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.Settlement;

@Entity
@DiscriminatorValue( "D")
public class DocumentDecree extends Decree {

    @OneToOne
    @JoinColumn( name= "ID", nullable= false)
    private Document decree;
}
