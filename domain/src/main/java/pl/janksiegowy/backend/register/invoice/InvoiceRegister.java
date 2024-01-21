package pl.janksiegowy.backend.register.invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;

@Getter

@Entity
public class InvoiceRegister extends Register {

    @Enumerated( EnumType.STRING)
    private InvoiceRegisterKind kind;

    public InvoiceRegister setKind( InvoiceRegisterKind kind) {
        this.kind= kind;
        return this;
    }

}
