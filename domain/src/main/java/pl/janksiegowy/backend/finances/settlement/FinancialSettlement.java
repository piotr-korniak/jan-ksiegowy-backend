package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.DiscriminatorValue;
import pl.janksiegowy.backend.finances.document.Document;

public abstract class FinancialSettlement extends Document {
    public FinancialType getFinancialType() {
        return FinancialType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

}
