package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.settlement.Settlement;

@Entity
@DiscriminatorValue( "D")
public class DocumentDecree extends Decree {

    @OneToOne( cascade= CascadeType.ALL)
    @JoinColumn( name= "ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Settlement settlement;

    public DocumentDecree setSettlement( Settlement settlement) {
        this.settlement= settlement;
        return this;
    }

}
