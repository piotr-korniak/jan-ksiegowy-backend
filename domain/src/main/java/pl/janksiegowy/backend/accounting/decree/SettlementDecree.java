package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.settlement.Settlement;

@Entity
@DiscriminatorValue( "S")
public class SettlementDecree extends Decree {

    @OneToOne( cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn( name= "ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Settlement settlement;

    public SettlementDecree setSettlement( Settlement settlement) {
        this.settlement= settlement;
        return this;
    }

}
