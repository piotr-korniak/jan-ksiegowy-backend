package pl.janksiegowy.backend.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record InvoiceLineId( UUID id) {

    @JsonValue
    public UUID json() {
        return id;
    }

    @JsonCreator
    public static InvoiceLineId of( UUID value) {
        return new InvoiceLineId( value);
    }

    public static InvoiceLineId random() {
        return new InvoiceLineId( UUID.randomUUID());
    }
}
