package pl.janksiegowy.backend.register.vat;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.janksiegowy.backend.register.Register;

@Entity
@DiscriminatorValue(value = "P")
public class VatPurchaseRegister extends Register {

    @Enumerated( EnumType.STRING)
    private VatRegisterKind kind;

    public VatPurchaseRegister setKind( VatRegisterKind kind) {
        this.kind= kind;
        return this;
    }
}
