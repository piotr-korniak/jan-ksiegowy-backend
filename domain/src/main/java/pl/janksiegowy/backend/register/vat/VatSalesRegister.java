package pl.janksiegowy.backend.register.vat;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.janksiegowy.backend.register.Register;

@Entity
@DiscriminatorValue(value = "S")
public class VatSalesRegister extends Register {

    @Enumerated( EnumType.STRING)
    private VatRegisterKind kind;

    public VatSalesRegister setKind( VatRegisterKind kind) {
        this.kind= kind;
        return this;
    }

}
