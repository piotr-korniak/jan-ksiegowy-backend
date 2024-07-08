package pl.janksiegowy.backend.accounting.template;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "F")
public class FinanceTemplateLine extends TemplateLine {

    @Enumerated( EnumType.STRING)
    private SettlementFunction function;

}
