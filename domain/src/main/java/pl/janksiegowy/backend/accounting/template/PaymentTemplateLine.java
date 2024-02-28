package pl.janksiegowy.backend.accounting.template;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter

@Entity
@DiscriminatorValue( "Y")
public class PaymentTemplateLine extends TemplateLine {


    @Enumerated( EnumType.STRING)
    private PaymentFunction function;


    public PaymentTemplateLine setFunction( PaymentFunction function) {
        this.function= function;
        return this;
    }
}
