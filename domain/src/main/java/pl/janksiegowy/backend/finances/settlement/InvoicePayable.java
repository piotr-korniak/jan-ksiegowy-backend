package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.data.repository.cdi.Eager;

@Entity
@DiscriminatorValue( "Y")
public class InvoicePayable extends Payable{
    @Override
    public <T> T accept( SettlementVisitor<T> visitor ) {
        return null;
    }
}
