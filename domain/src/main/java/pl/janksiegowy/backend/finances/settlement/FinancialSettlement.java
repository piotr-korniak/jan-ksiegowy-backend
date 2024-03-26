package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.DiscriminatorValue;

public abstract class FinancialSettlement extends Settlement {
    public FinancialType getFinancialType() {
        return FinancialType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

}
