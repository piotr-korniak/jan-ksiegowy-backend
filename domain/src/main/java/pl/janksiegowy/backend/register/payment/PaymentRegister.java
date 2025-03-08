package pl.janksiegowy.backend.register.payment;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;

@Getter

@Entity
public abstract class PaymentRegister extends Register {
/*
    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private PaymentRegisterType type;

    @Enumerated( EnumType.STRING)
    private PaymentRegisterKind kind= PaymentRegisterKind.P;
*/
}
