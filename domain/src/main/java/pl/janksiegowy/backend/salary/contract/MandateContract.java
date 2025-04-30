package pl.janksiegowy.backend.salary.contract;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "M")
public class MandateContract extends Contract {

    @Override public ContractType getType() {
        return ContractType.M;
    }
}
