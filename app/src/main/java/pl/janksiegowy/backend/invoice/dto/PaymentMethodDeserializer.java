package pl.janksiegowy.backend.invoice.dto;

import com.fasterxml.jackson.databind.util.StdConverter;
import pl.janksiegowy.backend.shared.financial.PaymentMethod;

public class PaymentMethodDeserializer extends StdConverter<String, PaymentMethod> {
    @Override
    public PaymentMethod convert( String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return PaymentMethod.valueOf(value.trim().toUpperCase());
    }
}