package pl.janksiegowy.backend.register.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.Register;

@Getter
@Setter
@Accessors( chain= true)

@Entity
public abstract class InvoiceRegister extends Register {

    @Enumerated( EnumType.STRING)
    private InvoiceRegisterKind kind;

}
