package pl.janksiegowy.backend.accounting.template;

import ch.qos.logback.core.recovery.ResilientOutputStreamBase;
import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.finances.payment.PaymentType;

import java.util.List;

@Getter

public abstract class PaymentTemplate extends Template{

    public PaymentTemplate setKind( PaymentType kind) {
        setKind( kind.name());
        return this;
    }
}
