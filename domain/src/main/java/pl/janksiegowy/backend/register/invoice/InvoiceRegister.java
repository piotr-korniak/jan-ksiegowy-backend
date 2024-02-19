package pl.janksiegowy.backend.register.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;

@Getter

@Entity
public abstract class InvoiceRegister extends Register {

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private InvoiceRegisterType type;

    @Enumerated( EnumType.STRING)
    private InvoiceRegisterKind kind;

    public InvoiceRegister setKind( InvoiceRegisterKind kind) {

        this.kind= kind;
        return this;
    }

}
