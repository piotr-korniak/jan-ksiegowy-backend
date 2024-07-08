package pl.janksiegowy.backend.accounting.template;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.payment.PaymentRegister;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "P")
public class PaymentTemplateLine extends TemplateLine {

    @Enumerated( EnumType.STRING)
    private PaymentFunction function;


}
